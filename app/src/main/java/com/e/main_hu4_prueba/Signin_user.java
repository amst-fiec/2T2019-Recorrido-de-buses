package com.e.main_hu4_prueba;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Signin_user extends AppCompatActivity {
    //instancio variables a utilizar
    private EditText etxt_nombre,etxt_mail,etxt_password;
    private Button btn_signin_driver,btn_ya_existo, btn_signin_passgr;
    //instancio objeto tipo FirebaseAuth para poder crear usuarios nuevos
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    private String nombre,email,password;
    private String conductor= "conductor";
    private String usuario="usuario";
    // Objetos mios
    Conductor conductor_o;
    Usuario usuario_o;
    Parada parada_o= new Parada("default", "00", "00");
    //ArrayList<Parada> paradas_al= new ArrayList<Parada>();

    // variables
    private int edad=0;
    private int cap_max_bus=0;
    private ProgressDialog message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_user);
        //database??
        mDatabase= FirebaseDatabase.getInstance().getReference();
        //
        //Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
        //
        //inicializo los View usados para la interaccion con el usuario
        //en especial los Editable Text
        etxt_nombre=(EditText) findViewById(R.id.etxt_NameUsuario);
        etxt_mail=(EditText)findViewById(R.id.etxt_EmailUsuario);
        etxt_password=(EditText)findViewById(R.id.etxt_PasswordUsuario);
        //en especial los buttons
        btn_signin_passgr=(Button)findViewById(R.id.btn_reg_as_passgr);
        btn_signin_driver=(Button)findViewById(R.id.btn_reg_as_driver);
        btn_ya_existo=(Button)findViewById(R.id.btn_ya_existo);
        ///////////////////////////

        // botón signin_driver agrego listeners para la intereccion
        btn_signin_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("hello from btnsignindriver ");
                nombre=etxt_nombre.getText().toString();
                email=etxt_mail.getText().toString();
                password=etxt_password.getText().toString();

                if (!nombre.isEmpty() && !email.isEmpty() && !password.isEmpty()){
                    if (password.length()>=6){
                        //llamada a metodo usado para crear usuarios
                        createAccount_D();
                    }
                    else{
                        Toast.makeText(Signin_user.this,"Password al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(Signin_user.this,"Debe completar los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // botón ya_existo agrego listeners para la intereccion
        // ya esta hecho tudo
        btn_ya_existo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signin_user.this,init_app_as.class ));
            }
        });

        // botón btn_signin_passgr  agrego listeners para la intereccion
        btn_signin_passgr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("hello from btnsigninpass ");
                nombre=etxt_nombre.getText().toString();
                email=etxt_mail.getText().toString();
                password=etxt_password.getText().toString();
                if (!nombre.isEmpty() && !email.isEmpty() && !password.isEmpty()){
                    if (password.length()>=6){
                        //llamada a metodo usado para crear usuarios
                        createAccount_P(); // comentado
                    }
                    else{
                        Toast.makeText(Signin_user.this,"Password al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(Signin_user.this,"Debe completar los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void createAccount_D() {
        System.out.println("hello createAccount_D");
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Signin_user.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // obtengo id de usuario creado
                    String id = mAuth.getCurrentUser().getUid();

                    /////// mi objeto
                    conductor_o= new Conductor(nombre ,email,password, cap_max_bus, edad);
                    conductor_o.setParada(parada_o);
                    mDatabase.child("user_c").child(id).setValue(conductor_o);
                    /////////////////
                    mDatabase.child("user_c").child(id).setValue(conductor_o).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {
                                startActivity(new Intent(Signin_user.this, panel_opcion_conductor.class));
                                finish();

                            } else {
                                Toast.makeText(Signin_user.this, "No se pudo crear los datos correctamente", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }

            }

        });}


    private void createAccount_P() {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Signin_user.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    /*
                    Map<String, Object> mapP = new HashMap<>();
                    mapP.put("name", nombre);
                    mapP.put("email", email);
                    mapP.put("password", password);
                    mapP.put("tipo",usuario);
                    */
                    String id = mAuth.getCurrentUser().getUid();

                    /////// mi objeto
                    usuario_o= new Usuario(nombre,email,password);
                    mDatabase.child("user_u").child(id).setValue(usuario_o);
                    /////////////////

                    mDatabase.child("user_u").child(id).setValue(usuario_o).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {
                                startActivity(new Intent(Signin_user.this, panel_opcion_pasajero.class));
                                finish();

                            } else {
                                Toast.makeText(Signin_user.this, "No se pudo crear los datos correctamente", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }

            }

        });

    }
}