package carlgira.com.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;

import io.jxcore.node.JXFunctions;
import io.jxcore.node.jxcore;

/**
 * Created by carlgira on 6/1/16.
 */
public class JavaRegisteredFunctions implements JXFunctions {

    private MainActivity activity;
    public JavaRegisteredFunctions(MainActivity activity){
        this.activity = activity;
    }

    @Override
    public void registerFunction() {

        jxcore.RegisterMethod("serverStarted", new jxcore.JXcoreCallback() {
            @SuppressLint("NewApi")
            @Override
            public void Receiver(ArrayList<Object> params, String callbackId) {

                Log.i("jxcore", "Callback, ServerStarted! ");


                activity.runOnUiThread(new Runnable() {
                    @Override
                    public final void run(){

                    activity.updateStatus("Sever Started (Callback from JS!!)");
                }
            });

            }
        });

        jxcore.RegisterMethod("serverStopped", new jxcore.JXcoreCallback() {
            @SuppressLint("NewApi")
            @Override
            public void Receiver(ArrayList<Object> params, String callbackId) {

                Log.i("jxcore", "Callback, ServerStopped");

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public final void run() {

                        activity.updateStatus("Sever Stopped (Callback from JS!!)");
                    }
                });



            }
        });

    }
}
