package myfab.wildcardenter.com.first_app.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import myfab.wildcardenter.com.first_app.R;

public class SellActivitySelector extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Uri imageUri;
    String myimageUri, getBookName, getBookPrice, getBookDesc;
    StorageTask uploadTask;
    StorageReference storageReference;

    private EditText bookName, bookPrice, bookDesc;
    ImageView bookImage, cancelUpload;
    TextView uploadPost;
    String onWhichItemSpinner,onWhichDeptSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_selector);
        Spinner spinner = findViewById(R.id.Spinner);
        Spinner deptSpinner = findViewById(R.id.SpinnerDept);
        bookName = findViewById(R.id.sellItemNameSelector);
        bookPrice = findViewById(R.id.sellItemPriceSelector);
        bookDesc = findViewById(R.id.sellItemNoteSelector);
        bookImage = findViewById(R.id.sellItemImageSelector);
        cancelUpload = findViewById(R.id.uploadClose);
        uploadPost = findViewById(R.id.UploadPost);

        List<String> spinnerList = new ArrayList<>();
        spinnerList.add("1st Semester");
        spinnerList.add("2nd Semester");
        spinnerList.add("3rd Semester");
        spinnerList.add("4th Semester");
        spinnerList.add("5th Semester");
        spinnerList.add("6th Semester");
        spinnerList.add("7th Semester");
        spinnerList.add("8th Semester");

        ArrayAdapter<String> deptArrayAdepter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.SpinnerDeptItems));
        deptSpinner.setAdapter(deptArrayAdepter);
        deptSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onWhichDeptSpinner = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                spinnerList);
        stringArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(stringArrayAdapter);
        spinner.setOnItemSelectedListener(this);
        storageReference = FirebaseStorage.getInstance().getReference("Sell_Posts");
        cancelUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SellActivitySelector.this, MainActivity.class));
                finish();
            }
        });
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }

        final boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();


            uploadPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getBookName = bookName.getText().toString().trim();
                    getBookDesc = bookDesc.getText().toString().trim();
                    getBookPrice = bookPrice.getText().toString().trim();

                    if (getBookName.isEmpty() || getBookPrice.isEmpty() || getBookDesc.isEmpty()) {
                        Toast.makeText(SellActivitySelector.this, "Fields are required!!", Toast.LENGTH_SHORT).show();
                    } else if (!isConnected) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SellActivitySelector.this);
                        builder.setMessage("\nOops....Looks like you are not connected to the Internet." +
                                "\n\n").setTitle("Network Error!!").setCancelable(true)
                                .setIcon(R.mipmap.ic_launcher).show();

                    } else {
                        try {
                            uploadImg();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });



            CropImage.activity()
                    .setAspectRatio(1, 1)
                    .setFixAspectRatio(true)
                    .start(SellActivitySelector.this);
        }


    private void uploadImg(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading..");
        progressDialog.show();
        if (imageUri != null) {
            final StorageReference file = storageReference.child(System.currentTimeMillis()
                    + ".jpeg");

            uploadTask = file.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return file.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        myimageUri = downloadUri.toString();
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Sell_posts");

                        String postId = reference.push().getKey();

                        HashMap<String, Object> objectHashMap = new HashMap<>();
                        objectHashMap.put("postId", postId);
                        objectHashMap.put("postImage", myimageUri);
                        objectHashMap.put("forDepartment",onWhichDeptSpinner);
                        objectHashMap.put("for_Semester", onWhichItemSpinner);
                        objectHashMap.put("bookName", getBookName);
                        objectHashMap.put("bookPrice", getBookPrice);
                        objectHashMap.put("bookDescription", getBookDesc);
                        objectHashMap.put("publisher", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        reference.child(postId).setValue(objectHashMap);
                        progressDialog.dismiss();
                        startActivity(new Intent(SellActivitySelector.this, MainActivity.class));
                        finish();

                    } else {
                        Toast.makeText(SellActivitySelector.this, "Failed to upload!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SellActivitySelector.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        } else {
            Toast.makeText(this, "No Images Selected!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.onWhichItemSpinner = (String) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            bookImage.setImageURI(imageUri);
        } else {
            Toast.makeText(this, "Cancelled!!!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SellActivitySelector.this, MainActivity.class));
            finish();
        }
    }
}
