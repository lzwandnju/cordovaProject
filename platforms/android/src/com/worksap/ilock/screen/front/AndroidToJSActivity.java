package com.worksap.ilock.screen.front;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.worksap.ilock.screen.BaseActivity;

import org.json.JSONObject;

import java.util.HashMap;

public class AndroidToJSActivity extends BaseActivity {
	
	private WebView mWebView;
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mWebView = new WebView(this);
		setContentView(mWebView);
		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		
		/**
		 * 第一种方式WebView方法本地方法
		 * 原理：不多解释，直接构造本地化对象，映射到js页面中访问，底层原理借助了V8引擎
		 * 缺点：4.2以下系统有WebView漏洞
		 */
		mWebView.addJavascriptInterface(new JSObject(), "myObj");  
		
		/**
		 * 第二种方式WebView访问本地方法
		 * 原理：借助shouldOverrideUrlLoading回调，拦截请求url,分析url格式得到具体参数
		 * 缺点：协议必须约束好，参数名称也必须约束，而且这种方式无法直接返回值，需要再次调用页面js方法得到返回值
		 */
		mWebView.setWebViewClient(new WebViewClient(){

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if(url.startsWith("http")||url.startsWith("https")){
					return super.shouldOverrideUrlLoading(view, url);
				}else{
					Log.i("jw", "url:"+url);
					//协议url:"js://demo?arg1=111"
					try {
						//只处理指定协议
						String protocol = getUrlScheme(url);
						if("baidumobilegamejsload".equals(protocol)){
							HashMap<String,String> map = getUrlParams(url);
							String faction = map.get("faction");//获取指定key的参数值，调用本地方法
							String res = getPwd("js调用本地方法通过拦截shouldOverrideUrlLoading以后的结果");
							if(faction.equals("show")){
								showToast("js调用本地方法通过拦截shouldOverrideUrlLoading");
							}
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("name", "zhangsan");
							jsonObject.put("age", "19");
							jsonObject.put("email", "zhangsan@qq.com");
							jsonObject.put("pwd", res);
							mWebView.loadUrl("javascript:clicktworesult("+jsonObject.toString()+")");
						}
					} catch (Exception e) {
						Log.i("jw", "error:"+ Log.getStackTraceString(e));
					}
					
					return true;
				}
				
			}

		});
		
		
		/**
		 * 第三种方式WebView方法本地方法
		 * 原理：借助WebChromeClient的回调方法，可以拦截JS中的三个方法：alert,confirm,prompt，解析参数，得到指定格式数据
		 * 缺点：需要页面和本地解析格式做一个约束
		 */
		mWebView.setWebChromeClient(new WebChromeClient(){

			//拦截JS的alert方法
			@Override
			public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
				return super.onJsAlert(view, url, message, result);
			}

			//拦截JS的confirm方法
			@Override
			public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
				return super.onJsConfirm(view, url, message, result);
			}

			//拦截JS的prompt方法
			@Override
			public boolean onJsPrompt(WebView view, String url, String message, String defaultValue,
									  JsPromptResult result) {
				Log.i("jw", "url:"+url+",message:"+message);
				//协议url:"js://demo?arg1=111"
				try {
					//只处理指定协议
					String protocol = getUrlScheme(message);
					if("js".equals(protocol)){
						HashMap<String,String> map = getUrlParams(message);
						String arg1 = map.get("faction");//获取到指定key的参数值，调用本地方法
						if(arg1.equals("show")){
							showToast("通过拦截onJsPrompt");
						}
						String res = getPwd(arg1);
						result.confirm(res);//返回值
					}
				} catch (Exception e) {
					Log.i("jw", "error:"+ Log.getStackTraceString(e));
				}
				
				return true;
			}
			
		});
		
		//访问asset目录中的页面
		mWebView.loadUrl("file:///android_asset/js_demo.html");
		
	}
	
	/**
	 * 本地化JS对象
	 * @author lzw
	 *
	 */
	class JSObject {
		@JavascriptInterface //sdk17版本以上加上注解
		public String getPwd(String txt){
			Log.i("jw", "get pwd...");
			return "123456";
		}
	}
	
	public String getPwd(String txt){
		Log.i("jw", "get pwd...");
		return txt+ "123456";
	}
	
	/**
	 * 获取链接中的参数
	 * @param url
	 * @return
	 */
	private HashMap<String, String> getUrlParams(String url){
		int index = url.indexOf("?");
		String argStr = url.substring(index+1);
		String[] argAry = argStr.split("&");
		HashMap<String,String> argMap = new HashMap<String,String>(argAry.length);
		for(String arg : argAry){
			System.out.println("arg:"+arg);
			String[] argAryT = arg.split("=");
			argMap.put(argAryT[0], argAryT[1]);
		}
		return argMap;
	}
	
	/**
	 * 获取链接中的协议
	 * @param url
	 * @return
	 */
	private String getUrlScheme(String url){
		int index = url.indexOf(":");
		return url.substring(0, index);
	}
	
	
	public  void showToast(String s){
		Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
	}
}
