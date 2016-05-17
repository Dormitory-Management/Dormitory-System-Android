package com.system.dormitory.dormitory_system_android.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.activiti_main.Activity_Main;
import com.system.dormitory.dormitory_system_android.helper.Helper_server;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.impl.cookie.BasicClientCookie;

/**
 * Created by Administrator on 2016-05-09.
 */
public class Activity_Login extends Activity {
    private String login_id = "";

    EditText et_id;
    EditText et_password;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        // activity_layout.xml을

        // 만듦
        et_id = (EditText)findViewById(R.id.et_login_id);
        et_password = (EditText)findViewById(R.id.et_login_password);

        AsyncHttpClient client = Helper_server.getInstance();
        final PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
        client.setCookieStore(myCookieStore);

        //자동 로그인 파트.
//        if (Helper_server.login(myCookieStore)) {
//
//            Log.i("abde", "what the!! ");
//            Intent intent = new Intent(Activity_Login.this, Activity_Main.class);
//
//            Helper_userData user = Helper_userData.getInstance();
//            user.getInstance("aaaaa");
//
//            startActivity(intent);
//            finish();
//        }

        et_password.setOnKeyListener(new View.OnKeyListener() {

                                         @Override
                                         public boolean onKey(View v, int keyCode, KeyEvent event) {
                                             //Enter key Action
                                             if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                                                 InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                                                 imm.hideSoftInputFromWindow(et_password.getWindowToken(), 0);    //hide keyboard
                                                 return true;
                                             }
                                             return false;
                                         }
                                     }

        );

        //login buton click
        Button btn_login = (Button) findViewById(R.id.btn_login_login);
        btn_login.setOnClickListener(new Button.OnClickListener()

                                     {
                                         public void onClick(View v) {
                                             RequestParams params = new RequestParams();
                                             final String id = et_id.getText().toString();
                                             String password = et_password.getText().toString();

                                             //put params
                                             params.put("id", id);
                                             params.put("password", password);
                                             //server connect
                                             Helper_server.post("login.php", params,  new JsonHttpResponseHandler() {
                                                 @Override

                                                 public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                                     Log.i("abde", "success");
                                                     String data="";
                                                     try{
                                                         data = response.get("ok").toString();
                                                     } catch(JSONException e){
                                                         e.printStackTrace();
                                                     }
                                                     Log.d("ok", "" + data);
                                                     if(data.equals("true")){

                                                         BasicClientCookie newCookie = new BasicClientCookie("login_cookie", id);
                                                         newCookie.setVersion(1);
                                                         newCookie.setDomain("4.199.191.229");
                                                         newCookie.setPath("/");
                                                         myCookieStore.addCookie(newCookie);

                                                         Intent intent = new Intent(Activity_Login.this, Activity_Main.class);

                                                         startActivity(intent);
                                                         finish();
                                                     }
                                                     else{
                                                         loginAlert();
                                                     }
                                                 }

                                                 @Override
                                                 public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                                     super.onFailure(statusCode, headers, responseString, throwable);
                                                     Log.d("Failed: ", ""+statusCode);
                                                     Log.d("Error : ", "" + throwable);
                                                 }
                                             });
                                         }


                                     }
        );

        Button btn_join = (Button) findViewById(R.id.btn_login_join);
        btn_join.setOnClickListener(new Button.OnClickListener(){
                                        public void onClick(View v) {

                                            Intent intent = new Intent(Activity_Login.this, Activity_Join.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                            startActivity(intent);

                                        }
                                    }

        );
    }//onCreateEnd

    public void loginAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(Activity_Login.this);
        alert.setTitle("로그인 실패");
        alert.setMessage("아이디 혹은 비밀번호가 확인해주세요 ");
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                et_id.setText(null);
                et_password.setText(null);
            }
        });
        alert.show();
    }

    public void onBackPressed(){
        finish();
    }


}