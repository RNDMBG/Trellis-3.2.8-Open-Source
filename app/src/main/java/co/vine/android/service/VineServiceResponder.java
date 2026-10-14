            package co.vine.android.service;

            import android.os.Binder;
            import android.os.Bundle;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public abstract class VineServiceResponder extends Binder {
                public abstract void onServiceResponse(int i, int i2, String str, Bundle bundle);
            }
