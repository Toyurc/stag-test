package com.example.adeba.steg_test.encyrptionLayer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.adeba.steg_test.abstracts.SteganographyTask;
import com.example.adeba.steg_test.interfaceLayer.AsyncResponse;
import com.example.adeba.steg_test.interfaceLayer.SteganographyParams;
import com.example.adeba.steg_test.utils.BitmapUtils;
import com.example.adeba.steg_test.utils.SteganographyUtils;



public class Decrypt extends SteganographyTask {
    public Decrypt(AsyncResponse<SteganographyParams> delegate) {
        super(delegate);
    }

    /**
     * Decodes a message out of an encoded image.
     * @param steganographyParams Contains filepath to image.
     * @return Contains decoded message.
     */
    @Override
    protected SteganographyParams execute(SteganographyParams steganographyParams) {
        Bitmap bitmap = BitmapFactory.decodeFile(steganographyParams.getFilePath());

        String message = SteganographyUtils.decode(BitmapUtils.getPixels(bitmap));

        steganographyParams.setMessage(message);
        steganographyParams.setType(AsyncResponse.Type.DECODE_SUCCESS);
        return steganographyParams;
    }
}