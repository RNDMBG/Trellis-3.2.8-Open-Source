            package co.vine.android.plugin;

            import android.app.Activity;
            import android.content.Intent;
            import co.vine.android.DebugHomeActivity;
            
            
    public class DebugHomeTask implements DebugTasksPlugin.DebugTask {
                @Override 
        public void performTask(Activity activity) {
            activity.startActivity(new Intent(activity, (Class<?>) DebugHomeActivity.class));
                }
            }
