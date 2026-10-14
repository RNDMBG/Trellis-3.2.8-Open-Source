            package co.vine.android.recorder;

            import android.annotation.TargetApi;
            import android.content.Context;
            import android.media.MediaCodec;
            import android.media.MediaCrypto;
            import android.media.MediaExtractor;
            import android.media.MediaFormat;
            import android.net.Uri;
            import android.view.Surface;
            import co.vine.android.util.SystemUtil;
            import com.edisonwang.android.slog.SLog;
            import java.io.File;
            import java.io.FileOutputStream;
            import java.io.IOException;
            import java.io.InputStream;
            import java.nio.ByteBuffer;
            import java.util.Map;
            import org.apache.commons.io.FileUtils;
            
            
    public class DecoderTest {
                private static final float MICROS_PER_SECOND = 1000000.0f;
                private static final int TIMEOUT_USEC = 10000;
                private static final String TYPE_AUDIO = "audio/";
                private static final String TYPE_VIDEO = "video/";
                private final Context mContext;
                private File mFile;

        public DecoderTest(Context context) {
            this.mContext = context;
                }

        public static boolean testAudio(Context context, int decodeTestRes) throws IOException {
            DecoderTest test = new DecoderTest(context);
                    try {
                        try {
                    test.initWithRawResource(decodeTestRes);
                    boolean zTranscodeAudio = test.transcodeAudio(1000L);
                    test.release();
                            return zTranscodeAudio;
                        } catch (IOException e) {
                    throw new IOException(e);
                        }
                    } catch (Throwable th) {
                        test.release();
                        throw new IOException(th);
                    }
                }

        public void initWithRawResource(int res) throws IOException {
            String fileName = Uri.parse(SystemUtil.getPathFromResourceRaw(this.mContext, res)).getLastPathSegment();
            InputStream in = this.mContext.getResources().openRawResource(res);
            this.mFile = new File(this.mContext.getCacheDir(), fileName);
            FileOutputStream out = new FileOutputStream(this.mFile);
            byte[] buff = new byte[1024];
                    while (true) {
                        try {
                    int read = in.read(buff);
                            if (read > 0) {
                        out.write(buff, 0, read);
                            } else {
                        in.close();
                        out.close();
                        return;
                            }
                        } catch (Throwable th) {
                            in.close();
                    out.close();
                            throw th;
                        }
                    }
                }

        public void release() throws IOException {
            if (this.mFile.exists()) {
                FileUtils.forceDelete(this.mFile);
                    }
                }

                @TargetApi(16)
        public boolean transcodeAudio(long expectedDuration) throws Throwable {
            MediaExtractor extractor = null;
            MediaCodec decoder = null;
                    try {
                MediaExtractor extractor2 = new MediaExtractor();
                        try {
                    extractor2.setDataSource(this.mContext, Uri.fromFile(this.mFile), (Map<String, String>) null);
                    int trackIndex = selectAudioTrack(extractor2);
                    if (trackIndex < 0) {
                       if (extractor2 != null) {
                           extractor2.release();
                                }
                       if (0 == 0) {
                            return false;
                                }
                       decoder.stop();
                       decoder.release();
                        return false;
                            }
                    extractor2.selectTrack(trackIndex);
                    MediaFormat format = extractor2.getTrackFormat(trackIndex);
                    int channelCount = format.getInteger("channel-count");
                    if (format.containsKey("bitrate")) {
                        format.getInteger("bitrate");
                            }
                    if (format.containsKey("durationUs")) {
                        format.getLong("durationUs");
                            }
                    int sampleRate = format.containsKey("sample-rate") ? format.getInteger("sample-rate") : -1;
                   if (sampleRate < 0) {
                       throw new RuntimeException("no sample rate");
                            }
                   String mime = format.getString("mime");
                   MediaCodec decoder2 = MediaCodec.createDecoderByType(mime);
                   decoder2.configure(format, (Surface) null, (MediaCrypto) null, 0);
                   decoder2.start();
                   ByteBuffer[] input = decoder2.getInputBuffers();
                   ByteBuffer[] output = decoder2.getOutputBuffers();
                   MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
                   boolean sawInputEOS = false;
                   boolean sawOutputEOS = false;
                   int noOutputCounter = 0;
                   Boolean doStop = false;
                   int bufferSize = 0;
                   byte[] chunk = null;
                   int totalCount = 0;
                   long finalPresentationStamp = 0;
                   while (!sawOutputEOS && noOutputCounter < 50 && !doStop.booleanValue()) {
                       noOutputCounter++;
                       if (!sawInputEOS) {
                           long presentationTimeUs = 0;
                           int inputBufIndex = decoder2.dequeueInputBuffer(10000L);
                           if (inputBufIndex >= 0) {
                               ByteBuffer dstBuf = input[inputBufIndex];
                               int sampleSize = extractor2.readSampleData(dstBuf, 0);
                               if (sampleSize < 0) {
                                   sawInputEOS = true;
                                   sampleSize = 0;
                                        } else {
                                   presentationTimeUs = extractor2.getSampleTime();
                                        }
                               decoder2.queueInputBuffer(inputBufIndex, 0, sampleSize, presentationTimeUs, sawInputEOS ? 4 : 0);
                               if (!sawInputEOS) {
                                   extractor2.advance();
                                        }
                                    }
                           int res = decoder2.dequeueOutputBuffer(info, 10000L);
                           if (res >= 0) {
                               if (info.size > 0) {
                                   noOutputCounter = 0;
                                        }
                               ByteBuffer buf = output[res];
                               if (chunk == null || chunk.length < info.size) {
                                   chunk = new byte[info.size];
                                        }
                               int ratio = channelCount * 2;
                               buf.get(chunk);
                               buf.clear();
                               int i = 0;
                               finalPresentationStamp = info.presentationTimeUs;
                               long sampleDurationUs = (long) ((1.0f / sampleRate) * MICROS_PER_SECOND);
                               while (i < info.size / ratio) {
                                   totalCount++;
                                   finalPresentationStamp += sampleDurationUs;
                                   i++;
                                            bufferSize++;
                                        }
                               decoder2.releaseOutputBuffer(res, false);
                               if ((info.flags & 4) != 0) {
                                   sawOutputEOS = true;
                                        }
                                    } else if (res == -3) {
                               output = decoder2.getOutputBuffers();
                                    }
                                }
                            }
                   SLog.m466i("Count {}, TimeUs: {}.", Integer.valueOf(totalCount), Long.valueOf(finalPresentationStamp));
                   if (extractor2 != null) {
                       extractor2.release();
                            }
                   if (decoder2 != null) {
                       decoder2.stop();
                       decoder2.release();
                            }
                   return true;
                        } catch (Throwable th) {
                   th = th;
                            extractor = extractor2;
                            if (extractor != null) {
                       extractor.release();
                            }
                   if (0 != 0) {
                       decoder.stop();
                       decoder.release();
                            }
                            throw th;
                        }
                    } catch (Throwable th2) {
                            throw th2;
                    }
                }

                @TargetApi(16)
       static int selectAudioTrack(MediaExtractor extractor) {
           return selectTrack(extractor, TYPE_AUDIO);
                }

                @TargetApi(16)
       static int selectTrack(MediaExtractor extractor, String type) {
           int numTracks = extractor.getTrackCount();
                    for (int i = 0; i < numTracks; i++) {
               MediaFormat format = extractor.getTrackFormat(i);
               String mime = format.getString("mime");
               if (mime.startsWith(type)) {
                   return i;
                        }
                    }
           return -1;
                }
            }
