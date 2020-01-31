package com.e.main_hu4_prueba;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class seleccionar_parada extends AppCompatActivity {
    private static final String TAG = "ViewDatabase";
    private ListView mListView;

    //firebase
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase; // == DatabaseReference mRef;
    private  String usuario;


    // Mis objetos
    ArrayList<Parada> arrayParada;
    Parada parada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_parada);
        mListView = (ListView) findViewById(R.id.listview2);

        // intancias de la sesion inicada en con firebase
        mAuth = FirebaseAuth.getInstance();
        // intancias de la sesion inicada, firebase database
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase=mFirebaseDatabase.getReference().child("user_c");//   anterior  mDatabase = mFirebaseDatabase.getReference();
        // se elije el child de la base de datos que coincida con el ID con el cual se inicio la sesion
        /*FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();*/

        // metodo que interactua con la base de datos
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                showData(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    // metodo que trabaja con la base de  datos que coincida con la clave valor del inicio de sesion
    private void showData(DataSnapshot dataSnapshot) {

        usuario= String.valueOf(dataSnapshot.child("nombre").getValue());
        ArrayList<String> array  = new ArrayList<>();
        array.add(usuario);
        ArrayAdapter adapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1,array);
        mListView.setAdapter(adapter);
       /* GenericTypeIndicator<ArrayList<Parada>> t;
        t = new GenericTypeIndicator<ArrayList<Parada>>() {
        };
        // lleno arreglo parada accediendo desde la base de datos
        arrayParada = dataSnapshot.child("nombre").getValue(t);
        //// añado la parada a la lista con paradas anteriores
        //arrayParada.add(new Parada(nombre_prd,lat,longitud));
        //// agrego nueva parada
        //mDatabase.child("parada").setValue(arrayParada);
        //
        //parada.setNombre_parada(dataSnapshot.child(userID).getValue(Parada.class).getNombre_parada()); //set the name

        ArrayList<String> array = new ArrayList<>();
        for (Parada p : arrayParada) {
            array.add(p.getNombre_parada());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);
        mListView.setAdapter(adapter);*/

    }
}