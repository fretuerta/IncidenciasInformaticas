package com.retuerta.fernando.incidenciasinformaticas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {

    static DrawerLayout drawerLayout;
    static ActionBarDrawerToggle actionBarDrawerToggle;
    static Toolbar toolbar;

    static String usuarioLogeado = "";
    static String tipoUsuarioLogeado = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        toolbar = (Toolbar) findViewById((R.id.toolbar));
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        final Toast primero_debe_identificarse = Toast.makeText(this, "Primero debe identificarse", Toast.LENGTH_SHORT);
        final Toast no_tiene_permiso = Toast.makeText(this, "No tiene permisos sufiencientes", Toast.LENGTH_LONG);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                if (usuarioLogeado.equals("")) {
                    primero_debe_identificarse.show();
                } else {
                    switch (menuItem.getItemId()) {
                        case R.id.nav_lista_usuarios:
                            if (tipoUsuarioLogeado.equals("Informático")) {
                                startActivity(new Intent(getApplicationContext(), ListaUsuariosActivity.class));
                                drawerLayout.closeDrawers();
                            } else {
                                no_tiene_permiso.show();
                            }
                            break;
                        case R.id.nav_gestion_usuarios:
                            if (tipoUsuarioLogeado.equals("Informático")) {
                                startActivity(new Intent(getApplicationContext(), GestionUsuariosActivity.class));
                                drawerLayout.closeDrawers();
                            } else {
                                no_tiene_permiso.show();
                            }
                            break;
                        case R.id.nav_lista_incidencias:
                            startActivity(new Intent(getApplicationContext(), ListaIncidenciasActivity.class));
                            drawerLayout.closeDrawers();
                            break;
                        case R.id.nav_gestion_incidencias:
                            startActivity(new Intent(getApplicationContext(), GestionIncidenciasActivity.class));
                            break;
                        case R.id.nav_logout:
                            usuarioLogeado = "";
                            tipoUsuarioLogeado = "";
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            break;
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        actionBarDrawerToggle.syncState();

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransitionExit();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransitionEnter();
    }

    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }


}
