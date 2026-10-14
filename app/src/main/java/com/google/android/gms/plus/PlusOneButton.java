            package com.google.android.gms.plus;

            import android.app.Activity;
            import android.content.Context;
            import android.content.Intent;
            import android.util.AttributeSet;
            import android.view.View;
            import android.widget.FrameLayout;
            import co.vine.android.service.VineService;
            import com.google.android.gms.internal.C1028dm;
            import com.google.android.gms.internal.C1031dp;
            import com.google.android.gms.internal.C1082fm;
            
            
            public final class PlusOneButton extends FrameLayout {
                public static final int ANNOTATION_BUBBLE = 1;
                public static final int ANNOTATION_INLINE = 2;
                public static final int ANNOTATION_NONE = 0;
                public static final int DEFAULT_ACTIVITY_REQUEST_CODE = -1;
                public static final int SIZE_MEDIUM = 1;
                public static final int SIZE_SMALL = 0;
                public static final int SIZE_STANDARD = 3;
                public static final int SIZE_TALL = 2;

                
                private String f2376hN;
                private int mSize;

                
                private View f2377re;

                
                private int f2378rf;

                
                private int f2379rg;

                
                private OnPlusOneClickListener f2380rh;

                protected class DefaultOnPlusOneClickListener implements View.OnClickListener, OnPlusOneClickListener {

                    
                    private final OnPlusOneClickListener f2381ri;

                    public DefaultOnPlusOneClickListener(OnPlusOneClickListener proxy) {
                        this.f2381ri = proxy;
                    }

                    @Override 
                    public void onClick(View view) {
                        Intent intent = (Intent) PlusOneButton.m2331a(PlusOneButton.this).getTag();
                        if (this.f2381ri != null) {
                            this.f2381ri.onPlusOneClick(intent);
                        } else {
                            onPlusOneClick(intent);
                        }
                    }

                    @Override 
                    public void onPlusOneClick(Intent intent) {
                        Context context = PlusOneButton.this.getContext();
                        if (!(context instanceof Activity) || intent == null) {
                            return;
                        }
                        ((Activity) context).startActivityForResult(intent, PlusOneButton.m2332b(PlusOneButton.this));
                    }
                }

                public interface OnPlusOneClickListener {
                    void onPlusOneClick(Intent intent);
                }

                public PlusOneButton(Context context) {
                    this(context, null);
                }

                public PlusOneButton(Context context, AttributeSet attrs) {
                    super(context, attrs);
                    this.mSize = getSize(context, attrs);
                    this.f2378rf = getAnnotation(context, attrs);
                    this.f2379rg = -1;
                    m2333p(getContext());
                    if (isInEditMode()) {
                    }
                }

                
                static  View m2331a(PlusOneButton plusOneButton) {
                    return plusOneButton.f2377re;
                }

                
                static  int m2332b(PlusOneButton plusOneButton) {
                    return plusOneButton.f2379rg;
                }

                protected static int getAnnotation(Context context, AttributeSet attrs) {
                    String strM1618a = C1031dp.m1618a("http://schemas.android.com/apk/lib/com.google.android.gms.plus", "annotation", context, attrs, true, false, "PlusOneButton");
                    if ("INLINE".equalsIgnoreCase(strM1618a)) {
                        return 2;
                    }
                    return !"NONE".equalsIgnoreCase(strM1618a) ? 1 : 0;
                }

                protected static int getSize(Context context, AttributeSet attrs) {
                    String strM1618a = C1031dp.m1618a("http://schemas.android.com/apk/lib/com.google.android.gms.plus", VineService.EXTRA_SIZE, context, attrs, true, false, "PlusOneButton");
                    if ("SMALL".equalsIgnoreCase(strM1618a)) {
                        return 0;
                    }
                    if ("MEDIUM".equalsIgnoreCase(strM1618a)) {
                        return 1;
                    }
                    return "TALL".equalsIgnoreCase(strM1618a) ? 2 : 3;
                }

                
                private void m2333p(Context context) {
                    if (this.f2377re != null) {
                        removeView(this.f2377re);
                    }
                    this.f2377re = C1082fm.m1979a(context, this.mSize, this.f2378rf, this.f2376hN, this.f2379rg);
                    setOnPlusOneClickListener(this.f2380rh);
                    addView(this.f2377re);
                }

                public void initialize(String url, int activityRequestCode) {
                    C1028dm.m1602a(getContext() instanceof Activity, "To use this method, the PlusOneButton must be placed in an Activity. Use initialize(PlusClient, String, OnPlusOneClickListener).");
                    this.f2376hN = url;
                    this.f2379rg = activityRequestCode;
                    m2333p(getContext());
                }

                public void initialize(String url, OnPlusOneClickListener plusOneClickListener) {
                    this.f2376hN = url;
                    this.f2379rg = 0;
                    m2333p(getContext());
                    setOnPlusOneClickListener(plusOneClickListener);
                }

                @Override 
                protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
                    this.f2377re.layout(0, 0, right - left, bottom - top);
                }

                @Override 
                protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                    View view = this.f2377re;
                    measureChild(view, widthMeasureSpec, heightMeasureSpec);
                    setMeasuredDimension(view.getMeasuredWidth(), view.getMeasuredHeight());
                }

                public void setAnnotation(int annotation) {
                    this.f2378rf = annotation;
                    m2333p(getContext());
                }

                public void setOnPlusOneClickListener(OnPlusOneClickListener listener) {
                    this.f2380rh = listener;
                    this.f2377re.setOnClickListener(new DefaultOnPlusOneClickListener(listener));
                }

                public void setSize(int size) {
                    this.mSize = size;
                    m2333p(getContext());
                }
            }
