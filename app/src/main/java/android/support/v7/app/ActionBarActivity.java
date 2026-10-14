            package android.support.v7.app;

            import android.content.Intent;
            import android.content.res.Configuration;
            import android.os.Build;
            import android.os.Bundle;
            import android.support.v4.app.ActionBarDrawerToggle;
            import android.support.v4.app.ActivityCompat;
            import android.support.v4.app.FragmentActivity;
            import android.support.v4.app.NavUtils;
            import android.support.v4.app.TaskStackBuilder;
            import android.support.v7.view.ActionMode;
            import android.view.Menu;
            import android.view.MenuInflater;
            import android.view.MenuItem;
            import android.view.View;
            import android.view.ViewGroup;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 64 */    public class ActionBarActivity extends FragmentActivity implements ActionBar.Callback, TaskStackBuilder.SupportParentable, ActionBarDrawerToggle.DelegateProvider {
                ActionBarActivityDelegate mImpl;

/* 65 */        public ActionBar getSupportActionBar() {
/* 66 */            return this.mImpl.getSupportActionBar();
                }

                @Override // android.app.Activity
/* 70 */        public MenuInflater getMenuInflater() {
/* 71 */            return this.mImpl.getMenuInflater();
                }

                @Override // android.app.Activity
/* 75 */        public void setContentView(int layoutResID) {
/* 76 */            this.mImpl.setContentView(layoutResID);
                }

                @Override // android.app.Activity
/* 80 */        public void setContentView(View view) {
/* 81 */            this.mImpl.setContentView(view);
                }

                @Override // android.app.Activity
/* 85 */        public void setContentView(View view, ViewGroup.LayoutParams params) {
/* 86 */            this.mImpl.setContentView(view, params);
                }

                @Override // android.app.Activity
/* 90 */        public void addContentView(View view, ViewGroup.LayoutParams params) {
/* 91 */            this.mImpl.addContentView(view, params);
                }

                @Override // android.support.v4.app.FragmentActivity, android.app.Activity
/* 95 */        protected void onCreate(Bundle savedInstanceState) {
/* 96 */            this.mImpl = ActionBarActivityDelegate.createDelegate(this);
/* 97 */            super.onCreate(savedInstanceState);
/* 98 */            this.mImpl.onCreate(savedInstanceState);
                }

                @Override // android.support.v4.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
/* 102 */       public void onConfigurationChanged(Configuration newConfig) {
/* 103 */           super.onConfigurationChanged(newConfig);
/* 104 */           this.mImpl.onConfigurationChanged(newConfig);
                }

                @Override // android.support.v4.app.FragmentActivity, android.app.Activity
/* 108 */       protected void onStop() {
/* 109 */           super.onStop();
/* 110 */           this.mImpl.onStop();
                }

                @Override // android.support.v4.app.FragmentActivity, android.app.Activity
/* 114 */       protected void onPostResume() {
/* 115 */           super.onPostResume();
/* 116 */           this.mImpl.onPostResume();
                }

                @Override // android.app.Activity, android.view.Window.Callback
/* 120 */       public View onCreatePanelView(int featureId) {
                    return featureId == 0 ? this.mImpl.onCreatePanelView(featureId) : super.onCreatePanelView(featureId);
                }

                @Override // android.support.v4.app.FragmentActivity, android.app.Activity, android.view.Window.Callback
/* 129 */       public final boolean onMenuItemSelected(int featureId, MenuItem item) {
/* 130 */           if (this.mImpl.onMenuItemSelected(featureId, item)) {
/* 131 */               return true;
                    }
/* 134 */           ActionBar ab = getSupportActionBar();
/* 135 */           if (item.getItemId() == 16908332 && ab != null && (ab.getDisplayOptions() & 4) != 0) {
/* 137 */               return onSupportNavigateUp();
                    }
/* 139 */           return false;
                }

                @Override // android.app.Activity
/* 143 */       protected void onTitleChanged(CharSequence title, int color) {
/* 144 */           super.onTitleChanged(title, color);
/* 145 */           this.mImpl.onTitleChanged(title);
                }

/* 162 */       public boolean supportRequestWindowFeature(int featureId) {
/* 163 */           return this.mImpl.supportRequestWindowFeature(featureId);
                }

                @Override // android.support.v4.app.FragmentActivity
/* 168 */       public void supportInvalidateOptionsMenu() {
/* 169 */           if (Build.VERSION.SDK_INT >= 14) {
/* 170 */               super.supportInvalidateOptionsMenu();
                    }
/* 172 */           this.mImpl.supportInvalidateOptionsMenu();
                }

/* 181 */       public void onSupportActionModeStarted(ActionMode mode) {
                }

/* 190 */       public void onSupportActionModeFinished(ActionMode mode) {
                }

/* 193 */       public ActionMode startSupportActionMode(ActionMode.Callback callback) {
/* 194 */           return this.mImpl.startSupportActionMode(callback);
                }

                @Override // android.support.v4.app.FragmentActivity, android.app.Activity, android.view.Window.Callback
/* 198 */       public boolean onCreatePanelMenu(int featureId, Menu menu) {
/* 199 */           return this.mImpl.onCreatePanelMenu(featureId, menu);
                }

                @Override // android.support.v4.app.FragmentActivity, android.app.Activity, android.view.Window.Callback
/* 203 */       public boolean onPreparePanel(int featureId, View view, Menu menu) {
/* 204 */           return this.mImpl.onPreparePanel(featureId, view, menu);
                }

                @Override // android.support.v4.app.FragmentActivity
/* 211 */       protected boolean onPrepareOptionsPanel(View view, Menu menu) {
/* 212 */           return this.mImpl.onPrepareOptionsPanel(view, menu);
                }

/* 215 */       void superSetContentView(int resId) {
/* 216 */           super.setContentView(resId);
                }

/* 219 */       void superSetContentView(View v) {
/* 220 */           super.setContentView(v);
                }

/* 223 */       void superSetContentView(View v, ViewGroup.LayoutParams lp) {
/* 224 */           super.setContentView(v, lp);
                }

/* 227 */       void superAddContentView(View v, ViewGroup.LayoutParams lp) {
/* 228 */           super.addContentView(v, lp);
                }

/* 231 */       boolean superOnCreatePanelMenu(int featureId, Menu frameworkMenu) {
/* 232 */           return super.onCreatePanelMenu(featureId, frameworkMenu);
                }

/* 235 */       boolean superOnPreparePanel(int featureId, View view, Menu menu) {
/* 236 */           return super.onPreparePanel(featureId, view, menu);
                }

/* 239 */       boolean superOnPrepareOptionsPanel(View view, Menu menu) {
/* 240 */           return super.onPrepareOptionsPanel(view, menu);
                }

/* 243 */       boolean superOnMenuItemSelected(int featureId, MenuItem menuItem) {
/* 244 */           return super.onMenuItemSelected(featureId, menuItem);
                }

                @Override // android.support.v4.app.FragmentActivity, android.app.Activity
/* 248 */       public void onBackPressed() {
/* 249 */           if (!this.mImpl.onBackPressed()) {
/* 250 */               super.onBackPressed();
                    }
                }

/* 264 */       public void setSupportProgressBarVisibility(boolean visible) {
/* 265 */           this.mImpl.setSupportProgressBarVisibility(visible);
                }

/* 278 */       public void setSupportProgressBarIndeterminateVisibility(boolean visible) {
/* 279 */           this.mImpl.setSupportProgressBarIndeterminateVisibility(visible);
                }

/* 293 */       public void setSupportProgressBarIndeterminate(boolean indeterminate) {
/* 294 */           this.mImpl.setSupportProgressBarIndeterminate(indeterminate);
                }

/* 309 */       public void setSupportProgress(int progress) {
/* 310 */           this.mImpl.setSupportProgress(progress);
                }

/* 336 */       public void onCreateSupportNavigateUpTaskStack(TaskStackBuilder builder) {
/* 337 */           builder.addParentStack(this);
                }

/* 355 */       public void onPrepareSupportNavigateUpTaskStack(TaskStackBuilder builder) {
                }

/* 381 */       public boolean onSupportNavigateUp() {
/* 382 */           Intent upIntent = getSupportParentActivityIntent();
/* 384 */           if (upIntent != null) {
/* 385 */               if (supportShouldUpRecreateTask(upIntent)) {
/* 386 */                   TaskStackBuilder b = TaskStackBuilder.create(this);
/* 387 */                   onCreateSupportNavigateUpTaskStack(b);
/* 388 */                   onPrepareSupportNavigateUpTaskStack(b);
/* 389 */                   b.startActivities();
                            try {
/* 392 */                       ActivityCompat.finishAffinity(this);
                            } catch (IllegalStateException e) {
/* 396 */                       finish();
                            }
                        } else {
/* 401 */                   supportNavigateUpTo(upIntent);
                        }
/* 403 */               return true;
                    }
/* 405 */           return false;
                }

                @Override // android.support.v4.app.TaskStackBuilder.SupportParentable
/* 417 */       public Intent getSupportParentActivityIntent() {
/* 418 */           return NavUtils.getParentActivityIntent(this);
                }

/* 434 */       public boolean supportShouldUpRecreateTask(Intent targetIntent) {
/* 435 */           return NavUtils.shouldUpRecreateTask(this, targetIntent);
                }

/* 450 */       public void supportNavigateUpTo(Intent upIntent) {
/* 451 */           NavUtils.navigateUpTo(this, upIntent);
                }

                @Override // android.support.v4.app.ActionBarDrawerToggle.DelegateProvider
/* 455 */       public final ActionBarDrawerToggle.Delegate getDrawerToggleDelegate() {
/* 456 */           return this.mImpl.getDrawerToggleDelegate();
                }

                @Override // android.app.Activity, android.view.Window.Callback
/* 462 */       public final void onContentChanged() {
/* 463 */           this.mImpl.onContentChanged();
                }

/* 470 */       public void onSupportContentChanged() {
                }
            }
