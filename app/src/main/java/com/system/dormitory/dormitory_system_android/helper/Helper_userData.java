package com.system.dormitory.dormitory_system_android.helper;

/**
 * Created by Administrator on 2016-05-18.
 */

        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.util.Log;

        import com.loopj.android.http.JsonHttpResponseHandler;
        import com.loopj.android.http.RequestParams;
        import com.system.dormitory.dormitory_system_android.activity_main.Activity_Main_Manager;
        import com.system.dormitory.dormitory_system_android.activity_main.Activity_Main_Student;

        import org.json.JSONException;
        import org.json.JSONObject;

        import cz.msebera.android.httpclient.Header;
/**
 * Created by Administrator on 2016-03-19.
 */

//TODO 유저정보 저장하는 클래스

public class Helper_userData {

    private static Helper_userData user;

    public String id;
    public String name;
    public String email;
    public String roomNumber;
    public int score;
    public int isStudent;

    public Helper_userData(String id, String name, String email, String roomNumber, int score, int isStudent) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.roomNumber = roomNumber;
        this.score = score;
        this.isStudent = isStudent;
    }
    public Helper_userData(){

    }
    public static Helper_userData getInstance(Context mContext) {
        return user;
    }


    public static void login_GetData(String id, final Context mContext) {
        if( user == null ) {
            final RequestParams idParams = new RequestParams("id", id);

            Helper_server.post("getProfile_Id.php", idParams, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.i("myself", "success");
                    String id;
                    String name;
                    String email;
                    String roomNumber;
                    int score;
                    int isStudent;

                    try {
                        id = isNull_String(response.get("id"));
                        name = isNull_String(response.get("name"));
                        email = isNull_String(response.get("email"));
                        roomNumber = isNull_String(response.get("roomNumber"));
                        score = isNull_Int(response.get("score"));
                        isStudent = isNull_Int(response.get("isStudent"));

                        Log.d("userData", id);
                        Log.d("userData", name);
                        Log.d("userData", email);

                        user = new Helper_userData(id, name, email, roomNumber, score, isStudent);

                        Intent intent;
                        if(isStudent == 0)
                            intent = new Intent(mContext, Activity_Main_Student.class);
                        else
                            intent = new Intent(mContext, Activity_Main_Manager.class);
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
                intent = new Intent(mContext, Activity_Main_Student.class);
            else
                intent = new Intent(mContext, Activity_Main_Manager.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }

    public static Helper_userData getInstance(){return user;}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getIsStudent() {
        return isStudent;
    }

    public void setIsStudent(int isStudent) {
        this.isStudent = isStudent;
    }

    public static String isNull_String(Object response){
        if(response == null | response.equals(null)) return "";
        else return response.toString().trim();
    }
    public static int isNull_Int(Object response){
        if(response == null || response.equals(null)) return -1;
        else return Integer.parseInt(response.toString().trim());
    }
    public static void setUserNull(){
        user = null;
    }

}
