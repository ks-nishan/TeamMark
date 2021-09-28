package com.teammark.parkingmanagement.util;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.squareup.picasso.Picasso;
import com.teammark.parkingmanagement.R;

public class QRGenerator {

    public QRGenerator() {

    }

    public void generateQR(String reservationID, ImageView imageView){

        MultiFormatWriter writer = new MultiFormatWriter();

        try {
            BitMatrix matrix = writer.encode(reservationID, BarcodeFormat.QR_CODE, 512, 512);

            BarcodeEncoder encoder = new BarcodeEncoder();

            Bitmap bitmap = encoder.createBitmap(matrix);

            imageView.setImageBitmap(bitmap);

            /*
            Picasso.get()
                    .load(bitmap)
                    .placeholder(R.drawable.parkingplaceholder)
                    .fit()
                    .centerCrop()
                    .into(imageView);

             */

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

}
