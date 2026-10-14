            package co.vine.android.recorder;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public enum DeviceIssue {
                CANNOT_CONNECT_TO_CAMERA("Failed to connect to camera"),
                CAMERA_NULL_FRAMES("Camera does not pass back non-null frames"),
                RS_NOT_SUPPORTED("Renderscript is not properly supported"),
                LIBS_NOT_COMPATIBLE("FFmpeg libs are not supported"),
                NOT_ENOUGH_SPACE("Not enough space is left on this device"),
                STORAGE_NOT_READY("Storage is not ready");

                public final String description;

/* 15 */        DeviceIssue(String s) {
/* 17 */            this.description = s;
                }
            }
