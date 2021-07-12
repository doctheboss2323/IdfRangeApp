package com.example.idfrange;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class namesActivity extends AppCompatActivity {
    Button saveButton,startRangeButton;
    EditText nameInput;
    TextView tv,clientNameTextView;
    String[] names;
    String newNameString,clientName,rangeId;
    int namesCounter;

    DatabaseReference myDatabase;
//    DatabaseReference myRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Initial work
        tv=findViewById(R.id.scrollNamesView);
        tv.setMovementMethod(new ScrollingMovementMethod());
        nameInput=findViewById(R.id.nameEditText);
        saveButton= findViewById(R.id.saveNameButton);
        startRangeButton=findViewById(R.id.startRangeButton);
        clientNameTextView=findViewById(R.id.clientNameTextView);

        Intent intent = getIntent();
        clientName=intent.getStringExtra("name");
        rangeId=intent.getStringExtra("rangeId");
        clientNameTextView.setText(clientName);

        //ArrayList <String> names= new ArrayList<String>();
        names=new String[40];
        namesCounter=0;

//        myDatabase= FirebaseDatabase.getInstance().getReference();
//        myDatabase.child("names").setValue("noder");
        // Write a message to the database





        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newNameString=nameInput.getText().toString();
                tv.append(newNameString+"\n");
                nameInput.setText("");

                names[namesCounter]=newNameString;
                namesCounter++;
            }
        });

        startRangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(namesActivity.this,rangesActivity.class);
                intent.putExtra("nameCount",namesCounter);
                intent.putExtra("nameList",names);
                startActivity(intent);
            }
        });




    }
}
