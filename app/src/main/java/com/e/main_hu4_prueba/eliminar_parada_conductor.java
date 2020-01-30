package com.e.main_hu4_prueba;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class eliminar_parada_conductor extends AppCompatActivity {
    //Views
    private static final String TAG = "ViewDatabase";
    private ListView mListView;

    //firebase
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase; // == DatabaseReference mRef;
    private  String userID;

    // Mis objetos
    ArrayList<Parada> arrayParada;
    Parada parada;



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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    // metodo que trabaja con la base de  datos que coincida con la clave valor del inicio de sesion
    private void showData(DataSnapshot dataSnapshot) {
        //mio
        // creo que indicador generico para asociar la DB con un arreglo tipo Parada
        GenericTypeIndicator<ArrayList<Parada>> t;
        t= new GenericTypeIndicator<ArrayList<Parada>>() {};
        // lleno arreglo parada accediendo desde la base de datos
        arrayParada = dataSnapshot.child("parada").getValue(t);
        //// añado la parada a la lista con paradas anteriores
        //arrayParada.add(new Parada(nombre_prd,lat,longitud));
        //// agrego nueva parada
        //mDatabase.child("parada").setValue(arrayParada);
        //
        //parada.setNombre_parada(dataSnapshot.child(userID).getValue(Parada.class).getNombre_parada()); //set the name

        ArrayList<String> array  = new ArrayList<>();
        for (Parada p : arrayParada){
            array.add(p.getNombre_parada());
        }
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,array);
        mListView.setAdapter(adapter);
    }
}
