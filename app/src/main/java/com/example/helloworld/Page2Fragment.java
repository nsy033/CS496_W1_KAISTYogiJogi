package com.example.helloworld;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import static com.example.helloworld.MainActivity.img;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Page2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Page2Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Page2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Page2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Page2Fragment newInstance(String param1, String param2) {
        Page2Fragment fragment = new Page2Fragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.page2, null);

        MyGridAdapter adapter = new MyGridAdapter(
                getActivity().getApplicationContext(),
                R.layout.dialog,       // GridView 항목의 레이아웃 row.xml
                img);    // 데이터

        GridView gv = (GridView) view.findViewById(R.id.gridView1);
        gv.setAdapter(adapter);  // 커스텀 아답타를 GridView 에 적용

        return view;
    }

    public class MyGridAdapter extends BaseAdapter {
        Context context;
        int layout;
        int img[];
        LayoutInflater inf;

        public MyGridAdapter(Context context, int layout, int[] img) {
            this.context = context;
            this.layout = layout;
            this.img = img;
            inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return img.length;
        }

        public Object getItem(int position) {
            return img[position];
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(5, 5, 5, 5);
            imageView.setImageResource(img[position]);
            final int pos = position;

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    View dialogView = (View) View.inflate(getActivity(), R.layout.dialog, null);
                    AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                    ImageView ivPoster = (ImageView) dialogView.findViewById(R.id.ivPoster);
                    ivPoster.setImageResource(img[pos]);
                    //dlg.setTitle("Title");
                    dlg.setIcon(R.drawable.ic_launcher_foreground);
                    dlg.setView(dialogView);
                    dlg.setNegativeButton("CLOSE", null);
                    dlg.show();
                }
            });
            return imageView;
        }
    }
}