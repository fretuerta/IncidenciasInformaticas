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

public class RecyclerViewUsuariosAdapter extends RecyclerView.Adapter<RecyclerViewUsuariosAdapter.ViewHolder> {

    private Cursor mCursor;
    private Context context;
    View view1;
    RecyclerView.ViewHolder viewHolder1;

    public RecyclerViewUsuariosAdapter(Context context1, Cursor cursor) {
        context = context1;
        this.mCursor = cursor;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public ViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.nombre_textview);
        }
    }

    @Override
    public RecyclerViewUsuariosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view1 = LayoutInflater.from(context).inflate(R.layout.usuarios_recyclerview_items, parent, false);

        viewHolder1 = new ViewHolder(view1);

        return (ViewHolder) viewHolder1;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Escribe los datos de la base de datos en el RecycleView
        if (!mCursor.moveToPosition(position)) return;

        String nombre = mCursor.getString(mCursor.getColumnIndex(IncidenciasContract.UsuariosEntry.COLUMN_NOMBRE));
        final long id = mCursor.getLong(mCursor.getColumnIndex(IncidenciasContract.UsuariosEntry._ID));

        holder.textView.setText(String.valueOf(nombre));

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
