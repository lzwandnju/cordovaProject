package com.worksap.ilock.screen.front;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.alibaba.fastjson.JSON;
import com.worksap.ilock.screen.R;
import com.worksap.ilock.screen.activity.GestureEditActivity;
import com.worksap.ilock.screen.activity.GestureVerifyActivity;
import com.worksap.ilock.screen.utils.RSAUtils;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private WebView contentWebView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        contentWebView = (WebView) findViewById(R.id.webview);
        // 启用javascript
        contentWebView.getSettings().setJavaScriptEnabled(true);
        // 从assets目录下面的加载html
        contentWebView.loadUrl("file:///android_asset/web.html");
//        mWebView.loadUrl("file:///android_asset/js_demo.html");
        contentWebView.addJavascriptInterface(MainActivity.this,"android");


        //无参调用Js点击
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 无参数调用
                contentWebView.loadUrl("javascript:javacalljs()");

            }
        });
        //有参调用Js点击
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 传递参数调用
                contentWebView.loadUrl("javascript:javacalljswith(" + "'http://blog.csdn.net/Leejizhou'" + ")");
            }
        });


    }

    //由于安全原因 需要加 @JavascriptInterface
    @JavascriptInterface
    public void startFunction(){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(MainActivity.this,"show",Toast.LENGTH_LONG).show();

//               MainActivity.this.finish();
                try {
                    testRSA();
                }catch(Exception e){
                    e.printStackTrace();
                }
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, GestureEditActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
    }

    public void testRSA() throws Exception {
        List<Person> personList=new ArrayList<Person>();
        int testMaxCount=10;//测试的最大数据条数
        //添加测试数据
        for(int i=0;i<testMaxCount;i++){
            Person person =new Person();
            person.setAge(i);
            person.setName(String.valueOf(i));
            personList.add(person);
        }
        //FastJson生成json数据

//        String jsonData=JsonUtils.objectToJsonForFastJson(personList);
        // 对象转成string字符串出现异常，寻找其他的方式进行处理。利用其他的第三方库进行处理。
        String jsonData= (String) JSON.toJSON(personList);
        Log.e("MainActivity","加密前json数据 ---->"+jsonData);
        Log.e("MainActivity","加密前json数据长度 ---->"+jsonData.length());

        KeyPair keyPair= RSAUtils.generateRSAKeyPair(RSAUtils.DEFAULT_KEY_SIZE);
        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        //公钥加密
        long start= System.currentTimeMillis();
        byte[] encryptBytes = RSAUtils.encryptByPublicKeyForSpilt(jsonData.getBytes(), publicKey.getEncoded());
        long end= System.currentTimeMillis();
        Log.e("MainActivity","公钥加密耗时 cost time---->"+(end-start));
        String encryStr= Base64.encodeToString(encryptBytes,0);
//        Base64Encoder.encode(encryptBytes);
        Log.e("MainActivity","加密后json数据 --1-->"+encryStr);
        Log.e("MainActivity","加密后json数据长度 --1-->"+encryStr.length());
        //私钥解密
        start= System.currentTimeMillis();
//        byte[] decryptBytes=  RSAUtils.decryptByPrivateKeyForSpilt(Base64Decoder.decodeToBytes(encryStr),privateKey.getEncoded());
        byte[] decryptBytes=  RSAUtils.decryptByPrivateKeyForSpilt(Base64.decode(encryStr,0),privateKey.getEncoded());
        String decryStr=new String(decryptBytes);
        end= System.currentTimeMillis();
        Log.e("MainActivity","私钥解密耗时 cost time---->"+(end-start));
        Log.e("MainActivity","解密后json数据 --1-->"+decryStr);

        //私钥加密
        start= System.currentTimeMillis();
        encryptBytes=    RSAUtils.encryptByPrivateKeyForSpilt(jsonData.getBytes(),privateKey.getEncoded());
        end= System.currentTimeMillis();
        Log.e("MainActivity","私钥加密密耗时 cost time---->"+(end-start));
//        encryStr=Base64Encoder.encode(encryptBytes);
        encryStr=new String(Base64.encode(encryptBytes,0));
        Log.e("MainActivity","加密后json数据 --2-->"+encryStr);
        Log.e("MainActivity","加密后json数据长度 --2-->"+encryStr.length());
        //公钥解密
        start= System.currentTimeMillis();
 //       decryptBytes=  RSAUtils.decryptByPublicKeyForSpilt(Base64Decoder.decodeToBytes(encryStr),publicKey.getEncoded());
        decryptBytes=  RSAUtils.decryptByPublicKeyForSpilt(Base64.decode(encryStr,0),publicKey.getEncoded());
        decryStr=new String(decryptBytes);
        end= System.currentTimeMillis();
        Log.e("MainActivity","公钥解密耗时 cost time---->"+(end-start));
        Log.e("MainActivity","解密后json数据 --2-->"+decryStr);




    }

    @JavascriptInterface
    public void startFunction(final String text){
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
//                new AlertDialog.Builder(MainActivity.this).setMessage(text).show();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, GestureVerifyActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
        }
        });


    }
}
