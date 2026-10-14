            package co.vine.android.recorder;

            import android.app.Activity;
            import android.content.ComponentName;
            import android.content.Intent;
            import android.content.ServiceConnection;
            import android.os.IBinder;
            import co.vine.android.service.ResourceService;
            import co.vine.android.util.CrashUtil;
            import com.edisonwang.android.slog.SLog;
            
            
    public class RecordingActivityHelper {
                private ServiceConnection mConnection = new ServiceConnectionC04821();
                public boolean mIsBound;

                
        class ServiceConnectionC04821 implements ServiceConnection {
            ServiceConnectionC04821() {
                    }

                    @Override 
            public void onServiceConnected(ComponentName className, IBinder service) {
                SLog.m464i("Camera service connected.");
                    }

                    @Override 
            public void onServiceDisconnected(ComponentName className) {
                SLog.m464i("Camera service disconnected.");
                    }
                }

        public void bindCameraService(Activity activity) {
            if (!this.mIsBound) {
                if (activity.bindService(new Intent(activity, (Class<?>) ResourceService.class), this.mConnection, 1)) {
                    this.mIsBound = true;
                    return;
                        } else {
                    CrashUtil.log("Failed to bind camera service.");
                    return;
                        }
                    }
            SLog.m446d("Camera service already binded. ");
                }

        public void unBindCameraService(Activity activity) {
            if (this.mIsBound) {
                activity.unbindService(this.mConnection);
                this.mIsBound = false;
                    }
                }
            }
