package com.example.idfrange;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import java.util.ArrayList;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class scoreActivity extends AppCompatActivity {
    ///// import variables and complete db data ranges
//    TextView title,scrollNamesView;
//    Button saveName,saveRange,saveScore,showScores;
    //EditText editName,editRange,editScore;
//    String[][] matrix;
//    String newRange, newName, newScore;//    int i;


    Button saveScoreButton;
    EditText editScore;
    Spinner spinner;
    String rangeId,clientId,chosenDrill="not changed yet";
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

        final ArrayList<String> rangesList=new ArrayList<String>();
        rangesList.add("");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,  R.layout.support_simple_spinner_dropdown_item, rangesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        rangeIdDb.child(rangeId).child("drillList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    try {
                        String value = ds.getValue(String.class);
                        rangesList.add(value);
                    }
                    catch(Exception e){}
                }
                Toast.makeText(scoreActivity.this, "drills added", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(scoreActivity.this, "Add new drill failed", Toast.LENGTH_SHORT).show();
            }
        });



        saveScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chosenDrill = spinner.getSelectedItem().toString();
                if (!chosenDrill.equals("") && !editScore.getText().toString().equals("000")) {
                    //"if' is used because of bug when first node of array is not added

                    rangeIdDb.child(rangeId).child(chosenDrill).child(clientId).setValue(editScore.getText().toString());
                    editScore.setText("000");
                    Toast.makeText(scoreActivity.this, "Score saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(scoreActivity.this, "choose a drill and enter a score", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }}