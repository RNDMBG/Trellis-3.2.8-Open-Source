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
            
            
    public abstract class BaseRecorderPlugin<T extends View, K extends BasicVineRecorder> extends BasePlugin implements RecorderPlugin<T, K> {
                protected Activity mActivity;
                private WeakReference<Fragment> mFragment;
                private WeakReference<T> mView;

                @Override 
                public abstract T onLayout(ViewGroup viewGroup, LayoutInflater layoutInflater, Fragment fragment);

        public BaseRecorderPlugin(String name) {
            super(name);
                }

                @Override 
        public void createLayout(ViewGroup parent, LayoutInflater inflater, Fragment fragment) {
            this.mFragment = new WeakReference<>(fragment);
            T viewOnLayout = onLayout(parent, inflater, fragment);
            if (viewOnLayout != null) {
                parent.addView(viewOnLayout);
                this.mView = new WeakReference<>(viewOnLayout);
                    } else {
                this.mView = null;
                    }
                }

                @Override 
                @Nullable
        public Fragment getFragment() {
            if (this.mFragment != null) {
                return this.mFragment.get();
                    }
                    return null;
                }

                @Override 
                @Nullable
        public T getView() {
            if (this.mView != null) {
                return this.mView.get();
                    }
                    return null;
                }

                @Override 
                @Nullable
        public Handler getHandler() {
            if (this.mManager != null) {
                        return this.mManager.getHandler();
                    }
                    return null;
                }

                @Override 
                @Nullable
        public K getRecorder() {
            RecorderPluginManager<K> manager = getManager();
            if (manager == null) {
                return null;
                    }
            return (K) manager.getRecorder();
                }

        public RecorderPluginManager<K> getManager() {
            return (RecorderPluginManager) this.mManager;
                }

                @Override 
        public void onActivityCreated(Activity activity) {
            super.onActivityCreated(activity);
            this.mActivity = activity;
                }

                @Override 
        public void onResume(Activity activity) {
            this.mActivity = activity;
                }

                @Override 
       public void onPause() {
           this.mActivity = null;
                }

                @Override 
       public boolean onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
           return false;
                }

                @Override 
       public boolean onBackButtonPressed(boolean isEditing) {
           return false;
                }

                @Override 
       public void onCameraReady(boolean frontFacing, boolean autoFocusSet) {
                }

                @Override 
       public boolean onSetEditMode(boolean editing, boolean hasData) {
           return false;
                }

                @Override 
       public void onStartEditMode() {
                }

                @Override 
       public void onStartDrafts() {
                }

                @Override 
       public void onAutoFocus(int x, int y) {
                }

                @Override 
       public void onSessionSwapped() {
                }

                @Override 
       public void onStartRelativeTime(K recorder) {
                }

                @Override 
       public void onEndRelativeTime(K recorder) {
                }

                @Override 
       public void onHideDrafts() {
                }

                @Override 
       public void onShowDrafts() {
                }

                @Override 
       public void onRecorderSet(BasicVineRecorder recorder) {
                }

                @Override 
       public void onDraftUpgradeNumberChanged(int count) {
                }

                @Override 
       public void setMessagingMode(boolean isMessaging) {
                }

                @Override 
       public void onResumeCameraAsyncTaskPostExecute() {
                }

                @Override 
       public void onResumeAsyncTaskPostExecute() {
                }

                @Override 
       public void onZoomUpdated(int zoom, boolean stopped) {
                }

                @Override 
       public void onOfferLastFrame(byte[] array, CameraSetting cameraSetting) {
                }

                @Override 
       public void onSegmentDataChanged(ArrayList<RecordSegment> editedSegments) {
                }

                @Override 
       public void onOfferLastFrame(Bitmap bitmap, CameraSetting cameraSetting) {
                }

                @Override 
       public void onIndividualSegmentClicked(RecordSegment segment, SdkVideoView player) {
                }

                @Override 
       public void onOnboardingStepFinished() {
                }

                @Override 
       public void onAnimateEditModeControlsInUI() {
                }

                @Override 
       public void onAnimateEditModeControlsOutUI() {
                }

                @Override 
       public boolean canEdit() {
           return true;
                }

                @Override 
       public void onAdjustingLayoutParamsComplete(int topMaskHeightPx, int size) {
                }
            }
