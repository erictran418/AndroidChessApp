package com.example.androidproject73;

public class Bishop extends ChessPiece {

    /**
     * Constructor of the class
     * @param file: the file of this piece
     * @param rank: the rank of this piece
     * @param color: the color of this piece
     *
     */
    public Bishop(int file, int rank, String color)
    {
        super(file, rank, color);

    }



    /**
     * This method determines if a particular move is valid for a particular Bishop piece/object on the board
     * @param newFile: the prospective file this Bishop piece will move to
     * @param newRank: the prospective file this Bishop piece will move to
     * @param board: the game chess board the piece is on
     * @return a boolean value that tells us whether the move to newFile, newRank is valid for this Bishop object
     *
     */





    @Override
    public boolean valid(int newFile, int newRank, ChessPiece[][] board) {




        int currentFile=this.getFile();
        int currentRank=this.getRank();
        String currentcolor=this.getColor();

        if(newFile<0 || newFile>7 || newRank<0 || newRank>7)
        {
            return false;
        }

        if(currentFile==newFile && currentRank==newRank)
        {
            return false;
        }




        if(newFile>currentFile && newRank<currentRank)
        {

            int rank=currentRank-1;
            int file=currentFile+1;


            while(rank>=0 && file<=7)
            {
                //check

                if(rank==newRank && file==newFile)
                {
                    if(board[file][rank]==null)
                    {

                        return true;
                    }
                    else
                    {
                        //same color

                        if(currentcolor.equals(board[file][rank].getColor()))
                        {
                            return false;
                        }
                        else
                        {

                            return true;
                        }


                    }
                }
                else
                {
                    if(board[file][rank]!=null)
                    {
                        return false;
                    }
                }


                rank--;
                file++;
            }

            //not in diag chain

            return false;

        }




        else if(newFile<currentFile && newRank<currentRank)
        {

            int rank=currentRank-1;
            int file=currentFile-1;


            while(file>=0 && rank>=0)
            {
                //check

                if(file==newFile && rank==newRank)
                {
                    if(board[file][rank]==null)
                    {

                        return true;
                    }
                    else
                    {
                        //same color

                        if(currentcolor.equals(board[file][rank].getColor()))
                        {
                            return false;
                        }
                        else
                        {

                            return true;
                        }


                    }
                }
                else
                {
                    if(board[file][rank]!=null)
                    {
                        return false;
                    }

                }


                file--;
                rank--;
            }

            //not in diag chain

            return false;

        }

        //bottom right

        else if(newFile>currentFile && newRank>currentRank)
        {

            int rank=currentRank+1;
            int file=currentFile+1;


            while(file<=7 && rank<=7)
            {
                //check

                if(file==newFile && rank==newRank)
                {
                    if(board[file][rank]==null)
                    {

                        return true;
                    }
                    else
                    {
                        //same color

                        if(currentcolor.equals(board[file][rank].getColor()))
                        {
                            return false;
                        }
                        else
                        {

                            return true;
                        }


                    }
                }
                else
                {
                    if(board[file][rank]!=null)
                    {
                        return false;
                    }
                }


                file++;
                rank++;
            }

            //not in diag chain

            return false;

        }

        //bottom left

        else if(newFile<currentFile && newRank>currentRank)
        {

            int rank=currentRank+1;
            int file=currentFile-1;


            while(rank<=7 && file>=0)
            {
                //check

                if(file==newFile && rank==newRank)
                {
                    if(board[file][rank]==null)
                    {

                        return true;
                    }
                    else
                    {
                        //same color

                        if(currentcolor.equals(board[file][rank].getColor()))
                        {
                            return false;
                        }
                        else
                        {

                            return true;
                        }


                    }
                }
                else
                {
                    if(board[file][rank]!=null)
                    {
                        return false;
                    }
                }


                rank++;
                file--;
            }

            //not in diag chain

            return false;

        }
        else
        {
            return false;
        }






    }
















}
