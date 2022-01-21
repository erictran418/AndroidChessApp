package com.example.androidproject73;

import java.io.Serializable;

public class Move implements Serializable {

     private String side;
     private int origfile;
     private int origrank;
     private int destfile;
     private int destrank;
     private String pieceorig;
     private String piecedest;
     private Point enpasssq;
     private boolean ismovedorg=false;
     private boolean ismoveddest=false;
     private boolean ispromotion=false;
     private boolean iscastle=false;
     private boolean isenpass=false;
     private char promoteTo='\0';

     public Move(ChessPiece[][] board, String side, int origfile, int origrank, int destfile , int destrank, Point enpasssqcurrent)
     {
         this.side=side;
         this.origfile=origfile;
         this.origrank=origrank;
         this.destfile=destfile;
         this.destrank=destrank;
         this.enpasssq=enpasssqcurrent;

         //orig

         if(board[origfile][origrank].getColor().equals("white"))
         {
             if(board[origfile][origrank] instanceof Pawn)
             {
                 this.pieceorig="wP";

             }
             else if(board[origfile][origrank] instanceof Queen)
             {
                 this.pieceorig="wQ";
             }
             else if(board[origfile][origrank] instanceof King)
             {
                 this.pieceorig="wK";
                 King k=(King)board[origfile][origrank];
                 this.ismovedorg=k.ismoved;
             }
             else if(board[origfile][origrank] instanceof Bishop)
             {
                 this.pieceorig="wB";
             }
             else if(board[origfile][origrank] instanceof Knight)
             {
                 this.pieceorig="wN";
             }
             else if(board[origfile][origrank] instanceof Rook)
             {
                 this.pieceorig="wR";
                 Rook r=(Rook)board[origfile][origrank];
                 this.ismovedorg=r.ismoved;
             }
         }
         else
         {
             if(board[origfile][origrank] instanceof Pawn)
             {
                 this.pieceorig="bP";

             }
             else if(board[origfile][origrank] instanceof Queen)
             {
                 this.pieceorig="bQ";
             }
             else if(board[origfile][origrank] instanceof King)
             {
                 this.pieceorig="bK";
                 King k=(King)board[origfile][origrank];
                 this.ismovedorg=k.ismoved;
             }
             else if(board[origfile][origrank] instanceof Bishop)
             {
                 this.pieceorig="bB";
             }
             else if(board[origfile][origrank] instanceof Knight)
             {
                 this.pieceorig="bN";
             }
             else if(board[origfile][origrank] instanceof Rook)
             {
                 this.pieceorig="bR";
                 Rook r=(Rook)board[origfile][origrank];
                 this.ismovedorg=r.ismoved;
             }
         }


         //dest
         if(board[destfile][destrank]==null)
         {
             this.piecedest=null;
         }
         else if(board[destfile][destrank].getColor().equals("white"))
         {
             if(board[destfile][destrank] instanceof Pawn)
             {
                 this.piecedest="wP";

             }
             else if(board[destfile][destrank] instanceof Queen)
             {
                 this.piecedest="wQ";
             }
             else if(board[destfile][destrank] instanceof King)
             {
                 this.piecedest="wK";
                 King k=(King)board[destfile][destrank];
                 this.ismoveddest=k.ismoved;
             }
             else if(board[destfile][destrank] instanceof Bishop)
             {
                 this.piecedest="wB";
             }
             else if(board[destfile][destrank] instanceof Knight)
             {
                 this.piecedest="wN";
             }
             else if(board[destfile][destrank] instanceof Rook)
             {
                 this.piecedest="wR";
                 Rook r=(Rook)board[destfile][destrank];
                 this.ismoveddest=r.ismoved;
             }
         }
         else
         {
             if(board[destfile][destrank] instanceof Pawn)
             {
                 this.piecedest="bP";

             }
             else if(board[destfile][destrank] instanceof Queen)
             {
                 this.piecedest="bQ";
             }
             else if(board[destfile][destrank] instanceof King)
             {
                 this.piecedest="bK";
                 King k=(King)board[destfile][destrank];
                 this.ismoveddest=k.ismoved;
             }
             else if(board[destfile][destrank] instanceof Bishop)
             {
                 this.piecedest="bB";
             }
             else if(board[destfile][destrank] instanceof Knight)
             {
                 this.piecedest="bN";
             }
             else if(board[destfile][destrank] instanceof Rook)
             {
                 this.piecedest="bR";
                 Rook r=(Rook)board[destfile][destrank];
                 this.ismoveddest=r.ismoved;
             }
         }













     }
     public String getSide()
     {
         return side;
     }
    public int getOrigfile()
    {
        return origfile;
    }
    public int getOrigrank()
    {
        return origrank;
    }
    public int getDestfile()
    {
        return destfile;
    }
    public int getDestrank()
    {
        return destrank;
    }
    public String getpOrig(){return this.pieceorig;}
    public String getpDest(){return this.piecedest;}
    public Point getEnpasssq(){return enpasssq;}

    public void setismovedorigin( boolean val)
    {
        this.ismovedorg=val;
    }
    public void setismoveddest( boolean val)
    {
        this.ismoveddest=val;
    }
    public boolean getismovedorigin()
    {
        return this.ismovedorg;
    }
    public boolean getismoveddest()
    {
        return this.ismoveddest;
    }
    public void setisprom()
    {
        this.ispromotion=true;
    }
    public boolean getisprom()
    {
        return this.ispromotion;
    }
    public void setiscas()
    {
        this.iscastle=true;
    }
    public boolean getiscas()
    {
        return this.iscastle;
    }
    public void setisen()
    {
        this.isenpass=true;
    }
    public boolean getisen()
    {
        return this.isenpass;
    }
    public void setpromTo(char c)
    {
        this.promoteTo=c;
    }
    public char getpromTo()
    {
        return this.promoteTo;
    }






}
