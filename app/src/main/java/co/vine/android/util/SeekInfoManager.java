            package co.vine.android.util;

            import android.support.annotation.Nullable;
            import co.vine.android.api.VinePrivateMessage;
            import co.vine.android.client.AppController;
            import co.vine.android.player.VideoViewInterface;
            import com.edisonwang.android.slog.MessageFormatter;
            import com.edisonwang.android.slog.SLog;
            import com.google.android.gms.games.GamesClient;
            import java.util.HashMap;
            
            
    public class SeekInfoManager {
                public static final SeekInfo NO_LOOP_LIMIT = new SeekInfo(System.currentTimeMillis() * 2, GamesClient.STATUS_REAL_TIME_CONNECTION_FAILED, Integer.MAX_VALUE);
                private final AppController mAppController;
                private final HashMap<Long, SeekInfo> mSeekInfoMap = new HashMap<>();
                private final HashMap<Long, Integer> mDurations = new HashMap<>();

        public SeekInfoManager(AppController appController) {
            this.mAppController = appController;
                }

        public SeekInfo get(long message) {
            return this.mSeekInfoMap.get(Long.valueOf(message));
                }

                @Nullable
        public SeekInfo getSeekInfo(VideoViewInterface view, VinePrivateMessage message) {
            SeekInfo seekInfo = this.mSeekInfoMap.get(Long.valueOf(message.messageId));
            if (seekInfo != null) {
                        return seekInfo;
                    }
                    if (message.localStartTime != 0) {
                Integer duration = this.mDurations.get(Long.valueOf(message.messageId));
                if (duration == null) {
                    if (view != null) {
                        duration = Integer.valueOf(view.getDuration());
                        if (duration.intValue() != -1) {
                            this.mDurations.put(Long.valueOf(message.messageId), duration);
                                }
                            } else {
                        throw new IllegalStateException("Duration cannot be determined.");
                            }
                        }
                int durationPadded = duration.intValue() <= 0 ? GamesClient.STATUS_REAL_TIME_CONNECTION_FAILED : duration.intValue() + 100;
                SeekInfo seekInfo2 = new SeekInfo(message.localStartTime, durationPadded, message.maxLoops);
                SLog.m447d("Adding SeekInfo: {}.", seekInfo2);
                this.mSeekInfoMap.put(Long.valueOf(message.messageId), seekInfo2);
                        return seekInfo2;
                    }
            return null;
                }

        public SeekInfo markAsRead(int duration, VinePrivateMessage message) {
            int durationPadded = duration <= 0 ? GamesClient.STATUS_REAL_TIME_CONNECTION_FAILED : duration + 100;
            long startTime = System.currentTimeMillis();
            message.localStartTime = startTime;
            SeekInfo seekInfo = new SeekInfo(startTime, durationPadded, message.maxLoops);
            SLog.m447d("Adding SeekInfo: {}.", seekInfo);
            this.mSeekInfoMap.put(Long.valueOf(message.messageId), seekInfo);
            SLog.m455e("Mark as read.");
            this.mAppController.markMessageLoopViewed(message);
            return seekInfo;
                }

        public static class SeekInfo {
                    public final int durationPadded;
                    public final int maxLoops;
                    public long startTime;

            public SeekInfo(long startTime, int durationPadded, int maxLoops) {
                this.startTime = startTime;
                this.durationPadded = durationPadded;
                this.maxLoops = maxLoops;
                    }

            public boolean hasLoopLimit() {
                        return this != SeekInfoManager.NO_LOOP_LIMIT;
                    }

           public int getLoopsLeft() {
               return this.maxLoops - ((int) Math.floor((System.currentTimeMillis() - this.startTime) / ((long) this.durationPadded)));
                    }

           public boolean isExpired() {
                        return hasLoopLimit() && getLoopsLeft() <= 0;
                    }

           public int getSeekTime() {
               if (isExpired()) {
                            return -1;
                        }
               return ((int) (System.currentTimeMillis() - this.startTime)) % this.durationPadded;
                    }

           public String toString() {
               return MessageFormatter.format("Start time: {}, durationPadded {}, maxLoops{}, loopsLeft {}.", new Object[]{Long.valueOf(this.startTime), Integer.valueOf(this.durationPadded), Integer.valueOf(this.maxLoops), Integer.valueOf(getLoopsLeft())}).getMessage();
                    }

           public String getLoopsLeftForDisplay() {
               return String.valueOf(getLoopsLeft());
                    }
                }
            }
