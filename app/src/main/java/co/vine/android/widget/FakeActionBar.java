            package co.vine.android.widget;

            import android.R;
            import android.content.res.Resources;
            import android.content.res.TypedArray;
            import android.graphics.drawable.Drawable;
            import android.graphics.drawable.TransitionDrawable;
            import android.os.Bundle;
            import android.support.v7.app.ActionBarActivity;
            import android.util.TypedValue;
            import android.view.LayoutInflater;
            import android.view.Menu;
            import android.view.MenuItem;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.ImageView;
            import android.widget.LinearLayout;
            import android.widget.RelativeLayout;
            import android.widget.TextView;
            import co.vine.android.C0257R;
            import co.vine.android.util.AppCompactProxy;
            import co.vine.android.util.ViewUtil;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 28 */    public class FakeActionBar implements View.OnClickListener {
                private RelativeLayout mActionBar;
                private RelativeLayout mActionBarContainer;
                private RelativeLayout mActionBarLeft;
                private LinearLayout mActionBarRight;
                private final ActionBarActivity mActivity;
                private ImageView mBackIcon;
                private View mContentView;
                private boolean mDisplayHomeAsUp;
                private boolean mDisplayLogo;
                private Boolean mDisplayShowTitle;
                private View mDistanceMarker;
                private boolean mHomeButtonEnabled;
                private ImageView mHomeIcon;
                private Menu mMenu;
                private final ArrayList<MenuItemPack> mMenuItemPacks = new ArrayList<>();
                public Theme mTheme = Theme.LIGHT;
                private String mTitle;
                private TextView mTitleView;

                public static class MenuItemPack {
                    MenuItem item;
                    public View view;
                }

                public enum Theme {
                    DARK,
                    LIGHT
                }

/* 29 */        static /* synthetic */ ActionBarActivity access$000(FakeActionBar x0) {
/* 30 */            return x0.mActivity;
                }

/* 53 */        public MenuItemPack getMenuItemPack(int id) {
                    for (MenuItemPack pack : this.mMenuItemPacks) {
/* 55 */                if (pack.item.getItemId() == id) {
/* 59 */                    return pack;
                        }
                    }
                    return null;
                }

/* 67 */        public void setActionBarColor(int color) {
/* 68 */            this.mActionBar.setBackgroundColor(color);
                }

/* 71 */        public void setActionBarTransition(TransitionDrawable td) {
/* 72 */            this.mActionBar.setBackgroundDrawable(td);
                }

                @Override // android.view.View.OnClickListener
/* 76 */        public void onClick(View v) {
/* 77 */            if (v == this.mBackIcon || v == this.mHomeIcon) {
/* 78 */                this.mActivity.finish();
                    }
                }

/* 82 */        public RelativeLayout getActionBarLeft() {
/* 83 */            return this.mActionBarLeft;
                }

/* 86 */        public LinearLayout getActionBarRight() {
/* 87 */            return this.mActionBarRight;
                }

/* 94 */        public FakeActionBar(ActionBarActivity activity) {
/* 96 */            this.mActivity = activity;
                }

/* 100 */       public void setTheme(Theme theme) {
/* 101 */           this.mTheme = theme;
                }

/* 104 */       public void onCreate() {
/* 105 */           this.mActionBarContainer = (RelativeLayout) LayoutInflater.from(this.mActivity).inflate(C0257R.layout.fake_action_bar, (ViewGroup) null);
/* 106 */           this.mActionBar = (RelativeLayout) this.mActionBarContainer.findViewById(C0257R.id.fake_action_bar);
/* 107 */           ViewUtil.setActionBarHeight(this.mActivity, this.mActionBar);
/* 108 */           this.mDistanceMarker = this.mActionBarContainer.findViewById(C0257R.id.distance_marker);
/* 109 */           ViewUtil.setActionBarHeight(this.mActivity, this.mDistanceMarker);
/* 110 */           this.mActionBarLeft = (RelativeLayout) this.mActionBarContainer.findViewById(C0257R.id.fake_action_bar_left);
/* 111 */           this.mActionBarRight = (LinearLayout) this.mActionBarContainer.findViewById(C0257R.id.fake_action_bar_right);
/* 112 */           this.mBackIcon = (ImageView) this.mActionBar.findViewById(C0257R.id.ab_back_button);
/* 113 */           this.mHomeIcon = (ImageView) this.mActionBar.findViewById(C0257R.id.ab_home_button);
/* 114 */           this.mTitleView = (TextView) this.mActionBar.findViewById(C0257R.id.ab_title_view);
                }

/* 117 */       public ImageView getBackIcon() {
/* 118 */           return this.mBackIcon;
                }

/* 121 */       public ImageView getHomeIcon() {
/* 122 */           return this.mHomeIcon;
                }

/* 125 */       public void onPostCreate(Bundle savedInstanceState) {
/* 126 */           if (this.mContentView == null || this.mActionBarContainer == null) {
/* 127 */               throw new IllegalStateException("custom setContentView is not called.");
                    }
/* 129 */           if (this.mDisplayHomeAsUp || this.mHomeButtonEnabled) {
/* 130 */               if (this.mDisplayHomeAsUp) {
/* 131 */                   this.mBackIcon.setImageResource(AppCompactProxy.getBackIcon("co.vine.android", this.mTheme == Theme.DARK));
/* 132 */                   this.mBackIcon.setClickable(true);
/* 133 */                   this.mBackIcon.setVisibility(0);
/* 134 */                   this.mBackIcon.setOnClickListener(this);
                        } else {
/* 136 */                   this.mBackIcon.setVisibility(8);
                        }
/* 138 */               Drawable icon = null;
/* 139 */               Resources.Theme theme = this.mActivity.getTheme();
/* 140 */               if (theme != null) {
/* 141 */                   TypedArray att = theme.obtainStyledAttributes(new int[]{R.attr.actionBarStyle});
/* 142 */                   TypedValue typedValue = new TypedValue();
/* 143 */                   if (att.getValue(0, typedValue)) {
/* 144 */                       TypedArray att2 = theme.obtainStyledAttributes(typedValue.resourceId, new int[]{R.attr.icon});
/* 145 */                       int iconRes = att2.getResourceId(0, -1);
/* 146 */                       att.recycle();
/* 147 */                       att2.recycle();
/* 148 */                       if (iconRes > 0) {
/* 149 */                           icon = this.mActivity.getResources().getDrawable(iconRes);
                                }
                            }
                        }
/* 153 */               if (icon == null) {
/* 154 */                   icon = this.mActivity.getApplicationInfo().loadLogo(this.mActivity.getPackageManager());
                        }
/* 156 */               if (this.mHomeButtonEnabled && this.mDisplayLogo) {
/* 157 */                   this.mHomeIcon.setClickable(true);
/* 158 */                   this.mHomeIcon.setOnClickListener(this);
/* 159 */                   this.mHomeIcon.setImageDrawable(icon);
/* 160 */                   this.mHomeIcon.setVisibility(0);
                        } else {
/* 162 */                   this.mHomeIcon.setVisibility(8);
                        }
                    } else {
/* 165 */               this.mHomeIcon.setVisibility(8);
                    }
/* 167 */           if (this.mDisplayShowTitle.booleanValue() && this.mTitle != null) {
/* 168 */               this.mTitleView.setVisibility(0);
/* 169 */               this.mTitleView.setText(this.mTitle);
                    } else {
/* 171 */               this.mTitleView.setVisibility(8);
                    }
                }

/* 175 */       public void setContentView(View v, ViewGroup.LayoutParams params) {
/* 176 */           this.mContentView = v;
/* 177 */           RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(params);
/* 178 */           v.setLayoutParams(p);
/* 179 */           p.addRule(3, this.mDistanceMarker.getId());
/* 180 */           this.mActionBarContainer.addView(this.mContentView);
                }

/* 183 */       public View getDistanceMaker() {
/* 184 */           return this.mDistanceMarker;
                }

/* 187 */       public RelativeLayout getActionBar() {
/* 188 */           return this.mActionBar;
                }

/* 191 */       public TextView getTitleView() {
/* 192 */           return this.mTitleView;
                }

/* 195 */       public RelativeLayout getRoot() {
/* 196 */           return this.mActionBarContainer;
                }

/* 199 */       public void setHomeButtonEnabled(Boolean setHomeButtonEnabled) {
/* 200 */           this.mHomeButtonEnabled = setHomeButtonEnabled.booleanValue();
                }

/* 203 */       public void setDisplayShowTitleEnabled(Boolean setDisplayShowTitleEnabled) {
/* 204 */           this.mDisplayShowTitle = setDisplayShowTitleEnabled;
                }

/* 207 */       public void setDisplayHomeAsUpEnabled(Boolean setDisplayHomeAsUpEnabled) {
/* 208 */           this.mDisplayHomeAsUp = setDisplayHomeAsUpEnabled.booleanValue();
                }

/* 211 */       public void setTitle(String title) {
/* 212 */           this.mTitle = title;
                }

/* 215 */       public void setTitle(Integer titleRes) {
/* 216 */           this.mTitle = this.mActivity.getString(titleRes.intValue());
                }

/* 219 */       public void setDisplayLogoEnabled(Boolean setDisplayLogoEnabled) {
/* 220 */           this.mDisplayLogo = setDisplayLogoEnabled.booleanValue();
                }

/* 223 */       public void onCreateOptionsMenu(Menu menu, boolean show) {
/* 224 */           this.mMenu = menu;
                    for (MenuItemPack pack : this.mMenuItemPacks) {
/* 226 */               if (pack.view != null) {
/* 227 */                   this.mActionBarRight.removeView(pack.view);
                        }
                    }
/* 230 */           if (show) {
/* 233 */               int size = this.mMenu.size();
/* 234 */               ArrayList<MenuItemPack> newMenuItemIndexes = new ArrayList<>();
                        for (int i = 0; i < size; i++) {
/* 236 */                   MenuItem item = this.mMenu.getItem(i);
/* 237 */                   if (item.isVisible()) {
/* 238 */                       View v = null;
/* 239 */                       Drawable icon = item.getIcon();
/* 240 */                       CharSequence title = item.getTitle();
/* 241 */                       if (icon != null) {
/* 242 */                           v = new ImageView(this.mActivity);
/* 243 */                           ((ImageView) v).setImageDrawable(icon);
                                }
/* 245 */                       if (title != null) {
/* 246 */                           v = new TextView(this.mActivity);
/* 247 */                           ((TextView) v).setText(title);
                                }
/* 249 */                       if (v != null) {
/* 250 */                           v.setOnClickListener(new ViewOnClickListenerC05491(item));
/* 256 */                           this.mActionBarRight.addView(v);
                                }
                            }
                        }
/* 260 */               this.mMenuItemPacks.clear();
/* 261 */               this.mMenuItemPacks.addAll(newMenuItemIndexes);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.widget.FakeActionBar$1 */
/* 248 */       class ViewOnClickListenerC05491 implements View.OnClickListener {
                    final /* synthetic */ MenuItem val$item;

/* 249 */           ViewOnClickListenerC05491(MenuItem menuItem) {
                        this.val$item = menuItem;
                    }

                    @Override // android.view.View.OnClickListener
/* 252 */           public void onClick(View v) {
/* 253 */               FakeActionBar.access$000(FakeActionBar.this).onOptionsItemSelected(this.val$item);
                    }
                }

/* 264 */       public void onPrepareOptionsMenu(Menu menu, boolean show) {
                }
            }
