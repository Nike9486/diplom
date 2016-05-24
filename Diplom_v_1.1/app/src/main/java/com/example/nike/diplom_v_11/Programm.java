package com.example.nike.diplom_v_11;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Nike on 20.04.2016.
 */
public class Programm extends Activity{
    ListView listView;
    Intent intent,intent2,intent3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.programm_ativity);
        final Intent geting_intent = getIntent();
        String[] menu;
        if(geting_intent.getStringExtra("Role").equals("Student")){
        menu = getResources().getStringArray(R.array.menu);
            intent = new Intent(this,Raspisanie.class);
            intent2= new Intent(this,Plan.class);
            intent3 = new Intent(this,Marks.class);
        }
        else{
         menu = getResources().getStringArray(R.array.menu2);
            intent = new Intent(this,Report.class);
            intent2= new Intent(this,Students.class);
            //intent3 = new Intent(this,Marks.class);
        }
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(this,R.layout.item,R.id.textView2,menu);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position){
                case 0:
                    intent.putExtra("cookie",geting_intent.getStringExtra("cookie"));
                    startActivity(intent);
                    break;
                case 1:
                    intent2.putExtra("cookie",geting_intent.getStringExtra("cookie"));
                    startActivity(intent2);
                    break;
                case 2:
                    intent3.putExtra("cookie",geting_intent.getStringExtra("cookie"));
                    startActivity(intent3);
                    break;
                default:
                    break;
            }
            }
        });
    }
}
