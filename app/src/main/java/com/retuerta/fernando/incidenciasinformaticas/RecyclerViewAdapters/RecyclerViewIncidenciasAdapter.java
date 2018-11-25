package com.retuerta.fernando.incidenciasinformaticas.RecyclerViewAdapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.retuerta.fernando.incidenciasinformaticas.R;
import com.retuerta.fernando.incidenciasinformaticas.data.IncidenciasContract;

public class RecyclerViewIncidenciasAdapter extends RecyclerView.Adapter<RecyclerViewIncidenciasAdapter.ViewHolder> {

    private Cursor mCursor;
    private Context context;
    View view1;
    RecyclerView.ViewHolder viewHolder1;

    public RecyclerViewIncidenciasAdapter(Context context1, Cursor cursor) {
        context = context1;
        this.mCursor = cursor;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView dniTV;
        public TextView fechaIncidenciaTV;
        public TextView observacionesTV;
        public TextView dniInformaticoTV;
        public TextView estadoIncidenciaTV;
        public TextView fechaResolucionTV;
        public TextView observacionesInformaticoTV;

        public ViewHolder(View v) {
            super(v);
            dniTV = (TextView) v.findViewById(R.id.dni_i_RecyView);
            fechaIncidenciaTV = (TextView) v.findViewById(R.id.fecha_i_RecyView);
            observacionesTV = (TextView) v.findViewById(R.id.observaciones_i_RecyView);
            dniInformaticoTV = (TextView) v.findViewById(R.id.dni_informatico_i_RecyView);
            estadoIncidenciaTV = (TextView) v.findViewById(R.id.estado_i_RecyView);
            fechaResolucionTV = (TextView) v.findViewById(R.id.fecha_resolucion_i_RecyView);
            observacionesInformaticoTV = (TextView) v.findViewById(R.id.observaciones_informatico_i_RecyView);
        }
    }

    @Override
    public RecyclerViewIncidenciasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view1 = LayoutInflater.from(context).inflate(R.layout.incidencias_recyclerview_items, parent, false);
        viewHolder1 = new ViewHolder(view1);
        return (ViewHolder) viewHolder1;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Escribe los datos de la base de datos en el RecycleView
        if (!mCursor.moveToPosition(position)) return;

        String dni = mCursor.getString(mCursor.getColumnIndex(IncidenciasContract.IncidenciasEntry.COLUMN_DNI));
        String fechaIncidencia = mCursor.getString(mCursor.getColumnIndex(IncidenciasContract.IncidenciasEntry.COLUMN_FECHA_INCIDENCIA));
        String observaciones = mCursor.getString(mCursor.getColumnIndex(IncidenciasContract.IncidenciasEntry.COLUMN_OBSERVACIONES));
        String dniInformatico = mCursor.getString(mCursor.getColumnIndex(IncidenciasContract.IncidenciasEntry.COLUMN_DNI_INFORMATICO));
        String estadoIncidencia = mCursor.getString(mCursor.getColumnIndex(IncidenciasContract.IncidenciasEntry.COLUMN_ESTADO_INCIDENCIA));
        String fechaResolucion = mCursor.getString(mCursor.getColumnIndex(IncidenciasContract.IncidenciasEntry.COLUMN_FECHA_RESOLUCION_INCIDENCIA));
        String observacionesInformatico = mCursor.getString(mCursor.getColumnIndex(IncidenciasContract.IncidenciasEntry.COLUMN_OBSERVACIONES_INFORMATICO));
        final long id = mCursor.getLong(mCursor.getColumnIndex(IncidenciasContract.IncidenciasEntry._ID));

        holder.dniTV.setText(String.valueOf(dni));
        holder.fechaIncidenciaTV.setText(String.valueOf(fechaIncidencia));
        holder.observacionesTV.setText(String.valueOf(observaciones));
        holder.dniInformaticoTV.setText(String.valueOf(dniInformatico));
        holder.estadoIncidenciaTV.setText(String.valueOf(estadoIncidencia));
        holder.fechaResolucionTV.setText(String.valueOf(fechaResolucion));
        holder.observacionesInformaticoTV.setText(String.valueOf(observacionesInformatico));

        // guarda el id sin presentarlo en pantalla
        holder.itemView.setTag(id);

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        // always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // refrescar RecyclerView
            this.notifyDataSetChanged();
        }
    }
}
