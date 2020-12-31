package com.example.helloworld;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.helloworld.MainActivity.department;
import static com.example.helloworld.MainActivity.des;
import static com.example.helloworld.MainActivity.name;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Page3Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Page3Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Page3Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment3.
     */
    // TODO: Rename and change types and number of parameters
    public static Page3Fragment newInstance(String param1, String param2) {
        Page3Fragment fragment = new Page3Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page3, null) ;
        GridViewAdapter adapter = new GridViewAdapter();
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, LIST_MENU)
        GridView gridview = (GridView) view.findViewById(R.id.gridView_third_tab);
        gridview.setAdapter(adapter);

        adapter = add_item_to_gridviewadapter(adapter);

        GridViewAdapter finalAdapter = adapter;
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                GridViewItem dto = (GridViewItem) finalAdapter.getItem(position);
                Toast.makeText(getActivity(), dto.getDescrip(), Toast.LENGTH_SHORT).show();
            }
        });

        return view ;
    }

    public class GridViewAdapter extends BaseAdapter {

        private ArrayList<GridViewItem> gridViewItemList = new ArrayList<GridViewItem>();
        public GridViewAdapter() {
        }

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
            GridViewItem gridViewItem = gridViewItemList.get(position);

            // 아이템 내 각 위젯에 데이터 반영
            iconImageView.setImageDrawable(gridViewItem.getIcon());
            strTextView.setText(gridViewItem.getStr());

            ImageView imageView = new ImageView(context);
            imageView.setImageResource(department[position]);

            return convertView;
        }

        @Override
        public int getCount() { return gridViewItemList.size(); }

        @Override
        public Object getItem(int position) { return gridViewItemList.get(position); }

        @Override
        public long getItemId(int position) {
            return position;
        }

        // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
        public void addItem(Drawable icon, String str, String descrip) {
            GridViewItem item = new GridViewItem();

            item.setIcon(icon);
            item.setStr(str);
            item.setDescrip(descrip);

            gridViewItemList.add(item);
        }
    }
    public GridViewAdapter add_item_to_gridviewadapter(GridViewAdapter myadapter){

        for (int i=0; i<department.length; i++) {
            myadapter.addItem(ContextCompat.getDrawable(getActivity(), department[i]), name[i], des[i]);
        }
        return myadapter;
    }
}