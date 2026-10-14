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
            
            
    public class VineFrameGrabber {
                static final  boolean $assertionsDisabled;
                private avcodec.AVCodecContext audio_c;
                private avformat.AVStream audio_st;
                private BytePointer buffer_rgb;
                private String filename;
                private Frame frame;
                private boolean frameGrabbed;
                private int[] got_frame;
                private swscale.SwsContext img_convert_ctx;

                
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
            $assertionsDisabled = !VineFrameGrabber.class.desiredAssertionStatus();
                    synchronized (SwVineFrameRecorder.LOCK) {
                        try {
                    avcodec.avcodec_register_all();
                    avformat.av_register_all();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

        public avcodec.AVCodecContext getVideoCodecContext() {
            return this.video_c;
                }

        public VineFrameGrabber(String filename) {
            this.filename = filename;
                }

        public void release() {
            if (this.pkt != null && this.pkt2 != null) {
                if (this.pkt2.size() > 0) {
                    avcodec.av_free_packet(this.pkt);
                        }
                this.pkt2 = null;
                        this.pkt = null;
                    }
            if (this.buffer_rgb != null) {
                avutil.av_free(this.buffer_rgb);
                this.buffer_rgb = null;
                    }
            if (this.picture_rgb != null) {
                avcodec.avcodec_free_frame(this.picture_rgb);
                this.picture_rgb = null;
                    }
            if (this.picture != null) {
                avcodec.avcodec_free_frame(this.picture);
                this.picture = null;
                    }
            if (this.video_c != null) {
                avcodec.avcodec_close(this.video_c);
                this.video_c = null;
                    }
            if (this.samples_frame != null) {
                avcodec.avcodec_free_frame(this.samples_frame);
               this.samples_frame = null;
                    }
           if (this.audio_c != null) {
               avcodec.avcodec_close(this.audio_c);
               this.audio_c = null;
                    }
           if (this.f110oc != null && !this.f110oc.isNull()) {
               avformat.avformat_close_input(this.f110oc);
               this.f110oc = null;
                    }
           if (this.img_convert_ctx != null) {
               swscale.sws_freeContext(this.img_convert_ctx);
               this.img_convert_ctx = null;
                    }
           this.got_frame = null;
           this.return_image = null;
           this.frameGrabbed = false;
           this.frame = null;
           this.timestamp = 0L;
           this.frameNumber = 0;
                }

       protected void finalize() throws Throwable {
           super.finalize();
           release();
                }

       public double getGamma() {
                    if (this.gamma == 0.0d) {
               return 2.2d;
                    }
                    return this.gamma;
                }

       public String getFormat() {
                    return this.f110oc == null ? this.format : this.f110oc.iformat().name().getString();
                }

       public int getImageWidth() {
                    return this.return_image == null ? this.imageWidth : this.return_image.width();
                }

       public int getImageHeight() {
                    return this.return_image == null ? this.imageHeight : this.return_image.height();
                }

       public int getAudioChannels() {
                    return this.audio_c == null ? this.audioChannels : this.audio_c.channels();
                }

       public int getPixelFormat() {
           if (this.imageMode == ImageMode.COLOR || this.imageMode == ImageMode.GRAY) {
               if (this.pixelFormat == -1) {
                            return this.imageMode == ImageMode.COLOR ? 3 : 8;
                        }
               return this.pixelFormat;
                    }
           if (this.video_c != null) {
               return this.video_c.pix_fmt();
                    }
           return this.pixelFormat;
                }

       public double getFrameRate() {
           if (this.video_st == null) {
               return this.frameRate;
                    }
           avutil.AVRational r = this.video_st.r_frame_rate();
           return ((double) r.num()) / ((double) r.den());
                }

       public int getSampleFormat() {
                    return this.audio_c == null ? this.sampleFormat : this.audio_c.sample_fmt();
                }

       public int getSampleRate() {
                    return this.audio_c == null ? this.sampleRate : this.audio_c.sample_rate();
                }

       public void setFrameNumber(int frameNumber) throws Exception {
           setTimestamp(Math.round((AudioUtils.MICROS_PER_SECOND * ((long) frameNumber)) / getFrameRate()));
                }

       public void setTimestamp(long timestamp) throws Exception {
           if (this.f110oc == null || this.video_c == null) {
               this.timestamp = timestamp;
               return;
                    }
           long timestamp2 = (timestamp * AudioUtils.MICROS_PER_SECOND) / AudioUtils.MICROS_PER_SECOND;
                    if (this.f110oc.start_time() != avutil.AV_NOPTS_VALUE) {
               timestamp2 += this.f110oc.start_time();
                    }
           int ret = avformat.avformat_seek_file(this.f110oc, -1, Long.MIN_VALUE, timestamp2, Long.MAX_VALUE, 1);
                    if (ret < 0) {
               throw new Exception("avformat_seek_file() error " + ret + ": Could not seek file to timestamp " + timestamp2 + ".");
                    }
           avcodec.avcodec_flush_buffers(this.video_c);
           if (this.audio_c != null) {
               avcodec.avcodec_flush_buffers(this.audio_c);
                    }
                    while (this.timestamp > timestamp2 && grab(false) != null) {
                    }
                    while (this.timestamp < timestamp2 && grab(false) != null) {
                    }
           this.frameGrabbed = true;
                }

       public int getLengthInFrames() {
           return (int) ((getLengthInTime() * getFrameRate()) / 1000000.0d);
                }

       public long getLengthInTime() {
           return (this.f110oc.duration() * AudioUtils.MICROS_PER_SECOND) / AudioUtils.MICROS_PER_SECOND;
                }

       public void start() throws Exception {
           this.img_convert_ctx = null;
           this.f110oc = new avformat.AVFormatContext((Pointer) null);
           this.video_c = null;
           this.audio_c = null;
           this.pkt = new avcodec.AVPacket();
           this.pkt2 = new avcodec.AVPacket();
           this.sizeof_pkt = this.pkt.sizeof();
           this.got_frame = new int[1];
           this.return_image = null;
           this.frameGrabbed = false;
           this.frame = new Frame();
           this.timestamp = 0L;
           this.frameNumber = 0;
           this.pkt2.size(0);
           avformat.AVInputFormat f = null;
           if (this.format != null && this.format.length() > 0 && (f = avformat.av_find_input_format(this.format)) == null) {
               throw new Exception("av_find_input_format() error: Could not find input format \"" + this.format + "\".");
                    }
           avutil.AVDictionary options = new avutil.AVDictionary(null);
                    if (this.frameRate > 0.0d) {
               avutil.AVRational r = avutil.av_d2q(this.frameRate, 1001000);
               avutil.av_dict_set(options, "framerate", r.num() + "/" + r.den(), 0);
                    }
           if (this.imageMode != ImageMode.RAW) {
               avutil.av_dict_set(options, "pixel_format", this.imageMode == ImageMode.COLOR ? "bgr24" : "gray8", 0);
                    }
           if (this.imageWidth > 0 && this.imageHeight > 0) {
               avutil.av_dict_set(options, "video_size", this.imageWidth + "x" + this.imageHeight, 0);
                    }
           if (this.sampleRate > 0) {
               avutil.av_dict_set(options, "sample_rate", "" + this.sampleRate, 0);
                    }
           if (this.audioChannels > 0) {
               avutil.av_dict_set(options, "channels", "" + this.audioChannels, 0);
                    }
           int ret = avformat.avformat_open_input(this.f110oc, this.filename, f, options);
                    if (ret < 0) {
               throw new Exception("avformat_open_input() error " + ret + ": Could not open input \"" + this.filename + "\". (Has setFormat() been called?)");
                    }
           avutil.av_dict_free(options);
           int ret2 = avformat.avformat_find_stream_info(this.f110oc, (PointerPointer) null);
                    if (ret2 < 0) {
               throw new Exception("avformat_find_stream_info() error " + ret2 + ": Could not find stream information.");
                    }
           avformat.av_dump_format(this.f110oc, 0, this.filename, 0);
           this.audio_st = null;
           this.video_st = null;
           int nb_streams = this.f110oc.nb_streams();
                    for (int i = 0; i < nb_streams; i++) {
               avformat.AVStream st = this.f110oc.streams(i);
               avcodec.AVCodecContext c = st.codec();
               if (this.video_st == null && c.codec_type() == 0) {
                   this.video_st = st;
                   this.video_c = c;
                        } else if (this.audio_st == null && c.codec_type() == 1) {
                   this.audio_st = st;
                   this.audio_c = c;
                        }
                    }
           if (this.video_st == null && this.audio_st == null) {
               throw new Exception("Did not find a video or audio stream inside \"" + this.filename + "\".");
                    }
           if (this.video_st != null) {
               avcodec.AVCodec codec = avcodec.avcodec_find_decoder(this.video_c.codec_id());
               if (codec == null) {
                   throw new Exception("avcodec_find_decoder() error: Unsupported video format or codec not found: " + this.video_c.codec_id() + ".");
                        }
               int ret3 = avcodec.avcodec_open2(this.video_c, codec, (PointerPointer) null);
                        if (ret3 < 0) {
                   throw new Exception("avcodec_open2() error " + ret3 + ": Could not open video codec.");
                        }
               if (this.video_c.time_base().num() > 1000 && this.video_c.time_base().den() == 1) {
                   this.video_c.time_base().den(1000);
                        }
               avutil.AVFrame aVFrameAvcodec_alloc_frame = avcodec.avcodec_alloc_frame();
                        this.picture = aVFrameAvcodec_alloc_frame;
                        if (aVFrameAvcodec_alloc_frame == null) {
                   throw new Exception("avcodec_alloc_frame() error: Could not allocate raw picture frame.");
                        }
               avutil.AVFrame aVFrameAvcodec_alloc_frame2 = avcodec.avcodec_alloc_frame();
                        this.picture_rgb = aVFrameAvcodec_alloc_frame2;
                        if (aVFrameAvcodec_alloc_frame2 == null) {
                   throw new Exception("avcodec_alloc_frame() error: Could not allocate RGB picture frame.");
                        }
               int width = getImageWidth() > 0 ? getImageWidth() : this.video_c.width();
               int height = getImageHeight() > 0 ? getImageHeight() : this.video_c.height();
                        switch (this.imageMode) {
                            case COLOR:
                            case GRAY:
                       int fmt = getPixelFormat();
                       int size = avcodec.avpicture_get_size(fmt, width, height);
                       this.buffer_rgb = new BytePointer(avutil.av_malloc(size));
                       avcodec.avpicture_fill(new avcodec.AVPicture(this.picture_rgb), this.buffer_rgb, fmt, width, height);
                       this.return_image = opencv_core.IplImage.createHeader(width, height, 8, 1);
                                break;
                            case RAW:
                       this.buffer_rgb = null;
                       this.return_image = opencv_core.IplImage.createHeader(this.video_c.width(), this.video_c.height(), 8, 1);
                                break;
                            default:
                       if (!$assertionsDisabled) {
                                    throw new AssertionError();
                                }
                                break;
                        }
                    }
           if (this.audio_st != null) {
               avcodec.AVCodec codec2 = avcodec.avcodec_find_decoder(this.audio_c.codec_id());
               if (codec2 == null) {
                   throw new Exception("avcodec_find_decoder() error: Unsupported audio format or codec not found: " + this.audio_c.codec_id() + ".");
                        }
               int ret4 = avcodec.avcodec_open2(this.audio_c, codec2, (PointerPointer) null);
                        if (ret4 < 0) {
                   throw new Exception("avcodec_open2() error " + ret4 + ": Could not open audio codec.");
                        }
               avutil.AVFrame aVFrameAvcodec_alloc_frame3 = avcodec.avcodec_alloc_frame();
                        this.samples_frame = aVFrameAvcodec_alloc_frame3;
                        if (aVFrameAvcodec_alloc_frame3 == null) {
                   throw new Exception("avcodec_alloc_frame() error: Could not allocate audio frame.");
                        }
                    }
                }

       public void stop() throws Exception {
           release();
                }

       public void trigger() throws Exception {
           if (this.f110oc == null || this.f110oc.isNull()) {
               throw new Exception("Could not trigger: No AVFormatContext. (Has start() been called?)");
                    }
           if (this.pkt2.size() > 0) {
               this.pkt2.size(0);
               avcodec.av_free_packet(this.pkt);
                    }
           for (int i = 0; i < this.numBuffers + 1 && avformat.av_read_frame(this.f110oc, this.pkt) >= 0; i++) {
               avcodec.av_free_packet(this.pkt);
                    }
                }

       private void processImage() throws Exception {
                    switch (this.imageMode) {
                        case COLOR:
                        case GRAY:
                   if (this.deinterlace) {
                       avcodec.AVPicture p = new avcodec.AVPicture(this.picture);
                       avcodec.avpicture_deinterlace(p, p, this.video_c.pix_fmt(), this.video_c.width(), this.video_c.height());
                            }
                   this.img_convert_ctx = swscale.sws_getCachedContext(this.img_convert_ctx, this.video_c.width(), this.video_c.height(), this.video_c.pix_fmt(), getImageWidth(), getImageHeight(), getPixelFormat(), 2, (swscale.SwsFilter) null, (swscale.SwsFilter) null, (DoublePointer) null);
                   if (this.img_convert_ctx == null) {
                       throw new Exception("sws_getCachedContext() error: Cannot initialize the conversion context.");
                            }
                   swscale.sws_scale(this.img_convert_ctx, new PointerPointer(this.picture), this.picture.linesize(), 0, this.video_c.height(), new PointerPointer(this.picture_rgb), this.picture_rgb.linesize());
                   this.return_image.imageData(this.buffer_rgb);
                   this.return_image.widthStep(this.picture_rgb.linesize(0));
                            break;
                        case RAW:
                   if (!$assertionsDisabled && (this.video_c.width() != this.return_image.width() || this.video_c.height() != this.return_image.height())) {
                                throw new AssertionError();
                            }
                   this.return_image.imageData(this.picture.data(0));
                   this.return_image.widthStep(this.picture.linesize(0));
                            break;
                        default:
                   if (!$assertionsDisabled) {
                                throw new AssertionError();
                            }
                            break;
                    }
           this.return_image.imageSize(this.return_image.height() * this.return_image.widthStep());
           this.return_image.nChannels(this.return_image.widthStep() / this.return_image.width());
                }

       public opencv_core.IplImage grab() throws Exception {
           Frame frame = grabFrame(true, false);
           if (frame != null) {
                        return frame.image;
                    }
                    return null;
                }

       public opencv_core.IplImage grab(boolean processImage) throws Exception {
           Frame frame = grabFrame(processImage, false);
           if (frame != null) {
                        return frame.image;
                    }
                    return null;
                }

       public Frame grabFrame() throws Exception {
           return grabFrame(true, true);
                }

       private Frame grabFrame(boolean processImage, boolean doAudio) throws Exception {
           this.frame.image = null;
           this.frame.samples = null;
           if (this.frameGrabbed) {
               this.frameGrabbed = false;
               if (processImage) {
                   processImage();
                        }
               this.frame.image = this.return_image;
               return this.frame;
                    }
           if (this.f110oc == null || this.f110oc.isNull()) {
               throw new Exception("Could not grab: No AVFormatContext. (Has start() been called?)");
                    }
           boolean done = false;
           while (!done) {
               if (this.pkt2.size() <= 0 && avformat.av_read_frame(this.f110oc, this.pkt) < 0) {
                   if (this.video_st != null) {
                       this.pkt.stream_index(this.video_st.index());
                       this.pkt.data(null);
                       this.pkt.size(0);
                            } else {
                       return null;
                            }
                        }
               if (this.video_st != null && this.pkt.stream_index() == this.video_st.index()) {
                   if (avcodec.avcodec_decode_video2(this.video_c, this.picture, this.got_frame, this.pkt) >= 0 && this.got_frame[0] != 0) {
                       long pts = avutil.av_frame_get_best_effort_timestamp(this.picture);
                       avutil.AVRational time_base = this.video_st.time_base();
                       this.timestamp = ((AudioUtils.MICROS_PER_SECOND * pts) * ((long) time_base.num())) / ((long) time_base.den());
                       this.frameNumber = (int) ((this.timestamp * getFrameRate()) / 1000000.0d);
                       if (processImage) {
                           processImage();
                                }
                       done = true;
                       this.frame.image = this.return_image;
                            } else if (this.pkt.data() == null && this.pkt.size() == 0) {
                       return null;
                            }
                        } else if (doAudio && this.audio_st != null && this.pkt.stream_index() == this.audio_st.index()) {
                   if (this.pkt2.size() <= 0) {
                       BytePointer.memcpy(this.pkt2, this.pkt, this.sizeof_pkt);
                            }
                   avcodec.avcodec_get_frame_defaults(this.samples_frame);
                   int len = avcodec.avcodec_decode_audio4(this.audio_c, this.samples_frame, this.got_frame, this.pkt2);
                   if (len <= 0) {
                       this.pkt2.size(0);
                            } else {
                       this.pkt2.data(this.pkt2.data().position(len));
                       this.pkt2.size(this.pkt2.size() - len);
                       if (this.got_frame[0] != 0) {
                           long pts2 = avutil.av_frame_get_best_effort_timestamp(this.samples_frame);
                           avutil.AVRational time_base2 = this.audio_st.time_base();
                           this.timestamp = ((AudioUtils.MICROS_PER_SECOND * pts2) * ((long) time_base2.num())) / ((long) time_base2.den());
                           done = true;
                           int sample_format = this.samples_frame.format();
                           int planes = avutil.av_sample_fmt_is_planar(sample_format) != 0 ? this.samples_frame.channels() : 1;
                           int data_size = avutil.av_samples_get_buffer_size((IntPointer) null, this.audio_c.channels(), this.samples_frame.nb_samples(), this.audio_c.sample_fmt(), 1) / planes;
                           if (this.samples_buf == null || this.samples_buf.length != planes) {
                               this.samples_ptr = new BytePointer[planes];
                               this.samples_buf = new Buffer[planes];
                                    }
                           this.frame.samples = this.samples_buf;
                           int sample_size = data_size / avutil.av_get_bytes_per_sample(sample_format);
                                    for (int i = 0; i < planes; i++) {
                               BytePointer p = this.samples_frame.data(i);
                               if (!p.equals(this.samples_ptr[i]) || this.samples_ptr[i].capacity() < data_size) {
                                   this.samples_ptr[i] = p.capacity(data_size);
                                   ByteBuffer b = p.asBuffer();
                                            switch (sample_format) {
                                                case 0:
                                                case 5:
                                           this.samples_buf[i] = b;
                                                    break;
                                                case 1:
                                                case 6:
                                           this.samples_buf[i] = b.asShortBuffer();
                                                    break;
                                                case 2:
                                                case 7:
                                           this.samples_buf[i] = b.asIntBuffer();
                                                    break;
                                                case 3:
                                                case 8:
                                           this.samples_buf[i] = b.asFloatBuffer();
                                                    break;
                                                case 4:
                                                case 9:
                                           this.samples_buf[i] = b.asDoubleBuffer();
                                                    break;
                                                default:
                                           if (!$assertionsDisabled) {
                                                        throw new AssertionError();
                                                    }
                                                    break;
                                            }
                                        }
                               this.samples_buf[i].position(0).limit(sample_size);
                                    }
                                }
                            }
                        }
               if (this.pkt2.size() <= 0) {
                   avcodec.av_free_packet(this.pkt);
                        }
                    }
           return this.frame;
                }

       public void setFormat(String format) {
           this.format = format;
                }

       public void setFrameRate(double frameRate) {
           this.frameRate = frameRate;
                }

       public long getTimestamp() {
           return this.timestamp;
                }

       public static class Exception extends java.lang.Exception {
           public Exception(String message) {
               super(message);
                    }

           public Exception(String message, Throwable cause) {
               super(message, cause);
                    }
                }
            }
