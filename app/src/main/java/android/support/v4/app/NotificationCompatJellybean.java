            package android.support.v4.app;

            import android.app.Notification;
            import android.app.PendingIntent;
            import android.content.Context;
            import android.graphics.Bitmap;
            import android.widget.RemoteViews;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 31 */    class NotificationCompatJellybean {

                /* JADX INFO: renamed from: b */
                private Notification.Builder f0b;

/* 32 */        public NotificationCompatJellybean(Context context, Notification n, CharSequence contentTitle, CharSequence contentText, CharSequence contentInfo, RemoteViews tickerView, int number, PendingIntent contentIntent, PendingIntent fullScreenIntent, Bitmap largeIcon, int mProgressMax, int mProgress, boolean mProgressIndeterminate, boolean useChronometer, int priority, CharSequence subText) {
                    this.f0b = new Notification.Builder(context).setWhen(n.when).setSmallIcon(n.icon, n.iconLevel).setContent(n.contentView).setTicker(n.tickerText, tickerView).setSound(n.sound, n.audioStreamType).setVibrate(n.vibrate).setLights(n.ledARGB, n.ledOnMS, n.ledOffMS).setOngoing((n.flags & 2) != 0).setOnlyAlertOnce((n.flags & 8) != 0).setAutoCancel((n.flags & 16) != 0).setDefaults(n.defaults).setContentTitle(contentTitle).setContentText(contentText).setSubText(subText).setContentInfo(contentInfo).setContentIntent(contentIntent).setDeleteIntent(n.deleteIntent).setFullScreenIntent(fullScreenIntent, (n.flags & 128) != 0).setLargeIcon(largeIcon).setNumber(number).setUsesChronometer(useChronometer).setPriority(priority).setProgress(mProgressMax, mProgress, mProgressIndeterminate);
                }

/* 61 */        public void addAction(int icon, CharSequence title, PendingIntent intent) {
/* 62 */            this.f0b.addAction(icon, title, intent);
                }

/* 66 */        public void addBigTextStyle(CharSequence bigContentTitle, boolean useSummary, CharSequence summaryText, CharSequence bigText) {
/* 67 */            Notification.BigTextStyle style = new Notification.BigTextStyle(this.f0b).setBigContentTitle(bigContentTitle).bigText(bigText);
/* 70 */            if (useSummary) {
/* 71 */                style.setSummaryText(summaryText);
                    }
                }

/* 77 */        public void addBigPictureStyle(CharSequence bigContentTitle, boolean useSummary, CharSequence summaryText, Bitmap bigPicture, Bitmap bigLargeIcon, boolean bigLargeIconSet) {
/* 78 */            Notification.BigPictureStyle style = new Notification.BigPictureStyle(this.f0b).setBigContentTitle(bigContentTitle).bigPicture(bigPicture);
/* 81 */            if (bigLargeIconSet) {
/* 82 */                style.bigLargeIcon(bigLargeIcon);
                    }
/* 84 */            if (useSummary) {
/* 85 */                style.setSummaryText(summaryText);
                    }
                }

/* 90 */        public void addInboxStyle(CharSequence bigContentTitle, boolean useSummary, CharSequence summaryText, ArrayList<CharSequence> texts) {
/* 91 */            Notification.InboxStyle style = new Notification.InboxStyle(this.f0b).setBigContentTitle(bigContentTitle);
/* 93 */            if (useSummary) {
/* 94 */                style.setSummaryText(summaryText);
                    }
                    for (CharSequence text : texts) {
/* 97 */                style.addLine(text);
                    }
                }

/* 101 */       public Notification build() {
/* 102 */           return this.f0b.build();
                }
            }
