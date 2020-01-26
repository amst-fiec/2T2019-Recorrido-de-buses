package com.amst.proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.HashMap;
import java.util.Map;



public class MainActivity extends AppCompatActivity {
    private EditText txtnombre,txtmail,txtpassword;
    private Button inicio,existe;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    private String nombre,mail,password;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase= FirebaseDatabase.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();
        txtnombre=(EditText) findViewById(R.id.NameConductor);
        txtmail=(EditText)findViewById(R.id.EmailConductor);
        txtpassword=(EditText)findViewById(R.id.PasswordConductor);
        inicio=(Button)findViewById(R.id.btnRegistro);
        existe=(Button)findViewById(R.id.btnExisteConductor);

        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre=txtnombre.getText().toString();
                mail=txtmail.getText().toString();
                password=txtpassword.getText().toString();

                if (!nombre.isEmpty() && !mail.isEmpty() && !password.isEmpty()){
                    if (password.length()>=6){
                        registerUser();
                    }
                    else{
                        Toast.makeText(MainActivity.this,"Password al menos 6 caracteres", Toast.LENGTH_SHORT).show();

                    }

                }
                else{
                    Toast.makeText(MainActivity.this,"Debe completar los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        existe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent (MainActivity.this,Inicio_sesion.class ));
            }
        });

    }

    private void registerUser() {
        mAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Map<String,Object> map=new HashMap<>();
                    map.put("name",nombre);
                    map.put("email",mail);
                    map.put("password",password);


                    String id= mAuth.getCurrentUser().getUid();

                    mDatabase.child("Conductor").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()){
                                startActivity(new Intent(MainActivity.this,Inicio_sesion.class));
                                finish();

                            }
                            else{
                                Toast.makeText(MainActivity.this,"No se pudo crear los datos correctamente", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
                else{
                    Toast.makeText(MainActivity.this,"No se pudo registrar este Conductor", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

}
