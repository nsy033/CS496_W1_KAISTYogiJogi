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
        ListViewAdapter adapter = new ListViewAdapter();
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, LIST_MENU)
        ListView listview = (ListView) view.findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        getContactList();   //핸드폰에서 불러오기.
        
        adapter = add_item_to_listviewadapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                /**

                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                Contact con = contactList.get(position);
                dlg.setTitle("Name : " + con.getName() );
                dlg.setMessage("PhoneNumber : " +
                        con.getPhonenumber() +
                        "\nAddress : " +
                        con.getAddress());
                dlg.setNegativeButton("Close", null);
                dlg.show();
                 */
                Intent tt = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+contactList.get(position).getUser_phNumber()));
                //Intent tt = new Intent("android.intent.action.CALL", Uri.parse("tel:010"));
                startActivity(tt);
            }
        });

        return view ;
    }


    public void getContactList(){
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = {
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        };

        String[] selectionArgs = null;
        //ContentResolver ccc = getContentResolver();

        String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
        Cursor cursor = getContext().getContentResolver().query(uri,projection,null,selectionArgs,sortOrder);
        //Cursor cursor = getActivity().getContentResolver().query(uri,projection,null,null,null);

        //LinkedHashSet<Contact> hashlist = new LinkedHashSet<>();
        if (cursor.moveToFirst()){
            do {
                ContactItem newcontact = new ContactItem();

                String user_name = cursor.getString(1);
                newcontact.setUser_Name(user_name);
                String phone_number = cursor.getString(0);
                //String phone_number = "010-1333-4456";
                newcontact.setUser_phNumber(phone_number);
                String address = "test@kaist.ac.kr";
                newcontact.setUser_Email(address);
                contactList.add(newcontact);

            }while(cursor.moveToNext());
        }

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
            str = str + mv.getUser_Name();
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

                contact.setUser_Name(contentObject.getString("name"));
                contact.setUser_phNumber(contentObject.getString("phonenumber"));
                contact.setUser_Email(contentObject.getString("address"));

                contactList.add(contact);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }

    }

}