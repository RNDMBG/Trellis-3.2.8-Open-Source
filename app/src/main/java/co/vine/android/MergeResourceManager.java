            package co.vine.android;

            import co.vine.android.recorder.BasicVineRecorder;
            import co.vine.android.recorder.ProgressView;
            import com.facebook.FacebookRequestError;
            import com.facebook.widget.WebDialog;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 12 */    public class MergeResourceManager {
/* 13 */        public static void onApplicationInit() {
/* 14 */            ProgressView.initDefaultPirmaryColor(C0257R.color.vine_green);
/* 15 */            WebDialog.initLoadingString(C0257R.string.com_facebook_loading);
/* 16 */            FacebookRequestError.initRequestErrorPermissions(C0257R.string.com_facebook_requesterror_permissions, C0257R.string.com_facebook_requesterror_relogin, C0257R.string.com_facebook_requesterror_web_login, C0257R.string.com_facebook_requesterror_password_changed, C0257R.string.com_facebook_requesterror_reconnect);
/* 22 */            BasicVineRecorder.initErrorStrings(C0257R.string.waiting_on_camera);
                }
            }
