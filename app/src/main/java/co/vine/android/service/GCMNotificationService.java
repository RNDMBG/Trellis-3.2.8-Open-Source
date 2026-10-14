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
            
            
    public class GCMNotificationService extends Service {
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

        static  Messenger access$002(GCMNotificationService x0, Messenger x1) {
            x0.mCameraMessenger = x1;
                    return x1;
                }

        static  ArrayList access$1000(GCMNotificationService x0) {
            return x0.mStartIds;
                }

        static  boolean access$102(GCMNotificationService x0, boolean x1) {
            x0.mIsCameraServiceConnected = x1;
                    return x1;
                }

        static  AppController access$200(GCMNotificationService x0) {
            return x0.mAppController;
                }

        static  HashMap<Long, BuildableNotification> access$300(GCMNotificationService x0) {
            return x0.mNotificationsToFire;
                }

        static  void access$400(GCMNotificationService x0, int x1, long x2) {
            x0.updateNotification(x1, x2);
                }

        static  SparseArray access$600(GCMNotificationService x0) {
            return x0.mPendingNotificationGroups;
                }

        static  boolean access$700(GCMNotificationService x0, NotificationGroup x1) {
            return x0.showNotificationIfReady(x1);
                }

        static  void access$800(GCMNotificationService x0, VineSingleNotification x1, NotificationGroup x2) {
            x0.checkWhetherToShowMessagingNotification(x1, x2);
                }

        static  void access$900(GCMNotificationService x0, VineSingleNotification x1, NotificationGroup x2) {
            x0.buildNotification(x1, x2);
                }

                static {
            sConversationStateFilter.addAction(ResourceService.CONVERSATION_STATE_ON);
            sConversationStateFilter.addAction(ResourceService.CONVERSATION_STATE_OFF);
                }

                
        class ServiceConnectionC05151 implements ServiceConnection {
           ServiceConnectionC05151() {
                    }

                    @Override 
           public void onServiceConnected(ComponentName name, IBinder service) {
               GCMNotificationService.access$002(GCMNotificationService.this, new Messenger(service));
               GCMNotificationService.access$102(GCMNotificationService.this, true);
                    }

                    @Override 
           public void onServiceDisconnected(ComponentName name) {
               GCMNotificationService.access$002(GCMNotificationService.this, null);
               GCMNotificationService.access$102(GCMNotificationService.this, false);
                    }
                }

                
       class RunnableC05162 implements Runnable {
           RunnableC05162() {
                    }

                    @Override 
           public void run() {
               GCMNotificationService.access$200(GCMNotificationService.this).fetchActivityCounts();
                    }
                }

                
       class RunnableC05173 implements Runnable {
           RunnableC05173() {
                    }

                    @Override 
           public void run() {
               GCMNotificationService.access$200(GCMNotificationService.this).fetchConversations(1, false, 1);
                    }
                }

                
       class C05184 extends BroadcastReceiver {
           C05184() {
                    }

                    @Override 
           public void onReceive(Context context, Intent intent) {
                        synchronized (GCMNotificationService.access$300(GCMNotificationService.this)) {
                            try {
                       SLog.m447d("Received camera request result: {}.", intent);
                       if (ResourceService.CONVERSATION_STATE_ON.equals(intent.getAction())) {
                           long conversationRowId = intent.getLongExtra(ResourceService.CONVERSATION_STATE_ON_ID, -1L);
                           GCMNotificationService.access$300(GCMNotificationService.this).remove(Long.valueOf(conversationRowId));
                           GCMNotificationService.access$400(GCMNotificationService.this, intent.getIntExtra(GCMNotificationService.EXTRA_NOTIFICATION_GROUP_ID, 2), conversationRowId);
                                }
                                for (BuildableNotification buildableNotification : GCMNotificationService.access$300(GCMNotificationService.this).values()) {
                           buildableNotification.build();
                                }
                       GCMNotificationService.access$300(GCMNotificationService.this).clear();
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

       public static class NotificationGroup {
                    public ImageKey avatarImageKey;
                    public NotificationCompat.Builder builder;
                    public Bitmap icon;
                    public VineSingleNotification newestNotification;
                    public final int notificationGroupId;
                    public long notificationObjectId;
                    public ArrayList<VineSingleNotification> notifications;
                    public final HashMap<ImageKey, Boolean> thumbnailImageKeys = new HashMap<>();
                    public final HashMap<VideoKey, Boolean> videoKeys = new HashMap<>();

           public NotificationGroup(int notificationGroupId, VineSingleNotification notification) {
               this.notificationGroupId = notificationGroupId;
               this.newestNotification = notification;
                    }

           public boolean isReady() {
               if (this.builder == null) {
                   SLog.m446d("Builder is not ready, this group has been reset by notification merge.");
                            return false;
                        }
               if (this.icon == null) {
                   SLog.m446d("Notification not ready because avatar is pending.");
                            return false;
                        }
                        for (ImageKey key : this.thumbnailImageKeys.keySet()) {
                   if (!this.thumbnailImageKeys.get(key).booleanValue()) {
                       SLog.m446d("Notification not ready because thumbnails are pending.");
                                return false;
                            }
                        }
                        for (VideoKey key2 : this.videoKeys.keySet()) {
                   if (!this.videoKeys.get(key2).booleanValue()) {
                       SLog.m446d("Notification not ready because videos are pending.");
                                return false;
                            }
                        }
               return true;
                    }

           public void reset() {
               this.builder = null;
               this.notifications = null;
               this.avatarImageKey = null;
               this.thumbnailImageKeys.clear();
               this.videoKeys.clear();
               this.icon = null;
                    }
                }

       public NotificationGroup getNotificationGroup(VineSingleNotification notification) {
                    int groupId;
           if (notification.isMessaging()) {
               groupId = 2;
                    } else if (!TextUtils.isEmpty(notification.onboard)) {
               groupId = 3;
                    } else {
               groupId = 1;
                    }
           NotificationGroup group = this.mPendingNotificationGroups.get(groupId);
           if (group == null) {
               NotificationGroup group2 = new NotificationGroup(groupId, notification);
               this.mPendingNotificationGroups.put(groupId, group2);
               return group2;
                    }
           return group;
                }

                @Override 
       public void onCreate() {
           super.onCreate();
           SLog.dWithTag(TAG, "Service created.");
           this.mAppController = AppController.getInstance(this);
           this.mAppSessionListener = new GCMNotificationServiceListener(this, null);
           this.mAppController.addListener(this.mAppSessionListener);
           this.mStartIds = new ArrayList<>();
           this.mHandler = new Handler();
           registerReceiver(this.mConversationStateReceiver, sConversationStateFilter, CrossConstants.BROADCAST_PERMISSION, null);
           bindService(new Intent(this, (Class<?>) ResourceService.class), this.mCameraConnection, 1);
                }

                @Override 
       public int onStartCommand(Intent intent, int flags, int startId) {
           SLog.dWithTag(TAG, "onStartCommand, startId=" + startId + ", startIds=" + this.mStartIds.toString());
           this.mStartIds.add(Integer.valueOf(startId));
           this.mHandler.removeCallbacksAndMessages(null);
           if (intent != null) {
               String action = intent.getAction();
               if (action == null) {
                   postStop();
                        } else {
                            switch (action) {
                                case "co.vine.android.notifications.ACTION_NEW_NOTIFICATION":
                           if (this.mAppController.isLoggedInReadOnly()) {
                               processNewNotification(intent);
                                        break;
                                    }
                                    break;
                                case "co.vine.android.notifications.ACTION_CLEAR_NOTIFICATIONS":
                           clearNotification(intent.getIntExtra(EXTRA_NOTIFICATION_GROUP_ID, 1));
                                    break;
                                case "co.vine.android.notifications.ACTION_UPDATE_NOTIFICATIONS":
                           int notificationId = intent.getIntExtra(EXTRA_NOTIFICATION_GROUP_ID, 2);
                           long conversationRowId = intent.getLongExtra("conversation_row_id", -1L);
                           updateNotification(notificationId, conversationRowId);
                                    break;
                                default:
                           postStop();
                                    break;
                            }
                        }
                    }
                    return 2;
                }

       private void updateNotification(int notificationId, long conversationRowId) {
           this.mAppController.removeNotification(notificationId, conversationRowId);
                }

       private void postStop() {
           this.mHandler.removeMessages(1);
           this.mHandler.sendMessageDelayed(Message.obtain((Handler) null, 1), 5000L);
                }

       private void processNewNotification(Intent intent) {
           String payload = intent.getStringExtra("notifications");
           if (!TextUtils.isEmpty(payload)) {
                        try {
                   JsonParser p = VineParsers.createParser(payload);
                   VineSingleNotification notification = VineParsers.parsePushNotification(p);
                   if (notification == null) {
                       CrashUtil.log("Invalid GCM notification payload, payload=" + payload);
                            } else if (notification.recipientUserId != this.mAppController.getActiveId()) {
                       SLog.m456e("This message is intended for someone else {}.", Long.valueOf(notification.recipientUserId));
                            } else {
                       FlurryUtils.trackNotificationReceived(notification.toString());
                       if (notification.isMessaging()) {
                           this.mHandler.removeCallbacks(this.mFetchConversationsRunnable);
                           this.mHandler.postDelayed(this.mFetchConversationsRunnable, 2500L);
                                }
                       this.mAppController.mergePushNotification(notification);
                       this.mHandler.removeCallbacks(this.mFetchActivityCountRunnable);
                       this.mHandler.postDelayed(this.mFetchActivityCountRunnable, 2500L);
                       return;
                            }
                   postStop();
                        } catch (IOException e) {
                   CrashUtil.logException(e, "Exception while parsing GCM push notification payload.", new Object[0]);
                   postStop();
                        }
                    }
                }

       private void buildNotification(VineSingleNotification notification, NotificationGroup group) {
                    String message;
                    String message2;
           int w = getResources().getDimensionPixelSize(C0257R.dimen.notification_panel_height);
           NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
           builder.setAutoCancel(true);
           builder.setSmallIcon(C0257R.drawable.ic_vine_status_bar);
           Intent notificationIntent = new Intent(this, (Class<?>) StartActivity.class);
           notificationIntent.putExtra("notification", notification);
                    switch (group.notificationGroupId) {
                        case 2:
                   notificationIntent.setAction(StartActivity.ACTION_MESSAGE_NOTIFICATION_PRESSED);
                            break;
                        case 3:
                   notificationIntent.setAction(StartActivity.ACTION_ONBOARD_NOTIFICATION_PRESSED);
                   notificationIntent.putExtra(EXTRA_NOTIFICATION_TYPE_ID, notification.notificationTypeId);
                   builder.setContentTitle(notification.title);
                   builder.setContentText(Util.toPrettyComment(notification));
                   Bitmap icon = BitmapFactory.decodeResource(getResources(), C0257R.drawable.ic_favorite_intro);
                   group.icon = Bitmap.createScaledBitmap(icon, w, w, true);
                   if (icon != group.icon) {
                       icon.recycle();
                            }
                            break;
                        default:
                   notificationIntent.setAction(StartActivity.ACTION_ACTIVITY_NOTIFICATION_PRESSED);
                   if (!TextUtils.isEmpty(notification.url)) {
                       notificationIntent.setData(Uri.parse(notification.url));
                            }
                   builder.setContentTitle(getString(C0257R.string.activity_notification_big_title));
                   builder.setContentText(Util.toPrettyComment(notification));
                            break;
                    }
           if (!TextUtils.isEmpty(notification.avatarUrl)) {
               group.avatarImageKey = new ImageKey(notification.avatarUrl, w, w, true);
               group.icon = this.mAppController.getPhotoBitmap(group.avatarImageKey);
                    } else {
               group.icon = BitmapFactory.decodeResource(getResources(), 2130837952);
                    }
           if (group.notificationGroupId == 2) {
                        for (ImageKey imageKey : notification.imageKeys) {
                   if (group.thumbnailImageKeys.get(imageKey) == null) {
                       group.thumbnailImageKeys.put(imageKey, false);
                       if (this.mAppController.getPhotoBitmap(imageKey) != null) {
                           group.thumbnailImageKeys.put(imageKey, true);
                                }
                            }
                        }
                        for (VideoKey videoKey : notification.videoKeys) {
                   if (group.videoKeys.get(videoKey) == null) {
                       group.videoKeys.put(videoKey, false);
                       if (this.mAppController.getVideoFilePath(videoKey) != null) {
                           group.videoKeys.put(videoKey, true);
                                }
                            }
                        }
               if (notification.messageCount > 1) {
                   message2 = getString(C0257R.string.message_notification_title_plural, new Object[]{Util.getUsernameFromActivity(notification.comment), Integer.valueOf(notification.messageCount)});
                        } else {
                   message2 = Util.stripUsernameEntities(notification.comment);
                        }
               builder.setContentTitle(message2);
               SLog.m446d("Notification - " + message2);
               builder.setSubText(getString(C0257R.string.message_notification_subtext));
                    }
           ArrayList<VineSingleNotification> unclearedNotifications = group.notifications;
           if (unclearedNotifications == null) {
               unclearedNotifications = new ArrayList<>();
                    }
           if (unclearedNotifications.size() > 1) {
               NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
               if (group.notificationGroupId == 1) {
                   inboxStyle.setBigContentTitle(getString(C0257R.string.activity_notification_big_title));
                        } else {
                   inboxStyle.setBigContentTitle(getString(C0257R.string.message_notification_big_title));
                        }
                        for (VineSingleNotification unclearedNotification : unclearedNotifications) {
                   if (group.notificationGroupId == 2) {
                       if (notification.messageCount > 1) {
                           message = getString(C0257R.string.message_notification_title_plural, new Object[]{Util.getUsernameFromActivity(unclearedNotification.comment), Integer.valueOf(unclearedNotification.messageCount)});
                                } else {
                           message = getString(C0257R.string.message_notification_title_single, new Object[]{Util.getUsernameFromActivity(unclearedNotification.comment), Integer.valueOf(unclearedNotification.messageCount)});
                                }
                            } else {
                       message = Util.toPrettyComment(unclearedNotification);
                            }
                   inboxStyle.addLine(message);
                        }
               builder.setStyle(inboxStyle);
               builder.setNumber(unclearedNotifications.size());
               builder.setSubText(getString(C0257R.string.activity_notification_subtext));
                    }
           if (group.notificationGroupId == 2 && unclearedNotifications.size() == 1) {
               SLog.m446d("Notification - converstaion.");
               notificationIntent.setData(Uri.parse(String.valueOf(notification.conversationRowId)));
               notificationIntent.putExtra("username", notification.username);
               notificationIntent.putExtra("user_id", notification.userId);
               notificationIntent.putExtra("am_following", notification.followStatus);
                    } else {
               SLog.m446d("Notification - home - " + unclearedNotifications.size());
                    }
           SharedPreferences sp = Util.getDefaultSharedPrefs(this);
           int defaults = 0;
           if (sp.getBoolean(Settings.PREF_NOTIFICATIONS_SOUND, true)) {
               if (group.notificationGroupId == 2) {
                   builder.setSound(Util.getUriFromResouce(this, C0257R.raw.vm_notification));
                        } else {
                   String ringtonePath = sp.getString(Settings.PREF_NOTIFICATIONS_RINGTONE, "");
                   if (!TextUtils.isEmpty(ringtonePath)) {
                       builder.setSound(Uri.parse(ringtonePath));
                            } else {
                       defaults = 0 | 1;
                            }
                        }
                    }
           if (sp.getBoolean(Settings.PREF_NOTIFICATIONS_VIBRATE, false)) {
               defaults |= 2;
                    }
           if (sp.getBoolean(Settings.PREF_NOTIFICATIONS_LIGHT, true)) {
               defaults |= 4;
                    }
           builder.setDefaults(defaults);
           builder.setContentIntent(PendingIntent.getActivity(this, 0, notificationIntent, 134217728));
           group.builder = builder;
           if (group.icon != null) {
               showNotificationIfReady(group);
               this.mPendingNotificationGroups.remove(group.notificationGroupId);
                    }
                }

       private void clearNotification(int notificationId) {
           NotificationManager manager = (NotificationManager) getSystemService("notification");
           manager.cancel(NOTIFICATION_TAG, notificationId);
           this.mAppController.removeNotification(notificationId, -1L);
           postStop();
                }

       private boolean showNotificationIfReady(NotificationGroup group) {
           if (!group.isReady()) {
                        return false;
                    }
           group.builder.setLargeIcon(group.icon);
           NotificationManager manager = (NotificationManager) getSystemService("notification");
                    try {
               manager.notify(NOTIFICATION_TAG, group.notificationGroupId, group.builder.build());
               FlurryUtils.trackNotificationShown(group.newestNotification.toString());
                    } catch (NullPointerException e) {
                    } catch (SecurityException e2) {
                    }
           return true;
                }

                @Override 
       public void onDestroy() {
           super.onDestroy();
           if (this.mIsCameraServiceConnected) {
               unbindService(this.mCameraConnection);
                    }
           unregisterReceiver(this.mConversationStateReceiver);
           if (this.mAppSessionListener != null) {
               this.mAppController.removeListener(this.mAppSessionListener);
                    }
                }

       public static Intent getUpdateNotificationIntent(Context context, int notificationId, long conversationRowId) {
           Intent intent = new Intent(context, (Class<?>) GCMNotificationService.class);
           intent.setAction(ACTION_UPDATE_NOTIFICATIONS);
           intent.putExtra(EXTRA_NOTIFICATION_GROUP_ID, notificationId);
           intent.putExtra("conversation_row_id", conversationRowId);
           return intent;
                }

       public static Intent getClearNotificationIntent(Context context, int notificationId) {
           Intent intent = new Intent(context, (Class<?>) GCMNotificationService.class);
           intent.setAction(ACTION_CLEAR_NOTIFICATIONS);
           intent.putExtra(EXTRA_NOTIFICATION_GROUP_ID, notificationId);
           return intent;
                }

       private class GCMNotificationServiceListener extends AppSessionListener {
           private GCMNotificationServiceListener() {
                    }

            GCMNotificationServiceListener(GCMNotificationService x0, ServiceConnectionC05151 x1) {
               this();
                    }

                    
                    @Override 
           public void onVideoPathError(VideoKey erroredVideoKey, HttpResult result) {
               ArrayList<Integer> toRemove = new ArrayList<>();
               Iterator<Integer> it = GCMNotificationService.access$600(GCMNotificationService.this).iterator();
                        while (it.hasNext()) {
                            int groupId = it.next().intValue();
                   NotificationGroup group = (NotificationGroup) GCMNotificationService.access$600(GCMNotificationService.this).get(groupId);
                   if (group.videoKeys.containsKey(erroredVideoKey)) {
                       group.videoKeys.put(erroredVideoKey, true);
                            }
                   if (GCMNotificationService.access$700(GCMNotificationService.this, group)) {
                       toRemove.add(Integer.valueOf(groupId));
                            }
                        }
               GCMNotificationService.access$600(GCMNotificationService.this).removeAll(toRemove);
                    }

                    
                    @Override 
           public void onPhotoImageError(ImageKey erroredImageKey, HttpResult result) {
               ArrayList<Integer> toRemove = new ArrayList<>();
               Iterator<Integer> it = GCMNotificationService.access$600(GCMNotificationService.this).iterator();
                        while (it.hasNext()) {
                            int groupId = it.next().intValue();
                   NotificationGroup group = (NotificationGroup) GCMNotificationService.access$600(GCMNotificationService.this).get(groupId);
                   if (group.avatarImageKey != null && erroredImageKey.equals(group.avatarImageKey)) {
                       group.avatarImageKey = null;
                            }
                   if (group.thumbnailImageKeys.containsKey(erroredImageKey)) {
                       group.thumbnailImageKeys.put(erroredImageKey, true);
                            }
                   if (GCMNotificationService.access$700(GCMNotificationService.this, group)) {
                       toRemove.add(Integer.valueOf(groupId));
                            }
                        }
               GCMNotificationService.access$600(GCMNotificationService.this).removeAll(toRemove);
                    }

                    
                    @Override 
           public void onVideoPathObtained(HashMap<VideoKey, UrlVideo> videos) {
               ArrayList<Integer> toRemove = new ArrayList<>();
               Iterator<Integer> it = GCMNotificationService.access$600(GCMNotificationService.this).iterator();
                        while (it.hasNext()) {
                            int groupId = it.next().intValue();
                   NotificationGroup group = (NotificationGroup) GCMNotificationService.access$600(GCMNotificationService.this).get(groupId);
                            for (VideoKey videoKey : group.videoKeys.keySet()) {
                       if (videos.containsKey(videoKey)) {
                           group.videoKeys.put(videoKey, true);
                                }
                            }
                   if (GCMNotificationService.access$700(GCMNotificationService.this, group)) {
                       toRemove.add(Integer.valueOf(groupId));
                            }
                        }
               GCMNotificationService.access$600(GCMNotificationService.this).removeAll(toRemove);
                    }

                    
                    @Override 
           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
                        UrlImage image;
               ArrayList<Integer> toRemove = new ArrayList<>();
               if (!images.isEmpty()) {
                   Iterator<Integer> it = GCMNotificationService.access$600(GCMNotificationService.this).iterator();
                            while (it.hasNext()) {
                                int groupId = it.next().intValue();
                       NotificationGroup group = (NotificationGroup) GCMNotificationService.access$600(GCMNotificationService.this).get(groupId);
                       if (group.avatarImageKey != null && images.containsKey(group.avatarImageKey) && (image = images.get(group.avatarImageKey)) != null && image.isValid()) {
                           group.icon = image.bitmap;
                                }
                                for (ImageKey thumbnailKey : group.thumbnailImageKeys.keySet()) {
                           if (images.containsKey(thumbnailKey)) {
                               group.thumbnailImageKeys.put(thumbnailKey, true);
                                    }
                                }
                       if (GCMNotificationService.access$700(GCMNotificationService.this, group)) {
                           toRemove.add(Integer.valueOf(groupId));
                                }
                            }
                        }
               GCMNotificationService.access$600(GCMNotificationService.this).removeAll(toRemove);
                    }

                    @Override 
           public void onMergeNotificationComplete(VineSingleNotification lastNotification, @NonNull ArrayList<VineSingleNotification> notifications) {
               if (lastNotification != null) {
                   NotificationGroup group = GCMNotificationService.this.getNotificationGroup(lastNotification);
                            if (group.notificationObjectId > 0 && lastNotification.conversationRowId != group.notificationObjectId) {
                       group.reset();
                       group.notificationObjectId = lastNotification.conversationRowId;
                            }
                   group.notifications = notifications;
                   if (lastNotification.isMessaging()) {
                       GCMNotificationService.access$800(GCMNotificationService.this, lastNotification, group);
                            } else {
                       GCMNotificationService.access$900(GCMNotificationService.this, lastNotification, group);
                            }
                        }
                    }
                }

       private void checkWhetherToShowMessagingNotification(VineSingleNotification lastNotification, NotificationGroup event) {
           if (this.mCameraMessenger != null) {
                        try {
                   this.mCameraMessenger.send(Message.obtain((Handler) null, 101));
                            synchronized (this.mNotificationsToFire) {
                                try {
                           BuildableNotification buildable = new BuildableNotification(lastNotification, event);
                           this.mNotificationsToFire.put(Long.valueOf(lastNotification.conversationRowId), buildable);
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                   return;
                        } catch (RemoteException e) {
                   SLog.m455e("Failed to send request to camera service.");
                        }
                    }
           postStop();
                }

       public class BuildableNotification {
                    final NotificationGroup group;
                    final VineSingleNotification notification;

           public BuildableNotification(VineSingleNotification notification, NotificationGroup group) {
               this.notification = notification;
               this.group = group;
                    }

           public void build() {
               GCMNotificationService.access$900(GCMNotificationService.this, this.notification, this.group);
                    }
                }

                @Override 
       public IBinder onBind(Intent intent) {
           return null;
                }

       private class GCMNotificationHandler extends Handler {
                    public static final int MSG_STOP_SERVICE = 1;

           private GCMNotificationHandler() {
                    }

                    @Override 
           public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case 1:
                       Iterator i$ = GCMNotificationService.access$1000(GCMNotificationService.this).iterator();
                                while (i$.hasNext()) {
                                    int startId = ((Integer) i$.next()).intValue();
                           SLog.dWithTag(GCMNotificationService.TAG, "Stopping service for startId=" + startId);
                           GCMNotificationService.this.stopSelf(startId);
                                }
                                break;
                        }
                    }
                }
            }
