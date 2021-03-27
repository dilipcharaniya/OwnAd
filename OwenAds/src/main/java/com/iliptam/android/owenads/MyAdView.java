package com.iliptam.android.owenads;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MyAdView extends RelativeLayout {

    Context context;
    ImageView imageView;
    TextView textViewName, textViewDescription;
    BannerAd myAd;
    RatingBar ratingBar;
    Button button;

    public MyAdView(final Context context, final BannerAd myAd) {
        super(context);
        this.context = context;
        this.myAd = myAd;

        inflate(context, R.layout.rv_layout, this);
        imageView = findViewById(R.id.app_image);
        textViewName = findViewById(R.id.app_name);
        textViewDescription = findViewById(R.id.app_desc);
        ratingBar = findViewById(R.id.stars);
        button = findViewById(R.id.button);

        this.setBackgroundColor(Color.parseColor("#F5F5F5"));

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(myAd.getPackageNameOrUrl()));
                context.startActivity(browse);
            }
        });

        setValues();
    }

    public void setValues() {
        Picasso.get().load(myAd.getIconUrl()).placeholder(R.drawable.ic_android_black_24dp).into(imageView);
        textViewName.setText("" + myAd.getAppTitle());
        textViewDescription.setText("" + myAd.getAppDesc());
        ratingBar.setRating(Float.parseFloat(myAd.getRating()));
    }
}
