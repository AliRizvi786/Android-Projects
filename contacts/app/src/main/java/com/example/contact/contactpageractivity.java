package com.example.contact;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.UUID;

public class contactpageractivity extends AppCompatActivity {
    static String tag="conid";
    private ViewPager vpager;
    private ArrayList<Contact> contactArrayList;
    public static Intent newIntent(Context packageContext, UUID id) {
        Intent intent = new Intent(packageContext, contactpageractivity.class);
        intent.putExtra(tag, id);
        return intent;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactpageractivity);

        UUID crimeId = (UUID) getIntent()
                .getSerializableExtra(tag);

        vpager = (ViewPager) findViewById(R.id.contact_view_pager);

        contactArrayList = Contactcreator.getContact(this).getcontactslist();
        FragmentManager fragmentManager = getSupportFragmentManager();
        vpager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Contact c = contactArrayList.get(position);
                return contactfragment.newInstance(c.id);
            }

            @Override
            public int getCount() {
                return contactArrayList.size();
            }
        });

        for (int i = 0; i < contactArrayList.size(); i++) {
            if (contactArrayList.get(i).id.equals(crimeId)) {
                vpager.setCurrentItem(i);
                break;
            }
        }
    }
}
