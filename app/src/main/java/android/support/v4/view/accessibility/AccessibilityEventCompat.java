            package android.support.v4.view.accessibility;

            import android.os.Build;
            import android.view.accessibility.AccessibilityEvent;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 31 */    public class AccessibilityEventCompat {
                private static final AccessibilityEventVersionImpl IMPL;
                public static final int TYPES_ALL_MASK = -1;
                public static final int TYPE_ANNOUNCEMENT = 16384;
                public static final int TYPE_GESTURE_DETECTION_END = 524288;
                public static final int TYPE_GESTURE_DETECTION_START = 262144;
                public static final int TYPE_TOUCH_EXPLORATION_GESTURE_END = 1024;
                public static final int TYPE_TOUCH_EXPLORATION_GESTURE_START = 512;
                public static final int TYPE_TOUCH_INTERACTION_END = 2097152;
                public static final int TYPE_TOUCH_INTERACTION_START = 1048576;
                public static final int TYPE_VIEW_ACCESSIBILITY_FOCUSED = 32768;
                public static final int TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED = 65536;
                public static final int TYPE_VIEW_HOVER_ENTER = 128;
                public static final int TYPE_VIEW_HOVER_EXIT = 256;
                public static final int TYPE_VIEW_SCROLLED = 4096;
                public static final int TYPE_VIEW_TEXT_SELECTION_CHANGED = 8192;
                public static final int TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY = 131072;
                public static final int TYPE_WINDOW_CONTENT_CHANGED = 2048;

                interface AccessibilityEventVersionImpl {
                    void appendRecord(AccessibilityEvent accessibilityEvent, Object obj);

                    Object getRecord(AccessibilityEvent accessibilityEvent, int i);

                    int getRecordCount(AccessibilityEvent accessibilityEvent);
                }

/* 32 */        static class AccessibilityEventStubImpl implements AccessibilityEventVersionImpl {
/* 33 */            AccessibilityEventStubImpl() {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityEventCompat.AccessibilityEventVersionImpl
/* 38 */            public void appendRecord(AccessibilityEvent event, Object record) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityEventCompat.AccessibilityEventVersionImpl
/* 42 */            public Object getRecord(AccessibilityEvent event, int index) {
/* 43 */                return null;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityEventCompat.AccessibilityEventVersionImpl
/* 47 */            public int getRecordCount(AccessibilityEvent event) {
/* 48 */                return 0;
                    }
                }

/* 50 */        static class AccessibilityEventIcsImpl extends AccessibilityEventStubImpl {
/* 51 */            AccessibilityEventIcsImpl() {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityEventCompat.AccessibilityEventStubImpl, android.support.v4.view.accessibility.AccessibilityEventCompat.AccessibilityEventVersionImpl
/* 55 */            public void appendRecord(AccessibilityEvent event, Object record) {
/* 56 */                AccessibilityEventCompatIcs.appendRecord(event, record);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityEventCompat.AccessibilityEventStubImpl, android.support.v4.view.accessibility.AccessibilityEventCompat.AccessibilityEventVersionImpl
/* 60 */            public Object getRecord(AccessibilityEvent event, int index) {
/* 61 */                return AccessibilityEventCompatIcs.getRecord(event, index);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityEventCompat.AccessibilityEventStubImpl, android.support.v4.view.accessibility.AccessibilityEventCompat.AccessibilityEventVersionImpl
/* 65 */            public int getRecordCount(AccessibilityEvent event) {
/* 66 */                return AccessibilityEventCompatIcs.getRecordCount(event);
                    }
                }

                static {
/* 73 */            if (Build.VERSION.SDK_INT >= 14) {
/* 74 */                IMPL = new AccessibilityEventIcsImpl();
                    } else {
/* 76 */                IMPL = new AccessibilityEventStubImpl();
                    }
                }

/* 183 */       private AccessibilityEventCompat() {
                }

/* 193 */       public static int getRecordCount(AccessibilityEvent event) {
/* 194 */           return IMPL.getRecordCount(event);
                }

/* 205 */       public static void appendRecord(AccessibilityEvent event, AccessibilityRecordCompat record) {
/* 206 */           IMPL.appendRecord(event, record.getImpl());
                }

/* 215 */       public static AccessibilityRecordCompat getRecord(AccessibilityEvent event, int index) {
/* 216 */           return new AccessibilityRecordCompat(IMPL.getRecord(event, index));
                }

/* 232 */       public static AccessibilityRecordCompat asRecord(AccessibilityEvent event) {
/* 233 */           return new AccessibilityRecordCompat(event);
                }
            }
