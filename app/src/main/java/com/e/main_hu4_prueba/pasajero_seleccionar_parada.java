package com.e.main_hu4_prueba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class pasajero_seleccionar_parada extends AppCompatActivity {
    //Views
    private ListView mListView;

    // variables
    private static String parada_seleccionada_user;

    //firebase
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabase; // == DatabaseReference mRef;

    // Mis objetos
    List<Map<String, String>> rutasList = new ArrayList<Map<String,String>>();
    ArrayList<Parada> arrayParada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_ruta);
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
    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()) {
            //System.out.println( "ds.child(\"nombre_ruta\")" + ds.child("nombre_ruta").getValue() + "pasajero_seleccionar_ruta: " + pasajero_seleccionar_ruta.ruta_seleccionada_user() );
            if (ds.child("nombre_ruta").getValue() .equals(pasajero_seleccionar_ruta.ruta_seleccionada_user())){
                System.out.println(" ************************* entro en el if ************************* ");
                // creo que indicador generico para asociar la DB con un arreglo tipo Parada
                GenericTypeIndicator<ArrayList<Parada>> t;
                t= new GenericTypeIndicator<ArrayList<Parada>>() {};
                // lleno arreglo parada accediendo desde la base de datos
                arrayParada = ds.child("parada").getValue(t);
                //inicio metodo que añade el nombre de la parada al map
                initList(arrayParada);
                // completo parametros de la list view
                SimpleAdapter adapter = new SimpleAdapter(this,rutasList,android.R.layout.simple_list_item_1, new String[] {"parada"}, new int[] {android.R.id.text1});
                mListView.setAdapter(adapter);
            }
        }
    }
    private void initList(ArrayList<Parada> arrayParada) {
        // Lleno la LIST
        for (Parada p : arrayParada){
            rutasList.add(createPardas("parada", p.getNombre_parada()));
        }
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
                Toast.makeText(pasajero_seleccionar_parada.this, "Parada seleccionada: "+clickedView.getText(), Toast.LENGTH_SHORT).show();
                parada_seleccionada_user= clickedView.getText().toString();
                // se redirije a otra activity despues de leccionar una ruta del ListView
                //startActivity(new Intent(pasajero_seleccionar_parada.this, pasajero_seleccionar_parada.class ));
            }

        });
    }

    public static String parada_seleccionada_user(){
        return parada_seleccionada_user;
    }

}
