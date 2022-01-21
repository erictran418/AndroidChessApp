package com.example.androidproject73;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import java.util.ArrayList;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import android.view.View;
import android.widget.Toast;

public class PlayRecordedGame extends AppCompatActivity {
    public static final String EXTRA_DATA = "EXTRA_DATA";
    Game game = new Game(null, null, null, null);
    ArrayList<Move> moves=null;
    private ChessPiece[][] board;
    private int bkingfile;
    private int bkingrank;
    private int wkingfile;
    private int wkingrank;
    int index=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_recorded_game);
        game = (Game) getIntent().getSerializableExtra(EXTRA_DATA);
        moves=game.getMoves();
        //System.out.println(game.getWinner());
        this.index=0;
        //set up board
        board = new ChessPiece[8][8];
        //empty spaces
        for(int i=2;i<=5;i++)
        {
            for(int j=0;j<8;j++)
            {
                board[i][j]=null;
            }
        }

        //black pawns
        for(int j=0;j<8;j++)
        {
            board[j][6]=new Pawn(j,6,"black");
        }

        //white pawns
        for(int j=0;j<8;j++)
        {
            board[j][1]=new Pawn(j,1,"white");
        }

        //black back row
        board[0][7]= new Rook(0,7,"black");
        board[1][7]= new Knight(1,7,"black");
        board[2][7]= new Bishop(2,7,"black");
        board[3][7]= new Queen(3,7,"black");
        board[4][7]= new King(4,7,"black");
        board[5][7]= new Bishop(5,7,"black");
        board[6][7]= new Knight(6,7,"black");
        board[7][7]= new Rook(7, 7,"black");

        //white back row
        board[0][0]= new Rook(0,0,"white");
        board[1][0]= new Knight(1,0,"white");
        board[2][0]= new Bishop(2,0,"white");
        board[3][0]= new Queen(3,0,"white");
        board[4][0]= new King(4,0,"white");
        board[5][0]= new Bishop(5,0,"white");
        board[6][0]= new Knight(6,0,"white");
        board[7][0]= new Rook(7,0,"white");


        bkingfile=4;
        bkingrank=7;
        wkingfile=4;
        wkingrank=0;





    }
    public void Cycle (View view){

        if(index>moves.size()-1)
        {
            Context context = getApplicationContext();
            CharSequence text = "Game is Over!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            Context context2 = getApplicationContext();
            CharSequence text2;
            if(game.getWinner() == null) {
               text2 = "Game Ended in Draw!";
            } else {
                text2 = game.getWinner()+" Wins!";
            }
            int duration2 = Toast.LENGTH_SHORT;

            Toast toast2 = Toast.makeText(context2, text2, duration2);
            toast2.show();
            return;
        }



       // System.out.println("***HeHe***");
        Move toexec=moves.get(index);
        String side=toexec.getSide();
        if(toexec.getisprom())
        {
            int bf=toexec.getOrigfile();
            int br=toexec.getOrigrank();
            Pawn p=(Pawn)board[bf][br];

            int df=toexec.getDestfile();
            int dr=toexec.getDestrank();

            Pawn.promote(board,df,dr,'Q', side);

            board[bf][br]=null;

            ImageButton orbut=getbutton(toexec.getOrigfile()+1, toexec.getOrigrank()+1);
            ImageButton dbut=getbutton(toexec.getDestfile()+1, toexec.getDestrank()+1);
            char promto=toexec.getpromTo();
            if(side.equals("white")) {

                if(promto=='Q' || promto=='\0') {
                    dbut.setImageResource(R.drawable.white_queen);
                }
                else if(promto=='R') {
                    dbut.setImageResource(R.drawable.white_rook);
                }
                else if(promto=='B') {
                    dbut.setImageResource(R.drawable.white_bishop);
                }
                else if(promto=='N') {
                    dbut.setImageResource(R.drawable.white_knight);
                }
            }
            else
            {
                if(promto=='Q' || promto=='\0') {
                    dbut.setImageResource(R.drawable.black_queen);
                }
                else if(promto=='R') {
                    dbut.setImageResource(R.drawable.black_rook);
                }
                else if(promto=='B') {
                    dbut.setImageResource(R.drawable.black_bishop);
                }
                else if(promto=='N') {
                    dbut.setImageResource(R.drawable.black_knight);
                }

            }

            if(isbrown(orbut))
            {
                orbut.setImageResource(R.drawable.brown);
            }
            else {
                orbut.setImageResource(R.drawable.blank);
            }



        }
        else if(toexec.getiscas())
        {

            int bf=toexec.getOrigfile();
            int br=toexec.getOrigrank();


            int df=toexec.getDestfile();
            int dr=toexec.getDestrank();





            ImageButton orbut=getbutton(toexec.getOrigfile()+1, toexec.getOrigrank()+1);
            ImageButton dbut=getbutton(toexec.getDestfile()+1, toexec.getDestrank()+1);
            ImageButton rookorig=null;
            ImageButton rookdest=null;







            //King move
            ChessPiece.move(bf, br, dr, df, board);
            if(side.equals("white"))
            {
                if(isbrown(orbut))
                {
                    orbut.setImageResource(R.drawable.brown);
                }
                else {
                    orbut.setImageResource(R.drawable.blank);
                }
                dbut.setImageResource(R.drawable.white_king);
                this.wkingfile=df;
                this.wkingrank=dr;



            }
            else
            {
                if(isbrown(orbut))
                {
                    orbut.setImageResource(R.drawable.brown);
                }
                else {
                    orbut.setImageResource(R.drawable.blank);
                }
                dbut.setImageResource(R.drawable.black_king);
                this.bkingfile=df;
                this.bkingrank=dr;
            }

            //Rook move

            if(df==6 && dr==0)
            {
                ChessPiece.move(7, 0, 5, 0, board);
                rookorig=getbutton(8,1);
                rookdest=getbutton(6,1);

                if(side.equals("white"))
                {
                    if(isbrown(rookorig))
                    {
                        rookorig.setImageResource(R.drawable.brown);
                    }
                    else {
                        rookorig.setImageResource(R.drawable.blank);
                    }

                    rookdest.setImageResource(R.drawable.white_rook);
                }
                else
                {
                    if(isbrown(rookorig))
                    {
                        rookorig.setImageResource(R.drawable.brown);
                    }
                    else {
                        rookorig.setImageResource(R.drawable.blank);
                    }
                    rookdest.setImageResource(R.drawable.black_rook);
                }


            }
            else if(df==2 && dr==0)
            {
                ChessPiece.move(0, 0 , 3, 0, board);
                rookorig=getbutton(1,1);
                rookdest=getbutton(4,1);

                if(side.equals("white"))
                {
                    if(isbrown(rookorig))
                    {
                        rookorig.setImageResource(R.drawable.brown);
                    }
                    else {
                        rookorig.setImageResource(R.drawable.blank);
                    }
                    rookdest.setImageResource(R.drawable.white_rook);
                }
                else
                {
                    if(isbrown(rookorig))
                    {
                        rookorig.setImageResource(R.drawable.brown);
                    }
                    else {
                        rookorig.setImageResource(R.drawable.blank);
                    }
                    rookdest.setImageResource(R.drawable.black_rook);
                }

            }
            else if(df==6 && dr==7)
            {
                ChessPiece.move(7, 7, 5, 7, board);
                rookorig=getbutton(8,8);
                rookdest=getbutton(6,8);

                if(side.equals("white"))
                {
                    if(isbrown(rookorig))
                    {
                        rookorig.setImageResource(R.drawable.brown);
                    }
                    else {
                        rookorig.setImageResource(R.drawable.blank);
                    }
                    rookdest.setImageResource(R.drawable.white_rook);
                }
                else
                {
                    if(isbrown(rookorig))
                    {
                        rookorig.setImageResource(R.drawable.brown);
                    }
                    else {
                        rookorig.setImageResource(R.drawable.blank);
                    }
                    rookdest.setImageResource(R.drawable.black_rook);
                }
            }
            else if(df==2 && dr==7)
            {
                ChessPiece.move(0, 7, 3, 7, board);
                rookorig=getbutton(1,8);
                rookdest=getbutton(4,8);

                if(side.equals("white"))
                {
                    if(isbrown(rookorig))
                    {
                        rookorig.setImageResource(R.drawable.brown);
                    }
                    else {
                        rookorig.setImageResource(R.drawable.blank);
                    }
                    rookdest.setImageResource(R.drawable.white_rook);
                }
                else
                {
                    if(isbrown(rookorig))
                    {
                        rookorig.setImageResource(R.drawable.brown);
                    }
                    else {
                        rookorig.setImageResource(R.drawable.blank);
                    }
                    rookdest.setImageResource(R.drawable.black_rook);
                }
            }
        }
        else if(toexec.getisen())
        {
            int bf=toexec.getOrigfile();
            int br=toexec.getOrigrank();


            int df=toexec.getDestfile();
            int dr=toexec.getDestrank();

            Point enpasssq= toexec.getEnpasssq();

            ImageButton orbut=getbutton(toexec.getOrigfile()+1, toexec.getOrigrank()+1);
            ImageButton dbut=getbutton(toexec.getDestfile()+1, toexec.getDestrank()+1);
            ImageButton enemptybut=getbutton(enpasssq.getFile()+1, enpasssq.getRank()+1);


            ChessPiece.move(bf, br, df, dr, board);


            String opposite="white";

            if(side.equals(opposite))
            {
                opposite="black";
            }



            board[enpasssq.getFile()][enpasssq.getRank()]=null;


            if(isbrown(enemptybut))
            {
                enemptybut.setImageResource(R.drawable.brown);
            }
            else {
                enemptybut.setImageResource(R.drawable.blank);
            }

            if(isbrown(orbut))
            {
                orbut.setImageResource(R.drawable.brown);
            }
            else {
                orbut.setImageResource(R.drawable.blank);
            }


            if(side.equals("white"))
            {
                dbut.setImageResource(R.drawable.white_pawn);
            }
            else
            {
                dbut.setImageResource(R.drawable.black_pawn);
            }








        }
        //normal move
        else
        {
            int bf=toexec.getOrigfile();
            int br=toexec.getOrigrank();
            int df=toexec.getDestfile();
            int dr=toexec.getDestrank();
            ChessPiece.move(bf, br, df, dr, board);
            ImageButton orbut=getbutton(toexec.getOrigfile()+1, toexec.getOrigrank()+1);
            ImageButton dbut=getbutton(toexec.getDestfile()+1, toexec.getDestrank()+1);

            if(isbrown(orbut))
            {
                orbut.setImageResource(R.drawable.brown);
            }
            else {
                orbut.setImageResource(R.drawable.blank);
            }


            ChessPiece cp=board[df][dr];
            if(side.equals("white"))
            {
                if(cp instanceof Queen)
                {
                    dbut.setImageResource(R.drawable.white_queen);
                }
                else if(cp instanceof King)
                {
                    dbut.setImageResource(R.drawable.white_king);
                    this.wkingrank=dr;
                    this.wkingfile=df;
                }
                else if(cp instanceof Rook)
                {
                    dbut.setImageResource(R.drawable.white_rook);
                }
                else if(cp instanceof Bishop)
                {
                    dbut.setImageResource(R.drawable.white_bishop);
                }
                else if(cp instanceof Knight)
                {
                    dbut.setImageResource(R.drawable.white_knight);
                }
                else if(cp instanceof Pawn)
                {
                    dbut.setImageResource(R.drawable.white_pawn);
                }
            }
            else
            {
                if(cp instanceof Queen)
                {
                    dbut.setImageResource(R.drawable.black_queen);
                }
                else if(cp instanceof King)
                {
                    dbut.setImageResource(R.drawable.black_king);
                    this.bkingrank=dr;
                    this.bkingfile=df;
                }
                else if(cp instanceof Rook)
                {
                    dbut.setImageResource(R.drawable.black_rook);
                }
                else if(cp instanceof Bishop)
                {
                    dbut.setImageResource(R.drawable.black_bishop);
                }
                else if(cp instanceof Knight)
                {
                    dbut.setImageResource(R.drawable.black_knight);
                }
                else if(cp instanceof Pawn)
                {
                    dbut.setImageResource(R.drawable.black_pawn);
                }
            }








        }



        //if last move
        //deal with winner


        index++;
    }




    public ImageButton getbutton(int file, int rank)
    {
        if(file==1 && rank==8)
        {
            return (ImageButton)findViewById(R.id.button8_1);
        }
        else if(file==2 && rank==8)
        {
            return (ImageButton)findViewById(R.id.button8_2);
        }
        else if(file==3 && rank==8)
        {
            return (ImageButton)findViewById(R.id.button8_3);
        }
        else if(file==4 && rank==8)
        {
            return (ImageButton)findViewById(R.id.button8_4);
        }
        else if(file==5 && rank==8)
        {
            return (ImageButton)findViewById(R.id.button8_5);
        }
        else if(file==6 && rank==8)
        {
            return (ImageButton)findViewById(R.id.button8_6);
        }
        else if(file==7 && rank==8)
        {
            return (ImageButton)findViewById(R.id.button8_7);
        }
        else if(file==8 && rank==8)
        {
            return (ImageButton)findViewById(R.id.button8_8);
        }
        else if(file==1 && rank==7)
        {
            return (ImageButton)findViewById(R.id.button7_1);
        }
        else if(file==2 && rank==7)
        {
            return (ImageButton)findViewById(R.id.button7_2);
        }
        else if(file==3 && rank==7)
        {
            return (ImageButton)findViewById(R.id.button7_3);
        }
        else if(file==4 && rank==7)
        {
            return (ImageButton)findViewById(R.id.button7_4);
        }
        else if(file==5 && rank==7)
        {
            return (ImageButton)findViewById(R.id.button7_5);
        }
        else if(file==6 && rank==7)
        {
            return (ImageButton)findViewById(R.id.button7_6);
        }
        else if(file==7 && rank==7)
        {
            return (ImageButton)findViewById(R.id.button7_7);
        }
        else if(file==8 && rank==7)
        {
            return (ImageButton)findViewById(R.id.button7_8);
        }
        else if(file==1 && rank==6)
        {
            return (ImageButton)findViewById(R.id.button6_1);
        }
        else if(file==2 && rank==6)
        {
            return (ImageButton)findViewById(R.id.button6_2);
        }
        else if(file==3 && rank==6)
        {
            return (ImageButton)findViewById(R.id.button6_3);
        }
        else if(file==4 && rank==6)
        {
            return (ImageButton)findViewById(R.id.button6_4);
        }
        else if(file==5 && rank==6)
        {
            return (ImageButton)findViewById(R.id.button6_5);
        }
        else if(file==6 && rank==6)
        {
            return (ImageButton)findViewById(R.id.button6_6);
        }
        else if(file==7 && rank==6)
        {
            return (ImageButton)findViewById(R.id.button6_7);
        }
        else if(file==8 && rank==6)
        {
            return (ImageButton)findViewById(R.id.button6_8);
        }
        else if(file==1 && rank==5)
        {
            return (ImageButton)findViewById(R.id.button5_1);
        }
        else if(file==2 && rank==5)
        {
            return (ImageButton)findViewById(R.id.button5_2);
        }
        else if(file==3 && rank==5)
        {
            return (ImageButton)findViewById(R.id.button5_3);
        }
        else if(file==4 && rank==5)
        {
            return (ImageButton)findViewById(R.id.button5_4);
        }
        else if(file==5 && rank==5)
        {
            return (ImageButton)findViewById(R.id.button5_5);
        }
        else if(file==6 && rank==5)
        {
            return (ImageButton)findViewById(R.id.button5_6);
        }
        else if(file==7 && rank==5)
        {
            return (ImageButton)findViewById(R.id.button5_7);
        }
        else if(file==8 && rank==5)
        {
            return (ImageButton)findViewById(R.id.button5_8);
        }
        else if(file==1 && rank==4)
        {
            return (ImageButton)findViewById(R.id.button4_1);
        }
        else if(file==2 && rank==4)
        {
            return (ImageButton)findViewById(R.id.button4_2);
        }
        else if(file==3 && rank==4)
        {
            return (ImageButton)findViewById(R.id.button4_3);
        }
        else if(file==4 && rank==4)
        {
            return (ImageButton)findViewById(R.id.button4_4);
        }
        else if(file==5 && rank==4)
        {
            return (ImageButton)findViewById(R.id.button4_5);
        }
        else if(file==6 && rank==4)
        {
            return (ImageButton)findViewById(R.id.button4_6);
        }
        else if(file==7 && rank==4)
        {
            return (ImageButton)findViewById(R.id.button4_7);
        }
        else if(file==8 && rank==4)
        {
            return (ImageButton)findViewById(R.id.button4_8);
        }
        else if(file==1 && rank==3)
        {
            return (ImageButton)findViewById(R.id.button3_1);
        }
        else if(file==2 && rank==3)
        {
            return (ImageButton)findViewById(R.id.button3_2);
        }
        else if(file==3 && rank==3)
        {
            return (ImageButton)findViewById(R.id.button3_3);
        }
        else if(file==4 && rank==3)
        {
            return (ImageButton)findViewById(R.id.button3_4);
        }
        else if(file==5 && rank==3)
        {
            return (ImageButton)findViewById(R.id.button3_5);
        }
        else if(file==6 && rank==3)
        {
            return (ImageButton)findViewById(R.id.button3_6);
        }
        else if(file==7 && rank==3)
        {
            return (ImageButton)findViewById(R.id.button3_7);
        }
        else if(file==8 && rank==3)
        {
            return (ImageButton)findViewById(R.id.button3_8);
        }
        else if(file==1 && rank==2)
        {
            return (ImageButton)findViewById(R.id.button2_1);
        }
        else if(file==2 && rank==2)
        {
            return (ImageButton)findViewById(R.id.button2_2);
        }
        else if(file==3 && rank==2)
        {
            return (ImageButton)findViewById(R.id.button2_3);
        }
        else if(file==4 && rank==2)
        {
            return (ImageButton)findViewById(R.id.button2_4);
        }
        else if(file==5 && rank==2)
        {
            return (ImageButton)findViewById(R.id.button2_5);
        }
        else if(file==6 && rank==2)
        {
            return (ImageButton)findViewById(R.id.button2_6);
        }
        else if(file==7 && rank==2)
        {
            return (ImageButton)findViewById(R.id.button2_7);
        }
        else if(file==8 && rank==2)
        {
            return (ImageButton)findViewById(R.id.button2_8);
        }
        else if(file==1 && rank==1)
        {
            return (ImageButton)findViewById(R.id.button1_1);
        }
        else if(file==2 && rank==1)
        {
            return (ImageButton)findViewById(R.id.button1_2);
        }
        else if(file==3 && rank==1)
        {
            return (ImageButton)findViewById(R.id.button1_3);
        }
        else if(file==4 && rank==1)
        {
            return (ImageButton)findViewById(R.id.button1_4);
        }
        else if(file==5 && rank==1)
        {
            return (ImageButton)findViewById(R.id.button1_5);
        }
        else if(file==6 && rank==1)
        {
            return (ImageButton)findViewById(R.id.button1_6);
        }
        else if(file==7 && rank==1)
        {
            return (ImageButton)findViewById(R.id.button1_7);
        }
        else
        {
            return (ImageButton)findViewById(R.id.button1_8);
        }


    }

    public boolean isbrown(ImageButton ib)
    {
        int ID=ib.getId();
        if(ID==R.id.button8_1)
        {
            return false;
        }
        else if(ID==R.id.button8_2)
        {
            return true;
        }
        else if(ID==R.id.button8_3)
        {
            return false;
        }
        else if(ID==R.id.button8_4)
        {
            return true;
        }
        else if(ID==R.id.button8_5)
        {
            return false;
        }
        else if(ID==R.id.button8_6)
        {
            return true;
        }
        else if(ID==R.id.button8_7)
        {
            return false;
        }
        else if(ID==R.id.button8_8)
        {
            return true;
        }
        else if(ID==R.id.button7_1)
        {
            return true;
        }
        else if(ID==R.id.button7_2)
        {
            return false;
        }
        else if(ID==R.id.button7_3)
        {
            return true;
        }
        else if(ID==R.id.button7_4)
        {
            return false;
        }
        else if(ID==R.id.button7_5)
        {
            return true;
        }
        else if(ID==R.id.button7_6)
        {
            return false;
        }
        else if(ID==R.id.button7_7)
        {
            return true;
        }
        else if(ID==R.id.button7_8)
        {
            return false;
        }
        else if(ID==R.id.button6_1)
        {
            return false;
        }
        else if(ID==R.id.button6_2)
        {
            return true;
        }
        else if(ID==R.id.button6_3)
        {
            return false;
        }
        else if(ID==R.id.button6_4)
        {
            return true;
        }
        else if(ID==R.id.button6_5)
        {
            return false;
        }
        else if(ID==R.id.button6_6)
        {
            return true;
        }
        else if(ID==R.id.button6_7)
        {
            return false;
        }
        else if(ID==R.id.button6_8)
        {
            return true;
        }
        else if(ID==R.id.button5_1)
        {
            return true;
        }
        else if(ID==R.id.button5_2)
        {
            return false;
        }
        else if(ID==R.id.button5_3)
        {
            return true;
        }
        else if(ID==R.id.button5_4)
        {
            return false;
        }
        else if(ID==R.id.button5_5)
        {
            return true;
        }
        else if(ID==R.id.button5_6)
        {
            return false;
        }
        else if(ID==R.id.button5_7)
        {
            return true;
        }
        else if(ID==R.id.button5_8)
        {
            return false;
        }
        else if(ID==R.id.button4_1)
        {
            return false;
        }
        else if(ID==R.id.button4_2)
        {
            return true;
        }
        else if(ID==R.id.button4_3)
        {
            return false;
        }
        else if(ID==R.id.button4_4)
        {
            return true;
        }
        else if(ID==R.id.button4_5)
        {
            return false;
        }
        else if(ID==R.id.button4_6)
        {
            return true;
        }
        else if(ID==R.id.button4_7)
        {
            return false;
        }
        else if(ID==R.id.button4_8)
        {
            return true;
        }
        else if(ID==R.id.button3_1)
        {
            return true;
        }
        else if(ID==R.id.button3_2)
        {
            return false;
        }
        else if(ID==R.id.button3_3)
        {
            return true;
        }
        else if(ID==R.id.button3_4)
        {
            return false;
        }
        else if(ID==R.id.button3_5)
        {
            return true;
        }
        else if(ID==R.id.button3_6)
        {
            return false;
        }
        else if(ID==R.id.button3_7)
        {
            return true;
        }
        else if(ID==R.id.button3_8)
        {
            return false;
        }
        else if(ID==R.id.button2_1)
        {
            return false;
        }
        else if(ID==R.id.button2_2)
        {
            return true;
        }
        else if(ID==R.id.button2_3)
        {
            return false;
        }
        else if(ID==R.id.button2_4)
        {
            return true;
        }
        else if(ID==R.id.button2_5)
        {
            return false;
        }
        else if(ID==R.id.button2_6)
        {
            return true;
        }
        else if(ID==R.id.button2_7)
        {
            return false;
        }
        else if(ID==R.id.button2_8)
        {
            return true;
        }
        else if(ID==R.id.button1_1)
        {
            return true;
        }
        else if(ID==R.id.button1_2)
        {
            return false;
        }
        else if(ID==R.id.button1_3)
        {
            return true;
        }
        else if(ID==R.id.button1_4)
        {
            return false;
        }
        else if(ID==R.id.button1_5)
        {
            return true;
        }
        else if(ID==R.id.button1_6)
        {
            return false;
        }
        else if(ID==R.id.button1_7)
        {
            return true;
        }
        else
        {
            return false;
        }
    }




}