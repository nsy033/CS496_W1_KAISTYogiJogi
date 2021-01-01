package com.example.myapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChildfragmentAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    public ArrayList<ChildFragmentItem> childFragmentItems = new ArrayList<ChildFragmentItem>();

    // ListViewAdapter의 생성자
    public ChildfragmentAdapter() {
    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return childFragmentItems.size();
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.childfragmentlist, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView mapImageView = (ImageView) convertView.findViewById(R.id.imageView_map);
        TextView buildingNumTv = (TextView) convertView.findViewById(R.id.building_num);
        TextView kingPhoneNumberTv = (TextView) convertView.findViewById(R.id.king_phone_number);
        TextView kingEmailTv = (TextView) convertView.findViewById(R.id.king_email);
        TextView adminPhoneNumberTv = (TextView) convertView.findViewById(R.id.admin_phone_number);
        TextView adminEmailTv = (TextView) convertView.findViewById(R.id.admin_email);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ChildFragmentItem childFragmentItem = childFragmentItems.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        mapImageView.setImageDrawable(childFragmentItem.getDep_map());
        buildingNumTv.setText(childFragmentItem.getDep_num());
        kingPhoneNumberTv.setText(childFragmentItem.getDep_king_phone());
        kingEmailTv.setText(childFragmentItem.getDep_king_email());
        adminPhoneNumberTv.setText(childFragmentItem.getDep_admin_phone());
        adminEmailTv.setText(childFragmentItem.getDep_admin_email());

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return childFragmentItems.get(position);
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(Drawable dep_map, String dep_num, String dep_king_phone,String dep_king_email ,String dep_admin_phone,String dep_admin_email) {
        ChildFragmentItem item = new ChildFragmentItem();

        item.setDep_map(dep_map);
        item.setDep_num(dep_num);
        item.setDep_king_phone(dep_king_phone);
        item.setDep_king_email(dep_king_email);
        item.setDep_admin_phone(dep_admin_phone);
        item.setDep_admin_email(dep_admin_email);

        childFragmentItems.add(item);
    }
}
