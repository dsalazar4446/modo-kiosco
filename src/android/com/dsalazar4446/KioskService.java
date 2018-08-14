package com.dsalazar4446;

public class KioskService extends Service {

  private static final long INTERVAL = TimeUnit.SECONDS.toMillis(2); // periodic interval to check in seconds -> 2 seconds
  private static final String TAG = KioskService.class.getSimpleName();
  private static final String PREF_KIOSK_MODE = "pref_kiosk_mode";

  private Thread t = null;
  private Context ctx = null;
  private boolean running = false;

  @Override
  public void onDestroy() {
    Log.i(TAG, "Stopping service 'KioskService'");
    running =false;
    super.onDestroy();
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    Log.i(TAG, "Starting service 'KioskService'");
    running = true;
    ctx = this;
    
    // start a thread that periodically checks if your app is in the foreground
    t = new Thread(new Runnable() {
      @Override
      public void run() {
        do {
          handleKioskMode();
          try {
            Thread.sleep(INTERVAL);
          } catch (InterruptedException e) {
            Log.i(TAG, "Thread interrupted: 'KioskService'");
          }
        }while(running);
        stopSelf();
      }
    });

    t.start();
    return Service.START_NOT_STICKY;
  }

  private void handleKioskMode() {
    // is Kiosk Mode active? 
	  if(isKioskModeActive()) {
	    // is App in background?
      if(isInBackground()) {
        restoreApp(); // restore!
      }
    }
  }

  private boolean isInBackground() {
    ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
	
    List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
    ComponentName componentInfo = taskInfo.get(0).topActivity;
    return (!ctx.getApplicationContext().getPackageName().equals(componentInfo.getPackageName()));
  }

  private void restoreApp() {
    // Restart activity
    Intent i = new Intent(ctx, MyActivity.class);
    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    ctx.startActivity(i);
  }
  
  public boolean isKioskModeActive(final Context context) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    return sp.getBoolean(PREF_KIOSK_MODE, false);
  }

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }
}