package com.example.androidproject73;
import java.util.*;
public class King extends ChessPiece {

    /**
     * A boolean value that tells us if the King has ever been moved from its original position
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


    public King(int file, int rank, String color)
    {
        super(file,rank,color);
        ismoved=false;

    }


    /**
     * A method that determines if a particular move is valid for a particular King piece/object on the board
     * @param newFile: the prospective file this King will move to
     * @param newRank: the prospective rank this King will move to
     * @param board: the game chess board
     * @return a boolean value that tells us whether the move to newFile, newRank is valid for this King object
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


        //castling ?

        if(castlegood(board, currentfile, currentrank, newFile, newRank, currentcolor))
        {

            return true;
        }











        //check if valid

        ArrayList<Point> al= new ArrayList<Point>();
        al.add(new Point(currentfile+1,currentrank));
        al.add(new Point(currentfile-1,currentrank));
        al.add(new Point(currentfile-1,currentrank-1));
        al.add(new Point(currentfile-1,currentrank+1));
        al.add(new Point(currentfile,currentrank+1));
        al.add(new Point(currentfile,currentrank-1));
        al.add(new Point(currentfile+1,currentrank+1));
        al.add(new Point(currentfile+1,currentrank-1));


        boolean firstcheck=false;
        boolean occupiedenemy=false;
        for(int a=0;a<al.size();a++)
        {
            Point p=al.get(a);
            int checkedfile=p.getFile();
            int checkedrank=p.getRank();

            //out of bounds
            if(checkedfile<0 || checkedfile>7 || checkedrank<0 || checkedrank>7)
            {
                continue;
            }

            //dest.

            if(checkedfile==newFile && checkedrank==newRank)
            {
                //unoccupied
                if(board[checkedfile][checkedrank]==null)
                {
                    firstcheck=true;
                    break;
                }
                //occupied
                else
                {
                    //same side
                    if(board[checkedfile][checkedrank].getColor().equals(currentcolor))
                    {

                        return false;
                    }
                    //diff side
                    else
                    {
                        firstcheck=true;
                        occupiedenemy=true;
                        break;
                    }
                }




            }





        }

        if(firstcheck==false)
        {
            return false;
        }

        //check if safe

        if(board[newFile][newRank]==null)
        {

            String opposite="black";
            if(currentcolor.equals("black"))
            {
                opposite="white";
            }

            boolean ans=incheck(board, opposite, currentfile, currentrank, newFile,newRank);

            return !ans;



        }
        else
        {
            String opposite="black";
            if(currentcolor.equals("black"))
            {
                opposite="white";
            }


            boolean ans=incheck(board, opposite, currentfile, currentrank, newFile,newRank);


            return !ans;


        }


    }





    /**
     * A static method that determines if a particular move of a King object puts that King in check (thus making that move illegal)
     * @param currentFile: the file the King in question currently occupies
     * @param currentRank: the rank the King in question currently occupies
     * @param newFile: the prospective file this King will move to
     * @param newRank: the prospective rank this King will move to
     * @param board: the game chess board
     * @return a boolean value that tells us whether the move to (newFile, newRank) puts a specified King in check
     *
     */



    public static boolean incheck(ChessPiece[][] board, String opposite, int currentFile, int currentRank,  int newFile, int newRank)
    {
        boolean retval=false;

        boolean same=false;

        if(currentFile==newFile && currentRank==newRank)
        {
            same=true;
        }

        //store old value
        ChessPiece destinationval=null;

        boolean ismoveddest=false;
        boolean ismovedorigin=false;

        if(same==false)
        {





            if(board[newFile][newRank]==null)
            {
                destinationval=null;
            }

            else if(board[newFile][newRank] instanceof Pawn)
            {
                destinationval=new Pawn(newFile,newRank, board[newFile][newRank].getColor());
            }
            else if(board[newFile][newRank] instanceof Knight)
            {
                destinationval=new Knight(newFile,newRank, board[newFile][newRank].getColor());
            }
            else if(board[newFile][newRank] instanceof Queen)
            {
                destinationval=new Queen(newFile,newRank, board[newFile][newRank].getColor());
            }
            else if(board[newFile][newRank] instanceof Rook)
            {
                destinationval=new Rook(newFile,newRank, board[newFile][newRank].getColor());

                ismoveddest= ((Rook)(board[newFile][newRank])).ismoved;

            }
            else if(board[newFile][newRank] instanceof Bishop)
            {
                destinationval=new Bishop(newFile,newRank, board[newFile][newRank].getColor());
            }
            else
            {
                destinationval=new King(newFile,newRank, board[newFile][newRank].getColor());

                ismoveddest= ((King)(board[newFile][newRank])).ismoved;

            }






            //origin store

            if(board[currentFile][currentRank]!=null && (board[currentFile][currentRank] instanceof King || board[currentFile][currentRank] instanceof Rook))
            {



                if(board[currentFile][currentRank] instanceof King)
                {
                    ismovedorigin=((King)(board[currentFile][currentRank])).ismoved;
                }
                else if(board[currentFile][currentRank] instanceof Rook)
                {
                    ismovedorigin=((Rook)(board[currentFile][currentRank])).ismoved;
                }
            }




            ChessPiece.move(currentFile, currentRank, newFile, newRank, board);


        }






        for(int file=0;file<8;file++)
        {
            for(int rank=0;rank<8;rank++)
            {
                if(board[file][rank]!=null && board[file][rank].getColor().equals(opposite))
                {
                    if(board[file][rank] instanceof Rook || board[file][rank] instanceof Bishop || board[file][rank] instanceof Knight || board[file][rank] instanceof Queen)
                    {
                        if(board[file][rank].valid(newFile, newRank, board))
                        {
                            retval=true;
                            break;
                        }
                    }
                    else if(board[file][rank] instanceof Pawn)
                    {
                        if(opposite.equals("white"))
                        {
                            int captfile1=file+1;
                            int captrank1=rank+1;
                            int captfile2=file-1;
                            int captrank2=rank+1;

                            if(captfile1==newFile && captrank1==newRank)
                            {
                                retval=true;
                                break;
                            }
                            else if(captfile2==newFile && captrank2==newRank)
                            {
                                retval=true;
                                break;
                            }


                        }
                        else
                        {
                            int captfile1=file+1;
                            int captrank1=rank-1;
                            int captfile2=file-1;
                            int captrank2=rank-1;

                            if(captfile1==newFile && captrank1==newRank)
                            {
                                retval=true;
                                break;
                            }
                            else if(captfile2==newFile && captrank2==newRank)
                            {
                                retval=true;
                                break;
                            }
                        }
                    }
                    //other king
                    else if(board[file][rank] instanceof King)
                    {
                        ArrayList<Point> al2= new ArrayList<Point>();
                        al2.add(new Point(file+1,rank));
                        al2.add(new Point(file-1,rank));
                        al2.add(new Point(file-1,rank-1));
                        al2.add(new Point(file-1,rank+1));
                        al2.add(new Point(file,rank+1));
                        al2.add(new Point(file,rank-1));
                        al2.add(new Point(file+1,rank+1));
                        al2.add(new Point(file+1,rank-1));
                        boolean breakouter=false;
                        for(int a=0;a<al2.size();a++)
                        {
                            Point p=al2.get(a);
                            int f=p.getFile();
                            int r=p.getRank();

                            if(f==newFile && r==newRank)
                            {
                                retval=true;
                                breakouter=true;
                                break;

                            }
                        }

                        if(breakouter)
                        {
                            break;
                        }




                    }
                }
            }
        }



        //restore

        if(same==false)
        {

            board[currentFile][currentRank]=board[newFile][newRank];
            board[currentFile][currentRank].setFile(currentFile);
            board[currentFile][currentRank].setRank(currentRank);
            board[newFile][newRank]=destinationval;

            if(board[currentFile][currentRank] instanceof Rook)
            {
                Rook r=(Rook)board[currentFile][currentRank];
                r.ismoved=ismovedorigin;
            }
            if(board[currentFile][currentRank] instanceof King)
            {
                King k=(King)board[currentFile][currentRank];
                k.ismoved=ismovedorigin;
            }

            if(board[newFile][newRank] !=null && board[newFile][newRank] instanceof Rook)
            {
                Rook r=(Rook)board[newFile][newRank];
                r.ismoved=ismoveddest;
            }
            if(board[newFile][newRank] !=null && board[newFile][newRank] instanceof King)
            {
                King k=(King)board[newFile][newRank];
                k.ismoved=ismoveddest;
            }


        }







        return retval;
    }


    /**
     * A method that determines if a particular castling of a King object is valid
     * @param board: the game chess board
     * @param currfile: the file the King in question currently occupies
     * @param currrank: the rank the King in question currently occupies
     * @param newFile: the prospective file this King will move to during the castling process
     * @param newRank: the prospective rank this King will move to during the castling process
     * @param color: the color of the King object in question
     * @return a boolean value that tells us whether the move to newFile, newRank is valid for this King object
     *
     */


    public boolean castlegood(ChessPiece[][] board, int currfile, int currrank, int newFile, int newRank, String color)
    {

        String opp="black";

        if(color.equals("black"))
        {
            opp="white";
        }










        if(newFile==6 && newRank==0 && currfile==4 && currrank==0 && color.equals("white"))
        {
            //King restrictions
            if(this.ismoved || board[5][0]!=null || board[6][0]!=null)
            {
                return false;
            }
            if(incheck(board,opp,currfile, currrank, 4,0) || incheck(board,opp,currfile, currrank, 5,0) || incheck(board,opp, currfile, currrank,6,0))
            {
                return false;
            }

            //Rook restrictions

            if(board[7][0]==null)
            {
                return false;
            }

            if(board[7][0].getColor().equals(opp) || board[7][0] instanceof Rook==false)
            {
                return false;
            }

            Rook r=(Rook)board[7][0];

            if(r.ismoved)
            {
                return false;
            }




            return true;


        }
        else if(newFile==2 && newRank==0 && currfile==4 && currrank==0 && color.equals("white"))
        {
            //King restrictions
            if(this.ismoved || board[3][0]!=null || board[2][0]!=null || board[1][0]!=null)
            {
                return false;
            }
            if(incheck(board,opp, currfile, currrank, 4,0) || incheck(board,opp,currfile, currrank, 3,0) || incheck(board,opp,currfile, currrank,2,0))
            {
                return false;
            }

            //Rook restrictions

            if(board[0][0]==null)
            {
                return false;
            }

            if(board[0][0].getColor().equals(opp) || board[0][0] instanceof Rook==false)
            {
                return false;
            }

            Rook r=(Rook)board[0][0];

            if(r.ismoved)
            {
                return false;
            }




            return true;
        }
        else if(newFile==6 && newRank==7 && currfile==4 && currrank==7 && color.equals("black"))
        {
            //King restrictions
            if(this.ismoved || board[5][7]!=null || board[6][7]!=null)
            {
                return false;
            }
            if(incheck(board,opp, currfile, currrank, 4,7) || incheck(board,opp, currfile, currrank, 5,7) || incheck(board,opp, currfile, currrank, 6,7))
            {
                return false;
            }

            //Rook restrictions

            if(board[7][7]==null)
            {
                return false;
            }

            if(board[7][7].getColor().equals(opp) || board[7][7] instanceof Rook==false)
            {
                return false;
            }

            Rook r=(Rook)board[7][7];

            if(r.ismoved)
            {
                return false;
            }




            return true;
        }
        else if(newFile==2 && newRank==7 && currfile==4 && currrank==7 && color.equals("black"))
        {
            //King restrictions
            if(this.ismoved|| board[3][7]!=null || board[2][7]!=null || board[1][7]!=null)
            {
                return false;
            }
            if(incheck(board,opp, currfile, currrank, 4,7) || incheck(board,opp, currfile, currrank, 3,7) || incheck(board,opp, currfile, currrank, 2,7))
            {
                return false;
            }

            //Rook restrictions

            if(board[0][7]==null)
            {
                return false;
            }

            if(board[0][7].getColor().equals(opp) || board[0][7] instanceof Rook==false)
            {
                return false;
            }

            Rook r=(Rook)board[0][7];

            if(r.ismoved)
            {
                return false;
            }




            return true;
        }
        else
        {
            return false;
        }




    }



}
