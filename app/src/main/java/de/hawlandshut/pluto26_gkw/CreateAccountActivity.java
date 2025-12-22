package de.hawlandshut.pluto26_gkw;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateAccountActivity extends AppCompatActivity
implements View.OnClickListener{
    private static final String TAG = "xxx CreateAccountActivitx";

    FirebaseAuth mAuth;

    private EditText mEditTextEMail;
    private EditText mEditTextPassword1;
    private EditText mEditTextPassword2;
    private Button mButtonCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mEditTextEMail =  findViewById(R.id.createAccountEmail);
        mEditTextPassword1 =  findViewById(R.id.createAccountPassword1);
        mEditTextPassword2 =  findViewById(R.id.createAccountPassword2);
        mButtonCreateAccount = findViewById( R.id.createAccountButtonCreateAccount);

        mButtonCreateAccount.setOnClickListener( this );

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // TODO: Presets for testing - remove for prod
        mEditTextEMail.setText("xxx@gmail.com");
        mEditTextPassword1.setText("123456");
        mEditTextPassword2.setText("123456");

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int i =  item.getItemId() ;
        if (i == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.createAccountButtonCreateAccount){
            Log.d(TAG, "doCreateAccount");
            doCreateAccount();
        }
    }

    private void doCreateAccount() {
        Log.d(TAG, "doCreateAccount");
        String email = mEditTextEMail.getText().toString();
        String password1 = mEditTextPassword1.getText().toString();
        String password2 = mEditTextPassword2.getText().toString();
        Log.d(TAG, "doCreateAccount");
        // Validierunf
        if (!password1.equals(password2)){
            Toast.makeText(getApplicationContext(),
                    "Passwords do not match",
                    Toast.LENGTH_LONG).show();
            Log.d(TAG, "Passwords do not match)");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password1).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User successfully created.");
                            Toast.makeText(getApplicationContext(),
                                    "User created",
                                    Toast.LENGTH_LONG).show();
                            Log.d(TAG, "Account created");
                            finish();
                        } else {
                            Log.d(TAG, "Error : " + task.getException().getMessage());
                            Toast.makeText(getApplicationContext(),
                                    "Error : " + task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                            Log.d(TAG, "Error" + task.getException().getMessage());
                        }
                    }
                }
        );
    }
}