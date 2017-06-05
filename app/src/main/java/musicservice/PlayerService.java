package musicservice;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by yang on 2016/11/13.
 */

public class PlayerService extends Service {
    private MediaPlayer mediaPlayer=new MediaPlayer(); //媒体播放器对象
    private String path;//文件路径
    private boolean isPause;//暂停状态
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(mediaPlayer.isPlaying()){
           stop();
        }
        path=intent.getStringExtra("utl");
        String msg=intent.getStringExtra("MSG");
        if(msg == "PLAY_MSG") {
            play(0);
        } else if(msg == "PAUSE_MSG") {
            pause();
        } else if(msg == "STOP_MSG") {
            stop();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 播放音乐
     * @param position
     */
    private void play(int position) {
        Log.i("MusicPlay","PLAY_MSG************************");
        try {
            mediaPlayer.reset();//把各项参数恢复到初始状态
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();  //进行缓冲
            mediaPlayer.setOnPreparedListener(new PreparedListener(position));//注册一个监听器
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 暂停音乐
     */
    private void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPause = true;
        }
        Log.i("MusicPlay","PAUSE_MSG************************");
    }
    /**
     * 停止音乐
     */
    private void stop(){
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            try {
                mediaPlayer.prepare(); // 在调用stop后如果需要再次通过start进行播放,需要之前调用prepare函数
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.i("MusicPlay","STOP_MSG************************");
        }
    }
    @Override
    public void onDestroy() {
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
    /**
     *
     * 实现一个OnPrepareLister接口,当音乐准备好的时候开始播放
     *
     */
    private final class PreparedListener implements MediaPlayer.OnPreparedListener {
        private int positon;

        public PreparedListener(int positon) {
            this.positon = positon;
        }

        @Override
        public void onPrepared(MediaPlayer mp) {
            mediaPlayer.start();    //开始播放
            if(positon > 0) {    //如果音乐不是从头播放
                mediaPlayer.seekTo(positon);
            }
        }
    }
}
