package com.rafael.cnube.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import java.lang.String;

import com.rafael.cnube.R;
import com.rafael.cnube.models.Contact;
import com.rafael.cnube.models.TipoMovil;

import java.util.ArrayList;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;

public class FragmentNew extends Fragment implements OnItemSelectedListener, View.OnClickListener {

    private Realm realm;
    private View view;
    private Spinner spinner;
    private  ArrayAdapter<TipoMovil> adapter;
    private List<TipoMovil> tipoMovils = new ArrayList<>();
    private ImageButton imageButtonSave;
    private EditText editTextNombre;
    private EditText editTextApellidos;
    private EditText editTextTelefono;
    private EditText editTextTipo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment_new, container, false);
        /*Obtener la configuracIón DE Realm hecha en la clase app*/
        realm = Realm.getDefaultInstance();

        spinner = (Spinner)view.findViewById(R.id.spinnerTipoTelefono);
        imageButtonSave = (ImageButton)view.findViewById(R.id.imageButtonSave);

        /*Añadir registros*/
        addRecords();

        adapter = new ArrayAdapter<TipoMovil>(getContext(),android.R.layout.simple_spinner_item, tipoMovils);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        /*Registrar el evento*/
        spinner.setOnItemSelectedListener(this);
        imageButtonSave.setOnClickListener(this);



        /*Devuelve el view creado*/
        return view;
    }

    private void createContact(){
        /*Declaración de variable a utilizar*/
        String nombre, apellidos, telefono, tipo;

        /*Referenciar cada objeto*/
        editTextNombre = (EditText) view.findViewById(R.id.editTextNombre);
        editTextApellidos = (EditText) view.findViewById(R.id.editTextApellidos);
        editTextTelefono = (EditText) view.findViewById(R.id.editTextTelefono);

        /*Asignar valor a cada objeto*/
        nombre = editTextNombre.getText().toString();
        apellidos = editTextApellidos.getText().toString();
        telefono = editTextTelefono.getText().toString();
        tipo = spinner.getSelectedItem().toString();

        if (nombre.length() > 0 && apellidos.length() > 0 && telefono.length() > 0 & tipo.length() > 0){
            /*Ingresar los valores obtenidos a la tabla Contacto*/
            realm.beginTransaction();
            Contact contacts = new Contact(nombre, apellidos, telefono, tipo);
            realm.copyToRealm(contacts);
            realm.commitTransaction();

            Toast.makeText(getContext(), "Contacto guardado con Exito.", Toast.LENGTH_LONG).show();
            /*Borra el contenido de los editText*/
            deleteEditText(editTextNombre, editTextApellidos, editTextTelefono);
            editTextNombre.requestFocus();

        }else {
            Toast.makeText(getContext(), "Todos los campos son obligatorio", Toast.LENGTH_LONG).show();

        }

    }

    private void deleteEditText(EditText nombre, EditText apellidos, EditText telefono){
        /*Limpia todos el contenido de los editText*/
        nombre.setText("");
        apellidos.setText("");
        telefono.setText("");

    }

    private void addRecords(){
        tipoMovils.add(new TipoMovil(1, "Móvil"));
        tipoMovils.add(new TipoMovil(2, "Casa"));
        tipoMovils.add(new TipoMovil(3, "Trabajo"));
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        /*Vacío*/

    }

    @Override
    public void onClick(View view) {
        /*Crea el nuevo ocntacto en la base de datos*/
        createContact();


    }
}
