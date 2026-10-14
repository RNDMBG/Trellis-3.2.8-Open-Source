            package co.vine.android.util;

            import android.content.Context;
            import android.content.Intent;
            import android.content.IntentFilter;
            import android.content.SharedPreferences;
            import com.edisonwang.android.slog.SLog;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 12 */    public class MuteUtil {
                public static final String ACTION_CHANGED_TO_MUTE = SLog.getAuthority() + ".muted";
                public static final String ACTION_CHANGED_TO_UNMUTE = SLog.getAuthority() + ".un_muted";
                public static final IntentFilter MUTE_INTENT_FILTER = new IntentFilter();
                private static final String PREF_MUTE = "mute";

                /* JADX INFO: renamed from: sp */
                private static SharedPreferences f114sp;

                static {
/* 22 */            MUTE_INTENT_FILTER.addAction(ACTION_CHANGED_TO_MUTE);
/* 23 */            MUTE_INTENT_FILTER.addAction(ACTION_CHANGED_TO_UNMUTE);
                }

/* 28 */        public static synchronized void setMuted(Context context, boolean mute) {
                    try {
                        if (f114sp == null) {
/* 30 */                    f114sp = context.getSharedPreferences(PREF_MUTE, 0);
                        }
/* 32 */                f114sp.edit().putBoolean(PREF_MUTE, mute).apply();
/* 33 */                SLog.m446d("Mute state change send.");
/* 34 */                context.sendBroadcast(new Intent(mute ? ACTION_CHANGED_TO_MUTE : ACTION_CHANGED_TO_UNMUTE), CrossConstants.BROADCAST_PERMISSION);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 37 */        public static synchronized boolean isMuted(Context context) {
                    try {
                        if (f114sp == null) {
/* 39 */                    f114sp = context.getSharedPreferences(PREF_MUTE, 0);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
/* 41 */            return f114sp.getBoolean(PREF_MUTE, false);
                }
            }
