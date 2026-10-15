            package co.vine.android;

            import co.vine.android.recorder.BasicVineRecorder;
            import co.vine.android.recorder.ProgressView;
            import com.facebook.FacebookRequestError;
            import com.facebook.widget.WebDialog;
            
            
    public class MergeResourceManager {
        public static void onApplicationInit() {
            ProgressView.initDefaultPirmaryColor(R.color.vine_green);
            WebDialog.initLoadingString(R.string.com_facebook_loading);
            FacebookRequestError.initRequestErrorPermissions(R.string.com_facebook_requesterror_permissions, R.string.com_facebook_requesterror_relogin, R.string.com_facebook_requesterror_web_login, R.string.com_facebook_requesterror_password_changed, R.string.com_facebook_requesterror_reconnect);
            BasicVineRecorder.initErrorStrings(R.string.waiting_on_camera);
                }
            }
