package com.learn.firebasesample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.learn.firebasesample.ViewHolder.PostViewHolder;
import com.learn.firebasesample.model.Post;

public class PostsActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView recyclerViewPosts;
    private LinearLayoutManager linearLayoutManager;
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Post, PostViewHolder> mAdapter;
    private Query recentPostsQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        recyclerViewPosts = (RecyclerView) findViewById(R.id.post_list);
        findViewById(R.id.fab_create_post).setOnClickListener(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        recentPostsQuery = mDatabase.child("posts")
                .limitToFirst(100);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_signout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this,AuthActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_create_post:
                startActivity(new Intent(PostsActivity.this, CreatePostActivity.class));
                break;
        }

    }

    private void loadPosts(){
        recyclerViewPosts.setLayoutManager(linearLayoutManager);

        mAdapter = new FirebaseRecyclerAdapter<Post, PostViewHolder>(Post.class, R.layout.item_post,
                PostViewHolder.class, recentPostsQuery) {
            @Override
            protected void populateViewHolder(final PostViewHolder viewHolder, final Post model, final int position) {
                viewHolder.bindToPost(model);
            }
        };
        recyclerViewPosts.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPosts();

    }
}
