package com.system.dormitory.dormitory_system_android.content;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.system.dormitory.dormitory_system_android.R;
import com.system.dormitory.dormitory_system_android.thread.DataPostThread;

import java.util.ArrayList;

import cz.msebera.android.httpclient.message.BasicNameValuePair;

/**
 * Created by 보운 on 2016-05-15.
 */
public class WritingActivity extends Activity {
    private AQuery aq;
    private String title, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writing_layout);
        aq = new AQuery(this);

        aq.id(R.id.btn_write_submit).clicked(listener);
        aq.id(R.id.btn_write_cancel).clicked(listener);
    }

    Button.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_write_submit:
                    title = aq.id(R.id.write_title_edit).getEditText().toString();
                    content = aq.id(R.id.write_content_edit).getEditText().toString();

                    ArrayList<BasicNameValuePair> boardPair = new ArrayList<BasicNameValuePair>();
                    boardPair.add(new BasicNameValuePair("title", title));
                    boardPair.add(new BasicNameValuePair("content", content));
                    new DataPostThread().execute(boardPair);
                    break;
                case R.id.btn_write_cancel:
                    Toast.makeText(getApplicationContext(), "글쓰기를 취소하셨습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                default:
                    break;
            }
        }
    };
}
