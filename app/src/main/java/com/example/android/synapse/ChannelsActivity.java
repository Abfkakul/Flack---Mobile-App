package com.example.android.synapse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChannelsActivity extends AppCompatActivity
{
    private TextView home,back,logout;
    private Button addChannel;
    final ArrayList<Channel> list = new ArrayList<Channel>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channels_list);
        this.getSupportActionBar().hide();


        //start
        //User Token
        final GlobalVariable globalVariable = (GlobalVariable) getApplicationContext()  ;
        final String token = globalVariable.getToken();
        //Toast.makeText(globalVariable, ""+token, Toast.LENGTH_SHORT).show();

        String s = globalVariable.getSomeVariable();
        String URL = s+"api/channel/GetChannelsForCurrentUser";

        //API integration
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.equals(null)) {
                    //Toast.makeText(globalVariable, ""+response, Toast.LENGTH_SHORT).show();
                    //Log.e("channelview:",response);
                    Log.e("ViewchannellistResponse", response);
                    try {
                        //JSONObject obj = new JSONObject(response);

                        JSONArray arr = new JSONArray(response);
                        for(int m=0; m<arr.length(); m++){
                            JSONObject jObj = arr.getJSONObject(m);
                            GlobalVariable globalVariable = new GlobalVariable();
                            String channelName = jObj.getString("ChannelName");
                            String channelId = jObj.getString("ChannelID");
                            list.add(new Channel(channelName,channelId));
                        }

                        generateChannelList();

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


//end


        home = findViewById(R.id.channelHome);
         logout = findViewById(R.id.channelLogout);
         addChannel = findViewById(R.id.addChannel);


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
        addChannel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Toast.makeText(globalVariable, "add channel", Toast.LENGTH_SHORT).show();
                openAddChannel(view);
            }
        });

    }


    public void generateChannelList(){
        final ChannelAdapter listAdapter = new ChannelAdapter(this,list,R.color.darkpurple);
        final ListView listView = (ListView)findViewById(R.id.channelList);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id)
            {
                Channel channel = list.get(position);
                final GlobalVariable globalVariable = (GlobalVariable) getApplicationContext();
                globalVariable.setChannelId(channel.getId());
                Log.e("onClickChannelId:",channel.getId());
                startActivity(new Intent(ChannelsActivity.this,DiscussionActivity.class));
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

    public void openAddChannel(View view)
    {
        Intent i = new Intent(this, AddChannelActivity.class);
        startActivity(i);
    }
}