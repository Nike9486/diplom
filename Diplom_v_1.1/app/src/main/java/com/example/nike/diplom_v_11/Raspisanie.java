package com.example.nike.diplom_v_11;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ArrayAdapter;

import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.htmlcleaner.TagNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Nike on 21.04.2016.
 */
public class Raspisanie extends Activity {
    static String adress = "https://ciu.nstu.ru/student_study/timetable/timetable_lessons";
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.raspisanie);
        final Intent geting_intent = getIntent();

        pd = ProgressDialog.show(Raspisanie.this, "Working...", "reqest to server", true, false);       //Диалог ожидания
        new MyAsync().execute(adress, geting_intent.getStringExtra("cookie"));
    }

    public class MyAsync extends AsyncTask<String, Void, Tabel> {
        @Override
        protected Tabel doInBackground(String... params) {
            Tabel tabel = null;
            try {
                HtmlHelper hh = new HtmlHelper(params[0], params[1]); //Создание объекта помошника для чтения html
                List<TagNode> tabels = hh.getTabelByClass("tdall2");
                //Вызов метода возвращающего таблицу с расписанием
                tabel = new Tabel(tabels.get(0), 3);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return tabel;
        }

        @Override
        protected void onPostExecute(Tabel tabel) {
            pd.dismiss(); //Отключение диалогового окна
            rec(tabel);
        }
    }
    public void rec(Tabel tabel) {
        GridLayout layout = (GridLayout) findViewById(R.id.my_layout);
        layout.setColumnCount(tabel.Max_Sise);
        tabel.makeForTimeTabel();
        tabel.makenormal();
        layout.setHorizontalScrollBarEnabled(false);
        layout.setUseDefaultMargins(false);
        layout.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        layout.setRowOrderPreserved(false);
        for (int i = 0; i < tabel.Rows.size(); i++)
            for (int j = 0; j < tabel.Rows.get(i).size(); j++) {
                TextView txt = new TextView(this);
                txt.setEms(15);
                txt.setBackgroundResource(R.drawable.border);
                txt.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                txt.setText(tabel.Rows.get(i).get(j).toString());
                layout.addView(txt);
            }
    }
}
