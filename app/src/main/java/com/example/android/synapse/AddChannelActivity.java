package com.example.android.synapse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AddChannelActivity extends AppCompatActivity
{
    private Button back,home,logout;
    private Button addChannelSubmit;
    private TextView input;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_channel);
        getSupportActionBar().hide();

        final GlobalVariable globalVariable = (GlobalVariable) getApplicationContext();
        back = findViewById(R.id.addChannelBack);
        home = findViewById(R.id.addChannelHome);
        logout = findViewById(R.id.addChannelLogout);
        input = findViewById(R.id.newChannelName);

        addChannelSubmit= findViewById(R.id.addChannelSubmit);

        addChannelSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final String userInput = input.getText().toString();
                if(userInput.matches("")){
                    Toast.makeText(globalVariable, "Enter Channel name", Toast.LENGTH_SHORT).show();
                }
                else{
                    input.setText("");
                    String s = globalVariable.getSomeVariable();
                    String name = input.getText().toString();
                    final String token = globalVariable.getToken();

                    //String URL = s+"api/channel/add/channelName";
                    String URL = s+"api/channel/add/"+userInput;
                    //API integration
                    StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (!response.equals(null)) {
                                Log.e("AdchannelResp",response);


                            }

                            else {
                                Toast.makeText(globalVariable, "response is null", Toast.LENGTH_SHORT).show();
                                Log.e("Your Array Response2", "Data Null");
                            }
                        }

                    }, new Response.ErrorListener() {
                        @Override



                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(globalVariable, "Error", Toast.LENGTH_SHORT).show();
                            Log.e("error is ", "" + error);
                        }
                    }) {

                        //This is for Headers If You Needed
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Content-Type", "application/json; charset=UTF-8");
                            //original
                            params.put("Authorization", "Bearer " + token);

                            return params;
                        }


                    };
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    queue.add(request);

                    //end new code

                    Toast.makeText(AddChannelActivity.this,"Request sent to Admin!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddChannelActivity.this,ChannelsActivity.class));
                }



                /*//new code


                String s = globalVariable.getSomeVariable();
                String name = input.getText().toString();
                final String token = globalVariable.getToken();

                //String URL = s+"api/channel/add/channelName";
                String URL = s+"api/channel/add/"+input;
                //API integration
                StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!response.equals(null)) {
                            Log.e("AdchannelResp",response);


                        }

                        else {
                            Toast.makeText(globalVariable, "response is null", Toast.LENGTH_SHORT).show();
                            Log.e("Your Array Response2", "Data Null");
                        }
                    }

                }, new Response.ErrorListener() {
                    @Override



                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(globalVariable, "Error", Toast.LENGTH_SHORT).show();
                        Log.e("error is ", "" + error);
                    }
                }) {

                    //This is for Headers If You Needed
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/json; charset=UTF-8");
                        //original
                        params.put("Authorization", "Bearer " + token);

                        return params;
                    }


                };
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(request);

                //end new code
*/






            }
        });

        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openChannels();
            }
        });

        home.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openHome();
            }
        });

        logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openMain();
            }
        });

    }

    public void openHome()
    {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

    public void openMain()
    {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void openChannels()
    {
        Intent i = new Intent(this, ChannelsActivity.class);
        startActivity(i);
    }
}
