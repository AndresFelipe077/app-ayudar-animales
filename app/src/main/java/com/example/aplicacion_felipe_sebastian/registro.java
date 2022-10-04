package com.example.aplicacion_felipe_sebastian;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class registro extends AppCompatActivity {

   private EditText nombre,correoRegistro,contrasenaRegistro,contrasenaRepetidaRegistro;
   private Button botonRegistrarse;
   private TextView elegirImagenTxt;
   private CircleImageView fotoDePerfil;

   private FirebaseAuth auth;
   private FirebaseDatabase database;
   private FirebaseUser currentuser;
   private DatabaseReference referenceUsuario;

   public static final int RESULT_GALLERY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        nombre                      = findViewById(R.id.nombre);
        correoRegistro              = findViewById(R.id.correoRegistro);
        contrasenaRegistro          = findViewById(R.id.contraseñaRegistro);
        contrasenaRepetidaRegistro  = findViewById(R.id.contraseñaRepetidaRegistro);
        botonRegistrarse            = findViewById(R.id.BotonRegistrarse);
        fotoDePerfil                = findViewById(R.id.fotoDePerfil);
        elegirImagenTxt             = findViewById(R.id.elegirImagen);


        auth                        = FirebaseAuth.getInstance();
        database                    = FirebaseDatabase.getInstance();
        currentuser                 = auth.getCurrentUser();

       /* nombre.setText(currentuser.getDisplayName());
        correoRegistro.setText(currentuser.getEmail());

        */

        Glide.with(this).load(currentuser.getPhotoUrl()).into(fotoDePerfil);

        referenceUsuario = database.getReference("USUARIOS");

        elegirImagenTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/");
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        botonRegistrarse.setOnClickListener(view -> {

            String correo     = correoRegistro.getText().toString();
            String nombreUsuario     = nombre.getText().toString();
            if (emailValido(correo) && validarContraseña() && validarNombre(nombreUsuario)){
                String contraseña = contrasenaRegistro.getText().toString();
                auth.createUserWithEmailAndPassword(correo, contraseña)
                        .addOnCompleteListener(registro.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(registro.this, "¡REGISTRO EXITOSO!", Toast.LENGTH_SHORT).show();
                                    usuario usuario = new usuario();
                                    usuario.setCorreo(correo);
                                    usuario.setNombre(nombreUsuario);
                                    referenceUsuario.push().setValue(usuario);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(registro.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }else{
                Toast.makeText(registro.this, "Validando", Toast.LENGTH_SHORT).show();
            }


        });




    }// fin onCreate

   private boolean emailValido(CharSequence target){
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public boolean validarContraseña() {
        String contraseña,contraseñaRepetida;
        contraseña         = contrasenaRegistro.getText().toString();
        contraseñaRepetida = contrasenaRepetidaRegistro.getText().toString();

        if(contraseña.equals(contraseñaRepetida)){
            if (contraseña.length() >= 8 && contraseña.length() <= 16){
                return true;
            }else return false;
        }else return false;
    }


public boolean validarNombre(String nombre){
        return !nombre.isEmpty();
}

}