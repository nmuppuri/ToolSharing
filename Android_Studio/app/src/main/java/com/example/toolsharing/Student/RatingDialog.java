package com.example.toolsharing.Student;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.toolsharing.R;


public class RatingDialog extends DialogFragment {

    RatingBar ratingBar;
    private RatingDialogListener listener;
    String rat;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater= getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.rating_dialog, null);

        builder.setView(view)
                .setTitle("Rate Tool")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        rat = String.valueOf(ratingBar.getRating());
                        System.out.println("URL Rating: " + rat);

                        listener.ratingVal(rat);
                    }
                });

        ratingBar = view.findViewById(R.id.rating_dialog);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (RatingDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement RatingDialogListener");
        }
    }

    public interface RatingDialogListener{
        void ratingVal(String rating);
    }
}
