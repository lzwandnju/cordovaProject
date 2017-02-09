package org.apache.cordova.demo;


import android.widget.Toast;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;


public class Echo extends CordovaPlugin {
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("coolMethod1")) {
            Toast.makeText(this.cordova.getActivity(),"coolMethod1",Toast.LENGTH_SHORT).show();
            String message = args.getString(0);
            this.coolMethod1(message, callbackContext);
            return true;
        }else if(action.equals("coolMethod2")){
            Toast.makeText(this.cordova.getActivity(),"coolMethod2",Toast.LENGTH_SHORT).show();
            String message = args.getString(0);
            this.coolMethod2(message, callbackContext);
            return true;
        }else if(action.equals("coolMethod3")){
            Toast.makeText(this.cordova.getActivity(),"coolMethod3",Toast.LENGTH_SHORT).show();
            String message = args.getString(0);
            this.coolMethod3(message, callbackContext);
            return true;
        }
        return false;
    }


    private void coolMethod1(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
    private void coolMethod2(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
    private void coolMethod3(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}

