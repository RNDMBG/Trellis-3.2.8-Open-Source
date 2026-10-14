            package android.support.v4.app;

            import android.content.Context;
            import android.content.res.Configuration;
            import android.os.Build;
            import android.os.Bundle;
            import android.os.Handler;
            import android.os.Parcelable;
            import android.support.v4.util.DebugUtils;
            import android.support.v4.util.LogWriter;
            import android.util.Log;
            import android.util.SparseArray;
            import android.view.Menu;
            import android.view.MenuInflater;
            import android.view.MenuItem;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.animation.AccelerateInterpolator;
            import android.view.animation.AlphaAnimation;
            import android.view.animation.Animation;
            import android.view.animation.AnimationSet;
            import android.view.animation.AnimationUtils;
            import android.view.animation.DecelerateInterpolator;
            import android.view.animation.Interpolator;
            import android.view.animation.ScaleAnimation;
            import java.io.FileDescriptor;
            import java.io.PrintWriter;
            import java.util.ArrayList;
            import java.util.Arrays;
            import java.util.List;
            
            /* JADX INFO: compiled from: FragmentManager.java */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 399 */   final class FragmentManagerImpl extends FragmentManager {
                static final Interpolator ACCELERATE_CUBIC;
                static final Interpolator ACCELERATE_QUINT;
                static final int ANIM_DUR = 220;
                public static final int ANIM_STYLE_CLOSE_ENTER = 3;
                public static final int ANIM_STYLE_CLOSE_EXIT = 4;
                public static final int ANIM_STYLE_FADE_ENTER = 5;
                public static final int ANIM_STYLE_FADE_EXIT = 6;
                public static final int ANIM_STYLE_OPEN_ENTER = 1;
                public static final int ANIM_STYLE_OPEN_EXIT = 2;
                static boolean DEBUG = false;
                static final Interpolator DECELERATE_CUBIC;
                static final Interpolator DECELERATE_QUINT;
                static final boolean HONEYCOMB;
                static final String TAG = "FragmentManager";
                static final String TARGET_REQUEST_CODE_STATE_TAG = "android:target_req_state";
                static final String TARGET_STATE_TAG = "android:target_state";
                static final String USER_VISIBLE_HINT_TAG = "android:user_visible_hint";
                static final String VIEW_STATE_TAG = "android:view_state";
                ArrayList<Fragment> mActive;
                FragmentActivity mActivity;
                ArrayList<Fragment> mAdded;
                ArrayList<Integer> mAvailBackStackIndices;
                ArrayList<Integer> mAvailIndices;
                ArrayList<BackStackRecord> mBackStack;
                ArrayList<FragmentManager.OnBackStackChangedListener> mBackStackChangeListeners;
                ArrayList<BackStackRecord> mBackStackIndices;
                FragmentContainer mContainer;
                ArrayList<Fragment> mCreatedMenus;
                boolean mDestroyed;
                boolean mExecutingActions;
                boolean mHavePendingDeferredStart;
                boolean mNeedMenuInvalidate;
                String mNoTransactionsBecause;
                Fragment mParent;
                ArrayList<Runnable> mPendingActions;
                boolean mStateSaved;
                Runnable[] mTmpActions;
                int mCurState = 0;
                Bundle mStateBundle = null;
                SparseArray<Parcelable> mStateArray = null;
                Runnable mExecCommit = new RunnableC00091();

/* 400 */       FragmentManagerImpl() {
                }

                static {
/* 405 */           HONEYCOMB = Build.VERSION.SDK_INT >= 11;
/* 738 */           DECELERATE_QUINT = new DecelerateInterpolator(2.5f);
/* 739 */           DECELERATE_CUBIC = new DecelerateInterpolator(1.5f);
/* 740 */           ACCELERATE_QUINT = new AccelerateInterpolator(2.5f);
/* 741 */           ACCELERATE_CUBIC = new AccelerateInterpolator(1.5f);
                }

                /* JADX INFO: renamed from: android.support.v4.app.FragmentManagerImpl$1 */
                /* JADX INFO: compiled from: FragmentManager.java */
/* 441 */       class RunnableC00091 implements Runnable {
/* 442 */           RunnableC00091() {
                    }

                    @Override // java.lang.Runnable
/* 445 */           public void run() {
/* 446 */               FragmentManagerImpl.this.execPendingActions();
                    }
                }

/* 450 */       private void throwException(RuntimeException ex) {
/* 451 */           Log.e(TAG, ex.getMessage());
/* 452 */           Log.e(TAG, "Activity state:");
/* 453 */           LogWriter logw = new LogWriter(TAG);
/* 454 */           PrintWriter pw = new PrintWriter(logw);
/* 455 */           if (this.mActivity != null) {
                        try {
/* 457 */                   this.mActivity.dump("  ", null, pw, new String[0]);
/* 468 */                   throw ex;
                        } catch (Exception e) {
/* 459 */                   Log.e(TAG, "Failed dumping state", e);
/* 468 */                   throw ex;
                        }
                    }
                    try {
/* 463 */               dump("  ", null, pw, new String[0]);
/* 468 */               throw ex;
                    } catch (Exception e2) {
/* 465 */               Log.e(TAG, "Failed dumping state", e2);
/* 468 */               throw ex;
                    }
                }

                @Override // android.support.v4.app.FragmentManager
/* 472 */       public FragmentTransaction beginTransaction() {
/* 473 */           return new BackStackRecord(this);
                }

                @Override // android.support.v4.app.FragmentManager
/* 477 */       public boolean executePendingTransactions() {
/* 478 */           return execPendingActions();
                }

                /* JADX INFO: renamed from: android.support.v4.app.FragmentManagerImpl$2 */
                /* JADX INFO: compiled from: FragmentManager.java */
/* 481 */       class RunnableC00102 implements Runnable {
/* 482 */           RunnableC00102() {
                    }

                    @Override // java.lang.Runnable
/* 484 */           public void run() {
/* 485 */               FragmentManagerImpl.this.popBackStackState(FragmentManagerImpl.this.mActivity.mHandler, null, -1, 0);
                    }
                }

                @Override // android.support.v4.app.FragmentManager
/* 482 */       public void popBackStack() {
/* 483 */           enqueueAction(new RunnableC00102(), false);
                }

                @Override // android.support.v4.app.FragmentManager
/* 491 */       public boolean popBackStackImmediate() {
/* 492 */           checkStateLoss();
/* 493 */           executePendingTransactions();
/* 494 */           return popBackStackState(this.mActivity.mHandler, null, -1, 0);
                }

                /* JADX INFO: renamed from: android.support.v4.app.FragmentManagerImpl$3 */
                /* JADX INFO: compiled from: FragmentManager.java */
/* 497 */       class RunnableC00113 implements Runnable {
                    final /* synthetic */ int val$flags;
                    final /* synthetic */ String val$name;

/* 498 */           RunnableC00113(String str, int i) {
                        this.val$name = str;
                        this.val$flags = i;
                    }

                    @Override // java.lang.Runnable
/* 500 */           public void run() {
/* 501 */               FragmentManagerImpl.this.popBackStackState(FragmentManagerImpl.this.mActivity.mHandler, this.val$name, -1, this.val$flags);
                    }
                }

                @Override // android.support.v4.app.FragmentManager
/* 498 */       public void popBackStack(String name, int flags) {
/* 499 */           enqueueAction(new RunnableC00113(name, flags), false);
                }

                @Override // android.support.v4.app.FragmentManager
/* 507 */       public boolean popBackStackImmediate(String name, int flags) {
/* 508 */           checkStateLoss();
/* 509 */           executePendingTransactions();
/* 510 */           return popBackStackState(this.mActivity.mHandler, name, -1, flags);
                }

                @Override // android.support.v4.app.FragmentManager
/* 514 */       public void popBackStack(int id, int flags) {
/* 515 */           if (id < 0) {
/* 516 */               throw new IllegalArgumentException("Bad id: " + id);
                    }
/* 518 */           enqueueAction(new RunnableC00124(id, flags), false);
                }

                /* JADX INFO: renamed from: android.support.v4.app.FragmentManagerImpl$4 */
                /* JADX INFO: compiled from: FragmentManager.java */
/* 516 */       class RunnableC00124 implements Runnable {
                    final /* synthetic */ int val$flags;
                    final /* synthetic */ int val$id;

/* 517 */           RunnableC00124(int i, int i2) {
                        this.val$id = i;
                        this.val$flags = i2;
                    }

                    @Override // java.lang.Runnable
/* 519 */           public void run() {
/* 520 */               FragmentManagerImpl.this.popBackStackState(FragmentManagerImpl.this.mActivity.mHandler, null, this.val$id, this.val$flags);
                    }
                }

                @Override // android.support.v4.app.FragmentManager
/* 526 */       public boolean popBackStackImmediate(int id, int flags) {
/* 527 */           checkStateLoss();
/* 528 */           executePendingTransactions();
/* 529 */           if (id < 0) {
/* 530 */               throw new IllegalArgumentException("Bad id: " + id);
                    }
/* 532 */           return popBackStackState(this.mActivity.mHandler, null, id, flags);
                }

                @Override // android.support.v4.app.FragmentManager
/* 536 */       public int getBackStackEntryCount() {
/* 537 */           if (this.mBackStack != null) {
                        return this.mBackStack.size();
                    }
                    return 0;
                }

                @Override // android.support.v4.app.FragmentManager
/* 541 */       public FragmentManager.BackStackEntry getBackStackEntryAt(int index) {
/* 542 */           return this.mBackStack.get(index);
                }

                @Override // android.support.v4.app.FragmentManager
/* 546 */       public void addOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener listener) {
/* 547 */           if (this.mBackStackChangeListeners == null) {
/* 548 */               this.mBackStackChangeListeners = new ArrayList<>();
                    }
/* 550 */           this.mBackStackChangeListeners.add(listener);
                }

                @Override // android.support.v4.app.FragmentManager
/* 554 */       public void removeOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener listener) {
/* 555 */           if (this.mBackStackChangeListeners != null) {
/* 556 */               this.mBackStackChangeListeners.remove(listener);
                    }
                }

                @Override // android.support.v4.app.FragmentManager
/* 561 */       public void putFragment(Bundle bundle, String key, Fragment fragment) {
/* 562 */           if (fragment.mIndex < 0) {
/* 563 */               throwException(new IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager"));
                    }
/* 566 */           bundle.putInt(key, fragment.mIndex);
                }

                @Override // android.support.v4.app.FragmentManager
/* 570 */       public Fragment getFragment(Bundle bundle, String key) {
/* 571 */           int index = bundle.getInt(key, -1);
/* 572 */           if (index == -1) {
/* 573 */               return null;
                    }
/* 575 */           if (index >= this.mActive.size()) {
/* 576 */               throwException(new IllegalStateException("Fragement no longer exists for key " + key + ": index " + index));
                    }
/* 579 */           Fragment f = this.mActive.get(index);
/* 580 */           if (f == null) {
/* 581 */               throwException(new IllegalStateException("Fragement no longer exists for key " + key + ": index " + index));
/* 584 */               return f;
                    }
/* 584 */           return f;
                }

                @Override // android.support.v4.app.FragmentManager
/* 588 */       public List<Fragment> getFragments() {
/* 589 */           return this.mActive;
                }

                @Override // android.support.v4.app.FragmentManager
/* 593 */       public Fragment.SavedState saveFragmentInstanceState(Fragment fragment) {
                    Bundle result;
/* 594 */           if (fragment.mIndex < 0) {
/* 595 */               throwException(new IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager"));
                    }
/* 598 */           if (fragment.mState <= 0 || (result = saveFragmentBasicState(fragment)) == null) {
                        return null;
                    }
                    return new Fragment.SavedState(result);
                }

                @Override // android.support.v4.app.FragmentManager
/* 606 */       public boolean isDestroyed() {
/* 607 */           return this.mDestroyed;
                }

/* 611 */       public String toString() {
/* 612 */           StringBuilder sb = new StringBuilder(128);
/* 613 */           sb.append("FragmentManager{");
/* 614 */           sb.append(Integer.toHexString(System.identityHashCode(this)));
/* 615 */           sb.append(" in ");
/* 616 */           if (this.mParent != null) {
/* 617 */               DebugUtils.buildShortClassTag(this.mParent, sb);
                    } else {
/* 619 */               DebugUtils.buildShortClassTag(this.mActivity, sb);
                    }
/* 621 */           sb.append("}}");
/* 622 */           return sb.toString();
                }

                @Override // android.support.v4.app.FragmentManager
/* 626 */       public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
                    int N;
                    int N2;
                    int N3;
                    int N4;
                    int N5;
                    int N6;
/* 627 */           String innerPrefix = prefix + "    ";
/* 630 */           if (this.mActive != null && (N6 = this.mActive.size()) > 0) {
/* 633 */               writer.print(prefix);
                        writer.print("Active Fragments in ");
/* 634 */               writer.print(Integer.toHexString(System.identityHashCode(this)));
/* 635 */               writer.println(":");
                        for (int i = 0; i < N6; i++) {
/* 637 */                   Fragment f = this.mActive.get(i);
/* 638 */                   writer.print(prefix);
                            writer.print("  #");
                            writer.print(i);
/* 639 */                   writer.print(": ");
                            writer.println(f);
/* 640 */                   if (f != null) {
/* 641 */                       f.dump(innerPrefix, fd, writer, args);
                            }
                        }
                    }
/* 647 */           if (this.mAdded != null && (N5 = this.mAdded.size()) > 0) {
/* 650 */               writer.print(prefix);
                        writer.println("Added Fragments:");
                        for (int i2 = 0; i2 < N5; i2++) {
/* 652 */                   Fragment f2 = this.mAdded.get(i2);
/* 653 */                   writer.print(prefix);
                            writer.print("  #");
                            writer.print(i2);
/* 654 */                   writer.print(": ");
                            writer.println(f2.toString());
                        }
                    }
/* 659 */           if (this.mCreatedMenus != null && (N4 = this.mCreatedMenus.size()) > 0) {
/* 662 */               writer.print(prefix);
                        writer.println("Fragments Created Menus:");
                        for (int i3 = 0; i3 < N4; i3++) {
/* 664 */                   Fragment f3 = this.mCreatedMenus.get(i3);
/* 665 */                   writer.print(prefix);
                            writer.print("  #");
                            writer.print(i3);
/* 666 */                   writer.print(": ");
                            writer.println(f3.toString());
                        }
                    }
/* 671 */           if (this.mBackStack != null && (N3 = this.mBackStack.size()) > 0) {
/* 674 */               writer.print(prefix);
                        writer.println("Back Stack:");
                        for (int i4 = 0; i4 < N3; i4++) {
/* 676 */                   BackStackRecord bs = this.mBackStack.get(i4);
/* 677 */                   writer.print(prefix);
                            writer.print("  #");
                            writer.print(i4);
/* 678 */                   writer.print(": ");
                            writer.println(bs.toString());
/* 679 */                   bs.dump(innerPrefix, fd, writer, args);
                        }
                    }
                    synchronized (this) {
                        try {
/* 685 */                   if (this.mBackStackIndices != null && (N2 = this.mBackStackIndices.size()) > 0) {
/* 688 */                       writer.print(prefix);
                                writer.println("Back Stack Indices:");
                                for (int i5 = 0; i5 < N2; i5++) {
/* 690 */                           BackStackRecord bs2 = this.mBackStackIndices.get(i5);
/* 691 */                           writer.print(prefix);
                                    writer.print("  #");
                                    writer.print(i5);
/* 692 */                           writer.print(": ");
                                    writer.println(bs2);
                                }
                            }
/* 697 */                   if (this.mAvailBackStackIndices != null && this.mAvailBackStackIndices.size() > 0) {
/* 698 */                       writer.print(prefix);
                                writer.print("mAvailBackStackIndices: ");
/* 699 */                       writer.println(Arrays.toString(this.mAvailBackStackIndices.toArray()));
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 703 */           if (this.mPendingActions != null && (N = this.mPendingActions.size()) > 0) {
/* 706 */               writer.print(prefix);
                        writer.println("Pending Actions:");
                        for (int i6 = 0; i6 < N; i6++) {
/* 708 */                   Runnable r = this.mPendingActions.get(i6);
/* 709 */                   writer.print(prefix);
                            writer.print("  #");
                            writer.print(i6);
/* 710 */                   writer.print(": ");
                            writer.println(r);
                        }
                    }
/* 715 */           writer.print(prefix);
                    writer.println("FragmentManager misc state:");
/* 716 */           writer.print(prefix);
                    writer.print("  mActivity=");
                    writer.println(this.mActivity);
/* 717 */           writer.print(prefix);
                    writer.print("  mContainer=");
                    writer.println(this.mContainer);
/* 718 */           if (this.mParent != null) {
/* 719 */               writer.print(prefix);
                        writer.print("  mParent=");
                        writer.println(this.mParent);
                    }
/* 721 */           writer.print(prefix);
                    writer.print("  mCurState=");
                    writer.print(this.mCurState);
/* 722 */           writer.print(" mStateSaved=");
                    writer.print(this.mStateSaved);
/* 723 */           writer.print(" mDestroyed=");
                    writer.println(this.mDestroyed);
/* 724 */           if (this.mNeedMenuInvalidate) {
/* 725 */               writer.print(prefix);
                        writer.print("  mNeedMenuInvalidate=");
/* 726 */               writer.println(this.mNeedMenuInvalidate);
                    }
/* 728 */           if (this.mNoTransactionsBecause != null) {
/* 729 */               writer.print(prefix);
                        writer.print("  mNoTransactionsBecause=");
/* 730 */               writer.println(this.mNoTransactionsBecause);
                    }
/* 732 */           if (this.mAvailIndices != null && this.mAvailIndices.size() > 0) {
/* 733 */               writer.print(prefix);
                        writer.print("  mAvailIndices: ");
/* 734 */               writer.println(Arrays.toString(this.mAvailIndices.toArray()));
                    }
                }

/* 746 */       static Animation makeOpenCloseAnimation(Context context, float startScale, float endScale, float startAlpha, float endAlpha) {
/* 747 */           AnimationSet set = new AnimationSet(false);
/* 748 */           ScaleAnimation scale = new ScaleAnimation(startScale, endScale, startScale, endScale, 1, 0.5f, 1, 0.5f);
/* 750 */           scale.setInterpolator(DECELERATE_QUINT);
/* 751 */           scale.setDuration(220L);
/* 752 */           set.addAnimation(scale);
/* 753 */           AlphaAnimation alpha = new AlphaAnimation(startAlpha, endAlpha);
/* 754 */           alpha.setInterpolator(DECELERATE_CUBIC);
/* 755 */           alpha.setDuration(220L);
/* 756 */           set.addAnimation(alpha);
/* 757 */           return set;
                }

/* 760 */       static Animation makeFadeAnimation(Context context, float start, float end) {
/* 761 */           AlphaAnimation anim = new AlphaAnimation(start, end);
/* 762 */           anim.setInterpolator(DECELERATE_CUBIC);
/* 763 */           anim.setDuration(220L);
/* 764 */           return anim;
                }

/* 768 */       Animation loadAnimation(Fragment fragment, int transit, boolean enter, int transitionStyle) {
                    int styleIndex;
                    Animation anim;
/* 769 */           Animation animObj = fragment.onCreateAnimation(transit, enter, fragment.mNextAnim);
/* 771 */           if (animObj == null) {
/* 775 */               if (fragment.mNextAnim != 0 && (anim = AnimationUtils.loadAnimation(this.mActivity, fragment.mNextAnim)) != null) {
                            return anim;
                        }
/* 782 */               if (transit != 0 && (styleIndex = transitToStyleIndex(transit, enter)) >= 0) {
                            switch (styleIndex) {
                                case 1:
/* 793 */                           return makeOpenCloseAnimation(this.mActivity, 1.125f, 1.0f, 0.0f, 1.0f);
                                case 2:
/* 795 */                           return makeOpenCloseAnimation(this.mActivity, 1.0f, 0.975f, 1.0f, 0.0f);
                                case 3:
/* 797 */                           return makeOpenCloseAnimation(this.mActivity, 0.975f, 1.0f, 0.0f, 1.0f);
                                case 4:
/* 799 */                           return makeOpenCloseAnimation(this.mActivity, 1.0f, 1.075f, 1.0f, 0.0f);
                                case 5:
/* 801 */                           return makeFadeAnimation(this.mActivity, 0.0f, 1.0f);
                                case 6:
/* 803 */                           return makeFadeAnimation(this.mActivity, 1.0f, 0.0f);
                                default:
/* 806 */                           if (transitionStyle == 0 && this.mActivity.getWindow() != null) {
/* 807 */                               transitionStyle = this.mActivity.getWindow().getAttributes().windowAnimations;
                                    }
                                    return transitionStyle == 0 ? null : null;
                            }
                        }
                        return null;
                    }
/* 823 */           return animObj;
                }

/* 826 */       public void performPendingDeferredStart(Fragment f) {
/* 827 */           if (f.mDeferStart) {
/* 828 */               if (this.mExecutingActions) {
/* 830 */                   this.mHavePendingDeferredStart = true;
                        } else {
/* 833 */                   f.mDeferStart = false;
/* 834 */                   moveToState(f, this.mCurState, 0, 0, false);
                        }
                    }
                }

                /* JADX WARN: Code duplicated, block: B:33:0x0046 A[FALL_THROUGH, PHI: r12
  0x0046: PHI (r12v6 'newState' int) = 
  (r12v4 'newState' int)
  (r12v4 'newState' int)
  (r12v4 'newState' int)
  (r12v4 'newState' int)
  (r12v4 'newState' int)
  (r12v4 'newState' int)
  (r12v5 'newState' int)
  (r12v4 'newState' int)
  (r12v7 'newState' int)
  (r12v7 'newState' int)
 binds: [B:114:0x0255, B:116:0x0259, B:119:0x025f, B:181:0x03a2, B:185:0x03ad, B:184:0x03a8, B:127:0x0275, B:32:0x0043, B:106:0x021e, B:110:0x023c] A[DONT_GENERATE, DONT_INLINE]] */
/* 840 */       void moveToState(Fragment f, int newState, int transit, int transitionStyle, boolean keepActive) {
/* 841 */           if ((!f.mAdded || f.mDetached) && newState > 1) {
/* 842 */               newState = 1;
                    }
/* 844 */           if (f.mRemoving && newState > f.mState) {
/* 846 */               newState = f.mState;
                    }
/* 850 */           if (f.mDeferStart && f.mState < 4 && newState > 3) {
/* 851 */               newState = 3;
                    }
/* 853 */           if (f.mState < newState) {
/* 857 */               if (!f.mFromLayout || f.mInLayout) {
/* 860 */                   if (f.mAnimatingAway != null) {
/* 865 */                       f.mAnimatingAway = null;
/* 866 */                       moveToState(f, f.mStateAfterAnimating, 0, 0, true);
                            }
                            switch (f.mState) {
                                case 0:
/* 870 */                           if (DEBUG) {
                                        Log.v(TAG, "moveto CREATED: " + f);
                                    }
/* 871 */                           if (f.mSavedFragmentState != null) {
/* 872 */                               f.mSavedViewState = f.mSavedFragmentState.getSparseParcelableArray(VIEW_STATE_TAG);
/* 874 */                               f.mTarget = getFragment(f.mSavedFragmentState, TARGET_STATE_TAG);
/* 876 */                               if (f.mTarget != null) {
/* 877 */                                   f.mTargetRequestCode = f.mSavedFragmentState.getInt(TARGET_REQUEST_CODE_STATE_TAG, 0);
                                        }
/* 880 */                               f.mUserVisibleHint = f.mSavedFragmentState.getBoolean(USER_VISIBLE_HINT_TAG, true);
/* 882 */                               if (!f.mUserVisibleHint) {
/* 883 */                                   f.mDeferStart = true;
/* 884 */                                   if (newState > 3) {
/* 885 */                                       newState = 3;
                                            }
                                        }
                                    }
/* 889 */                           f.mActivity = this.mActivity;
/* 890 */                           f.mParentFragment = this.mParent;
/* 891 */                           f.mFragmentManager = this.mParent != null ? this.mParent.mChildFragmentManager : this.mActivity.mFragments;
/* 893 */                           f.mCalled = false;
/* 894 */                           f.onAttach(this.mActivity);
/* 895 */                           if (!f.mCalled) {
/* 896 */                               throw new SuperNotCalledException("Fragment " + f + " did not call through to super.onAttach()");
                                    }
/* 899 */                           if (f.mParentFragment == null) {
/* 900 */                               this.mActivity.onAttachFragment(f);
                                    }
/* 903 */                           if (!f.mRetaining) {
/* 904 */                               f.performCreate(f.mSavedFragmentState);
                                    }
/* 906 */                           f.mRetaining = false;
/* 907 */                           if (f.mFromLayout) {
/* 911 */                               f.mView = f.performCreateView(f.getLayoutInflater(f.mSavedFragmentState), null, f.mSavedFragmentState);
/* 913 */                               if (f.mView != null) {
/* 914 */                                   f.mInnerView = f.mView;
/* 915 */                                   f.mView = NoSaveStateFrameLayout.wrap(f.mView);
/* 916 */                                   if (f.mHidden) {
                                                f.mView.setVisibility(8);
                                            }
/* 917 */                                   f.onViewCreated(f.mView, f.mSavedFragmentState);
                                        } else {
/* 919 */                                   f.mInnerView = null;
                                        }
                                    }
                                    break;
                                case 1:
/* 923 */                           if (newState > 1) {
/* 924 */                               if (DEBUG) {
                                            Log.v(TAG, "moveto ACTIVITY_CREATED: " + f);
                                        }
/* 925 */                               if (!f.mFromLayout) {
/* 926 */                                   ViewGroup container = null;
/* 927 */                                   if (f.mContainerId != 0 && (container = (ViewGroup) this.mContainer.findViewById(f.mContainerId)) == null && !f.mRestored) {
/* 930 */                                       throwException(new IllegalArgumentException("No view found for id 0x" + Integer.toHexString(f.mContainerId) + " (" + f.getResources().getResourceName(f.mContainerId) + ") for fragment " + f));
                                            }
/* 937 */                                   f.mContainer = container;
/* 938 */                                   f.mView = f.performCreateView(f.getLayoutInflater(f.mSavedFragmentState), container, f.mSavedFragmentState);
/* 940 */                                   if (f.mView != null) {
/* 941 */                                       f.mInnerView = f.mView;
/* 942 */                                       f.mView = NoSaveStateFrameLayout.wrap(f.mView);
/* 943 */                                       if (container != null) {
/* 944 */                                           Animation anim = loadAnimation(f, transit, true, transitionStyle);
/* 946 */                                           if (anim != null) {
/* 947 */                                               f.mView.startAnimation(anim);
                                                    }
/* 949 */                                           container.addView(f.mView);
                                                }
/* 951 */                                       if (f.mHidden) {
                                                    f.mView.setVisibility(8);
                                                }
/* 952 */                                       f.onViewCreated(f.mView, f.mSavedFragmentState);
                                            } else {
/* 954 */                                       f.mInnerView = null;
                                            }
                                        }
/* 958 */                               f.performActivityCreated(f.mSavedFragmentState);
/* 959 */                               if (f.mView != null) {
/* 960 */                                   f.restoreViewState(f.mSavedFragmentState);
                                        }
/* 962 */                               f.mSavedFragmentState = null;
                                        break;
                                    }
                                case 2:
                                case 3:
/* 966 */                           if (newState > 3) {
/* 967 */                               if (DEBUG) {
                                            Log.v(TAG, "moveto STARTED: " + f);
                                        }
/* 968 */                               f.performStart();
                                        break;
                                    }
                                case 4:
/* 971 */                           if (newState > 4) {
/* 972 */                               if (DEBUG) {
                                            Log.v(TAG, "moveto RESUMED: " + f);
                                        }
/* 973 */                               f.mResumed = true;
/* 974 */                               f.performResume();
/* 975 */                               f.mSavedFragmentState = null;
/* 976 */                               f.mSavedViewState = null;
                                        break;
                                    }
                                default:
/* 1089 */                          f.mState = newState;
                            }
                        } else {
/* 1090 */                  return;
                        }
                    } else if (f.mState > newState) {
                        switch (f.mState) {
                            case 5:
/* 982 */                       if (newState < 5) {
/* 983 */                           if (DEBUG) {
                                        Log.v(TAG, "movefrom RESUMED: " + f);
                                    }
/* 984 */                           f.performPause();
/* 985 */                           f.mResumed = false;
                                    break;
                                }
                            case 4:
/* 988 */                       if (newState < 4) {
/* 989 */                           if (DEBUG) {
                                        Log.v(TAG, "movefrom STARTED: " + f);
                                    }
/* 990 */                           f.performStop();
                                    break;
                                }
                            case 3:
/* 993 */                       if (newState < 3) {
/* 994 */                           if (DEBUG) {
                                        Log.v(TAG, "movefrom STOPPED: " + f);
                                    }
/* 995 */                           f.performReallyStop();
                                    break;
                                }
                            case 2:
/* 998 */                       if (newState < 2) {
/* 999 */                           if (DEBUG) {
                                        Log.v(TAG, "movefrom ACTIVITY_CREATED: " + f);
                                    }
/* 1000 */                          if (f.mView != null && !this.mActivity.isFinishing() && f.mSavedViewState == null) {
/* 1004 */                              saveFragmentViewState(f);
                                    }
/* 1007 */                          f.performDestroyView();
/* 1008 */                          if (f.mView != null && f.mContainer != null) {
/* 1009 */                              Animation anim2 = null;
/* 1010 */                              if (this.mCurState > 0 && !this.mDestroyed) {
/* 1011 */                                  anim2 = loadAnimation(f, transit, false, transitionStyle);
                                        }
/* 1014 */                              if (anim2 != null) {
/* 1016 */                                  f.mAnimatingAway = f.mView;
/* 1017 */                                  f.mStateAfterAnimating = newState;
/* 1015 */                                  anim2.setAnimationListener(new AnimationAnimationListenerC00135(f));
/* 1034 */                                  f.mView.startAnimation(anim2);
                                        }
/* 1036 */                              f.mContainer.removeView(f.mView);
                                    }
/* 1038 */                          f.mContainer = null;
/* 1039 */                          f.mView = null;
/* 1040 */                          f.mInnerView = null;
                                    break;
                                }
                            case 1:
/* 1043 */                      if (newState < 1) {
/* 1044 */                          if (this.mDestroyed && f.mAnimatingAway != null) {
/* 1052 */                              View v = f.mAnimatingAway;
/* 1053 */                              f.mAnimatingAway = null;
/* 1054 */                              v.clearAnimation();
                                    }
/* 1057 */                          if (f.mAnimatingAway != null) {
/* 1062 */                              f.mStateAfterAnimating = newState;
/* 1063 */                              newState = 1;
                                    } else {
/* 1065 */                              if (DEBUG) {
                                            Log.v(TAG, "movefrom CREATED: " + f);
                                        }
/* 1066 */                              if (!f.mRetaining) {
/* 1067 */                                  f.performDestroy();
                                        }
/* 1070 */                              f.mCalled = false;
/* 1071 */                              f.onDetach();
/* 1072 */                              if (!f.mCalled) {
/* 1073 */                                  throw new SuperNotCalledException("Fragment " + f + " did not call through to super.onDetach()");
                                        }
/* 1076 */                              if (!keepActive) {
/* 1077 */                                  if (!f.mRetaining) {
/* 1078 */                                      makeInactive(f);
                                            } else {
/* 1080 */                                      f.mActivity = null;
/* 1081 */                                      f.mFragmentManager = null;
                                            }
                                        }
                                    }
                                    break;
                                }
                            default:
/* 1089 */                      f.mState = newState;
                        }
                    }
/* 1089 */          f.mState = newState;
                }

                /* JADX INFO: renamed from: android.support.v4.app.FragmentManagerImpl$5 */
                /* JADX INFO: compiled from: FragmentManager.java */
/* 1016 */      class AnimationAnimationListenerC00135 implements Animation.AnimationListener {
                    final /* synthetic */ Fragment val$fragment;

/* 1017 */          AnimationAnimationListenerC00135(Fragment fragment) {
                        this.val$fragment = fragment;
                    }

                    @Override // android.view.animation.Animation.AnimationListener
/* 1020 */          public void onAnimationEnd(Animation animation) {
/* 1021 */              if (this.val$fragment.mAnimatingAway != null) {
/* 1022 */                  this.val$fragment.mAnimatingAway = null;
/* 1023 */                  FragmentManagerImpl.this.moveToState(this.val$fragment, this.val$fragment.mStateAfterAnimating, 0, 0, false);
                        }
                    }

                    @Override // android.view.animation.Animation.AnimationListener
/* 1028 */          public void onAnimationRepeat(Animation animation) {
                    }

                    @Override // android.view.animation.Animation.AnimationListener
/* 1031 */          public void onAnimationStart(Animation animation) {
                    }
                }

/* 1092 */      void moveToState(Fragment f) {
/* 1093 */          moveToState(f, this.mCurState, 0, 0, false);
                }

/* 1096 */      void moveToState(int newState, boolean always) {
/* 1097 */          moveToState(newState, 0, 0, always);
                }

/* 1100 */      void moveToState(int newState, int transit, int transitStyle, boolean always) {
/* 1101 */          if (this.mActivity == null && newState != 0) {
/* 1102 */              throw new IllegalStateException("No activity");
                    }
/* 1105 */          if (always || this.mCurState != newState) {
/* 1109 */              this.mCurState = newState;
/* 1110 */              if (this.mActive != null) {
/* 1111 */                  boolean loadersRunning = false;
                            for (int i = 0; i < this.mActive.size(); i++) {
/* 1113 */                      Fragment f = this.mActive.get(i);
/* 1114 */                      if (f != null) {
/* 1115 */                          moveToState(f, newState, transit, transitStyle, false);
/* 1116 */                          if (f.mLoaderManager != null) {
/* 1117 */                              loadersRunning |= f.mLoaderManager.hasRunningLoaders();
                                    }
                                }
                            }
/* 1122 */                  if (!loadersRunning) {
/* 1123 */                      startPendingDeferredFragments();
                            }
/* 1126 */                  if (this.mNeedMenuInvalidate && this.mActivity != null && this.mCurState == 5) {
/* 1127 */                      this.mActivity.supportInvalidateOptionsMenu();
/* 1128 */                      this.mNeedMenuInvalidate = false;
                            }
                        }
                    }
                }

/* 1133 */      void startPendingDeferredFragments() {
/* 1134 */          if (this.mActive != null) {
                        for (int i = 0; i < this.mActive.size(); i++) {
/* 1137 */                  Fragment f = this.mActive.get(i);
/* 1138 */                  if (f != null) {
/* 1139 */                      performPendingDeferredStart(f);
                            }
                        }
                    }
                }

/* 1144 */      void makeActive(Fragment f) {
/* 1145 */          if (f.mIndex < 0) {
/* 1149 */              if (this.mAvailIndices == null || this.mAvailIndices.size() <= 0) {
/* 1150 */                  if (this.mActive == null) {
/* 1151 */                      this.mActive = new ArrayList<>();
                            }
/* 1153 */                  f.setIndex(this.mActive.size(), this.mParent);
/* 1154 */                  this.mActive.add(f);
                        } else {
/* 1157 */                  f.setIndex(this.mAvailIndices.remove(this.mAvailIndices.size() - 1).intValue(), this.mParent);
/* 1158 */                  this.mActive.set(f.mIndex, f);
                        }
/* 1160 */              if (DEBUG) {
                            Log.v(TAG, "Allocated fragment index " + f);
                        }
                    }
                }

/* 1163 */      void makeInactive(Fragment f) {
/* 1164 */          if (f.mIndex >= 0) {
/* 1168 */              if (DEBUG) {
                            Log.v(TAG, "Freeing fragment index " + f);
                        }
/* 1169 */              this.mActive.set(f.mIndex, null);
/* 1170 */              if (this.mAvailIndices == null) {
/* 1171 */                  this.mAvailIndices = new ArrayList<>();
                        }
/* 1173 */              this.mAvailIndices.add(Integer.valueOf(f.mIndex));
/* 1174 */              this.mActivity.invalidateSupportFragment(f.mWho);
/* 1175 */              f.initState();
                    }
                }

/* 1178 */      public void addFragment(Fragment fragment, boolean moveToStateNow) {
/* 1179 */          if (this.mAdded == null) {
/* 1180 */              this.mAdded = new ArrayList<>();
                    }
/* 1182 */          if (DEBUG) {
                        Log.v(TAG, "add: " + fragment);
                    }
/* 1183 */          makeActive(fragment);
/* 1184 */          if (!fragment.mDetached) {
/* 1185 */              if (this.mAdded.contains(fragment)) {
/* 1186 */                  throw new IllegalStateException("Fragment already added: " + fragment);
                        }
/* 1188 */              this.mAdded.add(fragment);
/* 1189 */              fragment.mAdded = true;
/* 1190 */              fragment.mRemoving = false;
/* 1191 */              if (fragment.mHasMenu && fragment.mMenuVisible) {
/* 1192 */                  this.mNeedMenuInvalidate = true;
                        }
/* 1194 */              if (moveToStateNow) {
/* 1195 */                  moveToState(fragment);
                        }
                    }
                }

/* 1200 */      public void removeFragment(Fragment fragment, int transition, int transitionStyle) {
/* 1201 */          if (DEBUG) {
                        Log.v(TAG, "remove: " + fragment + " nesting=" + fragment.mBackStackNesting);
                    }
/* 1202 */          boolean inactive = !fragment.isInBackStack();
/* 1203 */          if (!fragment.mDetached || inactive) {
/* 1204 */              if (this.mAdded != null) {
/* 1205 */                  this.mAdded.remove(fragment);
                        }
/* 1207 */              if (fragment.mHasMenu && fragment.mMenuVisible) {
/* 1208 */                  this.mNeedMenuInvalidate = true;
                        }
/* 1210 */              fragment.mAdded = false;
/* 1211 */              fragment.mRemoving = true;
/* 1212 */              moveToState(fragment, inactive ? 0 : 1, transition, transitionStyle, false);
                    }
                }

/* 1217 */      public void hideFragment(Fragment fragment, int transition, int transitionStyle) {
/* 1218 */          if (DEBUG) {
                        Log.v(TAG, "hide: " + fragment);
                    }
/* 1219 */          if (!fragment.mHidden) {
/* 1220 */              fragment.mHidden = true;
/* 1221 */              if (fragment.mView != null) {
/* 1222 */                  Animation anim = loadAnimation(fragment, transition, false, transitionStyle);
/* 1224 */                  if (anim != null) {
/* 1225 */                      fragment.mView.startAnimation(anim);
                            }
/* 1227 */                  fragment.mView.setVisibility(8);
                        }
/* 1229 */              if (fragment.mAdded && fragment.mHasMenu && fragment.mMenuVisible) {
/* 1230 */                  this.mNeedMenuInvalidate = true;
                        }
/* 1232 */              fragment.onHiddenChanged(true);
                    }
                }

/* 1236 */      public void showFragment(Fragment fragment, int transition, int transitionStyle) {
/* 1237 */          if (DEBUG) {
                        Log.v(TAG, "show: " + fragment);
                    }
/* 1238 */          if (fragment.mHidden) {
/* 1239 */              fragment.mHidden = false;
/* 1240 */              if (fragment.mView != null) {
/* 1241 */                  Animation anim = loadAnimation(fragment, transition, true, transitionStyle);
/* 1243 */                  if (anim != null) {
/* 1244 */                      fragment.mView.startAnimation(anim);
                            }
/* 1246 */                  fragment.mView.setVisibility(0);
                        }
/* 1248 */              if (fragment.mAdded && fragment.mHasMenu && fragment.mMenuVisible) {
/* 1249 */                  this.mNeedMenuInvalidate = true;
                        }
/* 1251 */              fragment.onHiddenChanged(false);
                    }
                }

/* 1255 */      public void detachFragment(Fragment fragment, int transition, int transitionStyle) {
/* 1256 */          if (DEBUG) {
                        Log.v(TAG, "detach: " + fragment);
                    }
/* 1257 */          if (!fragment.mDetached) {
/* 1258 */              fragment.mDetached = true;
/* 1259 */              if (fragment.mAdded) {
/* 1261 */                  if (this.mAdded != null) {
/* 1262 */                      if (DEBUG) {
                                    Log.v(TAG, "remove from detach: " + fragment);
                                }
/* 1263 */                      this.mAdded.remove(fragment);
                            }
/* 1265 */                  if (fragment.mHasMenu && fragment.mMenuVisible) {
/* 1266 */                      this.mNeedMenuInvalidate = true;
                            }
/* 1268 */                  fragment.mAdded = false;
/* 1269 */                  moveToState(fragment, 1, transition, transitionStyle, false);
                        }
                    }
                }

/* 1274 */      public void attachFragment(Fragment fragment, int transition, int transitionStyle) {
/* 1275 */          if (DEBUG) {
                        Log.v(TAG, "attach: " + fragment);
                    }
/* 1276 */          if (fragment.mDetached) {
/* 1277 */              fragment.mDetached = false;
/* 1278 */              if (!fragment.mAdded) {
/* 1279 */                  if (this.mAdded == null) {
/* 1280 */                      this.mAdded = new ArrayList<>();
                            }
/* 1282 */                  if (this.mAdded.contains(fragment)) {
/* 1283 */                      throw new IllegalStateException("Fragment already added: " + fragment);
                            }
/* 1285 */                  if (DEBUG) {
                                Log.v(TAG, "add from attach: " + fragment);
                            }
/* 1286 */                  this.mAdded.add(fragment);
/* 1287 */                  fragment.mAdded = true;
/* 1288 */                  if (fragment.mHasMenu && fragment.mMenuVisible) {
/* 1289 */                      this.mNeedMenuInvalidate = true;
                            }
/* 1291 */                  moveToState(fragment, this.mCurState, transition, transitionStyle, false);
                        }
                    }
                }

                @Override // android.support.v4.app.FragmentManager
/* 1296 */      public Fragment findFragmentById(int id) {
/* 1297 */          if (this.mAdded != null) {
                        for (int i = this.mAdded.size() - 1; i >= 0; i--) {
/* 1300 */                  Fragment f = this.mAdded.get(i);
/* 1301 */                  if (f != null && f.mFragmentId == id) {
/* 1315 */                      return f;
                            }
                        }
                    }
/* 1306 */          if (this.mActive != null) {
                        for (int i2 = this.mActive.size() - 1; i2 >= 0; i2--) {
/* 1309 */                  Fragment f2 = this.mActive.get(i2);
/* 1310 */                  if (f2 != null && f2.mFragmentId == id) {
/* 1315 */                      return f2;
                            }
                        }
                    }
/* 1315 */          return null;
                }

                @Override // android.support.v4.app.FragmentManager
/* 1318 */      public Fragment findFragmentByTag(String tag) {
/* 1319 */          if (this.mAdded != null && tag != null) {
                        for (int i = this.mAdded.size() - 1; i >= 0; i--) {
/* 1322 */                  Fragment f = this.mAdded.get(i);
/* 1323 */                  if (f != null && tag.equals(f.mTag)) {
/* 1337 */                      return f;
                            }
                        }
                    }
/* 1328 */          if (this.mActive != null && tag != null) {
                        for (int i2 = this.mActive.size() - 1; i2 >= 0; i2--) {
/* 1331 */                  Fragment f2 = this.mActive.get(i2);
/* 1332 */                  if (f2 != null && tag.equals(f2.mTag)) {
/* 1337 */                      return f2;
                            }
                        }
                    }
/* 1337 */          return null;
                }

/* 1340 */      public Fragment findFragmentByWho(String who) {
                    Fragment f;
/* 1341 */          if (this.mActive != null && who != null) {
                        for (int i = this.mActive.size() - 1; i >= 0; i--) {
/* 1343 */                  Fragment f2 = this.mActive.get(i);
/* 1344 */                  if (f2 != null && (f = f2.findFragmentByWho(who)) != null) {
/* 1349 */                      return f;
                            }
                        }
                    }
/* 1349 */          return null;
                }

/* 1352 */      private void checkStateLoss() {
/* 1353 */          if (this.mStateSaved) {
/* 1354 */              throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
                    }
/* 1357 */          if (this.mNoTransactionsBecause != null) {
/* 1358 */              throw new IllegalStateException("Can not perform this action inside of " + this.mNoTransactionsBecause);
                    }
                }

/* 1370 */      public void enqueueAction(Runnable action, boolean allowStateLoss) {
/* 1371 */          if (!allowStateLoss) {
/* 1372 */              checkStateLoss();
                    }
                    synchronized (this) {
                        try {
/* 1375 */                  if (this.mDestroyed || this.mActivity == null) {
/* 1376 */                      throw new IllegalStateException("Activity has been destroyed");
                            }
/* 1378 */                  if (this.mPendingActions == null) {
/* 1379 */                      this.mPendingActions = new ArrayList<>();
                            }
/* 1381 */                  this.mPendingActions.add(action);
/* 1382 */                  if (this.mPendingActions.size() == 1) {
/* 1383 */                      this.mActivity.mHandler.removeCallbacks(this.mExecCommit);
/* 1384 */                      this.mActivity.mHandler.post(this.mExecCommit);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 1389 */      public int allocBackStackIndex(BackStackRecord bse) {
                    synchronized (this) {
                        try {
/* 1391 */                  if (this.mAvailBackStackIndices == null || this.mAvailBackStackIndices.size() <= 0) {
/* 1392 */                      if (this.mBackStackIndices == null) {
/* 1393 */                          this.mBackStackIndices = new ArrayList<>();
                                }
/* 1395 */                      int index = this.mBackStackIndices.size();
/* 1396 */                      if (DEBUG) {
                                    Log.v(TAG, "Setting back stack index " + index + " to " + bse);
                                }
/* 1397 */                      this.mBackStackIndices.add(bse);
                                return index;
                            }
/* 1401 */                  int index2 = this.mAvailBackStackIndices.remove(this.mAvailBackStackIndices.size() - 1).intValue();
/* 1402 */                  if (DEBUG) {
                                Log.v(TAG, "Adding back stack index " + index2 + " with " + bse);
                            }
/* 1403 */                  this.mBackStackIndices.set(index2, bse);
                            return index2;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 1409 */      public void setBackStackIndex(int index, BackStackRecord bse) {
                    synchronized (this) {
                        try {
/* 1411 */                  if (this.mBackStackIndices == null) {
/* 1412 */                      this.mBackStackIndices = new ArrayList<>();
                            }
/* 1414 */                  int N = this.mBackStackIndices.size();
/* 1415 */                  if (index < N) {
/* 1416 */                      if (DEBUG) {
                                    Log.v(TAG, "Setting back stack index " + index + " to " + bse);
                                }
/* 1417 */                      this.mBackStackIndices.set(index, bse);
                            } else {
/* 1419 */                      while (N < index) {
/* 1420 */                          this.mBackStackIndices.add(null);
/* 1421 */                          if (this.mAvailBackStackIndices == null) {
/* 1422 */                              this.mAvailBackStackIndices = new ArrayList<>();
                                    }
/* 1424 */                          if (DEBUG) {
                                        Log.v(TAG, "Adding available back stack index " + N);
                                    }
/* 1425 */                          this.mAvailBackStackIndices.add(Integer.valueOf(N));
/* 1426 */                          N++;
                                }
/* 1428 */                      if (DEBUG) {
                                    Log.v(TAG, "Adding back stack index " + index + " with " + bse);
                                }
/* 1429 */                      this.mBackStackIndices.add(bse);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 1434 */      public void freeBackStackIndex(int index) {
                    synchronized (this) {
                        try {
/* 1436 */                  this.mBackStackIndices.set(index, null);
/* 1437 */                  if (this.mAvailBackStackIndices == null) {
/* 1438 */                      this.mAvailBackStackIndices = new ArrayList<>();
                            }
/* 1440 */                  if (DEBUG) {
                                Log.v(TAG, "Freeing back stack index " + index);
                            }
/* 1441 */                  this.mAvailBackStackIndices.add(Integer.valueOf(index));
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                /* JADX WARN: Code restructure failed: missing block: B:35:0x0081, code lost:
                
                    r8.mExecutingActions = true;
                    r2 = 0;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:36:0x0085, code lost:
                
                    if (r2 >= r4) goto L49;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:37:0x0087, code lost:
                
                    r8.mTmpActions[r2].run();
                    r8.mTmpActions[r2] = null;
                    r2 = r2 + 1;
                 */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
/* 1448 */      public boolean execPendingActions() {
/* 1449 */          if (r8.mExecutingActions) {
/* 1450 */              throw new java.lang.IllegalStateException("Recursive entry to executePendingTransactions");
                    }
/* 1453 */          if (android.os.Looper.myLooper() != r8.mActivity.mHandler.getLooper()) {
/* 1454 */              throw new java.lang.IllegalStateException("Must be called from main thread of process");
                    }
/* 1457 */          boolean r0 = false;
                    while (true) {
                        synchronized (r8) {
                            try {
/* 1463 */                      if (r8.mPendingActions == null || r8.mPendingActions.size() == 0) {
                                    break;
                                }
/* 1467 */                      int r4 = r8.mPendingActions.size();
/* 1468 */                      if (r8.mTmpActions == null || r8.mTmpActions.length < r4) {
/* 1469 */                          r8.mTmpActions = new java.lang.Runnable[r4];
                                }
/* 1471 */                      r8.mPendingActions.toArray(r8.mTmpActions);
/* 1472 */                      r8.mPendingActions.clear();
/* 1473 */                      r8.mActivity.mHandler.removeCallbacks(r8.mExecCommit);
                            } catch (java.lang.Throwable r5) {
                                throw r5;
                            }
                        }
/* 1481 */              r8.mExecutingActions = false;
/* 1482 */              r0 = true;
                    }
/* 1485 */          if (r8.mHavePendingDeferredStart) {
/* 1486 */              boolean r3 = false;
                        for (int r2 = 0; r2 < r8.mActive.size(); r2++) {
/* 1488 */                  android.support.v4.app.Fragment r1 = r8.mActive.get(r2);
/* 1489 */                  if (r1 != null && r1.mLoaderManager != null) {
/* 1490 */                      r3 |= r1.mLoaderManager.hasRunningLoaders();
                            }
                        }
/* 1493 */              if (!r3) {
/* 1494 */                  r8.mHavePendingDeferredStart = false;
/* 1495 */                  startPendingDeferredFragments();
                        }
                    }
/* 1498 */          return r0;
                }

/* 1501 */      void reportBackStackChanged() {
/* 1502 */          if (this.mBackStackChangeListeners != null) {
                        for (int i = 0; i < this.mBackStackChangeListeners.size(); i++) {
/* 1504 */                  this.mBackStackChangeListeners.get(i).onBackStackChanged();
                        }
                    }
                }

/* 1509 */      void addBackStackState(BackStackRecord state) {
/* 1510 */          if (this.mBackStack == null) {
/* 1511 */              this.mBackStack = new ArrayList<>();
                    }
/* 1513 */          this.mBackStack.add(state);
/* 1514 */          reportBackStackChanged();
                }

/* 1517 */      boolean popBackStackState(Handler handler, String name, int id, int flags) {
/* 1518 */          if (this.mBackStack == null) {
                        return false;
                    }
/* 1521 */          if (name == null && id < 0 && (flags & 1) == 0) {
/* 1522 */              int last = this.mBackStack.size() - 1;
/* 1523 */              if (last < 0) {
                            return false;
                        }
/* 1527 */              this.mBackStack.remove(last).popFromBackStack(true);
/* 1528 */              reportBackStackChanged();
                    } else {
/* 1530 */              int index = -1;
/* 1531 */              if (name != null || id >= 0) {
/* 1534 */                  index = this.mBackStack.size() - 1;
/* 1535 */                  while (index >= 0) {
/* 1536 */                      BackStackRecord bss = this.mBackStack.get(index);
/* 1537 */                      if ((name != null && name.equals(bss.getName())) || (id >= 0 && id == bss.mIndex)) {
                                    break;
                                }
/* 1543 */                      index--;
                            }
/* 1545 */                  if (index < 0) {
                                return false;
                            }
/* 1548 */                  if ((flags & 1) != 0) {
/* 1549 */                      index--;
/* 1551 */                      while (index >= 0) {
/* 1552 */                          BackStackRecord bss2 = this.mBackStack.get(index);
/* 1553 */                          if ((name == null || !name.equals(bss2.getName())) && (id < 0 || id != bss2.mIndex)) {
                                        break;
                                    }
/* 1555 */                          index--;
                                }
                            }
                        }
/* 1562 */              if (index == this.mBackStack.size() - 1) {
                            return false;
                        }
/* 1565 */              ArrayList<BackStackRecord> states = new ArrayList<>();
                        for (int i = this.mBackStack.size() - 1; i > index; i--) {
/* 1568 */                  states.add(this.mBackStack.remove(i));
                        }
/* 1570 */              int LAST = states.size() - 1;
/* 1571 */              int i2 = 0;
                        while (i2 <= LAST) {
/* 1572 */                  if (DEBUG) {
                                Log.v(TAG, "Popping back stack state: " + states.get(i2));
                            }
/* 1573 */                  states.get(i2).popFromBackStack(i2 == LAST);
/* 1571 */                  i2++;
                        }
/* 1575 */              reportBackStackChanged();
                    }
                    return true;
                }

/* 1580 */      ArrayList<Fragment> retainNonConfig() {
/* 1581 */          ArrayList<Fragment> fragments = null;
/* 1582 */          if (this.mActive != null) {
                        for (int i = 0; i < this.mActive.size(); i++) {
/* 1584 */                  Fragment f = this.mActive.get(i);
/* 1585 */                  if (f != null && f.mRetainInstance) {
/* 1586 */                      if (fragments == null) {
/* 1587 */                          fragments = new ArrayList<>();
                                }
/* 1589 */                      fragments.add(f);
/* 1590 */                      f.mRetaining = true;
/* 1591 */                      f.mTargetIndex = f.mTarget != null ? f.mTarget.mIndex : -1;
/* 1592 */                      if (DEBUG) {
                                    Log.v(TAG, "retainNonConfig: keeping retained " + f);
                                }
                            }
                        }
                    }
/* 1596 */          return fragments;
                }

/* 1599 */      void saveFragmentViewState(Fragment f) {
/* 1600 */          if (f.mInnerView != null) {
/* 1603 */              if (this.mStateArray == null) {
/* 1604 */                  this.mStateArray = new SparseArray<>();
                        } else {
/* 1606 */                  this.mStateArray.clear();
                        }
/* 1608 */              f.mInnerView.saveHierarchyState(this.mStateArray);
/* 1609 */              if (this.mStateArray.size() > 0) {
/* 1610 */                  f.mSavedViewState = this.mStateArray;
/* 1611 */                  this.mStateArray = null;
                        }
                    }
                }

/* 1615 */      Bundle saveFragmentBasicState(Fragment f) {
/* 1616 */          Bundle result = null;
/* 1618 */          if (this.mStateBundle == null) {
/* 1619 */              this.mStateBundle = new Bundle();
                    }
/* 1621 */          f.performSaveInstanceState(this.mStateBundle);
/* 1622 */          if (!this.mStateBundle.isEmpty()) {
/* 1623 */              result = this.mStateBundle;
/* 1624 */              this.mStateBundle = null;
                    }
/* 1627 */          if (f.mView != null) {
/* 1628 */              saveFragmentViewState(f);
                    }
/* 1630 */          if (f.mSavedViewState != null) {
/* 1631 */              if (result == null) {
/* 1632 */                  result = new Bundle();
                        }
/* 1634 */              result.putSparseParcelableArray(VIEW_STATE_TAG, f.mSavedViewState);
                    }
/* 1637 */          if (!f.mUserVisibleHint) {
/* 1638 */              if (result == null) {
/* 1639 */                  result = new Bundle();
                        }
/* 1642 */              result.putBoolean(USER_VISIBLE_HINT_TAG, f.mUserVisibleHint);
                    }
/* 1645 */          return result;
                }

/* 1650 */      Parcelable saveAllState() {
                    int N;
                    int N2;
/* 1651 */          execPendingActions();
/* 1653 */          if (HONEYCOMB) {
/* 1663 */              this.mStateSaved = true;
                    }
/* 1666 */          if (this.mActive == null || this.mActive.size() <= 0) {
                        return null;
                    }
/* 1671 */          int N3 = this.mActive.size();
/* 1672 */          FragmentState[] active = new FragmentState[N3];
/* 1673 */          boolean haveFragments = false;
                    for (int i = 0; i < N3; i++) {
/* 1675 */              Fragment f = this.mActive.get(i);
/* 1676 */              if (f != null) {
/* 1677 */                  if (f.mIndex < 0) {
/* 1678 */                      throwException(new IllegalStateException("Failure saving state: active " + f + " has cleared index: " + f.mIndex));
                            }
/* 1683 */                  haveFragments = true;
/* 1685 */                  FragmentState fs = new FragmentState(f);
/* 1686 */                  active[i] = fs;
/* 1688 */                  if (f.mState > 0 && fs.mSavedFragmentState == null) {
/* 1689 */                      fs.mSavedFragmentState = saveFragmentBasicState(f);
/* 1691 */                      if (f.mTarget != null) {
/* 1692 */                          if (f.mTarget.mIndex < 0) {
/* 1693 */                              throwException(new IllegalStateException("Failure saving state: " + f + " has target not in fragment manager: " + f.mTarget));
                                    }
/* 1697 */                          if (fs.mSavedFragmentState == null) {
/* 1698 */                              fs.mSavedFragmentState = new Bundle();
                                    }
/* 1700 */                          putFragment(fs.mSavedFragmentState, TARGET_STATE_TAG, f.mTarget);
/* 1702 */                          if (f.mTargetRequestCode != 0) {
/* 1703 */                              fs.mSavedFragmentState.putInt(TARGET_REQUEST_CODE_STATE_TAG, f.mTargetRequestCode);
                                    }
                                }
                            } else {
/* 1710 */                      fs.mSavedFragmentState = f.mSavedFragmentState;
                            }
/* 1713 */                  if (DEBUG) {
                                Log.v(TAG, "Saved state of " + f + ": " + fs.mSavedFragmentState);
                            }
                        }
                    }
/* 1718 */          if (!haveFragments) {
/* 1719 */              if (!DEBUG) {
                            return null;
                        }
                        Log.v(TAG, "saveAllState: no fragments!");
                        return null;
                    }
/* 1723 */          int[] added = null;
/* 1724 */          BackStackState[] backStack = null;
/* 1727 */          if (this.mAdded != null && (N2 = this.mAdded.size()) > 0) {
/* 1730 */              added = new int[N2];
                        for (int i2 = 0; i2 < N2; i2++) {
/* 1732 */                  added[i2] = this.mAdded.get(i2).mIndex;
/* 1733 */                  if (added[i2] < 0) {
/* 1734 */                      throwException(new IllegalStateException("Failure saving state: active " + this.mAdded.get(i2) + " has cleared index: " + added[i2]));
                            }
/* 1738 */                  if (DEBUG) {
                                Log.v(TAG, "saveAllState: adding fragment #" + i2 + ": " + this.mAdded.get(i2));
                            }
                        }
                    }
/* 1745 */          if (this.mBackStack != null && (N = this.mBackStack.size()) > 0) {
/* 1748 */              backStack = new BackStackState[N];
                        for (int i3 = 0; i3 < N; i3++) {
/* 1750 */                  backStack[i3] = new BackStackState(this, this.mBackStack.get(i3));
/* 1751 */                  if (DEBUG) {
                                Log.v(TAG, "saveAllState: adding back stack #" + i3 + ": " + this.mBackStack.get(i3));
                            }
                        }
                    }
/* 1757 */          FragmentManagerState fms = new FragmentManagerState();
/* 1758 */          fms.mActive = active;
/* 1759 */          fms.mAdded = added;
/* 1760 */          fms.mBackStack = backStack;
/* 1761 */          return fms;
                }

/* 1766 */      void restoreAllState(Parcelable state, ArrayList<Fragment> nonConfig) {
/* 1767 */          if (state != null) {
/* 1768 */              FragmentManagerState fms = (FragmentManagerState) state;
/* 1769 */              if (fms.mActive != null) {
/* 1773 */                  if (nonConfig != null) {
                                for (int i = 0; i < nonConfig.size(); i++) {
/* 1775 */                          Fragment f = nonConfig.get(i);
/* 1776 */                          if (DEBUG) {
                                        Log.v(TAG, "restoreAllState: re-attaching retained " + f);
                                    }
/* 1777 */                          FragmentState fs = fms.mActive[f.mIndex];
/* 1778 */                          fs.mInstance = f;
/* 1779 */                          f.mSavedViewState = null;
/* 1780 */                          f.mBackStackNesting = 0;
/* 1781 */                          f.mInLayout = false;
/* 1782 */                          f.mAdded = false;
/* 1783 */                          f.mTarget = null;
/* 1784 */                          if (fs.mSavedFragmentState != null) {
/* 1785 */                              fs.mSavedFragmentState.setClassLoader(this.mActivity.getClassLoader());
/* 1786 */                              f.mSavedViewState = fs.mSavedFragmentState.getSparseParcelableArray(VIEW_STATE_TAG);
                                    }
                                }
                            }
/* 1794 */                  this.mActive = new ArrayList<>(fms.mActive.length);
/* 1795 */                  if (this.mAvailIndices != null) {
/* 1796 */                      this.mAvailIndices.clear();
                            }
                            for (int i2 = 0; i2 < fms.mActive.length; i2++) {
/* 1799 */                      FragmentState fs2 = fms.mActive[i2];
/* 1800 */                      if (fs2 != null) {
/* 1801 */                          Fragment f2 = fs2.instantiate(this.mActivity, this.mParent);
/* 1802 */                          if (DEBUG) {
                                        Log.v(TAG, "restoreAllState: active #" + i2 + ": " + f2);
                                    }
/* 1803 */                          this.mActive.add(f2);
/* 1807 */                          fs2.mInstance = null;
                                } else {
/* 1809 */                          this.mActive.add(null);
/* 1810 */                          if (this.mAvailIndices == null) {
/* 1811 */                              this.mAvailIndices = new ArrayList<>();
                                    }
/* 1813 */                          if (DEBUG) {
                                        Log.v(TAG, "restoreAllState: avail #" + i2);
                                    }
/* 1814 */                          this.mAvailIndices.add(Integer.valueOf(i2));
                                }
                            }
/* 1819 */                  if (nonConfig != null) {
                                for (int i3 = 0; i3 < nonConfig.size(); i3++) {
/* 1821 */                          Fragment f3 = nonConfig.get(i3);
/* 1822 */                          if (f3.mTargetIndex >= 0) {
/* 1823 */                              if (f3.mTargetIndex < this.mActive.size()) {
/* 1824 */                                  f3.mTarget = this.mActive.get(f3.mTargetIndex);
                                        } else {
/* 1826 */                                  Log.w(TAG, "Re-attaching retained fragment " + f3 + " target no longer exists: " + f3.mTargetIndex);
/* 1828 */                                  f3.mTarget = null;
                                        }
                                    }
                                }
                            }
/* 1835 */                  if (fms.mAdded != null) {
/* 1836 */                      this.mAdded = new ArrayList<>(fms.mAdded.length);
                                for (int i4 = 0; i4 < fms.mAdded.length; i4++) {
/* 1838 */                          Fragment f4 = this.mActive.get(fms.mAdded[i4]);
/* 1839 */                          if (f4 == null) {
/* 1840 */                              throwException(new IllegalStateException("No instantiated fragment for index #" + fms.mAdded[i4]));
                                    }
/* 1843 */                          f4.mAdded = true;
/* 1844 */                          if (DEBUG) {
                                        Log.v(TAG, "restoreAllState: added #" + i4 + ": " + f4);
                                    }
/* 1845 */                          if (this.mAdded.contains(f4)) {
/* 1846 */                              throw new IllegalStateException("Already added!");
                                    }
/* 1848 */                          this.mAdded.add(f4);
                                }
                            } else {
/* 1851 */                      this.mAdded = null;
                            }
/* 1855 */                  if (fms.mBackStack != null) {
/* 1856 */                      this.mBackStack = new ArrayList<>(fms.mBackStack.length);
                                for (int i5 = 0; i5 < fms.mBackStack.length; i5++) {
/* 1858 */                          BackStackRecord bse = fms.mBackStack[i5].instantiate(this);
/* 1859 */                          if (DEBUG) {
/* 1860 */                              Log.v(TAG, "restoreAllState: back stack #" + i5 + " (index " + bse.mIndex + "): " + bse);
/* 1862 */                              LogWriter logw = new LogWriter(TAG);
/* 1863 */                              PrintWriter pw = new PrintWriter(logw);
/* 1864 */                              bse.dump("  ", pw, false);
                                    }
/* 1866 */                          this.mBackStack.add(bse);
/* 1867 */                          if (bse.mIndex >= 0) {
/* 1868 */                              setBackStackIndex(bse.mIndex, bse);
                                    }
                                }
/* 1874 */                      return;
                            }
/* 1872 */                  this.mBackStack = null;
                        }
                    }
                }

/* 1877 */      public void attachActivity(FragmentActivity activity, FragmentContainer container, Fragment parent) {
/* 1878 */          if (this.mActivity != null) {
                        throw new IllegalStateException("Already attached");
                    }
/* 1879 */          this.mActivity = activity;
/* 1880 */          this.mContainer = container;
/* 1881 */          this.mParent = parent;
                }

/* 1884 */      public void noteStateNotSaved() {
/* 1885 */          this.mStateSaved = false;
                }

/* 1888 */      public void dispatchCreate() {
/* 1889 */          this.mStateSaved = false;
/* 1890 */          moveToState(1, false);
                }

/* 1893 */      public void dispatchActivityCreated() {
/* 1894 */          this.mStateSaved = false;
/* 1895 */          moveToState(2, false);
                }

/* 1898 */      public void dispatchStart() {
/* 1899 */          this.mStateSaved = false;
/* 1900 */          moveToState(4, false);
                }

/* 1903 */      public void dispatchResume() {
/* 1904 */          this.mStateSaved = false;
/* 1905 */          moveToState(5, false);
                }

/* 1908 */      public void dispatchPause() {
/* 1909 */          moveToState(4, false);
                }

/* 1915 */      public void dispatchStop() {
/* 1916 */          this.mStateSaved = true;
/* 1918 */          moveToState(3, false);
                }

/* 1921 */      public void dispatchReallyStop() {
/* 1922 */          moveToState(2, false);
                }

/* 1925 */      public void dispatchDestroyView() {
/* 1926 */          moveToState(1, false);
                }

/* 1929 */      public void dispatchDestroy() {
/* 1930 */          this.mDestroyed = true;
/* 1931 */          execPendingActions();
/* 1932 */          moveToState(0, false);
/* 1933 */          this.mActivity = null;
/* 1934 */          this.mContainer = null;
/* 1935 */          this.mParent = null;
                }

/* 1938 */      public void dispatchConfigurationChanged(Configuration newConfig) {
/* 1939 */          if (this.mAdded != null) {
                        for (int i = 0; i < this.mAdded.size(); i++) {
/* 1941 */                  Fragment f = this.mAdded.get(i);
/* 1942 */                  if (f != null) {
/* 1943 */                      f.performConfigurationChanged(newConfig);
                            }
                        }
                    }
                }

/* 1949 */      public void dispatchLowMemory() {
/* 1950 */          if (this.mAdded != null) {
                        for (int i = 0; i < this.mAdded.size(); i++) {
/* 1952 */                  Fragment f = this.mAdded.get(i);
/* 1953 */                  if (f != null) {
/* 1954 */                      f.performLowMemory();
                            }
                        }
                    }
                }

/* 1960 */      public boolean dispatchCreateOptionsMenu(Menu menu, MenuInflater inflater) {
/* 1961 */          boolean show = false;
/* 1962 */          ArrayList<Fragment> newMenus = null;
/* 1963 */          if (this.mAdded != null) {
                        for (int i = 0; i < this.mAdded.size(); i++) {
/* 1965 */                  Fragment f = this.mAdded.get(i);
/* 1966 */                  if (f != null && f.performCreateOptionsMenu(menu, inflater)) {
/* 1968 */                      show = true;
/* 1969 */                      if (newMenus == null) {
/* 1970 */                          newMenus = new ArrayList<>();
                                }
/* 1972 */                      newMenus.add(f);
                            }
                        }
                    }
/* 1978 */          if (this.mCreatedMenus != null) {
                        for (int i2 = 0; i2 < this.mCreatedMenus.size(); i2++) {
/* 1980 */                  Fragment f2 = this.mCreatedMenus.get(i2);
/* 1981 */                  if (newMenus == null || !newMenus.contains(f2)) {
/* 1982 */                      f2.onDestroyOptionsMenu();
                            }
                        }
                    }
/* 1987 */          this.mCreatedMenus = newMenus;
/* 1989 */          return show;
                }

/* 1992 */      public boolean dispatchPrepareOptionsMenu(Menu menu) {
/* 1993 */          boolean show = false;
/* 1994 */          if (this.mAdded != null) {
                        for (int i = 0; i < this.mAdded.size(); i++) {
/* 1996 */                  Fragment f = this.mAdded.get(i);
/* 1997 */                  if (f != null && f.performPrepareOptionsMenu(menu)) {
/* 1999 */                      show = true;
                            }
                        }
                    }
/* 2004 */          return show;
                }

/* 2007 */      public boolean dispatchOptionsItemSelected(MenuItem item) {
/* 2008 */          if (this.mAdded != null) {
                        for (int i = 0; i < this.mAdded.size(); i++) {
/* 2010 */                  Fragment f = this.mAdded.get(i);
/* 2011 */                  if (f != null && f.performOptionsItemSelected(item)) {
/* 2013 */                      return true;
                            }
                        }
                    }
/* 2018 */          return false;
                }

/* 2021 */      public boolean dispatchContextItemSelected(MenuItem item) {
/* 2022 */          if (this.mAdded != null) {
                        for (int i = 0; i < this.mAdded.size(); i++) {
/* 2024 */                  Fragment f = this.mAdded.get(i);
/* 2025 */                  if (f != null && f.performContextItemSelected(item)) {
/* 2027 */                      return true;
                            }
                        }
                    }
/* 2032 */          return false;
                }

/* 2035 */      public void dispatchOptionsMenuClosed(Menu menu) {
/* 2036 */          if (this.mAdded != null) {
                        for (int i = 0; i < this.mAdded.size(); i++) {
/* 2038 */                  Fragment f = this.mAdded.get(i);
/* 2039 */                  if (f != null) {
/* 2040 */                      f.performOptionsMenuClosed(menu);
                            }
                        }
                    }
                }

/* 2046 */      public static int reverseTransit(int transit) {
                    switch (transit) {
                        case FragmentTransaction.TRANSIT_FRAGMENT_OPEN /* 4097 */:
/* 2050 */                  return FragmentTransaction.TRANSIT_FRAGMENT_CLOSE;
                        case FragmentTransaction.TRANSIT_FRAGMENT_FADE /* 4099 */:
/* 2056 */                  return FragmentTransaction.TRANSIT_FRAGMENT_FADE;
                        case FragmentTransaction.TRANSIT_FRAGMENT_CLOSE /* 8194 */:
/* 2053 */                  return FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
                        default:
/* 2047 */                  return 0;
                    }
                }

/* 2070 */      public static int transitToStyleIndex(int transit, boolean enter) {
                    switch (transit) {
                        case FragmentTransaction.TRANSIT_FRAGMENT_OPEN /* 4097 */:
                            return enter ? 1 : 2;
                        case FragmentTransaction.TRANSIT_FRAGMENT_FADE /* 4099 */:
                            return enter ? 5 : 6;
                        case FragmentTransaction.TRANSIT_FRAGMENT_CLOSE /* 8194 */:
                            return enter ? 3 : 4;
                        default:
/* 2071 */                  return -1;
                    }
                }
            }
