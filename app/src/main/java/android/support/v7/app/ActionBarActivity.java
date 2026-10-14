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
            
            
    public class ActionBarActivity extends FragmentActivity implements ActionBar.Callback, TaskStackBuilder.SupportParentable, ActionBarDrawerToggle.DelegateProvider {
                ActionBarActivityDelegate mImpl;

        public ActionBar getSupportActionBar() {
            return this.mImpl.getSupportActionBar();
                }

                @Override 
        public MenuInflater getMenuInflater() {
            return this.mImpl.getMenuInflater();
                }

                @Override 
        public void setContentView(int layoutResID) {
            this.mImpl.setContentView(layoutResID);
                }

                @Override 
        public void setContentView(View view) {
            this.mImpl.setContentView(view);
                }

                @Override 
        public void setContentView(View view, ViewGroup.LayoutParams params) {
            this.mImpl.setContentView(view, params);
                }

                @Override 
        public void addContentView(View view, ViewGroup.LayoutParams params) {
            this.mImpl.addContentView(view, params);
                }

                @Override 
        protected void onCreate(Bundle savedInstanceState) {
            this.mImpl = ActionBarActivityDelegate.createDelegate(this);
            super.onCreate(savedInstanceState);
            this.mImpl.onCreate(savedInstanceState);
                }

                @Override 
       public void onConfigurationChanged(Configuration newConfig) {
           super.onConfigurationChanged(newConfig);
           this.mImpl.onConfigurationChanged(newConfig);
                }

                @Override 
       protected void onStop() {
           super.onStop();
           this.mImpl.onStop();
                }

                @Override 
       protected void onPostResume() {
           super.onPostResume();
           this.mImpl.onPostResume();
                }

                @Override 
       public View onCreatePanelView(int featureId) {
                    return featureId == 0 ? this.mImpl.onCreatePanelView(featureId) : super.onCreatePanelView(featureId);
                }

                @Override 
       public final boolean onMenuItemSelected(int featureId, MenuItem item) {
           if (this.mImpl.onMenuItemSelected(featureId, item)) {
               return true;
                    }
           ActionBar ab = getSupportActionBar();
           if (item.getItemId() == 16908332 && ab != null && (ab.getDisplayOptions() & 4) != 0) {
               return onSupportNavigateUp();
                    }
           return false;
                }

                @Override 
       protected void onTitleChanged(CharSequence title, int color) {
           super.onTitleChanged(title, color);
           this.mImpl.onTitleChanged(title);
                }

       public boolean supportRequestWindowFeature(int featureId) {
           return this.mImpl.supportRequestWindowFeature(featureId);
                }

                @Override 
       public void supportInvalidateOptionsMenu() {
           if (Build.VERSION.SDK_INT >= 14) {
               super.supportInvalidateOptionsMenu();
                    }
           this.mImpl.supportInvalidateOptionsMenu();
                }

       public void onSupportActionModeStarted(ActionMode mode) {
                }

       public void onSupportActionModeFinished(ActionMode mode) {
                }

       public ActionMode startSupportActionMode(ActionMode.Callback callback) {
           return this.mImpl.startSupportActionMode(callback);
                }

                @Override 
       public boolean onCreatePanelMenu(int featureId, Menu menu) {
           return this.mImpl.onCreatePanelMenu(featureId, menu);
                }

                @Override 
       public boolean onPreparePanel(int featureId, View view, Menu menu) {
           return this.mImpl.onPreparePanel(featureId, view, menu);
                }

                @Override 
       protected boolean onPrepareOptionsPanel(View view, Menu menu) {
           return this.mImpl.onPrepareOptionsPanel(view, menu);
                }

       void superSetContentView(int resId) {
           super.setContentView(resId);
                }

       void superSetContentView(View v) {
           super.setContentView(v);
                }

       void superSetContentView(View v, ViewGroup.LayoutParams lp) {
           super.setContentView(v, lp);
                }

       void superAddContentView(View v, ViewGroup.LayoutParams lp) {
           super.addContentView(v, lp);
                }

       boolean superOnCreatePanelMenu(int featureId, Menu frameworkMenu) {
           return super.onCreatePanelMenu(featureId, frameworkMenu);
                }

       boolean superOnPreparePanel(int featureId, View view, Menu menu) {
           return super.onPreparePanel(featureId, view, menu);
                }

       boolean superOnPrepareOptionsPanel(View view, Menu menu) {
           return super.onPrepareOptionsPanel(view, menu);
                }

       boolean superOnMenuItemSelected(int featureId, MenuItem menuItem) {
           return super.onMenuItemSelected(featureId, menuItem);
                }

                @Override 
       public void onBackPressed() {
           if (!this.mImpl.onBackPressed()) {
               super.onBackPressed();
                    }
                }

       public void setSupportProgressBarVisibility(boolean visible) {
           this.mImpl.setSupportProgressBarVisibility(visible);
                }

       public void setSupportProgressBarIndeterminateVisibility(boolean visible) {
           this.mImpl.setSupportProgressBarIndeterminateVisibility(visible);
                }

       public void setSupportProgressBarIndeterminate(boolean indeterminate) {
           this.mImpl.setSupportProgressBarIndeterminate(indeterminate);
                }

       public void setSupportProgress(int progress) {
           this.mImpl.setSupportProgress(progress);
                }

       public void onCreateSupportNavigateUpTaskStack(TaskStackBuilder builder) {
           builder.addParentStack(this);
                }

       public void onPrepareSupportNavigateUpTaskStack(TaskStackBuilder builder) {
                }

       public boolean onSupportNavigateUp() {
           Intent upIntent = getSupportParentActivityIntent();
           if (upIntent != null) {
               if (supportShouldUpRecreateTask(upIntent)) {
                   TaskStackBuilder b = TaskStackBuilder.create(this);
                   onCreateSupportNavigateUpTaskStack(b);
                   onPrepareSupportNavigateUpTaskStack(b);
                   b.startActivities();
                            try {
                       ActivityCompat.finishAffinity(this);
                            } catch (IllegalStateException e) {
                       finish();
                            }
                        } else {
                   supportNavigateUpTo(upIntent);
                        }
               return true;
                    }
           return false;
                }

                @Override 
       public Intent getSupportParentActivityIntent() {
           return NavUtils.getParentActivityIntent(this);
                }

       public boolean supportShouldUpRecreateTask(Intent targetIntent) {
           return NavUtils.shouldUpRecreateTask(this, targetIntent);
                }

       public void supportNavigateUpTo(Intent upIntent) {
           NavUtils.navigateUpTo(this, upIntent);
                }

                @Override 
       public final ActionBarDrawerToggle.Delegate getDrawerToggleDelegate() {
           return this.mImpl.getDrawerToggleDelegate();
                }

                @Override 
       public final void onContentChanged() {
           this.mImpl.onContentChanged();
                }

       public void onSupportContentChanged() {
                }
            }
