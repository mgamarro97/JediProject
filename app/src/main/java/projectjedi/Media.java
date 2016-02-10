package projectjedi;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class Media extends BaseActivity {

    public static final int THIS_ACTIVITY = 3;

    ImageButton mp3,loop;
    MediaPlayer player;
    boolean playing = false;
    public boolean repeat = false;
    public boolean looping = false;
    MyService service;
    public int code=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        setTitle("Media Player");
        checkMenuItem(3);

        //player = MediaPlayer.create(this, R.raw.song);
        //player.setOnCompletionListener(this);
        mp3 = (ImageButton)findViewById(R.id.play);
        mp3.setImageResource(R.drawable.paused);
        loop = (ImageButton)findViewById(R.id.repeat);
        loop.setImageResource(R.drawable.repeatoff);
    }


    public void play(View v) {
        code=1;
        Intent svc=new Intent(this, MyService.class);
        svc.putExtra("code",1);
        startService(svc);

       if (playing) {
            Log.w("Error", "Fail to pause");

                mp3.setImageResource(R.drawable.paused);
        }

        else if (!playing) {
            Log.e("Error", "Fail to restart");

                mp3.setImageResource(R.drawable.playing);

        }
        playing=!playing;
    }

    public void repeat(View v){
        code=2;
        Intent svc=new Intent(this, MyService.class);
        svc.putExtra("code",2);
        startService(svc);

        if (looping) {
            Log.w("Error", "Fail to pause");

            loop.setImageResource(R.drawable.repeatoff);
        }

        else if (!looping) {
            Log.e("Error", "Fail to restart");

            loop.setImageResource(R.drawable.repeaton);
        }
       looping=!looping;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkMenuItem(THIS_ACTIVITY);
    }
}
