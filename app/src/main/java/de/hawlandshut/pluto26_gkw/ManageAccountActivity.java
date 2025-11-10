package de.hawlandshut.pluto26_gkw;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ManageAccountActivity extends AppCompatActivity implements View.OnClickListener{
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
        Toast.makeText(getApplicationContext(), "Pressed Delete Account", Toast.LENGTH_LONG).show();
    }

    private void doSendVerificationMail() {
        Toast.makeText(getApplicationContext(), "Pressed Send VeriMail", Toast.LENGTH_LONG).show();
    }

    private void doSignOut() {
        Toast.makeText(getApplicationContext(), "Pressed SignOut", Toast.LENGTH_LONG).show();
    }
}