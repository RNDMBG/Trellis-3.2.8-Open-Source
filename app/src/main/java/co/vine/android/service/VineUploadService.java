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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 68 */    public class VineUploadService extends Service implements VineServiceConnection.ServiceResponseHandler {
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

/* 69 */        static /* synthetic */ Messenger access$100() {
/* 70 */            return sUploadResultReceiver;
                }

/* 69 */        static /* synthetic */ void access$1000(VineUploadService x0, String x1) {
/* 70 */            x0.markPreMergedMessageAsUploadFailed(x1);
                }

/* 69 */        static /* synthetic */ Messenger access$102(Messenger x0) {
/* 70 */            sUploadResultReceiver = x0;
                    return x0;
                }

/* 69 */        static /* synthetic */ void access$1100(VineUploadService x0, String x1) {
/* 70 */            x0.discardUpload(x1);
                }

/* 69 */        static /* synthetic */ void access$1200(VineUploadService x0) {
/* 70 */            x0.executeNext();
                }

/* 69 */        static /* synthetic */ ServiceAsyncTask access$200(VineUploadService x0) {
/* 70 */            return x0.mCurrentTask;
                }

/* 69 */        static /* synthetic */ ServiceAsyncTask access$202(VineUploadService x0, ServiceAsyncTask x1) {
/* 70 */            x0.mCurrentTask = x1;
                    return x1;
                }

/* 69 */        static /* synthetic */ void access$300(VineUploadService x0) {
/* 70 */            x0.stopIfNoTasksLeft();
                }

/* 69 */        static /* synthetic */ void access$400(VineUploadService x0, int x1) {
/* 70 */            x0.clearNotification(x1);
                }

/* 69 */        static /* synthetic */ void access$500(VineUploadService x0) {
/* 70 */            x0.cancelFailedNofitication();
                }

/* 69 */        static /* synthetic */ Bitmap access$600(VineUploadService x0, String x1) {
/* 70 */            return x0.setScaledNotificationIcon(x1);
                }

/* 69 */        static /* synthetic */ void access$700(VineUploadService x0, NotificationCompat.Builder x1, int x2) {
/* 70 */            x0.showNotification(x1, x2);
                }

/* 69 */        static /* synthetic */ VineServiceConnection access$800(VineUploadService x0) {
/* 70 */            return x0.mVineServiceConnection;
                }

/* 69 */        static /* synthetic */ UploadManager access$900(VineUploadService x0) {
/* 70 */            return x0.mUploadManager;
                }

/* 158 */       public static Intent getUploadIntent(Context context, String path, String hash, String thumbnail, String reference, boolean isMessaging, long conversationRowId) {
/* 159 */           sShowProgress = false;
/* 160 */           Intent intent = new Intent(context, (Class<?>) VineUploadService.class);
/* 161 */           intent.setAction(ACTION_UPLOAD);
/* 162 */           intent.putExtra("path", path);
/* 163 */           intent.putExtra("hash", hash);
/* 164 */           intent.putExtra(AG_THUMBNAIL, thumbnail);
/* 165 */           intent.putExtra("reference", reference);
/* 166 */           intent.putExtra("is_private", isMessaging);
/* 167 */           intent.putExtra("show_notifications", true);
/* 168 */           intent.putExtra("conversation_row_id", conversationRowId);
/* 169 */           return intent;
                }

/* 172 */       public static Intent getDiscardIntent(Context context, String path) {
/* 173 */           Intent intent = new Intent(context, (Class<?>) VineUploadService.class);
/* 174 */           intent.setAction(ACTION_DISCARD);
/* 175 */           intent.putExtra("path", path);
/* 176 */           return intent;
                }

/* 179 */       public static Intent getDiscardAllIntent(Context context) {
/* 180 */           Intent intent = new Intent(context, (Class<?>) VineUploadService.class);
/* 181 */           intent.setAction(ACTION_DISCARD_ALL);
/* 182 */           return intent;
                }

/* 185 */       public static Intent getClearNotificationsIntent(Context context) {
/* 186 */           Intent intent = new Intent(context, (Class<?>) VineUploadService.class);
/* 187 */           intent.setAction(ACTION_CLEAR_NOTIFICATIONS);
/* 188 */           return intent;
                }

/* 191 */       public static Intent getShowProgressIntent(Context context) {
/* 192 */           Intent intent = new Intent(context, (Class<?>) VineUploadService.class);
/* 193 */           intent.setAction("show_notifications");
/* 194 */           return intent;
                }

/* 197 */       public static Intent getPostIntent(Context context, VineUpload upload, boolean isRetry) {
/* 198 */           if (!TextUtils.isEmpty(upload.postInfo)) {
                        try {
/* 200 */                   JsonParser parser = VineParsers.createParser(upload.postInfo);
/* 201 */                   PostInfo postInfo = VineUploadParsers.parsePostInfo(parser);
/* 202 */                   Intent result = getPostIntent(context, upload.path, postInfo.caption, postInfo.postToTwitter, postInfo.postToFacebook, postInfo.channelId, isRetry, postInfo.entities, postInfo.recipients, postInfo.message, upload.conversationRowId, postInfo.maxLoops);
/* 206 */                   result.putExtra("upload", upload);
/* 212 */                   return result;
                        } catch (IOException e) {
/* 209 */                   throw new RuntimeException("This should never happen.", e);
                        }
                    }
/* 212 */           return null;
                }

/* 219 */       public static Intent getPostIntent(Context context, String path, String caption, boolean postToTwitter, boolean postToFacebook, long channelId, boolean isRetry, ArrayList<VineEntity> entities, ArrayList<VineRecipient> recipients, String message, long conversationRowId, int maxLoops) {
/* 220 */           Intent intent = new Intent(context, (Class<?>) VineUploadService.class);
/* 221 */           intent.setAction(ACTION_POST);
/* 222 */           intent.putExtra(PostActivity.AG_IS_RETRY, isRetry);
/* 223 */           intent.putExtra("path", path);
/* 224 */           intent.putExtra("description", caption);
/* 225 */           intent.putExtra("postToTwitter", postToTwitter);
/* 226 */           intent.putExtra("postToFacebook", postToFacebook);
/* 227 */           intent.putExtra("channelId", channelId);
/* 228 */           intent.putExtra("entities", entities);
/* 229 */           intent.putExtra("recipients", recipients);
/* 230 */           intent.putExtra("message", message);
/* 231 */           intent.putExtra("created", System.currentTimeMillis());
/* 232 */           intent.putExtra("conversation_row_id", conversationRowId);
/* 233 */           intent.putExtra("maxLoops", maxLoops);
/* 234 */           return intent;
                }

/* 238 */       public static Intent getVMSharePostIntent(Context context, boolean isRetry, long conversationRowId, ArrayList<VineRecipient> recipients, long postId, String videoUrl, String thumbUrl) {
/* 239 */           return getVMPostIntent(context, null, isRetry, -1L, conversationRowId, recipients, null, postId, videoUrl, thumbUrl, 0);
                }

/* 244 */       public static Intent getVMPostIntent(Context context, String path, boolean isRetry, long conversationRowId, ArrayList<VineRecipient> recipients, String message, int maxLoops) {
/* 245 */           return getVMPostIntent(context, path, isRetry, -1L, conversationRowId, recipients, message, -1L, null, null, maxLoops);
                }

/* 249 */       public static Intent getVMPostIntent(Context context, String path, boolean isRetry, long mergedMessageIdForRetry, long conversationRowId, ArrayList<VineRecipient> recipients, String message, long postId, String videoUrl, String thumbUrl, int maxLoops) {
/* 250 */           Intent intent = new Intent(context, (Class<?>) VineUploadService.class);
/* 252 */           if (isRetry) {
/* 253 */               intent.setAction(ACTION_POST);
/* 254 */               if (TextUtils.isEmpty(path)) {
/* 256 */                   VineUpload upload = new VineUpload();
/* 257 */                   upload.mergedMessageId = mergedMessageIdForRetry;
/* 258 */                   upload.isPrivate = true;
/* 259 */                   upload.status = 1;
/* 260 */                   upload.conversationRowId = conversationRowId;
/* 261 */                   intent.putExtra("upload", upload);
                        }
                    } else {
/* 264 */               intent.setAction(ACTION_PREMERGE_AND_POST);
                    }
/* 266 */           intent.putExtra("is_private", true);
/* 267 */           intent.putExtra(PostActivity.AG_IS_RETRY, isRetry);
/* 268 */           intent.putExtra("path", path);
/* 269 */           intent.putExtra("recipients", recipients);
/* 270 */           intent.putExtra("message", message);
/* 271 */           intent.putExtra("postId", postId);
/* 272 */           intent.putExtra("videoUrl", videoUrl);
/* 273 */           intent.putExtra("thumbUrl", thumbUrl);
/* 274 */           intent.putExtra("created", System.currentTimeMillis());
/* 275 */           intent.putExtra("conversation_row_id", conversationRowId);
/* 276 */           intent.putExtra("maxLoops", maxLoops);
/* 277 */           return intent;
                }

/* 280 */       public static Intent getNotifyFailedIntent(Context context) {
/* 281 */           String path = null;
/* 283 */           String[] selArgs = {String.valueOf(2), "0"};
/* 284 */           long ownerId = AppController.getInstance(context).getActiveSessionReadOnly().getUserId();
/* 285 */           Uri contentUri = ContentUris.withAppendedId(VineUploads.Uploads.CONTENT_URI, ownerId);
/* 286 */           Cursor c = context.getContentResolver().query(contentUri, VineUploadsDatabaseSQL.UploadsQuery.PROJECTION, "status=? AND is_private=?", selArgs, "_id ASC");
/* 288 */           if (c != null) {
/* 289 */               if (c.moveToLast()) {
/* 290 */                   path = c.getString(1);
                        }
/* 292 */               c.close();
                    }
/* 295 */           Intent intent = new Intent(context, (Class<?>) VineUploadService.class);
/* 296 */           intent.setAction(ACTION_NOTIFY_FAILED);
/* 297 */           intent.putExtra("path", path);
/* 298 */           return intent;
                }

                @Override // android.app.Service
/* 302 */       public void onCreate() {
/* 303 */           super.onCreate();
/* 304 */           this.mPendingTasks = new LinkedHashSet<>();
/* 305 */           this.mPendingVMTasks = new LinkedHashSet<>();
/* 306 */           this.mUploadManager = new UploadManager(this);
/* 307 */           this.mStartIds = new LinkedList<>();
/* 308 */           this.mNotificationManager = (NotificationManager) getSystemService("notification");
/* 309 */           VineAccountHelper.resetActiveSession();
/* 310 */           this.mBitmaps = new HashMap<>();
/* 311 */           this.mMessenger = new Messenger(new IncomingHandler(this, null));
/* 313 */           this.mIsBinded = bindService(new Intent(this, (Class<?>) VineService.class), this.mVineServiceConnection, 1);
                }

/* 315 */       private class IncomingHandler extends Handler {
/* 316 */           private IncomingHandler() {
                    }

/* 316 */           /* synthetic */ IncomingHandler(VineUploadService x0, RunnableC05251 x1) {
/* 317 */               this();
                    }

                    @Override // android.os.Handler
/* 319 */           public void handleMessage(Message msg) {
/* 320 */               SLog.dWithTag(VineUploadService.TAG, "Message received, what=" + msg.what + ", replyTo=" + msg.replyTo);
/* 321 */               int what = msg.what;
                        switch (what) {
                            case 1:
/* 324 */                       Messenger replyTo = msg.replyTo;
/* 325 */                       if (replyTo != null) {
/* 326 */                           VineUploadService.access$102(replyTo);
/* 327 */                           Bundle data = new Bundle();
/* 328 */                           ServiceAsyncTask currentTask = VineUploadService.access$200(VineUploadService.this);
/* 329 */                           if (currentTask != null) {
/* 330 */                               data.putLong("conversation_row_id", currentTask.conversationRowId);
/* 331 */                               data.putString(VineUploadService.AG_THUMBNAIL, currentTask.thumbnail);
/* 332 */                               boolean isActive = !AsyncTask.Status.FINISHED.equals(currentTask.getStatus());
/* 333 */                               data.putBoolean(VineUploadService.EXTRA_IS_ACTIVE, isActive);
                                    }
                                    try {
/* 336 */                               replyTo.send(Message.obtain(null, 3, data));
                                    } catch (RemoteException e) {
/* 339 */                               SLog.m459e("Failed to reply.", (Throwable) e);
/* 349 */                               return;
                                    }
                                }
                                break;
                            case 2:
/* 345 */                       VineUploadService.access$102(null);
                                break;
                        }
                    }
                }

                @Override // android.app.Service
/* 353 */       public void onDestroy() {
/* 354 */           super.onDestroy();
/* 355 */           if (this.mIsBinded) {
/* 356 */               unbindService(this.mVineServiceConnection);
                    }
                }

/* 360 */       private synchronized void showNotification(NotificationCompat.Builder nb, int id) {
                    try {
                        if (sShowProgress) {
                            try {
/* 364 */                       this.mNotificationManager.notify(NOTIFICATION_TAG, id, nb.build());
                            } catch (RuntimeException e) {
/* 366 */                       nb.setLargeIcon(null);
/* 367 */                       this.mNotificationManager.notify(NOTIFICATION_TAG, id, nb.build());
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 372 */       private synchronized void clearNotification(int id) {
                    try {
                        this.mNotificationManager.cancel(NOTIFICATION_TAG, id);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 376 */       private synchronized void cancelFailedNofitication() {
                    try {
                        if (UploadManager.uploadListIsEmpty(this)) {
/* 378 */                   clearNotification(0);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 382 */       private void stopIfNoTasksLeft() {
/* 383 */           if (this.mPendingVMTasks.isEmpty() && this.mPendingTasks.isEmpty() && !isRunning(this.mCurrentTask)) {
/* 384 */               stopService();
                    }
                }

/* 388 */       private boolean isRunning(ServiceAsyncTask task) {
                    return task != null && task.getStatus().equals(AsyncTask.Status.RUNNING);
                }

/* 392 */       private void stopService() {
                    synchronized (this.mLock) {
                        try {
                            for (Integer key : this.mStartIds) {
/* 395 */                       stopSelf(key.intValue());
                            }
/* 397 */                   this.mStartIds.clear();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                @Override // android.app.Service
/* 402 */       public int onStartCommand(Intent intent, int flags, int startId) {
/* 403 */           SLog.m447d("onStartCommand, intent={}", intent);
                    synchronized (this.mLock) {
                        try {
/* 405 */                   this.mStartIds.add(Integer.valueOf(startId));
/* 406 */                   if (intent == null) {
/* 407 */                       return 2;
                            }
/* 409 */                   String action = intent.getAction();
/* 410 */                   String path = intent.getStringExtra("path");
                            try {
/* 413 */                       if (action == null) {
/* 414 */                           throw new IllegalArgumentException("VineUploadService must be started with an explicit intent; set an action.");
                                }
/* 416 */                       if (ACTION_DISCARD_ALL.equals(action)) {
/* 417 */                           LinkedHashMap<String, Integer> paths = UploadManager.getAllPaths(this);
                                    for (String pathToDiscard : paths.keySet()) {
/* 419 */                               discardTask(pathToDiscard);
                                    }
/* 421 */                           cancelFailedNofitication();
                                } else if ("show_notifications".equals(action)) {
/* 423 */                           sShowProgress = true;
                                } else if (ACTION_CLEAR_NOTIFICATIONS.equals(action)) {
/* 425 */                           sShowProgress = false;
/* 426 */                           clearNotification(0);
/* 427 */                           clearNotification(1);
                                } else {
/* 429 */                           UploadProgressListener listener = null;
/* 430 */                           if (!TextUtils.isEmpty(path)) {
/* 431 */                               boolean showNotifications = intent.getBooleanExtra("show_notifications", false);
/* 432 */                               if (showNotifications) {
/* 433 */                                   listener = new NotificationProgressListener(path);
                                        } else {
/* 435 */                                   listener = new IPCProgressListener(path);
                                        }
                                    }
/* 438 */                           processCommand(path, action, intent, listener);
                                }
/* 445 */                       return 1;
                            } catch (Throwable e) {
/* 441 */                       CrashUtil.log("Upload Service crash on action {} and path {}", action, path);
/* 442 */                       throw e;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 450 */       private void processCommand(String path, String action, Intent intent, UploadProgressListener listener) {
                    VineUpload upload;
/* 451 */           SLog.m448d("Process command for action={}, path={}", action, path);
/* 452 */           ServiceAsyncTask newTask = new ServiceAsyncTask(path, action, intent, listener);
/* 454 */           if (ACTION_UPLOAD.equals(action)) {
/* 455 */               if (!this.mPendingTasks.contains(newTask) && !this.mPendingVMTasks.contains(newTask)) {
/* 456 */                   String hash = intent.getStringExtra("hash");
/* 457 */                   boolean isPrivate = intent.getBooleanExtra("is_private", false);
/* 458 */                   String reference = intent.getStringExtra("reference");
/* 459 */                   long conversationRowId = intent.getLongExtra("conversation_row_id", 0L);
/* 461 */                   UploadManager.addOrUpdateUpload(this, path, hash, reference, isPrivate, conversationRowId, -1L);
/* 463 */                   LinkedHashSet<ServiceAsyncTask> taskQueue = isPrivate ? this.mPendingVMTasks : this.mPendingTasks;
/* 464 */                   if (taskQueue.add(newTask)) {
/* 465 */                       SLog.m447d("Upload task added for path={}", path);
                            }
                        } else {
/* 468 */                   SLog.m447d("Upload task is already in queue for path={}", path);
                        }
                    } else if (ACTION_PREMERGE_AND_POST.equals(action)) {
/* 472 */               PostInfo info = getPostInfoFromIntent(intent);
/* 474 */               if (!TextUtils.isEmpty(path)) {
/* 476 */                   upload = UploadManager.getUpload(this, path);
/* 477 */                   UploadManager.setPostInfo(this, upload, info);
                        } else {
/* 480 */                   upload = new VineUpload();
/* 481 */                   upload.postInfo = info.toString();
/* 482 */                   upload.isPrivate = true;
/* 483 */                   upload.status = 1;
/* 484 */                   upload.conversationRowId = intent.getLongExtra("conversation_row_id", -1L);
                        }
/* 486 */               preMergeMessage(upload);
                    } else if (ACTION_POST.equals(action)) {
/* 488 */               VineUpload upload2 = UploadManager.getUpload(this, path);
/* 489 */               if (upload2 == null) {
/* 490 */                   upload2 = (VineUpload) intent.getParcelableExtra("upload");
                        }
/* 493 */               if (upload2 == null) {
/* 494 */                   SLog.m456e("Error posting. No upload via path nor via argument: {}.", path);
/* 558 */                   return;
                        }
/* 499 */               if (TextUtils.isEmpty(upload2.postInfo)) {
/* 501 */                   UploadManager.setPostInfo(this, upload2, getPostInfoFromIntent(intent));
                        }
/* 504 */               int status = upload2.status;
                        switch (status) {
                            case 0:
/* 507 */                       ServiceAsyncTask uploadTask = new ServiceAsyncTask(path, ACTION_UPLOAD, intent, listener);
/* 509 */                       LinkedHashSet<ServiceAsyncTask> taskQueue2 = upload2.isPrivate ? this.mPendingVMTasks : this.mPendingTasks;
/* 510 */                       if (!uploadTask.equals(this.mCurrentTask) && taskQueue2.add(uploadTask)) {
/* 512 */                           SLog.m448d("Upload task added for path={}", path, uploadTask.action);
                                }
/* 515 */                       if (taskQueue2.add(newTask)) {
/* 516 */                           SLog.m448d("Post task added for path={}", path, newTask.action);
                                }
                                break;
                            case 1:
                            case 2:
/* 524 */                       if (!TextUtils.isEmpty(upload2.path) && upload2.videoUrl == null) {
/* 525 */                           UploadManager.setStatus(this, upload2, 0);
/* 526 */                           startService(getPostIntent(this, upload2, true));
/* 558 */                           return;
                                } else if (!newTask.equals(this.mCurrentTask)) {
/* 531 */                           LinkedHashSet<ServiceAsyncTask> taskQueue3 = upload2.isPrivate ? this.mPendingVMTasks : this.mPendingTasks;
/* 532 */                           newTask.mUpload = upload2;
/* 533 */                           if (taskQueue3.add(newTask)) {
/* 534 */                               SLog.m448d("Post task added for path={}", path, newTask.action);
                                    }
                                }
                                break;
                            default:
/* 540 */                       SLog.m448d("Invalid state={} for path={}", Integer.valueOf(upload2.status), path);
                                break;
                        }
                    } else if (ACTION_NOTIFY_FAILED.equals(action)) {
/* 545 */               if (!TextUtils.isEmpty(path)) {
/* 547 */                   sShowProgress = true;
/* 548 */                   SLog.m447d("Notify failed upload for path={}.", path);
/* 549 */                   showFailedNotification(path);
                        }
                    } else if (ACTION_DISCARD.equals(action)) {
/* 552 */               if (!TextUtils.isEmpty(path)) {
/* 553 */                   discardTask(path);
/* 558 */                   return;
                        }
/* 558 */               return;
                    }
/* 557 */           executeNext();
                }

/* 560 */       public PostInfo getPostInfoFromIntent(Intent intent) {
/* 561 */           String caption = intent.getStringExtra("description");
/* 562 */           boolean postToTwitter = intent.getBooleanExtra("postToTwitter", false);
/* 563 */           boolean postToFacebook = intent.getBooleanExtra("postToFacebook", false);
/* 564 */           long channelId = intent.getLongExtra("channelId", -1L);
/* 565 */           ArrayList<VineEntity> entities = intent.getParcelableArrayListExtra("entities");
/* 566 */           String message = intent.getStringExtra("message");
/* 567 */           long postId = intent.getLongExtra("postId", -1L);
/* 568 */           String videoUrl = intent.getStringExtra("videoUrl");
/* 569 */           String thumbUrl = intent.getStringExtra("thumbUrl");
/* 570 */           int maxLoops = intent.getIntExtra("maxLoops", 0);
/* 571 */           long created = intent.getLongExtra("created", 0L);
/* 572 */           ArrayList<VineRecipient> recipients = (ArrayList) intent.getSerializableExtra("recipients");
/* 575 */           return new PostInfo(caption, postToTwitter, postToFacebook, channelId, entities, message, postId, videoUrl, thumbUrl, created, recipients, maxLoops);
                }

/* 580 */       private void preMergeMessage(VineUpload upload) {
/* 581 */           Bundle b = new Bundle();
/* 582 */           AppController.injectServiceBundle(b, VineAccountHelper.getActiveSession(this, true));
/* 583 */           b.putParcelable("upload", upload);
/* 584 */           this.mVineServiceConnection.queueAndExecute(93, b);
                }

/* 587 */       private void markPreMergedMessageAsUploadFailed(String uploadPath) {
/* 588 */           Bundle b = new Bundle();
/* 589 */           AppController.injectServiceBundle(b, VineAccountHelper.getActiveSession(this, true));
/* 590 */           b.putString("upload_path", uploadPath);
/* 591 */           this.mVineServiceConnection.queueAndExecute(100, b);
                }

/* 594 */       private void executeNext() {
                    LinkedHashSet<ServiceAsyncTask> taskQueue;
/* 595 */           SLog.m447d("Pending tasks: {}", this.mPendingTasks);
/* 596 */           SLog.m447d("Pending vm tasks: {}", this.mPendingVMTasks);
/* 597 */           if (this.mCurrentTask != null) {
/* 598 */               SLog.m446d("mCurrentTask is not null, will not execute next");
/* 628 */               return;
                    }
                    synchronized (this.mLock) {
                        try {
/* 604 */                   if (!this.mPendingVMTasks.isEmpty()) {
/* 605 */                       taskQueue = this.mPendingVMTasks;
                            } else {
/* 607 */                       taskQueue = this.mPendingTasks;
                            }
/* 609 */                   if (!taskQueue.isEmpty()) {
/* 610 */                       Iterator<ServiceAsyncTask> iterator = taskQueue.iterator();
/* 611 */                       ServiceAsyncTask currentTask = iterator.next();
/* 612 */                       if (!isRunning(currentTask)) {
/* 613 */                           currentTask.execute(new Void[0]);
/* 614 */                           SLog.m448d("Started executing task: action={}, path={}", currentTask.action, currentTask.path);
                                }
/* 617 */                       this.mCurrentTask = currentTask;
/* 618 */                       iterator.remove();
                            } else {
/* 620 */                       new Handler().post(new RunnableC05251());
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.service.VineUploadService$1 */
/* 618 */       class RunnableC05251 implements Runnable {
/* 619 */           RunnableC05251() {
                    }

                    @Override // java.lang.Runnable
/* 622 */           public void run() {
/* 623 */               VineUploadService.access$300(VineUploadService.this);
                    }
                }

/* 630 */       private void showFailedNotification(String path) {
/* 631 */           VineUpload upload = UploadManager.getUpload(this, path);
/* 632 */           if (upload != null) {
/* 634 */               SLog.m448d("Show failed notification for {}, upload expired? {}.", path, Boolean.valueOf(upload.isExpired()));
/* 635 */               NotificationCompat.Builder nb = new NotificationCompat.Builder(this);
/* 636 */               nb.setSmallIcon(2130838053);
/* 638 */               nb.setLargeIcon(setScaledNotificationIcon(upload.thumbnailPath));
/* 640 */               Intent intent = StartActivity.getStartIntent(this, StartActivity.ACTION_VIEW_UPLOAD_LIST);
/* 641 */               PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
/* 642 */               nb.setContentIntent(pendingIntent);
/* 644 */               nb.setContentTitle(getText(C0257R.string.post_failed));
/* 645 */               nb.setContentText(getText(C0257R.string.tap_to_retry_or_cancel));
/* 646 */               nb.setAutoCancel(false);
/* 647 */               showNotification(nb, 0);
                    }
                }

/* 653 */       private Bitmap setScaledNotificationIcon(String thumbnailPath) {
                    Bitmap bm;
/* 654 */           if (!TextUtils.isEmpty(thumbnailPath)) {
/* 655 */               Bitmap bm2 = this.mBitmaps.get(thumbnailPath);
/* 656 */               if (bm2 == null) {
/* 657 */                   if (this.mNotificationPanelSize == -1) {
/* 658 */                       this.mNotificationPanelSize = getResources().getDimensionPixelSize(C0257R.dimen.notification_panel_height);
                            }
/* 660 */                   Bitmap thumbnailBitmap = BitmapFactory.decodeFile(thumbnailPath);
/* 661 */                   if (thumbnailBitmap != null && (bm = Bitmap.createScaledBitmap(thumbnailBitmap, this.mNotificationPanelSize, this.mNotificationPanelSize, true)) != null) {
/* 664 */                       if (this.mBitmaps.size() >= 10) {
/* 665 */                           this.mBitmaps.clear();
                                }
/* 667 */                       this.mBitmaps.put(thumbnailPath, bm);
/* 679 */                       return bm;
                            }
                        } else {
/* 679 */                   return bm2;
                        }
                    }
/* 675 */           if (this.mDefaultNotificationIcon == null) {
/* 676 */               this.mDefaultNotificationIcon = BitmapFactory.decodeResource(getResources(), 2130837952);
                    }
/* 678 */           this.mBitmaps.put(thumbnailPath, this.mDefaultNotificationIcon);
/* 679 */           return this.mDefaultNotificationIcon;
                }

/* 682 */       private void discardTask(String path) {
/* 683 */           SLog.m447d("Discard task for path={}", path);
/* 684 */           ServiceAsyncTask currentTask = this.mCurrentTask;
/* 685 */           if (currentTask != null && TextUtils.equals(currentTask.path, path)) {
/* 686 */               currentTask.setIsDiscarded(true);
/* 687 */               currentTask.cancel(true);
/* 688 */               SLog.m447d("In-progress task has been cancelled, path={}", path);
                    }
/* 691 */           ArrayList<ServiceAsyncTask> tasks = new ArrayList<>();
                    for (ServiceAsyncTask task : this.mPendingTasks) {
/* 693 */               if (task != null && TextUtils.equals(task.path, path)) {
/* 694 */                   tasks.add(task);
                        }
                    }
                    for (ServiceAsyncTask task2 : this.mPendingVMTasks) {
/* 699 */               if (task2 != null && TextUtils.equals(task2.path, path)) {
/* 700 */                   tasks.add(task2);
                        }
                    }
/* 704 */           if (!tasks.isEmpty()) {
/* 705 */               SLog.m446d("Queued task(s) will be cancelled.");
/* 706 */               this.mPendingTasks.removeAll(tasks);
/* 707 */               this.mPendingVMTasks.removeAll(tasks);
                    }
/* 709 */           discardUpload(path);
                }

/* 712 */       private void discardUpload(String path) {
/* 713 */           SLog.m447d("Discard video: {}", Boolean.valueOf(new File(path).delete()));
/* 714 */           SLog.m447d("Discard encoded video: {}", Boolean.valueOf(new File(path + UploadManager.getVersionFromPath(path).videoOutputExtension).delete()));
/* 715 */           SLog.m447d("Discard thumbnail: {}", Boolean.valueOf(new File(RecordConfigUtils.getThumbnailPath(path)).delete()));
/* 716 */           SLog.m447d("Discard metadata: {}", Boolean.valueOf(new File(RecordConfigUtils.getMetadataPath(path)).delete()));
/* 717 */           UploadManager.deleteUploadRecord(this, path);
/* 718 */           cancelFailedNofitication();
                }

/* 721 */       private class NotificationProgressListener extends UploadProgressListener {
/* 722 */           public NotificationProgressListener(String path) {
/* 724 */               super(path);
                    }

                    @Override // co.vine.android.service.UploadProgressListener
/* 728 */           public void onTranscodeStarted() {
                    }

                    @Override // co.vine.android.service.UploadProgressListener
/* 732 */           public void onPostFinished(boolean success) {
/* 733 */               VineUploadService.access$400(VineUploadService.this, 1);
/* 734 */               if (success) {
/* 735 */                   VineUploadService.access$500(VineUploadService.this);
                        }
                    }

                    @Override // co.vine.android.service.UploadProgressListener, co.vine.android.network.FileNetworkEntity.ProgressListener
/* 740 */           public void progressChanged(TransferProgressEvent progressEvent) {
/* 741 */               this.currentSize += progressEvent.getBytesTransfered();
/* 742 */               int p = (int) ((this.currentSize * 100) / this.size);
/* 743 */               if (Math.abs(this.lastProgress - p) > 2) {
/* 744 */                   this.lastProgress = p;
/* 745 */                   updateUploadNotification(this.thumbnail);
                        }
                    }

                    @Override // co.vine.android.service.UploadProgressListener
/* 750 */           public void updateTranscodeProgress(int p) {
/* 751 */               if (Math.abs(this.lastProgress - p) > 2) {
/* 752 */                   this.lastProgress = p;
/* 753 */                   updateTranscodeNotification(this.thumbnail);
                        }
                    }

                    @Override // co.vine.android.service.UploadProgressListener
/* 758 */           public void showPostNotification(String thumbnail) {
/* 759 */               updateNotification(thumbnail, C0257R.string.posting, C0257R.string.almost_done, true);
                    }

/* 762 */           private void updateUploadNotification(String thumbnail) {
/* 763 */               updateNotification(thumbnail, C0257R.string.uploading, C0257R.string.uploading_text, true);
                    }

/* 766 */           private void updateTranscodeNotification(String thumbnail) {
/* 767 */               updateNotification(thumbnail, C0257R.string.preparing, C0257R.string.preparing_text, true);
                    }

/* 771 */           private void updateNotification(String thumbnail, int title, int text, boolean showProgress) {
/* 772 */               SLog.m448d("Update notification: {}, {}.", Boolean.valueOf(showProgress), Integer.valueOf(this.lastProgress));
/* 773 */               NotificationCompat.Builder nb = new NotificationCompat.Builder(VineUploadService.this);
/* 774 */               nb.setLargeIcon(VineUploadService.access$600(VineUploadService.this, thumbnail));
/* 775 */               nb.setSmallIcon(2130838053);
/* 776 */               nb.setContentTitle(VineUploadService.this.getText(title));
/* 777 */               nb.setContentText(VineUploadService.this.getText(text));
/* 778 */               nb.setWhen(0L);
/* 779 */               nb.setOngoing(true);
/* 780 */               if (showProgress && this.lastProgress > 0) {
/* 782 */                   nb.setProgress(100, this.lastProgress, false);
                        } else {
/* 784 */                   nb.setProgress(0, 0, true);
                        }
/* 789 */               VineUploadService.access$700(VineUploadService.this, nb, 1);
                    }
                }

/* 797 */       public class IPCProgressListener extends UploadProgressListener {
                    public static final double PROGRESS_RATIO_TRANSCODE = 0.65d;
                    public static final double PROGRESS_RATIO_UPLOAD = 0.33d;

/* 798 */           public IPCProgressListener(String path) {
/* 800 */               super(path);
                    }

                    @Override // co.vine.android.service.UploadProgressListener
/* 804 */           public void onTranscodeStarted() {
/* 805 */               if (VineUploadService.access$100() != null) {
/* 806 */                   sendMessageToActivity(Message.obtain((Handler) null, 4));
                        }
                    }

                    @Override // co.vine.android.service.UploadProgressListener
/* 811 */           public void onPostFinished(boolean success) {
/* 812 */               Bundle data = new Bundle();
/* 813 */               if (VineUploadService.access$100() != null) {
/* 814 */                   data.putBoolean(VineUploadService.EXTRA_SUCCESS, success);
/* 815 */                   sendMessageToActivity(Message.obtain(null, 7, data));
/* 816 */                   if (!success) {
/* 817 */                       data.putDouble(VineUploadService.EXTRA_UPLOAD_PROGRESS, 0.0d);
/* 818 */                       sendMessageToActivity(Message.obtain(null, 6, data));
                            }
                        }
                    }

                    @Override // co.vine.android.service.UploadProgressListener
/* 824 */           public void updateTranscodeProgress(int p) {
/* 825 */               if (VineUploadService.access$100() != null && Math.abs(this.lastProgress - p) > 2) {
/* 826 */                   this.lastProgress = p;
/* 827 */                   Bundle data = new Bundle();
/* 828 */                   data.putDouble(VineUploadService.EXTRA_TRANSCODE_PROGRESS, p);
/* 829 */                   sendMessageToActivity(Message.obtain(null, 5, data));
                        }
                    }

                    @Override // co.vine.android.service.UploadProgressListener, co.vine.android.network.FileNetworkEntity.ProgressListener
/* 834 */           public void progressChanged(TransferProgressEvent progressEvent) {
/* 835 */               this.currentSize += progressEvent.getBytesTransfered();
/* 836 */               int p = (int) ((this.currentSize * 100) / this.size);
/* 837 */               if (VineUploadService.access$100() != null && Math.abs(this.lastProgress - p) > 2) {
/* 838 */                   this.lastProgress = p;
/* 839 */                   Bundle data = new Bundle();
/* 840 */                   data.putDouble(VineUploadService.EXTRA_UPLOAD_PROGRESS, p);
/* 841 */                   sendMessageToActivity(Message.obtain(null, 6, data));
                        }
                    }

/* 846 */           private void sendMessageToActivity(Message msg) {
                        try {
/* 847 */                   VineUploadService.access$100().send(msg);
                        } catch (RemoteException e) {
                        }
                    }
                }

/* 852 */       public class ServiceAsyncTask extends AsyncTask<Void, Void, Boolean> {
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



/* 867 */           public ServiceAsyncTask(String path, String action, Intent intent, UploadProgressListener listener) {
/* 869 */               this.path = path;
/* 870 */               this.action = action;
/* 871 */               this.conversationRowId = intent.getLongExtra("conversation_row_id", -1L);
/* 872 */               this.mListener = listener;
/* 873 */               this.thumbnail = RecordConfigUtils.getThumbnailPath(path);
/* 874 */               this.mIsRetry = intent.getBooleanExtra(PostActivity.AG_IS_RETRY, false);
/* 875 */               this.mIsPrivate = intent.getBooleanExtra("is_private", false);
                    }

                    /* JADX WARN: Code duplicated, block: B:50:0x012d A[Catch: all -> 0x0091, Exception -> 0x01ea, TryCatch #1 {Exception -> 0x01ea, blocks: (B:4:0x0003, B:6:0x0009, B:7:0x0019, B:9:0x001f, B:14:0x002f, B:16:0x0037, B:17:0x0041, B:19:0x004d, B:21:0x0055, B:23:0x005d, B:29:0x0094, B:31:0x00b5, B:33:0x00bb, B:34:0x00c6, B:37:0x00ce, B:39:0x00da, B:41:0x00e9, B:44:0x00ff, B:46:0x0105, B:49:0x0112, B:50:0x012d, B:52:0x013f, B:53:0x0158, B:55:0x017c, B:59:0x0186, B:61:0x01b4, B:62:0x01cb, B:65:0x01d3, B:11:0x0028), top: B:73:0x0003, outer: #0 }] */
                    /* JADX WARN: Code duplicated, block: B:52:0x013f A[Catch: all -> 0x0091, Exception -> 0x01ea, TryCatch #1 {Exception -> 0x01ea, blocks: (B:4:0x0003, B:6:0x0009, B:7:0x0019, B:9:0x001f, B:14:0x002f, B:16:0x0037, B:17:0x0041, B:19:0x004d, B:21:0x0055, B:23:0x005d, B:29:0x0094, B:31:0x00b5, B:33:0x00bb, B:34:0x00c6, B:37:0x00ce, B:39:0x00da, B:41:0x00e9, B:44:0x00ff, B:46:0x0105, B:49:0x0112, B:50:0x012d, B:52:0x013f, B:53:0x0158, B:55:0x017c, B:59:0x0186, B:61:0x01b4, B:62:0x01cb, B:65:0x01d3, B:11:0x0028), top: B:73:0x0003, outer: #0 }] */
                    /* JADX WARN: Code duplicated, block: B:55:0x017c A[Catch: all -> 0x0091, Exception -> 0x01ea, TRY_LEAVE, TryCatch #1 {Exception -> 0x01ea, blocks: (B:4:0x0003, B:6:0x0009, B:7:0x0019, B:9:0x001f, B:14:0x002f, B:16:0x0037, B:17:0x0041, B:19:0x004d, B:21:0x0055, B:23:0x005d, B:29:0x0094, B:31:0x00b5, B:33:0x00bb, B:34:0x00c6, B:37:0x00ce, B:39:0x00da, B:41:0x00e9, B:44:0x00ff, B:46:0x0105, B:49:0x0112, B:50:0x012d, B:52:0x013f, B:53:0x0158, B:55:0x017c, B:59:0x0186, B:61:0x01b4, B:62:0x01cb, B:65:0x01d3, B:11:0x0028), top: B:73:0x0003, outer: #0 }] */
                    /* JADX WARN: Code duplicated, block: B:58:0x0184 A[DONT_INVERT] */
                    /* JADX WARN: Code duplicated, block: B:59:0x0186 A[Catch: all -> 0x0091, Exception -> 0x01ea, TRY_ENTER, TryCatch #1 {Exception -> 0x01ea, blocks: (B:4:0x0003, B:6:0x0009, B:7:0x0019, B:9:0x001f, B:14:0x002f, B:16:0x0037, B:17:0x0041, B:19:0x004d, B:21:0x0055, B:23:0x005d, B:29:0x0094, B:31:0x00b5, B:33:0x00bb, B:34:0x00c6, B:37:0x00ce, B:39:0x00da, B:41:0x00e9, B:44:0x00ff, B:46:0x0105, B:49:0x0112, B:50:0x012d, B:52:0x013f, B:53:0x0158, B:55:0x017c, B:59:0x0186, B:61:0x01b4, B:62:0x01cb, B:65:0x01d3, B:11:0x0028), top: B:73:0x0003, outer: #0 }] */
                    /* JADX WARN: Code duplicated, block: B:61:0x01b4 A[Catch: all -> 0x0091, Exception -> 0x01ea, TryCatch #1 {Exception -> 0x01ea, blocks: (B:4:0x0003, B:6:0x0009, B:7:0x0019, B:9:0x001f, B:14:0x002f, B:16:0x0037, B:17:0x0041, B:19:0x004d, B:21:0x0055, B:23:0x005d, B:29:0x0094, B:31:0x00b5, B:33:0x00bb, B:34:0x00c6, B:37:0x00ce, B:39:0x00da, B:41:0x00e9, B:44:0x00ff, B:46:0x0105, B:49:0x0112, B:50:0x012d, B:52:0x013f, B:53:0x0158, B:55:0x017c, B:59:0x0186, B:61:0x01b4, B:62:0x01cb, B:65:0x01d3, B:11:0x0028), top: B:73:0x0003, outer: #0 }] */
                    /* JADX WARN: Code duplicated, block: B:65:0x01d3 A[Catch: all -> 0x0091, Exception -> 0x01ea, TRY_ENTER, TRY_LEAVE, TryCatch #1 {Exception -> 0x01ea, blocks: (B:4:0x0003, B:6:0x0009, B:7:0x0019, B:9:0x001f, B:14:0x002f, B:16:0x0037, B:17:0x0041, B:19:0x004d, B:21:0x0055, B:23:0x005d, B:29:0x0094, B:31:0x00b5, B:33:0x00bb, B:34:0x00c6, B:37:0x00ce, B:39:0x00da, B:41:0x00e9, B:44:0x00ff, B:46:0x0105, B:49:0x0112, B:50:0x012d, B:52:0x013f, B:53:0x0158, B:55:0x017c, B:59:0x0186, B:61:0x01b4, B:62:0x01cb, B:65:0x01d3, B:11:0x0028), top: B:73:0x0003, outer: #0 }] */
                    /* JADX INFO: renamed from: doInBackground, reason: avoid collision after fix types in other method */
/* 879 */           protected Boolean doInBackground(Void... params) {
                        boolean z = false;
                        String[] uris;
                        synchronized (ServiceAsyncTask.class) {
                            try {
                                try {
/* 882 */                           if (this.mUpload == null) {
/* 883 */                               this.mUpload = UploadManager.getUpload(getContext(), this.path);
                                    }
/* 885 */                           if (this.mUpload == null || this.mUpload.status == -1) {
/* 886 */                               z = false;
                                    } else {
/* 889 */                               if (this.mUpload.path == null) {
/* 890 */                                   this.mUpload.path = this.path;
                                        }
/* 893 */                               if (VineUploadService.ACTION_POST.equals(this.action)) {
/* 894 */                                   if (this.mUpload.path == null && !this.mUpload.isPrivate) {
/* 895 */                                       CrashUtil.logException(new VineLoggingException("Invalid upload, but we know " + this.thumbnail + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.path));
/* 896 */                                       z = false;
                                            } else {
/* 898 */                                       Bundle b = new Bundle();
/* 899 */                                       b.putParcelable("upload", this.mUpload);
/* 900 */                                       VineUploadService.access$800(VineUploadService.this).queueAndExecute(98, b);
/* 901 */                                       if (!this.mIsRetry && this.mListener != null) {
/* 902 */                                           this.mListener.showPostNotification(this.thumbnail);
                                                }
/* 904 */                                       z = true;
                                            }
                                        } else if (VineUploadService.ACTION_UPLOAD.equals(this.action)) {
/* 906 */                                   File original = new File(this.path);
/* 907 */                                   if (original.exists()) {
/* 908 */                                       String hash = HashAsyncTask.getMD5Checksum(this.path);
/* 909 */                                       String expected = this.mUpload.hash;
/* 910 */                                       if (!hash.equals(expected)) {
/* 911 */                                           if (expected != null && expected.length() != 0) {
/* 912 */                                               SLog.m457e("Invalid hash: actual {}, expected {}.", hash, expected);
/* 913 */                                               z = false;
                                                    } else {
/* 915 */                                               CrashUtil.logException(new VineLoggingException("Expected hash was invalid: " + expected));
/* 921 */                                               RecordSessionVersion version = UploadManager.getVersionFromPath(this.path);
/* 923 */                                               String finalPath = original.getAbsolutePath();
/* 924 */                                               if (this.mListener != null) {
/* 925 */                                                   this.mListener.size = original.length() + new File(this.thumbnail).length();
                                                        }
/* 928 */                                               uris = VineUploadService.access$900(VineUploadService.this).upload(this, this.mListener, finalPath, this.thumbnail, this.path, this.mIsPrivate, version);
/* 932 */                                               if (isCancelled()) {
                                                            z = false;
                                                        } else if (uris != null) {
/* 935 */                                                   UploadManager.setUploadTime(getContext(), this.mUpload);
/* 936 */                                                   UploadManager.setUris(getContext(), this.mUpload, uris);
/* 937 */                                                   UploadManager.setStatus(getContext(), this.mUpload, 1);
/* 938 */                                                   if (!TextUtils.isEmpty(this.mUpload.postInfo)) {
/* 939 */                                                       VineUploadService.this.startService(VineUploadService.getPostIntent(getContext(), this.mUpload, this.mIsRetry));
                                                            }
/* 941 */                                                   z = true;
                                                        } else {
/* 943 */                                                   SLog.m447d("Current status: {}", Integer.valueOf(this.mUpload.status));
/* 944 */                                                   z = false;
                                                        }
                                                    }
                                                } else {
/* 921 */                                           RecordSessionVersion version2 = UploadManager.getVersionFromPath(this.path);
/* 923 */                                           String finalPath2 = original.getAbsolutePath();
/* 924 */                                           if (this.mListener != null) {
/* 925 */                                               this.mListener.size = original.length() + new File(this.thumbnail).length();
                                                    }
/* 928 */                                           uris = VineUploadService.access$900(VineUploadService.this).upload(this, this.mListener, finalPath2, this.thumbnail, this.path, this.mIsPrivate, version2);
/* 932 */                                           if (isCancelled()) {
                                                        z = false;
                                                    } else if (uris != null) {
/* 935 */                                               UploadManager.setUploadTime(getContext(), this.mUpload);
/* 936 */                                               UploadManager.setUris(getContext(), this.mUpload, uris);
/* 937 */                                               UploadManager.setStatus(getContext(), this.mUpload, 1);
/* 938 */                                               if (!TextUtils.isEmpty(this.mUpload.postInfo)) {
/* 939 */                                                   VineUploadService.this.startService(VineUploadService.getPostIntent(getContext(), this.mUpload, this.mIsRetry));
                                                        }
/* 941 */                                               z = true;
                                                    } else {
/* 943 */                                               SLog.m447d("Current status: {}", Integer.valueOf(this.mUpload.status));
/* 944 */                                               z = false;
                                                    }
                                                }
                                            } else {
/* 921 */                                       RecordSessionVersion version3 = UploadManager.getVersionFromPath(this.path);
/* 923 */                                       String finalPath3 = original.getAbsolutePath();
/* 924 */                                       if (this.mListener != null) {
/* 925 */                                           this.mListener.size = original.length() + new File(this.thumbnail).length();
                                                }
/* 928 */                                       uris = VineUploadService.access$900(VineUploadService.this).upload(this, this.mListener, finalPath3, this.thumbnail, this.path, this.mIsPrivate, version3);
/* 932 */                                       if (isCancelled()) {
                                                    z = false;
                                                } else if (uris != null) {
/* 935 */                                           UploadManager.setUploadTime(getContext(), this.mUpload);
/* 936 */                                           UploadManager.setUris(getContext(), this.mUpload, uris);
/* 937 */                                           UploadManager.setStatus(getContext(), this.mUpload, 1);
/* 938 */                                           if (!TextUtils.isEmpty(this.mUpload.postInfo)) {
/* 939 */                                               VineUploadService.this.startService(VineUploadService.getPostIntent(getContext(), this.mUpload, this.mIsRetry));
                                                    }
/* 941 */                                           z = true;
                                                } else {
/* 943 */                                           SLog.m447d("Current status: {}", Integer.valueOf(this.mUpload.status));
/* 944 */                                           z = false;
                                                }
                                            }
                                        } else {
/* 950 */                                   z = false;
                                        }
                                    }
                                } catch (Exception e) {
/* 948 */                           SLog.m459e("Failed to upload.", (Throwable) e);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
/* 950 */               return z;
                    }

                    /* JADX INFO: renamed from: onPostExecute, reason: avoid collision after fix types in other method */
/* 955 */           protected void onPostExecute(Boolean success) {
/* 956 */               boolean taskSuccessful = success.booleanValue();
/* 957 */               if (!this.mIsDiscarded) {
/* 958 */                   if (taskSuccessful) {
/* 959 */                       this.mExecutionStatus = 2;
/* 960 */                       if (VineUploadService.ACTION_UPLOAD.equals(this.action) && TextUtils.isEmpty(this.mUpload.postInfo)) {
/* 961 */                           this.mExecutionStatus = 3;
                                }
                            } else {
/* 964 */                       this.mExecutionStatus = 1;
/* 965 */                       VineUploadService.this.startService(VineUploadService.getNotifyFailedIntent(getContext()));
/* 966 */                       if (this.mUpload != null) {
/* 967 */                           UploadManager.setStatus(getContext(), this.mUpload, 2, null);
/* 968 */                           if (VineUploadService.ACTION_UPLOAD.equals(this.action) && this.mUpload.isPrivate) {
/* 969 */                               VineUploadService.access$1000(VineUploadService.this, this.mUpload.path);
                                    }
                                }
                            }
/* 973 */                   onComplete();
                        }
                    }

                    @Override // android.os.AsyncTask
/* 978 */           protected void onCancelled() {
/* 979 */               SLog.m448d("Task cancelled. {}, {}.", this.path, this.action);
/* 980 */               if (!this.mIsDiscarded) {
/* 981 */                   VineUploadService.access$1100(VineUploadService.this, this.path);
                        }
/* 983 */               onComplete();
                    }

/* 986 */           private void onComplete() {
                        synchronized (VineUploadService.class) {
                            try {
/* 988 */                       VineUploadService.access$400(VineUploadService.this, 1);
/* 989 */                       VineUploadService.access$202(VineUploadService.this, null);
/* 990 */                       Intent intent = new Intent(VineUploadService.ACTION_UPLOAD_RESULT);
/* 991 */                       intent.putExtra(VineUploadService.AG_UPLOAD_RESULT, this.mExecutionStatus);
/* 992 */                       intent.putExtra("path", this.path);
/* 993 */                       intent.putExtra(VineUploadService.AG_THUMBNAIL, this.thumbnail);
/* 994 */                       VineUploadService.this.sendBroadcast(intent, CrossConstants.BROADCAST_PERMISSION);
/* 995 */                       VineUploadService.access$1200(VineUploadService.this);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

/* 999 */           public Context getContext() {
/* 1000 */              return VineUploadService.this;
                    }

/* 1003 */          public void updateProgress(int p) {
/* 1004 */              if (this.mListener != null) {
/* 1005 */                  this.mListener.updateTranscodeProgress(p);
                        }
                    }

/* 1010 */          public boolean equals(Object o) {
/* 1011 */              if (this == o) {
                            return true;
                        }
/* 1012 */              if (!(o instanceof ServiceAsyncTask)) {
                            return false;
                        }
/* 1014 */              ServiceAsyncTask that = (ServiceAsyncTask) o;
/* 1016 */              if (this.path == null ? that.path != null : !this.path.equals(that.path)) {
                            return false;
                        }
/* 1018 */              if (this.action != null) {
                            if (this.action.equals(that.action)) {
                                return true;
                            }
                        } else if (that.action == null) {
                            return true;
                        }
                        return false;
                    }

/* 1025 */          public int hashCode() {
/* 1026 */              int result = this.path != null ? this.path.hashCode() : 0;
/* 1027 */              return (result * 31) + (this.action != null ? this.action.hashCode() : 0);
                    }

/* 1031 */          public void setIsDiscarded(boolean isDiscarded) {
/* 1032 */              this.mIsDiscarded = isDiscarded;
                    }
                }

                @Override // android.app.Service
/* 1037 */      public IBinder onBind(Intent intent) {
/* 1038 */          return this.mMessenger.getBinder();
                }

                @Override // android.app.Service
/* 1042 */      public boolean onUnbind(Intent intent) {
/* 1043 */          sUploadResultReceiver = null;
/* 1044 */          stopIfNoTasksLeft();
/* 1045 */          return false;
                }

                @Override // co.vine.android.service.VineServiceConnection.ServiceResponseHandler
/* 1048 */      public void handleServiceResponse(int what, int responseCode, String reasonPhrase, Bundle bundle) {
                    switch (what) {
                        case 93:
/* 1083 */                  VineUpload upload = (VineUpload) bundle.getParcelable("upload");
/* 1084 */                  long messageRowId = bundle.getLong(VineService.EXTRA_PREMERGED_MESSAGE_ID);
/* 1085 */                  upload.mergedMessageId = messageRowId;
/* 1086 */                  PostInfo info = upload.getPostInfo();
/* 1087 */                  SLog.m449d("Pre merge message complete. Path {} Row {} {}", upload.path, Long.valueOf(messageRowId), info.toString());
/* 1088 */                  if (upload.path != null) {
/* 1089 */                      UploadManager.setPostInfo(this, upload, info);
/* 1090 */                      UploadManager.setUploadMessageRowId(this, upload.path, messageRowId);
                            }
/* 1092 */                  startService(getPostIntent(this, upload, false));
                            break;
                        case 98:
/* 1051 */                  VineUpload upload2 = (VineUpload) bundle.getParcelable("upload");
/* 1052 */                  if (responseCode == 200) {
/* 1053 */                      SLog.m446d("Post successful.");
/* 1054 */                      cancelFailedNofitication();
/* 1055 */                      if (!upload2.isPrivate) {
/* 1056 */                          SharedPreferences prefs = Util.getDefaultSharedPrefs(this);
/* 1057 */                          SharedPreferences.Editor e = prefs.edit();
/* 1058 */                          e.putInt(Settings.PREF_POST_COUNT, prefs.getInt(Settings.PREF_POST_COUNT, 0) + 1);
/* 1060 */                          e.apply();
                                } else {
/* 1062 */                          String videoUrl = bundle.getString(VineService.EXTRA_VIDEO_URL);
/* 1063 */                          String thumbUrl = bundle.getString("thumbnail_url");
/* 1064 */                          if (videoUrl != null && thumbUrl != null) {
/* 1065 */                              UploadManager.prepopulateCache(this, upload2.path, videoUrl, thumbUrl);
                                    }
                                }
/* 1069 */                      boolean saveUpload = bundle.getBoolean(VineService.EXTRA_KEEP_UPLOAD, false);
/* 1070 */                      if (!TextUtils.isEmpty(upload2.path) && !saveUpload) {
/* 1071 */                          discardUpload(upload2.path);
                                    break;
                                }
                            } else {
/* 1074 */                      startService(getNotifyFailedIntent(this));
/* 1075 */                      if (upload2 != null) {
/* 1076 */                          UploadManager.setStatus(this, upload2, 2, upload2.captchaUrl);
                                }
                                break;
                            }
                            break;
                    }
                }
            }
