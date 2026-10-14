            package co.vine.android.util;

            import android.os.HandlerThread;
            import android.os.Process;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 31 */    public class PriorityHandlerThread extends HandlerThread {
                private final int priority;

/* 32 */        public PriorityHandlerThread(String name, int priority) {
/* 33 */            super(name);
/* 34 */            this.priority = priority;
                }

                @Override // android.os.HandlerThread, java.lang.Thread, java.lang.Runnable
/* 38 */        public void run() {
/* 39 */            Process.setThreadPriority(this.priority);
/* 40 */            super.run();
                }
            }
