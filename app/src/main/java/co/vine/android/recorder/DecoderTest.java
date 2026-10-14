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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 34 */    public class DecoderTest {
                private static final float MICROS_PER_SECOND = 1000000.0f;
                private static final int TIMEOUT_USEC = 10000;
                private static final String TYPE_AUDIO = "audio/";
                private static final String TYPE_VIDEO = "video/";
                private final Context mContext;
                private File mFile;

/* 35 */        public DecoderTest(Context context) {
/* 37 */            this.mContext = context;
                }

/* 40 */        public static boolean testAudio(Context context, int decodeTestRes) throws IOException {
/* 41 */            DecoderTest test = new DecoderTest(context);
                    try {
                        try {
/* 43 */                    test.initWithRawResource(decodeTestRes);
/* 44 */                    boolean zTranscodeAudio = test.transcodeAudio(1000L);
/* 48 */                    test.release();
                            return zTranscodeAudio;
                        } catch (IOException e) {
/* 46 */                    throw new IOException(e);
                        }
                    } catch (Throwable th) {
                        test.release();
                        throw new IOException(th);
                    }
                }

/* 52 */        public void initWithRawResource(int res) throws IOException {
/* 53 */            String fileName = Uri.parse(SystemUtil.getPathFromResourceRaw(this.mContext, res)).getLastPathSegment();
/* 54 */            InputStream in = this.mContext.getResources().openRawResource(res);
/* 55 */            this.mFile = new File(this.mContext.getCacheDir(), fileName);
/* 56 */            FileOutputStream out = new FileOutputStream(this.mFile);
/* 57 */            byte[] buff = new byte[1024];
                    while (true) {
                        try {
/* 60 */                    int read = in.read(buff);
                            if (read > 0) {
/* 61 */                        out.write(buff, 0, read);
                            } else {
/* 64 */                        in.close();
/* 65 */                        out.close();
/* 67 */                        return;
                            }
                        } catch (Throwable th) {
                            in.close();
/* 65 */                    out.close();
                            throw th;
                        }
                    }
                }

/* 69 */        public void release() throws IOException {
/* 70 */            if (this.mFile.exists()) {
/* 71 */                FileUtils.forceDelete(this.mFile);
                    }
                }

                @TargetApi(16)
/* 76 */        public boolean transcodeAudio(long expectedDuration) throws Throwable {
/* 77 */            MediaExtractor extractor = null;
/* 78 */            MediaCodec decoder = null;
                    try {
/* 80 */                MediaExtractor extractor2 = new MediaExtractor();
                        try {
/* 81 */                    extractor2.setDataSource(this.mContext, Uri.fromFile(this.mFile), (Map<String, String>) null);
/* 82 */                    int trackIndex = selectAudioTrack(extractor2);
/* 83 */                    if (trackIndex < 0) {
/* 190 */                       if (extractor2 != null) {
/* 191 */                           extractor2.release();
                                }
/* 195 */                       if (0 == 0) {
/* 84 */                            return false;
                                }
/* 196 */                       decoder.stop();
/* 197 */                       decoder.release();
/* 84 */                        return false;
                            }
/* 86 */                    extractor2.selectTrack(trackIndex);
/* 88 */                    MediaFormat format = extractor2.getTrackFormat(trackIndex);
/* 92 */                    int channelCount = format.getInteger("channel-count");
/* 93 */                    if (format.containsKey("bitrate")) {
/* 94 */                        format.getInteger("bitrate");
                            }
/* 95 */                    if (format.containsKey("durationUs")) {
/* 96 */                        format.getLong("durationUs");
                            }
/* 98 */                    int sampleRate = format.containsKey("sample-rate") ? format.getInteger("sample-rate") : -1;
/* 100 */                   if (sampleRate < 0) {
/* 101 */                       throw new RuntimeException("no sample rate");
                            }
/* 104 */                   String mime = format.getString("mime");
/* 105 */                   MediaCodec decoder2 = MediaCodec.createDecoderByType(mime);
/* 106 */                   decoder2.configure(format, (Surface) null, (MediaCrypto) null, 0);
/* 107 */                   decoder2.start();
/* 108 */                   ByteBuffer[] input = decoder2.getInputBuffers();
/* 109 */                   ByteBuffer[] output = decoder2.getOutputBuffers();
/* 112 */                   MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
/* 113 */                   boolean sawInputEOS = false;
/* 114 */                   boolean sawOutputEOS = false;
/* 115 */                   int noOutputCounter = 0;
/* 118 */                   Boolean doStop = false;
/* 120 */                   int bufferSize = 0;
/* 122 */                   byte[] chunk = null;
/* 123 */                   int totalCount = 0;
/* 124 */                   long finalPresentationStamp = 0;
/* 125 */                   while (!sawOutputEOS && noOutputCounter < 50 && !doStop.booleanValue()) {
/* 126 */                       noOutputCounter++;
/* 128 */                       if (!sawInputEOS) {
/* 129 */                           long presentationTimeUs = 0;
/* 130 */                           int inputBufIndex = decoder2.dequeueInputBuffer(10000L);
/* 132 */                           if (inputBufIndex >= 0) {
/* 133 */                               ByteBuffer dstBuf = input[inputBufIndex];
/* 135 */                               int sampleSize = extractor2.readSampleData(dstBuf, 0);
/* 138 */                               if (sampleSize < 0) {
/* 139 */                                   sawInputEOS = true;
/* 140 */                                   sampleSize = 0;
                                        } else {
/* 142 */                                   presentationTimeUs = extractor2.getSampleTime();
                                        }
/* 144 */                               decoder2.queueInputBuffer(inputBufIndex, 0, sampleSize, presentationTimeUs, sawInputEOS ? 4 : 0);
/* 146 */                               if (!sawInputEOS) {
/* 147 */                                   extractor2.advance();
                                        }
                                    }
/* 150 */                           int res = decoder2.dequeueOutputBuffer(info, 10000L);
/* 151 */                           if (res >= 0) {
/* 152 */                               if (info.size > 0) {
/* 153 */                                   noOutputCounter = 0;
                                        }
/* 156 */                               ByteBuffer buf = output[res];
/* 158 */                               if (chunk == null || chunk.length < info.size) {
/* 159 */                                   chunk = new byte[info.size];
                                        }
/* 164 */                               int ratio = channelCount * 2;
/* 166 */                               buf.get(chunk);
/* 167 */                               buf.clear();
/* 168 */                               int i = 0;
/* 170 */                               finalPresentationStamp = info.presentationTimeUs;
/* 173 */                               long sampleDurationUs = (long) ((1.0f / sampleRate) * MICROS_PER_SECOND);
/* 174 */                               while (i < info.size / ratio) {
/* 175 */                                   totalCount++;
/* 176 */                                   finalPresentationStamp += sampleDurationUs;
/* 174 */                                   i++;
                                            bufferSize++;
                                        }
/* 179 */                               decoder2.releaseOutputBuffer(res, false);
/* 180 */                               if ((info.flags & 4) != 0) {
/* 181 */                                   sawOutputEOS = true;
                                        }
                                    } else if (res == -3) {
/* 184 */                               output = decoder2.getOutputBuffers();
                                    }
                                }
                            }
/* 188 */                   SLog.m466i("Count {}, TimeUs: {}.", Integer.valueOf(totalCount), Long.valueOf(finalPresentationStamp));
/* 190 */                   if (extractor2 != null) {
/* 191 */                       extractor2.release();
                            }
/* 195 */                   if (decoder2 != null) {
/* 196 */                       decoder2.stop();
/* 197 */                       decoder2.release();
                            }
/* 201 */                   return true;
                        } catch (Throwable th) {
/* 190 */                   th = th;
                            extractor = extractor2;
                            if (extractor != null) {
/* 191 */                       extractor.release();
                            }
/* 195 */                   if (0 != 0) {
/* 196 */                       decoder.stop();
/* 197 */                       decoder.release();
                            }
                            throw th;
                        }
                    } catch (Throwable th2) {
                            throw th2;
                    }
                }

                @TargetApi(16)
/* 211 */       static int selectAudioTrack(MediaExtractor extractor) {
/* 212 */           return selectTrack(extractor, TYPE_AUDIO);
                }

                @TargetApi(16)
/* 217 */       static int selectTrack(MediaExtractor extractor, String type) {
/* 218 */           int numTracks = extractor.getTrackCount();
                    for (int i = 0; i < numTracks; i++) {
/* 220 */               MediaFormat format = extractor.getTrackFormat(i);
/* 221 */               String mime = format.getString("mime");
/* 222 */               if (mime.startsWith(type)) {
/* 227 */                   return i;
                        }
                    }
/* 227 */           return -1;
                }
            }
