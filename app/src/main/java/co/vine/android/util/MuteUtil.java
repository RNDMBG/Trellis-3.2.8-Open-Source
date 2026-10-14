            package co.vine.android.util;

            import android.content.Context;
            import android.content.Intent;
            import android.content.IntentFilter;
            import android.content.SharedPreferences;
            import com.edisonwang.android.slog.SLog;
            
            
    public class MuteUtil {
                public static final String ACTION_CHANGED_TO_MUTE = SLog.getAuthority() + ".muted";
                public static final String ACTION_CHANGED_TO_UNMUTE = SLog.getAuthority() + ".un_muted";
                public static final IntentFilter MUTE_INTENT_FILTER = new IntentFilter();
                private static final String PREF_MUTE = "mute";

                
                private static SharedPreferences f114sp;

                static {
            MUTE_INTENT_FILTER.addAction(ACTION_CHANGED_TO_MUTE);
            MUTE_INTENT_FILTER.addAction(ACTION_CHANGED_TO_UNMUTE);
                }

        public static synchronized void setMuted(Context context, boolean mute) {
                    try {
                        if (f114sp == null) {
                    f114sp = context.getSharedPreferences(PREF_MUTE, 0);
                        }
                f114sp.edit().putBoolean(PREF_MUTE, mute).apply();
                SLog.m446d("Mute state change send.");
                context.sendBroadcast(new Intent(mute ? ACTION_CHANGED_TO_MUTE : ACTION_CHANGED_TO_UNMUTE), CrossConstants.BROADCAST_PERMISSION);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

        public static synchronized boolean isMuted(Context context) {
                    try {
                        if (f114sp == null) {
                    f114sp = context.getSharedPreferences(PREF_MUTE, 0);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
            return f114sp.getBoolean(PREF_MUTE, false);
                }
            }
