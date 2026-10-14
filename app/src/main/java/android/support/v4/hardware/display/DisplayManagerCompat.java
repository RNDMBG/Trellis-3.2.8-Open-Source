            package android.support.v4.hardware.display;

            import android.content.Context;
            import android.os.Build;
            import android.view.Display;
            import android.view.WindowManager;
            import java.util.WeakHashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 45 */    public abstract class DisplayManagerCompat {
                public static final String DISPLAY_CATEGORY_PRESENTATION = "android.hardware.display.category.PRESENTATION";
                private static final WeakHashMap<Context, DisplayManagerCompat> sInstances = new WeakHashMap<>();

                public abstract Display getDisplay(int i);

                public abstract Display[] getDisplays();

                public abstract Display[] getDisplays(String str);

/* 46 */        DisplayManagerCompat() {
                }

/* 53 */        public static DisplayManagerCompat getInstance(Context context) {
                    DisplayManagerCompat instance;
                    synchronized (sInstances) {
                        try {
/* 55 */                    instance = sInstances.get(context);
/* 56 */                    if (instance == null) {
/* 57 */                        int version = Build.VERSION.SDK_INT;
/* 58 */                        if (version >= 17) {
/* 59 */                            instance = new JellybeanMr1Impl(context);
                                } else {
/* 61 */                            instance = new LegacyImpl(context);
                                }
/* 63 */                        sInstances.put(context, instance);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return instance;
                }

/* 106 */       private static class LegacyImpl extends DisplayManagerCompat {
                    private final WindowManager mWindowManager;

/* 107 */           public LegacyImpl(Context context) {
/* 109 */               this.mWindowManager = (WindowManager) context.getSystemService("window");
                    }

                    @Override // android.support.v4.hardware.display.DisplayManagerCompat
/* 113 */           public Display getDisplay(int displayId) {
/* 114 */               Display display = this.mWindowManager.getDefaultDisplay();
/* 115 */               if (display.getDisplayId() == displayId) {
/* 118 */                   return display;
                        }
                        return null;
                    }

                    @Override // android.support.v4.hardware.display.DisplayManagerCompat
/* 122 */           public Display[] getDisplays() {
/* 123 */               return new Display[]{this.mWindowManager.getDefaultDisplay()};
                    }

                    @Override // android.support.v4.hardware.display.DisplayManagerCompat
/* 127 */           public Display[] getDisplays(String category) {
                        return category == null ? getDisplays() : new Display[0];
                    }
                }

/* 133 */       private static class JellybeanMr1Impl extends DisplayManagerCompat {
                    private final Object mDisplayManagerObj;

/* 134 */           public JellybeanMr1Impl(Context context) {
/* 136 */               this.mDisplayManagerObj = DisplayManagerJellybeanMr1.getDisplayManager(context);
                    }

                    @Override // android.support.v4.hardware.display.DisplayManagerCompat
/* 140 */           public Display getDisplay(int displayId) {
/* 141 */               return DisplayManagerJellybeanMr1.getDisplay(this.mDisplayManagerObj, displayId);
                    }

                    @Override // android.support.v4.hardware.display.DisplayManagerCompat
/* 145 */           public Display[] getDisplays() {
/* 146 */               return DisplayManagerJellybeanMr1.getDisplays(this.mDisplayManagerObj);
                    }

                    @Override // android.support.v4.hardware.display.DisplayManagerCompat
/* 150 */           public Display[] getDisplays(String category) {
/* 151 */               return DisplayManagerJellybeanMr1.getDisplays(this.mDisplayManagerObj, category);
                    }
                }
            }
