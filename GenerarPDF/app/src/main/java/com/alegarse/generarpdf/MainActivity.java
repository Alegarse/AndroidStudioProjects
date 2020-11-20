package com.alegarse.generarpdf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button btnCrearPdf;
    private LinearLayout lnLayout;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btnCrearPdf = findViewById(R.id.btn);
        lnLayout = findViewById(R.id.llPdf);
        
        btnCrearPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Tamaño"," " + lnLayout.getWidth() + "  " + lnLayout.getWidth());
                bitmap = loadBitmapFromView(lnLayout,lnLayout.getWidth(),lnLayout.getHeight());
                createPdf();
            }
        });
    }

    public static Bitmap loadBitmapFromView (View v, int width, int height) {
        Bitmap bmpImg = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpImg);
        v.draw(c);
        return bmpImg;
    }

    private void createPdf() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float hight = displayMetrics.heightPixels;
        float width = displayMetrics.widthPixels;

        int convertHighet = (int) hight;
        int convertWidth = (int) width;

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth,convertHighet,1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap,convertWidth,convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap,0,0,null);
        document.finishPage(page);

        String targetPdf = "/./PDFGenerado.pdf";
        File filePath;
        filePath = new File(targetPdf);

        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(this,"PDF generado",Toast.LENGTH_LONG).show();
        } catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this,"Algo anda mal: " + e.toString(),Toast.LENGTH_LONG).show();
        }
        document.close();

        abrirPDFGenerado();

    }

    private void abrirPDFGenerado() {
        File file = new File("/sdcard/pdffromlayout.pdf");
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri,"application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException anf) {
                Toast.makeText(this,"No hay aplicacion dispnible para ver el pdf",Toast.LENGTH_LONG).show();
            }
        }
    }
}