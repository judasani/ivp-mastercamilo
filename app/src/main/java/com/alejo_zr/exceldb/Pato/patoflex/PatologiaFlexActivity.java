package com.alejo_zr.exceldb.Pato.patoflex;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alejo_zr.exceldb.R;
import com.alejo_zr.exceldb.entidades.PatoFlex;

public class PatologiaFlexActivity extends AppCompatActivity {

    private TextView tvIdDaño,tvIdSegmento,tvNombreCarreteraPatologiaActivity,tvCarrilDanio,tvAclaraciones,tvanchRepa,tvlarRepa,tvdanionombre,tvlarDanio,tvanchoDanio,
                    tvAbscisaPatoFlexActivity,tvLatPatoFlexActivity,tvLongFlexActivity,tvDireccionPatoFlex,tvSeveridadPatoFlexActivity;
    private String path;
    private ImageView imgPatoFlex;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patologia_flex);


        tvNombreCarreteraPatologiaActivity = (TextView) findViewById(R.id.tvNombreCarreteraPatologiaFlexActivity);
        tvAbscisaPatoFlexActivity = (TextView) findViewById(R.id.tvAbscisaPatoFlexActivity);
        tvLatPatoFlexActivity= (TextView) findViewById(R.id.tvLatPatoFlexActivity);
        tvLongFlexActivity= (TextView) findViewById(R.id.tvLongFlexActivity);
        tvCarrilDanio= (TextView) findViewById(R.id.tvCarrilDanioPatoFlexActivity);
        tvdanionombre = (TextView) findViewById(R.id.tvdanionombrePatoFlexActivity);
        tvSeveridadPatoFlexActivity = (TextView) findViewById(R.id.tvSeveridadPatoFlexActivity);
        tvlarDanio = (TextView) findViewById(R.id.tvlarDanioPatoFlexActivity);
        tvanchoDanio = (TextView) findViewById(R.id.tvanchDanioPatoFlexActivity);
        tvanchRepa = (TextView) findViewById(R.id.tvanchoRepaPatoFlexActivity);
        tvlarRepa = (TextView) findViewById(R.id.tvlarRepaPatoFlexActivity);
        tvAclaraciones=(TextView) findViewById(R.id.tvAclaracionesPatoFlexActivity);

        tvIdSegmento = (TextView) findViewById(R.id.tvIdSegmentoPatologiaFlexActivity);
        tvIdDaño = (TextView) findViewById(R.id.tvIdDañoPatoFlex);
        tvDireccionPatoFlex = (TextView) findViewById(R.id.tvDireccionPatoFlex);

        imgPatoFlex = (ImageView) findViewById(R.id.imgDanio_PatoFlex) ;


        Bundle patologiaEnviado=getIntent().getExtras();
        PatoFlex patoFlex=null;


        if(patologiaEnviado!=null){
            patoFlex = (PatoFlex) patologiaEnviado.getSerializable("patologia");

            tvAbscisaPatoFlexActivity.setText(patoFlex.getAbscisa().toString());
            tvLatPatoFlexActivity.setText(patoFlex.getLatitud().toString());
            tvLongFlexActivity.setText(patoFlex.getLongitud().toString());
            tvCarrilDanio.setText(patoFlex.getCarril().toString());
            tvdanionombre.setText(patoFlex.getDanio().toString());
            tvSeveridadPatoFlexActivity.setText(patoFlex.getSeveridad().toString());
            tvlarDanio.setText(patoFlex.getLargoDanio().toString());
            tvanchoDanio.setText(patoFlex.getAnchoDanio().toString());
            tvanchRepa.setText(patoFlex.getAnchoRepa().toString());
            tvlarRepa.setText(patoFlex.getLargoRepa().toString());
            tvAclaraciones.setText(patoFlex.getAclaraciones().toString());
            tvIdSegmento.setText(patoFlex.getId_segmento_patoFlex().toString());
            tvNombreCarreteraPatologiaActivity.setText(patoFlex.getNombre_carretera_patoFlex().toString());
            tvIdDaño.setText(patoFlex.getId_patoFlex().toString());
            tvDireccionPatoFlex.setText(patoFlex.getFoto().toString());

        }

        path = tvDireccionPatoFlex.getText().toString();
        obtenerFotoPatoFlex();
    }

    protected void onStart() {
        super.onStart();

        tvNombreCarreteraPatologiaActivity = (TextView) findViewById(R.id.tvNombreCarreteraPatologiaFlexActivity);
        tvAbscisaPatoFlexActivity = (TextView) findViewById(R.id.tvAbscisaPatoFlexActivity);
        tvLatPatoFlexActivity= (TextView) findViewById(R.id.tvLatPatoFlexActivity);
        tvLongFlexActivity= (TextView) findViewById(R.id.tvLongFlexActivity);
        tvCarrilDanio= (TextView) findViewById(R.id.tvCarrilDanioPatoFlexActivity);
        tvAclaraciones=(TextView) findViewById(R.id.tvAclaracionesPatoFlexActivity);
        tvanchRepa = (TextView) findViewById(R.id.tvanchoRepaPatoFlexActivity);
        tvlarRepa = (TextView) findViewById(R.id.tvlarRepaPatoFlexActivity);
        tvdanionombre = (TextView) findViewById(R.id.tvdanionombrePatoFlexActivity);
        tvlarDanio = (TextView) findViewById(R.id.tvlarDanioPatoFlexActivity);
        tvanchoDanio = (TextView) findViewById(R.id.tvanchDanioPatoFlexActivity);
        tvIdSegmento = (TextView) findViewById(R.id.tvIdSegmentoPatologiaFlexActivity);
        tvIdDaño = (TextView) findViewById(R.id.tvIdDañoPatoFlex);
        tvDireccionPatoFlex = (TextView) findViewById(R.id.tvDireccionPatoFlex);


        Bundle patologiaEnviado=getIntent().getExtras();
        PatoFlex patoFlex=null;

        if(patologiaEnviado!=null){
            patoFlex = (PatoFlex) patologiaEnviado.getSerializable("patologia");

            tvAbscisaPatoFlexActivity.setText(patoFlex.getAbscisa().toString());
            tvLatPatoFlexActivity.setText(patoFlex.getLatitud().toString());
            tvLongFlexActivity.setText(patoFlex.getLongitud().toString());
            tvCarrilDanio.setText(patoFlex.getCarril().toString());
            tvdanionombre.setText(patoFlex.getDanio().toString());
            tvSeveridadPatoFlexActivity.setText(patoFlex.getSeveridad().toString());
            tvlarDanio.setText(patoFlex.getLargoDanio().toString());
            tvanchoDanio.setText(patoFlex.getAnchoDanio().toString());
            tvanchRepa.setText(patoFlex.getAnchoRepa().toString());
            tvlarRepa.setText(patoFlex.getLargoRepa().toString());
            tvAclaraciones.setText(patoFlex.getAclaraciones().toString());
            tvIdSegmento.setText(patoFlex.getId_segmento_patoFlex().toString());
            tvNombreCarreteraPatologiaActivity.setText(patoFlex.getNombre_carretera_patoFlex().toString());
            tvIdDaño.setText(patoFlex.getId_patoFlex().toString());
            tvDireccionPatoFlex.setText(patoFlex.getFoto().toString());

        }

        path = tvDireccionPatoFlex.getText().toString();
        obtenerFotoPatoFlex();


    }

    private void obtenerFotoPatoFlex() {

        MediaScannerConnection.scanFile(this, new String[]{path}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("Ruta de almacenamiento","Path: "+path);
                    }
                });

        Bitmap bitmap= BitmapFactory.decodeFile(path);
        imgPatoFlex.setImageBitmap(bitmap);
    }

    public void onClick (View view){

        Intent intent = null;
        switch (view.getId()){

            case R.id.btnEditarPatologia:
                intent = new Intent(PatologiaFlexActivity.this, EditarPatologiaFlexActivity.class);
                intent.putExtra("tvAbscisa",tvAbscisaPatoFlexActivity.getText().toString());
                intent.putExtra("tvLatitud",tvLatPatoFlexActivity.getText().toString());
                intent.putExtra("tvLongitud",tvLongFlexActivity.getText().toString());
                intent.putExtra("tvCarrilDanio",tvCarrilDanio.getText().toString());
                intent.putExtra("tvAclaraciones",tvAclaraciones.getText().toString());
                intent.putExtra("tvanchRepa", tvanchRepa.getText().toString());
                intent.putExtra("tvlarRepa",tvlarRepa.getText().toString());
                intent.putExtra("tvdanionombre",tvdanionombre.getText().toString());
                intent.putExtra("tvanchDanio",tvanchoDanio.getText().toString());
                intent.putExtra("tvIdSegmento",tvIdSegmento.getText().toString());
                intent.putExtra("tvNombreCarreteraPatologiaActivity",tvNombreCarreteraPatologiaActivity.getText().toString());
                intent.putExtra("tvIdDaño",tvIdDaño.getText().toString());
                startActivity( intent);
                break;

        }
    }

}
