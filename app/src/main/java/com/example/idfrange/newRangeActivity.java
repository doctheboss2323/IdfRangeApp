package com.example.idfrange;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class newRangeActivity extends AppCompatActivity {

    EditText newrangeEditText;
    Button continueButton;
    FirebaseDatabase firstDatabase= FirebaseDatabase.getInstance("https://idfrange-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference rangeIdDb=firstDatabase.getReference();
    String creatorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_range);

        newrangeEditText=findViewById(R.id.newrange_edittext);
        continueButton=findViewById(R.id.registerrange_button);

        Intent intent=getIntent();
        creatorName=intent.getStringExtra("clientName");


        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rnge=newrangeEditText.getText().toString();
                if(TextUtils.isEmpty(rnge)){
                    Toast.makeText(getApplicationContext(), "Enter range id",
                            Toast.LENGTH_LONG).show();}
                else{
                    rangeIdDb.child(rnge).child("Drill list").setValue("");
                    rangeIdDb.child(rnge).child("Name list").setValue("");
                    rangeIdDb.child(rnge).child("Global list").setValue("");
                    rangeIdDb.child("Range list").child(rnge).setValue(rnge);

                    Intent profileIntent=new Intent(newRangeActivity.this, rangesActivity.class); // TO CHANGE
                    profileIntent.putExtra("rangeId",rnge);
                    profileIntent.putExtra("clientName",creatorName);
                    startActivity(profileIntent);

                }
            }
        });
    }
}