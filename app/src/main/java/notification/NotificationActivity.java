package notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import com.yss.intent.intenttest.R;

/**
 * Created by yang on 2016/11/12.
 */

public class NotificationActivity extends Activity {
    public final static String ACTION_BUTTON = "notification.ButtonOnclick";
    Button button;
    NotificationManager notificationmanage;
    public final static String INTENT_BUTTONID_TAG = "ButtonId";
    public boolean isPlay = false;
    RemoteViews mRemoteViews;
    Notification notif;
    /**
     * 通知栏按钮广播
     */
    public ButtonBroadcastReceiver receiver;
    /**
     * 播放/暂停 按钮点击 ID
     */
    Context context;
    public final static int BUTTON_PALY_ID = R.id.song_play_btn;
    private final int NOTIFICATION_ID = 0xa01;
    private final int REQUEST_CODE = 0xb01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.notification_btn);
        context=this;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNotification();
                Log.i("notifiac", "创建了notification");
            }
        });

    }
/*创建自定义通知栏*/
    public void createNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        mRemoteViews = new RemoteViews(getPackageName(), R.layout.notification_layout);
        mRemoteViews.setImageViewResource(R.id.song_img, R.mipmap.ic_launcher);
        mRemoteViews.setImageViewResource(R.id.song_small_img, R.mipmap.ic_launcher);
        //是否取消按钮
        mRemoteViews.setTextViewText(R.id.song_singer, "王光良");
        mRemoteViews.setTextViewText(R.id.song_name, "童话");
        //注册广播
        receiver = new ButtonBroadcastReceiver();
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction(ACTION_BUTTON);
        context.registerReceiver(receiver, intentfilter);
        //点击事件
        Intent buttonIntent = new Intent(ACTION_BUTTON);
        buttonIntent.putExtra(INTENT_BUTTONID_TAG, R.id.song_play_btn);
        PendingIntent intent_play = PendingIntent.getBroadcast(context, BUTTON_PALY_ID, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.song_play_btn, intent_play);
        //上一首
        builder.setContent(mRemoteViews)
                .setWhen(System.currentTimeMillis())
                .setTicker("正在播放")
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher);
        notif = builder.build();
        notificationmanage = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationmanage.notify(NOTIFICATION_ID, notif);
    }
    /*广播服务*/
    public class ButtonBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if (action.equals(ACTION_BUTTON)){
                int b=(int)intent.getExtras().get(INTENT_BUTTONID_TAG);
                int buttonID=intent.getIntExtra(INTENT_BUTTONID_TAG,0);
                Log.i("music","音乐播放了"+b+"BBBBBBBBBB\n"+buttonID);
                switch (buttonID){
                    case BUTTON_PALY_ID:
                        onDownLoadBtnClick();
                        Log.i("music","音乐播放了");
                        break;
                    default:
                        Log.i("music","音乐没有播放");
                }
                switch (b){
                    case R.id.song_play_btn:
                        onDownLoadBtnClick();
                        Log.i("musicBBBBBBBBB","音乐播放了");
                        break;
                    default:
                        Log.i("musicBBBBBBBBB","音乐没有播放");
                }
            }
        }
    }
    /*音乐播放监听事件*/
    private void onDownLoadBtnClick() {
        if (isPlay) {
            //当前是进行中，则暂停
            if (isPlay) {
                mRemoteViews.setTextViewText(R.id.song_play_btn, "播放");
            } else {
                isPlay = true;
                mRemoteViews.setTextViewText(R.id.song_play_btn, "暂停");
            }
            notificationmanage.notify(NOTIFICATION_ID, notif);
        }
    }
    /*播放音乐方法*/
    public void palyMP3(){
    }
/*注销广播*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        context.unregisterReceiver(receiver);
    }
}
