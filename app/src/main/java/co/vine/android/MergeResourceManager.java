            package co.vine.android;

            import co.vine.android.recorder.BasicVineRecorder;
            import co.vine.android.recorder.ProgressView;
            import com.facebook.FacebookRequestError;
            import com.facebook.widget.WebDialog;
            
            
    public class MergeResourceManager {
        public static void onApplicationInit() {
            ProgressView.initDefaultPirmaryColor(C0257R.color.vine_green);
            WebDialog.initLoadingString(C0257R.string.com_facebook_loading);
            FacebookRequestError.initRequestErrorPermissions(C0257R.string.com_facebook_requesterror_permissions, C0257R.string.com_facebook_requesterror_relogin, C0257R.string.com_facebook_requesterror_web_login, C0257R.string.com_facebook_requesterror_password_changed, C0257R.string.com_facebook_requesterror_reconnect);
            BasicVineRecorder.initErrorStrings(C0257R.string.waiting_on_camera);
                }
            }
