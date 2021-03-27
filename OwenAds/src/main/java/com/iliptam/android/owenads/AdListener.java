package com.iliptam.android.owenads;

public interface AdListener {
    void onAdLoadFailed(Exception var1);

    void onAdLoaded();

    void onAdClosed();

    void onAdShown();

    void onApplicationLeft();
}
