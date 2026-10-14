            package co.vine.android.recorder;

            import android.app.Activity;
            import android.content.ComponentName;
            import android.content.Intent;
            import android.content.ServiceConnection;
            import android.os.IBinder;
            import co.vine.android.service.ResourceService;
            import co.vine.android.util.CrashUtil;
            import com.edisonwang.android.slog.SLog;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    public class RecordingActivityHelper {
                private ServiceConnection mConnection = new ServiceConnectionC04821();
                public boolean mIsBound;

                /* JADX INFO: renamed from: co.vine.android.recorder.RecordingActivityHelper$1 */
/* 24 */        class ServiceConnectionC04821 implements ServiceConnection {
/* 25 */            ServiceConnectionC04821() {
                    }

                    @Override // android.content.ServiceConnection
/* 28 */            public void onServiceConnected(ComponentName className, IBinder service) {
/* 29 */                SLog.m464i("Camera service connected.");
                    }

                    @Override // android.content.ServiceConnection
/* 32 */            public void onServiceDisconnected(ComponentName className) {
/* 33 */                SLog.m464i("Camera service disconnected.");
                    }
                }

/* 37 */        public void bindCameraService(Activity activity) {
/* 38 */            if (!this.mIsBound) {
/* 39 */                if (activity.bindService(new Intent(activity, (Class<?>) ResourceService.class), this.mConnection, 1)) {
/* 41 */                    this.mIsBound = true;
/* 48 */                    return;
                        } else {
/* 43 */                    CrashUtil.log("Failed to bind camera service.");
/* 48 */                    return;
                        }
                    }
/* 46 */            SLog.m446d("Camera service already binded. ");
                }

/* 50 */        public void unBindCameraService(Activity activity) {
/* 51 */            if (this.mIsBound) {
/* 52 */                activity.unbindService(this.mConnection);
/* 53 */                this.mIsBound = false;
                    }
                }
            }
