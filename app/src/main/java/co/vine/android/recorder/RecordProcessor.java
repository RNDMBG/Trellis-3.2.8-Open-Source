            package co.vine.android.recorder;

            import android.app.Activity;
            import android.hardware.Camera;
            import co.vine.android.recorder.audio.AudioArray;
            import co.vine.android.recorder.audio.AudioReceiver;
            import co.vine.android.recorder.camera.CameraSetting;
            import co.vine.android.recorder.camera.PreviewManagerCallback;
            import co.vine.android.util.MediaUtil;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface RecordProcessor {

                public interface ProcessingErrorHandler {
                    void onNotEnoughSpaceLeft(Exception exc);
                }

                void finishLastIfNeeded();

                Camera.PreviewCallback getCameraPreviewCallback();

                BaseFinishProcessTask getFinishProcessTask();

                PreviewManagerCallback getPreviewManagerCallback();

                SurfaceController.SurfaceListener getSurfaceListener(RecordController recordController);

                void makePreview(RecordingFile recordingFile, RecordSegment recordSegment, boolean z, boolean z2);

                void onEndRelativeTime(RecordSegment recordSegment);

                void onExternalClipAdded(int i);

                void onNewSegmentStart();

                void onSessionTimestampChanged(RecordSession recordSession);

                void onStopped(boolean z);

                AudioReceiver prepareAudioReceiver(RecordSession recordSession);

                void prepareImageReceiver(RecordSession recordSession);

                void releaseResources();

                void setAudioTrim(boolean z);

                void setFinishProcessTask(BaseFinishProcessTask baseFinishProcessTask);

                void setVideoTimeStampUs(long j);

                boolean start(BasicVineRecorder basicVineRecorder, String str, Activity activity, CameraSetting cameraSetting, ProcessingErrorHandler processingErrorHandler);

                MediaUtil.GenerateThumbnailsRunnable stop();

                void stopProcessing();

                void swapSession(RecordSession recordSession);

                boolean wasProcessing();

                MediaUtil.GenerateThumbnailsRunnable writeToFile(RecordingFile recordingFile, ArrayList<RecordSegment> arrayList, byte[] bArr, AudioArray audioArray, boolean z);
            }
