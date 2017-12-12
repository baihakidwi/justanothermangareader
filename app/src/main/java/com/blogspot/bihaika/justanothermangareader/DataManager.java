package com.blogspot.bihaika.justanothermangareader;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by Baihaki Dwi on 11/12/2017.
 */

public class DataManager {

    private static DataManager mDataManager;

    private Context mContext;
    private RequestQueue mRequestQueue;

    private DataManager(Context context) {
        mContext = context.getApplicationContext();
        mRequestQueue = Volley.newRequestQueue(mContext);
    }

    public static DataManager getInstance(Context context) {
        if (mDataManager == null) {
            mDataManager = new DataManager(context);
        }
        return mDataManager;
    }

    public void parseBatotoMain(String url, final MangaActivity mangaActivity) {
        StringRequest request = new StringRequest(StringRequest.Method.GET
                , url
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(mangaActivity, response.substring(0, 20), Toast.LENGTH_SHORT).show();
                mangaActivity.addAdapterItem(parseBatotoMainResponse(response), true);
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mangaActivity.errorLoadItem(error.getLocalizedMessage());
            }
        });
        mRequestQueue.add(request);
    }

    private ArrayList<MangaItem> parseBatotoMainResponse(String response) {
        ArrayList<MangaItem> mangaList = new ArrayList<>();
        try {
            XmlPullParser xmlParser = XmlPullParserFactory.newInstance().newPullParser();
            xmlParser.setInput(new StringReader(response));

            int eventType = xmlParser.next();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG
                        && xmlParser.getName().equals("table")
                        && xmlParser.getAttributeValue(null, "class").equals("ipb_table chapters_list")) {

                    while (eventType != XmlPullParser.START_TAG
                            || !xmlParser.getName().equals("tr")
                            || !xmlParser.getAttributeValue(null, "class").startsWith("row")) {
                        eventType = xmlParser.next();
                    }
                    int prefix = 0;
                    if (!xmlParser.getAttributeValue(null, "class").startsWith("row" + prefix)) {
                        prefix = 1;
                    }

                    while (eventType != XmlPullParser.END_TAG
                            && xmlParser.getName().equals("table")) {
                        if (xmlParser.getName().equals("tr")) {
                            if (xmlParser.getAttributeValue(null, "class").startsWith("row" + prefix)) {
                                MangaItem mangaItem = new MangaItem();
                                while (eventType != XmlPullParser.START_TAG
                                        || !xmlParser.getName().equals("a")) {
                                    eventType = xmlParser.next();
                                }
                                mangaItem.setLink(xmlParser.getAttributeValue(null, "href"));
                                xmlParser.next();
                                mangaItem.setThumbnailUrl(xmlParser.getAttributeValue(null, "src"));
                                mangaItem.setMain(xmlParser.getAttributeValue(null, "alt"));
                                mangaList.add(mangaItem);
                                prefix = (prefix + 1) % 2;
                            }
                        }
                        eventType = xmlParser.next();
                    }

                }
                eventType = xmlParser.next();
            }

        } catch (XmlPullParserException ex) {
            Log.e("error", ex.getLocalizedMessage());
        } catch (IOException ex) {
            Log.e("error", ex.getLocalizedMessage());
        }
        return mangaList;
    }
}
