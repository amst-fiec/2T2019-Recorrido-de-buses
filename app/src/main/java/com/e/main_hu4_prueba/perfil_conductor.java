package com.e.main_hu4_prueba;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class perfil_conductor extends AppCompatActivity {
    //Views
    private EditText etxt_cap_max_bus, etxt_placa_bus, etxt_nombre, etxt_edad;
    private Button btn_guardar_datos;
    // variables
    private String  str_placa_bus, str_nombre;
    int int_cap_max_bus, int_edad;

    private static String parada_seleccionada;
    private static final String TAG = "ViewDatabase";
    // Mis objetos
    ArrayList<Parada> arrayParada;
    ArrayList<Parada> arrayParada_mod;
    Parada parada;
    List<Map<String, String>> paradasList = new ArrayList<Map<String,String>>();
    //firebase
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase; // == DatabaseReference mRef;
    private  String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_conductor);
        // View inicializacion

        btn_guardar_datos = (Button) findViewById(R.id.btn_guardar_datos_conductor);
        etxt_cap_max_bus= (EditText) findViewById(R.id.edit_capacidadBus);
        etxt_placa_bus= (EditText) findViewById(R.id.edit_placaBus);
        etxt_nombre= (EditText) findViewById(R.id.edit_nombre_conductor);
        etxt_edad= (EditText) findViewById(R.id.edit_edad_conductor);
        // accion LISTENER
        btn_guardar_datos.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                int_cap_max_bus= Integer.parseInt(etxt_cap_max_bus.getText().toString());
                str_placa_bus=etxt_placa_bus.getText().toString();
                str_nombre=etxt_nombre.getText().toString();
                int_edad=Integer.parseInt(etxt_edad.getText().toString());

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
                        mody_datos_conductor( str_nombre,str_placa_bus, int_edad, int_cap_max_bus);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
    }

    public void mody_datos_conductor( String nombre, String placa, int edad, int cap_maxima_bus){
        //// agrego nuevo dato
        mDatabase.child("capacidad_max_bus").setValue(cap_maxima_bus);
        mDatabase.child("edad").setValue(edad);
        mDatabase.child("nombre").setValue(nombre);
        mDatabase.child("placa").setValue(placa);
        //
        //parada.setNombre_parada(dataSnapshot.child(userID).getValue(Parada.class).getNombre_parada()); //set the name

    }
}
