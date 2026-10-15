            package co.vine.android.widget.trimControls;

            import android.content.Context;
            import android.graphics.Bitmap;
            import android.graphics.Matrix;
            import android.net.Uri;
            import android.view.MotionEvent;
            import android.widget.FrameLayout;
            import android.widget.HorizontalScrollView;
            import co.vine.android.R;
            import co.vine.android.recorder.ThumbnailExtractor;
            import co.vine.android.recorder.ThumbnailExtractorInterface;
            import co.vine.android.util.CrashUtil;
            import java.util.ArrayList;
            
            
    public class ThumbnailRangeFinderLayout extends FrameLayout implements ThumbnailCarousel.WidthMeasuredListener, ThumbnailTrimmer.OnScrubberMovedListener, ThumbnailExtractorInterface.OnThumbnailRetrievedListener {
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

        static  HorizontalScrollView access$000(ThumbnailRangeFinderLayout x0) {
            return x0.mScrollView;
                }

        static  int access$100(ThumbnailRangeFinderLayout x0) {
            return x0.mScrollViewWidth;
                }

        static  OnVideoTrimmedListener access$200(ThumbnailRangeFinderLayout x0) {
            return x0.mListener;
                }

        static  void access$300(ThumbnailRangeFinderLayout x0) {
            x0.prefetchThumbs();
                }

                
        class C05821 implements GripTrack.GripTrackTouchListener {
            C05821() {
                    }

                    @Override 
            public boolean onGripTouchEvent(MotionEvent event) {
                if (ThumbnailRangeFinderLayout.access$000(ThumbnailRangeFinderLayout.this) == null) {
                            return false;
                        }
                Matrix matrix = new Matrix();
                int deltaX = ThumbnailRangeFinderLayout.this.getWidth() - ThumbnailRangeFinderLayout.access$100(ThumbnailRangeFinderLayout.this);
                matrix.postTranslate(deltaX, 0.0f);
                event.transform(matrix);
                return ThumbnailRangeFinderLayout.access$000(ThumbnailRangeFinderLayout.this).onTouchEvent(event);
                    }
                }

        public void setListener(OnVideoTrimmedListener listener) {
            this.mListener = listener;
                }

        public ThumbnailRangeFinderLayout(Context context, int leftMarginPx) {
            super(context);
            this.mLeftMarginPx = 0;
            this.mLastRequestedThumbnailTimestampIndex = -1;
            this.mGripTrackTouchListener = new C05821();
            this.mLeftMarginPx = leftMarginPx;
            int screenWidth = getResources().getDisplayMetrics().widthPixels;
            this.mDesiredThumbWidth = screenWidth / 8;
            FrameLayout.LayoutParams wrapParams = new FrameLayout.LayoutParams(-2, -2);
            this.mThumbnailCarousel = new ThumbnailCarousel(context, this.mLeftMarginPx);
            this.mThumbnailCarousel.setListener(this);
            this.mThumbnailTrimmer = new ThumbnailTrimmer(context, this.mLeftMarginPx);
            this.mThumbnailTrimmer.setListener(this);
            this.mGripTrack = new GripTrack(context, this.mGripTrackTouchListener);
            CarouselScrollView scrollView = new CarouselScrollView(context);
            scrollView.setLayoutParams(wrapParams);
            scrollView.setVerticalScrollBarEnabled(false);
            scrollView.setHorizontalScrollBarEnabled(false);
            scrollView.addView(this.mThumbnailCarousel, wrapParams);
            scrollView.setOverScrollMode(2);
            addView(scrollView);
            addView(this.mThumbnailTrimmer);
            addView(this.mGripTrack);
            this.mScrollView = scrollView;
            this.mCarouselPadding = getResources().getDimensionPixelSize(R.dimen.carousel_padding);
                }

                @Override 
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
           int w = r - l;
           int h = b - t;
           this.mScrollView.layout(0, this.mCarouselPadding, this.mScrollViewWidth, h - this.mCarouselPadding);
           this.mGripTrack.layout(0, this.mCarouselPadding, r, h - this.mCarouselPadding);
           computeStartEndTimes();
           this.mGripTrack.invalidate();
           this.mLayoutWidth = w;
                }

       public void setVideoPath(Uri uri, long durationMs, long maxDurationMs, boolean forceMediaMetadataRetriever) {
           this.mThumbnailCarousel.updateLayout(this.mDesiredThumbWidth, durationMs, maxDurationMs);
           computeStartEndTimes();
           float thumbnailInterval = maxDurationMs / 8.0f;
           float numCuts = durationMs / thumbnailInterval;
           ArrayList<Long> timestamps = new ArrayList<>();
                    for (int i = 0; i < ((int) numCuts); i++) {
               timestamps.add(Long.valueOf(((long) i) * ((long) ((1000 * durationMs) / numCuts))));
                    }
           extractThumbs(uri, timestamps, forceMediaMetadataRetriever);
           this.durationUsec = 1000 * durationMs;
                }

       public void extractThumbs(Uri uri, ArrayList<Long> timestamps, boolean forceMediaMetadataRetriever) {
           this.mThumbnailExtractor = ThumbnailExtractor.getExtractor(forceMediaMetadataRetriever);
           this.mThumbnailExtractor.start(getContext(), uri, this.mThumbnailCarousel.preferredHeight, this);
           this.mTimestamps = timestamps;
           prefetchThumbs();
                }

                @Override 
       public synchronized void onThumbnailRetrieved(long timestamp, Bitmap bitmap) {
                    try {
                        int size = this.mTimestamps.size();
               if (size > 0) {
                   if (timestamp == this.mTimestamps.get(size - 1).longValue() && this.mThumbnailExtractor != null) {
                       this.mThumbnailExtractor.stop();
                       this.mThumbnailExtractor = null;
                            }
                        } else {
                   CrashUtil.logOrThrowInDebug(new IllegalStateException("WARNING: timestamp list is empty."));
                        }
               if (bitmap != null) {
                   int outHeight = this.mThumbnailCarousel.preferredHeight;
                   int outWidth = (bitmap.getWidth() * outHeight) / bitmap.getHeight();
                   Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, outWidth, outHeight, true);
                   bitmap.recycle();
                   int trimX = (outWidth - this.mDesiredThumbWidth) / 2;
                   int width = this.mDesiredThumbWidth;
                   if (trimX < 0) {
                       trimX = 0;
                       width = Math.min(this.mDesiredThumbWidth, scaledBitmap.getWidth());
                            }
                   Bitmap resultBitmap = Bitmap.createBitmap(scaledBitmap, trimX, 0, width, outHeight);
                   this.mThumbnailCarousel.addThumbnail(resultBitmap);
                   scaledBitmap.recycle();
                        } else {
                   this.mThumbnailCarousel.addThumbnail(null);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override 
       public void onError() {
                }

                @Override 
       public void onWidthMeasured(int containerWidth) {
           this.mScrollViewWidth = Math.min(containerWidth, this.mLayoutWidth);
           this.mThumbnailTrimmer.layout(0, 0, this.mScrollViewWidth, getHeight());
           computeStartEndTimes();
                }

                @Override 
       public void onScrubberMoved(int rangeRight, int trimmerFullWidth) {
           this.mScrollViewWidth = trimmerFullWidth;
           this.mThumbnailCarousel.addScrolledViewPadding(this.mLeftMarginPx, this.mThumbnailTrimmer.getWidth() - rangeRight);
           computeStartEndTimes();
           this.mListener.onVideoTrimmedByScrubber();
                }

       public void computeStartEndTimes() {
           ThumbnailCarousel thumbs = this.mThumbnailCarousel;
           if (thumbs.getMeasuredWidth() > 0) {
               int trimStartPx = (this.mScrollView.getScrollX() + thumbs.getPaddingLeft()) - this.mLeftMarginPx;
               int trimEndPx = trimStartPx + this.mThumbnailTrimmer.outlineRectWidth;
               int widthNoPadding = ((thumbs.getWidth() - thumbs.getPaddingLeft()) - thumbs.getPaddingRight()) - this.mThumbnailTrimmer.scrubberDrawableWidth;
               this.trimStartTimeUsec = (this.durationUsec * ((long) trimStartPx)) / ((long) widthNoPadding);
               this.trimEndTimeUsec = (this.durationUsec * ((long) trimEndPx)) / ((long) widthNoPadding);
               int thumbsLeft = this.mLeftMarginPx - trimStartPx;
               int thumbsRight = thumbs.getThumbnailStripWidth() - trimStartPx;
               this.mGripTrack.setBoundaries(thumbsLeft, thumbsRight);
               this.mThumbnailTrimmer.setDeselectedBounds(thumbsLeft, thumbsRight);
                    } else {
               this.trimStartTimeUsec = 0L;
               this.trimEndTimeUsec = this.durationUsec;
                    }
           this.mListener.onStartEndTimeChanged(this.trimStartTimeUsec, this.trimEndTimeUsec);
                }

       private class CarouselScrollView extends HorizontalScrollView {
           public CarouselScrollView(Context context) {
               super(context);
                    }

                    @Override 
           protected void onScrollChanged(int l, int t, int oldl, int oldt) {
               super.onScrollChanged(l, t, oldl, oldt);
               ThumbnailRangeFinderLayout.this.computeStartEndTimes();
               ThumbnailRangeFinderLayout.access$200(ThumbnailRangeFinderLayout.this).onVideoTrimmedByCarousel();
               ThumbnailRangeFinderLayout.access$300(ThumbnailRangeFinderLayout.this);
                    }
                }

       private void prefetchThumbs() {
           int desiredThumbCount = (this.mScrollView.getScrollX() / this.mDesiredThumbWidth) + 24;
                    for (int i = this.mLastRequestedThumbnailTimestampIndex + 1; i < this.mTimestamps.size() && i <= desiredThumbCount; i++) {
               if (this.mThumbnailExtractor != null) {
                   this.mThumbnailExtractor.requestThumbnail(this.mTimestamps.get(i).longValue());
                        }
               this.mLastRequestedThumbnailTimestampIndex = i;
                    }
                }

       public void stop() {
           if (this.mThumbnailExtractor != null) {
               this.mThumbnailExtractor.stop();
                    }
           this.mThumbnailCarousel.releaseBitmaps();
                }
            }
