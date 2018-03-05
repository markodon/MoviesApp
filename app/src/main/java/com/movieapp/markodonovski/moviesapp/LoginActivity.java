package com.movieapp.markodonovski.moviesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.movieapp.markodonovski.moviesapp.api.RestApi;
import com.movieapp.markodonovski.moviesapp.klasi.User;
import com.movieapp.markodonovski.moviesapp.other.PreferencesManager;
import com.movieapp.markodonovski.moviesapp.token.Token;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.username)
    EditText usename;
    @BindView(R.id.pass)
    EditText pass;
    @BindView(R.id.log)
    Button log;



    RestApi api1;
    RestApi api2;
    RestApi api3;

    Token tokenModel;
    String token;
    String token2;


    public String sesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);





    }

    @OnClick(R.id.log)
    public void click(){
        getToken();
    }



    public void getToken() {
        api1 = new RestApi(LoginActivity.this);
        {
            Call<Token> call = api1.getToken("request_token");
            call.enqueue(new Callback<Token>() {
                @Override
                public void onResponse(Call<Token> call, Response<Token> response) {
                    if (response.code() == 200) {
                        tokenModel = response.body();
                        token = tokenModel.request_token;
                        getLogin();  // text polinja
                        Toast.makeText(LoginActivity.this, "You are login", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {
                        Toast.makeText(LoginActivity.this, "Error please try again", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Token> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    public void getLogin() {   // tuka se vnesuvaat stringovi za username pass
        final User data = new User(usename.getText().toString(), pass.getText().toString());
        api2 = new RestApi(LoginActivity.this);
        Call<Token> call = api2.getLogin(  data.username, data.password, token);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.code() == 200) {
                    tokenModel = response.body();
                    token2 = tokenModel.request_token;
                    PreferencesManager.addUserID(token2,LoginActivity.this);
                    getSesion();
                } else if (response.code() == 401) {
                    Toast.makeText(LoginActivity.this, "Error please try again", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });

    }
    public void getSesion() {



        api3 = new RestApi(LoginActivity.this);
        Call<Token> call = api3.getSesion(token2);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.code() == 200) {
                    tokenModel=response.body();
                     sesion=tokenModel.session_id;
                    PreferencesManager.addSessionID(sesion,LoginActivity.this);
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else if (response.code() == 401) {
                    Toast.makeText(LoginActivity.this, "Error please try again", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });


    }

}
