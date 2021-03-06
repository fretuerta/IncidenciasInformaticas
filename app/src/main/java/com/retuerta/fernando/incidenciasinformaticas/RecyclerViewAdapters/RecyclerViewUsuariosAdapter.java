package com.retuerta.fernando.incidenciasinformaticas.RecyclerViewAdapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

        public TextView nombreTV;
        public TextView apellidosTV;
        public TextView dniTV;
        public ImageView fotoTV;
        public TextView tipoUsuarioTV;

        public ViewHolder(View v) {
            super(v);
            nombreTV = (TextView) v.findViewById(R.id.nombre_RecyView);
            apellidosTV = (TextView) v.findViewById(R.id.apellidos_RecyView);
            dniTV = (TextView) v.findViewById(R.id.dni_RecyView);
            fotoTV = (ImageView) v.findViewById(R.id.foto_RecyView);
            tipoUsuarioTV = (TextView) v.findViewById(R.id.tipo_usuario_RecyView);
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
        String apellidos = mCursor.getString(mCursor.getColumnIndex(IncidenciasContract.UsuariosEntry.COLUMN_APELLIDOS));
        String dni = mCursor.getString(mCursor.getColumnIndex(IncidenciasContract.UsuariosEntry.COLUMN_DNI));
        byte[] foto = mCursor.getBlob(mCursor.getColumnIndex(IncidenciasContract.UsuariosEntry.COLUMN_FOTO));
        Bitmap fotoBitmap = null;
        if (foto != null) fotoBitmap = BitmapFactory.decodeByteArray(foto, 0, foto.length);
        String tipoUsuario = mCursor.getString(mCursor.getColumnIndex(IncidenciasContract.UsuariosEntry.COLUMN_TIPO_USUARIO));
        final long id = mCursor.getLong(mCursor.getColumnIndex(IncidenciasContract.UsuariosEntry._ID));


        holder.nombreTV.setText(String.valueOf(nombre));
        holder.apellidosTV.setText(String.valueOf(apellidos));
        holder.dniTV.setText(String.valueOf(dni));
        holder.fotoTV.setMaxWidth(200);
        if (fotoBitmap != null) holder.fotoTV.setImageBitmap(Bitmap.createScaledBitmap(fotoBitmap, 120, 120, false));
        holder.tipoUsuarioTV.setText(String.valueOf(tipoUsuario));

        // guarda el id sin presentarlo en pantalla
        holder.itemView.setTag(id);

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // refrescar RecyclerView
            this.notifyDataSetChanged();
        }
    }
}
