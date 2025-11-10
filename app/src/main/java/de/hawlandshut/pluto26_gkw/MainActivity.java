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

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "xxx MainActivity";

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
        // TODO: Am Ende löschen
        Log.d(TAG, "onCreate called");
        Toast.makeText(getApplicationContext(), "App started", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart called");
        Intent intent = new Intent(getApplication(), ManageAccountActivity.class);
        startActivity(intent);
        super.onStart();
    }

    // Erzeugen des Menues oben rechts mit den drei Punkten.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_main_manage_account){
            Toast.makeText(getApplicationContext(), "Pressed Manage Account", Toast.LENGTH_LONG).show();
        }

        if (item.getItemId() == R.id.menu_main_test){
            Toast.makeText(getApplicationContext(), "Pressed Test", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
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