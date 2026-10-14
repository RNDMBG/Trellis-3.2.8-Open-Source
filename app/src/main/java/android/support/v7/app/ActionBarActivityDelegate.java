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
            
            
    abstract class ActionBarActivityDelegate {
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

        static ActionBarActivityDelegate createDelegate(ActionBarActivity activity) {
            if (Build.VERSION.SDK_INT >= 18) {
                return new ActionBarActivityDelegateJBMR2(activity);
                    }
            if (Build.VERSION.SDK_INT >= 16) {
                return new ActionBarActivityDelegateJB(activity);
                    }
            if (Build.VERSION.SDK_INT >= 14) {
                return new ActionBarActivityDelegateICS(activity);
                    }
            if (Build.VERSION.SDK_INT >= 11) {
                return new ActionBarActivityDelegateHC(activity);
                    }
            return new ActionBarActivityDelegateBase(activity);
                }

        ActionBarActivityDelegate(ActionBarActivity activity) {
            this.mActivity = activity;
                }

        final ActionBar getSupportActionBar() {
            if (this.mHasActionBar || this.mOverlayActionBar) {
                if (this.mActionBar == null) {
                    this.mActionBar = createSupportActionBar();
                    if (this.mEnableDefaultActionBarUp) {
                        this.mActionBar.setDisplayHomeAsUpEnabled(true);
                            }
                        }
                    } else {
                this.mActionBar = null;
                    }
            return this.mActionBar;
                }

        MenuInflater getMenuInflater() {
            if (this.mMenuInflater == null) {
                this.mMenuInflater = new SupportMenuInflater(getActionBarThemedContext());
                    }
           return this.mMenuInflater;
                }

       void onCreate(Bundle savedInstanceState) {
           TypedArray a = this.mActivity.obtainStyledAttributes(C0084R.styleable.ActionBarWindow);
           if (!a.hasValue(0)) {
               a.recycle();
               throw new IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.");
                    }
           this.mHasActionBar = a.getBoolean(0, false);
           this.mOverlayActionBar = a.getBoolean(1, false);
           a.recycle();
           if (NavUtils.getParentActivityName(this.mActivity) != null) {
               if (this.mActionBar == null) {
                   this.mEnableDefaultActionBarUp = true;
                        } else {
                   this.mActionBar.setDisplayHomeAsUpEnabled(true);
                        }
                    }
                }

       boolean onPrepareOptionsPanel(View view, Menu menu) {
                    return Build.VERSION.SDK_INT < 16 ? this.mActivity.onPrepareOptionsMenu(menu) : this.mActivity.superOnPrepareOptionsPanel(view, menu);
                }

       final ActionBarDrawerToggle.Delegate getDrawerToggleDelegate() {
           return new ActionBarDrawableToggleImpl(this, null);
                }

       protected final String getUiOptionsFromMetadata() {
                    try {
               PackageManager pm = this.mActivity.getPackageManager();
               ActivityInfo info = pm.getActivityInfo(this.mActivity.getComponentName(), 128);
               if (info.metaData == null) {
                   return null;
                        }
               String uiOptions = info.metaData.getString(METADATA_UI_OPTIONS);
               return uiOptions;
                    } catch (PackageManager.NameNotFoundException e) {
               Log.e(TAG, "getUiOptionsFromMetadata: Activity '" + this.mActivity.getClass().getSimpleName() + "' not in manifest");
               return null;
                    }
                }

       protected final Context getActionBarThemedContext() {
           Context context = this.mActivity;
           ActionBar ab = getSupportActionBar();
           if (ab != null) {
               Context context2 = ab.getThemedContext();
               return context2;
                    }
           return context;
                }

       private class ActionBarDrawableToggleImpl implements ActionBarDrawerToggle.Delegate {
           private ActionBarDrawableToggleImpl() {
                    }

            ActionBarDrawableToggleImpl(ActionBarActivityDelegate x0, C00821 x1) {
               this();
                    }

                    @Override 
           public Drawable getThemeUpIndicator() {
               TypedArray a = ActionBarActivityDelegate.this.mActivity.obtainStyledAttributes(new int[]{ActionBarActivityDelegate.this.getHomeAsUpIndicatorAttrId()});
               Drawable result = a.getDrawable(0);
               a.recycle();
               return result;
                    }

                    @Override 
           public void setActionBarUpIndicator(Drawable upDrawable, int contentDescRes) {
               ActionBar ab = ActionBarActivityDelegate.this.getSupportActionBar();
               if (ab != null) {
                   ab.setHomeAsUpIndicator(upDrawable);
                   ab.setHomeActionContentDescription(contentDescRes);
                        }
                    }

                    @Override 
           public void setActionBarDescription(int contentDescRes) {
               ActionBar ab = ActionBarActivityDelegate.this.getSupportActionBar();
               if (ab != null) {
                   ab.setHomeActionContentDescription(contentDescRes);
                        }
                    }
                }
            }
