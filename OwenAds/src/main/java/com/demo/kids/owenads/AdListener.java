package com.demo.kids.owenads;

public interface AdListener {
    void onAdLoadFailed(Exception var1);

    void onAdLoaded();

    void onAdClosed();

    void onAdShown();

    void onApplicationLeft();
}
