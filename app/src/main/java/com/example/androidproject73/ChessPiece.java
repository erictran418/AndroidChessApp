package com.example.androidproject73;
import com.example.androidproject73.King;

import java.io.Serializable;

public abstract class ChessPiece implements Serializable {

    /**
     * This is a static method that converts a user-inputed file value to a file coordinate matching up with the program's 2D array representation of the chess board
     * @param f: the file coordinate of a square on the chess board the user enters
     * @return an integer value used to identify the file of the square the user is referring to in the program's 2D array representation of the chess board
     */


    public static int fileToInt(char f) {
        switch(f) {
            case 'a':
                return 0;
            case 'b':
                return 1;
            case 'c':
                return 2;
            case 'd':
                return 3;
            case 'e':
                return 4;
            case 'f':
                return 5;
            case 'g':
                return 6;
            case 'h':
                return 7;
            default:
                return -1;
        }
    }




    public static int rankToInt(int r) {
        return r - 1;
    }
    public static void move(int oldFile, int oldRank, int newFile, int newRank, ChessPiece[][] board) {
        board[newFile][newRank] = board[oldFile][oldRank];
        board[newFile][newRank].setFile(newFile);
        board[newFile][newRank].setRank(newRank);

        if(board[newFile][newRank] instanceof King)
        {
            King k= (King) board[newFile][newRank];
            k.ismoved=true;
        }

        if(board[newFile][newRank] instanceof Rook)
        {
            Rook r= (Rook) board[newFile][newRank];
            r.ismoved=true;
        }


        board[oldFile][oldRank] = null;
    }

    /**
     * An integer value that is the current file coordinate of the ChessPiece object on the board
     */

    private int file;

    /**
     * An integer value that is the current rank coordinate of the ChessPiece object on the board
     */

    private int rank;

    /**
     * A String value that is the current color of the ChessPiece object
     */

    private String color;





    public abstract boolean valid(int fileMove, int rankMove, ChessPiece[][] board);


    /**
     * Constructor of the class
     * @param file: the file of this piece
     * @param rank: the rank of this piece
     * @param color: the color of this piece
     *
     */



    public ChessPiece(int file, int rank, String color)
    {
        this.file = file;
        this.rank = rank;
        this.color = color;
    }

    /**
     * Returns the file coordinate of this piece on the board
     * @return the file coordinate of this piece on the board
     */

    public int getFile()
    {
        return this.file;
    }

    /**
     * Returns the rank coordinate of this piece on the board
     * @return the rank coordinate of this piece on the board
     */


    public int getRank()
    {
        return this.rank;
    }

    /**
     * Returns the color of this piece
     * @return the color of this piece
     */

    public String getColor()
    {
        return color;
    }

    /**
     * Sets the file field of a ChessPiece object to an integer passed in as a parameter
     * @param newFile: the new file of this piece
     */

    public void setFile(int newFile)
    {
        this.file = newFile;
    }

    /**
     * Sets the rank field of a ChessPiece object to an integer passed in as a parameter
     * @param newRank: the new rank of this piece
     */

    public void setRank(int newRank)
    {
        this.rank = newRank;
    }

    /**
     * Sets the color field of a ChessPiece object to a String passed in as a parameter
     * @param newColor: the new color of this piece
     */

    public void setColor(String newColor)
    {
        this.color= newColor;
    }


}
