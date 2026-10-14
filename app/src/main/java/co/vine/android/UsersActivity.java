            package co.vine.android;

            import android.content.Context;
            import android.content.Intent;
            import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.graphics.Rect;
import android.view.TouchDelegate;
import android.widget.ImageButton;
import android.view.View;
import android.view.ViewGroup;
import android.view.Gravity;
            import android.widget.ImageView;
            import android.widget.FrameLayout;
            import android.widget.LinearLayout;
            import android.widget.RelativeLayout;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    public class UsersActivity extends BaseControllerActionBarActivity {
                public static final String EXTRA_ANCHOR = "anchor";
                public static final String EXTRA_NOTIFICATION_ID = "notification_id";
                public static final String EXTRA_POST_ID = "post_id";
                public static final String EXTRA_USERS_TYPE = "u_type";
                public static final String EXTRA_USER_ID = "p_id";
                private UsersFragment mFragment;

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 25 */        protected void onCreate(Bundle savedInstanceState) {
/* 26 */            super.onCreate(savedInstanceState, C0257R.layout.user_list, true);
/* 28 */            Intent intent = getIntent();
/* 30 */            setupActionBar((Boolean) true, (Boolean) true, (String) null, (Boolean) true, (Boolean) true);
/* 32 */            ActionBar ab = getSupportActionBar();
/* 34 */            int type = intent.getIntExtra("u_type", 0);
                    switch (type) {
                        case 1:
/* 38 */                    ab.setTitle(C0257R.string.user_following);
                            break;
                        case 2:
/* 42 */                    ab.setTitle(C0257R.string.user_followers);
                            break;
                        case 5:
/* 46 */                    ab.setTitle(C0257R.string.user_likers);
                            break;
                        case 9:
/* 50 */                    ab.setTitle(C0257R.string.revines_title);
                            break;
                        case 12:
/* 54 */                    ab.setTitle("");
                            break;
                    }
/* 59 */            if (savedInstanceState == null) {
/* 60 */                this.mFragment = new UsersFragment();
/* 61 */                Bundle b = UsersFragment.prepareArguments(intent, false);
/* 62 */                b.putBoolean("refresh", true);
/* 63 */                this.mFragment.setArguments(b);
/* 64 */                getSupportFragmentManager().beginTransaction().add(C0257R.id.fragment_container, this.mFragment).commit();
                    }
                    getWindow().getDecorView().post(new Runnable() {
                        @Override
                        public void run() {
                            restoreVineToolbarHeight();
                        }
                    });
                }

                @Override
                protected void onResume() {
                    super.onResume();
                    getWindow().getDecorView().post(new Runnable() {
                        @Override
                        public void run() {
                            restoreVineToolbarHeight();
                        }
                    });
                }

                @Override
                public void onWindowFocusChanged(boolean hasFocus) {
                    super.onWindowFocusChanged(hasFocus);
                    if (hasFocus) {
                        restoreVineToolbarHeight();
                    }
                }

                /** Keep the followers/following toolbar aligned with the other Vine screens (On The Rise = 48dp). */
                private void restoreVineToolbarHeight() {
                    int actionBarId = getResources().getIdentifier("action_bar", "id", getPackageName());
                    View actionBarView = findViewById(actionBarId);
                    float density = getResources().getDisplayMetrics().density;
                    int height = (int) (48.0f * density + 0.5f);
                    if (actionBarView != null) {
                        ViewGroup.LayoutParams params = actionBarView.getLayoutParams();
                        if (params != null) {
                            params.height = height;
                            actionBarView.setLayoutParams(params);
                        }
                        actionBarView.setMinimumHeight(height);
                        ViewGroup.LayoutParams lp = actionBarView.getLayoutParams();
                        if (lp != null) {
                            lp.height = height;
                            actionBarView.setLayoutParams(lp);
                        }
                    }
                    int containerId = getResources().getIdentifier("action_bar_container", "id", getPackageName());
                    View container = findViewById(containerId);
                    if (container != null) {
                        ViewGroup.LayoutParams containerParams = container.getLayoutParams();
                        if (containerParams != null) {
                            containerParams.height = height;
                            container.setLayoutParams(containerParams);
                        }
                        container.setMinimumHeight(height);
                    }
                    // AppCompat inflates the up button late; re-apply at two delays.
                    for (long delay : new long[]{250L, 800L}) {
                        final long d = delay;
                        getWindow().getDecorView().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                centerVineBackButton(getWindow().getDecorView());
                            }
                        }, d);
                    }
                }

                private void centerVineBackButton(View view) {
                    if (isActionBarUpView(view)) {
                        final ImageView image = (ImageView) view;
                        // Fixed 24dp square everywhere to match base style.
                        image.setScaleType(ImageView.ScaleType.CENTER);
                        image.setPadding(0, 0, 0, 0);
                        float upDensity = getResources().getDisplayMetrics().density;
                        int upSize = (int) (24.0f * upDensity + 0.5f);
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
                        image.setTranslationY(0.0f);
                        // Center against the 48dp action bar after layout.
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
                                // Enlarge touch area without moving the drawable:
                                // delegate a 56dp x 48dp rect to the small < icon.
                                View parent = (View) image.getParent();
                                if (parent != null) {
                                    float density = getResources().getDisplayMetrics().density;
                                    final int expandW = (int) (20.0f * density + 0.5f);
                                    final int expandH = (int) (12.0f * density + 0.5f);
                                    final View delegateParent = parent;
                                    final View delegateTarget = image;
                                    delegateParent.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Rect rect = new Rect();
                                            delegateTarget.getHitRect(rect);
                                            rect.left -= expandW;
                                            rect.right += expandW;
                                            rect.top -= expandH;
                                            rect.bottom += expandH;
                                            delegateParent.setTouchDelegate(new TouchDelegate(rect, delegateTarget));
                                        }
                                    });
                                }
                            }
                        });
                        return;
                    }
                    if (view instanceof ViewGroup) {
                        ViewGroup group = (ViewGroup) view;
                        for (int i = 0; i < group.getChildCount(); i++) {
                            centerVineBackButton(group.getChildAt(i));
                        }
                    }
                }

                private boolean isActionBarUpView(View view) {
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

/* 71 */        public static void startUserListForNotification(Context context, long notificationId, long anchor) {
/* 72 */            Intent i = new Intent(context, (Class<?>) UsersActivity.class);
/* 73 */            i.putExtra("u_type", 12);
/* 74 */            i.putExtra("notification_id", notificationId);
/* 75 */            i.putExtra("anchor", anchor);
/* 76 */            context.startActivity(i);
                }

/* 79 */        public static void startUserListForReviners(Context context, long postId) {
/* 80 */            Intent i = new Intent(context, (Class<?>) UsersActivity.class);
/* 81 */            i.putExtra("post_id", postId);
/* 82 */            i.putExtra("u_type", 9);
/* 83 */            context.startActivity(i);
                }

/* 86 */        public static void startUserListForLikers(Context context, long postId) {
/* 87 */            Intent i = new Intent(context, (Class<?>) UsersActivity.class);
/* 88 */            i.putExtra("post_id", postId);
/* 89 */            i.putExtra("u_type", 5);
/* 90 */            context.startActivity(i);
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 94 */        public void onActivityResult(int requestCode, int resultCode, Intent data) {
/* 95 */            this.mFragment.onActivityResult(requestCode, resultCode, data);
                }
            }
