package com.example.nike.diplom_v_11;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.TextView;

import org.htmlcleaner.TagNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nike on 23.05.2016.
 */
public class Students extends Activity  {

        static String adress = "https://ciu.nstu.ru/isu/study/students_groups";
        private ProgressDialog pd;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            final Context context = this;

            super.onCreate(savedInstanceState);
            setContentView(R.layout.raspisanie);
            final Intent geting_intent = getIntent();

            pd = ProgressDialog.show(Students.this, "Working...", "reqest to server", true, false);       //Диалог ожидания
            new MyAsync(context).execute(adress, geting_intent.getStringExtra("cookie"));
        }

        public class MyAsync extends AsyncTask<String, Void,Spisok> {
            Context context;
            private MyAsync(Context context){
                this.context = context.getApplicationContext();
            }
            @Override
            protected Spisok doInBackground(String... params) {
                Spisok spisok=null;
                try {
                    HtmlHelper hh = new HtmlHelper(params[0], params[1]); //Создание объекта помошника для чтения html
                    List<TagNode> tabels_site = hh.getEementById("s_faculty");
                    for (int i = 0; i < tabels_site.size(); i++) {
                        spisok=new Spisok(tabels_site.get(0));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return spisok;
            }

            @Override
            protected void onPostExecute(Spisok spisok) {
                pd.dismiss(); //Отключение диалогового окна

                    GridLayout layout = (GridLayout) findViewById(R.id.my_layout);




                        TextView txt = new TextView(context);
                        txt.setEms(35);
                        txt.setText(spisok.get());
                        layout.addView(txt);

            }


        }


}
