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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    public class LogReaderTask implements DialogInterface.OnClickListener, DebugTasksPlugin.DebugTask {
                public static final File DELETE_LOG;
                public static final File NETWORK_LOG;
                public static final File PREFETCH_LOG;
                public static final File SERVICE_LOG;
                private static final File[] sFiles;
                private WeakReference<Activity> mActivity;
                private static final String LOG_DIR_NAME = "VineLogReader";
                private static final File LOG_DIR = new File(Environment.getExternalStorageDirectory(), LOG_DIR_NAME);

                static {
/* 26 */            LOG_DIR.mkdirs();
/* 29 */            SERVICE_LOG = new File(LOG_DIR, "VineService.txt");
/* 30 */            NETWORK_LOG = new File(LOG_DIR, "NetworkOperation.txt");
/* 31 */            PREFETCH_LOG = new File(LOG_DIR, "Prefetch.txt");
/* 32 */            DELETE_LOG = new File("Clear All Logs");
/* 33 */            sFiles = new File[]{SERVICE_LOG, NETWORK_LOG, PREFETCH_LOG, DELETE_LOG};
                }

                @Override // android.content.DialogInterface.OnClickListener
/* 37 */        public void onClick(DialogInterface dialog, int which) {
/* 38 */            Activity activity = this.mActivity.get();
/* 39 */            if (activity != null) {
/* 40 */                if (sFiles[which] == DELETE_LOG) {
/* 41 */                    clearLogs();
/* 49 */                    return;
                        }
/* 43 */                SLog.m466i("adb pull /sdcard/{}/{}", LOG_DIR_NAME, sFiles[which].getName());
/* 44 */                Intent intent = new Intent(activity, (Class<?>) ShowLogActivity.class);
/* 45 */                intent.setAction(sFiles[which].getAbsolutePath());
/* 46 */                activity.startActivity(intent);
                    }
                }

                @Override // co.vine.android.plugin.DebugTasksPlugin.DebugTask
/* 52 */        public void performTask(Activity activity) {
/* 53 */            this.mActivity = new WeakReference<>(activity);
/* 54 */            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
/* 55 */            CharSequence[] paths = new CharSequence[sFiles.length];
/* 56 */            int i = 0;
/* 57 */            File[] arr$ = sFiles;
                    for (File file : arr$) {
/* 58 */                paths[i] = file.getName();
/* 59 */                i++;
                    }
/* 61 */            builder.setItems(paths, this);
/* 62 */            builder.create().show();
                }

/* 65 */        public static void clearLogs() {
/* 66 */            File[] arr$ = sFiles;
                    for (File file : arr$) {
/* 67 */                if (file != DELETE_LOG) {
/* 68 */                    FileLogger.getLogger(file).clear();
                        }
                    }
                }
            }
