package com.example.adeba.steg_test.userInterface;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adeba.steg_test.R;
import com.example.adeba.steg_test.abstracts.ImageActivity;
import com.example.adeba.steg_test.utils.FileUtils;


public class SendImageActivity extends ImageActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_image);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        mImageView = (ImageView) findViewById(R.id.imageView_encoded_image);

        Button sendButton = (Button) findViewById(R.id.button_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.setType("image/png");
                sendIntent.putExtra(Intent.EXTRA_STREAM, mFilePath);
                startActivity(Intent.createChooser(sendIntent,"Send"));
            }
        });

        TextView filePathTextView = (TextView) findViewById(R.id.textView_filePath);
        filePathTextView.setText(FileUtils.uriToFilePath(this,mFilePath));
    }
}