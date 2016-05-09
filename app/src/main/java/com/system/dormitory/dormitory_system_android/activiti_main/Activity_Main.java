package com.system.dormitory.dormitory_system_android.activiti_main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.adapter.ViewPagerAdapter;
import com.system.dormitory.dormitory_system_android.data.BoardItem;
import com.system.dormitory.dormitory_system_android.data.DataManager;
import com.system.dormitory.dormitory_system_android.data.NoticeItem;

public class Activity_Main extends AppCompatActivity {
    private ListView lvNavList;
    private DrawerLayout dlDrawer;
    private ActionBarDrawerToggle dtToggle;
    private ViewPager viewPager;
    private AQuery aq;
    private DataManager dataManager;
    private String[] navItems = {"점호", "대여", "외박", "공지사항", "게시판"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aq = new AQuery(this);

        init();
    }

    public void init() {
        dataManager = DataManager.getInstance();
        dataManager.DataClear();

        dataManager.getBoardItems().add(new BoardItem("게시글 #1", "게시글 내용 #1"));
        dataManager.getBoardItems().add(new BoardItem("게시글 #2", "게시글 내용 #2"));
        dataManager.getBoardItems().add(new BoardItem("게시글 #3", "게시글 내용 #3"));
        dataManager.getBoardItems().add(new BoardItem("게시글 #4", "게시글 내용 #4"));
        dataManager.getBoardItems().add(new BoardItem("게시글 #5", "게시글 내용 #5"));

        dataManager.getNoticeItems().add(new NoticeItem("공지사항 #1", "공지사항 내용 #1"));
        dataManager.getNoticeItems().add(new NoticeItem("공지사항 #2", "공지사항 내용 #2"));
        dataManager.getNoticeItems().add(new NoticeItem("공지사항 #3", "공지사항 내용 #3"));
        dataManager.getNoticeItems().add(new NoticeItem("공지사항 #4", "공지사항 내용 #4"));
        dataManager.getNoticeItems().add(new NoticeItem("공지사항 #5", "공지사항 내용 #5"));

        Log.i("size", String.valueOf(dataManager.getBoardItems().size()));

        aq.id(R.id.notice_layout).clicked(changePage);
        aq.id(R.id.point_layout).clicked(changePage);
        aq.id(R.id.board_layout).clicked(changePage);

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

    Button.OnClickListener changePage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.notice_layout:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.point_layout:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.board_layout:
                    viewPager.setCurrentItem(2);
                    break;
            }
        }
    };

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
            switch (position) {
                case 0:
                    Toast.makeText(Activity_Main.this, "점호", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(Activity_Main.this, "대여", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(Activity_Main.this, "외박", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(Activity_Main.this, "공지사항", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(Activity_Main.this, "게시판", Toast.LENGTH_SHORT).show();
                    break;
            }
            dlDrawer.closeDrawer(lvNavList);
        }
    }
}