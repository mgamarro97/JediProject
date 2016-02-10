package projectjedi;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service implements MediaPlayer.OnCompletionListener{
        private static final String TAG = null;
        MediaPlayer player;
    int code;
        public IBinder onBind(Intent arg0) {

            return null;
        }
        @Override
        public void onCreate() {
            super.onCreate();
            player = MediaPlayer.create(this, R.raw.navi);
            player.setLooping(false);

        }
        public int onStartCommand(Intent intent,int flag, int startId) {
            if (intent.hasExtra("code")) {
                 code = intent.getIntExtra("code", 1);
            }
            if (code==1) {

                if (player.isPlaying()) {
                    Log.w("Error", "Paused");

                    player.pause();
                } else if (!player.isPlaying()) {
                    Log.e("Error", "Restarted");

                    player.start();
                }
            }

            if (code==2) {

                if (player.isLooping()) {
                    player.setLooping(false);
                } else if (!player.isLooping()) {
                    player.setLooping(true);

                }
            }
            return 1;
        }

        public void onStart(Intent intent, int startId) {
        // TO DO
        }
        public IBinder onUnBind(Intent arg0) {
            // TO DO Auto-generated method
            return null;
        }

        public void repeat() {

        }
        public void onPause() {

        }
        @Override
        public void onDestroy() {
            player.stop();
            player.release();
        }

        @Override
        public void onLowMemory() {

        }

    @Override
    public void onCompletion(MediaPlayer mp){
        if(player.isLooping())player.start();
        player.stop();
    }
}