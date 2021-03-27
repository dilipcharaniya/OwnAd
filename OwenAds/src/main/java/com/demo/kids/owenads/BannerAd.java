package com.demo.kids.owenads;

public class BannerAd {
    private String iconUrl;
    private String appTitle;
    private String appDesc;
    private String packageNameOrUrl;
    private String ctaText;
    private String rating;

    public BannerAd(String iconUrl, String appTitle, String appDesc, String packageNameOrUrl, String ctaText, String rating) {
        this.iconUrl = iconUrl;
        this.appTitle = appTitle;
        this.appDesc = appDesc;
        this.packageNameOrUrl = packageNameOrUrl;
        this.ctaText = ctaText;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
