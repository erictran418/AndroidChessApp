package com.example.androidproject73;

public class Pawn extends ChessPiece {

    /**
     * This static field is a Point object. It represents the current square on the chess board that a Pawn object resides on.
     * The Pawn on this particular square is the only piece that can be potentially currently captured enpassant by another Pawn in the game.
     */
    public static Point ensq;


    /**
     * Constructor of the class
     * @param file: the file of this piece
     * @param rank: the rank of this piece
     * @param color: the color of this piece
     *
     */


    public Pawn(int file, int rank, String color)
    {
        super(file,rank,color);
        ensq=null;

    }




    /**
     * Sets the static ensq field of the Pawn class to a Point representing the coordinates in the parameters
     * @param file: the new file of the ensq field
     * @param rank: the new rank of the ensq field
     */


    public static void setensq(int file, int rank)
    {
        ensq=new Point(file, rank);
    }


    /**
     * Sets the static ensq field of the Pawn class to null. Indicates there is no currently valid enpassant move available in the game
     */

    public static void cancelensq()
    {
        ensq=null;
    }




    /**
     * A method that determines if a particular move is valid for a particular Pawn piece/object on the board
     * @param newFile: the prospective file this Pawn will move to
     * @param newRank: the prospective rank this Pawn will move to
     * @param board: the game chess board
     * @return a boolean value that tells us whether the move to newFile, newRank is valid for this Pawn object
     *
     */


    @Override
    public boolean valid(int newFile, int newRank, ChessPiece[][] board) {

        boolean retval = true;

        int currFile = this.getFile();
        int currRank = this.getRank();
        String pieceColor = this.getColor();

        if (newFile<0 || newFile>7 || newRank<0 || newRank>7) return false;

        if (currFile == newFile && currRank == newRank) return false;

        ChessPiece newSquare = board[newFile][newRank];
        if(pieceColor.equals("black"))
        {
            //starting
            if(newRank+1>7)
            {
                return false;
            }

            if(currRank == 6)
            {
                if(newFile == currFile && newRank == currRank - 2 && newSquare == null && board[newFile][newRank+1]==null)
                {
                    return true;
                }
            }

            //reg
            if(newFile == currFile && newRank == currRank - 1 && newSquare == null)
            {
                return true;
            }

            //capture
            if((newFile == currFile + 1 || newFile == currFile - 1) && newRank == currRank - 1 && newSquare != null && newSquare.getColor().equals("white")) {
                return true;
            }

            //enpass
            if((newFile == currFile + 1 || newFile == currFile - 1) && newRank == currRank - 1 && newSquare == null)
            {
                if(ensq!=null && ensq.getRank()==currRank && (ensq.getFile()==currFile+1 || ensq.getFile()==currFile-1))
                {
                    if(board[ensq.getFile()][ensq.getRank()]!=null && board[ensq.getFile()][ensq.getRank()] instanceof Pawn && board[ensq.getFile()][ensq.getRank()].getColor().equals("white"))
                    {
                        return true;
                    }
                }
            }






        } else
        {
            //starting
            if(newRank-1<0)
            {
                return false;
            }



            if(currRank == 1)
            {
                if(newFile == currFile && newRank == currRank + 2 && newSquare == null && board[newFile][newRank-1]==null)
                {
                    return true;
                }
            }

            //reg
            if(newFile == currFile && newRank == currRank + 1 && newSquare == null)
            {
                return true;
            }

            //capture
            if((newFile == currFile + 1 || newFile == currFile - 1) && newRank == currRank + 1 && newSquare != null && newSquare.getColor().equals("black")) {
                return true;
            }

            //enpass
            if((newFile == currFile + 1 || newFile == currFile - 1) && newRank == currRank + 1 && newSquare == null)
            {
                if(ensq!=null && ensq.getRank()==currRank && (ensq.getFile()==currFile+1 || ensq.getFile()==currFile-1))
                {
                    if(board[ensq.getFile()][ensq.getRank()]!=null && board[ensq.getFile()][ensq.getRank()] instanceof Pawn && board[ensq.getFile()][ensq.getRank()].getColor().equals("black"))
                    {
                        return true;
                    }
                }
            }







        }

        return false;
    }





    /**
     * A static method that moves and promotes a Pawn to another ChessPiece
     * @param board: the game chess board
     * @param destfile: the prospective file a Pawn will move to
     * @param destrank: the prospective rank a Pawn will move to
     * @param promoteTo: the ChessPiece a Pawn will be promoted to when it reaches that (destfile, destrank) square of the board
     * @param color: the color of the Pawn being promoted
     *
     */

    public static void promote(ChessPiece[][] board, int destfile, int destrank, char promoteTo, String color)
    {

        if(promoteTo=='Q')
        {
            board[destfile][destrank]= new Queen(destfile, destrank, color);
        }
        else if(promoteTo=='R')
        {
            Rook r= new Rook(destfile, destrank, color);
            r.ismoved=true;
            board[destfile][destrank]=r;
        }
        else if(promoteTo=='N')
        {
            board[destfile][destrank]= new Knight(destfile, destrank, color);
        }
        else if(promoteTo=='B')
        {
            board[destfile][destrank]= new Bishop(destfile, destrank, color);
        }



    }



    public static void unpromote(ChessPiece[][] board, int oldfile, int oldrank, String color)
    {
        board[oldfile][oldrank]=null;
        board[oldfile][oldrank]=new Pawn(oldfile, oldrank, color);

    }

    /**
     * A method that returns whether or not the movement of a Pawn from one square to another is a valid promotion of that Pawn
     * @param board: the game chess board
     * @param pieceFile: the file of the Pawn in question before being moved
     * @param pieceRank: the rank of the Pawn in question before being moved
     * @param moveFile: the file of the Pawn's intended destination
     * @param moveRank: the rank of the Pawn's intended destination
     * @param currentplayerside: the color of the Pawn being promoted
     * @return a boolean value that indicates whether or not the movement of a Pawn from one (pieceFile, pieceRank) to (moveFile, moveRank) is a valid promotion of that Pawn
     */

    public boolean ispromote(ChessPiece[][] board, int pieceFile, int pieceRank, int moveFile, int moveRank,  String currentplayerside)
    {

        if(!(board[pieceFile][pieceRank] instanceof Pawn) || board[pieceFile][pieceRank].valid(moveFile, moveRank, board)==false)
        {
            return false;
        }


        if(((currentplayerside.equals("white") && pieceRank==6 && moveRank==7 && (pieceFile==moveFile || moveFile==pieceFile+1 || moveFile==pieceFile-1)) || ((currentplayerside.equals("black") && pieceRank==1 && moveRank==0 && (pieceFile==moveFile || moveFile==pieceFile+1 || moveFile==pieceFile-1)))))
        {
            return true;
        }
        else
        {
            return false;
        }
    }









}
