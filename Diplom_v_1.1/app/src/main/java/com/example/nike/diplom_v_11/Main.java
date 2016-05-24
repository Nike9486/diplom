package com.example.nike.diplom_v_11;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import org.htmlcleaner.TagNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Nike on 14.04.2016.
 */
public class Main extends Activity
{   String adress="https://ciu.nstu.ru/student_study/timetable/timetable_lessons";
    String cookie="";
    private ProgressDialog pd;
    CookieManager cookieManager;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.raspisanie);
        Intent intent = getIntent();
        cookie=intent.getStringExtra("cookie"); // Извлечеине строки Куки и затерание ;
        Log.d("TagMy",intent.getStringExtra("cookie"));
        Log.d("SuperTag",cookie.toString());
        cookieManager=CookieManager.getInstance();
        String[] parts = cookie.split(";");
        for (int i=0;i<2;i++){
            cookieManager.setCookie(adress,parts[i]);
        }


    }

    /*
    private View.OnClickListener myclick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("HTMLtagContent","I Start Make My click");
            pd=ProgressDialog.show(Main.this,"Working...","reqest to server",true,false);       //Диалог ожидания
            new MyAsync().execute(adress,cookie);       //Запуск асинхронного потока для открытия страницы
        }
    };

public class MyAsync extends AsyncTask<String,Void,List<String>>{
    @Override
    protected List<String> doInBackground(String... params) {
        List<String> output = new ArrayList<String>();
        try{
            HtmlHelper hh = new HtmlHelper(params[0],params[1]); //Создание объекта помошника для чтения html
            List<TagNode> tabels = hh.getTabelByClass("tdall2");    //Вызов метода возвращающего таблицу с расписанием
            for (Iterator<TagNode>iterator=tabels.iterator();iterator.hasNext();){
                TagNode divelem = (TagNode)iterator.next(); //Взятие следующего элемента из списка
                output.add(divelem.getText().toString());   //Вывод в выходной список

        }
        }catch (Exception e){
            e.printStackTrace();
        }
        return output;
    }
    @Override
    protected void onPostExecute(List<String> output) {
        pd.dismiss(); //Отключение диалогового окна
        ListView listView =(ListView)findViewById(R.id.txt); //
        listView.setAdapter(
                new ArrayAdapter<String>(
                        Main.this,android.R.layout.simple_list_item_1,output));    //Вывод Выходного списка в список
    }
}
*/
}
