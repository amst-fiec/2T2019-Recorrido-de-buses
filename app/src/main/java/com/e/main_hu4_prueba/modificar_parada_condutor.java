package com.e.main_hu4_prueba;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class modificar_parada_condutor extends AppCompatActivity {
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
    ArrayList<Parada> arrayParada_mod;
    Parada parada;
    List<Map<String, String>> paradasList = new ArrayList<Map<String,String>>();

    // ventana flotante
    private PopupWindow mPopupWindow;
    private RelativeLayout mRelativeLayout;
    private Context mContext;
    private Button btn_cerrar;
    private EditText etxt_new_lat, etxt_new_long;
    private String newLAT_str, newLONG_str;

    // variables
    private static String parada_seleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_parada_condutor);
        //declarando View
        mListView = (ListView) findViewById(R.id.listview);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.ViewParent);
        // obtentiedo el contexto de la app
        mContext = getApplicationContext();
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
                showData(dataSnapshot);
                actionInList(mListView,dataSnapshot);
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
    // modify data 1 sirve para modificar los datos de coordenadas
    private void ModifyData1(DataSnapshot dataSnapshot,String parada_select, String latNew , String longNew){
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
    private void ModifyData2(DataSnapshot dataSnapshot_no_usado, final String parada_select, final String latNew , final String longNew){
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                GenericTypeIndicator<ArrayList<Parada>> t2;
                t2= new GenericTypeIndicator<ArrayList<Parada>>() {};

                arrayParada_mod = dataSnapshot.child("parada").getValue(t2);

                for(Parada p : arrayParada_mod){

                    if (p.getNombre_parada().equals(parada_select)){
                        System.out.println(" #################################################### : "
                                + parada_select +
                                " #################################################### ");
                        p.setCoordenadas1(latNew);
                        p.setCoordenadas2(longNew);
                    }
                    else{
                        System.out.println(" #################################################### " +
                                "no existe parada seleccionada: "+parada_select+
                                "####################################################");
                    }
                }
                //// agrego nueva parada
                //mDatabase.child("parada").setValue(arrayParada_mod);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
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
                Toast.makeText(modificar_parada_condutor.this, "Parada seleccionada: "+clickedView.getText(), Toast.LENGTH_SHORT).show();
                parada_seleccionada= clickedView.getText().toString();
                /* Iniciar startActivity hacia otra clase Activity para modificar el registro
                *  en esa otra clase usaras algo parecido al metodo ModifyData1(DataSnapshot dataSnapshot,String parada_select, String latNew , String longNew);
                * necesita todos enviarle esos atributos al metodo.
                * clickedView.getText() seria parada_select, que es el nombre de la parada seleccionada
                * latNew sera el edit text con la nueva latitud y lo mismo con longNew
                * abajo esta el start Activity, deberas crear una nueva activity para que alli se lanze
                * */
                startActivity(new Intent(modificar_parada_condutor.this, ModificarParada_datos.class ));
            }

        });
    }

    public static String getParadaSeleccionada(){
        return parada_seleccionada;
    }

}