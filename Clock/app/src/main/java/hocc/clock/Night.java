package hocc.clock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.Calendar;

public class Night extends AppCompatActivity {


    private final Handler handler = new Handler();

    private final Runnable checkTimeOfDay = new Runnable() {

        @Override
        public void run() {
            try {
                int hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                if (hourOfDay >=6  && hourOfDay <19) {
                    Night.this.startActivity(new Intent(Night.this, MainActivity.class));
                }
            } catch (Exception e) {
                Log.e("hocc.clock", "exception", e);
            } finally {
                handler.postDelayed(this, 10000);
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow(). addFlags (WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.blue_dark));
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        setContentView(R.layout.activity_night);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.post(checkTimeOfDay);
        getWindow().getDecorView().setSystemUiVisibility(   View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(checkTimeOfDay);
        super.onPause();
    }

}