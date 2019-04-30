package com.sim.allsiminformation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.facebook.ads.*;

import java.util.ArrayList;
import java.util.List;

public class gp extends AppCompatActivity {

    @BindView(R.id.gp_balace)
    Button gpBalace;
    @BindView(R.id.gp_customer_care)
    Button gpCustomerCare;
    @BindView(R.id.gp_emergency)
    Button gpEmergency;
    @BindView(R.id.gp_internet_offer)
    Button gpInternetOffer;
    @BindView(R.id.gp_data_check)
    Button gpDataCheck;
    @BindView(R.id.gp_minute_check)
    Button gpMinuteCheck;
    @BindView(R.id.gp_sms_check)
    Button gpSmsCheck;
    @BindView(R.id.gp_mms_check)
    Button gpMmsCheck;
    @BindView(R.id.gp_setting)
    Button gpSetting;
    @BindView(R.id.gp_miss_on)
    Button gpMissOn;
    @BindView(R.id.gp_miss_off)
    Button gpMissOff;
    @BindView(R.id.gp_sim_number)
    Button gpSimNumber;
    @BindView(R.id.gp_call_me)
    Button gpCallMe;

    private AppCompatActivity context;
    private int REQUEST_CALL_PERMISSION = 1;


    private AdView banner_adview;

    private final String TAG = gp.class.getSimpleName();
    private NativeAd nativeAd;
    private NativeAdLayout nativeAdLayout;
    private LinearLayout native_adView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gp);
        ButterKnife.bind(this);
        context = this;
        bannerad();
        loadNativeAd();
    }

    @OnClick({R.id.gp_balace, R.id.gp_customer_care, R.id.gp_emergency, R.id.gp_internet_offer, R.id.gp_data_check, R.id.gp_minute_check, R.id.gp_sms_check, R.id.gp_mms_check, R.id.gp_setting, R.id.gp_miss_on, R.id.gp_miss_off, R.id.gp_sim_number, R.id.gp_call_me})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.gp_balace:
                phone_number(getResources().getString(R.string.gp_balance_string));
                break;
            case R.id.gp_customer_care:
                phone_number(getResources().getString(R.string.gp_customer_string));
                break;
            case R.id.gp_emergency:
                phone_number(getResources().getString(R.string.gp_emergency_string));
                break;
            case R.id.gp_internet_offer:
                phone_number(getResources().getString(R.string.gp_internet_string));
                break;
            case R.id.gp_data_check:
                phone_number(getResources().getString(R.string.gp_data_string));
                break;
            case R.id.gp_minute_check:
                phone_number(getResources().getString(R.string.gp_minute_string));
                break;
            case R.id.gp_sms_check:
                phone_number(getResources().getString(R.string.gp_sms_string));
                break;
            case R.id.gp_mms_check:
                phone_number(getResources().getString(R.string.gp_mms_string));
                break;
            case R.id.gp_setting:
                phone_number(getResources().getString(R.string.gp_net_setting_string));
                break;
            case R.id.gp_miss_on:
                break;
            case R.id.gp_miss_off:
                break;
            case R.id.gp_sim_number:
                phone_number(getResources().getString(R.string.gp_sim_num_string));
                break;
            case R.id.gp_call_me:
                break;
        }
    }


    public void bannerad() {
        banner_adview = new AdView(this, getResources().getString(R.string.gp_banner), AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        LinearLayout adContainer = findViewById(R.id.banner_container_gp);

        // Add the ad view to your activity layout
        adContainer.addView(banner_adview);


        banner_adview.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                /*Toast.makeText(MainActivity.this, "Error: " + adError.getErrorMessage(),
                        Toast.LENGTH_LONG).show();*/
                Log.d("Banner", "Error: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Ad loaded callback
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }
        });
        // Request an ad
        banner_adview.loadAd();
    }

    @Override
    protected void onDestroy() {
        if (banner_adview != null) {
            banner_adview.destroy();
        }
        super.onDestroy();
    }


    private void loadNativeAd() {
        // Instantiate a NativeAd object.
        // NOTE: the placement ID will eventually identify this as your App, you can ignore it for
        // now, while you are testing and replace it later when you have signed up.
        // While you are using this temporary code you will only get test ads and if you release
        // your code like this to the Google Play your users will not receive ads (you will get a no fill error).
        nativeAd = new NativeAd(this, getResources().getString(R.string.gp_native));

        nativeAd.setAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                // Native ad finished downloading all assets
                Log.e(TAG, "Native ad finished downloading all assets.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Native ad failed to load
                Log.e(TAG, "Native ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Native ad is loaded and ready to be displayed
                Log.d(TAG, "Native ad is loaded and ready to be displayed!");
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
                // Inflate Native Ad into Container
                inflateAd(nativeAd);
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Native ad clicked
                Log.d(TAG, "Native ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Native ad impression
                Log.d(TAG, "Native ad impression logged!");
            }
        });

        // Request an ad
        nativeAd.loadAd();
        showNativeAdWithDelay();
    }

    private void inflateAd(NativeAd nativeAd) {

        nativeAd.unregisterView();

        // Add the Ad view into the ad container.
        nativeAdLayout = findViewById(R.id.native_ad_container_gp);
        LayoutInflater inflater = LayoutInflater.from(gp.this);
        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
        native_adView = (LinearLayout) inflater.inflate(R.layout.native_banner_ad_unit, nativeAdLayout, false);
        nativeAdLayout.addView(native_adView);

        // Add the AdOptionsView
        LinearLayout adChoicesContainer = findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(gp.this, nativeAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        AdIconView nativeAdIcon = native_adView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = native_adView.findViewById(R.id.native_ad_title);
        MediaView nativeAdMedia = native_adView.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = native_adView.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = native_adView.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = native_adView.findViewById(R.id.native_ad_sponsored_label);
        Button nativeAdCallToAction = native_adView.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

        // Create a list of clickable views
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);

        // Register the Title and CTA button to listen for clicks.
        nativeAd.registerViewForInteraction(
                native_adView,
                nativeAdMedia,
                nativeAdIcon,
                clickableViews);
    }

    private void showNativeAdWithDelay() {
        /**
         * Here is an example for displaying the ad with delay;
         * Please do not copy the Handler into your project
         */
        // Handler handler = new Handler();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                // Check if nativeAd has been loaded successfully
                if (nativeAd == null || !nativeAd.isAdLoaded()) {
                    return;
                }
                // Check if ad is already expired or invalidated, and do not show ad if that is the case. You will not get paid to show an invalidated ad.
                if (nativeAd.isAdInvalidated()) {
                    return;
                }
                inflateAd(nativeAd); // Inflate Native Ad into Container same as previous code example
            }
        }, 10000); // Show the ad after 15 minutes
    }

    public void phone_number(String number) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL,
                        Uri.parse("tel:" + number));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                ActivityCompat.requestPermissions(context,
                        new String[]{Manifest.permission.CALL_PHONE},
                        REQUEST_CALL_PERMISSION);
            }
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL,
                    Uri.parse("tel:" + number));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

}
