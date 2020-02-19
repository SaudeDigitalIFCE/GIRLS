package ifce.lara.vite.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import ifce.lara.vite.activity.MainActivity;

public class Pause_Alarme extends BroadcastReceiver {

    static AudioManager audio;

    MediaPlayer mp2 = null;
    static SoundPool sp;
    static int soundId;

    @Override
    public void onReceive(Context context, Intent intent) {
        int prevVolume;
        int volume;

        if ("android.media.VOLUME_CHANGED_ACTION".equals(intent.getAction())) {

            volume = (int)intent.getExtras().get("android.media.EXTRA_VOLUME_STREAM_VALUE");
            prevVolume = (int)intent.getExtras().get("android.media.EXTRA_PREV_VOLUME_STREAM_VALUE");
            if(volume < prevVolume) {

                 try {
                     if (!MainActivity.mp.equals(null)) {
                         MainActivity.mp.pause();
                     }
                 }catch(NullPointerException e){}

            }else if(volume > prevVolume){


            }

        }
    }
}
