package com.e.main_hu4_prueba;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Datos_conductor extends AppCompatActivity {
    //Views
    private ListView mListView;
    TextView txt_nombre,txt_cap_max,txt_placa,txt_ruta;

    // variables
    private static String str_nombre_ruta, str_cap_max_bus, str_nombre, str_placa;

    // list
    ArrayList<String> NOMBRE_C, RUTA, PLACA;
    ArrayList<String> CAP_MAX;

    //firebase
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabase; // == DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_conductor);
        // iniciar ListView
        mListView = (ListView) findViewById(R.id.ListView_infoC);
        // inicializo los arrays
        NOMBRE_C=new ArrayList<>();
        RUTA=new ArrayList<>();
        PLACA=new ArrayList<>();
        CAP_MAX=new ArrayList<>();

        // intancias de la sesion inicada, firebase database
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase=mFirebaseDatabase.getReference().child("user_c");//   anterior  mDatabase = mFirebaseDatabase.getReference();
        //
        String[] n= {"hola", "herman"};
        System.out.println("############################## ANTES DE IMPRIMO ##############################"+"n.length"+n.length);

        //
        // metodo que interactua con la base de datos
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            // invoco metodo para extraer datos desde Firebase y añadirlos a los Arrays
            showData(dataSnapshot);
            System.out.println(" ############################## IMPRIMO ############################## ");
            System.out.println(NOMBRE_C.size());
            System.out.println(RUTA.size());
            System.out.println(PLACA.size());
            System.out.println(CAP_MAX.size());
            /*
            for (String s : NOMBRE_C){
                System.out.println("s. "+s);
            }
            for (String s : CAP_MAX){
                System.out.println("s. "+s);
            }
            for (String s : PLACA){
                System.out.println("s. "+s);
            }
            for (String s : RUTA){
                System.out.println("s. "+s);
            }
            */
            CustomAdapter customAdapter= new CustomAdapter();
            mListView.setAdapter(customAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
    // metodo que trabaja con la base de  datos que coincida con la clave valor del inicio de sesion
    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()) {
            // extraigo valores desde Firebase
            str_nombre_ruta=ds.child("nombre_ruta").getValue().toString();
            //str_cap_max_bus=Integer.parseInt(ds.child("capacidad_max_bus").getValue().toString());
            str_cap_max_bus=ds.child("capacidad_max_bus").getValue().toString();
            str_nombre=ds.child("nombre").getValue().toString();
            str_placa=ds.child("placa").getValue().toString();
            // añado valores extraido a los Arrays
            NOMBRE_C.add(str_nombre);
            RUTA.add(str_nombre_ruta);
            CAP_MAX.add(str_cap_max_bus);
            PLACA.add(str_placa);
        }
    }
    // clase interna
    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return NOMBRE_C.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.custom_conductor_info, null);

            txt_cap_max= (TextView) convertView.findViewById(R.id.txt_cap_max);
            txt_placa= (TextView) convertView.findViewById(R.id.txt_placa);
            txt_ruta= (TextView) convertView.findViewById(R.id.txt_nombre_ruta);
            txt_nombre= (TextView) convertView.findViewById(R.id.txt_nombreC);

            txt_cap_max.setText(CAP_MAX.get(position));
            txt_placa.setText(PLACA.get(position));
            txt_ruta.setText(RUTA.get(position));
            txt_nombre.setText(NOMBRE_C.get(position));

            System.out.println(" ############################## EN el CustomAdapter ############################## ");
            System.out.println("CAP_MAX.get(position)"+CAP_MAX.get(position));

            return convertView;
        }
    }
}
