package com.system.dormitory.dormitory_system_android.helper;

/**
 * Created by Administrator on 2016-05-18.
 */

        import android.content.Context;
        import android.content.Intent;
        import android.util.Log;

        import com.loopj.android.http.AsyncHttpClient;
        import com.loopj.android.http.JsonHttpResponseHandler;
        import com.loopj.android.http.PersistentCookieStore;
        import com.loopj.android.http.RequestParams;
        import com.loopj.android.http.TextHttpResponseHandler;
        import com.system.dormitory.dormitory_system_android.activity_main.Manager.Activity_Manager_Main;
        import com.system.dormitory.dormitory_system_android.activity_main.Student.Activity_Student_Main;

        import org.json.JSONException;
        import org.json.JSONObject;

        import cz.msebera.android.httpclient.Header;
/**
 * Created by Administrator on 2016-03-19.
 */

//TODO 유저정보 저장하는 클래스

public class Helper_userData {

    private static Helper_userData user;

    public static int sno;
    public static String name;
    public static String email;
    public static String roomNumber;
    public static int score;
    public static int isStudent;
    private static String gcmClientKey;


    public Helper_userData(int sno, String name, String email, String roomNumber, int score, int isStudent) {
        this.sno = sno;
        this.name = name;
        this.email = email;
        this.roomNumber = roomNumber;
        this.score = score;
        this.isStudent = isStudent;
    }
    public Helper_userData(){

    }


    public static void login_GetData(int sno, final Context mContext) {
        user = null;
        if( user == null ) {
            final RequestParams idParams = new RequestParams("sno", sno);

            Helper_server.post("getProfile_Id.php", idParams, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.i("myself", "success");
                    int sno;
                    String name;
                    String email;
                    String roomNumber;
                    int score;
                    int isStudent;

                    try {
                        sno = isNull_Int(response.get("sno"));
                        name = isNull_String(response.get("name"));
                        email = isNull_String(response.get("email"));
                        roomNumber = isNull_String(response.get("roomNumber"));
                        score = isNull_Int(response.get("score"));
                        isStudent = isNull_Int(response.get("isStudent"));

                        Log.d("userData", ""+sno);
                        Log.d("userData", name);
                        Log.d("userData", email);

                        user = new Helper_userData(sno, name, email, roomNumber, score, isStudent);

                        Intent intent;
                        if(isStudent == 0)
                            intent = new Intent(mContext, Activity_Student_Main.class);
                        else
                            intent = new Intent(mContext, Activity_Manager_Main.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    Log.d("Failed: ", "myself " + statusCode);
                    Log.d("Error : ", "myself " + throwable);
                }
            });
        }
        else{
            Intent intent;
            if(Helper_userData.getInstance().getIsStudent() == 0)
                intent = new Intent(mContext, Activity_Student_Main.class);
            else
                intent = new Intent(mContext, Activity_Manager_Main.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }

    public static Helper_userData getInstance(){return user;}

    public static Helper_userData getInstance(Context mContext) {
        return user;
    }


    public int getSno() {
        return sno;
    }

    public static void setSno(int value) {
        sno = value;
    }

    public String getName() {
        return name;
    }

    public static void setName(String value) {
        name = value;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public static void setRoomNumber(String value) {
        roomNumber = value;
    }

    public String getEmail() {
        return email;
    }

    public static void setEmail(String value) {
        email = value;
    }
    public int getScore() {
        return score;
    }

    public static void setScore(int value) {
        score = value;
    }

    public int getIsStudent() {
        return isStudent;
    }

    public static void setIsStudent(int value) {
        isStudent = value;
    }

    public static String isNull_String(Object response){
        if(response == null | response.equals(null)) return "";
        else return response.toString().trim();
    }
    public static int isNull_Int(Object response){
        if(response == null || response.equals(null)) return -1;
        else return Integer.parseInt(response.toString().trim());
    }
    public static String getGcmClientKey() {
        return gcmClientKey;
    }

    public static void setGcmClientKey(String Key, int sno) {
        gcmClientKey = Key;
        System.out.println("aaaagcmClient"+gcmClientKey);

        RequestParams params = new RequestParams();
        //put params
        params.put("gcmKey", Key);
        params.put("sno", sno);

        System.out.println("aaaagcmClient" + sno);
        System.out.println("aaaagcmClient"+gcmClientKey);

        Helper_server.post("data/updateGcmKey.php", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i("GCMKEY CREATE", "FAIL");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                Log.i("GCMKEY CREATE", "SUCCESS");
            }
        });
    }


}
