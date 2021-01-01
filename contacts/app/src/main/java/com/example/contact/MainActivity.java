package com.example.contact;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    static String tag="get the intent";
    public static Intent newIntent(Context packageContext, UUID contactId) {
        Intent intent = new Intent(packageContext, MainActivity.class);
        intent.putExtra(tag, contactId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UUID contactid = (UUID) getIntent().getSerializableExtra(tag);
        FragmentManager fm=getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.framelay);


        if(fragment==null){
//            fragment=new contactfragment();
            fragment=contactfragment.newInstance(contactid);
            fm.beginTransaction().add(R.id.framelay,fragment).commit();
        }
    }
}
