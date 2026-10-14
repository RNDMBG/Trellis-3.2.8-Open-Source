            package co.vine.android;

            import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
            import android.view.View;
            import android.view.ViewGroup;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 15 */    public class SettingsActivity extends BaseControllerActionBarActivity {
                private String SETTINGS_TAG = "settings";
                private SettingsFragment mFrag;

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 16 */        protected void onCreate(Bundle savedInstanceState) {
/* 17 */            super.onCreate(savedInstanceState, C0257R.layout.fragment_layout, true);
/* 19 */            setupActionBar((Boolean) true, (Boolean) true, C0257R.string.settings, (Boolean) true, (Boolean) true);
            // The decompiled project creates both the platform action bar and
            // Vine's legacy support action bar. Keep the latter because it
            // owns the Settings title and Save action, and remove only the
            // platform container that causes the duplicate toolbar.
            if (getSupportActionBar() != null) {
                getSupportActionBar().show();
            }
            hidePlatformActionBar(getWindow().getDecorView());
/* 21 */            if (savedInstanceState == null) {
/* 22 */                SettingsFragment fragment = new SettingsFragment();
/* 23 */                fragment.setArguments(BaseTimelineFragment.prepareArguments(getIntent()));
/* 24 */                getSupportFragmentManager().beginTransaction().add(C0257R.id.fragment_container, fragment, this.SETTINGS_TAG).commit();
/* 28 */                this.mFrag = fragment;
        }
        }

        private boolean hidePlatformActionBar(View view) {
            if (view == null) {
                return false;
            }
            int id = getResources().getIdentifier("action_bar_container", "id", "android");
            if (id != 0 && view.getId() == id) {
                view.setVisibility(View.GONE);
                return true;
            }
            if (view instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) view;
                for (int i = 0; i < group.getChildCount(); i++) {
                    if (hidePlatformActionBar(group.getChildAt(i))) {
                        return true;
                    }
                }
            }
            return false;
        }

                @Override // co.vine.android.BaseActionBarActivity, android.app.Activity
/* 33 */        public boolean onOptionsItemSelected(MenuItem item) {
                    switch (item.getItemId()) {
                        case android.R.id.home:
/* 36 */                    if (this.mFrag == null) {
/* 37 */                        this.mFrag = (SettingsFragment) getSupportFragmentManager().findFragmentByTag(this.SETTINGS_TAG);
                            }
/* 40 */                    if (this.mFrag != null) {
/* 41 */                        this.mFrag.onBackPressed();
                            }
/* 43 */                    return true;
                        default:
/* 46 */                    return super.onOptionsItemSelected(item);
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 52 */        public void onBackPressed() {
/* 53 */            if (this.mFrag == null) {
/* 54 */                this.mFrag = (SettingsFragment) getSupportFragmentManager().findFragmentByTag(this.SETTINGS_TAG);
                    }
/* 57 */            if (this.mFrag != null) {
/* 58 */                this.mFrag.onBackPressed();
                    } else {
/* 60 */                super.onBackPressed();
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 65 */        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
/* 66 */            super.onActivityResult(requestCode, resultCode, data);
/* 67 */            if (this.mFrag == null) {
/* 68 */                this.mFrag = (SettingsFragment) getSupportFragmentManager().findFragmentByTag(this.SETTINGS_TAG);
                    }
/* 70 */            if (this.mFrag != null) {
/* 71 */                this.mFrag.onActivityResult(requestCode, resultCode, data);
                    } else {
/* 73 */                super.onActivityResult(requestCode, resultCode, data);
                    }
                }
            }
