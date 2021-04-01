package com.iliptam.android.owenads;

import android.view.View;

public interface NativeAdListener {
    void onAdLoaded();

    void onAdLoadFailed(Exception var1);

    public interface CallToActionListener {
        void onCallToActionClicked(View var1);
    }
}