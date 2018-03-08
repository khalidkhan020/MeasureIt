package com.appzone.measureit.views;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.appzone.measureit.R;

import java.util.Locale;

public class RulerView extends View {
    Paint f33a = new Paint();
    Paint f34b = new Paint();
    private final float f35c = getResources().getDimension(R.dimen.stroke_width);
    private final float f36d = getResources().getDimension(R.dimen.text_size_sub_header);
    private final float f37e = getResources().getDimension(R.dimen.text_size_sub_header);
    private float f38f;
    private float f39g;
    private float f40h = 100.0f;
    private boolean f41i = false;
    private int colorAccent = getResources().getColor( R.color.colorAccent);
    private int f43k = 255;

    public RulerView(Context context) {
        super(context);
        m64e();
    }

    public RulerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m64e();
        m57a(context, attributeSet);
    }

    public RulerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        m64e();
        m57a(context, attributeSet);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RulerView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        m64e();
        m57a(context, attributeSet);
    }

    private void m56a(float f) {
        if (f == ((float) ((int) f))) {
            this.f33a.setColor(colorAccent);
        } else {
            this.f33a.setColor(getResources().getColor(R.color.black));
        }
    }

    private void m57a(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.RulerView, 0, 0);
        try {
            colorAccent = obtainStyledAttributes.getColor(R.styleable.RulerView_accentColor, getResources().getColor(R.color.colorAccent));
            f41i = obtainStyledAttributes.getBoolean(R.styleable.RulerView_metric, false);
            if (!obtainStyledAttributes.getBoolean(R.styleable.RulerView_showPointer, true)) {
                this.f43k = 0;
            }
            obtainStyledAttributes.recycle();
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
        }
    }

    private void m58a(Canvas canvas) {
        for (float f = 0.0f; f < this.f38f; f = (float) (((double) f) + 0.0625d)) {
            m56a(f);
            int b = m60b(f);
            float f2 = f * this.f39g;
            canvas.drawLine(0.0f, f2, (float) b, f2, this.f33a);
            m59a(canvas, f, ((float) b) - this.f36d, f2 + (this.f36d / 2.0f));
        }
    }

    private void m59a(Canvas canvas, float f, float f2, float f3) {
        int i = (int) f;
        if (f == ((float) i)) {
            String valueOf = String.valueOf(i);
            canvas.save();
            canvas.rotate(90.0f, f2, f3);
            canvas.drawText(valueOf, f2, f3, this.f34b);
            canvas.restore();
        }
    }

    private int m60b(float f) {
        double ceil = Math.ceil((double) f);
        double floor = Math.floor((double) f);
        int width = getWidth() / 2;
        return ((double) f) == floor ? width : ((double) f) - 0.5d == floor ? width / 2 : (((double) f) - 0.25d == floor || ((double) f) + 0.25d == ceil) ? width / 4 : width / 8;
    }

    private void m61b(Canvas canvas) {
        float f = ((float) (((double) this.f38f) * 2.54d)) * 10.0f;
        for (float f2 = 0.0f; f2 < f; f2 = 1.0f + f2) {
            m56a(f2 / 10.0f);
            int b = m60b(f2 / 10.0f);
            float f3 = this.f39g * ((float) (((double) (f2 / 10.0f)) / 2.54d));
            canvas.drawLine(0.0f, f3, (float) b, f3, this.f33a);
            m59a(canvas, f2 / 10.0f, ((float) b) - this.f36d, f3 + (this.f36d / 2.0f));
        }
    }

    private void m62c(Canvas canvas) {
        this.f33a.setColor(this.colorAccent);
        this.f33a.setStyle(Style.FILL);
        this.f33a.setAlpha(this.f43k);
        int width = getWidth() / 8;
        float width2 = ((float) (getWidth() - width)) - this.f37e;
        Canvas canvas2 = canvas;
        canvas2.drawLine(0.0f, this.f40h, ((float) (getWidth() - (width * 2))) - this.f37e, this.f40h, this.f33a);
        canvas.drawCircle(width2, this.f40h, (float) width, this.f33a);
        this.f33a.setStyle(Style.STROKE);
        this.f33a.setColor(getResources().getColor( R.color.white));
    }

    private void m63d(Canvas canvas) {
        this.f34b.setColor(getResources().getColor(R.color.white));
        this.f34b.setAlpha(f43k);
        String format = !f41i ? String.format(Locale.getDefault(), "%.2f", new Object[]{Float.valueOf(this.f40h / this.f39g)}) : String.format(Locale.getDefault(), "%.2f", new Object[]{Double.valueOf((((double) this.f40h) * 2.54d) / ((double) this.f39g))});
        float width = (((float) (getWidth() - (getWidth() / 8))) - this.f37e) - (this.f36d / 3.0f);
        float f = format.length() > 4 ? this.f40h - (this.f36d / 4.0f) : this.f40h;
        canvas.save();
        canvas.rotate(90.0f, width, f);
        canvas.drawText(format, width - this.f36d, f, this.f34b);
        canvas.restore();
        this.f34b.setColor(getResources().getColor( R.color.black));
        this.f34b.setAlpha(255);
    }

    private void m64e() {
        this.f33a.setStyle(Style.STROKE);
        this.f33a.setStrokeWidth(this.f35c);
        this.f33a.setAntiAlias(false);
        this.f33a.setColor(getResources().getColor( R.color.black));
        this.f34b.setStyle(Style.FILL);
        this.f34b.setTextSize(this.f36d);
        this.f34b.setFakeBoldText(true);
        this.f34b.setColor(getResources().getColor(R.color.black));
    }

    private void m65f() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (getContext() instanceof Activity) {
            ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            this.f39g = displayMetrics.ydpi;
            this.f38f = ((float) getHeight()) / this.f39g;
            return;
        }
        Log.d("Error", "View not in activity, skipping measurements");
    }

    private void m66g() {
        invalidate();
        requestLayout();
    }

    private int getTargetAlpha() {
        return this.f43k > 0 ? 0 : 255;
    }

    public void m67a(int i) {
        ObjectAnimator ofInt = ObjectAnimator.ofInt(this, "accentColor", new int[]{this.colorAccent, i});
        ofInt.setEvaluator(new ArgbEvaluator());
        ofInt.setDuration(200);
        ofInt.start();
    }

    public boolean m68a() {
        return this.f43k > 0;
    }

    public boolean m69b() {
        return this.f41i;
    }

    public void m70c() {
        this.f41i = !this.f41i;
        invalidate();
    }

    public void m71d() {
        ObjectAnimator ofInt = ObjectAnimator.ofInt(this, "pointerAlpha", new int[]{this.f43k, getTargetAlpha()});
        ofInt.setDuration(200);
        ofInt.start();
    }

    public int getAccentColor() {
        return this.colorAccent;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        m65f();
        if (this.f41i) {
            m61b(canvas);
        } else {
            m58a(canvas);
        }
        if (this.f43k > 0) {
            m62c(canvas);
            m63d(canvas);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.f43k <= 0) {
            return super.onTouchEvent(motionEvent);
        }
        this.f40h = motionEvent.getY();
        invalidate();
        return true;
    }

    public void setAccentColor(int i) {
        this.colorAccent = i;
        m66g();
    }

    public void setIsMetric(boolean z) {
        this.f41i = z;
    }

    public void setPointerAlpha(int i) {
        this.f43k = i;
        m66g();
    }

    public void setShowPointer(boolean z) {
        setPointerAlpha(z ? 255 : 0);
    }
}
