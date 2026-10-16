            package co.vine.android;

            import android.annotation.TargetApi;
            import android.os.Build;
            import android.os.Bundle;
            import android.support.v7.app.ActionBar;
            import android.text.Editable;
            import android.text.TextWatcher;
            import android.view.KeyEvent;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.EditText;
            import android.widget.ImageButton;
            import android.widget.ImageView;
            import android.widget.TabHost;
            import android.widget.TabWidget;
            import android.widget.TextView;
            import co.vine.android.util.Util;
            import co.vine.android.util.ViewUtil;
            import co.vine.android.widget.tabs.FragmentTabHost;
            import java.lang.ref.WeakReference;
            
            
    public class SearchActivity extends BaseControllerActionBarActivity implements View.OnClickListener, TextView.OnEditorActionListener, TextWatcher {
                public static final String TAB_TAGS = "tags";
                public static final String TAB_USERS = "users";
                private ImageButton mClear;
                private WeakReference<BaseSearchFragment> mCurrentFragment;
                private EditText mQuery;
                private FragmentTabHost mTabHost;

                @Override 
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState, R.layout.search_activity, true);
            
            
            hideOuterSupportActionBar();
            setupActionBar((Boolean) true, (Boolean) true, (String) null, (Boolean) true, (Boolean) true);
            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setDisplayShowCustomEnabled(true);
                ab.setCustomView(R.layout.search_action_box);
                    }
            EditText query = (EditText) ab.getCustomView().findViewById(R.id.query);
            ViewUtil.reduceTextSizeViaFontScaleIfNeeded(this, 1.1f, 12.0f, query);
            query.setOnEditorActionListener(this);
            query.addTextChangedListener(this);
            query.setInputType(524288);
            query.requestFocus();
            this.mQuery = query;
            ImageButton clear = (ImageButton) findViewById(R.id.clear);
            clear.setOnClickListener(this);
            this.mClear = clear;
            this.mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
            this.mTabHost.setup(this, getSupportFragmentManager(), R.id.tabFrameLayout);
            LayoutInflater li = LayoutInflater.from(this);
            Bundle usersArgs = new Bundle();
            usersArgs.putInt("empty_desc", R.string.no_people_found);
            usersArgs.putBoolean("refresh", false);
            TabHost.TabSpec usersSpec = this.mTabHost.newTabSpec("users");
            View usersIndicator = li.inflate(R.layout.search_tab, (ViewGroup) this.mTabHost.getTabWidget(), false);
            TextView title = (TextView) usersIndicator.findViewById(R.id.tab_title);
            title.setText(getString(R.string.people).toUpperCase());
            ImageView icon = (ImageView) usersIndicator.findViewById(R.id.tab_icon);
            icon.setImageDrawable(getResources().getDrawable(R.drawable.tab_people));
            usersSpec.setIndicator(usersIndicator);
            this.mTabHost.addTab(usersSpec, UserSearchFragment.class, usersArgs);
            Bundle tagsArgs = new Bundle();
            tagsArgs.putInt("empty_desc", R.string.no_tags_found);
            tagsArgs.putBoolean("refresh", false);
            TabHost.TabSpec tagsSpec = this.mTabHost.newTabSpec("tags");
            View tagsIndicator = li.inflate(R.layout.search_tab, (ViewGroup) this.mTabHost.getTabWidget(), false);
            TextView title2 = (TextView) tagsIndicator.findViewById(R.id.tab_title);
            title2.setText(getString(R.string.tags).toUpperCase());
            ImageView icon2 = (ImageView) tagsIndicator.findViewById(R.id.tab_icon);
            icon2.setImageDrawable(getResources().getDrawable(R.drawable.tab_tags));
            tagsSpec.setIndicator(tagsIndicator);
            this.mTabHost.addTab(tagsSpec, TagSearchFragment.class, tagsArgs);
            modernShowDivider();
                }

                private void hideOuterSupportActionBar() {
                    int id = getResources().getIdentifier("action_bar_container", "id", "android");
                    if (id != 0) {
                        View outer = super.findViewById(id);
                        if (outer != null) {
                            outer.setVisibility(View.GONE);
                        }
                    }
                }

                @TargetApi(11)
       protected void modernShowDivider() {
           if (Build.VERSION.SDK_INT >= 11) {
               TabWidget tabWidget = this.mTabHost.getTabWidget();
               tabWidget.setShowDividers(0);
                    }
                }

                @Override 
       public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
           ImageButton cancel = (ImageButton) findViewById(R.id.clear);
           if (charSequence.length() > 0) {
               cancel.setVisibility(0);
                    } else {
               cancel.setVisibility(8);
                    }
           if (this.mCurrentFragment != null && this.mCurrentFragment.get() != null) {
               if (charSequence.length() >= this.mCurrentFragment.get().getMinimumSearchQueryLength()) {
                   this.mCurrentFragment.get().showSearchHint(getResources(), charSequence.toString());
                        } else {
                   this.mCurrentFragment.get().hideSearchHint();
                        }
                    }
                }

                @Override 
       public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override 
       public void afterTextChanged(Editable editable) {
                }

                @Override 
       public void onClick(View v) {
           int id = v.getId();
           if (id == R.id.query) {
               Util.setSoftKeyboardVisibility(this, this.mQuery, true);
               return;
                    }
           if (id == R.id.clear) {
               this.mClear.setVisibility(8);
               clearQueryText();
               if (this.mCurrentFragment != null && this.mCurrentFragment.get() != null) {
                   this.mCurrentFragment.get().setQueryText("");
                   this.mCurrentFragment.get().cleanSearchResults();
                   return;
                        }
               return;
                    }
           if (id == R.id.search_go_btn || id == R.id.search_close_btn) {
               performSearch();
                    }
                }

                @Override 
       public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
           if (v.getId() == R.id.query) {
               int minimumQueryLength = 2;
               if (this.mCurrentFragment != null && this.mCurrentFragment.get() != null) {
                   minimumQueryLength = this.mCurrentFragment.get().getMinimumSearchQueryLength();
                        }
               if (actionId == 3 && this.mQuery.getText().length() >= minimumQueryLength) {
                   performSearch();
                   return true;
                        }
                    }
                    return false;
                }

       private void performSearch() {
           this.mQuery.clearFocus();
           Util.setSoftKeyboardVisibility(this, this.mQuery, false);
           if (this.mCurrentFragment != null && this.mCurrentFragment.get() != null) {
               this.mCurrentFragment.get().setQueryText(this.mQuery.getText().toString());
               this.mCurrentFragment.get().performSearch();
                    }
                }

       public void restoreQueryText(String query) {
           this.mQuery.setText(query);
           this.mQuery.clearFocus();
           Util.setSoftKeyboardVisibility(this, this.mQuery, false);
                }

       public String getQueryText() {
           return this.mQuery.getText().toString();
                }

       public void clearQueryText() {
           this.mQuery.setText("");
           this.mQuery.requestFocus();
           Util.setSoftKeyboardVisibility(this, this.mQuery, true);
                }

                @Override 
       public void onDestroy() {
           super.onDestroy();
           this.mTabHost = null;
                }

       public void setCurrentTab(BaseSearchFragment fragment) {
           this.mCurrentFragment = new WeakReference<>(fragment);
                }
            }
