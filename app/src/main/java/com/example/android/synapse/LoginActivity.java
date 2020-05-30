package com.example.android.synapse;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity
{

    private Button submit,back;
    private EditText passwordView,emailView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        submit =findViewById(R.id.loginButton);
        emailView = findViewById(R.id.loginEmail);
        passwordView = findViewById(R.id.loginPassword);
        back = findViewById(R.id.loginBack);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.darkgreen)));


        submit.setOnClickListener(new View.OnClickListener()
        {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view)
            {   progressBar.setVisibility(View.VISIBLE);

                try {
                    //Toast.makeText(LoginActivity.this, "function call success", Toast.LENGTH_SHORT).show();
                    exitLoginPage();
                } catch (Exception e) {
                    //Toast.makeText(LoginActivity.this, "function call fail", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHome(view);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void exitLoginPage() throws IOException {
        //Get global variable
        final GlobalVariable globalVariable = (GlobalVariable) getApplicationContext()  ;
        String s = globalVariable.getSomeVariable();



        String URL = s+"token";
        //Toast.makeText(this, ""+URL, Toast.LENGTH_SHORT).show();

        progressBar.setVisibility(View.GONE);


        //harcode
        /*final String emailId = "k164064@nu.edu.pk";
        final String pass = "Hello123@";*/

        //generic
        final String emailId = emailView.getText().toString();
        final String pass = passwordView.getText().toString();
        String grantType = "password";

        //Toast.makeText(this, ""+emailId+" "+pass+" "+grantType, Toast.LENGTH_SHORT).show();

        //API integration in form-urlencoded form
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        final StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("JSON",response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            //Toast.makeText(globalVariable, ""+obj.getString("access_token"), Toast.LENGTH_SHORT).show();
                            globalVariable.setToken(obj.getString("access_token"));

                            Log.e("userToken",obj.getString("access_token"));

                            //Toast.makeText(globalVariable, ""+globalVariable.getToken(), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            Toast.makeText(globalVariable, "no json obj", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }


                        //Toast.makeText(LoginActivity.this, ""+response.toString(), Toast.LENGTH_LONG).show();
                        Toast.makeText(LoginActivity.this, "Successful LogIn", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(i);
                        Log.i("Tag", "Success");
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Error! Login Again", Toast.LENGTH_SHORT).show();
                VolleyLog.d("volley", "Error: " + error.getMessage());
                error.printStackTrace();
                Log.e("Tag", ""+error);
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", emailId);
                params.put("password", pass);
                params.put("grant_type", "password");
                return params;
            }

        };

        requestQueue.add(jsonObjRequest);
//        Intent i = new Intent(this, HomeActivity.class);
//        startActivity(i);
    }

    public void openHome(View view)
    {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

}