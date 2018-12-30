package myfab.wildcardenter.com.first_app.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import myfab.wildcardenter.com.first_app.R;
import myfab.wildcardenter.com.first_app.models.SellModel;

public class DetailedSellActivity extends AppCompatActivity {
    String str="";
    ImageView detailedPostImage;
    TextView detailedBookName,detailedBookPrice,detailedBookDept_Sem,detailedBookDesc,detailedFavButton,detailedContactButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_sell);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null){
            str=bundle.getString("putPostId");
        }
        detailedPostImage=findViewById(R.id.DetailedPostImage);
        detailedBookName=findViewById(R.id.DetailedBookName);
        detailedBookPrice=findViewById(R.id.DetailedBookPrice);
        detailedBookDept_Sem=findViewById(R.id.DetailedDept_Semester);
        detailedBookDesc=findViewById(R.id.DetailedPostDescription);
        detailedFavButton=findViewById(R.id.DetailedFavouriteButton);
        detailedContactButton=findViewById(R.id.DetailedContactButton);
        readAndSetPostsToViews();
        detailedFavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailedSellActivity.this, "Favorite Button", Toast.LENGTH_SHORT).show();
            }
        });
        detailedContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailedSellActivity.this, "Contact Button", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void readAndSetPostsToViews() {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Sell_posts").child(str);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SellModel model=dataSnapshot.getValue(SellModel.class);
                Picasso.with(DetailedSellActivity.this).load(model.getPostImage()).fit().placeholder(R.color.colorAccent).into(detailedPostImage);
                detailedBookName.setText(model.getBookName());
                detailedBookPrice.setText(model.getBookPrice());
                detailedBookDept_Sem.setText(String.format("%s %s", model.getForDepartment(), model.getFor_Semester()));
                detailedBookDesc.setText(model.getBookDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
