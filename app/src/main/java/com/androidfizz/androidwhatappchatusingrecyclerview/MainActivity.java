package com.androidfizz.androidwhatappchatusingrecyclerview;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private AdapterChat mAdapter;
    private EditText etMsg;
    private RecyclerView mList;
    private List<ModelMessage> mChatList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mList = findViewById(R.id.mList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mList.setLayoutManager(mLayoutManager);
        mChatList = new ArrayList<>();
        int msgID = new Random().nextInt(10000 - 1000) + 1000;
        String msgTime = new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(new Date());
        int viewType = 1;
        for (int i = 10; i >= 0; i--) {

            if (i % 5 == 0)
                viewType = 1;
            else
                viewType = 2;

            mChatList.add(0, new ModelMessage(msgID, viewType, "VISHNU",
                    getString(R.string.lorem) + " " + (i + 1), msgTime));
        }

        mAdapter = new AdapterChat(mChatList);
        mList.setAdapter(mAdapter);
        etMsg = findViewById(R.id.etMsg);

        //USING LAMBDA
        findViewById(R.id.ivSend).setOnClickListener(v -> sendMsg());

        //OR USE IT LIKE THIS :-) YOUR CHOICE
        /*findViewById(R.id.ivSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMsg();
            }
        });*/

        mAdapter.setOnLoadMoreListner(pos -> mList.post(() -> {
            mChatList.add(0, null);
            mAdapter.notifyItemInserted(0);
            new Handler().postDelayed(() -> loadMore(), 2000);
        }));

    }


    private void sendMsg() {
        String msg = etMsg.getText().toString().trim();
        if (TextUtils.isEmpty(msg)) {
            Toast.makeText(this, "Please enter your message.", Toast.LENGTH_LONG).show();
            return;
        }
        mAdapter.sendMsg(msg);
        //mList.scrollToPosition(mChatList.size()-1);
        etMsg.setText("");
    }

    private void loadMore() {

        new Handler().postDelayed(() -> {

            if (mChatList != null && mChatList.get(0) == null) {
                mChatList.remove(0);
                mAdapter.notifyItemRemoved(0);
            }

            int msgID = new Random().nextInt(10000 - 1000) + 1000;
            String msgTime = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).format(new Date());

            for (int i = 10; i >= 0; i--) {
                ModelMessage single = new ModelMessage(msgID, 1, "VISHNU",
                        getString(R.string.lorem) + " " + (i + 1), msgTime);
                if (i % 3 == 0)
                    single.setMsgType(2);
                mChatList.add(0, single);
            }
            mAdapter.notifiyRangeChanged(11);
        }, 2000);
    }

}
