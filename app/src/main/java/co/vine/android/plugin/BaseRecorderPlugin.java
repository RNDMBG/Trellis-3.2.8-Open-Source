            package co.vine.android.plugin;

            import android.app.Activity;
            import android.content.Intent;
            import android.graphics.Bitmap;
            import android.os.Handler;
            import android.support.annotation.Nullable;
            import android.support.v4.app.Fragment;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import co.vine.android.player.SdkVideoView;
            import co.vine.android.recorder.BasicVineRecorder;
            import co.vine.android.recorder.RecordSegment;
            import co.vine.android.recorder.camera.CameraSetting;
            import java.lang.ref.WeakReference;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 28 */    public abstract class BaseRecorderPlugin<T extends View, K extends BasicVineRecorder> extends BasePlugin implements RecorderPlugin<T, K> {
                protected Activity mActivity;
                private WeakReference<Fragment> mFragment;
                private WeakReference<T> mView;

                @Override // co.vine.android.plugin.RecorderPlugin
                public abstract T onLayout(ViewGroup viewGroup, LayoutInflater layoutInflater, Fragment fragment);

/* 29 */        public BaseRecorderPlugin(String name) {
/* 30 */            super(name);
                }

                @Override // co.vine.android.plugin.RecorderPlugin
/* 34 */        public void createLayout(ViewGroup parent, LayoutInflater inflater, Fragment fragment) {
/* 35 */            this.mFragment = new WeakReference<>(fragment);
/* 36 */            T viewOnLayout = onLayout(parent, inflater, fragment);
/* 37 */            if (viewOnLayout != null) {
/* 38 */                parent.addView(viewOnLayout);
/* 39 */                this.mView = new WeakReference<>(viewOnLayout);
                    } else {
/* 41 */                this.mView = null;
                    }
                }

                @Override // co.vine.android.plugin.RecorderPlugin
                @Nullable
/* 47 */        public Fragment getFragment() {
/* 48 */            if (this.mFragment != null) {
/* 49 */                return this.mFragment.get();
                    }
                    return null;
                }

                @Override // co.vine.android.plugin.RecorderPlugin
                @Nullable
/* 57 */        public T getView() {
/* 58 */            if (this.mView != null) {
/* 59 */                return this.mView.get();
                    }
                    return null;
                }

                @Override // co.vine.android.plugin.BasePlugin, co.vine.android.plugin.Plugin
                @Nullable
/* 67 */        public Handler getHandler() {
/* 68 */            if (this.mManager != null) {
                        return this.mManager.getHandler();
                    }
                    return null;
                }

                @Override // co.vine.android.plugin.RecorderPlugin
                @Nullable
/* 73 */        public K getRecorder() {
/* 75 */            RecorderPluginManager<K> manager = getManager();
/* 76 */            if (manager == null) {
/* 74 */                return null;
                    }
/* 77 */            return (K) manager.getRecorder();
                }

/* 82 */        public RecorderPluginManager<K> getManager() {
/* 83 */            return (RecorderPluginManager) this.mManager;
                }

                @Override // co.vine.android.plugin.BasePlugin, co.vine.android.plugin.Plugin
/* 91 */        public void onActivityCreated(Activity activity) {
/* 92 */            super.onActivityCreated(activity);
/* 93 */            this.mActivity = activity;
                }

                @Override // co.vine.android.plugin.BasePlugin, co.vine.android.plugin.Plugin
/* 97 */        public void onResume(Activity activity) {
/* 98 */            this.mActivity = activity;
                }

                @Override // co.vine.android.plugin.BasePlugin, co.vine.android.plugin.Plugin
/* 102 */       public void onPause() {
/* 103 */           this.mActivity = null;
                }

                @Override // co.vine.android.plugin.BasePlugin, co.vine.android.plugin.Plugin
/* 107 */       public boolean onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
/* 108 */           return false;
                }

                @Override // co.vine.android.plugin.RecorderPlugin
/* 112 */       public boolean onBackButtonPressed(boolean isEditing) {
/* 113 */           return false;
                }

                @Override // co.vine.android.plugin.RecorderPlugin
/* 118 */       public void onCameraReady(boolean frontFacing, boolean autoFocusSet) {
                }

                @Override // co.vine.android.plugin.RecorderPlugin
/* 122 */       public boolean onSetEditMode(boolean editing, boolean hasData) {
/* 123 */           return false;
                }

                @Override // co.vine.android.plugin.RecorderPlugin
/* 128 */       public void onStartEditMode() {
                }

                @Override // co.vine.android.plugin.RecorderPlugin
/* 133 */       public void onStartDrafts() {
                }

                @Override // co.vine.android.plugin.RecorderPlugin
/* 138 */       public void onAutoFocus(int x, int y) {
                }

                @Override // co.vine.android.plugin.RecorderPlugin
/* 143 */       public void onSessionSwapped() {
                }

                @Override // co.vine.android.plugin.RecorderPlugin
/* 148 */       public void onStartRelativeTime(K recorder) {
                }

                @Override // co.vine.android.plugin.RecorderPlugin
/* 153 */       public void onEndRelativeTime(K recorder) {
                }

                @Override // co.vine.android.plugin.RecorderPlugin
/* 158 */       public void onHideDrafts() {
                }

                @Override // co.vine.android.plugin.RecorderPlugin
/* 163 */       public void onShowDrafts() {
                }

                @Override // co.vine.android.plugin.RecorderPlugin
/* 168 */       public void onRecorderSet(BasicVineRecorder recorder) {
                }

                @Override // co.vine.android.plugin.RecorderPlugin
/* 173 */       public void onDraftUpgradeNumberChanged(int count) {
                }

                @Override // co.vine.android.plugin.RecorderPlugin
/* 178 */       public void setMessagingMode(boolean isMessaging) {
                }

                @Override // co.vine.android.plugin.RecorderPlugin
/* 183 */       public void onResumeCameraAsyncTaskPostExecute() {
                }

                @Override // co.vine.android.plugin.RecorderPlugin
/* 188 */       public void onResumeAsyncTaskPostExecute() {
                }

                @Override // co.vine.android.plugin.RecorderPlugin
/* 193 */       public void onZoomUpdated(int zoom, boolean stopped) {
                }

                @Override // co.vine.android.plugin.RecorderPlugin
/* 198 */       public void onOfferLastFrame(byte[] array, CameraSetting cameraSetting) {
                }

                @Override // co.vine.android.plugin.RecorderPlugin
/* 203 */       public void onSegmentDataChanged(ArrayList<RecordSegment> editedSegments) {
                }

                @Override // co.vine.android.plugin.RecorderPlugin
/* 208 */       public void onOfferLastFrame(Bitmap bitmap, CameraSetting cameraSetting) {
                }

                @Override // co.vine.android.plugin.RecorderPlugin
/* 213 */       public void onIndividualSegmentClicked(RecordSegment segment, SdkVideoView player) {
                }

                @Override // co.vine.android.plugin.RecorderPlugin
/* 218 */       public void onOnboardingStepFinished() {
                }

                @Override // co.vine.android.plugin.RecorderPlugin
/* 223 */       public void onAnimateEditModeControlsInUI() {
                }

                @Override // co.vine.android.plugin.RecorderPlugin
/* 228 */       public void onAnimateEditModeControlsOutUI() {
                }

                @Override // co.vine.android.plugin.RecorderPlugin
/* 232 */       public boolean canEdit() {
/* 233 */           return true;
                }

                @Override // co.vine.android.plugin.RecorderPlugin
/* 238 */       public void onAdjustingLayoutParamsComplete(int topMaskHeightPx, int size) {
                }
            }
