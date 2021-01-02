package com.example.helloworld;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

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

            Display display = getActivity().getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getRealSize(size); // or getSize(size)
            int width = size.x;
            int height = size.y;

            width /= 3;
            width -= 10;

            imageView.setLayoutParams(new GridView.LayoutParams(width, width));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(5, 5, 5, 5);
/*
            Bitmap bmp =  BitmapFactory.decodeResource(context.getResources(), img[position]);
            bmp = Bitmap.createScaledBitmap(bmp,10,10,false);
            imageView.setImageBitmap(bmp);
*/
            imageView.setImageResource(img[position]);
            final int pos = position;

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //View dialogView = (View) View.inflate(getActivity(), R.layout.dialog, null);
                    //AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                    //ImageView ivPoster = (ImageView) dialogView.findViewById(R.id.ivPoster);
                    //ivPoster.setImageResource(img[pos]);
                    //dlg.setTitle("Title");
                    //dlg.setIcon(R.drawable.ic_launcher_foreground);
                    //dlg.setView(dialogView);
                    //dlg.setPositiveButton("CLOSE", null);
                    //dlg.show();
                    showDialog(pos, view);
                }
            });

            return imageView;
        }

    }

    public void showDialog(int position, View v){
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pager_layout);

        List<PagerModel> pagerArr = new ArrayList<>();

        for(int i=0;i<img.length; i++){
            pagerArr.add(new PagerModel(""+(i+1), "Pager Item #" + i, img[i]));
        }

        TestPagerAdapter adapter = new TestPagerAdapter(getContext(), pagerArr);
        ViewPager pager = (ViewPager) dialog.findViewById(R.id.pager);
        pager.setAdapter(adapter);
        pager.setPageTransformer(true, new ZoomOutPageTransformer());
        pager.setCurrentItem(position);

        dialog.show();

    }

    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }
}