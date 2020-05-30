package com.example.android.synapse;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import android.app.Application;
import org.json.JSONException;
import org.json.JSONObject;

public class Signup extends AppCompatActivity
{
    private Button submit;
    private TextView name,emailView,passwordView, confirmPasswordView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

//
//        final String a = ((GlobalVariable) this.getApplication()).getSomeVariable();
       // Toast.makeText(this, ""+a, Toast.LENGTH_SHORT).show();

        getSupportActionBar().setTitle("User Registeration");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.darkblue)));


        submit =  findViewById(R.id.signupSubmit);
        name = findViewById(R.id.signupName);
        emailView =  findViewById(R.id.signupEmail);
        passwordView = findViewById(R.id.signupPassword);
        confirmPasswordView = findViewById(R.id.signupConfirmPassword);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        TextView back = (TextView) findViewById(R.id.signupBack);

        final String email=emailView.getText().toString();
        final String password=passwordView.getText().toString();
        final String confirmPassword = confirmPasswordView.getText().toString();



        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openHome(view);
            }
        });

        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                /*String em =emailView.getText().toString();
                String pass1 = passwordView.getText().toString();
                String pass2 = passwordView.getText().toString();


                JSONObject obj = new JSONObject();
                try{
                    obj.put("Email",em);
                    obj.put("Password",pass1);
                    obj.put("ConfirmPassword",pass2);
                } catch(JSONException e){
                    e.printStackTrace();
                }


                //API integration
                //Get global variable
                GlobalVariable globalVariable = (GlobalVariable) getApplicationContext()  ;
                String s = globalVariable.getSomeVariable();
                String URL = s+"api/Account/Register";
                //Toast.makeText(Signup.this, ""+URL, Toast.LENGTH_LONG).show();
                RequestQueue requestQueue = Volley.newRequestQueue(Signup.this);

                //Json Object (request object)
                JsonObjectRequest objectRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        URL,
                        obj,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.e("Rest Response",response.toString());
                                //Toast.makeText(Signup.this, "Success", Toast.LENGTH_SHORT).show();
                                //Toast.makeText(Signup.this, ""+response.toString(), Toast.LENGTH_SHORT).show();

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Rest Response",error.toString());
                                Toast.makeText(Signup.this, "Error", Toast.LENGTH_SHORT).show();
                                //Toast.makeText(Signup.this, ""+error.toString(), Toast.LENGTH_SHORT).show();

                            }
                        }
                );

                requestQueue.add(objectRequest);
*/
                progressBar.setVisibility(View.VISIBLE);

                String n = name.getText().toString();
                String emailId = emailView.getText().toString();
                String nuEmail = "@nu.edu.pk";
                String p1=passwordView.getText().toString();
                String p2=confirmPasswordView.getText().toString();


                if((n.isEmpty()) || (emailId.isEmpty()) || (p1.isEmpty()) || (p2.isEmpty()))
                {
                    progressBar.setVisibility(View.GONE);
                    emailView.setError("Please fill all fields");
                    emailView.requestFocus();
                    name.requestFocus();
                    passwordView.requestFocus();
                    Toast.makeText(Signup.this, "Fill up all fields", Toast.LENGTH_SHORT).show();
                }
                else if(emailId.length()==17)
                {

                    progressBar.setVisibility(View.GONE);
                    String subEmail = emailId.substring(7,17);

                    if((!nuEmail.equals(subEmail.toLowerCase()))) {
                        progressBar.setVisibility(View.GONE);
                        emailView.setError("Please enter correct Nu-mail");
                        emailView.requestFocus();
                        emailView.setText("");

                    }
                    else{
                        if(p1.equals(p2)){
                            //start
                            String em =emailView.getText().toString();
                            String pass1 = passwordView.getText().toString();
                            String pass2 = passwordView.getText().toString();


                            JSONObject obj = new JSONObject();
                            try{
                                obj.put("Email",em);
                                obj.put("Password",pass1);
                                obj.put("ConfirmPassword",pass2);
                            } catch(JSONException e){
                                e.printStackTrace();
                            }


                            //API integration
                            //Get global variable
                            GlobalVariable globalVariable = (GlobalVariable) getApplicationContext()  ;
                            String s = globalVariable.getSomeVariable();
                            String URL = s+"api/Account/Register";
                            //Toast.makeText(Signup.this, ""+URL, Toast.LENGTH_LONG).show();
                            RequestQueue requestQueue = Volley.newRequestQueue(Signup.this);

                            //Json Object (request object)
                            JsonObjectRequest objectRequest = new JsonObjectRequest(
                                    Request.Method.POST,
                                    URL,
                                    obj,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            Log.e("Rest Response",response.toString());
                                            //Toast.makeText(Signup.this, "Success", Toast.LENGTH_SHORT).show();
                                            //Toast.makeText(Signup.this, ""+response.toString(), Toast.LENGTH_SHORT).show();

                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.e("Rest Response",error.toString());
                                            Toast.makeText(Signup.this, "Successful SignUp!", Toast.LENGTH_SHORT).show();
                                            //Toast.makeText(Signup.this, ""+error.toString(), Toast.LENGTH_SHORT).show();

                                        }
                                    }
                            );

                            requestQueue.add(objectRequest);
                            //end

                            openLoginPage();

                        }
                        else{
                            Toast.makeText(Signup.this, "Password do not match", Toast.LENGTH_SHORT).show();
                            signUp();
                        }
                    }
                }
                else
                {
                    Toast.makeText(view.getContext(),"Enter correct Nu-mail", Toast.LENGTH_SHORT).show();
                    signUp();

                }
            }
        });
    }


    public void openLoginPage()
    {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }


    public void openHome(View view)
    {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void back(View view)
    {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

    public void signUp(){
        Intent i = new Intent(this, Signup.class);
        startActivity(i);
    }

}