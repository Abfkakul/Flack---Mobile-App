package com.example.android.synapse;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MembersActivity extends AppCompatActivity
{

    final ArrayList<Member> list = new ArrayList<Member>();
    private Button home,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);


        //User Token
        final GlobalVariable globalVariable = (GlobalVariable) getApplicationContext()  ;
        final String token = globalVariable.getToken();
        //Toast.makeText(globalVariable, ""+token, Toast.LENGTH_SHORT).show();

        String s = globalVariable.getSomeVariable();
        String URL = s+"api/UserChat";


        //API integration
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.equals(null)) {
                    //Toast.makeText(globalVariable, ""+response, Toast.LENGTH_SHORT).show();
                    Log.e("usersData",response);
                    Log.e("Viewmemberlist Response", response);
                    try {
                        //JSONObject obj = new JSONObject(response);

                        JSONArray arr = new JSONArray(response);
                        for(int m=0; m<arr.length(); m++){
                            JSONObject jObj = arr.getJSONObject(m);
                            String email = jObj.getString("Email");

                            //list.add(new Member(email));

                            String fromId = jObj.getString("Id");
                            list.add(new Member(email,fromId));

                            //understand Async callback flow
                            //Toast.makeText(globalVariable, ""+m, Toast.LENGTH_SHORT).show();
                        }

                        generateList();

                    } catch (JSONException e) {
                        Toast.makeText(globalVariable, "Failure in reading response body", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }


                } else {
                    Toast.makeText(globalVariable, "response is null", Toast.LENGTH_SHORT).show();
                    Log.e("Your Array Response", "Data Null");
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
                params.put("Authorization", "Bearer " + token);
                return params;
            }


        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);





        home = findViewById(R.id.memberHome);
        logout =  findViewById(R.id.memberLogout);
        this.getSupportActionBar().hide();

        home.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openHome(view);
            }
        });
        logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openMain(view);
            }
        });

        //understand Async callback flow
        //Toast.makeText(globalVariable, "out", Toast.LENGTH_SHORT).show();


    }

    public void generateList(){
        final MemberAdapter listAdapter = new MemberAdapter(this,list,R.color.darkskyblue);
        final ListView listView = (ListView)findViewById(R.id.membersList);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id)
            {
                Member member= list.get(position);

                //Storing Id in global variable of selected member
                final GlobalVariable globalVariable = (GlobalVariable) getApplicationContext()  ;
                //globalVariable.setUserId(member.getId());

                globalVariable.setFromUserId(member.getId());


                //Toast.makeText(globalVariable, ""+member.getName()+""+member.getId(), Toast.LENGTH_LONG).show();

                startActivity(new Intent(MembersActivity.this,PrivateChat.class));
                //Toast.makeText(MembersActivity.this,"Member name : "+ member.getName(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(MembersActivity.this, ""+member.getId(), Toast.LENGTH_SHORT).show();
                Log.e("ID:",member.getId());
            }
        });
    }

    public void openHome(View view)
    {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

    public void openMain(View view)
    {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}