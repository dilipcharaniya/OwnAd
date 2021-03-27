package com.demo.kids.owenads;

public interface JsonObjectGetListener {
    void onSuccess(MyAd[] myAds);

    void onError(String error);
}
