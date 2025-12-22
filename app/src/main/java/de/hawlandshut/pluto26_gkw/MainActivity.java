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
import androidx.annotation.Nullable;
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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import de.hawlandshut.pluto26_gkw.model.Post;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "xxx MainActivity";
    private CustomAdapter mAdapter;
    private RecyclerView mRecyclerView;

    FirebaseAuth mAuth;
    FirebaseFirestore mDb;
    ListenerRegistration mListenerRegistration;

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
        mDb = FirebaseFirestore.getInstance();

        mAdapter = new CustomAdapter();

        // Initialize mPostList
        mAdapter.mPostList = new ArrayList<Post>();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        // Init Listener on POST collection
        mListenerRegistration = createMyEventListener();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "called onStart");
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            mAdapter.mPostList.clear();
            if (mListenerRegistration != null){
                mListenerRegistration.remove();
                mListenerRegistration = null;
            }
            Intent intent = new Intent(getApplication(), SignInActivity.class);
            startActivity(intent);
        }
        else {
            if (mListenerRegistration == null){
                mListenerRegistration = createMyEventListener();
            }
        }
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

        if (item.getItemId() == R.id.menu_post) {
            Intent intent = new Intent(getApplication(), PostActivity.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.menu_manage_account) {
            Intent intent = new Intent(getApplication(), ManageAccountActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    ListenerRegistration createMyEventListener () {
        // Step 1: Define the query to firebase
        Query query = FirebaseFirestore.getInstance().collection("posts")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .limit(5);
        // Step 2: Define, how you process an update from the listener
        EventListener<QuerySnapshot> listener = new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot,
                                @Nullable FirebaseFirestoreException error) {
                Log.d(TAG, "Data received. Count = " + snapshot.size());
                mAdapter.mPostList.clear();
                for (QueryDocumentSnapshot doc : snapshot) {
                    if (doc.get("uid") != null) {
                        Log.d(TAG, "Post " + doc.getId() + " - " + doc.get("body"));

                        // Verarbeiten des Posts
                        Post addedPost = Post.fromDocument(doc);
                        // Post in die anzuzeigende Liste aufnehmen
                        mAdapter.mPostList.add(addedPost);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        };
        // Step 3 : return the query with the listener added.
        return query.addSnapshotListener(listener);
    }
}