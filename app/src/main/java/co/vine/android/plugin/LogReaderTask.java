            package co.vine.android.plugin;

            import android.app.Activity;
            import android.app.AlertDialog;
            import android.content.DialogInterface;
            import android.content.Intent;
            import android.os.Environment;
            import co.vine.android.ShowLogActivity;
            import co.vine.android.util.FileLogger;
            import com.edisonwang.android.slog.SLog;
            import java.io.File;
            import java.lang.ref.WeakReference;
            
            
    public class LogReaderTask implements DialogInterface.OnClickListener, DebugTasksPlugin.DebugTask {
                public static final File DELETE_LOG;
                public static final File NETWORK_LOG;
                public static final File PREFETCH_LOG;
                public static final File SERVICE_LOG;
                private static final File[] sFiles;
                private WeakReference<Activity> mActivity;
                private static final String LOG_DIR_NAME = "VineLogReader";
                private static final File LOG_DIR = new File(Environment.getExternalStorageDirectory(), LOG_DIR_NAME);

                static {
            LOG_DIR.mkdirs();
            SERVICE_LOG = new File(LOG_DIR, "VineService.txt");
            NETWORK_LOG = new File(LOG_DIR, "NetworkOperation.txt");
            PREFETCH_LOG = new File(LOG_DIR, "Prefetch.txt");
            DELETE_LOG = new File("Clear All Logs");
            sFiles = new File[]{SERVICE_LOG, NETWORK_LOG, PREFETCH_LOG, DELETE_LOG};
                }

                @Override 
        public void onClick(DialogInterface dialog, int which) {
            Activity activity = this.mActivity.get();
            if (activity != null) {
                if (sFiles[which] == DELETE_LOG) {
                    clearLogs();
                    return;
                        }
                SLog.m466i("adb pull /sdcard/{}/{}", LOG_DIR_NAME, sFiles[which].getName());
                Intent intent = new Intent(activity, (Class<?>) ShowLogActivity.class);
                intent.setAction(sFiles[which].getAbsolutePath());
                activity.startActivity(intent);
                    }
                }

                @Override 
        public void performTask(Activity activity) {
            this.mActivity = new WeakReference<>(activity);
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            CharSequence[] paths = new CharSequence[sFiles.length];
            int i = 0;
            File[] arr$ = sFiles;
                    for (File file : arr$) {
                paths[i] = file.getName();
                i++;
                    }
            builder.setItems(paths, this);
            builder.create().show();
                }

        public static void clearLogs() {
            File[] arr$ = sFiles;
                    for (File file : arr$) {
                if (file != DELETE_LOG) {
                    FileLogger.getLogger(file).clear();
                        }
                    }
                }
            }
