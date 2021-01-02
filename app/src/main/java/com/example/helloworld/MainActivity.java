package com.example.helloworld;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.material.tabs.TabLayout;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;

import android.provider.ContactsContract;

import static java.security.AccessController.getContext;
import static com.example.helloworld.Page1Fragment.newcontact;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    //static ArrayList<Contact> contactList = new ArrayList<Contact>();
    static ArrayList<ContactItem> contactItems = new ArrayList<ContactItem>();
    static final int img[] = {
            R.drawable.winter1, R.drawable.winter2, R.drawable.winter3, R.drawable.winter4,
            R.drawable.winter5, R.drawable.winter6, R.drawable.winter7, R.drawable.winter8,
            R.drawable.winter1, R.drawable.winter2, R.drawable.winter3, R.drawable.winter4,
            R.drawable.winter5, R.drawable.winter6, R.drawable.winter7, R.drawable.winter8
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        String json = "";
        json = getJsonString();
        jsonParsing(json); // arraylist 에 들어가게 됨.
 */
        getContactList();

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    public ArrayList<ContactItem> getContactList() {

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Uri uri1 = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        Uri uri2 = ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI;
        boolean flag_cursor1 = true;
        boolean flag_cursor2 = true;

        Cursor cursor = null;
        Cursor cursor1= null;
        Cursor cursor2 = null;
        ContentResolver contentResolver = getContentResolver();
        String sortorder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
        try{
            cursor = contentResolver.query(uri, null,null,null, sortorder);
            cursor1 = contentResolver.query(uri1, null,null,null, sortorder);
            cursor2 = contentResolver.query(uri2, null,null,null, sortorder);
        } catch(Exception ex) {
            Log.e("Error on contact", ex.getMessage());
        }

        if(cursor.moveToFirst()) {
            cursor1.moveToFirst();
            cursor2.moveToFirst();

            do {
                ContactItem contactItem = new ContactItem();
/*
                String[] temp = cursor.getColumnNames();
                for(int i=0; i<temp.length; i++){
                    System.out.println(temp[i]);
                }
*/
/*
                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                {
                    // Query phone here. Covered next
                    int ColumeIndex_ID = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                    String id = cursor.getString(ColumeIndex_ID);
                    Cursor phones = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,
                            null,
                            null);
                    while (phones.moveToNext()) {
                        String str = (String) ContactsContract.CommonDataKinds.Phone.NUMBER;
                        int tmp = (int) phones.getColumnIndex(str);
                        String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.i("Number", phoneNumber);
                        contactItem.setUser_phNumber(phoneNumber);
                    }
                    phones.close();
                }

 */
                contactItem.setUser_name(cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                ));
                contactItem.setUser_phNumber(cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                ));
                contactItem.setPhoto_id(cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_ID)
                ));

                if(cursor1.moveToNext() && flag_cursor1){
                    cursor1.moveToPrevious();
                    contactItem.setMail(cursor1.getString(
                            cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)
                    ));
                }
                else if(flag_cursor1){
                    cursor1.moveToPrevious();
                    contactItem.setMail(cursor1.getString(
                            cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)
                    ));
                    flag_cursor1 = false;
                }

                if(cursor2.moveToNext() && flag_cursor2){
                    cursor2.moveToPrevious();
                    contactItem.setAddress(cursor2.getString(
                            cursor2.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.DATA)
                    ));
                }
                else if(flag_cursor2){
                    cursor2.moveToPrevious();
                    contactItem.setAddress(cursor2.getString(
                            cursor2.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.DATA)
                    ));
                    flag_cursor2 = false;
                }

                /*
                contactItem.setPerson_id(cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts._ID)
                ));*/

                contactItems.add(contactItem);
                cursor1.moveToNext();
                cursor2.moveToNext();
            } while (cursor.moveToNext());
        }
        cursor.close();
        return contactItems;
    }
/*
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

                ContactItem contact = new ContactItem();

                contact.setUser_name(contentObject.getString("name"));
                contact.setUser_phNumber(contentObject.getString("phonenumber"));

                contactItems.add(contact);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
*/


}