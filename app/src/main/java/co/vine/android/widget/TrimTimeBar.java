            package co.vine.android.widget;

            import android.content.Context;
            import android.graphics.Bitmap;
            import android.graphics.BitmapFactory;
            import android.graphics.Canvas;
            import android.graphics.Paint;
            import android.graphics.Rect;
            import android.support.annotation.NonNull;
            import android.view.MotionEvent;
            import android.view.View;
            import co.vine.android.C0257R;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 47 */    public class TrimTimeBar extends View {
                public static final int SCRUBBER_END = 2;
                public static final int SCRUBBER_NONE = 0;
                public static final int SCRUBBER_START = 1;
                private int mCurrentTime;
                private final Listener mListener;
                private final Rect mProgressBar;
                private final Paint mProgressPaint;
                private int mScrubberCorrection;
                private int mScrubberLeft;
                private int mScrubberPadding;
                private int mScrubberTouched;
                private boolean mScrubbing;
                private final Rect mSelectedBar;
                private final Paint mSelectedPaint;
                private int mTotalTime;
                private final Bitmap mTrimEndScrubber;
                private int mTrimEndScrubberLeft;
                private int mTrimEndScrubberTop;
                private int mTrimEndTime;
                private final Bitmap mTrimStartScrubber;
                private int mTrimStartScrubberLeft;
                private int mTrimStartScrubberTop;
                private int mTrimStartTime;

                public interface Listener {
                    void onScrubbingEnd(int i, int i2, int i3);

                    void onScrubbingMove(int i);

                    void onScrubbingStart();
                }

/* 48 */        public TrimTimeBar(Context context, @NonNull Listener listener) {
/* 49 */            super(context);
/* 37 */            this.mScrubberTouched = 0;
/* 50 */            this.mListener = listener;
/* 52 */            this.mProgressBar = new Rect();
/* 53 */            this.mSelectedBar = new Rect();
/* 54 */            this.mProgressPaint = new Paint();
/* 55 */            this.mSelectedPaint = new Paint();
/* 56 */            this.mProgressPaint.setColor(getResources().getColor(C0257R.color.timebar_background_color));
/* 57 */            this.mSelectedPaint.setColor(getResources().getColor(C0257R.color.timebar_selected_color));
/* 59 */            this.mTrimStartTime = 0;
/* 60 */            this.mTrimEndTime = 0;
/* 61 */            this.mTrimStartScrubberLeft = 0;
/* 62 */            this.mTrimEndScrubberLeft = 0;
/* 63 */            this.mTrimStartScrubberTop = 0;
/* 64 */            this.mTrimEndScrubberTop = 0;
/* 65 */            this.mTrimStartScrubber = BitmapFactory.decodeResource(getResources(), C0257R.drawable.scrub_left);
/* 66 */            this.mTrimEndScrubber = BitmapFactory.decodeResource(getResources(), C0257R.drawable.scrub_right);
/* 67 */            this.mScrubberPadding = getResources().getDimensionPixelSize(C0257R.dimen.scrubber_padding);
                }

/* 70 */        private void update() {
/* 71 */            initTrimTimeIfNeeded();
/* 72 */            updatePlayedBarAndScrubberFromTime();
/* 73 */            invalidate();
                }

/* 76 */        public int getPreferredHeight() {
/* 77 */            int defaultHeight = getResources().getDimensionPixelSize(C0257R.dimen.time_bar_default_height);
/* 78 */            if (this.mTrimStartScrubber == null) {
                        return defaultHeight;
                    }
                    int defaultHeight2 = this.mTrimStartScrubber.getHeight();
                    return defaultHeight2;
                }

/* 81 */        public void setTime(int currentTime, int totalTime, int trimStartTime, int trimEndTime) {
/* 82 */            if (this.mCurrentTime != currentTime || this.mTotalTime != totalTime || this.mTrimStartTime != trimStartTime || this.mTrimEndTime != trimEndTime) {
/* 86 */                this.mCurrentTime = currentTime;
/* 87 */                this.mTotalTime = totalTime;
/* 88 */                this.mTrimStartTime = trimStartTime;
/* 89 */                this.mTrimEndTime = trimEndTime;
/* 90 */                update();
                    }
                }

/* 94 */        private boolean inScrubber(float touchX, float touchY, int scrubberLeft, int scrubberTop, Bitmap scrubber) {
/* 95 */            int scrubberRight = scrubberLeft + scrubber.getWidth();
/* 96 */            int scrubberBottom = scrubberTop + scrubber.getHeight();
                    return touchX > ((float) (scrubberLeft - this.mScrubberPadding)) && touchX < ((float) (this.mScrubberPadding + scrubberRight)) && touchY > ((float) (scrubberTop - this.mScrubberPadding)) && touchY < ((float) (this.mScrubberPadding + scrubberBottom));
                }

                @Override // android.view.View
/* 102 */       protected void onLayout(boolean changed, int l, int t, int r, int b) {
/* 103 */           int w = r - l;
/* 104 */           int topBottomMargin = getResources().getDimensionPixelSize(C0257R.dimen.time_bar_top_bottom_padding);
/* 105 */           int leftRightMargin = this.mTrimStartScrubber.getWidth() / 2;
/* 107 */           this.mTrimStartScrubberTop = 0;
/* 108 */           this.mTrimEndScrubberTop = 0;
/* 110 */           int left = getPaddingLeft() + leftRightMargin;
/* 111 */           int right = (w - getPaddingRight()) - leftRightMargin;
/* 112 */           int bottom = this.mTrimStartScrubber.getHeight() - topBottomMargin;
/* 113 */           this.mProgressBar.set(left, topBottomMargin, right, bottom);
/* 115 */           update();
                }

                @Override // android.view.View
/* 120 */       protected void onDraw(Canvas canvas) {
/* 121 */           canvas.drawRect(this.mProgressBar, this.mProgressPaint);
/* 122 */           canvas.drawRect(this.mSelectedBar, this.mSelectedPaint);
/* 125 */           canvas.drawBitmap(this.mTrimStartScrubber, this.mTrimStartScrubberLeft, this.mTrimStartScrubberTop, (Paint) null);
/* 126 */           canvas.drawBitmap(this.mTrimEndScrubber, this.mTrimEndScrubberLeft, this.mTrimEndScrubberTop, (Paint) null);
                }

                @Override // android.view.View
/* 130 */       public boolean onTouchEvent(MotionEvent event) {
/* 131 */           int x = (int) event.getX();
/* 132 */           int y = (int) event.getY();
                    switch (event.getAction()) {
                        case 0:
/* 136 */                   this.mScrubberTouched = whichScrubber(x, y);
                            switch (this.mScrubberTouched) {
                                case 1:
/* 142 */                           this.mScrubbing = true;
/* 143 */                           this.mScrubberCorrection = x - this.mTrimStartScrubberLeft;
                                    break;
                                case 2:
/* 147 */                           this.mScrubbing = true;
/* 148 */                           this.mScrubberCorrection = x - this.mTrimEndScrubberLeft;
                                    break;
                            }
/* 152 */                   if (!this.mScrubbing) {
                                return false;
                            }
/* 153 */                   this.mListener.onScrubbingStart();
                            return true;
                        case 1:
                        case 3:
/* 203 */                   if (!this.mScrubbing) {
                                return false;
                            }
/* 204 */                   int seekToTime = 0;
                            switch (this.mScrubberTouched) {
                                case 1:
/* 207 */                           seekToTime = getScrubberTime(this.mTrimStartScrubberLeft, trimStartScrubberTipOffset());
/* 209 */                           this.mScrubberLeft = this.mTrimStartScrubberLeft + trimStartScrubberTipOffset();
                                    break;
                                case 2:
/* 213 */                           seekToTime = getScrubberTime(this.mTrimEndScrubberLeft, trimEndScrubberTipOffset());
/* 215 */                           this.mScrubberLeft = this.mTrimEndScrubberLeft + trimEndScrubberTipOffset();
                                    break;
                            }
/* 219 */                   updateTimeFromPos();
/* 220 */                   this.mListener.onScrubbingEnd(seekToTime, getScrubberTime(this.mTrimStartScrubberLeft, trimStartScrubberTipOffset()), getScrubberTime(this.mTrimEndScrubberLeft, trimEndScrubberTipOffset()));
/* 225 */                   this.mScrubbing = false;
/* 226 */                   this.mScrubberTouched = 0;
                            return true;
                        case 2:
/* 159 */                   if (!this.mScrubbing) {
                                return false;
                            }
/* 160 */                   int seekToTime2 = -1;
/* 161 */                   int lowerBound = this.mTrimStartScrubberLeft + trimStartScrubberTipOffset();
/* 162 */                   int upperBound = this.mTrimEndScrubberLeft + trimEndScrubberTipOffset();
                            switch (this.mScrubberTouched) {
                                case 1:
/* 165 */                           this.mTrimStartScrubberLeft = x - this.mScrubberCorrection;
/* 167 */                           if (this.mTrimStartScrubberLeft > this.mTrimEndScrubberLeft) {
/* 168 */                               this.mTrimStartScrubberLeft = this.mTrimEndScrubberLeft;
                                    }
/* 170 */                           int lowerBound2 = this.mProgressBar.left;
/* 171 */                           this.mTrimStartScrubberLeft = clampScrubber(this.mTrimStartScrubberLeft, trimStartScrubberTipOffset(), lowerBound2, upperBound);
/* 175 */                           seekToTime2 = getScrubberTime(this.mTrimStartScrubberLeft, trimStartScrubberTipOffset());
                                    break;
                                case 2:
/* 180 */                           this.mTrimEndScrubberLeft = x - this.mScrubberCorrection;
/* 181 */                           int upperBound2 = this.mProgressBar.right;
/* 182 */                           this.mTrimEndScrubberLeft = clampScrubber(this.mTrimEndScrubberLeft, trimEndScrubberTipOffset(), lowerBound, upperBound2);
/* 186 */                           seekToTime2 = getScrubberTime(this.mTrimEndScrubberLeft, trimEndScrubberTipOffset());
                                    break;
                            }
/* 191 */                   updateTimeFromPos();
/* 192 */                   updatePlayedBarAndScrubberFromTime();
/* 193 */                   if (seekToTime2 != -1) {
/* 194 */                       this.mListener.onScrubbingMove(seekToTime2);
                            }
/* 196 */                   invalidate();
                            return true;
                        default:
                            return false;
                    }
                }

/* 235 */       private int getBarPosFromTime(int time) {
/* 236 */           return this.mProgressBar.left + ((int) ((((long) this.mProgressBar.width()) * ((long) time)) / ((long) this.mTotalTime)));
                }

/* 239 */       private int trimStartScrubberTipOffset() {
/* 240 */           return (this.mTrimStartScrubber.getWidth() * 3) / 4;
                }

/* 243 */       private int trimEndScrubberTipOffset() {
/* 244 */           return this.mTrimEndScrubber.getWidth() / 4;
                }

/* 248 */       private void updatePlayedBarAndScrubberFromTime() {
/* 249 */           this.mSelectedBar.set(this.mProgressBar);
/* 250 */           if (this.mTotalTime > 0) {
/* 251 */               this.mSelectedBar.left = getBarPosFromTime(this.mTrimStartTime);
/* 252 */               this.mSelectedBar.right = getBarPosFromTime(this.mTrimEndTime);
/* 253 */               if (!this.mScrubbing) {
/* 254 */                   this.mScrubberLeft = this.mSelectedBar.right;
/* 255 */                   this.mTrimStartScrubberLeft = this.mSelectedBar.left - trimStartScrubberTipOffset();
/* 256 */                   this.mTrimEndScrubberLeft = getBarPosFromTime(this.mTrimEndTime) - trimEndScrubberTipOffset();
/* 267 */                   return;
                        }
/* 267 */               return;
                    }
/* 262 */           this.mSelectedBar.right = this.mProgressBar.left;
/* 263 */           this.mScrubberLeft = this.mProgressBar.left;
/* 264 */           this.mTrimStartScrubberLeft = this.mProgressBar.left - trimStartScrubberTipOffset();
/* 265 */           this.mTrimEndScrubberLeft = this.mProgressBar.right - trimEndScrubberTipOffset();
                }

/* 269 */       private void initTrimTimeIfNeeded() {
/* 270 */           if (this.mTotalTime > 0 && this.mTrimEndTime == 0) {
/* 271 */               this.mTrimEndTime = this.mTotalTime;
                    }
                }

/* 275 */       private int whichScrubber(float x, float y) {
/* 276 */           if (inScrubber(x, y, this.mTrimStartScrubberLeft, this.mTrimStartScrubberTop, this.mTrimStartScrubber)) {
/* 277 */               return 1;
                    }
/* 278 */           if (inScrubber(x, y, this.mTrimEndScrubberLeft, this.mTrimEndScrubberTop, this.mTrimEndScrubber)) {
/* 279 */               return 2;
                    }
/* 281 */           return 0;
                }

/* 284 */       private int clampScrubber(int scrubberLeft, int offset, int lowerBound, int upperBound) {
/* 285 */           int max = upperBound - offset;
/* 286 */           int min = lowerBound - offset;
/* 287 */           return Math.min(max, Math.max(min, scrubberLeft));
                }

/* 290 */       private int getScrubberTime(int scrubberLeft, int offset) {
/* 291 */           return (int) ((((long) ((scrubberLeft + offset) - this.mProgressBar.left)) * ((long) this.mTotalTime)) / ((long) this.mProgressBar.width()));
                }

/* 295 */       private void updateTimeFromPos() {
/* 296 */           this.mCurrentTime = getScrubberTime(this.mScrubberLeft, 0);
/* 297 */           this.mTrimStartTime = getScrubberTime(this.mTrimStartScrubberLeft, trimStartScrubberTipOffset());
/* 298 */           this.mTrimEndTime = getScrubberTime(this.mTrimEndScrubberLeft, trimEndScrubberTipOffset());
                }
            }
