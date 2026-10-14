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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 231 */   public class ViewCompat {
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

/* 232 */       static class BaseViewCompatImpl implements ViewCompatImpl {
/* 233 */           BaseViewCompatImpl() {
                    }

                    @Override // android.support.v4.view.ViewCompat.ViewCompatImpl
/* 235 */           public boolean canScrollHorizontally(View v, int direction) {
/* 236 */               return false;
                    }

                    @Override // android.support.v4.view.ViewCompat.ViewCompatImpl
/* 238 */           public boolean canScrollVertically(View v, int direction) {
/* 239 */               return false;
                    }

                    @Override // android.support.v4.view.ViewCompat.ViewCompatImpl
/* 241 */           public int getOverScrollMode(View v) {
/* 242 */               return 2;
                    }

                    @Override // android.support.v4.view.ViewCompat.ViewCompatImpl
/* 245 */           public void setOverScrollMode(View v, int mode) {
                    }

                    @Override // android.support.v4.view.ViewCompat.ViewCompatImpl
/* 248 */           public void setAccessibilityDelegate(View v, AccessibilityDelegateCompat delegate) {
                    }

                    @Override // android.support.v4.view.ViewCompat.ViewCompatImpl
/* 251 */           public void onPopulateAccessibilityEvent(View v, AccessibilityEvent event) {
                    }

                    @Override // android.support.v4.view.ViewCompat.ViewCompatImpl
/* 254 */           public void onInitializeAccessibilityEvent(View v, AccessibilityEvent event) {
                    }

                    @Override // android.support.v4.view.ViewCompat.ViewCompatImpl
/* 257 */           public void onInitializeAccessibilityNodeInfo(View v, AccessibilityNodeInfoCompat info) {
                    }

                    @Override // android.support.v4.view.ViewCompat.ViewCompatImpl
/* 260 */           public boolean hasTransientState(View view) {
/* 261 */               return false;
                    }

                    @Override // android.support.v4.view.ViewCompat.ViewCompatImpl
/* 264 */           public void setHasTransientState(View view, boolean hasTransientState) {
                    }

                    @Override // android.support.v4.view.ViewCompat.ViewCompatImpl
/* 266 */           public void postInvalidateOnAnimation(View view) {
/* 267 */               view.postInvalidateDelayed(getFrameTime());
                    }

                    @Override // android.support.v4.view.ViewCompat.ViewCompatImpl
/* 269 */           public void postInvalidateOnAnimation(View view, int left, int top, int right, int bottom) {
/* 270 */               view.postInvalidateDelayed(getFrameTime(), left, top, right, bottom);
                    }

                    @Override // android.support.v4.view.ViewCompat.ViewCompatImpl
/* 272 */           public void postOnAnimation(View view, Runnable action) {
/* 273 */               view.postDelayed(action, getFrameTime());
                    }

                    @Override // android.support.v4.view.ViewCompat.ViewCompatImpl
/* 275 */           public void postOnAnimationDelayed(View view, Runnable action, long delayMillis) {
/* 276 */               view.postDelayed(action, getFrameTime() + delayMillis);
                    }

/* 278 */           long getFrameTime() {
/* 279 */               return ViewCompat.FAKE_FRAME_TIME;
                    }

                    @Override // android.support.v4.view.ViewCompat.ViewCompatImpl
/* 281 */           public int getImportantForAccessibility(View view) {
/* 282 */               return 0;
                    }

                    @Override // android.support.v4.view.ViewCompat.ViewCompatImpl
/* 285 */           public void setImportantForAccessibility(View view, int mode) {
                    }

                    @Override // android.support.v4.view.ViewCompat.ViewCompatImpl
/* 287 */           public boolean performAccessibilityAction(View view, int action, Bundle arguments) {
/* 288 */               return false;
                    }

                    @Override // android.support.v4.view.ViewCompat.ViewCompatImpl
/* 290 */           public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
/* 291 */               return null;
                    }

                    @Override // android.support.v4.view.ViewCompat.ViewCompatImpl
/* 293 */           public float getAlpha(View view) {
/* 294 */               return 1.0f;
                    }

                    @Override // android.support.v4.view.ViewCompat.ViewCompatImpl
/* 297 */           public void setLayerType(View view, int layerType, Paint paint) {
                    }

                    @Override // android.support.v4.view.ViewCompat.ViewCompatImpl
/* 299 */           public int getLayerType(View view) {
/* 300 */               return 0;
                    }

                    @Override // android.support.v4.view.ViewCompat.ViewCompatImpl
/* 302 */           public int getLabelFor(View view) {
/* 303 */               return 0;
                    }

                    @Override // android.support.v4.view.ViewCompat.ViewCompatImpl
/* 306 */           public void setLabelFor(View view, int id) {
                    }

                    @Override // android.support.v4.view.ViewCompat.ViewCompatImpl
/* 309 */           public void setLayerPaint(View view, Paint p) {
                    }

                    @Override // android.support.v4.view.ViewCompat.ViewCompatImpl
/* 313 */           public int getLayoutDirection(View view) {
/* 314 */               return 0;
                    }

                    @Override // android.support.v4.view.ViewCompat.ViewCompatImpl
/* 319 */           public void setLayoutDirection(View view, int layoutDirection) {
                    }

                    @Override // android.support.v4.view.ViewCompat.ViewCompatImpl
/* 323 */           public ViewParent getParentForAccessibility(View view) {
/* 324 */               return view.getParent();
                    }

                    @Override // android.support.v4.view.ViewCompat.ViewCompatImpl
/* 328 */           public boolean isOpaque(View view) {
/* 329 */               Drawable bg = view.getBackground();
                        return bg != null && bg.getOpacity() == -1;
                    }

                    @Override // android.support.v4.view.ViewCompat.ViewCompatImpl
/* 336 */           public int resolveSizeAndState(int size, int measureSpec, int childMeasuredState) {
/* 337 */               return View.resolveSize(size, measureSpec);
                    }

                    @Override // android.support.v4.view.ViewCompat.ViewCompatImpl
/* 341 */           public int getMeasuredWidthAndState(View view) {
/* 342 */               return view.getMeasuredWidth();
                    }

                    @Override // android.support.v4.view.ViewCompat.ViewCompatImpl
/* 346 */           public int getMeasuredHeightAndState(View view) {
/* 347 */               return view.getMeasuredHeight();
                    }

                    @Override // android.support.v4.view.ViewCompat.ViewCompatImpl
/* 351 */           public int getMeasuredState(View view) {
/* 352 */               return 0;
                    }

                    @Override // android.support.v4.view.ViewCompat.ViewCompatImpl
/* 356 */           public int getAccessibilityLiveRegion(View view) {
/* 357 */               return 0;
                    }

                    @Override // android.support.v4.view.ViewCompat.ViewCompatImpl
/* 362 */           public void setAccessibilityLiveRegion(View view, int mode) {
                    }
                }

/* 364 */       static class EclairMr1ViewCompatImpl extends BaseViewCompatImpl {
/* 365 */           EclairMr1ViewCompatImpl() {
                    }

                    @Override // android.support.v4.view.ViewCompat.BaseViewCompatImpl, android.support.v4.view.ViewCompat.ViewCompatImpl
/* 368 */           public boolean isOpaque(View view) {
/* 369 */               return ViewCompatEclairMr1.isOpaque(view);
                    }
                }

/* 371 */       static class GBViewCompatImpl extends EclairMr1ViewCompatImpl {
/* 372 */           GBViewCompatImpl() {
                    }

                    @Override // android.support.v4.view.ViewCompat.BaseViewCompatImpl, android.support.v4.view.ViewCompat.ViewCompatImpl
/* 375 */           public int getOverScrollMode(View v) {
/* 376 */               return ViewCompatGingerbread.getOverScrollMode(v);
                    }

                    @Override // android.support.v4.view.ViewCompat.BaseViewCompatImpl, android.support.v4.view.ViewCompat.ViewCompatImpl
/* 379 */           public void setOverScrollMode(View v, int mode) {
/* 380 */               ViewCompatGingerbread.setOverScrollMode(v, mode);
                    }
                }

/* 382 */       static class HCViewCompatImpl extends GBViewCompatImpl {
/* 383 */           HCViewCompatImpl() {
                    }

                    @Override // android.support.v4.view.ViewCompat.BaseViewCompatImpl
/* 386 */           long getFrameTime() {
/* 387 */               return ViewCompatHC.getFrameTime();
                    }

                    @Override // android.support.v4.view.ViewCompat.BaseViewCompatImpl, android.support.v4.view.ViewCompat.ViewCompatImpl
/* 390 */           public float getAlpha(View view) {
/* 391 */               return ViewCompatHC.getAlpha(view);
                    }

                    @Override // android.support.v4.view.ViewCompat.BaseViewCompatImpl, android.support.v4.view.ViewCompat.ViewCompatImpl
/* 394 */           public void setLayerType(View view, int layerType, Paint paint) {
/* 395 */               ViewCompatHC.setLayerType(view, layerType, paint);
                    }

                    @Override // android.support.v4.view.ViewCompat.BaseViewCompatImpl, android.support.v4.view.ViewCompat.ViewCompatImpl
/* 398 */           public int getLayerType(View view) {
/* 399 */               return ViewCompatHC.getLayerType(view);
                    }

                    @Override // android.support.v4.view.ViewCompat.BaseViewCompatImpl, android.support.v4.view.ViewCompat.ViewCompatImpl
/* 404 */           public void setLayerPaint(View view, Paint paint) {
/* 405 */               setLayerType(view, getLayerType(view), paint);
/* 407 */               view.invalidate();
                    }

                    @Override // android.support.v4.view.ViewCompat.BaseViewCompatImpl, android.support.v4.view.ViewCompat.ViewCompatImpl
/* 410 */           public int resolveSizeAndState(int size, int measureSpec, int childMeasuredState) {
/* 411 */               return ViewCompatHC.resolveSizeAndState(size, measureSpec, childMeasuredState);
                    }

                    @Override // android.support.v4.view.ViewCompat.BaseViewCompatImpl, android.support.v4.view.ViewCompat.ViewCompatImpl
/* 414 */           public int getMeasuredWidthAndState(View view) {
/* 415 */               return ViewCompatHC.getMeasuredWidthAndState(view);
                    }

                    @Override // android.support.v4.view.ViewCompat.BaseViewCompatImpl, android.support.v4.view.ViewCompat.ViewCompatImpl
/* 418 */           public int getMeasuredHeightAndState(View view) {
/* 419 */               return ViewCompatHC.getMeasuredHeightAndState(view);
                    }

                    @Override // android.support.v4.view.ViewCompat.BaseViewCompatImpl, android.support.v4.view.ViewCompat.ViewCompatImpl
/* 422 */           public int getMeasuredState(View view) {
/* 423 */               return ViewCompatHC.getMeasuredState(view);
                    }
                }

/* 425 */       static class ICSViewCompatImpl extends HCViewCompatImpl {
/* 426 */           ICSViewCompatImpl() {
                    }

                    @Override // android.support.v4.view.ViewCompat.BaseViewCompatImpl, android.support.v4.view.ViewCompat.ViewCompatImpl
/* 429 */           public boolean canScrollHorizontally(View v, int direction) {
/* 430 */               return ViewCompatICS.canScrollHorizontally(v, direction);
                    }

                    @Override // android.support.v4.view.ViewCompat.BaseViewCompatImpl, android.support.v4.view.ViewCompat.ViewCompatImpl
/* 433 */           public boolean canScrollVertically(View v, int direction) {
/* 434 */               return ViewCompatICS.canScrollVertically(v, direction);
                    }

                    @Override // android.support.v4.view.ViewCompat.BaseViewCompatImpl, android.support.v4.view.ViewCompat.ViewCompatImpl
/* 437 */           public void onPopulateAccessibilityEvent(View v, AccessibilityEvent event) {
/* 438 */               ViewCompatICS.onPopulateAccessibilityEvent(v, event);
                    }

                    @Override // android.support.v4.view.ViewCompat.BaseViewCompatImpl, android.support.v4.view.ViewCompat.ViewCompatImpl
/* 441 */           public void onInitializeAccessibilityEvent(View v, AccessibilityEvent event) {
/* 442 */               ViewCompatICS.onInitializeAccessibilityEvent(v, event);
                    }

                    @Override // android.support.v4.view.ViewCompat.BaseViewCompatImpl, android.support.v4.view.ViewCompat.ViewCompatImpl
/* 445 */           public void onInitializeAccessibilityNodeInfo(View v, AccessibilityNodeInfoCompat info) {
/* 446 */               ViewCompatICS.onInitializeAccessibilityNodeInfo(v, info.getInfo());
                    }

                    @Override // android.support.v4.view.ViewCompat.BaseViewCompatImpl, android.support.v4.view.ViewCompat.ViewCompatImpl
/* 449 */           public void setAccessibilityDelegate(View v, AccessibilityDelegateCompat delegate) {
/* 450 */               ViewCompatICS.setAccessibilityDelegate(v, delegate.getBridge());
                    }
                }

/* 452 */       static class JBViewCompatImpl extends ICSViewCompatImpl {
/* 453 */           JBViewCompatImpl() {
                    }

                    @Override // android.support.v4.view.ViewCompat.BaseViewCompatImpl, android.support.v4.view.ViewCompat.ViewCompatImpl
/* 456 */           public boolean hasTransientState(View view) {
/* 457 */               return ViewCompatJB.hasTransientState(view);
                    }

                    @Override // android.support.v4.view.ViewCompat.BaseViewCompatImpl, android.support.v4.view.ViewCompat.ViewCompatImpl
/* 460 */           public void setHasTransientState(View view, boolean hasTransientState) {
/* 461 */               ViewCompatJB.setHasTransientState(view, hasTransientState);
                    }

                    @Override // android.support.v4.view.ViewCompat.BaseViewCompatImpl, android.support.v4.view.ViewCompat.ViewCompatImpl
/* 464 */           public void postInvalidateOnAnimation(View view) {
/* 465 */               ViewCompatJB.postInvalidateOnAnimation(view);
                    }

                    @Override // android.support.v4.view.ViewCompat.BaseViewCompatImpl, android.support.v4.view.ViewCompat.ViewCompatImpl
/* 468 */           public void postInvalidateOnAnimation(View view, int left, int top, int right, int bottom) {
/* 469 */               ViewCompatJB.postInvalidateOnAnimation(view, left, top, right, bottom);
                    }

                    @Override // android.support.v4.view.ViewCompat.BaseViewCompatImpl, android.support.v4.view.ViewCompat.ViewCompatImpl
/* 472 */           public void postOnAnimation(View view, Runnable action) {
/* 473 */               ViewCompatJB.postOnAnimation(view, action);
                    }

                    @Override // android.support.v4.view.ViewCompat.BaseViewCompatImpl, android.support.v4.view.ViewCompat.ViewCompatImpl
/* 476 */           public void postOnAnimationDelayed(View view, Runnable action, long delayMillis) {
/* 477 */               ViewCompatJB.postOnAnimationDelayed(view, action, delayMillis);
                    }

                    @Override // android.support.v4.view.ViewCompat.BaseViewCompatImpl, android.support.v4.view.ViewCompat.ViewCompatImpl
/* 480 */           public int getImportantForAccessibility(View view) {
/* 481 */               return ViewCompatJB.getImportantForAccessibility(view);
                    }

                    @Override // android.support.v4.view.ViewCompat.BaseViewCompatImpl, android.support.v4.view.ViewCompat.ViewCompatImpl
/* 484 */           public void setImportantForAccessibility(View view, int mode) {
/* 485 */               ViewCompatJB.setImportantForAccessibility(view, mode);
                    }

                    @Override // android.support.v4.view.ViewCompat.BaseViewCompatImpl, android.support.v4.view.ViewCompat.ViewCompatImpl
/* 488 */           public boolean performAccessibilityAction(View view, int action, Bundle arguments) {
/* 489 */               return ViewCompatJB.performAccessibilityAction(view, action, arguments);
                    }

                    @Override // android.support.v4.view.ViewCompat.BaseViewCompatImpl, android.support.v4.view.ViewCompat.ViewCompatImpl
/* 492 */           public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
/* 493 */               Object compat = ViewCompatJB.getAccessibilityNodeProvider(view);
/* 494 */               if (compat != null) {
/* 495 */                   return new AccessibilityNodeProviderCompat(compat);
                        }
                        return null;
                    }

                    @Override // android.support.v4.view.ViewCompat.BaseViewCompatImpl, android.support.v4.view.ViewCompat.ViewCompatImpl
/* 501 */           public ViewParent getParentForAccessibility(View view) {
/* 502 */               return ViewCompatJB.getParentForAccessibility(view);
                    }
                }

/* 504 */       static class JbMr1ViewCompatImpl extends JBViewCompatImpl {
/* 505 */           JbMr1ViewCompatImpl() {
                    }

                    @Override // android.support.v4.view.ViewCompat.BaseViewCompatImpl, android.support.v4.view.ViewCompat.ViewCompatImpl
/* 509 */           public int getLabelFor(View view) {
/* 510 */               return ViewCompatJellybeanMr1.getLabelFor(view);
                    }

                    @Override // android.support.v4.view.ViewCompat.BaseViewCompatImpl, android.support.v4.view.ViewCompat.ViewCompatImpl
/* 514 */           public void setLabelFor(View view, int id) {
/* 515 */               ViewCompatJellybeanMr1.setLabelFor(view, id);
                    }

                    @Override // android.support.v4.view.ViewCompat.HCViewCompatImpl, android.support.v4.view.ViewCompat.BaseViewCompatImpl, android.support.v4.view.ViewCompat.ViewCompatImpl
/* 519 */           public void setLayerPaint(View view, Paint paint) {
/* 520 */               ViewCompatJellybeanMr1.setLayerPaint(view, paint);
                    }

                    @Override // android.support.v4.view.ViewCompat.BaseViewCompatImpl, android.support.v4.view.ViewCompat.ViewCompatImpl
/* 524 */           public int getLayoutDirection(View view) {
/* 525 */               return ViewCompatJellybeanMr1.getLayoutDirection(view);
                    }

                    @Override // android.support.v4.view.ViewCompat.BaseViewCompatImpl, android.support.v4.view.ViewCompat.ViewCompatImpl
/* 529 */           public void setLayoutDirection(View view, int layoutDirection) {
/* 530 */               ViewCompatJellybeanMr1.setLayoutDirection(view, layoutDirection);
                    }
                }

/* 532 */       static class KitKatViewCompatImpl extends JbMr1ViewCompatImpl {
/* 533 */           KitKatViewCompatImpl() {
                    }

                    @Override // android.support.v4.view.ViewCompat.BaseViewCompatImpl, android.support.v4.view.ViewCompat.ViewCompatImpl
/* 536 */           public int getAccessibilityLiveRegion(View view) {
/* 537 */               return ViewCompatKitKat.getAccessibilityLiveRegion(view);
                    }

                    @Override // android.support.v4.view.ViewCompat.BaseViewCompatImpl, android.support.v4.view.ViewCompat.ViewCompatImpl
/* 541 */           public void setAccessibilityLiveRegion(View view, int mode) {
/* 542 */               ViewCompatKitKat.setAccessibilityLiveRegion(view, mode);
                    }
                }

                static {
/* 548 */           int version = Build.VERSION.SDK_INT;
/* 549 */           if (version >= 19) {
/* 550 */               IMPL = new KitKatViewCompatImpl();
/* 564 */               return;
                    }
/* 551 */           if (version >= 17) {
/* 552 */               IMPL = new JbMr1ViewCompatImpl();
/* 564 */               return;
                    }
/* 553 */           if (version >= 16) {
/* 554 */               IMPL = new JBViewCompatImpl();
/* 564 */               return;
                    }
/* 555 */           if (version >= 14) {
/* 556 */               IMPL = new ICSViewCompatImpl();
/* 564 */               return;
                    }
/* 557 */           if (version >= 11) {
/* 558 */               IMPL = new HCViewCompatImpl();
                    } else if (version >= 9) {
/* 560 */               IMPL = new GBViewCompatImpl();
                    } else {
/* 562 */               IMPL = new BaseViewCompatImpl();
                    }
                }

/* 573 */       public static boolean canScrollHorizontally(View v, int direction) {
/* 574 */           return IMPL.canScrollHorizontally(v, direction);
                }

/* 584 */       public static boolean canScrollVertically(View v, int direction) {
/* 585 */           return IMPL.canScrollVertically(v, direction);
                }

/* 597 */       public static int getOverScrollMode(View v) {
/* 598 */           return IMPL.getOverScrollMode(v);
                }

/* 613 */       public static void setOverScrollMode(View v, int overScrollMode) {
/* 614 */           IMPL.setOverScrollMode(v, overScrollMode);
                }

/* 650 */       public static void onPopulateAccessibilityEvent(View v, AccessibilityEvent event) {
/* 651 */           IMPL.onPopulateAccessibilityEvent(v, event);
                }

/* 683 */       public static void onInitializeAccessibilityEvent(View v, AccessibilityEvent event) {
/* 684 */           IMPL.onInitializeAccessibilityEvent(v, event);
                }

/* 719 */       public static void onInitializeAccessibilityNodeInfo(View v, AccessibilityNodeInfoCompat info) {
/* 720 */           IMPL.onInitializeAccessibilityNodeInfo(v, info);
                }

/* 734 */       public static void setAccessibilityDelegate(View v, AccessibilityDelegateCompat delegate) {
/* 735 */           IMPL.setAccessibilityDelegate(v, delegate);
                }

/* 746 */       public static boolean hasTransientState(View view) {
/* 747 */           return IMPL.hasTransientState(view);
                }

/* 757 */       public static void setHasTransientState(View view, boolean hasTransientState) {
/* 758 */           IMPL.setHasTransientState(view, hasTransientState);
                }

/* 770 */       public static void postInvalidateOnAnimation(View view) {
/* 771 */           IMPL.postInvalidateOnAnimation(view);
                }

/* 788 */       public static void postInvalidateOnAnimation(View view, int left, int top, int right, int bottom) {
/* 789 */           IMPL.postInvalidateOnAnimation(view, left, top, right, bottom);
                }

/* 802 */       public static void postOnAnimation(View view, Runnable action) {
/* 803 */           IMPL.postOnAnimation(view, action);
                }

/* 819 */       public static void postOnAnimationDelayed(View view, Runnable action, long delayMillis) {
/* 820 */           IMPL.postOnAnimationDelayed(view, action, delayMillis);
                }

/* 836 */       public static int getImportantForAccessibility(View view) {
/* 837 */           return IMPL.getImportantForAccessibility(view);
                }

/* 853 */       public static void setImportantForAccessibility(View view, int mode) {
/* 854 */           IMPL.setImportantForAccessibility(view, mode);
                }

/* 871 */       public static boolean performAccessibilityAction(View view, int action, Bundle arguments) {
/* 872 */           return IMPL.performAccessibilityAction(view, action, arguments);
                }

/* 898 */       public static AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
/* 899 */           return IMPL.getAccessibilityNodeProvider(view);
                }

/* 909 */       public static float getAlpha(View view) {
/* 910 */           return IMPL.getAlpha(view);
                }

/* 952 */       public static void setLayerType(View view, int layerType, Paint paint) {
/* 953 */           IMPL.setLayerType(view, layerType, paint);
                }

/* 972 */       public static int getLayerType(View view) {
/* 973 */           return IMPL.getLayerType(view);
                }

/* 983 */       public static int getLabelFor(View view) {
/* 984 */           return IMPL.getLabelFor(view);
                }

/* 994 */       public static void setLabelFor(View view, int labeledId) {
/* 995 */           IMPL.setLabelFor(view, labeledId);
                }

/* 1028 */      public static void setLayerPaint(View view, Paint paint) {
/* 1029 */          IMPL.setLayerPaint(view, paint);
                }

/* 1042 */      public static int getLayoutDirection(View view) {
/* 1043 */          return IMPL.getLayoutDirection(view);
                }

/* 1062 */      public static void setLayoutDirection(View view, int layoutDirection) {
/* 1063 */          IMPL.setLayoutDirection(view, layoutDirection);
                }

/* 1074 */      public static ViewParent getParentForAccessibility(View view) {
/* 1075 */          return IMPL.getParentForAccessibility(view);
                }

/* 1087 */      public static boolean isOpaque(View view) {
/* 1088 */          return IMPL.isOpaque(view);
                }

/* 1104 */      public static int resolveSizeAndState(int size, int measureSpec, int childMeasuredState) {
/* 1105 */          return IMPL.resolveSizeAndState(size, measureSpec, childMeasuredState);
                }

/* 1118 */      public static int getMeasuredWidthAndState(View view) {
/* 1119 */          return IMPL.getMeasuredWidthAndState(view);
                }

/* 1132 */      public static int getMeasuredHeightAndState(View view) {
/* 1133 */          return IMPL.getMeasuredHeightAndState(view);
                }

/* 1143 */      public static int getMeasuredState(View view) {
/* 1144 */          return IMPL.getMeasuredState(view);
                }

/* 1155 */      public int getAccessibilityLiveRegion(View view) {
/* 1156 */          return IMPL.getAccessibilityLiveRegion(view);
                }

/* 1187 */      public void setAccessibilityLiveRegion(View view, int mode) {
/* 1188 */          IMPL.setAccessibilityLiveRegion(view, mode);
                }
            }
