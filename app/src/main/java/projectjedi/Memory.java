package projectjedi;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Memory extends BaseActivity implements View.OnClickListener {

    public static final int THIS_ACTIVITY = 1;


    public static final String PREFS_NAME = "Settings";

    private Integer[] images = {R.drawable.icon1, R.drawable.icon2, R.drawable.icon3, R.drawable.icon4,
            R.drawable.icon5, R.drawable.icon6, R.drawable.icon7, R.drawable.icon8, R.drawable.icon1,
            R.drawable.icon2, R.drawable.icon3, R.drawable.icon4, R.drawable.icon5, R.drawable.icon6,
            R.drawable.icon7, R.drawable.icon8};

    List<Integer> files = Arrays.asList(images);

    int pair=0;
    int x, y;
    int n=0;
    int remain=8;
    int highscore;
    String name,score;

    DBHelper dbHelper;

    TextView attempts;

    ImageButton card1, card2, card3, card4, card5, card6, card7, card8,
            card9, card10, card11, card12, card13, card14, card15, card16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setTitle("Memory Game");
        checkMenuItem(1);
        dbHelper = new DBHelper(getApplicationContext());

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        name = settings.getString("username", "User Not Found!!");

        Collections.shuffle(files);

        images = files.toArray(new Integer[files.size()]);

        attempts = (TextView) findViewById(R.id.attempts);
        attempts.setText("Attempts: " + n);

        card1 = (ImageButton) findViewById(R.id.card1);
        card2 = (ImageButton) findViewById(R.id.card2);
        card3 = (ImageButton) findViewById(R.id.card3);
        card4 = (ImageButton) findViewById(R.id.card4);
        card5 = (ImageButton) findViewById(R.id.card5);
        card6 = (ImageButton) findViewById(R.id.card6);
        card7 = (ImageButton) findViewById(R.id.card7);
        card8 = (ImageButton) findViewById(R.id.card8);
        card9 = (ImageButton) findViewById(R.id.card9);
        card10 = (ImageButton) findViewById(R.id.card10);
        card11 = (ImageButton) findViewById(R.id.card11);
        card12 = (ImageButton) findViewById(R.id.card12);
        card13 = (ImageButton) findViewById(R.id.card13);
        card14 = (ImageButton) findViewById(R.id.card14);
        card15 = (ImageButton) findViewById(R.id.card15);
        card16 = (ImageButton) findViewById(R.id.card16);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
        card5.setOnClickListener(this);
        card6.setOnClickListener(this);
        card7.setOnClickListener(this);
        card8.setOnClickListener(this);
        card9.setOnClickListener(this);
        card10.setOnClickListener(this);
        card11.setOnClickListener(this);
        card12.setOnClickListener(this);
        card13.setOnClickListener(this);
        card14.setOnClickListener(this);
        card15.setOnClickListener(this);
        card16.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.toolbarmem, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.reset:

                pair=0;
                Collections.shuffle(files);
                images = files.toArray(new Integer[files.size()]);
                x=0;
                y=0;
                n=0;
                remain=8;
                reset();

                attempts.setText("Attempts: " + n);

                return true;

            case R.id.rank:
                Intent score = new Intent(getApplicationContext(), Score.class);
                startActivity(score);


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void delete(int x) {
        if (x == 0) {
            card1.setVisibility(View.INVISIBLE);
            card1.setClickable(false);
        }
        if (x == 1) {
            card2.setVisibility(View.INVISIBLE);
            card2.setClickable(false);
        }
        if (x == 2) {
            card3.setVisibility(View.INVISIBLE);
            card3.setClickable(false);
        }
        if (x == 3) {
            card4.setVisibility(View.INVISIBLE);
            card4.setClickable(false);
        }
        if (x == 4) {
            card5.setVisibility(View.INVISIBLE);
            card5.setClickable(false);
        }
        if (x == 5) {
            card6.setVisibility(View.INVISIBLE);
            card6.setClickable(false);
        }
        if (x == 6) {
            card7.setVisibility(View.INVISIBLE);
            card7.setClickable(false);
        }
        if (x == 7) {
            card8.setVisibility(View.INVISIBLE);
            card8.setClickable(false);
        }
        if (x == 8) {
            card9.setVisibility(View.INVISIBLE);
            card9.setClickable(false);
        }
        if (x == 9) {
            card10.setVisibility(View.INVISIBLE);
            card10.setClickable(false);
        }
        if (x == 10) {
            card11.setVisibility(View.INVISIBLE);
            card11.setClickable(false);
        }
        if (x == 11) {
            card12.setVisibility(View.INVISIBLE);
            card12.setClickable(false);
        }
        if (x == 12) {
            card13.setVisibility(View.INVISIBLE);
            card13.setClickable(false);
        }
        if (x == 13) {
            card14.setVisibility(View.INVISIBLE);
            card14.setClickable(false);
        }
        if (x == 14) {
            card15.setVisibility(View.INVISIBLE);
            card15.setClickable(false);
        }
        if (x == 15) {
            card16.setVisibility(View.INVISIBLE);
            card16.setClickable(false);
        }
    }

    private void reset() {
        card1.setVisibility(View.VISIBLE);
        card1.setClickable(true);
        card2.setVisibility(View.VISIBLE);
        card2.setClickable(true);
        card3.setVisibility(View.VISIBLE);
        card3.setClickable(true);
        card4.setVisibility(View.VISIBLE);
        card4.setClickable(true);
        card5.setVisibility(View.VISIBLE);
        card5.setClickable(true);
        card6.setVisibility(View.VISIBLE);
        card6.setClickable(true);
        card7.setVisibility(View.VISIBLE);
        card7.setClickable(true);
        card8.setVisibility(View.VISIBLE);
        card8.setClickable(true);
        card9.setVisibility(View.VISIBLE);
        card9.setClickable(true);
        card10.setVisibility(View.VISIBLE);
        card10.setClickable(true);
        card11.setVisibility(View.VISIBLE);
        card11.setClickable(true);
        card12.setVisibility(View.VISIBLE);
        card12.setClickable(true);
        card13.setVisibility(View.VISIBLE);
        card13.setClickable(true);
        card14.setVisibility(View.VISIBLE);
        card14.setClickable(true);
        card15.setVisibility(View.VISIBLE);
        card15.setClickable(true);
        card16.setVisibility(View.VISIBLE);
        card16.setClickable(true);

        for (int i=0; i<16;i++){flip(i);}}

    //Flip
    private void flip(int x) {
        if (x == 0) card1.setImageResource(R.drawable.hiddencard);
        if (x == 1) card2.setImageResource(R.drawable.hiddencard);
        if (x == 2) card3.setImageResource(R.drawable.hiddencard);
        if (x == 3) card4.setImageResource(R.drawable.hiddencard);
        if (x == 4) card5.setImageResource(R.drawable.hiddencard);
        if (x == 5) card6.setImageResource(R.drawable.hiddencard);
        if (x == 6) card7.setImageResource(R.drawable.hiddencard);
        if (x == 7) card8.setImageResource(R.drawable.hiddencard);
        if (x == 8) card9.setImageResource(R.drawable.hiddencard);
        if (x == 9) card10.setImageResource(R.drawable.hiddencard);
        if (x == 10) card11.setImageResource(R.drawable.hiddencard);
        if (x == 11) card12.setImageResource(R.drawable.hiddencard);
        if (x == 12) card13.setImageResource(R.drawable.hiddencard);
        if (x == 13) card14.setImageResource(R.drawable.hiddencard);
        if (x == 14) card15.setImageResource(R.drawable.hiddencard);
        if (x == 15) card16.setImageResource(R.drawable.hiddencard);
    }

//Show
    private void show(int x) {
        if (x == 0) card1.setImageResource(images[x]);
        if (x == 1) card2.setImageResource(images[x]);
        if (x == 2) card3.setImageResource(images[x]);
        if (x == 3) card4.setImageResource(images[x]);
        if (x == 4) card5.setImageResource(images[x]);
        if (x == 5) card6.setImageResource(images[x]);
        if (x == 6) card7.setImageResource(images[x]);
        if (x == 7) card8.setImageResource(images[x]);
        if (x == 8) card9.setImageResource(images[x]);
        if (x == 9) card10.setImageResource(images[x]);
        if (x == 10) card11.setImageResource(images[x]);
        if (x == 11) card12.setImageResource(images[x]);
        if (x == 12) card13.setImageResource(images[x]);
        if (x == 13) card14.setImageResource(images[x]);
        if (x == 14) card15.setImageResource(images[x]);
        if (x == 15) card16.setImageResource(images[x]);
    }

//Start if(equals)
    private void pairs(final int x, final int y) {
        new Thread(new Runnable() {
            public void run() {
                MyTask task = new MyTask();
                task.execute();
            }
        }).start();
    }

//Show card
    private void addElements(int card) {
        if (pair == 0) {
            show(card);
            x = card;
            pair++;
        } else if (pair == 1 && card != x) {
            show(card);
            y = card;
            pair++;
            pairs(x, y);
        }
    }

//Buttons
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card1:

                addElements(0);
                break;

            case R.id.card2:

                addElements(1);
                break;

            case R.id.card3:
                addElements(2);
                break;

            case R.id.card4:

                addElements(3);
                break;

            case R.id.card5:

                addElements(4);
                break;

            case R.id.card6:

                addElements(5);
                break;

            case R.id.card7:

                addElements(6);
                break;

            case R.id.card8:

                addElements(7);
                break;

            case R.id.card9:

                addElements(8);
                break;

            case R.id.card10:

                addElements(9);
                break;

            case R.id.card11:

                addElements(10);
                break;

            case R.id.card12:

                addElements(11);
                break;

            case R.id.card13:

                addElements(12);
                break;

            case R.id.card14:

                addElements(13);
                break;

            case R.id.card15:

                addElements(14);
                break;

            case R.id.card16:

                addElements(15);
                break;

            default:
        }
    }

//Asynctask
    private class MyTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
                try {
                    Thread.sleep(1000);
                    if (String.valueOf(images[x]).equals(String.valueOf(images[y])))Thread.sleep(1000);;
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            Log.w("Error", "image x " + images[x] + " and images y " + images[y]);
            if (String.valueOf(images[x]).equals(String.valueOf(images[y]))) {
                Log.w("Error", "equals");
                delete(x);
                delete(y);
                remain--;
                if (remain == 0){
                    win();
                }
            } else {
                flip(x);
                flip(y);
            }
            pair = 0;
            n++;
            attempts.setText("Attempts: " + n);
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

    public void win() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("You Win!")
                .setMessage("Total Attempts :" + n)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Cursor c = dbHelper.getProfile(name);

                        if (c.moveToFirst()) {
                            score = c.getString(c.getColumnIndex("score"));
                            highscore = Integer.parseInt("" + score);
                        }

                        if (n < highscore) {
                            ContentValues valuesToStore = new ContentValues();
                            valuesToStore.put("score", n);
                            String[] args = {"" + name};
                            dbHelper.updateDB("LOGIN_TABLE", valuesToStore, "user=?", args);
                        }
                        pair = 0;
                        Collections.shuffle(files);
                        images = files.toArray(new Integer[files.size()]);
                        x = 0;
                        y = 0;
                        n = 0;
                        remain = 8;
                        reset();

                    }

                })
                .show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkMenuItem(THIS_ACTIVITY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ;
    }
}
