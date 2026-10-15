            package co.vine.android;

            import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
            import android.view.View;
            import android.view.ViewGroup;
            
            
    public class SettingsActivity extends BaseControllerActionBarActivity {
                private String SETTINGS_TAG = "settings";
                private SettingsFragment mFrag;

                @Override 
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState, R.layout.fragment_layout, true);
            setupActionBar((Boolean) true, (Boolean) true, R.string.settings, (Boolean) true, (Boolean) true);
            
            
            
            
            if (getSupportActionBar() != null) {
                getSupportActionBar().show();
            }
            hidePlatformActionBar(getWindow().getDecorView());
            if (savedInstanceState == null) {
                SettingsFragment fragment = new SettingsFragment();
                fragment.setArguments(BaseTimelineFragment.prepareArguments(getIntent()));
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment, this.SETTINGS_TAG).commit();
                this.mFrag = fragment;
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

                @Override 
        public boolean onOptionsItemSelected(MenuItem item) {
                    switch (item.getItemId()) {
                        case android.R.id.home:
                    if (this.mFrag == null) {
                        this.mFrag = (SettingsFragment) getSupportFragmentManager().findFragmentByTag(this.SETTINGS_TAG);
                            }
                    if (this.mFrag != null) {
                        this.mFrag.onBackPressed();
                            }
                    return true;
                        default:
                    return super.onOptionsItemSelected(item);
                    }
                }

                @Override 
        public void onBackPressed() {
            if (this.mFrag == null) {
                this.mFrag = (SettingsFragment) getSupportFragmentManager().findFragmentByTag(this.SETTINGS_TAG);
                    }
            if (this.mFrag != null) {
                this.mFrag.onBackPressed();
                    } else {
                super.onBackPressed();
                    }
                }

                @Override 
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (this.mFrag == null) {
                this.mFrag = (SettingsFragment) getSupportFragmentManager().findFragmentByTag(this.SETTINGS_TAG);
                    }
            if (this.mFrag != null) {
                this.mFrag.onActivityResult(requestCode, resultCode, data);
                    } else {
                super.onActivityResult(requestCode, resultCode, data);
                    }
                }
            }
