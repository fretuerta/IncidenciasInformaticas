package com.retuerta.fernando.incidenciasinformaticas.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class IncidenciasDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "incidencias.db";
    private static final int DATABASE_VERSION = 1;

    public IncidenciasDbHelper(Context context) {
        super(context , DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_USUARIOS_TABLE = "CREATE TABLE IF NOT EXISTS " +
                IncidenciasContract.UsuariosEntry.TABLE_NAME + " (" +
                IncidenciasContract.UsuariosEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                IncidenciasContract.UsuariosEntry.COLUMN_NOMBRE + " TEXT, " +
                IncidenciasContract.UsuariosEntry.COLUMN_APELLIDOS + " TEXT, " +
                IncidenciasContract.UsuariosEntry.COLUMN_DNI + " TEXT, " +
                IncidenciasContract.UsuariosEntry.COLUMN_NOMBRE_USUARIO + " TEXT, " +
                IncidenciasContract.UsuariosEntry.COLUMN_PASSWORD + " TEXT, " +
                IncidenciasContract.UsuariosEntry.COLUMN_FOTO + " TEXT, " +
                IncidenciasContract.UsuariosEntry.COLUMN_TIPO_USUARIO + " TEXT " +
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_USUARIOS_TABLE);

        final  String SQL_CREATE_INCIDENCIAS_TABLE = "CREATE TABLE IF NOT EXISTS " +
                IncidenciasContract.IncidenciasEntry.TABLE_NAME + " (" +
                IncidenciasContract.IncidenciasEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                IncidenciasContract.IncidenciasEntry.COLUMN_DNI + " TEXT, " +
                IncidenciasContract.IncidenciasEntry.COLUMN_FECHA_INCIDENCIA + " TEXT, " +
                IncidenciasContract.IncidenciasEntry.COLUMN_OBSERVACIONES + " TEXT, " +
                IncidenciasContract.IncidenciasEntry.COLUMN_DNI_INFORMATICO + " TEXT, " +
                IncidenciasContract.IncidenciasEntry.COLUMN_ESTADO_INCIDENCIA + " TEXT, " +
                IncidenciasContract.IncidenciasEntry.COLUMN_FECHA_RESOLUCION_INCIDENCIA + " TEXT, " +
                IncidenciasContract.IncidenciasEntry.COLUMN_OBSERVACIONES_INFORMATICO + " TEXT " +
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_INCIDENCIAS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion > oldVersion) {
            // de la 1 a la 2
        }
    }

}
