package com.maplocator;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.utils.SharePrefrences;

import java.io.File;


public class AppUtils {





    public static String strFolderNamePath = Environment.getExternalStorageDirectory() + File.separator + "MapLocator";

    public class ApiCallAdress {
        public static final String WEB_GOOGLE_PLACES_API ="https://maps.googleapis.com/maps/api/place/search/";
        public static final String GOOGLE_API_KEY1 = "AIzaSyAgrsctqXjg_Qk7bo6wmMUUfV2QKWSd_wE";
        public static final String GOOGLE_API_KEY2 = "AIzaSyACEQKnVMrnzUtf-7tc4dGureOIL3N1ryU";

        /*//https://console.developers.google.com/apis/api/places_backend?project=_

        madm
        //AIzaSyACEQKnVMrnzUtf-7tc4dGureOIL3N1ryU

        vin
        //AIzaSyAgrsctqXjg_Qk7bo6wmMUUfV2QKWSd_wE*/

    }

    public class IntentTags {
        public static final String FROM = "from";
        public static final String TITLE = "title";
        public static final String TYPE = "type";

        public static final String LAT = "lat";
        public static final String LONG = "long";
    }

    public class LocationConstants {
        public static final int SUCCESS_RESULT = 0;

        public static final int FAILURE_RESULT = 1;

        /*public static final String PACKAGE_NAME = "com.sample.sishin.maplocation";*/
        public static final String PACKAGE_NAME = "com.maplocator";

        public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";

        public static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";

        public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";

        public static final String LOCATION_DATA_AREA = PACKAGE_NAME + ".LOCATION_DATA_AREA";
        public static final String LOCATION_DATA_CITY = PACKAGE_NAME + ".LOCATION_DATA_CITY";
        public static final String LOCATION_DATA_STREET = PACKAGE_NAME + ".LOCATION_DATA_STREET";


    }


    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }


    public  static void  fullScreencall(Activity activity) {
        if(Build.VERSION.SDK_INT < 19){ //19 or above api
            View v = activity.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else {
            //for lower api versions.
            View decorView = activity.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }


    public static void hideTopStatus(Activity activity)
    {
        View decorView = activity.getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }



    public static boolean checkInternet(Context context)
    {
        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isConnected())
        {
            return true;
        }
        else if (!mobile.isConnected())
        {
            return false;
        }
        else if (mobile.isConnected())
        {
            return true;
        }
        return false;
    }

    public static void showToast(Context context,String msg)
    {
        Toast.makeText(context, msg , Toast.LENGTH_SHORT).show();
    }

    public static void showSnackBar(View view,String msg)
    {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }

    public static void showLog(String msg)
    {
        System.out.println("==AppUtils-Msg==="+msg);
    }
    public static void showLog(String tag,String msg)
    {
        System.out.println("==tag=="+tag+"   ==msg=="+msg);
    }
}
