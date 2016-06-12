package com.system.dormitory.dormitory_system_android.activity_main.Student;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.activity_main.Manager.RoomCheck;
import com.system.dormitory.dormitory_system_android.adapter.ViewPagerAdapter;
import com.system.dormitory.dormitory_system_android.data.BoardItem;
import com.system.dormitory.dormitory_system_android.data.DataManager;
import com.system.dormitory.dormitory_system_android.data.NoticeItem;
import com.system.dormitory.dormitory_system_android.helper.Helper_server;
import com.system.dormitory.dormitory_system_android.login.Activity_Login;

public class Activity_Student_Main extends AppCompatActivity implements ActionBar.TabListener {
    private ListView lvNavList;
    private DrawerLayout dlDrawer;
    private ActionBarDrawerToggle dtToggle;
    private ViewPager viewPager;
    private AQuery aq;
    private DataManager dataManager;
    private String[] navItems = {"대여", "외박", "벌점확인", "로그아웃"};
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Activity_Login login = (Activity_Login) Activity_Login.login_Activity; //login_Activity_finish
        login.finish();

        aq = new AQuery(this);

        init();
    }

    public void init() {
        dataManager = DataManager.getInstance();
        dataManager.DataClear();

        for (int i = 0; i < 20; i++) {
            dataManager.getBoardItems().add(new BoardItem("게시글 #" + (i + 1), "게시글 내용 #" + (i + 1), i + 100, "홍길동"));
            dataManager.getNoticeItems().add(new NoticeItem("공지사항 #" + (i + 1), "공지사항 내용 #" + (i + 1), "사감"));
        }

        Log.i("size", String.valueOf(dataManager.getBoardItems().size()));

//        aq.id(R.id.notice_layout).clicked(changePage);
//        aq.id(R.id.point_layout).clicked(changePage);
//        aq.id(R.id.board_layout).clicked(changePage);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new ViewPagerAdapter(getApplicationContext()));

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
                    Toast.makeText(Activity_Student_Main.this, "학생대여", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(Activity_Student_Main.this, "학생외박", Toast.LENGTH_SHORT).show();
                    intent = new Intent(Activity_Student_Main.this, Activity_Student_outSleep.class);
                    startActivity(intent);
                    finish();
                    break;
                case 2:
                    Toast.makeText(Activity_Student_Main.this, "벌점확인", Toast.LENGTH_SHORT).show();
                    intent = new Intent(Activity_Student_Main.this, Activity_Student_penaltyPoint.class);
                    startActivity(intent);
                    finish();
                    break;
                case 3:
                    Toast.makeText(Activity_Student_Main.this, "로그아웃", Toast.LENGTH_SHORT).show();
                    new AlertDialog.Builder(Activity_Student_Main.this)
                            .setTitle("종료")
                            .setMessage("종료 하시겠어요?")
                            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    final PersistentCookieStore myCookieStore = new PersistentCookieStore(Activity_Student_Main.this); //이부분 Context 확인해야함. Activity context로.
                                    AsyncHttpClient client = Helper_server.getInstance();

                                    Helper_server.logout(myCookieStore, Activity_Student_Main.this);
                                    client.setCookieStore(myCookieStore);
                                    Intent intents = new Intent(Activity_Student_Main.this, Activity_Login.class);
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
        switch(keyCode) {
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
}