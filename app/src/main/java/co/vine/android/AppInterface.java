            package co.vine.android;

            import android.accounts.Account;
            import android.accounts.AccountManager;
            import android.app.Activity;
            import android.content.Context;
            import android.content.Intent;
            import org.json.JSONObject;
            
            
            public interface AppInterface {
                void appendDebugInfo(Context context, StringBuilder sb, boolean z);

                void clearUploadCaptchas(Context context);

                boolean doAddWidget(Context context, AccountManager accountManager, Account account);

                Intent getClearNotificationsIntent(Context context);

                Intent getDiscardAllIntent(Context context);

                Intent getNotifyFailedIntent(Context context);

                Intent getRecordingIntent(Activity activity, int i, String str);

                void setupWidget(Activity activity);

                void startCameraService(Activity activity);

                void startCapture(Activity activity);

                void startConversationActivity(Activity activity, long j, String str, long j2, boolean z, boolean z2);

                void startUploadsListActivity(Activity activity);

                void updateRecordConfigs(Context context, JSONObject jSONObject);
            }
