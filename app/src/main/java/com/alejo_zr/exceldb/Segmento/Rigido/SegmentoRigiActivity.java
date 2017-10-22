package com.alejo_zr.exceldb.Segmento.Rigido;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alejo_zr.exceldb.Pato.patorigi.ConsultaPatologiaRigiActivity;
import com.alejo_zr.exceldb.R;
import com.alejo_zr.exceldb.Pato.patorigi.RegistroPatologiaRigiActivity;
import com.alejo_zr.exceldb.entidades.SegmentoFlex;
import com.alejo_zr.exceldb.entidades.SegmentoRigi;

public class SegmentoRigiActivity extends AppCompatActivity {

    private TextView tv_nombre_carretera_segmento,tv_id_segmento,tvPav, tvnCalzadas, tvnCarriles, tvespesorLosa, tvanchoBerma, tvPRI, tvPRF, tvComentarios,tvFechaSegmentoRigi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segmento_rigi);
        tv_nombre_carretera_segmento = (TextView) findViewById(R.id.tv_nombre_carretera_segmento_Rigi);
        tv_id_segmento = (TextView) findViewById(R.id.tv_id_segmentoFlexRigi);
        tvnCalzadas = (TextView) findViewById(R.id.tvnCalzadasFlex);
        tvnCarriles= (TextView) findViewById(R.id.tvnCarrilesFlex);
        tvespesorLosa= (TextView) findViewById(R.id.tvanchoCarril);
        tvanchoBerma= (TextView) findViewById(R.id.tvanchoBermaFlex);
        tvPRI= (TextView) findViewById(R.id.tvPRIFlex);
        tvPRF= (TextView) findViewById(R.id.tvPRFFlex);
        tvComentarios= (TextView) findViewById(R.id.tvComentariosFlex);
        tvFechaSegmentoRigi = (TextView) findViewById(R.id.tvFechaSegmentoFlex);


        Bundle segmentoEnviado=getIntent().getExtras();
        SegmentoRigi segmento=null;


        if(segmentoEnviado!=null){
            segmento = (SegmentoRigi) segmentoEnviado.getSerializable("segmento");
            tv_id_segmento.setText(segmento.getId_segmento().toString());
            /*tv_nombre_carretera_segmento.setText(segmento.getNombre_carretera().toString());
            tvnCalzadas.setText(segmento.getnCalzadas().toString());
            tvnCarriles.setText(segmento.getnCarriles().toString());
            tvespesorLosa.setText(segmento.getEspesorLosa().toString());
            tvanchoBerma.setText(segmento.getAnchoBerma().toString());
            tvPRI.setText(segmento.getPri().toString());
            tvPRF.setText(segmento.getPrf().toString());
            tvComentarios.setText(segmento.getComentarios().toString());
            tvFechaSegmentoRigi.setText(segmento.getFecha().toString());
            */
        }

    }
    public void onClick(View view) {

        Intent intent = null;
        switch (view.getId()) {



            case R.id.btnRegistrarPatologiaRigi:
                intent = new Intent(SegmentoRigiActivity.this, RegistroPatologiaRigiActivity.class);
                intent.putExtra("id_segmento",tv_id_segmento.getText().toString());
                intent.putExtra("nom_carretera_segmento",tv_nombre_carretera_segmento.getText().toString());
                startActivity(intent);
                break;
            case R.id.btnConsultarPatologiaRigi:
                intent = new Intent(SegmentoRigiActivity.this,ConsultaPatologiaRigiActivity.class);
                intent.putExtra("tv_id_segmento",tv_id_segmento.getText().toString());
                intent.putExtra("tv_nombre_carretera_segmento",tv_nombre_carretera_segmento.getText().toString());
                startActivity(intent);
                break;

            case R.id.btnEditarSegmentoRigi:

                intent = new Intent (SegmentoRigiActivity.this, EditarSegmentoRigiActivity.class);
                intent.putExtra("tv_id_segmento",tv_id_segmento.getText().toString());
                intent.putExtra("tv_nombre_carretera_segmento",tv_nombre_carretera_segmento.getText().toString());
                intent.putExtra("tvnCalzadas" , tvnCalzadas.getText().toString());
                intent.putExtra("tvnCarriles" , tvnCarriles.getText().toString());
                intent.putExtra("tvanchoCarril", tvespesorLosa.getText().toString());
                intent.putExtra("tvanchoBerma", tvanchoBerma.getText().toString());
                intent.putExtra("tvPRI" , tvPRI.getText().toString());
                intent.putExtra("tvPRF" , tvPRF.getText().toString());
                intent.putExtra("tvComentarios" , tvComentarios.getText().toString());
                intent.putExtra("tvFechaSegmentoFlex",tvFechaSegmentoRigi.getText().toString());

                startActivity(intent);
                break;

        }
    }
}


