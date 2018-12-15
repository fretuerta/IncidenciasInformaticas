package com.retuerta.fernando.incidenciasinformaticas;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.retuerta.fernando.incidenciasinformaticas.data.IncidenciasContract;
import com.retuerta.fernando.incidenciasinformaticas.data.IncidenciasDbHelper;

public class GestionIncidenciasActivity extends BaseActivity {

    Context context;
    static private SQLiteDatabase mDb;
    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        context = getApplicationContext();

        Intent intent = getIntent();
        id = intent.getLongExtra("id", -1);

        setTitle("Añadir una nueva incidencia");

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_gestion_incidencias, contentFrameLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);

        //Utilizar el dbHelper para escribir en la base de datos
        IncidenciasDbHelper dbHelper = new IncidenciasDbHelper(context);
        mDb = dbHelper.getWritableDatabase();

        Button botonAddIncidencia = findViewById(R.id.button_guardar_incidencia);
        botonAddIncidencia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                guardarIncidencia();
            }
        });

        if (id >= 0) { setDataById(); }

        restringirEdicion();
    }

    void restringirEdicion() {
        if (!this.tipoUsuarioLogeado.equals("Informático")) {
            TextView dniInformaticoTV = (TextView) findViewById(R.id.editText_i_dni_informatico);
            TextView estadoTV = (TextView) findViewById(R.id.editText_i_estado);
            TextView fechaResolucionTV = (TextView) findViewById(R.id.editText_i_fecha_resolucion);
            TextView observacionesInformaticoTV = (TextView) findViewById(R.id.editText_i_observaciones_informatico);
            dniInformaticoTV.setVisibility(View.INVISIBLE);
            estadoTV.setVisibility(View.INVISIBLE);
            fechaResolucionTV.setVisibility(View.INVISIBLE);
            observacionesInformaticoTV.setVisibility(View.INVISIBLE);
        }
    }

    void setDataById() {

        TextView dniTV = (TextView) findViewById(R.id.editText_i_dni);
        TextView fechaIncidenciaTV = (TextView) findViewById(R.id.editText_i_fecha);
        TextView observacionesTV = (TextView) findViewById(R.id.editText_i_observaciones);
        TextView dniInformaticoTV = (TextView) findViewById(R.id.editText_i_dni_informatico);
        TextView estadoTV = (TextView) findViewById(R.id.editText_i_estado);
        TextView fechaResolucionTV = (TextView) findViewById(R.id.editText_i_fecha_resolucion);
        TextView observacionesInformaticoTV = (TextView) findViewById(R.id.editText_i_observaciones_informatico);

        Cursor cursor = mDb.rawQuery("SELECT * FROM " + IncidenciasContract.IncidenciasEntry.TABLE_NAME +
                " WHERE " + IncidenciasContract.IncidenciasEntry._ID + " = " + id.toString() + ";", null );

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                dniTV.setText(cursor.getString(1));
                fechaIncidenciaTV.setText(cursor.getString(2));
                observacionesTV.setText(cursor.getString(3));
                dniInformaticoTV.setText(cursor.getString(4));
                estadoTV.setText(cursor.getString(5));
                fechaResolucionTV.setText(cursor.getString(6));
                observacionesInformaticoTV.setText(cursor.getString(7));
            }
        }
    }

    void guardarIncidencia() {

        TextView dniTV = (TextView) findViewById(R.id.editText_i_dni);
        TextView fechaIncidenciaTV = (TextView) findViewById(R.id.editText_i_fecha);
        TextView observacionesTV = (TextView) findViewById(R.id.editText_i_observaciones);
        TextView dniInformaticoTV = (TextView) findViewById(R.id.editText_i_dni_informatico);
        TextView estadoTV = (TextView) findViewById(R.id.editText_i_estado);
        TextView fechaResolucionTV = (TextView) findViewById(R.id.editText_i_fecha_resolucion);
        TextView observacionesInformaticoTV = (TextView) findViewById(R.id.editText_i_observaciones_informatico);

        String dni = dniTV.getText().toString();
        String fechaIncidencia = fechaIncidenciaTV.getText().toString();
        String observaciones = observacionesTV.getText().toString();
        String dniInformatico = dniInformaticoTV.getText().toString();
        String estado = estadoTV.getText().toString();
        String fechaResolucion = fechaResolucionTV.getText().toString();
        String observacionesInformatico = observacionesInformaticoTV.getText().toString();

        this.addIncidenciaToTable(dni, fechaIncidencia, observaciones, dniInformatico, estado,
                fechaResolucion, observacionesInformatico);
    }

    public void addIncidenciaToTable(String dni, String fechaIncidencia, String observaciones, String dniInformatico,
                                  String estado, String fechaResolucion, String observacionesInformatico) {

        ContentValues cv = new ContentValues();
        cv.put(IncidenciasContract.IncidenciasEntry.COLUMN_DNI, dni);
        cv.put(IncidenciasContract.IncidenciasEntry.COLUMN_FECHA_INCIDENCIA, fechaIncidencia);
        cv.put(IncidenciasContract.IncidenciasEntry.COLUMN_OBSERVACIONES, observaciones);
        cv.put(IncidenciasContract.IncidenciasEntry.COLUMN_DNI_INFORMATICO, dniInformatico);
        cv.put(IncidenciasContract.IncidenciasEntry.COLUMN_ESTADO_INCIDENCIA, estado);
        cv.put(IncidenciasContract.IncidenciasEntry.COLUMN_FECHA_RESOLUCION_INCIDENCIA, fechaResolucion);
        cv.put(IncidenciasContract.IncidenciasEntry.COLUMN_OBSERVACIONES_INFORMATICO, observacionesInformatico);

        if (id >= 0) {
            mDb.update(IncidenciasContract.IncidenciasEntry.TABLE_NAME, cv,
                    IncidenciasContract.IncidenciasEntry._ID + "=" + id.toString(), null);
        } else {
            Long resultado = mDb.insert(IncidenciasContract.IncidenciasEntry.TABLE_NAME, null, cv);
            if (resultado > 0) Toast.makeText(context, "Incidencia insertada", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(context, ListaIncidenciasActivity.class);
        startActivity(intent);

    }

    public boolean isIncidenciaInTable(String dni, String nombreUsuario) {
        Cursor mCount = mDb.rawQuery("SELECT COUNT(*) FROM " + IncidenciasContract.IncidenciasEntry.TABLE_NAME +
                        " WHERE " + IncidenciasContract.IncidenciasEntry.COLUMN_DNI + "='" + dni + "'"
                , null);
        mCount.moveToFirst();
        int count = mCount.getInt(0);
        mCount.close();
        return count > 0;

    }
}
