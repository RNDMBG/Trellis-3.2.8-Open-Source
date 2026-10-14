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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    public class BaseRecorderPluginManager<T extends BasicVineRecorder> extends BasePluginManager<RecorderPlugin<?, T>> implements RecorderPluginManager<T> {
                private WeakReference<T> mRecorder;

/* 24 */        public BaseRecorderPluginManager() {
                }

/* 28 */        public BaseRecorderPluginManager(Collection<RecorderPlugin<?, T>> plugins) {
/* 29 */            super(plugins);
                }

/* 32 */        public void setRecorder(T recorder) {
/* 33 */            this.mRecorder = new WeakReference<>(recorder);
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
/* 35 */                plugin.onRecorderSet(recorder);
                    }
                }

                @Override // co.vine.android.plugin.RecorderPluginManager
/* 39 */        public T getRecorder() {
/* 41 */            if (this.mRecorder == null) {
/* 40 */                return null;
                    }
/* 42 */            T recorder = this.mRecorder.get();
                    return recorder;
                }

/* 47 */        public void createLayouts(ViewGroup parentView, LayoutInflater inflater, Fragment fragment) {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
/* 49 */                plugin.createLayout(parentView, inflater, fragment);
                    }
                }

/* 53 */        public boolean onBackPressed(boolean isEditing) {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
/* 55 */                if (plugin.onBackButtonPressed(isEditing)) {
/* 56 */                    return true;
                        }
                    }
                    return false;
                }

/* 62 */        public void onStartRelativeTime(T recorder) {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
/* 64 */                plugin.onStartRelativeTime(recorder);
                    }
                }

/* 68 */        public void onEndRelativeTime(T recorder) {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
/* 70 */                plugin.onEndRelativeTime(recorder);
                    }
                }

/* 74 */        public void onCameraReady(boolean frontFacing, boolean autoFocusSet) {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
/* 76 */                plugin.onCameraReady(frontFacing, autoFocusSet);
                    }
                }

/* 80 */        public boolean onSetEditMode(boolean editing, boolean hasData) {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
/* 82 */                if (plugin.onSetEditMode(editing, hasData)) {
/* 83 */                    return true;
                        }
                    }
                    return false;
                }

/* 89 */        public void onStartEditMode() {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
/* 91 */                plugin.onStartEditMode();
                    }
                }

                @Override // co.vine.android.plugin.RecorderPluginManager
/* 95 */        public void onStartDrafts() {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
/* 97 */                plugin.onStartDrafts();
                    }
                }

/* 101 */       public void onAutoFocus(int x, int y) {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
/* 103 */               plugin.onAutoFocus(x, y);
                    }
                }

/* 107 */       public void onSessionSwapped() {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
/* 109 */               plugin.onSessionSwapped();
                    }
                }

/* 113 */       public void onHideDrafts() {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
/* 115 */               plugin.onHideDrafts();
                    }
                }

/* 119 */       public void onShowDrafts() {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
/* 121 */               plugin.onShowDrafts();
                    }
                }

/* 125 */       public void onDraftUpgradeNumberChanged(int count) {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
/* 127 */               plugin.onDraftUpgradeNumberChanged(count);
                    }
                }

/* 131 */       public void setMessagingMode(boolean isMessaging) {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
/* 133 */               plugin.setMessagingMode(isMessaging);
                    }
                }

/* 137 */       public void onResumeCameraAsyncTaskPostExecute() {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
/* 139 */               plugin.onResumeCameraAsyncTaskPostExecute();
                    }
                }

/* 143 */       public void onResumeAsyncTaskPostExecute() {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
/* 145 */               plugin.onResumeAsyncTaskPostExecute();
                    }
                }

/* 149 */       public void onZoomUpdated(int zoom, boolean stopped) {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
/* 151 */               plugin.onZoomUpdated(zoom, stopped);
                    }
                }

/* 155 */       public void onOfferLastFrame(byte[] array, CameraSetting cameraSetting) {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
/* 157 */               plugin.onOfferLastFrame(array, cameraSetting);
                    }
                }

/* 161 */       public void onOfferLastFrame(Bitmap buffer, CameraSetting cameraSetting) {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
/* 163 */               plugin.onOfferLastFrame(buffer, cameraSetting);
                    }
                }

/* 167 */       public void onSegmentDataChanged(ArrayList<RecordSegment> editedSegments) {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
/* 169 */               plugin.onSegmentDataChanged(editedSegments);
                    }
                }

/* 173 */       public void onIndividualSegmentClicked(RecordSegment segment, SdkVideoView player) {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
/* 175 */               plugin.onIndividualSegmentClicked(segment, player);
                    }
                }

/* 179 */       public void onOnboardingStepFinished() {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
/* 181 */               plugin.onOnboardingStepFinished();
                    }
                }

/* 185 */       public void onAnimateEditModeControlsInUI() {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
/* 187 */               plugin.onAnimateEditModeControlsInUI();
                    }
                }

/* 191 */       public void onAnimateEditModeControlsOutUI() {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
/* 193 */               plugin.onAnimateEditModeControlsOutUI();
                    }
                }

/* 197 */       public boolean canEdit() {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
/* 199 */               if (!plugin.canEdit()) {
/* 200 */                   return false;
                        }
                    }
                    return true;
                }

/* 206 */       public void onAdjustingLayoutParamsComplete(int topMaskHeightPx, int size) {
                    for (RecorderPlugin<?, T> plugin : this.mPlugins) {
/* 208 */               plugin.onAdjustingLayoutParamsComplete(topMaskHeightPx, size);
                    }
                }
            }
