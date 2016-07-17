package com.hardy.person.copyqq.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hardy.person.copyqq.R;

/**
 * @author 马鹏昊
 * @date {date}
 * @des
 * @updateAuthor
 * @updateDate
 * @updateDes
 */
public class ItemActivityFromDongtai extends Activity {

    TextView mTextView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_from_dongtai);
        mTextView = (TextView) findViewById(R.id.backToDongtaiMain);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        mTextView.setText(title);


    }
/*
    返回主界面按钮的回调方法
*/
    public void backToMain(View v){
        finish();
    }
}
