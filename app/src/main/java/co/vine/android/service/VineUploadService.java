            package co.vine.android.service;

            import android.app.NotificationManager;
            import android.app.PendingIntent;
            import android.app.Service;
            import android.content.ContentUris;
            import android.content.Context;
            import android.content.Intent;
            import android.content.SharedPreferences;
            import android.database.Cursor;
            import android.graphics.Bitmap;
            import android.graphics.BitmapFactory;
            import android.net.Uri;
            import android.os.AsyncTask;
            import android.os.Bundle;
            import android.os.Handler;
            import android.os.IBinder;
            import android.os.Message;
            import android.os.Messenger;
            import android.os.RemoteException;
            import android.support.v4.app.NotificationCompat;
            import android.text.TextUtils;
            import co.vine.android.C0257R;
            import co.vine.android.PostActivity;
            import co.vine.android.Settings;
            import co.vine.android.StartActivity;
            import co.vine.android.VineLoggingException;
            import co.vine.android.api.PostInfo;
            import co.vine.android.api.VineEntity;
            import co.vine.android.api.VineParsers;
            import co.vine.android.api.VineRecipient;
            import co.vine.android.api.VineUpload;
            import co.vine.android.api.VineUploadParsers;
            import co.vine.android.client.AppController;
            import co.vine.android.client.VineAccountHelper;
            import co.vine.android.network.TransferProgressEvent;
            import co.vine.android.provider.VineUploads;
            import co.vine.android.provider.VineUploadsDatabaseSQL;
            import co.vine.android.recorder.HashAsyncTask;
            import co.vine.android.recorder.RecordConfigUtils;
            import co.vine.android.recorder.RecordSessionVersion;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.CrossConstants;
            import co.vine.android.util.UploadManager;
            import co.vine.android.util.Util;
            import com.edisonwang.android.slog.SLog;
            import com.fasterxml.jackson.core.JsonParser;
            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            import java.io.File;
            import java.io.IOException;
            import java.util.ArrayList;
            import java.util.HashMap;
            import java.util.Iterator;
            import java.util.LinkedHashMap;
            import java.util.LinkedHashSet;
            import java.util.LinkedList;
            
            
    public class VineUploadService extends Service implements VineServiceConnection.ServiceResponseHandler {
                public static final String ACTION_CLEAR_NOTIFICATIONS = "clear_notifications";
                public static final String ACTION_DISCARD = "discard";
                public static final String ACTION_DISCARD_ALL = "discard_all";
                public static final String ACTION_DRAFT_UPGRADE_COUNT = "action_get_draft_count";
                public static final String ACTION_NOTIFY_FAILED = "cNotify";
                public static final String ACTION_POST = "bPost";
                public static final String ACTION_PREMERGE_AND_POST = "bMergeAndPost";
                public static final String ACTION_SHOW_NOTIFICATIONS = "show_notifications";
                public static final String ACTION_UPLOAD = "aUpload";
                public static final String ACTION_UPLOAD_RESULT = "co.vine.android.UPLOAD_RESULT";
                public static final String AG_CHANNEL_ID = "channelId";
                public static final String AG_CONVERSATION_ROW_ID = "conversation_row_id";
                public static final String AG_DESCRIPTION = "description";
                public static final String AG_ENTITIES = "entities";
                public static final String AG_HASH = "hash";
                public static final String AG_IS_PRIVATE = "is_private";
                public static final String AG_MAX_LOOPS = "maxLoops";
                public static final String AG_MESSAGE = "message";
                public static final String AG_PATH = "path";
                public static final String AG_POST_ID = "postId";
                public static final String AG_POST_TO_FACEBOOK = "postToFacebook";
                public static final String AG_POST_TO_TWITTER = "postToTwitter";
                public static final String AG_RECIPIENTS = "recipients";
                public static final String AG_REFERENCE = "reference";
                public static final String AG_SHOW_NOTIFICATIONS = "show_notifications";
                public static final String AG_THUMBNAIL = "thumbnail";
                public static final String AG_THUMB_URL = "thumbUrl";
                public static final String AG_TIMESTAMP = "created";
                public static final String AG_UPLOAD = "upload";
                public static final String AG_UPLOAD_RESULT = "upload_status";
                public static final String AG_VIDEO_URL = "videoUrl";
                public static final String EXTRA_IS_ACTIVE = "is_active";
                public static final String EXTRA_SUCCESS = "success";
                public static final String EXTRA_TRANSCODE_PROGRESS = "transcode_progress";
                public static final String EXTRA_UPLOAD_PROGRESS = "upload_progress";
                private static final int MAX_BITMAP_SIZE = 10;
                public static final int MESSAGE_COUNT_UPDATE = 8;
                public static final int MESSAGE_POST_FINISHED = 7;
                public static final int MESSAGE_RESULT_RECEIVER_WAS_SET = 3;
                public static final int MESSAGE_SET_RESULT_RECEIVER = 1;
                public static final int MESSAGE_TRANSCODE_PROGRESS_CHANGED = 5;
                public static final int MESSAGE_TRANSCODE_STARTED = 4;
                public static final int MESSAGE_UNSET_DRAFT_RECEIVER = 9;
                public static final int MESSAGE_UNSET_RESULT_RECEIVER = 2;
                public static final int MESSAGE_UPLOAD_PROGRESS_CHANGED = 6;
                private static final int NOTIFICATION_FAILED_ID = 0;
                private static final int NOTIFICATION_PROGRESS_ID = 1;
                private static final String NOTIFICATION_TAG = "upload_notification";
                public static final int RESULT_NEED_POST_INFO = 3;
                public static final int RESULT_STATUS_FAILED = 1;
                public static final int RESULT_STATUS_SUCCEEDED = 2;
                public static final int RESULT_STATUS_UPLOADING = 0;
                private static final int SMALL_ICON = 2130838053;
                private static final String TAG = "VineUploadService";
                private static boolean sShowProgress;
                private static Messenger sUploadResultReceiver;
                private HashMap<String, Bitmap> mBitmaps;
                private ServiceAsyncTask mCurrentTask;
                private Bitmap mDefaultNotificationIcon;
                private boolean mIsBinded;
                private Messenger mMessenger;
                private NotificationManager mNotificationManager;
                private LinkedHashSet<ServiceAsyncTask> mPendingTasks;
                private LinkedHashSet<ServiceAsyncTask> mPendingVMTasks;
                private LinkedList<Integer> mStartIds;
                private UploadManager mUploadManager;
                private final int[] mLock = new int[0];
                private int mNotificationPanelSize = -1;
                private VineServiceConnection mVineServiceConnection = new VineServiceConnection(this, this);

        static  Messenger access$100() {
            return sUploadResultReceiver;
                }

        static  void access$1000(VineUploadService x0, String x1) {
            x0.markPreMergedMessageAsUploadFailed(x1);
                }

        static  Messenger access$102(Messenger x0) {
            sUploadResultReceiver = x0;
                    return x0;
                }

        static  void access$1100(VineUploadService x0, String x1) {
            x0.discardUpload(x1);
                }

        static  void access$1200(VineUploadService x0) {
            x0.executeNext();
                }

        static  ServiceAsyncTask access$200(VineUploadService x0) {
            return x0.mCurrentTask;
                }

        static  ServiceAsyncTask access$202(VineUploadService x0, ServiceAsyncTask x1) {
            x0.mCurrentTask = x1;
                    return x1;
                }

        static  void access$300(VineUploadService x0) {
            x0.stopIfNoTasksLeft();
                }

        static  void access$400(VineUploadService x0, int x1) {
            x0.clearNotification(x1);
                }

        static  void access$500(VineUploadService x0) {
            x0.cancelFailedNofitication();
                }

        static  Bitmap access$600(VineUploadService x0, String x1) {
            return x0.setScaledNotificationIcon(x1);
                }

        static  void access$700(VineUploadService x0, NotificationCompat.Builder x1, int x2) {
            x0.showNotification(x1, x2);
                }

        static  VineServiceConnection access$800(VineUploadService x0) {
            return x0.mVineServiceConnection;
                }

        static  UploadManager access$900(VineUploadService x0) {
            return x0.mUploadManager;
                }

       public static Intent getUploadIntent(Context context, String path, String hash, String thumbnail, String reference, boolean isMessaging, long conversationRowId) {
           sShowProgress = false;
           Intent intent = new Intent(context, (Class<?>) VineUploadService.class);
           intent.setAction(ACTION_UPLOAD);
           intent.putExtra("path", path);
           intent.putExtra("hash", hash);
           intent.putExtra(AG_THUMBNAIL, thumbnail);
           intent.putExtra("reference", reference);
           intent.putExtra("is_private", isMessaging);
           intent.putExtra("show_notifications", true);
           intent.putExtra("conversation_row_id", conversationRowId);
           return intent;
                }

       public static Intent getDiscardIntent(Context context, String path) {
           Intent intent = new Intent(context, (Class<?>) VineUploadService.class);
           intent.setAction(ACTION_DISCARD);
           intent.putExtra("path", path);
           return intent;
                }

       public static Intent getDiscardAllIntent(Context context) {
           Intent intent = new Intent(context, (Class<?>) VineUploadService.class);
           intent.setAction(ACTION_DISCARD_ALL);
           return intent;
                }

       public static Intent getClearNotificationsIntent(Context context) {
           Intent intent = new Intent(context, (Class<?>) VineUploadService.class);
           intent.setAction(ACTION_CLEAR_NOTIFICATIONS);
           return intent;
                }

       public static Intent getShowProgressIntent(Context context) {
           Intent intent = new Intent(context, (Class<?>) VineUploadService.class);
           intent.setAction("show_notifications");
           return intent;
                }

       public static Intent getPostIntent(Context context, VineUpload upload, boolean isRetry) {
           if (!TextUtils.isEmpty(upload.postInfo)) {
                        try {
                   JsonParser parser = VineParsers.createParser(upload.postInfo);
                   PostInfo postInfo = VineUploadParsers.parsePostInfo(parser);
                   Intent result = getPostIntent(context, upload.path, postInfo.caption, postInfo.postToTwitter, postInfo.postToFacebook, postInfo.channelId, isRetry, postInfo.entities, postInfo.recipients, postInfo.message, upload.conversationRowId, postInfo.maxLoops);
                   result.putExtra("upload", upload);
                   return result;
                        } catch (IOException e) {
                   throw new RuntimeException("This should never happen.", e);
                        }
                    }
           return null;
                }

       public static Intent getPostIntent(Context context, String path, String caption, boolean postToTwitter, boolean postToFacebook, long channelId, boolean isRetry, ArrayList<VineEntity> entities, ArrayList<VineRecipient> recipients, String message, long conversationRowId, int maxLoops) {
           Intent intent = new Intent(context, (Class<?>) VineUploadService.class);
           intent.setAction(ACTION_POST);
           intent.putExtra(PostActivity.AG_IS_RETRY, isRetry);
           intent.putExtra("path", path);
           intent.putExtra("description", caption);
           intent.putExtra("postToTwitter", postToTwitter);
           intent.putExtra("postToFacebook", postToFacebook);
           intent.putExtra("channelId", channelId);
           intent.putExtra("entities", entities);
           intent.putExtra("recipients", recipients);
           intent.putExtra("message", message);
           intent.putExtra("created", System.currentTimeMillis());
           intent.putExtra("conversation_row_id", conversationRowId);
           intent.putExtra("maxLoops", maxLoops);
           return intent;
                }

       public static Intent getVMSharePostIntent(Context context, boolean isRetry, long conversationRowId, ArrayList<VineRecipient> recipients, long postId, String videoUrl, String thumbUrl) {
           return getVMPostIntent(context, null, isRetry, -1L, conversationRowId, recipients, null, postId, videoUrl, thumbUrl, 0);
                }

       public static Intent getVMPostIntent(Context context, String path, boolean isRetry, long conversationRowId, ArrayList<VineRecipient> recipients, String message, int maxLoops) {
           return getVMPostIntent(context, path, isRetry, -1L, conversationRowId, recipients, message, -1L, null, null, maxLoops);
                }

       public static Intent getVMPostIntent(Context context, String path, boolean isRetry, long mergedMessageIdForRetry, long conversationRowId, ArrayList<VineRecipient> recipients, String message, long postId, String videoUrl, String thumbUrl, int maxLoops) {
           Intent intent = new Intent(context, (Class<?>) VineUploadService.class);
           if (isRetry) {
               intent.setAction(ACTION_POST);
               if (TextUtils.isEmpty(path)) {
                   VineUpload upload = new VineUpload();
                   upload.mergedMessageId = mergedMessageIdForRetry;
                   upload.isPrivate = true;
                   upload.status = 1;
                   upload.conversationRowId = conversationRowId;
                   intent.putExtra("upload", upload);
                        }
                    } else {
               intent.setAction(ACTION_PREMERGE_AND_POST);
                    }
           intent.putExtra("is_private", true);
           intent.putExtra(PostActivity.AG_IS_RETRY, isRetry);
           intent.putExtra("path", path);
           intent.putExtra("recipients", recipients);
           intent.putExtra("message", message);
           intent.putExtra("postId", postId);
           intent.putExtra("videoUrl", videoUrl);
           intent.putExtra("thumbUrl", thumbUrl);
           intent.putExtra("created", System.currentTimeMillis());
           intent.putExtra("conversation_row_id", conversationRowId);
           intent.putExtra("maxLoops", maxLoops);
           return intent;
                }

       public static Intent getNotifyFailedIntent(Context context) {
           String path = null;
           String[] selArgs = {String.valueOf(2), "0"};
           long ownerId = AppController.getInstance(context).getActiveSessionReadOnly().getUserId();
           Uri contentUri = ContentUris.withAppendedId(VineUploads.Uploads.CONTENT_URI, ownerId);
           Cursor c = context.getContentResolver().query(contentUri, VineUploadsDatabaseSQL.UploadsQuery.PROJECTION, "status=? AND is_private=?", selArgs, "_id ASC");
           if (c != null) {
               if (c.moveToLast()) {
                   path = c.getString(1);
                        }
               c.close();
                    }
           Intent intent = new Intent(context, (Class<?>) VineUploadService.class);
           intent.setAction(ACTION_NOTIFY_FAILED);
           intent.putExtra("path", path);
           return intent;
                }

                @Override 
       public void onCreate() {
           super.onCreate();
           this.mPendingTasks = new LinkedHashSet<>();
           this.mPendingVMTasks = new LinkedHashSet<>();
           this.mUploadManager = new UploadManager(this);
           this.mStartIds = new LinkedList<>();
           this.mNotificationManager = (NotificationManager) getSystemService("notification");
           VineAccountHelper.resetActiveSession();
           this.mBitmaps = new HashMap<>();
           this.mMessenger = new Messenger(new IncomingHandler(this, null));
           this.mIsBinded = bindService(new Intent(this, (Class<?>) VineService.class), this.mVineServiceConnection, 1);
                }

       private class IncomingHandler extends Handler {
           private IncomingHandler() {
                    }

            IncomingHandler(VineUploadService x0, RunnableC05251 x1) {
               this();
                    }

                    @Override 
           public void handleMessage(Message msg) {
               SLog.dWithTag(VineUploadService.TAG, "Message received, what=" + msg.what + ", replyTo=" + msg.replyTo);
               int what = msg.what;
                        switch (what) {
                            case 1:
                       Messenger replyTo = msg.replyTo;
                       if (replyTo != null) {
                           VineUploadService.access$102(replyTo);
                           Bundle data = new Bundle();
                           ServiceAsyncTask currentTask = VineUploadService.access$200(VineUploadService.this);
                           if (currentTask != null) {
                               data.putLong("conversation_row_id", currentTask.conversationRowId);
                               data.putString(VineUploadService.AG_THUMBNAIL, currentTask.thumbnail);
                               boolean isActive = !AsyncTask.Status.FINISHED.equals(currentTask.getStatus());
                               data.putBoolean(VineUploadService.EXTRA_IS_ACTIVE, isActive);
                                    }
                                    try {
                               replyTo.send(Message.obtain(null, 3, data));
                                    } catch (RemoteException e) {
                               SLog.m459e("Failed to reply.", (Throwable) e);
                               return;
                                    }
                                }
                                break;
                            case 2:
                       VineUploadService.access$102(null);
                                break;
                        }
                    }
                }

                @Override 
       public void onDestroy() {
           super.onDestroy();
           if (this.mIsBinded) {
               unbindService(this.mVineServiceConnection);
                    }
                }

       private synchronized void showNotification(NotificationCompat.Builder nb, int id) {
                    try {
                        if (sShowProgress) {
                            try {
                       this.mNotificationManager.notify(NOTIFICATION_TAG, id, nb.build());
                            } catch (RuntimeException e) {
                       nb.setLargeIcon(null);
                       this.mNotificationManager.notify(NOTIFICATION_TAG, id, nb.build());
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       private synchronized void clearNotification(int id) {
                    try {
                        this.mNotificationManager.cancel(NOTIFICATION_TAG, id);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       private synchronized void cancelFailedNofitication() {
                    try {
                        if (UploadManager.uploadListIsEmpty(this)) {
                   clearNotification(0);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       private void stopIfNoTasksLeft() {
           if (this.mPendingVMTasks.isEmpty() && this.mPendingTasks.isEmpty() && !isRunning(this.mCurrentTask)) {
               stopService();
                    }
                }

       private boolean isRunning(ServiceAsyncTask task) {
                    return task != null && task.getStatus().equals(AsyncTask.Status.RUNNING);
                }

       private void stopService() {
                    synchronized (this.mLock) {
                        try {
                            for (Integer key : this.mStartIds) {
                       stopSelf(key.intValue());
                            }
                   this.mStartIds.clear();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                @Override 
       public int onStartCommand(Intent intent, int flags, int startId) {
           SLog.m447d("onStartCommand, intent={}", intent);
                    synchronized (this.mLock) {
                        try {
                   this.mStartIds.add(Integer.valueOf(startId));
                   if (intent == null) {
                       return 2;
                            }
                   String action = intent.getAction();
                   String path = intent.getStringExtra("path");
                            try {
                       if (action == null) {
                           throw new IllegalArgumentException("VineUploadService must be started with an explicit intent; set an action.");
                                }
                       if (ACTION_DISCARD_ALL.equals(action)) {
                           LinkedHashMap<String, Integer> paths = UploadManager.getAllPaths(this);
                                    for (String pathToDiscard : paths.keySet()) {
                               discardTask(pathToDiscard);
                                    }
                           cancelFailedNofitication();
                                } else if ("show_notifications".equals(action)) {
                           sShowProgress = true;
                                } else if (ACTION_CLEAR_NOTIFICATIONS.equals(action)) {
                           sShowProgress = false;
                           clearNotification(0);
                           clearNotification(1);
                                } else {
                           UploadProgressListener listener = null;
                           if (!TextUtils.isEmpty(path)) {
                               boolean showNotifications = intent.getBooleanExtra("show_notifications", false);
                               if (showNotifications) {
                                   listener = new NotificationProgressListener(path);
                                        } else {
                                   listener = new IPCProgressListener(path);
                                        }
                                    }
                           processCommand(path, action, intent, listener);
                                }
                       return 1;
                            } catch (Throwable e) {
                       CrashUtil.log("Upload Service crash on action {} and path {}", action, path);
                       throw e;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       private void processCommand(String path, String action, Intent intent, UploadProgressListener listener) {
                    VineUpload upload;
           SLog.m448d("Process command for action={}, path={}", action, path);
           ServiceAsyncTask newTask = new ServiceAsyncTask(path, action, intent, listener);
           if (ACTION_UPLOAD.equals(action)) {
               if (!this.mPendingTasks.contains(newTask) && !this.mPendingVMTasks.contains(newTask)) {
                   String hash = intent.getStringExtra("hash");
                   boolean isPrivate = intent.getBooleanExtra("is_private", false);
                   String reference = intent.getStringExtra("reference");
                   long conversationRowId = intent.getLongExtra("conversation_row_id", 0L);
                   UploadManager.addOrUpdateUpload(this, path, hash, reference, isPrivate, conversationRowId, -1L);
                   LinkedHashSet<ServiceAsyncTask> taskQueue = isPrivate ? this.mPendingVMTasks : this.mPendingTasks;
                   if (taskQueue.add(newTask)) {
                       SLog.m447d("Upload task added for path={}", path);
                            }
                        } else {
                   SLog.m447d("Upload task is already in queue for path={}", path);
                        }
                    } else if (ACTION_PREMERGE_AND_POST.equals(action)) {
               PostInfo info = getPostInfoFromIntent(intent);
               if (!TextUtils.isEmpty(path)) {
                   upload = UploadManager.getUpload(this, path);
                   UploadManager.setPostInfo(this, upload, info);
                        } else {
                   upload = new VineUpload();
                   upload.postInfo = info.toString();
                   upload.isPrivate = true;
                   upload.status = 1;
                   upload.conversationRowId = intent.getLongExtra("conversation_row_id", -1L);
                        }
               preMergeMessage(upload);
                    } else if (ACTION_POST.equals(action)) {
               VineUpload upload2 = UploadManager.getUpload(this, path);
               if (upload2 == null) {
                   upload2 = (VineUpload) intent.getParcelableExtra("upload");
                        }
               if (upload2 == null) {
                   SLog.m456e("Error posting. No upload via path nor via argument: {}.", path);
                   return;
                        }
               if (TextUtils.isEmpty(upload2.postInfo)) {
                   UploadManager.setPostInfo(this, upload2, getPostInfoFromIntent(intent));
                        }
               int status = upload2.status;
                        switch (status) {
                            case 0:
                       ServiceAsyncTask uploadTask = new ServiceAsyncTask(path, ACTION_UPLOAD, intent, listener);
                       LinkedHashSet<ServiceAsyncTask> taskQueue2 = upload2.isPrivate ? this.mPendingVMTasks : this.mPendingTasks;
                       if (!uploadTask.equals(this.mCurrentTask) && taskQueue2.add(uploadTask)) {
                           SLog.m448d("Upload task added for path={}", path, uploadTask.action);
                                }
                       if (taskQueue2.add(newTask)) {
                           SLog.m448d("Post task added for path={}", path, newTask.action);
                                }
                                break;
                            case 1:
                            case 2:
                       if (!TextUtils.isEmpty(upload2.path) && upload2.videoUrl == null) {
                           UploadManager.setStatus(this, upload2, 0);
                           startService(getPostIntent(this, upload2, true));
                           return;
                                } else if (!newTask.equals(this.mCurrentTask)) {
                           LinkedHashSet<ServiceAsyncTask> taskQueue3 = upload2.isPrivate ? this.mPendingVMTasks : this.mPendingTasks;
                           newTask.mUpload = upload2;
                           if (taskQueue3.add(newTask)) {
                               SLog.m448d("Post task added for path={}", path, newTask.action);
                                    }
                                }
                                break;
                            default:
                       SLog.m448d("Invalid state={} for path={}", Integer.valueOf(upload2.status), path);
                                break;
                        }
                    } else if (ACTION_NOTIFY_FAILED.equals(action)) {
               if (!TextUtils.isEmpty(path)) {
                   sShowProgress = true;
                   SLog.m447d("Notify failed upload for path={}.", path);
                   showFailedNotification(path);
                        }
                    } else if (ACTION_DISCARD.equals(action)) {
               if (!TextUtils.isEmpty(path)) {
                   discardTask(path);
                   return;
                        }
               return;
                    }
           executeNext();
                }

       public PostInfo getPostInfoFromIntent(Intent intent) {
           String caption = intent.getStringExtra("description");
           boolean postToTwitter = intent.getBooleanExtra("postToTwitter", false);
           boolean postToFacebook = intent.getBooleanExtra("postToFacebook", false);
           long channelId = intent.getLongExtra("channelId", -1L);
           ArrayList<VineEntity> entities = intent.getParcelableArrayListExtra("entities");
           String message = intent.getStringExtra("message");
           long postId = intent.getLongExtra("postId", -1L);
           String videoUrl = intent.getStringExtra("videoUrl");
           String thumbUrl = intent.getStringExtra("thumbUrl");
           int maxLoops = intent.getIntExtra("maxLoops", 0);
           long created = intent.getLongExtra("created", 0L);
           ArrayList<VineRecipient> recipients = (ArrayList) intent.getSerializableExtra("recipients");
           return new PostInfo(caption, postToTwitter, postToFacebook, channelId, entities, message, postId, videoUrl, thumbUrl, created, recipients, maxLoops);
                }

       private void preMergeMessage(VineUpload upload) {
           Bundle b = new Bundle();
           AppController.injectServiceBundle(b, VineAccountHelper.getActiveSession(this, true));
           b.putParcelable("upload", upload);
           this.mVineServiceConnection.queueAndExecute(93, b);
                }

       private void markPreMergedMessageAsUploadFailed(String uploadPath) {
           Bundle b = new Bundle();
           AppController.injectServiceBundle(b, VineAccountHelper.getActiveSession(this, true));
           b.putString("upload_path", uploadPath);
           this.mVineServiceConnection.queueAndExecute(100, b);
                }

       private void executeNext() {
                    LinkedHashSet<ServiceAsyncTask> taskQueue;
           SLog.m447d("Pending tasks: {}", this.mPendingTasks);
           SLog.m447d("Pending vm tasks: {}", this.mPendingVMTasks);
           if (this.mCurrentTask != null) {
               SLog.m446d("mCurrentTask is not null, will not execute next");
               return;
                    }
                    synchronized (this.mLock) {
                        try {
                   if (!this.mPendingVMTasks.isEmpty()) {
                       taskQueue = this.mPendingVMTasks;
                            } else {
                       taskQueue = this.mPendingTasks;
                            }
                   if (!taskQueue.isEmpty()) {
                       Iterator<ServiceAsyncTask> iterator = taskQueue.iterator();
                       ServiceAsyncTask currentTask = iterator.next();
                       if (!isRunning(currentTask)) {
                           currentTask.execute(new Void[0]);
                           SLog.m448d("Started executing task: action={}, path={}", currentTask.action, currentTask.path);
                                }
                       this.mCurrentTask = currentTask;
                       iterator.remove();
                            } else {
                       new Handler().post(new RunnableC05251());
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                
       class RunnableC05251 implements Runnable {
           RunnableC05251() {
                    }

                    @Override 
           public void run() {
               VineUploadService.access$300(VineUploadService.this);
                    }
                }

       private void showFailedNotification(String path) {
           VineUpload upload = UploadManager.getUpload(this, path);
           if (upload != null) {
               SLog.m448d("Show failed notification for {}, upload expired? {}.", path, Boolean.valueOf(upload.isExpired()));
               NotificationCompat.Builder nb = new NotificationCompat.Builder(this);
               nb.setSmallIcon(2130838053);
               nb.setLargeIcon(setScaledNotificationIcon(upload.thumbnailPath));
               Intent intent = StartActivity.getStartIntent(this, StartActivity.ACTION_VIEW_UPLOAD_LIST);
               PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
               nb.setContentIntent(pendingIntent);
               nb.setContentTitle(getText(C0257R.string.post_failed));
               nb.setContentText(getText(C0257R.string.tap_to_retry_or_cancel));
               nb.setAutoCancel(false);
               showNotification(nb, 0);
                    }
                }

       private Bitmap setScaledNotificationIcon(String thumbnailPath) {
                    Bitmap bm;
           if (!TextUtils.isEmpty(thumbnailPath)) {
               Bitmap bm2 = this.mBitmaps.get(thumbnailPath);
               if (bm2 == null) {
                   if (this.mNotificationPanelSize == -1) {
                       this.mNotificationPanelSize = getResources().getDimensionPixelSize(C0257R.dimen.notification_panel_height);
                            }
                   Bitmap thumbnailBitmap = BitmapFactory.decodeFile(thumbnailPath);
                   if (thumbnailBitmap != null && (bm = Bitmap.createScaledBitmap(thumbnailBitmap, this.mNotificationPanelSize, this.mNotificationPanelSize, true)) != null) {
                       if (this.mBitmaps.size() >= 10) {
                           this.mBitmaps.clear();
                                }
                       this.mBitmaps.put(thumbnailPath, bm);
                       return bm;
                            }
                        } else {
                   return bm2;
                        }
                    }
           if (this.mDefaultNotificationIcon == null) {
               this.mDefaultNotificationIcon = BitmapFactory.decodeResource(getResources(), 2130837952);
                    }
           this.mBitmaps.put(thumbnailPath, this.mDefaultNotificationIcon);
           return this.mDefaultNotificationIcon;
                }

       private void discardTask(String path) {
           SLog.m447d("Discard task for path={}", path);
           ServiceAsyncTask currentTask = this.mCurrentTask;
           if (currentTask != null && TextUtils.equals(currentTask.path, path)) {
               currentTask.setIsDiscarded(true);
               currentTask.cancel(true);
               SLog.m447d("In-progress task has been cancelled, path={}", path);
                    }
           ArrayList<ServiceAsyncTask> tasks = new ArrayList<>();
                    for (ServiceAsyncTask task : this.mPendingTasks) {
               if (task != null && TextUtils.equals(task.path, path)) {
                   tasks.add(task);
                        }
                    }
                    for (ServiceAsyncTask task2 : this.mPendingVMTasks) {
               if (task2 != null && TextUtils.equals(task2.path, path)) {
                   tasks.add(task2);
                        }
                    }
           if (!tasks.isEmpty()) {
               SLog.m446d("Queued task(s) will be cancelled.");
               this.mPendingTasks.removeAll(tasks);
               this.mPendingVMTasks.removeAll(tasks);
                    }
           discardUpload(path);
                }

       private void discardUpload(String path) {
           SLog.m447d("Discard video: {}", Boolean.valueOf(new File(path).delete()));
           SLog.m447d("Discard encoded video: {}", Boolean.valueOf(new File(path + UploadManager.getVersionFromPath(path).videoOutputExtension).delete()));
           SLog.m447d("Discard thumbnail: {}", Boolean.valueOf(new File(RecordConfigUtils.getThumbnailPath(path)).delete()));
           SLog.m447d("Discard metadata: {}", Boolean.valueOf(new File(RecordConfigUtils.getMetadataPath(path)).delete()));
           UploadManager.deleteUploadRecord(this, path);
           cancelFailedNofitication();
                }

       private class NotificationProgressListener extends UploadProgressListener {
           public NotificationProgressListener(String path) {
               super(path);
                    }

                    @Override 
           public void onTranscodeStarted() {
                    }

                    @Override 
           public void onPostFinished(boolean success) {
               VineUploadService.access$400(VineUploadService.this, 1);
               if (success) {
                   VineUploadService.access$500(VineUploadService.this);
                        }
                    }

                    @Override 
           public void progressChanged(TransferProgressEvent progressEvent) {
               this.currentSize += progressEvent.getBytesTransfered();
               int p = (int) ((this.currentSize * 100) / this.size);
               if (Math.abs(this.lastProgress - p) > 2) {
                   this.lastProgress = p;
                   updateUploadNotification(this.thumbnail);
                        }
                    }

                    @Override 
           public void updateTranscodeProgress(int p) {
               if (Math.abs(this.lastProgress - p) > 2) {
                   this.lastProgress = p;
                   updateTranscodeNotification(this.thumbnail);
                        }
                    }

                    @Override 
           public void showPostNotification(String thumbnail) {
               updateNotification(thumbnail, C0257R.string.posting, C0257R.string.almost_done, true);
                    }

           private void updateUploadNotification(String thumbnail) {
               updateNotification(thumbnail, C0257R.string.uploading, C0257R.string.uploading_text, true);
                    }

           private void updateTranscodeNotification(String thumbnail) {
               updateNotification(thumbnail, C0257R.string.preparing, C0257R.string.preparing_text, true);
                    }

           private void updateNotification(String thumbnail, int title, int text, boolean showProgress) {
               SLog.m448d("Update notification: {}, {}.", Boolean.valueOf(showProgress), Integer.valueOf(this.lastProgress));
               NotificationCompat.Builder nb = new NotificationCompat.Builder(VineUploadService.this);
               nb.setLargeIcon(VineUploadService.access$600(VineUploadService.this, thumbnail));
               nb.setSmallIcon(2130838053);
               nb.setContentTitle(VineUploadService.this.getText(title));
               nb.setContentText(VineUploadService.this.getText(text));
               nb.setWhen(0L);
               nb.setOngoing(true);
               if (showProgress && this.lastProgress > 0) {
                   nb.setProgress(100, this.lastProgress, false);
                        } else {
                   nb.setProgress(0, 0, true);
                        }
               VineUploadService.access$700(VineUploadService.this, nb, 1);
                    }
                }

       public class IPCProgressListener extends UploadProgressListener {
                    public static final double PROGRESS_RATIO_TRANSCODE = 0.65d;
                    public static final double PROGRESS_RATIO_UPLOAD = 0.33d;

           public IPCProgressListener(String path) {
               super(path);
                    }

                    @Override 
           public void onTranscodeStarted() {
               if (VineUploadService.access$100() != null) {
                   sendMessageToActivity(Message.obtain((Handler) null, 4));
                        }
                    }

                    @Override 
           public void onPostFinished(boolean success) {
               Bundle data = new Bundle();
               if (VineUploadService.access$100() != null) {
                   data.putBoolean(VineUploadService.EXTRA_SUCCESS, success);
                   sendMessageToActivity(Message.obtain(null, 7, data));
                   if (!success) {
                       data.putDouble(VineUploadService.EXTRA_UPLOAD_PROGRESS, 0.0d);
                       sendMessageToActivity(Message.obtain(null, 6, data));
                            }
                        }
                    }

                    @Override 
           public void updateTranscodeProgress(int p) {
               if (VineUploadService.access$100() != null && Math.abs(this.lastProgress - p) > 2) {
                   this.lastProgress = p;
                   Bundle data = new Bundle();
                   data.putDouble(VineUploadService.EXTRA_TRANSCODE_PROGRESS, p);
                   sendMessageToActivity(Message.obtain(null, 5, data));
                        }
                    }

                    @Override 
           public void progressChanged(TransferProgressEvent progressEvent) {
               this.currentSize += progressEvent.getBytesTransfered();
               int p = (int) ((this.currentSize * 100) / this.size);
               if (VineUploadService.access$100() != null && Math.abs(this.lastProgress - p) > 2) {
                   this.lastProgress = p;
                   Bundle data = new Bundle();
                   data.putDouble(VineUploadService.EXTRA_UPLOAD_PROGRESS, p);
                   sendMessageToActivity(Message.obtain(null, 6, data));
                        }
                    }

           private void sendMessageToActivity(Message msg) {
                        try {
                   VineUploadService.access$100().send(msg);
                        } catch (RemoteException e) {
                        }
                    }
                }

       public class ServiceAsyncTask extends AsyncTask<Void, Void, Boolean> {
                    public final String action;
                    public final long conversationRowId;
                    private int mExecutionStatus;
                    private boolean mIsDiscarded;
                    private boolean mIsPrivate;
                    private boolean mIsRetry;
                    private UploadProgressListener mListener;
                    public VineUpload mUpload;
                    public final String path;
                    public final String thumbnail;



           public ServiceAsyncTask(String path, String action, Intent intent, UploadProgressListener listener) {
               this.path = path;
               this.action = action;
               this.conversationRowId = intent.getLongExtra("conversation_row_id", -1L);
               this.mListener = listener;
               this.thumbnail = RecordConfigUtils.getThumbnailPath(path);
               this.mIsRetry = intent.getBooleanExtra(PostActivity.AG_IS_RETRY, false);
               this.mIsPrivate = intent.getBooleanExtra("is_private", false);
                    }

                    
                    
                    
                    
                    
                    
                    
                    
           protected Boolean doInBackground(Void... params) {
                        boolean z = false;
                        String[] uris;
                        synchronized (ServiceAsyncTask.class) {
                            try {
                                try {
                           if (this.mUpload == null) {
                               this.mUpload = UploadManager.getUpload(getContext(), this.path);
                                    }
                           if (this.mUpload == null || this.mUpload.status == -1) {
                               z = false;
                                    } else {
                               if (this.mUpload.path == null) {
                                   this.mUpload.path = this.path;
                                        }
                               if (VineUploadService.ACTION_POST.equals(this.action)) {
                                   if (this.mUpload.path == null && !this.mUpload.isPrivate) {
                                       CrashUtil.logException(new VineLoggingException("Invalid upload, but we know " + this.thumbnail + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.path));
                                       z = false;
                                            } else {
                                       Bundle b = new Bundle();
                                       b.putParcelable("upload", this.mUpload);
                                       VineUploadService.access$800(VineUploadService.this).queueAndExecute(98, b);
                                       if (!this.mIsRetry && this.mListener != null) {
                                           this.mListener.showPostNotification(this.thumbnail);
                                                }
                                       z = true;
                                            }
                                        } else if (VineUploadService.ACTION_UPLOAD.equals(this.action)) {
                                   File original = new File(this.path);
                                   if (original.exists()) {
                                       String hash = HashAsyncTask.getMD5Checksum(this.path);
                                       String expected = this.mUpload.hash;
                                       if (!hash.equals(expected)) {
                                           if (expected != null && expected.length() != 0) {
                                               SLog.m457e("Invalid hash: actual {}, expected {}.", hash, expected);
                                               z = false;
                                                    } else {
                                               CrashUtil.logException(new VineLoggingException("Expected hash was invalid: " + expected));
                                               RecordSessionVersion version = UploadManager.getVersionFromPath(this.path);
                                               String finalPath = original.getAbsolutePath();
                                               if (this.mListener != null) {
                                                   this.mListener.size = original.length() + new File(this.thumbnail).length();
                                                        }
                                               uris = VineUploadService.access$900(VineUploadService.this).upload(this, this.mListener, finalPath, this.thumbnail, this.path, this.mIsPrivate, version);
                                               if (isCancelled()) {
                                                            z = false;
                                                        } else if (uris != null) {
                                                   UploadManager.setUploadTime(getContext(), this.mUpload);
                                                   UploadManager.setUris(getContext(), this.mUpload, uris);
                                                   UploadManager.setStatus(getContext(), this.mUpload, 1);
                                                   if (!TextUtils.isEmpty(this.mUpload.postInfo)) {
                                                       VineUploadService.this.startService(VineUploadService.getPostIntent(getContext(), this.mUpload, this.mIsRetry));
                                                            }
                                                   z = true;
                                                        } else {
                                                   SLog.m447d("Current status: {}", Integer.valueOf(this.mUpload.status));
                                                   z = false;
                                                        }
                                                    }
                                                } else {
                                           RecordSessionVersion version2 = UploadManager.getVersionFromPath(this.path);
                                           String finalPath2 = original.getAbsolutePath();
                                           if (this.mListener != null) {
                                               this.mListener.size = original.length() + new File(this.thumbnail).length();
                                                    }
                                           uris = VineUploadService.access$900(VineUploadService.this).upload(this, this.mListener, finalPath2, this.thumbnail, this.path, this.mIsPrivate, version2);
                                           if (isCancelled()) {
                                                        z = false;
                                                    } else if (uris != null) {
                                               UploadManager.setUploadTime(getContext(), this.mUpload);
                                               UploadManager.setUris(getContext(), this.mUpload, uris);
                                               UploadManager.setStatus(getContext(), this.mUpload, 1);
                                               if (!TextUtils.isEmpty(this.mUpload.postInfo)) {
                                                   VineUploadService.this.startService(VineUploadService.getPostIntent(getContext(), this.mUpload, this.mIsRetry));
                                                        }
                                               z = true;
                                                    } else {
                                               SLog.m447d("Current status: {}", Integer.valueOf(this.mUpload.status));
                                               z = false;
                                                    }
                                                }
                                            } else {
                                       RecordSessionVersion version3 = UploadManager.getVersionFromPath(this.path);
                                       String finalPath3 = original.getAbsolutePath();
                                       if (this.mListener != null) {
                                           this.mListener.size = original.length() + new File(this.thumbnail).length();
                                                }
                                       uris = VineUploadService.access$900(VineUploadService.this).upload(this, this.mListener, finalPath3, this.thumbnail, this.path, this.mIsPrivate, version3);
                                       if (isCancelled()) {
                                                    z = false;
                                                } else if (uris != null) {
                                           UploadManager.setUploadTime(getContext(), this.mUpload);
                                           UploadManager.setUris(getContext(), this.mUpload, uris);
                                           UploadManager.setStatus(getContext(), this.mUpload, 1);
                                           if (!TextUtils.isEmpty(this.mUpload.postInfo)) {
                                               VineUploadService.this.startService(VineUploadService.getPostIntent(getContext(), this.mUpload, this.mIsRetry));
                                                    }
                                           z = true;
                                                } else {
                                           SLog.m447d("Current status: {}", Integer.valueOf(this.mUpload.status));
                                           z = false;
                                                }
                                            }
                                        } else {
                                   z = false;
                                        }
                                    }
                                } catch (Exception e) {
                           SLog.m459e("Failed to upload.", (Throwable) e);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
               return z;
                    }

                    
           protected void onPostExecute(Boolean success) {
               boolean taskSuccessful = success.booleanValue();
               if (!this.mIsDiscarded) {
                   if (taskSuccessful) {
                       this.mExecutionStatus = 2;
                       if (VineUploadService.ACTION_UPLOAD.equals(this.action) && TextUtils.isEmpty(this.mUpload.postInfo)) {
                           this.mExecutionStatus = 3;
                                }
                            } else {
                       this.mExecutionStatus = 1;
                       VineUploadService.this.startService(VineUploadService.getNotifyFailedIntent(getContext()));
                       if (this.mUpload != null) {
                           UploadManager.setStatus(getContext(), this.mUpload, 2, null);
                           if (VineUploadService.ACTION_UPLOAD.equals(this.action) && this.mUpload.isPrivate) {
                               VineUploadService.access$1000(VineUploadService.this, this.mUpload.path);
                                    }
                                }
                            }
                   onComplete();
                        }
                    }

                    @Override 
           protected void onCancelled() {
               SLog.m448d("Task cancelled. {}, {}.", this.path, this.action);
               if (!this.mIsDiscarded) {
                   VineUploadService.access$1100(VineUploadService.this, this.path);
                        }
               onComplete();
                    }

           private void onComplete() {
                        synchronized (VineUploadService.class) {
                            try {
                       VineUploadService.access$400(VineUploadService.this, 1);
                       VineUploadService.access$202(VineUploadService.this, null);
                       Intent intent = new Intent(VineUploadService.ACTION_UPLOAD_RESULT);
                       intent.putExtra(VineUploadService.AG_UPLOAD_RESULT, this.mExecutionStatus);
                       intent.putExtra("path", this.path);
                       intent.putExtra(VineUploadService.AG_THUMBNAIL, this.thumbnail);
                       VineUploadService.this.sendBroadcast(intent, CrossConstants.BROADCAST_PERMISSION);
                       VineUploadService.access$1200(VineUploadService.this);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

           public Context getContext() {
              return VineUploadService.this;
                    }

          public void updateProgress(int p) {
              if (this.mListener != null) {
                  this.mListener.updateTranscodeProgress(p);
                        }
                    }

          public boolean equals(Object o) {
              if (this == o) {
                            return true;
                        }
              if (!(o instanceof ServiceAsyncTask)) {
                            return false;
                        }
              ServiceAsyncTask that = (ServiceAsyncTask) o;
              if (this.path == null ? that.path != null : !this.path.equals(that.path)) {
                            return false;
                        }
              if (this.action != null) {
                            if (this.action.equals(that.action)) {
                                return true;
                            }
                        } else if (that.action == null) {
                            return true;
                        }
                        return false;
                    }

          public int hashCode() {
              int result = this.path != null ? this.path.hashCode() : 0;
              return (result * 31) + (this.action != null ? this.action.hashCode() : 0);
                    }

          public void setIsDiscarded(boolean isDiscarded) {
              this.mIsDiscarded = isDiscarded;
                    }
                }

                @Override 
      public IBinder onBind(Intent intent) {
          return this.mMessenger.getBinder();
                }

                @Override 
      public boolean onUnbind(Intent intent) {
          sUploadResultReceiver = null;
          stopIfNoTasksLeft();
          return false;
                }

                @Override 
      public void handleServiceResponse(int what, int responseCode, String reasonPhrase, Bundle bundle) {
                    switch (what) {
                        case 93:
                  VineUpload upload = (VineUpload) bundle.getParcelable("upload");
                  long messageRowId = bundle.getLong(VineService.EXTRA_PREMERGED_MESSAGE_ID);
                  upload.mergedMessageId = messageRowId;
                  PostInfo info = upload.getPostInfo();
                  SLog.m449d("Pre merge message complete. Path {} Row {} {}", upload.path, Long.valueOf(messageRowId), info.toString());
                  if (upload.path != null) {
                      UploadManager.setPostInfo(this, upload, info);
                      UploadManager.setUploadMessageRowId(this, upload.path, messageRowId);
                            }
                  startService(getPostIntent(this, upload, false));
                            break;
                        case 98:
                  VineUpload upload2 = (VineUpload) bundle.getParcelable("upload");
                  if (responseCode == 200) {
                      SLog.m446d("Post successful.");
                      cancelFailedNofitication();
                      if (!upload2.isPrivate) {
                          SharedPreferences prefs = Util.getDefaultSharedPrefs(this);
                          SharedPreferences.Editor e = prefs.edit();
                          e.putInt(Settings.PREF_POST_COUNT, prefs.getInt(Settings.PREF_POST_COUNT, 0) + 1);
                          e.apply();
                                } else {
                          String videoUrl = bundle.getString(VineService.EXTRA_VIDEO_URL);
                          String thumbUrl = bundle.getString("thumbnail_url");
                          if (videoUrl != null && thumbUrl != null) {
                              UploadManager.prepopulateCache(this, upload2.path, videoUrl, thumbUrl);
                                    }
                                }
                      boolean saveUpload = bundle.getBoolean(VineService.EXTRA_KEEP_UPLOAD, false);
                      if (!TextUtils.isEmpty(upload2.path) && !saveUpload) {
                          discardUpload(upload2.path);
                                    break;
                                }
                            } else {
                      startService(getNotifyFailedIntent(this));
                      if (upload2 != null) {
                          UploadManager.setStatus(this, upload2, 2, upload2.captchaUrl);
                                }
                                break;
                            }
                            break;
                    }
                }
            }
