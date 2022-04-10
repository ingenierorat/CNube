package com.rafael.cnube.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rafael.cnube.activities.CNubeActivity;
import com.rafael.cnube.activities.ContactDetailActivity;
import com.rafael.cnube.adapters.ContactAdapter;
import com.rafael.cnube.R;
import com.rafael.cnube.models.Contact;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;


public class FragmentHome extends Fragment implements RealmChangeListener<RealmResults<Contact>>, AdapterView.OnItemClickListener {

    private Realm realm;

    private View view;
    private ListView listViewContact;
    private ContactAdapter mAdapter;
    private RealmResults<Contact> contacts;
    private EditText editTextSearch;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextPhone;
    private EditText editTextType;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*Inflate the layout for this fragment*/
        view = inflater.inflate(R.layout.fragment_fragment_home, container, false);

        realm = Realm.getDefaultInstance();
        contacts = realm.where(Contact.class).findAll();
        contacts.addChangeListener(this);



        /*Referenciar cada objeto*/
        listViewContact = (ListView) view.findViewById(R.id.listViewContact);
        editTextSearch = (EditText) view.findViewById(R.id.editTextSearch);

        mAdapter = new ContactAdapter(getContext(), contacts);
        listViewContact.setAdapter(mAdapter);
        listViewContact.setOnItemClickListener(this);

        registerForContextMenu(listViewContact);



        /*Activando el filtro de busqueda*/
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        /*Devuelve el View creado*/
        return view;
    }


    private void deleteContact(Contact contact){
        realm.beginTransaction();
        contact.deleteFromRealm();
        realm.commitTransaction();
    }

    private void editContact(Contact contact) {
        realm.beginTransaction();
        contact.setNombre(editTextFirstName.getText().toString());
        contact.setApellidos(editTextLastName.getText().toString());
        contact.setTelefono(editTextPhone.getText().toString());
        contact.setTipo(editTextType.getText().toString());
        realm.copyToRealmOrUpdate(contact);
        realm.commitTransaction();
    }

    private void showAlertForEditingContact(String title, String message, final Contact contact){

        /*Declarar el dialogo a utilizar*/
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        /*Asinar el titulo y el mensaje a mostra cuando se llamado*/
        if (title != null)builder.setTitle(title);
        if (message !=null)builder.setMessage(message);
        /*Inflar el view a utilizar*/
        View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.dialog_editing_contact, null);
        builder.setView(viewInflated);

        /*Referenciar cada objeto inflado*/
        editTextFirstName = (EditText)viewInflated.findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText)viewInflated.findViewById(R.id.editTextLastName);
        editTextPhone = (EditText)viewInflated.findViewById(R.id.editTextPhone);
        editTextType = (EditText)viewInflated.findViewById(R.id.editTextType);

        builder.setPositiveButton("Editar",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                /*Llamar el mètodo que ejecutará la acción*/
                editContact(contact);

            }
        });
        builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                /*No hacer nada si cancela la acción*/
            }
        });

        /*Mostrar el dialogo de edicción*/
        AlertDialog dialog = builder.create();
        dialog.show();
    }



    @Override
    public void onChange(RealmResults<Contact> contacts) {
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), ContactDetailActivity.class);
        intent.putExtra("id", contacts.get(position).getId());
        startActivity(intent);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
      AdapterView.AdapterContextMenuInfo info =(AdapterView.AdapterContextMenuInfo) menuInfo;
      menu.setHeaderIcon(R.drawable.ic_account_circle_black_24dp);
      menu.setHeaderTitle(contacts.get(info.position).getNombre());
      getActivity().getMenuInflater().inflate(R.menu.context_menu_contact, menu);
      super.onCreateContextMenu(menu, v, menuInfo);


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){

            case R.id.delete_contact:
                deleteContact(contacts.get(info.position));
                return true;

            case R.id.edit_contact:
                showAlertForEditingContact("Editar", "Editar los datos del contacto", contacts.get(info.position));

            default:
                return super.onContextItemSelected(item);
        }

    }


}
