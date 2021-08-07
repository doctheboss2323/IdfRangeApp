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
    TextView drillTextView1,drillTextView2,drillTextView3,drillTextView4,drillTextView5,drillTextView6,drillTextView7,drillTextView8,drillTextView9,drillTextView10;
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
        drillTextView4=findViewById(R.id.drill4textview);
        drillTextView5=findViewById(R.id.drill5textview);
        drillTextView6=findViewById(R.id.drill6textview);
        drillTextView7=findViewById(R.id.drill7textview);
        drillTextView8=findViewById(R.id.drill8textview);
        drillTextView9=findViewById(R.id.drill9textview);
        drillTextView10=findViewById(R.id.drill10textview);


        nameDb.child(rangeId).child("Drill list").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    try{
                        String stcount=ds.getValue(String.class);
                        int count=Integer.parseInt(stcount);
                        setDrillValue(count,ds.getKey());}
                    catch(Exception e){}
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



 }
    public void setDrillValue(int count,String drill){
        if(count==1){
            drillTextView1.setText(drill);
        }
        if(count==2){
            drillTextView2.setText(drill);
        }
        if(count==3){
            drillTextView3.setText(drill);
        }
        if(count==4){
            drillTextView4.setText(drill);
        }
        if(count==5){
            drillTextView5.setText(drill);
        }
        if(count==6){
            drillTextView6.setText(drill);
        }
        if(count==7){
            drillTextView7.setText(drill);
        }
        if(count==8){
            drillTextView8.setText(drill);
        }
        if(count==9){
            drillTextView9.setText(drill);
        }
        if(count==10){
            drillTextView10.setText(drill);
        }
    }





//    private int idCounter(DatabaseReference ref) {
//        final int[] counter = new int[0];
//        ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                counter[0] = (int) snapshot.getChildrenCount();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//
//        });
//        int count=counter[0];
//        return count;
//    }
}
