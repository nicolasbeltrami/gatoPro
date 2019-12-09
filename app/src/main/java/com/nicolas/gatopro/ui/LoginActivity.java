package com.nicolas.gatopro.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nicolas.gatopro.R;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin, btnRegistro;
    private ScrollView formLogin;
    private ProgressBar pbLogin;
    private FirebaseAuth firebaseAuth;
    private String email, password;
    private boolean tryLogin = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etEmail = findViewById(R.id.editTextEmail);
        etPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.buttonLogin);
        btnRegistro = findViewById(R.id.buttonRegistro);
        formLogin = findViewById(R.id.formLogin);
        pbLogin = findViewById(R.id.progressBarLogin);

        firebaseAuth = FirebaseAuth.getInstance();
        changeLoginFormVisibility(true);
        eventos();

    }

    private void eventos() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();

               if (email.isEmpty()) {
                    etEmail.setError("El email es obigatorio");
                } else if (password.isEmpty()){
                    etPassword.setError("La contraseña es requerida");
                } else {
                    //TODO: realizar registro en firebase auth
                   changeLoginFormVisibility(false);
                   loginUser();
                }

            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(i);

            }
        });
    }

    private void loginUser() {
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        tryLogin = true;
                        if (task.isSuccessful()){
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateUI(user);
                        }else {
                            Log.w("TAG", "signInError: ", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }


    private void changeLoginFormVisibility(boolean showForm) {
        pbLogin.setVisibility(showForm ?  View.GONE : View.VISIBLE);
        formLogin.setVisibility(showForm ? View.VISIBLE : View.GONE);
    }


    private void updateUI(FirebaseUser user) {
        if (user != null){
            // Navegar hacia siguiente pantalla
            Intent i = new Intent(LoginActivity.this, FindGameActivity.class);
            startActivity(i);
        }else{
            changeLoginFormVisibility(true);
            if (tryLogin){
                etPassword.setError("Datos incorrectos");
                etPassword.requestFocus();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Comprobar si el usuario ya inició sesión
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);

    }
}
