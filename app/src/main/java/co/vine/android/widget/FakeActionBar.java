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
            
            
    public class FakeActionBar implements View.OnClickListener {
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

        static  ActionBarActivity access$000(FakeActionBar x0) {
            return x0.mActivity;
                }

        public MenuItemPack getMenuItemPack(int id) {
                    for (MenuItemPack pack : this.mMenuItemPacks) {
                if (pack.item.getItemId() == id) {
                    return pack;
                        }
                    }
                    return null;
                }

        public void setActionBarColor(int color) {
            this.mActionBar.setBackgroundColor(color);
                }

        public void setActionBarTransition(TransitionDrawable td) {
            this.mActionBar.setBackgroundDrawable(td);
                }

                @Override 
        public void onClick(View v) {
            if (v == this.mBackIcon || v == this.mHomeIcon) {
                this.mActivity.finish();
                    }
                }

        public RelativeLayout getActionBarLeft() {
            return this.mActionBarLeft;
                }

        public LinearLayout getActionBarRight() {
            return this.mActionBarRight;
                }

        public FakeActionBar(ActionBarActivity activity) {
            this.mActivity = activity;
                }

       public void setTheme(Theme theme) {
           this.mTheme = theme;
                }

       public void onCreate() {
           this.mActionBarContainer = (RelativeLayout) LayoutInflater.from(this.mActivity).inflate(C0257R.layout.fake_action_bar, (ViewGroup) null);
           this.mActionBar = (RelativeLayout) this.mActionBarContainer.findViewById(C0257R.id.fake_action_bar);
           ViewUtil.setActionBarHeight(this.mActivity, this.mActionBar);
           this.mDistanceMarker = this.mActionBarContainer.findViewById(C0257R.id.distance_marker);
           ViewUtil.setActionBarHeight(this.mActivity, this.mDistanceMarker);
           this.mActionBarLeft = (RelativeLayout) this.mActionBarContainer.findViewById(C0257R.id.fake_action_bar_left);
           this.mActionBarRight = (LinearLayout) this.mActionBarContainer.findViewById(C0257R.id.fake_action_bar_right);
           this.mBackIcon = (ImageView) this.mActionBar.findViewById(C0257R.id.ab_back_button);
           this.mHomeIcon = (ImageView) this.mActionBar.findViewById(C0257R.id.ab_home_button);
           this.mTitleView = (TextView) this.mActionBar.findViewById(C0257R.id.ab_title_view);
                }

       public ImageView getBackIcon() {
           return this.mBackIcon;
                }

       public ImageView getHomeIcon() {
           return this.mHomeIcon;
                }

       public void onPostCreate(Bundle savedInstanceState) {
           if (this.mContentView == null || this.mActionBarContainer == null) {
               throw new IllegalStateException("custom setContentView is not called.");
                    }
           if (this.mDisplayHomeAsUp || this.mHomeButtonEnabled) {
               if (this.mDisplayHomeAsUp) {
                   this.mBackIcon.setImageResource(AppCompactProxy.getBackIcon("co.vine.android", this.mTheme == Theme.DARK));
                   this.mBackIcon.setClickable(true);
                   this.mBackIcon.setVisibility(0);
                   this.mBackIcon.setOnClickListener(this);
                        } else {
                   this.mBackIcon.setVisibility(8);
                        }
               Drawable icon = null;
               Resources.Theme theme = this.mActivity.getTheme();
               if (theme != null) {
                   TypedArray att = theme.obtainStyledAttributes(new int[]{R.attr.actionBarStyle});
                   TypedValue typedValue = new TypedValue();
                   if (att.getValue(0, typedValue)) {
                       TypedArray att2 = theme.obtainStyledAttributes(typedValue.resourceId, new int[]{R.attr.icon});
                       int iconRes = att2.getResourceId(0, -1);
                       att.recycle();
                       att2.recycle();
                       if (iconRes > 0) {
                           icon = this.mActivity.getResources().getDrawable(iconRes);
                                }
                            }
                        }
               if (icon == null) {
                   icon = this.mActivity.getApplicationInfo().loadLogo(this.mActivity.getPackageManager());
                        }
               if (this.mHomeButtonEnabled && this.mDisplayLogo) {
                   this.mHomeIcon.setClickable(true);
                   this.mHomeIcon.setOnClickListener(this);
                   this.mHomeIcon.setImageDrawable(icon);
                   this.mHomeIcon.setVisibility(0);
                        } else {
                   this.mHomeIcon.setVisibility(8);
                        }
                    } else {
               this.mHomeIcon.setVisibility(8);
                    }
           if (this.mDisplayShowTitle.booleanValue() && this.mTitle != null) {
               this.mTitleView.setVisibility(0);
               this.mTitleView.setText(this.mTitle);
                    } else {
               this.mTitleView.setVisibility(8);
                    }
                }

       public void setContentView(View v, ViewGroup.LayoutParams params) {
           this.mContentView = v;
           RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(params);
           v.setLayoutParams(p);
           p.addRule(3, this.mDistanceMarker.getId());
           this.mActionBarContainer.addView(this.mContentView);
                }

       public View getDistanceMaker() {
           return this.mDistanceMarker;
                }

       public RelativeLayout getActionBar() {
           return this.mActionBar;
                }

       public TextView getTitleView() {
           return this.mTitleView;
                }

       public RelativeLayout getRoot() {
           return this.mActionBarContainer;
                }

       public void setHomeButtonEnabled(Boolean setHomeButtonEnabled) {
           this.mHomeButtonEnabled = setHomeButtonEnabled.booleanValue();
                }

       public void setDisplayShowTitleEnabled(Boolean setDisplayShowTitleEnabled) {
           this.mDisplayShowTitle = setDisplayShowTitleEnabled;
                }

       public void setDisplayHomeAsUpEnabled(Boolean setDisplayHomeAsUpEnabled) {
           this.mDisplayHomeAsUp = setDisplayHomeAsUpEnabled.booleanValue();
                }

       public void setTitle(String title) {
           this.mTitle = title;
                }

       public void setTitle(Integer titleRes) {
           this.mTitle = this.mActivity.getString(titleRes.intValue());
                }

       public void setDisplayLogoEnabled(Boolean setDisplayLogoEnabled) {
           this.mDisplayLogo = setDisplayLogoEnabled.booleanValue();
                }

       public void onCreateOptionsMenu(Menu menu, boolean show) {
           this.mMenu = menu;
                    for (MenuItemPack pack : this.mMenuItemPacks) {
               if (pack.view != null) {
                   this.mActionBarRight.removeView(pack.view);
                        }
                    }
           if (show) {
               int size = this.mMenu.size();
               ArrayList<MenuItemPack> newMenuItemIndexes = new ArrayList<>();
                        for (int i = 0; i < size; i++) {
                   MenuItem item = this.mMenu.getItem(i);
                   if (item.isVisible()) {
                       View v = null;
                       Drawable icon = item.getIcon();
                       CharSequence title = item.getTitle();
                       if (icon != null) {
                           v = new ImageView(this.mActivity);
                           ((ImageView) v).setImageDrawable(icon);
                                }
                       if (title != null) {
                           v = new TextView(this.mActivity);
                           ((TextView) v).setText(title);
                                }
                       if (v != null) {
                           v.setOnClickListener(new ViewOnClickListenerC05491(item));
                           this.mActionBarRight.addView(v);
                                }
                            }
                        }
               this.mMenuItemPacks.clear();
               this.mMenuItemPacks.addAll(newMenuItemIndexes);
                    }
                }

                
       class ViewOnClickListenerC05491 implements View.OnClickListener {
                    final  MenuItem val$item;

           ViewOnClickListenerC05491(MenuItem menuItem) {
                        this.val$item = menuItem;
                    }

                    @Override 
           public void onClick(View v) {
               FakeActionBar.access$000(FakeActionBar.this).onOptionsItemSelected(this.val$item);
                    }
                }

       public void onPrepareOptionsMenu(Menu menu, boolean show) {
                }
            }
