            package com.google.android.gms.plus;

            import android.R;
            import android.content.Context;
            import android.content.pm.PackageManager;
            import android.content.res.Resources;
            import android.graphics.Point;
            import android.graphics.drawable.Drawable;
            import android.util.DisplayMetrics;
            import android.util.TypedValue;
            import android.widget.Button;
            import android.widget.FrameLayout;
            import com.google.android.gms.common.GooglePlayServicesUtil;
            
            
            public class PlusOneDummyView extends FrameLayout {
                public static final String TAG = "PlusOneDummyView";

                
                private static class C1198a implements InterfaceC1201d {
                    private Context mContext;

                    private C1198a(Context context) {
                        this.mContext = context;
                    }

                     C1198a(Context context, Object c11971) {
                        this(context);
                    }

                    @Override 
                    public Drawable getDrawable(int size) {
                        return this.mContext.getResources().getDrawable(R.drawable.btn_default);
                    }

                    @Override 
                    public boolean isValid() {
                        return true;
                    }
                }

                
                private static class C1199b implements InterfaceC1201d {
                    private Context mContext;

                    private C1199b(Context context) {
                        this.mContext = context;
                    }

                     C1199b(Context context, Object c11971) {
                        this(context);
                    }

                    @Override 
                    public Drawable getDrawable(int size) {
                        String str;
                        try {
                            Resources resources = this.mContext.createPackageContext(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE, 4).getResources();
                            switch (size) {
                                case 0:
                                    str = "ic_plusone_small";
                                    break;
                                case 1:
                                    str = "ic_plusone_medium";
                                    break;
                                case 2:
                                    str = "ic_plusone_tall";
                                    break;
                                default:
                                    str = "ic_plusone_standard";
                                    break;
                            }
                            return resources.getDrawable(resources.getIdentifier(str, "drawable", GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE));
                        } catch (PackageManager.NameNotFoundException e) {
                            return null;
                        }
                    }

                    @Override 
                    public boolean isValid() {
                        try {
                            this.mContext.createPackageContext(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE, 4).getResources();
                            return true;
                        } catch (PackageManager.NameNotFoundException e) {
                            return false;
                        }
                    }
                }

                
                private static class C1200c implements InterfaceC1201d {
                    private Context mContext;

                    private C1200c(Context context) {
                        this.mContext = context;
                    }

                     C1200c(Context context, Object c11971) {
                        this(context);
                    }

                    @Override 
                    public Drawable getDrawable(int size) {
                        String str;
                        switch (size) {
                            case 0:
                                str = "ic_plusone_small_off_client";
                                break;
                            case 1:
                                str = "ic_plusone_medium_off_client";
                                break;
                            case 2:
                                str = "ic_plusone_tall_off_client";
                                break;
                            default:
                                str = "ic_plusone_standard_off_client";
                                break;
                        }
                        return this.mContext.getResources().getDrawable(this.mContext.getResources().getIdentifier(str, "drawable", this.mContext.getPackageName()));
                    }

                    @Override 
                    public boolean isValid() {
                        return (this.mContext.getResources().getIdentifier("ic_plusone_small_off_client", "drawable", this.mContext.getPackageName()) == 0 || this.mContext.getResources().getIdentifier("ic_plusone_medium_off_client", "drawable", this.mContext.getPackageName()) == 0 || this.mContext.getResources().getIdentifier("ic_plusone_tall_off_client", "drawable", this.mContext.getPackageName()) == 0 || this.mContext.getResources().getIdentifier("ic_plusone_standard_off_client", "drawable", this.mContext.getPackageName()) == 0) ? false : true;
                    }
                }

                
                private interface InterfaceC1201d {
                    Drawable getDrawable(int i);

                    boolean isValid();
                }

                public PlusOneDummyView(Context context, int size) {
                    super(context);
                    Button button = new Button(context);
                    button.setEnabled(false);
                    button.setBackgroundDrawable(m2338cU().getDrawable(size));
                    Point pointM2337ae = m2337ae(size);
                    addView(button, new FrameLayout.LayoutParams(pointM2337ae.x, pointM2337ae.y, 17));
                }

                
                private Point m2337ae(int i) {
                    int i2 = 24;
                    int i3 = 20;
                    Point point = new Point();
                    switch (i) {
                        case 0:
                            i3 = 14;
                            break;
                        case 1:
                            i2 = 32;
                            break;
                        case 2:
                            i2 = 50;
                            break;
                        default:
                            i2 = 38;
                            i3 = 24;
                            break;
                    }
                    DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                    float fApplyDimension = TypedValue.applyDimension(1, i2, displayMetrics);
                    float fApplyDimension2 = TypedValue.applyDimension(1, i3, displayMetrics);
                    point.x = (int) (((double) fApplyDimension) + 0.5d);
                    point.y = (int) (((double) fApplyDimension2) + 0.5d);
                    return point;
                }

                
                private InterfaceC1201d m2338cU() {
                    InterfaceC1201d c1199b = new C1199b(getContext(), null);
                    if (!c1199b.isValid()) {
                        c1199b = new C1200c(getContext(), null);
                    }
                    return !c1199b.isValid() ? new C1198a(getContext(), null) : c1199b;
                }
            }
