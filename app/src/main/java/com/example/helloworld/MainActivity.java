package com.example.helloworld;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    static ArrayList<Contact> contactList = new ArrayList<Contact>();
    static final int img[] = {
            R.drawable.winter1, R.drawable.winter2, R.drawable.winter3, R.drawable.winter4,
            R.drawable.winter5, R.drawable.winter6, R.drawable.winter7, R.drawable.winter8,
            R.drawable.winter1, R.drawable.winter2, R.drawable.winter3, R.drawable.winter4,
            R.drawable.winter5, R.drawable.winter6, R.drawable.winter7, R.drawable.winter8
    };
    static final int department[] = {
            R.drawable.aerospace, R.drawable.brain, R.drawable.business, R.drawable.chemistry,
            R.drawable.civil, R.drawable.computer, R.drawable.design, R.drawable.dna,
            R.drawable.electrical, R.drawable.math, R.drawable.mechanic, R.drawable.nuclear,
            R.drawable.physics, R.drawable.system
    };
    static final String name[] = {
            "AE", "BBE", "BTM", "CH", "CEE", "CS", "ID", "BS", "EE", "MS", "ME", "NQE", "PH", "ISE"
    };
    static final String des[] = {
            "Aerospace Engineering", "Bio and Brain Engineering", "Business Technology Management", "Chemistry",
            "Civil and Environmental Engineering", "Computer Science", "Industrial Design", "Biological Sciences", "Electrical Engineering",
            "Mathematical Sciences", "Mechanical Engineering", "Nuclear and Quantum Engineering", "Physics", "Industrial and Systems Engineering"
    };

    static final String pos[] = {
            "AE", "BBE", "BTM", "CH", "CEE", "CS", "ID", "BS", "EE", "MS", "ME", "NQE", "PH", "ISE"
    };
    static final String tel[] = {
            "+82-42-350-4502", "+82-42-350-4502","+82-42-350-4502","+82-42-350-4502",
            "+82-42-350-4502","+82-42-350-4502","+82-42-350-4502","+82-42-350-4502",
            "+82-42-350-4502","+82-42-350-4502","+82-42-350-4502","+82-42-350-4502",
            "+82-42-350-4502","+82-42-350-4502"
    };
    static final String fax[] = {
            "+82-42-350-4510", "+82-42-350-4510","+82-42-350-4510","+82-42-350-4510",
            "+82-42-350-4510","+82-42-350-4510","+82-42-350-4510","+82-42-350-4510",
            "+82-42-350-4510","+82-42-350-4510","+82-42-350-4510","+82-42-350-4510",
            "+82-42-350-4510","+82-42-350-4510"
    };
    static final int resId[] = {
            R.drawable.winter1, R.drawable.winter2, R.drawable.winter3, R.drawable.winter4,
            R.drawable.winter5, R.drawable.winter6, R.drawable.winter7, R.drawable.winter8,
            R.drawable.winter1, R.drawable.winter2, R.drawable.winter3, R.drawable.winter4,
            R.drawable.winter5, R.drawable.winter6, R.drawable.winter7, R.drawable.winter8
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String json = "";
        json = getJsonString();
        jsonParsing(json); // arraylist 에 들어가게 됨.

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    private String getJsonString()
    {
        String json = "";

        try {
            InputStream is = getAssets().open("contact.json");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return json;
    }

    private void jsonParsing(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONArray contentArray = jsonObject.getJSONArray("Contacts");

            for (int i = 0; i < contentArray.length(); i++) {
                JSONObject contentObject = contentArray.getJSONObject(i);

                Contact contact = new Contact();

                contact.setName(contentObject.getString("name"));
                contact.setPhonenumber(contentObject.getString("phonenumber"));
                contact.setAddress(contentObject.getString("address"));

                contactList.add(contact);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}