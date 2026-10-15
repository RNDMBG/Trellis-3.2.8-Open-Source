            package co.vine.android.service;

            import android.app.NotificationChannel;
            import android.app.NotificationManager;
            import android.content.Context;
            import android.os.Build;
            import co.vine.android.R;


     public final class NotificationChannels {
                 public static final String ACTIVITY = "vine_activity";

         private NotificationChannels() {
                 }

        public static void ensure(Context context) {
                     if (Build.VERSION.SDK_INT < 26) {
                         return;
                     }
                     try {
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                if (manager == null || manager.getNotificationChannel(ACTIVITY) != null) {
                    return;
                         }
                NotificationChannel channel = new NotificationChannel(ACTIVITY, context.getString(R.string.notif_channel_activity), NotificationManager.IMPORTANCE_DEFAULT);
                manager.createNotificationChannel(channel);
                     } catch (Exception ignored) {
                     }
                 }
             }
