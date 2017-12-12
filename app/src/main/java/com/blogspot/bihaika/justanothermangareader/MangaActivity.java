package com.blogspot.bihaika.justanothermangareader;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class MangaActivity extends Activity {

    GridView mGridView;
    MangaGridAdapter mGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga);

        mGridView = findViewById(R.id.gridv_mangaactivity_mangalist);

        mGridAdapter = new MangaGridAdapter(this, R.layout.griditem_all, new ArrayList());

        mGridView.setAdapter(mGridAdapter);

        DataManager.getInstance(this).parseBatotoMain("http://bato.to", this);
    }

    public void addAdapterItem(ArrayList<MangaItem> newList, boolean inFront) {
        for (int i = newList.size() - 1; i >= 0; i--) {
            if (newList.get(i).equals(mGridAdapter.getItem(0))) {
                newList.remove(i);
            }
        }
        if (newList.isEmpty()) {
            mGridAdapter.addAll(newList, inFront);
        }
        mGridView.invalidate();
    }

    public void errorLoadItem(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
