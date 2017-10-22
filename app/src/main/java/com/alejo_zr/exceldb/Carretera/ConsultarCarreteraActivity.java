package com.alejo_zr.exceldb.Carretera;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.alejo_zr.exceldb.BaseDatos;
import com.alejo_zr.exceldb.R;
import com.alejo_zr.exceldb.entidades.Carretera;
import com.alejo_zr.exceldb.entidades.SegmentoFlex;
import com.alejo_zr.exceldb.utilidades.Utilidades;

import java.util.ArrayList;

public class ConsultarCarreteraActivity extends AppCompatActivity {

    private ListView listViewCarreteras;
    private ArrayList<String> listaInformacion;
    private ArrayList<Carretera> listaCarreteras;
    private ArrayList<SegmentoFlex> listaSegmentosF;

    BaseDatos baseDatos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_carretera);


        baseDatos=new BaseDatos(this);

        listViewCarreteras= (ListView) findViewById(R.id.listViewCarretera);

        consultarListaCarreteras();
        cargarSegmentosFlex();

        ArrayAdapter adaptador=new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformacion);
        listViewCarreteras.setAdapter(adaptador);

        listViewCarreteras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                Carretera carretera=listaCarreteras.get(pos);

                Intent intent=new Intent(ConsultarCarreteraActivity.this,CarreteraActivity.class);

                Bundle bundle=new Bundle();
                bundle.putSerializable("carretera",carretera);

                intent.putExtras(bundle);
                startActivity(intent);


            }
        });

    }

    protected void onStart() {
        super.onStart();
        baseDatos=new BaseDatos(this);

        listViewCarreteras= (ListView) findViewById(R.id.listViewCarretera);

        consultarListaCarreteras();

        ArrayAdapter adaptador=new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformacion);
        listViewCarreteras.setAdapter(adaptador);

        listViewCarreteras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                Carretera carretera=listaCarreteras.get(pos);

                Intent intent=new Intent(ConsultarCarreteraActivity.this,CarreteraActivity.class);

                Bundle bundle=new Bundle();
                bundle.putSerializable("carretera",carretera);

                intent.putExtras(bundle);
                startActivity(intent);


            }
        });
    }

    private void consultarListaCarreteras() {
        SQLiteDatabase db=baseDatos.getReadableDatabase();

        Carretera carretera=null;
        listaCarreteras= new ArrayList<Carretera>();
        //select * from carretera
        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades.CARRETERA.TABLA_CARRETERA,null);

        while (cursor.moveToNext()){
            carretera=new Carretera();
            carretera.setId(cursor.getInt(0));
            carretera.setNombreCarretera(cursor.getString(1));
            carretera.setCodCarretera(cursor.getString(2));
            carretera.setTerritorial(cursor.getString(3));
            carretera.setAdmon(cursor.getString(4));
            carretera.setLevantado(cursor.getString(5));

            listaCarreteras.add(carretera);
        }
        obtenerLista();
    }

    private void cargarSegmentosFlex() {

        SQLiteDatabase db=baseDatos.getReadableDatabase();

        SegmentoFlex segmento=null;
        listaSegmentosF= new ArrayList<SegmentoFlex>();
        //select * from carretera
        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades.SEGMENTOFLEX.TABLA_SEGMENTO,null);


        while(cursor.moveToNext()){
            segmento = new SegmentoFlex();

            segmento.setId_segmento(cursor.getInt(0));
            segmento.setNombre_carretera(cursor.getString(1));
            segmento.setnCalzadas(cursor.getString(2));
            segmento.setnCarriles(cursor.getString(3));
            segmento.setAnchoCarril(cursor.getString(4));
            segmento.setAnchoBerma(cursor.getString(5));
            segmento.setPri(cursor.getString(6));
            segmento.setPrf(cursor.getString(7));
            segmento.setComentarios(cursor.getString(8));
            segmento.setFecha(cursor.getString(9));

            listaSegmentosF.add(segmento);
        }
        editarIdSegFlex();
    }

    private void editarIdSegFlex() {

        SQLiteDatabase db = baseDatos.getWritableDatabase();
        int id=1;
        for (int i=0; i<listaSegmentosF.size();i++) {

            int idSegmento = listaSegmentosF.get(i).getId_segmento();
            int modulo = idSegmento / id;
            Toast.makeText(getApplicationContext(), "Id " + id + "idS" + idSegmento + "M" + modulo, Toast.LENGTH_SHORT).show();
            if (modulo != 1) {
                Toast.makeText(getApplicationContext(), "Entra If", Toast.LENGTH_SHORT).show();
                String segmentoId;
                segmentoId = ("" + id);
                Toast.makeText(getApplicationContext(), "segId " + segmentoId, Toast.LENGTH_SHORT).show();
                String[] parametrosSF = {segmentoId};
                ContentValues values = new ContentValues();
                values.put(Utilidades.SEGMENTOFLEX.CAMPO_ID_SEGMENTO, segmentoId.toString());
                db.update(Utilidades.SEGMENTOFLEX.TABLA_SEGMENTO, values, Utilidades.SEGMENTOFLEX.CAMPO_ID_SEGMENTO + "=?", parametrosSF);
                id = id + 1;
            }
        }
    }

    private void obtenerLista() {
        listaInformacion=new ArrayList<String>();

        for (int i=0; i<listaCarreteras.size();i++){
            listaInformacion.add(listaCarreteras.get(i).getNombreCarretera()+" - "+listaCarreteras.get(i).getCodCarretera()+" - "
                    +listaCarreteras.get(i).getTerritorial());
        }

    }

}



