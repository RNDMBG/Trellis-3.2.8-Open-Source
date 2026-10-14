            package co.vine.android.plugin;

            import android.app.Activity;
            import android.content.Intent;
            import co.vine.android.DebugHomeActivity;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public class DebugHomeTask implements DebugTasksPlugin.DebugTask {
                @Override // co.vine.android.plugin.DebugTasksPlugin.DebugTask
/* 14 */        public void performTask(Activity activity) {
/* 15 */            activity.startActivity(new Intent(activity, (Class<?>) DebugHomeActivity.class));
                }
            }
