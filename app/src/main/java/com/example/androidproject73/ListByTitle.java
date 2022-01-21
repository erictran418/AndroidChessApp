package com.example.androidproject73;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;

import android.view.View;


public class ListByTitle extends AppCompatActivity {
    public static final String EXTRA_DATA = "EXTRA_DATA";
    private ArrayList<Game> al = new ArrayList<>();
    ArrayList<String> lister = new ArrayList<>();
    Game game = new Game (null,null,null,null);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_by_title);
        Bundle ext=getIntent().getExtras();
        al=(ArrayList<Game>) ext.get(EXTRA_DATA);
        //al = (ArrayList<Game>) getIntent().getSerializableExtra(EXTRA_DATA);
        if(al.isEmpty()) {

        } else {
            for (int i = 0; i < al.size(); i++) {
                lister.add(al.get(i).getTitle());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.lbtlistholder, lister);
            ListView lView = (ListView) findViewById(R.id.lbtList);
            lView.setAdapter(adapter);
            lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String hold = lView.getItemAtPosition(position).toString();
                    int num = returnIndex(hold);
                    game = al.get(num);

                    new AlertDialog.Builder(ListByTitle.this)
                            .setTitle("PlayRecordedGame?")
                            .setMessage("Play the currently selected game?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(ListByTitle.this, PlayRecordedGame.class);
                                    intent.putExtra(ListByTitle.EXTRA_DATA, game);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();


                }
            });
        }

    }
    public int returnIndex(String hold) {
        for(int i = 0; i<al.size();i++) {
            if(al.get(i).getTitle().equals(hold)) {
                return i;
            }
        }
        return -1;
    }
    public void AtoZ(View view){
        lister.sort(Comparator.comparing(String::toString));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.lbtlistholder, lister);
        ListView lView = (ListView)findViewById(R.id.lbtList);
        lView.setAdapter(adapter);

    }
    public void ZtoA(View view) {
        lister.sort(Comparator.comparing(String::toString).reversed());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.lbtlistholder, lister);
        ListView lView = (ListView)findViewById(R.id.lbtList);
        lView.setAdapter(adapter);


    }

}