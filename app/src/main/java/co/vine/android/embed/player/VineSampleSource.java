            package co.vine.android.embed.player;

            import android.content.Context;
            import android.media.MediaCodec;
            import android.media.MediaExtractor;
            import android.media.MediaFormat;
            import android.net.Uri;
            import android.util.Log;
            import co.vine.android.util.CommonUtil;
            import com.google.android.exoplayer.util.MimeTypes;
            import java.io.File;
            import java.io.IOException;
            import java.nio.ByteBuffer;
            import java.util.ArrayList;
            import java.util.Map;
            import org.apache.commons.io.IOUtils;
            
            
    public class VineSampleSource {
                private static final int DEFAULT_FILE_SIZE = 5242880;
                private static final int MINIUM_FRAME_SIZE = 2073600;
                private static final String TAG = "VineSampleSource";
                private AudioPreparingThread mAudioPrepareThread;
                private AudioSampleTrack mAudioTrack;
                private Exception mError;
                private boolean mHasAudioTrack;
                private final VineMediaExtractor mMediaExtractor;
                private byte[] mVideoData;
                private MediaCodec.BufferInfo[] mVideoFrames;

        public static class EncodedAudioSamples {
                    public final byte[] data;
                    public final long estimatedDuration;
                    public final AudioFrame[] frames;
                    public final AudioSampleTrack track;

            public EncodedAudioSamples(byte[] data, AudioSampleTrack track, AudioFrame[] frames, long estimatedDuration) {
                this.data = data;
                this.track = track;
                this.frames = frames;
                this.estimatedDuration = estimatedDuration;
                    }
                }

        public static class EncodedVideoSamples {
                    public final byte[] data;
                    public final MediaCodec.BufferInfo[] frames;

            public EncodedVideoSamples(byte[] array, MediaCodec.BufferInfo[] videoFrames) {
                this.data = array;
                this.frames = videoFrames;
                    }
                }

        public VineSampleSource(Uri uri) {
            this.mMediaExtractor = new VineMediaExtractor(uri);
                }

        public void prepare(Context context) throws IOException {
            long start = System.currentTimeMillis();
            this.mMediaExtractor.open(context);
            Log.i(TAG, this.mMediaExtractor.printTrackInfo());
            this.mHasAudioTrack = this.mMediaExtractor.hasAudioTrack();
            if (this.mHasAudioTrack) {
                prepareAudio(this.mMediaExtractor);
                    }
            if (this.mMediaExtractor.hasVideoTrack()) {
                prepareVideo(this.mMediaExtractor);
                    }
            Log.i(TAG, "prepare took " + (System.currentTimeMillis() - start));
                }

        public void release() {
            AudioPreparingThread thread = this.mAudioPrepareThread;
            if (thread != null) {
                thread.cancel();
                        try {
                    thread.join();
                        } catch (InterruptedException e) {
                        }
                this.mError = thread.getError();
                this.mAudioPrepareThread = null;
                    }
            this.mMediaExtractor.release();
            this.mAudioTrack = null;
            this.mVideoData = null;
            this.mVideoFrames = null;
                }

        public boolean hasAudioTrack() {
            return this.mHasAudioTrack;
                }

        private void prepareAudio(VineMediaExtractor extractor) throws IOException {
           this.mAudioTrack = extractor.getAudioSamples().track;
           this.mAudioPrepareThread = new AudioPreparingThread(extractor);
           this.mAudioPrepareThread.start();
                }

       public AudioSampleTrack getAudioTrack() {
           return this.mAudioTrack;
                }

       public ByteBuffer getPreparedAudioSamples() {
           if (this.mAudioPrepareThread != null) {
                        return this.mAudioPrepareThread.getPreparedSamples();
                    }
                    return null;
                }

       public ByteBuffer getUnpreparedAudioSamples() {
           if (this.mAudioPrepareThread != null) {
                        return this.mAudioPrepareThread.getUnpreparedSamples();
                    }
                    return null;
                }

       private void prepareVideo(VineMediaExtractor extractor) {
           if (VineMediaExtractor.access$000(extractor) != null) {
               this.mVideoData = VineMediaExtractor.access$000(extractor).data;
               this.mVideoFrames = VineMediaExtractor.access$000(extractor).frames;
                    }
                }

       public MediaFormat getVideoTrackFormat() {
           return this.mMediaExtractor.getVideoTrackFormat();
                }

       public byte[] getVideoFrames() {
           return this.mVideoData;
                }

       public MediaCodec.BufferInfo[] getVideoFrameInfo() {
           return this.mVideoFrames;
                }

       public static class VineMediaExtractor {
                    private EncodedAudioSamples mAudioSamples;
                    private AudioSampleTrack mAudioTrack;
                    private final int mEstimatedFileSize;
                    private final Uri mUri;
                    private MediaFormat mVideoFormat;
                    private EncodedVideoSamples mVideoSamples;
                    private int mVideoTrackIndex;

           static  EncodedVideoSamples access$000(VineMediaExtractor x0) {
               return x0.mVideoSamples;
                    }

           public VineMediaExtractor(Uri uri) {
               this.mUri = uri;
               String scheme = uri.getScheme();
               if (CommonUtil.FILE.equals(scheme)) {
                   this.mEstimatedFileSize = (int) (new File(uri.getPath()).length() * 1.5d);
                        } else {
                   this.mEstimatedFileSize = VineSampleSource.DEFAULT_FILE_SIZE;
                        }
                    }

           public synchronized void release() {
                        try {
                            this.mAudioSamples = null;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }

           public synchronized void open(Context context) throws IOException {
                        try {
                            long startTime = System.currentTimeMillis();
                   MediaExtractor extractor = new MediaExtractor();
                            try {
                                try {
                           extractor.setDataSource(context, this.mUri, (Map<String, String>) null);
                           int trackCount = extractor.getTrackCount();
                           this.mAudioTrack = null;
                           int videoTrackIndex = -1;
                                    for (int i = 0; i < trackCount; i++) {
                               extractor.selectTrack(i);
                               MediaFormat format = extractor.getTrackFormat(i);
                               String mime = format.getString("mime");
                               if (this.mAudioTrack == null && isAudio(mime)) {
                                   int sampleRate = -1;
                                   if (format.containsKey("sample-rate")) {
                                       sampleRate = format.getInteger("sample-rate");
                                            }
                                   this.mAudioTrack = new AudioSampleTrack(mime, i, sampleRate, format);
                                        } else if (isVideo(mime)) {
                                   videoTrackIndex = i;
                                   this.mVideoFormat = extractor.getTrackFormat(videoTrackIndex);
                                        }
                               if (videoTrackIndex != -1 && this.mAudioTrack != null) {
                                            break;
                                        }
                                    }
                           this.mVideoTrackIndex = videoTrackIndex;
                           byte[] audioData = new byte[VineSampleSource.DEFAULT_FILE_SIZE];
                           ArrayList<AudioFrame> audioFrames = new ArrayList<>();
                           ByteBuffer audioBuffer = ByteBuffer.wrap(audioData);
                           ByteBuffer videoBuffer = ByteBuffer.allocate((int) (((double) this.mEstimatedFileSize) * 1.5d));
                           long lastTimestamp = 0;
                           ArrayList<MediaCodec.BufferInfo> frames = new ArrayList<>();
                           int offsetAudio = 0;
                           int offsetVideo = 0;
                           for (int trackIndex = extractor.getSampleTrackIndex(); trackIndex >= 0; trackIndex = extractor.getSampleTrackIndex()) {
                               MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
                               if (trackIndex == this.mVideoTrackIndex) {
                                   bufferInfo.offset = offsetVideo;
                                   bufferInfo.size = extractor.readSampleData(videoBuffer, offsetVideo);
                                   if (bufferInfo.size < 0) {
                                       bufferInfo.size = 0;
                                       Log.i(VineSampleSource.TAG, "EOS reached? ");
                                            } else {
                                       offsetVideo += bufferInfo.size;
                                       if (videoBuffer.capacity() - offsetVideo < VineSampleSource.MINIUM_FRAME_SIZE) {
                                           Log.w(VineSampleSource.TAG, "Processing large file?");
                                           ByteBuffer newBuffer = ByteBuffer.allocate(videoBuffer.capacity() * 2);
                                           videoBuffer.position(0);
                                           newBuffer.put(videoBuffer);
                                           videoBuffer = newBuffer;
                                                }
                                       bufferInfo.presentationTimeUs = extractor.getSampleTime();
                                       bufferInfo.flags = extractor.getSampleFlags();
                                       frames.add(bufferInfo);
                                                if (bufferInfo.presentationTimeUs > lastTimestamp) {
                                           lastTimestamp = bufferInfo.presentationTimeUs;
                                                }
                                            }
                                        } else if (this.mAudioTrack != null && trackIndex == this.mAudioTrack.index) {
                                   bufferInfo.offset = offsetAudio;
                                   bufferInfo.size = extractor.readSampleData(audioBuffer, offsetAudio);
                                   if (bufferInfo.size < 0) {
                                       bufferInfo.size = 0;
                                       Log.i(VineSampleSource.TAG, "EOS reached? ");
                                            } else {
                                       offsetAudio += bufferInfo.size;
                                       if (audioBuffer.capacity() - offsetAudio < VineSampleSource.MINIUM_FRAME_SIZE) {
                                           Log.w(VineSampleSource.TAG, "Processing large file?");
                                           audioData = new byte[audioBuffer.capacity() * 2];
                                           ByteBuffer newBuffer2 = ByteBuffer.wrap(audioData);
                                           audioBuffer.position(0);
                                           newBuffer2.put(audioBuffer);
                                           audioBuffer = newBuffer2;
                                                }
                                       bufferInfo.presentationTimeUs = extractor.getSampleTime();
                                       bufferInfo.flags = extractor.getSampleFlags();
                                       audioFrames.add(new AudioFrame(this.mAudioTrack, (bufferInfo.flags & 1) > 0, trackIndex, bufferInfo));
                                                if (bufferInfo.presentationTimeUs > lastTimestamp) {
                                           lastTimestamp = bufferInfo.presentationTimeUs;
                                                }
                                            }
                                        }
                               extractor.advance();
                                    }
                           MediaCodec.BufferInfo[] videoFrames = new MediaCodec.BufferInfo[frames.size()];
                           frames.toArray(videoFrames);
                           this.mVideoSamples = new EncodedVideoSamples(videoBuffer.array(), videoFrames);
                           Log.i(VineSampleSource.TAG, "Reading samples took " + (System.currentTimeMillis() - startTime) + "ms");
                           this.mAudioSamples = new EncodedAudioSamples(audioData, this.mAudioTrack, (AudioFrame[]) audioFrames.toArray(new AudioFrame[audioFrames.size()]), 1000 + lastTimestamp);
                           extractor.release();
                                } catch (Exception e) {
                           throw new IOException(e);
                                }
                            } catch (Throwable th) {
                                extractor.release();
                                throw th;
                            }
                        } catch (Throwable th2) {
                            throw th2;
                        }
                    }

           public EncodedAudioSamples getAudioSamples() {
               return this.mAudioSamples;
                    }

           private boolean isAudio(String mimeType) {
               int indexOfSlash = mimeType.indexOf(47);
               if (indexOfSlash == -1) {
                   throw new IllegalArgumentException("Invalid mime type: " + mimeType);
                        }
               return MimeTypes.BASE_TYPE_AUDIO.equals(mimeType.substring(0, indexOfSlash));
                    }

           private boolean isVideo(String mimeType) {
               int indexOfSlash = mimeType.indexOf(47);
               if (indexOfSlash == -1) {
                   throw new IllegalArgumentException("Invalid mime type: " + mimeType);
                        }
               return MimeTypes.BASE_TYPE_VIDEO.equals(mimeType.substring(0, indexOfSlash));
                    }

           public boolean hasAudioTrack() {
                        return this.mAudioTrack != null;
                    }

           public boolean hasVideoTrack() {
                        return this.mVideoTrackIndex >= 0;
                    }

           public MediaFormat getVideoTrackFormat() {
               return this.mVideoFormat;
                    }

           public String printTrackInfo() {
               StringBuilder trackInfo = new StringBuilder();
               if (this.mAudioTrack != null) {
                   trackInfo.append("\nTrack Index " + this.mAudioTrack.index);
                   trackInfo.append("\nAudio " + this.mAudioTrack.mime);
                   trackInfo.append("\nChannel Count: " + this.mAudioTrack.channelCount);
                   trackInfo.append(IOUtils.LINE_SEPARATOR_UNIX);
                        }
               if (this.mVideoTrackIndex >= 0) {
                   trackInfo.append("\nTrack Index " + this.mVideoTrackIndex);
                   trackInfo.append("\nVideo " + this.mVideoFormat.getString("mime"));
                   trackInfo.append("\nSize: " + this.mVideoFormat.getInteger("width") + " x " + this.mVideoFormat.getInteger("height"));
                   trackInfo.append("\n\n");
                        }
               return trackInfo.toString();
                    }
                }

       public Exception getError() {
           return this.mError;
                }
            }
