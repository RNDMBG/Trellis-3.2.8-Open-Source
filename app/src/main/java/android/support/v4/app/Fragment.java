            package android.support.v4.app;

            import android.app.Activity;
            import android.content.ComponentCallbacks;
            import android.content.Context;
            import android.content.Intent;
            import android.content.res.Configuration;
            import android.content.res.Resources;
            import android.os.Bundle;
            import android.os.Parcel;
            import android.os.Parcelable;
            import android.support.v4.util.DebugUtils;
            import android.support.v4.util.SimpleArrayMap;
            import android.util.AttributeSet;
            import android.util.SparseArray;
            import android.view.ContextMenu;
            import android.view.LayoutInflater;
            import android.view.Menu;
            import android.view.MenuInflater;
            import android.view.MenuItem;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.animation.Animation;
            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            import java.io.FileDescriptor;
            import java.io.PrintWriter;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 306 */   public class Fragment implements ComponentCallbacks, View.OnCreateContextMenuListener {
                static final int ACTIVITY_CREATED = 2;
                static final int CREATED = 1;
                static final int INITIALIZING = 0;
                static final int RESUMED = 5;
                static final int STARTED = 4;
                static final int STOPPED = 3;
                private static final SimpleArrayMap<String, Class<?>> sClassMap = new SimpleArrayMap<>();
                FragmentActivity mActivity;
                boolean mAdded;
                View mAnimatingAway;
                Bundle mArguments;
                int mBackStackNesting;
                boolean mCalled;
                boolean mCheckedForLoaderManager;
                FragmentManagerImpl mChildFragmentManager;
                ViewGroup mContainer;
                int mContainerId;
                boolean mDeferStart;
                boolean mDetached;
                int mFragmentId;
                FragmentManagerImpl mFragmentManager;
                boolean mFromLayout;
                boolean mHasMenu;
                boolean mHidden;
                boolean mInLayout;
                View mInnerView;
                LoaderManagerImpl mLoaderManager;
                boolean mLoadersStarted;
                int mNextAnim;
                Fragment mParentFragment;
                boolean mRemoving;
                boolean mRestored;
                boolean mResumed;
                boolean mRetainInstance;
                boolean mRetaining;
                Bundle mSavedFragmentState;
                SparseArray<Parcelable> mSavedViewState;
                int mStateAfterAnimating;
                String mTag;
                Fragment mTarget;
                int mTargetRequestCode;
                View mView;
                String mWho;
                int mState = 0;
                int mIndex = -1;
                int mTargetIndex = -1;
                boolean mMenuVisible = true;
                boolean mUserVisibleHint = true;

/* 307 */       public static class SavedState implements Parcelable {
                    public static final Parcelable.Creator<SavedState> CREATOR = new C00061();
                    final Bundle mState;

/* 308 */           SavedState(Bundle state) {
/* 310 */               this.mState = state;
                    }

/* 312 */           SavedState(Parcel in, ClassLoader loader) {
/* 314 */               this.mState = in.readBundle();
/* 315 */               if (loader != null && this.mState != null) {
/* 316 */                   this.mState.setClassLoader(loader);
                        }
                    }

                    @Override // android.os.Parcelable
/* 321 */           public int describeContents() {
/* 322 */               return 0;
                    }

                    @Override // android.os.Parcelable
/* 326 */           public void writeToParcel(Parcel dest, int flags) {
/* 327 */               dest.writeBundle(this.mState);
                    }

                    /* JADX INFO: renamed from: android.support.v4.app.Fragment$SavedState$1 */
/* 329 */           static class C00061 implements Parcelable.Creator<SavedState> {
/* 330 */               C00061() {
                        }

                        @Override // android.os.Parcelable.Creator
/* 330 */               public /* bridge */ /* synthetic */ SavedState createFromParcel(Parcel x0) {
/* 331 */                   return createFromParcel(x0);
                        }

                        @Override // android.os.Parcelable.Creator
/* 330 */               public /* bridge */ /* synthetic */ SavedState[] newArray(int x0) {
/* 331 */                   return newArray(x0);
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.os.Parcelable.Creator
/* 332 */               public SavedState createFromParcel(Parcel in) {
/* 333 */                   return new SavedState(in, null);
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.os.Parcelable.Creator
/* 336 */               public SavedState[] newArray(int size) {
/* 337 */                   return new SavedState[size];
                        }
                    }
                }

/* 346 */       public static class InstantiationException extends RuntimeException {
/* 347 */           public InstantiationException(String msg, Exception cause) {
/* 348 */               super(msg, cause);
                    }
                }

/* 376 */       public static Fragment instantiate(Context context, String fname) {
/* 377 */           return instantiate(context, fname, null);
                }

/* 395 */       public static Fragment instantiate(Context context, String fname, Bundle args) {
                    try {
/* 396 */               Class<?> clazz = sClassMap.get(fname);
/* 397 */               if (clazz == null) {
/* 399 */                   clazz = context.getClassLoader().loadClass(fname);
/* 400 */                   sClassMap.put(fname, clazz);
                        }
/* 402 */               Fragment f = (Fragment) clazz.newInstance();
/* 403 */               if (args != null) {
/* 404 */                   args.setClassLoader(f.getClass().getClassLoader());
/* 405 */                   f.mArguments = args;
                        }
/* 407 */               return f;
                    } catch (ClassNotFoundException e) {
/* 409 */               throw new InstantiationException("Unable to instantiate fragment " + fname + ": make sure class name exists, is public, and has an empty constructor that is public", e);
                    } catch (IllegalAccessException e2) {
/* 417 */               throw new InstantiationException("Unable to instantiate fragment " + fname + ": make sure class name exists, is public, and has an empty constructor that is public", e2);
                    } catch (java.lang.InstantiationException e3) {
/* 413 */               throw new InstantiationException("Unable to instantiate fragment " + fname + ": make sure class name exists, is public, and has an empty constructor that is public", e3);
                    }
                }

/* 432 */       static boolean isSupportFragmentClass(Context context, String fname) {
                    try {
/* 433 */               Class<?> clazz = sClassMap.get(fname);
/* 434 */               if (clazz == null) {
/* 436 */                   clazz = context.getClassLoader().loadClass(fname);
/* 437 */                   sClassMap.put(fname, clazz);
                        }
/* 439 */               return Fragment.class.isAssignableFrom(clazz);
                    } catch (ClassNotFoundException e) {
/* 441 */               return false;
                    }
                }

/* 445 */       final void restoreViewState(Bundle savedInstanceState) {
/* 446 */           if (this.mSavedViewState != null) {
/* 447 */               this.mInnerView.restoreHierarchyState(this.mSavedViewState);
/* 448 */               this.mSavedViewState = null;
                    }
/* 450 */           this.mCalled = false;
/* 451 */           onViewStateRestored(savedInstanceState);
/* 452 */           if (!this.mCalled) {
/* 453 */               throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onViewStateRestored()");
                    }
                }

/* 458 */       final void setIndex(int index, Fragment parent) {
/* 459 */           this.mIndex = index;
/* 460 */           if (parent != null) {
/* 461 */               this.mWho = parent.mWho + ":" + this.mIndex;
                    } else {
/* 463 */               this.mWho = "android:fragment:" + this.mIndex;
                    }
                }

/* 467 */       final boolean isInBackStack() {
                    return this.mBackStackNesting > 0;
                }

/* 474 */       public final boolean equals(Object o) {
/* 475 */           return super.equals(o);
                }

/* 481 */       public final int hashCode() {
/* 482 */           return super.hashCode();
                }

/* 486 */       public String toString() {
/* 487 */           StringBuilder sb = new StringBuilder(128);
/* 488 */           DebugUtils.buildShortClassTag(this, sb);
/* 489 */           if (this.mIndex >= 0) {
/* 490 */               sb.append(" #");
/* 491 */               sb.append(this.mIndex);
                    }
/* 493 */           if (this.mFragmentId != 0) {
/* 494 */               sb.append(" id=0x");
/* 495 */               sb.append(Integer.toHexString(this.mFragmentId));
                    }
/* 497 */           if (this.mTag != null) {
/* 498 */               sb.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
/* 499 */               sb.append(this.mTag);
                    }
/* 501 */           sb.append('}');
/* 502 */           return sb.toString();
                }

/* 510 */       public final int getId() {
/* 511 */           return this.mFragmentId;
                }

/* 517 */       public final String getTag() {
/* 518 */           return this.mTag;
                }

/* 528 */       public void setArguments(Bundle args) {
/* 529 */           if (this.mIndex >= 0) {
/* 530 */               throw new IllegalStateException("Fragment already active");
                    }
/* 532 */           this.mArguments = args;
                }

/* 539 */       public final Bundle getArguments() {
/* 540 */           return this.mArguments;
                }

/* 551 */       public void setInitialSavedState(SavedState state) {
/* 552 */           if (this.mIndex >= 0) {
/* 553 */               throw new IllegalStateException("Fragment already active");
                    }
/* 555 */           this.mSavedFragmentState = (state == null || state.mState == null) ? null : state.mState;
                }

/* 570 */       public void setTargetFragment(Fragment fragment, int requestCode) {
/* 571 */           this.mTarget = fragment;
/* 572 */           this.mTargetRequestCode = requestCode;
                }

/* 578 */       public final Fragment getTargetFragment() {
/* 579 */           return this.mTarget;
                }

/* 585 */       public final int getTargetRequestCode() {
/* 586 */           return this.mTargetRequestCode;
                }

/* 592 */       public final FragmentActivity getActivity() {
/* 593 */           return this.mActivity;
                }

/* 599 */       public final Resources getResources() {
/* 600 */           if (this.mActivity == null) {
/* 601 */               throw new IllegalStateException("Fragment " + this + " not attached to Activity");
                    }
/* 603 */           return this.mActivity.getResources();
                }

/* 612 */       public final CharSequence getText(int resId) {
/* 613 */           return getResources().getText(resId);
                }

/* 622 */       public final String getString(int resId) {
/* 623 */           return getResources().getString(resId);
                }

/* 635 */       public final String getString(int resId, Object... formatArgs) {
/* 636 */           return getResources().getString(resId, formatArgs);
                }

/* 649 */       public final FragmentManager getFragmentManager() {
/* 650 */           return this.mFragmentManager;
                }

/* 657 */       public final FragmentManager getChildFragmentManager() {
/* 658 */           if (this.mChildFragmentManager == null) {
/* 659 */               instantiateChildFragmentManager();
/* 660 */               if (this.mState >= 5) {
/* 661 */                   this.mChildFragmentManager.dispatchResume();
                        } else if (this.mState >= 4) {
/* 663 */                   this.mChildFragmentManager.dispatchStart();
                        } else if (this.mState >= 2) {
/* 665 */                   this.mChildFragmentManager.dispatchActivityCreated();
                        } else if (this.mState >= 1) {
/* 667 */                   this.mChildFragmentManager.dispatchCreate();
                        }
                    }
/* 670 */           return this.mChildFragmentManager;
                }

/* 677 */       public final Fragment getParentFragment() {
/* 678 */           return this.mParentFragment;
                }

/* 684 */       public final boolean isAdded() {
                    return this.mActivity != null && this.mAdded;
                }

/* 693 */       public final boolean isDetached() {
/* 694 */           return this.mDetached;
                }

/* 702 */       public final boolean isRemoving() {
/* 703 */           return this.mRemoving;
                }

/* 713 */       public final boolean isInLayout() {
/* 714 */           return this.mInLayout;
                }

/* 721 */       public final boolean isResumed() {
/* 722 */           return this.mResumed;
                }

/* 730 */       public final boolean isVisible() {
                    return (!isAdded() || isHidden() || this.mView == null || this.mView.getWindowToken() == null || this.mView.getVisibility() != 0) ? false : true;
                }

/* 742 */       public final boolean isHidden() {
/* 743 */           return this.mHidden;
                }

/* 747 */       public final boolean hasOptionsMenu() {
/* 748 */           return this.mHasMenu;
                }

/* 752 */       public final boolean isMenuVisible() {
/* 753 */           return this.mMenuVisible;
                }

/* 763 */       public void onHiddenChanged(boolean hidden) {
                }

/* 780 */       public void setRetainInstance(boolean retain) {
/* 781 */           if (retain && this.mParentFragment != null) {
/* 782 */               throw new IllegalStateException("Can't retain fragements that are nested in other fragments");
                    }
/* 785 */           this.mRetainInstance = retain;
                }

/* 788 */       public final boolean getRetainInstance() {
/* 789 */           return this.mRetainInstance;
                }

/* 799 */       public void setHasOptionsMenu(boolean hasMenu) {
/* 800 */           if (this.mHasMenu != hasMenu) {
/* 801 */               this.mHasMenu = hasMenu;
/* 802 */               if (isAdded() && !isHidden()) {
/* 803 */                   this.mActivity.supportInvalidateOptionsMenu();
                        }
                    }
                }

/* 817 */       public void setMenuVisibility(boolean menuVisible) {
/* 818 */           if (this.mMenuVisible != menuVisible) {
/* 819 */               this.mMenuVisible = menuVisible;
/* 820 */               if (this.mHasMenu && isAdded() && !isHidden()) {
/* 821 */                   this.mActivity.supportInvalidateOptionsMenu();
                        }
                    }
                }

/* 839 */       public void setUserVisibleHint(boolean isVisibleToUser) {
/* 840 */           if (!this.mUserVisibleHint && isVisibleToUser && this.mState < 4) {
/* 841 */               this.mFragmentManager.performPendingDeferredStart(this);
                    }
/* 843 */           this.mUserVisibleHint = isVisibleToUser;
/* 844 */           this.mDeferStart = !isVisibleToUser;
                }

/* 851 */       public boolean getUserVisibleHint() {
/* 852 */           return this.mUserVisibleHint;
                }

/* 858 */       public LoaderManager getLoaderManager() {
/* 859 */           if (this.mLoaderManager != null) {
/* 860 */               return this.mLoaderManager;
                    }
/* 862 */           if (this.mActivity == null) {
/* 863 */               throw new IllegalStateException("Fragment " + this + " not attached to Activity");
                    }
/* 865 */           this.mCheckedForLoaderManager = true;
/* 866 */           this.mLoaderManager = this.mActivity.getLoaderManager(this.mWho, this.mLoadersStarted, true);
/* 867 */           return this.mLoaderManager;
                }

/* 874 */       public void startActivity(Intent intent) {
/* 875 */           if (this.mActivity == null) {
/* 876 */               throw new IllegalStateException("Fragment " + this + " not attached to Activity");
                    }
/* 878 */           this.mActivity.startActivityFromFragment(this, intent, -1);
                }

/* 885 */       public void startActivityForResult(Intent intent, int requestCode) {
/* 886 */           if (this.mActivity == null) {
/* 887 */               throw new IllegalStateException("Fragment " + this + " not attached to Activity");
                    }
/* 889 */           this.mActivity.startActivityFromFragment(this, intent, requestCode);
                }

/* 906 */       public void onActivityResult(int requestCode, int resultCode, Intent data) {
                }

/* 914 */       public LayoutInflater getLayoutInflater(Bundle savedInstanceState) {
/* 915 */           return this.mActivity.getLayoutInflater();
                }

/* 960 */       public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
/* 961 */           this.mCalled = true;
                }

/* 968 */       public void onAttach(Activity activity) {
/* 969 */           this.mCalled = true;
                }

/* 975 */       public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
/* 976 */           return null;
                }

/* 993 */       public void onCreate(Bundle savedInstanceState) {
/* 994 */           this.mCalled = true;
                }

/* 1017 */      public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/* 1018 */          return null;
                }

/* 1031 */      public void onViewCreated(View view, Bundle savedInstanceState) {
                }

/* 1040 */      public View getView() {
/* 1041 */          return this.mView;
                }

/* 1057 */      public void onActivityCreated(Bundle savedInstanceState) {
/* 1058 */          this.mCalled = true;
                }

/* 1072 */      public void onViewStateRestored(Bundle savedInstanceState) {
/* 1073 */          this.mCalled = true;
                }

/* 1081 */      public void onStart() {
/* 1082 */          this.mCalled = true;
/* 1084 */          if (!this.mLoadersStarted) {
/* 1085 */              this.mLoadersStarted = true;
/* 1086 */              if (!this.mCheckedForLoaderManager) {
/* 1087 */                  this.mCheckedForLoaderManager = true;
/* 1088 */                  this.mLoaderManager = this.mActivity.getLoaderManager(this.mWho, this.mLoadersStarted, false);
                        }
/* 1090 */              if (this.mLoaderManager != null) {
/* 1091 */                  this.mLoaderManager.doStart();
                        }
                    }
                }

/* 1102 */      public void onResume() {
/* 1103 */          this.mCalled = true;
                }

/* 1125 */      public void onSaveInstanceState(Bundle outState) {
                }

                @Override // android.content.ComponentCallbacks
/* 1128 */      public void onConfigurationChanged(Configuration newConfig) {
/* 1129 */          this.mCalled = true;
                }

/* 1137 */      public void onPause() {
/* 1138 */          this.mCalled = true;
                }

/* 1146 */      public void onStop() {
/* 1147 */          this.mCalled = true;
                }

                @Override // android.content.ComponentCallbacks
/* 1150 */      public void onLowMemory() {
/* 1151 */          this.mCalled = true;
                }

/* 1163 */      public void onDestroyView() {
/* 1164 */          this.mCalled = true;
                }

/* 1171 */      public void onDestroy() {
/* 1172 */          this.mCalled = true;
/* 1175 */          if (!this.mCheckedForLoaderManager) {
/* 1176 */              this.mCheckedForLoaderManager = true;
/* 1177 */              this.mLoaderManager = this.mActivity.getLoaderManager(this.mWho, this.mLoadersStarted, false);
                    }
/* 1179 */          if (this.mLoaderManager != null) {
/* 1180 */              this.mLoaderManager.doDestroy();
                    }
                }

/* 1190 */      void initState() {
/* 1191 */          this.mIndex = -1;
/* 1192 */          this.mWho = null;
/* 1193 */          this.mAdded = false;
/* 1194 */          this.mRemoving = false;
/* 1195 */          this.mResumed = false;
/* 1196 */          this.mFromLayout = false;
/* 1197 */          this.mInLayout = false;
/* 1198 */          this.mRestored = false;
/* 1199 */          this.mBackStackNesting = 0;
/* 1200 */          this.mFragmentManager = null;
/* 1201 */          this.mActivity = null;
/* 1202 */          this.mFragmentId = 0;
/* 1203 */          this.mContainerId = 0;
/* 1204 */          this.mTag = null;
/* 1205 */          this.mHidden = false;
/* 1206 */          this.mDetached = false;
/* 1207 */          this.mRetaining = false;
/* 1208 */          this.mLoaderManager = null;
/* 1209 */          this.mLoadersStarted = false;
/* 1210 */          this.mCheckedForLoaderManager = false;
                }

/* 1217 */      public void onDetach() {
/* 1218 */          this.mCalled = true;
                }

/* 1234 */      public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
                }

/* 1251 */      public void onPrepareOptionsMenu(Menu menu) {
                }

/* 1261 */      public void onDestroyOptionsMenu() {
                }

/* 1282 */      public boolean onOptionsItemSelected(MenuItem item) {
/* 1283 */          return false;
                }

/* 1293 */      public void onOptionsMenuClosed(Menu menu) {
                }

                @Override // android.view.View.OnCreateContextMenuListener
/* 1313 */      public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
/* 1314 */          getActivity().onCreateContextMenu(menu, v, menuInfo);
                }

/* 1327 */      public void registerForContextMenu(View view) {
/* 1328 */          view.setOnCreateContextMenuListener(this);
                }

/* 1338 */      public void unregisterForContextMenu(View view) {
/* 1339 */          view.setOnCreateContextMenuListener(null);
                }

/* 1359 */      public boolean onContextItemSelected(MenuItem item) {
/* 1360 */          return false;
                }

/* 1372 */      public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
/* 1373 */          writer.print(prefix);
                    writer.print("mFragmentId=#");
/* 1374 */          writer.print(Integer.toHexString(this.mFragmentId));
/* 1375 */          writer.print(" mContainerId=#");
/* 1376 */          writer.print(Integer.toHexString(this.mContainerId));
/* 1377 */          writer.print(" mTag=");
                    writer.println(this.mTag);
/* 1378 */          writer.print(prefix);
                    writer.print("mState=");
                    writer.print(this.mState);
/* 1379 */          writer.print(" mIndex=");
                    writer.print(this.mIndex);
/* 1380 */          writer.print(" mWho=");
                    writer.print(this.mWho);
/* 1381 */          writer.print(" mBackStackNesting=");
                    writer.println(this.mBackStackNesting);
/* 1382 */          writer.print(prefix);
                    writer.print("mAdded=");
                    writer.print(this.mAdded);
/* 1383 */          writer.print(" mRemoving=");
                    writer.print(this.mRemoving);
/* 1384 */          writer.print(" mResumed=");
                    writer.print(this.mResumed);
/* 1385 */          writer.print(" mFromLayout=");
                    writer.print(this.mFromLayout);
/* 1386 */          writer.print(" mInLayout=");
                    writer.println(this.mInLayout);
/* 1387 */          writer.print(prefix);
                    writer.print("mHidden=");
                    writer.print(this.mHidden);
/* 1388 */          writer.print(" mDetached=");
                    writer.print(this.mDetached);
/* 1389 */          writer.print(" mMenuVisible=");
                    writer.print(this.mMenuVisible);
/* 1390 */          writer.print(" mHasMenu=");
                    writer.println(this.mHasMenu);
/* 1391 */          writer.print(prefix);
                    writer.print("mRetainInstance=");
                    writer.print(this.mRetainInstance);
/* 1392 */          writer.print(" mRetaining=");
                    writer.print(this.mRetaining);
/* 1393 */          writer.print(" mUserVisibleHint=");
                    writer.println(this.mUserVisibleHint);
/* 1394 */          if (this.mFragmentManager != null) {
/* 1395 */              writer.print(prefix);
                        writer.print("mFragmentManager=");
/* 1396 */              writer.println(this.mFragmentManager);
                    }
/* 1398 */          if (this.mActivity != null) {
/* 1399 */              writer.print(prefix);
                        writer.print("mActivity=");
/* 1400 */              writer.println(this.mActivity);
                    }
/* 1402 */          if (this.mParentFragment != null) {
/* 1403 */              writer.print(prefix);
                        writer.print("mParentFragment=");
/* 1404 */              writer.println(this.mParentFragment);
                    }
/* 1406 */          if (this.mArguments != null) {
/* 1407 */              writer.print(prefix);
                        writer.print("mArguments=");
                        writer.println(this.mArguments);
                    }
/* 1409 */          if (this.mSavedFragmentState != null) {
/* 1410 */              writer.print(prefix);
                        writer.print("mSavedFragmentState=");
/* 1411 */              writer.println(this.mSavedFragmentState);
                    }
/* 1413 */          if (this.mSavedViewState != null) {
/* 1414 */              writer.print(prefix);
                        writer.print("mSavedViewState=");
/* 1415 */              writer.println(this.mSavedViewState);
                    }
/* 1417 */          if (this.mTarget != null) {
/* 1418 */              writer.print(prefix);
                        writer.print("mTarget=");
                        writer.print(this.mTarget);
/* 1419 */              writer.print(" mTargetRequestCode=");
/* 1420 */              writer.println(this.mTargetRequestCode);
                    }
/* 1422 */          if (this.mNextAnim != 0) {
/* 1423 */              writer.print(prefix);
                        writer.print("mNextAnim=");
                        writer.println(this.mNextAnim);
                    }
/* 1425 */          if (this.mContainer != null) {
/* 1426 */              writer.print(prefix);
                        writer.print("mContainer=");
                        writer.println(this.mContainer);
                    }
/* 1428 */          if (this.mView != null) {
/* 1429 */              writer.print(prefix);
                        writer.print("mView=");
                        writer.println(this.mView);
                    }
/* 1431 */          if (this.mInnerView != null) {
/* 1432 */              writer.print(prefix);
                        writer.print("mInnerView=");
                        writer.println(this.mView);
                    }
/* 1434 */          if (this.mAnimatingAway != null) {
/* 1435 */              writer.print(prefix);
                        writer.print("mAnimatingAway=");
                        writer.println(this.mAnimatingAway);
/* 1436 */              writer.print(prefix);
                        writer.print("mStateAfterAnimating=");
/* 1437 */              writer.println(this.mStateAfterAnimating);
                    }
/* 1439 */          if (this.mLoaderManager != null) {
/* 1440 */              writer.print(prefix);
                        writer.println("Loader Manager:");
/* 1441 */              this.mLoaderManager.dump(prefix + "  ", fd, writer, args);
                    }
/* 1443 */          if (this.mChildFragmentManager != null) {
/* 1444 */              writer.print(prefix);
                        writer.println("Child " + this.mChildFragmentManager + ":");
/* 1445 */              this.mChildFragmentManager.dump(prefix + "  ", fd, writer, args);
                    }
                }

/* 1449 */      Fragment findFragmentByWho(String who) {
/* 1450 */          if (!who.equals(this.mWho)) {
/* 1453 */              if (this.mChildFragmentManager != null) {
/* 1454 */                  return this.mChildFragmentManager.findFragmentByWho(who);
                        }
/* 1456 */              return null;
                    }
/* 1456 */          return this;
                }

                /* JADX INFO: renamed from: android.support.v4.app.Fragment$1 */
/* 1459 */      class C00051 implements FragmentContainer {
/* 1460 */          C00051() {
                    }

                    @Override // android.support.v4.app.FragmentContainer
/* 1463 */          public View findViewById(int id) {
/* 1464 */              if (Fragment.this.mView == null) {
/* 1465 */                  throw new IllegalStateException("Fragment does not have a view");
                        }
/* 1467 */              return Fragment.this.mView.findViewById(id);
                    }
                }

/* 1459 */      void instantiateChildFragmentManager() {
/* 1460 */          this.mChildFragmentManager = new FragmentManagerImpl();
/* 1461 */          this.mChildFragmentManager.attachActivity(this.mActivity, new C00051(), this);
                }

/* 1472 */      void performCreate(Bundle savedInstanceState) {
                    Parcelable p;
/* 1473 */          if (this.mChildFragmentManager != null) {
/* 1474 */              this.mChildFragmentManager.noteStateNotSaved();
                    }
/* 1476 */          this.mCalled = false;
/* 1477 */          onCreate(savedInstanceState);
/* 1478 */          if (!this.mCalled) {
/* 1479 */              throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onCreate()");
                    }
/* 1482 */          if (savedInstanceState != null && (p = savedInstanceState.getParcelable("android:support:fragments")) != null) {
/* 1486 */              if (this.mChildFragmentManager == null) {
/* 1487 */                  instantiateChildFragmentManager();
                        }
/* 1489 */              this.mChildFragmentManager.restoreAllState(p, null);
/* 1490 */              this.mChildFragmentManager.dispatchCreate();
                    }
                }

/* 1496 */      View performCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/* 1497 */          if (this.mChildFragmentManager != null) {
/* 1498 */              this.mChildFragmentManager.noteStateNotSaved();
                    }
/* 1500 */          return onCreateView(inflater, container, savedInstanceState);
                }

/* 1503 */      void performActivityCreated(Bundle savedInstanceState) {
/* 1504 */          if (this.mChildFragmentManager != null) {
/* 1505 */              this.mChildFragmentManager.noteStateNotSaved();
                    }
/* 1507 */          this.mCalled = false;
/* 1508 */          onActivityCreated(savedInstanceState);
/* 1509 */          if (!this.mCalled) {
/* 1510 */              throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onActivityCreated()");
                    }
/* 1513 */          if (this.mChildFragmentManager != null) {
/* 1514 */              this.mChildFragmentManager.dispatchActivityCreated();
                    }
                }

/* 1518 */      void performStart() {
/* 1519 */          if (this.mChildFragmentManager != null) {
/* 1520 */              this.mChildFragmentManager.noteStateNotSaved();
/* 1521 */              this.mChildFragmentManager.execPendingActions();
                    }
/* 1523 */          this.mCalled = false;
/* 1524 */          onStart();
/* 1525 */          if (!this.mCalled) {
/* 1526 */              throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onStart()");
                    }
/* 1529 */          if (this.mChildFragmentManager != null) {
/* 1530 */              this.mChildFragmentManager.dispatchStart();
                    }
/* 1532 */          if (this.mLoaderManager != null) {
/* 1533 */              this.mLoaderManager.doReportStart();
                    }
                }

/* 1537 */      void performResume() {
/* 1538 */          if (this.mChildFragmentManager != null) {
/* 1539 */              this.mChildFragmentManager.noteStateNotSaved();
/* 1540 */              this.mChildFragmentManager.execPendingActions();
                    }
/* 1542 */          this.mCalled = false;
/* 1543 */          onResume();
/* 1544 */          if (!this.mCalled) {
/* 1545 */              throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onResume()");
                    }
/* 1548 */          if (this.mChildFragmentManager != null) {
/* 1549 */              this.mChildFragmentManager.dispatchResume();
/* 1550 */              this.mChildFragmentManager.execPendingActions();
                    }
                }

/* 1554 */      void performConfigurationChanged(Configuration newConfig) {
/* 1555 */          onConfigurationChanged(newConfig);
/* 1556 */          if (this.mChildFragmentManager != null) {
/* 1557 */              this.mChildFragmentManager.dispatchConfigurationChanged(newConfig);
                    }
                }

/* 1561 */      void performLowMemory() {
/* 1562 */          onLowMemory();
/* 1563 */          if (this.mChildFragmentManager != null) {
/* 1564 */              this.mChildFragmentManager.dispatchLowMemory();
                    }
                }

/* 1577 */      boolean performCreateOptionsMenu(Menu menu, MenuInflater inflater) {
/* 1578 */          boolean show = false;
/* 1579 */          if (this.mHidden) {
/* 1578 */              return false;
                    }
/* 1580 */          if (this.mHasMenu && this.mMenuVisible) {
/* 1581 */              show = true;
/* 1582 */              onCreateOptionsMenu(menu, inflater);
                    }
/* 1584 */          if (this.mChildFragmentManager != null) {
/* 1585 */              return show | this.mChildFragmentManager.dispatchCreateOptionsMenu(menu, inflater);
                    }
/* 1588 */          return show;
                }

/* 1591 */      boolean performPrepareOptionsMenu(Menu menu) {
/* 1592 */          boolean show = false;
/* 1593 */          if (this.mHidden) {
/* 1592 */              return false;
                    }
/* 1594 */          if (this.mHasMenu && this.mMenuVisible) {
/* 1595 */              show = true;
/* 1596 */              onPrepareOptionsMenu(menu);
                    }
/* 1598 */          if (this.mChildFragmentManager != null) {
/* 1599 */              return show | this.mChildFragmentManager.dispatchPrepareOptionsMenu(menu);
                    }
/* 1602 */          return show;
                }

/* 1605 */      boolean performOptionsItemSelected(MenuItem item) {
/* 1606 */          if (!this.mHidden) {
/* 1607 */              if (this.mHasMenu && this.mMenuVisible && onOptionsItemSelected(item)) {
                            return true;
                        }
/* 1612 */              if (this.mChildFragmentManager != null && this.mChildFragmentManager.dispatchOptionsItemSelected(item)) {
                            return true;
                        }
                    }
/* 1618 */          return false;
                }

/* 1621 */      boolean performContextItemSelected(MenuItem item) {
/* 1622 */          if (!this.mHidden) {
/* 1623 */              if (onContextItemSelected(item)) {
                            return true;
                        }
/* 1626 */              if (this.mChildFragmentManager != null && this.mChildFragmentManager.dispatchContextItemSelected(item)) {
                            return true;
                        }
                    }
/* 1632 */          return false;
                }

/* 1635 */      void performOptionsMenuClosed(Menu menu) {
/* 1636 */          if (!this.mHidden) {
/* 1637 */              if (this.mHasMenu && this.mMenuVisible) {
/* 1638 */                  onOptionsMenuClosed(menu);
                        }
/* 1640 */              if (this.mChildFragmentManager != null) {
/* 1641 */                  this.mChildFragmentManager.dispatchOptionsMenuClosed(menu);
                        }
                    }
                }

/* 1646 */      void performSaveInstanceState(Bundle outState) {
                    Parcelable p;
/* 1647 */          onSaveInstanceState(outState);
/* 1648 */          if (this.mChildFragmentManager != null && (p = this.mChildFragmentManager.saveAllState()) != null) {
/* 1651 */              outState.putParcelable("android:support:fragments", p);
                    }
                }

/* 1656 */      void performPause() {
/* 1657 */          if (this.mChildFragmentManager != null) {
/* 1658 */              this.mChildFragmentManager.dispatchPause();
                    }
/* 1660 */          this.mCalled = false;
/* 1661 */          onPause();
/* 1662 */          if (!this.mCalled) {
/* 1663 */              throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onPause()");
                    }
                }

/* 1668 */      void performStop() {
/* 1669 */          if (this.mChildFragmentManager != null) {
/* 1670 */              this.mChildFragmentManager.dispatchStop();
                    }
/* 1672 */          this.mCalled = false;
/* 1673 */          onStop();
/* 1674 */          if (!this.mCalled) {
/* 1675 */              throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onStop()");
                    }
                }

/* 1680 */      void performReallyStop() {
/* 1681 */          if (this.mChildFragmentManager != null) {
/* 1682 */              this.mChildFragmentManager.dispatchReallyStop();
                    }
/* 1684 */          if (this.mLoadersStarted) {
/* 1685 */              this.mLoadersStarted = false;
/* 1686 */              if (!this.mCheckedForLoaderManager) {
/* 1687 */                  this.mCheckedForLoaderManager = true;
/* 1688 */                  this.mLoaderManager = this.mActivity.getLoaderManager(this.mWho, this.mLoadersStarted, false);
                        }
/* 1690 */              if (this.mLoaderManager != null) {
/* 1691 */                  if (!this.mActivity.mRetaining) {
/* 1692 */                      this.mLoaderManager.doStop();
                            } else {
/* 1694 */                      this.mLoaderManager.doRetain();
                            }
                        }
                    }
                }

/* 1700 */      void performDestroyView() {
/* 1701 */          if (this.mChildFragmentManager != null) {
/* 1702 */              this.mChildFragmentManager.dispatchDestroyView();
                    }
/* 1704 */          this.mCalled = false;
/* 1705 */          onDestroyView();
/* 1706 */          if (!this.mCalled) {
/* 1707 */              throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onDestroyView()");
                    }
/* 1710 */          if (this.mLoaderManager != null) {
/* 1711 */              this.mLoaderManager.doReportNextStart();
                    }
                }

/* 1715 */      void performDestroy() {
/* 1716 */          if (this.mChildFragmentManager != null) {
/* 1717 */              this.mChildFragmentManager.dispatchDestroy();
                    }
/* 1719 */          this.mCalled = false;
/* 1720 */          onDestroy();
/* 1721 */          if (!this.mCalled) {
/* 1722 */              throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onDestroy()");
                    }
                }
            }
