package projectjedi;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText user;
    EditText pass;
    EditText confirmpass;
    boolean message=true;

    public static final String PREFS_NAME = "Settings";

    boolean first;
    DBHelper dbHelper;

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent i) {
            int level = i.getIntExtra("level", 0);
            ProgressBar pb = (ProgressBar) findViewById(R.id.batterybar);
            pb.setProgress(level);
            TextView tv = (TextView) findViewById(R.id.battery);
            tv.setText("Battery Level: " + Integer.toString(level) + "%");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean logged = settings.getBoolean("userlogged", false);
        message = settings.getBoolean("toast", true);
        if (logged){
            Intent memory = new Intent(getApplicationContext(), Memory.class);
            startActivity(memory);

            finish();
        }
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        registerReceiver(mBatInfoReceiver, new IntentFilter(
                Intent.ACTION_BATTERY_CHANGED));


        user = (EditText)findViewById(R.id.user);
        pass =  (EditText) findViewById(R.id.pass);
        confirmpass =  (EditText) findViewById(R.id.confirmpass);
        confirmpass.setVisibility(View.GONE);
        confirmpass.setClickable(false);
        first=true;

        dbHelper = new DBHelper(getApplicationContext());
    }

    public void addUser(View view) {

        if (first == true) {
            confirmpass.setVisibility(View.VISIBLE);
            first = false;
        } else {
            ContentValues valuesToStore = new ContentValues();
            valuesToStore.put("user", String.valueOf(user.getText()));
            valuesToStore.put("pass", String.valueOf(pass.getText()));
            valuesToStore.put("score",0);
            valuesToStore.put("street","N/A");

            if (String.valueOf(user.getText()).equals("") || String.valueOf(pass.getText()).equals("") || String.valueOf(confirmpass.getText()).equals("")) {
                Toast toast = Toast.makeText(getApplicationContext(), "Empty fields!!", Toast.LENGTH_SHORT);

                NotificationCompat.Builder mBuilder =
                        (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                                .setSmallIcon(R.drawable.back)
                                .setContentTitle("Error")
                                .setContentText("Empty fields!!");
                NotificationManager mNotifyMgr =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                if (message)toast.show();
                if (!message) mNotifyMgr.notify(1, mBuilder.build());
            } else if (String.valueOf(pass.getText()).equals(String.valueOf(confirmpass.getText()))) {

                dbHelper.createDB(valuesToStore, "login");

                Toast toast = Toast.makeText(getApplicationContext(), "Succesful Sign Up!!", Toast.LENGTH_SHORT);

                NotificationCompat.Builder mBuilder =
                        (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                                .setSmallIcon(R.drawable.back)
                                .setContentTitle("Done!")
                                .setContentText("Succesful Sign Up!!");
                NotificationManager mNotifyMgr =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                if (message)toast.show();
                if (!message) mNotifyMgr.notify(1, mBuilder.build());
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Diferrent passwords!!", Toast.LENGTH_SHORT);

                NotificationCompat.Builder mBuilder =
                        (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                                .setSmallIcon(R.drawable.back)
                                .setContentTitle("Error")
                                .setContentText("Diferrent passwords!!");
                NotificationManager mNotifyMgr =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                if (message)toast.show();
                if (!message) mNotifyMgr.notify(1, mBuilder.build());

            }
            pass.setText("");
            confirmpass.setText("");
        }
    }

    public void enterUser(View view) {
        Cursor c = dbHelper.getUserPassword((user.getText().toString()), pass.getText().toString());
        if (c.moveToFirst()){

            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("userlogged", true);
            editor.putString("username", String.valueOf(user.getText()));

            // Commit the edits!
            editor.commit();
                Intent memory = new Intent(getApplicationContext(), Memory.class);
                startActivity(memory);

                 finish();
            Toast toast = Toast.makeText(getApplicationContext(), "Logged In", Toast.LENGTH_SHORT);

            NotificationCompat.Builder mBuilder =
                    (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.back)
                            .setContentTitle("Succesful Login")
                            .setContentText("Logged In");
            NotificationManager mNotifyMgr =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            if(message)toast.show();
            if(!message) mNotifyMgr.notify(1, mBuilder.build());

        }

        else {
            Toast toast = Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_SHORT);

            NotificationCompat.Builder mBuilder =
                    (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.back)
                            .setContentTitle("Error")
                            .setContentText("Wrong username or password");
            NotificationManager mNotifyMgr =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            if (message)toast.show();
            if (!message) mNotifyMgr.notify(1, mBuilder.build());
        }
    }
}