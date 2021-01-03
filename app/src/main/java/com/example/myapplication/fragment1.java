package com.example.myapplication;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment1 extends Fragment {

    public ArrayList<ContactItem> contactList = new ArrayList<ContactItem>();

    public fragment1() { }

    public static fragment1 newInstance() {
        fragment1 fragment = new fragment1();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment1, null) ;
        /**
        ListViewAdapter adapter = new ListViewAdapter();
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, LIST_MENU)
        ListView listview = (ListView) view.findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        getContactList();   //핸드폰에서 불러오기.
        
        adapter = add_item_to_listviewadapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){

                Intent tt = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+contactList.get(position).getUser_phNumber()));
                //Intent tt = new Intent("android.intent.action.CALL", Uri.parse("tel:010"));
                startActivity(tt);
            }
        });
        */
        return view ;
    }


    public void getContactList(){
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Uri uri1 = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        Uri uri2 = ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI;

        Cursor cursor = null;
        Cursor cursor1= null;
        Cursor cursor2 = null;
        ContentResolver contentResolver = getActivity().getContentResolver();
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
                contactItem.setMail(cursor1.getString(
                        cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)
                ));
                contactItem.setAddress(cursor2.getString(
                        cursor2.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.DATA)
                ));

                /*
                contactItem.setPerson_id(cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts._ID)
                ));*/

                contactList.add(contactItem);
                cursor1.moveToNext();
                cursor2.moveToNext();
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    public ListViewAdapter add_item_to_listviewadapter(ListViewAdapter myadapter){
        /** to use Json contacts
        String json = "";
        json = getJsonString("contacts.json");
        jsonParsing(json); // arraylist 에 들어가게 됨.
        */
         String str = "-";
        //LIST_MENU.add(str);

        for(int i = 0; i< contactList.size() ; i++){
            ContactItem mv = contactList.get(i);
            str = "";
            str = str + mv.getUser_name();
            String str2 = "";
            str2 = str2 + mv.getUser_phNumber();

            myadapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.person), str, str2);

//            LIST_MENU.add(str);
        }

        return myadapter;
    }

    public String getJsonString(String filename)
    {
        String json = "";

        try {
            InputStream is = getActivity().getAssets().open(filename);
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


    public void jsonParsing(String json)
    {
        try{
            JSONObject jsonObject = new JSONObject(json);

            JSONArray contentArray = jsonObject.getJSONArray("Contacts");

            for(int i=0; i<contentArray.length(); i++)
            {
                JSONObject contentObject = contentArray.getJSONObject(i);

                ContactItem contact = new ContactItem();

                contact.setUser_name(contentObject.getString("name"));
                contact.setUser_phNumber(contentObject.getString("phonenumber"));
                contact.setAddress(contentObject.getString("address"));

                contactList.add(contact);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }

    }

}