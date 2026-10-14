            package co.vine.android.widget.trimControls;

            import android.content.Context;
            import android.graphics.Bitmap;
            import android.graphics.Matrix;
            import android.net.Uri;
            import android.view.MotionEvent;
            import android.widget.FrameLayout;
            import android.widget.HorizontalScrollView;
            import co.vine.android.C0257R;
            import co.vine.android.recorder.ThumbnailExtractor;
            import co.vine.android.recorder.ThumbnailExtractorInterface;
            import co.vine.android.util.CrashUtil;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    public class ThumbnailRangeFinderLayout extends FrameLayout implements ThumbnailCarousel.WidthMeasuredListener, ThumbnailTrimmer.OnScrubberMovedListener, ThumbnailExtractorInterface.OnThumbnailRetrievedListener {
                public long durationUsec;
                private int mCarouselPadding;
                private int mDesiredThumbWidth;
                private GripTrack mGripTrack;
                private GripTrack.GripTrackTouchListener mGripTrackTouchListener;
                private int mLastRequestedThumbnailTimestampIndex;
                private int mLayoutWidth;
                private int mLeftMarginPx;
                private OnVideoTrimmedListener mListener;
                private HorizontalScrollView mScrollView;
                private int mScrollViewWidth;
                private ThumbnailCarousel mThumbnailCarousel;
                private ThumbnailExtractorInterface mThumbnailExtractor;
                private ThumbnailTrimmer mThumbnailTrimmer;
                private ArrayList<Long> mTimestamps;
                public long trimEndTimeUsec;
                public long trimStartTimeUsec;

                public interface OnVideoTrimmedListener {
                    void onStartEndTimeChanged(long j, long j2);

                    void onVideoTrimmedByCarousel();

                    void onVideoTrimmedByScrubber();
                }

/* 22 */        static /* synthetic */ HorizontalScrollView access$000(ThumbnailRangeFinderLayout x0) {
/* 23 */            return x0.mScrollView;
                }

/* 22 */        static /* synthetic */ int access$100(ThumbnailRangeFinderLayout x0) {
/* 23 */            return x0.mScrollViewWidth;
                }

/* 22 */        static /* synthetic */ OnVideoTrimmedListener access$200(ThumbnailRangeFinderLayout x0) {
/* 23 */            return x0.mListener;
                }

/* 22 */        static /* synthetic */ void access$300(ThumbnailRangeFinderLayout x0) {
/* 23 */            x0.prefetchThumbs();
                }

                /* JADX INFO: renamed from: co.vine.android.widget.trimControls.ThumbnailRangeFinderLayout$1 */
/* 47 */        class C05821 implements GripTrack.GripTrackTouchListener {
/* 48 */            C05821() {
                    }

                    @Override // co.vine.android.widget.trimControls.GripTrack.GripTrackTouchListener
/* 51 */            public boolean onGripTouchEvent(MotionEvent event) {
/* 52 */                if (ThumbnailRangeFinderLayout.access$000(ThumbnailRangeFinderLayout.this) == null) {
                            return false;
                        }
/* 53 */                Matrix matrix = new Matrix();
/* 54 */                int deltaX = ThumbnailRangeFinderLayout.this.getWidth() - ThumbnailRangeFinderLayout.access$100(ThumbnailRangeFinderLayout.this);
/* 55 */                matrix.postTranslate(deltaX, 0.0f);
/* 56 */                event.transform(matrix);
/* 57 */                return ThumbnailRangeFinderLayout.access$000(ThumbnailRangeFinderLayout.this).onTouchEvent(event);
                    }
                }

/* 63 */        public void setListener(OnVideoTrimmedListener listener) {
/* 64 */            this.mListener = listener;
                }

/* 67 */        public ThumbnailRangeFinderLayout(Context context, int leftMarginPx) {
/* 68 */            super(context);
/* 28 */            this.mLeftMarginPx = 0;
/* 45 */            this.mLastRequestedThumbnailTimestampIndex = -1;
/* 49 */            this.mGripTrackTouchListener = new C05821();
/* 69 */            this.mLeftMarginPx = leftMarginPx;
/* 71 */            int screenWidth = getResources().getDisplayMetrics().widthPixels;
/* 72 */            this.mDesiredThumbWidth = screenWidth / 8;
/* 73 */            FrameLayout.LayoutParams wrapParams = new FrameLayout.LayoutParams(-2, -2);
/* 76 */            this.mThumbnailCarousel = new ThumbnailCarousel(context, this.mLeftMarginPx);
/* 77 */            this.mThumbnailCarousel.setListener(this);
/* 78 */            this.mThumbnailTrimmer = new ThumbnailTrimmer(context, this.mLeftMarginPx);
/* 79 */            this.mThumbnailTrimmer.setListener(this);
/* 81 */            this.mGripTrack = new GripTrack(context, this.mGripTrackTouchListener);
/* 83 */            CarouselScrollView scrollView = new CarouselScrollView(context);
/* 84 */            scrollView.setLayoutParams(wrapParams);
/* 85 */            scrollView.setVerticalScrollBarEnabled(false);
/* 86 */            scrollView.setHorizontalScrollBarEnabled(false);
/* 87 */            scrollView.addView(this.mThumbnailCarousel, wrapParams);
/* 89 */            scrollView.setOverScrollMode(2);
/* 91 */            addView(scrollView);
/* 92 */            addView(this.mThumbnailTrimmer);
/* 93 */            addView(this.mGripTrack);
/* 94 */            this.mScrollView = scrollView;
/* 95 */            this.mCarouselPadding = getResources().getDimensionPixelSize(C0257R.dimen.carousel_padding);
                }

                @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
/* 99 */        protected void onLayout(boolean changed, int l, int t, int r, int b) {
/* 100 */           int w = r - l;
/* 101 */           int h = b - t;
/* 102 */           this.mScrollView.layout(0, this.mCarouselPadding, this.mScrollViewWidth, h - this.mCarouselPadding);
/* 103 */           this.mGripTrack.layout(0, this.mCarouselPadding, r, h - this.mCarouselPadding);
/* 104 */           computeStartEndTimes();
/* 105 */           this.mGripTrack.invalidate();
/* 106 */           this.mLayoutWidth = w;
                }

/* 109 */       public void setVideoPath(Uri uri, long durationMs, long maxDurationMs, boolean forceMediaMetadataRetriever) {
/* 110 */           this.mThumbnailCarousel.updateLayout(this.mDesiredThumbWidth, durationMs, maxDurationMs);
/* 111 */           computeStartEndTimes();
/* 113 */           float thumbnailInterval = maxDurationMs / 8.0f;
/* 114 */           float numCuts = durationMs / thumbnailInterval;
/* 115 */           ArrayList<Long> timestamps = new ArrayList<>();
                    for (int i = 0; i < ((int) numCuts); i++) {
/* 117 */               timestamps.add(Long.valueOf(((long) i) * ((long) ((1000 * durationMs) / numCuts))));
                    }
/* 119 */           extractThumbs(uri, timestamps, forceMediaMetadataRetriever);
/* 120 */           this.durationUsec = 1000 * durationMs;
                }

/* 123 */       public void extractThumbs(Uri uri, ArrayList<Long> timestamps, boolean forceMediaMetadataRetriever) {
/* 124 */           this.mThumbnailExtractor = ThumbnailExtractor.getExtractor(forceMediaMetadataRetriever);
/* 125 */           this.mThumbnailExtractor.start(getContext(), uri, this.mThumbnailCarousel.preferredHeight, this);
/* 126 */           this.mTimestamps = timestamps;
/* 127 */           prefetchThumbs();
                }

                @Override // co.vine.android.recorder.ThumbnailExtractorInterface.OnThumbnailRetrievedListener
/* 131 */       public synchronized void onThumbnailRetrieved(long timestamp, Bitmap bitmap) {
                    try {
                        int size = this.mTimestamps.size();
/* 133 */               if (size > 0) {
/* 135 */                   if (timestamp == this.mTimestamps.get(size - 1).longValue() && this.mThumbnailExtractor != null) {
/* 137 */                       this.mThumbnailExtractor.stop();
/* 138 */                       this.mThumbnailExtractor = null;
                            }
                        } else {
/* 142 */                   CrashUtil.logOrThrowInDebug(new IllegalStateException("WARNING: timestamp list is empty."));
                        }
/* 145 */               if (bitmap != null) {
/* 146 */                   int outHeight = this.mThumbnailCarousel.preferredHeight;
/* 147 */                   int outWidth = (bitmap.getWidth() * outHeight) / bitmap.getHeight();
/* 149 */                   Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, outWidth, outHeight, true);
/* 150 */                   bitmap.recycle();
/* 151 */                   int trimX = (outWidth - this.mDesiredThumbWidth) / 2;
/* 152 */                   int width = this.mDesiredThumbWidth;
/* 153 */                   if (trimX < 0) {
/* 154 */                       trimX = 0;
/* 155 */                       width = Math.min(this.mDesiredThumbWidth, scaledBitmap.getWidth());
                            }
/* 157 */                   Bitmap resultBitmap = Bitmap.createBitmap(scaledBitmap, trimX, 0, width, outHeight);
/* 158 */                   this.mThumbnailCarousel.addThumbnail(resultBitmap);
/* 159 */                   scaledBitmap.recycle();
                        } else {
/* 161 */                   this.mThumbnailCarousel.addThumbnail(null);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // co.vine.android.recorder.ThumbnailExtractorInterface.OnThumbnailRetrievedListener
/* 167 */       public void onError() {
                }

                @Override // co.vine.android.widget.trimControls.ThumbnailCarousel.WidthMeasuredListener
/* 171 */       public void onWidthMeasured(int containerWidth) {
/* 172 */           this.mScrollViewWidth = Math.min(containerWidth, this.mLayoutWidth);
/* 173 */           this.mThumbnailTrimmer.layout(0, 0, this.mScrollViewWidth, getHeight());
/* 174 */           computeStartEndTimes();
                }

                @Override // co.vine.android.widget.trimControls.ThumbnailTrimmer.OnScrubberMovedListener
/* 178 */       public void onScrubberMoved(int rangeRight, int trimmerFullWidth) {
/* 179 */           this.mScrollViewWidth = trimmerFullWidth;
/* 180 */           this.mThumbnailCarousel.addScrolledViewPadding(this.mLeftMarginPx, this.mThumbnailTrimmer.getWidth() - rangeRight);
/* 181 */           computeStartEndTimes();
/* 182 */           this.mListener.onVideoTrimmedByScrubber();
                }

/* 186 */       public void computeStartEndTimes() {
/* 187 */           ThumbnailCarousel thumbs = this.mThumbnailCarousel;
/* 188 */           if (thumbs.getMeasuredWidth() > 0) {
/* 189 */               int trimStartPx = (this.mScrollView.getScrollX() + thumbs.getPaddingLeft()) - this.mLeftMarginPx;
/* 190 */               int trimEndPx = trimStartPx + this.mThumbnailTrimmer.outlineRectWidth;
/* 191 */               int widthNoPadding = ((thumbs.getWidth() - thumbs.getPaddingLeft()) - thumbs.getPaddingRight()) - this.mThumbnailTrimmer.scrubberDrawableWidth;
/* 193 */               this.trimStartTimeUsec = (this.durationUsec * ((long) trimStartPx)) / ((long) widthNoPadding);
/* 194 */               this.trimEndTimeUsec = (this.durationUsec * ((long) trimEndPx)) / ((long) widthNoPadding);
/* 196 */               int thumbsLeft = this.mLeftMarginPx - trimStartPx;
/* 197 */               int thumbsRight = thumbs.getThumbnailStripWidth() - trimStartPx;
/* 198 */               this.mGripTrack.setBoundaries(thumbsLeft, thumbsRight);
/* 199 */               this.mThumbnailTrimmer.setDeselectedBounds(thumbsLeft, thumbsRight);
                    } else {
/* 201 */               this.trimStartTimeUsec = 0L;
/* 202 */               this.trimEndTimeUsec = this.durationUsec;
                    }
/* 204 */           this.mListener.onStartEndTimeChanged(this.trimStartTimeUsec, this.trimEndTimeUsec);
                }

/* 214 */       private class CarouselScrollView extends HorizontalScrollView {
/* 215 */           public CarouselScrollView(Context context) {
/* 217 */               super(context);
                    }

                    @Override // android.view.View
/* 221 */           protected void onScrollChanged(int l, int t, int oldl, int oldt) {
/* 222 */               super.onScrollChanged(l, t, oldl, oldt);
/* 223 */               ThumbnailRangeFinderLayout.this.computeStartEndTimes();
/* 224 */               ThumbnailRangeFinderLayout.access$200(ThumbnailRangeFinderLayout.this).onVideoTrimmedByCarousel();
/* 225 */               ThumbnailRangeFinderLayout.access$300(ThumbnailRangeFinderLayout.this);
                    }
                }

/* 230 */       private void prefetchThumbs() {
/* 231 */           int desiredThumbCount = (this.mScrollView.getScrollX() / this.mDesiredThumbWidth) + 24;
                    for (int i = this.mLastRequestedThumbnailTimestampIndex + 1; i < this.mTimestamps.size() && i <= desiredThumbCount; i++) {
/* 233 */               if (this.mThumbnailExtractor != null) {
/* 234 */                   this.mThumbnailExtractor.requestThumbnail(this.mTimestamps.get(i).longValue());
                        }
/* 236 */               this.mLastRequestedThumbnailTimestampIndex = i;
                    }
                }

/* 240 */       public void stop() {
/* 241 */           if (this.mThumbnailExtractor != null) {
/* 242 */               this.mThumbnailExtractor.stop();
                    }
/* 244 */           this.mThumbnailCarousel.releaseBitmaps();
                }
            }
