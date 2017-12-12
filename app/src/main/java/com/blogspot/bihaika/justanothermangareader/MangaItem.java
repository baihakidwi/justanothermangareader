package com.blogspot.bihaika.justanothermangareader;

/**
 * Created by Baihaki Dwi on 11/12/2017.
 */

public class MangaItem {

    String mThumbnailUrl;
    String mMain;
    String mDetail;
    String mLink;

    public MangaItem() {
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

    public MangaItem setThumbnailUrl(String thumbnailUrl) {
        mThumbnailUrl = thumbnailUrl;
        return this;
    }

    public String getMain() {
        return mMain;
    }

    public MangaItem setMain(String main) {
        mMain = main;
        return this;
    }

    public String getDetail() {
        return mDetail;
    }

    public MangaItem setDetail(String detail) {
        mDetail = detail;
        return this;
    }

    public String getLink() {
        return mLink;
    }

    public MangaItem setLink(String link) {
        this.mLink = link;
        return this;
    }

    public boolean equals(MangaItem item) {
        return mThumbnailUrl == item.getThumbnailUrl()
                && mMain == item.getMain()
                && mDetail == item.getDetail()
                && mLink == item.getLink();
    }
}
