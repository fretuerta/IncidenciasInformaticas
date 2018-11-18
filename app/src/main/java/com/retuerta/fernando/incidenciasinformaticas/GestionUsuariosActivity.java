package com.retuerta.fernando.incidenciasinformaticas;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.retuerta.fernando.incidenciasinformaticas.data.IncidenciasContract;
import com.retuerta.fernando.incidenciasinformaticas.data.IncidenciasDbHelper;

public class GestionUsuariosActivity extends AppCompatActivity {

    static private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_usuarios);

        Intent intent = getIntent();
        // String user = intent.getStringExtra("user");

        setTitle("Añadir un nuevo usuario");

        //Utilizar el dbHelper para escribir en la base de datos
        IncidenciasDbHelper dbHelper = new IncidenciasDbHelper(this);
        mDb = dbHelper.getWritableDatabase();

    }
    /*
            public static final String COLUMN_NOMBRE = "nombre";
        public static final String COLUMN_APELLIDOS = "apellidos";
        public static final String COLUMN_DNI = "dni";
        public static final String COLUMN_NOMBRE_USUARIO = "nombre_usuario";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_FOTO = "foto";
        public static final String COLUMN_TIPO_USUARIO = "tipo_usuario";
    */

    public void addUsuarioToTable(String nombre, String apellidos, String dni, String nombreUsuario,
                                  String password, String foto, String tipoUsuario) {
        if (dni.length() == 0 || nombreUsuario.length() == 0) { return; }

        ContentValues cv = new ContentValues();
        cv.put(IncidenciasContract.UsuariosEntry.COLUMN_NOMBRE, nombre);
        cv.put(IncidenciasContract.UsuariosEntry.COLUMN_APELLIDOS, apellidos);
        cv.put(IncidenciasContract.UsuariosEntry.COLUMN_DNI, dni);
        cv.put(IncidenciasContract.UsuariosEntry.COLUMN_NOMBRE_USUARIO, nombreUsuario);
        cv.put(IncidenciasContract.UsuariosEntry.COLUMN_PASSWORD, password);
        cv.put(IncidenciasContract.UsuariosEntry.COLUMN_FOTO, foto);
        cv.put(IncidenciasContract.UsuariosEntry.COLUMN_TIPO_USUARIO, tipoUsuario);

        mDb.insert(IncidenciasContract.UsuariosEntry.TABLE_NAME, null, cv);

        Intent intent = new Intent(getApplicationContext(), ListaUsuariosActivity.class);
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
