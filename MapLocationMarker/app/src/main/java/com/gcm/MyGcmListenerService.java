/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gcm;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Html;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.maplocator.AppUtils;
import com.maplocator.HomeActivity;
import com.maplocator.R;
import com.utils.App;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * The type My gcm listener service.
 */
public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "-MyGcmListenerService-";

    /**
     * The constant NOTIFICATION_ID.
     */
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    /**
     * The Builder.
     */
    NotificationCompat.Builder builder;
    /**
     * The Context.
     */
    Context context;
    /**
     * The Icon.
     */
    Bitmap icon;
    /**
     * The Content intent.
     */
    PendingIntent contentIntent;

    /**
     * The Img.
     */
    String img="http://hdwallpaperbackgrounds.net/wp-content/uploads/2015/07/top-cartoon_hd_top_wallpapers.jpg",
    /**
     * The Title.
     */
    title="Make a wish",
    /**
     * The Detail.
     */
    detail="We believe in the healing power of a wish come true, for many children it is the turning point they need to feeling stronger and better able to battle their life-threatening medical ...",
    /**
     * The Time.
     */
    time = "5 mins ago",
    /**
     * The Type.
     */
    type = "";

    /**
     * The Bln form expire.
     */
    boolean blnFormExpire = false;


    /**
     * Bundle 2 string string.
     *
     * @param bundle the bundle
     * @return the string
     */
    public static String bundle2string(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        String string = "Bundle{";
        for (String key : bundle.keySet()) {
            string += " " + key + " => " + bundle.get(key) + ";";
        }
        string += " }Bundle";
        return string;
    }

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        App.showLog(TAG, "==onMessageReceived===");
        try{
            context = this.getApplicationContext();
            // TODO(developer): Handle FCM messages here.
            // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
            App.showLog(TAG, "From: " + from);

            // Check if message contains a data payload.
            if (data.size() > 0) {
                App.showLog(TAG, "Message data payload: " + data);

                img =  data.getString("img");
                title =  data.getString("title");
                detail =  data.getString("detail");
                time =  data.getString("time");
                type =  data.getString("type");


                // type 100 for the update database
                if(data.getString("type") !=null && data.getString("type").equalsIgnoreCase("100"))
                {
                    Intent intent = new Intent("message-id");
                    intent.putExtra("someExtraMessage", "Some Message :)");
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }

                App.showLog(TAG, "img--"+img);
                App.showLog(TAG, "title--"+title);
            }
           /* if(strCode !=null&& strCode.length() > 0) {
                sendNotificationImage(data);

                Intent intent = new Intent("message-id");
                intent.putExtra("someExtraMessage", "Some Message :)");
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }*/
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void sendNotification(String messageBody) {
       // Intent intent = new Intent(this, LocalActHomeList.class);
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra(AppUtils.IntentTags.FROM, "notifiction");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher) //small icon
                .setContentTitle("Notification")
                .setContentText(Html.fromHtml(messageBody))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int notifitionIdTime = (int)System.currentTimeMillis();
        notificationManager.notify(notifitionIdTime /* ID of notification */, notificationBuilder.build());
    }

    private void sendNotificationImage(Bundle data) {

        // Check if message contains a data payload.
        if (data.size() > 0) {
            App.showLog(TAG, "Message data payload: " + data);


            img =  data.getString("img");
            title =  data.getString("title");
            detail =  data.getString("detail");
            time =  data.getString("time");
            type =  data.getString("type");

            App.showLog(TAG, "type--"+type);
            App.showLog(TAG, "img--"+img);
            App.showLog(TAG, "title--"+title);
        }

        new generatePictureStyleNotification2222(this.getApplicationContext(), title, detail,time, img).execute();

        // new generatePictureStyleNotification22(this.getApplicationContext(), "", "", "").execute();
    }

    /**
     * The type Generate picture style notification 2222.
     */
    public class generatePictureStyleNotification2222 extends AsyncTask<String, Void, Bitmap> {

        private Context mContext1;
        private String title1, message1,time1,imageUrl1;

        /**
         * Instantiates a new Generate picture style notification 2222.
         *
         * @param context  the context
         * @param title    the title
         * @param message  the message
         * @param time     the time
         * @param imageUrl the image url
         */
        public generatePictureStyleNotification2222(Context context, String title, String message,String time,String imageUrl) {
            super();
            mContext1 = context;
            title1 = title;
            message1 = message;
            time1 = time;
            imageUrl1 = imageUrl;

        }

        @Override
        protected Bitmap doInBackground(String... params) {

            InputStream input;
            try {
                URL url = new URL(imageUrl1);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);

                File dir = new File(App.strFolderNamePath);
                try {

                    int count;

                   String fileName = imageUrl1.substring(imageUrl1.lastIndexOf('/') + 1);

                 //   int lenghtOfFile = conection.getContentLength();

                    if (dir.mkdirs()) {
                        System.out.println("Directory created");
                    } else {
                        System.out.println("Directory is already created");
                    }



                    OutputStream output = new FileOutputStream(App.strFolderNamePath + File.separator + fileName);
                    System.out.println("==save image path is ==>>" + App.strFolderNamePath + File.separator + fileName);

                    byte data[] = new byte[1024];
                    long total = 0;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        // publishing the progress....
                        // After this onProgressUpdate will be called
                     //111   publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                        // writing data to file
                        output.write(data, 0, count);
                    }
                    // flushing output
                    output.flush();
                    // closing streams
                    output.close();
                    input.close();







                } catch (Exception e) {
                    e.printStackTrace();
                }


                return myBitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            try {

                //Intent intent = new Intent(MyGcmListenerService.this, LocalActHomeList.class);
                Intent intent = new Intent(MyGcmListenerService.this, HomeActivity.class);
                intent.putExtra(AppUtils.IntentTags.FROM, "notification");
                intent.putExtra("title",title1);
                intent.putExtra("message",message1);
                intent.putExtra("time",time1);
                intent.putExtra("imageUrl",imageUrl1);

                if(type !=null && type.length() > 0)
                {
                    intent.putExtra("type",type);
                }






                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(MyGcmListenerService.this, 0  /*Request code */, intent,
                        PendingIntent.FLAG_ONE_SHOT);

                Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(MyGcmListenerService.this)
                        .setSmallIcon(R.drawable.ic_launcher)// small icon
                        .setContentTitle(Html.fromHtml(title1))
                        // .setContentText(this.message)
                        .setSubText(Html.fromHtml(message1))
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(result))
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    notificationBuilder.setSmallIcon(R.drawable.ic_launcher); // small icon
                    notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(mContext1.getResources(), R.drawable.ic_launcher));
                } else {
                    notificationBuilder.setSmallIcon(R.drawable.ic_launcher);
                }

                int notifitionIdTime = (int)System.currentTimeMillis();

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(notifitionIdTime  /*ID of notification*/ , notificationBuilder.build());

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    // [END receive_message]


    /**
     * File exists boolean.
     *
     * @param context  the context
     * @param filename the filename
     * @return the boolean
     */
    public boolean fileExists(Context context, String filename) {
        File file = new File(filename);
        if (file == null || !file.exists()) {
            return false;
        }
        return true;
    }


    /**
     * Strip html string.
     *
     * @param html the html
     * @return the string
     */
    public String stripHtml(String html) {
        return Html.fromHtml(html).toString();
    }


    private static String displayTimeZone(TimeZone tz) {

        long hours = TimeUnit.MILLISECONDS.toHours(tz.getRawOffset());
        long minutes = TimeUnit.MILLISECONDS.toMinutes(tz.getRawOffset())
                - TimeUnit.HOURS.toMinutes(hours);
        // avoid -4:-30 issue
        minutes = Math.abs(minutes);

        String result = "";
        /*if (hours > 0) {
            result = String.format("(GMT+%d:%02d) %s", hours, minutes, tz.getID());
        } else {
            result = String.format("(GMT%d:%02d) %s", hours, minutes, tz.getID());
        }*/

        result = String.format(tz.getID());

        return result;

    }

    /**
     * The type Notification id.
     */
    public static class NotificationID {
        private final AtomicInteger c = new AtomicInteger(0);

        /**
         * Gets id.
         *
         * @return the id
         */
        public int getID() {
            return c.incrementAndGet();
        }
    }
}
