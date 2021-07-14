package com.example.idfrange;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

public class scoreActivity3 extends AppCompatActivity {
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


//        final List<String> rangesList = Arrays.asList("AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY");

        rangeIdDb.child(rangeId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    String value = ds.getValue(String.class);
                    rangesList.add(value);
                }
                Toast.makeText(scoreActivity3.this, "drills added", Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(scoreActivity3.this, "Add new drill failed", Toast.LENGTH_SHORT).show();
            }
        });






        saveScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chosenDrill=spinner.getSelectedItem().toString();
                saveScoreButton.setText(chosenDrill);
                rangeIdDb.child(rangeId).child(chosenDrill).child(clientId).setValue(editScore.getText().toString());
                Toast.makeText(scoreActivity3.this, "Score saved", Toast.LENGTH_SHORT).show();
            }

        });



    }}