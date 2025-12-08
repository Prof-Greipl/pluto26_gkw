package de.hawlandshut.pluto26_gkw;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hawlandshut.pluto26_gkw.test.Test;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "xxx MainActivity";
    private CustomAdapter mAdapter;
    private RecyclerView mRecyclerView;

    // TODO Only for testing - remove later
    private static final String TEST_MAIL = "fhgreipl@gmail.com";
    private static final String TEST_PASSWORD = "123456";

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Comment
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();

        mAdapter = new CustomAdapter();
        // Testdaten setzen
        mAdapter.mPostList = Test.createPostList(3);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        // TODO: Am Ende löschen
        Log.d(TAG, "onCreate called");
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart called");
        //Intent intent = new Intent(getApplication(), PostActivity.class);
        //startActivity(intent);
        super.onStart();
    }

    // Erzeugen des Menues oben rechts mit den drei Punkten.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_test_auth) {
            UserTestAuthStatus();
        }

        if (item.getItemId() == R.id.menu_create_user) {
            AuthCreateUser(TEST_MAIL, TEST_PASSWORD);
        }

        if (item.getItemId() == R.id.menu_delete) {
            UserDelete();
        }

        if (item.getItemId() == R.id.menu_password_reset_email) {
            AuthSendPasswordResetEmail(TEST_MAIL);
        }

        if (item.getItemId() == R.id.menu_verification_mail) {
            UserSendMailVerification();
        }

        if (item.getItemId() == R.id.menu_sign_in) {
            AuthSignInWithEmailAndPassword(TEST_MAIL, TEST_PASSWORD);
        }

        if (item.getItemId() == R.id.menu_sign_out) {
            AuthSignOut();
        }

        return super.onOptionsItemSelected(item);
    }

    private void AuthSignOut() {
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
    }

    private void AuthSignInWithEmailAndPassword(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(),
                                            "User signed in : " + user.getEmail(),
                                            Toast.LENGTH_LONG)
                                    .show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(),
                                            "User signIn failed." + task.getException().getMessage(),
                                            Toast.LENGTH_LONG)
                                    .show();
                        }
                        // ...
                    }
                });
    }

    private void UserSendMailVerification() {
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

    private void AuthSendPasswordResetEmail(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                            "We sent you a link to your e-mail account.",
                                            Toast.LENGTH_LONG)
                                    .show();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                            "Could not send mail. Correct e-mail?.",
                                            Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
    }

    private void UserDelete() {

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

                        } else {
                            Toast.makeText(getApplicationContext(),
                                            "Deletion failed : " + task.getException().getMessage(),
                                            Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
    }

    private void AuthCreateUser(String testMail, String testPassword) {
        mAuth.createUserWithEmailAndPassword(TEST_MAIL, TEST_PASSWORD).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User successfully created.");
                            Toast.makeText(getApplicationContext(),
                                    "User created",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Log.d(TAG, "Error : " + task.getException().getMessage());
                            Toast.makeText(getApplicationContext(),
                                    "Error : " + task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    private void UserTestAuthStatus() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Toast.makeText(getApplicationContext(),
                    "Not authenticated",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Authenticated as" + user.getEmail() + " (" + user.isEmailVerified() + ")",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume called");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause called");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop called");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy called");
        super.onDestroy();
    }
}