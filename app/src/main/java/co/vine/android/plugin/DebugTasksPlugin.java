            package co.vine.android.plugin;

            import android.app.Activity;
            import android.app.AlertDialog;
            import android.content.DialogInterface;
            import android.view.Menu;
            import android.view.MenuItem;
            import co.vine.android.player.SdkVideoView;
            import co.vine.android.util.CommonUtil;
            import co.vine.android.util.analytics.BehaviorManager;
            
            
    public class DebugTasksPlugin extends BasePlugin implements DialogInterface.OnClickListener {
                private static final String DEBUG_TASKS = "Debug Tasks";

                public interface DebugTask {
                    void performTask(Activity activity);
                }

        public enum DebugTasks {
                    VIEW_LOGS("View Logs", new LogReaderTask()),
                    DEBUG_SCREEN("Debug Screen", new DebugHomeTask()),
                    ENABLE_MAIN_THREAD_MEDIA_OPS("Thread Flag for SdkVideoView", new C04181()),
                    SHOW_USAGES("Show Viewing Statistics", new C04192()),
                    ENABLE_VERBOSE_DEBUG_FLAG("Verbose Debug Flag", new C04203());

                    private final String mLabel;
                    private final DebugTask mTask;

            static  String access$000(DebugTasks x0) {
                return x0.mLabel;
                    }

            static  DebugTask access$100(DebugTasks x0) {
                return x0.mTask;
                    }

                    
            static class C04181 implements DebugTask {
                        private boolean mWasEnabled = true;

                C04181() {
                        }

                        @Override 
                public void performTask(Activity activity) {
                    this.mWasEnabled = !this.mWasEnabled;
                    SdkVideoView.setRunMediaOpsOnSeparateThread(this.mWasEnabled);
                    CommonUtil.showCenteredToast(activity, "Run on separate thread? {}", Boolean.valueOf(this.mWasEnabled));
                        }
                    }

                    
            static class C04192 implements DebugTask {
                C04192() {
                        }

                        @Override 
                public void performTask(Activity activity) {
                    BehaviorManager.getInstance(activity).showUsageView(activity);
                        }
                    }

                    
            static class C04203 implements DebugTask {
                C04203() {
                        }

                        @Override 
                public void performTask(Activity activity) {
                    CommonUtil.DEBUG_VERBOSE = !CommonUtil.DEBUG_VERBOSE;
                    CommonUtil.showCenteredToast(activity, "Debug Verbose: {}", Boolean.valueOf(CommonUtil.DEBUG_VERBOSE));
                        }
                    }

            DebugTasks(String label, DebugTask task) {
                this.mLabel = label;
                this.mTask = task;
                    }
                }

        public DebugTasksPlugin() {
            super("debug tasks");
                }

                @Override 
        public boolean onCreateOptionsMenu(Menu menu) {
            super.onCreateOptionsMenu(menu);
            menu.add(DEBUG_TASKS);
            return true;
                }

                @Override 
        public boolean onOptionsItemSelected(MenuItem item) {
            if (!DEBUG_TASKS.equals(item.getTitle())) {
                        return false;
                    }
            Activity activity = getActivity();
            if (activity != null) {
                DebugTasks[] tasks = DebugTasks.values();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                CharSequence[] taskNames = new CharSequence[tasks.length];
                int i = 0;
                        for (DebugTasks t : tasks) {
                    taskNames[i] = DebugTasks.access$000(t);
                    i++;
                        }
                builder.setItems(taskNames, this);
                builder.create().show();
                    }
            return true;
                }

                @Override 
        public void onClick(DialogInterface dialog, int which) {
            Activity activity = getActivity();
            if (activity != null) {
                DebugTasks.access$100(DebugTasks.values()[which]).performTask(activity);
                    }
            dialog.dismiss();
                }
            }
