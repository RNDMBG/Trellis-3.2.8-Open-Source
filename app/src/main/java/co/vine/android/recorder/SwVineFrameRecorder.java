            package co.vine.android.recorder;

            import co.vine.android.util.AudioUtils;
            import co.vine.android.util.SystemUtil;
            import com.edisonwang.android.slog.SLog;
            import com.googlecode.javacpp.BytePointer;
            import com.googlecode.javacpp.DoublePointer;
            import com.googlecode.javacpp.FloatPointer;
            import com.googlecode.javacpp.IntPointer;
            import com.googlecode.javacpp.Loader;
            import com.googlecode.javacpp.Pointer;
            import com.googlecode.javacpp.PointerPointer;
            import com.googlecode.javacpp.ShortPointer;
            import com.googlecode.javacv.Frame;
            import com.googlecode.javacv.cpp.avcodec;
            import com.googlecode.javacv.cpp.avformat;
            import com.googlecode.javacv.cpp.avutil;
            import com.googlecode.javacv.cpp.opencv_core;
            import com.googlecode.javacv.cpp.swresample;
            import com.googlecode.javacv.cpp.swscale;
            import java.io.File;
            import java.io.Serializable;
            import java.nio.Buffer;
            import java.nio.ByteBuffer;
            import java.nio.ByteOrder;
            import java.nio.DoubleBuffer;
            import java.nio.FloatBuffer;
            import java.nio.IntBuffer;
            import java.nio.ShortBuffer;
            import java.util.ArrayList;
            import java.util.HashMap;
            import java.util.Map;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 143 */   public class SwVineFrameRecorder {
                static final /* synthetic */ boolean $assertionsDisabled;
                public static final int[] LOCK;
                public static boolean hasEverSuccessfullyLoaded;
                private static Exception loadingException;
                protected int audioBitrate;
                protected int audioChannels;
                protected int audioCodec;
                protected String audioCodecName;
                protected HashMap<String, String> audioOptions;
                protected double audioQuality;
                private long audioRecordTime;
                private avcodec.AVCodecContext audio_c;
                private long audio_clock;
                private avcodec.AVCodec audio_codec;
                private int audio_input_frame_size;
                private BytePointer audio_outbuf;
                private int audio_outbuf_size;
                public avcodec.AVPacket audio_pkt;
                private avformat.AVStream audio_st;
                private String filename;
                protected String format;
                protected int fps;
                private avutil.AVFrame frame;
                protected int frameNumber;
                protected int gop;
                private int[] got_audio_packet;
                private int[] got_video_packet;
                private boolean hasData;
                protected int imageHeight;
                protected int imageWidth;
                private swscale.SwsContext img_convert_ctx;
                protected boolean interleaved;
                private volatile boolean isReleased;
                private boolean isVorbis;
                protected AudioOutBuffer mAudioOutBuffer;
                private boolean mWasKeyFrame;

                /* JADX INFO: renamed from: oc */
                private avformat.AVFormatContext f109oc;
                private avformat.AVOutputFormat oformat;
                private avutil.AVFrame picture;
                private BytePointer picture_buf;
                protected int pixelFormat;
                protected int sampleFormat;
                protected int sampleRate;
                private swresample.SwrContext samples_convert_ctx;
                private Pointer[] samples_in;
                private PointerPointer samples_in_ptr;
                private BytePointer[] samples_out;
                private PointerPointer samples_out_ptr;
                protected long timestamp;
                private avutil.AVFrame tmp_picture;
                protected int videoBitrate;
                protected int videoCodec;
                protected String videoCodecName;
                private long videoEncodeTime;
                protected HashMap<String, String> videoOptions;
                protected double videoQuality;
                private long videoScaleTime;
                private long videoWriteTime;
                private avcodec.AVCodecContext video_c;
                private avcodec.AVCodec video_codec;
                private BytePointer video_outbuf;
                private int video_outbuf_size;
                public avcodec.AVPacket video_pkt;
                private avformat.AVStream video_st;

                static {
/* 145 */           $assertionsDisabled = !SwVineFrameRecorder.class.desiredAssertionStatus();
/* 190 */           LOCK = new int[0];
/* 196 */           loadingException = null;
/* 198 */           hasEverSuccessfullyLoaded = false;
                    synchronized (LOCK) {
                        try {
/* 245 */                   avcodec.avcodec_register_all();
/* 246 */                   avformat.av_register_all();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 165 */       public static class AudioOutBuffer implements Serializable {
                    public final byte[] data;
                    public final ArrayList<Integer> sizes;

/* 166 */           public AudioOutBuffer(byte[] data, ArrayList<Integer> sizes) {
/* 168 */               this.data = data;
/* 172 */               this.sizes = sizes == null ? new ArrayList<>() : sizes;
                    }

/* 175 */           public int getPosition() {
/* 176 */               int size = 0;
                        for (Integer s : this.sizes) {
/* 178 */                   size += s.intValue();
                        }
/* 180 */               return size;
                    }
                }

/* 184 */       public void setAudioOutBuffer(AudioOutBuffer buffer) {
/* 185 */           this.mAudioOutBuffer = buffer;
                }

                /*  JADX ERROR: JadxRuntimeException in pass: BlockSplitter
                    jadx.core.utils.exceptions.JadxRuntimeException: Unexpected missing predecessor for block: B:3:0x0008
                    	at jadx.core.dex.visitors.blocks.BlockSplitter.addTempConnectionsForExcHandlers(BlockSplitter.java:280)
                    	at jadx.core.dex.visitors.blocks.BlockSplitter.visit(BlockSplitter.java:79)
                    */
/* 201 */       public static void tryLoad(android.content.Context r4) throws co.vine.android.recorder.SwVineFrameRecorder.Exception {
                    /*  JADX ERROR: JadxRuntimeException in pass: BlockSplitter
                        jadx.core.utils.exceptions.JadxRuntimeException: Unexpected missing predecessor for block: B:3:0x0008
                        	at jadx.core.dex.visitors.blocks.BlockSplitter.addTempConnectionsForExcHandlers(BlockSplitter.java:280)
                        */
                    /*  JADX ERROR: Method code generation error
                        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.nodes.IContainer.get(jadx.api.plugins.input.data.attributes.IJadxAttrType)" because "cont" is null
                        	at jadx.core.codegen.RegionGen.declareVars(RegionGen.java:70)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:65)
                        	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:291)
                        	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:275)
                        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:420)
                        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:345)
                        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:299)
                        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:186)
                        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1612)
                        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                        	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:261)
                        	at java.base/java.util.stream.ReferencePipeline$7$1FlatMap.end(ReferencePipeline.java:284)
                        	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:571)
                        	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:560)
                        	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:153)
                        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:176)
                        	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:265)
                        	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:632)
                        	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:295)
                        	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:284)
                        	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:268)
                        	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:160)
                        	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:104)
                        	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:45)
                        	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:34)
                        	at jadx.core.codegen.CodeGen.generate(CodeGen.java:22)
                        	at jadx.core.ProcessClass.process(ProcessClass.java:89)
                        	at jadx.core.ProcessClass.generateCode(ProcessClass.java:127)
                        	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:405)
                        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:393)
                        	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:343)
                        */
                    /*
                        return
                    L28:
                    */
                    return;
                }

/* 223 */       public SwVineFrameRecorder(File file, int audioChannels) {
/* 224 */           this(file, 0, 0, audioChannels);
                }

/* 227 */       public SwVineFrameRecorder(String filename, int audioChannels) {
/* 228 */           this(filename, 0, 0, audioChannels);
                }

/* 231 */       public SwVineFrameRecorder(File file, int imageWidth, int imageHeight) {
/* 232 */           this(file, imageWidth, imageHeight, 0);
                }

/* 235 */       public SwVineFrameRecorder(String filename, int imageWidth, int imageHeight) {
/* 236 */           this(filename, imageWidth, imageHeight, 0);
                }

/* 239 */       public SwVineFrameRecorder(File file, int imageWidth, int imageHeight, int audioChannels) {
/* 240 */           this(file.getAbsolutePath(), imageWidth, imageHeight, audioChannels);
                }

/* 249 */       public SwVineFrameRecorder(String filename, int imageWidth, int imageHeight, int audioChannels, avcodec.AVPacket videoPacket, avcodec.AVPacket audioPacket) {
/* 152 */           this.videoQuality = -1.0d;
/* 154 */           this.audioQuality = -1.0d;
/* 156 */           this.videoOptions = new HashMap<>();
/* 157 */           this.audioOptions = new HashMap<>();
/* 158 */           this.frameNumber = 0;
/* 159 */           this.timestamp = 0L;
/* 192 */           this.hasData = false;
/* 193 */           this.isReleased = false;
/* 252 */           SLog.m448d("Creating a recorder thats {} * {}", Integer.valueOf(imageWidth), Integer.valueOf(imageHeight));
/* 254 */           this.filename = filename;
/* 255 */           this.imageWidth = imageWidth;
/* 256 */           this.imageHeight = imageHeight;
/* 257 */           this.audioChannels = audioChannels;
/* 259 */           this.pixelFormat = -1;
/* 260 */           this.videoCodec = 0;
/* 261 */           this.videoBitrate = RecordConfigUtils.VIDEO_BIT_RATE;
/* 262 */           this.fps = 30;
/* 263 */           this.gop = RecordConfigUtils.getGopFromKps(3.0d, this.fps);
/* 265 */           this.sampleFormat = -1;
/* 266 */           this.audioCodec = 0;
/* 267 */           this.audioBitrate = RecordConfigUtils.AUDIO_BIT_RATE;
/* 268 */           this.sampleRate = 44100;
/* 270 */           this.interleaved = true;
/* 271 */           this.video_pkt = videoPacket == null ? new avcodec.AVPacket() : videoPacket;
/* 272 */           this.audio_pkt = audioPacket == null ? new avcodec.AVPacket() : audioPacket;
                }

/* 275 */       public SwVineFrameRecorder(String filename, int imageWidth, int imageHeight, int bitRate, int audioChannels, avcodec.AVPacket videoPacket, avcodec.AVPacket audioPacket) {
/* 276 */           this(filename, imageWidth, imageHeight, audioChannels, videoPacket, audioPacket);
/* 277 */           this.videoBitrate = bitRate;
                }

/* 281 */       public SwVineFrameRecorder(String filename, int imageWidth, int imageHeight, int audioChannels) {
/* 282 */           this(filename, imageWidth, imageHeight, audioChannels, null, null);
                }

/* 285 */       public void setKeyFramesPerSecond(double kps) {
/* 286 */           this.gop = RecordConfigUtils.getGopFromKps(this.fps, this.fps);
                }

/* 289 */       public void release() {
/* 290 */           if (SLog.sLogsOn && !this.isReleased) {
                        if (this.videoScaleTime > 0) {
/* 292 */                   SLog.m442b("Video Scale: {}.", Long.valueOf(this.videoScaleTime));
                        }
                        if (this.videoEncodeTime > 0) {
/* 295 */                   SLog.m442b("Video Encode: {}.", Long.valueOf(this.videoEncodeTime));
                        }
                        if (this.videoWriteTime > 0) {
/* 298 */                   SLog.m442b("Video Write: {}.", Long.valueOf(this.videoWriteTime));
                        }
                        if (this.audioRecordTime > 0) {
/* 301 */                   SLog.m442b("Audio Record: {}.", Long.valueOf(this.audioRecordTime));
                        }
                    }
/* 304 */           this.isReleased = true;
                    synchronized (LOCK) {
                        try {
/* 307 */                   if (this.video_c != null) {
/* 308 */                       avcodec.avcodec_close(this.video_c);
/* 309 */                       this.video_c = null;
                            }
/* 311 */                   if (this.audio_c != null) {
/* 312 */                       avcodec.avcodec_close(this.audio_c);
/* 313 */                       this.audio_c = null;
                            }
/* 315 */                   if (this.picture_buf != null) {
/* 316 */                       avutil.av_free(this.picture_buf);
/* 317 */                       this.picture_buf = null;
                            }
/* 319 */                   if (this.picture != null) {
/* 320 */                       avcodec.avcodec_free_frame(this.picture);
/* 321 */                       this.picture = null;
                            }
/* 323 */                   if (this.tmp_picture != null) {
/* 324 */                       avcodec.avcodec_free_frame(this.tmp_picture);
/* 325 */                       this.tmp_picture = null;
                            }
/* 327 */                   if (this.video_outbuf != null) {
/* 328 */                       avutil.av_free(this.video_outbuf);
/* 329 */                       this.video_outbuf = null;
                            }
/* 331 */                   if (this.frame != null) {
/* 332 */                       avcodec.avcodec_free_frame(this.frame);
/* 333 */                       this.frame = null;
                            }
/* 335 */                   if (this.samples_out != null) {
                                for (int i = 0; i < this.samples_out.length; i++) {
/* 337 */                           avutil.av_free(this.samples_out[i].position(0));
                                }
/* 339 */                       this.samples_out = null;
                            }
/* 341 */                   if (this.audio_outbuf != null) {
/* 342 */                       avutil.av_free(this.audio_outbuf);
/* 343 */                       this.audio_outbuf = null;
                            }
/* 345 */                   this.video_st = null;
/* 346 */                   this.audio_st = null;
/* 348 */                   if (this.f109oc != null) {
/* 350 */                       int nb_streams = this.f109oc.nb_streams();
                                for (int i2 = 0; i2 < nb_streams; i2++) {
/* 352 */                           avutil.av_free(this.f109oc.streams(i2).codec());
/* 353 */                           avutil.av_free(this.f109oc.streams(i2));
                                }
/* 357 */                       avutil.av_free(this.f109oc);
/* 358 */                       this.f109oc = null;
                            }
/* 361 */                   if (this.img_convert_ctx != null) {
/* 362 */                       swscale.sws_freeContext(this.img_convert_ctx);
/* 363 */                       this.img_convert_ctx = null;
                            }
/* 366 */                   if (this.samples_convert_ctx != null) {
/* 367 */                       swresample.swr_free(this.samples_convert_ctx);
/* 368 */                       this.samples_convert_ctx = null;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 374 */       protected void finalize() throws Throwable {
/* 375 */           super.finalize();
/* 376 */           release();
                }

/* 379 */       public String getFilename() {
/* 380 */           return this.filename;
                }

/* 406 */       public int getFrameNumber() {
                    return this.picture == null ? (int) this.audio_pkt.pts() : (int) this.picture.pts();
                }

/* 410 */       public void setFrameNumber(int frameNumber) {
/* 411 */           if (this.picture == null) {
/* 412 */               this.frameNumber = frameNumber;
                    } else {
/* 414 */               this.picture.pts(frameNumber);
                    }
                }

/* 418 */       public long getTimestamp() {
                    return this.picture == null ? (this.audio_clock * AudioUtils.MICROS_PER_SECOND) / ((long) this.sampleRate) : Math.round((((long) getFrameNumber()) * AudioUtils.MICROS_PER_SECOND) / getFps());
                }

/* 426 */       public int setTimestampAndGetFrameNumber(long timestamp) {
/* 427 */           int fn = (int) Math.round((timestamp * getFps()) / 1000000.0d);
/* 428 */           setFrameNumber(fn);
/* 429 */           return fn;
                }

/* 432 */       public void setTimestamp(long timestamp) {
/* 433 */           if (this.picture != null) {
/* 434 */               setFrameNumber((int) Math.round((timestamp * getFps()) / 1000000.0d));
                    }
                }

/* 438 */       public void makeAudioAheadOfVideo() throws Exception {
/* 439 */           if (this.picture != null && this.audio_c != null) {
/* 440 */               long nextFrameNumber = this.picture.pts();
/* 441 */               int x = (int) (((double) this.sampleRate) * ((nextFrameNumber / getFps()) - ((double) (this.audio_clock / this.sampleRate))));
/* 442 */               if (x > 0) {
/* 443 */                   SLog.m478w("Making sure audio is ahead BAD: {}.", Integer.valueOf(x));
/* 444 */                   record(ShortBuffer.wrap(new short[x]));
                        } else {
/* 446 */                   SLog.m465i("Making sure audio is ahead GOOD: {}.", Integer.valueOf(x));
                        }
                    }
                }

/* 451 */       public String getErrorString(int ret) {
/* 452 */           byte[] st = new byte[64];
/* 453 */           avutil.av_make_error_string(st, st.length, ret);
/* 454 */           int i = 0;
                    for (byte a : st) {
/* 456 */               if (a == 0) {
                            break;
                        }
/* 459 */               i++;
                    }
/* 462 */           return new String(st, 0, i);
                }

/* 466 */       public void start() throws Exception {
/* 467 */           this.picture = null;
/* 468 */           this.tmp_picture = null;
/* 469 */           this.picture_buf = null;
/* 470 */           this.frame = null;
/* 471 */           this.video_outbuf = null;
/* 472 */           this.audio_outbuf = null;
/* 473 */           this.f109oc = null;
/* 474 */           this.video_c = null;
/* 475 */           this.audio_c = null;
/* 476 */           this.video_st = null;
/* 477 */           this.audio_st = null;
/* 478 */           this.got_video_packet = new int[1];
/* 479 */           this.got_audio_packet = new int[1];
/* 480 */           this.videoEncodeTime = 0L;
/* 481 */           this.videoWriteTime = 0L;
/* 482 */           this.videoScaleTime = 0L;
/* 483 */           this.audioRecordTime = 0L;
/* 486 */           String format_name = (this.format == null || this.format.length() == 0) ? null : this.format;
/* 487 */           avformat.AVOutputFormat aVOutputFormatAv_guess_format = avformat.av_guess_format(format_name, this.filename, (String) null);
                    this.oformat = aVOutputFormatAv_guess_format;
                    if (aVOutputFormatAv_guess_format == null) {
/* 488 */               int proto = this.filename.indexOf("://");
/* 489 */               if (proto > 0) {
/* 490 */                   format_name = this.filename.substring(0, proto);
                        }
/* 492 */               avformat.AVOutputFormat aVOutputFormatAv_guess_format2 = avformat.av_guess_format(format_name, this.filename, (String) null);
                        this.oformat = aVOutputFormatAv_guess_format2;
                        if (aVOutputFormatAv_guess_format2 == null) {
/* 493 */                   throw new Exception("av_guess_format() error: Could not guess output format for \"" + this.filename + "\" and " + this.format + " format.");
                        }
                    }
/* 496 */           String format_name2 = this.oformat.name().getString();
/* 499 */           avformat.AVFormatContext aVFormatContextAvformat_alloc_context = avformat.avformat_alloc_context();
                    this.f109oc = aVFormatContextAvformat_alloc_context;
                    if (aVFormatContextAvformat_alloc_context == null) {
/* 500 */               throw new Exception("avformat_alloc_context() error: Could not allocate format context");
                    }
/* 503 */           this.f109oc.oformat(this.oformat);
/* 504 */           this.f109oc.filename().putString(this.filename);
/* 509 */           if (this.imageWidth > 0 && this.imageHeight > 0) {
/* 510 */               if (this.videoCodec != 0) {
/* 511 */                   this.oformat.video_codec(this.videoCodec);
                        } else if ("flv".equals(format_name2)) {
/* 513 */                   this.oformat.video_codec(22);
                        } else if ("mp4".equals(format_name2)) {
/* 515 */                   this.oformat.video_codec(13);
                        } else if ("3gp".equals(format_name2)) {
/* 517 */                   this.oformat.video_codec(5);
                        } else if ("avi".equals(format_name2)) {
/* 519 */                   this.oformat.video_codec(26);
                        }
/* 523 */               avcodec.AVCodec namedCodec = avcodec.avcodec_find_encoder_by_name(this.videoCodecName);
                        this.video_codec = namedCodec;
/* 524 */               if (namedCodec == null) {
                            avcodec.AVCodec aVCodecAvcodec_find_encoder = avcodec.avcodec_find_encoder(this.oformat.video_codec());
                            this.video_codec = aVCodecAvcodec_find_encoder;
                            if (aVCodecAvcodec_find_encoder == null) {
/* 526 */                       release();
/* 527 */                       throw new Exception("avcodec_find_encoder() error: Video codec not found.");
                            }
                        }
/* 529 */               if (namedCodec != null) {
/* 530 */                   this.oformat.video_codec(this.video_codec.m2375id());
                        }
/* 533 */               avutil.AVRational frame_rate = avutil.av_d2q(this.fps, 1001000);
/* 534 */               avutil.AVRational supported_framerates = this.video_codec.supported_framerates();
/* 535 */               if (supported_framerates != null) {
/* 536 */                   int idx = avutil.av_find_nearest_q_idx(frame_rate, supported_framerates);
/* 537 */                   frame_rate = supported_framerates.position(idx);
                        }
/* 541 */               avformat.AVStream aVStreamAvformat_new_stream = avformat.avformat_new_stream(this.f109oc, this.video_codec);
                        this.video_st = aVStreamAvformat_new_stream;
                        if (aVStreamAvformat_new_stream == null) {
/* 542 */                   release();
/* 543 */                   throw new Exception("avformat_new_stream() error: Could not allocate video stream.");
                        }
/* 545 */               this.video_c = this.video_st.codec();
/* 546 */               this.video_c.codec_id(this.oformat.video_codec());
/* 547 */               this.video_c.codec_type(0);
/* 550 */               this.video_c.bit_rate(this.videoBitrate);
/* 552 */               this.video_c.width(((this.imageWidth + 15) / 16) * 16);
/* 553 */               this.video_c.height(this.imageHeight);
/* 558 */               this.video_c.time_base(avutil.av_inv_q(frame_rate));
/* 559 */               this.video_c.gop_size(this.gop);
                        if (this.videoQuality >= 0.0d) {
/* 561 */                   this.video_c.flags(this.video_c.flags() | 2);
/* 562 */                   this.video_c.global_quality((int) Math.round(118.0d * this.videoQuality));
                        }
/* 565 */               if (this.pixelFormat != -1) {
/* 566 */                   this.video_c.pix_fmt(this.pixelFormat);
                        } else if (this.video_c.codec_id() == 14 || this.video_c.codec_id() == 62 || this.video_c.codec_id() == 26 || this.video_c.codec_id() == 34) {
/* 569 */                   this.video_c.pix_fmt(avutil.AV_PIX_FMT_RGB32);
                        } else {
/* 571 */                   this.video_c.pix_fmt(0);
                        }
/* 574 */               if (this.video_c.codec_id() == 2) {
/* 576 */                   this.video_c.max_b_frames(2);
                        } else if (this.video_c.codec_id() == 1) {
/* 581 */                   this.video_c.mb_decision(2);
                        } else if (this.video_c.codec_id() == 5) {
/* 584 */                   if (this.imageWidth <= 128 && this.imageHeight <= 96) {
/* 585 */                       this.video_c.width(128).height(96);
                            } else if (this.imageWidth <= 176 && this.imageHeight <= 144) {
/* 587 */                       this.video_c.width(176).height(144);
                            } else if (this.imageWidth <= 352 && this.imageHeight <= 288) {
/* 589 */                       this.video_c.width(352).height(288);
                            } else if (this.imageWidth <= 704 && this.imageHeight <= 576) {
/* 591 */                       this.video_c.width(704).height(576);
                            } else {
/* 593 */                       this.video_c.width(1408).height(1152);
                            }
                        } else if (this.video_c.codec_id() == 28) {
/* 598 */                   this.video_c.profile(avcodec.AVCodecContext.FF_PROFILE_H264_CONSTRAINED_BASELINE);
                        }
/* 602 */               if ((this.oformat.flags() & 64) != 0) {
/* 603 */                   this.video_c.flags(this.video_c.flags() | 4194304);
                        }
/* 606 */               if ((this.video_codec.capabilities() & 512) != 0) {
/* 607 */                   this.video_c.strict_std_compliance(-2);
                        }
                    }
/* 614 */           if (this.audioChannels > 0 && this.audioBitrate > 0 && this.sampleRate > 0) {
/* 615 */               if (this.audioCodec != 0) {
/* 616 */                   this.oformat.audio_codec(this.audioCodec);
                        } else if ("flv".equals(format_name2) || "mp4".equals(format_name2) || "3gp".equals(format_name2)) {
/* 618 */                   this.oformat.audio_codec(avcodec.AV_CODEC_ID_AAC);
                        } else if ("avi".equals(format_name2)) {
/* 620 */                   this.oformat.audio_codec(65536);
                        }
/* 624 */               avcodec.AVCodec aVCodecAvcodec_find_encoder_by_name = avcodec.avcodec_find_encoder_by_name(this.audioCodecName);
                        this.audio_codec = aVCodecAvcodec_find_encoder_by_name;
                        if (aVCodecAvcodec_find_encoder_by_name == null) {
                            avcodec.AVCodec aVCodecAvcodec_find_encoder2 = avcodec.avcodec_find_encoder(this.oformat.audio_codec());
                            this.audio_codec = aVCodecAvcodec_find_encoder2;
                            if (aVCodecAvcodec_find_encoder2 == null) {
/* 626 */                       release();
/* 627 */                       throw new Exception("avcodec_find_encoder() error: Audio codec not found.");
                            }
                        }
/* 630 */               avformat.AVStream aVStreamAvformat_new_stream2 = avformat.avformat_new_stream(this.f109oc, this.audio_codec);
                        this.audio_st = aVStreamAvformat_new_stream2;
                        if (aVStreamAvformat_new_stream2 == null) {
/* 631 */                   release();
/* 632 */                   throw new Exception("avformat_new_stream() error: Could not allocate audio stream.");
                        }
/* 635 */               this.isVorbis = "libvorbis".equals(this.audioCodecName);
/* 637 */               this.audio_c = this.audio_st.codec();
/* 638 */               this.audio_c.codec_id(this.oformat.audio_codec());
/* 639 */               this.audio_c.codec_type(1);
/* 642 */               this.audio_c.bit_rate(this.audioBitrate);
/* 643 */               this.audio_c.sample_rate(this.sampleRate);
/* 644 */               this.audio_c.channels(this.audioChannels);
/* 645 */               this.audio_c.channel_layout(avutil.av_get_default_channel_layout(this.audioChannels));
/* 646 */               if (this.sampleFormat != -1) {
/* 647 */                   this.audio_c.sample_fmt(this.sampleFormat);
                        } else if (this.isVorbis || (this.audio_c.codec_id() == 86018 && (this.audio_codec.capabilities() & 512) != 0)) {
/* 650 */                   this.audio_c.sample_fmt(8);
                        } else {
/* 652 */                   this.audio_c.sample_fmt(1);
                        }
/* 654 */               this.audio_c.time_base().num(1).den(this.sampleRate);
                        switch (this.audio_c.sample_fmt()) {
                            case 0:
                            case 5:
/* 658 */                       this.audio_c.bits_per_raw_sample(8);
                                break;
                            case 1:
                            case 6:
/* 662 */                       this.audio_c.bits_per_raw_sample(16);
                                break;
                            case 2:
                            case 7:
/* 666 */                       this.audio_c.bits_per_raw_sample(32);
                                break;
                            case 3:
                            case 8:
/* 670 */                       this.audio_c.bits_per_raw_sample(32);
                                break;
                            case 4:
                            case 9:
/* 674 */                       this.audio_c.bits_per_raw_sample(64);
                                break;
                            default:
/* 677 */                       if (!$assertionsDisabled) {
                                    throw new AssertionError();
                                }
                                break;
                        }
                        if (this.audioQuality >= 0.0d) {
/* 680 */                   this.audio_c.flags(this.audio_c.flags() | 2);
/* 681 */                   this.audio_c.global_quality((int) Math.round(118.0d * this.audioQuality));
                        }
/* 685 */               if ((this.oformat.flags() & 64) != 0) {
/* 686 */                   this.audio_c.flags(this.audio_c.flags() | 4194304);
                        }
/* 689 */               if ((this.audio_codec.capabilities() & 512) != 0) {
/* 690 */                   this.audio_c.strict_std_compliance(-2);
                        }
                    }
/* 694 */           avformat.av_dump_format(this.f109oc, 0, this.filename, 1);
/* 698 */           if (this.video_st != null) {
/* 699 */               avutil.AVDictionary options = new avutil.AVDictionary(null);
                        if (this.videoQuality >= 0.0d) {
/* 701 */                   avutil.av_dict_set(options, "crf", "" + this.videoQuality, 0);
                        }
                        for (Map.Entry<String, String> e : this.videoOptions.entrySet()) {
/* 704 */                   avutil.av_dict_set(options, e.getKey(), e.getValue(), 0);
                        }
                        synchronized (LOCK) {
                            try {
/* 708 */                       int ret = avcodec.avcodec_open2(this.video_c, this.video_codec, options);
                                if (ret < 0) {
/* 709 */                           release();
/* 710 */                           throw new Exception("avcodec_open2() error " + ret + ": Could not open video codec.");
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
/* 713 */               avutil.av_dict_free(options);
/* 715 */               this.video_outbuf = null;
/* 716 */               if ((this.oformat.flags() & 32) == 0) {
/* 723 */                   this.video_outbuf_size = Math.max(262144, this.video_c.width() * 8 * this.video_c.height());
/* 724 */                   this.video_outbuf = new BytePointer(avutil.av_malloc(this.video_outbuf_size));
                        }
/* 728 */               avutil.AVFrame aVFrameAvcodec_alloc_frame = avcodec.avcodec_alloc_frame();
                        this.picture = aVFrameAvcodec_alloc_frame;
                        if (aVFrameAvcodec_alloc_frame == null) {
/* 729 */                   release();
/* 730 */                   throw new Exception("avcodec_alloc_frame() error: Could not allocate picture.");
                        }
/* 732 */               this.picture.pts(0L);
/* 734 */               int size = avcodec.avpicture_get_size(this.video_c.pix_fmt(), this.video_c.width(), this.video_c.height());
/* 735 */               BytePointer bytePointer = new BytePointer(avutil.av_malloc(size));
                        this.picture_buf = bytePointer;
                        if (bytePointer.isNull()) {
/* 736 */                   release();
/* 737 */                   throw new Exception("av_malloc() error: Could not allocate picture buffer.");
                        }
/* 742 */               avutil.AVFrame aVFrameAvcodec_alloc_frame2 = avcodec.avcodec_alloc_frame();
                        this.tmp_picture = aVFrameAvcodec_alloc_frame2;
                        if (aVFrameAvcodec_alloc_frame2 == null) {
/* 743 */                   release();
/* 744 */                   throw new Exception("avcodec_alloc_frame() error: Could not allocate temporary picture.");
                        }
                    }
/* 748 */           if (this.audio_st != null) {
/* 749 */               avutil.AVDictionary options2 = new avutil.AVDictionary(null);
                        if (this.audioQuality >= 0.0d) {
/* 751 */                   avutil.av_dict_set(options2, "crf", "" + this.audioQuality, 0);
                        }
                        for (Map.Entry<String, String> e2 : this.audioOptions.entrySet()) {
/* 754 */                   avutil.av_dict_set(options2, e2.getKey(), e2.getValue(), 0);
                        }
                        synchronized (LOCK) {
                            try {
/* 759 */                       int ret2 = avcodec.avcodec_open2(this.audio_c, this.audio_codec, options2);
                                if (ret2 < 0) {
/* 760 */                           release();
/* 761 */                           throw new Exception("avcodec_open2() error " + ret2 + ": Could not open audio codec.");
                                }
                            } catch (Throwable th2) {
                                throw th2;
                            }
                        }
/* 764 */               avutil.av_dict_free(options2);
/* 766 */               this.audio_outbuf_size = 262144;
/* 767 */               this.audio_outbuf = new BytePointer(avutil.av_malloc(this.audio_outbuf_size));
/* 771 */               if (this.audio_c.frame_size() <= 1) {
/* 772 */                   this.audio_outbuf_size = 16384;
/* 773 */                   this.audio_input_frame_size = this.audio_outbuf_size / this.audio_c.channels();
                            switch (this.audio_c.codec_id()) {
                                case 65536:
                                case avcodec.AV_CODEC_ID_PCM_S16BE /* 65537 */:
                                case avcodec.AV_CODEC_ID_PCM_U16LE /* 65538 */:
                                case avcodec.AV_CODEC_ID_PCM_U16BE /* 65539 */:
/* 779 */                           this.audio_input_frame_size >>= 1;
                                    break;
                            }
                        } else {
/* 785 */                   this.audio_input_frame_size = this.audio_c.frame_size();
                        }
/* 788 */               int planes = avutil.av_sample_fmt_is_planar(this.audio_c.sample_fmt()) != 0 ? this.audio_c.channels() : 1;
/* 789 */               int data_size = avutil.av_samples_get_buffer_size((IntPointer) null, this.audio_c.channels(), this.audio_input_frame_size, this.audio_c.sample_fmt(), 1) / planes;
/* 791 */               this.samples_out = new BytePointer[planes];
                        for (int i = 0; i < this.samples_out.length; i++) {
/* 793 */                   this.samples_out[i] = new BytePointer(avutil.av_malloc(data_size)).capacity(data_size);
                        }
/* 795 */               this.samples_in = new Pointer[8];
/* 796 */               this.samples_in_ptr = new PointerPointer(8);
/* 797 */               this.samples_out_ptr = new PointerPointer(8);
/* 800 */               avutil.AVFrame aVFrameAvcodec_alloc_frame3 = avcodec.avcodec_alloc_frame();
                        this.frame = aVFrameAvcodec_alloc_frame3;
                        if (aVFrameAvcodec_alloc_frame3 == null) {
/* 801 */                   release();
/* 802 */                   throw new Exception("avcodec_alloc_frame() error: Could not allocate audio frame.");
                        }
                    }
/* 806 */           SystemUtil.quietlyEnsureParentExists(new File(this.filename));
/* 809 */           if ((this.oformat.flags() & 1) == 0) {
/* 810 */               avformat.AVIOContext pb = new avformat.AVIOContext((Pointer) null);
/* 811 */               int ret3 = avformat.avio_open(pb, this.filename, 2);
                        if (ret3 < 0) {
/* 812 */                   release();
/* 813 */                   throw new Exception("BAD avio_open error() error " + ret3 + ": Could not open '" + this.filename);
                        }
/* 815 */               this.f109oc.m2409pb(pb);
                    }
/* 819 */           avformat.avformat_write_header(this.f109oc, (PointerPointer) null);
                }

/* 822 */       public void stopEncoding() throws Exception {
/* 823 */           if (this.f109oc != null && (this.oformat.flags() & 1) == 0) {
/* 826 */               avformat.avio_close(this.f109oc.m2410pb());
                    }
/* 829 */           release();
                }

/* 832 */       public void stop() throws Exception {
/* 833 */           if (this.f109oc != null) {
/* 836 */               while (this.video_st != null && record(null, -1, false)) {
                        }
/* 837 */               while (this.audio_st != null && record((avutil.AVFrame) null)) {
                        }
/* 839 */               if (this.interleaved && this.video_st != null && this.audio_st != null) {
/* 840 */                   avformat.av_interleaved_write_frame(this.f109oc, null);
                        } else {
/* 842 */                   avformat.av_write_frame(this.f109oc, null);
                        }
/* 845 */               this.f109oc.duration((this.audio_clock * 1000) / ((long) this.sampleRate));
/* 848 */               avformat.av_write_trailer(this.f109oc);
/* 850 */               if ((this.oformat.flags() & 1) == 0) {
/* 852 */                   avformat.avio_close(this.f109oc.m2410pb());
                        }
                    }
/* 855 */           release();
                }

/* 858 */       public boolean record(opencv_core.IplImage image) throws Exception {
/* 859 */           return record(image, -1, false);
                }

/* 862 */       public int encode(opencv_core.IplImage image, byte[] output, int start) throws Exception {
                    BytePointer data;
/* 863 */           if (!record(image, -1, true) || (data = this.video_pkt.data()) == null) {
                        return -1;
                    }
/* 866 */           int limit = this.video_pkt.size();
/* 867 */           data.get(output, start, limit);
/* 871 */           return limit;
                }

/* 874 */       public int getVideoCodecPixFormat() {
/* 875 */           return this.video_c.pix_fmt();
                }

/* 878 */       public void writeEncodedImage(byte[] data, int start, int length, boolean keyFrame) throws Exception {
/* 879 */           this.hasData = true;
/* 880 */           avcodec.av_init_packet(this.video_pkt);
/* 881 */           this.video_pkt.data(this.video_outbuf);
/* 882 */           BytePointer pData = this.video_pkt.data();
/* 883 */           pData.position(0);
/* 884 */           pData.put(data, start, length);
/* 885 */           pData.limit(length);
/* 886 */           this.video_pkt.size(length);
/* 887 */           this.video_pkt.pts(this.picture.pts());
/* 888 */           this.video_pkt.dts(this.video_pkt.pts());
/* 889 */           this.picture.pts(this.picture.pts() + 1);
                    if (this.video_pkt.pts() != avutil.AV_NOPTS_VALUE) {
/* 891 */               this.video_pkt.pts(avutil.av_rescale_q(this.video_pkt.pts(), this.video_c.time_base(), this.video_st.time_base()));
                    }
                    if (this.video_pkt.dts() != avutil.AV_NOPTS_VALUE) {
/* 894 */               this.video_pkt.dts(avutil.av_rescale_q(this.video_pkt.dts(), this.video_c.time_base(), this.video_st.time_base()));
                    }
/* 896 */           this.video_pkt.stream_index(this.video_st.index());
/* 897 */           if (keyFrame) {
/* 898 */               this.video_pkt.flags(this.video_pkt.flags() | 1);
                    }
/* 900 */           writeVideoFrame();
                }

/* 904 */       private int writeVideoFrame() throws Exception {
                    int ret;
/* 905 */           long start = System.currentTimeMillis();
                    synchronized (this.f109oc) {
                        try {
/* 908 */                   if (this.interleaved && this.audio_st != null) {
/* 909 */                       ret = avformat.av_interleaved_write_frame(this.f109oc, this.video_pkt);
                                if (ret < 0) {
/* 910 */                           throw new Exception("av_interleaved_write_frame() error " + ret + " while writing interleaved video frame.");
                                }
                            } else {
/* 913 */                       ret = avformat.av_write_frame(this.f109oc, this.video_pkt);
                                if (ret < 0) {
/* 914 */                           throw new Exception("av_write_frame() error " + ret + " while writing video frame.");
                                }
                            }
/* 917 */                   this.videoWriteTime += System.currentTimeMillis() - start;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return ret;
                }

/* 922 */       public boolean record(opencv_core.IplImage image, int pixelFormat, boolean encodeOnly) throws Exception {
/* 923 */           if (this.video_st == null) {
/* 924 */               throw new Exception("No video output stream (Is imageWidth > 0 && imageHeight > 0 and has start() been called?)");
                    }
/* 927 */           if (image != null) {
/* 932 */               this.hasData = true;
/* 933 */               int width = image.width();
/* 934 */               int height = image.height();
/* 935 */               int step = image.widthStep();
/* 936 */               BytePointer data = image.imageData();
/* 938 */               if (pixelFormat == -1) {
/* 939 */                   int depth = image.depth();
/* 940 */                   int channels = image.nChannels();
/* 941 */                   if ((depth == 8 || depth == -2147483640) && channels == 3) {
/* 942 */                       pixelFormat = 3;
                            } else if ((depth == 8 || depth == -2147483640) && channels == 1) {
/* 944 */                       pixelFormat = 8;
                            } else if ((depth == 16 || depth == -2147483632) && channels == 1) {
/* 946 */                       pixelFormat = ByteOrder.nativeOrder().equals(ByteOrder.BIG_ENDIAN) ? 31 : 32;
                            } else if ((depth == 8 || depth == -2147483640) && channels == 4) {
/* 948 */                       pixelFormat = 28;
                            } else if ((depth == 8 || depth == -2147483640) && channels == 2) {
/* 950 */                       pixelFormat = 26;
/* 951 */                       step = width;
                            } else {
/* 953 */                       throw new Exception("Could not guess pixel format of image: depth=" + depth + ", channels=" + channels);
                            }
                        }
/* 957 */               long start = System.currentTimeMillis();
/* 959 */               if (this.video_c.pix_fmt() != pixelFormat || this.video_c.width() != width || this.video_c.height() != height) {
/* 961 */                   this.img_convert_ctx = swscale.sws_getCachedContext(this.img_convert_ctx, width, height, pixelFormat, this.video_c.width(), this.video_c.height(), this.video_c.pix_fmt(), 2, (swscale.SwsFilter) null, (swscale.SwsFilter) null, (DoublePointer) null);
/* 963 */                   if (this.img_convert_ctx == null) {
/* 964 */                       throw new Exception("sws_getCachedContext() error: Cannot initialize the conversion context.");
                            }
/* 966 */                   avcodec.avpicture_fill(new avcodec.AVPicture(this.tmp_picture), data, pixelFormat, width, height);
/* 967 */                   avcodec.avpicture_fill(new avcodec.AVPicture(this.picture), this.picture_buf, this.video_c.pix_fmt(), this.video_c.width(), this.video_c.height());
/* 968 */                   this.tmp_picture.linesize(0, step);
/* 969 */                   swscale.sws_scale(this.img_convert_ctx, new PointerPointer(this.tmp_picture), this.tmp_picture.linesize(), 0, height, new PointerPointer(this.picture), this.picture.linesize());
/* 971 */                   this.videoScaleTime += System.currentTimeMillis() - start;
                        } else {
/* 973 */                   avcodec.avpicture_fill(new avcodec.AVPicture(this.picture), data, pixelFormat, width, height);
/* 974 */                   this.picture.linesize(0, step);
                        }
                    }
/* 977 */           if ((this.oformat.flags() & 32) != 0) {
/* 978 */               if (image == null) {
/* 979 */                   return false;
                        }
/* 982 */               avcodec.av_init_packet(this.video_pkt);
/* 983 */               this.video_pkt.flags(this.video_pkt.flags() | 1);
/* 984 */               this.video_pkt.stream_index(this.video_st.index());
/* 985 */               this.video_pkt.data(new BytePointer(this.picture));
/* 986 */               this.video_pkt.size(Loader.sizeof(avcodec.AVPicture.class));
                    } else {
/* 990 */               avcodec.av_init_packet(this.video_pkt);
/* 991 */               this.video_pkt.data(this.video_outbuf);
/* 992 */               this.video_pkt.size(this.video_outbuf_size);
/* 993 */               this.picture.quality(this.video_c.global_quality());
/* 994 */               long start2 = System.currentTimeMillis();
/* 995 */               int ret = avcodec.avcodec_encode_video2(this.video_c, this.video_pkt, image == null ? null : this.picture, this.got_video_packet);
                        if (ret < 0) {
/* 996 */                   throw new Exception("avcodec_encode_video2() error " + ret + ": Could not encode video packet.");
                        }
/* 998 */               this.picture.pts(this.picture.pts() + 1);
/* 1001 */              if (this.got_video_packet[0] != 0) {
/* 1002 */                  this.mWasKeyFrame = (this.video_pkt.flags() & 1) == 1;
/* 1003 */                  SLog.m465i("Key frame: {}.", Boolean.valueOf(this.mWasKeyFrame));
/* 1005 */                  if (!encodeOnly) {
                                if (this.video_pkt.pts() != avutil.AV_NOPTS_VALUE) {
/* 1007 */                          this.video_pkt.pts(avutil.av_rescale_q(this.video_pkt.pts(), this.video_c.time_base(), this.video_st.time_base()));
                                }
                                if (this.video_pkt.dts() != avutil.AV_NOPTS_VALUE) {
/* 1010 */                          this.video_pkt.dts(avutil.av_rescale_q(this.video_pkt.dts(), this.video_c.time_base(), this.video_st.time_base()));
                                }
/* 1012 */                      this.video_pkt.stream_index(this.video_st.index());
                            }
/* 1017 */                  this.videoEncodeTime += System.currentTimeMillis() - start2;
                        } else {
/* 1015 */                  return false;
                        }
                    }
/* 1019 */          if (!encodeOnly) {
/* 1020 */              writeVideoFrame();
                    }
/* 1023 */          return true;
                }

/* 1026 */      public boolean wasLastEncodedFrameKeyFrame() {
/* 1027 */          return this.mWasKeyFrame;
                }

/* 1030 */      public String getVideoCodecName() {
/* 1031 */          return this.videoCodecName;
                }

/* 1034 */      public void setVideoCodecName(String videoCodecName) {
/* 1035 */          this.videoCodecName = videoCodecName;
                }

/* 1038 */      public String getAudioCodecName() {
/* 1039 */          return this.audioCodecName;
                }

/* 1042 */      public void setAudioCodecName(String audioCodecName) {
/* 1043 */          this.audioCodecName = audioCodecName;
                }

/* 1046 */      public boolean record(Buffer... samples) throws Exception {
                    int inputFormat;
                    int inputDepth;
/* 1047 */          if (this.audio_st == null) {
/* 1048 */              throw new Exception("No audio output stream (Is audioChannels > 0 and has start() been called?)");
                    }
/* 1050 */          this.hasData = true;
/* 1053 */          long start = System.currentTimeMillis();
/* 1055 */          int inputSize = samples[0].limit() - samples[0].position();
/* 1057 */          int inputChannels = samples.length > 1 ? 1 : this.audioChannels;
/* 1059 */          int outputFormat = this.audio_c.sample_fmt();
/* 1060 */          int outputChannels = this.samples_out.length > 1 ? 1 : this.audioChannels;
/* 1061 */          int outputDepth = avutil.av_get_bytes_per_sample(outputFormat);
/* 1062 */          if (samples[0] instanceof ByteBuffer) {
/* 1063 */              inputFormat = samples.length > 1 ? 5 : 0;
/* 1064 */              inputDepth = 1;
                        for (int i = 0; i < samples.length; i++) {
/* 1066 */                  ByteBuffer b = (ByteBuffer) samples[i];
/* 1067 */                  if ((this.samples_in[i] instanceof BytePointer) && this.samples_in[i].capacity() >= inputSize && b.hasArray()) {
/* 1068 */                      ((BytePointer) this.samples_in[i]).position(0).put(b.array(), b.position(), inputSize);
                            } else {
/* 1070 */                      this.samples_in[i] = new BytePointer(b);
                            }
                        }
                    } else if (samples[0] instanceof ShortBuffer) {
/* 1074 */              inputFormat = samples.length > 1 ? 6 : 1;
/* 1075 */              inputDepth = 2;
                        for (int i2 = 0; i2 < samples.length; i2++) {
/* 1077 */                  ShortBuffer b2 = (ShortBuffer) samples[i2];
/* 1078 */                  if ((this.samples_in[i2] instanceof ShortPointer) && this.samples_in[i2].capacity() >= inputSize && b2.hasArray()) {
/* 1079 */                      ((ShortPointer) this.samples_in[i2]).position(0).put(b2.array(), samples[i2].position(), inputSize);
                            } else {
/* 1081 */                      this.samples_in[i2] = new ShortPointer(b2);
                            }
                        }
                    } else if (samples[0] instanceof IntBuffer) {
/* 1085 */              inputFormat = samples.length > 1 ? 7 : 2;
/* 1086 */              inputDepth = 4;
                        for (int i3 = 0; i3 < samples.length; i3++) {
/* 1088 */                  IntBuffer b3 = (IntBuffer) samples[i3];
/* 1089 */                  if ((this.samples_in[i3] instanceof IntPointer) && this.samples_in[i3].capacity() >= inputSize && b3.hasArray()) {
/* 1090 */                      ((IntPointer) this.samples_in[i3]).position(0).put(b3.array(), samples[i3].position(), inputSize);
                            } else {
/* 1092 */                      this.samples_in[i3] = new IntPointer(b3);
                            }
                        }
                    } else if (samples[0] instanceof FloatBuffer) {
/* 1096 */              inputFormat = samples.length > 1 ? 8 : 3;
/* 1097 */              inputDepth = 4;
                        for (int i4 = 0; i4 < samples.length; i4++) {
/* 1099 */                  FloatBuffer b4 = (FloatBuffer) samples[i4];
/* 1100 */                  if ((this.samples_in[i4] instanceof FloatPointer) && this.samples_in[i4].capacity() >= inputSize && b4.hasArray()) {
/* 1101 */                      ((FloatPointer) this.samples_in[i4]).position(0).put(b4.array(), b4.position(), inputSize);
                            } else {
/* 1103 */                      this.samples_in[i4] = new FloatPointer(b4);
                            }
                        }
                    } else if (samples[0] instanceof DoubleBuffer) {
/* 1107 */              inputFormat = samples.length > 1 ? 9 : 4;
/* 1108 */              inputDepth = 8;
                        for (int i5 = 0; i5 < samples.length; i5++) {
/* 1110 */                  DoubleBuffer b5 = (DoubleBuffer) samples[i5];
/* 1111 */                  if ((this.samples_in[i5] instanceof DoublePointer) && this.samples_in[i5].capacity() >= inputSize && b5.hasArray()) {
/* 1112 */                      ((DoublePointer) this.samples_in[i5]).position(0).put(b5.array(), b5.position(), inputSize);
                            } else {
/* 1114 */                      this.samples_in[i5] = new DoublePointer(b5);
                            }
                        }
                    } else {
/* 1118 */              throw new Exception("Audio samples Buffer has unsupported type: " + samples);
                    }
/* 1121 */          if (this.samples_convert_ctx == null) {
/* 1122 */              this.samples_convert_ctx = swresample.swr_alloc_set_opts(null, this.audio_c.channel_layout(), outputFormat, this.audio_c.sample_rate(), this.audio_c.channel_layout(), inputFormat, this.audio_c.sample_rate(), 0, null);
/* 1125 */              if (this.samples_convert_ctx == null) {
/* 1126 */                  throw new Exception("swr_alloc_set_opts() error: Cannot allocate the conversion context.");
                        }
/* 1127 */              int ret = swresample.swr_init(this.samples_convert_ctx);
                        if (ret < 0) {
/* 1128 */                  throw new Exception("swr_init() error " + ret + ": Cannot initialize the conversion context.");
                        }
                    }
                    for (int i6 = 0; i6 < samples.length; i6++) {
/* 1133 */              this.samples_in[i6].position(this.samples_in[i6].position() * inputDepth).limit((this.samples_in[i6].position() + inputSize) * inputDepth);
                    }
/* 1136 */          while (this.samples_in[0].position() < this.samples_in[0].limit()) {
/* 1137 */              int inputCount = (this.samples_in[0].limit() - this.samples_in[0].position()) / (inputChannels * inputDepth);
/* 1138 */              int outputCount = (this.samples_out[0].limit() - this.samples_out[0].position()) / (outputChannels * outputDepth);
/* 1139 */              int count = Math.min(inputCount, outputCount);
                        for (int i7 = 0; i7 < samples.length; i7++) {
/* 1141 */                  this.samples_in_ptr.put(i7, this.samples_in[i7]);
                        }
                        for (int i8 = 0; i8 < this.samples_out.length; i8++) {
/* 1144 */                  this.samples_out_ptr.put(i8, this.samples_out[i8]);
                        }
/* 1146 */              int ret2 = swresample.swr_convert(this.samples_convert_ctx, this.samples_out_ptr, count, this.samples_in_ptr, count);
                        if (ret2 < 0) {
/* 1147 */                  throw new Exception("swr_convert() error " + ret2 + ": Cannot convert audio samples.");
                        }
                        for (int i9 = 0; i9 < samples.length; i9++) {
/* 1150 */                  this.samples_in[i9].position(this.samples_in[i9].position() + (ret2 * inputChannels * inputDepth));
                        }
                        for (int i10 = 0; i10 < this.samples_out.length; i10++) {
/* 1153 */                  this.samples_out[i10].position(this.samples_out[i10].position() + (ret2 * outputChannels * outputDepth));
                        }
/* 1156 */              if (this.samples_out[0].position() >= this.samples_out[0].limit()) {
/* 1157 */                  this.frame.nb_samples(this.audio_input_frame_size);
/* 1158 */                  if (this.isVorbis) {
/* 1159 */                      this.frame.pts((this.audio_clock * 1000) / ((long) this.sampleRate));
                            }
/* 1161 */                  if (this.frame != null) {
/* 1162 */                      this.audio_clock += (long) this.audio_input_frame_size;
                            }
/* 1164 */                  avcodec.avcodec_fill_audio_frame(this.frame, this.audio_c.channels(), outputFormat, this.samples_out[0], this.samples_out[0].limit(), 0);
                            for (int i11 = 0; i11 < this.samples_out.length; i11++) {
/* 1166 */                      this.frame.data(i11, this.samples_out[i11].position(0));
/* 1167 */                      this.frame.linesize(i11, this.samples_out[i11].limit());
                            }
/* 1169 */                  this.frame.quality(this.audio_c.global_quality());
/* 1170 */                  record(this.frame);
                        }
                    }
/* 1174 */          this.audioRecordTime += System.currentTimeMillis() - start;
                    return this.frame.key_frame() != 0;
                }

/* 1180 */      private void writeAudioFrame() throws Exception {
                    synchronized (this.f109oc) {
                        try {
/* 1183 */                  if (this.interleaved && this.video_st != null) {
/* 1184 */                      int ret = avformat.av_interleaved_write_frame(this.f109oc, this.audio_pkt);
                                if (ret < 0) {
/* 1185 */                          throw new Exception("av_interleaved_write_frame() error " + ret + " while writing interleaved audio frame.");
                                }
                            } else {
/* 1188 */                      int ret2 = avformat.av_write_frame(this.f109oc, this.audio_pkt);
                                if (ret2 < 0) {
/* 1189 */                          throw new Exception("av_write_frame() error " + ret2 + " while writing audio frame.");
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 1197 */      boolean record(avutil.AVFrame frame) throws Exception {
/* 1198 */          avcodec.av_init_packet(this.audio_pkt);
/* 1199 */          this.audio_pkt.data(this.audio_outbuf);
/* 1200 */          this.audio_pkt.size(this.audio_outbuf_size);
/* 1201 */          int oldDelay = this.audio_c.delay();
/* 1202 */          int ret = avcodec.avcodec_encode_audio2(this.audio_c, this.audio_pkt, frame, this.got_audio_packet);
                    if (ret < 0) {
/* 1203 */              throw new Exception("avcodec_encode_audio2() error " + ret + ": Could not encode audio packet.");
                    }
/* 1205 */          int newDelay = this.audio_c.delay();
/* 1206 */          boolean delayHasChanged = oldDelay != newDelay;
/* 1207 */          if (this.got_audio_packet[0] == 0) {
                        return false;
                    }
/* 1208 */          if (delayHasChanged) {
/* 1209 */              SLog.m477w("Delay has changed on audio encoder.");
                    }
                    if (this.audio_pkt.pts() < 0) {
/* 1212 */              this.audio_pkt.pts(0L);
                    }
                    if (this.audio_pkt.dts() < 0) {
/* 1215 */              this.audio_pkt.dts(0L);
                    }
                    if (this.audio_pkt.pts() != avutil.AV_NOPTS_VALUE) {
/* 1218 */              this.audio_pkt.pts(avutil.av_rescale_q(this.audio_pkt.pts(), this.audio_c.time_base(), this.audio_c.time_base()));
                    }
                    if (this.audio_pkt.dts() != avutil.AV_NOPTS_VALUE) {
/* 1221 */              this.audio_pkt.dts(avutil.av_rescale_q(this.audio_pkt.dts(), this.audio_c.time_base(), this.audio_c.time_base()));
                    }
/* 1223 */          this.audio_pkt.flags(this.audio_pkt.flags() | 1);
/* 1224 */          this.audio_pkt.stream_index(this.audio_st.index());
/* 1229 */          if (this.mAudioOutBuffer != null) {
/* 1230 */              BytePointer data = this.audio_pkt.data();
/* 1231 */              if (data == null) {
                            return true;
                        }
/* 1232 */              int limit = this.audio_pkt.size();
/* 1233 */              int start = this.mAudioOutBuffer.getPosition();
/* 1234 */              data.get(this.mAudioOutBuffer.data, start, limit);
/* 1235 */              this.mAudioOutBuffer.sizes.add(Integer.valueOf(start));
                        return true;
                    }
/* 1238 */          writeAudioFrame();
                    return true;
                }

/* 1243 */      public boolean hasData() {
/* 1244 */          return this.hasData;
                }

/* 1247 */      public boolean isReleased() {
/* 1248 */          return this.isReleased;
                }

/* 1251 */      public String getFormat() {
/* 1252 */          return this.format;
                }

/* 1255 */      public void setFormat(String format) {
/* 1256 */          this.format = format;
                }

/* 1259 */      public void setVideoCodec(int videoCodec) {
/* 1260 */          this.videoCodec = videoCodec;
                }

/* 1263 */      public double getFps() {
/* 1264 */          return this.fps;
                }

/* 1267 */      public void setFps(int fps) {
/* 1268 */          this.fps = fps;
                }

/* 1271 */      public void setSampleFormat(int sampleFormat) {
/* 1272 */          this.sampleFormat = sampleFormat;
                }

/* 1275 */      public void setAudioCodec(int audioCodec) {
/* 1276 */          this.audioCodec = audioCodec;
                }

/* 1279 */      public void setSampleRate(int sampleRate) {
/* 1280 */          this.sampleRate = sampleRate;
                }

/* 1283 */      public void record(Frame frame) throws Exception {
/* 1284 */          if (frame == null || (frame.image == null && frame.samples == null)) {
/* 1285 */              record((opencv_core.IplImage) null);
/* 1295 */              return;
                    }
/* 1287 */          if (frame.image != null) {
/* 1288 */              record(frame.image);
/* 1289 */              frame.keyFrame = this.mWasKeyFrame;
                    }
/* 1291 */          if (frame.samples != null) {
/* 1292 */              frame.keyFrame = record(frame.samples);
                    }
                }

/* 1297 */      public static class Exception extends java.lang.Exception {
/* 1298 */          public Exception(String message) {
/* 1299 */              super(message);
                    }

/* 1302 */          public Exception(String message, Throwable cause) {
/* 1303 */              super(message, cause);
                    }
                }

/* 1307 */      public static int calculateOutputSampleCount(int inRate, int inCount, int outRate) {
/* 1308 */          float inputSeconds = inCount / (1.0f * inRate);
/* 1309 */          return (int) (outRate * inputSeconds);
                }

/* 1313 */      public static byte[] resample(int inRate, int inCount, byte[] inBuffer, int outRate) {
/* 1314 */          long channel_layout = avutil.av_get_default_channel_layout(1);
/* 1315 */          swresample.SwrContext samples_convert_ctx = swresample.swr_alloc_set_opts(null, channel_layout, 1, outRate, channel_layout, 1, inRate, 0, null);
/* 1319 */          swresample.swr_init(samples_convert_ctx);
/* 1321 */          int outCount = calculateOutputSampleCount(inRate, inCount, outRate);
/* 1323 */          byte[] outBuffer = new byte[outCount * 2];
/* 1324 */          swresample.swr_convert(samples_convert_ctx, outBuffer, outCount, inBuffer, inCount);
/* 1326 */          return outBuffer;
                }
            }
