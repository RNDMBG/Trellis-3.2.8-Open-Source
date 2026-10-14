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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 42 */    public class SearchActivity extends BaseControllerActionBarActivity implements View.OnClickListener, TextView.OnEditorActionListener, TextWatcher {
                public static final String TAB_TAGS = "tags";
                public static final String TAB_USERS = "users";
                private ImageButton mClear;
                private WeakReference<BaseSearchFragment> mCurrentFragment;
                private EditText mQuery;
                private FragmentTabHost mTabHost;

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 43 */        protected void onCreate(Bundle savedInstanceState) {
/* 44 */            super.onCreate(savedInstanceState, C0257R.layout.search_activity, true);
/* 45 */            // search_activity.xml owns the search-specific toolbar. The
/* 46 */            // platform action bar would otherwise be rendered above it.
/* 47 */            hideOuterSupportActionBar();
/* 46 */            setupActionBar((Boolean) true, (Boolean) true, (String) null, (Boolean) true, (Boolean) true);
/* 48 */            ActionBar ab = getSupportActionBar();
/* 49 */            if (ab != null) {
/* 50 */                ab.setDisplayShowCustomEnabled(true);
/* 51 */                ab.setCustomView(C0257R.layout.search_action_box);
                    }
/* 54 */            EditText query = (EditText) ab.getCustomView().findViewById(C0257R.id.query);
/* 56 */            ViewUtil.reduceTextSizeViaFontScaleIfNeeded(this, 1.1f, 12.0f, query);
/* 58 */            query.setOnEditorActionListener(this);
/* 59 */            query.addTextChangedListener(this);
/* 60 */            query.setInputType(524288);
/* 61 */            query.requestFocus();
/* 62 */            this.mQuery = query;
/* 64 */            ImageButton clear = (ImageButton) findViewById(C0257R.id.clear);
/* 65 */            clear.setOnClickListener(this);
/* 66 */            this.mClear = clear;
/* 68 */            this.mTabHost = (FragmentTabHost) findViewById(C0257R.id.tabhost);
/* 69 */            this.mTabHost.setup(this, getSupportFragmentManager(), C0257R.id.tabFrameLayout);
/* 71 */            LayoutInflater li = LayoutInflater.from(this);
/* 72 */            Bundle usersArgs = new Bundle();
/* 73 */            usersArgs.putInt("empty_desc", C0257R.string.no_people_found);
/* 74 */            usersArgs.putBoolean("refresh", false);
/* 75 */            TabHost.TabSpec usersSpec = this.mTabHost.newTabSpec("users");
/* 76 */            View usersIndicator = li.inflate(C0257R.layout.search_tab, (ViewGroup) this.mTabHost.getTabWidget(), false);
/* 78 */            TextView title = (TextView) usersIndicator.findViewById(C0257R.id.tab_title);
/* 79 */            title.setText(getString(C0257R.string.people).toUpperCase());
/* 80 */            ImageView icon = (ImageView) usersIndicator.findViewById(C0257R.id.tab_icon);
/* 81 */            icon.setImageDrawable(getResources().getDrawable(C0257R.drawable.tab_people));
/* 82 */            usersSpec.setIndicator(usersIndicator);
/* 83 */            this.mTabHost.addTab(usersSpec, UserSearchFragment.class, usersArgs);
/* 85 */            Bundle tagsArgs = new Bundle();
/* 86 */            tagsArgs.putInt("empty_desc", C0257R.string.no_tags_found);
/* 87 */            tagsArgs.putBoolean("refresh", false);
/* 88 */            TabHost.TabSpec tagsSpec = this.mTabHost.newTabSpec("tags");
/* 89 */            View tagsIndicator = li.inflate(C0257R.layout.search_tab, (ViewGroup) this.mTabHost.getTabWidget(), false);
/* 91 */            TextView title2 = (TextView) tagsIndicator.findViewById(C0257R.id.tab_title);
/* 92 */            title2.setText(getString(C0257R.string.tags).toUpperCase());
/* 93 */            ImageView icon2 = (ImageView) tagsIndicator.findViewById(C0257R.id.tab_icon);
/* 94 */            icon2.setImageDrawable(getResources().getDrawable(C0257R.drawable.tab_tags));
/* 95 */            tagsSpec.setIndicator(tagsIndicator);
/* 96 */            this.mTabHost.addTab(tagsSpec, TagSearchFragment.class, tagsArgs);
/* 99 */            modernShowDivider();
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
/* 103 */       protected void modernShowDivider() {
/* 104 */           if (Build.VERSION.SDK_INT >= 11) {
/* 105 */               TabWidget tabWidget = this.mTabHost.getTabWidget();
/* 106 */               tabWidget.setShowDividers(0);
                    }
                }

                @Override // android.text.TextWatcher
/* 111 */       public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
/* 112 */           ImageButton cancel = (ImageButton) findViewById(C0257R.id.clear);
/* 113 */           if (charSequence.length() > 0) {
/* 114 */               cancel.setVisibility(0);
                    } else {
/* 116 */               cancel.setVisibility(8);
                    }
/* 119 */           if (this.mCurrentFragment != null && this.mCurrentFragment.get() != null) {
/* 120 */               if (charSequence.length() >= this.mCurrentFragment.get().getMinimumSearchQueryLength()) {
/* 121 */                   this.mCurrentFragment.get().showSearchHint(getResources(), charSequence.toString());
                        } else {
/* 123 */                   this.mCurrentFragment.get().hideSearchHint();
                        }
                    }
                }

                @Override // android.text.TextWatcher
/* 129 */       public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override // android.text.TextWatcher
/* 133 */       public void afterTextChanged(Editable editable) {
                }

                @Override // android.view.View.OnClickListener
/* 137 */       public void onClick(View v) {
/* 138 */           int id = v.getId();
/* 139 */           if (id == 2131427790) {
/* 140 */               Util.setSoftKeyboardVisibility(this, this.mQuery, true);
/* 151 */               return;
                    }
/* 141 */           if (id == 2131427686) {
/* 142 */               this.mClear.setVisibility(8);
/* 143 */               clearQueryText();
/* 144 */               if (this.mCurrentFragment != null && this.mCurrentFragment.get() != null) {
/* 145 */                   this.mCurrentFragment.get().setQueryText("");
/* 146 */                   this.mCurrentFragment.get().cleanSearchResults();
/* 151 */                   return;
                        }
/* 151 */               return;
                    }
/* 148 */           if (id == 2131427848 || id == 2131427846) {
/* 149 */               performSearch();
                    }
                }

                @Override // android.widget.TextView.OnEditorActionListener
/* 154 */       public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
/* 155 */           if (v.getId() == 2131427790) {
/* 156 */               int minimumQueryLength = 2;
/* 157 */               if (this.mCurrentFragment != null && this.mCurrentFragment.get() != null) {
/* 158 */                   minimumQueryLength = this.mCurrentFragment.get().getMinimumSearchQueryLength();
                        }
/* 160 */               if (actionId == 3 && this.mQuery.getText().length() >= minimumQueryLength) {
/* 162 */                   performSearch();
/* 163 */                   return true;
                        }
                    }
                    return false;
                }

/* 169 */       private void performSearch() {
/* 170 */           this.mQuery.clearFocus();
/* 171 */           Util.setSoftKeyboardVisibility(this, this.mQuery, false);
/* 172 */           if (this.mCurrentFragment != null && this.mCurrentFragment.get() != null) {
/* 173 */               this.mCurrentFragment.get().setQueryText(this.mQuery.getText().toString());
/* 174 */               this.mCurrentFragment.get().performSearch();
                    }
                }

/* 178 */       public void restoreQueryText(String query) {
/* 179 */           this.mQuery.setText(query);
/* 180 */           this.mQuery.clearFocus();
/* 181 */           Util.setSoftKeyboardVisibility(this, this.mQuery, false);
                }

/* 184 */       public String getQueryText() {
/* 185 */           return this.mQuery.getText().toString();
                }

/* 188 */       public void clearQueryText() {
/* 189 */           this.mQuery.setText("");
/* 190 */           this.mQuery.requestFocus();
/* 191 */           Util.setSoftKeyboardVisibility(this, this.mQuery, true);
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 195 */       public void onDestroy() {
/* 196 */           super.onDestroy();
/* 197 */           this.mTabHost = null;
                }

/* 200 */       public void setCurrentTab(BaseSearchFragment fragment) {
/* 201 */           this.mCurrentFragment = new WeakReference<>(fragment);
                }
            }
