            package co.vine.android.plugin;

            import android.graphics.Bitmap;
            import android.support.v4.app.Fragment;
            import android.view.LayoutInflater;
            import android.view.ViewGroup;
            import co.vine.android.player.SdkVideoView;
            import co.vine.android.recorder.BasicVineRecorder;
            import co.vine.android.recorder.RecordSegment;
            import co.vine.android.recorder.camera.CameraSetting;
            import java.lang.ref.WeakReference;
            import java.util.ArrayList;
            import java.util.Collection;
            
            
    public class BaseRecorderPluginManager<T extends BasicVineRecorder> extends BasePluginManager<RecorderPlugin<?, T>> implements RecorderPluginManager<T> {
                private WeakReference<T> mRecorder;

        public BaseRecorderPluginManager() {
                }

        public BaseRecorderPluginManager(Collection<RecorderPlugin<?, T>> plugins) {
            super(plugins);
                }

        public void setRecorder(T recorder) {
            this.mRecorder = new WeakReference<>(recorder);
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
                plugin.onRecorderSet(recorder);
                    }
                }

                @Override 
        public T getRecorder() {
            if (this.mRecorder == null) {
                return null;
                    }
            T recorder = this.mRecorder.get();
                    return recorder;
                }

        public void createLayouts(ViewGroup parentView, LayoutInflater inflater, Fragment fragment) {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
                plugin.createLayout(parentView, inflater, fragment);
                    }
                }

        public boolean onBackPressed(boolean isEditing) {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
                if (plugin.onBackButtonPressed(isEditing)) {
                    return true;
                        }
                    }
                    return false;
                }

        public void onStartRelativeTime(T recorder) {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
                plugin.onStartRelativeTime(recorder);
                    }
                }

        public void onEndRelativeTime(T recorder) {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
                plugin.onEndRelativeTime(recorder);
                    }
                }

        public void onCameraReady(boolean frontFacing, boolean autoFocusSet) {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
                plugin.onCameraReady(frontFacing, autoFocusSet);
                    }
                }

        public boolean onSetEditMode(boolean editing, boolean hasData) {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
                if (plugin.onSetEditMode(editing, hasData)) {
                    return true;
                        }
                    }
                    return false;
                }

        public void onStartEditMode() {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
                plugin.onStartEditMode();
                    }
                }

                @Override 
        public void onStartDrafts() {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
                plugin.onStartDrafts();
                    }
                }

       public void onAutoFocus(int x, int y) {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
               plugin.onAutoFocus(x, y);
                    }
                }

       public void onSessionSwapped() {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
               plugin.onSessionSwapped();
                    }
                }

       public void onHideDrafts() {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
               plugin.onHideDrafts();
                    }
                }

       public void onShowDrafts() {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
               plugin.onShowDrafts();
                    }
                }

       public void onDraftUpgradeNumberChanged(int count) {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
               plugin.onDraftUpgradeNumberChanged(count);
                    }
                }

       public void setMessagingMode(boolean isMessaging) {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
               plugin.setMessagingMode(isMessaging);
                    }
                }

       public void onResumeCameraAsyncTaskPostExecute() {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
               plugin.onResumeCameraAsyncTaskPostExecute();
                    }
                }

       public void onResumeAsyncTaskPostExecute() {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
               plugin.onResumeAsyncTaskPostExecute();
                    }
                }

       public void onZoomUpdated(int zoom, boolean stopped) {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
               plugin.onZoomUpdated(zoom, stopped);
                    }
                }

       public void onOfferLastFrame(byte[] array, CameraSetting cameraSetting) {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
               plugin.onOfferLastFrame(array, cameraSetting);
                    }
                }

       public void onOfferLastFrame(Bitmap buffer, CameraSetting cameraSetting) {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
               plugin.onOfferLastFrame(buffer, cameraSetting);
                    }
                }

       public void onSegmentDataChanged(ArrayList<RecordSegment> editedSegments) {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
               plugin.onSegmentDataChanged(editedSegments);
                    }
                }

       public void onIndividualSegmentClicked(RecordSegment segment, SdkVideoView player) {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
               plugin.onIndividualSegmentClicked(segment, player);
                    }
                }

       public void onOnboardingStepFinished() {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
               plugin.onOnboardingStepFinished();
                    }
                }

       public void onAnimateEditModeControlsInUI() {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
               plugin.onAnimateEditModeControlsInUI();
                    }
                }

       public void onAnimateEditModeControlsOutUI() {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
               plugin.onAnimateEditModeControlsOutUI();
                    }
                }

       public boolean canEdit() {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
               if (!plugin.canEdit()) {
                   return false;
                        }
                    }
                    return true;
                }

       public void onAdjustingLayoutParamsComplete(int topMaskHeightPx, int size) {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
               plugin.onAdjustingLayoutParamsComplete(topMaskHeightPx, size);
                    }
                }
            }
