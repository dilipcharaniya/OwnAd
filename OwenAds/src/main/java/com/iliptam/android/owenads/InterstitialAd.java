package com.iliptam.android.owenads;

public class InterstitialAd {

    private String iconUrl;
    private String appTitle;
    private String appDesc;
    private String largeImageUrl;
    private String packageNameOrUrl;
    private String ctaText;
    private String price;
    private String rating;

    public InterstitialAd(String iconUrl, String appTitle, String appDesc, String largeImageUrl, String packageNameOrUrl, String ctaText, String price, String rating) {
        this.iconUrl = iconUrl;
        this.appTitle = appTitle;
        this.appDesc = appDesc;
        this.largeImageUrl = largeImageUrl;
        this.packageNameOrUrl = packageNameOrUrl;
        this.ctaText = ctaText;
        this.price = price;
        this.rating = rating;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getAppTitle() {
        return appTitle;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }

    public String getLargeImageUrl() {
        return largeImageUrl;
    }

    public void setLargeImageUrl(String largeImageUrl) {
        this.largeImageUrl = largeImageUrl;
    }

    public String getPackageNameOrUrl() {
        return packageNameOrUrl;
    }

    public void setPackageNameOrUrl(String packageNameOrUrl) {
        this.packageNameOrUrl = packageNameOrUrl;
    }

    public String getCtaText() {
        return ctaText;
    }

    public void setCtaText(String ctaText) {
        this.ctaText = ctaText;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
