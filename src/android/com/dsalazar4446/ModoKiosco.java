/**
 */
package com.dsalazar4446;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

import java.util.Date;

public class ModoKiosco extends CordovaPlugin {
  private static final String TAG = "ModoKiosco";

  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);

    Log.d(TAG, "Inicializando ModoKiosco");
  }

  public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
    if(action.equals("mensaje")) {
      // An example of returning data back to the web layer
       String phrase = args.getString(0);
      // Echo back the first argument      
      final PluginResult result = new PluginResult(PluginResult.Status.OK, ""+phrase);
      callbackContext.sendPluginResult(result);
    }
    return true;
  }

  @Override
  public void onBackPressed() {
    // nothing to do here
    // … really      
  }
  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    if(!hasFocus) {
      // Close every kind of system dialog
      Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
      sendBroadcast(closeDialog);
    }
  }
  private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP));

  @Override
  public boolean dispatchKeyEvent(KeyEvent event) {
    if (blockedKeys.contains(event.getKeyCode())) {
      return true;
    } else {
      return super.dispatchKeyEvent(event);
    }
  }
}
