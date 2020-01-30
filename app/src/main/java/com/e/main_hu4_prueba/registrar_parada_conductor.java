package com.e.main_hu4_prueba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class registrar_parada_conductor extends AppCompatActivity {

    // FireBase
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    FirebaseDatabase mFirebaseDatabase; //extra
    FirebaseAuth.AuthStateListener mAuthListener; //extra
    String id;

    /// Views
    Button btn_registrar;
    EditText etxt_nombre_parada, etxt_lat, etxt_long;

    //// Variables
    String nombre_prd, lat, longitud;

    //// Mis Objetos
    Parada new_parada;
    ArrayList<Parada> arrayParada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_parada);

        // Instancia Firebase Auth, para obtener informacion sobre el usuario actual
        mAuth = FirebaseAuth.getInstance();
        // Instancia Firebase DataBase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseDatabase.getReference("user_c").child(mAuth.getUid());//anterior  ->  mDatabase= mFirebaseDatabase.getReference();
        // EditText
        etxt_lat = (EditText) findViewById(R.id.edit_coordenadas1);
        etxt_long = (EditText) findViewById(R.id.edit_coordenadas2);
        etxt_nombre_parada = (EditText) findViewById(R.id.edit_nombre_parada);
        // Button
        btn_registrar = (Button) findViewById(R.id.btn_registrar);

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //// etxt (->) String
                nombre_prd = etxt_nombre_parada.getText().toString();
                longitud = etxt_long.getText().toString();
                lat = etxt_lat.getText().toString();
                //// Objeto Parada
                new_parada = new Parada(nombre_prd, lat, longitud);
                //// Firebase
                id = mAuth.getCurrentUser().getUid();
                // metodo para registrar campos
                registrarCampos ( mDatabase );
            }
        });
    }

    private void registrarCampos (final DatabaseReference mDatabase ){
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // creo que indicador generico para asociar la DB con un arreglo tipo Parada
                GenericTypeIndicator<ArrayList<Parada>> t;
                t= new GenericTypeIndicator<ArrayList<Parada>>() {};
                // lleno arreglo parada accediendo desde la base de datos
                arrayParada = dataSnapshot.child("parada").getValue(t);
                // añado la parada a la lista con paradas anteriores
                arrayParada.add(new Parada(nombre_prd,lat,longitud));
                // condiciono que los campos esten llenos
                if (!nombre_prd.isEmpty() &&!longitud.isEmpty()&&!lat.isEmpty()){
                    // agrego nueva parada
                    mDatabase.child("parada").setValue(arrayParada);
                }
                else {
                    Toast.makeText(registrar_parada_conductor.this, "complete todos los campos",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}