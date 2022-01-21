package com.example.androidproject73;

public class Rook extends ChessPiece {


    /**
     * A boolean value that tells us if the Rook has ever been moved from its original position
     * Is useful for determining the validity of castles involving this piece
     *
     */


    public boolean ismoved;



    /**
     * Constructor of the class
     * @param file: the file of this piece
     * @param rank: the rank of this piece
     * @param color: the color of this piece
     *
     */


    public Rook(int file, int rank, String color)
    {
        super(file,rank,color);
        ismoved=false;

    }

    /**
     * This method determines if a particular move is valid for a particular Rook piece/object on the board
     * @param newFile: the prospective file this Rook piece will move to
     * @param newRank: the prospective file this Rook piece will move to
     * @param board: the game chess board the piece is on
     * @return a boolean value that tells us whether the move to newFile, newRank is valid for this Rook object
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

        if(currentfile!=newFile && currentrank!=newRank)
        {
            return false;
        }

        if(currentfile==newFile && currentrank==newRank)
        {
            return false;
        }


        //looking to move across
        if(currentrank!=newRank)
        {
            if(newRank<currentrank)
            {
                for(int rank=currentrank-1;rank>=
                        newRank;rank--)
                {
                    if(board[currentfile][rank]!=null)
                    {
                        if(rank==newRank)
                        {
                            if((board[currentfile][rank].getColor()).equals(currentcolor))
                            {
                                return false;
                            }
                            else
                            {
                                return true;
                            }
                        }
                        else
                        {
                            return false;
                        }
                    }
                }
            }
            else if(newRank>currentrank)
            {
                for(int rank=currentrank+1;rank<=
                        newRank;rank++)
                {
                    if(board[currentfile][rank]!=null)
                    {

                        if(rank==newRank)
                        {
                            if((board[currentfile][rank].getColor()).equals(currentcolor))
                            {
                                return false;
                            }
                            else
                            {
                                return true;
                            }
                        }
                        else
                        {
                            return false;
                        }
                    }
                }
            }
        }

        //looking to move vertical
        else if(currentfile!=newFile)
        {
            if(newFile<currentfile)
            {
                for(int file=currentfile-1;file>=newFile;file--)
                {
                    if(board[file][currentrank]!=null)
                    {
                        if(file==newFile)
                        {
                            if((board[file][currentrank].getColor()).equals(currentcolor))
                            {
                                return false;
                            }
                            else
                            {
                                return true;
                            }
                        }
                        else
                        {
                            return false;
                        }
                    }
                }
            }
            else if(newFile>currentfile)
            {
                for(int file=currentfile+1;file<=newFile;file++)
                {
                    if(board[file][currentrank]!=null )
                    {
                        if(file==newFile)
                        {
                            if((board[file][currentrank].getColor()).equals(currentcolor))
                            {
                                return false;
                            }
                            else
                            {
                                return true;
                            }
                        }
                        else
                        {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

}
