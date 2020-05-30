package com.example.android.synapse;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Using set and get  global varibale in any activity
/*        ((GlobalVariable) this.getApplication()).setSomeVariable("foo");
        String s = ((GlobalVariable) this.getApplication()).getSomeVariable();
        Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();*/

        final Button signup =(Button) findViewById(R.id.signup);
        final Button login = (Button) findViewById(R.id.login);

        signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Toast.makeText(view.getContext(), "signup!", Toast.LENGTH_SHORT).show();
                openSignupPage(signup);
            }
        });

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Toast.makeText(view.getContext(), "Login!", Toast.LENGTH_SHORT).show();
                openLoginPage(login);
                //Toast.makeText(view.getContext(), "Login!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void openSignupPage(View view)
    {
        Intent i = new Intent(this,Signup.class);
        startActivity(i);
    }

    public void openLoginPage(View view)
    {
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
    }

}