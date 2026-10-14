            package co.vine.android.service;

            import android.content.BroadcastReceiver;
            import android.content.Context;
            import android.content.Intent;
            import co.vine.android.Settings;
            import com.edisonwang.android.slog.SLog;
            
            
    public class GCMBroadcastReceiver extends BroadcastReceiver {
                private static final String TAG = "GCMBroadcastReceiver";

                @Override 
        public void onReceive(Context context, Intent intent) {
            SLog.dWithTag(TAG, "Message received!");
            if (Settings.isNotificationEnabled(context)) {
                Intent serviceIntent = new Intent(context, (Class<?>) GCMNotificationService.class);
                serviceIntent.putExtras(intent.getExtras());
                serviceIntent.setAction(GCMNotificationService.ACTION_NEW_NOTIFICATION);
                context.startService(serviceIntent);
                    }
            setResultCode(-1);
                }
            }
