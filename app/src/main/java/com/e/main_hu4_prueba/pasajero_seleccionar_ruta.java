package com.e.main_hu4_prueba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class pasajero_seleccionar_ruta extends AppCompatActivity {
    //Views
    private ListView mListView;

    // variables
    private static String ruta_seleccionada_user;

    //firebase
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabase; // == DatabaseReference mRef;

    // Mis objetos
    List<Map<String, String>> rutasList = new ArrayList<Map<String,String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_ruta);

        // iniciar ListView
        mListView = (ListView) findViewById(R.id.listview2);

        // intancias de la sesion inicada en con firebase
        // intancias de la sesion inicada, firebase database
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase=mFirebaseDatabase.getReference().child("user_c");//   anterior  mDatabase = mFirebaseDatabase.getReference();
        // metodo que interactua con la base de datos
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
                actionInList(mListView);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    // metodo que trabaja con la base de  datos que coincida con la clave valor del inicio de sesion
    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()) {
            // inicio metodo que añade el nombre de la ruta al map
            initList(ds.child("nombre_ruta").getValue().toString());
            // completo parametros de la list view
            SimpleAdapter adapter = new SimpleAdapter(this,rutasList,android.R.layout.simple_list_item_1, new String[] {"ruta"}, new int[] {android.R.id.text1});
            mListView.setAdapter(adapter);
        }
    }

    private void initList(String ruta_nombre) {
        // Lleno la LIST
        rutasList.add(createPardas("ruta", ruta_nombre));
    }
    private HashMap<String, String> createPardas(String key, String name) {
        HashMap<String, String> planet = new HashMap<String, String>();
        planet.put(key, name);
        return planet;
    }
    public void actionInList(ListView lv){
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // We know the View is a TextView so we can cast it
                TextView clickedView = (TextView) view;
                Toast.makeText(pasajero_seleccionar_ruta.this, "Parada seleccionada: "+clickedView.getText(), Toast.LENGTH_SHORT).show();
                ruta_seleccionada_user= clickedView.getText().toString();
                // se redirije a otra activity despues de leccionar una ruta del ListView
                startActivity(new Intent(pasajero_seleccionar_ruta.this, pasajero_seleccionar_parada.class ));
            }

        });
    }

    public static String ruta_seleccionada_user(){
        return ruta_seleccionada_user;
    }
}