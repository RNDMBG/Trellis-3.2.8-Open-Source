            package co.vine.android.util;

            import android.R;
            import android.annotation.TargetApi;
            import android.app.Activity;
            import android.content.Context;
            import android.content.res.Resources;
            import android.content.res.TypedArray;
            import android.graphics.Bitmap;
            import android.graphics.PorterDuff;
            import android.graphics.drawable.BitmapDrawable;
            import android.graphics.drawable.Drawable;
            import android.os.Build;
            import android.support.v7.app.ActionBar;
            import android.support.v7.app.ActionBarActivity;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.ViewTreeObserver;
            import android.view.animation.Animation;
            import android.view.animation.Transformation;
            import android.widget.ImageView;
            import android.widget.TextView;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 32 */    public class ViewUtil {
                private static int sActionBarHeight = -1;
                public static final View.OnClickListener EMPTY_VIEW_CLICK_LISTENER = new ViewOnClickListenerC05331();

                public enum ResizeAnimationType {
                    EXPAND_WIDTH,
                    EXPAND_HEIGHT,
                    COLLAPSE_WIDTH,
                    COLLAPSE_HEIGHT
                }

                /* JADX INFO: renamed from: co.vine.android.util.ViewUtil$1 */
/* 33 */        static class ViewOnClickListenerC05331 implements View.OnClickListener {
/* 34 */            ViewOnClickListenerC05331() {
                    }

                    @Override // android.view.View.OnClickListener
/* 34 */            public void onClick(View v) {
                    }
                }

/* 40 */        public static int getActionBarHeight(Context context) {
/* 41 */            if (sActionBarHeight < 0) {
/* 42 */                TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(new int[]{R.attr.actionBarSize});
/* 44 */                sActionBarHeight = (int) styledAttributes.getDimension(0, -1.0f);
/* 45 */                styledAttributes.recycle();
                    }
/* 47 */            return sActionBarHeight;
                }

/* 53 */        public static float getFontScale(Context context) {
/* 54 */            return context.getResources().getConfiguration().fontScale;
                }

/* 64 */        public static void reduceTextSizeViaFontScaleIfNeeded(Context context, float maxScaleToStart, float maxStepDown, TextView... textViews) {
/* 65 */            float fontScale = getFontScale(context);
                    if (fontScale > maxScaleToStart) {
                        for (TextView textView : textViews) {
/* 68 */                    stepDownViaFontScale(textView, fontScale, maxStepDown);
                        }
                    }
                }

/* 79 */        public static void stepDownViaFontScale(TextView textView, float fontScale, float maxStepDown) {
/* 80 */            float size = textView.getTextSize();
/* 82 */            textView.setTextSize(0, (float) (((double) size) - (((double) maxStepDown) * (1.0d - (1.5d - ((double) fontScale))))));
                }

/* 88 */        public static int getPecentageColor(float percent, int color) {
/* 89 */            return (((int) (255.0f * percent)) << 24) | color;
                }

/* 95 */        public static void setActionBarHeight(ActionBarActivity actionBarActivity, View viewToSet) {
/* 96 */            int height = getActionBarHeight(actionBarActivity);
/* 97 */            if (height > 0) {
/* 98 */                viewToSet.getLayoutParams().height = height;
                    } else {
/* 100 */               setActionBarHeightWhenReady(actionBarActivity, viewToSet);
                    }
                }

/* 104 */       public static void setActionBarHeightWhenReady(ActionBarActivity actionBarActivity, View viewToSet) {
/* 105 */           ViewTreeObserver observer = viewToSet.getViewTreeObserver();
/* 106 */           if (observer != null) {
/* 107 */               observer.addOnGlobalLayoutListener(new ViewTreeObserverOnGlobalLayoutListenerC05342(actionBarActivity, viewToSet, observer));
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.util.ViewUtil$2 */
/* 105 */       static class ViewTreeObserverOnGlobalLayoutListenerC05342 implements ViewTreeObserver.OnGlobalLayoutListener {
                    final /* synthetic */ ActionBarActivity val$actionBarActivity;
                    final /* synthetic */ ViewTreeObserver val$observer;
                    final /* synthetic */ View val$viewToSet;

/* 106 */           ViewTreeObserverOnGlobalLayoutListenerC05342(ActionBarActivity actionBarActivity, View view, ViewTreeObserver viewTreeObserver) {
/* 107 */               this.val$actionBarActivity = actionBarActivity;
                        this.val$viewToSet = view;
                        this.val$observer = viewTreeObserver;
                    }

                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
/* 109 */           public void onGlobalLayout() {
                        int height;
/* 110 */               ActionBar ab = this.val$actionBarActivity.getSupportActionBar();
/* 111 */               if (ab != null && (height = ab.getHeight()) > 0) {
/* 114 */                   this.val$viewToSet.getLayoutParams().height = height;
/* 115 */                   if (Build.VERSION.SDK_INT < 16) {
/* 116 */                       this.val$observer.removeGlobalOnLayoutListener(this);
                            } else {
/* 118 */                       this.val$observer.removeOnGlobalLayoutListener(this);
                            }
                        }
                    }
                }

/* 128 */       public static void setBackground(View view, Drawable drawable) {
/* 129 */           if (Build.VERSION.SDK_INT >= 16) {
/* 130 */               view.setBackground(drawable);
                    } else {
/* 132 */               view.setBackgroundDrawable(drawable);
                    }
                }

/* 136 */       public static void setBackground(Resources res, View view, Bitmap bitmap) {
/* 137 */           BitmapDrawable d = new BitmapDrawable(res, bitmap);
/* 138 */           setBackground(view, d);
                }

/* 141 */       public static void fillColor(Resources res, int color, int resId, View v) {
/* 142 */           if (v instanceof ImageView) {
/* 143 */               ((ImageView) v).setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
/* 149 */               return;
                    }
/* 145 */           Drawable d = res.getDrawable(resId);
/* 146 */           d.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
/* 147 */           setBackground(v, d);
                }

/* 159 */       public static Animation makeResizeAnimation(ResizeAnimationType type, View v, int targetSize) {
/* 160 */           return makeResizeAnimation(type, v, targetSize, -1, null);
                }

/* 164 */       public static Animation makeResizeAnimation(ResizeAnimationType type, View v, int targetSize, int duration) {
/* 165 */           return makeResizeAnimation(type, v, targetSize, duration, null);
                }

/* 171 */       public static Animation makeResizeAnimation(ResizeAnimationType type, View v, int targetSize, int duration, Animation.AnimationListener listener) {
                    Animation animation;
/* 172 */           ViewGroup.LayoutParams params = v.getLayoutParams();
/* 173 */           boolean typeWidth = type == ResizeAnimationType.EXPAND_WIDTH || type == ResizeAnimationType.COLLAPSE_WIDTH;
/* 175 */           boolean typeExpand = type == ResizeAnimationType.EXPAND_WIDTH || type == ResizeAnimationType.EXPAND_HEIGHT;
/* 177 */           if (typeExpand) {
/* 178 */               if (typeWidth) {
/* 179 */                   params.width = 0;
                        } else {
/* 181 */                   params.height = 0;
                        }
/* 183 */               v.setVisibility(0);
/* 184 */               animation = new C05353(typeWidth, params, targetSize, v);
                    } else {
/* 203 */               int initialSize = typeWidth ? v.getMeasuredWidth() : v.getMeasuredHeight();
/* 204 */               animation = new C05364(v, typeWidth, params, initialSize);
                    }
/* 226 */           if (duration > 0) {
/* 227 */               animation.setDuration(duration);
                    }
/* 229 */           animation.setAnimationListener(listener);
/* 230 */           return animation;
                }

                /* JADX INFO: renamed from: co.vine.android.util.ViewUtil$3 */
/* 182 */       static class C05353 extends Animation {
                    final /* synthetic */ ViewGroup.LayoutParams val$params;
                    final /* synthetic */ int val$targetSize;
                    final /* synthetic */ boolean val$typeWidth;
                    final /* synthetic */ View val$v;

/* 183 */           C05353(boolean z, ViewGroup.LayoutParams layoutParams, int i, View view) {
/* 184 */               this.val$typeWidth = z;
                        this.val$params = layoutParams;
                        this.val$targetSize = i;
                        this.val$v = view;
                    }

                    @Override // android.view.animation.Animation
/* 186 */           protected void applyTransformation(float interpolatedTime, Transformation t) {
/* 187 */               if (this.val$typeWidth) {
/* 188 */                   this.val$params.width = interpolatedTime == 1.0f ? this.val$targetSize : (int) (this.val$targetSize * interpolatedTime);
                        } else {
/* 191 */                   this.val$params.height = interpolatedTime == 1.0f ? this.val$targetSize : (int) (this.val$targetSize * interpolatedTime);
                        }
/* 194 */               this.val$v.requestLayout();
                    }

                    @Override // android.view.animation.Animation
/* 198 */           public boolean willChangeBounds() {
/* 199 */               return true;
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.util.ViewUtil$4 */
/* 202 */       static class C05364 extends Animation {
                    final /* synthetic */ int val$initialSize;
                    final /* synthetic */ ViewGroup.LayoutParams val$params;
                    final /* synthetic */ boolean val$typeWidth;
                    final /* synthetic */ View val$v;

/* 203 */           C05364(View view, boolean z, ViewGroup.LayoutParams layoutParams, int i) {
/* 204 */               this.val$v = view;
                        this.val$typeWidth = z;
                        this.val$params = layoutParams;
                        this.val$initialSize = i;
                    }

                    @Override // android.view.animation.Animation
/* 206 */           protected void applyTransformation(float interpolatedTime, Transformation t) {
                        if (interpolatedTime == 1.0f) {
/* 208 */                   this.val$v.setVisibility(8);
/* 217 */                   return;
                        }
/* 210 */               if (this.val$typeWidth) {
/* 211 */                   this.val$params.width = this.val$initialSize - ((int) (this.val$initialSize * interpolatedTime));
                        } else {
/* 213 */                   this.val$params.height = this.val$initialSize - ((int) (this.val$initialSize * interpolatedTime));
                        }
/* 215 */               this.val$v.requestLayout();
                    }

                    @Override // android.view.animation.Animation
/* 220 */           public boolean willChangeBounds() {
/* 221 */               return true;
                    }
                }

                @TargetApi(14)
/* 234 */       public static void setLowProfileSystemUiMode(Activity activity) {
/* 235 */           activity.getWindow().getDecorView().setSystemUiVisibility(1);
                }
            }
