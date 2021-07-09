package com.example.idfrange;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class tableActivity extends AppCompatActivity {
    String[][] matrix;
    TextView matrixView;
    Button returnButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        matrixView=findViewById(R.id.matrixView);
        matrixView.setMovementMethod(new ScrollingMovementMethod());
        returnButton=findViewById(R.id.returnButton);

        Bundle extras = getIntent().getExtras();           // getting previous view arrays.
        matrix = (String[][]) extras.getSerializable("matrix");

        for(int i=0;i<matrix.length-1;i++){
            for(int j=0;j<matrix[i].length-1;j++){
                if(matrix[i][j]!=null){
                    matrixView.append("| "+matrix[i][j]+" |");}
                else{matrixView.append("|       |");}
            }
            matrixView.append("\n"+"------------------------------------------------------------------"+"\n");
        }

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            finish();

//                Intent mIntent =new Intent(tableActivity.this,scoresActivity.class);
//                Bundle mBundle = new Bundle();
//                mBundle.putSerializable("matrix",matrix);
//                mIntent.putExtras(mBundle);
//                startActivity(mIntent);
                    }
                });
    }
}
