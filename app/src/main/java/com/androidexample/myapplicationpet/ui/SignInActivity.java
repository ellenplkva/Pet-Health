package com.androidexample.myapplicationpet.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.androidexample.myapplicationpet.MainActivity;
import com.androidexample.myapplicationpet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {
    private static final String TAG = "SignInActivity";

    private FirebaseAuth mAuth;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText nickEditText;
    private EditText cityEditText;
    private TextView TextViewSignIn;
    private TextView TextViewSignUp;
    private TextView repeatPasswordEditText;
    private Button SignUpButton;
    private boolean LogInModActive;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // Initialized firebaseAuth
        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        nickEditText = findViewById(R.id.nickEditText);
        repeatPasswordEditText = findViewById(R.id.repeatPasswordEditText);
        TextViewSignIn = findViewById(R.id.textViewSignUp);
        TextViewSignUp = findViewById(R.id.textViewSignUp);
        SignUpButton = findViewById(R.id.button_sign_in);

        if(mAuth.getCurrentUser() != null){
            // getCurrentUser возвращает null если пользователь не авторизовывался, иначе возвращает объект класса firebaseUser
            startActivity(new Intent(SignInActivity.this, MainActivity.class));

        }

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginSignUpUser(emailEditText.getText().toString().trim(),
                        passwordEditText.getText().toString().trim());
            }

        });


    }

    private void LoginSignUpUser(String email, String password) {
        if(LogInModActive) { // Если на кнопке установлено "Войти"
           /*if(passwordEditText.getText().toString().trim().length() < 7){
                Toast.makeText(this, "Пароль должен быть больше 6 символов",
                        Toast.LENGTH_SHORT).show();
            }else if(emailEditText.getText().toString().trim().equals("")){
                Toast.makeText(this, "Введите email",
                        Toast.LENGTH_SHORT).show();
            }*/
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                startActivity(new Intent(SignInActivity.this, MainActivity.class));//Если авторизация успешна
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(SignInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });
        } else { // Иначе, если установлено зарегестрирован
            /*if (!passwordEditText.getText().toString().trim().equals( // проверка при регистрации, что пароли совпадают
                    repeatPasswordEditText.getText().toString().trim()
            )) {
                Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
            }else if(passwordEditText.getText().toString().trim().length() < 7){
                Toast.makeText(this, "Пароль должен быть больше 6 символов",
                        Toast.LENGTH_SHORT).show();
            }else if(emailEditText.getText().toString().trim().equals("")){
                Toast.makeText(this, "Введите email",
                        Toast.LENGTH_SHORT).show();
            }else if(!emailEditText.getText().toString().trim().equals("@")){
                Toast.makeText(this, "email должен содержать @",
                        Toast.LENGTH_SHORT).show();
            }
            else { //только если праоли совпали переходим к авторизации*/
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) { //Если авторизация успешна
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                //CreateUser();
                                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });
        }
    }

    /*private void CreateUser(FirebaseUser firebaseUser) {
        User user = new User();
        user.setId(firebaseUser.getUid());
        user.setEmail(firebaseUser.getEmail());
        user.setName(nickEditText.getText().toString().trim());

    }*/


    public void toggleLogInMode(View view) {
        if(LogInModActive){
            LogInModActive = false;
            SignUpButton.setText("Зарегестрироваться");
            TextViewSignIn.setText("Войти");
            repeatPasswordEditText.setVisibility(View.VISIBLE);
            nickEditText.setVisibility(View.VISIBLE);

        }
        else{
            LogInModActive = true;
            SignUpButton.setText("Войти");
            TextViewSignIn.setText("Зарегестрироваться");
            repeatPasswordEditText.setVisibility(View.GONE);
            nickEditText.setVisibility(View.GONE);

        }

    }
}

