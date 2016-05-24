package com.example.nike.diplom_v_11;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;
import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkView;
import org.xwalk.core.internal.XWalkCookieManager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Nike on 14.04.2016.
 */
public class Login extends Activity {
    String url;
    static String Role;
    @Override
    public void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login_activity);
        final Context context = this;
        Intent intent = getIntent();
        Role = intent.getStringExtra("Role");
        final EditText login = (EditText)findViewById(R.id.Login);
        final EditText pass = (EditText)findViewById(R.id.pass);
        if (Role.equals("Student")){
            url = "https://login.nstu.ru/ssoservice/json/authenticate?goto=https%3A%2F%2Fciu.nstu.ru%2Fstudent_study%2F";
        }else {
            url = "https://login.nstu.ru/ssoservice/json/authenticate?goto=https%3A%2F%2Fciu.nstu.ru%2Fuser_redirect%2F";
        }
        Button start_login = (Button)findViewById(R.id.button3);
        start_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyAsyncTask(context).execute(url,login.getText().toString(),pass.getText().toString());
            }
        });
    }

    public class MyAsyncTask extends AsyncTask<String,Void,String> {
        Context context;
        private MyAsyncTask(Context context){
            this.context = context.getApplicationContext();
        }
        @Override
        protected String doInBackground(String... params) {

            URL url;
            HttpURLConnection connection,connection1;
            try {
                //Create connection
                url = new URL(params[0]);
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("POST");


                //Get Response
                InputStream is = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer response = new StringBuffer();
                while((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                rd.close();
                String jsonstring= response.toString();
                JSONObject jsonObject = new JSONObject(jsonstring);

                String urlParameters = "{\"authId\":\""+jsonObject.getString("authId")+
                        "\",\"template\":\"\",\"stage\":\"JDBCExt1\",\"header\"" +
                        ":\"Авторизация\",\"callbacks\":" +
                        "[{\"type\":\"NameCallback\",\"output\":[{\"name\":\"prompt\",\"value\":\"Логин:\"}]" +
                        ",\"input\":[{\"name\":\"IDToken1\",\"value\":\""+params[1]+"\"}]},{\"type\":\"" +
                        "PasswordCallback\",\"output\":[{\"name\":\"prompt\",\"value\":\"Пароль:\"}]" +
                        ",\"input\":[{\"name\":\"IDToken2\",\"value\":\""+params[2]+"\"}]}]}";
                //Send request
                connection1 = (HttpURLConnection)url.openConnection();
                connection1.setRequestMethod("POST");
                connection1.setRequestProperty("Content-Type",
                        "application/json");


                connection1.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(connection1.getOutputStream ());
                wr.writeBytes (urlParameters);
                wr.flush ();
                wr.close ();

                //Get Response
                InputStream is1 = connection1.getInputStream();
                BufferedReader rd1 = new BufferedReader(new InputStreamReader(is1));
                String line1;
                StringBuffer response1 = new StringBuffer();
                while((line1 = rd1.readLine()) != null) {
                    response1.append(line1);
                    response1.append('\r');
                }
                rd.close();

                jsonstring= response1.toString();
                JSONObject jsonO = new JSONObject(jsonstring);
                String Myresult=jsonO.getString("tokenId");

                return Myresult;
            } catch (Exception e) {

                e.printStackTrace();

                return null;

            }

        }

        @Override
        protected void onPostExecute(String Myresult) {
            super.onPostExecute(Myresult);
            if(Myresult==null){
            } else {
                Intent start = new Intent(context,Programm.class);
                start.putExtra("Role",Login.Role);
                start.putExtra("cookie",Myresult);
                startActivity(start);
            }
        }
    }
}
