package com.example.adeba.steg_test.abstracts;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.adeba.steg_test.interfaceLayer.AsyncResponse;
import com.example.adeba.steg_test.interfaceLayer.ImageLoaderTask;
import com.example.adeba.steg_test.userInterface.MainActivity;

public abstract class ImageActivity extends AppCompatActivity implements AsyncResponse<Bitmap> {

    static final String KEY_FILEPATH = "Key FilePath";

    public ImageView mImageView;
    public Uri mFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFilePath(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadImage();
    }

    private void initFilePath(Bundle savedInstanceState) {
        mFilePath  = getIntent().getParcelableExtra(MainActivity.EXTRA_FILE_PATH);

        if (savedInstanceState != null) {
            mFilePath = savedInstanceState.getParcelable(KEY_FILEPATH);
        }
    }

    private void loadImage() {
        if (mFilePath != null) {
            new ImageLoaderTask(this,this).execute(mFilePath);
        }
    }

    @Override
    @CallSuper
    public void onSaveInstanceState(Bundle b) {
        super.onSaveInstanceState(b);
        b.putParcelable(KEY_FILEPATH, mFilePath
        );
    }

    @Override
    public void processResult(Bitmap result, AsyncResponse.Type t) {
        if (result == null) {
            return;
        }

        switch (t) {
            case IMAGE_LOADED:
                mImageView.setImageBitmap(result);
                break;
        }
    }
}
