package com.iliptam.android.owenads;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class OwnAdsNativeView {
    private TextView title;
    private TextView description;
    private TextView price;
    private ImageView icon;
    private ImageView headerImage;
    private Button cta;
    private RatingBar ratings;

    public OwnAdsNativeView(TextView title, TextView description, TextView price, ImageView icon, ImageView headerImage, Button cta, RatingBar ratings) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.icon = icon;
        this.headerImage = headerImage;
        this.cta = cta;
        this.ratings = ratings;
    }

    public OwnAdsNativeView() {
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public TextView getDescription() {
        return description;
    }

    public void setDescription(TextView description) {
        this.description = description;
    }

    public TextView getPrice() {
        return price;
    }

    public void setPrice(TextView price) {
        this.price = price;
    }

    public ImageView getIcon() {
        return icon;
    }

    public void setIcon(ImageView icon) {
        this.icon = icon;
    }

    public ImageView getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(ImageView headerImage) {
        this.headerImage = headerImage;
    }

    public Button getCta() {
        return cta;
    }

    public void setCta(Button cta) {
        this.cta = cta;
    }

    public RatingBar getRatings() {
        return ratings;
    }

    public void setRatings(RatingBar ratings) {
        this.ratings = ratings;
    }
}
