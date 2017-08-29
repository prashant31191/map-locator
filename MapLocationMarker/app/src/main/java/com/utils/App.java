package com.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.utils.DatabaseUtils;
import com.utils.SharePrefrences;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Admin on 8/4/2016.
 */

public class App extends Application
{
    // share pref name
    public String PREF_NAME = "maplocator";

    // class for the share pref keys and valyes get set
    public static SharePrefrences sharePrefrences;

    // intent passing tags or string key names
    public static String ITAG_FROM = "from";

    String TAG = "==App==";

    // for the pref key name
    public static String PF_INSTALL = "install";//USER CODE
    public static String PF_CODE = "code";//USER CODE
    public static String PF_DEVICE_ID= "device_id"; // USER DEVICE TOKEN

    public static String PF_FS_DATE = "fsdate"; // START DATE
    public static String PF_FE_DATE= "fedate"; // END DATE
    public static String PF_FEXP_DATE= "fexpdate"; // EXPIRE DATE

    public static String strPrevTime = "";



    public static String PF_FNOTIFY_DATE = "fnotifydate"; // START DATE


    public DatabaseUtils dbutil;
    // for the app context
    public static Context mContext;

    // for the set app fontface or type face
    static Typeface tfRoboto,tfShortStack;


    // for the  api call
    public static String strBaseUrl="http://asd.com/sd";

    // App static KeyWords for api call and database fields
    public static  String KEY_nid = "nid";
    public static String KEY_fid = "fid";
    public static String KEY_ttl = "ttl";
    public static String KEY_img = "img";
    public static String KEY_desc = "desc";
    public static String KEY_itllst = "itllst";
    public static String KEY_date = "date";
    public static String KEY_time = "time";
    public static String KEY_type = "type";
    public static String KEY_sts = "sts";
    public static String KEY_isDaily = "isDaily";
    public static String KEY_reg_day = "reg_day";

    public static String KEY_isNotify = "isNotify";
    public static String KEY_isDownload = "isDownload";
    public static String KEY_timeNotifty = "timeNotifty";



    // Folder Name
    public static String strFolderNamePath = Environment.getExternalStorageDirectory() + File.separator + "maplocator";


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

    // application on create methode for the create and int base values
    @Override
    public void onCreate() {
        super.onCreate();


        mContext = getApplicationContext();


        File folder = new File(strFolderNamePath + File.separator +"Database");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        sharePrefrences = new SharePrefrences(App.this);
        getFontRoboto();
        getFontShortStack();
        //setDataArrayList();

        dbutil = new DatabaseUtils(getApplicationContext());
    }


    public static Typeface getFontRoboto()
    {
        tfRoboto = Typeface.createFromAsset(mContext.getAssets(),"fonts/gothic-regular.TTF"); //Roboto-Regular
        return tfRoboto;
    }

    public static Typeface getFontShortStack() {
        tfShortStack = Typeface.createFromAsset(mContext.getAssets(),"fonts/ShortStack-Regular.ttf");
        return tfShortStack;
    }


    public static  void showSnackBar(View view,String strMessage)
    {
        //Snackbar.make(view,strMessage,Snackbar.LENGTH_SHORT).show();
    }

    public static  void sysOut(String strMessage)
    {
        System.out.println("====== "+strMessage+" ======");
    }

    public static  void showLog(String strMessage)
    {
      //111  Log.v("==App==", "--Data--"+strMessage);
    }

    public static  void showLog(String strTAG , String strMessage)
{
    Log.v(strTAG+"==App==", "--Data--"+strMessage);
}

    public static  void showLog2(String strMessage)
    {
        Log.v("==App==", "--Data--"+strMessage);
    }
    public static  void showLog2(String strTAG , String strMessage)
    {
        Log.v(strTAG+"==App==", "--Data--"+strMessage);
    }

    public static void setTaskBarColored(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
        }
    }




    public static boolean deleteDirectory(File path) {
        if( path.exists() ) {
            File[] files = path.listFiles();
            if (files == null) {
                return true;
            }
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                }
                else {
                    files[i].delete();
                }
            }
        }
        return( path.delete() );
    }



    static SimpleDateFormat dfDate  = new SimpleDateFormat("yyyy-MM-dd hh:mm a");

    public static boolean checkDates(String d1,String d2)
    {
        boolean b = false;
        try {
            if (dfDate.parse(d1).before(dfDate.parse(d2))) {
                b = true;//If start date is before end date
            } else if (dfDate.parse(d1).equals(dfDate.parse(d2))) {
                b = false;//If two dates are equal
            } else {
                b = false; //If start date is after the end date
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    catch (Exception e2)
    {
        e2.printStackTrace();
    }
        return b;
    }



    public static boolean checkDatabaseDates(String d1,String d2)
    {
        boolean b = false;
        try {
            if (dfDate.parse(d1).before(dfDate.parse(d2))) {
                b = true;//If start date is before end date
            } else if (dfDate.parse(d1).equals(dfDate.parse(d2))) {
                b = true;//If two dates are equal
            } else {
                b = false; //If start date is after the end date
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (Exception e2)
        {
            e2.printStackTrace();
        }
        return b;
    }

    public static boolean isInternetAvail(Context context)
    {


        ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }
}
