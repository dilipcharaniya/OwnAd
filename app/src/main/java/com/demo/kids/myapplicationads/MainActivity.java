package com.demo.kids.myapplicationads;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.demo.kids.owenads.OwnAds;

public class MainActivity extends AppCompatActivity {

    OwnAds ownAds;
    LinearLayout linearLayout;
    Button button;

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


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ownAds.isAdLoaded()) {
                    ownAds.showInterstitial();
                }else {
                    ownAds.loadInterstitial();
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