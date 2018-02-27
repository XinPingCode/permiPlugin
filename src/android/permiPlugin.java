package permiPlugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

// import org.json.JSONArray;
// import org.json.JSONException;
import org.json.JSONObject;

import android.content.pm.PackageManager;
import android.Manifest;
import android.os.Build;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PermissionHelper;
import org.apache.cordova.PluginResult;
import org.apache.cordova.LOG;
import org.json.JSONArray;
import org.json.JSONException;

import javax.security.auth.callback.Callback;

/**
 * This class echoes a string called from JavaScript.
 */
public class permiPlugin extends CordovaPlugin {

    String TAG = "GeolocationPlugin";
    CallbackContext context;
    //String[] permissions = { Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION };
    String[] permissions = { Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE };
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        LOG.d(TAG, "We are entering execute");
        context = callbackContext;
        if (action.equals("getPermission")) {
            if (hasPermisssion()) {
                PluginResult r = new PluginResult(PluginResult.Status.OK);
                context.sendPluginResult(r);
                return true;
            } else {
                PermissionHelper.requestPermissions(this, 0, permissions);
            }
            return true;
        }
        return false;
    }

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults)
            throws JSONException {
        PluginResult result;
        //This is important if we're using Cordova without using Cordova, but we have the geolocation plugin installed
        if (context != null) {
            for (int r : grantResults) {
                if (r == PackageManager.PERMISSION_DENIED) {
                    LOG.d(TAG, "Permission Denied!");
                    result = new PluginResult(PluginResult.Status.ILLEGAL_ACCESS_EXCEPTION);
                    context.sendPluginResult(result);
                    return;
                }

            }
            result = new PluginResult(PluginResult.Status.OK);
            context.sendPluginResult(result);
        }
    }

    public boolean hasPermisssion() {
        for (String p : permissions) {
            if (!PermissionHelper.hasPermission(this, p)) {
                return false;
            }
        }
        return true;
    }

    /*
    * We override this so that we can access the permissions variable, which no longer exists in
    * the parent class, since we can't initialize it reliably in the constructor!
    */

    public void requestPermissions(int requestCode) {
        PermissionHelper.requestPermissions(this, requestCode, permissions);
    }

}
