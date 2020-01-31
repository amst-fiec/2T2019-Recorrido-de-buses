package com.e.main_hu4_prueba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ModificarParada_datos extends AppCompatActivity {
    // instancio Views
    private Button btn_registrar;
    private EditText etxt_new_lat, etxt_new_long;
    private String str_new_lat, str_new_long, parada_mody;
    private TextView txt_parada;
    //firebase
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase; // == DatabaseReference mRef;
    private  String userID;

    // Mis objetos
    ArrayList<Parada> arrayParada_mod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_parada_datos);
        // inicializo los Views
        txt_parada= (TextView) findViewById(R.id.txt_parada);
        btn_registrar= (Button) findViewById(R.id.btn_modify_parada);
        etxt_new_lat= (EditText) findViewById( R.id.etxt_new_latitud);
        etxt_new_long= (EditText) findViewById(R.id.etxt_new_longitud);
        // coloco nombre de parada a modificar al text view "parada", llamado asi por default
        parada_mody= modificar_parada_condutor.getParadaSeleccionada();
        txt_parada.setText(parada_mody);
        btn_registrar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                str_new_lat= etxt_new_lat.getText().toString();
                str_new_long= etxt_new_long.getText().toString();

                // intancias de la sesion inicada en con firebase
                mAuth = FirebaseAuth.getInstance();
                // intancias de la sesion inicada, firebase database
                mFirebaseDatabase = FirebaseDatabase.getInstance();
                mDatabase=mFirebaseDatabase.getReference("user_c").child(mAuth.getUid());//   anterior  mDatabase = mFirebaseDatabase.getReference();
                // se elije el child de la base de datos que coincida con el ID con el cual se inicio la sesion
                FirebaseUser user = mAuth.getCurrentUser();
                userID = user.getUid();
                /*metodo que interactua con la base de datos
                 * Este metodo iniciara el metodo encaragado del llenado de la lista leyendo desde la base de
                 *  datos, y luego iniciara otro metodo que se encarga de la interaccion de los elementos
                 * (items del list view) añadidos desde la base de datos
                 * */
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        System.out.println("############### "+"str_new_lat: "+str_new_lat+";\tstr_new_long: "+str_new_long);
                        ModifyData(dataSnapshot, parada_mody, str_new_lat,str_new_long);
                        startActivity(new Intent(ModificarParada_datos.this,opcion_paradas_conductor.class ));

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });

    }
    // ModifyData sirve para modificar los datos de coordenadas
    private void ModifyData(DataSnapshot dataSnapshot, String parada_select, String latNew , String longNew){
        GenericTypeIndicator<ArrayList<Parada>> t2;
        t2= new GenericTypeIndicator<ArrayList<Parada>>() {};
        arrayParada_mod = dataSnapshot.child("parada").getValue(t2);
        for(Parada p : arrayParada_mod){
            if (p.getNombre_parada().equals(parada_select)){
                p.setCoordenadas1(latNew);
                p.setCoordenadas2(longNew);
            }
            else{
                System.out.println("no existe parada seleccionada: "+parada_select);
            }
        }
        //// añado la parada a la lista con paradas anteriores
        //arrayParada.add(new Parada(nombre_prd,lat,longitud));
        //// agrego nueva parada
        mDatabase.child("parada").setValue(arrayParada_mod);
        //
        //parada.setNombre_parada(dataSnapshot.child(userID).getValue(Parada.class).getNombre_parada()); //set the name

    }
}
