            package android.support.v7.app;

            import android.content.Context;
            import android.content.pm.ActivityInfo;
            import android.content.pm.PackageManager;
            import android.content.res.Configuration;
            import android.content.res.TypedArray;
            import android.graphics.drawable.Drawable;
            import android.os.Build;
            import android.os.Bundle;
            import android.support.v4.app.ActionBarDrawerToggle;
            import android.support.v4.app.NavUtils;
            import android.support.v7.appcompat.C0084R;
            import android.support.v7.internal.view.SupportMenuInflater;
            import android.support.v7.view.ActionMode;
            import android.util.Log;
            import android.view.Menu;
            import android.view.MenuInflater;
            import android.view.MenuItem;
            import android.view.View;
            import android.view.ViewGroup;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 45 */    abstract class ActionBarActivityDelegate {
                static final String METADATA_UI_OPTIONS = "android.support.UI_OPTIONS";
                private static final String TAG = "ActionBarActivityDelegate";
                static final String UIOPTION_SPLIT_ACTION_BAR_WHEN_NARROW = "splitActionBarWhenNarrow";
                private ActionBar mActionBar;
                final ActionBarActivity mActivity;
                private boolean mEnableDefaultActionBarUp;
                boolean mHasActionBar;
                private MenuInflater mMenuInflater;
                boolean mOverlayActionBar;

                abstract void addContentView(View view, ViewGroup.LayoutParams layoutParams);

                abstract ActionBar createSupportActionBar();

                abstract int getHomeAsUpIndicatorAttrId();

                abstract boolean onBackPressed();

                abstract void onConfigurationChanged(Configuration configuration);

                abstract void onContentChanged();

                abstract boolean onCreatePanelMenu(int i, Menu menu);

                abstract View onCreatePanelView(int i);

                abstract boolean onMenuItemSelected(int i, MenuItem menuItem);

                abstract void onPostResume();

                abstract boolean onPreparePanel(int i, View view, Menu menu);

                abstract void onStop();

                abstract void onTitleChanged(CharSequence charSequence);

                abstract void setContentView(int i);

                abstract void setContentView(View view);

                abstract void setContentView(View view, ViewGroup.LayoutParams layoutParams);

                abstract void setSupportProgress(int i);

                abstract void setSupportProgressBarIndeterminate(boolean z);

                abstract void setSupportProgressBarIndeterminateVisibility(boolean z);

                abstract void setSupportProgressBarVisibility(boolean z);

                abstract ActionMode startSupportActionMode(ActionMode.Callback callback);

                abstract void supportInvalidateOptionsMenu();

                abstract boolean supportRequestWindowFeature(int i);

/* 46 */        static ActionBarActivityDelegate createDelegate(ActionBarActivity activity) {
/* 47 */            if (Build.VERSION.SDK_INT >= 18) {
/* 48 */                return new ActionBarActivityDelegateJBMR2(activity);
                    }
/* 49 */            if (Build.VERSION.SDK_INT >= 16) {
/* 50 */                return new ActionBarActivityDelegateJB(activity);
                    }
/* 51 */            if (Build.VERSION.SDK_INT >= 14) {
/* 52 */                return new ActionBarActivityDelegateICS(activity);
                    }
/* 53 */            if (Build.VERSION.SDK_INT >= 11) {
/* 54 */                return new ActionBarActivityDelegateHC(activity);
                    }
/* 56 */            return new ActionBarActivityDelegateBase(activity);
                }

/* 71 */        ActionBarActivityDelegate(ActionBarActivity activity) {
/* 73 */            this.mActivity = activity;
                }

/* 80 */        final ActionBar getSupportActionBar() {
/* 81 */            if (this.mHasActionBar || this.mOverlayActionBar) {
/* 82 */                if (this.mActionBar == null) {
/* 83 */                    this.mActionBar = createSupportActionBar();
/* 85 */                    if (this.mEnableDefaultActionBarUp) {
/* 86 */                        this.mActionBar.setDisplayHomeAsUpEnabled(true);
                            }
                        }
                    } else {
/* 91 */                this.mActionBar = null;
                    }
/* 93 */            return this.mActionBar;
                }

/* 96 */        MenuInflater getMenuInflater() {
/* 97 */            if (this.mMenuInflater == null) {
/* 98 */                this.mMenuInflater = new SupportMenuInflater(getActionBarThemedContext());
                    }
/* 100 */           return this.mMenuInflater;
                }

/* 103 */       void onCreate(Bundle savedInstanceState) {
/* 104 */           TypedArray a = this.mActivity.obtainStyledAttributes(C0084R.styleable.ActionBarWindow);
/* 106 */           if (!a.hasValue(0)) {
/* 107 */               a.recycle();
/* 108 */               throw new IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.");
                    }
/* 112 */           this.mHasActionBar = a.getBoolean(0, false);
/* 113 */           this.mOverlayActionBar = a.getBoolean(1, false);
/* 114 */           a.recycle();
/* 116 */           if (NavUtils.getParentActivityName(this.mActivity) != null) {
/* 117 */               if (this.mActionBar == null) {
/* 118 */                   this.mEnableDefaultActionBarUp = true;
                        } else {
/* 120 */                   this.mActionBar.setDisplayHomeAsUpEnabled(true);
                        }
                    }
                }

/* 150 */       boolean onPrepareOptionsPanel(View view, Menu menu) {
                    return Build.VERSION.SDK_INT < 16 ? this.mActivity.onPrepareOptionsMenu(menu) : this.mActivity.superOnPrepareOptionsPanel(view, menu);
                }

/* 176 */       final ActionBarDrawerToggle.Delegate getDrawerToggleDelegate() {
/* 177 */           return new ActionBarDrawableToggleImpl(this, null);
                }

/* 185 */       protected final String getUiOptionsFromMetadata() {
                    try {
/* 186 */               PackageManager pm = this.mActivity.getPackageManager();
/* 187 */               ActivityInfo info = pm.getActivityInfo(this.mActivity.getComponentName(), 128);
/* 191 */               if (info.metaData == null) {
/* 190 */                   return null;
                        }
/* 192 */               String uiOptions = info.metaData.getString(METADATA_UI_OPTIONS);
/* 198 */               return uiOptions;
                    } catch (PackageManager.NameNotFoundException e) {
/* 196 */               Log.e(TAG, "getUiOptionsFromMetadata: Activity '" + this.mActivity.getClass().getSimpleName() + "' not in manifest");
/* 198 */               return null;
                    }
                }

/* 202 */       protected final Context getActionBarThemedContext() {
/* 203 */           Context context = this.mActivity;
/* 206 */           ActionBar ab = getSupportActionBar();
/* 207 */           if (ab != null) {
/* 208 */               Context context2 = ab.getThemedContext();
/* 210 */               return context2;
                    }
/* 210 */           return context;
                }

/* 211 */       private class ActionBarDrawableToggleImpl implements ActionBarDrawerToggle.Delegate {
/* 212 */           private ActionBarDrawableToggleImpl() {
                    }

/* 212 */           /* synthetic */ ActionBarDrawableToggleImpl(ActionBarActivityDelegate x0, C00821 x1) {
/* 213 */               this();
                    }

                    @Override // android.support.v4.app.ActionBarDrawerToggle.Delegate
/* 215 */           public Drawable getThemeUpIndicator() {
/* 216 */               TypedArray a = ActionBarActivityDelegate.this.mActivity.obtainStyledAttributes(new int[]{ActionBarActivityDelegate.this.getHomeAsUpIndicatorAttrId()});
/* 218 */               Drawable result = a.getDrawable(0);
/* 219 */               a.recycle();
/* 220 */               return result;
                    }

                    @Override // android.support.v4.app.ActionBarDrawerToggle.Delegate
/* 224 */           public void setActionBarUpIndicator(Drawable upDrawable, int contentDescRes) {
/* 225 */               ActionBar ab = ActionBarActivityDelegate.this.getSupportActionBar();
/* 226 */               if (ab != null) {
/* 227 */                   ab.setHomeAsUpIndicator(upDrawable);
/* 228 */                   ab.setHomeActionContentDescription(contentDescRes);
                        }
                    }

                    @Override // android.support.v4.app.ActionBarDrawerToggle.Delegate
/* 233 */           public void setActionBarDescription(int contentDescRes) {
/* 234 */               ActionBar ab = ActionBarActivityDelegate.this.getSupportActionBar();
/* 235 */               if (ab != null) {
/* 236 */                   ab.setHomeActionContentDescription(contentDescRes);
                        }
                    }
                }
            }
