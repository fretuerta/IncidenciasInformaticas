package com.retuerta.fernando.incidenciasinformaticas;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.retuerta.fernando.incidenciasinformaticas.RecyclerViewAdapters.RecyclerViewUsuariosAdapter;
import com.retuerta.fernando.incidenciasinformaticas.data.IncidenciasContract;
import com.retuerta.fernando.incidenciasinformaticas.data.IncidenciasDbHelper;

public class ListaUsuariosActivity extends AppCompatActivity {

    Context context;
    RecyclerView recyclerView;
    private static RecyclerViewUsuariosAdapter recyclerViewUsuariosAdapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;

    static private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios);

        context = getApplicationContext();

        Intent intent = getIntent();
        // String user = intent.getStringExtra("user");

        setTitle("Listado de usuarios");

        //Utilizar el dbHelper para escribir en la base de datos
        IncidenciasDbHelper dbHelper = new IncidenciasDbHelper(context);
        mDb = dbHelper.getWritableDatabase();

        // Leer todos los usuarios
        Cursor cursor = getAllUsuarios();

        recyclerView = (RecyclerView) findViewById(R.id.usuarios_recyclerview);
        recyclerViewLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerViewUsuariosAdapter = new RecyclerViewUsuariosAdapter(this, cursor);
        recyclerView.setAdapter(recyclerViewUsuariosAdapter);

    }

    private static Cursor getAllUsuarios() {
        return mDb.query(
                IncidenciasContract.UsuariosEntry.TABLE_NAME,
                null, null, null, null,null,
                IncidenciasContract.UsuariosEntry._ID
        );
    }
}
