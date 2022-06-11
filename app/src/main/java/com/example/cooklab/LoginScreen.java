package com.example.cooklab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginScreen extends AppCompatActivity {

    private Button registerBtn,loginBtn,showMapBtn;
    private EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        ConstraintLayout constraintLayout=findViewById(R.id.loginLayout);

        AnimationDrawable animationDrawable=(AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(1800);
        animationDrawable.start();

        registerBtn=findViewById(R.id.register_button);
        loginBtn=findViewById(R.id.login_button);
        showMapBtn=findViewById(R.id.show_map_button);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginScreen.this,RegisterScreen.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputName=username.getText().toString();
                String inputPassword=password.getText().toString();
                if(inputName.isEmpty() || inputPassword.isEmpty()){
                    Toast.makeText(LoginScreen.this,"All fields must be filled",Toast.LENGTH_SHORT).show();
                }
                else{
                    UserDatabase userDatabase=UserDatabase.getDatabase(LoginScreen.this);
                    UserDao userDao=userDatabase.userDao();
                    User user=userDao.login(inputName,inputPassword);
                    if(user==null){
                        Toast.makeText(LoginScreen.this,"Invalid name or password!",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Intent intent=new Intent(LoginScreen.this,IngredientScreen.class);
                        startActivity(intent);
                    }
                }
            }
        });

        showMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginScreen.this,MapScreen.class);
                startActivity(intent);
            }
        });
    }
}