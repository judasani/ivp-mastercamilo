package com.alejo_zr.exceldb.Pato.patorigi;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alejo_zr.exceldb.BaseDatos;
import com.alejo_zr.exceldb.R;
import com.alejo_zr.exceldb.utilidades.Utilidades;

import java.io.File;

import fr.ganfra.materialspinner.MaterialSpinner;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class RegistroPatologiaRigiActivity extends AppCompatActivity {

    private final String CARPETA_RAIZ="InventarioVial/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"PavimentoRigido";

    final int COD_SELECCIONA=10;
    final int COD_FOTO=20;

    private Button btnRegistrarPatologia;
    private ImageButton botonCargar;
    private ImageView imagen;
    private String path;
    private String idFotoRigi,campoSeveridad;

    private MaterialSpinner spinnerPatoRigi,spinnerSeveridadPatoRigiRegistro;
    private TextView tv_nombre_carretera_patologia,tv_id_segmento_patologia_Rigi,tv_foto_danio,tv_idFotoRigi,tv_foto_nombre,ej_Pato_Rigi;
    private TextInputLayout input_campoAbscisaRigi,input_campoCarrilPato,input_campoDanioPatoRigi,input_campoLargoDanio,input_campoAnchoDanio,input_campoSeveridad,
            input_campoidFotoRigi;
    private EditText campoCarrilPato, campoDanioPatoRigi, campoLargoDanio, campoAnchoDanio, campoLargoRepa, campoAnchoRepa, campoAclaracion,campoAbscisaRigi,
            campoLatitudPatoRigi,campoLongitudPatoRigi;
    private String[] tipoDanioRigi = { "Grieta de esquina", "Grieta longitudinal",
            "Grieta Transversal", "Grieta en los extremos de los pasadores", "Grieta en bloque", "Grieta en pozos y sumideros", "separacion de juntas longitudinales",
            "Deterioro de sello", "Desportillamiento de juntas", "Descascaramiento", "Desintegracion", "Baches", "Pulimiento", "Escalonamiento de juntas longitudinales y transversale",
            "Levantamiento localizado", "Parche", "Hundimineto O Asentamiento", "Fisuracion por retraccion", "Fisuras ligeras de aparicion temprana", "Fisuracion por durabilidad", "Bombeo sobre la junta transversal/longitudinal", "Surcos",
            "Ondulaciones", "Descenso de la berma", "Separacion entre berma y pavimento"};
    private String[] severidad = {"Alta", "Media", "Baja", "No aplica"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_patologia_rigi);


        botonCargar= (ImageButton) findViewById(R.id.btnDanio);
        if(validaPermisos()){
            botonCargar.setEnabled(true);
        }else{
            botonCargar.setEnabled(false);
        }
        imagen= (ImageView) findViewById(R.id.imagemId);

        btnRegistrarPatologia= (Button) findViewById(R.id.btnRegistroPatologia);

        spinnerSeveridadPatoRigiRegistro = (MaterialSpinner) findViewById(R.id.spinnerSeveridadPatoRigiRegistro);
        spinnerPatoRigi = (MaterialSpinner) findViewById(R.id.spinnerPatoRigi);
        campoCarrilPato = (EditText) findViewById(R.id.campoCarrilPatoRigi);
        campoDanioPatoRigi = (EditText) findViewById(R.id.campoDanioPatoRigi);
        campoLargoDanio = (EditText) findViewById(R.id.campoLargoDanioRigi);
        campoAnchoDanio = (EditText) findViewById(R.id.campoAnchoDanioRigi);
        campoLargoRepa = (EditText) findViewById(R.id.campoLargoRepaRigi);
        campoAnchoRepa = (EditText) findViewById(R.id.campoAnchoRepaRigi);
        campoAclaracion = (EditText) findViewById(R.id.campoAclaracionesRigi);
        campoAbscisaRigi = (EditText) findViewById(R.id.campoAbscisaRigi);
        campoLatitudPatoRigi = (EditText) findViewById(R.id.campoLatitudPatoRigi);
        campoLongitudPatoRigi = (EditText) findViewById(R.id.campolongitudPatoRigi);
        tv_nombre_carretera_patologia = (TextView) findViewById(R.id.tv_nombre_carretera_patologia_Rigi);
        tv_id_segmento_patologia_Rigi = (TextView) findViewById(R.id.tv_id_segmento_patologia_Rigi_registro);
        tv_foto_danio = (TextView) findViewById(R.id.tv_foto_danioRigi);
        tv_idFotoRigi = (TextView) findViewById(R.id.tv_idFotoRigi);
        tv_foto_nombre = (TextView) findViewById(R.id.tv_foto_nombreRigi);
        ej_Pato_Rigi = (TextView) findViewById(R.id.ej_Pato_Rigi);
        input_campoAbscisaRigi = (TextInputLayout) findViewById(R.id.input_campoAbscisaRigi);
        input_campoCarrilPato = (TextInputLayout) findViewById(R.id.input_campoCarrilPatoRigi);
        input_campoDanioPatoRigi = (TextInputLayout) findViewById(R.id.input_campoDanioPatoRigi);
        input_campoLargoDanio = (TextInputLayout) findViewById(R.id.input_campoLargoDanioRigi);
        input_campoAnchoDanio = (TextInputLayout) findViewById(R.id.input_campoAnchoDanioRigi);
        input_campoSeveridad = (TextInputLayout) findViewById(R.id.input_campoSeveridadRigi);
        input_campoidFotoRigi = (TextInputLayout) findViewById(R.id.input_campoidFotoRigi);

        Bundle bundle = getIntent().getExtras();
        String dato_nom_carretera = bundle.getString("nom_carretera_segmento");
        String id_segmento = bundle.getString("id_segmento");
        tv_nombre_carretera_patologia.setText(dato_nom_carretera);
        tv_id_segmento_patologia_Rigi.setText(id_segmento);

        ArrayAdapter<String> arrayAdapterPato = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, tipoDanioRigi);
        spinnerPatoRigi.setAdapter(arrayAdapterPato);
        ArrayAdapter<String> arrayAdapterSeveridad = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, severidad);
        spinnerSeveridadPatoRigiRegistro.setAdapter(arrayAdapterSeveridad);

        spinnerSeveridadPatoRigiRegistro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {


                    case 0:
                        campoSeveridad = "A";
                        break;
                    case 1:
                        campoSeveridad = "M";
                        break;
                    case 2:
                        campoSeveridad = "B";
                        break;
                    case 3:
                        campoSeveridad="N.A";
                        break;
                    default:
                        campoSeveridad="";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerPatoRigi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position){

                    case 0:
                        //No se ha seleccionado el Spinner
                        break;
                    case 1:
                        campoDanioPatoRigi.setText(R.string.grieta_esquina);
                        break;
                    case 2:
                        campoDanioPatoRigi.setText(R.string.grieta_longitudinal);
                        break;
                    case 3:
                        campoDanioPatoRigi.setText(R.string.grieta_transversal);
                        break;
                    case 4:
                        campoDanioPatoRigi.setText(R.string.grieta_pasadores);
                        break;
                    case 5:
                        campoDanioPatoRigi.setText(R.string.grietas_bloque);
                        break;
                    case 6:
                        campoDanioPatoRigi.setText(R.string.grietas_pozos);
                        break;
                    case 7:
                        campoDanioPatoRigi.setText(R.string.separacion_juntas);
                        break;
                    case 8:
                        campoDanioPatoRigi.setText(R.string.deterioro_sello);
                        break;
                    case 9:
                        campoDanioPatoRigi.setText(R.string.desportillamiento_juntas);
                        break;
                    case 10:
                        campoDanioPatoRigi.setText(R.string.descascaramiento);
                        break;
                    case 11:
                        campoDanioPatoRigi.setText(R.string.desintegracion);
                        break;
                    case 12:
                        campoDanioPatoRigi.setText(R.string.baches);
                        break;
                    case 13:
                        campoDanioPatoRigi.setText(R.string.pulimiento);
                        break;
                    case 14:
                        campoDanioPatoRigi.setText(R.string.escalonamiento_juntas);
                        break;
                    case 15:
                        campoDanioPatoRigi.setText(R.string.levantamiento_localizado);
                        break;
                    case 16:
                        campoDanioPatoRigi.setText(R.string.parches);
                        break;
                    case 17:
                        campoDanioPatoRigi.setText(R.string.hundimientos);
                        break;
                    case 18:
                        campoDanioPatoRigi.setText(R.string.fisuracion_retraccion);
                        break;
                    case 19:
                        campoDanioPatoRigi.setText(R.string.fisuras_ligeras);
                        break;
                    case 20:
                        campoDanioPatoRigi.setText(R.string.fisuracion_durabilidad);
                        break;
                    case 21:
                        campoDanioPatoRigi.setText(R.string.bombeo_juntas);
                        break;
                    case 22:
                        campoDanioPatoRigi.setText(R.string.ondulaciones);
                        break;
                    case 23:
                        campoDanioPatoRigi.setText(R.string.descenso_berma);
                        break;
                    case 24:
                        campoDanioPatoRigi.setText(R.string.separacion_berma_pavimento);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }



    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnRegistroPatologiaRigi:
                verificarDatosPatoRigi();
                break;
            case R.id.btnDanioRigi:
                guardarFotografia();
                tomarFotografia();
                break;
            case R.id.btnObtenerCoordenadasPatoRigi:
                obtenerCoordenadas();
                break;
            case R.id.ej_Pato_Rigi:
                Intent intent = new Intent(RegistroPatologiaRigiActivity.this, RegistroPatologiaRigiEjemploActivity.class);
                startActivity(intent);
                break;
        }

    }

    private void verificarDatosPatoRigi() {
        boolean isValid = true;
        if(campoAbscisaRigi.getText().toString().trim().isEmpty()){
            input_campoAbscisaRigi.setError("Ingrese la abscisa");
            isValid=false;
        }else{
            input_campoAbscisaRigi.setErrorEnabled(false);
        }
        if(campoCarrilPato.getText().toString().trim().isEmpty()){
            input_campoCarrilPato.setError("Ingrese el caril");
            isValid=false;
        }else{
            input_campoCarrilPato.setErrorEnabled(false);
        }
        if(campoDanioPatoRigi.getText().toString().trim().isEmpty()){
            input_campoDanioPatoRigi.setError("Seleccion el daño");
            isValid=false;
        }else{
            input_campoDanioPatoRigi.setErrorEnabled(false);
        }
        if(campoSeveridad.trim().isEmpty()){
            input_campoSeveridad.setError("Ingrese la severidad");
            isValid=false;
        }else{
            input_campoSeveridad.setErrorEnabled(false);
        }
        if(campoLargoDanio.getText().toString().trim().isEmpty()){
            input_campoLargoDanio.setError("Ingrese el largo del daño");
            isValid=false;
        }else{
            input_campoLargoDanio.setErrorEnabled(false);
        }
        if(tv_idFotoRigi.getText().toString().trim().isEmpty()){
            input_campoidFotoRigi.setError("Tome la foto del daño");
            isValid=false;
        }else{
            input_campoidFotoRigi.setErrorEnabled(false);
        }

        if(isValid){
            registrarPatoRigi();
        }

    }

    private void registrarPatoRigi() {

        BaseDatos bd=new BaseDatos(this);

        SQLiteDatabase db=bd.getWritableDatabase();

        String insert="INSERT INTO "+ Utilidades.PATOLOGIARIGI.TABLA_PATOLOGIA
                +" ( " +Utilidades.PATOLOGIARIGI.CAMPO_NOMBRE_CARRETERA_PATOLOGIA+","+Utilidades.PATOLOGIARIGI.CAMPO_ID_SEGMENTO_PATOLOGIA+","+
                Utilidades.PATOLOGIARIGI.CAMPO_ABSCISA_PATOLOGIA+","+Utilidades.PATOLOGIARIGI.CAMPO_LATITUD+","+Utilidades.PATOLOGIARIGI.CAMPO_LONGITUD+","
                +Utilidades.PATOLOGIARIGI.CAMPO_CARRIL_PATOLOGIA+","+Utilidades.PATOLOGIARIGI.CAMPO_DANIO_PATOLOGIA+","+Utilidades.PATOLOGIARIGI.CAMPO_SEVERIDAD+","
                +Utilidades.PATOLOGIARIGI.CAMPO_LARGO_PATOLOGIA+","+Utilidades.PATOLOGIARIGI.CAMPO_ANCHO_PATOLOGIA+","+Utilidades.PATOLOGIARIGI.CAMPO_LARGO_REPARACION
                +"," +Utilidades.PATOLOGIARIGI.CAMPO_ANCHO_REPARACION+","+Utilidades.PATOLOGIARIGI.CAMPO_ACLARACIONES+","+Utilidades.PATOLOGIARIGI.CAMPO_NOMBRE_FOTO+","
                +Utilidades.PATOLOGIARIGI.CAMPO_FOTO_DANIO+")" +
                " VALUES ('"+tv_nombre_carretera_patologia.getText().toString()+"' , '"+tv_id_segmento_patologia_Rigi.getText().toString()+"' , '"+
                campoAbscisaRigi.getText().toString()+"' , '"+campoLatitudPatoRigi.getText().toString()+"' , '"+campoLongitudPatoRigi.getText().toString()+"' , '"
                +campoCarrilPato.getText().toString()+"' , '"+campoDanioPatoRigi.getText().toString()+"' , '"+campoSeveridad+"' , '"
                +campoLargoDanio.getText().toString()+"' , '"+campoAnchoDanio.getText().toString()+"' , '"+campoLargoRepa.getText().toString()+"' , '"
                +campoAnchoRepa.getText().toString()+"' , '"+campoAclaracion.getText().toString()+"' , '"+tv_foto_nombre.getText().toString()+"' , '"
                +tv_foto_danio.getText().toString()+"')";

        Toast.makeText(getApplicationContext(),"Se registro el Daño: "+campoDanioPatoRigi.getText().toString(),Toast.LENGTH_SHORT).show();
        db.execSQL(insert);

        db.close();

    }

    private void guardarFotografia() {

        BaseDatos bd=new BaseDatos(this);

        SQLiteDatabase db=bd.getWritableDatabase();


        String insert="INSERT INTO "+ Utilidades.FOTORIGI.TABLA_FOTORIGI
                +" ( " +Utilidades.FOTORIGI.CAMPO_NOMBRE_CARRETERA_FOTORIGI+","+Utilidades.FOTORIGI.CAMPO_ID_SEGMENTO_FOTORIGI+")" +
                " VALUES ('"+tv_nombre_carretera_patologia.getText().toString()+"' , '"+tv_id_segmento_patologia_Rigi.getText().toString()+"')";


        db.execSQL(insert);

        BaseDatos baseDatos = new BaseDatos(this);
        final Cursor cursor = baseDatos.getfotoFLex();

        if(cursor.moveToNext()){
            do{
                idFotoRigi = cursor.getString(cursor.getColumnIndex(Utilidades.FOTOFLEX.CAMPO_ID_FOTO));
            }while (cursor.moveToNext());
            tv_idFotoRigi.setText(idFotoRigi);
        }

        db.close();

    }

    private void tomarFotografia() {
        File fileImagen=new File(Environment.getExternalStorageDirectory(),RUTA_IMAGEN);
        boolean isCreada=fileImagen.exists();
        String nombreImagen="";
        if(isCreada==false){
            isCreada=fileImagen.mkdirs();
        }

        if(isCreada==true){
            nombreImagen=("Carretera_"+tv_nombre_carretera_patologia.getText().toString()+"_Segmento_"+tv_id_segmento_patologia_Rigi.getText().toString()+"_Foto_"+tv_idFotoRigi.getText().toString()+".png");
        }

        tv_foto_nombre.setText(nombreImagen);
        path=Environment.getExternalStorageDirectory()+
                File.separator+RUTA_IMAGEN+File.separator+nombreImagen;

        tv_foto_danio.setText(path);


        File imagen=new File(path);

        Intent intent=null;
        intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ////
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
        {
            String authorities=getApplicationContext().getPackageName()+".provider";
            Uri imageUri= FileProvider.getUriForFile(this,authorities,imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        }else
        {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        }
        startActivityForResult(intent,COD_FOTO);

        ////
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){

            switch (requestCode){
                case COD_SELECCIONA:
                    Uri miPath=data.getData();
                    imagen.setImageURI(miPath);
                    break;

                case COD_FOTO:
                    MediaScannerConnection.scanFile(this, new String[]{path}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("Ruta de almacenamiento","Path: "+path);
                                }
                            });

                    Bitmap bitmap= BitmapFactory.decodeFile(path);
                    imagen.setImageBitmap(bitmap);

                    break;
            }


        }
    }

    private boolean validaPermisos() {

        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return true;
        }

        if((checkSelfPermission(CAMERA)== PackageManager.PERMISSION_GRANTED)&&
                (checkSelfPermission(WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)){
            return true;
        }

        if((shouldShowRequestPermissionRationale(CAMERA)) ||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))){
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
        }

        return false;
    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(RegistroPatologiaRigiActivity.this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
            }
        });
        dialogo.show();
    }

    private void solicitarPermisosManual() {
        final CharSequence[] opciones={"si","no"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(RegistroPatologiaRigiActivity.this);
        alertOpciones.setTitle("¿Desea configurar los permisos de forma manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("si")){
                    Intent intent=new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri=Uri.fromParts("package",getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Los permisos no fueron aceptados",Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
    }
    private void obtenerCoordenadas() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        RegistroPatologiaRigiActivity.Localizacion Local = new RegistroPatologiaRigiActivity.Localizacion();
        Local.setRegistroPatologiaRigiActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 50, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 50, (LocationListener) Local);


    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                obtenerCoordenadas();
                return;
            }
        }
    }

    private class Localizacion implements LocationListener {
        RegistroPatologiaRigiActivity registroPatologiaRigiActivity;

        public RegistroPatologiaRigiActivity getRegistroPatologiaRigiActivity() {
            return registroPatologiaRigiActivity;
        }

        public void setRegistroPatologiaRigiActivity(RegistroPatologiaRigiActivity registroPatologiaRigiActivity) {
            this.registroPatologiaRigiActivity = registroPatologiaRigiActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion

            loc.getLatitude();
            loc.getLongitude();

            String latitud = ""+ loc.getLatitude();// + "\n Long = " + loc.getLongitude();
            String longitud = ""+loc.getLongitude();
            campoLatitudPatoRigi.setText(latitud);
            campoLongitudPatoRigi.setText(longitud);

        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            //mensaje1.setText("GPS Desactivado");
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            //mensaje1.setText("GPS Activado");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    }

}
