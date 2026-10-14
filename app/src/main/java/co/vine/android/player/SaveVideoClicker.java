            package co.vine.android.player;

            import android.widget.Toast;
            import co.vine.android.cache.CacheKey;
            import co.vine.android.util.MediaUtil;
            import com.edisonwang.android.slog.SLog;
            import java.io.File;
            import java.io.IOException;
            import org.apache.commons.io.FileUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    public class SaveVideoClicker {
                private static File LONG_PRESS_SAVE_DIR = null;
                private int mCount = 0;
                private Toast mToast;

/* 23 */        public static void setLongPressSaveDir(File dir) {
/* 24 */            LONG_PRESS_SAVE_DIR = dir;
                }

/* 29 */        public void onClick(SdkVideoView view) {
/* 30 */            if (LONG_PRESS_SAVE_DIR != null) {
/* 31 */                this.mCount++;
/* 32 */                if (this.mCount >= 20) {
/* 33 */                    String path = view.getPath();
                            try {
/* 35 */                        String name = new File(path).getName();
/* 36 */                        if (!name.endsWith(".mp4")) {
/* 37 */                            name = name + ".mp4";
                                }
/* 39 */                        File dest = new File(LONG_PRESS_SAVE_DIR, name);
/* 40 */                        FileUtils.copyFile(new File(path), dest);
/* 41 */                        if (this.mToast == null) {
/* 42 */                            this.mToast = Toast.makeText(view.getContext().getApplicationContext(), "", 0);
                                }
/* 44 */                        this.mToast.setText("File Saved: " + dest);
/* 45 */                        this.mToast.show();
/* 46 */                        MediaUtil.scanFile(view.getContext(), dest, null);
                            } catch (Throwable e) {
/* 48 */                        SLog.m459e("Failed to save video", (Throwable) e);
                            }
/* 50 */                    this.mCount = 0;
                        }
/* 52 */                if (this.mCount > 3) {
/* 53 */                    CacheKey cacheKey = view.getCacheKey();
/* 54 */                    if (this.mToast == null) {
/* 55 */                        this.mToast = Toast.makeText(view.getContext().getApplicationContext(), "", 0);
                            }
/* 57 */                    this.mToast.setText(cacheKey.toString());
/* 58 */                    this.mToast.show();
                        }
                    }
                }
            }
