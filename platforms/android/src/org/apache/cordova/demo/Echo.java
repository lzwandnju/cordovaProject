package org.apache.cordova.demo;


import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.worksap.ilock.screen.activity.GestureEditActivity;
import com.worksap.ilock.screen.activity.GestureVerifyActivity;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;


public class Echo extends CordovaPlugin {
    public Echo(){}
    CallbackContext callbackContext;
    private String infos="";
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        this.callbackContext = callbackContext;
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
        infos = message;
        callbackContext.success(message);
        this.startEditIntent();
        return ;
//        if (message != null && message.length() > 0) {
//            callbackContext.success(message);
//        } else {
//            callbackContext.error("Expected one non-empty string argument.");
//        }
    }
    private void coolMethod3(String message, CallbackContext callbackContext) {
        infos = message;
        callbackContext.success(message);
        this.startVerifyIntent();
        return ;
//        if (message != null && message.length() > 0) {
//            callbackContext.success(message);
//        } else {
//            callbackContext.error("Expected one non-empty string argument.");
//        }
    }
    private void startEditIntent(){
        // cordova.getActivity() 获取当前activity的this
        Log.i("123", cordova.getActivity().toString());
        Intent intent = new Intent(cordova.getActivity(), GestureEditActivity.class);
        intent.putExtra("infos", infos);
        cordova.startActivityForResult((CordovaPlugin) this, intent, 200);
    }
    private void startVerifyIntent(){
        // cordova.getActivity() 获取当前activity的this
        Log.i("123", cordova.getActivity().toString());
        Intent intent = new Intent(cordova.getActivity(), GestureVerifyActivity.class);
        intent.putExtra("infos", infos);
        cordova.startActivityForResult((CordovaPlugin) this, intent, 200);
    }
}

