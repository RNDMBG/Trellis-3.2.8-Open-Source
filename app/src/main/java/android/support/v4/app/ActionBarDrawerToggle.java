            package android.support.v4.app;

            import android.app.Activity;
            import android.content.res.Configuration;
            import android.graphics.Canvas;
            import android.graphics.Rect;
            import android.graphics.drawable.Drawable;
            import android.graphics.drawable.InsetDrawable;
            import android.os.Build;
            import android.support.v4.view.GravityCompat;
            import android.support.v4.view.ViewCompat;
            import android.support.v4.widget.DrawerLayout;
            import android.view.MenuItem;
            import android.view.View;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 53 */    public class ActionBarDrawerToggle implements DrawerLayout.DrawerListener {
                private static final int ID_HOME = 16908332;
                private static final ActionBarDrawerToggleImpl IMPL;
                private static final float TOGGLE_DRAWABLE_OFFSET = 0.33333334f;
                private final Activity mActivity;
                private final Delegate mActivityImpl;
                private final int mCloseDrawerContentDescRes;
                private Drawable mDrawerImage;
                private final int mDrawerImageResource;
                private boolean mDrawerIndicatorEnabled = true;
                private final DrawerLayout mDrawerLayout;
                private final int mOpenDrawerContentDescRes;
                private Object mSetIndicatorInfo;
                private SlideDrawable mSlider;
                private Drawable mThemeImage;

                private interface ActionBarDrawerToggleImpl {
                    Drawable getThemeUpIndicator(Activity activity);

                    Object setActionBarDescription(Object obj, Activity activity, int i);

                    Object setActionBarUpIndicator(Object obj, Activity activity, Drawable drawable, int i);
                }

                public interface Delegate {
                    Drawable getThemeUpIndicator();

                    void setActionBarDescription(int i);

                    void setActionBarUpIndicator(Drawable drawable, int i);
                }

                public interface DelegateProvider {
                    Delegate getDrawerToggleDelegate();
                }

/* 54 */        static /* synthetic */ Activity access$400(ActionBarDrawerToggle x0) {
/* 55 */            return x0.mActivity;
                }

/* 98 */        private static class ActionBarDrawerToggleImplBase implements ActionBarDrawerToggleImpl {
/* 99 */            private ActionBarDrawerToggleImplBase() {
                    }

/* 99 */            /* synthetic */ ActionBarDrawerToggleImplBase(C00021 x0) {
/* 100 */               this();
                    }

                    @Override // android.support.v4.app.ActionBarDrawerToggle.ActionBarDrawerToggleImpl
/* 102 */           public Drawable getThemeUpIndicator(Activity activity) {
/* 103 */               return null;
                    }

                    @Override // android.support.v4.app.ActionBarDrawerToggle.ActionBarDrawerToggleImpl
/* 109 */           public Object setActionBarUpIndicator(Object info, Activity activity, Drawable themeImage, int contentDescRes) {
/* 110 */               return info;
                    }

                    @Override // android.support.v4.app.ActionBarDrawerToggle.ActionBarDrawerToggleImpl
/* 115 */           public Object setActionBarDescription(Object info, Activity activity, int contentDescRes) {
/* 116 */               return info;
                    }
                }

/* 118 */       private static class ActionBarDrawerToggleImplHC implements ActionBarDrawerToggleImpl {
/* 119 */           private ActionBarDrawerToggleImplHC() {
                    }

/* 119 */           /* synthetic */ ActionBarDrawerToggleImplHC(C00021 x0) {
/* 120 */               this();
                    }

                    @Override // android.support.v4.app.ActionBarDrawerToggle.ActionBarDrawerToggleImpl
/* 122 */           public Drawable getThemeUpIndicator(Activity activity) {
/* 123 */               return ActionBarDrawerToggleHoneycomb.getThemeUpIndicator(activity);
                    }

                    @Override // android.support.v4.app.ActionBarDrawerToggle.ActionBarDrawerToggleImpl
/* 128 */           public Object setActionBarUpIndicator(Object info, Activity activity, Drawable themeImage, int contentDescRes) {
/* 129 */               return ActionBarDrawerToggleHoneycomb.setActionBarUpIndicator(info, activity, themeImage, contentDescRes);
                    }

                    @Override // android.support.v4.app.ActionBarDrawerToggle.ActionBarDrawerToggleImpl
/* 134 */           public Object setActionBarDescription(Object info, Activity activity, int contentDescRes) {
/* 135 */               return ActionBarDrawerToggleHoneycomb.setActionBarDescription(info, activity, contentDescRes);
                    }
                }

/* 138 */       private static class ActionBarDrawerToggleImplJellybeanMR2 implements ActionBarDrawerToggleImpl {
/* 139 */           private ActionBarDrawerToggleImplJellybeanMR2() {
                    }

/* 139 */           /* synthetic */ ActionBarDrawerToggleImplJellybeanMR2(C00021 x0) {
/* 140 */               this();
                    }

                    @Override // android.support.v4.app.ActionBarDrawerToggle.ActionBarDrawerToggleImpl
/* 143 */           public Drawable getThemeUpIndicator(Activity activity) {
/* 144 */               return ActionBarDrawerToggleJellybeanMR2.getThemeUpIndicator(activity);
                    }

                    @Override // android.support.v4.app.ActionBarDrawerToggle.ActionBarDrawerToggleImpl
/* 149 */           public Object setActionBarUpIndicator(Object info, Activity activity, Drawable themeImage, int contentDescRes) {
/* 150 */               return ActionBarDrawerToggleJellybeanMR2.setActionBarUpIndicator(info, activity, themeImage, contentDescRes);
                    }

                    @Override // android.support.v4.app.ActionBarDrawerToggle.ActionBarDrawerToggleImpl
/* 155 */           public Object setActionBarDescription(Object info, Activity activity, int contentDescRes) {
/* 156 */               return ActionBarDrawerToggleJellybeanMR2.setActionBarDescription(info, activity, contentDescRes);
                    }
                }

                static {
/* 164 */           int version = Build.VERSION.SDK_INT;
/* 165 */           if (version >= 18) {
/* 166 */               IMPL = new ActionBarDrawerToggleImplJellybeanMR2(null);
                    } else if (version >= 11) {
/* 168 */               IMPL = new ActionBarDrawerToggleImplHC(null);
                    } else {
/* 170 */               IMPL = new ActionBarDrawerToggleImplBase(null);
                    }
                }

                /* JADX WARN: Multi-variable type inference failed */
/* 213 */       public ActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, int drawerImageRes, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
/* 215 */           this.mActivity = activity;
/* 218 */           if (activity instanceof DelegateProvider) {
/* 219 */               this.mActivityImpl = ((DelegateProvider) activity).getDrawerToggleDelegate();
                    } else {
/* 221 */               this.mActivityImpl = null;
                    }
/* 224 */           this.mDrawerLayout = drawerLayout;
/* 225 */           this.mDrawerImageResource = drawerImageRes;
/* 226 */           this.mOpenDrawerContentDescRes = openDrawerContentDescRes;
/* 227 */           this.mCloseDrawerContentDescRes = closeDrawerContentDescRes;
/* 229 */           this.mThemeImage = getThemeUpIndicator();
/* 230 */           this.mDrawerImage = activity.getResources().getDrawable(drawerImageRes);
/* 231 */           this.mSlider = new SlideDrawable(this, this.mDrawerImage, null);
/* 232 */           this.mSlider.setOffset(TOGGLE_DRAWABLE_OFFSET);
                }

/* 244 */       public void syncState() {
/* 245 */           if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
/* 246 */               this.mSlider.setPosition(1.0f);
                    } else {
/* 248 */               this.mSlider.setPosition(0.0f);
                    }
/* 251 */           if (this.mDrawerIndicatorEnabled) {
/* 252 */               setActionBarUpIndicator(this.mSlider, this.mDrawerLayout.isDrawerOpen(GravityCompat.START) ? this.mCloseDrawerContentDescRes : this.mOpenDrawerContentDescRes);
                    }
                }

/* 267 */       public void setDrawerIndicatorEnabled(boolean enable) {
/* 268 */           if (enable != this.mDrawerIndicatorEnabled) {
/* 269 */               if (enable) {
/* 270 */                   setActionBarUpIndicator(this.mSlider, this.mDrawerLayout.isDrawerOpen(GravityCompat.START) ? this.mCloseDrawerContentDescRes : this.mOpenDrawerContentDescRes);
                        } else {
/* 273 */                   setActionBarUpIndicator(this.mThemeImage, 0);
                        }
/* 275 */               this.mDrawerIndicatorEnabled = enable;
                    }
                }

/* 283 */       public boolean isDrawerIndicatorEnabled() {
/* 284 */           return this.mDrawerIndicatorEnabled;
                }

/* 295 */       public void onConfigurationChanged(Configuration newConfig) {
/* 296 */           this.mThemeImage = getThemeUpIndicator();
/* 297 */           this.mDrawerImage = this.mActivity.getResources().getDrawable(this.mDrawerImageResource);
/* 298 */           syncState();
                }

/* 310 */       public boolean onOptionsItemSelected(MenuItem item) {
/* 311 */           if (item != null && item.getItemId() == 16908332 && this.mDrawerIndicatorEnabled) {
/* 312 */               if (this.mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
/* 313 */                   this.mDrawerLayout.closeDrawer(GravityCompat.START);
                        } else {
/* 315 */                   this.mDrawerLayout.openDrawer(GravityCompat.START);
                        }
/* 317 */               return true;
                    }
/* 319 */           return false;
                }

                @Override // android.support.v4.widget.DrawerLayout.DrawerListener
/* 331 */       public void onDrawerSlide(View drawerView, float slideOffset) {
                    float glyphOffset;
/* 332 */           float glyphOffset2 = this.mSlider.getPosition();
                    if (slideOffset > 0.5f) {
/* 334 */               glyphOffset = Math.max(glyphOffset2, Math.max(0.0f, slideOffset - 0.5f) * 2.0f);
                    } else {
/* 336 */               glyphOffset = Math.min(glyphOffset2, slideOffset * 2.0f);
                    }
/* 338 */           this.mSlider.setPosition(glyphOffset);
                }

                @Override // android.support.v4.widget.DrawerLayout.DrawerListener
/* 349 */       public void onDrawerOpened(View drawerView) {
/* 350 */           this.mSlider.setPosition(1.0f);
/* 351 */           if (this.mDrawerIndicatorEnabled) {
/* 352 */               setActionBarDescription(this.mCloseDrawerContentDescRes);
                    }
                }

                @Override // android.support.v4.widget.DrawerLayout.DrawerListener
/* 364 */       public void onDrawerClosed(View drawerView) {
/* 365 */           this.mSlider.setPosition(0.0f);
/* 366 */           if (this.mDrawerIndicatorEnabled) {
/* 367 */               setActionBarDescription(this.mOpenDrawerContentDescRes);
                    }
                }

                @Override // android.support.v4.widget.DrawerLayout.DrawerListener
/* 379 */       public void onDrawerStateChanged(int newState) {
                }

/* 382 */       Drawable getThemeUpIndicator() {
                    return this.mActivityImpl != null ? this.mActivityImpl.getThemeUpIndicator() : IMPL.getThemeUpIndicator(this.mActivity);
                }

/* 389 */       void setActionBarUpIndicator(Drawable upDrawable, int contentDescRes) {
/* 390 */           if (this.mActivityImpl != null) {
/* 391 */               this.mActivityImpl.setActionBarUpIndicator(upDrawable, contentDescRes);
                    } else {
/* 394 */               this.mSetIndicatorInfo = IMPL.setActionBarUpIndicator(this.mSetIndicatorInfo, this.mActivity, upDrawable, contentDescRes);
                    }
                }

/* 398 */       void setActionBarDescription(int contentDescRes) {
/* 399 */           if (this.mActivityImpl != null) {
/* 400 */               this.mActivityImpl.setActionBarDescription(contentDescRes);
                    } else {
/* 403 */               this.mSetIndicatorInfo = IMPL.setActionBarDescription(this.mSetIndicatorInfo, this.mActivity, contentDescRes);
                    }
                }

/* 405 */       private class SlideDrawable extends InsetDrawable implements Drawable.Callback {
                    private final boolean mHasMirroring;
                    private float mOffset;
                    private float mPosition;
                    private final Rect mTmpRect;

/* 406 */           /* synthetic */ SlideDrawable(ActionBarDrawerToggle x0, Drawable x1, C00021 x2) {
/* 407 */               this(x1);
                    }

/* 413 */           private SlideDrawable(Drawable wrapped) {
/* 415 */               super(wrapped, 0);
/* 408 */               this.mHasMirroring = Build.VERSION.SDK_INT > 18;
/* 409 */               this.mTmpRect = new Rect();
                    }

/* 423 */           public void setPosition(float position) {
/* 424 */               this.mPosition = position;
/* 425 */               invalidateSelf();
                    }

/* 428 */           public float getPosition() {
/* 429 */               return this.mPosition;
                    }

/* 439 */           public void setOffset(float offset) {
/* 440 */               this.mOffset = offset;
/* 441 */               invalidateSelf();
                    }

                    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
/* 445 */           public void draw(Canvas canvas) {
/* 446 */               copyBounds(this.mTmpRect);
/* 447 */               canvas.save();
/* 450 */               boolean isLayoutRTL = ViewCompat.getLayoutDirection(ActionBarDrawerToggle.access$400(ActionBarDrawerToggle.this).getWindow().getDecorView()) == 1;
/* 452 */               int flipRtl = isLayoutRTL ? -1 : 1;
/* 453 */               int width = this.mTmpRect.width();
/* 454 */               canvas.translate((-this.mOffset) * width * this.mPosition * flipRtl, 0.0f);
/* 457 */               if (isLayoutRTL && !this.mHasMirroring) {
/* 458 */                   canvas.translate(width, 0.0f);
/* 459 */                   canvas.scale(-1.0f, 1.0f);
                        }
/* 462 */               super.draw(canvas);
/* 463 */               canvas.restore();
                    }
                }
            }
