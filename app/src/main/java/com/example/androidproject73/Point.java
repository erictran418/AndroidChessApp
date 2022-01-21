package com.example.androidproject73;

import java.io.Serializable;

public class Point implements Serializable {

    /**
     * An integer value that tells us the file of a particular square on the chess board
     */
    private int file;

    /**
     * An integer value that tells us the rank of a particular square  on the chess board
     */
    private int rank;

    /**
     * Constructor of the class, creates a new Point object
     * @param file: the file of the chess board square represented by the Point object
     * @param rank: the rank of the chess board square represented by the Point object
     *
     */


    public Point(int file,int rank)
    {
        this.file=file;
        this.rank=rank;
    }



    /**
     * This method  gives us the value of the file of a particular square on the chess board
     * @return the file of the square on the chess board represented by the Point object
     */


    public int getFile()
    {
        return file;
    }

    /**
     * This method  gives us the value of the rank of a particular square on the chess board
     * @return the rank of the square on the chess board represented by the Point object
     */


    public int getRank()
    {
        return rank;
    }

}
