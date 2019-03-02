package com.example.adeba.steg_test.userInterface;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.adeba.steg_test.R;
import com.example.adeba.steg_test.abstracts.ImageActivity;
import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

public class EncodeActivity extends ImageActivity {

    static final String EXTRA_MESSAGE = "Extra Message";
    static final String KEY_MESSAGE = "Key Message";

    public EditText mEditText;
    public Button mEncodeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encode);

        mImageView = (ImageView) findViewById(R.id.imageView_encode_thumbnail);
        mEditText  = (EditText)  findViewById(R.id.editText_message);
        mEncodeButton = (Button) findViewById(R.id.button_upload_encode);

        if (savedInstanceState != null) {
            mEditText.setText(savedInstanceState.getString(KEY_MESSAGE));
        }

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEncodeButton.setEnabled(mEditText.getText().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mEncodeButton.setEnabled(mEditText.getText().length() > 0);
        mEncodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = "badman";
                String text = mEditText.getText().toString();
                try {
                    String encryptedMsg = AESCrypt.encrypt(password, text);
                    Intent data = new Intent();
                    data.putExtra(EXTRA_MESSAGE, encryptedMsg);
                    setResult(RESULT_OK, data);
                    finish();
                }catch (GeneralSecurityException e) {
                    //handle error
                    Log.e("Error", e.toString());
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle b) {
        super.onSaveInstanceState(b);
        b.putString(KEY_MESSAGE, mEditText.getText().toString());
    }
}