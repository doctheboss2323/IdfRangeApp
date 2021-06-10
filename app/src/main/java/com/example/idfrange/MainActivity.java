package com.example.idfrange;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    Button saveButton,startRangeButton;
    EditText nameInput;
    TextView tv;
    String[] names;
    String newNameString;
    int namesCounter;

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

        //ArrayList <String> names= new ArrayList<String>();
        names=new String[40];

        namesCounter=0;


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
                Intent intent =new Intent(MainActivity.this,rangesActivity.class);
                intent.putExtra("nameCount",namesCounter);
                intent.putExtra("nameList",names);
                startActivity(intent);
            }
        });



//        startRangeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tv.setText("\n");
//                for(int i=0;i<names.size();i++){
//                    tv.append(names.get(i)+"\n");
//                }
//            }
//        });

    }
}
