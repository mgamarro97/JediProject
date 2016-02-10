package projectjedi;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Calc extends BaseActivity implements View.OnClickListener,View.OnLongClickListener{

    public static final int THIS_ACTIVITY = 2;

    private Button boton0,boton1,boton2,boton3,boton4,boton5,boton6,boton7,boton8,boton9,botonsuma,botonresta;
    private Button botonx,botondiv,botonigual,botondec;
    private TextView result;
    private String equation = "";
    boolean message=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        setTitle("Calculator");
        checkMenuItem(2);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        message = settings.getBoolean("toast",true);

        result = (TextView) findViewById(R.id.calctext);
        result.setText("");

        boton0 = (Button)findViewById(R.id.boton0);
        boton1 = (Button)findViewById(R.id.boton1);
        boton2 = (Button)findViewById(R.id.boton2);
        boton3 = (Button)findViewById(R.id.boton3);
        boton4 = (Button)findViewById(R.id.boton4);
        boton5 = (Button)findViewById(R.id.boton5);
        boton6 = (Button)findViewById(R.id.boton6);
        boton7 = (Button)findViewById(R.id.boton7);
        boton8 = (Button)findViewById(R.id.boton8);
        boton9 = (Button)findViewById(R.id.boton9);
        botonsuma = (Button)findViewById(R.id.botonsuma);
        botonresta = (Button)findViewById(R.id.botonresta);
        botonx = (Button)findViewById(R.id.botonx);
        botondiv = (Button)findViewById(R.id.botondiv);
        botonigual = (Button)findViewById(R.id.botonigual);
        botondec = (Button)findViewById(R.id.botondec);

        boton0.setOnClickListener(this);
        boton1.setOnClickListener(this);
        boton2.setOnClickListener(this);
        boton3.setOnClickListener(this);
        boton4.setOnClickListener(this);
        boton5.setOnClickListener(this);
        boton6.setOnClickListener(this);
        boton7.setOnClickListener(this);
        boton8.setOnClickListener(this);
        boton9.setOnClickListener(this);
        botonsuma.setOnClickListener(this);
        botonresta.setOnClickListener(this);
        botonsuma.setOnLongClickListener(this);
        botonresta.setOnLongClickListener(this);
        botonx.setOnClickListener(this);
        botonx.setOnLongClickListener(this);
        botondiv.setOnClickListener(this);
        botonigual.setOnClickListener(this);
        botondec.setOnClickListener(this);

    }

    public static double eval(final String str) {
        class Parser {
            int pos = -1, c;

            void eatChar() {
                c = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            void eatSpace() {
                while (Character.isWhitespace(c)) eatChar();
            }

            double parse() {
                eatChar();
                double v = parseExpression();
                if (c != -1) throw new RuntimeException("Unexpected: " + (char)c);
                return v;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor | term brackets
            // factor = brackets | number | factor `^` factor
            // brackets = `(` expression `)`

            double parseExpression() {
                double v = parseTerm();
                for (;;) {
                    eatSpace();
                    if (c == '+') { // addition
                        eatChar();
                        v += parseTerm();
                    } else if (c == '-') { // subtraction
                        eatChar();
                        v -= parseTerm();
                    } else {
                        return v;
                    }
                }
            }

            double parseTerm() {
                double v = parseFactor();
                for (;;) {
                    eatSpace();
                    if (c == '/') { // division
                        eatChar();
                        v /= parseFactor();
                    } else if (c == 'x' || c == '(') { // multiplication
                        if (c == 'x') eatChar();
                        v *= parseFactor();
                    } else {
                        return v;
                    }
                }
            }

            double parseFactor() {
                double v;
                boolean negate = false;
                eatSpace();
                if (c == '+' || c == '-') { // unary plus & minus
                    negate = c == '-';
                    eatChar();
                    eatSpace();
                }
                if (c == '(') { // brackets
                    eatChar();
                    v = parseExpression();
                    if (c == ')') eatChar();
                } else { // numbers
                    StringBuilder sb = new StringBuilder();
                    while ((c >= '0' && c <= '9') || c == '.') {
                        sb.append((char)c);
                        eatChar();
                    }
                    if (sb.length() == 0) throw new RuntimeException("Unexpected: " + (char)c);
                    v = Double.parseDouble(sb.toString());
                }
                eatSpace();
                if (c == '^') { // exponentiation
                    eatChar();
                    v = Math.pow(v, parseFactor());
                }
                if (negate) v = -v; // unary minus is applied after exponentiation; e.g. -3^2=-9
                return v;
            }
        }
        return new Parser().parse();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString("Screen", equation);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        String screen = savedInstanceState.getString("Screen");
        equation=screen;
        result.setText("" + equation);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.toolbarcalc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.delete:

                equation="";
                result.setText("");

                return true;
            case R.id.back:

                equation = equation.substring(0,equation.length()-1);
                result.setText(equation);
                return true;

            case R.id.call:

                Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+equation));
                startActivity(call);

                return true;

            case R.id.contacts:
                Intent contact = new Intent(
                        ContactsContract.Intents.SHOW_OR_CREATE_CONTACT,
                        Uri.parse("tel:" + equation));
                contact.putExtra(ContactsContract.Intents.EXTRA_FORCE_CREATE, true);
                startActivity(contact);

                return true;
            case R.id.msg:
                message=!message;

            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("toast", message);

                Toast toast = Toast.makeText(getApplicationContext(), "Toasts!", Toast.LENGTH_SHORT);

                NotificationCompat.Builder mBuilder =
                        (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                                .setSmallIcon(R.drawable.back)
                                .setContentTitle("Notifications")
                                .setContentText("Notifications!!");
                NotificationManager mNotifyMgr =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                if(message)toast.show();
                else mNotifyMgr.notify(1, mBuilder.build());
            editor.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onLongClick(View v) {
        switch (v.getId())  {
            case R.id.botonsuma:

                equation+="(";
                result.append("(");

                break;
            case R.id.botonresta:

                equation+=")";
                result.append(")");

                break;
            case R.id.botonx:

                equation+="^";
                result.append("^");

                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.boton0:

                equation+="0";
                result.append("0");


                break;

            case R.id.boton1:

                equation+="1";
                result.append("1");

                break;

            case R.id.boton2:

                equation+="2";
                result.append("2");

                break;

            case R.id.boton3:

                equation+= (this.getString(R.string.three));
                result.append(this.getString(R.string.three));

                break;

            case R.id.boton4:

                equation+="4";
                result.append("4");

                break;

            case R.id.boton5:

                equation+="5";
                result.append("5");

                break;

            case R.id.boton6:

                equation+="6";
                result.append("6");

                break;

            case R.id.boton7:

                equation+="7";
                result.append("7");

                break;


            case R.id.boton8:

                equation+="8";
                result.append("8");

                break;

            case R.id.boton9:

                equation+="9";
                result.append("9");

                break;

            case R.id.botonsuma:

                equation+=(this.getString(R.string.sum));
                result.append(this.getString(R.string.sum));

                break;

            case R.id.botonresta:

                equation+="-";
                result.append("-");

                break;

            case R.id.botonx:

                equation+="x";
                result.append("x");

                break;

            case R.id.botondiv:

                equation+="/";
                result.append("/");

                break;

            case R.id.botondec:
                equation+=".";
                result.append(".");

                break;

            case R.id.botonigual:
                double solution = eval(equation);

                Context context = getApplicationContext();
                CharSequence text = "Operation Error!";
                int duration = Toast.LENGTH_SHORT;

                Toast error = Toast.makeText(context, text, duration);

                if(String.valueOf(solution).equals("Infinity") || String.valueOf(solution).equals("-Infinity")){

                    error.show();
                    result.setText("");
                    equation = "";

            }
                    result.setText(String.valueOf(solution));
                    equation=""+solution;

                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkMenuItem(THIS_ACTIVITY);
    }

    @Override
     protected void onDestroy() {
        super.onDestroy();

    }
}