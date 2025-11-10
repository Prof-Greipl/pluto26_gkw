package de.hawlandshut.pluto26_gkw;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    // 3.a Declare UI Variables
    EditText mEditTextEmail;
    EditText mEditTextPassword;
    Button mButtonSignIn;
    Button mButtonResetPassword;
    Button mButtonCreateAccount;

    private final String TESTMAIL = "fhgreipl@gmail.com";
    private final String TESTPASSWORD = "123456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //3.b Initialize UI-Variables
        mEditTextEmail = findViewById( R.id.signInEditTextEMail);
        mEditTextPassword = findViewById( R.id.signInEditTextPassword);
        mButtonSignIn = findViewById( R.id.signInButtonSignIn);
        mButtonResetPassword = findViewById( R.id.signInButtonResetPassword);
        mButtonCreateAccount = findViewById( R.id.signInButtonCreateAccount);

        // 3.c Assign Listeners
        mButtonSignIn.setOnClickListener( this );
        mButtonResetPassword.setOnClickListener( this );
        mButtonCreateAccount.setOnClickListener( this );

        // TODO: Only for testing - remove later.
        mEditTextEmail.setText( TESTMAIL );
        mEditTextPassword.setText( TESTPASSWORD );

    }

    // 3.c Implement Business Logic
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.signInButtonSignIn){
            doSignIn();
            return;
        }

        if (view.getId() == R.id.signInButtonResetPassword){
            doResetPassword();
            return;
        }

        if (view.getId() == R.id.signInButtonCreateAccount){
            doGotoCreateAccount();
            return;
        }
    }

    private void doGotoCreateAccount() {
        Toast.makeText(getApplicationContext(), "Pressed Create Account", Toast.LENGTH_LONG).show();
    }

    private void doResetPassword() {
        Toast.makeText(getApplicationContext(), "Pressed Reset Password", Toast.LENGTH_LONG).show();
    }

    private void doSignIn() {
        Toast.makeText(getApplicationContext(), "Pressed Sign In", Toast.LENGTH_LONG).show();
    }
}