package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    static ArrayList<String> LIST_MENU = new ArrayList<String>();
    private ArrayList<Contact> contactList = new ArrayList<Contact>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String json = "";
        json = getJsonString();
        jsonParsing(json); // arraylist 에 들어가게 됨.
        String str = "-";
        LIST_MENU.add(str);

        for(int i = 0; i< contactList.size() ; i++){
            Contact mv = contactList.get(i);
            str = "";
            str = str + mv.getName() + " " + mv.getPhonenumber() + " " + mv.getAddress();
            LIST_MENU.add(str);
        }

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
            InputStream is = getAssets().open("contacts.json");
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

    private void jsonParsing(String json)
    {
        try{
            JSONObject jsonObject = new JSONObject(json);

            JSONArray contentArray = jsonObject.getJSONArray("Contacts");

            for(int i=0; i<contentArray.length(); i++)
            {
                JSONObject contentObject = contentArray.getJSONObject(i);

                Contact contact = new Contact();

                contact.setName(contentObject.getString("name"));
                contact.setPhonenumber(contentObject.getString("phonenumber"));
                contact.setAddress(contentObject.getString("address"));

                contactList.add(contact);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
}