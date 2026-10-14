            package co.vine.android.util;

            import android.telephony.SmsManager;
            import com.googlecode.javacv.cpp.avcodec;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 8 */     public class SMSUtil {
/* 9 */         public static boolean sendSMS(String dest, String message) {
/* 10 */            SmsManager defaultManager = SmsManager.getDefault();
/* 11 */            if (message.length() > 140) {
/* 12 */                message = message.substring(0, avcodec.AV_CODEC_ID_YOP);
/* 13 */                CrashUtil.log("Message is truncated.");
                    }
/* 15 */            if (defaultManager == null) {
                        return false;
                    }
                    try {
/* 17 */                defaultManager.sendTextMessage(dest, null, message, null, null);
                    } catch (Exception e) {
/* 19 */                CrashUtil.logException(e, "Failed to send: {" + message + "}", new Object[0]);
                    }
/* 21 */            return true;
                }
            }
