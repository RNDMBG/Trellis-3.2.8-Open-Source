            package co.vine.android.util.analytics;

            import android.content.Context;
            import co.vine.android.analytics.TimeTracker;
            import co.vine.android.cache.CacheKey;
            import co.vine.android.player.VideoViewInterface;
            import co.vine.android.prefetch.PrefetchManager;
            import com.edisonwang.android.slog.SLog;
            
            
    public class AnalyticsManager {
                private static final String CATEGORY_LAUNCH_TIME = "Launch Time";
                private static final String CATEGORY_VIDEO_LOAD = "Feed Video Loading";
                public static final String EVENT_APPLICATION_START = "application_start";
                public static final String EVENT_FIRST_VIDEO_PREPARED = "video_prepared";
                public static final String EVENT_LAST_ACTIVITY_START = "application_start";
                private static final String VARIABLE_ACTIVITY_START_TO_VIDEO_LOAD = "Feed Activity To First Video";
                private static final String VARIABLE_APP_START_TO_VIDEO_LOAD = "VineApplication To First Video";

        public static void onApplicationInit() {
            TimeTracker.onEventHappened("application_start");
                }

        public static void onStart(Context context) {
            FlurryUtils.start(context);
            TimeTracker.onEventHappened("application_start");
                }

        public static void onStop(Context context) {
            FlurryUtils.stop(context);
                }

        public static void onVideoLoaded(VideoViewInterface view) {
            Context context = view.getContext();
            if (TimeTracker.onFirstEventHappened(EVENT_FIRST_VIDEO_PREPARED)) {
                long applicationStartToVideoPrepare = TimeTracker.timeBetween("application_start", EVENT_FIRST_VIDEO_PREPARED);
                SLog.m442b("applicationStartToVideoPrepare {}ms", Long.valueOf(applicationStartToVideoPrepare));
                long activityStartToVideoPrepare = TimeTracker.timeBetween("application_start", EVENT_FIRST_VIDEO_PREPARED);
                SLog.m442b("activityStartToVideoPrepare {}ms", Long.valueOf(activityStartToVideoPrepare));
                    }
            CacheKey key = view.getCacheKey();
            if (key != null) {
                boolean wasSyncedRecently = System.currentTimeMillis() - PrefetchManager.getInstance(context).getLastSync() < 28800000;
                FlurryUtils.trackVideoLoadHits(wasSyncedRecently, key.getCacheState());
                    }
                }
            }
