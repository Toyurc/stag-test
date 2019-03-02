package com.example.adeba.steg_test.encyrptionLayer;

import android.graphics.Bitmap;
import android.net.Uri;

import com.example.adeba.steg_test.abstracts.SteganographyTask;
import com.example.adeba.steg_test.interfaceLayer.AsyncResponse;
import com.example.adeba.steg_test.interfaceLayer.SteganographyParams;
import com.example.adeba.steg_test.utils.BitmapUtils;
import com.example.adeba.steg_test.utils.FileUtils;
import com.example.adeba.steg_test.utils.SteganographyUtils;

public class Encrypt extends SteganographyTask {
    public Encrypt(AsyncResponse<SteganographyParams> delegate) {
        super(delegate);
    }

    /**
     * Encodes an image with the specified message, and saves it.
     * @param steganographyParams Contains filepath to image, and specified message
     * @return Contains filepath to encoded image.
     */
    @Override
    protected SteganographyParams execute(SteganographyParams steganographyParams) {

        Bitmap bitmap = BitmapUtils.decodeFile(steganographyParams.getFilePath());
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int numberOfPixels = w * h;

        byte[] data = steganographyParams.getMessage().getBytes();

        int requiredLength = data.length * 8 + 32;

        if (requiredLength > numberOfPixels) {
            throw new IllegalArgumentException("Message is too long to fit into pixels.");
        }

        int[] encodedPixels = SteganographyUtils.encode(
                BitmapUtils.getPixels(bitmap, requiredLength),
                steganographyParams.getMessage()
        );

        BitmapUtils.setPixels(bitmap, encodedPixels);

        Uri resultUri = FileUtils.saveBitmap(bitmap);

        steganographyParams.setResultUri(resultUri);

        steganographyParams.setType(AsyncResponse.Type.ENCODE_SUCCESS);
        return steganographyParams;
    }
}