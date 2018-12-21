package myfab.wildcardenter.com.first_app.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import myfab.wildcardenter.com.first_app.R;
import myfab.wildcardenter.com.first_app.fragments.Request_Fragment;
import myfab.wildcardenter.com.first_app.fragments.Sell_Fragment;

public class MainActivity extends AppCompatActivity {

    private static final int RECORD_REQUEST_CODE = 1234;
    private static boolean ON_BACK_PRESSED = true;
    private static int FRAGMENT_LISTENER =0;
    private static final String TAG = "MainActivity";

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    private FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();
        NavigationView navigationView = findViewById(R.id.navView);
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.WebCliID))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_cont, new Sell_Fragment())
                    .commit();
            FRAGMENT_LISTENER=1;
            getSupportActionBar().setTitle(R.string.sell_books);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.sell_fragment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_cont, new Sell_Fragment()).commit();
                        getSupportActionBar().setTitle(R.string.sell_books);
                        FRAGMENT_LISTENER=1;
                        break;
                    case R.id.Request_fragment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_cont, new Request_Fragment()).commit();
                        getSupportActionBar().setTitle(R.string.request_books);
                        FRAGMENT_LISTENER=2;
                        break;
                    case R.id.nav_statement:
                        Toast.makeText(getApplicationContext(), "You pressed Statement", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_locate:
                        Toast.makeText(getApplicationContext(), "You pressed Locate", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_settings:
                        Toast.makeText(getApplicationContext(), "You pressed Settings", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_contact:
                        Toast.makeText(getApplicationContext(), "You pressed Contact", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_logOut:
                        mAuth.signOut();
                        mGoogleSignInClient.revokeAccess();

                        Intent intent = new Intent(MainActivity.this, Login.class);
                        startActivity(intent);
                        finish();
                        break;

                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }


        });
        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.sell_fragment);
        }
        int permission1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission1 != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to record denied");
            makeRequest();
        }

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (ON_BACK_PRESSED) {
            Toast.makeText(this, "Tap again to Exit", Toast.LENGTH_SHORT).show();
            ON_BACK_PRESSED = false;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        updateUI(user);
    }

    private void setUpToolbar() {

        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle =
                new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile_menu:
                Toast.makeText(this, "Profile selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.addSellItem:
                if(FRAGMENT_LISTENER==1){
                    startActivity(new Intent(MainActivity.this,SellActivitySelector.class));
                    finish();
                }
                else if (FRAGMENT_LISTENER==2){
                    Toast.makeText(this, "To be Developed!!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Snackbar.make(findViewById(R.id.Main_view), "Welcome " + user.getDisplayName(), Snackbar.LENGTH_SHORT).show();

        } else {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        }
    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                RECORD_REQUEST_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RECORD_REQUEST_CODE: {

                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {

                    Log.i(TAG, "Permission has been denied by user");
                } else {
                    Log.i(TAG, "Permission has been granted by user");
                }

            }
        }
    }
}