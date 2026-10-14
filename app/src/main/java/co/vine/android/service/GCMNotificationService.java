            package co.vine.android.service;

            import android.app.NotificationManager;
            import android.app.PendingIntent;
            import android.app.Service;
            import android.content.BroadcastReceiver;
            import android.content.ComponentName;
            import android.content.Context;
            import android.content.Intent;
            import android.content.IntentFilter;
            import android.content.ServiceConnection;
            import android.content.SharedPreferences;
            import android.graphics.Bitmap;
            import android.graphics.BitmapFactory;
            import android.net.Uri;
            import android.os.Handler;
            import android.os.IBinder;
            import android.os.Message;
            import android.os.Messenger;
            import android.os.RemoteException;
            import android.support.annotation.NonNull;
            import android.support.v4.app.NotificationCompat;
            import android.text.TextUtils;
            import co.vine.android.C0257R;
            import co.vine.android.Settings;
            import co.vine.android.StartActivity;
            import co.vine.android.api.VineParsers;
            import co.vine.android.api.VineSingleNotification;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.cache.video.UrlVideo;
            import co.vine.android.cache.video.VideoKey;
            import co.vine.android.client.AppController;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.network.HttpResult;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.CrossConstants;
            import co.vine.android.util.SparseArray;
            import co.vine.android.util.Util;
            import co.vine.android.util.analytics.FlurryUtils;
            import com.edisonwang.android.slog.SLog;
            import com.fasterxml.jackson.core.JsonParser;
            import java.io.IOException;
            import java.util.ArrayList;
            import java.util.HashMap;
            import java.util.Iterator;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 53 */    public class GCMNotificationService extends Service {
                public static final String ACTION_CLEAR_NOTIFICATIONS = "co.vine.android.notifications.ACTION_CLEAR_NOTIFICATIONS";
                public static final String ACTION_NEW_NOTIFICATION = "co.vine.android.notifications.ACTION_NEW_NOTIFICATION";
                public static final String ACTION_UPDATE_NOTIFICATIONS = "co.vine.android.notifications.ACTION_UPDATE_NOTIFICATIONS";
                private static final int DELAY_REFRESH_SERVICE_MS = 2500;
                private static final int DELAY_STOP_SERVICE_MS = 5000;
                public static final String EXTRA_AM_FOLLOWING = "am_following";
                public static final String EXTRA_CONVERSATION_ROW_ID = "conversation_row_id";
                public static final String EXTRA_NOTIFICATION = "notification";
                public static final String EXTRA_NOTIFICATIONS_JSON = "notifications";
                public static final String EXTRA_NOTIFICATION_GROUP_ID = "notification_group_id";
                public static final String EXTRA_NOTIFICATION_TYPE_ID = "notification_type_id";
                public static final String EXTRA_USERNAME = "username";
                public static final String EXTRA_USER_ID = "user_id";
                public static final int GROUP_ID_ACTIVITY = 1;
                public static final int GROUP_ID_ONBOARD = 3;
                public static final int GROUP_ID_VMS = 2;
                private static final String NOTIFICATION_TAG = "gcmGenericNotification";
                public static final String ONBOARD_VALUE_FAVORITES = "favs";
                private static final String TAG = "GCMNotifService";
                private static final IntentFilter sConversationStateFilter = new IntentFilter();
                private AppController mAppController;
                private AppSessionListener mAppSessionListener;
                private Messenger mCameraMessenger;
                private Handler mHandler;
                private boolean mIsCameraServiceConnected;
                private ArrayList<Integer> mStartIds;
                private final SparseArray<NotificationGroup> mPendingNotificationGroups = new SparseArray<>();
                private final HashMap<Long, BuildableNotification> mNotificationsToFire = new HashMap<>();
                private final ServiceConnection mCameraConnection = new ServiceConnectionC05151();
                private final Runnable mFetchActivityCountRunnable = new RunnableC05162();
                private final Runnable mFetchConversationsRunnable = new RunnableC05173();
                private final BroadcastReceiver mConversationStateReceiver = new C05184();

/* 54 */        static /* synthetic */ Messenger access$002(GCMNotificationService x0, Messenger x1) {
/* 55 */            x0.mCameraMessenger = x1;
                    return x1;
                }

/* 54 */        static /* synthetic */ ArrayList access$1000(GCMNotificationService x0) {
/* 55 */            return x0.mStartIds;
                }

/* 54 */        static /* synthetic */ boolean access$102(GCMNotificationService x0, boolean x1) {
/* 55 */            x0.mIsCameraServiceConnected = x1;
                    return x1;
                }

/* 54 */        static /* synthetic */ AppController access$200(GCMNotificationService x0) {
/* 55 */            return x0.mAppController;
                }

/* 54 */        static /* synthetic */ HashMap<Long, BuildableNotification> access$300(GCMNotificationService x0) {
/* 55 */            return x0.mNotificationsToFire;
                }

/* 54 */        static /* synthetic */ void access$400(GCMNotificationService x0, int x1, long x2) {
/* 55 */            x0.updateNotification(x1, x2);
                }

/* 54 */        static /* synthetic */ SparseArray access$600(GCMNotificationService x0) {
/* 55 */            return x0.mPendingNotificationGroups;
                }

/* 54 */        static /* synthetic */ boolean access$700(GCMNotificationService x0, NotificationGroup x1) {
/* 55 */            return x0.showNotificationIfReady(x1);
                }

/* 54 */        static /* synthetic */ void access$800(GCMNotificationService x0, VineSingleNotification x1, NotificationGroup x2) {
/* 55 */            x0.checkWhetherToShowMessagingNotification(x1, x2);
                }

/* 54 */        static /* synthetic */ void access$900(GCMNotificationService x0, VineSingleNotification x1, NotificationGroup x2) {
/* 55 */            x0.buildNotification(x1, x2);
                }

                static {
/* 88 */            sConversationStateFilter.addAction(ResourceService.CONVERSATION_STATE_ON);
/* 89 */            sConversationStateFilter.addAction(ResourceService.CONVERSATION_STATE_OFF);
                }

                /* JADX INFO: renamed from: co.vine.android.service.GCMNotificationService$1 */
/* 99 */        class ServiceConnectionC05151 implements ServiceConnection {
/* 100 */           ServiceConnectionC05151() {
                    }

                    @Override // android.content.ServiceConnection
/* 104 */           public void onServiceConnected(ComponentName name, IBinder service) {
/* 105 */               GCMNotificationService.access$002(GCMNotificationService.this, new Messenger(service));
/* 106 */               GCMNotificationService.access$102(GCMNotificationService.this, true);
                    }

                    @Override // android.content.ServiceConnection
/* 110 */           public void onServiceDisconnected(ComponentName name) {
/* 111 */               GCMNotificationService.access$002(GCMNotificationService.this, null);
/* 112 */               GCMNotificationService.access$102(GCMNotificationService.this, false);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.service.GCMNotificationService$2 */
/* 114 */       class RunnableC05162 implements Runnable {
/* 115 */           RunnableC05162() {
                    }

                    @Override // java.lang.Runnable
/* 118 */           public void run() {
/* 119 */               GCMNotificationService.access$200(GCMNotificationService.this).fetchActivityCounts();
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.service.GCMNotificationService$3 */
/* 121 */       class RunnableC05173 implements Runnable {
/* 122 */           RunnableC05173() {
                    }

                    @Override // java.lang.Runnable
/* 125 */           public void run() {
/* 126 */               GCMNotificationService.access$200(GCMNotificationService.this).fetchConversations(1, false, 1);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.service.GCMNotificationService$4 */
/* 128 */       class C05184 extends BroadcastReceiver {
/* 129 */           C05184() {
                    }

                    @Override // android.content.BroadcastReceiver
/* 133 */           public void onReceive(Context context, Intent intent) {
                        synchronized (GCMNotificationService.access$300(GCMNotificationService.this)) {
                            try {
/* 135 */                       SLog.m447d("Received camera request result: {}.", intent);
/* 136 */                       if (ResourceService.CONVERSATION_STATE_ON.equals(intent.getAction())) {
/* 137 */                           long conversationRowId = intent.getLongExtra(ResourceService.CONVERSATION_STATE_ON_ID, -1L);
/* 138 */                           GCMNotificationService.access$300(GCMNotificationService.this).remove(Long.valueOf(conversationRowId));
/* 139 */                           GCMNotificationService.access$400(GCMNotificationService.this, intent.getIntExtra(GCMNotificationService.EXTRA_NOTIFICATION_GROUP_ID, 2), conversationRowId);
                                }
                                for (BuildableNotification buildableNotification : GCMNotificationService.access$300(GCMNotificationService.this).values()) {
/* 142 */                           buildableNotification.build();
                                }
/* 144 */                       GCMNotificationService.access$300(GCMNotificationService.this).clear();
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

/* 158 */       public static class NotificationGroup {
                    public ImageKey avatarImageKey;
                    public NotificationCompat.Builder builder;
                    public Bitmap icon;
                    public VineSingleNotification newestNotification;
                    public final int notificationGroupId;
                    public long notificationObjectId;
                    public ArrayList<VineSingleNotification> notifications;
                    public final HashMap<ImageKey, Boolean> thumbnailImageKeys = new HashMap<>();
                    public final HashMap<VideoKey, Boolean> videoKeys = new HashMap<>();

/* 159 */           public NotificationGroup(int notificationGroupId, VineSingleNotification notification) {
/* 161 */               this.notificationGroupId = notificationGroupId;
/* 162 */               this.newestNotification = notification;
                    }

/* 165 */           public boolean isReady() {
/* 166 */               if (this.builder == null) {
/* 167 */                   SLog.m446d("Builder is not ready, this group has been reset by notification merge.");
                            return false;
                        }
/* 170 */               if (this.icon == null) {
/* 171 */                   SLog.m446d("Notification not ready because avatar is pending.");
                            return false;
                        }
                        for (ImageKey key : this.thumbnailImageKeys.keySet()) {
/* 175 */                   if (!this.thumbnailImageKeys.get(key).booleanValue()) {
/* 176 */                       SLog.m446d("Notification not ready because thumbnails are pending.");
                                return false;
                            }
                        }
                        for (VideoKey key2 : this.videoKeys.keySet()) {
/* 181 */                   if (!this.videoKeys.get(key2).booleanValue()) {
/* 182 */                       SLog.m446d("Notification not ready because videos are pending.");
                                return false;
                            }
                        }
/* 186 */               return true;
                    }

/* 189 */           public void reset() {
/* 190 */               this.builder = null;
/* 191 */               this.notifications = null;
/* 192 */               this.avatarImageKey = null;
/* 193 */               this.thumbnailImageKeys.clear();
/* 194 */               this.videoKeys.clear();
/* 195 */               this.icon = null;
                    }
                }

/* 200 */       public NotificationGroup getNotificationGroup(VineSingleNotification notification) {
                    int groupId;
/* 201 */           if (notification.isMessaging()) {
/* 202 */               groupId = 2;
                    } else if (!TextUtils.isEmpty(notification.onboard)) {
/* 204 */               groupId = 3;
                    } else {
/* 206 */               groupId = 1;
                    }
/* 209 */           NotificationGroup group = this.mPendingNotificationGroups.get(groupId);
/* 210 */           if (group == null) {
/* 211 */               NotificationGroup group2 = new NotificationGroup(groupId, notification);
/* 212 */               this.mPendingNotificationGroups.put(groupId, group2);
/* 214 */               return group2;
                    }
/* 214 */           return group;
                }

                @Override // android.app.Service
/* 218 */       public void onCreate() {
/* 219 */           super.onCreate();
/* 220 */           SLog.dWithTag(TAG, "Service created.");
/* 221 */           this.mAppController = AppController.getInstance(this);
/* 222 */           this.mAppSessionListener = new GCMNotificationServiceListener(this, null);
/* 223 */           this.mAppController.addListener(this.mAppSessionListener);
/* 224 */           this.mStartIds = new ArrayList<>();
/* 225 */           this.mHandler = new Handler();
/* 226 */           registerReceiver(this.mConversationStateReceiver, sConversationStateFilter, CrossConstants.BROADCAST_PERMISSION, null);
/* 227 */           bindService(new Intent(this, (Class<?>) ResourceService.class), this.mCameraConnection, 1);
                }

                @Override // android.app.Service
/* 231 */       public int onStartCommand(Intent intent, int flags, int startId) {
/* 232 */           SLog.dWithTag(TAG, "onStartCommand, startId=" + startId + ", startIds=" + this.mStartIds.toString());
/* 233 */           this.mStartIds.add(Integer.valueOf(startId));
/* 234 */           this.mHandler.removeCallbacksAndMessages(null);
/* 235 */           if (intent != null) {
/* 236 */               String action = intent.getAction();
/* 237 */               if (action == null) {
/* 238 */                   postStop();
                        } else {
                            switch (action) {
                                case "co.vine.android.notifications.ACTION_NEW_NOTIFICATION":
/* 242 */                           if (this.mAppController.isLoggedInReadOnly()) {
/* 243 */                               processNewNotification(intent);
                                        break;
                                    }
                                    break;
                                case "co.vine.android.notifications.ACTION_CLEAR_NOTIFICATIONS":
/* 247 */                           clearNotification(intent.getIntExtra(EXTRA_NOTIFICATION_GROUP_ID, 1));
                                    break;
                                case "co.vine.android.notifications.ACTION_UPDATE_NOTIFICATIONS":
/* 250 */                           int notificationId = intent.getIntExtra(EXTRA_NOTIFICATION_GROUP_ID, 2);
/* 251 */                           long conversationRowId = intent.getLongExtra("conversation_row_id", -1L);
/* 252 */                           updateNotification(notificationId, conversationRowId);
                                    break;
                                default:
/* 255 */                           postStop();
                                    break;
                            }
                        }
                    }
                    return 2;
                }

/* 263 */       private void updateNotification(int notificationId, long conversationRowId) {
/* 264 */           this.mAppController.removeNotification(notificationId, conversationRowId);
                }

/* 267 */       private void postStop() {
/* 268 */           this.mHandler.removeMessages(1);
/* 269 */           this.mHandler.sendMessageDelayed(Message.obtain((Handler) null, 1), 5000L);
                }

/* 273 */       private void processNewNotification(Intent intent) {
/* 274 */           String payload = intent.getStringExtra("notifications");
/* 275 */           if (!TextUtils.isEmpty(payload)) {
                        try {
/* 278 */                   JsonParser p = VineParsers.createParser(payload);
/* 279 */                   VineSingleNotification notification = VineParsers.parsePushNotification(p);
/* 286 */                   if (notification == null) {
/* 287 */                       CrashUtil.log("Invalid GCM notification payload, payload=" + payload);
                            } else if (notification.recipientUserId != this.mAppController.getActiveId()) {
/* 289 */                       SLog.m456e("This message is intended for someone else {}.", Long.valueOf(notification.recipientUserId));
                            } else {
/* 291 */                       FlurryUtils.trackNotificationReceived(notification.toString());
/* 292 */                       if (notification.isMessaging()) {
/* 293 */                           this.mHandler.removeCallbacks(this.mFetchConversationsRunnable);
/* 294 */                           this.mHandler.postDelayed(this.mFetchConversationsRunnable, 2500L);
                                }
/* 296 */                       this.mAppController.mergePushNotification(notification);
/* 297 */                       this.mHandler.removeCallbacks(this.mFetchActivityCountRunnable);
/* 298 */                       this.mHandler.postDelayed(this.mFetchActivityCountRunnable, 2500L);
/* 303 */                       return;
                            }
/* 301 */                   postStop();
                        } catch (IOException e) {
/* 281 */                   CrashUtil.logException(e, "Exception while parsing GCM push notification payload.", new Object[0]);
/* 282 */                   postStop();
                        }
                    }
                }

/* 305 */       private void buildNotification(VineSingleNotification notification, NotificationGroup group) {
                    String message;
                    String message2;
/* 306 */           int w = getResources().getDimensionPixelSize(C0257R.dimen.notification_panel_height);
/* 307 */           NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
/* 308 */           builder.setAutoCancel(true);
/* 309 */           builder.setSmallIcon(C0257R.drawable.ic_vine_status_bar);
/* 311 */           Intent notificationIntent = new Intent(this, (Class<?>) StartActivity.class);
/* 312 */           notificationIntent.putExtra("notification", notification);
                    switch (group.notificationGroupId) {
                        case 2:
/* 329 */                   notificationIntent.setAction(StartActivity.ACTION_MESSAGE_NOTIFICATION_PRESSED);
                            break;
                        case 3:
/* 316 */                   notificationIntent.setAction(StartActivity.ACTION_ONBOARD_NOTIFICATION_PRESSED);
/* 317 */                   notificationIntent.putExtra(EXTRA_NOTIFICATION_TYPE_ID, notification.notificationTypeId);
/* 318 */                   builder.setContentTitle(notification.title);
/* 319 */                   builder.setContentText(Util.toPrettyComment(notification));
/* 321 */                   Bitmap icon = BitmapFactory.decodeResource(getResources(), C0257R.drawable.ic_favorite_intro);
/* 322 */                   group.icon = Bitmap.createScaledBitmap(icon, w, w, true);
/* 323 */                   if (icon != group.icon) {
/* 324 */                       icon.recycle();
                            }
                            break;
                        default:
/* 334 */                   notificationIntent.setAction(StartActivity.ACTION_ACTIVITY_NOTIFICATION_PRESSED);
/* 335 */                   if (!TextUtils.isEmpty(notification.url)) {
/* 336 */                       notificationIntent.setData(Uri.parse(notification.url));
                            }
/* 338 */                   builder.setContentTitle(getString(C0257R.string.activity_notification_big_title));
/* 339 */                   builder.setContentText(Util.toPrettyComment(notification));
                            break;
                    }
/* 345 */           if (!TextUtils.isEmpty(notification.avatarUrl)) {
/* 346 */               group.avatarImageKey = new ImageKey(notification.avatarUrl, w, w, true);
/* 347 */               group.icon = this.mAppController.getPhotoBitmap(group.avatarImageKey);
                    } else {
/* 350 */               group.icon = BitmapFactory.decodeResource(getResources(), 2130837952);
                    }
/* 353 */           if (group.notificationGroupId == 2) {
                        for (ImageKey imageKey : notification.imageKeys) {
/* 355 */                   if (group.thumbnailImageKeys.get(imageKey) == null) {
/* 356 */                       group.thumbnailImageKeys.put(imageKey, false);
/* 357 */                       if (this.mAppController.getPhotoBitmap(imageKey) != null) {
/* 358 */                           group.thumbnailImageKeys.put(imageKey, true);
                                }
                            }
                        }
                        for (VideoKey videoKey : notification.videoKeys) {
/* 364 */                   if (group.videoKeys.get(videoKey) == null) {
/* 365 */                       group.videoKeys.put(videoKey, false);
/* 366 */                       if (this.mAppController.getVideoFilePath(videoKey) != null) {
/* 367 */                           group.videoKeys.put(videoKey, true);
                                }
                            }
                        }
/* 373 */               if (notification.messageCount > 1) {
/* 374 */                   message2 = getString(C0257R.string.message_notification_title_plural, new Object[]{Util.getUsernameFromActivity(notification.comment), Integer.valueOf(notification.messageCount)});
                        } else {
/* 376 */                   message2 = Util.stripUsernameEntities(notification.comment);
                        }
/* 378 */               builder.setContentTitle(message2);
/* 379 */               SLog.m446d("Notification - " + message2);
/* 380 */               builder.setSubText(getString(C0257R.string.message_notification_subtext));
                    }
/* 384 */           ArrayList<VineSingleNotification> unclearedNotifications = group.notifications;
/* 385 */           if (unclearedNotifications == null) {
/* 386 */               unclearedNotifications = new ArrayList<>();
                    }
/* 389 */           if (unclearedNotifications.size() > 1) {
/* 390 */               NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
/* 391 */               if (group.notificationGroupId == 1) {
/* 392 */                   inboxStyle.setBigContentTitle(getString(C0257R.string.activity_notification_big_title));
                        } else {
/* 394 */                   inboxStyle.setBigContentTitle(getString(C0257R.string.message_notification_big_title));
                        }
                        for (VineSingleNotification unclearedNotification : unclearedNotifications) {
/* 398 */                   if (group.notificationGroupId == 2) {
/* 399 */                       if (notification.messageCount > 1) {
/* 400 */                           message = getString(C0257R.string.message_notification_title_plural, new Object[]{Util.getUsernameFromActivity(unclearedNotification.comment), Integer.valueOf(unclearedNotification.messageCount)});
                                } else {
/* 402 */                           message = getString(C0257R.string.message_notification_title_single, new Object[]{Util.getUsernameFromActivity(unclearedNotification.comment), Integer.valueOf(unclearedNotification.messageCount)});
                                }
                            } else {
/* 405 */                       message = Util.toPrettyComment(unclearedNotification);
                            }
/* 407 */                   inboxStyle.addLine(message);
                        }
/* 409 */               builder.setStyle(inboxStyle);
/* 410 */               builder.setNumber(unclearedNotifications.size());
/* 411 */               builder.setSubText(getString(C0257R.string.activity_notification_subtext));
                    }
/* 414 */           if (group.notificationGroupId == 2 && unclearedNotifications.size() == 1) {
/* 415 */               SLog.m446d("Notification - converstaion.");
/* 416 */               notificationIntent.setData(Uri.parse(String.valueOf(notification.conversationRowId)));
/* 417 */               notificationIntent.putExtra("username", notification.username);
/* 418 */               notificationIntent.putExtra("user_id", notification.userId);
/* 419 */               notificationIntent.putExtra("am_following", notification.followStatus);
                    } else {
/* 421 */               SLog.m446d("Notification - home - " + unclearedNotifications.size());
                    }
/* 425 */           SharedPreferences sp = Util.getDefaultSharedPrefs(this);
/* 426 */           int defaults = 0;
/* 427 */           if (sp.getBoolean(Settings.PREF_NOTIFICATIONS_SOUND, true)) {
/* 428 */               if (group.notificationGroupId == 2) {
/* 429 */                   builder.setSound(Util.getUriFromResouce(this, C0257R.raw.vm_notification));
                        } else {
/* 432 */                   String ringtonePath = sp.getString(Settings.PREF_NOTIFICATIONS_RINGTONE, "");
/* 433 */                   if (!TextUtils.isEmpty(ringtonePath)) {
/* 434 */                       builder.setSound(Uri.parse(ringtonePath));
                            } else {
/* 436 */                       defaults = 0 | 1;
                            }
                        }
                    }
/* 440 */           if (sp.getBoolean(Settings.PREF_NOTIFICATIONS_VIBRATE, false)) {
/* 441 */               defaults |= 2;
                    }
/* 443 */           if (sp.getBoolean(Settings.PREF_NOTIFICATIONS_LIGHT, true)) {
/* 444 */               defaults |= 4;
                    }
/* 446 */           builder.setDefaults(defaults);
/* 448 */           builder.setContentIntent(PendingIntent.getActivity(this, 0, notificationIntent, 134217728));
/* 450 */           group.builder = builder;
/* 452 */           if (group.icon != null) {
/* 453 */               showNotificationIfReady(group);
/* 454 */               this.mPendingNotificationGroups.remove(group.notificationGroupId);
                    }
                }

/* 458 */       private void clearNotification(int notificationId) {
/* 459 */           NotificationManager manager = (NotificationManager) getSystemService("notification");
/* 461 */           manager.cancel(NOTIFICATION_TAG, notificationId);
/* 462 */           this.mAppController.removeNotification(notificationId, -1L);
/* 463 */           postStop();
                }

/* 466 */       private boolean showNotificationIfReady(NotificationGroup group) {
/* 467 */           if (!group.isReady()) {
                        return false;
                    }
/* 468 */           group.builder.setLargeIcon(group.icon);
/* 470 */           NotificationManager manager = (NotificationManager) getSystemService("notification");
                    try {
/* 473 */               manager.notify(NOTIFICATION_TAG, group.notificationGroupId, group.builder.build());
/* 474 */               FlurryUtils.trackNotificationShown(group.newestNotification.toString());
                    } catch (NullPointerException e) {
                    } catch (SecurityException e2) {
                    }
/* 479 */           return true;
                }

                @Override // android.app.Service
/* 486 */       public void onDestroy() {
/* 487 */           super.onDestroy();
/* 488 */           if (this.mIsCameraServiceConnected) {
/* 489 */               unbindService(this.mCameraConnection);
                    }
/* 491 */           unregisterReceiver(this.mConversationStateReceiver);
/* 492 */           if (this.mAppSessionListener != null) {
/* 493 */               this.mAppController.removeListener(this.mAppSessionListener);
                    }
                }

/* 497 */       public static Intent getUpdateNotificationIntent(Context context, int notificationId, long conversationRowId) {
/* 498 */           Intent intent = new Intent(context, (Class<?>) GCMNotificationService.class);
/* 499 */           intent.setAction(ACTION_UPDATE_NOTIFICATIONS);
/* 500 */           intent.putExtra(EXTRA_NOTIFICATION_GROUP_ID, notificationId);
/* 501 */           intent.putExtra("conversation_row_id", conversationRowId);
/* 502 */           return intent;
                }

/* 505 */       public static Intent getClearNotificationIntent(Context context, int notificationId) {
/* 506 */           Intent intent = new Intent(context, (Class<?>) GCMNotificationService.class);
/* 507 */           intent.setAction(ACTION_CLEAR_NOTIFICATIONS);
/* 508 */           intent.putExtra(EXTRA_NOTIFICATION_GROUP_ID, notificationId);
/* 509 */           return intent;
                }

/* 510 */       private class GCMNotificationServiceListener extends AppSessionListener {
/* 511 */           private GCMNotificationServiceListener() {
                    }

/* 511 */           /* synthetic */ GCMNotificationServiceListener(GCMNotificationService x0, ServiceConnectionC05151 x1) {
/* 512 */               this();
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // co.vine.android.client.AppSessionListener
/* 514 */           public void onVideoPathError(VideoKey erroredVideoKey, HttpResult result) {
/* 515 */               ArrayList<Integer> toRemove = new ArrayList<>();
/* 516 */               Iterator<Integer> it = GCMNotificationService.access$600(GCMNotificationService.this).iterator();
                        while (it.hasNext()) {
                            int groupId = it.next().intValue();
/* 517 */                   NotificationGroup group = (NotificationGroup) GCMNotificationService.access$600(GCMNotificationService.this).get(groupId);
/* 518 */                   if (group.videoKeys.containsKey(erroredVideoKey)) {
/* 519 */                       group.videoKeys.put(erroredVideoKey, true);
                            }
/* 521 */                   if (GCMNotificationService.access$700(GCMNotificationService.this, group)) {
/* 522 */                       toRemove.add(Integer.valueOf(groupId));
                            }
                        }
/* 525 */               GCMNotificationService.access$600(GCMNotificationService.this).removeAll(toRemove);
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // co.vine.android.client.AppSessionListener
/* 529 */           public void onPhotoImageError(ImageKey erroredImageKey, HttpResult result) {
/* 530 */               ArrayList<Integer> toRemove = new ArrayList<>();
/* 531 */               Iterator<Integer> it = GCMNotificationService.access$600(GCMNotificationService.this).iterator();
                        while (it.hasNext()) {
                            int groupId = it.next().intValue();
/* 532 */                   NotificationGroup group = (NotificationGroup) GCMNotificationService.access$600(GCMNotificationService.this).get(groupId);
/* 533 */                   if (group.avatarImageKey != null && erroredImageKey.equals(group.avatarImageKey)) {
/* 534 */                       group.avatarImageKey = null;
                            }
/* 536 */                   if (group.thumbnailImageKeys.containsKey(erroredImageKey)) {
/* 537 */                       group.thumbnailImageKeys.put(erroredImageKey, true);
                            }
/* 539 */                   if (GCMNotificationService.access$700(GCMNotificationService.this, group)) {
/* 540 */                       toRemove.add(Integer.valueOf(groupId));
                            }
                        }
/* 543 */               GCMNotificationService.access$600(GCMNotificationService.this).removeAll(toRemove);
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // co.vine.android.client.AppSessionListener
/* 547 */           public void onVideoPathObtained(HashMap<VideoKey, UrlVideo> videos) {
/* 548 */               ArrayList<Integer> toRemove = new ArrayList<>();
/* 549 */               Iterator<Integer> it = GCMNotificationService.access$600(GCMNotificationService.this).iterator();
                        while (it.hasNext()) {
                            int groupId = it.next().intValue();
/* 550 */                   NotificationGroup group = (NotificationGroup) GCMNotificationService.access$600(GCMNotificationService.this).get(groupId);
                            for (VideoKey videoKey : group.videoKeys.keySet()) {
/* 552 */                       if (videos.containsKey(videoKey)) {
/* 553 */                           group.videoKeys.put(videoKey, true);
                                }
                            }
/* 556 */                   if (GCMNotificationService.access$700(GCMNotificationService.this, group)) {
/* 557 */                       toRemove.add(Integer.valueOf(groupId));
                            }
                        }
/* 560 */               GCMNotificationService.access$600(GCMNotificationService.this).removeAll(toRemove);
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // co.vine.android.client.AppSessionListener
/* 564 */           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
                        UrlImage image;
/* 565 */               ArrayList<Integer> toRemove = new ArrayList<>();
/* 566 */               if (!images.isEmpty()) {
/* 567 */                   Iterator<Integer> it = GCMNotificationService.access$600(GCMNotificationService.this).iterator();
                            while (it.hasNext()) {
                                int groupId = it.next().intValue();
/* 568 */                       NotificationGroup group = (NotificationGroup) GCMNotificationService.access$600(GCMNotificationService.this).get(groupId);
/* 569 */                       if (group.avatarImageKey != null && images.containsKey(group.avatarImageKey) && (image = images.get(group.avatarImageKey)) != null && image.isValid()) {
/* 572 */                           group.icon = image.bitmap;
                                }
                                for (ImageKey thumbnailKey : group.thumbnailImageKeys.keySet()) {
/* 576 */                           if (images.containsKey(thumbnailKey)) {
/* 577 */                               group.thumbnailImageKeys.put(thumbnailKey, true);
                                    }
                                }
/* 580 */                       if (GCMNotificationService.access$700(GCMNotificationService.this, group)) {
/* 581 */                           toRemove.add(Integer.valueOf(groupId));
                                }
                            }
                        }
/* 585 */               GCMNotificationService.access$600(GCMNotificationService.this).removeAll(toRemove);
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 590 */           public void onMergeNotificationComplete(VineSingleNotification lastNotification, @NonNull ArrayList<VineSingleNotification> notifications) {
/* 591 */               if (lastNotification != null) {
/* 592 */                   NotificationGroup group = GCMNotificationService.this.getNotificationGroup(lastNotification);
                            if (group.notificationObjectId > 0 && lastNotification.conversationRowId != group.notificationObjectId) {
/* 596 */                       group.reset();
/* 597 */                       group.notificationObjectId = lastNotification.conversationRowId;
                            }
/* 599 */                   group.notifications = notifications;
/* 600 */                   if (lastNotification.isMessaging()) {
/* 601 */                       GCMNotificationService.access$800(GCMNotificationService.this, lastNotification, group);
                            } else {
/* 603 */                       GCMNotificationService.access$900(GCMNotificationService.this, lastNotification, group);
                            }
                        }
                    }
                }

/* 610 */       private void checkWhetherToShowMessagingNotification(VineSingleNotification lastNotification, NotificationGroup event) {
/* 611 */           if (this.mCameraMessenger != null) {
                        try {
/* 613 */                   this.mCameraMessenger.send(Message.obtain((Handler) null, 101));
                            synchronized (this.mNotificationsToFire) {
                                try {
/* 615 */                           BuildableNotification buildable = new BuildableNotification(lastNotification, event);
/* 616 */                           this.mNotificationsToFire.put(Long.valueOf(lastNotification.conversationRowId), buildable);
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
/* 624 */                   return;
                        } catch (RemoteException e) {
/* 620 */                   SLog.m455e("Failed to send request to camera service.");
                        }
                    }
/* 623 */           postStop();
                }

/* 629 */       public class BuildableNotification {
                    final NotificationGroup group;
                    final VineSingleNotification notification;

/* 630 */           public BuildableNotification(VineSingleNotification notification, NotificationGroup group) {
/* 632 */               this.notification = notification;
/* 633 */               this.group = group;
                    }

/* 636 */           public void build() {
/* 637 */               GCMNotificationService.access$900(GCMNotificationService.this, this.notification, this.group);
                    }
                }

                @Override // android.app.Service
/* 643 */       public IBinder onBind(Intent intent) {
/* 644 */           return null;
                }

/* 645 */       private class GCMNotificationHandler extends Handler {
                    public static final int MSG_STOP_SERVICE = 1;

/* 646 */           private GCMNotificationHandler() {
                    }

                    @Override // android.os.Handler
/* 651 */           public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case 1:
/* 654 */                       Iterator i$ = GCMNotificationService.access$1000(GCMNotificationService.this).iterator();
                                while (i$.hasNext()) {
                                    int startId = ((Integer) i$.next()).intValue();
/* 655 */                           SLog.dWithTag(GCMNotificationService.TAG, "Stopping service for startId=" + startId);
/* 656 */                           GCMNotificationService.this.stopSelf(startId);
                                }
                                break;
                        }
                    }
                }
            }
