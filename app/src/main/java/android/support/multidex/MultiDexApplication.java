            package android.support.multidex;

            import android.app.Application;
            import android.content.Context;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 36 */    public class MultiDexApplication extends Application {
                @Override // android.content.ContextWrapper
/* 37 */        protected void attachBaseContext(Context base) {
/* 38 */            super.attachBaseContext(base);
/* 39 */            MultiDex.install(this);
                }
            }
