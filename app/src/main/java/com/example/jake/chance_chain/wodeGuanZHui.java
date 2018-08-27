package com.example.jake.chance_chain;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.squareup.picasso.Picasso;

public class wodeGuanZHui extends AppCompatActivity {

    DynamoDBMapper mapper;
    AppHelper helper = new AppHelper();
    Context context;
    LinearLayout beijing;
    String myUsr;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wode_guan_zhui);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.chatbar);
        ImageView back = (ImageView) actionBar.getCustomView().findViewById(R.id.back);
        TextView titlteText = (TextView) actionBar.getCustomView().findViewById(R.id.title);
        titlteText.setText("我的关注");
        progressBar = (ProgressBar) findViewById(R.id.progressBarchat);
        myUsr = helper.getCurrentUserName(context);
        mapper = helper.getMapper(context);
        new Thread(guanRun).run();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    Handler handler = new Handler(){
        @Override
        public  void handleMessage(Message msg){
            if(msg.what==1){
                progressBar.setVisibility(View.INVISIBLE);
                onAddView("https://s3.amazonaws.com/chance-userfiles-mobilehub-653619147/"+msg.obj.toString()+".png",msg.obj.toString());
            }
            else{
                progressBar.setVisibility(View.INVISIBLE);
            }

        }
    };

    Runnable guanRun = new Runnable() {
        @Override
        public void run() {
            UserPoolDO userPoolDO = mapper.load(UserPoolDO.class,myUsr);
            Message msg = new Message();
            for(int i =0; i <userPoolDO.getGuanZhu().size();i++){
                msg.what=1;
                msg.obj=userPoolDO.getGuanZhu().get(i);
                handler.sendMessage(msg);
            }
            msg.what=2;
            handler.sendMessage(msg);

        }
    };

    public void onAddView(String URI, String usrName){
        View layout1 = LayoutInflater.from(this).inflate(R.layout.guanitem, beijing, false);
        ImageView touxiang;
        TextView uSr;
        touxiang =(ImageView) layout1.findViewById(R.id.imageView16);
        uSr = (TextView) layout1.findViewById(R.id.textView17);
        Picasso.get().load(URI).placeholder(R.drawable.splash).into(touxiang);
        uSr.setText(usrName);
        touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(wodeGuanZHui.this, HisActivity.class);
                intent.putExtra("userName",usrName);
                startActivity(intent);

            }
        });
    }
}
