            package co.vine.android.service;

            import android.os.Bundle;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 7 */     public class PendingAction {
                public final int actionCode;
                public final Bundle bundle;

/* 8 */         public PendingAction(int actionCode, Bundle bundle) {
/* 10 */            if (bundle == null) {
/* 11 */                throw new IllegalArgumentException("Bundle cannot be null.");
                    }
/* 13 */            this.actionCode = actionCode;
/* 14 */            this.bundle = bundle;
                }
            }
