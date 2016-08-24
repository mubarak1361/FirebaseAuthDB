package com.learn.firebasesample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.learn.firebasesample.model.Post;
import com.learn.firebasesample.model.User;

import java.util.HashMap;
import java.util.Map;

public class CreatePostActivity extends AppCompatActivity implements View.OnClickListener{

    private AppCompatEditText editTextTitle,editTextBody;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        editTextTitle = (AppCompatEditText) findViewById(R.id.edit_txt_title);
        editTextBody = (AppCompatEditText) findViewById(R.id.edit_txt_body);
        findViewById(R.id.btn_publish).setOnClickListener(this);
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(editTextTitle.getText().toString())) {
            editTextTitle.setError("Required");
            result = false;
        } else {
            editTextTitle.setError(null);
        }

        if (TextUtils.isEmpty(editTextBody.getText().toString())) {
            editTextBody.setError("Required");
            result = false;
        } else {
            editTextBody.setError(null);
        }

        return result;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_publish:
                publishPost();
                break;
        }
    }

    private void publishPost(){
        if(!validateForm())
            return;
        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);

                        // [START_EXCLUDE]
                        if (user == null) {
                            // User is null, error out
                            Toast.makeText(CreatePostActivity.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new post
                            writeNewPost(userId, user.username,
                                    editTextTitle.getText().toString(), editTextBody.getText().toString());
                        }
                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    private void writeNewPost(String userId, String username, String title, String body) {

        String key = mDatabase.child("posts").push().getKey();
        Post post = new Post(userId, username, title, body);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }
}
