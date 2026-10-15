            package co.vine.android;

            import android.content.BroadcastReceiver;
            import android.content.Context;
            import android.content.Intent;
            import android.net.Uri;
            import android.os.Bundle;
            import co.vine.android.service.ResourceService;
            import co.vine.android.service.VineService;
            import co.vine.android.util.CrashUtil;
            import com.edisonwang.android.slog.SLog;
            
            
    public class DeviceBootReceiver extends BroadcastReceiver {
                @Override 
        public void onReceive(Context context, Intent intent) {
                    Uri data;
            String action = intent.getAction();
            SLog.m447d("Received device boot message {}.", action);
            if (!"android.intent.action.PACKAGE_REPLACED".equals(action) || (data = intent.getData()) == null || context.getPackageName().equals(data.getSchemeSpecificPart())) {
                         try {
                     co.vine.android.service.ActivityPollJobService.schedule(context);
                         } catch (Exception ignored) {
                         }
                         try {
                     context.startService(new Intent(context, (Class<?>) ResourceService.class));
                     context.startService(new Intent(context, (Class<?>) VineService.class).setAction(VineService.ACTION_DEVICE_BOOT).putExtras(Bundle.EMPTY));
                         } catch (SecurityException e) {
                    CrashUtil.logException(e);
                        }
                    }
                }
            }
