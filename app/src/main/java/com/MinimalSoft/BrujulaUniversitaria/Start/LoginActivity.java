package com.MinimalSoft.BrujulaUniversitaria.Start;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import com.MinimalSoft.BrujulaUniversitaria.Models.Response_General;
import com.MinimalSoft.BrujulaUniversitaria.Utilities.Interfaces;
import com.facebook.FacebookSdk;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.MinimalSoft.BrujulaUniversitaria.R;
import com.MinimalSoft.BrujulaUniversitaria.Main.MainActivity;
import com.MinimalSoft.BrujulaUniversitaria.Facebook.FacebookDataCollector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Callback<Response_General> {
    private CallbackManager facebookCallbackManager;
    private LoginButton facebookLoginButton;
    private EditText passwordField;
    private EditText emailField;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(this.getApplicationContext());
        this.setContentView(R.layout.activity_login);

        facebookLoginButton = (LoginButton) this.findViewById(R.id.login_hidden_facebook_button);
        Button fakeFacebookButton = (Button) this.findViewById(R.id.login_facebook_button);
        Button registerButton = (Button) this.findViewById(R.id.login_registerButton);
        Button loginButton = (Button) this.findViewById(R.id.login_accessButton);
        passwordField = (EditText) this.findViewById(R.id.login_passwordField);
        emailField = (EditText) this.findViewById(R.id.login_emailField);
        facebookCallbackManager = CallbackManager.Factory.create();
        FacebookDataCollector dataCollector = new FacebookDataCollector(this);

        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        fakeFacebookButton.setOnClickListener(this);
        facebookLoginButton.setReadPermissions("public_profile email");
        facebookLoginButton.registerCallback(facebookCallbackManager, dataCollector);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_facebook_button:
                facebookLoginButton.performClick();
                break;

            case R.id.login_registerButton:
                intent = new Intent(this.getApplicationContext(), RegisterActivity.class);
                this.startActivity(intent);
                this.finish();
                break;

            case R.id.login_accessButton:
                String email = emailField.getText().toString().trim();
                String password = passwordField.getText().toString();

                if (email.length() == 0) {
                    Toast.makeText(this, "Inserte el correo", Toast.LENGTH_LONG).show();
                } else if (password.length() == 0) {
                    Toast.makeText(this, "Inserte la contraseña", Toast.LENGTH_LONG).show();
                } else {
                    String BASE_URL = "http://ec2-54-210-116-247.compute-1.amazonaws.com";
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
                    Interfaces interfaces = retrofit.create(Interfaces.class);
                    Call <Response_General> call = interfaces.logInUser("login", email, password, "0", "0");
                    call.enqueue(this);
                }

                break;
        }
    }

    public void logIn() {
        intent = new Intent(this.getApplicationContext(), MainActivity.class);
        this.startActivity(intent);
        this.finish();
    }

    /*----Retrofit Methods----*/

    @Override
    public void onResponse(Call<Response_General> call, Response<Response_General> response) {
        if(response.code() == 404) {
            Toast.makeText(this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
        } else if (!response.body().getResponse().equals("success")){
            Toast.makeText(this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
        } else {
            this.logIn();
        }
    }

    @Override
    public void onFailure(Call<Response_General> call, Throwable t) {
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG).show();
        Log.e(this.getClass().getSimpleName(), "Message: " + t.getMessage());
        t.printStackTrace();
    }
}
