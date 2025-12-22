package de.hawlandshut.pluto26_gkw;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ManageAccountActivity extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth mAuth;
    // 3.a Declare UI-Variables
    TextView mTextViewEmail;
    TextView mTextViewVerificationState;
    TextView mTextViewTechnicalId;
    Button mButtonSignOut;
    Button mButtonSendVerificationEmail;
    EditText mEditTextPassword;
    Button mButtonDeleteAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Add a back arrow
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // 3.b Initialize UI Variables
        mTextViewEmail = findViewById( R.id.manageAccountEmail);
        mTextViewVerificationState = findViewById( R.id.manageAccountVerificationState);
        mTextViewTechnicalId = findViewById( R.id.manageAccountTechnicalId );
        mButtonSignOut = findViewById( R.id.manageAccountSignOut);
        mButtonSendVerificationEmail = findViewById(R.id.manageAccountSendVerificationMail);
        mEditTextPassword = findViewById( R.id.manageAccountPassword);
        mButtonDeleteAccount = findViewById( R.id.manageAccountDeleteAccount);

        // 3.c Listener für Buttons setzen
        mButtonSignOut.setOnClickListener( this );
        mButtonSendVerificationEmail.setOnClickListener( this );
        mButtonDeleteAccount.setOnClickListener( this );

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            this.finish(); // This should never happen
        }
        else {
            mTextViewEmail.setText("E-Mail : " + user.getEmail());
            mTextViewTechnicalId.setText("Technical ID : " +  user.getUid() );
            if ( user.isEmailVerified()){
                mTextViewVerificationState.setText("Ihr Konto ist verifiziert");
                mButtonSendVerificationEmail.setEnabled(false);
            }
            else {
                mTextViewVerificationState.setText("Ihr Konto ist nicht verifiziert.");
            }
        }
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
        if ( view.getId() == R.id.manageAccountSignOut){
            doSignOut();
            return;
        }

        if ( view.getId() == R.id.manageAccountSendVerificationMail){
            doSendVerificationMail();
            return;
        }

        if ( view.getId() == R.id.manageAccountDeleteAccount){
            doDeleteAccount();
            return;
        }
    }

    private void doDeleteAccount() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Toast.makeText(getApplicationContext(),
                            "No user signed in. Please sign in before deleting.",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        user.delete()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                            "Account was deleted.",
                                            Toast.LENGTH_LONG)
                                    .show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                            "Deletion failed : " + task.getException().getMessage(),
                                            Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });

    }

    private void doSendVerificationMail() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Toast.makeText(getApplicationContext(), "No user authentiated.",
                    Toast.LENGTH_SHORT).show();
            return;
        } else {
            user.sendEmailVerification()
                    .addOnCompleteListener(
                            this,
                            new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        //Erfolgsfall
                                        Toast.makeText(getApplicationContext(),
                                                        "Ver. Mail sent.",
                                                        Toast.LENGTH_LONG)
                                                .show();
                                    } else {
                                        // Fehlerfall
                                        Toast.makeText(getApplicationContext(),
                                                        "Sending Verif. Mail Failed: "
                                                                + task.getException().getMessage(),
                                                        Toast.LENGTH_LONG)
                                                .show();
                                    }
                                }
                            }
                    );
        }
    }

    private void doSignOut() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Toast.makeText(getApplicationContext(),
                    "No user signed in.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signOut();
        Toast.makeText(getApplicationContext(),
                "User " + user.getEmail() + " signed out.",
                Toast.LENGTH_SHORT).show();
        finish();
    }
}