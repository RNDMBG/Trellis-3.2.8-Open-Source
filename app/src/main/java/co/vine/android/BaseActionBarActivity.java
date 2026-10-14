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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 60 */    public abstract class BaseActionBarActivity extends ActionBarActivity implements SlidingActivityHelper.MenuStateHandler.MenuStateListener {
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

/* 61 */        public BaseActionBarActivity() {
/* 84 */            this.mDefaultPluginManager.addPlugin(new AnalyticsPlugin());
/* 106 */           this.mFinishReceiver = new C01501();
                }

                static {
/* 103 */           FINISH_INTENT_FILTER.addAction(FINISH_BROADCAST_ACTION);
                }

                @Override // android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity, android.view.Window.Callback
/* 89 */        public boolean onCreatePanelMenu(int featureId, Menu menu) {
                    try {
/* 90 */                return super.onCreatePanelMenu(featureId, menu);
                    } catch (Exception e) {
/* 92 */                CrashUtil.logException(e);
/* 94 */                return false;
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.BaseActionBarActivity$1 */
/* 104 */       class C01501 extends BroadcastReceiver {
/* 105 */           C01501() {
                    }

                    @Override // android.content.BroadcastReceiver
/* 108 */           public void onReceive(Context context, Intent intent) {
/* 109 */               if (intent != null && BaseActionBarActivity.FINISH_BROADCAST_ACTION.equals(intent.getAction())) {
/* 110 */                   BaseActionBarActivity.this.finish();
                        }
                    }
                }

                @Override // android.support.v4.app.FragmentActivity, android.app.Activity
/* 116 */       protected void onStart() {
/* 117 */           super.onStart();
/* 118 */           FlurryUtils.start(this);
/* 119 */           this.mDefaultPluginManager.onStart(this);
                }

                @Override // android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 123 */       protected void onStop() {
/* 124 */           super.onStop();
/* 125 */           FlurryUtils.stop(this);
/* 126 */           this.mDefaultPluginManager.onStop(this);
                }

/* 129 */       public boolean isFakeActionBarEnabled() {
/* 130 */           return false;
                }

                @Override // android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 134 */       protected void onCreate(Bundle savedInstanceState) {
/* 135 */           throw new IllegalArgumentException("If extending BaseActivity, please use multiple parameter version of this method.");
                }

/* 142 */       protected void restoreActivityState() {
/* 143 */           if (BuildUtil.isLogsOn()) {
/* 144 */               Log.d(TAG, "Restoring activity " + getClass().getName());
                    }
                }

/* 148 */       public void preSetContentView() {
                }

                @Override // android.app.Activity
/* 152 */       public void onPostCreate(Bundle savedInstanceState) {
/* 153 */           super.onPostCreate(savedInstanceState);
/* 154 */           if (this.mFakeActionBar != null) {
/* 155 */               this.mFakeActionBar.onPostCreate(savedInstanceState);
/* 156 */               applyDefaultTitleViewStylingToFakeActionBar();
                    }
/* 158 */           if (this.mSlidingMenuHelper != null) {
/* 159 */               this.mSlidingMenuHelper.onPostCreate(savedInstanceState);
                    }
                }

/* 163 */       protected void applyDefaultTitleViewStylingToFakeActionBar() {
/* 164 */           Resources res = getResources();
/* 165 */           FakeActionBar fakeActionBar = getFakeActionBar();
/* 166 */           fakeActionBar.getTitleView().setTextColor(res.getColor(C0257R.color.primary_text));
/* 167 */           fakeActionBar.getTitleView().setTypeface(Typefaces.get(this).mediumContent);
/* 168 */           fakeActionBar.getTitleView().setTextSize(0, res.getDimensionPixelSize(C0257R.dimen.font_size_large));
                }

                @Override // android.app.Activity
/* 173 */       public View findViewById(int id) {
/* 174 */           View v = super.findViewById(id);
/* 175 */           if (v == null) {
/* 177 */               if (this.mSlidingMenuHelper != null) {
/* 178 */                   return this.mSlidingMenuHelper.findViewById(id);
                        }
/* 180 */               return null;
                    }
/* 180 */           return v;
                }

                @Override // android.support.v7.app.ActionBarActivity, android.app.Activity
/* 185 */       public void setContentView(int layoutResID) {
/* 186 */           if (this.mSlidingMenuHelper != null || this.mFakeActionBar != null) {
/* 187 */               setContentView(getLayoutInflater().inflate(layoutResID, (ViewGroup) null));
                    } else {
/* 189 */               super.setContentView(layoutResID);
                    }
                }

                @Override // android.support.v7.app.ActionBarActivity, android.app.Activity
/* 194 */       public void setContentView(View v) {
/* 195 */           if (this.mSlidingMenuHelper != null || this.mFakeActionBar != null) {
/* 196 */               setContentView(v, new ViewGroup.LayoutParams(-1, -1));
                    } else {
/* 198 */               super.setContentView(v);
                    }
                }

                @Override // android.support.v7.app.ActionBarActivity, android.app.Activity
/* 203 */       public void setContentView(View v, ViewGroup.LayoutParams params) {
/* 204 */           if (this.mFakeActionBar != null) {
/* 205 */               this.mFakeActionBar.setContentView(v, params);
/* 206 */               v = this.mFakeActionBar.getRoot();
                    }
/* 208 */           super.setContentView(v, params);
/* 209 */           if (this.mSlidingMenuHelper != null) {
/* 210 */               this.mSlidingMenuHelper.registerAboveContentView(v, params);
                    }
                }

/* 214 */       public SlidingMenu getSlidingMenu() {
/* 215 */           if (this.mSlidingMenuHelper != null) {
                        return this.mSlidingMenuHelper.getSlidingMenu();
                    }
                    return null;
                }

/* 218 */       public SlidingActivityHelper getSlidingMenuHelper() {
/* 219 */           return this.mSlidingMenuHelper;
                }

                @Override // android.app.Activity, android.view.KeyEvent.Callback
/* 223 */       public boolean onKeyUp(int keyCode, @NonNull KeyEvent event) {
/* 224 */           if (this.mSlidingMenuHelper != null) {
/* 225 */               boolean b = this.mSlidingMenuHelper.onKeyUp(keyCode, event);
/* 226 */               if (b) {
                            return true;
                        }
                    }
/* 228 */           if (this.mDefaultPluginManager.onKeyUp(keyCode, event)) {
                        return true;
                    }
/* 231 */           return super.onKeyUp(keyCode, event);
                }

                @Override // android.support.v4.app.FragmentActivity, android.app.Activity, android.view.KeyEvent.Callback
/* 235 */       public boolean onKeyDown(int keyCode, KeyEvent event) {
/* 236 */           if (this.mDefaultPluginManager.onKeyDown(keyCode, event)) {
/* 237 */               return true;
                    }
                    return super.onKeyDown(keyCode, event);
                }

/* 242 */       public View setBehindContentView(View v) {
/* 243 */           setBehindContentView(v, new ViewGroup.LayoutParams(-1, -1));
/* 244 */           return v;
                }

/* 247 */       public View setBehindContentView(View v, ViewGroup.LayoutParams params) {
/* 248 */           this.mSlidingMenuHelper.setBehindContentView(v, params);
/* 249 */           return v;
                }

/* 252 */       public View setBehindContentView(int id) {
/* 253 */           View backView = getLayoutInflater().inflate(id, (ViewGroup) null);
/* 254 */           setBehindContentView(backView);
/* 255 */           return backView;
                }

/* 260 */       public void onCreate(Bundle savedInstanceState, int layout, boolean loginRequired, boolean readOnly) {
                    try {
/* 261 */               super.onCreate(savedInstanceState);
                    } catch (AndroidRuntimeException e) {
/* 263 */               CrashUtil.logException(e);
                    } catch (NoSuchFieldError e2) {
/* 266 */               CrashUtil.logException(e2);
                    }
/* 269 */           if (isFakeActionBarEnabled()) {
/* 270 */               this.mFakeActionBar = new FakeActionBar(this);
/* 271 */               this.mFakeActionBar.onCreate();
/* 272 */               requestWindowFeature(1);
                    }
/* 275 */           this.mVineGreen = getResources().getColor(C0257R.color.vine_green);
/* 277 */           if (isSlidingMenuEnabled()) {
/* 278 */               SLog.m464i("Sliding menu is enabled.");
/* 279 */               this.mSlidingMenuHelper = createSlidingMenuHelper();
/* 280 */               this.mSlidingMenuHelper.onCreate(savedInstanceState);
/* 281 */               this.mSlidingMenuHelper.setMenuStateListener(this);
                    }
/* 283 */           preSetContentView();
/* 284 */           setContentView(layout);
/* 285 */           Activity parent = getParent();
/* 287 */           boolean needsToLogin = loginRequired && !VineAccountHelper.isLoggedIn(this, readOnly);
/* 290 */           if (needsToLogin && parent == null) {
/* 291 */               StartActivity.toStart(this);
                    }
/* 294 */           if (SystemUtil.isViewServerEnabled(this)) {
/* 295 */               ViewServer.get(this).addWindow(this);
                    }
/* 297 */           registerReceiver(this.mFinishReceiver, FINISH_INTENT_FILTER, CrossConstants.BROADCAST_PERMISSION, null);
/* 299 */           this.mDefaultPluginManager.addPlugin(new DebugSubPluginManager(onCreateDebugPlugins()));
/* 300 */           this.mDefaultPluginManager.addPlugins(onCraeteDefaultPlugins());
/* 301 */           this.mDefaultPluginManager.onActivityCreated(this);
                }

/* 304 */       protected Collection<Plugin> onCreateDebugPlugins() {
/* 305 */           ArrayList<Plugin> debugPlugins = new ArrayList<>();
/* 306 */           debugPlugins.add(new DebugReceiverPlugin());
/* 307 */           if (SLog.sLogsOn) {
/* 308 */               debugPlugins.add(new DebugTasksPlugin());
                    }
/* 310 */           return debugPlugins;
                }

/* 313 */       protected ArrayList<Plugin> onCraeteDefaultPlugins() {
/* 314 */           return new ArrayList<>();
                }

/* 317 */       protected SlidingActivityHelper createSlidingMenuHelper() {
/* 318 */           return new SlidingActivityHelper(this);
                }

                @Override // android.support.v4.app.FragmentActivity, android.app.Activity
/* 322 */       protected void onSaveInstanceState(Bundle outState) {
/* 323 */           super.onSaveInstanceState(outState);
/* 324 */           if (this.mSlidingMenuHelper != null) {
/* 325 */               this.mSlidingMenuHelper.onSaveInstanceState(outState);
                    }
                }

/* 329 */       public ArrayList<Plugin> getEnabledPlugins() {
/* 330 */           return this.mDefaultPluginManager.getEnabledPlugins();
                }

                @Override // android.app.Activity
/* 334 */       public boolean onCreateOptionsMenu(Menu menu) {
/* 335 */           updateMuteActionState(menu.findItem(C0257R.id.audio));
/* 336 */           this.mMenu = menu;
/* 337 */           if (this.mFakeActionBar != null) {
/* 338 */               this.mFakeActionBar.onCreateOptionsMenu(this.mMenu, true);
                    }
/* 340 */           this.mDefaultPluginManager.onCreateOptionsMenu(menu);
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

                @Override // android.support.v4.app.FragmentActivity, android.app.Activity
/* 345 */       protected void onResume() {
/* 346 */           super.onResume();
/* 347 */           if (getSlidingMenu() != null && getSlidingMenu().isMenuShowing()) {
/* 348 */               sendBroadcast(new Intent(MuteUtil.ACTION_CHANGED_TO_MUTE), CrossConstants.BROADCAST_PERMISSION);
                    } else if (!MuteUtil.isMuted(this)) {
/* 351 */               sendBroadcast(new Intent(MuteUtil.ACTION_CHANGED_TO_UNMUTE), CrossConstants.BROADCAST_PERMISSION);
                    }
/* 354 */           CrashUtil.set("Activity", getClass().getName() + " - " + System.currentTimeMillis());
/* 356 */           if (BuildUtil.isLogsOn() && SystemUtil.isViewServerEnabled(this)) {
/* 358 */               ViewServer.get(this).setFocusedWindow(this);
                    }
/* 361 */           if (this.mMenu != null) {
/* 362 */               updateMuteActionState(this.mMenu.findItem(C0257R.id.audio));
                    }
/* 364 */           this.mDefaultPluginManager.onResume(this);
                    registerReceiver(this.mFinishReceiver, FINISH_INTENT_FILTER, CrossConstants.BROADCAST_PERMISSION, null);
                    // Re-apply 48dp + centered back after return (Sync, Comments, etc).
                    if (getWindow() != null && getWindow().getDecorView() != null) {
                        getWindow().getDecorView().post(new Runnable() {
                            @Override
                            public void run() {
                                applyVineToolbarStyle();
                            }
                        });
                    }
                }

                @Override // android.support.v4.app.FragmentActivity, android.app.Activity
/* 369 */       protected void onActivityResult(int requestCode, int resultCode, Intent data) {
/* 370 */           if (!this.mDefaultPluginManager.onActivityResult(this, requestCode, resultCode, data)) {
/* 371 */               super.onActivityResult(requestCode, resultCode, data);
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

                @Override // android.support.v4.app.FragmentActivity, android.app.Activity
/* 376 */       protected void onPause() {
/* 377 */           super.onPause();
/* 378 */           dismissDialog();
/* 379 */           this.mDefaultPluginManager.onPause();
                }

                @Override // android.app.Activity
/* 383 */       public boolean onPrepareOptionsMenu(Menu menu) {
/* 384 */           boolean r = super.onPrepareOptionsMenu(menu);
/* 385 */           if (this.mFakeActionBar != null) {
/* 386 */               this.mFakeActionBar.onPrepareOptionsMenu(menu, r);
                    }
/* 388 */           return r;
                }

                @Override // android.support.v4.app.FragmentActivity, android.app.Activity
/* 392 */       protected void onDestroy() {
/* 393 */           super.onDestroy();
/* 394 */           if (SystemUtil.isViewServerEnabled(this)) {
/* 395 */               ViewServer.get(this).removeWindow(this);
                    }
/* 397 */           this.mDefaultPluginManager.onDestroy();
/* 398 */           unregisterReceiver(this.mFinishReceiver);
                }

/* 401 */       public void updateMuteActionState(MenuItem menu) {
/* 402 */           if (menu != null) {
/* 403 */               boolean isMuted = MuteUtil.isMuted(this);
/* 404 */               SLog.m447d("Is muted? {}", Boolean.valueOf(isMuted));
/* 405 */               menu.setTitle(isMuted ? C0257R.string.unmute : C0257R.string.mute);
                    }
                }

                @TargetApi(14)
/* 411 */       private void setupActionBarJBOnly(Boolean setHomeButtonEnabled, Boolean setDisplayShowTitleEnabled, Boolean setDisplayHomeAsUpEnabled) {
/* 412 */           ActionBar actionBar = getActionBar();
/* 413 */           if (actionBar != null) {
/* 414 */               if (setHomeButtonEnabled != null) {
/* 415 */                   actionBar.setHomeButtonEnabled(setHomeButtonEnabled.booleanValue());
                        }
/* 417 */               if (setDisplayShowTitleEnabled != null) {
/* 418 */                   actionBar.setDisplayShowTitleEnabled(setDisplayShowTitleEnabled.booleanValue());
                        }
/* 420 */               if (setDisplayHomeAsUpEnabled != null) {
/* 421 */                   actionBar.setDisplayHomeAsUpEnabled(setDisplayHomeAsUpEnabled.booleanValue());
                        }
                    }
                }

                @Override // android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 428 */       public void onBackPressed() {
                    try {
/* 429 */               if (!this.mDefaultPluginManager.onBackPressed()) {
/* 430 */                   super.onBackPressed();
                        }
                    } catch (IllegalStateException e) {
/* 433 */               CrashUtil.logException(e);
                    }
                }

/* 447 */       protected void setupActionBar(Boolean setHomeButtonEnabled, Boolean setDisplayShowTitleEnabled, int titleRes, Boolean setDisplayHomeAsUpEnabled, Boolean setDisplayLogoEnabled) {
/* 448 */           String title = titleRes > 0 ? getString(titleRes) : null;
/* 449 */           setupActionBar(setHomeButtonEnabled, setDisplayShowTitleEnabled, title, setDisplayHomeAsUpEnabled, setDisplayLogoEnabled);
                }

/* 456 */       protected void setupActionBar(Boolean setHomeButtonEnabled, Boolean setDisplayShowTitleEnabled, String title, Boolean setDisplayHomeAsUpEnabled, Boolean setDisplayLogoEnabled) {
/* 457 */           if (this.mFakeActionBar != null) {
/* 458 */               if (setHomeButtonEnabled != null) {
/* 459 */                   this.mFakeActionBar.setHomeButtonEnabled(setHomeButtonEnabled);
                        }
/* 461 */               if (setDisplayShowTitleEnabled != null) {
/* 462 */                   this.mFakeActionBar.setDisplayShowTitleEnabled(setDisplayShowTitleEnabled);
                        }
/* 464 */               if (setDisplayHomeAsUpEnabled != null) {
/* 465 */                   this.mFakeActionBar.setDisplayHomeAsUpEnabled(setDisplayHomeAsUpEnabled);
                        }
/* 467 */               if (!TextUtils.isEmpty(title)) {
/* 468 */                   this.mFakeActionBar.setTitle(title);
                        }
/* 470 */               if (setDisplayLogoEnabled != null) {
/* 471 */                   this.mFakeActionBar.setDisplayLogoEnabled(setDisplayLogoEnabled);
/* 506 */                   return;
                        }
/* 506 */               return;
                    }
/* 474 */           android.support.v7.app.ActionBar ab = getSupportActionBar();
/* 475 */           if (ab != null) {
/* 476 */               if (setHomeButtonEnabled != null) {
/* 477 */                   ab.setHomeButtonEnabled(setHomeButtonEnabled.booleanValue());
                        }
/* 479 */               if (setDisplayShowTitleEnabled != null) {
/* 480 */                   ab.setDisplayShowTitleEnabled(setDisplayShowTitleEnabled.booleanValue());
                        }
/* 482 */               if (setDisplayHomeAsUpEnabled != null) {
/* 483 */                   ab.setDisplayHomeAsUpEnabled(setDisplayHomeAsUpEnabled.booleanValue());
                        }
/* 485 */               if (!TextUtils.isEmpty(title) && Build.VERSION.SDK_INT >= 19) {
/* 489 */                   SpannableString spannable = new SpannableString(title);
/* 490 */                   TypefacesSpan medium = new TypefacesSpan(Typefaces.get(this).getContentTypeface(0, 3));
/* 492 */                   spannable.setSpan(medium, 0, title.length(), 33);
/* 493 */                   ab.setTitle(spannable);
                        }
/* 496 */               if (!setDisplayLogoEnabled.booleanValue()) {
/* 497 */                   ab.setIcon(android.R.color.transparent);
                        }
                    }
/* 501 */           if (Build.VERSION.SDK_INT >= 14) {
/* 502 */               setupActionBarJBOnly(setHomeButtonEnabled, setDisplayShowTitleEnabled, setDisplayHomeAsUpEnabled);
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

                /**
                 * Unified Vine toolbar style: 48dp bar (matches On The Rise),
                 * centered < icon, big back hitbox (< + logo + title) with
                 * pressed darkening. Applied to Sync, Comments, Followers,
                 * Popular Now and every other setupActionBar screen.
                 */
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
                        // Fixed 24dp square everywhere so < renders the 48px
                        // asset at full resolution, no downscale - Sync was
                        // blowing up to 84x126 and Settings down to 42x42.
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
                        // No per-view background - the unified overlay draws the
                        // single pressed rectangle. Clear any old separate box.
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
                    // Only on screens with a back arrow - never hijack home tabs.
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
                    // Clear old separate pressed boxes so only the unified
                    // rectangle shows.
                    for (int i = 0; i < bar.getChildCount(); i++) {
                        View child = bar.getChildAt(i);
                        if (child instanceof ImageView || child instanceof android.widget.TextView) {
                            try {
                                child.setBackgroundDrawable(null);
                            } catch (Exception ignored) {
                            }
                        }
                    }
                    // Single equal rectangle covering < + logo + title text.
                    // Built after layout so title width is known.
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
                        // Pressed feedback is best-effort; never crash toolbar.
                        CrashUtil.logException(e);
                    }
                }

                /**
                 * The legacy Vine action bar treats the arrow, logo, and title as
                 * one visual home/back control. AppCompat exposes only the small
                 * arrow ImageButton as clickable in the reconstructed sources,
                 * which leaves the rest of the pressed home region inert.
                 */
                private void installActionBarHomeControl(View view) {
                    int upId = getResources().getIdentifier("up", "id", getPackageName());
                    int titleId = getResources().getIdentifier("action_bar_title", "id", getPackageName());
                    if (view.getId() == upId && view.getParent() instanceof ViewGroup) {
                        // ActionBarView already owns the real legacy back listener.
                        // Reconstructed AppCompat builds do not reliably expose that
                        // listener, so restore it on the entire HomeView.  The HomeView
                        // already carries the legacy actionBarItemBackground selector,
                        // which supplies the darker rectangular pressed state.
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

                @Override // android.app.Activity
/* 509 */       public boolean onOptionsItemSelected(MenuItem item) {
/* 510 */           if (this.mDefaultPluginManager.onOptionsItemSelected(item)) {
                        return true;
                    }
/* 513 */           int id = item.getItemId();
/* 514 */           if (id == 16908332) {
/* 515 */               if (this.mSlidingMenuHelper != null) {
/* 516 */                   this.mSlidingMenuHelper.toggle();
                            return true;
                        }
                        try {
/* 519 */                   super.onBackPressed();
                            return true;
                        } catch (IllegalStateException e) {
                            return true;
                        }
                    }
/* 525 */           if (id == C0257R.id.capture) {
/* 526 */               AppImpl.getInstance().startCapture(this);
                        return true;
                    }
/* 528 */           if (id == C0257R.id.audio) {
/* 529 */               MuteUtil.setMuted(this, !MuteUtil.isMuted(this));
/* 530 */               updateMuteActionState(item);
                        return true;
                    }
/* 533 */           return super.onOptionsItemSelected(item);
                }

/* 537 */       public FakeActionBar getFakeActionBar() {
/* 538 */           return this.mFakeActionBar;
                }

/* 541 */       public void setActionBarColor(int profileBackground) {
/* 542 */           if (profileBackground != 0) {
/* 543 */               if (this.mFakeActionBar != null) {
/* 544 */                   this.mFakeActionBar.setActionBarColor(profileBackground | (-16777216));
/* 545 */                   this.mFakeActionBar.getActionBar().invalidate();
                        } else {
/* 547 */                   getSupportActionBar().setBackgroundDrawable(new ColorDrawable(profileBackground | (-16777216)));
                        }
/* 550 */               if (this instanceof HomeTabActivity) {
/* 551 */                   if (profileBackground == Settings.DEFAULT_PROFILE_COLOR || profileBackground <= 0 || profileBackground == (Settings.DEFAULT_PROFILE_COLOR | (-16777216))) {
/* 553 */                       ((HomeTabActivity) this).setProfileColor(getResources().getColor(C0257R.color.vine_green));
                            } else {
/* 555 */                       ((HomeTabActivity) this).setProfileColor(profileBackground | (-16777216));
                            }
                        }
                    } else if (this.mVineGreen != 0) {
/* 560 */               setActionBarColor(this.mVineGreen);
                    }
/* 563 */           invalidateOptionsMenu();
                }

/* 566 */       public void setActionBarColor() {
/* 567 */           setActionBarColor(this.mVineGreen);
                }

/* 570 */       protected void dismissDialog() {
/* 571 */           if (this.mProgressDialog != null) {
/* 572 */               this.mProgressDialog.dismiss();
/* 573 */               this.mProgressDialog = null;
                    }
                }

/* 577 */       protected boolean isSlidingMenuEnabled() {
/* 578 */           return false;
                }

                @Override // com.jeremyfeinstein.slidingmenu.lib.SlidingActivityHelper.MenuStateHandler.MenuStateListener
/* 582 */       public boolean onMenuDrag(View v, DragEvent event) {
/* 583 */           return false;
                }

                @Override // com.jeremyfeinstein.slidingmenu.lib.SlidingActivityHelper.MenuStateHandler.MenuStateListener
/* 588 */       public void onMenuClosed() {
                }

                @Override // com.jeremyfeinstein.slidingmenu.lib.SlidingActivityHelper.MenuStateHandler.MenuStateListener
/* 593 */       public void onMenuClose() {
                }

                @Override // com.jeremyfeinstein.slidingmenu.lib.SlidingActivityHelper.MenuStateHandler.MenuStateListener
/* 598 */       public void onMenuOpened() {
                }

                @Override // com.jeremyfeinstein.slidingmenu.lib.SlidingActivityHelper.MenuStateHandler.MenuStateListener
/* 603 */       public void onMenuOpen() {
                }

                @Override // com.jeremyfeinstein.slidingmenu.lib.SlidingActivityHelper.MenuStateHandler.MenuStateListener
/* 608 */       public void onMenuClick(View v) {
                }

                @Nullable
/* 612 */       protected View getWindowDecorView() {
/* 613 */           View decor = getWindow().getDecorView();
/* 614 */           if (decor instanceof ViewGroup) {
/* 617 */               return decor;
                    }
                    return null;
                }
            }
