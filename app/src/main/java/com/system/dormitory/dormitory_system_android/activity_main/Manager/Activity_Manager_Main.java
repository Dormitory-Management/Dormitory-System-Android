package com.system.dormitory.dormitory_system_android.activity_main.Manager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.adapter.ViewPagerAdapter;
import com.system.dormitory.dormitory_system_android.data.BoardItem;
import com.system.dormitory.dormitory_system_android.data.DataManager;
import com.system.dormitory.dormitory_system_android.data.NoticeItem;
import com.system.dormitory.dormitory_system_android.data.QuestionItem;
import com.system.dormitory.dormitory_system_android.data.TodayOutSleepData;
import com.system.dormitory.dormitory_system_android.helper.Helper_server;
import com.system.dormitory.dormitory_system_android.login.Activity_Login;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import com.ibm.icu.text.SimpleDateFormat;

import cz.msebera.android.httpclient.Header;

public class Activity_Manager_Main extends AppCompatActivity implements ActionBar.TabListener {
    private ListView lvNavList;
    private DrawerLayout dlDrawer;
    private ActionBarDrawerToggle dtToggle;
    private ViewPager viewPager;
    private AQuery aq;
    private DataManager dataManager;
    private String[] navItems = {"대여승인", "외박승인", "점호확인", "로그아웃"};
    private ActionBar actionBar;
    private SimpleDateFormat simpleDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Activity_Login login = (Activity_Login) Activity_Login.login_Activity; //login_Activity_finish
        if (login != null) login.finish();

        aq = new AQuery(this);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new ViewPagerAdapter(getApplicationContext()));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }
        });

        lvNavList = (ListView) findViewById(R.id.lv_activity_main_nav_list);

        lvNavList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, navItems));
        lvNavList.setOnItemClickListener(new DrawerItemClickListener());

        dlDrawer = (DrawerLayout) findViewById(R.id.dl_activity_main_drawer);
        dtToggle = new ActionBarDrawerToggle(this, dlDrawer, R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        dlDrawer.setDrawerListener(dtToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.addTab(actionBar.newTab().setText(R.string.first).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.second).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.third).setTabListener(this));
        //init -> onresume
    }

    public void init() {
        dataManager = DataManager.getInstance();
        dataManager.DataClear();
        simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.KOREAN);

        RequestParams params = new RequestParams();
        params.add("id", "123");
        Helper_server.post("data/getBoardAndNoticeAndQuestion.php", params, new JsonHttpResponseHandler() {

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int notice_sum = Integer.parseInt(response.get("notice_sum").toString());
                    for (int i = 0; i < notice_sum; i++) {
                        Log.i("notice_time", response.get("notice_time" + i).toString().trim());
                        dataManager.getNoticeItems().add(new NoticeItem(Integer.parseInt(response.get("notice_number" + i).toString()), response.get("notice_title" + i).toString(),
                                response.get("notice_content" + i).toString(), "사감",
                                response.get("notice_time" + i).toString().substring(5, 16)));
                    }
                    int board_sum = Integer.parseInt(response.get("board_sum").toString());
                    for (int i = 0; i < board_sum; i++) {
                        dataManager.getBoardItems().add(new BoardItem(Integer.parseInt(response.get("board_number" + i).toString()), response.get("board_title" + i).toString(),
                                response.get("board_content" + i).toString(), Integer.parseInt(response.get("board_sno" + i).toString()),
                                response.get("board_time" + i).toString().substring(5, 16)));
                    }
                    int question_sum = Integer.parseInt(response.get("question_sum").toString());
                    for (int i = 0; i < question_sum; i++) {
                        dataManager.getQuestionItems().add(new QuestionItem(Integer.parseInt(response.get("question_number" + i).toString()), response.get("question_title" + i).toString(),
                                response.get("question_content" + i).toString(), Integer.parseInt(response.get("question_sno" + i).toString()),
                                response.get("question_time" + i).toString().substring(5,16), response.get("question_answer" + i).toString(),
                                response.get("question_answerTime" + i).toString()));
                    }
                    viewPager.getAdapter().notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
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



    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
//        Toast.makeText(this, tab.getText() + "선택됨", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
//        Toast.makeText(this, tab.getText() + "선택됨", Toast.LENGTH_SHORT).show();
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        dtToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        dtToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (dtToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    Button.OnClickListener changePage = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            switch (view.getId()) {
//                case R.id.notice_layout:
//                    viewPager.setCurrentItem(0);
//                    break;
//                case R.id.point_layout:
//                    viewPager.setCurrentItem(1);
//                    break;
//                case R.id.board_layout:
//                    viewPager.setCurrentItem(2);
//                    break;
//            }
//        }
//    };

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
            Intent intent;
            switch (position) {
                case 0:
                    Toast.makeText(Activity_Manager_Main.this, "대여승인", Toast.LENGTH_SHORT).show();
                    intent = new Intent(Activity_Manager_Main.this, Activity_Manager_Rental.class);
                    startActivity(intent);
                    break;
                case 1:
                    Toast.makeText(Activity_Manager_Main.this, "외박승인", Toast.LENGTH_SHORT).show();
                    intent = new Intent(Activity_Manager_Main.this, Activity_Manager_outSleep.class);
                    startActivity(intent);
                    break;
                case 2:
                    Toast.makeText(Activity_Manager_Main.this, "점호확인", Toast.LENGTH_SHORT).show();
                    intent = new Intent(getApplicationContext(), RoomCheck.class);
                    startActivity(intent);
                    break;
                case 3:
                    Toast.makeText(Activity_Manager_Main.this, "로그아웃", Toast.LENGTH_SHORT).show();
                    new AlertDialog.Builder(Activity_Manager_Main.this)
                            .setTitle("종료")
                            .setMessage("종료 하시겠어요?")
                            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    final PersistentCookieStore myCookieStore = new PersistentCookieStore(Activity_Manager_Main.this); //이부분 Context 확인해야함. Activity context로.
                                    AsyncHttpClient client = Helper_server.getInstance();

                                    Helper_server.logout(myCookieStore, Activity_Manager_Main.this);
                                    client.setCookieStore(myCookieStore);
                                    Intent intents = new Intent(Activity_Manager_Main.this, Activity_Login.class);
                                    startActivity(intents);
                                    finish();
                                }
                            })
                            .setNegativeButton("아니오", null).show();
                    break;
            }
            dlDrawer.closeDrawer(lvNavList);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                new AlertDialog.Builder(this)
                        .setTitle("종료")
                        .setMessage("종료 하시겠어요?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                finish();
                            }
                        })
                        .setNegativeButton("아니오", null).show();
                return false;
            default:
                return false;
        }
    }

    public void onResume() {
        super.onResume();  // Always call the superclass method first

        init();
    }
}