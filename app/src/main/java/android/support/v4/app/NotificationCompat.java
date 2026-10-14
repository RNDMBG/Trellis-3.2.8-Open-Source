            package android.support.v4.app;

            import android.app.Notification;
            import android.app.PendingIntent;
            import android.content.Context;
            import android.graphics.Bitmap;
            import android.net.Uri;
            import android.os.Build;
            import android.widget.RemoteViews;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 32 */    public class NotificationCompat {
                public static final int FLAG_HIGH_PRIORITY = 128;
                private static final NotificationCompatImpl IMPL;
                public static final int PRIORITY_DEFAULT = 0;
                public static final int PRIORITY_HIGH = 1;
                public static final int PRIORITY_LOW = -1;
                public static final int PRIORITY_MAX = 2;
                public static final int PRIORITY_MIN = -2;

                interface NotificationCompatImpl {
                    Notification build(Builder builder);
                }

/* 33 */        static /* synthetic */ NotificationCompatImpl access$000() {
/* 34 */            return IMPL;
                }

/* 83 */        static class NotificationCompatImplBase implements NotificationCompatImpl {
/* 84 */            NotificationCompatImplBase() {
                    }

                    @Override // android.support.v4.app.NotificationCompat.NotificationCompatImpl
/* 86 */            public Notification build(Builder b) {
/* 87 */                Notification result = b.mNotification;
/* 88 */                result.setLatestEventInfo(b.mContext, b.mContentTitle, b.mContentText, b.mContentIntent);
/* 91 */                if (b.mPriority > 0) {
/* 92 */                    result.flags |= 128;
                        }
/* 94 */                return result;
                    }
                }

/* 96 */        static class NotificationCompatImplGingerbread extends NotificationCompatImplBase {
/* 97 */            NotificationCompatImplGingerbread() {
                    }

                    @Override // android.support.v4.app.NotificationCompat.NotificationCompatImplBase, android.support.v4.app.NotificationCompat.NotificationCompatImpl
/* 99 */            public Notification build(Builder b) {
/* 100 */               Notification result = b.mNotification;
/* 101 */               result.setLatestEventInfo(b.mContext, b.mContentTitle, b.mContentText, b.mContentIntent);
/* 103 */               Notification result2 = NotificationCompatGingerbread.add(result, b.mContext, b.mContentTitle, b.mContentText, b.mContentIntent, b.mFullScreenIntent);
/* 106 */               if (b.mPriority > 0) {
/* 107 */                   result2.flags |= 128;
                        }
/* 109 */               return result2;
                    }
                }

/* 111 */       static class NotificationCompatImplHoneycomb implements NotificationCompatImpl {
/* 112 */           NotificationCompatImplHoneycomb() {
                    }

                    @Override // android.support.v4.app.NotificationCompat.NotificationCompatImpl
/* 114 */           public Notification build(Builder b) {
/* 115 */               return NotificationCompatHoneycomb.add(b.mContext, b.mNotification, b.mContentTitle, b.mContentText, b.mContentInfo, b.mTickerView, b.mNumber, b.mContentIntent, b.mFullScreenIntent, b.mLargeIcon);
                    }
                }

/* 119 */       static class NotificationCompatImplIceCreamSandwich implements NotificationCompatImpl {
/* 120 */           NotificationCompatImplIceCreamSandwich() {
                    }

                    @Override // android.support.v4.app.NotificationCompat.NotificationCompatImpl
/* 122 */           public Notification build(Builder b) {
/* 123 */               return NotificationCompatIceCreamSandwich.add(b.mContext, b.mNotification, b.mContentTitle, b.mContentText, b.mContentInfo, b.mTickerView, b.mNumber, b.mContentIntent, b.mFullScreenIntent, b.mLargeIcon, b.mProgressMax, b.mProgress, b.mProgressIndeterminate);
                    }
                }

/* 128 */       static class NotificationCompatImplJellybean implements NotificationCompatImpl {
/* 129 */           NotificationCompatImplJellybean() {
                    }

                    @Override // android.support.v4.app.NotificationCompat.NotificationCompatImpl
/* 131 */           public Notification build(Builder b) {
/* 132 */               NotificationCompatJellybean jbBuilder = new NotificationCompatJellybean(b.mContext, b.mNotification, b.mContentTitle, b.mContentText, b.mContentInfo, b.mTickerView, b.mNumber, b.mContentIntent, b.mFullScreenIntent, b.mLargeIcon, b.mProgressMax, b.mProgress, b.mProgressIndeterminate, b.mUseChronometer, b.mPriority, b.mSubText);
                        for (Action action : b.mActions) {
/* 138 */                   jbBuilder.addAction(action.icon, action.title, action.actionIntent);
                        }
/* 140 */               if (b.mStyle != null) {
/* 141 */                   if (b.mStyle instanceof BigTextStyle) {
/* 142 */                       BigTextStyle style = (BigTextStyle) b.mStyle;
/* 143 */                       jbBuilder.addBigTextStyle(style.mBigContentTitle, style.mSummaryTextSet, style.mSummaryText, style.mBigText);
                            } else if (b.mStyle instanceof InboxStyle) {
/* 148 */                       InboxStyle style2 = (InboxStyle) b.mStyle;
/* 149 */                       jbBuilder.addInboxStyle(style2.mBigContentTitle, style2.mSummaryTextSet, style2.mSummaryText, style2.mTexts);
                            } else if (b.mStyle instanceof BigPictureStyle) {
/* 154 */                       BigPictureStyle style3 = (BigPictureStyle) b.mStyle;
/* 155 */                       jbBuilder.addBigPictureStyle(style3.mBigContentTitle, style3.mSummaryTextSet, style3.mSummaryText, style3.mPicture, style3.mBigLargeIcon, style3.mBigLargeIconSet);
                            }
                        }
/* 163 */               return jbBuilder.build();
                    }
                }

                static {
/* 168 */           if (Build.VERSION.SDK_INT >= 16) {
/* 169 */               IMPL = new NotificationCompatImplJellybean();
/* 179 */               return;
                    }
/* 170 */           if (Build.VERSION.SDK_INT >= 14) {
/* 171 */               IMPL = new NotificationCompatImplIceCreamSandwich();
/* 179 */               return;
                    }
/* 172 */           if (Build.VERSION.SDK_INT >= 11) {
/* 173 */               IMPL = new NotificationCompatImplHoneycomb();
                    } else if (Build.VERSION.SDK_INT >= 9) {
/* 175 */               IMPL = new NotificationCompatImplGingerbread();
                    } else {
/* 177 */               IMPL = new NotificationCompatImplBase();
                    }
                }

/* 232 */       public static class Builder {
                    CharSequence mContentInfo;
                    PendingIntent mContentIntent;
                    CharSequence mContentText;
                    CharSequence mContentTitle;
                    Context mContext;
                    PendingIntent mFullScreenIntent;
                    Bitmap mLargeIcon;
                    int mNumber;
                    int mPriority;
                    int mProgress;
                    boolean mProgressIndeterminate;
                    int mProgressMax;
                    Style mStyle;
                    CharSequence mSubText;
                    RemoteViews mTickerView;
                    boolean mUseChronometer;
                    ArrayList<Action> mActions = new ArrayList<>();
                    Notification mNotification = new Notification();

/* 233 */           public Builder(Context context) {
/* 235 */               this.mContext = context;
/* 238 */               this.mNotification.when = System.currentTimeMillis();
/* 239 */               this.mNotification.audioStreamType = -1;
/* 240 */               this.mPriority = 0;
                    }

/* 247 */           public Builder setWhen(long when) {
/* 248 */               this.mNotification.when = when;
/* 249 */               return this;
                    }

/* 263 */           public Builder setUsesChronometer(boolean b) {
/* 264 */               this.mUseChronometer = b;
/* 265 */               return this;
                    }

/* 275 */           public Builder setSmallIcon(int icon) {
/* 276 */               this.mNotification.icon = icon;
/* 277 */               return this;
                    }

/* 290 */           public Builder setSmallIcon(int icon, int level) {
/* 291 */               this.mNotification.icon = icon;
/* 292 */               this.mNotification.iconLevel = level;
/* 293 */               return this;
                    }

/* 299 */           public Builder setContentTitle(CharSequence title) {
/* 300 */               this.mContentTitle = title;
/* 301 */               return this;
                    }

/* 307 */           public Builder setContentText(CharSequence text) {
/* 308 */               this.mContentText = text;
/* 309 */               return this;
                    }

/* 321 */           public Builder setSubText(CharSequence text) {
/* 322 */               this.mSubText = text;
/* 323 */               return this;
                    }

/* 331 */           public Builder setNumber(int number) {
/* 332 */               this.mNumber = number;
/* 333 */               return this;
                    }

/* 339 */           public Builder setContentInfo(CharSequence info) {
/* 340 */               this.mContentInfo = info;
/* 341 */               return this;
                    }

/* 348 */           public Builder setProgress(int max, int progress, boolean indeterminate) {
/* 349 */               this.mProgressMax = max;
/* 350 */               this.mProgress = progress;
/* 351 */               this.mProgressIndeterminate = indeterminate;
/* 352 */               return this;
                    }

/* 358 */           public Builder setContent(RemoteViews views) {
/* 359 */               this.mNotification.contentView = views;
/* 360 */               return this;
                    }

/* 371 */           public Builder setContentIntent(PendingIntent intent) {
/* 372 */               this.mContentIntent = intent;
/* 373 */               return this;
                    }

/* 383 */           public Builder setDeleteIntent(PendingIntent intent) {
/* 384 */               this.mNotification.deleteIntent = intent;
/* 385 */               return this;
                    }

/* 401 */           public Builder setFullScreenIntent(PendingIntent intent, boolean highPriority) {
/* 402 */               this.mFullScreenIntent = intent;
/* 403 */               setFlag(128, highPriority);
/* 404 */               return this;
                    }

/* 411 */           public Builder setTicker(CharSequence tickerText) {
/* 412 */               this.mNotification.tickerText = tickerText;
/* 413 */               return this;
                    }

/* 421 */           public Builder setTicker(CharSequence tickerText, RemoteViews views) {
/* 422 */               this.mNotification.tickerText = tickerText;
/* 423 */               this.mTickerView = views;
/* 424 */               return this;
                    }

/* 430 */           public Builder setLargeIcon(Bitmap icon) {
/* 431 */               this.mLargeIcon = icon;
/* 432 */               return this;
                    }

/* 438 */           public Builder setSound(Uri sound) {
/* 439 */               this.mNotification.sound = sound;
/* 440 */               this.mNotification.audioStreamType = -1;
/* 441 */               return this;
                    }

/* 450 */           public Builder setSound(Uri sound, int streamType) {
/* 451 */               this.mNotification.sound = sound;
/* 452 */               this.mNotification.audioStreamType = streamType;
/* 453 */               return this;
                    }

/* 462 */           public Builder setVibrate(long[] pattern) {
/* 463 */               this.mNotification.vibrate = pattern;
/* 464 */               return this;
                    }

/* 472 */           public Builder setLights(int argb, int onMs, int offMs) {
/* 473 */               this.mNotification.ledARGB = argb;
/* 474 */               this.mNotification.ledOnMS = onMs;
/* 475 */               this.mNotification.ledOffMS = offMs;
/* 476 */               boolean showLights = (this.mNotification.ledOnMS == 0 || this.mNotification.ledOffMS == 0) ? false : true;
/* 477 */               this.mNotification.flags = (showLights ? 1 : 0) | (this.mNotification.flags & (-2));
/* 479 */               return this;
                    }

/* 493 */           public Builder setOngoing(boolean ongoing) {
/* 494 */               setFlag(2, ongoing);
/* 495 */               return this;
                    }

/* 502 */           public Builder setOnlyAlertOnce(boolean onlyAlertOnce) {
/* 503 */               setFlag(8, onlyAlertOnce);
/* 504 */               return this;
                    }

/* 513 */           public Builder setAutoCancel(boolean autoCancel) {
/* 514 */               setFlag(16, autoCancel);
/* 515 */               return this;
                    }

/* 528 */           public Builder setDefaults(int defaults) {
/* 529 */               this.mNotification.defaults = defaults;
/* 530 */               if ((defaults & 4) != 0) {
                            this.mNotification.flags |= 1;
                        }
/* 533 */               return this;
                    }

/* 536 */           private void setFlag(int mask, boolean value) {
/* 537 */               if (value) {
                            this.mNotification.flags |= mask;
                        } else {
                            this.mNotification.flags &= mask ^ (-1);
                        }
                    }

/* 555 */           public Builder setPriority(int pri) {
/* 556 */               this.mPriority = pri;
/* 557 */               return this;
                    }

/* 576 */           public Builder addAction(int icon, CharSequence title, PendingIntent intent) {
/* 577 */               this.mActions.add(new Action(icon, title, intent));
/* 578 */               return this;
                    }

/* 589 */           public Builder setStyle(Style style) {
/* 590 */               if (this.mStyle != style) {
/* 591 */                   this.mStyle = style;
/* 592 */                   if (this.mStyle != null) {
/* 593 */                       this.mStyle.setBuilder(this);
                            }
                        }
/* 596 */               return this;
                    }

                    @Deprecated
/* 603 */           public Notification getNotification() {
/* 604 */               return NotificationCompat.access$000().build(this);
                    }

/* 611 */           public Notification build() {
/* 612 */               return NotificationCompat.access$000().build(this);
                    }
                }

/* 629 */       public static abstract class Style {
                    CharSequence mBigContentTitle;
                    Builder mBuilder;
                    CharSequence mSummaryText;
                    boolean mSummaryTextSet = false;

/* 630 */           public void setBuilder(Builder builder) {
/* 631 */               if (this.mBuilder != builder) {
/* 632 */                   this.mBuilder = builder;
/* 633 */                   if (this.mBuilder != null) {
/* 634 */                       this.mBuilder.setStyle(this);
                            }
                        }
                    }

/* 639 */           public Notification build() {
/* 641 */               if (this.mBuilder == null) {
/* 640 */                   return null;
                        }
/* 642 */               Notification notification = this.mBuilder.build();
/* 644 */               return notification;
                    }
                }

/* 671 */       public static class BigPictureStyle extends Style {
                    Bitmap mBigLargeIcon;
                    boolean mBigLargeIconSet;
                    Bitmap mPicture;

/* 672 */           public BigPictureStyle() {
                    }

/* 675 */           public BigPictureStyle(Builder builder) {
/* 677 */               setBuilder(builder);
                    }

/* 684 */           public BigPictureStyle setBigContentTitle(CharSequence title) {
/* 685 */               this.mBigContentTitle = title;
/* 686 */               return this;
                    }

/* 692 */           public BigPictureStyle setSummaryText(CharSequence cs) {
/* 693 */               this.mSummaryText = cs;
/* 694 */               this.mSummaryTextSet = true;
/* 695 */               return this;
                    }

/* 701 */           public BigPictureStyle bigPicture(Bitmap b) {
/* 702 */               this.mPicture = b;
/* 703 */               return this;
                    }

/* 709 */           public BigPictureStyle bigLargeIcon(Bitmap b) {
/* 710 */               this.mBigLargeIcon = b;
/* 711 */               this.mBigLargeIconSet = true;
/* 712 */               return this;
                    }
                }

/* 738 */       public static class BigTextStyle extends Style {
                    CharSequence mBigText;

/* 739 */           public BigTextStyle() {
                    }

/* 742 */           public BigTextStyle(Builder builder) {
/* 744 */               setBuilder(builder);
                    }

/* 751 */           public BigTextStyle setBigContentTitle(CharSequence title) {
/* 752 */               this.mBigContentTitle = title;
/* 753 */               return this;
                    }

/* 759 */           public BigTextStyle setSummaryText(CharSequence cs) {
/* 760 */               this.mSummaryText = cs;
/* 761 */               this.mSummaryTextSet = true;
/* 762 */               return this;
                    }

/* 769 */           public BigTextStyle bigText(CharSequence cs) {
/* 770 */               this.mBigText = cs;
/* 771 */               return this;
                    }
                }

/* 800 */       public static class InboxStyle extends Style {
                    ArrayList<CharSequence> mTexts = new ArrayList<>();

/* 801 */           public InboxStyle() {
                    }

/* 804 */           public InboxStyle(Builder builder) {
/* 806 */               setBuilder(builder);
                    }

/* 813 */           public InboxStyle setBigContentTitle(CharSequence title) {
/* 814 */               this.mBigContentTitle = title;
/* 815 */               return this;
                    }

/* 821 */           public InboxStyle setSummaryText(CharSequence cs) {
/* 822 */               this.mSummaryText = cs;
/* 823 */               this.mSummaryTextSet = true;
/* 824 */               return this;
                    }

/* 830 */           public InboxStyle addLine(CharSequence cs) {
/* 831 */               this.mTexts.add(cs);
/* 832 */               return this;
                    }
                }

/* 839 */       public static class Action {
                    public PendingIntent actionIntent;
                    public int icon;
                    public CharSequence title;

/* 840 */           public Action(int icon_, CharSequence title_, PendingIntent intent_) {
/* 842 */               this.icon = icon_;
/* 843 */               this.title = title_;
/* 844 */               this.actionIntent = intent_;
                    }
                }
            }
