package notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yss.intent.intenttest.R;

/**
 * Created by yang on 2016/11/12.
 */

public class ButtonBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ButtonOnclick buttonOnclick=new ButtonOnclick();
        String action=intent.getAction();
        if (action.equals("ACTION_BUTTON")){
            int buttonID=intent.getIntExtra("INTENT_BUTTONID_TAG",0);
            switch (buttonID){
                case R.id.song_play_btn:
                    break;
            }
        }
    }

}
