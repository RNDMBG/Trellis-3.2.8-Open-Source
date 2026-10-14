            package co.vine.android.util;

            import android.support.annotation.Nullable;
            import co.vine.android.api.VinePrivateMessage;
            import co.vine.android.client.AppController;
            import co.vine.android.player.VideoViewInterface;
            import com.edisonwang.android.slog.MessageFormatter;
            import com.edisonwang.android.slog.SLog;
            import com.google.android.gms.games.GamesClient;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    public class SeekInfoManager {
                public static final SeekInfo NO_LOOP_LIMIT = new SeekInfo(System.currentTimeMillis() * 2, GamesClient.STATUS_REAL_TIME_CONNECTION_FAILED, Integer.MAX_VALUE);
                private final AppController mAppController;
                private final HashMap<Long, SeekInfo> mSeekInfoMap = new HashMap<>();
                private final HashMap<Long, Integer> mDurations = new HashMap<>();

/* 25 */        public SeekInfoManager(AppController appController) {
/* 27 */            this.mAppController = appController;
                }

/* 30 */        public SeekInfo get(long message) {
/* 31 */            return this.mSeekInfoMap.get(Long.valueOf(message));
                }

                @Nullable
/* 42 */        public SeekInfo getSeekInfo(VideoViewInterface view, VinePrivateMessage message) {
/* 43 */            SeekInfo seekInfo = this.mSeekInfoMap.get(Long.valueOf(message.messageId));
/* 44 */            if (seekInfo != null) {
                        return seekInfo;
                    }
                    if (message.localStartTime != 0) {
/* 46 */                Integer duration = this.mDurations.get(Long.valueOf(message.messageId));
/* 47 */                if (duration == null) {
/* 48 */                    if (view != null) {
/* 49 */                        duration = Integer.valueOf(view.getDuration());
/* 50 */                        if (duration.intValue() != -1) {
/* 51 */                            this.mDurations.put(Long.valueOf(message.messageId), duration);
                                }
                            } else {
/* 54 */                        throw new IllegalStateException("Duration cannot be determined.");
                            }
                        }
/* 57 */                int durationPadded = duration.intValue() <= 0 ? GamesClient.STATUS_REAL_TIME_CONNECTION_FAILED : duration.intValue() + 100;
/* 58 */                SeekInfo seekInfo2 = new SeekInfo(message.localStartTime, durationPadded, message.maxLoops);
/* 59 */                SLog.m447d("Adding SeekInfo: {}.", seekInfo2);
/* 60 */                this.mSeekInfoMap.put(Long.valueOf(message.messageId), seekInfo2);
                        return seekInfo2;
                    }
/* 64 */            return null;
                }

/* 72 */        public SeekInfo markAsRead(int duration, VinePrivateMessage message) {
/* 73 */            int durationPadded = duration <= 0 ? GamesClient.STATUS_REAL_TIME_CONNECTION_FAILED : duration + 100;
/* 74 */            long startTime = System.currentTimeMillis();
/* 75 */            message.localStartTime = startTime;
/* 76 */            SeekInfo seekInfo = new SeekInfo(startTime, durationPadded, message.maxLoops);
/* 77 */            SLog.m447d("Adding SeekInfo: {}.", seekInfo);
/* 78 */            this.mSeekInfoMap.put(Long.valueOf(message.messageId), seekInfo);
/* 79 */            SLog.m455e("Mark as read.");
/* 80 */            this.mAppController.markMessageLoopViewed(message);
/* 81 */            return seekInfo;
                }

/* 88 */        public static class SeekInfo {
                    public final int durationPadded;
                    public final int maxLoops;
                    public long startTime;

/* 89 */            public SeekInfo(long startTime, int durationPadded, int maxLoops) {
/* 91 */                this.startTime = startTime;
/* 92 */                this.durationPadded = durationPadded;
/* 93 */                this.maxLoops = maxLoops;
                    }

/* 96 */            public boolean hasLoopLimit() {
                        return this != SeekInfoManager.NO_LOOP_LIMIT;
                    }

/* 100 */           public int getLoopsLeft() {
/* 101 */               return this.maxLoops - ((int) Math.floor((System.currentTimeMillis() - this.startTime) / ((long) this.durationPadded)));
                    }

/* 104 */           public boolean isExpired() {
                        return hasLoopLimit() && getLoopsLeft() <= 0;
                    }

/* 108 */           public int getSeekTime() {
/* 109 */               if (isExpired()) {
                            return -1;
                        }
/* 110 */               return ((int) (System.currentTimeMillis() - this.startTime)) % this.durationPadded;
                    }

/* 117 */           public String toString() {
/* 118 */               return MessageFormatter.format("Start time: {}, durationPadded {}, maxLoops{}, loopsLeft {}.", new Object[]{Long.valueOf(this.startTime), Integer.valueOf(this.durationPadded), Integer.valueOf(this.maxLoops), Integer.valueOf(getLoopsLeft())}).getMessage();
                    }

/* 122 */           public String getLoopsLeftForDisplay() {
/* 123 */               return String.valueOf(getLoopsLeft());
                    }
                }
            }
