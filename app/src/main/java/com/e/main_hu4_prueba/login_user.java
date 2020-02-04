package com.e.main_hu4_prueba;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*
* quite eso de que si es usuario conducto se logee de conductor, se debe validar de otra forma
* */

public class login_user extends AppCompatActivity {

    private Button btnInicio;
    private EditText txtmail,txtcontraseña;

    private String email,contraseña;

    public DatabaseReference db_reference;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        btnInicio=(Button) findViewById(R.id.Inicio);
        txtmail=(EditText) findViewById(R.id.email);
        txtcontraseña=(EditText) findViewById(R.id.contraseña);

        mAuth=FirebaseAuth.getInstance();
        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // seteo valores predeterminados para inicar como user pasajero
                //txtmail.setText("u@h.com");
                //txtcontraseña.setText("123456");
                email=txtmail.getText().toString();
                contraseña= txtcontraseña.getText().toString();
                if (!email.isEmpty() && !contraseña.isEmpty()){

                    loginUser();
                }
                else{
                    Toast.makeText(login_user.this, "complete todos los campos",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void loginUser() {
        System.out.println("hola desde loginUser");
        mAuth.signInWithEmailAndPassword(email,contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    System.out.println("hola desde taskisSuccesfull");
                    //REVISA
                    //startActivity(new Intent(login_user.this,Mapa_SigFox.class));
                    //hay que validar si es usuairo o pasajero el que inicia sesion
                    if (init_app_as.Listener.equals("btn_conductor")){
                        System.out.println("hola desde ibit as btn_conductor");
                        db_reference = FirebaseDatabase.getInstance().getReference("user_c").child(mAuth.getUid());
                        db_reference.addValueEventListener(new ValueEventListener() { @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                System.out.println("datasnapshot existe");
                                //String tipo = dataSnapshot.child("tipo").getValue(String.class);
                                // elimine validacion de "si es conductor"
                                init_app_as.setListener("");
                                startActivity(new Intent(login_user.this,panel_opcion_conductor.class));
                                finish();
                            }
                            else{
                                System.out.println("datasnapshot no existe, no es conductor");
                                Toast.makeText(login_user.this, "Usted no es un conductor",Toast.LENGTH_SHORT).show();
                            }
                            //ya tenemos los datos desde Firebase, podemos actualizar la UI
                        }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                System.out.println("Fallo la lectura: " + databaseError.getCode());
                            }
                        });
                    }
                    if (init_app_as.Listener.equals("btn_pasajero")){
                        System.out.println("hola desde ibit as btn_user");
                        db_reference = FirebaseDatabase.getInstance().getReference("user_u").child(mAuth.getUid());
                        db_reference.addValueEventListener(new ValueEventListener() { @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                System.out.println("datasnapshot existe");
                                init_app_as.setListener("");
                                startActivity(new Intent(login_user.this,panel_opcion_pasajero.class));
                                finish();
                            }
                            //ya tenemos los datos desde Firebase, podemos actualizar la UI
                            else{
                                System.out.println("datasnapshot no existe, no es pasajero");
                                Toast.makeText(login_user.this, "Usted no es un pasajero",Toast.LENGTH_SHORT).show();
                            }
                        }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                System.out.println("Fallo la lectura: " + databaseError.getCode());
                            }
                        });
                    }
                }
                else{
                    System.out.println("hola desde not taskisSuccesfull");
                    Toast.makeText(login_user.this, "No se puede iniciar sesion,compruebe los datos",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}