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
    int scoreCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score3);

        ///// change databse to match user class

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
        scoreCount=refChild(rangeIdDb.child(rangeId).child("Name list").child(clientId));

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
                String score=editScore.getText().toString();
                if (!chosenDrill.equals("") && !score.equals("")) {
                    //"if' is used because of bug when first node of array is not added
                    if(Integer.parseInt(score)<101){

                    rangeIdDb.child(rangeId).child(chosenDrill).child(clientId).setValue(score);
                    rangeIdDb.child(rangeId).child("Name list").child(clientId).child(chosenDrill).setValue(score);

                    globalScore(rangeIdDb.child(rangeId).child("Global list").child(clientId),scoreCount,clientId,score,chosenDrill);
                    scoreCount++;

                    editScore.setText("");
                    Toast.makeText(scoreActivity.this, "Score saved", Toast.LENGTH_SHORT).show();}
                    Toast.makeText(scoreActivity.this, "enter score between 0 and 100", Toast.LENGTH_SHORT).show();
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
    }
    public int refChild(DatabaseReference db){
        final int[] count = {0};
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                if(ds.hasChildren()){
                    count[0] = (int) ds.getChildrenCount();}
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
        return count[0];
    }
    public void globalScore(DatabaseReference db,int count,String clientId,String score,String drill){
        if(count==0){
            db.child("name").setValue(clientId);
            db.child("drill1").setValue(drill);
            db.child("score1").setValue(score);
        }
        if(count==1){
            db.child("drill2").setValue(drill);
            db.child("score2").setValue(score);
        }
        if(count==2){
            db.child("drill3").setValue(drill);
            db.child("score3").setValue(score);
        }
        if(count==3){
            db.child("drill4").setValue(drill);
            db.child("score4").setValue(score);
        }
        if(count==4){
            db.child("drill5").setValue(drill);
            db.child("score5").setValue(score);
        }
        if(count==5){
            db.child("drill6").setValue(drill);
            db.child("score6").setValue(score);
        }
        if(count==6){
            db.child("drill7").setValue(drill);
            db.child("score7").setValue(score);
        }
        if(count==7){
            db.child("drill8").setValue(drill);
            db.child("score8").setValue(score);
        }
        if(count==8){
            db.child("drill9").setValue(drill);
            db.child("score9").setValue(score);
        }
        if(count==9){
            db.child("drill10").setValue(drill);
            db.child("score10").setValue(score);
        }
    }
}