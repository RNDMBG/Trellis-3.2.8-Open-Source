            package co.vine.android.widget;

            import android.content.Context;
            import android.content.res.Resources;
            import android.content.res.TypedArray;
            import android.graphics.PorterDuff;
            import android.util.AttributeSet;
            import android.widget.Button;
            import co.vine.android.R;
            import co.vine.android.Settings;
            import co.vine.android.api.VineUser;
            
            
    public class FollowStateButton extends Button {
                public static final int STATE_FOLLOWING = 3;
                public static final int STATE_FOLLOW_REQUESTED = 4;
                public static final int STATE_NOT_FOLLOWING = 2;
                public static final int STATE_SELF = 1;
                private int mColor;
                private int mFollowRequestedDrawableRes;
                private int mFollowRequestedText;
                private int mFollowRequestedTextColor;
                private int mFollowingDrawableRes;
                private int mFollowingText;
                private int mFollowingTextColor;
                private int mNotFollowingDrawableRes;
                private int mNotFollowingText;
                private int mNotFollowingTextColor;
                private int mSelfDrawableRes;
                private int mSelfText;
                private int mSelfTextColor;
                private int mState;

        public FollowStateButton(Context context) {
            this(context, null);
                }

        public FollowStateButton(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
                }

        public FollowStateButton(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FollowStateButton, defStyle, 0);
            this.mSelfDrawableRes = a.getResourceId(R.styleable.FollowStateButton_selfDrawable, R.drawable.btn_profile_settings);
            this.mNotFollowingDrawableRes = a.getResourceId(R.styleable.FollowStateButton_notFollowingDrawable, R.drawable.btn_profile_follow);
            this.mFollowingDrawableRes = a.getResourceId(R.styleable.FollowStateButton_followingDrawable, R.drawable.btn_profile_following);
            this.mFollowRequestedDrawableRes = a.getResourceId(R.styleable.FollowStateButton_followRequestedDrawable, R.drawable.btn_profile_pending);
            this.mSelfText = a.getResourceId(R.styleable.FollowStateButton_selfString, R.string.settings);
            this.mNotFollowingText = a.getResourceId(R.styleable.FollowStateButton_notFollowingString, R.string.follow);
            this.mFollowingText = a.getResourceId(R.styleable.FollowStateButton_followingString, R.string.following);
            this.mFollowRequestedText = a.getResourceId(R.styleable.FollowStateButton_followRequestedString, R.string.follow_requested);
            Resources res = getResources();
            this.mSelfTextColor = a.getColor(R.styleable.FollowStateButton_selfTextColor, res.getColor(R.color.settings_btn_text));
            this.mNotFollowingTextColor = a.getColor(R.styleable.FollowStateButton_notFollowingTextColor, res.getColor(R.color.not_following_btn_text));
            this.mFollowingTextColor = a.getColor(R.styleable.FollowStateButton_notFollowingTextColor, res.getColor(R.color.following_btn_text));
            this.mFollowRequestedTextColor = a.getColor(R.styleable.FollowStateButton_followRequestedTextColor, res.getColor(R.color.not_following_btn_text));
            this.mColor = res.getColor(R.color.vine_green);
            a.recycle();
            setPadding(getResources().getDimensionPixelSize(R.dimen.follow_request_left_padding), 0, 0, 0);
            invalidate();
                }

        public void setUser(long ownerId, VineUser user) {
            this.mColor = user.profileBackground;
            if (this.mColor == Settings.DEFAULT_PROFILE_COLOR || this.mColor <= 0) {
               this.mColor = getResources().getColor(R.color.vine_green);
                    }
           this.mColor |= -16777216;
                    if (ownerId == user.userId) {
               setState(1);
                    } else if (user.hasFollowRequested()) {
               setState(4);
                    } else if (user.isFollowing()) {
               setState(3);
                    } else {
               setState(2);
                    }
           setColors();
           invalidate();
                }

       private void setColors() {
           int color = this.mColor;
           this.mFollowingTextColor = -1;
           this.mNotFollowingTextColor = color;
           this.mFollowRequestedTextColor = color;
           if (getBackground() != null && this.mState != 1) {
               getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                    }
                }

       public int getState() {
           return this.mState;
                }

       public void follow(VineUser user) {
           if (user.isPrivate()) {
               if (this.mState != 4) {
                   setState(4);
                   invalidate();
                   return;
                        }
               return;
                    }
           if (this.mState != 3) {
               setState(3);
               invalidate();
                    }
                }

       public void unfollow() {
           if (this.mState != 2) {
               setState(2);
               invalidate();
                    }
                }

       private void setState(int state) {
                    switch (state) {
                        case 1:
                   setBackgroundResource(this.mSelfDrawableRes);
                   setText(this.mSelfText);
                   setTextColor(this.mSelfTextColor);
                   this.mState = 1;
                            break;
                        case 2:
                   setBackgroundResource(this.mNotFollowingDrawableRes);
                   setText(this.mNotFollowingText);
                   setTextColor(this.mNotFollowingTextColor);
                   this.mState = 2;
                            break;
                        case 3:
                   setBackgroundResource(this.mFollowingDrawableRes);
                   setText(this.mFollowingText);
                   setTextColor(this.mFollowingTextColor);
                   this.mState = 3;
                            break;
                        case 4:
                   setBackgroundResource(this.mFollowRequestedDrawableRes);
                   setText(this.mFollowRequestedText);
                   setTextColor(this.mFollowRequestedTextColor);
                            break;
                    }
           setColors();
                }
            }
