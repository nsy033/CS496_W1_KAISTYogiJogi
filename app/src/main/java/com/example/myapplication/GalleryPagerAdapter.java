package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

public class GalleryPagerAdapter extends PagerAdapter {
    Context context;
    public List<PagerModel> pagerArr;
    LayoutInflater inflater;

    public GalleryPagerAdapter(Context context, List<PagerModel> pagerArr) {
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
        //TextView tv = (TextView) view.findViewById(R.id.textview);
        ImageView iv = (ImageView) view.findViewById(R.id.imageview_forgallerypager);
        view.setTag(position);

        ((ViewPager) container).addView(view);

        PagerModel model = pagerArr.get(position);
        iv.setImageResource(model.resId);
        //tv.setText(model.getTitle());
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
