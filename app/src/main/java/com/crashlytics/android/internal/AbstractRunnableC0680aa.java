            package com.crashlytics.android.internal;

            import android.os.Process;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.aa */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 10 */    public abstract class AbstractRunnableC0680aa implements Runnable {
                /* JADX INFO: renamed from: a */
                protected abstract void mo38a();

                @Override // java.lang.Runnable
/* 11 */        public final void run() {
/* 12 */            Process.setThreadPriority(10);
/* 13 */            mo38a();
                }
            }
