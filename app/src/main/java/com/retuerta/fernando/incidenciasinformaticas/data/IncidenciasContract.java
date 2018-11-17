package com.retuerta.fernando.incidenciasinformaticas.data;

import android.provider.BaseColumns;

public class IncidenciasContract {

    public static final class UsuariosEntry implements BaseColumns {

        public static final String TABLE_NAME = "usuarios";

        public static final String COLUMN_NOMBRE = "nombre";
        public static final String COLUMN_APELLIDOS = "apellidos";
        public static final String COLUMN_DNI = "dni";
        public static final String COLUMN_NOMBRE_USUARIO = "nombre_usuario";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_FOTO = "foto";
        public static final String COLUMN_TIPO_USUARIO = "tipo_usuario";

    }

    public static final class IncidenciasEntry implements BaseColumns {

        public static final String TABLE_NAME = "incidencias";

        public static final String COLUMN_DNI = "dni";
        public static final String COLUMN_FECHA_INCIDENCIA = "fecha_incidencia";
        public static final String COLUMN_OBSERVACIONES = "observaciones";
        public static final String COLUMN_DNI_INFORMATICO = "dni_informatico";
        public static final String COLUMN_ESTADO_INCIDENCIA = "estado_incidencia";
        public static final String COLUMN_FECHA_RESOLUCION_INCIDENCIA = "fecha_resolucion_incidencia";
        public static final String COLUMN_OBSERVACIONES_INFORMATICO = "observaciones_informatico";
    }

}
