package com.example.adeba.steg_test.userInterface;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.adeba.steg_test.R;
import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

public class DecodedMessageDialogFragment extends DialogFragment {

    static final String ARG_MESSAGE = "Image path";

    public static DecodedMessageDialogFragment newInstance(String message) {
        Bundle args = new Bundle();
        args.putString(ARG_MESSAGE, message);

        DecodedMessageDialogFragment dialogFragment = new DecodedMessageDialogFragment();
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_decoded_message, null);

        String password = "badman";
        String encryptedMsg = getArguments().getString(ARG_MESSAGE);
        try {
            String message = AESCrypt.decrypt(password, encryptedMsg);
            TextView messageTextView = (TextView) v.findViewById(R.id.textView_decoded_message);
            messageTextView.setText(message);
        }catch (GeneralSecurityException e){
            //handle error - could be due to incorrect password or tampered encryptedMsg
            Log.e("Decrypting Error:",e.toString());
        }

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.decoded_message)
                .create();
    }
}