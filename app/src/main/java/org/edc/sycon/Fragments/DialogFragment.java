package org.edc.sycon.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.edc.sycon.R;

//DialogFragment will be used for BookMyShow
//and also to show user details when user registers with twitter.

public class DialogFragment extends android.app.DialogFragment {

    private static final String TITLE = "title";
    private static final String MESSAGE="message";
    private static final String POS_BUTTON="pos_button";
    private static final String NEUT_BUTTON="neut_button";
    // Use this instance of the interface to deliver action events
    private DialogListener mListener;
    private String mTitle;
    private String mMessage;
    private String mPosButtonString;
    private String mNeutButtonString;

    public DialogFragment() {
    }

    public static DialogFragment newInstance(String title,String message,String posButton,String neutButton) {
        DialogFragment fragment = new DialogFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(MESSAGE,message);
        args.putString(POS_BUTTON,posButton);
        args.putString(NEUT_BUTTON,neutButton);
        fragment.setArguments(args);
        return fragment;
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (DialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if (getArguments() != null) {
            mTitle = getArguments().getString(TITLE);
            mMessage=getArguments().getString(MESSAGE);
            mPosButtonString=getArguments().getString(POS_BUTTON);
            mNeutButtonString=getArguments().getString(NEUT_BUTTON);
        }
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        mBuilder.setTitle(mTitle)
                .setMessage(mMessage)
                .setPositiveButton(mPosButtonString, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mListener != null) {
                            mListener.onDialogPositiveClick(DialogFragment.this);
                        }
                    }
                })
                .setNeutralButton(mNeutButtonString, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mListener != null) {
                            mListener.onDialogNeutralClick(DialogFragment.this);
                        }
                    }
                });
        return mBuilder.create();
    }

    public interface DialogListener {
        void onDialogPositiveClick(android.app.DialogFragment dialog);

        void onDialogNeutralClick(android.app.DialogFragment dialog);
    }
}