package com.example.helloworld;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.helloworld.Page3Fragment.dep_icon;
import static com.example.helloworld.Page3Fragment.dep_name;


public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private long time = 0;
    public static Context context_main;
    public static Bitmap[] sized = new Bitmap[6];
    public static int[] forsized = {R.drawable.iconuser, R.drawable.iconhome, R.drawable.iconsearch, R.drawable.iconadd, R.drawable.go, R.drawable.web};

    //static ArrayList<Contact> contactList = new ArrayList<Contact>();
    static ArrayList<ContactItem> contactItems = new ArrayList<ContactItem>();
    static ArrayList<GalleryImage> img = new ArrayList<>();
    static ArrayList<GridViewItemKaist> kaist = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context_main = this;

        for(int i=0;i<6;i++){
            Bitmap tmpb = BitmapFactory.decodeResource(getResources(), forsized[i]);
            int w = tmpb.getWidth();
            int h = tmpb.getHeight();
            Bitmap sd = Bitmap.createScaledBitmap( tmpb, w/15, h/15, true );
            sized[i] = sd;
        }

        for(int i=0; i<dep_icon.length;i++) {

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), dep_icon[i]);
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            Bitmap resize = Bitmap.createScaledBitmap(bitmap, width / 10, height / 10, true);

            GridViewItemKaist gk = new GridViewItemKaist();
            gk.setIcon(new BitmapDrawable(getResources(), resize));
            gk.setStr(dep_name[i]);
            kaist.add(gk);
        }

        //getContactList();
        //getGallery();

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        tabLayout.setSelectedTabIndicatorHeight(0);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(viewPagerAdapter);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                TextView tabTextView = new TextView(this);
                tab.setCustomView(tabTextView);
                tabTextView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                tabTextView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                tabTextView.setText(tab.getText());
                tabTextView.setTextSize((float) 12.5);
                // First tab is the selected tab, so if i==0 then set BOLD typeface
                if (i == 0) {
                    tabTextView.setTypeface(null, Typeface.BOLD);
                    tabTextView.setTextColor(Color.parseColor("#655B3E"));
                }
            }
        }

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                TextView text = (TextView) tab.getCustomView();
                text.setTypeface(null, Typeface.BOLD);
                text.setTextColor(Color.parseColor("#655B3E"));
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView text = (TextView) tab.getCustomView();
                text.setTypeface(null, Typeface.NORMAL);
                text.setTextColor(Color.parseColor("#9C9788"));
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void getGallery() throws IOException {

        Uri uri;
        Cursor cursor;
        int column_index_data;
        String absolutePathOfImage = null;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        cursor = getContentResolver().query(uri, projection, null,null, MediaStore.Images.Media.DATE_TAKEN + " DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        int check = 0;
        while (cursor.moveToNext() && check <=30) {
            absolutePathOfImage = cursor.getString(column_index_data);
            File files = new File(absolutePathOfImage);
            //Bitmap myBitmap = null;
            GalleryImage gi = new GalleryImage();
/*
            myBitmap = BitmapFactory.decodeFile(files.getAbsolutePath());
            Drawable d = new BitmapDrawable(getResources(), myBitmap);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            int w = myBitmap.getWidth();
            int h = myBitmap.getHeight();
            Bitmap resized = Bitmap.createScaledBitmap( myBitmap, w/5, h/5, true );
            Drawable rd = new BitmapDrawable(getResources(), resized);
            gi.setRd(rd);
            gi.setD(d);*/
            Bitmap bitmap = null;
            if (Build.VERSION.SDK_INT >= 29){
                ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(),Uri.fromFile(files));
                bitmap = ImageDecoder.decodeBitmap(source);
            }
            else{
                bitmap = BitmapFactory.decodeFile(absolutePathOfImage);
            }
            if(bitmap != null) {
                int w = bitmap.getWidth();
                int h = bitmap.getHeight();
                Bitmap resized = Bitmap.createScaledBitmap( bitmap, w/5, h/5, true );
                gi.setD(bitmap);
                gi.setRd(resized);
                gi.setPath(absolutePathOfImage);
                img.add(gi);
            }

            check++;
        }
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