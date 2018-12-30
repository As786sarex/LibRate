package myfab.wildcardenter.com.first_app.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;
import java.util.Objects;

import myfab.wildcardenter.com.first_app.R;
import myfab.wildcardenter.com.first_app.activities.AccountActivity;
import myfab.wildcardenter.com.first_app.activities.MainActivity;
import myfab.wildcardenter.com.first_app.activities.SellActivitySelector;
import myfab.wildcardenter.com.first_app.models.UserModel;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountEditFragment extends Fragment {

    MaterialEditText editName,editEmail,editPhone;
    CircularImageView profileImg;
    AppCompatButton updaterofileButton;
    TextView updateProfilePicButton;
    private Uri imageUri;
    private StorageReference storageReference;
    private StorageTask uploadTask;
    String profileImageUri;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_account_edit, container, false);
        editName=view.findViewById(R.id.EditProfileNameChange);
        editPhone=view.findViewById(R.id.EditProfileMobileChange);
        profileImg=view.findViewById(R.id.EditProfileImage);
        updaterofileButton=view.findViewById(R.id.UpdateProfileButton);
        updateProfilePicButton=view.findViewById(R.id.ProfilePicEditButton);
        mAuth=FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();
        storageReference=FirebaseStorage.getInstance().getReference("Profile_Images");
        updateProfilePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setAspectRatio(1, 1)
                        .setFixAspectRatio(true)
                        .start(getContext(),AccountEditFragment.this);
            }
        });
        updaterofileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        return  view;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE&&resultCode==RESULT_OK){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            imageUri=result.getUri();
            Picasso.with(getContext()).load(imageUri).fit().into(profileImg);
        }
        else{
            Toast.makeText(getContext(), "cancelled!", Toast.LENGTH_SHORT).show();
        }
    }
}
