package com.example.idfrange;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class scoresActivity extends AppCompatActivity {

    TextView title,scrollNamesView;
    Button saveName,saveRange,saveScore,showScores;
    EditText editName,editRange,editScore;
    String[][] matrix;
    String newRange, newName, newScore;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        title=findViewById(R.id.title);
        saveName=findViewById(R.id.saveName);
        saveRange=findViewById(R.id.saveRange);
        saveScore=findViewById(R.id.saveScore);
        showScores=findViewById(R.id.showScoresButton);

        editName=findViewById(R.id.editName);
        editRange=findViewById(R.id.editRange);
        editScore=findViewById(R.id.editScore);

        scrollNamesView=findViewById(R.id.scrollNamesView);
        scrollNamesView.setMovementMethod(new ScrollingMovementMethod());

        saveRange=findViewById(R.id.saveRange);

        newScore="999";
        i=0;


        Bundle extras = getIntent().getExtras();           // getting previous view arrays
        final String[] nameList= extras.getStringArray("nameList"); //firstTable
        final String[] rangeList= extras.getStringArray("rangeList"); //secondTable


        printNames(nameList);
        printNames(rangeList);
        matrix= new String[40][10]; //one more for "name" string
        matrix=createTable(matrix,nameList,rangeList);


        saveRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollNamesView.setText("");
                printNames(rangeList);
                scrollNamesView.append(editRange.getText().toString());
                if(checkExist(rangeList,editRange.getText().toString()))
                {
                    newRange=editRange.getText().toString();
                    //editRange.setText("");
                    title.setText("מטווח נשמר");
                }
                else
                {
                    title.setText("מתווח לא קיים");
                }

            }
        });

        saveName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkExist(nameList,editName.getText().toString())) {
                    newName = editName.getText().toString();
                    //editName.setText("");
                    title.setText("שם נשמר");
                }
                else
                {
                    title.setText("שם לא קיים");
                }
            }
        });

        saveScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title.setText("");
                newRange= editRange.getText().toString();
                newName = editName.getText().toString();
                newScore= editScore.getText().toString();

                if(!checkExist(rangeList,newRange))
                {
                    title.setText("מתווח לא קיים");
                }
                else if(!checkExist(nameList,newName))
                {
                    title.setText("שם לא קיים");
                }
                else if(newScore.equals("999")){
                    title.setText("לא הוקש מספר");
                }

                else
                {
                    addScore(matrix,newName,newRange,newScore);
                    editScore.setText("");
                    //title.append("תוצאה נשמרה בהצלחה");
                    newScore="999"; //to recognize a blank
                }
                }
        });

        showScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent =new Intent(scoresActivity.this,tableActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("matrix",matrix);
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
            }
        });


    }

    public String[][] createTable(String[][] lastTable,String[] firstTable,String[] secondTable){
        lastTable[0][0]="ס";
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


    public void printNames(String[]names)
    {
        for (int i = 0; i <names.length ; i++) {
            if(names[i]!=null)
            {scrollNamesView.append(names[i]+"\n");}
        }
    }


    public boolean checkExist(String[] list,String check)
    {
        for (int i = 0; i < list.length; i++) {
            if(list[i].equals(check));
            {scrollNamesView.append("\n"+"TRUE because "+list[i]+" at place number "+i+" = "+check);
            return true;}
        }
        scrollNamesView.append("\n"+"comparison is FALSE");
        return false;
    }

    public String[][] addScore(String [][] matrix, String name, String range, String score){
        scrollNamesView.setText("");
        scrollNamesView.append(name+"\n"+range+"\n"+score);
        int namePosition=38,rangePosition=8;

        for (int i = 1; i < matrix.length-1; i++) {
            try{
                if(matrix[i][0].equals(name)){
                    scrollNamesView.append("\n"+namePosition);
                    namePosition=i;}}
            catch(Exception e){}
        }
        for (int j = 1; j < matrix[0].length-1; j++) {
            try{
                if(matrix[0][j].equals(range)){
                    scrollNamesView.append("\n"+rangePosition);
                    rangePosition=j;}}
            catch(Exception e){}
        }
        matrix[namePosition][rangePosition]=String.valueOf(score);
        return matrix;
    }

}