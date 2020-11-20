package com.example.carplus3g365v2.Modelos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class BitmapUtils {

    public static Bitmap redim(Bitmap b, int maxHeight, int maxWidth){
        Matrix matrix = new Matrix();
        float scale = Math.min(((float) maxWidth / b.getWidth()), ((float) maxHeight / b.getHeight()));
        matrix.postScale(scale, scale);
        return Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
    }

    public static Bitmap bitmapToGrayScale(Bitmap b){
        int width, height;
        height = b.getHeight();
        width = b.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(b, 0, 0, paint);
        return bmpGrayscale;
    }

    public static Bitmap addWaterMark(Bitmap b, String text){
        int w = b.getWidth();
        int h = b.getHeight();

        Paint bgPaint = new Paint();
        bgPaint.setColor(Color.argb(25, 0, 0, 0));  //transparent black,change opacity by changing hex value "AA" between "00" and "FF"

        Bitmap result = Bitmap.createBitmap(w, h, b.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(b, 0, 0, null);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(20);
        paint.setAntiAlias(true);
        paint.setUnderlineText(false);

        //should draw background first, order is important
        int left = 0;
        int right = w;
        int bottom = h;
        int top = bottom - 30;
        canvas.drawRect(left, top, right, bottom, bgPaint);
        canvas.drawText(text, 10, h - 8, paint);

        return result;
    }

    public static String bitmapToBase64PNG(Bitmap b) {
        if (b == null){
            return "";
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public static String bitmapToBase64JPG(Bitmap b) {
        if (b == null){
            return "";
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 85, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public static Bitmap base64StringToBitmap(String s) {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inJustDecodeBounds = false;
        bitmapOptions.inSampleSize = 1;
        bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;

        byte[] bytes = Base64.decode(s, 0);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, bitmapOptions);
    }

    public static String base64PNGToJPEG(String origen) {
        byte[] bytes;
        Bitmap bmpFirma;
        ByteArrayOutputStream byteArrayOutputStream;
        byte[] byteArray;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inJustDecodeBounds = false;
        bitmapOptions.inSampleSize = 1;
        bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;

        byteArrayOutputStream = new ByteArrayOutputStream();
        bytes = Base64.decode(origen, 0);
        bmpFirma = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, bitmapOptions);
        bmpFirma.compress(Bitmap.CompressFormat.JPEG, 91, byteArrayOutputStream);
        byteArray = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static Bitmap overlayBitmaps(Bitmap background, Bitmap layer){
        Bitmap bmOverlay = Bitmap.createBitmap(background.getWidth(), background.getHeight(), background.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(background, new Matrix(), null);
        canvas.drawBitmap(layer, new Matrix(), null);
        return bmOverlay;
    }
}
