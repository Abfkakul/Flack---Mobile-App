package com.example.android.synapse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PrivateChat extends AppCompatActivity
{
    private Button openList,sendButton,home,back,logout,get;
    private EditText newMessage;
    final ArrayList<ChatMessage> list = new ArrayList<ChatMessage>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_chat);

        getSupportActionBar().hide();
        newMessage = findViewById(R.id.newMessage);
        sendButton = findViewById(R.id.messageSend);
        home = findViewById(R.id.Home);
        back = findViewById(R.id.Back);
        logout = findViewById(R.id.Logout);
        get = findViewById(R.id.fetch);

        //User Token
        final GlobalVariable globalVariable = (GlobalVariable) getApplicationContext();
        final String token = globalVariable.getToken();

        String s = globalVariable.getSomeVariable();
        String fromId = globalVariable.getFromUserId();
        //Log.e("matchingId",fromId);
        //Log.e("token",token);

        //String URL = s+"api/Message?FromID={fromId}";
        //String URL = s+"api/Message?FromID=2d72f529-2fd1-4d2c-8231-7a30e9f1c4d7";

        //String URL = s+"api/Message?FromID={"+fromId+"}";

        //original
        String URL = s+"api/Message?FromID="+fromId;


        //API integration
        RequestQueue requestQueue = Volley.newRequestQueue(PrivateChat.this);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.equals(null)) {
                    //Toast.makeText(globalVariable, ""+response, Toast.LENGTH_SHORT).show();
                    //Toast.makeText(globalVariable, "Response is not null", Toast.LENGTH_SHORT).show();
                    Log.e("Personal chat Response", response);

                    try {
                        /*JSONArray arr = new JSONArray(response);
                        for(int m=0; m<arr.length(); m++){
                            JSONObject jObj = arr.getJSONObject(m);
                            String fromId = jObj.getString("FromUserID");
                            String toId = jObj.getString("ToUserID");
                            String mess = jObj.getString("MessageBody");
                            String time = jObj.getString("TimeSent");
                            String messId = jObj.getString("ID");
                            globalVariable.setToUserId(fromId);

                            list.add(new ChatMessage(messId,toId,fromId,mess,time));


                            //understand Async callback flow
                            //Toast.makeText(globalVariable, ""+m, Toast.LENGTH_SHORT).show();
                        }*/


                        JSONArray arr = new JSONArray(response);
                        for(int m=arr.length()-1; m>=0; m--){
                            JSONObject jObj = arr.getJSONObject(m);
                            String fromId = jObj.getString("FromUserID");
                            String toId = jObj.getString("ToUserID");
                            String mess = jObj.getString("MessageBody");
                            String time = jObj.getString("TimeSent");
                            String messId = jObj.getString("ID");
                            globalVariable.setToUserId(fromId);

                            list.add(new ChatMessage(messId,toId,fromId,mess,time));

                        }



                    } catch (JSONException e) {
                        Toast.makeText(globalVariable, "Error!reading response body", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    generateMessages();

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
        //RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        //queue.add(request);
        requestQueue.add(request);


    }

    public String getCurrentTimeStamp()
    {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        String timeStamp = df.format("dd-MM-yyyy hh:mm", cal).toString();
        return timeStamp;
    }

    public void generateMessages(){

        final ChatMessageAdapter listAdapter = new ChatMessageAdapter(this,list,R.color.darkblue);
        final ListView listView = (ListView)findViewById(R.id.messageList);



        sendButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Toast.makeText(PrivateChat.this, "Testing", Toast.LENGTH_SHORT).show();
                final String input = newMessage.getText().toString();
                final GlobalVariable globalVariable = (GlobalVariable) getApplicationContext();


                if(input.matches("")){
                    Toast.makeText(globalVariable, "Enter message first", Toast.LENGTH_SHORT).show();
                }
                else{

                    newMessage.setText("");
                    final String token = globalVariable.getToken();
                    String s = globalVariable.getSomeVariable();
                    String messSentTo = globalVariable.getFromUserId();
                    String messBody = input;

                    //messBody = messBody.replaceAll(" ","%20");

                    //String URL = s+"api/Message/send/ToUser/messagebody";
                    String URL = s+"api/Message/send/"+messSentTo+"/"+messBody;



                    //API integration
                    RequestQueue requestQueue = Volley.newRequestQueue(PrivateChat.this);
                    StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (!response.equals(null)) {
                                Log.e("messageSent res:", response);
                            }

                            else {
                                Toast.makeText(globalVariable, "response is nullll", Toast.LENGTH_SHORT).show();
                                //Log.e("Your Array Response2", "Data Null");
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
                    requestQueue.add(request);




                    //2nd
 /*                   JSONObject obj = new JSONObject();


                    String data = "{"+
                                "\"ToUserId\"" + "\"" + globalVariable.getFromUserId() + "\","+
                                "\"MessageBody\"" + "\"" + input + "\""+
                                "}";


                    try{
                        obj.put("ToUserID",globalVariable.getFromUserId());
                        obj.put("MessageBody","aaassddd");
                    } catch(JSONException e){
                        e.printStackTrace();
                    }


                    //API integration
                    RequestQueue requestQueue = Volley.newRequestQueue(PrivateChat.this);

                    //Json Object (request object)
                    JsonObjectRequest objectRequest = new JsonObjectRequest(
                            Request.Method.POST,
                            URL,
                            obj,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.e("SendMessResponse",response.toString());
                                    Toast.makeText(PrivateChat.this, "Success", Toast.LENGTH_SHORT).show();

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("onfailuremessres",error.toString());
                                    Toast.makeText(PrivateChat.this, "failure", Toast.LENGTH_SHORT).show();
                                }
                            }

                    ){

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Content-Type", "application/json; charset=UTF-8");
                            //original
                            params.put("Authorization", "Bearer " + token);

                            return params;
                        }

                    };


                    requestQueue.add(objectRequest);
*/



                    //1st approach

/*

                    StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                            new Response.Listener<String>()
                            {
                                @Override
                                public void onResponse(String response) {
                                    // response
                                    Log.e("Response", response);
                                }
                            },
                            new Response.ErrorListener()
                            {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Log.e("Error.Response", ""+error);

                                }
                            }
                    )


                    {
                        //Body

                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String, String>  params = new HashMap<String, String>();
                            //params.put("ID", "100");
                            //params.put("FromUserID", globalVariable.getToUserId());
                            params.put("ToUserID",globalVariable.getFromUserId());
                            params.put("MessageBody",input);
                            //params.put("TimeSent",getCurrentTimeStamp());

                            return params;
                        }

                        //Header
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError{
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Content-Type", "application/json; charset=UTF-8");
                            //original
                            params.put("Authorization", "Bearer " + token);

                            return params;
                        }





                    };
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    queue.add(postRequest);

*/


                }


            }
        });


        //get button fetches new messages
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //new code
                //User Token
                final GlobalVariable globalVariable = (GlobalVariable) getApplicationContext();
                final String token = globalVariable.getToken();

                String s = globalVariable.getSomeVariable();
                String fromId = globalVariable.getFromUserId();


                //original
                String URL = s+"api/Message?FromID="+fromId;


                //API integration
                RequestQueue requestQueue = Volley.newRequestQueue(PrivateChat.this);
                StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!response.equals(null)) {
                            //Toast.makeText(globalVariable, ""+response, Toast.LENGTH_SHORT).show();
                            //Toast.makeText(globalVariable, "Response is not null", Toast.LENGTH_SHORT).show();
                            Log.e("PersonalchatReseagain", response);

                            list.clear();

                            try {
                               /* JSONArray arr = new JSONArray(response);
                                for(int m=0; m<arr.length(); m++){
                                    JSONObject jObj = arr.getJSONObject(m);
                                    String fromId = jObj.getString("FromUserID");
                                    String toId = jObj.getString("ToUserID");
                                    String mess = jObj.getString("MessageBody");
                                    String time = jObj.getString("TimeSent");
                                    String messId = jObj.getString("ID");
                                    globalVariable.setToUserId(fromId);

                                    list.add(new ChatMessage(messId,toId,fromId,mess,time));


                                    //understand Async callback flow
                                    //Toast.makeText(globalVariable, ""+m, Toast.LENGTH_SHORT).show();
                                }*/


                               //reverse messages
                                JSONArray arr = new JSONArray(response);
                                for(int m=arr.length()-1; m>=0; m--){
                                    JSONObject jObj = arr.getJSONObject(m);
                                    String fromId = jObj.getString("FromUserID");
                                    String toId = jObj.getString("ToUserID");
                                    String mess = jObj.getString("MessageBody");
                                    String time = jObj.getString("TimeSent");
                                    String messId = jObj.getString("ID");
                                    globalVariable.setToUserId(fromId);

                                    list.add(new ChatMessage(messId,toId,fromId,mess,time));

                                }
                            } catch (JSONException e) {
                                Toast.makeText(globalVariable, "Error!reading response body", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }

                            generateMessages();

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
                requestQueue.add(request);



                //new code ends here
            }
        });



        listView.setAdapter(listAdapter);

        home.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openHome(view);
            }
        });
        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                back(view);
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
    }

    public void openMain(View view)
    {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void openHome(View view)
    {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

    public void back(View view)
    {
        Intent i = new Intent(this, MembersActivity.class);
        startActivity(i);
    }
}
