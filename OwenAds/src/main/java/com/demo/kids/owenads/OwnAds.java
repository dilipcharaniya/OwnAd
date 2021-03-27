package com.demo.kids.owenads;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.AnimRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class OwnAds {

    // Timer
    public static Handler handler = new Handler();
    private static Runnable mRunnable;
    private static AdListener mAdListener;
    private String TAG = this.getClass().getSimpleName();
    //
    private String countSP = "countSP";

    private Context context;

    private LinearLayout linearLayout;
    private int bannerCount = 0;
    private MyAdView currentAdView;

    private int lastLoaded = 0;
    private static boolean isAdLoaded = false;
    public static Bitmap bitmap_icon, bitmap_photo;
    public static String urlClicked = "";
    public static String adTitle = "";
    public static String adDesc = "";
    public static String adPrice = "";
    public static String adButtonText = "";
    public static Float adRating;
    private ArrayList<BannerAd> bannerAds = new ArrayList<>();
    private ArrayList<InterstitialAd> interstitialAds = new ArrayList<>();

    private String feedbackEmail = "";
    private String sharePreString = "Check out this Amazing Android App: ";
    private int rate = 0;
    boolean shuffleDialogAds = false;
    boolean menInBlack = false;

    public OwnAds(final Context context, String url) {
        this.context = context;
        FayazSP.init(context);

        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                try {
                    JSONObject roote = new JSONObject(string);
                    JSONArray jsonArray = roote.optJSONArray("apps");
                    JSONArray jsonArray2 = roote.optJSONArray("apps");
                    bannerAds = new ArrayList<>();
                    interstitialAds = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
//                bannerAds = new BannerAd(
//                        object.getString("app_icon"),
//                        object.getString("app_title"),
//                        object.getString("app_desc"),
//                        object.getString("app_uri"),
//                        object.getString("app_uri"),
//                        object.getString("app_rating"
                        Log.e("BANNNNN", "" + object);
                        if (object.optString("app_adType").equals("banner")) {
                            bannerAds.add(new BannerAd(object.getString("app_icon"),
                                    object.getString("app_title"),
                                    object.getString("app_desc"),
                                    object.getString("app_uri"),
                                    object.getString("app_cta_text"),
                                    object.getString("app_rating")));
                        }
                    }

                    for (int i = 0; i < jsonArray2.length(); i++) {
                        JSONObject object = jsonArray2.getJSONObject(i);
//                bannerAds = new BannerAd(
//                        object.getString("app_icon"),
//                        object.getString("app_title"),
//                        object.getString("app_desc"),
//                        object.getString("app_uri"),
//                        object.getString("app_uri"),
//                        object.getString("app_rating"
                        Log.e("BANNNNN", "" + object);
                        if (object.optString("app_adType").equals("interstitial")) {
                            interstitialAds.add(new InterstitialAd(object.getString("app_icon"),
                                    object.getString("app_title"),
                                    object.getString("app_desc"),
                                    object.getString("app_header_image"),
                                    object.getString("app_uri"),
                                    object.getString("app_cta_text"),
                                    object.getString("app_price"),
                                    object.getString("app_rating")));
                        }
                    }
                    removeSameAppAds();
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context, "Some error occurred!!", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(context);
        rQueue.add(request);
    }

 /*   public OwnAds(final Context context, String url, final LinearLayout linearLayout) {
        this.context = context;
        this.linearLayout = linearLayout;
        FayazSP.init(context);


        removeSameAppAds();
        putBannerAds();
    }*/

    public void doSomethingAfter(double seconds, Runnable runnable) {
        handler.removeCallbacks(mRunnable);
        mRunnable = runnable;
        handler.postDelayed(runnable, (long) (seconds * 1000));
    }

    public void loadBannerAd(final LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
        bannerCount = FayazSP.getInt(countSP, 0);
        try {
            currentAdView = new MyAdView(context, bannerAds.get(bannerCount));
            if (linearLayout != null)
                linearLayout.addView(currentAdView);

            incrementAndSaveCounter();
        } catch (Exception e) {
            //
        }
    }

    private void incrementAndSaveCounter() {
        bannerCount++;
        if (bannerCount == bannerAds.size()) {
            bannerCount = 0;
        }

        FayazSP.put(countSP, bannerCount);
    }

    public void autoChangeBannerAds(final int intervalSeconds) {
        doSomethingAfter(intervalSeconds, new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: Changing Banner: " + bannerCount);
                if (linearLayout != null) {
                    linearLayout.removeView(currentAdView);
                    loadBannerAd(linearLayout);
                    autoChangeBannerAds(intervalSeconds);
                }
            }
        });
    }

    private void removeSameAppAds() {
        for (int i = 0; i < bannerAds.size(); i++) {
            if (bannerAds.get(i).getPackageNameOrUrl().contains(context.getPackageName())) {
                bannerAds.remove(i);
                break;
            }
        }
    }

    public void setAdListener(AdListener adListener) {
        mAdListener = adListener;
    }

    public void loadInterstitial() {
        Log.e("IHIHI", "" + interstitialAds.size());
        if (interstitialAds.size() > 0) {
            InterstitialAd modal = (InterstitialAd) interstitialAds.get(this.lastLoaded);
            if (this.lastLoaded == interstitialAds.size() - 1) {
                this.lastLoaded = 0;
            } else {
                ++this.lastLoaded;
            }

            Picasso.get().load(modal.getIconUrl()).into(new Target() {
                public void onBitmapLoaded(Bitmap resource, Picasso.LoadedFrom from) {
                    OwnAds.bitmap_icon = resource;
                    if (OwnAds.mAdListener != null) {
                        OwnAds.mAdListener.onAdLoaded();
                    }
                }

                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    if (OwnAds.mAdListener != null) {
                        OwnAds.mAdListener.onAdLoadFailed(e);
                    }

                    OwnAds.isAdLoaded = false;
                }

                public void onPrepareLoad(Drawable placeHolderDrawable) {
                }
            });
            Picasso.get().load(modal.getLargeImageUrl()).into(new Target() {
                public void onBitmapLoaded(Bitmap resource, Picasso.LoadedFrom from) {
                    OwnAds.bitmap_photo = resource;
                    if (OwnAds.mAdListener != null) {
                        OwnAds.mAdListener.onAdLoaded();
                    }

                    OwnAds.isAdLoaded = true;
                }

                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    if (OwnAds.mAdListener != null) {
                        OwnAds.mAdListener.onAdLoadFailed(e);
                    }

                    OwnAds.isAdLoaded = false;
                }

                public void onPrepareLoad(Drawable placeHolderDrawable) {
                }
            });

            adTitle = modal.getAppTitle();
            adDesc = modal.getAppDesc();
            adPrice = modal.getPrice();
            adButtonText = modal.getCtaText();
            adRating = Float.valueOf(modal.getRating());
            urlClicked = modal.getPackageNameOrUrl();
        } else {

        }
    }

    public boolean isAdLoaded() {
        return isAdLoaded;
    }

    public void showInterstitial() {
        this.context.startActivity(new Intent(this.context, OwnAds.InterstitialActivity.class));
        if (this.context instanceof AppCompatActivity) {
            ((AppCompatActivity) this.context).overridePendingTransition(0, 0);
        }

    }

    public void showInterstitial(@AnimRes int enterAnim, @AnimRes int exitAnim) {
        this.context.startActivity(new Intent(this.context, OwnAds.InterstitialActivity.class));
        if (this.context instanceof AppCompatActivity) {
            ((AppCompatActivity) this.context).overridePendingTransition(enterAnim, exitAnim);
        }

    }

    public static class InterstitialActivity extends Activity {
        public InterstitialActivity() {
        }

        public ImageView iv_ads_cancel, iv_ads_logo, iv_ads_photo;
        public TextView tv_ads_title, price, tv_ads_desc;
        public RatingBar stars;
        public Button btn_install;


        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (OwnAds.mAdListener != null) {
                OwnAds.mAdListener.onAdShown();
            }

            this.setContentView(R.layout.house_ads_interstitial_layout);
            iv_ads_cancel = (ImageView) this.findViewById(R.id.iv_ads_cancel);
            iv_ads_logo = (ImageView) this.findViewById(R.id.iv_ads_logo);
            iv_ads_photo = (ImageView) this.findViewById(R.id.iv_ads_photo);
            tv_ads_title = (TextView) this.findViewById(R.id.tv_ads_title);
            price = (TextView) this.findViewById(R.id.price);
            tv_ads_desc = (TextView) this.findViewById(R.id.tv_ads_desc);
            stars = (RatingBar) this.findViewById(R.id.stars);
            btn_install = (Button) this.findViewById(R.id.btn_ads_install2);

            iv_ads_logo.setImageBitmap(OwnAds.bitmap_icon);
            iv_ads_photo.setImageBitmap(OwnAds.bitmap_photo);

            btn_install.setText(adButtonText);
            tv_ads_title.setText(adTitle);
            tv_ads_desc.setText(adDesc);
            price.setText(adPrice);
            stars.setRating(adRating);

            btn_install.setOnClickListener((view) -> {
                Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(OwnAds.urlClicked));
                this.startActivity(browse);
                if (OwnAds.mAdListener != null) {
                    OwnAds.mAdListener.onApplicationLeft();
                }
                this.finish();

            });
            iv_ads_photo.setOnClickListener((view) -> {
                Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(OwnAds.urlClicked));
                this.startActivity(browse);
                if (OwnAds.mAdListener != null) {
                    OwnAds.mAdListener.onApplicationLeft();
                }
                this.finish();

            });
            iv_ads_cancel.setOnClickListener((view) -> {
                this.finish();
                OwnAds.isAdLoaded = false;
                if (OwnAds.mAdListener != null) {
                    OwnAds.mAdListener.onAdClosed();
                }

            });
        }

        public void onBackPressed() {
            OwnAds.isAdLoaded = false;
            if (OwnAds.mAdListener != null) {
                OwnAds.mAdListener.onAdClosed();
            }

            this.finish();
        }
    }


    public void showRateDialog() {
        if (feedbackEmail.isEmpty()) {
            rateApp();
        } else {
            rate = 0;
            View dialogView = LayoutInflater.from(context).inflate(R.layout.rating_dialog_layout, null);
            RatingBar ratingBar = dialogView.findViewById(R.id.ratingBar);
            final EditText editTextFeedback = dialogView.findViewById(R.id.feecbackEt);
            final Button button = dialogView.findViewById(R.id.submitBtn);

            final AlertDialog dialog = new AlertDialog.Builder(context)
                    .setView(dialogView)
                    .show();

            if (menInBlack) {
                button.setTextColor(context.getResources().getColor(R.color.primaryText));
            }

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (rate > 4) {
                        rateApp();
                    } else {
                        sendEmailFeedback(editTextFeedback.getText().toString());
                    }
                    dialog.dismiss();
                }
            });

            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    rate = (int) v;
                    if (v > 3) {
                        rateApp();
                    } else {
                        editTextFeedback.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    private void sendEmailFeedback(String feedback) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "" + feedbackEmail, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "" + context.getApplicationInfo().loadLabel(context.getPackageManager()).toString() + " feedback");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "" + feedback);
        context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    private void rateApp() {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }

    public void shuffleBeforeShowingDialog() {
        shuffleDialogAds = true;
    }

    public void setFeedbackEmail(String feedbackEmail) {
        this.feedbackEmail = feedbackEmail;
    }

    public void setMenInBlack(boolean menInBlack) {
        this.menInBlack = menInBlack;
    }

}
