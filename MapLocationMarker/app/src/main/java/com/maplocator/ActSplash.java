package com.maplocator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
/*

import com.elasticode.provider.Elasticode;
import com.elasticode.provider.callback.ElasticodeResponse;
*/


import com.flaviofaria.kenburnsview.KenBurnsView;
import com.gcm.QuickstartPreferences;
import com.gcm.RegistrationIntentService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.utils.App;
import com.wallpaper.ActFoggyGlass;

import org.jsoup.Jsoup;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * Created by Admin on 10/21/2016.
 */
public class ActSplash extends Activity
{

    private boolean isAppUpdated = false;
    private static int TIME = 2000;
    /**
     * The Array.
     */
    int[] array={R.drawable.bg_image,R.drawable.bg_splash_1,R.drawable.bg_splash_2,R.drawable.bg_image,R.drawable.bg_splash_1,R.drawable.bg_splash_2,R.drawable.bg_image,R.drawable.bg_splash_1,R.drawable.bg_splash_2};
    /**
     * The M ken burns.
     */
    KenBurnsView mKenBurns;

   // String TAG = "==Splash==";

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    /**
     * The Android id.
     */
    String android_id;
    /**
     * The Flag.
     */
    boolean flag = false;
    /**
     * The Shared preferences.
     */
    SharedPreferences sharedPreferences;
    /**
     * The Device token.
     */
    String deviceToken = "0";
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private boolean isReceiverRegistered;


    String currentVersion = "0.0";
    String packageName = "com.kdah.kdahdoctors";



    private final static String TAG = ActSplash.class.getSimpleName();
    //private Elasticode elasticode;
    //int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
		/*ViewGroup.inflate(this,R.layout.activity_home,linearContentLayout);
		txtHeader.setText("Home");*/
        //AppUtils.HideTopStatus(ActSplash.this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.act_splash_screen);

      /*  Elasticode elasticode =Elasticode.getInstance(this, "ms25j4cfyut71dgtf47jai6y", elasticodeObserver);
        elasticode.ready();*/
        // Elasticode api key that you can find in Dashboard -> acoount page
      //  elasticode = Elasticode.getInstance(this,"your-api-key-here", elasticodeObserver);


      /*  elasticode =Elasticode.getInstance(this, "ms25j4cfyut71dgtf47jai6y", elasticodeObserver);
        // ready is mandatory
        elasticode.ready();
*/


        mKenBurns = (KenBurnsView) findViewById(R.id.ken_burns_images);
      //  mKenBurns.setImageResource(R.drawable.splash_background);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                int i = random.nextInt(array.length);

                Animation animationFadeIn = AnimationUtils.loadAnimation(ActSplash.this, R.anim.fadein);
                //imageViewProfilePic.setImageDrawable(HomeActivity.this.getResources().getDrawable(array[i]));
                mKenBurns.setImageDrawable(ActSplash.this.getResources().getDrawable(array[i]));
                mKenBurns.startAnimation(animationFadeIn);


/*
		while (i > array.length)
		{
            mKenBurns.setBackgroundDrawable(ActSplash.this.getResources().getDrawable(array[i]));


        }
i=1;
        new CountDownTimer(8000,800)
        {

            @Override
            public void onTick(long millisUntilFinished)
            {

            }

            @Override
            public void onFinish()
            {
                Animation animationFadeIn = AnimationUtils.loadAnimation(ActSplash.this, R.anim.fadein);
                //imageViewProfilePic.setImageDrawable(HomeActivity.this.getResources().getDrawable(array[i]));
                mKenBurns.setImageDrawable(ActSplash.this.getResources().getDrawable(array[i]));
                mKenBurns.startAnimation(animationFadeIn);
                i++;
                if(i== array.length-1) i=0;
                start();
            }

        }.start();
*/
            }
        });



    /*    new CountDownTimer(10000,2000)
        {

            @Override
            public void onTick(long millisUntilFinished)
            {

            }

            @Override
            public void onFinish()
            {
                Animation animationFadeIn = AnimationUtils.loadAnimation(ActSplash.this, R.anim.fadein);
                //imageViewProfilePic.setImageDrawable(HomeActivity.this.getResources().getDrawable(array[i]));
                mKenBurns.setBackgroundDrawable(ActSplash.this.getResources().getDrawable(array[2]));
                mKenBurns.startAnimation(animationFadeIn);
                i++;
                if(i== array.length-1) i=0;
                start();
            }
        }.start();*/
      //  getDeviceToken();

        displaySplash();

        try {

            //currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            //new GetVersionCode().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private class GetVersionCode extends AsyncTask<Void, String, String> {

        @Override

        protected String doInBackground(Void... voids) {

            String newVersion = null;

            try {

                //newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + getPackageName() + "&hl=it").timeout(30000)
                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + packageName + "&hl=it").timeout(30000)

                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")

                        .referrer("http://www.google.com")
                        .get()
                        .select("div[itemprop=softwareVersion]")
                        .first()
                        .ownText();

                return newVersion;

            } catch (Exception e) {

                return newVersion;

            }

        }


        @Override

        protected void onPostExecute(String onlineVersion) {

            super.onPostExecute(onlineVersion);


            App.showLog("=====Api======onlineVersion===="+onlineVersion);

            App.showLog("=====Api===000===my version==2.2.5.2==");
            App.showLog("==ANS 000==="+versionCompare("2.2.5.2",onlineVersion));


            App.showLog("=====Api===111===my version==3.9.3==");
            App.showLog("==ANS 111===" + versionCompare("3.9.3", onlineVersion));


            App.showLog("=====Api===222===onlineVersion==4.0.3==");
            App.showLog("==ANS 222==="+versionCompare("4.0.3",onlineVersion));


            App.showLog("=====Api===333===my version==3.9.4==");
            App.showLog("==ANS 333==="+versionCompare("3.9.4",onlineVersion));


            App.showLog("=====Api===444===my version==3.9.0==");
            App.showLog("==ANS 444==="+versionCompare("3.9.0",onlineVersion));




            if(versionCompare("3.9.5",onlineVersion) < 0)
            {
                isAppUpdated = true;

                new Handler().postDelayed(new Runnable() {
                    public void run() {


                         AlertDialog dialog = new AlertDialog.Builder(ActSplash.this)
                                //.setView(v)
                                .setTitle("New version update")
                                .setMessage("Please update to latest version of KDAH Pro")
                                .setPositiveButton("UPDATE", null) //Set to null. We override the onclick
                                .create();

                        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

                            @Override
                            public void onShow(DialogInterface dialog) {

                                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                                button.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View view) {
                                        // TODO Do something

                                        //Dismiss once everything is OK.

                                        final String appPackageName = "com.kdah";//getPackageName(); // getPackageName() from Context or Activity object
                                        try {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                        } catch (android.content.ActivityNotFoundException anfe) {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                        }

                                     //   dialog.dismiss();
                                    }
                                });
                            }
                        });

                      /*  AlertDialog.Builder builder = new AlertDialog.Builder(ActSplash.this);
                        builder.setCancelable(false);
                        builder.setTitle("New version update");
                        builder.setMessage("Please update to latest version of KDAH");
                        builder.setNegativeButton("Update",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,int which) {

                                        final String appPackageName = "com.kdah";//getPackageName(); // getPackageName() from Context or Activity object
                                        try {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                        } catch (android.content.ActivityNotFoundException anfe) {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                        }
                                        //dialog.dismiss();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                        */


                    }



                }, 100);



            }
            else
            {
                App.showLog("=======Application updated==============");
                isAppUpdated = false;
            }


        /*

            if (onlineVersion != null && !onlineVersion.isEmpty()) {

                if (Float.valueOf(currentVersion) < Float.valueOf(onlineVersion)) {

                    //show dialog
                    Log.d("update", "---YES show dialog----Current version " + currentVersion + "playstore version " + onlineVersion);
                }
                else
                {
                    Log.d("update", "---NO show dialog----Current version " + currentVersion + "playstore version " + onlineVersion);
                }

            }*/

            Log.d("update", "Current version " + currentVersion + "playstore version " + onlineVersion);

        }
    }

    /**
     * Compares two version strings.
     *
     * Use this instead of String.compareTo() for a non-lexicographical
     * comparison that works for version strings. e.g. "1.10".compareTo("1.6").
     *
     * @note It does not work if "1.10" is supposed to be equal to "1.10.0".
     *
     * @param str1 a string of ordinal numbers separated by decimal points.
     * @param str2 a string of ordinal numbers separated by decimal points.
     * @return The result is a negative integer if str1 is _numerically_ less than str2.
     *         The result is a positive integer if str1 is _numerically_ greater than str2.
     *         The result is zero if the strings are _numerically_ equal.
     */
    public static int versionCompare(String str1, String str2) {
        String[] vals1 = str1.split("\\.");
        String[] vals2 = str2.split("\\.");
        int i = 0;
        // set index to first non-equal ordinal or length of shortest version string
        while (i < vals1.length && i < vals2.length && vals1[i].equals(vals2[i])) {
            i++;
        }
        // compare first non-equal ordinal number
        if (i < vals1.length && i < vals2.length) {
            int diff = Integer.valueOf(vals1[i]).compareTo(Integer.valueOf(vals2[i]));
            return Integer.signum(diff);
        }
        // the strings are equal or one string is a substring of the other
        // e.g. "1.2.3" = "1.2.3" or "1.2.3" < "1.2.3.4"
        return Integer.signum(vals1.length - vals2.length);
    }


        private void getDeviceToken() {
        try{
            String strToken = App.sharePrefrences.getStringPref(App.PF_DEVICE_ID);
            if(strToken !=null && strToken.length() > 5)
            {
                Log.d(TAG, "--strToken--"+strToken);
                Log.d(TAG, "--No need to getDeviceToken--");
            }
            else {
                Log.d(TAG, "--Need to getDeviceToken--");

                getGcmKey();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void registerReceiver() {
        if (!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
            isReceiverRegistered = true;
        }
    }
    private void getGcmKey() {
        try {
            String deviceToken = App.sharePrefrences.getStringPref(App.PF_DEVICE_ID);
            if (deviceToken != null && deviceToken.length() > 5) {
                Log.e(TAG, "==111=My Device id is==>>" + deviceToken);
            } else {

                Log.e(TAG, "==111=My Device id is Not getting properly==");
            }
            mRegistrationBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    boolean sentToken = sharedPreferences
                            .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                    if (sentToken) {

                        //   mInformationTextView.setText(getString(R.string.gcm_send_message));
                        String deviceToken = App.sharePrefrences.getStringPref(App.PF_DEVICE_ID);

                        if (deviceToken != null && deviceToken.length() > 0) {
                            Log.e(TAG, "==222=My Device id is==>>" + deviceToken);
                        } else {
                            Log.e(TAG, "==222=My Device id is Not getting properly==");
                        }
                    } else {

                        //     mInformationTextView.setText(getString(R.string.token_error_message));
                    }
                }
            };

            // Registering BroadcastReceiver
            registerReceiver();

            if (checkPlayServices()) {
                // Start IntentService to register this application with GCM.
                Intent intent = new Intent(this, RegistrationIntentService.class);
                startService(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                // finish();
            }
            return false;
        }
        return true;
    }


    // splash screen set with timing
    private void displaySplash() {
        // TODO Auto-generated method stub b

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                if(isAppUpdated == false) {
                    Intent iv = new Intent(ActSplash.this, HomeActivity.class);
                    //Intent iv = new Intent(ActSplash.this, ActFoggyGlass.class);

                    iv.putExtra("from", "splash");
                    startActivity(iv);
                    finish();
                }
            }
        }, TIME);
    }


    @Override
    protected void onResume() {
        super.onResume();
   //     registerReceiver();
        AppUtils.fullScreencall(ActSplash.this);
    }



/*
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        elasticode.setNewIntent(intent);
    }

    private Observer elasticodeObserver = new Observer() {
        @Override
        public void update(Observable observable, Object data) {
            if (data instanceof ElasticodeResponse) {
                ElasticodeResponse response = (ElasticodeResponse) data;
                if (response.getError() != null) {
                    Log.d(TAG, "Error: " + response.getError());
                }else{
                    switch (response.getType()) {
                        case SESSION_STARTED:
                            // Elasticode in sync
                            break;
                    }
                }
            }
        }
    };*/


}
