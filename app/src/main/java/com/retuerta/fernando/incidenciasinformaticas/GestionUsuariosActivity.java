package com.retuerta.fernando.incidenciasinformaticas;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.retuerta.fernando.incidenciasinformaticas.data.IncidenciasContract;
import com.retuerta.fernando.incidenciasinformaticas.data.IncidenciasDbHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class GestionUsuariosActivity extends BaseActivity {

    Context context;
    static private SQLiteDatabase mDb;
    private static final String CARPETA_IMAGENES = "IncidenciasInformaticas/images";
    private static final int SELECT_PHOTO = 1;
    private static final int CAPTURE_PHOTO = 2;

    private String path;
    File fileImage;

    ImageView imgFoto;
    Bitmap thumbnail;

    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        context = getApplicationContext();

        Intent intent = getIntent();
        id = intent.getLongExtra("id", -1);

        setTitle("Añadir un nuevo usuario");

        imgFoto = (ImageView) findViewById(R.id.image_view_foto);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_gestion_usuarios, contentFrameLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);

        //Utilizar el dbHelper para escribir en la base de datos
        IncidenciasDbHelper dbHelper = new IncidenciasDbHelper(context);
        mDb = dbHelper.getWritableDatabase();

        Button botonAddUsuario = findViewById(R.id.button_guardar_usuario);
        botonAddUsuario.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { guardarUsuario(); }
        });

        Button botonTakePhoto = findViewById(R.id.button_take_image);
        botonTakePhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { abrirCamara(); }
        });

        if (id >= 0) { setDataById(); }
    }

    void abrirCamara() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAPTURE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_PHOTO && resultCode == Activity.RESULT_OK) {
            thumbnail = (Bitmap) data.getExtras().get("data");

            imgFoto = (ImageView) findViewById(R.id.image_view_foto);
            imgFoto.setMaxWidth(200);
            imgFoto.setImageBitmap(Bitmap.createScaledBitmap(thumbnail, 120, 120, false));
        }
    }

    void setDataById() {

        TextView nombreTV = (TextView) findViewById(R.id.editText_nombre);
        TextView apellidosTV = (TextView) findViewById(R.id.editText_apellidos);
        TextView dniTV = (TextView) findViewById(R.id.editText_dni);
        TextView nombreUsuarioTV = (TextView) findViewById(R.id.editText_nombre_usuario);
        TextView passwordTV = (TextView) findViewById(R.id.editText_password);
        ImageView fotoIV = (ImageView) findViewById(R.id.image_view_foto);
        TextView tipoUsuarioTV = (TextView) findViewById(R.id.editText_tipo_usuario);

        Cursor cursor = mDb.rawQuery("SELECT * FROM " + IncidenciasContract.UsuariosEntry.TABLE_NAME +
                " WHERE " + IncidenciasContract.UsuariosEntry._ID + " = " + id.toString() + ";", null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                nombreTV.setText(cursor.getString(1));
                apellidosTV.setText(cursor.getString(2));
                dniTV.setText(cursor.getString(3));
                nombreUsuarioTV.setText(cursor.getString(4));
                passwordTV.setText(cursor.getString(5));
                byte[] foto = cursor.getBlob(6);
                Bitmap fotoBitmap = null;
                if (foto != null) fotoBitmap = BitmapFactory.decodeByteArray(foto, 0, foto.length);
                if (fotoBitmap != null) fotoIV.setImageBitmap(Bitmap.createScaledBitmap(fotoBitmap, 120, 120, false));
                tipoUsuarioTV.setText(cursor.getString(7));
            }
        }
    }

    void guardarUsuario() {

        TextView nombreTV = (TextView) findViewById(R.id.editText_nombre);
        TextView apellidosTV = (TextView) findViewById(R.id.editText_apellidos);
        TextView dniTV = (TextView) findViewById(R.id.editText_dni);
        TextView nombreUsuarioTV = (TextView) findViewById(R.id.editText_nombre_usuario);
        TextView passwordTV = (TextView) findViewById(R.id.editText_password);
        TextView tipoUsuarioTV = (TextView) findViewById(R.id.editText_tipo_usuario);

        String nombre = nombreTV.getText().toString();
        String apellidos = apellidosTV.getText().toString();
        String dni = dniTV.getText().toString();
        String nombreUsuario = nombreUsuarioTV.getText().toString();
        String password = passwordTV.getText().toString();
        String tipoUsuario = tipoUsuarioTV.getText().toString();

        imgFoto = (ImageView) findViewById(R.id.image_view_foto);
        imgFoto.setDrawingCacheEnabled(true);
        Bitmap bitmap = imgFoto.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] foto = baos.toByteArray();


        this.addUsuarioToTable(nombre, apellidos, dni, nombreUsuario, password,
                foto, tipoUsuario);
    }

    public void addUsuarioToTable(String nombre, String apellidos, String dni, String nombreUsuario,
                                  String password, byte[] foto, String tipoUsuario) {
        if (dni.length() == 0 || nombreUsuario.length() == 0) {
            Toast.makeText(context, "Debe completar el Nombre de Usuario y el DNI.", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues cv = new ContentValues();
        cv.put(IncidenciasContract.UsuariosEntry.COLUMN_NOMBRE, nombre);
        cv.put(IncidenciasContract.UsuariosEntry.COLUMN_APELLIDOS, apellidos);
        cv.put(IncidenciasContract.UsuariosEntry.COLUMN_DNI, dni);
        cv.put(IncidenciasContract.UsuariosEntry.COLUMN_NOMBRE_USUARIO, nombreUsuario);
        cv.put(IncidenciasContract.UsuariosEntry.COLUMN_PASSWORD, password);
        cv.put(IncidenciasContract.UsuariosEntry.COLUMN_FOTO, foto);
        cv.put(IncidenciasContract.UsuariosEntry.COLUMN_TIPO_USUARIO, tipoUsuario);

        if (id >= 0) {
            mDb.update(IncidenciasContract.UsuariosEntry.TABLE_NAME, cv,
                    IncidenciasContract.UsuariosEntry._ID + "=" + id.toString(), null);
        } else {
            if (isUsuarioInTable(dni, nombreUsuario)) {
                Toast.makeText(context, "Usuario repetido", Toast.LENGTH_SHORT).show();

            } else {
                Long resultado = mDb.insert(IncidenciasContract.UsuariosEntry.TABLE_NAME, null, cv);
                if (resultado > 0) Toast.makeText(context, "Usuario insertado", Toast.LENGTH_SHORT).show();
            }
        }

        Intent intent = new Intent(context, ListaUsuariosActivity.class);
        startActivity(intent);

    }

    public boolean isUsuarioInTable(String dni, String nombreUsuario) {
        Cursor mCount = mDb.rawQuery("SELECT COUNT(*) FROM " + IncidenciasContract.UsuariosEntry.TABLE_NAME +
                        " WHERE " + IncidenciasContract.UsuariosEntry.COLUMN_DNI + "='" + dni + "'" +
                        " OR " + IncidenciasContract.UsuariosEntry.COLUMN_NOMBRE_USUARIO + "='" + nombreUsuario + "'"
                , null);
        mCount.moveToFirst();
        int count = mCount.getInt(0);
        mCount.close();
        return count > 0;

    }
}
