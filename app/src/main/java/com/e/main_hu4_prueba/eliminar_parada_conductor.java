package com.e.main_hu4_prueba;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Snapshot;

import com.google.android.gms.common.util.ArrayUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class eliminar_parada_conductor extends AppCompatActivity {
    //Views
    private ListView mListView;

    //firebase
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase; // == DatabaseReference mRef;
    private  String userID;

    // Mis objetos
    ArrayList<Parada> arrayParada; //
    Parada parada; // no usado
    List<Map<String, String>> paradasList = new ArrayList<Map<String,String>>();
    ArrayList<Parada> arrayParada_mod;
    ArrayList<Parada> new_arrayParada_mod; // no usado
    List<Parada> parada_TO_list;

    // variables
    private static String parada_seleccionada;
    private static final String TAG = "ViewDatabase";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_parada_conductor);

        mListView = (ListView) findViewById(R.id.listview);

        // intancias de la sesion inicada en con firebase
        mAuth = FirebaseAuth.getInstance();
        // intancias de la sesion inicada, firebase database
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase=mFirebaseDatabase.getReference("user_c").child(mAuth.getUid());//   anterior  mDatabase = mFirebaseDatabase.getReference();
        // se elije el child de la base de datos que coincida con el ID con el cual se inicio la sesion
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        // metodo que interactua con la base de datos
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                showData(dataSnapshot);
                actionInList(mListView, dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    // metodo que trabaja con la base de  datos que coincida con la clave valor del inicio de sesion
    private void showData(DataSnapshot dataSnapshot) {
        // creo que indicador generico para asociar la DB con un arreglo tipo Parada
        GenericTypeIndicator<ArrayList<Parada>> t;
        t= new GenericTypeIndicator<ArrayList<Parada>>() {};
        // lleno arreglo parada accediendo desde la base de datos
        arrayParada = dataSnapshot.child("parada").getValue(t);

        for (Parada p : arrayParada){
            System.out.println("p.getNombre_parada(): "+ p.getNombre_parada());
        }

        initList(arrayParada);

        // completo parametros de la list view
        SimpleAdapter adapter = new SimpleAdapter(this,paradasList,android.R.layout.simple_list_item_1, new String[] {"parada"}, new int[] {android.R.id.text1});
        mListView.setAdapter(adapter);
    }
    private void eliminar_data(DataSnapshot dataSnapshot, String parada_select){
        GenericTypeIndicator<ArrayList<Parada>> t2;
        t2= new GenericTypeIndicator<ArrayList<Parada>>() {};
        arrayParada_mod = dataSnapshot.child("parada").getValue(t2);
        Parada[] paradas_array= arrayParada_mod.toArray(new Parada[arrayParada_mod.size()]);
        int i=0;
        int i_delete;
        for(Parada p : paradas_array){
            i++;
            if (p.getNombre_parada().equals(parada_select)){
                // p.setCoordenadas1(latNew);
                i_delete=i;
                paradas_array= ArrayUtils.removeAll(paradas_array, p);
            }
            else{
                System.out.println("no existe parada seleccionada: "+parada_select);
            }
        }
        List<Parada> parada_TO_list = Arrays.asList(paradas_array);
        //// añado la parada a la lista con paradas anteriores
        //arrayParada.add(new Parada(nombre_prd,lat,longitud));
        //// agrego nueva parada
        mDatabase.child("parada").setValue(parada_TO_list);
        //
        //parada.setNombre_parada(dataSnapshot.child(userID).getValue(Parada.class).getNombre_parada()); //set the name

    }
    private void initList(ArrayList<Parada> arrayParada) {
        // Lleno la LIST
        for (Parada p : arrayParada){
            paradasList.add(createPardas("parada", p.getNombre_parada()));
        }
    }
    private HashMap<String, String> createPardas(String key, String name) {
        HashMap<String, String> planet = new HashMap<String, String>();
        planet.put(key, name);
        return planet;
    }
    /*
     * Metodo encargado de la accion, interacion, de los lementos del ListView
     * */
    public void actionInList(ListView lv, final DataSnapshot dataSnapshot){
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // We know the View is a TextView so we can cast it
                TextView clickedView = (TextView) view;
                Toast.makeText(eliminar_parada_conductor.this, "Parada seleccionada para eliminar: "+clickedView.getText(), Toast.LENGTH_SHORT).show();
                parada_seleccionada= clickedView.getText().toString();

                eliminar_data(dataSnapshot, parada_seleccionada);

            }

        });
    }
}
