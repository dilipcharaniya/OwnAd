package com.iliptam.android.owenads;

public interface JsonObjectGetListener {
    void onSuccess(MyAd[] myAds);

    void onError(String error);
}
