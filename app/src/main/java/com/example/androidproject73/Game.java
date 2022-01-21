package com.example.androidproject73;

import java.io.Serializable;
import java.util.Calendar;
import java.util.*;
public class Game implements Serializable {

    private String title;
    private Calendar date;
    private ArrayList<Move> moves;
    private String winner;

    public Game(String title, Calendar currdate, ArrayList<Move> movelist, String winner)
    {
        this.title=title;
        this.date=currdate;
        this.moves=movelist;
        this.winner=winner;

    }
    public String getTitle()
    {
        return this.title;
    }
    public Calendar getDate()
    {
        return this.date;
    }
    public ArrayList<Move> getMoves()
    {
        return this.moves;
    }
    public String getWinner()
    {
        return this.winner;
    }




}
