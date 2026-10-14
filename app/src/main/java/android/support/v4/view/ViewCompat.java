            package android.support.v4.view;

            import android.graphics.Paint;
            import android.graphics.drawable.Drawable;
            import android.os.Build;
            import android.os.Bundle;
            import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
            import android.support.v4.view.accessibility.AccessibilityNodeProviderCompat;
            import android.view.View;
            import android.view.ViewParent;
            import android.view.accessibility.AccessibilityEvent;
            
            
   public class ViewCompat {
                public static final int ACCESSIBILITY_LIVE_REGION_ASSERTIVE = 2;
                public static final int ACCESSIBILITY_LIVE_REGION_NONE = 0;
                public static final int ACCESSIBILITY_LIVE_REGION_POLITE = 1;
                private static final long FAKE_FRAME_TIME = 10;
                static final ViewCompatImpl IMPL;
                public static final int IMPORTANT_FOR_ACCESSIBILITY_AUTO = 0;
                public static final int IMPORTANT_FOR_ACCESSIBILITY_NO = 2;
                public static final int IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS = 4;
                public static final int IMPORTANT_FOR_ACCESSIBILITY_YES = 1;
                public static final int LAYER_TYPE_HARDWARE = 2;
                public static final int LAYER_TYPE_NONE = 0;
                public static final int LAYER_TYPE_SOFTWARE = 1;
                public static final int LAYOUT_DIRECTION_INHERIT = 2;
                public static final int LAYOUT_DIRECTION_LOCALE = 3;
                public static final int LAYOUT_DIRECTION_LTR = 0;
                public static final int LAYOUT_DIRECTION_RTL = 1;
                public static final int MEASURED_HEIGHT_STATE_SHIFT = 16;
                public static final int MEASURED_SIZE_MASK = 16777215;
                public static final int MEASURED_STATE_MASK = -16777216;
                public static final int MEASURED_STATE_TOO_SMALL = 16777216;
                public static final int OVER_SCROLL_ALWAYS = 0;
                public static final int OVER_SCROLL_IF_CONTENT_SCROLLS = 1;
                public static final int OVER_SCROLL_NEVER = 2;

                interface ViewCompatImpl {
                    boolean canScrollHorizontally(View view, int i);

                    boolean canScrollVertically(View view, int i);

                    int getAccessibilityLiveRegion(View view);

                    AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view);

                    float getAlpha(View view);

                    int getImportantForAccessibility(View view);

                    int getLabelFor(View view);

                    int getLayerType(View view);

                    int getLayoutDirection(View view);

                    int getMeasuredHeightAndState(View view);

                    int getMeasuredState(View view);

                    int getMeasuredWidthAndState(View view);

                    int getOverScrollMode(View view);

                    ViewParent getParentForAccessibility(View view);

                    boolean hasTransientState(View view);

                    boolean isOpaque(View view);

                    void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent);

                    void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat);

                    void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent);

                    boolean performAccessibilityAction(View view, int i, Bundle bundle);

                    void postInvalidateOnAnimation(View view);

                    void postInvalidateOnAnimation(View view, int i, int i2, int i3, int i4);

                    void postOnAnimation(View view, Runnable runnable);

                    void postOnAnimationDelayed(View view, Runnable runnable, long j);

                    int resolveSizeAndState(int i, int i2, int i3);

                    void setAccessibilityDelegate(View view, AccessibilityDelegateCompat accessibilityDelegateCompat);

                    void setAccessibilityLiveRegion(View view, int i);

                    void setHasTransientState(View view, boolean z);

                    void setImportantForAccessibility(View view, int i);

                    void setLabelFor(View view, int i);

                    void setLayerPaint(View view, Paint paint);

                    void setLayerType(View view, int i, Paint paint);

                    void setLayoutDirection(View view, int i);

                    void setOverScrollMode(View view, int i);
                }

       static class BaseViewCompatImpl implements ViewCompatImpl {
           BaseViewCompatImpl() {
                    }

                    @Override 
           public boolean canScrollHorizontally(View v, int direction) {
               return false;
                    }

                    @Override 
           public boolean canScrollVertically(View v, int direction) {
               return false;
                    }

                    @Override 
           public int getOverScrollMode(View v) {
               return 2;
                    }

                    @Override 
           public void setOverScrollMode(View v, int mode) {
                    }

                    @Override 
           public void setAccessibilityDelegate(View v, AccessibilityDelegateCompat delegate) {
                    }

                    @Override 
           public void onPopulateAccessibilityEvent(View v, AccessibilityEvent event) {
                    }

                    @Override 
           public void onInitializeAccessibilityEvent(View v, AccessibilityEvent event) {
                    }

                    @Override 
           public void onInitializeAccessibilityNodeInfo(View v, AccessibilityNodeInfoCompat info) {
                    }

                    @Override 
           public boolean hasTransientState(View view) {
               return false;
                    }

                    @Override 
           public void setHasTransientState(View view, boolean hasTransientState) {
                    }

                    @Override 
           public void postInvalidateOnAnimation(View view) {
               view.postInvalidateDelayed(getFrameTime());
                    }

                    @Override 
           public void postInvalidateOnAnimation(View view, int left, int top, int right, int bottom) {
               view.postInvalidateDelayed(getFrameTime(), left, top, right, bottom);
                    }

                    @Override 
           public void postOnAnimation(View view, Runnable action) {
               view.postDelayed(action, getFrameTime());
                    }

                    @Override 
           public void postOnAnimationDelayed(View view, Runnable action, long delayMillis) {
               view.postDelayed(action, getFrameTime() + delayMillis);
                    }

           long getFrameTime() {
               return ViewCompat.FAKE_FRAME_TIME;
                    }

                    @Override 
           public int getImportantForAccessibility(View view) {
               return 0;
                    }

                    @Override 
           public void setImportantForAccessibility(View view, int mode) {
                    }

                    @Override 
           public boolean performAccessibilityAction(View view, int action, Bundle arguments) {
               return false;
                    }

                    @Override 
           public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
               return null;
                    }

                    @Override 
           public float getAlpha(View view) {
               return 1.0f;
                    }

                    @Override 
           public void setLayerType(View view, int layerType, Paint paint) {
                    }

                    @Override 
           public int getLayerType(View view) {
               return 0;
                    }

                    @Override 
           public int getLabelFor(View view) {
               return 0;
                    }

                    @Override 
           public void setLabelFor(View view, int id) {
                    }

                    @Override 
           public void setLayerPaint(View view, Paint p) {
                    }

                    @Override 
           public int getLayoutDirection(View view) {
               return 0;
                    }

                    @Override 
           public void setLayoutDirection(View view, int layoutDirection) {
                    }

                    @Override 
           public ViewParent getParentForAccessibility(View view) {
               return view.getParent();
                    }

                    @Override 
           public boolean isOpaque(View view) {
               Drawable bg = view.getBackground();
                        return bg != null && bg.getOpacity() == -1;
                    }

                    @Override 
           public int resolveSizeAndState(int size, int measureSpec, int childMeasuredState) {
               return View.resolveSize(size, measureSpec);
                    }

                    @Override 
           public int getMeasuredWidthAndState(View view) {
               return view.getMeasuredWidth();
                    }

                    @Override 
           public int getMeasuredHeightAndState(View view) {
               return view.getMeasuredHeight();
                    }

                    @Override 
           public int getMeasuredState(View view) {
               return 0;
                    }

                    @Override 
           public int getAccessibilityLiveRegion(View view) {
               return 0;
                    }

                    @Override 
           public void setAccessibilityLiveRegion(View view, int mode) {
                    }
                }

       static class EclairMr1ViewCompatImpl extends BaseViewCompatImpl {
           EclairMr1ViewCompatImpl() {
                    }

                    @Override 
           public boolean isOpaque(View view) {
               return ViewCompatEclairMr1.isOpaque(view);
                    }
                }

       static class GBViewCompatImpl extends EclairMr1ViewCompatImpl {
           GBViewCompatImpl() {
                    }

                    @Override 
           public int getOverScrollMode(View v) {
               return ViewCompatGingerbread.getOverScrollMode(v);
                    }

                    @Override 
           public void setOverScrollMode(View v, int mode) {
               ViewCompatGingerbread.setOverScrollMode(v, mode);
                    }
                }

       static class HCViewCompatImpl extends GBViewCompatImpl {
           HCViewCompatImpl() {
                    }

                    @Override 
           long getFrameTime() {
               return ViewCompatHC.getFrameTime();
                    }

                    @Override 
           public float getAlpha(View view) {
               return ViewCompatHC.getAlpha(view);
                    }

                    @Override 
           public void setLayerType(View view, int layerType, Paint paint) {
               ViewCompatHC.setLayerType(view, layerType, paint);
                    }

                    @Override 
           public int getLayerType(View view) {
               return ViewCompatHC.getLayerType(view);
                    }

                    @Override 
           public void setLayerPaint(View view, Paint paint) {
               setLayerType(view, getLayerType(view), paint);
               view.invalidate();
                    }

                    @Override 
           public int resolveSizeAndState(int size, int measureSpec, int childMeasuredState) {
               return ViewCompatHC.resolveSizeAndState(size, measureSpec, childMeasuredState);
                    }

                    @Override 
           public int getMeasuredWidthAndState(View view) {
               return ViewCompatHC.getMeasuredWidthAndState(view);
                    }

                    @Override 
           public int getMeasuredHeightAndState(View view) {
               return ViewCompatHC.getMeasuredHeightAndState(view);
                    }

                    @Override 
           public int getMeasuredState(View view) {
               return ViewCompatHC.getMeasuredState(view);
                    }
                }

       static class ICSViewCompatImpl extends HCViewCompatImpl {
           ICSViewCompatImpl() {
                    }

                    @Override 
           public boolean canScrollHorizontally(View v, int direction) {
               return ViewCompatICS.canScrollHorizontally(v, direction);
                    }

                    @Override 
           public boolean canScrollVertically(View v, int direction) {
               return ViewCompatICS.canScrollVertically(v, direction);
                    }

                    @Override 
           public void onPopulateAccessibilityEvent(View v, AccessibilityEvent event) {
               ViewCompatICS.onPopulateAccessibilityEvent(v, event);
                    }

                    @Override 
           public void onInitializeAccessibilityEvent(View v, AccessibilityEvent event) {
               ViewCompatICS.onInitializeAccessibilityEvent(v, event);
                    }

                    @Override 
           public void onInitializeAccessibilityNodeInfo(View v, AccessibilityNodeInfoCompat info) {
               ViewCompatICS.onInitializeAccessibilityNodeInfo(v, info.getInfo());
                    }

                    @Override 
           public void setAccessibilityDelegate(View v, AccessibilityDelegateCompat delegate) {
               ViewCompatICS.setAccessibilityDelegate(v, delegate.getBridge());
                    }
                }

       static class JBViewCompatImpl extends ICSViewCompatImpl {
           JBViewCompatImpl() {
                    }

                    @Override 
           public boolean hasTransientState(View view) {
               return ViewCompatJB.hasTransientState(view);
                    }

                    @Override 
           public void setHasTransientState(View view, boolean hasTransientState) {
               ViewCompatJB.setHasTransientState(view, hasTransientState);
                    }

                    @Override 
           public void postInvalidateOnAnimation(View view) {
               ViewCompatJB.postInvalidateOnAnimation(view);
                    }

                    @Override 
           public void postInvalidateOnAnimation(View view, int left, int top, int right, int bottom) {
               ViewCompatJB.postInvalidateOnAnimation(view, left, top, right, bottom);
                    }

                    @Override 
           public void postOnAnimation(View view, Runnable action) {
               ViewCompatJB.postOnAnimation(view, action);
                    }

                    @Override 
           public void postOnAnimationDelayed(View view, Runnable action, long delayMillis) {
               ViewCompatJB.postOnAnimationDelayed(view, action, delayMillis);
                    }

                    @Override 
           public int getImportantForAccessibility(View view) {
               return ViewCompatJB.getImportantForAccessibility(view);
                    }

                    @Override 
           public void setImportantForAccessibility(View view, int mode) {
               ViewCompatJB.setImportantForAccessibility(view, mode);
                    }

                    @Override 
           public boolean performAccessibilityAction(View view, int action, Bundle arguments) {
               return ViewCompatJB.performAccessibilityAction(view, action, arguments);
                    }

                    @Override 
           public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
               Object compat = ViewCompatJB.getAccessibilityNodeProvider(view);
               if (compat != null) {
                   return new AccessibilityNodeProviderCompat(compat);
                        }
                        return null;
                    }

                    @Override 
           public ViewParent getParentForAccessibility(View view) {
               return ViewCompatJB.getParentForAccessibility(view);
                    }
                }

       static class JbMr1ViewCompatImpl extends JBViewCompatImpl {
           JbMr1ViewCompatImpl() {
                    }

                    @Override 
           public int getLabelFor(View view) {
               return ViewCompatJellybeanMr1.getLabelFor(view);
                    }

                    @Override 
           public void setLabelFor(View view, int id) {
               ViewCompatJellybeanMr1.setLabelFor(view, id);
                    }

                    @Override 
           public void setLayerPaint(View view, Paint paint) {
               ViewCompatJellybeanMr1.setLayerPaint(view, paint);
                    }

                    @Override 
           public int getLayoutDirection(View view) {
               return ViewCompatJellybeanMr1.getLayoutDirection(view);
                    }

                    @Override 
           public void setLayoutDirection(View view, int layoutDirection) {
               ViewCompatJellybeanMr1.setLayoutDirection(view, layoutDirection);
                    }
                }

       static class KitKatViewCompatImpl extends JbMr1ViewCompatImpl {
           KitKatViewCompatImpl() {
                    }

                    @Override 
           public int getAccessibilityLiveRegion(View view) {
               return ViewCompatKitKat.getAccessibilityLiveRegion(view);
                    }

                    @Override 
           public void setAccessibilityLiveRegion(View view, int mode) {
               ViewCompatKitKat.setAccessibilityLiveRegion(view, mode);
                    }
                }

                static {
           int version = Build.VERSION.SDK_INT;
           if (version >= 19) {
               IMPL = new KitKatViewCompatImpl();
               return;
                    }
           if (version >= 17) {
               IMPL = new JbMr1ViewCompatImpl();
               return;
                    }
           if (version >= 16) {
               IMPL = new JBViewCompatImpl();
               return;
                    }
           if (version >= 14) {
               IMPL = new ICSViewCompatImpl();
               return;
                    }
           if (version >= 11) {
               IMPL = new HCViewCompatImpl();
                    } else if (version >= 9) {
               IMPL = new GBViewCompatImpl();
                    } else {
               IMPL = new BaseViewCompatImpl();
                    }
                }

       public static boolean canScrollHorizontally(View v, int direction) {
           return IMPL.canScrollHorizontally(v, direction);
                }

       public static boolean canScrollVertically(View v, int direction) {
           return IMPL.canScrollVertically(v, direction);
                }

       public static int getOverScrollMode(View v) {
           return IMPL.getOverScrollMode(v);
                }

       public static void setOverScrollMode(View v, int overScrollMode) {
           IMPL.setOverScrollMode(v, overScrollMode);
                }

       public static void onPopulateAccessibilityEvent(View v, AccessibilityEvent event) {
           IMPL.onPopulateAccessibilityEvent(v, event);
                }

       public static void onInitializeAccessibilityEvent(View v, AccessibilityEvent event) {
           IMPL.onInitializeAccessibilityEvent(v, event);
                }

       public static void onInitializeAccessibilityNodeInfo(View v, AccessibilityNodeInfoCompat info) {
           IMPL.onInitializeAccessibilityNodeInfo(v, info);
                }

       public static void setAccessibilityDelegate(View v, AccessibilityDelegateCompat delegate) {
           IMPL.setAccessibilityDelegate(v, delegate);
                }

       public static boolean hasTransientState(View view) {
           return IMPL.hasTransientState(view);
                }

       public static void setHasTransientState(View view, boolean hasTransientState) {
           IMPL.setHasTransientState(view, hasTransientState);
                }

       public static void postInvalidateOnAnimation(View view) {
           IMPL.postInvalidateOnAnimation(view);
                }

       public static void postInvalidateOnAnimation(View view, int left, int top, int right, int bottom) {
           IMPL.postInvalidateOnAnimation(view, left, top, right, bottom);
                }

       public static void postOnAnimation(View view, Runnable action) {
           IMPL.postOnAnimation(view, action);
                }

       public static void postOnAnimationDelayed(View view, Runnable action, long delayMillis) {
           IMPL.postOnAnimationDelayed(view, action, delayMillis);
                }

       public static int getImportantForAccessibility(View view) {
           return IMPL.getImportantForAccessibility(view);
                }

       public static void setImportantForAccessibility(View view, int mode) {
           IMPL.setImportantForAccessibility(view, mode);
                }

       public static boolean performAccessibilityAction(View view, int action, Bundle arguments) {
           return IMPL.performAccessibilityAction(view, action, arguments);
                }

       public static AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
           return IMPL.getAccessibilityNodeProvider(view);
                }

       public static float getAlpha(View view) {
           return IMPL.getAlpha(view);
                }

       public static void setLayerType(View view, int layerType, Paint paint) {
           IMPL.setLayerType(view, layerType, paint);
                }

       public static int getLayerType(View view) {
           return IMPL.getLayerType(view);
                }

       public static int getLabelFor(View view) {
           return IMPL.getLabelFor(view);
                }

       public static void setLabelFor(View view, int labeledId) {
           IMPL.setLabelFor(view, labeledId);
                }

      public static void setLayerPaint(View view, Paint paint) {
          IMPL.setLayerPaint(view, paint);
                }

      public static int getLayoutDirection(View view) {
          return IMPL.getLayoutDirection(view);
                }

      public static void setLayoutDirection(View view, int layoutDirection) {
          IMPL.setLayoutDirection(view, layoutDirection);
                }

      public static ViewParent getParentForAccessibility(View view) {
          return IMPL.getParentForAccessibility(view);
                }

      public static boolean isOpaque(View view) {
          return IMPL.isOpaque(view);
                }

      public static int resolveSizeAndState(int size, int measureSpec, int childMeasuredState) {
          return IMPL.resolveSizeAndState(size, measureSpec, childMeasuredState);
                }

      public static int getMeasuredWidthAndState(View view) {
          return IMPL.getMeasuredWidthAndState(view);
                }

      public static int getMeasuredHeightAndState(View view) {
          return IMPL.getMeasuredHeightAndState(view);
                }

      public static int getMeasuredState(View view) {
          return IMPL.getMeasuredState(view);
                }

      public int getAccessibilityLiveRegion(View view) {
          return IMPL.getAccessibilityLiveRegion(view);
                }

      public void setAccessibilityLiveRegion(View view, int mode) {
          IMPL.setAccessibilityLiveRegion(view, mode);
                }
            }
