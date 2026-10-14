            package co.vine.android.plugin;

            import android.app.Activity;
            import android.content.Intent;
            import android.view.KeyEvent;
            import com.edisonwang.android.slog.SLog;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    public class DebugReceiverPlugin extends BasePlugin {
                private static int[] mDebugMenuSecrets = {24, 25, 24, 25};
                private int mDebugMenu;

/* 22 */        public DebugReceiverPlugin() {
/* 23 */            super("debugReceiver");
/* 14 */            this.mDebugMenu = 0;
                }

                @Override // co.vine.android.plugin.BasePlugin, co.vine.android.plugin.Plugin
/* 27 */        public boolean onKeyUp(int keyCode, KeyEvent event) {
                    Activity activity;
/* 28 */            if (SLog.sLogsOn && this.mDebugMenu < mDebugMenuSecrets.length && keyCode == mDebugMenuSecrets[this.mDebugMenu]) {
/* 30 */                this.mDebugMenu++;
/* 31 */                if (this.mDebugMenu == mDebugMenuSecrets.length && (activity = getActivity()) != null) {
/* 34 */                    activity.sendBroadcast(new Intent("co.vine.android.debug_receiver"));
/* 39 */                    return false;
                        }
/* 39 */                return false;
                    }
/* 39 */            return false;
                }
            }
