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

public class GridViewAdapterForKaist extends BaseAdapter {

    public ArrayList<GridViewItemKaist> gridViewItemKaistList = new ArrayList<GridViewItemKaist>();
    public GridViewAdapterForKaist(){}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.gridview_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.image_gridview);
        TextView strTextView = (TextView) convertView.findViewById(R.id.text_gridview);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        GridViewItemKaist gridViewItemKaist = gridViewItemKaistList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        iconImageView.setImageDrawable(gridViewItemKaist.getIcon());
        strTextView.setText(gridViewItemKaist.getStr());

        return convertView;
    }

    @Override
    public int getCount() { return gridViewItemKaistList.size(); }

    @Override
    public Object getItem(int position) { return gridViewItemKaistList.get(position); }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(Drawable icon, String str) {
        GridViewItemKaist item = new GridViewItemKaist();

        item.setIcon(icon);
        item.setStr(str);

        gridViewItemKaistList.add(item);
    }
}
