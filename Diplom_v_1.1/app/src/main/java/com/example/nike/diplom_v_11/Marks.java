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
 * Created by Nike on 21.05.2016.
 */
public class Marks extends Activity {
    final Context context = this;
    static String adress = "https://ciu.nstu.ru/student_study/student_info/progress";
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.raspisanie);
        final Intent geting_intent = getIntent();

        pd = ProgressDialog.show(Marks.this, "Working...", "reqest to server", true, false);       //Диалог ожидания
        new MyAsync(context).execute(adress, geting_intent.getStringExtra("cookie"));
    }

    public class MyAsync extends AsyncTask<String, Void, List<Tabel>> {

        Context context;
        private MyAsync(Context context){
            this.context = context.getApplicationContext();
        }

        @Override
        protected List<Tabel> doInBackground(String... params) {
            List<Tabel> tabels = new ArrayList<>();
            try {
                HtmlHelper hh = new HtmlHelper(params[0], params[1]); //Создание объекта помошника для чтения html
                List<TagNode> tabels_site = hh.getTabelByClass("tdall");
                for (int i = 0; i < tabels_site.size(); i++) {
                    tabels.add(new Tabel(tabels_site.get(i), 0));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return tabels;
        }

        @Override
        protected void onPostExecute(List<Tabel> tabel) {
            pd.dismiss(); //Отключение диалогового окна
            for (int i = 1; i < tabel.size(); i++) {
                              rec(tabel.get(i));
            }
        }


    }
    public void rec(Tabel tabel) {
        GridLayout layout = (GridLayout) findViewById(R.id.my_layout);
        layout.setColumnCount(tabel.Max_Sise);
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