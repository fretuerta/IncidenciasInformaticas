package com.retuerta.fernando.incidenciasinformaticas;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.retuerta.fernando.incidenciasinformaticas.data.IncidenciasContract;
import com.retuerta.fernando.incidenciasinformaticas.data.IncidenciasDbHelper;

public class GestionUsuariosActivity extends BaseActivity {

    static private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        super.setContentView(R.layout.activity_gestion_usuarios);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        // String user = intent.getStringExtra("user");

        setTitle("Añadir un nuevo usuario");

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_gestion_usuarios, contentFrameLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);

        //Utilizar el dbHelper para escribir en la base de datos
        IncidenciasDbHelper dbHelper = new IncidenciasDbHelper(this);
        mDb = dbHelper.getWritableDatabase();

    }

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
