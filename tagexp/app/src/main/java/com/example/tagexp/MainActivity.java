package com.example.tagexp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> checked=new ArrayList<>();
    Button b;
    int buttoncounter=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Tags");

// Add a checkbox list
        final String[] choices = {"Alumni", "Faculty", "Seminar", "Workshop", "Research","Job","Internship","Club","Event","Company","Placement"};
        final boolean[] checkedItems =new boolean[choices.length];
        builder.setMultiChoiceItems(choices, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // The user checked or unchecked a box
                if(isChecked){
                    if(!checked.contains(choices[which])){
                        checked.add(choices[which]);
                    }
                }
                else{
                    if(checked.contains(choices[which])){
                        checked.add(choices[which]);
                    }
                }
            }
        });

// Add OK and Cancel buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // The user clicked OK
            }
        });
        builder.setNegativeButton("Cancel", null);

// Create and show the alert dialog
        AlertDialog dialog = builder.create();
        //dialog.show();
        b=(Button)findViewById(R.id.interested);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttoncounter%2==0){
                    b.setBackground(getResources().getDrawable(R.drawable.bluearrowpaint));
                    buttoncounter++;}
                else{
                    b.setBackground(getResources().getDrawable(R.drawable.uparrowpaint));
                    buttoncounter++;

                }
            }
        });
    }
}
