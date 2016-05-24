package com.example.nike.diplom_v_11;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Nike on 18.04.2016.
 */
public class Role_Class extends Activity implements View.OnClickListener{
    Intent intent;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.role_actyvity);     //Запуск Activity выбора роли (Студент/Преподаватель)
        Button stud = (Button)findViewById(R.id.btn1);
        Button prep = (Button)findViewById(R.id.btn2);
        stud.setOnClickListener(this);
        prep.setOnClickListener(this);
        intent = new Intent(this, Login.class); //Запуск WebView для авторизации
    }
    @Override
    public void onClick(View v) {       //Метод для передачи нужной ссылки
        switch (v.getId()) {
            case R.id.btn1:
                intent.putExtra("Role", "Student");
                startActivity(intent);
                break;
            case R.id.btn2:
                intent.putExtra("Role","Teacher");
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
