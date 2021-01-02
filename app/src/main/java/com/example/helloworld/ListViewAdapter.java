package com.example.helloworld;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.helloworld.MainActivity.contactItems;
//import static com.example.helloworld.MainActivity.contactList;

public class ListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    public static ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;

    // ListViewAdapter의 생성자
    public ListViewAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1) ;
        TextView titleTextView = (TextView) convertView.findViewById(R.id.textView1) ;
        TextView descTextView = (TextView) convertView.findViewById(R.id.textView2) ;
        ImageButton btn = (ImageButton) convertView.findViewById(R.id.button);
        btn.setScaleType(ImageButton.ScaleType.FIT_CENTER);

        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                ContactItem con = contactItems.get(position);
                Intent tt = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+con.getUser_phNumber()));
                context.startActivity(tt);
            }
        });
        btn.setOnLongClickListener(new View.OnLongClickListener(){
            public boolean onLongClick(View view) {
                ContactItem con = contactItems.get(position);
                Intent tt = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+con.getUser_phNumber()));
                context.startActivity(tt);
                return true;
            }
        });

        convertView.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContactItem con = contactItems.get(position);

                AlertDialog.Builder adb = new AlertDialog.Builder(context);
                //int id = (int) parent.getItemIdAtPosition(position);
                adb.setTitle("Name: " + con.getUser_name());
                adb.setMessage("PhoneNumber: "
                        + con.getUser_phNumber()
                        + "\nEmail: "
                        + con.getMail()
                        + "\nAddress: "
                        + con.getAddress()
                );
                adb.setPositiveButton("Ok", null);
                adb.show();

            }
        });

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        iconImageView.setImageDrawable(listViewItem.getIcon());
        titleTextView.setText(listViewItem.getTitle());
        descTextView.setText(listViewItem.getDesc());

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(Drawable icon, String title, String desc) {
        ListViewItem item = new ListViewItem();

        item.setIcon(icon);
        item.setTitle(title);
        item.setDesc(desc);

        listViewItemList.add(item);
    }

    public void clearItem() {
        listViewItemList = new ArrayList<ListViewItem>();
    }
}