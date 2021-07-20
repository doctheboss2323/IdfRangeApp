package com.example.idfrange;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import java.util.ArrayList;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

public class scoreActivity extends AppCompatActivity {

    Button saveScoreButton,allScoresButton;
    TextView myScores;
    EditText editScore;
    Spinner spinner;
    String rangeId,clientId,chosenDrill="not changed yet",score="";
    FirebaseDatabase firstDatabase= FirebaseDatabase.getInstance("https://idfrange-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference rangeIdDb=firstDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score3);

        //initial work
        Intent intent= getIntent();
        rangeId=intent.getStringExtra("rangeId");
        clientId=intent.getStringExtra("clientName");
        spinner=findViewById(R.id.ranges_spinner);
        editScore=findViewById(R.id.score_edittext);
        saveScoreButton=findViewById(R.id.savescore_button);
        myScores=findViewById(R.id.myscorestextview);
        allScoresButton=findViewById(R.id.allscoresbutton);
        final ArrayList<String> rangesList=new ArrayList<String>();
        rangesList.add("");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,  R.layout.support_simple_spinner_dropdown_item, rangesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        rangeIdDb.child(rangeId).child("Drill list").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    try {
                        String value = ds.getValue(String.class);
                        rangesList.add(value);
                    }
                    catch(Exception e){}
                }
//                Toast.makeText(scoreActivity.this, "drills added", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(scoreActivity.this, "Add new drill failed", Toast.LENGTH_SHORT).show();
            }
        });

        rangeIdDb.child(rangeId).child("Name list").child(clientId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myScores.setText("My scores: "+"\n");
                myScores.append("\n");
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    try{
                        myScores.append(score);
                        String drill = ds.getKey();
                        String score = ds.getValue(String.class);
                        myScores.append(drill+": "+score+"\n");
                    }
                    catch(Exception e){}
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



        saveScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chosenDrill = spinner.getSelectedItem().toString();
                if (!chosenDrill.equals("") && !editScore.getText().toString().equals("")) {
                    //"if' is used because of bug when first node of array is not added
                    rangeIdDb.child(rangeId).child(chosenDrill).child(clientId).setValue(editScore.getText().toString());
                    rangeIdDb.child(rangeId).child("Name list").child(clientId).child(chosenDrill).setValue(editScore.getText().toString());
                    editScore.setText("");
                    Toast.makeText(scoreActivity.this, "Score saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(scoreActivity.this, "choose a drill and enter a score", Toast.LENGTH_SHORT).show();
                }
            }
        });
        allScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(scoreActivity.this,tableActivity.class);
                intent.putExtra("clientId",clientId);
                intent.putExtra("rangeId",rangeId);
                startActivity(intent);
            }
        });
    }}