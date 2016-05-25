package com.example.nike.diplom_v_11;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkView;
import org.xwalk.core.internal.XWalkCookieManager;

/**
 * Created by Nike on 24.05.2016.
 */
public class Login_By_WebView extends Activity {
        XWalkView xWalkView;
        XWalkCookieManager xWalkCookieManager;
        String url="https://ciu.nstu.ru/student_study/";
        Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);
        xWalkView= (XWalkView) findViewById(R.id.web);      //Взятие элемента WebView
        xWalkCookieManager=new XWalkCookieManager();    //Создание CookieManager для WebView
        intent=new Intent(this,Programm.class);
        xWalkView.setResourceClient(new MyResourceClient(xWalkView));


        xWalkView.load(url, null);
    }

    public class MyResourceClient extends XWalkResourceClient{


        public MyResourceClient(XWalkView view) {
            super(view);
        }

        @Override
        public void onLoadFinished(XWalkView view, String url) {
            super.onLoadFinished(view, url);
            if (xWalkCookieManager!=null){
                intent.putExtra("cookies",xWalkCookieManager.getCookie(url));
                startActivity(intent);
            }
        }
    }
}
