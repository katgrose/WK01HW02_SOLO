package com.example.wk01hw02_solo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText username;
    private EditText password;

    private TextView textViewResult;
    private TextView welcomeMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.login_button);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                verifyAccount(user, pass);
            }
        });
    }

    public void verifyAccount(String user, String pass) {
        List<Users> usersList = Users.getUsersList();

        Users activeUser = Users.getUserbyUsername(user, usersList);

        if (activeUser == null) {
            Toast.makeText(MainActivity.this, "Bad Username", Toast.LENGTH_SHORT).show();
        }
        else if (!Users.verifyPassword(pass, activeUser)) {
            Toast.makeText(MainActivity.this, "Bad Password", Toast.LENGTH_SHORT).show();
        }
        else {
            setContentView(R.layout.activity_main);
            textViewResult = findViewById(R.id.text_view_result);

            textViewResult.append("Welcome " + activeUser.getUsername()
                    + " [ User ID: " + activeUser.getUserID() + " ]!\n\n" );

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://jsonplaceholder.typicode.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

            Call<List<Post>> call = jsonPlaceHolderApi.getPosts();

            call.enqueue(new Callback<List<Post>>() {
                @Override
                public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                    if (!response.isSuccessful()) {
                        textViewResult.setText("Code: " + response.code());
                        return;
                    }

                    List<Post> posts = response.body();

                    for (Post post : posts) {
                        if (post.getUserId() == activeUser.getUserID()) {
                            String content = "";
                            content += "ID: " + post.getId() + "\n";
                            content += "User ID: " + post.getUserId() + "\n";
                            content += "Title: " + post.getTitle() + "\n";
                            content += "Text: " + post.getText() + "\n\n";

                            textViewResult.append(content);
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Post>> call, Throwable t) {
                    textViewResult.setText(t.getMessage());
                }

            });
        }
    }
}