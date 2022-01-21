package com.example.androidproject73;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

public class DrawDialog extends AppCompatDialogFragment {

    private Listener l;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        Bundle bundle = getArguments();
        String side=bundle.getString("currentside");
        String opposite="White";

        if(side.equals("white"))
        {
            opposite="Black";
        }



        String message="It is now " + side+"'s turn to move. "+opposite+" has offered you a draw, please choose to accept or decline it";
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setPositiveButton(R.string.acceptdraw, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        l.onYesClicked();
                    }
                })
                .setNegativeButton(R.string.rejectdraw, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();




    }



    public interface Listener
    {
        public void onYesClicked();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try{
            l=(Listener) context;
        }
        catch(ClassCastException e)
        {
            throw new ClassCastException(context.toString());
        }

    }





}
