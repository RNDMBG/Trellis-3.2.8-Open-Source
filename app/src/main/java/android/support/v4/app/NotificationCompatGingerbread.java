            package android.support.v4.app;

            import android.app.Notification;
            import android.app.PendingIntent;
            import android.content.Context;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    class NotificationCompatGingerbread {
/* 22 */        NotificationCompatGingerbread() {
                }

/* 26 */        public static Notification add(Notification notification, Context context, CharSequence contentTitle, CharSequence contentText, PendingIntent contentIntent, PendingIntent fullScreenIntent) {
/* 27 */            notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
/* 28 */            notification.fullScreenIntent = fullScreenIntent;
/* 29 */            return notification;
                }
            }
