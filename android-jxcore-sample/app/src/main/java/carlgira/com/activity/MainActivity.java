package carlgira.com.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import carlgira.com.android_jxcore_sample.R;
import io.jxcore.node.jxcore;

public class MainActivity extends AppCompatActivity {

    private jxcore jx;
    private JavaRegisteredFunctions javaRegisteredFunctions;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        javaRegisteredFunctions = new JavaRegisteredFunctions(this);
        final Button startButton = (Button)findViewById(R.id.startButton);
        final Button stopButton = (Button)findViewById(R.id.stopButton);
        textView = (TextView)findViewById(R.id.textView);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jxcore.CallJSMethod("JXCoreModule_startServer", "{}");
                startButton.setEnabled(false);
                stopButton.setEnabled(true);

            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jxcore.CallJSMethod("JXCoreModule_stopServer", "{}");
                stopButton.setEnabled(false);
                startButton.setEnabled(true);
            }
        });

        InitJXcore task = new InitJXcore(this);
        task.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class InitJXcore extends AsyncTask<String, Void, Void> {

        private Activity _context;

        InitJXcore(Activity context){
            _context = context;

        }

        @Override
        protected Void doInBackground(String... params) {

            if(jx == null){
                jx = new jxcore(_context);
                jx.addJXCustomFuctions(javaRegisteredFunctions);
                jx.pluginInitialize();
            }
            return null;
        }
    }

    public void updateStatus(String content){
        this.textView.setText(content);
    }
}
