package com.dsalazar4446;

public class BootReceiver extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
    Intent myIntent = new Intent(context, MyKioskModeActivity.class);
    myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(myIntent);
  }
}