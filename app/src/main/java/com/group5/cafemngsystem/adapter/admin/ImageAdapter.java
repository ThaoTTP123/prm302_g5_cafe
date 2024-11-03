package com.group5.cafemngsystem.adapter.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private int[] mImageResources;
    private ImageSelectionListener mListener;

    public interface ImageSelectionListener {
        void onImageSelected(int resourceId);
    }

    public ImageAdapter(Context context, int[] imageResources, ImageSelectionListener listener) {
        mContext = context;
        mImageResources = imageResources;
        mListener = listener;
    }

    @Override
    public int getCount() {
        return mImageResources.length;
    }

    @Override
    public Object getItem(int position) {
        return mImageResources[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(200, 200)); // Adjust size as needed
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mImageResources[position]);

        // Set click listener for the image
        imageView.setOnClickListener(v -> {
            // Notify the listener of the selected image
            mListener.onImageSelected(mImageResources[position]);
        });

        return imageView;
    }
}
