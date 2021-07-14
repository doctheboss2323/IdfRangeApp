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

public class previousScoreActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_scores);

        //initial work
        Intent intent= getIntent();
        rangeId=intent.getStringExtra("rangeId");
        clientId=intent.getStringExtra("clientName");
        spinner=findViewById(R.id.ranges_spinner);
        editScore=findViewById(R.id.score_edittext);
        saveScoreButton=findViewById(R.id.savescore_button);

        final ArrayList<String> rangesList=new ArrayList<String>();

        rangeIdDb.child(rangeId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    String value = ds.getValue(String.class);
                    rangesList.add(value);
                }
                Toast.makeText(previousScoreActivity.this, "drills added", Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(previousScoreActivity.this, "Add new drill failed", Toast.LENGTH_SHORT).show();
            }
        });

        ArrayAdapter adapter= new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,rangesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);





//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,  R.layout.support_simple_spinner_dropdown_item, rangesList);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//        spinner.setAdapter(adapter);

        saveScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chosenDrill=spinner.getSelectedItem().toString();
                saveScoreButton.setText(chosenDrill);
                //rangeIdDb.child(rangeId).child(chosenDrill).child(clientId).setValue(editScore.getText().toString());
                Toast.makeText(previousScoreActivity.this, "Score saved", Toast.LENGTH_SHORT).show();
            }

        });



    }}