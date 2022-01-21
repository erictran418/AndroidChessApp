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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import android.view.View;

public class ListByDate extends AppCompatActivity {
    public static final String EXTRA_DATA = "EXTRA_DATA";
    private ArrayList<Game> al = new ArrayList<>();
    ArrayList<Date> dateList = new ArrayList<Date>();
    Game game = new Game (null,null,null,null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_by_date);
        al = (ArrayList<Game>) getIntent().getSerializableExtra(EXTRA_DATA);
        if(al.isEmpty()) {

        } else {
            for (int i = 0; i < al.size(); i++) {
                dateList.add(al.get(i).getDate().getTime());
            }
            ArrayAdapter<Date> adapter = new ArrayAdapter<Date>(this, R.layout.lbtlistholder, dateList);
            ListView lView = (ListView) findViewById(R.id.lbdList);
            lView.setAdapter(adapter);
            lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Date date = (Date) lView.getAdapter().getItem(position);
                    int num = returnIndex(date);
                    game = al.get(num);

                    new AlertDialog.Builder(ListByDate.this)
                            .setTitle("PlayRecordedGame?")
                            .setMessage("Play the currently selected game?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(ListByDate.this, PlayRecordedGame.class);
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
    public int returnIndex(Date date) {
        for(int i = 0; i<dateList.size();i++) {
            if(al.get(i).getDate().getTime().equals(date)) {
                return i;
            }
        }
        return -1;
    }
    public void Oldest(View view) {
        Collections.sort(dateList);
        ArrayAdapter<Date> adapter = new ArrayAdapter<Date>(this, R.layout.lbtlistholder, dateList);
        ListView lView = (ListView)findViewById(R.id.lbdList);
        lView.setAdapter(adapter);
    }
    public void Newest(View view) {
        Collections.sort(dateList, Collections.reverseOrder());
        ArrayAdapter<Date> adapter = new ArrayAdapter<Date>(this, R.layout.lbtlistholder, dateList);
        ListView lView = (ListView)findViewById(R.id.lbdList);
        lView.setAdapter(adapter);
    }
}