package com.iliptam.android.myapplicationads;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.iliptam.android.owenads.NativeAdListener;
import com.iliptam.android.owenads.OwnAds;
import com.iliptam.android.owenads.OwnAdsNativeView;

public class MainActivity extends AppCompatActivity {

    OwnAds ownAds;
    LinearLayout linearLayout;
    Button button;

    public ImageView iv_ads_logo, iv_ads_photo;
    public TextView tv_ads_title, price, tv_ads_desc;
    public RatingBar stars;
    public Button btn_install;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = findViewById(R.id.linear);
        button = findViewById(R.id.button2);
        ownAds = new OwnAds(this,
                "https://firebasestorage.googleapis.com/v0/b/app-test-9565f.appspot.com/o/Ads.json?alt=media&token=a146f91b-7217-42bd-81e1-ced82c1fcb9b");
//        ownAds = new OwnAds(MainActivity.this, "https://firebasestorage.googleapis.com/v0/b/app-test-9565f.appspot.com/o/Ads.json?alt=media&token=28c23d25-c57a-4abc-b5db-a98bad5fd258"
//        , linearLayout);
        ownAds.loadBannerAd(linearLayout);
        ownAds.autoChangeBannerAds(5);
        ownAds.setFeedbackEmail("dilipcharaniya237@gmail.com");
        ownAds.loadInterstitial();

        iv_ads_logo = (ImageView) findViewById(R.id.houseAds_app_icon);
        iv_ads_photo = (ImageView) findViewById(R.id.houseAds_header_image);
        tv_ads_title = (TextView) findViewById(R.id.houseAds_title);
        price = (TextView) findViewById(R.id.houseAds_price);
        tv_ads_desc = (TextView) findViewById(R.id.houseAds_description);
        stars = (RatingBar) findViewById(R.id.houseAds_rating);
        btn_install = (Button) findViewById(R.id.houseAds_cta);

        ownAds.setNativeAdView(new OwnAdsNativeView(tv_ads_title, tv_ads_desc, price,
                iv_ads_logo, iv_ads_photo, btn_install, stars));

        ownAds.loadNative();
        ownAds.autoChangeNativeAds(5);
        ownAds.setNativeAdListener(new NativeAdListener() {
            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onAdLoadFailed(Exception var1) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ownAds.isAdLoaded()) {
                    ownAds.showInterstitial();
                } else {
                    ownAds.loadInterstitial();
                    ownAds.loadNative();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!ownAds.isAdLoaded()) {
            ownAds.loadInterstitial();
        }
    }

    @Override
    public void onBackPressed() {
        ownAds.showRateDialog();
    }
}