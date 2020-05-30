package com.example.android.synapse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity
{
    private TextView channel,members;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        final GlobalVariable globalVariable = (GlobalVariable) getApplicationContext()  ;
        //User Token
        String s = globalVariable.getToken();
        //Toast.makeText(globalVariable, ""+s, Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        channel =  findViewById(R.id.Channel);
        members =  findViewById(R.id.members);
        logout = findViewById(R.id.homeLogout);
        this.getSupportActionBar().hide();
        //Toast.makeText(this,"Home!", Toast.LENGTH_SHORT).show();


        channel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Toast.makeText(view.getContext(),"Your channels!", Toast.LENGTH_SHORT).show();
                openChannels(view);
            }
        });

        members.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Toast.makeText(view.getContext(),"Members!", Toast.LENGTH_SHORT).show();
                openMembers(view);
            }
        });

        logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                logout();
            }
        });

    }

    public void logout()
    {
        Intent i = new Intent(HomeActivity.this,LoginActivity.class);
        startActivity(i);
    }

    public void openChannels(View view)
    {
        Intent i = new Intent(this, ChannelsActivity.class);
        startActivity(i);
    }


    public void openMembers(View view)
    {
        Intent i = new Intent(this,MembersActivity.class);
        startActivity(i);
    }
}
