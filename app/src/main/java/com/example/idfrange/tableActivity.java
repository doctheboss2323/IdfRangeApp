package com.example.idfrange;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
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

import java.util.ArrayList;

public class tableActivity extends AppCompatActivity {
//    String[][] matrix;
//    TextView matrixView;
//    Button returnButton;
    String rangeId,clientId;
    RecyclerView scoresTable;
    TextView drillTextView1,drillTextView2,drillTextView3;
    MyAdapter myAdapter;
    ArrayList<User> list;
    FirebaseDatabase firstDatabase= FirebaseDatabase.getInstance("https://idfrange-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference nameDb=firstDatabase.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        //Initial work
        Intent intent=getIntent();
        rangeId=intent.getStringExtra("rangeId");
        scoresTable=findViewById(R.id.scorestable);
        scoresTable.setHasFixedSize(true);
        scoresTable.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        myAdapter=new MyAdapter(this,list);
        scoresTable.setAdapter(myAdapter);
        drillTextView1=findViewById(R.id.drill1textview);
        drillTextView2=findViewById(R.id.drill2textview);
        drillTextView3=findViewById(R.id.drill3textview);


        //make the drills names
        nameDb.child(rangeId).child("Drill list").addListenerForSingleValueEvent(new ValueEventListener() {
            int count=0;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    setDrillValue(count,ds.getValue(String.class));
                    count++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        nameDb.child(rangeId).child("Global list").addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    User user=ds.getValue(User.class);
                    list.add(user);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });










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
    public void setDrillValue(int count,String drill){
        if(count==0){
            drillTextView1.setText(drill);
        }
        if(count==1){
            drillTextView2.setText(drill);
        }
        if(count==2){
            drillTextView3.setText(drill);
        }
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
