package com.example.ttucse.lab4;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    private static  final String MAP_URL = "file:///android_asset/Map.html";
    private WebView webView;
    LocationManager locationManager;
    LocationListener locationListener;
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setWebView();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        locationListener =new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if(location != null){
                    Log.i("SuperMap","LocationChange : lat"
                            + location.getAltitude() + "Lng" +
                    location.getLongitude());

                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

    }
    private  void setWebView(){
        webView = (WebView) findViewById(R.id.webview);
        webView.addJavascriptInterface(new JavaScriptInterface(this),"Android");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(MAP_URL);
    }
    public class JavaScriptInterface{
        Context mContext;
        JavaScriptInterface(Context c){
          mContext = c;
        }
        @JavascriptInterface
        public float getLatitude(){
            if(ActivityCompat.checkSelfPermission(this.mContext, Manifest.permission.ACCESS_FINE_LOCATION) != 0){
                return 0;
            }
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            return (float)location.getLatitude();
        }
        @JavascriptInterface
        public float getLongitude(){
            if(ActivityCompat.checkSelfPermission(this.mContext, Manifest.permission.ACCESS_FINE_LOCATION) != 0){
                return 0;
            }
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            return (float)location.getLongitude();
        }


    }
}
