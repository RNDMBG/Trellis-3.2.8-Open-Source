            package android.support.v4.app;

            import android.app.Activity;
            import java.io.FileDescriptor;
            import java.io.PrintWriter;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 25 */    class ActivityCompatHoneycomb {
/* 26 */        ActivityCompatHoneycomb() {
                }

/* 28 */        static void invalidateOptionsMenu(Activity activity) {
/* 29 */            activity.invalidateOptionsMenu();
                }

/* 33 */        static void dump(Activity activity, String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
/* 34 */            activity.dump(prefix, fd, writer, args);
                }
            }
