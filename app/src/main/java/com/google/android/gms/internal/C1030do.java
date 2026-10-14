            package com.google.android.gms.internal;

            import android.R;
            import android.content.Context;
            import android.content.res.Resources;
            import android.graphics.Typeface;
            import android.util.AttributeSet;
            import android.widget.Button;
            import com.google.android.gms.C0881R;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.do */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C1030do extends Button {
                public C1030do(Context context) {
                    this(context, null);
                }

                public C1030do(Context context, AttributeSet attributeSet) {
                    super(context, attributeSet, R.attr.buttonStyle);
                }

                /* JADX INFO: renamed from: b */
                private int m1613b(int i, int i2, int i3) {
                    switch (i) {
                        case 0:
                            return i2;
                        case 1:
                            return i3;
                        default:
                            throw new IllegalStateException("Unknown color scheme: " + i);
                    }
                }

                /* JADX INFO: renamed from: b */
                private void m1614b(Resources resources, int i, int i2) {
                    int iM1613b;
                    switch (i) {
                        case 0:
                        case 1:
                            iM1613b = m1613b(i2, C0881R.drawable.common_signin_btn_text_dark, C0881R.drawable.common_signin_btn_text_light);
                            break;
                        case 2:
                            iM1613b = m1613b(i2, C0881R.drawable.common_signin_btn_icon_dark, C0881R.drawable.common_signin_btn_icon_light);
                            break;
                        default:
                            throw new IllegalStateException("Unknown button size: " + i);
                    }
                    if (iM1613b == -1) {
                        throw new IllegalStateException("Could not find background resource!");
                    }
                    setBackgroundDrawable(resources.getDrawable(iM1613b));
                }

                /* JADX INFO: renamed from: c */
                private void m1615c(Resources resources) {
                    setTypeface(Typeface.DEFAULT_BOLD);
                    setTextSize(14.0f);
                    float f = resources.getDisplayMetrics().density;
                    setMinHeight((int) ((f * 48.0f) + 0.5f));
                    setMinWidth((int) ((f * 48.0f) + 0.5f));
                }

                /* JADX INFO: renamed from: c */
                private void m1616c(Resources resources, int i, int i2) {
                    setTextColor(resources.getColorStateList(m1613b(i2, C0881R.color.common_signin_btn_text_dark, C0881R.color.common_signin_btn_text_light)));
                    switch (i) {
                        case 0:
                            setText(resources.getString(C0881R.string.common_signin_button_text));
                            return;
                        case 1:
                            setText(resources.getString(C0881R.string.common_signin_button_text_long));
                            return;
                        case 2:
                            setText((CharSequence) null);
                            return;
                        default:
                            throw new IllegalStateException("Unknown button size: " + i);
                    }
                }

                /* JADX INFO: renamed from: a */
                public void m1617a(Resources resources, int i, int i2) {
                    C1028dm.m1602a(i >= 0 && i < 3, "Unknown button size " + i);
                    C1028dm.m1602a(i2 >= 0 && i2 < 2, "Unknown color scheme " + i2);
                    m1615c(resources);
                    m1614b(resources, i, i2);
                    m1616c(resources, i, i2);
                }
            }
