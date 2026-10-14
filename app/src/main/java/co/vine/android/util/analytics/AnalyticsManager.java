            package co.vine.android.util.analytics;

            import android.content.Context;
            import co.vine.android.analytics.TimeTracker;
            import co.vine.android.cache.CacheKey;
            import co.vine.android.player.VideoViewInterface;
            import co.vine.android.prefetch.PrefetchManager;
            import com.edisonwang.android.slog.SLog;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 33 */    public class AnalyticsManager {
                private static final String CATEGORY_LAUNCH_TIME = "Launch Time";
                private static final String CATEGORY_VIDEO_LOAD = "Feed Video Loading";
                public static final String EVENT_APPLICATION_START = "application_start";
                public static final String EVENT_FIRST_VIDEO_PREPARED = "video_prepared";
                public static final String EVENT_LAST_ACTIVITY_START = "application_start";
                private static final String VARIABLE_ACTIVITY_START_TO_VIDEO_LOAD = "Feed Activity To First Video";
                private static final String VARIABLE_APP_START_TO_VIDEO_LOAD = "VineApplication To First Video";

/* 34 */        public static void onApplicationInit() {
/* 35 */            TimeTracker.onEventHappened("application_start");
                }

/* 38 */        public static void onStart(Context context) {
/* 39 */            FlurryUtils.start(context);
/* 40 */            TimeTracker.onEventHappened("application_start");
                }

/* 43 */        public static void onStop(Context context) {
/* 44 */            FlurryUtils.stop(context);
                }

/* 47 */        public static void onVideoLoaded(VideoViewInterface view) {
/* 48 */            Context context = view.getContext();
/* 49 */            if (TimeTracker.onFirstEventHappened(EVENT_FIRST_VIDEO_PREPARED)) {
/* 50 */                long applicationStartToVideoPrepare = TimeTracker.timeBetween("application_start", EVENT_FIRST_VIDEO_PREPARED);
/* 51 */                SLog.m442b("applicationStartToVideoPrepare {}ms", Long.valueOf(applicationStartToVideoPrepare));
/* 52 */                long activityStartToVideoPrepare = TimeTracker.timeBetween("application_start", EVENT_FIRST_VIDEO_PREPARED);
/* 53 */                SLog.m442b("activityStartToVideoPrepare {}ms", Long.valueOf(activityStartToVideoPrepare));
                    }
/* 55 */            CacheKey key = view.getCacheKey();
/* 56 */            if (key != null) {
/* 57 */                boolean wasSyncedRecently = System.currentTimeMillis() - PrefetchManager.getInstance(context).getLastSync() < 28800000;
/* 58 */                FlurryUtils.trackVideoLoadHits(wasSyncedRecently, key.getCacheState());
                    }
                }
            }
