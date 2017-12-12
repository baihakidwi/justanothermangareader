package com.blogspot.bihaika.justanothermangareader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Baihaki Dwi on 11/12/2017.
 */

public class MangaGridAdapter extends ArrayAdapter {
    ArrayList<MangaItem> mList;

    public MangaGridAdapter(@android.support.annotation.NonNull Context context, int resource, @android.support.annotation.NonNull List objects) {
        super(context, resource, objects);
        mList = new ArrayList(objects);
    }

    public void addAll(@NonNull ArrayList<MangaItem> list, boolean inFront) {
        if (inFront) {
            mList.addAll(0, list);
        } else {
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Nullable
    @Override
    public MangaItem getItem(int position) {
        return mList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.griditem_all, parent, false);

        ImageView imgThumbnail = convertView.findViewById(R.id.imgv_griditem_mangathumbnails);
        TextView txvMain = convertView.findViewById(R.id.txv_griditem_main);
        TextView txvDetail = convertView.findViewById(R.id.txv_griditem_detail);

        MangaItem manga = getItem(position);

//        Glide.with(getContext()).asBitmap().load(manga.getThumbnailUrl()).into(imgThumbnail);

        txvMain.setText(manga.getMain());
        txvDetail.setText(manga.getDetail());

        return convertView;
    }
}
