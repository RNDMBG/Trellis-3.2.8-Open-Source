            package co.vine.android.service;

            import android.content.BroadcastReceiver;
            import android.content.Context;
            import android.content.Intent;
            import co.vine.android.client.VineAccountHelper;
            
            
    public class SessionChangedReceiver extends BroadcastReceiver {
                public static final String ACTION_SESSION_LOGIN = "co.vine.android.session.login";
                public static final String ACTION_SESSION_LOGOUT = "co.vine.android.session.logout";

                public static final class RecordSessionChangedReceiver extends SessionChangedReceiver {
                }

                public static final class UploadSessionChangedReceiver extends SessionChangedReceiver {
                }

                @Override 
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_SESSION_LOGIN.equals(action) || ACTION_SESSION_LOGOUT.equals(action)) {
                VineAccountHelper.resetActiveSession();
                    }
                }

        public static Intent createSessionLoginIntent() {
            return new Intent(ACTION_SESSION_LOGIN);
                }

        public static Intent createSessionLogoutIntent() {
            return new Intent(ACTION_SESSION_LOGOUT);
                }
            }
