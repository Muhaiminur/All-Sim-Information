package com.sim.allsiminformation;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.sim1)
    TextView sim1;
    @BindView(R.id.sim2)
    TextView sim2;
    @BindView(R.id.gp)
    ImageButton gp;
    @BindView(R.id.robi)
    ImageButton robi;
    @BindView(R.id.airtel)
    ImageButton airtel;
    @BindView(R.id.banglalink)
    ImageButton banglalink;
    @BindView(R.id.teletalk)
    ImageButton teletalk;


    String sim1_operator;
    String sim2_operator;
    private AppCompatActivity context;
    private int REQUEST_CALL_PERMISSION = 1;


    private AdView adView;
    private InterstitialAd interstitialAd;
    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        AudienceNetworkAds.initialize(this);
        context = this;
        sim1_operator = getOutput(MainActivity.this, "getSimOperatorName", 0);
        sim2_operator = getOutput(MainActivity.this, "getSimOperatorName", 1);
        if (sim1_operator != null && !TextUtils.isEmpty(sim1_operator)) {
            sim1.setText(sim1_operator);
        } else {
            sim1.setVisibility(View.GONE);
        }
        if (sim2_operator != null && !TextUtils.isEmpty(sim2_operator)) {
            sim2.setText(sim2_operator);
        } else {
            sim2.setVisibility(View.GONE);
        }

        if (checkPermission()) {

        } else {
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);
        }

        bannerad();
        interstellar();
    }

    @OnClick({R.id.gp, R.id.robi, R.id.airtel, R.id.banglalink, R.id.teletalk})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.gp:
                startActivity(new Intent(context, com.sim.allsiminformation.gp.class));
                break;
            case R.id.robi:
                startActivity(new Intent(context, com.sim.allsiminformation.robi.class));
                break;
            case R.id.airtel:
                startActivity(new Intent(context, com.sim.allsiminformation.airtel.class));
                break;
            case R.id.banglalink:
                startActivity(new Intent(context, com.sim.allsiminformation.Banglalink.class));
                break;
            case R.id.teletalk:
                startActivity(new Intent(context, com.sim.allsiminformation.Teletalk.class));
                break;
        }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == REQUEST_CALL_PERMISSION) {
                /*Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);*/
                //we checked permission befor, then don't worry about the red line
                //


                Toast.makeText(context, "Permission Guaranteed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED) {

            return true;

        } else {

            return false;

        }
    }

    public void bannerad() {
        adView = new AdView(this, getResources().getString(R.string.homepage_banner), AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        LinearLayout adContainer = findViewById(R.id.banner_container);

        // Add the ad view to your activity layout
        adContainer.addView(adView);


        adView.setAdListener(new AdListener() {
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
        adView.loadAd();
    }

    private void interstellar() {
        interstitialAd = new InterstitialAd(this, getResources().getString(R.string.homepage_intersteller));
        // Set listeners for the Interstitial Ad
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e(TAG, "Interstitial ad dismissed.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
                //interstitialAd.show();
                showAdWithDelay();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d(TAG, "Interstitial ad impression logged!");
            }
        });

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialAd.loadAd();
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }

        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }

    private void showAdWithDelay() {
        /**
         * Here is an example for displaying the ad with delay;
         * Please do not copy the Handler into your project
         */
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // Check if interstitialAd has been loaded successfully
                if (interstitialAd == null || !interstitialAd.isAdLoaded()) {
                    return;
                }
                // Check if ad is already expired or invalidated, and do not show ad if that is the case. You will not get paid to show an invalidated ad.
                if (interstitialAd.isAdInvalidated()) {
                    return;
                }
                // Show the ad
                interstitialAd.show();
            }
        }, 10000); // Show the ad after 15 minutes
    }

    private static String getOutput(Context context, String methodName, int slotId) {
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        Class<?> telephonyClass;
        String reflectionMethod = null;
        String output = null;
        try {
            telephonyClass = Class.forName(telephony.getClass().getName());
            for (Method method : telephonyClass.getMethods()) {
                String name = method.getName();
                if (name.contains(methodName)) {
                    Class<?>[] params = method.getParameterTypes();
                    if (params.length == 1 && params[0].getName().equals("int")) {
                        reflectionMethod = name;
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (reflectionMethod != null) {
            try {
                output = getOpByReflection(telephony, reflectionMethod, slotId, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return output;
    }

    private static String getOpByReflection(TelephonyManager telephony, String predictedMethodName, int slotID, boolean isPrivate) {

        //Log.i("Reflection", "Method: " + predictedMethodName+" "+slotID);
        String result = null;

        try {

            Class<?> telephonyClass = Class.forName(telephony.getClass().getName());

            Class<?>[] parameter = new Class[1];
            parameter[0] = int.class;
            Method getSimID;
            if (slotID != -1) {
                if (isPrivate) {
                    getSimID = telephonyClass.getDeclaredMethod(predictedMethodName, parameter);
                } else {
                    getSimID = telephonyClass.getMethod(predictedMethodName, parameter);
                }
            } else {
                if (isPrivate) {
                    getSimID = telephonyClass.getDeclaredMethod(predictedMethodName);
                } else {
                    getSimID = telephonyClass.getMethod(predictedMethodName);
                }
            }

            Object ob_phone;
            Object[] obParameter = new Object[1];
            obParameter[0] = slotID;
            if (getSimID != null) {
                if (slotID != -1) {
                    ob_phone = getSimID.invoke(telephony, obParameter);
                } else {
                    ob_phone = getSimID.invoke(telephony);
                }

                if (ob_phone != null) {
                    result = ob_phone.toString();

                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
        //Log.i("Reflection", "Result: " + result);
        return result;
    }
}
