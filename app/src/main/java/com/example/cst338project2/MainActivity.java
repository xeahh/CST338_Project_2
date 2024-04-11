package com.example.cst338project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.cst338project2.DB.AppDatabase;
import com.example.cst338project2.DB.UserDAO;
import com.example.cst338project2.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    UserDAO userDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        userDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .userDAO();
        User admin  = new User("admin","admin",true);
        User user = new User("user","user",false);

        //add admin and user to the database if they do not exist
        if(userDAO.getUserByUsername(admin.getUsername()).isEmpty()) {
            userDAO.insert(admin);
        }
        if(userDAO.getUserByUsername(user.getUsername()).isEmpty()) {
            userDAO.insert(user);
        }

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInput()){
                    List<User> users = userDAO.getUsers();
                    for (User user: users){
                        if(user.getUsername().equals(binding.usernameInput.getText().toString())){
                            Toast.makeText(MainActivity.this, "Username already exists!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    userDAO.insert(createUserFromInput());

                }
                Toast.makeText(MainActivity.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInput()){
                    List<User> users = userDAO.getUsers();
                    //check if the user exists in the database

                    //if the user and password match, check if the user is an admin
                    for(User user: users) {
                        if(user.getUsername().equals(binding.usernameInput.getText().toString()) && user.getPassword().equals(binding.passwordInput.getText().toString())){
                            if(user.isAdmin()){
                                Intent intent = AdminHomePage.AdminHomePageIntentFactory(getApplicationContext());
                                startActivity(intent);
                               // Toast.makeText(MainActivity.this, "Admin activity", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = PlayerHomePageActivity.PlayerHomePageIntentFactory(getApplicationContext());
                                startActivity(intent);
                               // Toast.makeText(MainActivity.this, "User activity", Toast.LENGTH_SHORT).show();
                            }
                            return;

                        }
                    }
                    Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    //if the user is an admin, display the admin activity
                    //if the user is not an admin, display the user activity
                    //if the user and password do not match, display an error message
                }
            }
        });
    }
    private boolean validateInput(){
        String username = binding.usernameInput.getText().toString();
        String password = binding.passwordInput.getText().toString();
        //Toast.makeText(this, "Please enter a username and password", Toast.LENGTH_SHORT).show();
        // Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
        return !username.isEmpty() && !password.isEmpty();
    }

    private User createUserFromInput(){
        return new User(binding.usernameInput.getText().toString(),binding.passwordInput.getText().toString(),false);
    }

    public static Intent MainActivityIntentFactory(Context context) {
        return new Intent(context, MainActivity.class);
    }
}