package com.example.android.synapse;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DiscussionActivity extends AppCompatActivity
{
    private TextView home,back,logout;
    private Button newMsg,get2;
    //remove me if any error
    //private ListView list;
    final Context context = this;
    final ArrayList<Discussion> list = new ArrayList<Discussion>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);
        this.getSupportActionBar().hide();

        home = findViewById(R.id.Home);
        back = findViewById(R.id.Back);
        logout = findViewById(R.id.Logout);
        newMsg = findViewById(R.id.addMessage);
        get2 = findViewById(R.id.fetch2);


        //remove me if any error
        //list = findViewById(R.id.discussionList);


        //User Token
        final GlobalVariable globalVariable = (GlobalVariable) getApplicationContext()  ;
        final String token = globalVariable.getToken();
        String s = globalVariable.getSomeVariable();
        String channelId = globalVariable.getChannelId();

        //String URL = s+"api/channel/ChannelMessages?channelID={channelID};
        String URL = s+"api/channel/ChannelMessages?channelID="+channelId;

        //API integration
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.equals(null)) {
                    Log.e("ChanldiscussionResponse",response);
                    //Toast.makeText(globalVariable, "Response is not null", Toast.LENGTH_SHORT).show();

                    JSONArray arr = null;
                    try {
                        /*arr = new JSONArray(response);
                        for(int m=0 ; m<arr.length() ; m++){
                            JSONObject jObj = arr.getJSONObject(m);
                            String fromId = jObj.getString("FromUserID");
                            String mess = jObj.getString("MessageBody");
                            String time = jObj.getString("TimeSent");

                            list.add(new Discussion(fromId,mess,time));
                        }*/


                        //reverse message logic
                                arr = new JSONArray(response);
                                for(int m=arr.length()-1 ; m>=0 ; m--){
                                    JSONObject jObj = arr.getJSONObject(m);
                                    String fromId = jObj.getString("FromUserID");
                                    String mess = jObj.getString("MessageBody");
                                    String time = jObj.getString("TimeSent");



                                    list.add(new Discussion(fromId,mess,time));
                                }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    generateChannelMessages();

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




        newMsg.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {

                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompts, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog,int id)
                                    {
                                        // get user input and set it to result
                                        // edit text
                                        //adds message on top
                                        //list.add(0,getMessage(userInput));
                                        //list.add(new Discussion("New message User",msg,date));
                                        //result.setText(userInput.getText());

                                        //new code for msg send
                                        //String URL = s+"api/hannel/send/channelid/messgae;


                                        //User Token
                                        final GlobalVariable globalVariable = (GlobalVariable) getApplicationContext()  ;
                                        final String token = globalVariable.getToken();
                                        String s = globalVariable.getSomeVariable();
                                        String channelId = globalVariable.getChannelId();
                                        String message = userInput.getText().toString();

                                        //message = message.replaceAll(" ","%20");
                                        //URL sourceUrl = new URL (sourceUrl);

                                        String URL = s+"api/channel/send/"+channelId+"/"+message;

                                        //API integration
                                        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                if (!response.equals(null)) {
                                                    Log.e("sendmsgtodiscroomResp",response);


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




                                        //end here new code


                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id)
                                    {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();


            }





        });

        //on get click button

        //new code


        get2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //User Token
                final GlobalVariable globalVariable = (GlobalVariable) getApplicationContext()  ;
                final String token = globalVariable.getToken();
                String s = globalVariable.getSomeVariable();
                String channelId = globalVariable.getChannelId();
                //Toast.makeText(globalVariable, "Onclick", Toast.LENGTH_SHORT).show();

                //String URL = s+"api/channel/ChannelMessages?channelID={channelID};
                String URL = s+"api/channel/ChannelMessages?channelID="+channelId;

                //API integration
                StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!response.equals(null)) {
                            Log.e("ChanldiscussionResponse",response);
                            //Toast.makeText(globalVariable, "Response is not null", Toast.LENGTH_SHORT).show();

                            list.clear();

                            JSONArray arr = null;
                            try {
                                /*arr = new JSONArray(response);
                                for(int m=0 ; m<arr.length() ; m++){
                                    JSONObject jObj = arr.getJSONObject(m);
                                    String fromId = jObj.getString("FromUserID");
                                    String mess = jObj.getString("MessageBody");
                                    String time = jObj.getString("TimeSent");



                                    list.add(new Discussion(fromId,mess,time));
                                }
*/
                                //reverse message logic
                                arr = new JSONArray(response);
                                for(int m=arr.length()-1 ; m>=0 ; m--){
                                    JSONObject jObj = arr.getJSONObject(m);
                                    String fromId = jObj.getString("FromUserID");
                                    String mess = jObj.getString("MessageBody");
                                    String time = jObj.getString("TimeSent");



                                    list.add(new Discussion(fromId,mess,time));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            generateChannelMessages();

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


            }
        });


        //end new code

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


    public void generateChannelMessages(){
        final DiscussionAdapter listAdapter = new DiscussionAdapter(this,list,R.color.darkpurple);
        final ListView listView = (ListView)findViewById(R.id.discussionList);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id)
            {
                Discussion message = list.get(position);
                //Toast.makeText(DiscussionActivity.this,"Message  : "+ message.getMessage()+"\tMember name : " + message.getName() +"\t"+message.getTimeStamp(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    public String getCurrentTimeStamp()
    {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        String timeStamp = df.format("dd-MM-yyyy hh:mm", cal).toString();
        return timeStamp;
    }

    public Discussion getMessage(EditText userInput)
    {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        String date = df.format("dd-MM-yyyy hh:mm", cal).toString();
        String msg = userInput.getText().toString();
        Discussion newDiscussion = new Discussion("Current user",msg,date);
        return newDiscussion;
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
        Intent i = new Intent(this, ChannelsActivity.class);
        startActivity(i);
    }
}
