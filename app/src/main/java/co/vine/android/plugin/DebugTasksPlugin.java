            package co.vine.android.plugin;

            import android.app.Activity;
            import android.app.AlertDialog;
            import android.content.DialogInterface;
            import android.view.Menu;
            import android.view.MenuItem;
            import co.vine.android.player.SdkVideoView;
            import co.vine.android.util.CommonUtil;
            import co.vine.android.util.analytics.BehaviorManager;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    public class DebugTasksPlugin extends BasePlugin implements DialogInterface.OnClickListener {
                private static final String DEBUG_TASKS = "Debug Tasks";

                public interface DebugTask {
                    void performTask(Activity activity);
                }

/* 22 */        public enum DebugTasks {
                    VIEW_LOGS("View Logs", new LogReaderTask()),
                    DEBUG_SCREEN("Debug Screen", new DebugHomeTask()),
                    ENABLE_MAIN_THREAD_MEDIA_OPS("Thread Flag for SdkVideoView", new C04181()),
                    SHOW_USAGES("Show Viewing Statistics", new C04192()),
                    ENABLE_VERBOSE_DEBUG_FLAG("Verbose Debug Flag", new C04203());

                    private final String mLabel;
                    private final DebugTask mTask;

/* 23 */            static /* synthetic */ String access$000(DebugTasks x0) {
/* 24 */                return x0.mLabel;
                    }

/* 23 */            static /* synthetic */ DebugTask access$100(DebugTasks x0) {
/* 24 */                return x0.mTask;
                    }

                    /* JADX INFO: renamed from: co.vine.android.plugin.DebugTasksPlugin$DebugTasks$1 */
/* 25 */            static class C04181 implements DebugTask {
                        private boolean mWasEnabled = true;

/* 26 */                C04181() {
                        }

                        @Override // co.vine.android.plugin.DebugTasksPlugin.DebugTask
/* 30 */                public void performTask(Activity activity) {
/* 31 */                    this.mWasEnabled = !this.mWasEnabled;
/* 32 */                    SdkVideoView.setRunMediaOpsOnSeparateThread(this.mWasEnabled);
/* 33 */                    CommonUtil.showCenteredToast(activity, "Run on separate thread? {}", Boolean.valueOf(this.mWasEnabled));
                        }
                    }

                    /* JADX INFO: renamed from: co.vine.android.plugin.DebugTasksPlugin$DebugTasks$2 */
/* 34 */            static class C04192 implements DebugTask {
/* 35 */                C04192() {
                        }

                        @Override // co.vine.android.plugin.DebugTasksPlugin.DebugTask
/* 38 */                public void performTask(Activity activity) {
/* 39 */                    BehaviorManager.getInstance(activity).showUsageView(activity);
                        }
                    }

                    /* JADX INFO: renamed from: co.vine.android.plugin.DebugTasksPlugin$DebugTasks$3 */
/* 40 */            static class C04203 implements DebugTask {
/* 41 */                C04203() {
                        }

                        @Override // co.vine.android.plugin.DebugTasksPlugin.DebugTask
/* 44 */                public void performTask(Activity activity) {
/* 45 */                    CommonUtil.DEBUG_VERBOSE = !CommonUtil.DEBUG_VERBOSE;
/* 46 */                    CommonUtil.showCenteredToast(activity, "Debug Verbose: {}", Boolean.valueOf(CommonUtil.DEBUG_VERBOSE));
                        }
                    }

/* 52 */            DebugTasks(String label, DebugTask task) {
/* 54 */                this.mLabel = label;
/* 55 */                this.mTask = task;
                    }
                }

/* 59 */        public DebugTasksPlugin() {
/* 60 */            super("debug tasks");
                }

                @Override // co.vine.android.plugin.BasePlugin, co.vine.android.plugin.Plugin
/* 64 */        public boolean onCreateOptionsMenu(Menu menu) {
/* 65 */            super.onCreateOptionsMenu(menu);
/* 66 */            menu.add(DEBUG_TASKS);
/* 67 */            return true;
                }

                @Override // co.vine.android.plugin.BasePlugin, co.vine.android.plugin.Plugin
/* 71 */        public boolean onOptionsItemSelected(MenuItem item) {
/* 72 */            if (!DEBUG_TASKS.equals(item.getTitle())) {
                        return false;
                    }
/* 73 */            Activity activity = getActivity();
/* 74 */            if (activity != null) {
/* 75 */                DebugTasks[] tasks = DebugTasks.values();
/* 76 */                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
/* 77 */                CharSequence[] taskNames = new CharSequence[tasks.length];
/* 78 */                int i = 0;
                        for (DebugTasks t : tasks) {
/* 80 */                    taskNames[i] = DebugTasks.access$000(t);
/* 81 */                    i++;
                        }
/* 83 */                builder.setItems(taskNames, this);
/* 84 */                builder.create().show();
                    }
/* 86 */            return true;
                }

                @Override // android.content.DialogInterface.OnClickListener
/* 92 */        public void onClick(DialogInterface dialog, int which) {
/* 93 */            Activity activity = getActivity();
/* 94 */            if (activity != null) {
/* 95 */                DebugTasks.access$100(DebugTasks.values()[which]).performTask(activity);
                    }
/* 97 */            dialog.dismiss();
                }
            }
