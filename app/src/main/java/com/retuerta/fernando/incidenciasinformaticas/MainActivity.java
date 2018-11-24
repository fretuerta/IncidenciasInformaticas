package com.retuerta.fernando.incidenciasinformaticas;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.retuerta.fernando.incidenciasinformaticas.data.IncidenciasDbHelper;

public class MainActivity extends BaseActivity {

    IncidenciasDbHelper dbHelper = new IncidenciasDbHelper(this);
    static private SQLiteDatabase mDb;

    private TextView mTextMessage;
    /*
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.navigation_lista_usuarios:
                            mTextMessage.setText("Lista de usuarios");
                            return true;
                        case R.id.navigation_gestion_usuarios:
                            mTextMessage.setText("Gestión de usuarios");
                            return true;
                    }
                    return false;
                }
            };
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_main, contentFrameLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);
/*
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
*/
        mDb = dbHelper.getWritableDatabase();
        dbHelper.onCreate(mDb);


    }

}
