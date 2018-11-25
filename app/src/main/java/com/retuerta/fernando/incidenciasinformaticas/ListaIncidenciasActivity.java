package com.retuerta.fernando.incidenciasinformaticas;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.FrameLayout;

import com.retuerta.fernando.incidenciasinformaticas.RecyclerViewAdapters.RecyclerViewIncidenciasAdapter;
import com.retuerta.fernando.incidenciasinformaticas.RecyclerViewAdapters.RecyclerViewUsuariosAdapter;
import com.retuerta.fernando.incidenciasinformaticas.data.IncidenciasContract;
import com.retuerta.fernando.incidenciasinformaticas.data.IncidenciasDbHelper;

public class ListaIncidenciasActivity extends BaseActivity {


    Context context;
    RecyclerView recyclerView;
    RecyclerViewIncidenciasAdapter recyclerViewIncidenciasAdapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;

    static private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        super.setContentView(R.layout.activity_lista_incidencias);
        super.onCreate(savedInstanceState);

        context = getApplicationContext();

        Intent intent = getIntent();
        // String user = intent.getStringExtra("user");

        setTitle("Listado de incidencias");

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_lista_incidencias, contentFrameLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);

        //Utilizar el dbHelper para escribir en la base de datos
        IncidenciasDbHelper dbHelper = new IncidenciasDbHelper(context);
        mDb = dbHelper.getWritableDatabase();

        // Leer todos los usuarios
        Cursor cursor = getAllIncidencias();

        recyclerView = (RecyclerView) findViewById(R.id.incidencias_recyclerview);
        recyclerViewLayoutManager = new LinearLayoutManager(context);

        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        recyclerViewIncidenciasAdapter = new RecyclerViewIncidenciasAdapter(context, cursor);
        recyclerView.setAdapter(recyclerViewIncidenciasAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder targer) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                long id = (long) viewHolder.itemView.getTag();
                removeIncidencia(id);
                recyclerViewIncidenciasAdapter.swapCursor(getAllIncidencias());
            }

        }).attachToRecyclerView(recyclerView);

    }

    private static Cursor getAllIncidencias() {
        return mDb.query(
                IncidenciasContract.IncidenciasEntry.TABLE_NAME,
                null, null, null, null,null,
                IncidenciasContract.IncidenciasEntry._ID
        );
    }

    public boolean removeIncidencia(long id) {
        boolean hecho;
        hecho = mDb.delete(IncidenciasContract.IncidenciasEntry.TABLE_NAME,
                IncidenciasContract.IncidenciasEntry._ID + "=" + id, null) > 0;
        // Actualiza el cursor en adapter para actualizar la información del UI
        if (hecho) recyclerViewIncidenciasAdapter.swapCursor(getAllIncidencias());
        return hecho;
    }













}
