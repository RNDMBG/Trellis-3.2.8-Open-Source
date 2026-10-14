            package co.vine.android.util;

            import android.content.Context;
            import co.vine.android.C0257R;
            import co.vine.android.client.AppController;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 12 */    public class ShareUtils {
/* 13 */        public static String getSmsMessage(Context context) {
/* 14 */            BuildUtil.getMarket();
/* 17 */            return getShareMyProfileMessage(context, C0257R.string.find_friends_invite_sms);
                }

/* 22 */        public static String getEmailMessage(Context context) {
/* 23 */            BuildUtil.getMarket();
/* 26 */            return getShareMyProfileMessage(context, C0257R.string.find_friends_invite_email);
                }

/* 31 */        private static String getShareMyProfileMessage(Context context, int stringId) {
                    return context.getString(stringId, String.valueOf(AppController.getInstance(context).getActiveId())).replace("vine://user", BuildUtil.getWebsiteUrl(context) + "/u");
                }
            }
