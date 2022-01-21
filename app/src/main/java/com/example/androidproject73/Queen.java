package com.example.androidproject73;

public class Queen extends ChessPiece {

    /**
     * Constructor of the class
     * @param file: the file of this piece
     * @param rank: the rank of this piece
     * @param color: the color of this piece
     *
     */

    public Queen(int file, int rank, String color)
    {
        super(file,rank,color);

    }

    /**
     * This method determines if a particular move is valid for a particular Queen piece/object on the board
     * @param newFile: the prospective file this Queen piece will move to
     * @param newRank: the prospective file this Queen piece will move to
     * @param board: the game chess board the piece is on
     * @return a boolean value that tells us whether the move to newFile, newRank is valid for this Queen object
     *
     */

    @Override
    public boolean valid(int newFile, int newRank, ChessPiece[][] board) {
        int currentFile = this.getFile();
        int currentRank = this.getRank();
        String currentcolor = this.getColor();

        //Use bishop logic with temporary bishop instance
        Bishop bishopQueen = new Bishop(currentFile, currentRank, currentcolor);
        if (bishopQueen.valid(newFile, newRank, board)) {
            return true;
        }

        //Use rook logic with temporary rook instance
        Rook rookQueen = new Rook(currentFile, currentRank, currentcolor);
        if (rookQueen.valid(newFile, newRank, board)) {
            return true;
        }

        return false;
    }














}
