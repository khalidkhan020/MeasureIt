package com.appzone.measureit;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build.VERSION;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.appzone.measureit.views.RulerView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnClickListener
{
    private AppCompatTextView tvToggleToMetric;
    private AppCompatTextView tvTogglePointer;
    private RulerView ruler;
    private LinearLayout llRightContainer;
    private SharedPreferences preferences;
    private int f23r;
    private AlertDialog colorDialog;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        findAllViews();
    }
    private void findAllViews() {
        ruler = findViewById(R.id.ruler);
        llRightContainer = findViewById(R.id.right_container);
        tvToggleToMetric = findViewById(R.id.toggle_metric_button);
        tvTogglePointer = findViewById(R.id.toggle_pointer_button);
        AppCompatTextView tvRandomColor = findViewById(R.id.random_color_button);

        tvTogglePointer.setOnClickListener(this);
        tvToggleToMetric.setOnClickListener(this);
        tvRandomColor.setOnClickListener(this);

        this.preferences = getPreferences(0);
        boolean z = this.preferences.getBoolean(getString(R.string.ruler_show_pointer_pref_key), true);
        String string = getString(R.string.ruler_is_metric_pref_key);
        boolean z2 = (getIntent().getExtras() == null || !getIntent().getExtras().containsKey(string)) ? this.preferences.getBoolean(string, false) : getIntent().getExtras().getBoolean(string);
        m39a(z, z2);
    }
    @Override
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z && VERSION.SDK_INT >= 19) {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_FULLSCREEN|
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                );
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.choose_color:
                m40b(this.f23r);
                this.ruler.m67a(this.f23r);
                this.colorDialog.cancel();
                m49q();
                break;
            case R.id.random_color_button:
                this.f23r = getResources().getColor( R.color.colorAccent);
                colorDialog = new AlertDialog.Builder(this)
                        .setView( R.layout.color_picker)
                        .setNegativeButton(R.string.cancel, null)
                        .setPositiveButton(R.string.random_color, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Random random = new Random();
                                f23r = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
                                m40b(f23r);
                                ruler.m67a(f23r);
                                m49q();
                            }
                        })
                        .show();
                final   AppCompatImageView appCompatImageView = colorDialog.findViewById(R.id.choose_color);
                final  ImageView imageView =colorDialog.findViewById(R.id.color_spectrum);
                if (imageView != null && appCompatImageView != null)
                {
                    imageView.setOnTouchListener(new OnTouchListener()
                    {

                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            switch (event.getAction()) {
                                case 2:
                                    int a = m37a(event, imageView, ((BitmapDrawable) imageView.getDrawable()).getBitmap());
                                    if (a != 0) {
                                        f23r = Color.argb(255, Color.red(a), Color.green(a), Color.blue(a));
                                        appCompatImageView.getBackground().setColorFilter(f23r, Mode.SRC_ATOP);
                                        break;
                                    }
                                    break;
                            }
                            return true;
                        }
                    });
                    appCompatImageView.setOnClickListener(this);
                }  break;
            case R.id.toggle_metric_button:
                preferences.edit().putBoolean(getString(R.string.ruler_is_metric_pref_key), !this.ruler.m69b()).apply();
                this.tvToggleToMetric.setText(this.ruler.m69b() ? getString(R.string.button_metric) : getString(R.string.button_imperial));
                this.ruler.m70c();
                break;
            case R.id.toggle_pointer_button:
                preferences.edit().putBoolean(getString(R.string.ruler_show_pointer_pref_key), !this.ruler.m68a()).apply();
                this.tvTogglePointer.setText(this.ruler.m68a() ? getString(R.string.button_show_pointer) : getString(R.string.button_hide_pointer));
                this.ruler.m71d();
                break;

        }
    }

    private int m37a(MotionEvent motionEvent, ImageView imageView, Bitmap bitmap) {
        int i = 0;
        Matrix matrix = new Matrix();
        imageView.getImageMatrix().invert(matrix);
        float[] fArr = new float[]{motionEvent.getX(), motionEvent.getY()};
        matrix.mapPoints(fArr);
        int i2 = (int) fArr[0];
        int i3 = (int) fArr[1];
        if (i2 < 0) {
            i2 = 0;
        }
        if (i2 > bitmap.getWidth() - 1) {
            i2 = bitmap.getWidth() - 1;
        }
        if (i3 >= 0) {
            i = i3;
        }
        if (i > bitmap.getHeight() - 1) {
            i = bitmap.getHeight() - 1;
        }
        return bitmap.getPixel(i2, i);
    }

    private void m39a(boolean z, boolean z2) {
        this.f23r = this.preferences.getInt(getString(R.string.ruler_color_pref_key),getResources().getColor( R.color.colorAccent));
        this.ruler.setShowPointer(z);
        this.ruler.setIsMetric(z2);
        this.ruler.setAccentColor(this.f23r);
        this.llRightContainer.setBackgroundColor(this.f23r);
        this.tvToggleToMetric.setText(z2 ? getString(R.string.button_imperial) : getString(R.string.button_metric));
        this.tvTogglePointer.setText(z ? getString(R.string.button_hide_pointer) : getString(R.string.button_show_pointer));
    }

    private void m40b(int i) {
        ObjectAnimator ofInt = ObjectAnimator.ofInt(this.llRightContainer, "backgroundColor", new int[]{this.ruler.getAccentColor(), i});
        ofInt.setEvaluator(new ArgbEvaluator());
        ofInt.setDuration(200);
        ofInt.start();
    }

    private void m49q() {
        Editor edit = this.preferences.edit();
        edit.putInt(getString(R.string.ruler_color_pref_key), this.f23r);
        edit.apply();
    }

}
