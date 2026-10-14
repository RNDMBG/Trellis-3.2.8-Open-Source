            package co.vine.android;

            import android.content.Context;
            import co.vine.android.recorder.DeviceIssue;
            import co.vine.android.recorder.RegularVineRecorder;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 16 */    public class DeviceIssueStringGetter implements RegularVineRecorder.DeviceIssueStringGetter {
                private final String mCannotConnectToCamera;
                private final String mGenericError;
                private final String mNotEnoughSpace;
                private final String mStorageNotReady;

/* 17 */        public DeviceIssueStringGetter(Context context) {
/* 19 */            this.mCannotConnectToCamera = context.getString(C0257R.string.camera_failed_to_open);
/* 20 */            this.mNotEnoughSpace = context.getString(C0257R.string.failed_to_start_recording_space);
/* 21 */            this.mGenericError = context.getString(C0257R.string.unsupported_feature_recording);
/* 22 */            this.mStorageNotReady = context.getString(C0257R.string.storage_not_ready);
                }

/* 25 */        private String getGenericError(DeviceIssue issue) {
/* 26 */            return this.mGenericError + " (" + issue.description + ")";
                }

                @Override // co.vine.android.recorder.RegularVineRecorder.DeviceIssueStringGetter
/* 30 */        public String getString(DeviceIssue issue) {
                    switch (issue) {
                        case CANNOT_CONNECT_TO_CAMERA:
/* 33 */                    return this.mCannotConnectToCamera;
                        case CAMERA_NULL_FRAMES:
                        case RS_NOT_SUPPORTED:
                        case LIBS_NOT_COMPATIBLE:
/* 37 */                    return getGenericError(issue);
                        case STORAGE_NOT_READY:
/* 39 */                    return this.mStorageNotReady;
                        case NOT_ENOUGH_SPACE:
/* 41 */                    return this.mNotEnoughSpace;
                        default:
/* 43 */                    return this.mGenericError;
                    }
                }
            }
