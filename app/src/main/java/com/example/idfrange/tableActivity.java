package com.example.idfrange;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class tableActivity extends AppCompatActivity {
//    String[][] matrix;
//    TextView matrixView;
//    Button returnButton;
    TableLayout table;
    LinearLayout linLayout;
    int rowNumber,columnNumber;
    String rangeId,clientId;
    FirebaseDatabase firstDatabase= FirebaseDatabase.getInstance("https://idfrange-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference nameDb=firstDatabase.getReference();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        //Initial work
        Intent intent=getIntent();
        rangeId=intent.getStringExtra("rangeId");
        clientId=intent.getStringExtra("clientId");

        LinearLayout mainLayout = findViewById(R.id.mainLayout);
        TableLayout table=new TableLayout(this);

        //rowNumber=idCounter(nameDb.child(rangeId).child("Name list"));
        rowNumber=3;
        //columnNumber=idCounter(nameDb.child(rangeId).child("Drill list"));
        columnNumber=8;

        for (int i=0; i < rowNumber; i++) {
            TableRow row = new TableRow(tableActivity.this);
            for (int j=0; j < columnNumber; j++) {
//                int value = random.nextInt(100) + 1;
                TextView tv = new TextView(tableActivity.this);
//                tv.setText(String.valueOf(value));
                row.addView(tv);
            }
            table.addView(row);
        } ////////// add actual names and scores and ranges

        mainLayout.addView(table);



//        matrixView=findViewById(R.id.matrixView);
//        matrixView.setMovementMethod(new ScrollingMovementMethod());
//        returnButton=findViewById(R.id.returnButton);
//
//        Bundle extras = getIntent().getExtras();           // getting previous view arrays.
//        matrix = (String[][]) extras.getSerializable("matrix");
//
//        for(int i=0;i<matrix.length-1;i++){
//            for(int j=0;j<matrix[i].length-1;j++){
//                if(matrix[i][j]!=null){
//                    matrixView.append("| "+matrix[i][j]+" |");}
//                else{matrixView.append("|       |");}
//            }
//            matrixView.append("\n"+"------------------------------------------------------------------"+"\n");
//        }
//
//        returnButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            finish();
//
////                Intent mIntent =new Intent(tableActivity.this,scoresActivity.class);
////                Bundle mBundle = new Bundle();
////                mBundle.putSerializable("matrix",matrix);
////                mIntent.putExtras(mBundle);
////                startActivity(mIntent);
//                    }
//                });
 }
    private int idCounter(DatabaseReference ref) {
        final int[] counter = new int[0];
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                counter[0] = (int) snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });
        int count=counter[0];
        return count;
    }
}
