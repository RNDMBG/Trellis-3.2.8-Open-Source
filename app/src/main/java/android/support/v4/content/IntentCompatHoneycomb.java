            package android.support.v4.content;

            import android.content.ComponentName;
            import android.content.Intent;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 20 */    class IntentCompatHoneycomb {
/* 21 */        IntentCompatHoneycomb() {
                }

/* 23 */        public static Intent makeMainActivity(ComponentName mainActivity) {
/* 24 */            return Intent.makeMainActivity(mainActivity);
                }

/* 27 */        public static Intent makeRestartActivityTask(ComponentName mainActivity) {
/* 28 */            return Intent.makeRestartActivityTask(mainActivity);
                }
            }
