            package co.vine.android.util.analytics;

            import android.content.Context;
            import android.graphics.Canvas;
            import android.graphics.Paint;
            import android.graphics.Point;
            import android.support.annotation.NonNull;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.ViewGroup;
            import co.vine.android.prefetch.PrefetchManager;
            import co.vine.android.util.SystemUtil;
            import com.edisonwang.android.slog.MessageFormatter;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 34 */    public class BehaviorUsageView extends View {
                private static final String[] DAYS = {"SUN", "MON", "TUE", "WED", "THR", "FRI", "SAT"};
                private float mDeltaY;
                private final BehaviorManager mManager;
                private final Paint mPaint;
                private Point mScreenSize;
                private final String[] mStats;
                private int[] mUsagesByDayOfWeek;
                private long[] mUsagesByDayOfWeekOriginal;
                private int[] mUsagesByHourOfDay;
                private long[] mUsagesByHourOfDayOriginal;
                private String[] mUsagesByTimeline;

/* 35 */        public BehaviorUsageView(Context context) {
/* 36 */            super(context);
/* 37 */            this.mManager = BehaviorManager.getInstance(context);
/* 38 */            this.mStats = PrefetchManager.getInstance(context).getStats();
/* 39 */            this.mPaint = initialize();
                }

/* 42 */        private Paint initialize() {
/* 43 */            this.mUsagesByHourOfDayOriginal = this.mManager.getUsagesByHourOfDay();
/* 44 */            this.mUsagesByHourOfDay = normalize(this.mUsagesByHourOfDayOriginal);
/* 45 */            this.mUsagesByDayOfWeekOriginal = this.mManager.getUsagesByDateOfWeek();
/* 46 */            this.mUsagesByDayOfWeek = normalize(this.mUsagesByDayOfWeekOriginal);
/* 47 */            this.mUsagesByTimeline = this.mManager.getUsagesStringsByTimelineForStatView();
/* 48 */            Paint paint = new Paint(1);
/* 49 */            paint.setTextAlign(Paint.Align.LEFT);
/* 50 */            paint.setTextSize(30.0f);
/* 51 */            this.mDeltaY = paint.measureText("%") * 1.5f;
/* 52 */            this.mScreenSize = SystemUtil.getDisplaySize(getContext());
/* 53 */            return paint;
                }

/* 56 */        private int[] normalize(long[] usages) {
/* 57 */            long sum = 0;
                    for (long usage : usages) {
/* 59 */                sum += usage;
                    }
/* 61 */            int[] percentages = new int[usages.length];
                    for (int i = 0; i < usages.length; i++) {
                        if (sum != 0) {
/* 64 */                    percentages[i] = (int) ((usages[i] * 100) / sum);
                        }
                    }
/* 67 */            return percentages;
                }

                /* JADX INFO: renamed from: co.vine.android.util.analytics.BehaviorUsageView$1 */
/* 70 */        class RunnableC05421 implements Runnable {
/* 71 */            RunnableC05421() {
                    }

                    @Override // java.lang.Runnable
/* 74 */            public void run() {
/* 75 */                ((ViewGroup) BehaviorUsageView.this.getParent()).removeView(BehaviorUsageView.this);
                    }
                }

                @Override // android.view.View
/* 71 */        public boolean onTouchEvent(@NonNull MotionEvent event) {
/* 72 */            getHandler().post(new RunnableC05421());
/* 78 */            return super.onTouchEvent(event);
                }

                @Override // android.view.View
/* 82 */        protected void onDraw(Canvas canvas) {
/* 83 */            super.onDraw(canvas);
/* 84 */            canvas.drawColor(-1);
/* 86 */            canvas.drawText("Usages by hour of day: ", 10.0f, 50, this.mPaint);
/* 88 */            int y = (int) (50 + this.mDeltaY);
                    for (int i = 0; i < 23; i++) {
/* 90 */                String text = MessageFormatter.toStringMessage("{}:00: {}% ({})", Integer.valueOf(i), Integer.valueOf(this.mUsagesByHourOfDay[i]), Long.valueOf(this.mUsagesByHourOfDayOriginal[i]));
/* 93 */                canvas.drawText(text, 10.0f, y, this.mPaint);
/* 94 */                y = (int) (y + this.mDeltaY);
                    }
/* 96 */            int lastY = y;
/* 98 */            canvas.drawText("Usages by day of week: ", this.mScreenSize.x / 2, 50, this.mPaint);
/* 100 */           int y2 = (int) (50 + this.mDeltaY);
                    for (int i2 = 0; i2 < 7; i2++) {
/* 102 */               String text2 = MessageFormatter.toStringMessage("{}: {}% ({})", DAYS[i2], Integer.valueOf(this.mUsagesByDayOfWeek[i2]), Long.valueOf(this.mUsagesByDayOfWeekOriginal[i2]));
/* 105 */               canvas.drawText(text2, this.mScreenSize.x / 2, y2, this.mPaint);
/* 106 */               y2 = (int) (y2 + this.mDeltaY);
                    }
/* 108 */           int y3 = (int) (y2 + (this.mDeltaY * 5.0f));
                    for (int i3 = 0; i3 < this.mStats.length; i3++) {
/* 111 */               canvas.drawText(this.mStats[i3], this.mScreenSize.x / 2, y3, this.mPaint);
/* 112 */               y3 = (int) (y3 + this.mDeltaY);
                    }
/* 115 */           int lastY2 = (int) (Math.max(lastY, y3) + (this.mDeltaY * 5.0f));
/* 116 */           String[] arr$ = this.mUsagesByTimeline;
                    for (String s : arr$) {
/* 117 */               canvas.drawText(s, 0.0f, lastY2, this.mPaint);
/* 118 */               lastY2 = (int) (lastY2 + this.mDeltaY);
                    }
                }
            }
