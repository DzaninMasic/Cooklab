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

public class RegisterScreen extends AppCompatActivity {

    private Button loginBtn,registerBtn;
    private EditText username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        ConstraintLayout constraintLayout=findViewById(R.id.registerLayout);

        AnimationDrawable animationDrawable=(AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(1800);
        animationDrawable.start();

        loginBtn=findViewById(R.id.login_button);
        registerBtn=findViewById(R.id.register_button);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterScreen.this,LoginScreen.class);
                startActivity(intent);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String registeredUsername=username.getText().toString();
                String registeredPassword=password.getText().toString();

                UserDatabase userDatabase=UserDatabase.getDatabase(getApplicationContext());
                final UserDao userDao=userDatabase.userDao();
                final User user=new User();
                user.setName(registeredUsername);
                user.setPassword(registeredPassword);

                if(validateInput(user)){
                    if(userDao.checkIfExist(registeredUsername)){
                        Toast.makeText(RegisterScreen.this,"User already exists!",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Intent intent=new Intent(RegisterScreen.this,LoginScreen.class);
                        userDao.add(user);
                        startActivity(intent);
                    }
                }else{
                    Toast.makeText(RegisterScreen.this,"Fill in all the fields",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean validateInput(User user){
        if(user.getName().isEmpty() || user.getPassword().isEmpty()){
            return false;
        }
        return true;
    }
}