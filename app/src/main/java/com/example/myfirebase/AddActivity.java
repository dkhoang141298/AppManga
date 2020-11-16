package com.example.myfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    private Button mNextBtn;
    private EditText meditCategory;
    private EditText meditTitle;
    private EditText meditContent;

    private FirebaseFirestore mFireStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        mFireStore = FirebaseFirestore.getInstance();

        meditTitle = (EditText) findViewById(R.id.ed_title);
        meditCategory = (EditText) findViewById(R.id.ed_category);
        meditContent = (EditText) findViewById(R.id.editContent);
        mNextBtn = (Button) findViewById(R.id.btnNext);

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = meditTitle.getText().toString();
                String category = meditCategory.getText().toString();
                String content = meditContent.getText().toString();

                Map<String, String> addMap = new HashMap<>();
                addMap.put("title", title);
                addMap.put("category", category);
                addMap.put("content", content);

                mFireStore.collection("title").add(addMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AddActivity.this,"Book Added to Firestore", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String error =e.getMessage();
                        Toast.makeText(AddActivity.this, "Error :" +error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}