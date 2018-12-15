package com.retuerta.fernando.incidenciasinformaticas;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.retuerta.fernando.incidenciasinformaticas.data.IncidenciasContract;
import com.retuerta.fernando.incidenciasinformaticas.data.IncidenciasDbHelper;

public class MainActivity extends BaseActivity {

    IncidenciasDbHelper dbHelper = new IncidenciasDbHelper(this);
    private SQLiteDatabase mDb;

    private TextView mTextMessage;
    private EditText loginUserET;
    private EditText loginPasswordET;
    private Button loginBoton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_main, contentFrameLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);

        loginUserET = findViewById(R.id.loginUser);
        loginPasswordET = findViewById(R.id.loginPassword);
        loginBoton = findViewById(R.id.loginBoton);

        mDb = dbHelper.getWritableDatabase();
        dbHelper.onCreate(mDb);

        updateScreen();

        loginBoton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                doLoginUser();
            }
        });

    }

    void doLoginUser() {

        String logUser = loginUserET.getText().toString();
        String logPassword = loginPasswordET.getText().toString();

        if (logUser.equals("root") && logPassword.equals("root")) {
            this.usuarioLogeado = "root";
            this.tipoUsuarioLogeado = "Informático";
            Toast.makeText(this, "Bienvenido " + logUser, Toast.LENGTH_SHORT).show();
        } else {
            Cursor cursor = mDb.rawQuery("SELECT * FROM " + IncidenciasContract.UsuariosEntry.TABLE_NAME +
                    " WHERE " + IncidenciasContract.UsuariosEntry.COLUMN_NOMBRE_USUARIO + " = '" + logUser +
                    "' AND " + IncidenciasContract.UsuariosEntry.COLUMN_PASSWORD + " = '" + logPassword + "';", null );
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    this.usuarioLogeado = cursor.getString(4);
                    this.tipoUsuarioLogeado = cursor.getString(7);
                }

            } else {
                Toast.makeText(this, "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show();
            }
        }
        updateScreen();
    }

    void updateScreen() {
        Boolean userLogged = this.usuarioLogeado.equals("");
        loginUserET.setEnabled(userLogged);
        loginPasswordET.setEnabled(userLogged);
        loginBoton.setEnabled(userLogged);
    }

}
