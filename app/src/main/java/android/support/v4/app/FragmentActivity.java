            package android.support.v4.app;

            import android.R;
            import android.app.Activity;
            import android.content.Context;
            import android.content.Intent;
            import android.content.res.Configuration;
            import android.content.res.Resources;
            import android.content.res.TypedArray;
            import android.os.Build;
            import android.os.Bundle;
            import android.os.Handler;
            import android.os.Message;
            import android.os.Parcelable;
            import android.support.v4.util.SimpleArrayMap;
            import android.util.AttributeSet;
            import android.util.Log;
            import android.view.KeyEvent;
            import android.view.Menu;
            import android.view.MenuItem;
            import android.view.View;
            import android.view.ViewGroup;
            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            import java.io.FileDescriptor;
            import java.io.PrintWriter;
            import java.util.ArrayList;
            import org.apache.commons.io.FilenameUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 83 */    public class FragmentActivity extends Activity {
                static final String FRAGMENTS_TAG = "android:support:fragments";
                private static final int HONEYCOMB = 11;
                static final int MSG_REALLY_STOPPED = 1;
                static final int MSG_RESUME_PENDING = 2;
                private static final String TAG = "FragmentActivity";
                SimpleArrayMap<String, LoaderManagerImpl> mAllLoaderManagers;
                boolean mCheckedForLoaderManager;
                boolean mCreated;
                LoaderManagerImpl mLoaderManager;
                boolean mLoadersStarted;
                boolean mOptionsMenuInvalidated;
                boolean mReallyStopped;
                boolean mResumed;
                boolean mRetaining;
                boolean mStopped;
                final Handler mHandler = new HandlerC00071();
                final FragmentManagerImpl mFragments = new FragmentManagerImpl();
                final FragmentContainer mContainer = new C00082();

                /* JADX INFO: renamed from: android.support.v4.app.FragmentActivity$1 */
/* 84 */        class HandlerC00071 extends Handler {
/* 85 */            HandlerC00071() {
                    }

                    @Override // android.os.Handler
/* 88 */            public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case 1:
/* 91 */                        if (FragmentActivity.this.mStopped) {
/* 92 */                            FragmentActivity.this.doReallyStop(false);
                                }
                                break;
                            case 2:
/* 96 */                        FragmentActivity.this.onResumeFragments();
/* 97 */                        FragmentActivity.this.mFragments.execPendingActions();
                                break;
                            default:
/* 100 */                       super.handleMessage(msg);
                                break;
                        }
                    }
                }

                /* JADX INFO: renamed from: android.support.v4.app.FragmentActivity$2 */
/* 104 */       class C00082 implements FragmentContainer {
/* 105 */           C00082() {
                    }

                    @Override // android.support.v4.app.FragmentContainer
/* 108 */           public View findViewById(int id) {
/* 109 */               return FragmentActivity.this.findViewById(id);
                    }
                }

/* 124 */       static final class NonConfigurationInstances {
                    Object activity;
                    SimpleArrayMap<String, Object> children;
                    Object custom;
                    ArrayList<Fragment> fragments;
                    SimpleArrayMap<String, LoaderManagerImpl> loaders;

/* 125 */           NonConfigurationInstances() {
                    }
                }

/* 132 */       static class FragmentTag {
                    public static final int[] Fragment = {R.attr.name, R.attr.id, R.attr.tag};
                    public static final int Fragment_id = 1;
                    public static final int Fragment_name = 0;
                    public static final int Fragment_tag = 2;

/* 133 */           FragmentTag() {
                    }
                }

                @Override // android.app.Activity
/* 151 */       protected void onActivityResult(int requestCode, int resultCode, Intent data) {
/* 152 */           this.mFragments.noteStateNotSaved();
/* 153 */           int index = requestCode >> 16;
/* 154 */           if (index != 0) {
/* 155 */               int index2 = index - 1;
/* 156 */               if (this.mFragments.mActive == null || index2 < 0 || index2 >= this.mFragments.mActive.size()) {
/* 157 */                   Log.w(TAG, "Activity result fragment index out of range: 0x" + Integer.toHexString(requestCode));
/* 172 */                   return;
                        }
/* 161 */               Fragment frag = this.mFragments.mActive.get(index2);
/* 162 */               if (frag == null) {
/* 163 */                   Log.w(TAG, "Activity result no fragment exists for index: 0x" + Integer.toHexString(requestCode));
/* 172 */                   return;
                        } else {
/* 166 */                   frag.onActivityResult(65535 & requestCode, resultCode, data);
/* 172 */                   return;
                        }
                    }
/* 171 */           super.onActivityResult(requestCode, resultCode, data);
                }

                @Override // android.app.Activity
/* 178 */       public void onBackPressed() {
/* 179 */           if (!this.mFragments.popBackStackImmediate()) {
/* 180 */               finish();
                    }
                }

                @Override // android.app.Activity, android.content.ComponentCallbacks
/* 188 */       public void onConfigurationChanged(Configuration newConfig) {
/* 189 */           super.onConfigurationChanged(newConfig);
/* 190 */           this.mFragments.dispatchConfigurationChanged(newConfig);
                }

                @Override // android.app.Activity
/* 197 */       protected void onCreate(Bundle savedInstanceState) {
/* 198 */           this.mFragments.attachActivity(this, this.mContainer, null);
/* 200 */           if (getLayoutInflater().getFactory() == null) {
/* 201 */               getLayoutInflater().setFactory(this);
                    }
/* 204 */           super.onCreate(savedInstanceState);
/* 206 */           NonConfigurationInstances nc = (NonConfigurationInstances) getLastNonConfigurationInstance();
/* 208 */           if (nc != null) {
/* 209 */               this.mAllLoaderManagers = nc.loaders;
                    }
/* 211 */           if (savedInstanceState != null) {
/* 212 */               Parcelable p = savedInstanceState.getParcelable(FRAGMENTS_TAG);
/* 213 */               this.mFragments.restoreAllState(p, nc != null ? nc.fragments : null);
                    }
/* 215 */           this.mFragments.dispatchCreate();
                }

                @Override // android.app.Activity, android.view.Window.Callback
/* 222 */       public boolean onCreatePanelMenu(int featureId, Menu menu) {
/* 223 */           if (featureId == 0) {
/* 224 */               boolean show = super.onCreatePanelMenu(featureId, menu);
/* 225 */               boolean show2 = show | this.mFragments.dispatchCreateOptionsMenu(menu, getMenuInflater());
/* 226 */               if (Build.VERSION.SDK_INT < 11) {
/* 232 */                   return true;
                        }
/* 234 */               return show2;
                    }
/* 234 */           boolean show3 = super.onCreatePanelMenu(featureId, menu);
/* 234 */           return show3;
                }

                @Override // android.app.Activity, android.view.LayoutInflater.Factory
/* 241 */       public View onCreateView(String name, Context context, AttributeSet attrs) {
/* 242 */           if (!"fragment".equals(name)) {
/* 243 */               return super.onCreateView(name, context, attrs);
                    }
/* 246 */           String fname = attrs.getAttributeValue(null, "class");
/* 247 */           TypedArray a = context.obtainStyledAttributes(attrs, FragmentTag.Fragment);
/* 248 */           if (fname == null) {
/* 249 */               fname = a.getString(0);
                    }
/* 251 */           int id = a.getResourceId(1, -1);
/* 252 */           String tag = a.getString(2);
/* 253 */           a.recycle();
/* 255 */           if (!Fragment.isSupportFragmentClass(this, fname)) {
/* 258 */               return super.onCreateView(name, context, attrs);
                    }
/* 261 */           View parent = null;
/* 262 */           int containerId = 0 != 0 ? parent.getId() : 0;
/* 263 */           if (containerId == -1 && id == -1 && tag == null) {
/* 264 */               throw new IllegalArgumentException(attrs.getPositionDescription() + ": Must specify unique android:id, android:tag, or have a parent with an id for " + fname);
                    }
/* 271 */           Fragment fragment = id != -1 ? this.mFragments.findFragmentById(id) : null;
/* 272 */           if (fragment == null && tag != null) {
/* 273 */               fragment = this.mFragments.findFragmentByTag(tag);
                    }
/* 275 */           if (fragment == null && containerId != -1) {
/* 276 */               fragment = this.mFragments.findFragmentById(containerId);
                    }
/* 279 */           if (FragmentManagerImpl.DEBUG) {
                        Log.v(TAG, "onCreateView: id=0x" + Integer.toHexString(id) + " fname=" + fname + " existing=" + fragment);
                    }
/* 282 */           if (fragment == null) {
/* 283 */               fragment = Fragment.instantiate(this, fname);
/* 284 */               fragment.mFromLayout = true;
/* 285 */               fragment.mFragmentId = id != 0 ? id : containerId;
/* 286 */               fragment.mContainerId = containerId;
/* 287 */               fragment.mTag = tag;
/* 288 */               fragment.mInLayout = true;
/* 289 */               fragment.mFragmentManager = this.mFragments;
/* 290 */               fragment.onInflate(this, attrs, fragment.mSavedFragmentState);
/* 291 */               this.mFragments.addFragment(fragment, true);
                    } else {
/* 293 */               if (fragment.mInLayout) {
/* 296 */                   throw new IllegalArgumentException(attrs.getPositionDescription() + ": Duplicate id 0x" + Integer.toHexString(id) + ", tag " + tag + ", or parent id 0x" + Integer.toHexString(containerId) + " with another fragment for " + fname);
                        }
/* 303 */               fragment.mInLayout = true;
/* 307 */               if (!fragment.mRetaining) {
/* 308 */                   fragment.onInflate(this, attrs, fragment.mSavedFragmentState);
                        }
/* 310 */               this.mFragments.moveToState(fragment);
                    }
/* 313 */           if (fragment.mView == null) {
/* 314 */               throw new IllegalStateException("Fragment " + fname + " did not create a view.");
                    }
/* 317 */           if (id != 0) {
/* 318 */               fragment.mView.setId(id);
                    }
/* 320 */           if (fragment.mView.getTag() == null) {
/* 321 */               fragment.mView.setTag(tag);
                    }
/* 323 */           return fragment.mView;
                }

                @Override // android.app.Activity
/* 330 */       protected void onDestroy() {
/* 331 */           super.onDestroy();
/* 333 */           doReallyStop(false);
/* 335 */           this.mFragments.dispatchDestroy();
/* 336 */           if (this.mLoaderManager != null) {
/* 337 */               this.mLoaderManager.doDestroy();
                    }
                }

                @Override // android.app.Activity, android.view.KeyEvent.Callback
/* 345 */       public boolean onKeyDown(int keyCode, KeyEvent event) {
/* 346 */           if (Build.VERSION.SDK_INT >= 5 || keyCode != 4 || event.getRepeatCount() != 0) {
                        return super.onKeyDown(keyCode, event);
                    }
/* 351 */           onBackPressed();
/* 352 */           return true;
                }

                @Override // android.app.Activity, android.content.ComponentCallbacks
/* 362 */       public void onLowMemory() {
/* 363 */           super.onLowMemory();
/* 364 */           this.mFragments.dispatchLowMemory();
                }

                @Override // android.app.Activity, android.view.Window.Callback
/* 371 */       public boolean onMenuItemSelected(int featureId, MenuItem item) {
/* 372 */           if (super.onMenuItemSelected(featureId, item)) {
/* 373 */               return true;
                    }
                    switch (featureId) {
                        case 0:
/* 378 */                   return this.mFragments.dispatchOptionsItemSelected(item);
                        case 6:
/* 381 */                   return this.mFragments.dispatchContextItemSelected(item);
                        default:
/* 384 */                   return false;
                    }
                }

                @Override // android.app.Activity, android.view.Window.Callback
/* 392 */       public void onPanelClosed(int featureId, Menu menu) {
                    switch (featureId) {
                        case 0:
/* 395 */                   this.mFragments.dispatchOptionsMenuClosed(menu);
                            break;
                    }
/* 398 */           super.onPanelClosed(featureId, menu);
                }

                @Override // android.app.Activity
/* 405 */       protected void onPause() {
/* 406 */           super.onPause();
/* 407 */           this.mResumed = false;
/* 408 */           if (this.mHandler.hasMessages(2)) {
/* 409 */               this.mHandler.removeMessages(2);
/* 410 */               onResumeFragments();
                    }
/* 412 */           this.mFragments.dispatchPause();
                }

                @Override // android.app.Activity
/* 426 */       protected void onNewIntent(Intent intent) {
/* 427 */           super.onNewIntent(intent);
/* 428 */           this.mFragments.noteStateNotSaved();
                }

                @Override // android.app.Activity
/* 441 */       protected void onResume() {
/* 442 */           super.onResume();
/* 443 */           this.mHandler.sendEmptyMessage(2);
/* 444 */           this.mResumed = true;
/* 445 */           this.mFragments.execPendingActions();
                }

                @Override // android.app.Activity
/* 452 */       protected void onPostResume() {
/* 453 */           super.onPostResume();
/* 454 */           this.mHandler.removeMessages(2);
/* 455 */           onResumeFragments();
/* 456 */           this.mFragments.execPendingActions();
                }

/* 465 */       protected void onResumeFragments() {
/* 466 */           this.mFragments.dispatchResume();
                }

                @Override // android.app.Activity, android.view.Window.Callback
/* 473 */       public boolean onPreparePanel(int featureId, View view, Menu menu) {
/* 474 */           if (featureId == 0 && menu != null) {
/* 475 */               if (this.mOptionsMenuInvalidated) {
/* 476 */                   this.mOptionsMenuInvalidated = false;
/* 477 */                   menu.clear();
/* 478 */                   onCreatePanelMenu(featureId, menu);
                        }
/* 480 */               boolean goforit = onPrepareOptionsPanel(view, menu);
/* 481 */               return goforit | this.mFragments.dispatchPrepareOptionsMenu(menu);
                    }
                    boolean goforit2 = super.onPreparePanel(featureId, view, menu);
/* 484 */           return goforit2;
                }

/* 490 */       protected boolean onPrepareOptionsPanel(View view, Menu menu) {
/* 491 */           return super.onPreparePanel(0, view, menu);
                }

                @Override // android.app.Activity
/* 500 */       public final Object onRetainNonConfigurationInstance() {
/* 501 */           if (this.mStopped) {
/* 502 */               doReallyStop(true);
                    }
/* 505 */           Object custom = onRetainCustomNonConfigurationInstance();
/* 507 */           ArrayList<Fragment> fragments = this.mFragments.retainNonConfig();
/* 508 */           boolean retainLoaders = false;
/* 509 */           if (this.mAllLoaderManagers != null) {
/* 512 */               int N = this.mAllLoaderManagers.size();
/* 513 */               LoaderManagerImpl[] loaders = new LoaderManagerImpl[N];
                        for (int i = N - 1; i >= 0; i--) {
/* 515 */                   loaders[i] = this.mAllLoaderManagers.valueAt(i);
                        }
                        for (int i2 = 0; i2 < N; i2++) {
/* 518 */                   LoaderManagerImpl lm = loaders[i2];
/* 519 */                   if (lm.mRetaining) {
/* 520 */                       retainLoaders = true;
                            } else {
/* 522 */                       lm.doDestroy();
/* 523 */                       this.mAllLoaderManagers.remove(lm.mWho);
                            }
                        }
                    }
/* 527 */           if (fragments == null && !retainLoaders && custom == null) {
                        return null;
                    }
/* 531 */           NonConfigurationInstances nci = new NonConfigurationInstances();
/* 532 */           nci.activity = null;
/* 533 */           nci.custom = custom;
/* 534 */           nci.children = null;
/* 535 */           nci.fragments = fragments;
/* 536 */           nci.loaders = this.mAllLoaderManagers;
/* 537 */           return nci;
                }

                @Override // android.app.Activity
/* 544 */       protected void onSaveInstanceState(Bundle outState) {
/* 545 */           super.onSaveInstanceState(outState);
/* 546 */           Parcelable p = this.mFragments.saveAllState();
/* 547 */           if (p != null) {
/* 548 */               outState.putParcelable(FRAGMENTS_TAG, p);
                    }
                }

                @Override // android.app.Activity
/* 557 */       protected void onStart() {
/* 558 */           super.onStart();
/* 560 */           this.mStopped = false;
/* 561 */           this.mReallyStopped = false;
/* 562 */           this.mHandler.removeMessages(1);
/* 564 */           if (!this.mCreated) {
/* 565 */               this.mCreated = true;
/* 566 */               this.mFragments.dispatchActivityCreated();
                    }
/* 569 */           this.mFragments.noteStateNotSaved();
/* 570 */           this.mFragments.execPendingActions();
/* 572 */           if (!this.mLoadersStarted) {
/* 573 */               this.mLoadersStarted = true;
/* 574 */               if (this.mLoaderManager != null) {
/* 575 */                   this.mLoaderManager.doStart();
                        } else if (!this.mCheckedForLoaderManager) {
/* 577 */                   this.mLoaderManager = getLoaderManager("(root)", this.mLoadersStarted, false);
/* 579 */                   if (this.mLoaderManager != null && !this.mLoaderManager.mStarted) {
/* 580 */                       this.mLoaderManager.doStart();
                            }
                        }
/* 583 */               this.mCheckedForLoaderManager = true;
                    }
/* 587 */           this.mFragments.dispatchStart();
/* 588 */           if (this.mAllLoaderManagers != null) {
/* 589 */               int N = this.mAllLoaderManagers.size();
/* 590 */               LoaderManagerImpl[] loaders = new LoaderManagerImpl[N];
                        for (int i = N - 1; i >= 0; i--) {
/* 592 */                   loaders[i] = this.mAllLoaderManagers.valueAt(i);
                        }
                        for (int i2 = 0; i2 < N; i2++) {
/* 595 */                   LoaderManagerImpl lm = loaders[i2];
/* 596 */                   lm.finishRetain();
/* 597 */                   lm.doReportStart();
                        }
                    }
                }

                @Override // android.app.Activity
/* 606 */       protected void onStop() {
/* 607 */           super.onStop();
/* 609 */           this.mStopped = true;
/* 610 */           this.mHandler.sendEmptyMessage(1);
/* 612 */           this.mFragments.dispatchStop();
                }

/* 623 */       public Object onRetainCustomNonConfigurationInstance() {
/* 624 */           return null;
                }

/* 631 */       public Object getLastCustomNonConfigurationInstance() {
/* 632 */           NonConfigurationInstances nc = (NonConfigurationInstances) getLastNonConfigurationInstance();
/* 634 */           if (nc != null) {
                        return nc.custom;
                    }
                    return null;
                }

/* 644 */       public void supportInvalidateOptionsMenu() {
/* 645 */           if (Build.VERSION.SDK_INT >= 11) {
/* 648 */               ActivityCompatHoneycomb.invalidateOptionsMenu(this);
                    } else {
/* 654 */               this.mOptionsMenuInvalidated = true;
                    }
                }

                @Override // android.app.Activity
/* 667 */       public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
/* 668 */           if (Build.VERSION.SDK_INT >= 11) {
                    }
/* 672 */           writer.print(prefix);
                    writer.print("Local FragmentActivity ");
/* 673 */           writer.print(Integer.toHexString(System.identityHashCode(this)));
/* 674 */           writer.println(" State:");
/* 675 */           String innerPrefix = prefix + "  ";
/* 676 */           writer.print(innerPrefix);
                    writer.print("mCreated=");
/* 677 */           writer.print(this.mCreated);
                    writer.print("mResumed=");
/* 678 */           writer.print(this.mResumed);
                    writer.print(" mStopped=");
/* 679 */           writer.print(this.mStopped);
                    writer.print(" mReallyStopped=");
/* 680 */           writer.println(this.mReallyStopped);
/* 681 */           writer.print(innerPrefix);
                    writer.print("mLoadersStarted=");
/* 682 */           writer.println(this.mLoadersStarted);
/* 683 */           if (this.mLoaderManager != null) {
/* 684 */               writer.print(prefix);
                        writer.print("Loader Manager ");
/* 685 */               writer.print(Integer.toHexString(System.identityHashCode(this.mLoaderManager)));
/* 686 */               writer.println(":");
/* 687 */               this.mLoaderManager.dump(prefix + "  ", fd, writer, args);
                    }
/* 689 */           this.mFragments.dump(prefix, fd, writer, args);
/* 690 */           writer.print(prefix);
                    writer.println("View Hierarchy:");
/* 691 */           dumpViewHierarchy(prefix + "  ", writer, getWindow().getDecorView());
                }

                /* JADX WARN: Failed to find 'out' block for switch in B:40:0x00ed. Please report as an issue. */
/* 694 */       private static String viewToString(View view) {
                    String pkgname;
                    char c = FilenameUtils.EXTENSION_SEPARATOR;
/* 695 */           StringBuilder out = new StringBuilder(128);
/* 696 */           out.append(view.getClass().getName());
/* 697 */           out.append('{');
/* 698 */           out.append(Integer.toHexString(System.identityHashCode(view)));
/* 699 */           out.append(' ');
                    switch (view.getVisibility()) {
                        case 0:
/* 701 */                   out.append('V');
                            break;
                        case 4:
/* 702 */                   out.append('I');
                            break;
                        case 8:
/* 703 */                   out.append('G');
                            break;
                        default:
/* 704 */                   out.append(FilenameUtils.EXTENSION_SEPARATOR);
                            break;
                    }
/* 706 */           out.append(view.isFocusable() ? 'F' : '.');
/* 707 */           out.append(view.isEnabled() ? 'E' : '.');
/* 708 */           out.append(view.willNotDraw() ? '.' : 'D');
/* 709 */           out.append(view.isHorizontalScrollBarEnabled() ? 'H' : '.');
/* 710 */           out.append(view.isVerticalScrollBarEnabled() ? 'V' : '.');
/* 711 */           out.append(view.isClickable() ? 'C' : '.');
/* 712 */           out.append(view.isLongClickable() ? 'L' : '.');
/* 713 */           out.append(' ');
/* 714 */           out.append(view.isFocused() ? 'F' : '.');
/* 715 */           out.append(view.isSelected() ? 'S' : '.');
/* 716 */           if (view.isPressed()) {
                        c = 'P';
                    }
                    out.append(c);
/* 717 */           out.append(' ');
/* 718 */           out.append(view.getLeft());
/* 719 */           out.append(',');
/* 720 */           out.append(view.getTop());
/* 721 */           out.append('-');
/* 722 */           out.append(view.getRight());
/* 723 */           out.append(',');
/* 724 */           out.append(view.getBottom());
/* 725 */           int id = view.getId();
/* 726 */           if (id != -1) {
/* 727 */               out.append(" #");
/* 728 */               out.append(Integer.toHexString(id));
/* 729 */               Resources r = view.getResources();
/* 730 */               if (id != 0 && r != null) {
                            switch ((-16777216) & id) {
                                case 16777216:
/* 738 */                           pkgname = "android";
/* 744 */                           String typename = r.getResourceTypeName(id);
/* 745 */                           String entryname = r.getResourceEntryName(id);
/* 746 */                           out.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
/* 747 */                           out.append(pkgname);
/* 748 */                           out.append(":");
/* 749 */                           out.append(typename);
/* 750 */                           out.append("/");
/* 751 */                           out.append(entryname);
                                    break;
                                case 2130706432:
/* 735 */                           pkgname = "app";
/* 744 */                           String typename2 = r.getResourceTypeName(id);
/* 745 */                           String entryname2 = r.getResourceEntryName(id);
/* 746 */                           out.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
/* 747 */                           out.append(pkgname);
/* 748 */                           out.append(":");
/* 749 */                           out.append(typename2);
/* 750 */                           out.append("/");
/* 751 */                           out.append(entryname2);
                                    break;
                                default:
                                    try {
/* 741 */                               pkgname = r.getResourcePackageName(id);
/* 744 */                               String typename3 = r.getResourceTypeName(id);
/* 745 */                               String entryname3 = r.getResourceEntryName(id);
/* 746 */                               out.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
/* 747 */                               out.append(pkgname);
/* 748 */                               out.append(":");
/* 749 */                               out.append(typename3);
/* 750 */                               out.append("/");
/* 751 */                               out.append(entryname3);
                                    } catch (Resources.NotFoundException e) {
                                    }
                                    break;
                            }
                        }
                    }
/* 756 */           out.append("}");
/* 757 */           return out.toString();
                }

/* 760 */       private void dumpViewHierarchy(String prefix, PrintWriter writer, View view) {
                    ViewGroup grp;
                    int N;
/* 761 */           writer.print(prefix);
/* 762 */           if (view == null) {
/* 763 */               writer.println("null");
/* 779 */               return;
                    }
/* 766 */           writer.println(viewToString(view));
/* 767 */           if ((view instanceof ViewGroup) && (N = (grp = (ViewGroup) view).getChildCount()) > 0) {
/* 775 */               String prefix2 = prefix + "  ";
                        for (int i = 0; i < N; i++) {
/* 777 */                   dumpViewHierarchy(prefix2, writer, grp.getChildAt(i));
                        }
                    }
                }

/* 781 */       void doReallyStop(boolean retaining) {
/* 782 */           if (!this.mReallyStopped) {
/* 783 */               this.mReallyStopped = true;
/* 784 */               this.mRetaining = retaining;
/* 785 */               this.mHandler.removeMessages(1);
/* 786 */               onReallyStop();
                    }
                }

/* 797 */       void onReallyStop() {
/* 798 */           if (this.mLoadersStarted) {
/* 799 */               this.mLoadersStarted = false;
/* 800 */               if (this.mLoaderManager != null) {
/* 801 */                   if (!this.mRetaining) {
/* 802 */                       this.mLoaderManager.doStop();
                            } else {
/* 804 */                       this.mLoaderManager.doRetain();
                            }
                        }
                    }
/* 809 */           this.mFragments.dispatchReallyStop();
                }

/* 819 */       public void onAttachFragment(Fragment fragment) {
                }

/* 826 */       public FragmentManager getSupportFragmentManager() {
/* 827 */           return this.mFragments;
                }

                @Override // android.app.Activity
/* 835 */       public void startActivityForResult(Intent intent, int requestCode) {
/* 836 */           if (requestCode != -1 && ((-65536) & requestCode) != 0) {
/* 837 */               throw new IllegalArgumentException("Can only use lower 16 bits for requestCode");
                    }
/* 839 */           super.startActivityForResult(intent, requestCode);
                }

/* 846 */       public void startActivityFromFragment(Fragment fragment, Intent intent, int requestCode) {
/* 847 */           if (requestCode == -1) {
/* 848 */               super.startActivityForResult(intent, -1);
                    } else {
/* 851 */               if (((-65536) & requestCode) != 0) {
/* 852 */                   throw new IllegalArgumentException("Can only use lower 16 bits for requestCode");
                        }
/* 854 */               super.startActivityForResult(intent, ((fragment.mIndex + 1) << 16) + (65535 & requestCode));
                    }
                }

/* 858 */       void invalidateSupportFragment(String who) {
                    LoaderManagerImpl lm;
/* 859 */           if (this.mAllLoaderManagers != null && (lm = this.mAllLoaderManagers.get(who)) != null && !lm.mRetaining) {
/* 862 */               lm.doDestroy();
/* 863 */               this.mAllLoaderManagers.remove(who);
                    }
                }

/* 875 */       public LoaderManager getSupportLoaderManager() {
/* 876 */           if (this.mLoaderManager != null) {
/* 877 */               return this.mLoaderManager;
                    }
/* 879 */           this.mCheckedForLoaderManager = true;
/* 880 */           this.mLoaderManager = getLoaderManager("(root)", this.mLoadersStarted, true);
/* 881 */           return this.mLoaderManager;
                }

/* 884 */       LoaderManagerImpl getLoaderManager(String who, boolean started, boolean create) {
/* 885 */           if (this.mAllLoaderManagers == null) {
/* 886 */               this.mAllLoaderManagers = new SimpleArrayMap<>();
                    }
/* 888 */           LoaderManagerImpl lm = this.mAllLoaderManagers.get(who);
/* 889 */           if (lm == null) {
/* 890 */               if (create) {
/* 891 */                   LoaderManagerImpl lm2 = new LoaderManagerImpl(who, this, started);
/* 892 */                   this.mAllLoaderManagers.put(who, lm2);
/* 897 */                   return lm2;
                        }
/* 897 */               return lm;
                    }
/* 895 */           lm.updateActivity(this);
/* 897 */           return lm;
                }
            }
