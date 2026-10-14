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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    public class DeviceBootReceiver extends BroadcastReceiver {
                @Override // android.content.BroadcastReceiver
/* 22 */        public void onReceive(Context context, Intent intent) {
                    Uri data;
/* 23 */            String action = intent.getAction();
/* 24 */            SLog.m447d("Received device boot message {}.", action);
/* 25 */            if (!"android.intent.action.PACKAGE_REPLACED".equals(action) || (data = intent.getData()) == null || context.getPackageName().equals(data.getSchemeSpecificPart())) {
                        try {
/* 32 */                    context.startService(new Intent(context, (Class<?>) ResourceService.class));
/* 33 */                    context.startService(new Intent(context, (Class<?>) VineService.class).setAction(VineService.ACTION_DEVICE_BOOT).putExtras(Bundle.EMPTY));
                        } catch (SecurityException e) {
/* 38 */                    CrashUtil.logException(e);
                        }
                    }
                }
            }
