package edu.unidhaka.cse.cse2216.keephealthy.step;


import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import edu.unidhaka.cse.cse2216.keephealthy.MyApplication;
import edu.unidhaka.cse.cse2216.keephealthy.Step_counter;
import edu.unidhaka.cse.cse2216.keephealthy.R;


public class StepService extends Service {

    private StepThread thread;
    private PowerManager.WakeLock mWakeLock;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("service", "service create()");
        thread = new StepThread(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("service", "service start()");
        MyApplication app = (MyApplication) getApplication();
        app.setServiceRun(true);

        if (intent.getBooleanExtra("isActivity", false))
            thread.setActivity(true);
        String s = intent.getStringExtra("restart");
        if (s != null) {
            Log.d("restart", s);
        }
        if (thread.getState() == Thread.State.NEW)
            thread.start();
        SharedPreferences sharedPreferences = getSharedPreferences("conf", MODE_PRIVATE);
        boolean foreground_model = sharedPreferences.getBoolean("foreground_model", false);

        if (foreground_model) {

            myStartForeground();
            mWakeLock(this);
        } else {
            stopForeground(true);
            if (mWakeLock != null) {
                if (mWakeLock.isHeld())
                    mWakeLock.release();
                mWakeLock = null;
            }
        }


        return START_STICKY;
    }

    public void myStartForeground() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Pedometer")
                        .setContentText("running");

        Intent notificationIntent = new Intent(this, Step_counter.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(Step_counter.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);


        startForeground(1, mBuilder.build());
    }


    @Override
    public void onDestroy() {
        Log.d("service", "service stop()");
        if (mWakeLock != null) {
            if (mWakeLock.isHeld())
                mWakeLock.release();
            mWakeLock = null;
        }

        stopForeground(true);
        thread.mystop();
        MyApplication app = (MyApplication) getApplication();

        app.setServiceRun(false);
        boolean temp = getSharedPreferences("conf", MODE_PRIVATE).getBoolean("switch_on", false);
        if (temp) {
            Log.d("restart", "auto restart");
            Intent intent = new Intent("cn.ikaze.pedometer.start");
            sendBroadcast(intent);
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    synchronized private PowerManager.WakeLock mWakeLock(Context context) {
        if (mWakeLock != null) {
            if (mWakeLock.isHeld())
                mWakeLock.release();
            mWakeLock = null;
        }

        if (mWakeLock == null) {
            PowerManager mgr = (PowerManager) context
                    .getSystemService(Context.POWER_SERVICE);
            mWakeLock = mgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    StepService.class.getName());
            mWakeLock.setReferenceCounted(true);
            mWakeLock.acquire();
        }
        return (mWakeLock);
    }


}
