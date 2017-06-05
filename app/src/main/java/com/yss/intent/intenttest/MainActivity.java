package com.yss.intent.intenttest;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import musicservice.PlayerService;
import utill.MP3Utill;
import utill.Mp3info;
import utill.MusicItenOnclickListener;

public class MainActivity extends Activity {
    CheckBox play;
    TextView last;
    TextView next;
    private MP3Utill mp3Utill;
    LayoutInflater layoutInflater;
    private static Mp3info mp3info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play= (CheckBox) findViewById(R.id.song_play_btn);
        last= (TextView) findViewById(R.id.song_last_btn);
        next= (TextView) findViewById(R.id.song_next_btn);

        play.setOnClickListener(onClickListener);
        last.setOnClickListener(onClickListener);
        next.setOnClickListener(onClickListener);

        layoutInflater=getLayoutInflater();
        mp3info=new Mp3info();
        mp3Utill=new MP3Utill();
        View v=layoutInflater.inflate(R.layout.activity_main,null);
        List<Mp3info> list= mp3Utill.getMp3infos(this);
        mp3Utill.setListAdapter(list,this,v);

    }
    AdapterView.OnItemClickListener onItemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent=new Intent();
            intent.setClass(MainActivity.this, PlayerService.class);
            intent.putExtra("mp3info", mp3info);
            intent.putExtra("MSG", "PLAY_MSG" );
            startService ( intent );//启动Service
        }
    };
    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            intent.setClass(MainActivity.this, PlayerService.class);
            intent.putExtra("mp3info",mp3info);
            switch (v.getId()){
                case R.id.song_play_btn:
                    if (play.isChecked()){
                        intent.putExtra("MSG", "PLAY_MSG" );
                    }else{
                        intent.putExtra("MSG", "PAUSE_MSG" );
                    }
                    break;
                case R.id.song_last_btn:
                    break;
                case R.id.song_next_btn:
                    break;
            }
            startService ( intent );//启动Service
        }
    };
}
