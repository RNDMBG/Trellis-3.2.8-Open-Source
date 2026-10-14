            package co.vine.android;

            import android.annotation.TargetApi;
            import android.app.ActionBar;
            import android.app.Activity;
            import android.app.ProgressDialog;
            import android.content.BroadcastReceiver;
            import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.TouchDelegate;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
            import android.os.Build;
            import android.os.Bundle;
            import android.support.annotation.NonNull;
            import android.support.annotation.Nullable;
            import android.support.v7.app.ActionBarActivity;
            import android.text.SpannableString;
            import android.text.TextUtils;
            import android.util.AndroidRuntimeException;
            import android.util.Log;
import android.view.DragEvent;
import android.view.KeyEvent;
            import android.view.Menu;
            import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageButton;
import android.widget.ImageView;
            import co.vine.android.client.VineAccountHelper;
            import co.vine.android.plugin.BasePluginManager;
            import co.vine.android.plugin.DebugReceiverPlugin;
            import co.vine.android.plugin.DebugSubPluginManager;
            import co.vine.android.plugin.DebugTasksPlugin;
            import co.vine.android.plugin.Plugin;
            import co.vine.android.util.BuildUtil;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.CrossConstants;
            import co.vine.android.util.MuteUtil;
            import co.vine.android.util.SystemUtil;
            import co.vine.android.util.analytics.AnalyticsPlugin;
            import co.vine.android.util.analytics.FlurryUtils;
            import co.vine.android.widget.FakeActionBar;
            import co.vine.android.widget.Typefaces;
            import co.vine.android.widget.TypefacesSpan;
            import com.android.debug.p002hv.ViewServer;
            import com.edisonwang.android.slog.SLog;
            import com.jeremyfeinstein.slidingmenu.lib.SlidingActivityHelper;
            import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
            import java.util.ArrayList;
            import java.util.Collection;
            
            
    public abstract class BaseActionBarActivity extends ActionBarActivity implements SlidingActivityHelper.MenuStateHandler.MenuStateListener {
                public static final String FINISH_BROADCAST_ACTION = "co.vine.android.FINISH";
                private static IntentFilter FINISH_INTENT_FILTER = new IntentFilter();
                private static final String TAG = "BaseActionBarActivity";
                private final BasePluginManager<Plugin> mDefaultPluginManager = new BasePluginManager<>();
                private FakeActionBar mFakeActionBar;
                private final BroadcastReceiver mFinishReceiver;
                protected Menu mMenu;
                protected ProgressDialog mProgressDialog;
                private SlidingActivityHelper mSlidingMenuHelper;
                private int mVineGreen;

        public BaseActionBarActivity() {
            this.mDefaultPluginManager.addPlugin(new AnalyticsPlugin());
           this.mFinishReceiver = new C01501();
                }

                static {
           FINISH_INTENT_FILTER.addAction(FINISH_BROADCAST_ACTION);
                }

                @Override 
        public boolean onCreatePanelMenu(int featureId, Menu menu) {
                    try {
                return super.onCreatePanelMenu(featureId, menu);
                    } catch (Exception e) {
                CrashUtil.logException(e);
                return false;
                    }
                }

                
       class C01501 extends BroadcastReceiver {
           C01501() {
                    }

                    @Override 
           public void onReceive(Context context, Intent intent) {
               if (intent != null && BaseActionBarActivity.FINISH_BROADCAST_ACTION.equals(intent.getAction())) {
                   BaseActionBarActivity.this.finish();
                        }
                    }
                }

                @Override 
       protected void onStart() {
           super.onStart();
           FlurryUtils.start(this);
           this.mDefaultPluginManager.onStart(this);
                }

                @Override 
       protected void onStop() {
           super.onStop();
           FlurryUtils.stop(this);
           this.mDefaultPluginManager.onStop(this);
                }

       public boolean isFakeActionBarEnabled() {
           return false;
                }

                @Override 
       protected void onCreate(Bundle savedInstanceState) {
           throw new IllegalArgumentException("If extending BaseActivity, please use multiple parameter version of this method.");
                }

       protected void restoreActivityState() {
           if (BuildUtil.isLogsOn()) {
               Log.d(TAG, "Restoring activity " + getClass().getName());
                    }
                }

       public void preSetContentView() {
                }

                @Override 
       public void onPostCreate(Bundle savedInstanceState) {
           super.onPostCreate(savedInstanceState);
           if (this.mFakeActionBar != null) {
               this.mFakeActionBar.onPostCreate(savedInstanceState);
               applyDefaultTitleViewStylingToFakeActionBar();
                    }
           if (this.mSlidingMenuHelper != null) {
               this.mSlidingMenuHelper.onPostCreate(savedInstanceState);
                    }
                }

       protected void applyDefaultTitleViewStylingToFakeActionBar() {
           Resources res = getResources();
           FakeActionBar fakeActionBar = getFakeActionBar();
           fakeActionBar.getTitleView().setTextColor(res.getColor(C0257R.color.primary_text));
           fakeActionBar.getTitleView().setTypeface(Typefaces.get(this).mediumContent);
           fakeActionBar.getTitleView().setTextSize(0, res.getDimensionPixelSize(C0257R.dimen.font_size_large));
                }

                @Override 
       public View findViewById(int id) {
           View v = super.findViewById(id);
           if (v == null) {
               if (this.mSlidingMenuHelper != null) {
                   return this.mSlidingMenuHelper.findViewById(id);
                        }
               return null;
                    }
           return v;
                }

                @Override 
       public void setContentView(int layoutResID) {
           if (this.mSlidingMenuHelper != null || this.mFakeActionBar != null) {
               setContentView(getLayoutInflater().inflate(layoutResID, (ViewGroup) null));
                    } else {
               super.setContentView(layoutResID);
                    }
                }

                @Override 
       public void setContentView(View v) {
           if (this.mSlidingMenuHelper != null || this.mFakeActionBar != null) {
               setContentView(v, new ViewGroup.LayoutParams(-1, -1));
                    } else {
               super.setContentView(v);
                    }
                }

                @Override 
       public void setContentView(View v, ViewGroup.LayoutParams params) {
           if (this.mFakeActionBar != null) {
               this.mFakeActionBar.setContentView(v, params);
               v = this.mFakeActionBar.getRoot();
                    }
           super.setContentView(v, params);
           if (this.mSlidingMenuHelper != null) {
               this.mSlidingMenuHelper.registerAboveContentView(v, params);
                    }
                }

       public SlidingMenu getSlidingMenu() {
           if (this.mSlidingMenuHelper != null) {
                        return this.mSlidingMenuHelper.getSlidingMenu();
                    }
                    return null;
                }

       public SlidingActivityHelper getSlidingMenuHelper() {
           return this.mSlidingMenuHelper;
                }

                @Override 
       public boolean onKeyUp(int keyCode, @NonNull KeyEvent event) {
           if (this.mSlidingMenuHelper != null) {
               boolean b = this.mSlidingMenuHelper.onKeyUp(keyCode, event);
               if (b) {
                            return true;
                        }
                    }
           if (this.mDefaultPluginManager.onKeyUp(keyCode, event)) {
                        return true;
                    }
           return super.onKeyUp(keyCode, event);
                }

                @Override 
       public boolean onKeyDown(int keyCode, KeyEvent event) {
           if (this.mDefaultPluginManager.onKeyDown(keyCode, event)) {
               return true;
                    }
                    return super.onKeyDown(keyCode, event);
                }

       public View setBehindContentView(View v) {
           setBehindContentView(v, new ViewGroup.LayoutParams(-1, -1));
           return v;
                }

       public View setBehindContentView(View v, ViewGroup.LayoutParams params) {
           this.mSlidingMenuHelper.setBehindContentView(v, params);
           return v;
                }

       public View setBehindContentView(int id) {
           View backView = getLayoutInflater().inflate(id, (ViewGroup) null);
           setBehindContentView(backView);
           return backView;
                }

       public void onCreate(Bundle savedInstanceState, int layout, boolean loginRequired, boolean readOnly) {
                    try {
               super.onCreate(savedInstanceState);
                    } catch (AndroidRuntimeException e) {
               CrashUtil.logException(e);
                    } catch (NoSuchFieldError e2) {
               CrashUtil.logException(e2);
                    }
           if (isFakeActionBarEnabled()) {
               this.mFakeActionBar = new FakeActionBar(this);
               this.mFakeActionBar.onCreate();
               requestWindowFeature(1);
                    }
           this.mVineGreen = getResources().getColor(C0257R.color.vine_green);
           if (isSlidingMenuEnabled()) {
               SLog.m464i("Sliding menu is enabled.");
               this.mSlidingMenuHelper = createSlidingMenuHelper();
               this.mSlidingMenuHelper.onCreate(savedInstanceState);
               this.mSlidingMenuHelper.setMenuStateListener(this);
                    }
           preSetContentView();
           setContentView(layout);
           Activity parent = getParent();
           boolean needsToLogin = loginRequired && !VineAccountHelper.isLoggedIn(this, readOnly);
           if (needsToLogin && parent == null) {
               StartActivity.toStart(this);
                    }
           if (SystemUtil.isViewServerEnabled(this)) {
               ViewServer.get(this).addWindow(this);
                    }
           registerReceiver(this.mFinishReceiver, FINISH_INTENT_FILTER, CrossConstants.BROADCAST_PERMISSION, null);
           this.mDefaultPluginManager.addPlugin(new DebugSubPluginManager(onCreateDebugPlugins()));
           this.mDefaultPluginManager.addPlugins(onCraeteDefaultPlugins());
           this.mDefaultPluginManager.onActivityCreated(this);
                }

       protected Collection<Plugin> onCreateDebugPlugins() {
           ArrayList<Plugin> debugPlugins = new ArrayList<>();
           debugPlugins.add(new DebugReceiverPlugin());
           if (SLog.sLogsOn) {
               debugPlugins.add(new DebugTasksPlugin());
                    }
           return debugPlugins;
                }

       protected ArrayList<Plugin> onCraeteDefaultPlugins() {
           return new ArrayList<>();
                }

       protected SlidingActivityHelper createSlidingMenuHelper() {
           return new SlidingActivityHelper(this);
                }

                @Override 
       protected void onSaveInstanceState(Bundle outState) {
           super.onSaveInstanceState(outState);
           if (this.mSlidingMenuHelper != null) {
               this.mSlidingMenuHelper.onSaveInstanceState(outState);
                    }
                }

       public ArrayList<Plugin> getEnabledPlugins() {
           return this.mDefaultPluginManager.getEnabledPlugins();
                }

                @Override 
       public boolean onCreateOptionsMenu(Menu menu) {
           updateMuteActionState(menu.findItem(C0257R.id.audio));
           this.mMenu = menu;
           if (this.mFakeActionBar != null) {
               this.mFakeActionBar.onCreateOptionsMenu(this.mMenu, true);
                    }
           this.mDefaultPluginManager.onCreateOptionsMenu(menu);
                    if (getWindow() != null && getWindow().getDecorView() != null) {
                        getWindow().getDecorView().post(new Runnable() {
                            @Override
                            public void run() {
                                whitenVineOverflowButton(getWindow().getDecorView());
                            }
                        });
                    }
                    return true;
                }

                @Override 
       protected void onResume() {
           super.onResume();
           if (getSlidingMenu() != null && getSlidingMenu().isMenuShowing()) {
               sendBroadcast(new Intent(MuteUtil.ACTION_CHANGED_TO_MUTE), CrossConstants.BROADCAST_PERMISSION);
                    } else if (!MuteUtil.isMuted(this)) {
               sendBroadcast(new Intent(MuteUtil.ACTION_CHANGED_TO_UNMUTE), CrossConstants.BROADCAST_PERMISSION);
                    }
           CrashUtil.set("Activity", getClass().getName() + " - " + System.currentTimeMillis());
           if (BuildUtil.isLogsOn() && SystemUtil.isViewServerEnabled(this)) {
               ViewServer.get(this).setFocusedWindow(this);
                    }
           if (this.mMenu != null) {
               updateMuteActionState(this.mMenu.findItem(C0257R.id.audio));
                    }
           this.mDefaultPluginManager.onResume(this);
                    registerReceiver(this.mFinishReceiver, FINISH_INTENT_FILTER, CrossConstants.BROADCAST_PERMISSION, null);
                    
                    if (getWindow() != null && getWindow().getDecorView() != null) {
                        getWindow().getDecorView().post(new Runnable() {
                            @Override
                            public void run() {
                                applyVineToolbarStyle();
                            }
                        });
                    }
                }

                @Override 
       protected void onActivityResult(int requestCode, int resultCode, Intent data) {
           if (!this.mDefaultPluginManager.onActivityResult(this, requestCode, resultCode, data)) {
               super.onActivityResult(requestCode, resultCode, data);
                    }
                }

                @Override
                public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                    if (requestCode != AppImpl.REQUEST_RECORDING_PERMISSIONS) {
                        return;
                    }
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                    }
                    AppImpl.getInstance().startCapture(this);
                }

                @Override 
       protected void onPause() {
           super.onPause();
           dismissDialog();
           this.mDefaultPluginManager.onPause();
                }

                @Override 
       public boolean onPrepareOptionsMenu(Menu menu) {
           boolean r = super.onPrepareOptionsMenu(menu);
           if (this.mFakeActionBar != null) {
               this.mFakeActionBar.onPrepareOptionsMenu(menu, r);
                    }
           return r;
                }

                @Override 
       protected void onDestroy() {
           super.onDestroy();
           if (SystemUtil.isViewServerEnabled(this)) {
               ViewServer.get(this).removeWindow(this);
                    }
           this.mDefaultPluginManager.onDestroy();
           unregisterReceiver(this.mFinishReceiver);
                }

       public void updateMuteActionState(MenuItem menu) {
           if (menu != null) {
               boolean isMuted = MuteUtil.isMuted(this);
               SLog.m447d("Is muted? {}", Boolean.valueOf(isMuted));
               menu.setTitle(isMuted ? C0257R.string.unmute : C0257R.string.mute);
                    }
                }

                @TargetApi(14)
       private void setupActionBarJBOnly(Boolean setHomeButtonEnabled, Boolean setDisplayShowTitleEnabled, Boolean setDisplayHomeAsUpEnabled) {
           ActionBar actionBar = getActionBar();
           if (actionBar != null) {
               if (setHomeButtonEnabled != null) {
                   actionBar.setHomeButtonEnabled(setHomeButtonEnabled.booleanValue());
                        }
               if (setDisplayShowTitleEnabled != null) {
                   actionBar.setDisplayShowTitleEnabled(setDisplayShowTitleEnabled.booleanValue());
                        }
               if (setDisplayHomeAsUpEnabled != null) {
                   actionBar.setDisplayHomeAsUpEnabled(setDisplayHomeAsUpEnabled.booleanValue());
                        }
                    }
                }

                @Override 
       public void onBackPressed() {
                    try {
               if (!this.mDefaultPluginManager.onBackPressed()) {
                   super.onBackPressed();
                        }
                    } catch (IllegalStateException e) {
               CrashUtil.logException(e);
                    }
                }

       protected void setupActionBar(Boolean setHomeButtonEnabled, Boolean setDisplayShowTitleEnabled, int titleRes, Boolean setDisplayHomeAsUpEnabled, Boolean setDisplayLogoEnabled) {
           String title = titleRes > 0 ? getString(titleRes) : null;
           setupActionBar(setHomeButtonEnabled, setDisplayShowTitleEnabled, title, setDisplayHomeAsUpEnabled, setDisplayLogoEnabled);
                }

       protected void setupActionBar(Boolean setHomeButtonEnabled, Boolean setDisplayShowTitleEnabled, String title, Boolean setDisplayHomeAsUpEnabled, Boolean setDisplayLogoEnabled) {
           if (this.mFakeActionBar != null) {
               if (setHomeButtonEnabled != null) {
                   this.mFakeActionBar.setHomeButtonEnabled(setHomeButtonEnabled);
                        }
               if (setDisplayShowTitleEnabled != null) {
                   this.mFakeActionBar.setDisplayShowTitleEnabled(setDisplayShowTitleEnabled);
                        }
               if (setDisplayHomeAsUpEnabled != null) {
                   this.mFakeActionBar.setDisplayHomeAsUpEnabled(setDisplayHomeAsUpEnabled);
                        }
               if (!TextUtils.isEmpty(title)) {
                   this.mFakeActionBar.setTitle(title);
                        }
               if (setDisplayLogoEnabled != null) {
                   this.mFakeActionBar.setDisplayLogoEnabled(setDisplayLogoEnabled);
                   return;
                        }
               return;
                    }
           android.support.v7.app.ActionBar ab = getSupportActionBar();
           if (ab != null) {
               if (setHomeButtonEnabled != null) {
                   ab.setHomeButtonEnabled(setHomeButtonEnabled.booleanValue());
                        }
               if (setDisplayShowTitleEnabled != null) {
                   ab.setDisplayShowTitleEnabled(setDisplayShowTitleEnabled.booleanValue());
                        }
               if (setDisplayHomeAsUpEnabled != null) {
                   ab.setDisplayHomeAsUpEnabled(setDisplayHomeAsUpEnabled.booleanValue());
                        }
               if (!TextUtils.isEmpty(title) && Build.VERSION.SDK_INT >= 19) {
                   SpannableString spannable = new SpannableString(title);
                   TypefacesSpan medium = new TypefacesSpan(Typefaces.get(this).getContentTypeface(0, 3));
                   spannable.setSpan(medium, 0, title.length(), 33);
                   ab.setTitle(spannable);
                        }
               if (!setDisplayLogoEnabled.booleanValue()) {
                   ab.setIcon(android.R.color.transparent);
                        }
                    }
           if (Build.VERSION.SDK_INT >= 14) {
               setupActionBarJBOnly(setHomeButtonEnabled, setDisplayShowTitleEnabled, setDisplayHomeAsUpEnabled);
                    }
                    getWindow().getDecorView().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            applyVineToolbarStyle();
                        }
                    }, 250L);
                    getWindow().getDecorView().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            applyVineToolbarStyle();
                        }
                    }, 800L);
                }

                





                protected void applyVineToolbarStyle() {
                    float density = getResources().getDisplayMetrics().density;
                    int barHeight = (int) (48.0f * density + 0.5f);
                    int actionBarId = getResources().getIdentifier("action_bar", "id", getPackageName());
                    View actionBarView = findViewById(actionBarId);
                    if (actionBarView != null) {
                        ViewGroup.LayoutParams params = actionBarView.getLayoutParams();
                        if (params != null) {
                            params.height = barHeight;
                            actionBarView.setLayoutParams(params);
                        }
                        actionBarView.setMinimumHeight(barHeight);
                    }
                    int containerId = getResources().getIdentifier("action_bar_container", "id", getPackageName());
                    View container = findViewById(containerId);
                    if (container != null) {
                        ViewGroup.LayoutParams containerParams = container.getLayoutParams();
                        if (containerParams != null) {
                            containerParams.height = barHeight;
                            container.setLayoutParams(containerParams);
                        }
                        container.setMinimumHeight(barHeight);
                    }
                    centerVineUpButton(getWindow().getDecorView());
                    makeVineBackRegionClickable(getWindow().getDecorView());
                    whitenVineOverflowButton(getWindow().getDecorView());
                }

                private boolean isVineUpView(View view) {
                    if (!(view instanceof ImageView)) {
                        return false;
                    }
                    CharSequence desc = view.getContentDescription();
                    if (desc != null && desc.toString().toLowerCase().contains("navigate")) {
                        return true;
                    }
                    int upId = getResources().getIdentifier("up", "id", getPackageName());
                    return upId != 0 && view.getId() == upId;
                }

                private void centerVineUpButton(View view) {
                    if (isVineUpView(view)) {
                        final ImageView image = (ImageView) view;
                        image.setScaleType(ImageView.ScaleType.CENTER);
                        image.setPadding(0, 0, 0, 0);
                        
                        
                        
                        float density = getResources().getDisplayMetrics().density;
                        int upSize = (int) (24.0f * density + 0.5f);
                        ViewGroup.LayoutParams params = image.getLayoutParams();
                        if (params != null) {
                            params.width = upSize;
                            params.height = upSize;
                            if (params instanceof LinearLayout.LayoutParams) {
                                ((LinearLayout.LayoutParams) params).gravity = Gravity.CENTER_VERTICAL;
                            } else if (params instanceof FrameLayout.LayoutParams) {
                                ((FrameLayout.LayoutParams) params).gravity = Gravity.CENTER_VERTICAL;
                            }
                            image.setLayoutParams(params);
                        }
                        image.setMinimumWidth(0);
                        image.setMinimumHeight(0);
                        
                        
                        try {
                            image.setBackgroundDrawable(null);
                        } catch (Exception ignored) {
                        }
                        image.post(new Runnable() {
                            @Override
                            public void run() {
                                View actionBar = findViewById(getResources().getIdentifier("action_bar", "id", getPackageName()));
                                if (actionBar == null) {
                                    return;
                                }
                                int[] barLoc = new int[2];
                                int[] imgLoc = new int[2];
                                actionBar.getLocationOnScreen(barLoc);
                                image.getLocationOnScreen(imgLoc);
                                float barCenter = barLoc[1] + actionBar.getHeight() / 2.0f;
                                float imgCenter = imgLoc[1] + image.getHeight() / 2.0f;
                                float delta = barCenter - imgCenter;
                                if (Math.abs(delta) > 0.5f && Math.abs(delta) < 60.0f) {
                                    image.setTranslationY(image.getTranslationY() + delta);
                                }
                            }
                        });
                        return;
                    }
                    if (view instanceof ViewGroup) {
                        ViewGroup group = (ViewGroup) view;
                        for (int i = 0; i < group.getChildCount(); i++) {
                            centerVineUpButton(group.getChildAt(i));
                        }
                    }
                }

                private void makeVineBackRegionClickable(View root) {
                    final View actionBarView = findViewById(getResources().getIdentifier("action_bar", "id", getPackageName()));
                    if (!(actionBarView instanceof ViewGroup)) {
                        return;
                    }
                    final ViewGroup bar = (ViewGroup) actionBarView;
                    
                    boolean hasUp = false;
                    for (int i = 0; i < bar.getChildCount(); i++) {
                        if (isVineUpView(bar.getChildAt(i))) {
                            hasUp = true;
                            break;
                        }
                    }
                    if (!hasUp) {
                        return;
                    }
                    
                    
                    for (int i = 0; i < bar.getChildCount(); i++) {
                        View child = bar.getChildAt(i);
                        if (child instanceof ImageView || child instanceof android.widget.TextView) {
                            try {
                                child.setBackgroundDrawable(null);
                            } catch (Exception ignored) {
                            }
                        }
                    }
                    
                    
                    bar.post(new Runnable() {
                        @Override
                        public void run() {
                            View up = null;
                            View title = null;
                            for (int i = 0; i < bar.getChildCount(); i++) {
                                View child = bar.getChildAt(i);
                                if (up == null && isVineUpView(child)) {
                                    up = child;
                                }
                                if (title == null && child instanceof android.widget.TextView) {
                                    title = child;
                                }
                            }
                            if (up == null || title == null) {
                                return;
                            }
                            float density = getResources().getDisplayMetrics().density;
                            int endPadding = (int) (12.0f * density + 0.5f);
                            int left = Math.min(up.getLeft(), title.getLeft());
                            int right = Math.max(up.getRight(), title.getRight()) + endPadding;
                            int width = Math.max(right - left, (int) (120.0f * density + 0.5f));
                            int height = bar.getHeight() > 0 ? bar.getHeight() : (int) (48.0f * density + 0.5f);
                            View container = findViewById(getResources().getIdentifier("action_bar_container", "id", getPackageName()));
                            ViewGroup overlayParent = container instanceof ViewGroup ? (ViewGroup) container : bar;
                            View overlay = overlayParent.findViewWithTag("vine_back_overlay");
                            if (overlay == null) {
                                overlay = new View(BaseActionBarActivity.this);
                                overlay.setTag("vine_back_overlay");
                                overlay.setClickable(true);
                                overlay.setFocusable(true);
                                overlay.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        BaseActionBarActivity.this.onBackPressed();
                                    }
                                });
                                try {
                                    int[] attrs = new int[]{android.R.attr.selectableItemBackground};
                                    TypedArray a = obtainStyledAttributes(attrs);
                                    int resId = a.getResourceId(0, 0);
                                    a.recycle();
                                    if (resId != 0) {
                                        overlay.setBackgroundResource(resId);
                                    }
                                } catch (Exception e) {
                                    CrashUtil.logException(e);
                                }
                                if (overlayParent instanceof FrameLayout) {
                                    FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width, height, Gravity.LEFT | Gravity.CENTER_VERTICAL);
                                    lp.leftMargin = left;
                                    overlayParent.addView(overlay, lp);
                                } else {
                                    ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(width, height);
                                    overlayParent.addView(overlay, lp);
                                    overlay.setTranslationX(left);
                                    overlay.setTranslationY(0.0f);
                                }
                            } else {
                                ViewGroup.LayoutParams lp = overlay.getLayoutParams();
                                if (lp instanceof FrameLayout.LayoutParams) {
                                    ((FrameLayout.LayoutParams) lp).width = width;
                                    ((FrameLayout.LayoutParams) lp).height = height;
                                    ((FrameLayout.LayoutParams) lp).gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
                                    ((FrameLayout.LayoutParams) lp).leftMargin = left;
                                    overlay.setLayoutParams(lp);
                                } else if (lp != null) {
                                    lp.width = width;
                                    lp.height = height;
                                    overlay.setLayoutParams(lp);
                                }
                                overlay.bringToFront();
                                overlayParent.invalidate();
                            }
                        }
                    });
                }

                private void whitenVineOverflowButton(View view) {
                    if (view instanceof ImageView) {
                        String cls = view.getClass().getName();
                        CharSequence desc = view.getContentDescription();
                        boolean isOverflow = cls.contains("OverflowMenuButton");
                        if (!isOverflow && desc != null) {
                            String d = desc.toString().toLowerCase();
                            isOverflow = d.contains("more option") || d.contains("more options");
                        }
                        if (isOverflow) {
                            try {
                                ImageView iv = (ImageView) view;
                                iv.setColorFilter(null);
                                iv.clearColorFilter();
                                iv.setAlpha(1.0f);
                                iv.setImageAlpha(255);
                                int whiteId = getResources().getIdentifier("ic_action_overflow", "drawable", getPackageName());
                                if (whiteId != 0) {
                                    iv.setImageResource(whiteId);
                                }
                            } catch (Exception e) {
                                CrashUtil.logException(e);
                            }
                            return;
                        }
                    }
                    if (view instanceof ViewGroup) {
                        ViewGroup group = (ViewGroup) view;
                        for (int i = 0; i < group.getChildCount(); i++) {
                            whitenVineOverflowButton(group.getChildAt(i));
                        }
                    }
                }

                private void applySelectableBackBackground(View view) {
                    try {
                        int[] attrs = new int[]{android.R.attr.selectableItemBackground};
                        TypedArray a = obtainStyledAttributes(attrs);
                        int resId = a.getResourceId(0, 0);
                        a.recycle();
                        if (resId != 0) {
                            view.setBackgroundResource(resId);
                            view.setClickable(true);
                        }
                    } catch (Exception e) {
                        
                        CrashUtil.logException(e);
                    }
                }

                





                private void installActionBarHomeControl(View view) {
                    int upId = getResources().getIdentifier("up", "id", getPackageName());
                    int titleId = getResources().getIdentifier("action_bar_title", "id", getPackageName());
                    if (view.getId() == upId && view.getParent() instanceof ViewGroup) {
                        
                        
                        
                        
                        
                        ViewGroup home = (ViewGroup) view.getParent();
                        home.setClickable(true);
                        home.setFocusable(true);
                        home.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                BaseActionBarActivity.this.onBackPressed();
                            }
                        });
                        return;
                    }
                    if (view.getId() == titleId) {
                        ViewParent parent = view.getParent();
                        View titleControl = view;
                        int actionBarId = getResources().getIdentifier("action_bar", "id", getPackageName());
                        while (parent instanceof ViewGroup && parent != getWindow().getDecorView()) {
                            titleControl = (View) parent;
                            if (titleControl.getParent() instanceof View && titleControl.getParent() == findViewById(actionBarId)) {
                                break;
                            }
                            parent = parent.getParent();
                        }
                        titleControl.setEnabled(true);
                        titleControl.setClickable(true);
                        titleControl.setFocusable(true);
                        titleControl.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                BaseActionBarActivity.this.onBackPressed();
                            }
                        });
                        return;
                    }
                    if (view instanceof ViewGroup) {
                        ViewGroup group = (ViewGroup) view;
                        for (int i = 0; i < group.getChildCount(); i++) {
                            installActionBarHomeControl(group.getChildAt(i));
                        }
                    }
                }

                private void normalizeActionBarUpButton(View view) {
                    int upId = getResources().getIdentifier("up", "id", getPackageName());
                    boolean isUpView = view.getId() == upId;
                    if (view instanceof ImageButton || view instanceof ImageView) {
                        CharSequence description = view.getContentDescription();
                        if (isUpView || (description != null && description.toString().toLowerCase().contains("navigate"))) {
                            ViewGroup.LayoutParams params = view.getLayoutParams();
                            if (params != null) {
                                params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                                view.setLayoutParams(params);
                            }
                            ImageView image = (ImageView) view;
                            image.setScaleType(ImageView.ScaleType.CENTER);
                            image.setPadding(0, 0, 0, 0);
                            ViewParent parent = image.getParent();
                            View actionBar = findViewById(getResources().getIdentifier("action_bar", "id", getPackageName()));
                            if (actionBar instanceof View) {
                                int parentHeight = ((View) actionBar).getHeight();
                                int imageHeight = image.getHeight();
                                if (parentHeight > imageHeight && imageHeight > 0) {
                                    image.setTranslationY((parentHeight - imageHeight) / 2.0f);
                                } else {
                                    image.setTranslationY(0.0f);
                                }
                            } else if (parent instanceof View) {
                                int parentHeight = ((View) parent).getHeight();
                                int imageHeight = image.getHeight();
                                if (parentHeight > imageHeight && imageHeight > 0) {
                                    image.setTranslationY((parentHeight - imageHeight) / 2.0f);
                                } else {
                                    image.setTranslationY(0.0f);
                                }
                            }
                            return;
                        }
                    }
                    if (view instanceof ViewGroup) {
                        ViewGroup group = (ViewGroup) view;
                        for (int i = 0; i < group.getChildCount(); i++) {
                            normalizeActionBarUpButton(group.getChildAt(i));
                        }
                    }
                }

                @Override 
       public boolean onOptionsItemSelected(MenuItem item) {
           if (this.mDefaultPluginManager.onOptionsItemSelected(item)) {
                        return true;
                    }
           int id = item.getItemId();
           if (id == 16908332) {
               if (this.mSlidingMenuHelper != null) {
                   this.mSlidingMenuHelper.toggle();
                            return true;
                        }
                        try {
                   super.onBackPressed();
                            return true;
                        } catch (IllegalStateException e) {
                            return true;
                        }
                    }
           if (id == C0257R.id.capture) {
               AppImpl.getInstance().startCapture(this);
                        return true;
                    }
           if (id == C0257R.id.audio) {
               MuteUtil.setMuted(this, !MuteUtil.isMuted(this));
               updateMuteActionState(item);
                        return true;
                    }
           return super.onOptionsItemSelected(item);
                }

       public FakeActionBar getFakeActionBar() {
           return this.mFakeActionBar;
                }

       public void setActionBarColor(int profileBackground) {
           if (profileBackground != 0) {
               if (this.mFakeActionBar != null) {
                   this.mFakeActionBar.setActionBarColor(profileBackground | (-16777216));
                   this.mFakeActionBar.getActionBar().invalidate();
                        } else {
                   getSupportActionBar().setBackgroundDrawable(new ColorDrawable(profileBackground | (-16777216)));
                        }
               if (this instanceof HomeTabActivity) {
                   if (profileBackground == Settings.DEFAULT_PROFILE_COLOR || profileBackground <= 0 || profileBackground == (Settings.DEFAULT_PROFILE_COLOR | (-16777216))) {
                       ((HomeTabActivity) this).setProfileColor(getResources().getColor(C0257R.color.vine_green));
                            } else {
                       ((HomeTabActivity) this).setProfileColor(profileBackground | (-16777216));
                            }
                        }
                    } else if (this.mVineGreen != 0) {
               setActionBarColor(this.mVineGreen);
                    }
           invalidateOptionsMenu();
                }

       public void setActionBarColor() {
           setActionBarColor(this.mVineGreen);
                }

       protected void dismissDialog() {
           if (this.mProgressDialog != null) {
               this.mProgressDialog.dismiss();
               this.mProgressDialog = null;
                    }
                }

       protected boolean isSlidingMenuEnabled() {
           return false;
                }

                @Override 
       public boolean onMenuDrag(View v, DragEvent event) {
           return false;
                }

                @Override 
       public void onMenuClosed() {
                }

                @Override 
       public void onMenuClose() {
                }

                @Override 
       public void onMenuOpened() {
                }

                @Override 
       public void onMenuOpen() {
                }

                @Override 
       public void onMenuClick(View v) {
                }

                @Nullable
       protected View getWindowDecorView() {
           View decor = getWindow().getDecorView();
           if (decor instanceof ViewGroup) {
               return decor;
                    }
                    return null;
                }
            }
