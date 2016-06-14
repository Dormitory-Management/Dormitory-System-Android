package com.system.dormitory.dormitory_system_android.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.gcm.QuickstartPreferences;
import com.system.dormitory.dormitory_system_android.gcm.RegistrationIntentService;
import com.system.dormitory.dormitory_system_android.helper.Helper_server;
import com.system.dormitory.dormitory_system_android.helper.Helper_userData;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.impl.cookie.BasicClientCookie;

/**
 * Created by Administrator on 2016-05-09.
 */
public class Activity_Login extends Activity {
    private String login_id = "";
    public static Activity login_Activity; //Aacitivity_login class선언.
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    EditText et_sno;
    EditText et_password;

    public void open_UserView_Activity(int sno, Context mContext) {
        getInstanceIdToken(sno);
        Helper_userData.login_GetData(sno, mContext);
    }

    //TODO GCM Client Key를 서버에 저장시킬것 DataManager.getGcmClientKey() 메소드를 사용하면 현재 Client에 GCM키를 가져올 수 있음
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        registBroadcastReceiver();
        TypefaceProvider.registerDefaultIconSets();
        // activity_layout.xml을
        login_Activity = Activity_Login.this;

        // 만듦
        et_sno = (EditText) findViewById(R.id.et_login_sno);
        et_password = (EditText) findViewById(R.id.et_login_password);

        AsyncHttpClient client = Helper_server.getInstance();
        final PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
        client.setCookieStore(myCookieStore);

        if (Helper_server.login(myCookieStore)) {
            Log.i("abde", "what the!! ");
            int sno = Integer.parseInt(Helper_server.getCookieValue(myCookieStore, "sno"));
            open_UserView_Activity(sno, getApplicationContext());
        }

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
                                             final int sno = Integer.parseInt(et_sno.getText().toString());
                                             String password = et_password.getText().toString();
                                             //put params
                                             params.put("sno", sno);
                                             params.put("password", password);
                                             //server connect
                                             Helper_server.post("login.php", params, new JsonHttpResponseHandler() {
                                                 @Override

                                                 public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                                     Log.i("abde", "success");
                                                     String data = "";
                                                     try {
                                                         data = response.get("phpsession").toString();
                                                     } catch (JSONException e) {
                                                         e.printStackTrace();
                                                     }
                                                     Log.d("phpsession", "" + data);
                                                     if (!data.equals("no")) {

                                                         BasicClientCookie newCookie = new BasicClientCookie("login_session", data);
                                                         newCookie.setVersion(1);
                                                         newCookie.setDomain("54.199.191.229");
                                                         newCookie.setPath("/");
                                                         myCookieStore.addCookie(newCookie);
                                                         newCookie = new BasicClientCookie("sno", "" + sno);
                                                         newCookie.setVersion(1);
                                                         newCookie.setDomain("54.199.191.229");
                                                         newCookie.setPath("/");
                                                         myCookieStore.addCookie(newCookie);
                                                         open_UserView_Activity(sno, getApplicationContext());
                                                     } else {
                                                         loginAlert();
                                                     }
                                                 }

                                                 @Override
                                                 public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                                     super.onFailure(statusCode, headers, responseString, throwable);
                                                     Log.d("Failed: ", "" + statusCode);
                                                     Log.d("Error : ", "" + throwable);
                                                 }
                                             });
                                         }


                                     }
        );

        Button btn_join = (Button) findViewById(R.id.btn_login_join);
        btn_join.setOnClickListener(new Button.OnClickListener() {
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
                et_sno.setText(null);
                et_password.setText(null);
            }
        });
        alert.show();
    }

    public void onBackPressed() {
        finish();
    }

    /**
     * Instance ID를 이용하여 디바이스 토큰을 가져오는 RegistrationIntentService를 실행한다.
     */
    public void getInstanceIdToken(int sno) {
        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            intent.putExtra("sno", sno);
            startService(intent);
        }
    }

//    /**
//     * 앱이 실행되어 화면에 나타날때 LocalBoardcastManager에 액션을 정의하여 등록한다.
//     */
//    @Override
//    protected void onResume() {
//        super.onResume();
//        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
//                new IntentFilter(QuickstartPreferences.REGISTRATION_READY));
//        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
//                new IntentFilter(QuickstartPreferences.REGISTRATION_GENERATING));
//        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
//                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
//
//    }
//
//    /**
//     * 앱이 화면에서 사라지면 등록된 LocalBoardcast를 모두 삭제한다.
//     */
//    @Override
//    protected void onPause() {
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
//        super.onPause();
//    }
//
//    public void registBroadcastReceiver(){
//        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                String action = intent.getAction();
//                if(action.equals(QuickstartPreferences.REGISTRATION_READY)){
//                    // 액션이 READY일 경우
////                    mRegistrationProgressBar.setVisibility(ProgressBar.GONE);
////                    mInformationTextView.setVisibility(View.GONE);
//                } else if(action.equals(QuickstartPreferences.REGISTRATION_GENERATING)){
//                    // 액션이 GENERATING일 경우
////                    mRegistrationProgressBar.setVisibility(ProgressBar.VISIBLE);
////                    mInformationTextView.setVisibility(View.VISIBLE);
////                    mInformationTextView.setText(getString(R.string.registering_message_generating));
//                } else if(action.equals(QuickstartPreferences.REGISTRATION_COMPLETE)){
//                    // 액션이 COMPLETE일 경우
////                    mRegistrationProgressBar.setVisibility(ProgressBar.GONE);
////                    mRegistrationButton.setText(getString(R.string.registering_message_complete));
////                    mRegistrationButton.setEnabled(false);
////                    String token = intent.getStringExtra("token");
////                    mInformationTextView.setText(token);
//                }
//
//            }
//        };
//    }

    /**
     * Google Play Service를 사용할 수 있는 환경이지를 체크한다.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, 9000).show();
            } else {
                Log.i("GCM", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
}