            package co.vine.android.service;

            import android.content.BroadcastReceiver;
            import android.content.Context;
            import android.content.Intent;
            import co.vine.android.Settings;
            import com.edisonwang.android.slog.SLog;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    public class GCMBroadcastReceiver extends BroadcastReceiver {
                private static final String TAG = "GCMBroadcastReceiver";

                @Override // android.content.BroadcastReceiver
/* 22 */        public void onReceive(Context context, Intent intent) {
/* 23 */            SLog.dWithTag(TAG, "Message received!");
/* 24 */            if (Settings.isNotificationEnabled(context)) {
/* 25 */                Intent serviceIntent = new Intent(context, (Class<?>) GCMNotificationService.class);
/* 26 */                serviceIntent.putExtras(intent.getExtras());
/* 27 */                serviceIntent.setAction(GCMNotificationService.ACTION_NEW_NOTIFICATION);
/* 28 */                context.startService(serviceIntent);
                    }
/* 30 */            setResultCode(-1);
                }
            }
