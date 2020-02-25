package com.example.toolsharing.Student;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.toolsharing.R;


public class PenaltyDialog extends DialogFragment {

    EditText penalty_dam;
    private PenaltyDialogListener listener;
    String pen;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater= getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.penalty_dialog, null);

        builder.setView(view)
                .setTitle("Penalty")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        pen = String.valueOf(penalty_dam.getText());
                        //System.out.println("URL Rating: " + pen);

                        listener.penaltyAmt(pen);
                    }
                });

        penalty_dam = view.findViewById(R.id.penalty_dam);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (PenaltyDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement PenaltyDialogListener");
        }
    }

    public interface PenaltyDialogListener{
        void penaltyAmt(String penalty);
    }
}
