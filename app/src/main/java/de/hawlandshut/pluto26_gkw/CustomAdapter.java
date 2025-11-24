package de.hawlandshut.pluto26_gkw;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hawlandshut.pluto26_gkw.model.Post;

public class CustomAdapter extends RecyclerView.Adapter {

    ArrayList<Post> mPostList = new ArrayList<>();
    int c = 1;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("xxx", ">>>>> Created Viewholder  " + c++);
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_view, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Post post = mPostList.get(position);
        Log.d("xxx", "called with position = " + position);
        ((CustomViewHolder) holder).mLine1.setText(post.title + " (" + post.createdAt +")");
        ((CustomViewHolder) holder).mLine2.setText(post.body);
    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }
}
