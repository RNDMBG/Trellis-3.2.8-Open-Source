            package com.google.android.gms.common;

            import android.content.Context;
            import android.util.AttributeSet;
            import android.util.Log;
            import android.view.View;
            import android.widget.Button;
            import android.widget.FrameLayout;
            import com.google.android.gms.dynamic.AbstractC0907e;
            import com.google.android.gms.internal.C1028dm;
            import com.google.android.gms.internal.C1029dn;
            import com.google.android.gms.internal.C1030do;
            
            
            public final class SignInButton extends FrameLayout implements View.OnClickListener {
                public static final int COLOR_DARK = 0;
                public static final int COLOR_LIGHT = 1;
                public static final int SIZE_ICON_ONLY = 2;
                public static final int SIZE_STANDARD = 0;
                public static final int SIZE_WIDE = 1;

                
                private int f943jc;

                
                private View f944jd;

                
                private View.OnClickListener f945je;
                private int mSize;

                public SignInButton(Context context) {
                    this(context, null);
                }

                public SignInButton(Context context, AttributeSet attrs) {
                    this(context, attrs, 0);
                }

                public SignInButton(Context context, AttributeSet attrs, int defStyle) {
                    super(context, attrs, defStyle);
                    this.f945je = null;
                    setStyle(0, 0);
                }

                
                private static Button m1026c(Context context, int i, int i2) {
                    C1030do c1030do = new C1030do(context);
                    c1030do.m1617a(context.getResources(), i, i2);
                    return c1030do;
                }

                
                private void m1027p(Context context) {
                    if (this.f944jd != null) {
                        removeView(this.f944jd);
                    }
                    try {
                        this.f944jd = C1029dn.m1610d(context, this.mSize, this.f943jc);
                    } catch (AbstractC0907e.a e) {
                        Log.w("SignInButton", "Sign in button not found, using placeholder instead");
                        this.f944jd = m1026c(context, this.mSize, this.f943jc);
                    }
                    addView(this.f944jd);
                    this.f944jd.setEnabled(isEnabled());
                    this.f944jd.setOnClickListener(this);
                }

                @Override 
                public void onClick(View view) {
                    if (this.f945je == null || view != this.f944jd) {
                        return;
                    }
                    this.f945je.onClick(this);
                }

                public void setColorScheme(int colorScheme) {
                    setStyle(this.mSize, colorScheme);
                }

                @Override 
                public void setEnabled(boolean enabled) {
                    super.setEnabled(enabled);
                    this.f944jd.setEnabled(enabled);
                }

                @Override 
                public void setOnClickListener(View.OnClickListener listener) {
                    this.f945je = listener;
                    if (this.f944jd != null) {
                        this.f944jd.setOnClickListener(this);
                    }
                }

                public void setSize(int buttonSize) {
                    setStyle(buttonSize, this.f943jc);
                }

                public void setStyle(int buttonSize, int colorScheme) {
                    C1028dm.m1602a(buttonSize >= 0 && buttonSize < 3, "Unknown button size " + buttonSize);
                    C1028dm.m1602a(colorScheme >= 0 && colorScheme < 2, "Unknown color scheme " + colorScheme);
                    this.mSize = buttonSize;
                    this.f943jc = colorScheme;
                    m1027p(getContext());
                }
            }
