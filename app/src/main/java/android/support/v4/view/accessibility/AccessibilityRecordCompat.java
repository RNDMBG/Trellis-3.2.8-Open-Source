            package android.support.v4.view.accessibility;

            import android.os.Build;
            import android.os.Parcelable;
            import android.view.View;
            import java.util.Collections;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 79 */    public class AccessibilityRecordCompat {
                private static final AccessibilityRecordImpl IMPL;
                private final Object mRecord;

                interface AccessibilityRecordImpl {
                    int getAddedCount(Object obj);

                    CharSequence getBeforeText(Object obj);

                    CharSequence getClassName(Object obj);

                    CharSequence getContentDescription(Object obj);

                    int getCurrentItemIndex(Object obj);

                    int getFromIndex(Object obj);

                    int getItemCount(Object obj);

                    int getMaxScrollX(Object obj);

                    int getMaxScrollY(Object obj);

                    Parcelable getParcelableData(Object obj);

                    int getRemovedCount(Object obj);

                    int getScrollX(Object obj);

                    int getScrollY(Object obj);

                    AccessibilityNodeInfoCompat getSource(Object obj);

                    List<CharSequence> getText(Object obj);

                    int getToIndex(Object obj);

                    int getWindowId(Object obj);

                    boolean isChecked(Object obj);

                    boolean isEnabled(Object obj);

                    boolean isFullScreen(Object obj);

                    boolean isPassword(Object obj);

                    boolean isScrollable(Object obj);

                    Object obtain();

                    Object obtain(Object obj);

                    void recycle(Object obj);

                    void setAddedCount(Object obj, int i);

                    void setBeforeText(Object obj, CharSequence charSequence);

                    void setChecked(Object obj, boolean z);

                    void setClassName(Object obj, CharSequence charSequence);

                    void setContentDescription(Object obj, CharSequence charSequence);

                    void setCurrentItemIndex(Object obj, int i);

                    void setEnabled(Object obj, boolean z);

                    void setFromIndex(Object obj, int i);

                    void setFullScreen(Object obj, boolean z);

                    void setItemCount(Object obj, int i);

                    void setMaxScrollX(Object obj, int i);

                    void setMaxScrollY(Object obj, int i);

                    void setParcelableData(Object obj, Parcelable parcelable);

                    void setPassword(Object obj, boolean z);

                    void setRemovedCount(Object obj, int i);

                    void setScrollX(Object obj, int i);

                    void setScrollY(Object obj, int i);

                    void setScrollable(Object obj, boolean z);

                    void setSource(Object obj, View view);

                    void setSource(Object obj, View view, int i);

                    void setToIndex(Object obj, int i);
                }

/* 80 */        static class AccessibilityRecordStubImpl implements AccessibilityRecordImpl {
/* 81 */            AccessibilityRecordStubImpl() {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 83 */            public Object obtain() {
/* 84 */                return null;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 87 */            public Object obtain(Object record) {
/* 88 */                return null;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 91 */            public int getAddedCount(Object record) {
/* 92 */                return 0;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 95 */            public CharSequence getBeforeText(Object record) {
/* 96 */                return null;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 99 */            public CharSequence getClassName(Object record) {
/* 100 */               return null;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 103 */           public CharSequence getContentDescription(Object record) {
/* 104 */               return null;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 107 */           public int getCurrentItemIndex(Object record) {
/* 108 */               return 0;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 111 */           public int getFromIndex(Object record) {
/* 112 */               return 0;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 115 */           public int getItemCount(Object record) {
/* 116 */               return 0;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 119 */           public int getMaxScrollX(Object record) {
/* 120 */               return 0;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 123 */           public int getMaxScrollY(Object record) {
/* 124 */               return 0;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 127 */           public Parcelable getParcelableData(Object record) {
/* 128 */               return null;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 131 */           public int getRemovedCount(Object record) {
/* 132 */               return 0;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 135 */           public int getScrollX(Object record) {
/* 136 */               return 0;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 139 */           public int getScrollY(Object record) {
/* 140 */               return 0;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 143 */           public AccessibilityNodeInfoCompat getSource(Object record) {
/* 144 */               return null;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 147 */           public List<CharSequence> getText(Object record) {
/* 148 */               return Collections.emptyList();
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 151 */           public int getToIndex(Object record) {
/* 152 */               return 0;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 155 */           public int getWindowId(Object record) {
/* 156 */               return 0;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 159 */           public boolean isChecked(Object record) {
/* 160 */               return false;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 163 */           public boolean isEnabled(Object record) {
/* 164 */               return false;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 167 */           public boolean isFullScreen(Object record) {
/* 168 */               return false;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 171 */           public boolean isPassword(Object record) {
/* 172 */               return false;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 175 */           public boolean isScrollable(Object record) {
/* 176 */               return false;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 180 */           public void recycle(Object record) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 184 */           public void setAddedCount(Object record, int addedCount) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 188 */           public void setBeforeText(Object record, CharSequence beforeText) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 192 */           public void setChecked(Object record, boolean isChecked) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 196 */           public void setClassName(Object record, CharSequence className) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 200 */           public void setContentDescription(Object record, CharSequence contentDescription) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 204 */           public void setCurrentItemIndex(Object record, int currentItemIndex) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 208 */           public void setEnabled(Object record, boolean isEnabled) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 212 */           public void setFromIndex(Object record, int fromIndex) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 216 */           public void setFullScreen(Object record, boolean isFullScreen) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 220 */           public void setItemCount(Object record, int itemCount) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 224 */           public void setMaxScrollX(Object record, int maxScrollX) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 228 */           public void setMaxScrollY(Object record, int maxScrollY) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 232 */           public void setParcelableData(Object record, Parcelable parcelableData) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 236 */           public void setPassword(Object record, boolean isPassword) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 240 */           public void setRemovedCount(Object record, int removedCount) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 244 */           public void setScrollX(Object record, int scrollX) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 248 */           public void setScrollY(Object record, int scrollY) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 252 */           public void setScrollable(Object record, boolean scrollable) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 256 */           public void setSource(Object record, View source) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 260 */           public void setSource(Object record, View root, int virtualDescendantId) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 264 */           public void setToIndex(Object record, int toIndex) {
                    }
                }

/* 266 */       static class AccessibilityRecordIcsImpl extends AccessibilityRecordStubImpl {
/* 267 */           AccessibilityRecordIcsImpl() {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 270 */           public Object obtain() {
/* 271 */               return AccessibilityRecordCompatIcs.obtain();
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 275 */           public Object obtain(Object record) {
/* 276 */               return AccessibilityRecordCompatIcs.obtain(record);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 280 */           public int getAddedCount(Object record) {
/* 281 */               return AccessibilityRecordCompatIcs.getAddedCount(record);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 285 */           public CharSequence getBeforeText(Object record) {
/* 286 */               return AccessibilityRecordCompatIcs.getBeforeText(record);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 290 */           public CharSequence getClassName(Object record) {
/* 291 */               return AccessibilityRecordCompatIcs.getClassName(record);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 295 */           public CharSequence getContentDescription(Object record) {
/* 296 */               return AccessibilityRecordCompatIcs.getContentDescription(record);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 300 */           public int getCurrentItemIndex(Object record) {
/* 301 */               return AccessibilityRecordCompatIcs.getCurrentItemIndex(record);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 305 */           public int getFromIndex(Object record) {
/* 306 */               return AccessibilityRecordCompatIcs.getFromIndex(record);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 310 */           public int getItemCount(Object record) {
/* 311 */               return AccessibilityRecordCompatIcs.getItemCount(record);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 315 */           public Parcelable getParcelableData(Object record) {
/* 316 */               return AccessibilityRecordCompatIcs.getParcelableData(record);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 320 */           public int getRemovedCount(Object record) {
/* 321 */               return AccessibilityRecordCompatIcs.getRemovedCount(record);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 325 */           public int getScrollX(Object record) {
/* 326 */               return AccessibilityRecordCompatIcs.getScrollX(record);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 330 */           public int getScrollY(Object record) {
/* 331 */               return AccessibilityRecordCompatIcs.getScrollY(record);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 335 */           public AccessibilityNodeInfoCompat getSource(Object record) {
/* 336 */               return AccessibilityNodeInfoCompat.wrapNonNullInstance(AccessibilityRecordCompatIcs.getSource(record));
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 341 */           public List<CharSequence> getText(Object record) {
/* 342 */               return AccessibilityRecordCompatIcs.getText(record);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 346 */           public int getToIndex(Object record) {
/* 347 */               return AccessibilityRecordCompatIcs.getToIndex(record);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 351 */           public int getWindowId(Object record) {
/* 352 */               return AccessibilityRecordCompatIcs.getWindowId(record);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 356 */           public boolean isChecked(Object record) {
/* 357 */               return AccessibilityRecordCompatIcs.isChecked(record);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 361 */           public boolean isEnabled(Object record) {
/* 362 */               return AccessibilityRecordCompatIcs.isEnabled(record);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 366 */           public boolean isFullScreen(Object record) {
/* 367 */               return AccessibilityRecordCompatIcs.isFullScreen(record);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 371 */           public boolean isPassword(Object record) {
/* 372 */               return AccessibilityRecordCompatIcs.isPassword(record);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 376 */           public boolean isScrollable(Object record) {
/* 377 */               return AccessibilityRecordCompatIcs.isScrollable(record);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 381 */           public void recycle(Object record) {
/* 382 */               AccessibilityRecordCompatIcs.recycle(record);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 386 */           public void setAddedCount(Object record, int addedCount) {
/* 387 */               AccessibilityRecordCompatIcs.setAddedCount(record, addedCount);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 391 */           public void setBeforeText(Object record, CharSequence beforeText) {
/* 392 */               AccessibilityRecordCompatIcs.setBeforeText(record, beforeText);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 396 */           public void setChecked(Object record, boolean isChecked) {
/* 397 */               AccessibilityRecordCompatIcs.setChecked(record, isChecked);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 401 */           public void setClassName(Object record, CharSequence className) {
/* 402 */               AccessibilityRecordCompatIcs.setClassName(record, className);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 406 */           public void setContentDescription(Object record, CharSequence contentDescription) {
/* 407 */               AccessibilityRecordCompatIcs.setContentDescription(record, contentDescription);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 411 */           public void setCurrentItemIndex(Object record, int currentItemIndex) {
/* 412 */               AccessibilityRecordCompatIcs.setCurrentItemIndex(record, currentItemIndex);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 416 */           public void setEnabled(Object record, boolean isEnabled) {
/* 417 */               AccessibilityRecordCompatIcs.setEnabled(record, isEnabled);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 421 */           public void setFromIndex(Object record, int fromIndex) {
/* 422 */               AccessibilityRecordCompatIcs.setFromIndex(record, fromIndex);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 426 */           public void setFullScreen(Object record, boolean isFullScreen) {
/* 427 */               AccessibilityRecordCompatIcs.setFullScreen(record, isFullScreen);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 431 */           public void setItemCount(Object record, int itemCount) {
/* 432 */               AccessibilityRecordCompatIcs.setItemCount(record, itemCount);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 436 */           public void setParcelableData(Object record, Parcelable parcelableData) {
/* 437 */               AccessibilityRecordCompatIcs.setParcelableData(record, parcelableData);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 441 */           public void setPassword(Object record, boolean isPassword) {
/* 442 */               AccessibilityRecordCompatIcs.setPassword(record, isPassword);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 446 */           public void setRemovedCount(Object record, int removedCount) {
/* 447 */               AccessibilityRecordCompatIcs.setRemovedCount(record, removedCount);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 451 */           public void setScrollX(Object record, int scrollX) {
/* 452 */               AccessibilityRecordCompatIcs.setScrollX(record, scrollX);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 456 */           public void setScrollY(Object record, int scrollY) {
/* 457 */               AccessibilityRecordCompatIcs.setScrollY(record, scrollY);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 461 */           public void setScrollable(Object record, boolean scrollable) {
/* 462 */               AccessibilityRecordCompatIcs.setScrollable(record, scrollable);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 466 */           public void setSource(Object record, View source) {
/* 467 */               AccessibilityRecordCompatIcs.setSource(record, source);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 471 */           public void setToIndex(Object record, int toIndex) {
/* 472 */               AccessibilityRecordCompatIcs.setToIndex(record, toIndex);
                    }
                }

/* 474 */       static class AccessibilityRecordIcsMr1Impl extends AccessibilityRecordIcsImpl {
/* 475 */           AccessibilityRecordIcsMr1Impl() {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 478 */           public int getMaxScrollX(Object record) {
/* 479 */               return AccessibilityRecordCompatIcsMr1.getMaxScrollX(record);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 483 */           public int getMaxScrollY(Object record) {
/* 484 */               return AccessibilityRecordCompatIcsMr1.getMaxScrollY(record);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 488 */           public void setMaxScrollX(Object record, int maxScrollX) {
/* 489 */               AccessibilityRecordCompatIcsMr1.setMaxScrollX(record, maxScrollX);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 493 */           public void setMaxScrollY(Object record, int maxScrollY) {
/* 494 */               AccessibilityRecordCompatIcsMr1.setMaxScrollY(record, maxScrollY);
                    }
                }

/* 496 */       static class AccessibilityRecordJellyBeanImpl extends AccessibilityRecordIcsMr1Impl {
/* 497 */           AccessibilityRecordJellyBeanImpl() {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordStubImpl, android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordImpl
/* 500 */           public void setSource(Object record, View root, int virtualDescendantId) {
/* 501 */               AccessibilityRecordCompatJellyBean.setSource(record, root, virtualDescendantId);
                    }
                }

                static {
/* 506 */           if (Build.VERSION.SDK_INT >= 16) {
/* 507 */               IMPL = new AccessibilityRecordJellyBeanImpl();
/* 515 */               return;
                    }
/* 508 */           if (Build.VERSION.SDK_INT >= 15) {
/* 509 */               IMPL = new AccessibilityRecordIcsMr1Impl();
                    } else if (Build.VERSION.SDK_INT >= 14) {
/* 511 */               IMPL = new AccessibilityRecordIcsImpl();
                    } else {
/* 513 */               IMPL = new AccessibilityRecordStubImpl();
                    }
                }

/* 527 */       public AccessibilityRecordCompat(Object record) {
/* 529 */           this.mRecord = record;
                }

/* 538 */       public Object getImpl() {
/* 539 */           return this.mRecord;
                }

/* 549 */       public static AccessibilityRecordCompat obtain(AccessibilityRecordCompat record) {
/* 550 */           return new AccessibilityRecordCompat(IMPL.obtain(record.mRecord));
                }

/* 559 */       public static AccessibilityRecordCompat obtain() {
/* 560 */           return new AccessibilityRecordCompat(IMPL.obtain());
                }

/* 570 */       public void setSource(View source) {
/* 571 */           IMPL.setSource(this.mRecord, source);
                }

/* 588 */       public void setSource(View root, int virtualDescendantId) {
/* 589 */           IMPL.setSource(this.mRecord, root, virtualDescendantId);
                }

/* 604 */       public AccessibilityNodeInfoCompat getSource() {
/* 605 */           return IMPL.getSource(this.mRecord);
                }

/* 613 */       public int getWindowId() {
/* 614 */           return IMPL.getWindowId(this.mRecord);
                }

/* 622 */       public boolean isChecked() {
/* 623 */           return IMPL.isChecked(this.mRecord);
                }

/* 633 */       public void setChecked(boolean isChecked) {
/* 634 */           IMPL.setChecked(this.mRecord, isChecked);
                }

/* 642 */       public boolean isEnabled() {
/* 643 */           return IMPL.isEnabled(this.mRecord);
                }

/* 653 */       public void setEnabled(boolean isEnabled) {
/* 654 */           IMPL.setEnabled(this.mRecord, isEnabled);
                }

/* 662 */       public boolean isPassword() {
/* 663 */           return IMPL.isPassword(this.mRecord);
                }

/* 673 */       public void setPassword(boolean isPassword) {
/* 674 */           IMPL.setPassword(this.mRecord, isPassword);
                }

/* 682 */       public boolean isFullScreen() {
/* 683 */           return IMPL.isFullScreen(this.mRecord);
                }

/* 693 */       public void setFullScreen(boolean isFullScreen) {
/* 694 */           IMPL.setFullScreen(this.mRecord, isFullScreen);
                }

/* 702 */       public boolean isScrollable() {
/* 703 */           return IMPL.isScrollable(this.mRecord);
                }

/* 713 */       public void setScrollable(boolean scrollable) {
/* 714 */           IMPL.setScrollable(this.mRecord, scrollable);
                }

/* 722 */       public int getItemCount() {
/* 723 */           return IMPL.getItemCount(this.mRecord);
                }

/* 733 */       public void setItemCount(int itemCount) {
/* 734 */           IMPL.setItemCount(this.mRecord, itemCount);
                }

/* 742 */       public int getCurrentItemIndex() {
/* 743 */           return IMPL.getCurrentItemIndex(this.mRecord);
                }

/* 753 */       public void setCurrentItemIndex(int currentItemIndex) {
/* 754 */           IMPL.setCurrentItemIndex(this.mRecord, currentItemIndex);
                }

/* 765 */       public int getFromIndex() {
/* 766 */           return IMPL.getFromIndex(this.mRecord);
                }

/* 779 */       public void setFromIndex(int fromIndex) {
/* 780 */           IMPL.setFromIndex(this.mRecord, fromIndex);
                }

/* 789 */       public int getToIndex() {
/* 790 */           return IMPL.getToIndex(this.mRecord);
                }

/* 799 */       public void setToIndex(int toIndex) {
/* 800 */           IMPL.setToIndex(this.mRecord, toIndex);
                }

/* 808 */       public int getScrollX() {
/* 809 */           return IMPL.getScrollX(this.mRecord);
                }

/* 817 */       public void setScrollX(int scrollX) {
/* 818 */           IMPL.setScrollX(this.mRecord, scrollX);
                }

/* 826 */       public int getScrollY() {
/* 827 */           return IMPL.getScrollY(this.mRecord);
                }

/* 835 */       public void setScrollY(int scrollY) {
/* 836 */           IMPL.setScrollY(this.mRecord, scrollY);
                }

/* 844 */       public int getMaxScrollX() {
/* 845 */           return IMPL.getMaxScrollX(this.mRecord);
                }

/* 852 */       public void setMaxScrollX(int maxScrollX) {
/* 853 */           IMPL.setMaxScrollX(this.mRecord, maxScrollX);
                }

/* 861 */       public int getMaxScrollY() {
/* 862 */           return IMPL.getMaxScrollY(this.mRecord);
                }

/* 870 */       public void setMaxScrollY(int maxScrollY) {
/* 871 */           IMPL.setMaxScrollY(this.mRecord, maxScrollY);
                }

/* 879 */       public int getAddedCount() {
/* 880 */           return IMPL.getAddedCount(this.mRecord);
                }

/* 890 */       public void setAddedCount(int addedCount) {
/* 891 */           IMPL.setAddedCount(this.mRecord, addedCount);
                }

/* 899 */       public int getRemovedCount() {
/* 900 */           return IMPL.getRemovedCount(this.mRecord);
                }

/* 910 */       public void setRemovedCount(int removedCount) {
/* 911 */           IMPL.setRemovedCount(this.mRecord, removedCount);
                }

/* 919 */       public CharSequence getClassName() {
/* 920 */           return IMPL.getClassName(this.mRecord);
                }

/* 930 */       public void setClassName(CharSequence className) {
/* 931 */           IMPL.setClassName(this.mRecord, className);
                }

/* 940 */       public List<CharSequence> getText() {
/* 941 */           return IMPL.getText(this.mRecord);
                }

/* 949 */       public CharSequence getBeforeText() {
/* 950 */           return IMPL.getBeforeText(this.mRecord);
                }

/* 960 */       public void setBeforeText(CharSequence beforeText) {
/* 961 */           IMPL.setBeforeText(this.mRecord, beforeText);
                }

/* 969 */       public CharSequence getContentDescription() {
/* 970 */           return IMPL.getContentDescription(this.mRecord);
                }

/* 980 */       public void setContentDescription(CharSequence contentDescription) {
/* 981 */           IMPL.setContentDescription(this.mRecord, contentDescription);
                }

/* 989 */       public Parcelable getParcelableData() {
/* 990 */           return IMPL.getParcelableData(this.mRecord);
                }

/* 1000 */      public void setParcelableData(Parcelable parcelableData) {
/* 1001 */          IMPL.setParcelableData(this.mRecord, parcelableData);
                }

/* 1013 */      public void recycle() {
/* 1014 */          IMPL.recycle(this.mRecord);
                }

/* 1018 */      public int hashCode() {
/* 1019 */          if (this.mRecord == null) {
                        return 0;
                    }
                    return this.mRecord.hashCode();
                }

/* 1024 */      public boolean equals(Object obj) {
/* 1025 */          if (this == obj) {
                        return true;
                    }
/* 1028 */          if (obj != null && getClass() == obj.getClass()) {
/* 1034 */              AccessibilityRecordCompat other = (AccessibilityRecordCompat) obj;
/* 1035 */              if (this.mRecord == null) {
                            return other.mRecord == null;
                        }
                        return this.mRecord.equals(other.mRecord);
                    }
                    return false;
                }
            }
