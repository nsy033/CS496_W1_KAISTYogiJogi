package com.example.helloworld;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.material.tabs.TabLayout;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;

import android.provider.ContactsContract;
import android.widget.SimpleCursorAdapter;

import static android.graphics.ImageDecoder.decodeDrawable;
import static java.security.AccessController.getContext;
import static com.example.helloworld.Page1Fragment.newcontact;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    //static ArrayList<Contact> contactList = new ArrayList<Contact>();
    static ArrayList<ContactItem> contactItems = new ArrayList<ContactItem>();
    static ArrayList<GalleryImage> img = new ArrayList<>();
    /*
    static final int img[] = {
            R.drawable.winter1, R.drawable.winter2, R.drawable.winter3, R.drawable.winter4,
            R.drawable.winter5, R.drawable.winter6, R.drawable.winter7, R.drawable.winter8,
            R.drawable.winter1, R.drawable.winter2, R.drawable.winter3, R.drawable.winter4,
            R.drawable.winter5, R.drawable.winter6, R.drawable.winter7, R.drawable.winter8
    };
    */

    @RequiresApi(api = Build.VERSION_CODES.P)
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
        try {
            getGallery();
        } catch (IOException e) {
        e.printStackTrace();
        }

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    private final int GET_GALLERY_IMAGE = 200;

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void getGallery() throws IOException {

        Uri uri;
        Cursor cursor;
        int column_index_data;
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        cursor = getContentResolver().query(uri, projection, null,null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            File files = new File(absolutePathOfImage);
            Bitmap myBitmap = null;

            myBitmap = BitmapFactory.decodeFile(files.getAbsolutePath());

            Drawable d = new BitmapDrawable(getResources(), myBitmap);
            GalleryImage gi = new GalleryImage();
            
            gi.setRd(d);
            gi.setD(d);
            gi.setPath(absolutePathOfImage);
            img.add(gi);
        }
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            try {
                Bitmap btm = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                Drawable d = new BitmapDrawable(getResources(), btm);
                img.add(d);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
*/
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

}