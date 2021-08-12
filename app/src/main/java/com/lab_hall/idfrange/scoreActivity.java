package com.lab_hall.idfrange;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import java.util.ArrayList;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
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

public class scoreActivity extends AppCompatActivity {

    Button saveScoreButton,allScoresButton,descriptionButton,positionsButton;
    TextView myScores,descriptionTextView,spinnerTextView;
    EditText editScore;
    Spinner spinner;
    String rangeId,clientId,chosenDrill="עדיין לא שונה",score="";
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
        myScores.setMovementMethod(new ScrollingMovementMethod());
        allScoresButton=findViewById(R.id.allscoresbutton);
        descriptionTextView=findViewById(R.id.description_textview);
        descriptionTextView.setMovementMethod(new ScrollingMovementMethod());
        descriptionButton=findViewById(R.id.description_button);
        positionsButton=findViewById(R.id.positionsbutton);
        spinnerTextView=findViewById(R.id.spinnertextview);
        final ArrayList<String> rangesList=new ArrayList<String>();
        rangesList.add("");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,  R.layout.support_simple_spinner_dropdown_item, rangesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        rangeIdDb.child(rangeId).child("Drill list").addListenerForSingleValueEvent(new ValueEventListener() { //add drills to spinner
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    try {
                        String value = ds.getKey();
                        rangesList.add(value);
                    }
                    catch(Exception e){}
                }
//                Toast.makeText(scoreActivity.this, "drills added", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });



        rangeIdDb.child(rangeId).child("Name list").child(clientId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myScores.setText("התוצאות שלי"+"\n");
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
            }
        });


        descriptionButton.setOnClickListener(new View.OnClickListener() { //get the description
            @Override
            public void onClick(View view) {

                rangeIdDb.child(rangeId).child("Drill list").child(spinner.getSelectedItem().toString()).child("Description").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String description=dataSnapshot.getValue(String.class);
                        descriptionTextView.setText(description);

                    }

                    @Override
                    public void onCancelled(DatabaseError error) { }});
            }
        });


        saveScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chosenDrill = spinner.getSelectedItem().toString();
                String score = editScore.getText().toString();
                if (chosenDrill.equals("") || score.equals("")) {
                    Toast.makeText(scoreActivity.this, "בחר מקצה והכנס תוצאה", Toast.LENGTH_SHORT).show();
                    return;
                }
                //"if' is used because of bug when first node of array is not added
                if (Integer.parseInt(score) > 101) {
                    Toast.makeText(scoreActivity.this, "בחר תוצאה בין 0 ל100", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{

                rangeIdDb.child(rangeId).child(chosenDrill).child(clientId).setValue(score);
                rangeIdDb.child(rangeId).child("Name list").child(clientId).child(chosenDrill).setValue(score);

                refChild(rangeIdDb.child(rangeId), chosenDrill,clientId, score, chosenDrill);



                editScore.setText("");
                Toast.makeText(scoreActivity.this, "תוצאה נשמרה. כל הכבוד תותח/ית !!!", Toast.LENGTH_SHORT).show();
                descriptionTextView.setText("");
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
        positionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(scoreActivity.this,positionsActivity.class);
                startActivity(intent);
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (!spinner.getSelectedItem().toString().equals("")) {
                    spinnerTextView.setVisibility(View.INVISIBLE);
                }

            }
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


    }



    public void refChild(DatabaseReference db,String range,String clientId,String score,String chosenDrill){ //getting the right num for specific drill
        final int[] count = {0};
        db.child("Drill list").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                for(DataSnapshot data:ds.getChildren()){
                    if(data.getKey().equals(range)){
                        db.child("Drill list").child(range).child("Num").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String scount=dataSnapshot.getValue(String.class);
                                count[0]=Integer.valueOf(scount);
                                globalScore(db.child("Global list").child(clientId), count[0], clientId, score, chosenDrill,clientId);
                            }
                            @Override
                            public void onCancelled(DatabaseError error) { }});
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
//        return count[0];
    }

    public void globalScore(DatabaseReference db,int count,String clientId,String score,String drill,String client){
        db.child("name").setValue(client);
        if(count==1){
            db.child("drill1").setValue(drill);
            db.child("score1").setValue(score);
        }
        if(count==2){
            db.child("drill2").setValue(drill);
            db.child("score2").setValue(score);
        }
        if(count==3){
            db.child("drill3").setValue(drill);
            db.child("score3").setValue(score);
        }
        if(count==4){
            db.child("drill4").setValue(drill);
            db.child("score4").setValue(score);
        }
        if(count==5){
            db.child("drill5").setValue(drill);
            db.child("score5").setValue(score);
        }
        if(count==6){
            db.child("drill6").setValue(drill);
            db.child("score6").setValue(score);
        }
        if(count==7){
            db.child("drill7").setValue(drill);
            db.child("score7").setValue(score);
        }
        if(count==8){
            db.child("drill8").setValue(drill);
            db.child("score8").setValue(score);
        }
        if(count==9){
            db.child("drill9").setValue(drill);
            db.child("score9").setValue(score);
        }
        if(count==10){
            db.child("drill10").setValue(drill);
            db.child("score10").setValue(score);
        }
    }
}