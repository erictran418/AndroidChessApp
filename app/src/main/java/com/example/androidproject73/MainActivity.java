package com.example.androidproject73;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.View;
import java.util.*;
import android.widget.*;

import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.View;
import java.util.*;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.content.Intent;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.View;
import java.util.*;
import android.widget.*;

public class MainActivity extends AppCompatActivity implements DrawDialog.Listener {

    private ArrayList<Game> al;
    private boolean exit=false;
    private ArrayList<Move> currentgamemoves;
    private boolean drawoffered=false;
    private boolean drawacc=false;
    private boolean gameover=false;
    private String currentside="white";
    private String currentgametitle="";
    private String actualwinner="white";
    private int fileorig=0;
    private int rankorig=0;
    private int filedest=0;
    private int rankdest=0;
    private int first=0;
    private ChessPiece[][] board;
    private String currentplayerside;
    private String winner;
    private int bkingfile;
    private int bkingrank;
    private int wkingfile;
    private int wkingrank;
    private Point enpasssq;
    private boolean isresign=false;
    char promotion='\0';
    private boolean isyes=false;
    private String title;

    private boolean undovalid=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int x=4;
        int y=5;
        playgame();
        currentgamemoves=new ArrayList<Move>();
        al=new ArrayList<Game>();
        //t();

    }






    public boolean ismate(String currentplayer)
    {
        //kings own mvmt

        int currentfile=0;
        int currentrank=0;
        String opp=null;

        if(currentplayer.equals("white"))
        {
            currentfile=wkingfile;
            currentrank=wkingrank;
            opp="black";
        }
        else
        {
            currentfile=bkingfile;
            currentrank=bkingrank;
            opp="white";
        }




        ArrayList<Point> al= new ArrayList<Point>();
        al.add(new Point(currentfile+1,currentrank));
        al.add(new Point(currentfile-1,currentrank));
        al.add(new Point(currentfile-1,currentrank-1));
        al.add(new Point(currentfile-1,currentrank+1));
        al.add(new Point(currentfile,currentrank+1));
        al.add(new Point(currentfile,currentrank-1));
        al.add(new Point(currentfile+1,currentrank+1));
        al.add(new Point(currentfile+1,currentrank-1));


        for(int i=0;i<al.size();i++)
        {
            int file=al.get(i).getFile();
            int rank=al.get(i).getRank();
            if(board[currentfile][currentrank].valid(file, rank, board))
            {
                return false;
            }

        }












        //try all moves on this side

        for(int file=0;file<8;file++)
        {
            for(int rank=0;rank<8;rank++)
            {

                if(board[file][rank]!=null && board[file][rank].getColor().equals(currentplayer))
                {
                    for(int testfile=0;testfile<8;testfile++)
                    {
                        for(int testrank=0; testrank<8;testrank++)
                        {

                            if(board[file][rank] instanceof Pawn)
                            {
                                //check if valid
                                if(!board[file][rank].valid(testfile, testrank, board))
                                {
                                    continue;
                                }


                                //move

                                boolean enpas=false;

                                if(enpasssq!=null)
                                {
                                    if(currentplayer.equals("white"))
                                    {

                                        if((testfile == file + 1 || testfile == file - 1) && testrank == rank + 1 && board[testfile][testrank] == null)
                                        {
                                            if(enpasssq!=null && enpasssq.getRank()==rank && (enpasssq.getFile()==file+1 || enpasssq.getFile()==file-1))
                                            {
                                                if(board[enpasssq.getFile()][enpasssq.getRank()]!=null && board[enpasssq.getFile()][enpasssq.getRank()] instanceof Pawn && board[enpasssq.getFile()][enpasssq.getRank()].getColor().equals("black"))
                                                {
                                                    enpas=true;
                                                }
                                            }
                                        }
                                    }
                                    else
                                    {
                                        if((testfile == file + 1 || testfile== file - 1) && testrank == rank - 1 && board[testfile][testrank] == null)
                                        {
                                            if(enpasssq!=null && enpasssq.getRank()==rank && (enpasssq.getFile()==file+1 || enpasssq.getFile()==file-1))
                                            {
                                                if(board[enpasssq.getFile()][enpasssq.getRank()]!=null && board[enpasssq.getFile()][enpasssq.getRank()] instanceof Pawn && board[enpasssq.getFile()][enpasssq.getRank()].getColor().equals("white"))
                                                {
                                                    enpas=true;
                                                }
                                            }
                                        }
                                    }
                                }





                                if(enpas)
                                {
                                    ChessPiece.move(file, rank, testfile, testrank, board);
                                    ChessPiece enepawn;



                                    enepawn=new Pawn(enpasssq.getFile(), enpasssq.getRank(), opp);

                                    board[enpasssq.getFile()][enpasssq.getRank()]=null;


                                    boolean ans=King.incheck(board,opp,currentfile, currentrank, currentfile,currentrank);

                                    //restore if doesnt fix problem
                                    if(ans==true)
                                    {
                                        board[file][rank]=board[testfile][testrank];
                                        board[file][rank].setFile(file);
                                        board[file][rank].setRank(rank);
                                        board[testfile][testrank]=null;
                                        board[enpasssq.getFile()][enpasssq.getRank()]=enepawn;

                                    }
                                    else
                                    {
                                        board[file][rank]=board[testfile][testrank];
                                        board[file][rank].setFile(file);
                                        board[file][rank].setRank(rank);
                                        board[testfile][testrank]=null;
                                        board[enpasssq.getFile()][enpasssq.getRank()]=enepawn;
                                        return false;
                                    }





                                }

                                else
                                {



                                    ChessPiece destinationval2;
                                    boolean ismoveddest=false;
                                    boolean ismovedorigin=false;

                                    if(board[testfile][testrank]==null)
                                    {
                                        destinationval2=null;
                                    }

                                    else if(board[testfile][testrank] instanceof Pawn)
                                    {
                                        destinationval2=new Pawn(testfile,testrank, board[testfile][testrank].getColor());
                                    }
                                    else if(board[testfile][testrank] instanceof Knight)
                                    {
                                        destinationval2=new Knight(testfile,testrank, board[testfile][testrank].getColor());
                                    }
                                    else if(board[testfile][testrank] instanceof Queen)
                                    {
                                        destinationval2=new Queen(testfile,testrank, board[testfile][testrank].getColor());
                                    }
                                    else if(board[testfile][testrank] instanceof Rook)
                                    {
                                        destinationval2=new Rook(testfile,testrank, board[testfile][testrank].getColor());
                                        ismoveddest= ((Rook)(board[testfile][testrank])).ismoved;
                                    }
                                    else if(board[testfile][testrank] instanceof Bishop)
                                    {
                                        destinationval2=new Bishop(testfile,testrank, board[testfile][testrank].getColor());
                                    }
                                    else
                                    {
                                        destinationval2=new King(testfile,testrank, board[testfile][testrank].getColor());
                                        ismoveddest= ((King)(board[testfile][testrank])).ismoved;
                                    }

                                    ChessPiece.move(file, rank, testfile, testrank, board);


                                    //check if no check anymore


                                    boolean ans=King.incheck(board,opp,currentfile, currentrank, currentfile,currentrank);

                                    //restore if doesnt fix problem
                                    if(ans==true)
                                    {
                                        board[file][rank]=board[testfile][testrank];
                                        board[file][rank].setFile(file);
                                        board[file][rank].setRank(rank);
                                        board[testfile][testrank]=destinationval2;

                                        if(board[file][rank] instanceof Rook)
                                        {
                                            Rook r=(Rook)board[file][rank];
                                            r.ismoved=ismovedorigin;
                                        }
                                        if(board[file][rank] instanceof King)
                                        {
                                            King k=(King)board[file][rank];
                                            k.ismoved=ismovedorigin;
                                        }

                                        if(board[testfile][testrank] !=null && board[testfile][testrank] instanceof Rook)
                                        {
                                            Rook r=(Rook)board[testfile][testrank];
                                            r.ismoved=ismoveddest;
                                        }
                                        if(board[testfile][testrank] !=null && board[testfile][testrank] instanceof King)
                                        {
                                            King k=(King)board[testfile][testrank];
                                            k.ismoved=ismoveddest;
                                        }
                                    }
                                    else
                                    {
                                        board[file][rank]=board[testfile][testrank];
                                        board[file][rank].setFile(file);
                                        board[file][rank].setRank(rank);
                                        board[testfile][testrank]=destinationval2;

                                        if(board[file][rank] instanceof Rook)
                                        {
                                            Rook r=(Rook)board[file][rank];
                                            r.ismoved=ismovedorigin;
                                        }
                                        if(board[file][rank] instanceof King)
                                        {
                                            King k=(King)board[file][rank];
                                            k.ismoved=ismovedorigin;
                                        }

                                        if(board[testfile][testrank] !=null && board[testfile][testrank] instanceof Rook)
                                        {
                                            Rook r=(Rook)board[testfile][testrank];
                                            r.ismoved=ismoveddest;
                                        }
                                        if(board[testfile][testrank] !=null && board[testfile][testrank] instanceof King)
                                        {
                                            King k=(King)board[testfile][testrank];
                                            k.ismoved=ismoveddest;
                                        }
                                        return false;
                                    }



                                }



                            }

                            else
                            {
                                if(!(board[file][rank] instanceof King) && board[file][rank].valid(testfile, testrank, board))
                                {
                                    //move

                                    ChessPiece destinationval2;
                                    boolean ismoveddest=false;
                                    boolean ismovedorigin=false;

                                    if(board[testfile][testrank]==null)
                                    {
                                        destinationval2=null;
                                    }

                                    else if(board[testfile][testrank] instanceof Pawn)
                                    {
                                        destinationval2=new Pawn(testfile,testrank, board[testfile][testrank].getColor());
                                    }
                                    else if(board[testfile][testrank] instanceof Knight)
                                    {
                                        destinationval2=new Knight(testfile,testrank, board[testfile][testrank].getColor());
                                    }
                                    else if(board[testfile][testrank] instanceof Queen)
                                    {
                                        destinationval2=new Queen(testfile,testrank, board[testfile][testrank].getColor());
                                    }
                                    else if(board[testfile][testrank] instanceof Rook)
                                    {
                                        destinationval2=new Rook(testfile,testrank, board[testfile][testrank].getColor());
                                        ismoveddest= ((Rook)(board[testfile][testrank])).ismoved;
                                    }
                                    else if(board[testfile][testrank] instanceof Bishop)
                                    {
                                        destinationval2=new Bishop(testfile,testrank, board[testfile][testrank].getColor());
                                    }
                                    else
                                    {
                                        destinationval2=new King(testfile,testrank, board[testfile][testrank].getColor());
                                        ismoveddest= ((King)(board[testfile][testrank])).ismoved;
                                    }

                                    ChessPiece.move(file, rank, testfile, testrank, board);

                                    //check if no check anymore

                                    boolean ans=King.incheck(board,opp,currentfile, currentrank, currentfile,currentrank);

                                    //restore if doesnt fix problem
                                    if(ans==true)
                                    {
                                        board[file][rank]=board[testfile][testrank];
                                        board[file][rank].setFile(file);
                                        board[file][rank].setRank(rank);
                                        board[testfile][testrank]=destinationval2;

                                        if(board[file][rank] instanceof Rook)
                                        {
                                            Rook r=(Rook)board[file][rank];
                                            r.ismoved=ismovedorigin;
                                        }
                                        if(board[file][rank] instanceof King)
                                        {
                                            King k=(King)board[file][rank];
                                            k.ismoved=ismovedorigin;
                                        }

                                        if(board[testfile][testrank] !=null && board[testfile][testrank] instanceof Rook)
                                        {
                                            Rook r=(Rook)board[testfile][testrank];
                                            r.ismoved=ismoveddest;
                                        }
                                        if(board[testfile][testrank] !=null && board[testfile][testrank] instanceof King)
                                        {
                                            King k=(King)board[testfile][testrank];
                                            k.ismoved=ismoveddest;
                                        }
                                    }
                                    else
                                    {
                                        board[file][rank]=board[testfile][testrank];
                                        board[file][rank].setFile(file);
                                        board[file][rank].setRank(rank);
                                        board[testfile][testrank]=destinationval2;

                                        if(board[file][rank] instanceof Rook)
                                        {
                                            Rook r=(Rook)board[file][rank];
                                            r.ismoved=ismovedorigin;
                                        }
                                        if(board[file][rank] instanceof King)
                                        {
                                            King k=(King)board[file][rank];
                                            k.ismoved=ismovedorigin;
                                        }

                                        if(board[testfile][testrank] !=null && board[testfile][testrank] instanceof Rook)
                                        {
                                            Rook r=(Rook)board[testfile][testrank];
                                            r.ismoved=ismoveddest;
                                        }
                                        if(board[testfile][testrank] !=null && board[testfile][testrank] instanceof King)
                                        {
                                            King k=(King)board[testfile][testrank];
                                            k.ismoved=ismoveddest;
                                        }
                                        return false;
                                    }




                                }
                            }



                        }
                    }
                }



            }
        }




        return true;


    }


    public void playgame()
    {
        //set up
        currentplayerside="White";
        TextView textView = (TextView) findViewById(R.id.turn);
        String s = currentplayerside + "'s Turn";
        textView.setText(s);

        board = new ChessPiece[8][8];
        winner=null;
        this.first=0;
        this.enpasssq=null;
        Pawn.cancelensq();
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
        enpasssq=null;


        //play loop
        this.currentplayerside = "White";
        char promotion='\0';



        //reset board for another game
        Context c2 = getApplicationContext();
        CharSequence t2 = "Resetting board for another game!";
        int d2 = Toast.LENGTH_SHORT;

        Toast tb2 = Toast.makeText(c2, t2, d2);
        tb2.show();




        ImageButton b18=(ImageButton)findViewById(R.id.button8_1);
        ImageButton b28=(ImageButton)findViewById(R.id.button8_2);
        ImageButton b38=(ImageButton)findViewById(R.id.button8_3);
        ImageButton b48=(ImageButton)findViewById(R.id.button8_4);
        ImageButton b58=(ImageButton)findViewById(R.id.button8_5);
        ImageButton b68=(ImageButton)findViewById(R.id.button8_6);
        ImageButton b78=(ImageButton)findViewById(R.id.button8_7);
        ImageButton b88=(ImageButton)findViewById(R.id.button8_8);

        b18.setImageResource(R.drawable.black_rook);
        b28.setImageResource(R.drawable.black_knight);
        b38.setImageResource(R.drawable.black_bishop);
        b48.setImageResource(R.drawable.black_queen);
        b58.setImageResource(R.drawable.black_king);
        b68.setImageResource(R.drawable.black_bishop);
        b78.setImageResource(R.drawable.black_knight);
        b88.setImageResource(R.drawable.black_rook);



        ImageButton b17=(ImageButton)findViewById(R.id.button7_1);
        ImageButton b27=(ImageButton)findViewById(R.id.button7_2);
        ImageButton b37=(ImageButton)findViewById(R.id.button7_3);
        ImageButton b47=(ImageButton)findViewById(R.id.button7_4);
        ImageButton b57=(ImageButton)findViewById(R.id.button7_5);
        ImageButton b67=(ImageButton)findViewById(R.id.button7_6);
        ImageButton b77=(ImageButton)findViewById(R.id.button7_7);
        ImageButton b87=(ImageButton)findViewById(R.id.button7_8);

        b17.setImageResource(R.drawable.black_pawn);
        b27.setImageResource(R.drawable.black_pawn);
        b37.setImageResource(R.drawable.black_pawn);
        b47.setImageResource(R.drawable.black_pawn);
        b57.setImageResource(R.drawable.black_pawn);
        b67.setImageResource(R.drawable.black_pawn);
        b77.setImageResource(R.drawable.black_pawn);
        b87.setImageResource(R.drawable.black_pawn);

        ImageButton b12=(ImageButton)findViewById(R.id.button2_1);
        ImageButton b22=(ImageButton)findViewById(R.id.button2_2);
        ImageButton b32=(ImageButton)findViewById(R.id.button2_3);
        ImageButton b42=(ImageButton)findViewById(R.id.button2_4);
        ImageButton b52=(ImageButton)findViewById(R.id.button2_5);
        ImageButton b62=(ImageButton)findViewById(R.id.button2_6);
        ImageButton b72=(ImageButton)findViewById(R.id.button2_7);
        ImageButton b82=(ImageButton)findViewById(R.id.button2_8);

        b12.setImageResource(R.drawable.white_pawn);
        b22.setImageResource(R.drawable.white_pawn);
        b32.setImageResource(R.drawable.white_pawn);
        b42.setImageResource(R.drawable.white_pawn);
        b52.setImageResource(R.drawable.white_pawn);
        b62.setImageResource(R.drawable.white_pawn);
        b72.setImageResource(R.drawable.white_pawn);
        b82.setImageResource(R.drawable.white_pawn);

        ImageButton b11=(ImageButton)findViewById(R.id.button1_1);
        ImageButton b21=(ImageButton)findViewById(R.id.button1_2);
        ImageButton b31=(ImageButton)findViewById(R.id.button1_3);
        ImageButton b41=(ImageButton)findViewById(R.id.button1_4);
        ImageButton b51=(ImageButton)findViewById(R.id.button1_5);
        ImageButton b61=(ImageButton)findViewById(R.id.button1_6);
        ImageButton b71=(ImageButton)findViewById(R.id.button1_7);
        ImageButton b81=(ImageButton)findViewById(R.id.button1_8);

        b11.setImageResource(R.drawable.white_rook);
        b21.setImageResource(R.drawable.white_knight);
        b31.setImageResource(R.drawable.white_bishop);
        b41.setImageResource(R.drawable.white_queen);
        b51.setImageResource(R.drawable.white_king);
        b61.setImageResource(R.drawable.white_bishop);
        b71.setImageResource(R.drawable.white_knight);
        b81.setImageResource(R.drawable.white_rook);


        String side = currentplayerside.toLowerCase();
        first = 0;
        gameover=false;




           /*




        //after game is done

        //collect user info
        endofGame();
        if(winner!=null)
        {
            if(isresign==false)
            {
                System.out.println("Checkmate");
            }
            System.out.print(winner + " wins.");
        }

        if(drawoffered==true)
        {
            System.out.print("Draw");
        }

        */


    }

    public void endofGame()
    {



        //start another dialog

        /*
        Context context = getApplicationContext();
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Game Save Choosing");

        builder1.setMessage("You have finished your game. Please click whether you want to save this game, and if so, give it a title");
        // Set up the input
        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder1.setView(input);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Save",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if(input.getText()==null)
                        {
                            Context c2 = getApplicationContext();
                            CharSequence t2 = "Must Enter A Title to Save Game!";
                            int d2 = Toast.LENGTH_SHORT;

                            Toast tb2 = Toast.makeText(c2, t2, d2);
                            tb2.show();
                            dialog.cancel();
                        }


                        String title = input.getText().toString();

                        if(title==null || title.isEmpty())
                        {
                            Context c2 = getApplicationContext();
                            CharSequence t2 = "Must Enter A Title to Save Game!";
                            int d2 = Toast.LENGTH_SHORT;

                            Toast tb2 = Toast.makeText(c2, t2, d2);
                            tb2.show();
                            dialog.cancel();
                        }





                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.MILLISECOND,0);

                        Game g=new Game(title, calendar,currentgamemoves,winner);
                        al.add(g);

                        Context c2 = getApplicationContext();
                        CharSequence t2 = "Game successfully saved!";
                        int d2 = Toast.LENGTH_SHORT;

                        Toast tb2 = Toast.makeText(c2, t2, d2);
                        tb2.show();




                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "Don't Save",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

         */

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        String win=null;

        if(winner==null)
        {
            win="Game is a Draw";
        }
        else
        {
            win=winner+" Wins";
        }






        alertDialogBuilder.setMessage(win+" Do you want to save this completed game? ");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        alertDialogBuilder.setCancelable(true);
        final ArrayList<Move> t = this.currentgamemoves;
        final String w=this.winner;
        final ArrayList<Game> tempg=this.al;
        alertDialogBuilder.setView(input);
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {


                                if(input.getText()==null)
                                {
                                    Context c2 = getApplicationContext();
                                    CharSequence t2 = "Must Enter A Title to Save Game!";
                                    int d2 = Toast.LENGTH_SHORT;

                                    Toast tb2 = Toast.makeText(c2, t2, d2);
                                    tb2.show();
                                    arg0.cancel();
                                    return;
                                }


                                String titles = input.getText().toString();

                                if(titles==null || titles.isEmpty())
                                {
                                    Context c2 = getApplicationContext();
                                    CharSequence t2 = "Must Enter A Title to Save Game!";
                                    int d2 = Toast.LENGTH_SHORT;

                                    Toast tb2 = Toast.makeText(c2, t2, d2);
                                    tb2.show();
                                    arg0.cancel();
                                    return;
                                }

                                ArrayList<Move> currentgamemoves2=t;
                                String winner=w;

                                Calendar calendar = Calendar.getInstance();
                                calendar.set(Calendar.MILLISECOND,0);

                                Game g=new Game(titles, calendar,currentgamemoves2,winner);
                                tempg.add(g);

                                Context c2 = getApplicationContext();
                                CharSequence t2 = "Game successfully saved!";
                                int d2 = Toast.LENGTH_SHORT;

                                Toast tb2 = Toast.makeText(c2, t2, d2);
                                tb2.show();







                                arg0.cancel();






                            }
                        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();













        //start another one
        currentgamemoves=new ArrayList<Move>();

        ImageButton b13=(ImageButton)findViewById(R.id.button3_1);
        ImageButton b23=(ImageButton)findViewById(R.id.button3_2);
        ImageButton b33=(ImageButton)findViewById(R.id.button3_3);
        ImageButton b43=(ImageButton)findViewById(R.id.button3_4);
        ImageButton b53=(ImageButton)findViewById(R.id.button3_5);
        ImageButton b63=(ImageButton)findViewById(R.id.button3_6);
        ImageButton b73=(ImageButton)findViewById(R.id.button3_7);
        ImageButton b83=(ImageButton)findViewById(R.id.button3_8);

        ImageButton b14=(ImageButton)findViewById(R.id.button4_1);
        ImageButton b24=(ImageButton)findViewById(R.id.button4_2);
        ImageButton b34=(ImageButton)findViewById(R.id.button4_3);
        ImageButton b44=(ImageButton)findViewById(R.id.button4_4);
        ImageButton b54=(ImageButton)findViewById(R.id.button4_5);
        ImageButton b64=(ImageButton)findViewById(R.id.button4_6);
        ImageButton b74=(ImageButton)findViewById(R.id.button4_7);
        ImageButton b84=(ImageButton)findViewById(R.id.button4_8);

        ImageButton b15=(ImageButton)findViewById(R.id.button5_1);
        ImageButton b25=(ImageButton)findViewById(R.id.button5_2);
        ImageButton b35=(ImageButton)findViewById(R.id.button5_3);
        ImageButton b45=(ImageButton)findViewById(R.id.button5_4);
        ImageButton b55=(ImageButton)findViewById(R.id.button5_5);
        ImageButton b65=(ImageButton)findViewById(R.id.button5_6);
        ImageButton b75=(ImageButton)findViewById(R.id.button5_7);
        ImageButton b85=(ImageButton)findViewById(R.id.button5_8);

        ImageButton b16=(ImageButton)findViewById(R.id.button6_1);
        ImageButton b26=(ImageButton)findViewById(R.id.button6_2);
        ImageButton b36=(ImageButton)findViewById(R.id.button6_3);
        ImageButton b46=(ImageButton)findViewById(R.id.button6_4);
        ImageButton b56=(ImageButton)findViewById(R.id.button6_5);
        ImageButton b66=(ImageButton)findViewById(R.id.button6_6);
        ImageButton b76=(ImageButton)findViewById(R.id.button6_7);
        ImageButton b86=(ImageButton)findViewById(R.id.button6_8);

        b13.setImageResource(R.drawable.brown);
         b23.setImageResource(R.drawable.blank);
         b33.setImageResource(R.drawable.brown);
         b43.setImageResource(R.drawable.blank);
         b53.setImageResource(R.drawable.brown);
         b63.setImageResource(R.drawable.blank);
         b73.setImageResource(R.drawable.brown);
         b83.setImageResource(R.drawable.blank);

         b14.setImageResource(R.drawable.blank);
         b24.setImageResource(R.drawable.brown);
         b34.setImageResource(R.drawable.blank);
         b44.setImageResource(R.drawable.brown);
         b54.setImageResource(R.drawable.blank);
         b64.setImageResource(R.drawable.brown);
        b74.setImageResource(R.drawable.blank);
        b84.setImageResource(R.drawable.brown);

         b15.setImageResource(R.drawable.brown);
         b25.setImageResource(R.drawable.blank);
         b35.setImageResource(R.drawable.brown);
         b45.setImageResource(R.drawable.blank);
         b55.setImageResource(R.drawable.brown);
        b65.setImageResource(R.drawable.blank);
         b75.setImageResource(R.drawable.brown);
        b85.setImageResource(R.drawable.blank);

         b16.setImageResource(R.drawable.blank);
        b26.setImageResource(R.drawable.brown);
         b36.setImageResource(R.drawable.blank);
         b46.setImageResource(R.drawable.brown);
         b56.setImageResource(R.drawable.blank);
         b66.setImageResource(R.drawable.brown);
         b76.setImageResource(R.drawable.blank);
         b86.setImageResource(R.drawable.brown);
         this.gameover=false;




        playgame();
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


    public void onClick(View view) {
        int ID=view.getId();
        int buttonfile=0;
        int buttonrank=0;
        if(ID==R.id.button8_1)
        {
             buttonfile=1;
             buttonrank=8;
        }
        else if(ID==R.id.button8_2)
        {
            buttonfile=2;
            buttonrank=8;
        }
        else if(ID==R.id.button8_3)
        {
            buttonfile=3;
            buttonrank=8;
        }
        else if(ID==R.id.button8_4)
        {
            buttonfile=4;
            buttonrank=8;
        }
        else if(ID==R.id.button8_5)
        {
            buttonfile=5;
            buttonrank=8;
        }
        else if(ID==R.id.button8_6)
        {
            buttonfile=6;
            buttonrank=8;
        }
        else if(ID==R.id.button8_7)
        {
            buttonfile=7;
            buttonrank=8;
        }
        else if(ID==R.id.button8_8)
        {
            buttonfile=8;
            buttonrank=8;
        }
        else if(ID==R.id.button7_1)
        {
            buttonfile=1;
            buttonrank=7;
        }
        else if(ID==R.id.button7_2)
        {
            buttonfile=2;
            buttonrank=7;
        }
        else if(ID==R.id.button7_3)
        {
            buttonfile=3;
            buttonrank=7;
        }
        else if(ID==R.id.button7_4)
        {
            buttonfile=4;
            buttonrank=7;
        }
        else if(ID==R.id.button7_5)
        {
            buttonfile=5;
            buttonrank=7;
        }
        else if(ID==R.id.button7_6)
        {
            buttonfile=6;
            buttonrank=7;
        }
        else if(ID==R.id.button7_7)
        {
            buttonfile=7;
            buttonrank=7;
        }
        else if(ID==R.id.button7_8)
        {
            buttonfile=8;
            buttonrank=7;
        }
        else if(ID==R.id.button6_1)
        {
            buttonfile=1;
            buttonrank=6;
        }
        else if(ID==R.id.button6_2)
        {
            buttonfile=2;
            buttonrank=6;
        }
        else if(ID==R.id.button6_3)
        {
            buttonfile=3;
            buttonrank=6;
        }
        else if(ID==R.id.button6_4)
        {
            buttonfile=4;
            buttonrank=6;
        }
        else if(ID==R.id.button6_5)
        {
            buttonfile=5;
            buttonrank=6;
        }
        else if(ID==R.id.button6_6)
        {
            buttonfile=6;
            buttonrank=6;
        }
        else if(ID==R.id.button6_7)
        {
            buttonfile=7;
            buttonrank=6;
        }
        else if(ID==R.id.button6_8)
        {
            buttonfile=8;
            buttonrank=6;
        }
        else if(ID==R.id.button5_1)
        {
            buttonfile=1;
            buttonrank=5;
        }
        else if(ID==R.id.button5_2)
        {
            buttonfile=2;
            buttonrank=5;
        }
        else if(ID==R.id.button5_3)
        {
            buttonfile=3;
            buttonrank=5;
        }
        else if(ID==R.id.button5_4)
        {
            buttonfile=4;
            buttonrank=5;
        }
        else if(ID==R.id.button5_5)
        {
            buttonfile=5;
            buttonrank=5;
        }
        else if(ID==R.id.button5_6)
        {
            buttonfile=6;
            buttonrank=5;
        }
        else if(ID==R.id.button5_7)
        {
            buttonfile=7;
            buttonrank=5;
        }
        else if(ID==R.id.button5_8)
        {
            buttonfile=8;
            buttonrank=5;
        }
        else if(ID==R.id.button4_1)
        {
            buttonfile=1;
            buttonrank=4;
        }
        else if(ID==R.id.button4_2)
        {
            buttonfile=2;
            buttonrank=4;
        }
        else if(ID==R.id.button4_3)
        {
            buttonfile=3;
            buttonrank=4;
        }
        else if(ID==R.id.button4_4)
        {
            buttonfile=4;
            buttonrank=4;
        }
        else if(ID==R.id.button4_5)
        {
            buttonfile=5;
            buttonrank=4;
        }
        else if(ID==R.id.button4_6)
        {
            buttonfile=6;
            buttonrank=4;
        }
        else if(ID==R.id.button4_7)
        {
            buttonfile=7;
            buttonrank=4;
        }
        else if(ID==R.id.button4_8)
        {
            buttonfile=8;
            buttonrank=4;
        }
        else if(ID==R.id.button3_1)
        {
            buttonfile=1;
            buttonrank=3;
        }
        else if(ID==R.id.button3_2)
        {
            buttonfile=2;
            buttonrank=3;
        }
        else if(ID==R.id.button3_3)
        {
            buttonfile=3;
            buttonrank=3;
        }
        else if(ID==R.id.button3_4)
        {
            buttonfile=4;
            buttonrank=3;
        }
        else if(ID==R.id.button3_5)
        {
            buttonfile=5;
            buttonrank=3;
        }
        else if(ID==R.id.button3_6)
        {
            buttonfile=6;
            buttonrank=3;
        }
        else if(ID==R.id.button3_7)
        {
            buttonfile=7;
            buttonrank=3;
        }
        else if(ID==R.id.button3_8)
        {
            buttonfile=8;
            buttonrank=3;
        }
        else if(ID==R.id.button2_1)
        {
            buttonfile=1;
            buttonrank=2;
        }
        else if(ID==R.id.button2_2)
        {
            buttonfile=2;
            buttonrank=2;
        }
        else if(ID==R.id.button2_3)
        {
            buttonfile=3;
            buttonrank=2;
        }
        else if(ID==R.id.button2_4)
        {
            buttonfile=4;
            buttonrank=2;
        }
        else if(ID==R.id.button2_5)
        {
            buttonfile=5;
            buttonrank=2;
        }
        else if(ID==R.id.button2_6)
        {
            buttonfile=6;
            buttonrank=2;
        }
        else if(ID==R.id.button2_7)
        {
            buttonfile=7;
            buttonrank=2;
        }
        else if(ID==R.id.button2_8)
        {
            buttonfile=8;
            buttonrank=2;
        }
        else if(ID==R.id.button1_1)
        {
            buttonfile=1;
            buttonrank=1;
        }
        else if(ID==R.id.button1_2)
        {
            buttonfile=2;
            buttonrank=1;
        }
        else if(ID==R.id.button1_3)
        {
            buttonfile=3;
            buttonrank=1;
        }
        else if(ID==R.id.button1_4)
        {
            buttonfile=4;
            buttonrank=1;
        }
        else if(ID==R.id.button1_5)
        {
            buttonfile=5;
            buttonrank=1;
        }
        else if(ID==R.id.button1_6)
        {
            buttonfile=6;
            buttonrank=1;
        }
        else if(ID==R.id.button1_7)
        {
            buttonfile=7;
            buttonrank=1;
        }
        else if(ID==R.id.button1_8)
        {
            buttonfile=8;
            buttonrank=1;
        }




        if(first==0)
        {
            this.fileorig=buttonfile-1;
            this.rankorig=buttonrank-1;
            first++;
        }
        else if(first==1)
        {
            this.filedest=buttonfile-1;
            this.rankdest=buttonrank-1;
            first++;
            makeMove();
        }
        else
        {
            Context context = getApplicationContext();
            CharSequence text = "Please refrain from clicking while your move selection is being processed!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }


    }

    public void makeMove()
    {
        int pieceFile=fileorig;
        int pieceRank=rankorig;
        int moveFile=filedest;
        int moveRank=rankdest;

        String side=currentplayerside.toLowerCase();
        promotion='\0';

        if(pieceFile<=-1 || pieceRank<=-1 || moveFile<=-1 || moveRank<=-1 || pieceRank>7)
        {
            Context c = getApplicationContext();
            CharSequence t = "That is an invalid move, please select a valid move!";
            int d = Toast.LENGTH_SHORT;

            Toast tb = Toast.makeText(c, t, d);
            tb.show();

            first=0;
            undovalid=true;
            //prompt user for 2 touches
            Context context = getApplicationContext();
            CharSequence text = "Please first touch the piece you want to move and then the square you want to move it to!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;

        }

        if(board[pieceFile][pieceRank]==null)
        {
            Context c = getApplicationContext();
            CharSequence t = "That is an invalid move, please select a valid move!";
            int d = Toast.LENGTH_SHORT;

            Toast tb = Toast.makeText(c, t, d);
            tb.show();

            first=0;
            undovalid=true;
            //prompt user for 2 touches
            Context context = getApplicationContext();
            CharSequence text = "Please first touch the piece you want to move and then the square you want to move it to!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();




            return;

        }


        if(board[pieceFile][pieceRank].getColor().equals(side)==false)
        {
            Context c = getApplicationContext();
            CharSequence t = "That is an invalid move, please select a valid move!";
            int d = Toast.LENGTH_SHORT;

            Toast tb = Toast.makeText(c, t, d);
            tb.show();


            first=0;
            undovalid=true;
            //prompt user for 2 touches
            Context context = getApplicationContext();
            CharSequence text = "Please first touch the piece you want to move and then the square you want to move it to!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();



            return;
        }






        ChessPiece pieceToMove = board[pieceFile][pieceRank];








        if (pieceToMove != null && pieceToMove.getColor().equals(currentplayerside.toLowerCase())) {
            if (!pieceToMove.valid(moveFile, moveRank, board)) {

                Context c = getApplicationContext();
                CharSequence t = "That is an invalid move, please select a valid move!";
                int d = Toast.LENGTH_SHORT;

                Toast tb = Toast.makeText(c, t, d);
                tb.show();
                this.first=0;
                undovalid=true;
                return;


            } else {

                Move m=new Move(board,side, pieceFile, pieceRank, moveFile, moveRank, enpasssq);
                //store old value

                ChessPiece destinationval;

                boolean ismoveddest=false;
                boolean ismovedorigin=false;

                if(board[moveFile][moveRank]==null)
                {
                    destinationval=null;
                }

                else if(board[moveFile][moveRank] instanceof Pawn)
                {
                    destinationval=new Pawn(moveFile,moveRank, board[moveFile][moveRank].getColor());
                }
                else if(board[moveFile][moveRank] instanceof Knight)
                {
                    destinationval=new Knight(moveFile,moveRank, board[moveFile][moveRank].getColor());
                }
                else if(board[moveFile][moveRank] instanceof Queen)
                {
                    destinationval=new Queen(moveFile,moveRank, board[moveFile][moveRank].getColor());
                }
                else if(board[moveFile][moveRank] instanceof Rook)
                {
                    destinationval=new Rook(moveFile,moveRank, board[moveFile][moveRank].getColor());

                    ismoveddest= ((Rook)(board[moveFile][moveRank])).ismoved;

                }
                else if(board[moveFile][moveRank] instanceof Bishop)
                {
                    destinationval=new Bishop(moveFile,moveRank, board[moveFile][moveRank].getColor());
                }
                else
                {
                    destinationval=new King(moveFile,moveRank, board[moveFile][moveRank].getColor());

                    ismoveddest= ((King)(board[moveFile][moveRank])).ismoved;

                }



                //origin store

                if(board[pieceFile][pieceRank]!=null && (board[pieceFile][pieceRank] instanceof King || board[pieceFile][pieceRank] instanceof Rook))
                {



                    if(board[pieceFile][pieceRank] instanceof King)
                    {
                        ismovedorigin=((King)(board[pieceFile][pieceRank])).ismoved;
                    }
                    else if(board[pieceFile][pieceRank] instanceof Rook)
                    {
                        ismovedorigin=((Rook)(board[pieceFile][pieceRank])).ismoved;
                    }
                }










                //update king's values

                if(board[pieceFile][pieceRank] instanceof King)
                {
                    if(currentplayerside.equals("White"))
                    {
                        wkingfile=moveFile;
                        wkingrank=moveRank;
                    }
                    else
                    {
                        bkingfile=moveFile;
                        bkingrank=moveRank;
                    }
                }



                //if valid castle




                boolean validcas=false;
                boolean validprom=false;
                boolean validenpas=false;


                if(board[pieceFile][pieceRank] instanceof King)
                {
                    King k=(King) board[pieceFile][pieceRank];

                    if(k.castlegood(board, pieceFile, pieceRank, moveFile, moveRank, side))
                    {
                        validcas=true;
                        m.setiscas();
                    }
                }

                if(board[pieceFile][pieceRank] instanceof Pawn)
                {
                    Pawn p= (Pawn) board[pieceFile][pieceRank];

                    if(p.ispromote(board, pieceFile, pieceRank, moveFile, moveRank, side))
                    {
                        validprom=true;
                        m.setisprom();
                    }
                }


                //validenpas

                if(side.equals("white"))
                {
                    if(board[pieceFile][pieceRank] instanceof Pawn)
                    {
                        if((moveFile == pieceFile + 1 || moveFile == pieceFile - 1) && moveRank == pieceRank + 1 && board[moveFile][moveRank] == null)
                        {
                            if(enpasssq!=null && enpasssq.getRank()==pieceRank && (enpasssq.getFile()==pieceFile+1 || enpasssq.getFile()==pieceFile-1))
                            {
                                if(board[enpasssq.getFile()][enpasssq.getRank()]!=null && board[enpasssq.getFile()][enpasssq.getRank()] instanceof Pawn && board[enpasssq.getFile()][enpasssq.getRank()].getColor().equals("black"))
                                {
                                    validenpas=true;
                                    m.setisen();
                                }
                            }
                        }
                    }
                }
                else
                {
                    if(board[pieceFile][pieceRank] instanceof Pawn)
                    {
                        if((moveFile == pieceFile + 1 || moveFile == pieceFile - 1) && moveRank == pieceRank - 1 && board[moveFile][moveRank] == null)
                        {
                            if(enpasssq!=null && enpasssq.getRank()==pieceRank && (enpasssq.getFile()==pieceFile+1 || enpasssq.getFile()==pieceFile-1))
                            {
                                if(board[enpasssq.getFile()][enpasssq.getRank()]!=null && board[enpasssq.getFile()][enpasssq.getRank()] instanceof Pawn && board[enpasssq.getFile()][enpasssq.getRank()].getColor().equals("white"))
                                {
                                    validenpas=true;
                                    m.setisen();
                                }
                            }
                        }

                    }
                }






                if(validenpas)
                {
                    ChessPiece.move(pieceFile, pieceRank, moveFile, moveRank, board);

                    //moving on screen
                    ImageButton origbut=getbutton(pieceFile+1,pieceRank+1);
                    ImageButton destbut=getbutton(moveFile+1,moveRank+1);
                    ImageButton ensq=getbutton(enpasssq.getFile()+1, enpasssq.getRank()+1);















                    ChessPiece enepawn;

                    String opp="white";

                    if(side.equals(opp))
                    {
                        opp="black";
                    }

                    enepawn=new Pawn(enpasssq.getFile(), enpasssq.getRank(), opp);

                    board[enpasssq.getFile()][enpasssq.getRank()]=null;

                    int kfile=wkingfile, krank=wkingrank;

                    if(side.equals("black"))
                    {
                        kfile=bkingfile;
                        krank=bkingrank;
                    }





                    boolean ans=King.incheck(board,opp,kfile, krank, kfile,krank);

                    //restore if doesnt fix problem
                    if(ans==true)
                    {
                        System.out.println("Illegal move, try again");
                        board[pieceFile][pieceRank]=board[moveFile][moveRank];
                        board[pieceFile][pieceRank].setFile(pieceFile);
                        board[pieceFile][pieceRank].setRank(pieceRank);
                        board[moveFile][moveRank]=null;
                        board[enpasssq.getFile()][enpasssq.getRank()]=enepawn;
                        Context c = getApplicationContext();
                        CharSequence t = "That is an invalid move, please select a valid move!";
                        int d = Toast.LENGTH_SHORT;

                        Toast tb = Toast.makeText(c, t, d);
                        tb.show();
                        return;
                    }


                    if(isbrown(ensq))
                    {
                        ensq.setImageResource(R.drawable.brown);
                    }
                    else {
                        ensq.setImageResource(R.drawable.blank);
                    }

                    if(isbrown(origbut))
                    {
                        origbut.setImageResource(R.drawable.brown);
                    }
                    else {
                        origbut.setImageResource(R.drawable.blank);
                    }

                    if(side.equals("white"))
                    {
                        destbut.setImageResource(R.drawable.white_pawn);
                    }
                    else{
                        destbut.setImageResource(R.drawable.black_pawn);
                    }





                }








                if(validprom)
                {
                    EditText simpleEditText = (EditText) findViewById(R.id.prom);
                    String editTextValue = simpleEditText.getText().toString();

                    if(editTextValue==null || editTextValue.isEmpty())
                    {
                        promotion='Q';
                    }

                    promotion=editTextValue.charAt(0);

                    if(promotion!='Q' && promotion!='N' && promotion!='R' && promotion!='B')
                    {
                        promotion='Q';
                    }




                    ChessPiece.move(pieceFile, pieceRank, moveFile, moveRank, board);
                    Pawn.promote(board, moveFile, moveRank, promotion, side);
                    boolean checkgood=false;
                    if(side.equals("white"))
                    {
                        checkgood=King.incheck(board, "black", wkingfile, wkingrank, wkingfile, wkingrank);
                    }
                    else
                    {
                        checkgood=King.incheck(board, "white", bkingfile, bkingrank, bkingfile, bkingrank);
                    }


                    if(checkgood==true)
                    {

                        Pawn.unpromote(board, pieceFile, pieceRank, side);
                        board[moveFile][moveRank]=destinationval;
                        Context c = getApplicationContext();
                        CharSequence t = "That is an invalid move, please select a valid move!";
                        int d = Toast.LENGTH_SHORT;

                        Toast tb = Toast.makeText(c, t, d);
                        tb.show();
                        this.first=0;

                        return;

                    }

                    ImageButton origbut=getbutton(pieceFile+1, pieceRank+1);
                    ImageButton destbut=getbutton(moveFile+1, moveRank+1);

                    if(isbrown(origbut))
                    {
                        origbut.setImageResource(R.drawable.brown);
                    }
                    else {
                        origbut.setImageResource(R.drawable.blank);
                    }

                    if(side.equals("white"))
                    {
                        if(promotion=='Q')
                        {
                            destbut.setImageResource(R.drawable.white_queen);
                            m.setpromTo('Q');
                        }
                        else if(promotion=='R')
                        {
                            destbut.setImageResource(R.drawable.white_rook);
                            m.setpromTo('R');
                        }
                        else if(promotion=='N')
                        {
                            destbut.setImageResource(R.drawable.white_knight);
                            m.setpromTo('N');
                        }
                        else if(promotion=='B')
                        {
                            destbut.setImageResource(R.drawable.white_bishop);
                            m.setpromTo('B');
                        }
                    }
                    else
                    {
                        if(promotion=='Q')
                        {
                            destbut.setImageResource(R.drawable.black_queen);
                            m.setpromTo('Q');
                        }
                        else if(promotion=='R')
                        {
                            destbut.setImageResource(R.drawable.black_rook);
                            m.setpromTo('R');
                        }
                        else if(promotion=='N')
                        {
                            destbut.setImageResource(R.drawable.black_knight);
                            m.setpromTo('N');
                        }
                        else if(promotion=='B')
                        {
                            destbut.setImageResource(R.drawable.black_bishop);
                            m.setpromTo('B');
                        }
                    }

                    m.setpromTo(promotion);






                }








                if(validcas)
                {
                    ImageButton origbutk=getbutton(pieceFile+1,pieceRank+1);
                    ImageButton destbutk=getbutton(moveFile+1,moveRank+1);
                    //King move
                    ChessPiece.move(pieceFile, pieceRank, moveFile, moveRank, board);


                    ImageButton origr=null;
                    ImageButton destr=null;






                    //Rook move

                    if(moveFile==6 && moveRank==0)
                    {
                        ChessPiece.move(7, 0, 5, 0, board);
                        origr=getbutton(7+1,0+1);
                        destr=getbutton(5+1,0+1);

                        if(isbrown(origr))
                        {
                            origr.setImageResource(R.drawable.brown);
                        }
                        else {
                            origr.setImageResource(R.drawable.blank);
                        }

                        if(isbrown(origbutk))
                        {
                            origbutk.setImageResource(R.drawable.brown);
                        }
                        else {
                            origbutk.setImageResource(R.drawable.blank);
                        }
                        destr.setImageResource(R.drawable.white_rook);
                        destbutk.setImageResource(R.drawable.white_king);





                    }
                    else if(moveFile==2 && moveRank==0)
                    {
                        ChessPiece.move(0, 0 , 3, 0, board);
                        origr=getbutton(0+1,0+1);
                        destr=getbutton(3+1,0+1);

                        if(isbrown(origr))
                        {
                            origr.setImageResource(R.drawable.brown);
                        }
                        else {
                            origr.setImageResource(R.drawable.blank);
                        }

                        if(isbrown(origbutk))
                        {
                            origbutk.setImageResource(R.drawable.brown);
                        }
                        else {
                            origbutk.setImageResource(R.drawable.blank);
                        }
                        destr.setImageResource(R.drawable.white_rook);
                        destbutk.setImageResource(R.drawable.white_king);
                    }
                    else if(moveFile==6 && moveRank==7)
                    {
                        ChessPiece.move(7, 7, 5, 7, board);
                        origr=getbutton(7+1,7+1);
                        destr=getbutton(5+1,7+1);

                        if(isbrown(origr))
                        {
                            origr.setImageResource(R.drawable.brown);
                        }
                        else {
                            origr.setImageResource(R.drawable.blank);
                        }

                        if(isbrown(origbutk))
                        {
                            origbutk.setImageResource(R.drawable.brown);
                        }
                        else {
                            origbutk.setImageResource(R.drawable.blank);
                        }
                        destr.setImageResource(R.drawable.black_rook);
                        destbutk.setImageResource(R.drawable.black_king);
                    }
                    else if(moveFile==2 && moveRank==7)
                    {
                        ChessPiece.move(0, 7, 3, 7, board);
                        origr=getbutton(0+1,7+1);
                        destr=getbutton(3+1,7+1);

                        if(isbrown(origr))
                        {
                            origr.setImageResource(R.drawable.brown);
                        }
                        else {
                            origr.setImageResource(R.drawable.blank);
                        }

                        if(isbrown(origbutk))
                        {
                            origbutk.setImageResource(R.drawable.brown);
                        }
                        else {
                            origbutk.setImageResource(R.drawable.blank);
                        }
                        destr.setImageResource(R.drawable.black_rook);
                        destbutk.setImageResource(R.drawable.black_king);
                    }
                }
                else
                {
                    if(validprom==false && validenpas==false)
                    {
                        ChessPiece.move(pieceFile, pieceRank, moveFile, moveRank, board);
                    }
                }






                if(validenpas==false && validprom==false && validcas==false)
                {


                    //check if the player's king is in check now (not allowed)

                    if(currentplayerside.equals("White"))
                    {
                        boolean currentcheck=King.incheck(board, "black", wkingfile, wkingrank, wkingfile,wkingrank);

                        if(currentcheck)
                        {
                            //undo



                            if(board[moveFile][moveRank] instanceof King)
                            {
                                wkingfile=pieceFile;
                                wkingrank=pieceRank;
                            }



                            board[pieceFile][pieceRank]=board[moveFile][moveRank];
                            board[pieceFile][pieceRank].setFile(pieceFile);
                            board[pieceFile][pieceRank].setRank(pieceRank);
                            board[moveFile][moveRank]=destinationval;

                            if(board[pieceFile][pieceRank] instanceof Rook)
                            {
                                Rook r=(Rook)board[pieceFile][pieceRank];
                                r.ismoved=ismovedorigin;
                            }
                            if(board[pieceFile][pieceRank] instanceof King)
                            {
                                King k=(King)board[pieceFile][pieceRank];
                                k.ismoved=ismovedorigin;
                            }

                            if(board[moveFile][moveRank] !=null && board[moveFile][moveRank] instanceof Rook)
                            {
                                Rook r=(Rook)board[moveFile][moveRank];
                                r.ismoved=ismoveddest;
                            }
                            if(board[moveFile][moveRank] !=null && board[moveFile][moveRank] instanceof King)
                            {
                                King k=(King)board[moveFile][moveRank];
                                k.ismoved=ismoveddest;
                            }

                            Context c = getApplicationContext();
                            CharSequence t = "That is an invalid move, please select a valid move!";
                            int d = Toast.LENGTH_SHORT;

                            Toast tb = Toast.makeText(c, t, d);
                            tb.show();


                            this.first=0;

                            return;


                        }
                        else {
                            ImageButton origbut = getbutton(pieceFile + 1, pieceRank + 1);
                            ImageButton destbut = getbutton(moveFile + 1, moveRank + 1);

                            if(isbrown(origbut))
                            {
                                origbut.setImageResource(R.drawable.brown);
                            }
                            else {
                                origbut.setImageResource(R.drawable.blank);
                            }

                            if (board[moveFile][moveRank] instanceof King) {
                                destbut.setImageResource(R.drawable.white_king);
                            } else if (board[moveFile][moveRank] instanceof Queen) {
                                destbut.setImageResource(R.drawable.white_queen);
                            } else if (board[moveFile][moveRank] instanceof Rook) {
                                destbut.setImageResource(R.drawable.white_rook);
                            } else if (board[moveFile][moveRank] instanceof Knight) {
                                destbut.setImageResource(R.drawable.white_knight);
                            } else if (board[moveFile][moveRank] instanceof Bishop) {
                                destbut.setImageResource(R.drawable.white_bishop);
                            } else if (board[moveFile][moveRank] instanceof Pawn) {
                                destbut.setImageResource(R.drawable.white_pawn);
                            }


                        }









                    }
                    else
                    {
                        boolean currentcheck=King.incheck(board, "white", bkingfile, bkingrank, bkingfile,bkingrank);

                        if(currentcheck)
                        {
                            //undo


                            if(board[moveFile][moveRank] instanceof King)
                            {
                                bkingfile=pieceFile;
                                bkingrank=pieceRank;
                            }

                            board[pieceFile][pieceRank]=board[moveFile][moveRank];
                            board[pieceFile][pieceRank].setFile(pieceFile);
                            board[pieceFile][pieceRank].setRank(pieceRank);
                            board[moveFile][moveRank]=destinationval;

                            if(board[pieceFile][pieceRank] instanceof Rook)
                            {
                                Rook r=(Rook)board[pieceFile][pieceRank];
                                r.ismoved=ismovedorigin;
                            }
                            if(board[pieceFile][pieceRank] instanceof King)
                            {
                                King k=(King)board[pieceFile][pieceRank];
                                k.ismoved=ismovedorigin;
                            }

                            if(board[moveFile][moveRank] !=null && board[moveFile][moveRank] instanceof Rook)
                            {
                                Rook r=(Rook)board[moveFile][moveRank];
                                r.ismoved=ismoveddest;
                            }
                            if(board[moveFile][moveRank] !=null && board[moveFile][moveRank] instanceof King)
                            {
                                King k=(King)board[moveFile][moveRank];
                                k.ismoved=ismoveddest;
                            }

                            Context c = getApplicationContext();
                            CharSequence t = "That is an invalid move, please select a valid move!";
                            int d = Toast.LENGTH_SHORT;

                            Toast tb = Toast.makeText(c, t, d);
                            tb.show();


                            this.first=0;

                            return;



                        }
                        else {
                            ImageButton origbut = getbutton(pieceFile + 1, pieceRank + 1);
                            ImageButton destbut = getbutton(moveFile + 1, moveRank + 1);

                            if(isbrown(origbut))
                            {
                                origbut.setImageResource(R.drawable.brown);
                            }
                            else {
                                origbut.setImageResource(R.drawable.blank);
                            }

                            if (board[moveFile][moveRank] instanceof King) {
                                destbut.setImageResource(R.drawable.black_king);
                            } else if (board[moveFile][moveRank] instanceof Queen) {
                                destbut.setImageResource(R.drawable.black_queen);
                            } else if (board[moveFile][moveRank] instanceof Rook) {
                                destbut.setImageResource(R.drawable.black_rook);
                            } else if (board[moveFile][moveRank] instanceof Knight) {
                                destbut.setImageResource(R.drawable.black_knight);
                            } else if (board[moveFile][moveRank] instanceof Bishop) {
                                destbut.setImageResource(R.drawable.black_bishop);
                            } else if (board[moveFile][moveRank] instanceof Pawn) {
                                destbut.setImageResource(R.drawable.black_pawn);
                            }


                        }
                    }

                }








                //check if other king is in check now

                boolean othercheck=false;

                if(currentplayerside.equals("White"))
                {
                    boolean currentcheck=King.incheck(board, "white", bkingfile, bkingrank, bkingfile,bkingrank);

                    if(currentcheck)
                    {
                        othercheck=true;



                    }
                }
                else
                {
                    boolean currentcheck=King.incheck(board, "black", wkingfile, wkingrank, wkingfile,wkingrank);

                    if(currentcheck)
                    {

                        othercheck=true;

                    }
                }


                //check for mate (for other player)

                boolean check=false;
                if(othercheck==true)
                {

                    String oppos="white";

                    if(side.equals("white"))
                    {
                        oppos="black";
                    }




                    boolean mate=ismate(oppos);

                    if(mate)
                    {
                        this.winner=currentplayerside;

                        gameover=true;

                        undovalid=true;
                        first=0;
                    }
                    else
                    {
                        check=true;

                    }



                }


                if(gameover==true)
                {
                    enpasssq=null;
                    Pawn.cancelensq();
                    currentgamemoves.add(m);
                    undovalid=true;
                    first=0;
                    endofGame();
                }

                else {
                    //update enpas

                    if (board[moveFile][moveRank] != null && board[moveFile][moveRank] instanceof Pawn) {
                        if (moveFile == pieceFile && moveRank == pieceRank + 2 && board[moveFile][moveRank].getColor().equals("white")) {
                            enpasssq = new Point(moveFile, moveRank);
                            Pawn.setensq(moveFile, moveRank);
                        } else if (moveFile == pieceFile && moveRank == pieceRank - 2 && board[moveFile][moveRank].getColor().equals("black")) {
                            enpasssq = new Point(moveFile, moveRank);
                            Pawn.setensq(moveFile, moveRank);
                        } else {
                            enpasssq = null;
                            Pawn.cancelensq();
                        }
                    } else {
                        enpasssq = null;
                        Pawn.cancelensq();
                    }


                    currentplayerside = currentplayerside.equals("White") ? "Black" : "White";
                    side = currentplayerside.toLowerCase();
                    TextView textView = (TextView) findViewById(R.id.turn);
                    String s = currentplayerside + "'s Turn";
                    textView.setText(s);

                    if (check) {
                        Context c = getApplicationContext();
                        CharSequence t = "It is " + currentplayerside + " 's move! Check!";
                        int d = Toast.LENGTH_SHORT;

                        Toast tb = Toast.makeText(c, t, d);
                        tb.show();
                    } else {
                        Context c = getApplicationContext();
                        CharSequence t = "It is " + currentplayerside + " 's move!";
                        int d = Toast.LENGTH_SHORT;

                        Toast tb = Toast.makeText(c, t, d);
                        tb.show();
                    }

                    currentgamemoves.add(m);
                    undovalid = true;
                    first = 0;
                }

            }
        }








        else {

            Context c = getApplicationContext();
            CharSequence t = "That is an invalid move, please select a valid move!";
            int d = Toast.LENGTH_SHORT;

            Toast tb = Toast.makeText(c, t, d);
            tb.show();
        }




        first=0;
        //prompt user for 2 touches
        Context context = getApplicationContext();
        CharSequence text = "Please first touch the piece you want to move and then the square you want to move it to!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }










    public void DrawOffer(View view) {

        this.winner=null;
        gameover=true;
        endofGame();





    }

    @Override
    public void onYesClicked(){
       drawacc=true;
       endofGame();

    }





    public void Resign(View view) {
        gameover=true;
        this.winner="white";
        if(currentplayerside.equals("white") || currentplayerside.equals("White"))
        {
            this.winner="black";
        }
        else
        {
            this.winner="white";
        }

        if(currentgamemoves.size()==0)
        {
            winner="black";
        }


        endofGame();

    }













    public void Undo(View view) {

       int movenum=currentgamemoves.size();

       if(currentgamemoves.size()<=0)
       {
           Context context = getApplicationContext();
           CharSequence text = "No Moves Have Been Made in This Game to Undo!";
           int duration = Toast.LENGTH_LONG;

           Toast toast = Toast.makeText(context, text, duration);
           toast.show();
           return;
       }
       else if(undovalid==false)
       {
           Context context = getApplicationContext();
           CharSequence text = "You Can Only Undo the Last Move!";
           int duration = Toast.LENGTH_LONG;

           Toast toast = Toast.makeText(context, text, duration);
           toast.show();
           return;
       }
       else
       {
           //restore board
           undovalid=false;
           Move m=currentgamemoves.get(currentgamemoves.size()-1);
           int begfile=m.getOrigfile();
           int begrank=m.getOrigrank();
           int endfile=m.getDestfile();
           int endrank=m.getDestrank();

           String pieceorig=m.getpOrig();
           String piecedest=m.getpDest();
           currentplayerside = currentplayerside.equals("White") ? "Black" : "White";
           String side=currentplayerside.toLowerCase();

           String color=side;
           ImageButton origbut=getbutton(begfile+1, begrank+1);
           ImageButton destbut=getbutton(endfile+1, endrank+1);

           //promotion
           if(m.getisprom())
           {
               Pawn.unpromote(board,begfile,begrank,side);
               board[endfile][endrank]=null;

               if(side.equals("white"))
               {
                   origbut.setImageResource(R.drawable.white_pawn);
               }
               else
               {
                   origbut.setImageResource(R.drawable.black_pawn);
               }
               //dest


               if(piecedest==null)
               {
                   board[endfile][endrank]=null;
                   if(isbrown(destbut))
                   {
                       destbut.setImageResource(R.drawable.brown);
                   }
                   else {
                       destbut.setImageResource(R.drawable.blank);
                   }


               }
               else if(piecedest.equals("bQ"))
               {
                   board[endfile][endrank]=new Queen(endfile, endrank, "black");
                   destbut.setImageResource(R.drawable.black_queen);
               }
               else if(piecedest.equals("bK"))
               {
                   King k=new King(endfile, endrank, "black");
                   k.ismoved=m.getismoveddest();
                   board[endfile][endrank]=k;
                   destbut.setImageResource(R.drawable.black_king);
               }
               else if(piecedest.equals("bR"))
               {
                   Rook r=new Rook(endfile, endrank, "black");
                   r.ismoved=m.getismoveddest();
                   board[endfile][endrank] =r;
                   destbut.setImageResource(R.drawable.black_rook);
               }
               else if(piecedest.equals("bN"))
               {
                   board[endfile][endrank]=new Knight(endfile, endrank, "black");
                   destbut.setImageResource(R.drawable.black_knight);
               }
               else if(piecedest.equals("bB"))
               {
                   board[endfile][endrank]=new Bishop(endfile, endrank, "black");
                   destbut.setImageResource(R.drawable.black_bishop);
               }
               else if(piecedest.equals("bP"))
               {
                   board[endfile][endrank]=new Pawn(endfile, endrank, "black");
                   destbut.setImageResource(R.drawable.black_pawn);
               }
               else if(piecedest.equals("wQ"))
               {
                   board[endfile][endrank]=new Queen(endfile, endrank, "white");
                   destbut.setImageResource(R.drawable.white_queen);
               }
               else if(piecedest.equals("wK"))
               {
                   King k=new King(endfile, endrank, "white");
                   k.ismoved=m.getismoveddest();
                   board[endfile][endrank]=k;
                   destbut.setImageResource(R.drawable.white_king);
               }
               else if(piecedest.equals("wR"))
               {
                   Rook r=new Rook(endfile, endrank, "white");
                   r.ismoved=m.getismoveddest();
                   board[endfile][endrank] =r;
                   destbut.setImageResource(R.drawable.white_rook);
               }
               else if(piecedest.equals("wN"))
               {
                   board[endfile][endrank]=new Knight(endfile, endrank, "white");
                   destbut.setImageResource(R.drawable.white_knight);
               }
               else if(piecedest.equals("wB"))
               {
                   board[endfile][endrank]=new Bishop(endfile, endrank, "white");
                   destbut.setImageResource(R.drawable.white_bishop);
               }
               else if(piecedest.equals("wP"))
               {
                   board[endfile][endrank]=new Pawn(endfile, endrank, "white");
                   destbut.setImageResource(R.drawable.white_pawn);
               }

               //remove
               currentgamemoves.remove(currentgamemoves.size()-1);
               this.first=0;

               //currentplayerside = currentplayerside.equals("White") ? "Black" : "White";
               side=currentplayerside.toLowerCase();
               TextView textView = (TextView) findViewById(R.id.turn);
               String s=currentplayerside+"'s Turn";
               textView.setText(s);


               String opp2="white";

               if(side.equals(opp2))
               {
                   opp2="black";
               }









               int kfile=0;
               int krank=0;

               if(side.equals("white"))
               {
                   kfile=wkingfile;
                   krank=wkingrank;
               }
               else
               {
                   kfile=bkingfile;
                   krank=bkingrank;
               }





               if(King.incheck(board, opp2, kfile, krank, kfile, krank))
               {

                   Context c2 = getApplicationContext();
                   CharSequence t2 = "Your King is In Check!";
                   int d2 = Toast.LENGTH_SHORT;

                   Toast tb2 = Toast.makeText(c2, t2, d2);
                   tb2.show();


               }






               return;


           }


           //enpass
           if(m.getisen())
           {
               Point eqattime=m.getEnpasssq();

               String opp="white";

               if(side.equals("white"))
               {
                   opp="black";
               }


               ChessPiece enepawn=new Pawn(eqattime.getFile(), eqattime.getRank(), opp);
               board[begfile][begrank]=board[endfile][endrank];
               board[begfile][begrank].setFile(begfile);
               board[begfile][begrank].setRank(begrank);
               board[endfile][endrank]=null;
               board[eqattime.getFile()][eqattime.getRank()]=enepawn;

               //restore img
               ImageButton enbut=getbutton(eqattime.getFile()+1, eqattime.getRank()+1);

               if(side.equals("white"))
               {
                   enbut.setImageResource(R.drawable.black_pawn);
                   origbut.setImageResource(R.drawable.white_pawn);

               }
               else
               {
                   enbut.setImageResource(R.drawable.white_pawn);
                   origbut.setImageResource(R.drawable.black_pawn);
               }

               if(isbrown(destbut))
               {
                   destbut.setImageResource(R.drawable.brown);
               }
               else {
                   destbut.setImageResource(R.drawable.blank);
               }



               enpasssq = new Point(eqattime.getFile(),eqattime.getRank());
               Pawn.setensq(eqattime.getFile(), eqattime.getRank());



               this.first=0;
               currentgamemoves.remove(currentgamemoves.size()-1);

               //currentplayerside = currentplayerside.equals("White") ? "Black" : "White";
               side=currentplayerside.toLowerCase();
               TextView textView = (TextView) findViewById(R.id.turn);
               String s=currentplayerside+"'s Turn";
               textView.setText(s);


               String opp2="white";

               if(side.equals(opp2))
               {
                   opp2="black";
               }







               int kfile=0;
               int krank=0;

               if(side.equals("white"))
               {
                   kfile=wkingfile;
                   krank=wkingrank;
               }
               else
               {
                   kfile=bkingfile;
                   krank=bkingrank;
               }





               if(King.incheck(board, opp2, kfile, krank, kfile, krank))
               {

                   Context c2 = getApplicationContext();
                   CharSequence t2 = "Your King is In Check!";
                   int d2 = Toast.LENGTH_SHORT;

                   Toast tb2 = Toast.makeText(c2, t2, d2);
                   tb2.show();


               }







               return;
           }


           //castling
           if(m.getiscas())
           {
               int rfile=0;
               int rrank=0;
               //4 cases
               if(endfile==6 && endrank==0 && begfile==4 && begrank==0 && side.equals("white"))
               {
                    King k=new King(begfile,begrank,side);
                    k.ismoved=false;
                    board[begfile][begrank]=k;
                    board[endfile][endrank]=null;
                    this.wkingfile=begfile;
                    this.wkingrank=begrank;
                    origbut.setImageResource(R.drawable.white_king);


                   if(isbrown(destbut))
                   {
                       destbut.setImageResource(R.drawable.brown);
                   }
                   else {
                       destbut.setImageResource(R.drawable.blank);
                   }


                    Rook r=new Rook(7,0,side);
                    r.ismoved=false;
                    board[7][0]=r;
                    board[5][0]=null;
                    ImageButton rbut=getbutton(7+1, 0+1);
                    ImageButton rbutor=getbutton(5+1,0+1);
                    rbut.setImageResource(R.drawable.white_rook);

                   if(isbrown(rbutor))
                   {
                       rbutor.setImageResource(R.drawable.brown);
                   }
                   else {
                       rbutor.setImageResource(R.drawable.blank);
                   }






               }
               else if(endfile==2 && endrank==0 && begfile==4 && begrank==0 && side.equals("white"))
               {
                   King k=new King(begfile,begrank,side);
                   k.ismoved=false;
                   board[begfile][begrank]=k;
                   board[endfile][endrank]=null;
                   this.wkingfile=begfile;
                   this.wkingrank=begrank;
                   origbut.setImageResource(R.drawable.white_king);
                   if(isbrown(destbut))
                   {
                       destbut.setImageResource(R.drawable.brown);
                   }
                   else {
                       destbut.setImageResource(R.drawable.blank);
                   }

                   Rook r=new Rook(0,0,side);
                   r.ismoved=false;
                   board[0][0]=r;
                   board[3][0]=null;
                   ImageButton rbut=getbutton(0+1, 0+1);
                   ImageButton rbutor=getbutton(3+1,0+1);
                   rbut.setImageResource(R.drawable.white_rook);
                   if(isbrown(rbutor))
                   {
                       rbutor.setImageResource(R.drawable.brown);
                   }
                   else {
                       rbutor.setImageResource(R.drawable.blank);
                   }
               }
               else if(endfile==6 && endrank==7 && begfile==4 && begrank==7 && side.equals("black"))
               {
                   King k=new King(begfile,begrank,side);
                   k.ismoved=false;
                   board[begfile][begrank]=k;
                   board[endfile][endrank]=null;
                   this.bkingfile=begfile;
                   this.bkingrank=begrank;
                   origbut.setImageResource(R.drawable.black_king);
                   if(isbrown(destbut))
                   {
                       destbut.setImageResource(R.drawable.brown);
                   }
                   else {
                       destbut.setImageResource(R.drawable.blank);
                   }

                   Rook r=new Rook(7,0,side);
                   r.ismoved=false;
                   board[7][7]=r;
                   board[5][7]=null;
                   ImageButton rbut=getbutton(7+1, 7+1);
                   ImageButton rbutor=getbutton(5+1,7+1);
                   rbut.setImageResource(R.drawable.black_rook);
                   if(isbrown(rbutor))
                   {
                       rbutor.setImageResource(R.drawable.brown);
                   }
                   else {
                       rbutor.setImageResource(R.drawable.blank);
                   }
               }
               else if(endfile==2 && endrank==7 && begfile==4 && begrank==7 && side.equals("black"))
               {
                   King k=new King(begfile,begrank,side);
                   k.ismoved=false;
                   board[begfile][begrank]=k;
                   board[endfile][endrank]=null;
                   this.bkingfile=begfile;
                   this.bkingrank=begrank;
                   origbut.setImageResource(R.drawable.black_king);
                   if(isbrown(destbut))
                   {
                       destbut.setImageResource(R.drawable.brown);
                   }
                   else {
                       destbut.setImageResource(R.drawable.blank);
                   }

                   Rook r=new Rook(0,7,side);
                   r.ismoved=false;
                   board[0][7]=r;
                   board[3][7]=null;
                   ImageButton rbut=getbutton(0+1, 7+1);
                   ImageButton rbutor=getbutton(3+1,7+1);
                   rbut.setImageResource(R.drawable.black_rook);
                   if(isbrown(rbutor))
                   {
                       rbutor.setImageResource(R.drawable.brown);
                   }
                   else {
                       rbutor.setImageResource(R.drawable.blank);
                   }
               }




               this.first=0;
               currentgamemoves.remove(currentgamemoves.size()-1);

               //currentplayerside = currentplayerside.equals("White") ? "Black" : "White";
               side=currentplayerside.toLowerCase();
               TextView textView = (TextView) findViewById(R.id.turn);
               String s=currentplayerside+"'s Turn";
               textView.setText(s);


               String opp2="white";

               if(side.equals(opp2))
               {
                   opp2="black";
               }








               int kfile=0;
               int krank=0;

               if(side.equals("white"))
               {
                   kfile=wkingfile;
                   krank=wkingrank;
               }
               else
               {
                   kfile=bkingfile;
                   krank=bkingrank;
               }





               if(King.incheck(board, opp2, kfile, krank, kfile, krank))
               {

                   Context c2 = getApplicationContext();
                   CharSequence t2 = "Your King is In Check!";
                   int d2 = Toast.LENGTH_SHORT;

                   Toast tb2 = Toast.makeText(c2, t2, d2);
                   tb2.show();


               }









               return;
           }




           //orig
           if(pieceorig==null)
           {
                board[begfile][begrank]=null;
                if(isbrown(origbut))
                {
                    origbut.setImageResource(R.drawable.brown);
                }
                else {
                    origbut.setImageResource(R.drawable.blank);
                }

           }
           else if(pieceorig.equals("bQ"))
           {
               board[begfile][begrank]=new Queen(begfile, begrank, "black");
               origbut.setImageResource(R.drawable.black_queen);
           }
           else if(pieceorig.equals("bK"))
           {
               King k=new King(begfile, begrank, "black");
               k.ismoved=m.getismovedorigin();
               board[begfile][begrank]=k;
               this.bkingfile=begfile;
               this.bkingrank=begrank;
               origbut.setImageResource(R.drawable.black_king);
           }
           else if(pieceorig.equals("bR"))
           {
               Rook r=new Rook(begfile, begrank, "black");
               r.ismoved=m.getismovedorigin();
               board[begfile][begrank] =r;
               origbut.setImageResource(R.drawable.black_rook);
           }
           else if(pieceorig.equals("bN"))
           {
               board[begfile][begrank]=new Knight(begfile, begrank, "black");
               origbut.setImageResource(R.drawable.black_knight);
           }
           else if(pieceorig.equals("bB"))
           {
               board[begfile][begrank]=new Bishop(begfile, begrank, "black");
               origbut.setImageResource(R.drawable.black_bishop);
           }
           else if(pieceorig.equals("bP"))
           {
               board[begfile][begrank]=new Pawn(begfile, begrank, "black");
               origbut.setImageResource(R.drawable.black_pawn);
           }
           else if(pieceorig.equals("wQ"))
           {
               board[begfile][begrank]=new Queen(begfile, begrank, "white");
               origbut.setImageResource(R.drawable.white_queen);
           }
           else if(pieceorig.equals("wK"))
           {
               King k=new King(begfile, begrank, "white");
               k.ismoved=m.getismovedorigin();
               board[begfile][begrank]=k;
               this.wkingfile=begfile;
               this.wkingrank=begrank;
               origbut.setImageResource(R.drawable.white_king);
           }
           else if(pieceorig.equals("wR"))
           {
               Rook r=new Rook(begfile, begrank, "white");
               r.ismoved=m.getismovedorigin();
               board[begfile][begrank] =r;
               origbut.setImageResource(R.drawable.white_rook);
           }
           else if(pieceorig.equals("wN"))
           {
               board[begfile][begrank]=new Knight(begfile, begrank, "white");
               origbut.setImageResource(R.drawable.white_knight);
           }
           else if(pieceorig.equals("wB"))
           {
               board[begfile][begrank]=new Bishop(begfile, begrank, "white");
               origbut.setImageResource(R.drawable.white_bishop);
           }
           else if(pieceorig.equals("wP"))
           {
               board[begfile][begrank]=new Pawn(begfile, begrank, "white");
               origbut.setImageResource(R.drawable.white_pawn);
           }



           //dest


           if(piecedest==null)
           {
               board[endfile][endrank]=null;
               if(isbrown(destbut))
               {
                   destbut.setImageResource(R.drawable.brown);
               }
               else {
                   destbut.setImageResource(R.drawable.blank);
               }

           }
           else if(piecedest.equals("bQ"))
           {
               board[endfile][endrank]=new Queen(endfile, endrank, "black");
               destbut.setImageResource(R.drawable.black_queen);
           }
           else if(piecedest.equals("bK"))
           {
               King k=new King(endfile, endrank, "black");
               k.ismoved=m.getismoveddest();
               board[endfile][endrank]=k;
               this.bkingfile=endfile;
               this.bkingrank=endrank;
               destbut.setImageResource(R.drawable.black_king);
           }
           else if(piecedest.equals("bR"))
           {
               Rook r=new Rook(endfile, endrank, "black");
               r.ismoved=m.getismoveddest();
               board[endfile][endrank] =r;
               destbut.setImageResource(R.drawable.black_rook);
           }
           else if(piecedest.equals("bN"))
           {
               board[endfile][endrank]=new Knight(endfile, endrank, "black");
               destbut.setImageResource(R.drawable.black_knight);
           }
           else if(piecedest.equals("bB"))
           {
               board[endfile][endrank]=new Bishop(endfile, endrank, "black");
               destbut.setImageResource(R.drawable.black_bishop);
           }
           else if(piecedest.equals("bP"))
           {
               board[endfile][endrank]=new Pawn(endfile, endrank, "black");
               destbut.setImageResource(R.drawable.black_pawn);
           }
           else if(piecedest.equals("wQ"))
           {
               board[endfile][endrank]=new Queen(endfile, endrank, "white");
               destbut.setImageResource(R.drawable.white_queen);
           }
           else if(piecedest.equals("wK"))
           {
               King k=new King(endfile, endrank, "white");
               k.ismoved=m.getismoveddest();
               board[endfile][endrank]=k;
               this.wkingfile=endfile;
               this.wkingrank=endrank;
               destbut.setImageResource(R.drawable.white_king);
           }
           else if(piecedest.equals("wR"))
           {
               Rook r=new Rook(endfile, endrank, "white");
               r.ismoved=m.getismoveddest();
               board[endfile][endrank] =r;
               destbut.setImageResource(R.drawable.white_rook);
           }
           else if(piecedest.equals("wN"))
           {
               board[endfile][endrank]=new Knight(endfile, endrank, "white");
               destbut.setImageResource(R.drawable.white_knight);
           }
           else if(piecedest.equals("wB"))
           {
               board[endfile][endrank]=new Bishop(endfile, endrank, "white");
               destbut.setImageResource(R.drawable.white_bishop);
           }
           else if(piecedest.equals("wP"))
           {
               board[endfile][endrank]=new Pawn(endfile, endrank, "white");
               destbut.setImageResource(R.drawable.white_pawn);
           }




           //remove
           currentgamemoves.remove(currentgamemoves.size()-1);
           this.first=0;
           //currentplayerside = currentplayerside.equals("White") ? "Black" : "White";
           side=currentplayerside.toLowerCase();
           TextView textView = (TextView) findViewById(R.id.turn);
           String s=currentplayerside+"'s Turn";
           textView.setText(s);


           String opp2="white";

           if(side.equals(opp2))
           {
               opp2="black";
           }




           int kfile=0;
           int krank=0;

           if(side.equals("white"))
           {
               kfile=wkingfile;
               krank=wkingrank;
           }
           else
           {
               kfile=bkingfile;
               krank=bkingrank;
           }





           if(King.incheck(board, opp2, kfile, krank, kfile, krank))
           {

               Context c2 = getApplicationContext();
               CharSequence t2 = "Your King is In Check!";
               int d2 = Toast.LENGTH_SHORT;

               Toast tb2 = Toast.makeText(c2, t2, d2);
               tb2.show();


           }


       }





    }
    public void AIChoose(View view) {

        //set up AL
        String side=currentplayerside.toLowerCase();
        ArrayList<Move> possiblemoves=new ArrayList<Move>();

        //king
        int kf=0;
        int kr=0;
        String opp="white";
        if(side.equals("white"))
        {
            kf=wkingfile;
            kr=wkingrank;
            opp="black";
        }
        else
        {
            kf=bkingfile;
            kr=bkingrank;
        }








        //all valid moves

        //try all moves on this side

        for(int file=0;file<8;file++)
        {
            for(int rank=0;rank<8;rank++)
            {

                if(board[file][rank]!=null && board[file][rank].getColor().equals(side))
                {
                    for(int testfile=0;testfile<8;testfile++)
                    {
                        for(int testrank=0; testrank<8;testrank++)
                        {

                            if(board[file][rank] instanceof Pawn)
                            {
                                //check if valid
                                if(!board[file][rank].valid(testfile, testrank, board))
                                {

                                    continue;



                                }


                                //move

                                boolean enpas=false;

                                if(enpasssq!=null)
                                {
                                    if(side.equals("white"))
                                    {

                                        if((testfile == file + 1 || testfile == file - 1) && testrank == rank + 1 && board[testfile][testrank] == null)
                                        {
                                            if(enpasssq!=null && enpasssq.getRank()==rank && (enpasssq.getFile()==file+1 || enpasssq.getFile()==file-1))
                                            {
                                                if(board[enpasssq.getFile()][enpasssq.getRank()]!=null && board[enpasssq.getFile()][enpasssq.getRank()] instanceof Pawn && board[enpasssq.getFile()][enpasssq.getRank()].getColor().equals("black"))
                                                {
                                                    enpas=true;
                                                }
                                            }
                                        }
                                    }
                                    else
                                    {
                                        if((testfile == file + 1 || testfile== file - 1) && testrank == rank - 1 && board[testfile][testrank] == null)
                                        {
                                            if(enpasssq!=null && enpasssq.getRank()==rank && (enpasssq.getFile()==file+1 || enpasssq.getFile()==file-1))
                                            {
                                                if(board[enpasssq.getFile()][enpasssq.getRank()]!=null && board[enpasssq.getFile()][enpasssq.getRank()] instanceof Pawn && board[enpasssq.getFile()][enpasssq.getRank()].getColor().equals("white"))
                                                {
                                                    enpas=true;
                                                }
                                            }
                                        }
                                    }
                                }





                                if(enpas)
                                {
                                    ChessPiece.move(file, rank, testfile, testrank, board);
                                    ChessPiece enepawn;



                                    enepawn=new Pawn(enpasssq.getFile(), enpasssq.getRank(), opp);

                                    board[enpasssq.getFile()][enpasssq.getRank()]=null;


                                    boolean ans=King.incheck(board,opp,kf, kr, kf,kr);

                                    //restore if doesnt fix problem
                                    if(ans==true)
                                    {
                                        board[file][rank]=board[testfile][testrank];
                                        board[file][rank].setFile(file);
                                        board[file][rank].setRank(rank);
                                        board[testfile][testrank]=null;
                                        board[enpasssq.getFile()][enpasssq.getRank()]=enepawn;

                                    }
                                    else
                                    {
                                        board[file][rank]=board[testfile][testrank];
                                        board[file][rank].setFile(file);
                                        board[file][rank].setRank(rank);
                                        board[testfile][testrank]=null;
                                        board[enpasssq.getFile()][enpasssq.getRank()]=enepawn;

                                        //add
                                        Move m=new Move(board,side, file, rank, testfile, testrank,enpasssq);
                                        m.setisen();
                                        possiblemoves.add(m);




                                    }





                                }

                                else
                                {
                                     Pawn p=(Pawn) board[file][rank];
                                     if(p.ispromote(board,file,rank,testfile,testrank,side))
                                     {
                                         //Check if good
                                         //Do it
                                         ChessPiece destinationval2;
                                         boolean ismoveddest=false;
                                         boolean ismovedorigin=false;

                                         if(board[testfile][testrank]==null)
                                         {
                                             destinationval2=null;
                                         }

                                         else if(board[testfile][testrank] instanceof Pawn)
                                         {
                                             destinationval2=new Pawn(testfile,testrank, board[testfile][testrank].getColor());
                                         }
                                         else if(board[testfile][testrank] instanceof Knight)
                                         {
                                             destinationval2=new Knight(testfile,testrank, board[testfile][testrank].getColor());
                                         }
                                         else if(board[testfile][testrank] instanceof Queen)
                                         {
                                             destinationval2=new Queen(testfile,testrank, board[testfile][testrank].getColor());
                                         }
                                         else if(board[testfile][testrank] instanceof Rook)
                                         {
                                             destinationval2=new Rook(testfile,testrank, board[testfile][testrank].getColor());
                                             ismoveddest= ((Rook)(board[testfile][testrank])).ismoved;
                                         }
                                         else if(board[testfile][testrank] instanceof Bishop)
                                         {
                                             destinationval2=new Bishop(testfile,testrank, board[testfile][testrank].getColor());
                                         }
                                         else
                                         {
                                             destinationval2=new King(testfile,testrank, board[testfile][testrank].getColor());
                                             ismoveddest= ((King)(board[testfile][testrank])).ismoved;
                                         }

                                         Pawn.promote(board, testfile, testrank, 'Q', side);
                                         board[file][rank]=null;

                                         boolean ans=King.incheck(board,opp,kf, kr, kf,kr);

                                         if(ans)
                                         {
                                            Pawn.unpromote(board,file,rank,side);
                                            board[testfile][testrank]=destinationval2;
                                         }
                                         else
                                         {
                                             Pawn.unpromote(board,file,rank,side);
                                             board[testfile][testrank]=destinationval2;
                                             Move m=new Move(board,side, file, rank, testfile, testrank,enpasssq);
                                             m.setisprom();
                                             m.setpromTo('Q');
                                             possiblemoves.add(m);
                                         }


                                         continue;
                                     }







                                    ChessPiece destinationval2;
                                    boolean ismoveddest=false;
                                    boolean ismovedorigin=false;

                                    if(board[testfile][testrank]==null)
                                    {
                                        destinationval2=null;
                                    }

                                    else if(board[testfile][testrank] instanceof Pawn)
                                    {
                                        destinationval2=new Pawn(testfile,testrank, board[testfile][testrank].getColor());
                                    }
                                    else if(board[testfile][testrank] instanceof Knight)
                                    {
                                        destinationval2=new Knight(testfile,testrank, board[testfile][testrank].getColor());
                                    }
                                    else if(board[testfile][testrank] instanceof Queen)
                                    {
                                        destinationval2=new Queen(testfile,testrank, board[testfile][testrank].getColor());
                                    }
                                    else if(board[testfile][testrank] instanceof Rook)
                                    {
                                        destinationval2=new Rook(testfile,testrank, board[testfile][testrank].getColor());
                                        ismoveddest= ((Rook)(board[testfile][testrank])).ismoved;
                                    }
                                    else if(board[testfile][testrank] instanceof Bishop)
                                    {
                                        destinationval2=new Bishop(testfile,testrank, board[testfile][testrank].getColor());
                                    }
                                    else
                                    {
                                        destinationval2=new King(testfile,testrank, board[testfile][testrank].getColor());
                                        ismoveddest= ((King)(board[testfile][testrank])).ismoved;
                                    }

                                    ChessPiece.move(file, rank, testfile, testrank, board);


                                    //check if no check anymore


                                    boolean ans=King.incheck(board,opp,kf, kr, kf,kr);

                                    //restore if doesnt fix problem
                                    if(ans==true)
                                    {
                                        board[file][rank]=board[testfile][testrank];
                                        board[file][rank].setFile(file);
                                        board[file][rank].setRank(rank);
                                        board[testfile][testrank]=destinationval2;

                                        if(board[file][rank] instanceof Rook)
                                        {
                                            Rook r=(Rook)board[file][rank];
                                            r.ismoved=ismovedorigin;
                                        }
                                        if(board[file][rank] instanceof King)
                                        {
                                            King k=(King)board[file][rank];
                                            k.ismoved=ismovedorigin;
                                        }

                                        if(board[testfile][testrank] !=null && board[testfile][testrank] instanceof Rook)
                                        {
                                            Rook r=(Rook)board[testfile][testrank];
                                            r.ismoved=ismoveddest;
                                        }
                                        if(board[testfile][testrank] !=null && board[testfile][testrank] instanceof King)
                                        {
                                            King k=(King)board[testfile][testrank];
                                            k.ismoved=ismoveddest;
                                        }
                                    }
                                    else
                                    {
                                        board[file][rank]=board[testfile][testrank];
                                        board[file][rank].setFile(file);
                                        board[file][rank].setRank(rank);
                                        board[testfile][testrank]=destinationval2;

                                        if(board[file][rank] instanceof Rook)
                                        {
                                            Rook r=(Rook)board[file][rank];
                                            r.ismoved=ismovedorigin;
                                        }
                                        if(board[file][rank] instanceof King)
                                        {
                                            King k=(King)board[file][rank];
                                            k.ismoved=ismovedorigin;
                                        }

                                        if(board[testfile][testrank] !=null && board[testfile][testrank] instanceof Rook)
                                        {
                                            Rook r=(Rook)board[testfile][testrank];
                                            r.ismoved=ismoveddest;
                                        }
                                        if(board[testfile][testrank] !=null && board[testfile][testrank] instanceof King)
                                        {
                                            King k=(King)board[testfile][testrank];
                                            k.ismoved=ismoveddest;
                                        }

                                        //add
                                        Move m=new Move(board,side, file, rank, testfile, testrank,enpasssq);
                                        possiblemoves.add(m);





                                    }



                                }



                            }

                            else
                            {
                                if(board[file][rank] instanceof King)
                                {
                                     King k=(King)board[file][rank];
                                     if(k.castlegood(board,file,rank, testfile, testrank, side))
                                     {
                                         Move m=new Move(board,side, file, rank, testfile, testrank, enpasssq);
                                         m.setiscas();
                                         possiblemoves.add(m);
                                         continue;
                                     }

                                     if(board[file][rank].valid(testfile,testrank,board))
                                     {
                                         Move m=new Move(board,side, file, rank, testfile, testrank, enpasssq);
                                         possiblemoves.add(m);
                                     }




                                }







                                if(!(board[file][rank] instanceof King) && board[file][rank].valid(testfile, testrank, board))
                                {
                                    //move

                                    ChessPiece destinationval2;
                                    boolean ismoveddest=false;
                                    boolean ismovedorigin=false;

                                    if(board[testfile][testrank]==null)
                                    {
                                        destinationval2=null;
                                    }

                                    else if(board[testfile][testrank] instanceof Pawn)
                                    {
                                        destinationval2=new Pawn(testfile,testrank, board[testfile][testrank].getColor());
                                    }
                                    else if(board[testfile][testrank] instanceof Knight)
                                    {
                                        destinationval2=new Knight(testfile,testrank, board[testfile][testrank].getColor());
                                    }
                                    else if(board[testfile][testrank] instanceof Queen)
                                    {
                                        destinationval2=new Queen(testfile,testrank, board[testfile][testrank].getColor());
                                    }
                                    else if(board[testfile][testrank] instanceof Rook)
                                    {
                                        destinationval2=new Rook(testfile,testrank, board[testfile][testrank].getColor());
                                        ismoveddest= ((Rook)(board[testfile][testrank])).ismoved;
                                    }
                                    else if(board[testfile][testrank] instanceof Bishop)
                                    {
                                        destinationval2=new Bishop(testfile,testrank, board[testfile][testrank].getColor());
                                    }
                                    else
                                    {
                                        destinationval2=new King(testfile,testrank, board[testfile][testrank].getColor());
                                        ismoveddest= ((King)(board[testfile][testrank])).ismoved;
                                    }

                                    ChessPiece.move(file, rank, testfile, testrank, board);

                                    //check if no check anymore

                                    boolean ans=King.incheck(board,opp,kf, kr, kf,kr);

                                    //restore if doesnt fix problem
                                    if(ans==true)
                                    {
                                        board[file][rank]=board[testfile][testrank];
                                        board[file][rank].setFile(file);
                                        board[file][rank].setRank(rank);
                                        board[testfile][testrank]=destinationval2;

                                        if(board[file][rank] instanceof Rook)
                                        {
                                            Rook r=(Rook)board[file][rank];
                                            r.ismoved=ismovedorigin;
                                        }
                                        if(board[file][rank] instanceof King)
                                        {
                                            King k=(King)board[file][rank];
                                            k.ismoved=ismovedorigin;
                                        }

                                        if(board[testfile][testrank] !=null && board[testfile][testrank] instanceof Rook)
                                        {
                                            Rook r=(Rook)board[testfile][testrank];
                                            r.ismoved=ismoveddest;
                                        }
                                        if(board[testfile][testrank] !=null && board[testfile][testrank] instanceof King)
                                        {
                                            King k=(King)board[testfile][testrank];
                                            k.ismoved=ismoveddest;
                                        }
                                    }

                                    //good move
                                    else
                                    {






                                        board[file][rank]=board[testfile][testrank];
                                        board[file][rank].setFile(file);
                                        board[file][rank].setRank(rank);
                                        board[testfile][testrank]=destinationval2;

                                        if(board[file][rank] instanceof Rook)
                                        {
                                            Rook r=(Rook)board[file][rank];
                                            r.ismoved=ismovedorigin;
                                        }
                                        if(board[file][rank] instanceof King)
                                        {
                                            King k=(King)board[file][rank];
                                            k.ismoved=ismovedorigin;
                                        }

                                        if(board[testfile][testrank] !=null && board[testfile][testrank] instanceof Rook)
                                        {
                                            Rook r=(Rook)board[testfile][testrank];
                                            r.ismoved=ismoveddest;
                                        }
                                        if(board[testfile][testrank] !=null && board[testfile][testrank] instanceof King)
                                        {
                                            King k=(King)board[testfile][testrank];
                                            k.ismoved=ismoveddest;
                                        }



                                        Move m=new Move(board, side,file,rank, testfile, testrank, enpasssq);
                                        possiblemoves.add(m);





                                    }




                                }
                            }



                        }
                    }
                }



            }
        }

























        int n=possiblemoves.size();

       if(n<=0)
       {
           Context context = getApplicationContext();
           CharSequence text = "No Valid Moves Left! Game is Over and is a Draw!";
           int duration = Toast.LENGTH_LONG;

           Toast toast = Toast.makeText(context, text, duration);
           toast.show();
           endofGame();
           return;


       }




       int number = (int)(Math.random() * n)-1;

       if(number<0)
       {
           number=0;
       }



       Move toexec=possiblemoves.get(number);
       String oppos="white";

       if(side.equals("white"))
        {
            oppos="black";
        }


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

           if(side.equals("white")) {
               dbut.setImageResource(R.drawable.white_queen);
           }
           else
           {
               dbut.setImageResource(R.drawable.black_queen);
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



           board[bf][br]=null;

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



       currentgamemoves.add(toexec);
       undovalid=true;
       if(ismate(oppos))
       {
           this.winner=currentplayerside;
           Context c2 = getApplicationContext();
           CharSequence t2 = winner+ " Wins!";
           int d2 = Toast.LENGTH_SHORT;

           Toast tb2 = Toast.makeText(c2, t2, d2);
           tb2.show();

           endofGame();
       }



       currentplayerside = currentplayerside.equals("White") ? "Black" : "White";
       side=currentplayerside.toLowerCase();
       TextView textView = (TextView) findViewById(R.id.turn);
       String s=currentplayerside+"'s Turn";
       textView.setText(s);


       String opp2="white";

        if(side.equals(opp2))
        {
            opp2="black";
        }






        int bf=toexec.getOrigfile();
        int br=toexec.getOrigfile();


        int df=toexec.getDestfile();
        int dr=toexec.getDestfile();


        int kfile=0;
        int krank=0;

        if(side.equals("white"))
        {
            kfile=wkingfile;
            krank=wkingrank;
        }
        else
        {
            kfile=bkingfile;
            krank=bkingrank;
        }





        if(King.incheck(board, opp2, kfile, krank, kfile, krank))
        {

            Context c2 = getApplicationContext();
            CharSequence t2 = "Your King is In Check!";
            int d2 = Toast.LENGTH_SHORT;

            Toast tb2 = Toast.makeText(c2, t2, d2);
            tb2.show();


        }

        //change enpas
        int moveFile=df;
        int moveRank=dr;
        int pieceFile=bf;
        int pieceRank=br;
        if (board[moveFile][moveRank] != null && board[moveFile][moveRank] instanceof Pawn) {
            if (moveFile == pieceFile && moveRank == pieceRank + 2 && board[moveFile][moveRank].getColor().equals("white")) {
                enpasssq = new Point(moveFile, moveRank);
                Pawn.setensq(moveFile, moveRank);
            } else if (moveFile == pieceFile && moveRank == pieceRank - 2 && board[moveFile][moveRank].getColor().equals("black")) {
                enpasssq = new Point(moveFile, moveRank);
                Pawn.setensq(moveFile, moveRank);
            } else {
                enpasssq = null;
                Pawn.cancelensq();
            }
        } else {
            enpasssq = null;
            Pawn.cancelensq();
        }




    }
    public void ListByTitle(View view) {
       // System.out.println(al.size());
        Intent intent = new Intent(MainActivity.this, ListByTitle.class);
        intent.putExtra(ListByTitle.EXTRA_DATA,al);
        startActivity(intent);
    }
    public void ListByDate(View view) {
       // System.out.println(al.size());
        Intent intent = new Intent(MainActivity.this, ListByDate.class);
        intent.putExtra(ListByDate.EXTRA_DATA,al);
        startActivity(intent);
    }
    public void StoreOffer()
    {
        Bundle bundle=new Bundle();
        bundle.putString("currentside",currentside);
        DialogFragment newFragment = new DrawDialog();
        newFragment.setArguments(bundle);
        newFragment.show(getSupportFragmentManager(), "NoticeDialogFragment");

        return;
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

    public String getWinner(){
        return this.winner;
    }
    public ArrayList<Move> getMoves()
    {
        return this.currentgamemoves;
    }





}