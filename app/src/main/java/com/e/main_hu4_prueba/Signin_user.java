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


public class Signin_user extends AppCompatActivity {
    //instancio variables a utilizar
    private EditText etxt_nombre,etxt_mail,etxt_password;
    private Button btn_signin_driver,btn_ya_existo, btn_signin_passgr;
    //instancio objeto tipo FirebaseAuth para poder crear usuarios nuevos
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    private String nombre,email,password;


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

        //agrego listeners, para la intereccion con el botón signin_driver
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
        //agrgo listeners, para la intereccion con el botón ya_existo
        btn_ya_existo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signin_user.this,init_app_as.class ));
            }
        });


    }

    public void createAccount_D(){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Signin_user.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()){
                    FirebaseAuthException e = (FirebaseAuthException )task.getException();
                    Toast.makeText(Signin_user.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    startActivity(new Intent (Signin_user.this, init_app_as.class));
                }
            }
        });
    }

}