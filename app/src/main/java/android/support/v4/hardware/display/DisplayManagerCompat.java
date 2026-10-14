            package android.support.v4.hardware.display;

            import android.content.Context;
            import android.os.Build;
            import android.view.Display;
            import android.view.WindowManager;
            import java.util.WeakHashMap;
            
            
    public abstract class DisplayManagerCompat {
                public static final String DISPLAY_CATEGORY_PRESENTATION = "android.hardware.display.category.PRESENTATION";
                private static final WeakHashMap<Context, DisplayManagerCompat> sInstances = new WeakHashMap<>();

                public abstract Display getDisplay(int i);

                public abstract Display[] getDisplays();

                public abstract Display[] getDisplays(String str);

        DisplayManagerCompat() {
                }

        public static DisplayManagerCompat getInstance(Context context) {
                    DisplayManagerCompat instance;
                    synchronized (sInstances) {
                        try {
                    instance = sInstances.get(context);
                    if (instance == null) {
                        int version = Build.VERSION.SDK_INT;
                        if (version >= 17) {
                            instance = new JellybeanMr1Impl(context);
                                } else {
                            instance = new LegacyImpl(context);
                                }
                        sInstances.put(context, instance);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return instance;
                }

       private static class LegacyImpl extends DisplayManagerCompat {
                    private final WindowManager mWindowManager;

           public LegacyImpl(Context context) {
               this.mWindowManager = (WindowManager) context.getSystemService("window");
                    }

                    @Override 
           public Display getDisplay(int displayId) {
               Display display = this.mWindowManager.getDefaultDisplay();
               if (display.getDisplayId() == displayId) {
                   return display;
                        }
                        return null;
                    }

                    @Override 
           public Display[] getDisplays() {
               return new Display[]{this.mWindowManager.getDefaultDisplay()};
                    }

                    @Override 
           public Display[] getDisplays(String category) {
                        return category == null ? getDisplays() : new Display[0];
                    }
                }

       private static class JellybeanMr1Impl extends DisplayManagerCompat {
                    private final Object mDisplayManagerObj;

           public JellybeanMr1Impl(Context context) {
               this.mDisplayManagerObj = DisplayManagerJellybeanMr1.getDisplayManager(context);
                    }

                    @Override 
           public Display getDisplay(int displayId) {
               return DisplayManagerJellybeanMr1.getDisplay(this.mDisplayManagerObj, displayId);
                    }

                    @Override 
           public Display[] getDisplays() {
               return DisplayManagerJellybeanMr1.getDisplays(this.mDisplayManagerObj);
                    }

                    @Override 
           public Display[] getDisplays(String category) {
               return DisplayManagerJellybeanMr1.getDisplays(this.mDisplayManagerObj, category);
                    }
                }
            }
