package com.example.idfrange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.ContentValues.TAG;

public class loginActivity extends AppCompatActivity {
    EditText emailEditText, passwordEditText,nameEditText,rangeEditText;
    Button joinRangeButton,newRangeButton;
    FirebaseAuth mAuth;
    String email,password,id,range;
    FirebaseDatabase firstDatabase= FirebaseDatabase.getInstance("https://idfrange-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference nameDb=firstDatabase.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initial work
        emailEditText=findViewById(R.id.email_edittext);
        passwordEditText=findViewById(R.id.enterpass_edittext);
        nameEditText=findViewById(R.id.fullname_edittext);
        rangeEditText=findViewById(R.id.range_edittext);
        joinRangeButton=findViewById(R.id.register_button);
        newRangeButton=findViewById(R.id.newrange_button);

        mAuth = FirebaseAuth.getInstance();
        //FirebaseAuth.getInstance().signOut();  ///////// TO KEEP


        joinRangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email=emailEditText.getText().toString();
                password=passwordEditText.getText().toString();
                id=nameEditText.getText().toString();
                range=rangeEditText.getText().toString();
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Name and range Id",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    Intent profileIntent=new Intent(loginActivity.this, scoreActivity.class);
                    profileIntent.putExtra("clientName", nameEditText.getText().toString());
                    profileIntent.putExtra("rangeId", rangeEditText.getText().toString());}
            }
//                mAuth.signInWithEmailAndPassword(email,password)
//                        .addOnCompleteListener(loginActivity.this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    // Sign in success, update UI with the signed-in user's information
//                                    Log.d(TAG, "signInWithEmail:success");
//                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    updateUI(user);
//                                } else {
//                                    // If sign in fails, display a message to the user.
//                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
//                                    Toast.makeText(loginActivity.this, "Authentication failed.",
//                                            Toast.LENGTH_SHORT).show();
//                                 }
//                        }
//                    });

        });


        newRangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=emailEditText.getText().toString();
                String password=passwordEditText.getText().toString();
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)  || TextUtils.isEmpty(id) || TextUtils.isEmpty(range) ) {
                    Toast.makeText(getApplicationContext(), "Enter email password name and range to join",
                            Toast.LENGTH_LONG).show();

                    return;
                }
                mAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(loginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    Toast.makeText(loginActivity.this, "Authentication sucessfull.",
                                            Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUInew(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(loginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();           ///////// TO PUT BACK
         //Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser()
//        if(currentUser!=null){
//            updateUI(currentUser);}
    }

//    public void updateUI(FirebaseUser currentUser) {
//        Intent profileIntent=new Intent(this, scoreActivity.class);
//        profileIntent.putExtra("clientName", nameEditText.getText().toString());
//        profileIntent.putExtra("rangeId", rangeEditText.getText().toString());
//        startActivity(profileIntent);
//    }

    public void updateUInew(FirebaseUser currentUser) {
        Intent profileIntent=new Intent(this,newRangeActivity.class);
        profileIntent.putExtra("clientName", nameEditText.getText().toString());
        startActivity(profileIntent);
    }
}