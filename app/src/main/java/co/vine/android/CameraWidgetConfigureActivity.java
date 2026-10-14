            package co.vine.android;

            import android.app.Activity;
            import android.content.Context;
            import android.content.Intent;
            import android.os.Bundle;
            import android.widget.Toast;
            import co.vine.android.util.analytics.FlurryUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 15 */    public class CameraWidgetConfigureActivity extends Activity {
                @Override // android.app.Activity
/* 16 */        protected void onCreate(Bundle savedInstanceState) {
/* 17 */            super.onCreate(savedInstanceState);
/* 18 */            setupShortcut(this);
/* 19 */            Toast.makeText(this, C0257R.string.widget_added_toast, 0).show();
/* 20 */            FlurryUtils.trackCameraWidgetAdded();
/* 21 */            setResult(0);
/* 22 */            finish();
                }

/* 25 */        public static void setupShortcut(Context context) {
/* 26 */            Intent shortcutIntent = new Intent(context.getApplicationContext(), (Class<?>) StartActivity.class);
/* 28 */            shortcutIntent.setAction(StartActivity.ACTION_RECORD);
/* 29 */            shortcutIntent.setFlags(131072);
/* 30 */            Intent addIntent = new Intent();
/* 31 */            addIntent.putExtra("android.intent.extra.shortcut.INTENT", shortcutIntent);
/* 32 */            addIntent.putExtra("android.intent.extra.shortcut.NAME", context.getText(C0257R.string.camera_widget_name));
/* 33 */            addIntent.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", Intent.ShortcutIconResource.fromContext(context.getApplicationContext(), C0257R.drawable.ic_launcher_capture_widget));
/* 36 */            addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
/* 37 */            context.getApplicationContext().sendBroadcast(addIntent);
                }
            }
