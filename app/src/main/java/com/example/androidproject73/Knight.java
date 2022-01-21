package com.example.androidproject73;
import java.util.*;
public class Knight extends ChessPiece{

    /**
     * Constructor of the class
     * @param file: the file of this piece
     * @param rank: the rank of this piece
     * @param color: the color of this piece
     *
     */

    public Knight(int file, int rank, String color)
    {
        super(file,rank,color);

    }

    /**
     * This method determines if a particular move is valid for a particular Knight piece/object on the board
     * @param newFile: the prospective file this Knight piece will move to
     * @param newRank: the prospective file this Knight piece will move to
     * @param board: the game chess board the piece is on
     * @return a boolean value that tells us whether the move to newFile, newRank is valid for this Knight object
     *
     */

    @Override
    public boolean valid(int newFile, int newRank, ChessPiece[][] board) {



        int currentfile=this.getFile();
        int currentrank=this.getRank();
        String currentcolor=this.getColor();

        if(newFile<0 || newFile>7 || newRank<0 || newRank>7)
        {
            return false;
        }

        if(currentfile==newFile && currentrank==newRank)
        {
            return false;
        }


        ArrayList<Point> al= new ArrayList<Point>();
        al.add(new Point(currentfile-2,currentrank+1));
        al.add(new Point(currentfile-2,currentrank-1));
        al.add(new Point(currentfile-1,currentrank-2));
        al.add(new Point(currentfile-1,currentrank+2));
        al.add(new Point(currentfile+1,currentrank-2));
        al.add(new Point(currentfile+2,currentrank+1));
        al.add(new Point(currentfile+2,currentrank-1));
        al.add(new Point(currentfile+1,currentrank+2));


        for (Point p : al) {
            int checkedFile = p.getFile();
            int checkedRank = p.getRank();

            //out of bounds
            if (checkedFile < 0 || checkedRank > 7 || checkedFile < 0 || checkedRank > 7) {
                continue;
            }

            //dest.
            if (checkedRank == newRank && checkedFile == newFile) {
                //unoccupied
                if (board[checkedFile][checkedRank] == null) {
                    return true;
                }
                //occupied
                else {
                    //same side
                    if (board[checkedFile][checkedRank].getColor().equals(currentcolor)) {
                        return false;
                    }
                    //diff side
                    else {
                        return true;
                    }
                }


            }


        }
        //not in possible moves

        return false;
    }







}
