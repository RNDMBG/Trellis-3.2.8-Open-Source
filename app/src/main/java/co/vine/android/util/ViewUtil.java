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
            
            
    public class ViewUtil {
                private static int sActionBarHeight = -1;
                public static final View.OnClickListener EMPTY_VIEW_CLICK_LISTENER = new ViewOnClickListenerC05331();

                public enum ResizeAnimationType {
                    EXPAND_WIDTH,
                    EXPAND_HEIGHT,
                    COLLAPSE_WIDTH,
                    COLLAPSE_HEIGHT
                }

                
        static class ViewOnClickListenerC05331 implements View.OnClickListener {
            ViewOnClickListenerC05331() {
                    }

                    @Override 
            public void onClick(View v) {
                    }
                }

        public static int getActionBarHeight(Context context) {
            if (sActionBarHeight < 0) {
                TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(new int[]{R.attr.actionBarSize});
                sActionBarHeight = (int) styledAttributes.getDimension(0, -1.0f);
                styledAttributes.recycle();
                    }
            return sActionBarHeight;
                }

        public static float getFontScale(Context context) {
            return context.getResources().getConfiguration().fontScale;
                }

        public static void reduceTextSizeViaFontScaleIfNeeded(Context context, float maxScaleToStart, float maxStepDown, TextView... textViews) {
            float fontScale = getFontScale(context);
                    if (fontScale > maxScaleToStart) {
                        for (TextView textView : textViews) {
                    stepDownViaFontScale(textView, fontScale, maxStepDown);
                        }
                    }
                }

        public static void stepDownViaFontScale(TextView textView, float fontScale, float maxStepDown) {
            float size = textView.getTextSize();
            textView.setTextSize(0, (float) (((double) size) - (((double) maxStepDown) * (1.0d - (1.5d - ((double) fontScale))))));
                }

        public static int getPecentageColor(float percent, int color) {
            return (((int) (255.0f * percent)) << 24) | color;
                }

        public static void setActionBarHeight(ActionBarActivity actionBarActivity, View viewToSet) {
            int height = getActionBarHeight(actionBarActivity);
            if (height > 0) {
                viewToSet.getLayoutParams().height = height;
                    } else {
               setActionBarHeightWhenReady(actionBarActivity, viewToSet);
                    }
                }

       public static void setActionBarHeightWhenReady(ActionBarActivity actionBarActivity, View viewToSet) {
           ViewTreeObserver observer = viewToSet.getViewTreeObserver();
           if (observer != null) {
               observer.addOnGlobalLayoutListener(new ViewTreeObserverOnGlobalLayoutListenerC05342(actionBarActivity, viewToSet, observer));
                    }
                }

                
       static class ViewTreeObserverOnGlobalLayoutListenerC05342 implements ViewTreeObserver.OnGlobalLayoutListener {
                    final  ActionBarActivity val$actionBarActivity;
                    final  ViewTreeObserver val$observer;
                    final  View val$viewToSet;

           ViewTreeObserverOnGlobalLayoutListenerC05342(ActionBarActivity actionBarActivity, View view, ViewTreeObserver viewTreeObserver) {
               this.val$actionBarActivity = actionBarActivity;
                        this.val$viewToSet = view;
                        this.val$observer = viewTreeObserver;
                    }

                    @Override 
           public void onGlobalLayout() {
                        int height;
               ActionBar ab = this.val$actionBarActivity.getSupportActionBar();
               if (ab != null && (height = ab.getHeight()) > 0) {
                   this.val$viewToSet.getLayoutParams().height = height;
                   if (Build.VERSION.SDK_INT < 16) {
                       this.val$observer.removeGlobalOnLayoutListener(this);
                            } else {
                       this.val$observer.removeOnGlobalLayoutListener(this);
                            }
                        }
                    }
                }

       public static void setBackground(View view, Drawable drawable) {
           if (Build.VERSION.SDK_INT >= 16) {
               view.setBackground(drawable);
                    } else {
               view.setBackgroundDrawable(drawable);
                    }
                }

       public static void setBackground(Resources res, View view, Bitmap bitmap) {
           BitmapDrawable d = new BitmapDrawable(res, bitmap);
           setBackground(view, d);
                }

       public static void fillColor(Resources res, int color, int resId, View v) {
           if (v instanceof ImageView) {
               ((ImageView) v).setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
               return;
                    }
           Drawable d = res.getDrawable(resId);
           d.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
           setBackground(v, d);
                }

       public static Animation makeResizeAnimation(ResizeAnimationType type, View v, int targetSize) {
           return makeResizeAnimation(type, v, targetSize, -1, null);
                }

       public static Animation makeResizeAnimation(ResizeAnimationType type, View v, int targetSize, int duration) {
           return makeResizeAnimation(type, v, targetSize, duration, null);
                }

       public static Animation makeResizeAnimation(ResizeAnimationType type, View v, int targetSize, int duration, Animation.AnimationListener listener) {
                    Animation animation;
           ViewGroup.LayoutParams params = v.getLayoutParams();
           boolean typeWidth = type == ResizeAnimationType.EXPAND_WIDTH || type == ResizeAnimationType.COLLAPSE_WIDTH;
           boolean typeExpand = type == ResizeAnimationType.EXPAND_WIDTH || type == ResizeAnimationType.EXPAND_HEIGHT;
           if (typeExpand) {
               if (typeWidth) {
                   params.width = 0;
                        } else {
                   params.height = 0;
                        }
               v.setVisibility(0);
               animation = new C05353(typeWidth, params, targetSize, v);
                    } else {
               int initialSize = typeWidth ? v.getMeasuredWidth() : v.getMeasuredHeight();
               animation = new C05364(v, typeWidth, params, initialSize);
                    }
           if (duration > 0) {
               animation.setDuration(duration);
                    }
           animation.setAnimationListener(listener);
           return animation;
                }

                
       static class C05353 extends Animation {
                    final  ViewGroup.LayoutParams val$params;
                    final  int val$targetSize;
                    final  boolean val$typeWidth;
                    final  View val$v;

           C05353(boolean z, ViewGroup.LayoutParams layoutParams, int i, View view) {
               this.val$typeWidth = z;
                        this.val$params = layoutParams;
                        this.val$targetSize = i;
                        this.val$v = view;
                    }

                    @Override 
           protected void applyTransformation(float interpolatedTime, Transformation t) {
               if (this.val$typeWidth) {
                   this.val$params.width = interpolatedTime == 1.0f ? this.val$targetSize : (int) (this.val$targetSize * interpolatedTime);
                        } else {
                   this.val$params.height = interpolatedTime == 1.0f ? this.val$targetSize : (int) (this.val$targetSize * interpolatedTime);
                        }
               this.val$v.requestLayout();
                    }

                    @Override 
           public boolean willChangeBounds() {
               return true;
                    }
                }

                
       static class C05364 extends Animation {
                    final  int val$initialSize;
                    final  ViewGroup.LayoutParams val$params;
                    final  boolean val$typeWidth;
                    final  View val$v;

           C05364(View view, boolean z, ViewGroup.LayoutParams layoutParams, int i) {
               this.val$v = view;
                        this.val$typeWidth = z;
                        this.val$params = layoutParams;
                        this.val$initialSize = i;
                    }

                    @Override 
           protected void applyTransformation(float interpolatedTime, Transformation t) {
                        if (interpolatedTime == 1.0f) {
                   this.val$v.setVisibility(8);
                   return;
                        }
               if (this.val$typeWidth) {
                   this.val$params.width = this.val$initialSize - ((int) (this.val$initialSize * interpolatedTime));
                        } else {
                   this.val$params.height = this.val$initialSize - ((int) (this.val$initialSize * interpolatedTime));
                        }
               this.val$v.requestLayout();
                    }

                    @Override 
           public boolean willChangeBounds() {
               return true;
                    }
                }

                @TargetApi(14)
       public static void setLowProfileSystemUiMode(Activity activity) {
           activity.getWindow().getDecorView().setSystemUiVisibility(1);
                }
            }
