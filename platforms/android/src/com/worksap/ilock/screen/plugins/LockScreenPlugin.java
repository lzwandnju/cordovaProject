package com.worksap.ilock.screen.plugins;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.worksap.ilock.screen.activity.GestureEditActivity;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

/**
 * Created by li_zhe-pc on 2017/2/8.
 */

public class LockScreenPlugin extends CordovaPlugin{
    CallbackContext callbackContext;
    private String infos;
    //无参构造函数
    LockScreenPlugin(){}

    @Override
    public boolean execute(String action, org.json.JSONArray args,
                           CallbackContext callbackContext) throws org.json.JSONException{
        // TODO Auto-generated method stub
        //参数action:对应的js中的方法名,
        //参数agrs:对应js方法中传入的参数
        //参数callbackContext回调方法
        this.callbackContext = callbackContext;
        LogUtils.d(action);
        if (action.equals("intent")) {
            // 获取JS传递的args的第一个参数
            infos = args.getString(0);
            this.function();
            return true;
        }
        return true;
    }

    // 方法执行体
    private void function() {
        // cordova.getActivity() 获取当前activity的this
        Log.i("123", cordova.getActivity().toString());
        Intent intent = new Intent(cordova.getActivity(), GestureEditActivity.class);
        intent.putExtra("infos", infos);
        cordova.startActivityForResult((CordovaPlugin) this, intent, 200);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);
        // 传递返回值 给js方法
        callbackContext.success("Completed Successfully!");
        Toast.makeText(cordova.getActivity(), "恭喜,完成", Toast.LENGTH_LONG).show();
    }


    private void show(String text ,int type){
        if (1==type) {
            Toast.makeText(this.cordova.getActivity(), text, Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this.cordova.getActivity(), text, Toast.LENGTH_SHORT).show();
        }
    }
}
