package ifce.lara.vite.management;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import ifce.lara.vite.R;


public class AlarmeManager extends Thread{

    MediaPlayer mp;
    Context context;
    Context contxtFalse;
    AudioManager audio;
    MediaPlayer mplay;

    public AlarmeManager(Context contex){


        if(contex!=null) {
            context = contex;

            try {
                mplay = MediaPlayer.create(context, R.raw.sirene);
                audio = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);

            } catch (Exception e) {

            }

        }
        else{ contxtFalse = contex;  }
    }


    public void rum(){

            if(context!=null && mplay.isPlaying()==false) {

                mplay.start();

                audio.adjustVolume(AudioManager.ADJUST_RAISE, 1);
                audio.adjustVolume(AudioManager.ADJUST_RAISE, 1);
                mplay.setLooping(true);

            }
            else if(contxtFalse==null){

                mplay.stop();

            }



    }

}
