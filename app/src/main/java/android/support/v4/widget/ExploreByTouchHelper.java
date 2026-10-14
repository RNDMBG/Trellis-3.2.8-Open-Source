            package android.support.v4.widget;

            import android.content.Context;
            import android.graphics.Rect;
            import android.os.Bundle;
            import android.support.v4.view.AccessibilityDelegateCompat;
            import android.support.v4.view.ViewCompat;
            import android.support.v4.view.ViewParentCompat;
            import android.support.v4.view.accessibility.AccessibilityEventCompat;
            import android.support.v4.view.accessibility.AccessibilityManagerCompat;
            import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
            import android.support.v4.view.accessibility.AccessibilityNodeProviderCompat;
            import android.support.v4.view.accessibility.AccessibilityRecordCompat;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.ViewParent;
            import android.view.accessibility.AccessibilityEvent;
            import android.view.accessibility.AccessibilityManager;
            import java.util.LinkedList;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 54 */    public abstract class ExploreByTouchHelper extends AccessibilityDelegateCompat {
                private static final String DEFAULT_CLASS_NAME = View.class.getName();
                public static final int INVALID_ID = Integer.MIN_VALUE;
                private final AccessibilityManager mManager;
                private ExploreByTouchNodeProvider mNodeProvider;
                private final View mView;
                private final Rect mTempScreenRect = new Rect();
                private final Rect mTempParentRect = new Rect();
                private final Rect mTempVisibleRect = new Rect();
                private final int[] mTempGlobalRect = new int[2];
                private int mFocusedVirtualViewId = Integer.MIN_VALUE;
                private int mHoveredVirtualViewId = Integer.MIN_VALUE;

                protected abstract int getVirtualViewAt(float f, float f2);

                protected abstract void getVisibleVirtualViews(List<Integer> list);

                protected abstract boolean onPerformActionForVirtualView(int i, int i2, Bundle bundle);

                protected abstract void onPopulateEventForVirtualView(int i, AccessibilityEvent accessibilityEvent);

                protected abstract void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat);

/* 55 */        static /* synthetic */ AccessibilityNodeInfoCompat access$100(ExploreByTouchHelper x0, int x1) {
/* 56 */            return x0.createNode(x1);
                }

/* 55 */        static /* synthetic */ boolean access$200(ExploreByTouchHelper x0, int x1, int x2, Bundle x3) {
/* 56 */            return x0.performAction(x1, x2, x3);
                }

/* 88 */        public ExploreByTouchHelper(View forView) {
/* 90 */            if (forView == null) {
/* 91 */                throw new IllegalArgumentException("View may not be null");
                    }
/* 94 */            this.mView = forView;
/* 95 */            Context context = forView.getContext();
/* 96 */            this.mManager = (AccessibilityManager) context.getSystemService("accessibility");
                }

                @Override // android.support.v4.view.AccessibilityDelegateCompat
/* 106 */       public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View host) {
/* 107 */           if (this.mNodeProvider == null) {
/* 108 */               this.mNodeProvider = new ExploreByTouchNodeProvider(this, null);
                    }
/* 110 */           return this.mNodeProvider;
                }

/* 132 */       public boolean dispatchHoverEvent(MotionEvent event) {
/* 133 */           if (!this.mManager.isEnabled() || !AccessibilityManagerCompat.isTouchExplorationEnabled(this.mManager)) {
                        return false;
                    }
                    switch (event.getAction()) {
                        case 7:
                        case 9:
/* 141 */                   int virtualViewId = getVirtualViewAt(event.getX(), event.getY());
/* 142 */                   updateHoveredVirtualView(virtualViewId);
/* 143 */                   return virtualViewId != Integer.MIN_VALUE;
                        case 8:
                        default:
                            return false;
                        case 10:
/* 145 */                   if (this.mFocusedVirtualViewId == Integer.MIN_VALUE) {
                                return false;
                            }
/* 146 */                   updateHoveredVirtualView(Integer.MIN_VALUE);
                            return true;
                    }
                }

/* 172 */       public boolean sendEventForVirtualView(int virtualViewId, int eventType) {
                    ViewParent parent;
/* 173 */           if (virtualViewId == Integer.MIN_VALUE || !this.mManager.isEnabled() || (parent = this.mView.getParent()) == null) {
                        return false;
                    }
/* 182 */           AccessibilityEvent event = createEvent(virtualViewId, eventType);
/* 183 */           return ViewParentCompat.requestSendAccessibilityEvent(parent, this.mView, event);
                }

/* 193 */       public void invalidateRoot() {
/* 194 */           invalidateVirtualView(-1);
                }

/* 206 */       public void invalidateVirtualView(int virtualViewId) {
/* 207 */           sendEventForVirtualView(virtualViewId, 2048);
                }

/* 217 */       public int getFocusedVirtualView() {
/* 218 */           return this.mFocusedVirtualViewId;
                }

/* 229 */       private void updateHoveredVirtualView(int virtualViewId) {
/* 230 */           if (this.mHoveredVirtualViewId != virtualViewId) {
/* 234 */               int previousVirtualViewId = this.mHoveredVirtualViewId;
/* 235 */               this.mHoveredVirtualViewId = virtualViewId;
/* 239 */               sendEventForVirtualView(virtualViewId, 128);
/* 240 */               sendEventForVirtualView(previousVirtualViewId, 256);
                    }
                }

/* 254 */       private AccessibilityEvent createEvent(int virtualViewId, int eventType) {
                    switch (virtualViewId) {
                        case -1:
/* 257 */                   return createEventForHost(eventType);
                        default:
/* 259 */                   return createEventForChild(virtualViewId, eventType);
                    }
                }

/* 270 */       private AccessibilityEvent createEventForHost(int eventType) {
/* 271 */           AccessibilityEvent event = AccessibilityEvent.obtain(eventType);
/* 272 */           ViewCompat.onInitializeAccessibilityEvent(this.mView, event);
/* 273 */           return event;
                }

/* 286 */       private AccessibilityEvent createEventForChild(int virtualViewId, int eventType) {
/* 287 */           AccessibilityEvent event = AccessibilityEvent.obtain(eventType);
/* 288 */           event.setEnabled(true);
/* 289 */           event.setClassName(DEFAULT_CLASS_NAME);
/* 292 */           onPopulateEventForVirtualView(virtualViewId, event);
/* 295 */           if (event.getText().isEmpty() && event.getContentDescription() == null) {
/* 296 */               throw new RuntimeException("Callbacks must add text or a content description in populateEventForVirtualViewId()");
                    }
/* 301 */           event.setPackageName(this.mView.getContext().getPackageName());
/* 303 */           AccessibilityRecordCompat record = AccessibilityEventCompat.asRecord(event);
/* 304 */           record.setSource(this.mView, virtualViewId);
/* 306 */           return event;
                }

/* 319 */       private AccessibilityNodeInfoCompat createNode(int virtualViewId) {
                    switch (virtualViewId) {
                        case -1:
/* 322 */                   return createNodeForHost();
                        default:
/* 324 */                   return createNodeForChild(virtualViewId);
                    }
                }

/* 334 */       private AccessibilityNodeInfoCompat createNodeForHost() {
/* 335 */           AccessibilityNodeInfoCompat node = AccessibilityNodeInfoCompat.obtain(this.mView);
/* 336 */           ViewCompat.onInitializeAccessibilityNodeInfo(this.mView, node);
/* 339 */           LinkedList<Integer> virtualViewIds = new LinkedList<>();
/* 340 */           getVisibleVirtualViews(virtualViewIds);
                    for (Integer childVirtualViewId : virtualViewIds) {
/* 343 */               node.addChild(this.mView, childVirtualViewId.intValue());
                    }
/* 346 */           return node;
                }

/* 377 */       private AccessibilityNodeInfoCompat createNodeForChild(int virtualViewId) {
/* 378 */           AccessibilityNodeInfoCompat node = AccessibilityNodeInfoCompat.obtain();
/* 381 */           node.setEnabled(true);
/* 382 */           node.setClassName(DEFAULT_CLASS_NAME);
/* 385 */           onPopulateNodeForVirtualView(virtualViewId, node);
/* 388 */           if (node.getText() == null && node.getContentDescription() == null) {
/* 389 */               throw new RuntimeException("Callbacks must add text or a content description in populateNodeForVirtualViewId()");
                    }
/* 393 */           node.getBoundsInParent(this.mTempParentRect);
/* 394 */           if (this.mTempParentRect.isEmpty()) {
/* 395 */               throw new RuntimeException("Callbacks must set parent bounds in populateNodeForVirtualViewId()");
                    }
/* 399 */           int actions = node.getActions();
/* 400 */           if ((actions & 64) != 0) {
/* 401 */               throw new RuntimeException("Callbacks must not add ACTION_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
                    }
/* 404 */           if ((actions & 128) != 0) {
/* 405 */               throw new RuntimeException("Callbacks must not add ACTION_CLEAR_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
                    }
/* 410 */           node.setPackageName(this.mView.getContext().getPackageName());
/* 411 */           node.setSource(this.mView, virtualViewId);
/* 412 */           node.setParent(this.mView);
/* 415 */           if (this.mFocusedVirtualViewId == virtualViewId) {
/* 416 */               node.setAccessibilityFocused(true);
/* 417 */               node.addAction(128);
                    } else {
/* 419 */               node.setAccessibilityFocused(false);
/* 420 */               node.addAction(64);
                    }
/* 424 */           if (intersectVisibleToUser(this.mTempParentRect)) {
/* 425 */               node.setVisibleToUser(true);
/* 426 */               node.setBoundsInParent(this.mTempParentRect);
                    }
/* 430 */           this.mView.getLocationOnScreen(this.mTempGlobalRect);
/* 431 */           int offsetX = this.mTempGlobalRect[0];
/* 432 */           int offsetY = this.mTempGlobalRect[1];
/* 433 */           this.mTempScreenRect.set(this.mTempParentRect);
/* 434 */           this.mTempScreenRect.offset(offsetX, offsetY);
/* 435 */           node.setBoundsInScreen(this.mTempScreenRect);
/* 437 */           return node;
                }

/* 440 */       private boolean performAction(int virtualViewId, int action, Bundle arguments) {
                    switch (virtualViewId) {
                        case -1:
/* 443 */                   return performActionForHost(action, arguments);
                        default:
/* 445 */                   return performActionForChild(virtualViewId, action, arguments);
                    }
                }

/* 449 */       private boolean performActionForHost(int action, Bundle arguments) {
/* 450 */           return ViewCompat.performAccessibilityAction(this.mView, action, arguments);
                }

/* 453 */       private boolean performActionForChild(int virtualViewId, int action, Bundle arguments) {
                    switch (action) {
                        case 64:
                        case 128:
/* 457 */                   return manageFocusForChild(virtualViewId, action, arguments);
                        default:
/* 459 */                   return onPerformActionForVirtualView(virtualViewId, action, arguments);
                    }
                }

/* 463 */       private boolean manageFocusForChild(int virtualViewId, int action, Bundle arguments) {
                    switch (action) {
                        case 64:
/* 466 */                   return requestAccessibilityFocus(virtualViewId);
                        case 128:
/* 468 */                   return clearAccessibilityFocus(virtualViewId);
                        default:
/* 470 */                   return false;
                    }
                }

/* 483 */       private boolean intersectVisibleToUser(Rect localRect) {
/* 484 */           if (localRect == null || localRect.isEmpty() || this.mView.getWindowVisibility() != 0) {
                        return false;
                    }
/* 494 */           ViewParent viewParent = this.mView.getParent();
/* 495 */           while (viewParent instanceof View) {
/* 496 */               View view = (View) viewParent;
                        if (ViewCompat.getAlpha(view) <= 0.0f || view.getVisibility() != 0) {
                            return false;
                        }
/* 500 */               viewParent = view.getParent();
                    }
/* 504 */           if (viewParent == null || !this.mView.getLocalVisibleRect(this.mTempVisibleRect)) {
                        return false;
                    }
/* 514 */           return localRect.intersect(this.mTempVisibleRect);
                }

/* 522 */       private boolean isAccessibilityFocused(int virtualViewId) {
                    return this.mFocusedVirtualViewId == virtualViewId;
                }

/* 538 */       private boolean requestAccessibilityFocus(int virtualViewId) {
/* 539 */           if (!this.mManager.isEnabled() || !AccessibilityManagerCompat.isTouchExplorationEnabled(this.mManager) || isAccessibilityFocused(virtualViewId)) {
                        return false;
                    }
/* 545 */           this.mFocusedVirtualViewId = virtualViewId;
/* 547 */           this.mView.invalidate();
/* 548 */           sendEventForVirtualView(virtualViewId, 32768);
/* 550 */           return true;
                }

/* 562 */       private boolean clearAccessibilityFocus(int virtualViewId) {
/* 563 */           if (!isAccessibilityFocused(virtualViewId)) {
                        return false;
                    }
/* 564 */           this.mFocusedVirtualViewId = Integer.MIN_VALUE;
/* 565 */           this.mView.invalidate();
/* 566 */           sendEventForVirtualView(virtualViewId, 65536);
/* 568 */           return true;
                }

/* 711 */       private class ExploreByTouchNodeProvider extends AccessibilityNodeProviderCompat {
/* 712 */           private ExploreByTouchNodeProvider() {
                    }

/* 712 */           /* synthetic */ ExploreByTouchNodeProvider(ExploreByTouchHelper x0, C00671 x1) {
/* 713 */               this();
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeProviderCompat
/* 715 */           public AccessibilityNodeInfoCompat createAccessibilityNodeInfo(int virtualViewId) {
/* 716 */               return ExploreByTouchHelper.access$100(ExploreByTouchHelper.this, virtualViewId);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeProviderCompat
/* 720 */           public boolean performAction(int virtualViewId, int action, Bundle arguments) {
/* 721 */               return ExploreByTouchHelper.access$200(ExploreByTouchHelper.this, virtualViewId, action, arguments);
                    }
                }
            }
