package com.dreamernguyen.AppRaoVatSaFaCo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.dreamernguyen.AppRaoVatSaFaCo.Activity.AnhChiTietActivity;

import java.util.ArrayList;
import java.util.List;

public class AnhBaiVietAdapter extends PagerAdapter {
    List<String> mlistanh;
    Boolean fullImage;

    public AnhBaiVietAdapter(List<String> mlistanh, Boolean fullImage) {
        this.fullImage = fullImage;
        this.mlistanh = mlistanh;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        final Context context = container.getContext();
        final ImageView imageView = new ImageView(context);

        container.addView(imageView);
        // Load ảnh vào ImageView bằng Glide
        if (fullImage) {
            Glide.with(context).load(mlistanh.get(position)).into(imageView);

        } else {
            Glide.with(context).load(mlistanh.get(position)).centerCrop().into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("TAG", "instantiateItem: " + mlistanh.get(position));
                            Intent i = new Intent(context, AnhChiTietActivity.class);
                            i.putExtra("pos", position);
                            i.putStringArrayListExtra("listAnh", (ArrayList<String>) mlistanh);
                            context.startActivity(i);
                        }
                    });
                }
            });
        }
        return imageView;
    }

    @Override
    public int getCount() {
        return mlistanh.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        // container chính là ViewPager, còn Object chính là return của instantiateItem ứng với position
        container.removeView((View) object);
    }
}
