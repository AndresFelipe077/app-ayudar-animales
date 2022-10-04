package com.example.aplicacion_felipe_sebastian;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.core.Tag;

public class login extends AppCompatActivity {

   private EditText correo,contrasena;
   private Button aceptar,registro;
   private SignInButton sign_in_button;

   private FirebaseAuth auth;
   private GoogleSignInClient mGoogleSignInClient;

   private static final String TAG = "GoogleActivity";
   /*private static final*/ int RC_SIGN_IN = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        correo         = findViewById(R.id.correo);
        contrasena     = findViewById(R.id.contraseña);
        aceptar        = findViewById(R.id.aceptar);
        registro       = findViewById(R.id.registro);
        sign_in_button = findViewById(R.id.sign_in_button);


        auth = FirebaseAuth.getInstance();



        registro.setOnClickListener(v -> {
        Intent i = new Intent(login.this,registro.class);
        startActivity(i);
    });

        aceptar.setOnClickListener(view -> {
        String Correo     = correo.getText().toString();
        if (emailValido(Correo) && validarContraseña()){
            String contraseña = contrasena.getText().toString();
            auth.signInWithEmailAndPassword(Correo, contraseña)
                    .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(login.this, "REGISTRO EXITOSO", Toast.LENGTH_SHORT).show();
                                nextActivity();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(login.this, "Identificacion correcta", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });


    }else{
            Toast.makeText(login.this, "Validando", Toast.LENGTH_SHORT).show();
        }


    });

        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sign();
            }
        });

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.common_signin_button_text))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);



    }//fin oncreate


    private boolean emailValido(CharSequence target){
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public boolean validarContraseña() {
        String contraseña;
        contraseña = contrasena.getText().toString();
            if (contraseña.length() >= 8 && contraseña.length() <= 16){
                return true;
            }else return false;

    }


    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser currentuser = auth.getCurrentUser();
        if (currentuser != null){
            nextActivity();
        }
    }

    private void nextActivity(){
        startActivity(new Intent(login.this,pantallaPrincipal.class));
    }



    private void sign() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG,"firebaseAuthWithGoogle: "+ account.getId());
                //Toast.makeText(login.this, "firebaseAuthWithGoogle" + account.getId(), Toast.LENGTH_SHORT).show();
                firebaseAuthWithGoogle(account.getIdToken());
            }catch (ApiException e){
                Log.w(TAG,"Google sign in failed",e);
                //Toast.makeText(login.this, "Google sign in failed", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG,"signInWithCredential:success");
                            //Toast.makeText(login.this, "signInWithCredential:success", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = auth.getCurrentUser();
                            updateUI(user);
                        }else{
                            Log.w(TAG,"signInWithCredential:failed",task.getException());
                            //Toast.makeText(login.this, "signInWithCredential:failed", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
            }

    private void updateUI(FirebaseUser user){
        if(user != null){
            Toast.makeText(login.this, "Login Succesful", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(login.this, "Something Error", Toast.LENGTH_SHORT).show();
        }
    }




}// fin todo