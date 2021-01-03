package com.example.helloworld;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

import static com.example.helloworld.MainActivity.img;

public class TestPagerAdapter extends PagerAdapter {
    Context context;
    List<PagerModel> pagerArr;
    LayoutInflater inflater;

    public TestPagerAdapter(Context context, List<PagerModel> pagerArr) {
        this.context = context;
        this.pagerArr = pagerArr;

        inflater = ((Activity) context).getLayoutInflater();
    }

    @Override
    public int getCount(){
        return pagerArr.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        View view = inflater.inflate(R.layout.pager_list_item, container, false);
        TextView tv = (TextView) view.findViewById(R.id.textview);
        ImageView iv = (ImageView) view.findViewById(R.id.imageview);
        //view.setTag(position);

        ((ViewPager) container).addView(view);
        PagerModel model = pagerArr.get(position);
        //iv.setImageResource(R.drawable.iconuser);
        iv.setImageResource(img[position]);
        tv.setText(""+(position+1) + " / " + getCount());
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((View) object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        ((ViewPager) container).removeView((View)object);
    }

}
