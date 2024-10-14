package com.alexander_rodriguez.mihogar;

import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.alexander_rodriguez.mihogar.Base.BaseActivity;
import com.alexander_rodriguez.mihogar.UTILIDADES.TAlquiler;

import java.io.File;

public class ViewPdfActivity extends AppCompatActivity {
    public static final String EXTRA_PATH_PDF = "path_pdf";
    //private PDFView pdfView;
    private File file;
    private String correo;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        setTitle("Voucher");
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_pdf);
//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//        pdfView = findViewById(R.id.pdfView);
//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null){
//            file = new File(bundle.getString(EXTRA_PATH_PDF, ""));
//            pdfView.fromFile(file)
//                    .enableSwipe(true)
//                    .swipeHorizontal(false)
//                    .enableDoubletap(true)
//                    .enableAntialiasing(true)
//                    .load();
//        }
//
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == BaseActivity.BACK_PRESSED){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onclick(View view){
        Send.sendForGMail(this, file.getName(), getIntent().getStringExtra(TAlquiler.CORREO));
    }
    public void onclick2(View view){
        Send.sendForWhatsapp(this, file.getName(), getIntent().getStringExtra(TAlquiler.NUMERO_TEL));
    }
}

