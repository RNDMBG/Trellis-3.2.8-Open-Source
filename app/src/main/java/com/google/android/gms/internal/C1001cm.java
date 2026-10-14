            package com.google.android.gms.internal;

            import android.content.Context;
            import android.os.Build;
            import android.os.Handler;
            import android.os.Looper;
            import android.provider.Settings;
            import android.util.DisplayMetrics;
            import android.util.TypedValue;
            import android.view.ViewGroup;
            import android.widget.FrameLayout;
            import android.widget.TextView;
            import java.math.BigInteger;
            import java.security.MessageDigest;
            import java.security.NoSuchAlgorithmException;
            import java.util.Locale;
            
            
            
            public final class C1001cm {

                
                public static final Handler f1595hO = new Handler(Looper.getMainLooper());

                
                public static int m1442a(Context context, int i) {
                    return m1443a(context.getResources().getDisplayMetrics(), i);
                }

                
                public static int m1443a(DisplayMetrics displayMetrics, int i) {
                    return (int) TypedValue.applyDimension(1, i, displayMetrics);
                }

                
                public static void m1444a(ViewGroup viewGroup, C1126x c1126x, String str) {
                    C1002cn.m1458q(str);
                    m1445a(viewGroup, c1126x, str, -65536, -16777216);
                }

                
                private static void m1445a(ViewGroup viewGroup, C1126x c1126x, String str, int i, int i2) {
                    if (viewGroup.getChildCount() != 0) {
                        return;
                    }
                    Context context = viewGroup.getContext();
                    TextView textView = new TextView(context);
                    textView.setGravity(17);
                    textView.setText(str);
                    textView.setTextColor(i);
                    textView.setBackgroundColor(i2);
                    FrameLayout frameLayout = new FrameLayout(context);
                    frameLayout.setBackgroundColor(i);
                    int iM1442a = m1442a(context, 3);
                    frameLayout.addView(textView, new FrameLayout.LayoutParams(c1126x.widthPixels - iM1442a, c1126x.heightPixels - iM1442a, 17));
                    viewGroup.addView(frameLayout, c1126x.widthPixels, c1126x.heightPixels);
                }

                
                public static boolean m1446aq() {
                    return Build.DEVICE.startsWith("generic");
                }

                
                public static boolean m1447ar() {
                    return Looper.myLooper() == Looper.getMainLooper();
                }

                
                public static void m1448b(ViewGroup viewGroup, C1126x c1126x, String str) {
                    m1445a(viewGroup, c1126x, str, -16777216, -1);
                }

                
                public static String m1449l(Context context) {
                    String string = Settings.Secure.getString(context.getContentResolver(), "android_id");
                    if (string == null || m1446aq()) {
                        string = "emulator";
                    }
                    return m1450l(string);
                }

                
                public static String m1450l(String str) {
                    for (int i = 0; i < 2; i++) {
                        try {
                            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                            messageDigest.update(str.getBytes());
                            return String.format(Locale.US, "%032X", new BigInteger(1, messageDigest.digest()));
                        } catch (NoSuchAlgorithmException e) {
                        }
                    }
                    return null;
                }
            }
