package projectjedi;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class Profile extends BaseActivity implements View.OnClickListener {

    public static final int THIS_ACTIVITY = 4;
    private final int requestCode = 20;

    ImageButton image;
    DBHelper dbHelper;
    TextView street,user,bestscore;
    Button edit,exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("User Profile");
        checkMenuItem(3);
        new DownloadImageTask((ImageView) findViewById(R.id.profileImage))
                .execute("https://java.sogeti.nl/JavaBlog/wp-content/uploads/2009/04/android_icon_256.png");
        edit = (Button) findViewById(R.id.edit);
        edit.setOnClickListener(this);
        exit = (Button) findViewById(R.id.exit);
        exit.setOnClickListener(this);
        dbHelper = new DBHelper(getApplicationContext());
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        name = settings.getString("username", "User Not Found!!");

        street = (TextView) findViewById(R.id.street);
        bestscore = (TextView) findViewById(R.id.bestscore);
        user = (TextView) findViewById(R.id.loggeduser);

        profileinfo();
        if (String.valueOf(street.getText()).equals("Adress: N/A")){
            editStreet();
        }
    }


        private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
            ImageView bmImage;

            public DownloadImageTask(ImageView bmImage) {
                this.bmImage = bmImage;
            }

            protected Bitmap doInBackground(String... urls) {
                String urldisplay = urls[0];
                Bitmap mIcon11 = null;
                try {
                    InputStream in = new java.net.URL(urldisplay).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                return mIcon11;
            }

            protected void onPostExecute(Bitmap result) {
                bmImage.setImageBitmap(result);
            }
        }

    public void profileinfo(){
        Cursor c = dbHelper.getProfile(name);
        if (c.moveToFirst()) {
            String name = c.getString(c.getColumnIndex("user"));
            user.setText("Logged as: " + name);
        }
        if (c.moveToFirst()) {
            String score = c.getString(c.getColumnIndex("score"));
            bestscore.setText("Min.attempts: " + score);
        }
        if (c.moveToFirst()) {
            String adress = c.getString(c.getColumnIndex("street"));
            street.setText("Adress: " + adress);
        }
    }

    public void editStreet(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");

        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ContentValues valuesToStore = new ContentValues();
                valuesToStore.put("street", "" + String.valueOf(input.getText()));
                String[] args = {"" + name};
                dbHelper.updateDB("LOGIN_TABLE", valuesToStore, "user=?", args);
                street.setText("Street: " + String.valueOf(input.getText()));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profileImage:
                Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(photoCaptureIntent,requestCode);
                break;
            case R.id.edit:
                editStreet();
                break;
            case R.id.exit:
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("userlogged", false);

                editor.commit();

                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(this.requestCode == requestCode && resultCode == RESULT_OK){
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            image.setImageBitmap(bitmap);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.toolbarempty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
              }
        return false;
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
