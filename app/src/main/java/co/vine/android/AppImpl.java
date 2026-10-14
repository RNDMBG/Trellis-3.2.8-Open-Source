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
            
            
    public class AppImpl implements AppInterface {
                public static final int REQUEST_RECORDING_PERMISSIONS = 1601;
                private static AppImpl sInstance;

        private AppImpl() {
                }

        public static AppImpl getInstance() {
            if (sInstance == null) {
                sInstance = new AppImpl();
                    }
            return sInstance;
                }

        public void updateClientProfile(Context context, AppController appController, boolean record) {
            if (RecordConfigUtils.RecordConfig.needsUpdate(context) && !record) {
                appController.updateClientProfile();
                    }
                }

                @Override 
        public Intent getRecordingIntent(Activity activity, int flags, String callingActivity) {
            return AbstractRecordingActivity.getIntentForGeneric(activity, flags, callingActivity);
                }

                @Override 
        public Intent getClearNotificationsIntent(Context c) {
            return VineUploadService.getClearNotificationsIntent(c);
                }

                @Override 
        public Intent getDiscardAllIntent(Context c) {
            return VineUploadService.getDiscardAllIntent(c);
                }

                @Override 
        public Intent getNotifyFailedIntent(Context c) {
            return VineUploadService.getNotifyFailedIntent(c);
                }

                @Override 
        public void startCapture(Activity activity) {
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

                @Override 
        public void appendDebugInfo(Context c, StringBuilder debugInfo, boolean hideSensitiveData) {
            RecordConfigUtils.RecordConfig config = RecordConfigUtils.getGenericConfig(c);
            debugInfo.append("\n\nDefault Recording Configuration: ");
            debugInfo.append("\nRecording enabled: " + config.recordingEnabled);
            debugInfo.append("\nZoom enabled: " + config.zoomEnabled);
            debugInfo.append("\nCamera Switch enabled: " + config.cameraSwitchEnabled);
            debugInfo.append("\nFlash Switch enabled: " + config.flashSwitchEnabled);
            debugInfo.append("\nBuffer Pre-allocation enabled: " + config.preAllocateBuffer);
            debugInfo.append("\nBuffer count: " + config.bufferCount);
            debugInfo.append("\nPreview width: " + config.previewWidth);
            debugInfo.append("\nTarget Frame Rate: " + config.targetFrameRate);
            if (!hideSensitiveData) {
                debugInfo.append("\nProcess path: " + config.processDir.getPath());
                    }
            debugInfo.append("\nTarget Size: " + config.targetSize);
            RecordConfigUtils.RecordConfig config2 = RecordConfigUtils.getGenericConfig(c);
            debugInfo.append("\n\nDefault Message Recording Configuration: ");
            debugInfo.append("\nBuffer count: " + config2.bufferCount);
            debugInfo.append("\nPreview width: " + config2.previewWidth);
            debugInfo.append("\nPreview height: " + config2.previewHeight);
            debugInfo.append("\nTarget Frame Rate: " + config2.targetFrameRate);
            debugInfo.append("\nTarget Size: " + config2.targetSize);
                }

                @Override 
       public boolean doAddWidget(Context c, AccountManager am, Account acc) {
                    return RecordConfigUtils.loadWasEverSuccessful(c) && VineAccountHelper.needsAddwidget(am, acc);
                }

                @Override 
       public void startUploadsListActivity(Activity a) {
           UploadsListActivity.start(a);
                }

                @Override 
       public void startCameraService(Activity parent) {
           parent.startService(new Intent(parent, (Class<?>) ResourceService.class));
                }

                @Override 
       public void setupWidget(Activity a) {
           CameraWidgetConfigureActivity.setupShortcut(a);
                }

                @Override 
       public void updateRecordConfigs(Context applicationContext, JSONObject object) {
           RecordConfigUtils.RecordConfig.update(applicationContext, object);
                }

                @Override 
       public void startConversationActivity(Activity parent, long conversationId, String username, long recipientId, boolean isRecipientExternal, boolean amFollowing) {
           Intent i = ConversationActivity.getIntent(parent, conversationId, username, recipientId, isRecipientExternal, amFollowing, false);
           parent.startActivity(i);
                }

                @Override 
       public void clearUploadCaptchas(Context applicationContext) {
           UploadManager.clearUploadCaptchas(applicationContext);
                }
            }
