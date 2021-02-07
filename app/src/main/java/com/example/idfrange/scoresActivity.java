package com.example.idfrange;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class scoresActivity extends AppCompatActivity {

    TextView title,scrollView;
    Button saveData;
    EditText editData;
    String[][] matrix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        title=findViewById(R.id.title);
        scrollView=findViewById(R.id.scrollView);
        saveData=findViewById(R.id.saveData);
        editData=findViewById(R.id.editData);


        Bundle extras = getIntent().getExtras();           // getting previous view arrays
        final String[] nameList= extras.getStringArray("nameList"); //firstTable
        final String[] rangeList= extras.getStringArray("rangeList"); //secondTable


        matrix= new String[41][11]; //one more for "name" string
        matrix=createTable(matrix,nameList,rangeList);





    }

    public String[][] createTable(String[][] lastTable,String[] firstTable,String[] secondTable){
        lastTable[0][0]="name";
        for(int i=1;i<firstTable.length-1;i++)   //i is 1 because of "name" string
        {
            lastTable[i][0]=firstTable[i-1];
        }
        for(int j=1;j<secondTable.length-1;j++)   //i is 1 because of "name" string
        {
            lastTable[0][j]=secondTable[j-1];
        }
        return lastTable;


    }

}