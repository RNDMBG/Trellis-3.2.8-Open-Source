            package co.vine.android.widget;

            import android.content.Context;
            import android.content.res.Resources;
            import android.content.res.TypedArray;
            import android.graphics.PorterDuff;
            import android.util.AttributeSet;
            import android.widget.Button;
            import co.vine.android.C0257R;
            import co.vine.android.Settings;
            import co.vine.android.api.VineUser;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 40 */    public class FollowStateButton extends Button {
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

/* 41 */        public FollowStateButton(Context context) {
/* 42 */            this(context, null);
                }

/* 45 */        public FollowStateButton(Context context, AttributeSet attrs) {
/* 46 */            this(context, attrs, 0);
                }

/* 49 */        public FollowStateButton(Context context, AttributeSet attrs, int defStyle) {
/* 50 */            super(context, attrs, defStyle);
/* 51 */            TypedArray a = context.obtainStyledAttributes(attrs, C0257R.styleable.FollowStateButton, defStyle, 0);
/* 54 */            this.mSelfDrawableRes = a.getResourceId(0, C0257R.drawable.btn_profile_settings);
/* 56 */            this.mNotFollowingDrawableRes = a.getResourceId(3, C0257R.drawable.btn_profile_follow);
/* 59 */            this.mFollowingDrawableRes = a.getResourceId(6, C0257R.drawable.btn_profile_following);
/* 61 */            this.mFollowRequestedDrawableRes = a.getResourceId(9, C0257R.drawable.btn_profile_pending);
/* 64 */            this.mSelfText = a.getResourceId(1, C0257R.string.settings);
/* 66 */            this.mNotFollowingText = a.getResourceId(4, C0257R.string.follow);
/* 68 */            this.mFollowingText = a.getResourceId(7, C0257R.string.following);
/* 70 */            this.mFollowRequestedText = a.getResourceId(10, C0257R.string.follow_requested);
/* 73 */            Resources res = getResources();
/* 75 */            this.mSelfTextColor = a.getColor(2, res.getColor(C0257R.color.settings_btn_text));
/* 77 */            this.mNotFollowingTextColor = a.getColor(5, res.getColor(C0257R.color.not_following_btn_text));
/* 79 */            this.mFollowingTextColor = a.getColor(5, res.getColor(C0257R.color.following_btn_text));
/* 81 */            this.mFollowRequestedTextColor = a.getColor(11, res.getColor(C0257R.color.not_following_btn_text));
/* 84 */            this.mColor = res.getColor(C0257R.color.vine_green);
/* 85 */            a.recycle();
/* 87 */            setPadding(getResources().getDimensionPixelSize(C0257R.dimen.follow_request_left_padding), 0, 0, 0);
/* 88 */            invalidate();
                }

/* 97 */        public void setUser(long ownerId, VineUser user) {
/* 98 */            this.mColor = user.profileBackground;
/* 99 */            if (this.mColor == Settings.DEFAULT_PROFILE_COLOR || this.mColor <= 0) {
/* 100 */               this.mColor = getResources().getColor(C0257R.color.vine_green);
                    }
/* 103 */           this.mColor |= -16777216;
                    if (ownerId == user.userId) {
/* 106 */               setState(1);
                    } else if (user.hasFollowRequested()) {
/* 108 */               setState(4);
                    } else if (user.isFollowing()) {
/* 110 */               setState(3);
                    } else {
/* 112 */               setState(2);
                    }
/* 115 */           setColors();
/* 116 */           invalidate();
                }

/* 122 */       private void setColors() {
/* 123 */           int color = this.mColor;
/* 124 */           this.mFollowingTextColor = -1;
/* 125 */           this.mNotFollowingTextColor = color;
/* 126 */           this.mFollowRequestedTextColor = color;
/* 127 */           if (getBackground() != null && this.mState != 1) {
/* 128 */               getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                    }
                }

/* 132 */       public int getState() {
/* 133 */           return this.mState;
                }

/* 141 */       public void follow(VineUser user) {
/* 142 */           if (user.isPrivate()) {
/* 143 */               if (this.mState != 4) {
/* 144 */                   setState(4);
/* 145 */                   invalidate();
/* 153 */                   return;
                        }
/* 153 */               return;
                    }
/* 148 */           if (this.mState != 3) {
/* 149 */               setState(3);
/* 150 */               invalidate();
                    }
                }

/* 158 */       public void unfollow() {
/* 159 */           if (this.mState != 2) {
/* 160 */               setState(2);
/* 161 */               invalidate();
                    }
                }

/* 170 */       private void setState(int state) {
                    switch (state) {
                        case 1:
/* 173 */                   setBackgroundResource(this.mSelfDrawableRes);
/* 174 */                   setText(this.mSelfText);
/* 175 */                   setTextColor(this.mSelfTextColor);
/* 176 */                   this.mState = 1;
                            break;
                        case 2:
/* 180 */                   setBackgroundResource(this.mNotFollowingDrawableRes);
/* 181 */                   setText(this.mNotFollowingText);
/* 182 */                   setTextColor(this.mNotFollowingTextColor);
/* 183 */                   this.mState = 2;
                            break;
                        case 3:
/* 187 */                   setBackgroundResource(this.mFollowingDrawableRes);
/* 188 */                   setText(this.mFollowingText);
/* 189 */                   setTextColor(this.mFollowingTextColor);
/* 190 */                   this.mState = 3;
                            break;
                        case 4:
/* 194 */                   setBackgroundResource(this.mFollowRequestedDrawableRes);
/* 195 */                   setText(this.mFollowRequestedText);
/* 196 */                   setTextColor(this.mFollowRequestedTextColor);
                            break;
                    }
/* 200 */           setColors();
                }
            }
