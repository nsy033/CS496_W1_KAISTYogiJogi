package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

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

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment3 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment3() {
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
    public static fragment3 newInstance(String param1, String param2) {
        fragment3 fragment = new fragment3();
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
        View view = inflater.inflate(R.layout.fragment_fragment3, null) ;
        GridViewAdapter adapter = new GridViewAdapter();
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, LIST_MENU)
        GridView gridview = (GridView) view.findViewById(R.id.gridView_third_tab);
        gridview.setAdapter(adapter);

        adapter = add_item_to_gridviewadapter(adapter);

        /**
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                Contact con = contactList.get(position);
                dlg.setTitle("Name : " + con.getName() );
                dlg.setMessage("PhoneNumber : " +
                        con.getPhonenumber() +
                        "\nAddress : " +
                        con.getAddress());
                dlg.setNegativeButton("Close", null);
                dlg.show();
            }
        });
        */
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
        public void addItem(Drawable icon, String str) {
            GridViewItem item = new GridViewItem();

            item.setIcon(icon);
            item.setStr(str);

            gridViewItemList.add(item);
        }
    }
    public GridViewAdapter add_item_to_gridviewadapter(GridViewAdapter myadapter){


            myadapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.civil), "civil"); //건설환경공학과
            myadapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.business), "business"); //기술경영학과
            myadapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.mechanic), "mechanic"); //기계공학과
            myadapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.physics), "physics"); //물리학과
            myadapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.brain), "brain"); //바이오 및 뇌공학과
            myadapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.system), "system"); //산업 및 시스템공학과
            myadapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.dna), "dna"); //생명과학과
            myadapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.biochem), "biochem"); //생명화학공학과
            myadapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.design), "design"); //산업디자인학과
            myadapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.math), "math"); //수리과학과
            myadapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.material), "material"); //신소재공학과
            myadapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.nuclear), "nuclear"); //원자력 및 양자공학과
            myadapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.electrical), "electrical"); //전기 및 전자공학과
            myadapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.computer), "computer"); //전산학과
            myadapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.chemistry), "chemistry"); //화학과
            myadapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.aerospace), "aerospace"); //항공우주공학과


        return myadapter;
    }
}