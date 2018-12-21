package myfab.wildcardenter.com.first_app.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import myfab.wildcardenter.com.first_app.R;

public class SignUp extends AppCompatActivity {
    TextView textView;
    AutoCompleteTextView mEmail;
    EditText mPass;
    EditText mConfPass;
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "SignUp";
    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        textView = findViewById(R.id.Already_acc);
        tv = findViewById(R.id.Drawer_profile_name);
        mEmail = findViewById(R.id.Signup_Email);
        mPass = findViewById(R.id.Signup_Pass);
        mConfPass = findViewById(R.id.Signup_conf_pass);
        String s = "Already have an Account? Sign In";

        SpannableString spannableString = new SpannableString(s);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
                finish();

            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setFakeBoldText(true);
                ds.setUnderlineText(false);
                ds.setColor(Color.WHITE);
            }
        };

        spannableString.setSpan(clickableSpan, 25, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());


        mConfPass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int Id, KeyEvent event) {
                if (Id == EditorInfo.IME_NULL|| Id==R.integer.register_form_finished) {
                    attemptRegistration();
                    return true;
                }

                return false;
            }

        });
        firebaseAuth = FirebaseAuth.getInstance();

    }

    public void signUp(View view) {
        attemptRegistration();

    }

    private void attemptRegistration() {
        mPass.setError(null);
        mPass.setError(null);

        String email = mEmail.getText().toString();
        String password = mPass.getText().toString();
        boolean cancell = false;
        View fView = null;

        if (TextUtils.isEmpty(password) || !isPassValid(password)) {
            mPass.setError(getString(R.string.error_invalid_password));
            fView = mPass;
            cancell = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmail.setError(getString(R.string.error_field_required));
            fView = mEmail;
            cancell = true;
        } else if (!isEmailValid(email)) {
            mEmail.setError(getString(R.string.error_invalid_email));
            fView = mEmail;
            cancell = true;
        }

        if (cancell) {

            fView.requestFocus();
        } else {
            createUser();

        }
    }


    private boolean isEmailValid(String email) {

        return email.contains("@");
    }

    private boolean isPassValid(String password) {
        String confPass = mConfPass.getText().toString();
        return confPass.equals(password) && password.length() > 6;
    }

    private void createUser() {

        String email = mEmail.getText().toString();
        String password = mPass.getText().toString();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "Oncreate completed" + task.isSuccessful());


                if (!task.isSuccessful()) {
                    showErrorDialog("Error!!!");
                } else {

                    Intent intent = new Intent(SignUp.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            }


        });
    }


    public  void showErrorDialog(String msg) {
        new AlertDialog.Builder(this).setTitle("Error!").setIcon(R.drawable.ic_account_box_black_24dp).setMessage(msg)
                .setPositiveButton(android.R.string.ok, null).show();

    }
}