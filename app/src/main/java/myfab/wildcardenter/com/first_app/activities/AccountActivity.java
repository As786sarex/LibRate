package myfab.wildcardenter.com.first_app.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import myfab.wildcardenter.com.first_app.R;
import myfab.wildcardenter.com.first_app.fragments.AccountEditFragment;
import myfab.wildcardenter.com.first_app.fragments.MySellItemFragment;
import myfab.wildcardenter.com.first_app.models.UserModel;

public class AccountActivity extends AppCompatActivity {
    private FirebaseUser user;
    CircularImageView profile_act_image;
    TextView profile_act_Name, profile_act_Number, profile_act_Email, profile_act_EditButton,showMySellItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = findViewById(R.id.toolbarAccountAct);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle("");
        profile_act_image = findViewById(R.id.My_profile_Act_image);
        profile_act_Name = findViewById(R.id.My_profile_Act_Name);
        profile_act_Number = findViewById(R.id.My_profile_Act_Number);
        profile_act_Email = findViewById(R.id.My_profile_Act_Email);
        profile_act_EditButton = findViewById(R.id.My_profile_Act_EditButton);
        showMySellItems=findViewById(R.id.ViewMySellItemButton);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        profile_act_EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.AccountFragmentHolder,new AccountEditFragment()).addToBackStack(null)
                        .commit();
                profile_act_EditButton.setVisibility(View.GONE);
            }
        });
        showMySellItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.AccountFragmentHolder,new MySellItemFragment())
                        .addToBackStack(null).commit();
            }
        });
        loadProfileDesc();
    }

    private void loadProfileDesc() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel userModel = dataSnapshot.getValue(UserModel.class);
                if (userModel != null) {
                    Picasso.with(AccountActivity.this).load(userModel.getImageUri()).fit().into(profile_act_image);
                    profile_act_Name.setText(userModel.getUsername());
                    if(userModel.getContactNumber()!=null) {
                        if (userModel.getContactNumber().isEmpty()) {
                            profile_act_Number.setVisibility(View.GONE);
                        } else {
                            profile_act_Number.setVisibility(View.VISIBLE);
                            profile_act_Number.setText(userModel.getContactNumber());
                        }
                    }

                    profile_act_Email.setText(userModel.getEmail());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            profile_act_EditButton.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }
}
