package com.rafael.cnube.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.rafael.cnube.R;
import com.rafael.cnube.models.Contact;
import com.rafael.cnube.models.Contact;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class ContactAdapter extends BaseAdapter {

    private Context context;
    private List<Contact> items;

    public ContactAdapter(Context context, List<Contact> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {return this.items.size();}

    @Nullable
    @Override
    public Contact getItem(int position) {return this.items.get(position);}

    @Override
    public long getItemId(int id) {return id;}

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        /*View Holder Pattern*/
        ViewHolder holder;

        if (convertView == null){
            /*Inflar el layout*/
            convertView = LayoutInflater.from(this.context).inflate(R.layout.list_item, null);

            holder = new ViewHolder();
            holder.textViewNombreCompleto = (TextView) convertView.findViewById(R.id.textViewNombreCompleto);
            holder.textViewTelefono = (TextView) convertView.findViewById(R.id.textViewTelefono);
            holder.textViewDate = (TextView) convertView.findViewById(R.id.textViewDate);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //Declarar variable para luego asignarle su contenido
        Contact item = (Contact) items.get(position);

        //Asognar los valores a cada objeto referenciado
        holder.textViewNombreCompleto.setText(item.getNombre());
        holder.textViewTelefono.setText(item.getTelefono());

        /*Formatera la fecha y presentarla de forma corta*/
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String createAt = dateFormat.format(item.getCreateAt());
        holder.textViewDate.setText(createAt);

        //Devolvemos la vista inflada y con su respectivo datos
        return convertView;
    }

    static class ViewHolder {
        private TextView textViewNombreCompleto;
        private TextView textViewTelefono;
        private TextView textViewDate;

    }


}


