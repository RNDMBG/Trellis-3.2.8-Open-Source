            package co.vine.android.recorder;

            import co.vine.android.util.AudioUtils;
            import com.googlecode.javacpp.BytePointer;
            import com.googlecode.javacpp.DoublePointer;
            import com.googlecode.javacpp.IntPointer;
            import com.googlecode.javacpp.Pointer;
            import com.googlecode.javacpp.PointerPointer;
            import com.googlecode.javacv.Frame;
            import com.googlecode.javacv.cpp.avcodec;
            import com.googlecode.javacv.cpp.avformat;
            import com.googlecode.javacv.cpp.avutil;
            import com.googlecode.javacv.cpp.opencv_core;
            import com.googlecode.javacv.cpp.swscale;
            import java.nio.Buffer;
            import java.nio.ByteBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 33 */    public class VineFrameGrabber {
                static final /* synthetic */ boolean $assertionsDisabled;
                private avcodec.AVCodecContext audio_c;
                private avformat.AVStream audio_st;
                private BytePointer buffer_rgb;
                private String filename;
                private Frame frame;
                private boolean frameGrabbed;
                private int[] got_frame;
                private swscale.SwsContext img_convert_ctx;

                /* JADX INFO: renamed from: oc */
                private avformat.AVFormatContext f110oc;
                private avutil.AVFrame picture;
                private avutil.AVFrame picture_rgb;
                private avcodec.AVPacket pkt;
                private avcodec.AVPacket pkt2;
                private opencv_core.IplImage return_image;
                private Buffer[] samples_buf;
                private avutil.AVFrame samples_frame;
                private BytePointer[] samples_ptr;
                private int sizeof_pkt;
                private avcodec.AVCodecContext video_c;
                private avformat.AVStream video_st;
                protected String format = null;
                protected int imageWidth = 0;
                protected int imageHeight = 0;
                protected int audioChannels = 0;
                protected ImageMode imageMode = ImageMode.COLOR;
                protected int pixelFormat = -1;
                protected double frameRate = 0.0d;
                protected int sampleFormat = 0;
                protected int sampleRate = 0;
                protected int numBuffers = 4;
                protected double gamma = 0.0d;
                protected boolean deinterlace = false;
                protected int frameNumber = 0;
                protected long timestamp = 0;

                public enum ImageMode {
                    COLOR,
                    GRAY,
                    RAW
                }

                static {
/* 35 */            $assertionsDisabled = !VineFrameGrabber.class.desiredAssertionStatus();
                    synchronized (SwVineFrameRecorder.LOCK) {
                        try {
/* 59 */                    avcodec.avcodec_register_all();
/* 60 */                    avformat.av_register_all();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 37 */        public avcodec.AVCodecContext getVideoCodecContext() {
/* 38 */            return this.video_c;
                }

/* 63 */        public VineFrameGrabber(String filename) {
/* 65 */            this.filename = filename;
                }

/* 67 */        public void release() {
/* 68 */            if (this.pkt != null && this.pkt2 != null) {
/* 69 */                if (this.pkt2.size() > 0) {
/* 70 */                    avcodec.av_free_packet(this.pkt);
                        }
/* 72 */                this.pkt2 = null;
                        this.pkt = null;
                    }
/* 76 */            if (this.buffer_rgb != null) {
/* 77 */                avutil.av_free(this.buffer_rgb);
/* 78 */                this.buffer_rgb = null;
                    }
/* 80 */            if (this.picture_rgb != null) {
/* 81 */                avcodec.avcodec_free_frame(this.picture_rgb);
/* 82 */                this.picture_rgb = null;
                    }
/* 86 */            if (this.picture != null) {
/* 87 */                avcodec.avcodec_free_frame(this.picture);
/* 88 */                this.picture = null;
                    }
/* 92 */            if (this.video_c != null) {
/* 93 */                avcodec.avcodec_close(this.video_c);
/* 94 */                this.video_c = null;
                    }
/* 98 */            if (this.samples_frame != null) {
/* 99 */                avcodec.avcodec_free_frame(this.samples_frame);
/* 100 */               this.samples_frame = null;
                    }
/* 104 */           if (this.audio_c != null) {
/* 105 */               avcodec.avcodec_close(this.audio_c);
/* 106 */               this.audio_c = null;
                    }
/* 110 */           if (this.f110oc != null && !this.f110oc.isNull()) {
/* 111 */               avformat.avformat_close_input(this.f110oc);
/* 112 */               this.f110oc = null;
                    }
/* 115 */           if (this.img_convert_ctx != null) {
/* 116 */               swscale.sws_freeContext(this.img_convert_ctx);
/* 117 */               this.img_convert_ctx = null;
                    }
/* 120 */           this.got_frame = null;
/* 121 */           this.return_image = null;
/* 122 */           this.frameGrabbed = false;
/* 123 */           this.frame = null;
/* 124 */           this.timestamp = 0L;
/* 125 */           this.frameNumber = 0;
                }

/* 127 */       protected void finalize() throws Throwable {
/* 128 */           super.finalize();
/* 129 */           release();
                }

/* 150 */       public double getGamma() {
                    if (this.gamma == 0.0d) {
/* 152 */               return 2.2d;
                    }
                    return this.gamma;
                }

/* 158 */       public String getFormat() {
                    return this.f110oc == null ? this.format : this.f110oc.iformat().name().getString();
                }

/* 166 */       public int getImageWidth() {
                    return this.return_image == null ? this.imageWidth : this.return_image.width();
                }

/* 170 */       public int getImageHeight() {
                    return this.return_image == null ? this.imageHeight : this.return_image.height();
                }

/* 174 */       public int getAudioChannels() {
                    return this.audio_c == null ? this.audioChannels : this.audio_c.channels();
                }

/* 178 */       public int getPixelFormat() {
/* 179 */           if (this.imageMode == ImageMode.COLOR || this.imageMode == ImageMode.GRAY) {
/* 180 */               if (this.pixelFormat == -1) {
                            return this.imageMode == ImageMode.COLOR ? 3 : 8;
                        }
/* 183 */               return this.pixelFormat;
                    }
/* 185 */           if (this.video_c != null) {
/* 186 */               return this.video_c.pix_fmt();
                    }
/* 188 */           return this.pixelFormat;
                }

/* 192 */       public double getFrameRate() {
/* 193 */           if (this.video_st == null) {
/* 194 */               return this.frameRate;
                    }
/* 196 */           avutil.AVRational r = this.video_st.r_frame_rate();
/* 197 */           return ((double) r.num()) / ((double) r.den());
                }

/* 201 */       public int getSampleFormat() {
                    return this.audio_c == null ? this.sampleFormat : this.audio_c.sample_fmt();
                }

/* 205 */       public int getSampleRate() {
                    return this.audio_c == null ? this.sampleRate : this.audio_c.sample_rate();
                }

/* 210 */       public void setFrameNumber(int frameNumber) throws Exception {
/* 211 */           setTimestamp(Math.round((AudioUtils.MICROS_PER_SECOND * ((long) frameNumber)) / getFrameRate()));
                }

/* 215 */       public void setTimestamp(long timestamp) throws Exception {
/* 216 */           if (this.f110oc == null || this.video_c == null) {
/* 217 */               this.timestamp = timestamp;
/* 239 */               return;
                    }
/* 219 */           long timestamp2 = (timestamp * AudioUtils.MICROS_PER_SECOND) / AudioUtils.MICROS_PER_SECOND;
                    if (this.f110oc.start_time() != avutil.AV_NOPTS_VALUE) {
/* 222 */               timestamp2 += this.f110oc.start_time();
                    }
/* 224 */           int ret = avformat.avformat_seek_file(this.f110oc, -1, Long.MIN_VALUE, timestamp2, Long.MAX_VALUE, 1);
                    if (ret < 0) {
/* 225 */               throw new Exception("avformat_seek_file() error " + ret + ": Could not seek file to timestamp " + timestamp2 + ".");
                    }
/* 227 */           avcodec.avcodec_flush_buffers(this.video_c);
/* 228 */           if (this.audio_c != null) {
/* 229 */               avcodec.avcodec_flush_buffers(this.audio_c);
                    }
                    while (this.timestamp > timestamp2 && grab(false) != null) {
                    }
                    while (this.timestamp < timestamp2 && grab(false) != null) {
                    }
/* 237 */           this.frameGrabbed = true;
                }

/* 242 */       public int getLengthInFrames() {
/* 243 */           return (int) ((getLengthInTime() * getFrameRate()) / 1000000.0d);
                }

/* 245 */       public long getLengthInTime() {
/* 246 */           return (this.f110oc.duration() * AudioUtils.MICROS_PER_SECOND) / AudioUtils.MICROS_PER_SECOND;
                }

/* 250 */       public void start() throws Exception {
/* 251 */           this.img_convert_ctx = null;
/* 252 */           this.f110oc = new avformat.AVFormatContext((Pointer) null);
/* 253 */           this.video_c = null;
/* 254 */           this.audio_c = null;
/* 255 */           this.pkt = new avcodec.AVPacket();
/* 256 */           this.pkt2 = new avcodec.AVPacket();
/* 257 */           this.sizeof_pkt = this.pkt.sizeof();
/* 258 */           this.got_frame = new int[1];
/* 259 */           this.return_image = null;
/* 260 */           this.frameGrabbed = false;
/* 261 */           this.frame = new Frame();
/* 262 */           this.timestamp = 0L;
/* 263 */           this.frameNumber = 0;
/* 265 */           this.pkt2.size(0);
/* 268 */           avformat.AVInputFormat f = null;
/* 269 */           if (this.format != null && this.format.length() > 0 && (f = avformat.av_find_input_format(this.format)) == null) {
/* 271 */               throw new Exception("av_find_input_format() error: Could not find input format \"" + this.format + "\".");
                    }
/* 274 */           avutil.AVDictionary options = new avutil.AVDictionary(null);
                    if (this.frameRate > 0.0d) {
/* 276 */               avutil.AVRational r = avutil.av_d2q(this.frameRate, 1001000);
/* 277 */               avutil.av_dict_set(options, "framerate", r.num() + "/" + r.den(), 0);
                    }
/* 279 */           if (this.imageMode != ImageMode.RAW) {
/* 280 */               avutil.av_dict_set(options, "pixel_format", this.imageMode == ImageMode.COLOR ? "bgr24" : "gray8", 0);
                    }
/* 282 */           if (this.imageWidth > 0 && this.imageHeight > 0) {
/* 283 */               avutil.av_dict_set(options, "video_size", this.imageWidth + "x" + this.imageHeight, 0);
                    }
/* 285 */           if (this.sampleRate > 0) {
/* 286 */               avutil.av_dict_set(options, "sample_rate", "" + this.sampleRate, 0);
                    }
/* 288 */           if (this.audioChannels > 0) {
/* 289 */               avutil.av_dict_set(options, "channels", "" + this.audioChannels, 0);
                    }
/* 291 */           int ret = avformat.avformat_open_input(this.f110oc, this.filename, f, options);
                    if (ret < 0) {
/* 292 */               throw new Exception("avformat_open_input() error " + ret + ": Could not open input \"" + this.filename + "\". (Has setFormat() been called?)");
                    }
/* 294 */           avutil.av_dict_free(options);
/* 297 */           int ret2 = avformat.avformat_find_stream_info(this.f110oc, (PointerPointer) null);
                    if (ret2 < 0) {
/* 298 */               throw new Exception("avformat_find_stream_info() error " + ret2 + ": Could not find stream information.");
                    }
/* 302 */           avformat.av_dump_format(this.f110oc, 0, this.filename, 0);
/* 305 */           this.audio_st = null;
/* 305 */           this.video_st = null;
/* 306 */           int nb_streams = this.f110oc.nb_streams();
                    for (int i = 0; i < nb_streams; i++) {
/* 308 */               avformat.AVStream st = this.f110oc.streams(i);
/* 310 */               avcodec.AVCodecContext c = st.codec();
/* 311 */               if (this.video_st == null && c.codec_type() == 0) {
/* 312 */                   this.video_st = st;
/* 313 */                   this.video_c = c;
                        } else if (this.audio_st == null && c.codec_type() == 1) {
/* 315 */                   this.audio_st = st;
/* 316 */                   this.audio_c = c;
                        }
                    }
/* 319 */           if (this.video_st == null && this.audio_st == null) {
/* 320 */               throw new Exception("Did not find a video or audio stream inside \"" + this.filename + "\".");
                    }
/* 323 */           if (this.video_st != null) {
/* 325 */               avcodec.AVCodec codec = avcodec.avcodec_find_decoder(this.video_c.codec_id());
/* 326 */               if (codec == null) {
/* 327 */                   throw new Exception("avcodec_find_decoder() error: Unsupported video format or codec not found: " + this.video_c.codec_id() + ".");
                        }
/* 331 */               int ret3 = avcodec.avcodec_open2(this.video_c, codec, (PointerPointer) null);
                        if (ret3 < 0) {
/* 332 */                   throw new Exception("avcodec_open2() error " + ret3 + ": Could not open video codec.");
                        }
/* 336 */               if (this.video_c.time_base().num() > 1000 && this.video_c.time_base().den() == 1) {
/* 337 */                   this.video_c.time_base().den(1000);
                        }
/* 341 */               avutil.AVFrame aVFrameAvcodec_alloc_frame = avcodec.avcodec_alloc_frame();
                        this.picture = aVFrameAvcodec_alloc_frame;
                        if (aVFrameAvcodec_alloc_frame == null) {
/* 342 */                   throw new Exception("avcodec_alloc_frame() error: Could not allocate raw picture frame.");
                        }
/* 344 */               avutil.AVFrame aVFrameAvcodec_alloc_frame2 = avcodec.avcodec_alloc_frame();
                        this.picture_rgb = aVFrameAvcodec_alloc_frame2;
                        if (aVFrameAvcodec_alloc_frame2 == null) {
/* 345 */                   throw new Exception("avcodec_alloc_frame() error: Could not allocate RGB picture frame.");
                        }
/* 348 */               int width = getImageWidth() > 0 ? getImageWidth() : this.video_c.width();
/* 349 */               int height = getImageHeight() > 0 ? getImageHeight() : this.video_c.height();
                        switch (this.imageMode) {
                            case COLOR:
                            case GRAY:
/* 354 */                       int fmt = getPixelFormat();
/* 357 */                       int size = avcodec.avpicture_get_size(fmt, width, height);
/* 358 */                       this.buffer_rgb = new BytePointer(avutil.av_malloc(size));
/* 362 */                       avcodec.avpicture_fill(new avcodec.AVPicture(this.picture_rgb), this.buffer_rgb, fmt, width, height);
/* 364 */                       this.return_image = opencv_core.IplImage.createHeader(width, height, 8, 1);
                                break;
                            case RAW:
/* 368 */                       this.buffer_rgb = null;
/* 369 */                       this.return_image = opencv_core.IplImage.createHeader(this.video_c.width(), this.video_c.height(), 8, 1);
                                break;
                            default:
/* 373 */                       if (!$assertionsDisabled) {
                                    throw new AssertionError();
                                }
                                break;
                        }
                    }
/* 377 */           if (this.audio_st != null) {
/* 379 */               avcodec.AVCodec codec2 = avcodec.avcodec_find_decoder(this.audio_c.codec_id());
/* 380 */               if (codec2 == null) {
/* 381 */                   throw new Exception("avcodec_find_decoder() error: Unsupported audio format or codec not found: " + this.audio_c.codec_id() + ".");
                        }
/* 385 */               int ret4 = avcodec.avcodec_open2(this.audio_c, codec2, (PointerPointer) null);
                        if (ret4 < 0) {
/* 386 */                   throw new Exception("avcodec_open2() error " + ret4 + ": Could not open audio codec.");
                        }
/* 390 */               avutil.AVFrame aVFrameAvcodec_alloc_frame3 = avcodec.avcodec_alloc_frame();
                        this.samples_frame = aVFrameAvcodec_alloc_frame3;
                        if (aVFrameAvcodec_alloc_frame3 == null) {
/* 391 */                   throw new Exception("avcodec_alloc_frame() error: Could not allocate audio frame.");
                        }
                    }
                }

/* 396 */       public void stop() throws Exception {
/* 397 */           release();
                }

/* 400 */       public void trigger() throws Exception {
/* 401 */           if (this.f110oc == null || this.f110oc.isNull()) {
/* 402 */               throw new Exception("Could not trigger: No AVFormatContext. (Has start() been called?)");
                    }
/* 404 */           if (this.pkt2.size() > 0) {
/* 405 */               this.pkt2.size(0);
/* 406 */               avcodec.av_free_packet(this.pkt);
                    }
/* 409 */           for (int i = 0; i < this.numBuffers + 1 && avformat.av_read_frame(this.f110oc, this.pkt) >= 0; i++) {
/* 412 */               avcodec.av_free_packet(this.pkt);
                    }
                }

/* 416 */       private void processImage() throws Exception {
                    switch (this.imageMode) {
                        case COLOR:
                        case GRAY:
/* 421 */                   if (this.deinterlace) {
/* 422 */                       avcodec.AVPicture p = new avcodec.AVPicture(this.picture);
/* 423 */                       avcodec.avpicture_deinterlace(p, p, this.video_c.pix_fmt(), this.video_c.width(), this.video_c.height());
                            }
/* 427 */                   this.img_convert_ctx = swscale.sws_getCachedContext(this.img_convert_ctx, this.video_c.width(), this.video_c.height(), this.video_c.pix_fmt(), getImageWidth(), getImageHeight(), getPixelFormat(), 2, (swscale.SwsFilter) null, (swscale.SwsFilter) null, (DoublePointer) null);
/* 430 */                   if (this.img_convert_ctx == null) {
/* 431 */                       throw new Exception("sws_getCachedContext() error: Cannot initialize the conversion context.");
                            }
/* 435 */                   swscale.sws_scale(this.img_convert_ctx, new PointerPointer(this.picture), this.picture.linesize(), 0, this.video_c.height(), new PointerPointer(this.picture_rgb), this.picture_rgb.linesize());
/* 437 */                   this.return_image.imageData(this.buffer_rgb);
/* 438 */                   this.return_image.widthStep(this.picture_rgb.linesize(0));
                            break;
                        case RAW:
/* 442 */                   if (!$assertionsDisabled && (this.video_c.width() != this.return_image.width() || this.video_c.height() != this.return_image.height())) {
                                throw new AssertionError();
                            }
/* 444 */                   this.return_image.imageData(this.picture.data(0));
/* 445 */                   this.return_image.widthStep(this.picture.linesize(0));
                            break;
                        default:
/* 449 */                   if (!$assertionsDisabled) {
                                throw new AssertionError();
                            }
                            break;
                    }
/* 451 */           this.return_image.imageSize(this.return_image.height() * this.return_image.widthStep());
/* 452 */           this.return_image.nChannels(this.return_image.widthStep() / this.return_image.width());
                }

/* 455 */       public opencv_core.IplImage grab() throws Exception {
/* 456 */           Frame frame = grabFrame(true, false);
/* 457 */           if (frame != null) {
                        return frame.image;
                    }
                    return null;
                }

/* 460 */       public opencv_core.IplImage grab(boolean processImage) throws Exception {
/* 461 */           Frame frame = grabFrame(processImage, false);
/* 462 */           if (frame != null) {
                        return frame.image;
                    }
                    return null;
                }

/* 464 */       public Frame grabFrame() throws Exception {
/* 465 */           return grabFrame(true, true);
                }

/* 468 */       private Frame grabFrame(boolean processImage, boolean doAudio) throws Exception {
/* 469 */           this.frame.image = null;
/* 470 */           this.frame.samples = null;
/* 471 */           if (this.frameGrabbed) {
/* 472 */               this.frameGrabbed = false;
/* 473 */               if (processImage) {
/* 474 */                   processImage();
                        }
/* 476 */               this.frame.image = this.return_image;
/* 477 */               return this.frame;
                    }
/* 479 */           if (this.f110oc == null || this.f110oc.isNull()) {
/* 480 */               throw new Exception("Could not grab: No AVFormatContext. (Has start() been called?)");
                    }
/* 482 */           boolean done = false;
/* 483 */           while (!done) {
/* 484 */               if (this.pkt2.size() <= 0 && avformat.av_read_frame(this.f110oc, this.pkt) < 0) {
/* 486 */                   if (this.video_st != null) {
/* 488 */                       this.pkt.stream_index(this.video_st.index());
/* 489 */                       this.pkt.data(null);
/* 490 */                       this.pkt.size(0);
                            } else {
/* 492 */                       return null;
                            }
                        }
/* 498 */               if (this.video_st != null && this.pkt.stream_index() == this.video_st.index()) {
/* 503 */                   if (avcodec.avcodec_decode_video2(this.video_c, this.picture, this.got_frame, this.pkt) >= 0 && this.got_frame[0] != 0) {
/* 504 */                       long pts = avutil.av_frame_get_best_effort_timestamp(this.picture);
/* 505 */                       avutil.AVRational time_base = this.video_st.time_base();
/* 506 */                       this.timestamp = ((AudioUtils.MICROS_PER_SECOND * pts) * ((long) time_base.num())) / ((long) time_base.den());
/* 508 */                       this.frameNumber = (int) ((this.timestamp * getFrameRate()) / 1000000.0d);
/* 509 */                       if (processImage) {
/* 510 */                           processImage();
                                }
/* 512 */                       done = true;
/* 514 */                       this.frame.image = this.return_image;
                            } else if (this.pkt.data() == null && this.pkt.size() == 0) {
/* 516 */                       return null;
                            }
                        } else if (doAudio && this.audio_st != null && this.pkt.stream_index() == this.audio_st.index()) {
/* 519 */                   if (this.pkt2.size() <= 0) {
/* 522 */                       BytePointer.memcpy(this.pkt2, this.pkt, this.sizeof_pkt);
                            }
/* 524 */                   avcodec.avcodec_get_frame_defaults(this.samples_frame);
/* 526 */                   int len = avcodec.avcodec_decode_audio4(this.audio_c, this.samples_frame, this.got_frame, this.pkt2);
/* 527 */                   if (len <= 0) {
/* 529 */                       this.pkt2.size(0);
                            } else {
/* 531 */                       this.pkt2.data(this.pkt2.data().position(len));
/* 532 */                       this.pkt2.size(this.pkt2.size() - len);
/* 533 */                       if (this.got_frame[0] != 0) {
/* 534 */                           long pts2 = avutil.av_frame_get_best_effort_timestamp(this.samples_frame);
/* 535 */                           avutil.AVRational time_base2 = this.audio_st.time_base();
/* 536 */                           this.timestamp = ((AudioUtils.MICROS_PER_SECOND * pts2) * ((long) time_base2.num())) / ((long) time_base2.den());
/* 538 */                           done = true;
/* 539 */                           int sample_format = this.samples_frame.format();
/* 540 */                           int planes = avutil.av_sample_fmt_is_planar(sample_format) != 0 ? this.samples_frame.channels() : 1;
/* 541 */                           int data_size = avutil.av_samples_get_buffer_size((IntPointer) null, this.audio_c.channels(), this.samples_frame.nb_samples(), this.audio_c.sample_fmt(), 1) / planes;
/* 543 */                           if (this.samples_buf == null || this.samples_buf.length != planes) {
/* 544 */                               this.samples_ptr = new BytePointer[planes];
/* 545 */                               this.samples_buf = new Buffer[planes];
                                    }
/* 548 */                           this.frame.samples = this.samples_buf;
/* 549 */                           int sample_size = data_size / avutil.av_get_bytes_per_sample(sample_format);
                                    for (int i = 0; i < planes; i++) {
/* 551 */                               BytePointer p = this.samples_frame.data(i);
/* 552 */                               if (!p.equals(this.samples_ptr[i]) || this.samples_ptr[i].capacity() < data_size) {
/* 553 */                                   this.samples_ptr[i] = p.capacity(data_size);
/* 554 */                                   ByteBuffer b = p.asBuffer();
                                            switch (sample_format) {
                                                case 0:
                                                case 5:
/* 557 */                                           this.samples_buf[i] = b;
                                                    break;
                                                case 1:
                                                case 6:
/* 559 */                                           this.samples_buf[i] = b.asShortBuffer();
                                                    break;
                                                case 2:
                                                case 7:
/* 561 */                                           this.samples_buf[i] = b.asIntBuffer();
                                                    break;
                                                case 3:
                                                case 8:
/* 563 */                                           this.samples_buf[i] = b.asFloatBuffer();
                                                    break;
                                                case 4:
                                                case 9:
/* 565 */                                           this.samples_buf[i] = b.asDoubleBuffer();
                                                    break;
                                                default:
/* 566 */                                           if (!$assertionsDisabled) {
                                                        throw new AssertionError();
                                                    }
                                                    break;
                                            }
                                        }
/* 569 */                               this.samples_buf[i].position(0).limit(sample_size);
                                    }
                                }
                            }
                        }
/* 575 */               if (this.pkt2.size() <= 0) {
/* 577 */                   avcodec.av_free_packet(this.pkt);
                        }
                    }
/* 580 */           return this.frame;
                }

/* 582 */       public void setFormat(String format) {
/* 583 */           this.format = format;
                }

/* 586 */       public void setFrameRate(double frameRate) {
/* 587 */           this.frameRate = frameRate;
                }

/* 590 */       public long getTimestamp() {
/* 591 */           return this.timestamp;
                }

/* 593 */       public static class Exception extends java.lang.Exception {
/* 594 */           public Exception(String message) {
/* 595 */               super(message);
                    }

/* 595 */           public Exception(String message, Throwable cause) {
/* 596 */               super(message, cause);
                    }
                }
            }
