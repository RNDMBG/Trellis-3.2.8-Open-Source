            package co.vine.android;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.Manifest;
            import co.vine.android.client.AppController;
            import co.vine.android.client.VineAccountHelper;
            import co.vine.android.recorder.RecordConfigUtils;
            import co.vine.android.service.ResourceService;
            import co.vine.android.service.VineUploadService;
            import co.vine.android.util.UploadManager;
            import co.vine.android.util.Util;
            import org.json.JSONObject;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 26 */    public class AppImpl implements AppInterface {
                public static final int REQUEST_RECORDING_PERMISSIONS = 1601;
                private static AppImpl sInstance;

/* 27 */        private AppImpl() {
                }

/* 32 */        public static AppImpl getInstance() {
/* 33 */            if (sInstance == null) {
/* 34 */                sInstance = new AppImpl();
                    }
/* 36 */            return sInstance;
                }

/* 39 */        public void updateClientProfile(Context context, AppController appController, boolean record) {
/* 40 */            if (RecordConfigUtils.RecordConfig.needsUpdate(context) && !record) {
/* 41 */                appController.updateClientProfile();
                    }
                }

                @Override // co.vine.android.AppInterface
/* 46 */        public Intent getRecordingIntent(Activity activity, int flags, String callingActivity) {
/* 47 */            return AbstractRecordingActivity.getIntentForGeneric(activity, flags, callingActivity);
                }

                @Override // co.vine.android.AppInterface
/* 51 */        public Intent getClearNotificationsIntent(Context c) {
/* 52 */            return VineUploadService.getClearNotificationsIntent(c);
                }

                @Override // co.vine.android.AppInterface
/* 56 */        public Intent getDiscardAllIntent(Context c) {
/* 57 */            return VineUploadService.getDiscardAllIntent(c);
                }

                @Override // co.vine.android.AppInterface
/* 61 */        public Intent getNotifyFailedIntent(Context c) {
/* 62 */            return VineUploadService.getNotifyFailedIntent(c);
                }

                @Override // co.vine.android.AppInterface
/* 66 */        public void startCapture(Activity activity) {
                    if (activity == null || activity.isFinishing()) {
                        return;
                    }
                    final Activity captureActivity = activity;
                    new AlertDialog.Builder(captureActivity)
                            .setMessage("Sadly, recording within the app has been disabled due to 32 bit .so libraries being broken while being compiled in ARM64, you can still select media from your gallery and upload it.")
                            .setCancelable(false)
                            .setPositiveButton("I acknowledge", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(captureActivity, ImportTrimCropActivity.class);
                                    intent.putExtra(ImportTrimCropActivity.EXTRA_GALLERY_ONLY, true);
                                    captureActivity.startActivity(intent);
                                }
                            })
                            .show();
                }

                private boolean hasRecordingPermissions(Activity activity) {
                    return activity.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                            && activity.checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
                }

                @Override // co.vine.android.AppInterface
/* 74 */        public void appendDebugInfo(Context c, StringBuilder debugInfo, boolean hideSensitiveData) {
/* 75 */            RecordConfigUtils.RecordConfig config = RecordConfigUtils.getGenericConfig(c);
/* 77 */            debugInfo.append("\n\nDefault Recording Configuration: ");
/* 78 */            debugInfo.append("\nRecording enabled: " + config.recordingEnabled);
/* 79 */            debugInfo.append("\nZoom enabled: " + config.zoomEnabled);
/* 80 */            debugInfo.append("\nCamera Switch enabled: " + config.cameraSwitchEnabled);
/* 81 */            debugInfo.append("\nFlash Switch enabled: " + config.flashSwitchEnabled);
/* 82 */            debugInfo.append("\nBuffer Pre-allocation enabled: " + config.preAllocateBuffer);
/* 83 */            debugInfo.append("\nBuffer count: " + config.bufferCount);
/* 84 */            debugInfo.append("\nPreview width: " + config.previewWidth);
/* 85 */            debugInfo.append("\nTarget Frame Rate: " + config.targetFrameRate);
/* 86 */            if (!hideSensitiveData) {
/* 87 */                debugInfo.append("\nProcess path: " + config.processDir.getPath());
                    }
/* 89 */            debugInfo.append("\nTarget Size: " + config.targetSize);
/* 91 */            RecordConfigUtils.RecordConfig config2 = RecordConfigUtils.getGenericConfig(c);
/* 93 */            debugInfo.append("\n\nDefault Message Recording Configuration: ");
/* 94 */            debugInfo.append("\nBuffer count: " + config2.bufferCount);
/* 95 */            debugInfo.append("\nPreview width: " + config2.previewWidth);
/* 96 */            debugInfo.append("\nPreview height: " + config2.previewHeight);
/* 97 */            debugInfo.append("\nTarget Frame Rate: " + config2.targetFrameRate);
/* 98 */            debugInfo.append("\nTarget Size: " + config2.targetSize);
                }

                @Override // co.vine.android.AppInterface
/* 102 */       public boolean doAddWidget(Context c, AccountManager am, Account acc) {
                    return RecordConfigUtils.loadWasEverSuccessful(c) && VineAccountHelper.needsAddwidget(am, acc);
                }

                @Override // co.vine.android.AppInterface
/* 108 */       public void startUploadsListActivity(Activity a) {
/* 109 */           UploadsListActivity.start(a);
                }

                @Override // co.vine.android.AppInterface
/* 113 */       public void startCameraService(Activity parent) {
/* 114 */           parent.startService(new Intent(parent, (Class<?>) ResourceService.class));
                }

                @Override // co.vine.android.AppInterface
/* 118 */       public void setupWidget(Activity a) {
/* 119 */           CameraWidgetConfigureActivity.setupShortcut(a);
                }

                @Override // co.vine.android.AppInterface
/* 123 */       public void updateRecordConfigs(Context applicationContext, JSONObject object) {
/* 124 */           RecordConfigUtils.RecordConfig.update(applicationContext, object);
                }

                @Override // co.vine.android.AppInterface
/* 129 */       public void startConversationActivity(Activity parent, long conversationId, String username, long recipientId, boolean isRecipientExternal, boolean amFollowing) {
/* 130 */           Intent i = ConversationActivity.getIntent(parent, conversationId, username, recipientId, isRecipientExternal, amFollowing, false);
/* 131 */           parent.startActivity(i);
                }

                @Override // co.vine.android.AppInterface
/* 134 */       public void clearUploadCaptchas(Context applicationContext) {
/* 135 */           UploadManager.clearUploadCaptchas(applicationContext);
                }
            }
