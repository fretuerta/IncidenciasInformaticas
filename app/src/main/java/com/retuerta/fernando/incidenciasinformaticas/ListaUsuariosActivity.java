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

import com.retuerta.fernando.incidenciasinformaticas.RecyclerViewAdapters.RecyclerViewUsuariosAdapter;
import com.retuerta.fernando.incidenciasinformaticas.data.IncidenciasContract;
import com.retuerta.fernando.incidenciasinformaticas.data.IncidenciasDbHelper;

public class ListaUsuariosActivity extends BaseActivity {

    Context context;
    RecyclerView recyclerView;
    RecyclerViewUsuariosAdapter recyclerViewUsuariosAdapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;

    static private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        super.setContentView(R.layout.activity_lista_usuarios);
        super.onCreate(savedInstanceState);

        context = getApplicationContext();

        Intent intent = getIntent();
        // String user = intent.getStringExtra("user");

        setTitle("Listado de usuarios");

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_lista_usuarios, contentFrameLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);

        //Utilizar el dbHelper para escribir en la base de datos
        IncidenciasDbHelper dbHelper = new IncidenciasDbHelper(context);
        mDb = dbHelper.getWritableDatabase();

        // Leer todos los usuarios
        Cursor cursor = getAllUsuarios();

        recyclerView = (RecyclerView) findViewById(R.id.usuarios_recyclerview);
        recyclerViewLayoutManager = new LinearLayoutManager(context);

        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        recyclerViewUsuariosAdapter = new RecyclerViewUsuariosAdapter(context, cursor);
        recyclerView.setAdapter(recyclerViewUsuariosAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder targer) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                long id = (long) viewHolder.itemView.getTag();
                removeArticulo(id);
                recyclerViewUsuariosAdapter.swapCursor(getAllUsuarios());
            }

        }).attachToRecyclerView(recyclerView);

    }

    private static Cursor getAllUsuarios() {
        return mDb.query(
                IncidenciasContract.UsuariosEntry.TABLE_NAME,
                null, null, null, null,null,
                IncidenciasContract.UsuariosEntry._ID
        );
    }

    public boolean removeArticulo(long id) {
        boolean hecho;
        hecho = mDb.delete(IncidenciasContract.UsuariosEntry.TABLE_NAME,
                IncidenciasContract.UsuariosEntry._ID + "=" + id, null) > 0;
        // Actualiza el cursor en adapter para actualizar la información del UI
        if (hecho) recyclerViewUsuariosAdapter.swapCursor(getAllUsuarios());
        return hecho;
    }
}
