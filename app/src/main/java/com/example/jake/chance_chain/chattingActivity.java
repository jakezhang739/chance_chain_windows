package com.example.jake.chance_chain;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;

public class chattingActivity extends AppCompatActivity {
    LinearLayout beijing;
    EditText getInput;
    String myUsr;
    Context context;
    AppHelper helper=new AppHelper();
    DynamoDBMapper mapper;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        ActionBar actionBar = getSupportActionBar();
        context = getApplicationContext().getApplicationContext();
        myUsr = helper.getCurrentUserName(context);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.chatbar);
        ImageView back = (ImageView) actionBar.getCustomView().findViewById(R.id.back);
        TextView titlteText = (TextView) actionBar.getCustomView().findViewById(R.id.title);
        userId = getIntent().getStringExtra("title").toString();
        mapper = helper.getMapper(context);
        titlteText.setText(userId);
         beijing = (LinearLayout) findViewById(R.id.liaobeijing);
         getInput = (EditText) findViewById(R.id.inputMsg);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView add = (ImageView) findViewById(R.id.fasongxiao);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddMyField(v);
            }
        });

    }
    public void onAddMyField(View v) {
        View layout1 = LayoutInflater.from(this).inflate(R.layout.wodeliaotian, beijing, false);
        TextView myMsg = (TextView) layout1.findViewById(R.id.woshuo);
        ImageView wotou = (ImageView) layout1.findViewById(R.id.wotou);
        Picasso.get().load("https://s3.amazonaws.com/chance-userfiles-mobilehub-653619147/"+myUsr+".png").placeholder(R.drawable.splash).into(wotou);
        String getMsg = getInput.getText().toString();
        if(!getMsg.isEmpty()) {
            myMsg.setText(getMsg);
            beijing.addView(layout1);
            getInput.setText("");
            new Thread(sendMsg).start();
        }

        Log.d("beijing",String.valueOf(beijing.getChildCount())+Picasso.get().load("https://s3.amazonaws.com/chance-userfiles-mobilehub-653619147/"+"sd"+".png"));
    }

    Runnable sendMsg = new Runnable() {
        @Override
        public void run() {
            final ChattingTableDO chattingTableDO = new ChattingTableDO();
            int chatsize = helper.returnChatSize(mapper);
            chattingTableDO.setChatid(String.valueOf(chatsize));
            chattingTableDO.setSender(myUsr);
            chattingTableDO.setReceiver(userId);
            Date currentTime = Calendar.getInstance().getTime();
            String dateString = DateFormat.format("yyyyMMddHHmmss", new Date(currentTime.getTime())).toString();
            chattingTableDO.setTime(dateString);
            mapper.save(chattingTableDO);
        }
    };
}
