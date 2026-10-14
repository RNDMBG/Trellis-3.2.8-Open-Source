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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 32 */    public class VineSampleSource {
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

/* 33 */        public static class EncodedAudioSamples {
                    public final byte[] data;
                    public final long estimatedDuration;
                    public final AudioFrame[] frames;
                    public final AudioSampleTrack track;

/* 34 */            public EncodedAudioSamples(byte[] data, AudioSampleTrack track, AudioFrame[] frames, long estimatedDuration) {
/* 36 */                this.data = data;
/* 37 */                this.track = track;
/* 38 */                this.frames = frames;
/* 39 */                this.estimatedDuration = estimatedDuration;
                    }
                }

/* 45 */        public static class EncodedVideoSamples {
                    public final byte[] data;
                    public final MediaCodec.BufferInfo[] frames;

/* 46 */            public EncodedVideoSamples(byte[] array, MediaCodec.BufferInfo[] videoFrames) {
/* 48 */                this.data = array;
/* 49 */                this.frames = videoFrames;
                    }
                }

/* 58 */        public VineSampleSource(Uri uri) {
/* 60 */            this.mMediaExtractor = new VineMediaExtractor(uri);
                }

/* 63 */        public void prepare(Context context) throws IOException {
/* 64 */            long start = System.currentTimeMillis();
/* 65 */            this.mMediaExtractor.open(context);
/* 66 */            Log.i(TAG, this.mMediaExtractor.printTrackInfo());
/* 67 */            this.mHasAudioTrack = this.mMediaExtractor.hasAudioTrack();
/* 68 */            if (this.mHasAudioTrack) {
/* 69 */                prepareAudio(this.mMediaExtractor);
                    }
/* 71 */            if (this.mMediaExtractor.hasVideoTrack()) {
/* 72 */                prepareVideo(this.mMediaExtractor);
                    }
/* 74 */            Log.i(TAG, "prepare took " + (System.currentTimeMillis() - start));
                }

/* 77 */        public void release() {
/* 78 */            AudioPreparingThread thread = this.mAudioPrepareThread;
/* 79 */            if (thread != null) {
/* 80 */                thread.cancel();
                        try {
/* 82 */                    thread.join();
                        } catch (InterruptedException e) {
                        }
/* 86 */                this.mError = thread.getError();
/* 87 */                this.mAudioPrepareThread = null;
                    }
/* 89 */            this.mMediaExtractor.release();
/* 90 */            this.mAudioTrack = null;
/* 91 */            this.mVideoData = null;
/* 92 */            this.mVideoFrames = null;
                }

/* 95 */        public boolean hasAudioTrack() {
/* 96 */            return this.mHasAudioTrack;
                }

/* 99 */        private void prepareAudio(VineMediaExtractor extractor) throws IOException {
/* 100 */           this.mAudioTrack = extractor.getAudioSamples().track;
/* 101 */           this.mAudioPrepareThread = new AudioPreparingThread(extractor);
/* 102 */           this.mAudioPrepareThread.start();
                }

/* 105 */       public AudioSampleTrack getAudioTrack() {
/* 106 */           return this.mAudioTrack;
                }

/* 109 */       public ByteBuffer getPreparedAudioSamples() {
/* 110 */           if (this.mAudioPrepareThread != null) {
                        return this.mAudioPrepareThread.getPreparedSamples();
                    }
                    return null;
                }

/* 113 */       public ByteBuffer getUnpreparedAudioSamples() {
/* 114 */           if (this.mAudioPrepareThread != null) {
                        return this.mAudioPrepareThread.getUnpreparedSamples();
                    }
                    return null;
                }

/* 117 */       private void prepareVideo(VineMediaExtractor extractor) {
/* 118 */           if (VineMediaExtractor.access$000(extractor) != null) {
/* 119 */               this.mVideoData = VineMediaExtractor.access$000(extractor).data;
/* 120 */               this.mVideoFrames = VineMediaExtractor.access$000(extractor).frames;
                    }
                }

/* 124 */       public MediaFormat getVideoTrackFormat() {
/* 125 */           return this.mMediaExtractor.getVideoTrackFormat();
                }

/* 128 */       public byte[] getVideoFrames() {
/* 129 */           return this.mVideoData;
                }

/* 132 */       public MediaCodec.BufferInfo[] getVideoFrameInfo() {
/* 133 */           return this.mVideoFrames;
                }

/* 134 */       public static class VineMediaExtractor {
                    private EncodedAudioSamples mAudioSamples;
                    private AudioSampleTrack mAudioTrack;
                    private final int mEstimatedFileSize;
                    private final Uri mUri;
                    private MediaFormat mVideoFormat;
                    private EncodedVideoSamples mVideoSamples;
                    private int mVideoTrackIndex;

/* 135 */           static /* synthetic */ EncodedVideoSamples access$000(VineMediaExtractor x0) {
/* 136 */               return x0.mVideoSamples;
                    }

/* 145 */           public VineMediaExtractor(Uri uri) {
/* 147 */               this.mUri = uri;
/* 148 */               String scheme = uri.getScheme();
/* 149 */               if (CommonUtil.FILE.equals(scheme)) {
/* 150 */                   this.mEstimatedFileSize = (int) (new File(uri.getPath()).length() * 1.5d);
                        } else {
/* 152 */                   this.mEstimatedFileSize = VineSampleSource.DEFAULT_FILE_SIZE;
                        }
                    }

/* 156 */           public synchronized void release() {
                        try {
                            this.mAudioSamples = null;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }

/* 160 */           public synchronized void open(Context context) throws IOException {
                        try {
                            long startTime = System.currentTimeMillis();
/* 162 */                   MediaExtractor extractor = new MediaExtractor();
                            try {
                                try {
/* 164 */                           extractor.setDataSource(context, this.mUri, (Map<String, String>) null);
/* 165 */                           int trackCount = extractor.getTrackCount();
/* 166 */                           this.mAudioTrack = null;
/* 167 */                           int videoTrackIndex = -1;
                                    for (int i = 0; i < trackCount; i++) {
/* 169 */                               extractor.selectTrack(i);
/* 170 */                               MediaFormat format = extractor.getTrackFormat(i);
/* 171 */                               String mime = format.getString("mime");
/* 172 */                               if (this.mAudioTrack == null && isAudio(mime)) {
/* 173 */                                   int sampleRate = -1;
/* 174 */                                   if (format.containsKey("sample-rate")) {
/* 175 */                                       sampleRate = format.getInteger("sample-rate");
                                            }
/* 177 */                                   this.mAudioTrack = new AudioSampleTrack(mime, i, sampleRate, format);
                                        } else if (isVideo(mime)) {
/* 179 */                                   videoTrackIndex = i;
/* 180 */                                   this.mVideoFormat = extractor.getTrackFormat(videoTrackIndex);
                                        }
/* 182 */                               if (videoTrackIndex != -1 && this.mAudioTrack != null) {
                                            break;
                                        }
                                    }
/* 186 */                           this.mVideoTrackIndex = videoTrackIndex;
/* 188 */                           byte[] audioData = new byte[VineSampleSource.DEFAULT_FILE_SIZE];
/* 189 */                           ArrayList<AudioFrame> audioFrames = new ArrayList<>();
/* 190 */                           ByteBuffer audioBuffer = ByteBuffer.wrap(audioData);
/* 191 */                           ByteBuffer videoBuffer = ByteBuffer.allocate((int) (((double) this.mEstimatedFileSize) * 1.5d));
/* 192 */                           long lastTimestamp = 0;
/* 193 */                           ArrayList<MediaCodec.BufferInfo> frames = new ArrayList<>();
/* 194 */                           int offsetAudio = 0;
/* 195 */                           int offsetVideo = 0;
/* 197 */                           for (int trackIndex = extractor.getSampleTrackIndex(); trackIndex >= 0; trackIndex = extractor.getSampleTrackIndex()) {
/* 198 */                               MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
/* 199 */                               if (trackIndex == this.mVideoTrackIndex) {
/* 200 */                                   bufferInfo.offset = offsetVideo;
/* 201 */                                   bufferInfo.size = extractor.readSampleData(videoBuffer, offsetVideo);
/* 202 */                                   if (bufferInfo.size < 0) {
/* 203 */                                       bufferInfo.size = 0;
/* 204 */                                       Log.i(VineSampleSource.TAG, "EOS reached? ");
                                            } else {
/* 206 */                                       offsetVideo += bufferInfo.size;
/* 207 */                                       if (videoBuffer.capacity() - offsetVideo < VineSampleSource.MINIUM_FRAME_SIZE) {
/* 208 */                                           Log.w(VineSampleSource.TAG, "Processing large file?");
/* 209 */                                           ByteBuffer newBuffer = ByteBuffer.allocate(videoBuffer.capacity() * 2);
/* 210 */                                           videoBuffer.position(0);
/* 211 */                                           newBuffer.put(videoBuffer);
/* 212 */                                           videoBuffer = newBuffer;
                                                }
/* 214 */                                       bufferInfo.presentationTimeUs = extractor.getSampleTime();
/* 215 */                                       bufferInfo.flags = extractor.getSampleFlags();
/* 216 */                                       frames.add(bufferInfo);
                                                if (bufferInfo.presentationTimeUs > lastTimestamp) {
/* 218 */                                           lastTimestamp = bufferInfo.presentationTimeUs;
                                                }
                                            }
                                        } else if (this.mAudioTrack != null && trackIndex == this.mAudioTrack.index) {
/* 222 */                                   bufferInfo.offset = offsetAudio;
/* 223 */                                   bufferInfo.size = extractor.readSampleData(audioBuffer, offsetAudio);
/* 224 */                                   if (bufferInfo.size < 0) {
/* 225 */                                       bufferInfo.size = 0;
/* 226 */                                       Log.i(VineSampleSource.TAG, "EOS reached? ");
                                            } else {
/* 228 */                                       offsetAudio += bufferInfo.size;
/* 229 */                                       if (audioBuffer.capacity() - offsetAudio < VineSampleSource.MINIUM_FRAME_SIZE) {
/* 230 */                                           Log.w(VineSampleSource.TAG, "Processing large file?");
/* 231 */                                           audioData = new byte[audioBuffer.capacity() * 2];
/* 232 */                                           ByteBuffer newBuffer2 = ByteBuffer.wrap(audioData);
/* 233 */                                           audioBuffer.position(0);
/* 234 */                                           newBuffer2.put(audioBuffer);
/* 235 */                                           audioBuffer = newBuffer2;
                                                }
/* 237 */                                       bufferInfo.presentationTimeUs = extractor.getSampleTime();
/* 238 */                                       bufferInfo.flags = extractor.getSampleFlags();
/* 239 */                                       audioFrames.add(new AudioFrame(this.mAudioTrack, (bufferInfo.flags & 1) > 0, trackIndex, bufferInfo));
                                                if (bufferInfo.presentationTimeUs > lastTimestamp) {
/* 242 */                                           lastTimestamp = bufferInfo.presentationTimeUs;
                                                }
                                            }
                                        }
/* 246 */                               extractor.advance();
                                    }
/* 249 */                           MediaCodec.BufferInfo[] videoFrames = new MediaCodec.BufferInfo[frames.size()];
/* 250 */                           frames.toArray(videoFrames);
/* 252 */                           this.mVideoSamples = new EncodedVideoSamples(videoBuffer.array(), videoFrames);
/* 254 */                           Log.i(VineSampleSource.TAG, "Reading samples took " + (System.currentTimeMillis() - startTime) + "ms");
/* 255 */                           this.mAudioSamples = new EncodedAudioSamples(audioData, this.mAudioTrack, (AudioFrame[]) audioFrames.toArray(new AudioFrame[audioFrames.size()]), 1000 + lastTimestamp);
/* 260 */                           extractor.release();
                                } catch (Exception e) {
/* 258 */                           throw new IOException(e);
                                }
                            } catch (Throwable th) {
                                extractor.release();
                                throw th;
                            }
                        } catch (Throwable th2) {
                            throw th2;
                        }
                    }

/* 264 */           public EncodedAudioSamples getAudioSamples() {
/* 265 */               return this.mAudioSamples;
                    }

/* 268 */           private boolean isAudio(String mimeType) {
/* 269 */               int indexOfSlash = mimeType.indexOf(47);
/* 270 */               if (indexOfSlash == -1) {
/* 271 */                   throw new IllegalArgumentException("Invalid mime type: " + mimeType);
                        }
/* 273 */               return MimeTypes.BASE_TYPE_AUDIO.equals(mimeType.substring(0, indexOfSlash));
                    }

/* 276 */           private boolean isVideo(String mimeType) {
/* 277 */               int indexOfSlash = mimeType.indexOf(47);
/* 278 */               if (indexOfSlash == -1) {
/* 279 */                   throw new IllegalArgumentException("Invalid mime type: " + mimeType);
                        }
/* 281 */               return MimeTypes.BASE_TYPE_VIDEO.equals(mimeType.substring(0, indexOfSlash));
                    }

/* 284 */           public boolean hasAudioTrack() {
                        return this.mAudioTrack != null;
                    }

/* 288 */           public boolean hasVideoTrack() {
                        return this.mVideoTrackIndex >= 0;
                    }

/* 292 */           public MediaFormat getVideoTrackFormat() {
/* 293 */               return this.mVideoFormat;
                    }

/* 296 */           public String printTrackInfo() {
/* 297 */               StringBuilder trackInfo = new StringBuilder();
/* 298 */               if (this.mAudioTrack != null) {
/* 299 */                   trackInfo.append("\nTrack Index " + this.mAudioTrack.index);
/* 300 */                   trackInfo.append("\nAudio " + this.mAudioTrack.mime);
/* 301 */                   trackInfo.append("\nChannel Count: " + this.mAudioTrack.channelCount);
/* 302 */                   trackInfo.append(IOUtils.LINE_SEPARATOR_UNIX);
                        }
/* 304 */               if (this.mVideoTrackIndex >= 0) {
/* 305 */                   trackInfo.append("\nTrack Index " + this.mVideoTrackIndex);
/* 306 */                   trackInfo.append("\nVideo " + this.mVideoFormat.getString("mime"));
/* 307 */                   trackInfo.append("\nSize: " + this.mVideoFormat.getInteger("width") + " x " + this.mVideoFormat.getInteger("height"));
/* 309 */                   trackInfo.append("\n\n");
                        }
/* 311 */               return trackInfo.toString();
                    }
                }

/* 315 */       public Exception getError() {
/* 316 */           return this.mError;
                }
            }
