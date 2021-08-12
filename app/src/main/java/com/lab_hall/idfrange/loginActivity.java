package com.lab_hall.idfrange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

public class loginActivity extends AppCompatActivity {
    EditText emailEditText, passwordEditText,nameEditText,rangeEditText;
    Button joinRangeButton,newRangeButton,firstTimeButton,firstTimeButton2,revealButton,revealButton2;
    ImageView img;
    FirebaseAuth mAuth;
    String email,password,id,range;
    FirebaseDatabase firstDatabase= FirebaseDatabase.getInstance("https://idfrange-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference nameDb=firstDatabase.getReference();

   ////////// ADD function to check if join range exist
    //// add function to keep personal data
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view=getLayoutInflater().inflate(R.layout.activity_login,null);
        setContentView(view);

        //Initial work
        emailEditText = findViewById(R.id.email_edittext);
        passwordEditText = findViewById(R.id.enterpass_edittext);
        nameEditText = findViewById(R.id.fullname_edittext);
        rangeEditText = findViewById(R.id.range_edittext);
        joinRangeButton = findViewById(R.id.register_button);
        newRangeButton = findViewById(R.id.newrange_button);
        firstTimeButton = findViewById(R.id.firstTime_button);
        firstTimeButton2 = findViewById(R.id.firstTime2_button);
        img=findViewById(R.id.imageView);
        revealButton=findViewById(R.id.revealnewrangebutton);
        revealButton2=findViewById(R.id.revealjoinrangebutton);

        firstTimeButton2.setVisibility (View.INVISIBLE);
        emailEditText.setVisibility(View.INVISIBLE);
        passwordEditText.setVisibility(View.INVISIBLE);
        newRangeButton.setVisibility(View.INVISIBLE);
        revealButton2.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();
        range="";
        id= getPreferences(MODE_PRIVATE).getString("id","");
        nameEditText.setText(id);
        range= getPreferences(MODE_PRIVATE).getString("range","");
        rangeEditText.setText(range);

        //FirebaseAuth.getInstance().signOut();  ///////// TO KEEP


        firstTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstTimeButton.setVisibility (View.INVISIBLE);
                firstTimeButton2.setVisibility (View.VISIBLE);
            }
        });



        revealButton.setOnClickListener(new View.OnClickListener() {  //turn acitivity into making new range
            @Override
            public void onClick(View view) {
                joinRangeButton.setVisibility(View.INVISIBLE);
                revealButton.setVisibility (View.INVISIBLE);
                rangeEditText.setVisibility(View.INVISIBLE);
                revealButton2.setVisibility (View.VISIBLE);
                emailEditText.setVisibility (View.VISIBLE);
                passwordEditText.setVisibility (View.VISIBLE);
                newRangeButton.setVisibility (View.VISIBLE);

            }
        });
        revealButton2.setOnClickListener(new View.OnClickListener() { // turn it back to join range
            @Override
            public void onClick(View view) {
                revealButton2.setVisibility (View.INVISIBLE);
                revealButton.setVisibility (View.VISIBLE);
                emailEditText.setVisibility (View.INVISIBLE);
                passwordEditText.setVisibility (View.INVISIBLE);
                newRangeButton.setVisibility (View.INVISIBLE);
                rangeEditText.setVisibility(View.VISIBLE);
                joinRangeButton.setVisibility(View.VISIBLE);

            }
        });


        joinRangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                email=emailEditText.getText().toString();
//                password=passwordEditText.getText().toString();

                id = nameEditText.getText().toString();
                getPreferences(MODE_PRIVATE).edit().putString("id",id).commit();
                range = rangeEditText.getText().toString();
                getPreferences(MODE_PRIVATE).edit().putString("range",range).commit();


                if (TextUtils.isEmpty(id) || TextUtils.isEmpty(range)) { //check if name and range are typed in
                    Toast.makeText(getApplicationContext(), "הכנס שם מלא ושם המטווח",
                            Toast.LENGTH_LONG).show();}

                 else {  // check if chosen range actually exist
                    nameDb.child("Range list").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(range))
                            {
                                Intent profileIntent = new Intent(loginActivity.this, scoreActivity.class);
                                profileIntent.putExtra("clientName", nameEditText.getText().toString());
                                profileIntent.putExtra("rangeId", rangeEditText.getText().toString());
                                startActivity(profileIntent);
                            }
                            else{Toast.makeText(loginActivity.this, "המטווח לא קיים", Toast.LENGTH_SHORT).show();
                      }
                        }
                        @Override
                        public void onCancelled(DatabaseError error) {
                        }
                    });
                }
            }

        });


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rotateImg();
            }
        });


        newRangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String id = nameEditText.getText().toString();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(id)) {
                    Toast.makeText(getApplicationContext(), "הכנס כתובת דוא״ל, שם משתמש וסיסמה כדי ליצור מטווח חדש",
                            Toast.LENGTH_LONG).show();

                    return;
                }
                rotateImg();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(loginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    Toast.makeText(loginActivity.this, "זיהוי בוצע בהצלחה",
                                            Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUInew(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(loginActivity.this, "זיהוי נכשל",
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

    public void updateUInew(FirebaseUser currentUser) {
        Intent profileIntent=new Intent(this,newRangeActivity.class);
        profileIntent.putExtra("clientName", nameEditText.getText().toString());
        startActivity(profileIntent);
    }

    public void rotateImg(){
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(500);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        findViewById(R.id.imageView).startAnimation(rotateAnimation);
    }
}