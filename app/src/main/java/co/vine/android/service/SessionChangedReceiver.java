            package co.vine.android.service;

            import android.content.BroadcastReceiver;
            import android.content.Context;
            import android.content.Intent;
            import co.vine.android.client.VineAccountHelper;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 26 */    public class SessionChangedReceiver extends BroadcastReceiver {
                public static final String ACTION_SESSION_LOGIN = "co.vine.android.session.login";
                public static final String ACTION_SESSION_LOGOUT = "co.vine.android.session.logout";

                public static final class RecordSessionChangedReceiver extends SessionChangedReceiver {
                }

                public static final class UploadSessionChangedReceiver extends SessionChangedReceiver {
                }

                @Override // android.content.BroadcastReceiver
/* 27 */        public void onReceive(Context context, Intent intent) {
/* 28 */            String action = intent.getAction();
/* 29 */            if (ACTION_SESSION_LOGIN.equals(action) || ACTION_SESSION_LOGOUT.equals(action)) {
/* 30 */                VineAccountHelper.resetActiveSession();
                    }
                }

/* 34 */        public static Intent createSessionLoginIntent() {
/* 35 */            return new Intent(ACTION_SESSION_LOGIN);
                }

/* 38 */        public static Intent createSessionLogoutIntent() {
/* 39 */            return new Intent(ACTION_SESSION_LOGOUT);
                }
            }
