            package com.googlecode.javacv.cpp;

            import com.googlecode.javacpp.BytePointer;
            import com.googlecode.javacpp.DoublePointer;
            import com.googlecode.javacpp.FunctionPointer;
            import com.googlecode.javacpp.IntPointer;
            import com.googlecode.javacpp.Loader;
            import com.googlecode.javacpp.LongPointer;
            import com.googlecode.javacpp.Pointer;
            import com.googlecode.javacpp.PointerPointer;
            import com.googlecode.javacpp.annotation.ByPtrPtr;
            import com.googlecode.javacpp.annotation.ByVal;
            import com.googlecode.javacpp.annotation.Cast;
            import com.googlecode.javacpp.annotation.Const;
            import com.googlecode.javacpp.annotation.MemberGetter;
            import com.googlecode.javacpp.annotation.Opaque;
            import com.googlecode.javacpp.annotation.Platform;
            import com.googlecode.javacpp.annotation.Properties;
            import java.nio.ByteBuffer;
            import java.nio.IntBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @Properties(inherit = {avformat.class, postproc.class, swresample.class, swscale.class}, value = {@Platform(cinclude = {"<libavfilter/avfilter.h>", "<libavfilter/buffersink.h>", "<libavfilter/buffersrc.h>"}, link = {"avfilter@.3"}), @Platform(preload = {"avfilter-3"}, value = {"windows"})})
/* 37 */    public class avfilter {
                public static final int AVFILTER_ALIGN = 16;
                public static final int AVFILTER_AUTO_CONVERT_ALL = 0;
                public static final int AVFILTER_AUTO_CONVERT_NONE = -1;
                public static final int AVFILTER_CMD_FLAG_FAST = 2;
                public static final int AVFILTER_CMD_FLAG_ONE = 1;
                public static final int AVFILTER_FLAG_DYNAMIC_INPUTS = 1;
                public static final int AVFILTER_FLAG_DYNAMIC_OUTPUTS = 2;
                public static final int AVFILTER_FLAG_SLICE_THREADS = 4;
                public static final int AVFILTER_FLAG_SUPPORT_TIMELINE = 196608;
                public static final int AVFILTER_FLAG_SUPPORT_TIMELINE_GENERIC = 65536;
                public static final int AVFILTER_FLAG_SUPPORT_TIMELINE_INTERNAL = 131072;
                public static final int AVFILTER_THREAD_SLICE = 1;
                public static final int AV_BUFFERSINK_FLAG_NO_REQUEST = 2;
                public static final int AV_BUFFERSINK_FLAG_PEEK = 1;
                public static final int AV_BUFFERSRC_FLAG_KEEP_REF = 8;
                public static final int AV_BUFFERSRC_FLAG_NO_CHECK_FORMAT = 1;
                public static final int AV_BUFFERSRC_FLAG_NO_COPY = 2;
                public static final int AV_BUFFERSRC_FLAG_PUSH = 4;
                public static final int AV_PERM_ALIGN = 64;
                public static final int AV_PERM_NEG_LINESIZES = 32;
                public static final int AV_PERM_PRESERVE = 4;
                public static final int AV_PERM_READ = 1;
                public static final int AV_PERM_REUSE = 8;
                public static final int AV_PERM_REUSE2 = 16;
                public static final int AV_PERM_WRITE = 2;

                public static native AVABufferSinkParams av_abuffersink_params_alloc();

                @Deprecated
                public static native int av_buffersink_get_buffer_ref(AVFilterContext aVFilterContext, @Cast({"AVFilterBufferRef**"}) PointerPointer pointerPointer, int i);

                @Deprecated
                public static native int av_buffersink_get_buffer_ref(AVFilterContext aVFilterContext, @ByPtrPtr AVFilterBufferRef aVFilterBufferRef, int i);

                public static native int av_buffersink_get_frame(AVFilterContext aVFilterContext, avutil.AVFrame aVFrame);

                public static native int av_buffersink_get_frame_flags(AVFilterContext aVFilterContext, avutil.AVFrame aVFrame, int i);

                @ByVal
                public static native avutil.AVRational av_buffersink_get_frame_rate(AVFilterContext aVFilterContext);

                public static native int av_buffersink_get_samples(AVFilterContext aVFilterContext, avutil.AVFrame aVFrame, int i);

                public static native AVBufferSinkParams av_buffersink_params_alloc();

                @Deprecated
                public static native int av_buffersink_poll_frame(AVFilterContext aVFilterContext);

                @Deprecated
                public static native int av_buffersink_read(AVFilterContext aVFilterContext, @Cast({"AVFilterBufferRef**"}) PointerPointer pointerPointer);

                @Deprecated
                public static native int av_buffersink_read(AVFilterContext aVFilterContext, @ByPtrPtr AVFilterBufferRef aVFilterBufferRef);

                @Deprecated
                public static native int av_buffersink_read_samples(AVFilterContext aVFilterContext, @Cast({"AVFilterBufferRef**"}) PointerPointer pointerPointer, int i);

                @Deprecated
                public static native int av_buffersink_read_samples(AVFilterContext aVFilterContext, @ByPtrPtr AVFilterBufferRef aVFilterBufferRef, int i);

                public static native void av_buffersink_set_frame_size(AVFilterContext aVFilterContext, @Cast({"unsigned"}) int i);

                public static native int av_buffersrc_add_frame(AVFilterContext aVFilterContext, avutil.AVFrame aVFrame);

                public static native int av_buffersrc_add_frame_flags(AVFilterContext aVFilterContext, avutil.AVFrame aVFrame, int i);

                public static native int av_buffersrc_add_ref(AVFilterContext aVFilterContext, AVFilterBufferRef aVFilterBufferRef, int i);

                @Deprecated
                public static native int av_buffersrc_buffer(AVFilterContext aVFilterContext, AVFilterBufferRef aVFilterBufferRef);

                @Cast({"unsigned"})
                public static native int av_buffersrc_get_nb_failed_requests(AVFilterContext aVFilterContext);

                public static native int av_buffersrc_write_frame(AVFilterContext aVFilterContext, @Const avutil.AVFrame aVFrame);

                @Cast({"AVFilter**"})
                @Deprecated
                public static native PointerPointer av_filter_next(@Cast({"AVFilter**"}) PointerPointer pointerPointer);

                @ByPtrPtr
                @Deprecated
                public static native AVFilter av_filter_next(@ByPtrPtr AVFilter aVFilter);

                public static native int avfilter_config_links(AVFilterContext aVFilterContext);

                @Cast({"const char*"})
                public static native BytePointer avfilter_configuration();

                @Deprecated
                public static native int avfilter_copy_buf_props(avutil.AVFrame aVFrame, @Const AVFilterBufferRef aVFilterBufferRef);

                @Deprecated
                public static native void avfilter_copy_buffer_ref_props(AVFilterBufferRef aVFilterBufferRef, AVFilterBufferRef aVFilterBufferRef2);

                @Deprecated
                public static native int avfilter_copy_frame_props(AVFilterBufferRef aVFilterBufferRef, @Const avutil.AVFrame aVFrame);

                public static native void avfilter_free(AVFilterContext aVFilterContext);

                @Deprecated
                public static native AVFilterBufferRef avfilter_get_audio_buffer_ref_from_arrays(@ByPtrPtr @Cast({"uint8_t**"}) BytePointer bytePointer, int i, int i2, int i3, @Cast({"AVSampleFormat"}) int i4, @Cast({"uint64_t"}) long j);

                @Deprecated
                public static native AVFilterBufferRef avfilter_get_audio_buffer_ref_from_arrays(@Cast({"uint8_t**"}) PointerPointer pointerPointer, int i, int i2, int i3, @Cast({"AVSampleFormat"}) int i4, @Cast({"uint64_t"}) long j);

                @Deprecated
                public static native AVFilterBufferRef avfilter_get_audio_buffer_ref_from_arrays(@ByPtrPtr @Cast({"uint8_t**"}) ByteBuffer byteBuffer, int i, int i2, int i3, @Cast({"AVSampleFormat"}) int i4, @Cast({"uint64_t"}) long j);

                @Deprecated
                public static native AVFilterBufferRef avfilter_get_audio_buffer_ref_from_arrays(@ByPtrPtr @Cast({"uint8_t**"}) byte[] bArr, int i, int i2, int i3, @Cast({"AVSampleFormat"}) int i4, @Cast({"uint64_t"}) long j);

                @Deprecated
                public static native AVFilterBufferRef avfilter_get_audio_buffer_ref_from_arrays_channels(@ByPtrPtr @Cast({"uint8_t**"}) BytePointer bytePointer, int i, int i2, int i3, @Cast({"AVSampleFormat"}) int i4, int i5, @Cast({"uint64_t"}) long j);

                @Deprecated
                public static native AVFilterBufferRef avfilter_get_audio_buffer_ref_from_arrays_channels(@Cast({"uint8_t**"}) PointerPointer pointerPointer, int i, int i2, int i3, @Cast({"AVSampleFormat"}) int i4, int i5, @Cast({"uint64_t"}) long j);

                @Deprecated
                public static native AVFilterBufferRef avfilter_get_audio_buffer_ref_from_arrays_channels(@ByPtrPtr @Cast({"uint8_t**"}) ByteBuffer byteBuffer, int i, int i2, int i3, @Cast({"AVSampleFormat"}) int i4, int i5, @Cast({"uint64_t"}) long j);

                @Deprecated
                public static native AVFilterBufferRef avfilter_get_audio_buffer_ref_from_arrays_channels(@ByPtrPtr @Cast({"uint8_t**"}) byte[] bArr, int i, int i2, int i3, @Cast({"AVSampleFormat"}) int i4, int i5, @Cast({"uint64_t"}) long j);

                public static native AVFilter avfilter_get_by_name(@Cast({"const char*"}) BytePointer bytePointer);

                public static native AVFilter avfilter_get_by_name(String str);

                @Const
                public static native avutil.AVClass avfilter_get_class();

                @Deprecated
                public static native AVFilterBufferRef avfilter_get_video_buffer_ref_from_arrays(@ByPtrPtr @Cast({"uint8_t*const*"}) BytePointer bytePointer, @Const IntPointer intPointer, int i, int i2, int i3, @Cast({"AVPixelFormat"}) int i4);

                @Deprecated
                public static native AVFilterBufferRef avfilter_get_video_buffer_ref_from_arrays(@Cast({"uint8_t*const*"}) PointerPointer pointerPointer, @Const IntPointer intPointer, int i, int i2, int i3, @Cast({"AVPixelFormat"}) int i4);

                @Deprecated
                public static native AVFilterBufferRef avfilter_get_video_buffer_ref_from_arrays(@ByPtrPtr @Cast({"uint8_t*const*"}) ByteBuffer byteBuffer, @Const IntBuffer intBuffer, int i, int i2, int i3, @Cast({"AVPixelFormat"}) int i4);

                @Deprecated
                public static native AVFilterBufferRef avfilter_get_video_buffer_ref_from_arrays(@ByPtrPtr @Cast({"uint8_t*const*"}) byte[] bArr, @Const int[] iArr, int i, int i2, int i3, @Cast({"AVPixelFormat"}) int i4);

                @Deprecated
                public static native int avfilter_graph_add_filter(AVFilterGraph aVFilterGraph, AVFilterContext aVFilterContext);

                public static native AVFilterGraph avfilter_graph_alloc();

                public static native AVFilterContext avfilter_graph_alloc_filter(AVFilterGraph aVFilterGraph, @Const AVFilter aVFilter, @Cast({"const char*"}) BytePointer bytePointer);

                public static native AVFilterContext avfilter_graph_alloc_filter(AVFilterGraph aVFilterGraph, @Const AVFilter aVFilter, String str);

                public static native int avfilter_graph_config(AVFilterGraph aVFilterGraph, Pointer pointer);

                public static native int avfilter_graph_create_filter(@Cast({"AVFilterContext**"}) PointerPointer pointerPointer, AVFilter aVFilter, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, Pointer pointer, AVFilterGraph aVFilterGraph);

                public static native int avfilter_graph_create_filter(@ByPtrPtr AVFilterContext aVFilterContext, AVFilter aVFilter, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, Pointer pointer, AVFilterGraph aVFilterGraph);

                public static native int avfilter_graph_create_filter(@ByPtrPtr AVFilterContext aVFilterContext, AVFilter aVFilter, String str, String str2, Pointer pointer, AVFilterGraph aVFilterGraph);

                @Cast({"char*"})
                public static native BytePointer avfilter_graph_dump(AVFilterGraph aVFilterGraph, @Cast({"const char*"}) BytePointer bytePointer);

                @Cast({"char*"})
                public static native ByteBuffer avfilter_graph_dump(AVFilterGraph aVFilterGraph, String str);

                public static native void avfilter_graph_free(@Cast({"AVFilterGraph**"}) PointerPointer pointerPointer);

                public static native void avfilter_graph_free(@ByPtrPtr AVFilterGraph aVFilterGraph);

                public static native AVFilterContext avfilter_graph_get_filter(AVFilterGraph aVFilterGraph, @Cast({"char*"}) BytePointer bytePointer);

                public static native AVFilterContext avfilter_graph_get_filter(AVFilterGraph aVFilterGraph, @Cast({"char*"}) ByteBuffer byteBuffer);

                public static native AVFilterContext avfilter_graph_get_filter(AVFilterGraph aVFilterGraph, @Cast({"char*"}) byte[] bArr);

                @Deprecated
                public static native int avfilter_graph_parse(AVFilterGraph aVFilterGraph, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"AVFilterInOut**"}) PointerPointer pointerPointer, @Cast({"AVFilterInOut**"}) PointerPointer pointerPointer2, Pointer pointer);

                @Deprecated
                public static native int avfilter_graph_parse(AVFilterGraph aVFilterGraph, @Cast({"const char*"}) BytePointer bytePointer, @ByPtrPtr AVFilterInOut aVFilterInOut, @ByPtrPtr AVFilterInOut aVFilterInOut2, Pointer pointer);

                @Deprecated
                public static native int avfilter_graph_parse(AVFilterGraph aVFilterGraph, String str, @ByPtrPtr AVFilterInOut aVFilterInOut, @ByPtrPtr AVFilterInOut aVFilterInOut2, Pointer pointer);

                public static native int avfilter_graph_parse2(AVFilterGraph aVFilterGraph, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"AVFilterInOut**"}) PointerPointer pointerPointer, @Cast({"AVFilterInOut**"}) PointerPointer pointerPointer2);

                public static native int avfilter_graph_parse2(AVFilterGraph aVFilterGraph, @Cast({"const char*"}) BytePointer bytePointer, @ByPtrPtr AVFilterInOut aVFilterInOut, @ByPtrPtr AVFilterInOut aVFilterInOut2);

                public static native int avfilter_graph_parse2(AVFilterGraph aVFilterGraph, String str, @ByPtrPtr AVFilterInOut aVFilterInOut, @ByPtrPtr AVFilterInOut aVFilterInOut2);

                public static native int avfilter_graph_parse_ptr(AVFilterGraph aVFilterGraph, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"AVFilterInOut**"}) PointerPointer pointerPointer, @Cast({"AVFilterInOut**"}) PointerPointer pointerPointer2, Pointer pointer);

                public static native int avfilter_graph_parse_ptr(AVFilterGraph aVFilterGraph, @Cast({"const char*"}) BytePointer bytePointer, @ByPtrPtr AVFilterInOut aVFilterInOut, @ByPtrPtr AVFilterInOut aVFilterInOut2, Pointer pointer);

                public static native int avfilter_graph_parse_ptr(AVFilterGraph aVFilterGraph, String str, @ByPtrPtr AVFilterInOut aVFilterInOut, @ByPtrPtr AVFilterInOut aVFilterInOut2, Pointer pointer);

                public static native int avfilter_graph_queue_command(AVFilterGraph aVFilterGraph, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, @Cast({"const char*"}) BytePointer bytePointer3, int i, double d);

                public static native int avfilter_graph_queue_command(AVFilterGraph aVFilterGraph, String str, String str2, String str3, int i, double d);

                public static native int avfilter_graph_request_oldest(AVFilterGraph aVFilterGraph);

                public static native int avfilter_graph_send_command(AVFilterGraph aVFilterGraph, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, @Cast({"const char*"}) BytePointer bytePointer3, @Cast({"char*"}) BytePointer bytePointer4, int i, int i2);

                public static native int avfilter_graph_send_command(AVFilterGraph aVFilterGraph, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, @Cast({"const char*"}) BytePointer bytePointer3, @Cast({"char*"}) ByteBuffer byteBuffer, int i, int i2);

                public static native int avfilter_graph_send_command(AVFilterGraph aVFilterGraph, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, @Cast({"const char*"}) BytePointer bytePointer3, @Cast({"char*"}) byte[] bArr, int i, int i2);

                public static native int avfilter_graph_send_command(AVFilterGraph aVFilterGraph, String str, String str2, String str3, @Cast({"char*"}) BytePointer bytePointer, int i, int i2);

                public static native int avfilter_graph_send_command(AVFilterGraph aVFilterGraph, String str, String str2, String str3, @Cast({"char*"}) ByteBuffer byteBuffer, int i, int i2);

                public static native int avfilter_graph_send_command(AVFilterGraph aVFilterGraph, String str, String str2, String str3, @Cast({"char*"}) byte[] bArr, int i, int i2);

                public static native void avfilter_graph_set_auto_convert(AVFilterGraph aVFilterGraph, @Cast({"unsigned"}) int i);

                public static native int avfilter_init_dict(AVFilterContext aVFilterContext, @Cast({"AVDictionary**"}) PointerPointer pointerPointer);

                public static native int avfilter_init_dict(AVFilterContext aVFilterContext, @ByPtrPtr avutil.AVDictionary aVDictionary);

                @Deprecated
                public static native int avfilter_init_filter(AVFilterContext aVFilterContext, @Cast({"const char*"}) BytePointer bytePointer, Pointer pointer);

                @Deprecated
                public static native int avfilter_init_filter(AVFilterContext aVFilterContext, String str, Pointer pointer);

                public static native int avfilter_init_str(AVFilterContext aVFilterContext, @Cast({"const char*"}) BytePointer bytePointer);

                public static native int avfilter_init_str(AVFilterContext aVFilterContext, String str);

                public static native AVFilterInOut avfilter_inout_alloc();

                public static native void avfilter_inout_free(@Cast({"AVFilterInOut**"}) PointerPointer pointerPointer);

                public static native void avfilter_inout_free(@ByPtrPtr AVFilterInOut aVFilterInOut);

                public static native int avfilter_insert_filter(AVFilterLink aVFilterLink, AVFilterContext aVFilterContext, @Cast({"unsigned"}) int i, @Cast({"unsigned"}) int i2);

                @Cast({"const char*"})
                public static native BytePointer avfilter_license();

                public static native int avfilter_link(AVFilterContext aVFilterContext, @Cast({"unsigned"}) int i, AVFilterContext aVFilterContext2, @Cast({"unsigned"}) int i2);

                public static native void avfilter_link_free(@Cast({"AVFilterLink**"}) PointerPointer pointerPointer);

                public static native void avfilter_link_free(@ByPtrPtr AVFilterLink aVFilterLink);

                public static native int avfilter_link_get_channels(AVFilterLink aVFilterLink);

                public static native void avfilter_link_set_closed(AVFilterLink aVFilterLink, int i);

                @Const
                public static native AVFilter avfilter_next(@Const AVFilter aVFilter);

                @Deprecated
                public static native int avfilter_open(@Cast({"AVFilterContext**"}) PointerPointer pointerPointer, AVFilter aVFilter, @Cast({"const char*"}) BytePointer bytePointer);

                @Deprecated
                public static native int avfilter_open(@ByPtrPtr AVFilterContext aVFilterContext, AVFilter aVFilter, @Cast({"const char*"}) BytePointer bytePointer);

                @Deprecated
                public static native int avfilter_open(@ByPtrPtr AVFilterContext aVFilterContext, AVFilter aVFilter, String str);

                public static native int avfilter_pad_count(@Const AVFilterPad aVFilterPad);

                @Cast({"const char*"})
                public static native BytePointer avfilter_pad_get_name(@Const AVFilterPad aVFilterPad, int i);

                @Cast({"AVMediaType"})
                public static native int avfilter_pad_get_type(@Const AVFilterPad aVFilterPad, int i);

                public static native int avfilter_process_command(AVFilterContext aVFilterContext, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, @Cast({"char*"}) BytePointer bytePointer3, int i, int i2);

                public static native int avfilter_process_command(AVFilterContext aVFilterContext, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, @Cast({"char*"}) ByteBuffer byteBuffer, int i, int i2);

                public static native int avfilter_process_command(AVFilterContext aVFilterContext, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, @Cast({"char*"}) byte[] bArr, int i, int i2);

                public static native int avfilter_process_command(AVFilterContext aVFilterContext, String str, String str2, @Cast({"char*"}) BytePointer bytePointer, int i, int i2);

                public static native int avfilter_process_command(AVFilterContext aVFilterContext, String str, String str2, @Cast({"char*"}) ByteBuffer byteBuffer, int i, int i2);

                public static native int avfilter_process_command(AVFilterContext aVFilterContext, String str, String str2, @Cast({"char*"}) byte[] bArr, int i, int i2);

                @Deprecated
                public static native AVFilterBufferRef avfilter_ref_buffer(AVFilterBufferRef aVFilterBufferRef, int i);

                @Deprecated
                public static native int avfilter_ref_get_channels(AVFilterBufferRef aVFilterBufferRef);

                public static native int avfilter_register(AVFilter aVFilter);

                public static native void avfilter_register_all();

                @Deprecated
                public static native void avfilter_uninit();

                @Deprecated
                public static native void avfilter_unref_buffer(AVFilterBufferRef aVFilterBufferRef);

                @Deprecated
                public static native void avfilter_unref_bufferp(@Cast({"AVFilterBufferRef**"}) PointerPointer pointerPointer);

                @Deprecated
                public static native void avfilter_unref_bufferp(@ByPtrPtr AVFilterBufferRef aVFilterBufferRef);

                @Cast({"unsigned"})
                public static native int avfilter_version();

                static {
/* 39 */            Loader.load();
                }

                @Opaque
/* 103 */       public static class AVFilterFormats extends Pointer {
/* 104 */           public AVFilterFormats() {
                    }

/* 105 */           public AVFilterFormats(Pointer p) {
/* 106 */               super(p);
                    }
                }

/* 113 */       public static class AVFilterBuffer extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    @Cast({"uint8_t*"})
                    public native BytePointer data(int i);

                    @MemberGetter
                    @Cast({"uint8_t**"})
                    public native PointerPointer data();

                    public native AVFilterBuffer data(int i, BytePointer bytePointer);

                    @Cast({"uint8_t*"})
                    public native BytePointer extended_data(int i);

                    @MemberGetter
                    @Cast({"uint8_t**"})
                    public native PointerPointer extended_data();

                    public native AVFilterBuffer extended_data(int i, BytePointer bytePointer);

                    public native int format();

                    public native AVFilterBuffer format(int i);

                    public native Free_AVFilterBuffer free();

                    public native AVFilterBuffer free(Free_AVFilterBuffer free_AVFilterBuffer);

                    /* JADX INFO: renamed from: h */
                    public native int m2395h();

                    /* JADX INFO: renamed from: h */
                    public native AVFilterBuffer m2396h(int i);

                    public native int linesize(int i);

                    @MemberGetter
                    public native IntPointer linesize();

                    public native AVFilterBuffer linesize(int i, int i2);

                    public native Pointer priv();

                    public native AVFilterBuffer priv(Pointer pointer);

                    @Cast({"unsigned"})
                    public native int refcount();

                    public native AVFilterBuffer refcount(int i);

                    /* JADX INFO: renamed from: w */
                    public native int m2397w();

                    /* JADX INFO: renamed from: w */
                    public native AVFilterBuffer m2398w(int i);


                    static {
/* 116 */               Loader.load();
                    }

/* 116 */           public AVFilterBuffer() {
                        allocate();
                    }

/* 117 */           public AVFilterBuffer(int size) {
                        allocateArray(size);
                    }

/* 118 */           public AVFilterBuffer(Pointer p) {
/* 119 */               super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 122 */           public AVFilterBuffer position(int position) {
/* 123 */               return (AVFilterBuffer) super.position(position);
                    }

/* 157 */           public static class Free_AVFilterBuffer extends FunctionPointer {
                        private native void allocate();

                        public native void call(AVFilterBuffer aVFilterBuffer);

                        static {
/* 159 */                   Loader.load();
                        }

/* 159 */               public Free_AVFilterBuffer(Pointer p) {
/* 160 */                   super(p);
                        }

/* 160 */               protected Free_AVFilterBuffer() {
                            allocate();
                        }
                    }
                }

/* 196 */       public static class AVFilterBufferRefAudioProps extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    @Cast({"uint64_t"})
                    public native long channel_layout();

                    public native AVFilterBufferRefAudioProps channel_layout(long j);

                    public native int channels();

                    public native AVFilterBufferRefAudioProps channels(int i);

                    public native int nb_samples();

                    public native AVFilterBufferRefAudioProps nb_samples(int i);

                    public native int sample_rate();

                    public native AVFilterBufferRefAudioProps sample_rate(int i);


                    static {
/* 199 */               Loader.load();
                    }

/* 199 */           public AVFilterBufferRefAudioProps() {
                        allocate();
                    }

/* 200 */           public AVFilterBufferRefAudioProps(int size) {
                        allocateArray(size);
                    }

/* 201 */           public AVFilterBufferRefAudioProps(Pointer p) {
/* 202 */               super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 205 */           public AVFilterBufferRefAudioProps position(int position) {
/* 206 */               return (AVFilterBufferRefAudioProps) super.position(position);
                    }
                }

/* 222 */       public static class AVFilterBufferRefVideoProps extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    /* JADX INFO: renamed from: h */
                    public native int m2399h();

                    /* JADX INFO: renamed from: h */
                    public native AVFilterBufferRefVideoProps m2400h(int i);

                    public native int interlaced();

                    public native AVFilterBufferRefVideoProps interlaced(int i);

                    public native int key_frame();

                    public native AVFilterBufferRefVideoProps key_frame(int i);

                    @Cast({"AVPictureType"})
                    public native int pict_type();

                    public native AVFilterBufferRefVideoProps pict_type(int i);

                    public native BytePointer qp_table();

                    public native AVFilterBufferRefVideoProps qp_table(BytePointer bytePointer);

                    public native int qp_table_linesize();

                    public native AVFilterBufferRefVideoProps qp_table_linesize(int i);

                    public native int qp_table_size();

                    public native AVFilterBufferRefVideoProps qp_table_size(int i);

                    public native AVFilterBufferRefVideoProps sample_aspect_ratio(avutil.AVRational aVRational);

                    @ByVal
                    public native avutil.AVRational sample_aspect_ratio();

                    public native int top_field_first();

                    public native AVFilterBufferRefVideoProps top_field_first(int i);

                    /* JADX INFO: renamed from: w */
                    public native int m2401w();

                    /* JADX INFO: renamed from: w */
                    public native AVFilterBufferRefVideoProps m2402w(int i);


                    static {
/* 225 */               Loader.load();
                    }

/* 225 */           public AVFilterBufferRefVideoProps() {
                        allocate();
                    }

/* 226 */           public AVFilterBufferRefVideoProps(int size) {
                        allocateArray(size);
                    }

/* 227 */           public AVFilterBufferRefVideoProps(Pointer p) {
/* 228 */               super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 231 */           public AVFilterBufferRefVideoProps position(int position) {
/* 232 */               return (AVFilterBufferRefVideoProps) super.position(position);
                    }
                }

/* 263 */       public static class AVFilterBufferRef extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native AVFilterBufferRef audio(AVFilterBufferRefAudioProps aVFilterBufferRefAudioProps);

                    public native AVFilterBufferRefAudioProps audio();

                    public native AVFilterBuffer buf();

                    public native AVFilterBufferRef buf(AVFilterBuffer aVFilterBuffer);

                    @Cast({"uint8_t*"})
                    public native BytePointer data(int i);

                    @MemberGetter
                    @Cast({"uint8_t**"})
                    public native PointerPointer data();

                    public native AVFilterBufferRef data(int i, BytePointer bytePointer);

                    @Cast({"uint8_t*"})
                    public native BytePointer extended_data(int i);

                    @MemberGetter
                    @Cast({"uint8_t**"})
                    public native PointerPointer extended_data();

                    public native AVFilterBufferRef extended_data(int i, BytePointer bytePointer);

                    public native int format();

                    public native AVFilterBufferRef format(int i);

                    public native int linesize(int i);

                    @MemberGetter
                    public native IntPointer linesize();

                    public native AVFilterBufferRef linesize(int i, int i2);

                    public native AVFilterBufferRef metadata(avutil.AVDictionary aVDictionary);

                    public native avutil.AVDictionary metadata();

                    public native int perms();

                    public native AVFilterBufferRef perms(int i);

                    public native long pos();

                    public native AVFilterBufferRef pos(long j);

                    public native long pts();

                    public native AVFilterBufferRef pts(long j);

                    @Cast({"AVMediaType"})
                    public native int type();

                    public native AVFilterBufferRef type(int i);

                    public native AVFilterBufferRef video(AVFilterBufferRefVideoProps aVFilterBufferRefVideoProps);

                    public native AVFilterBufferRefVideoProps video();


                    static {
/* 266 */               Loader.load();
                    }

/* 266 */           public AVFilterBufferRef() {
                        allocate();
                    }

/* 267 */           public AVFilterBufferRef(int size) {
                        allocateArray(size);
                    }

/* 268 */           public AVFilterBufferRef(Pointer p) {
/* 269 */               super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 272 */           public AVFilterBufferRef position(int position) {
/* 273 */               return (AVFilterBufferRef) super.position(position);
                    }
                }

/* 380 */       public static class AVFilterPad extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native Config_props_AVFilterLink config_props();

                    public native AVFilterPad config_props(Config_props_AVFilterLink config_props_AVFilterLink);

                    public native Draw_slice_AVFilterLink_int_int_int draw_slice();

                    public native AVFilterPad draw_slice(Draw_slice_AVFilterLink_int_int_int draw_slice_AVFilterLink_int_int_int);

                    public native End_frame_AVFilterLink end_frame();

                    public native AVFilterPad end_frame(End_frame_AVFilterLink end_frame_AVFilterLink);

                    public native Filter_frame_AVFilterLink_AVFrame filter_frame();

                    public native AVFilterPad filter_frame(Filter_frame_AVFilterLink_AVFrame filter_frame_AVFilterLink_AVFrame);

                    public native Get_audio_buffer_AVFilterLink_int get_audio_buffer();

                    public native AVFilterPad get_audio_buffer(Get_audio_buffer_AVFilterLink_int get_audio_buffer_AVFilterLink_int);

                    public native Get_video_buffer_AVFilterLink_int_int get_video_buffer();

                    public native AVFilterPad get_video_buffer(Get_video_buffer_AVFilterLink_int_int get_video_buffer_AVFilterLink_int_int);

                    @Deprecated
                    public native int min_perms();

                    public native AVFilterPad min_perms(int i);

                    @MemberGetter
                    @Cast({"const char*"})
                    public native BytePointer name();

                    public native int needs_fifo();

                    public native AVFilterPad needs_fifo(int i);

                    public native int needs_writable();

                    public native AVFilterPad needs_writable(int i);

                    public native Poll_frame_AVFilterLink poll_frame();

                    public native AVFilterPad poll_frame(Poll_frame_AVFilterLink poll_frame_AVFilterLink);

                    @Deprecated
                    public native int rej_perms();

                    public native AVFilterPad rej_perms(int i);

                    public native Request_frame_AVFilterLink request_frame();

                    public native AVFilterPad request_frame(Request_frame_AVFilterLink request_frame_AVFilterLink);

                    public native Start_frame_AVFilterLink_AVFilterBufferRef start_frame();

                    public native AVFilterPad start_frame(Start_frame_AVFilterLink_AVFilterBufferRef start_frame_AVFilterLink_AVFilterBufferRef);

                    @Cast({"AVMediaType"})
                    public native int type();

                    public native AVFilterPad type(int i);


                    static {
/* 383 */               Loader.load();
                    }

/* 383 */           public AVFilterPad() {
                        allocate();
                    }

/* 384 */           public AVFilterPad(int size) {
                        allocateArray(size);
                    }

/* 385 */           public AVFilterPad(Pointer p) {
/* 386 */               super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 389 */           public AVFilterPad position(int position) {
/* 390 */               return (AVFilterPad) super.position(position);
                    }

/* 434 */           public static class Start_frame_AVFilterLink_AVFilterBufferRef extends FunctionPointer {
                        private native void allocate();

                        public native int call(AVFilterLink aVFilterLink, AVFilterBufferRef aVFilterBufferRef);

                        static {
/* 436 */                   Loader.load();
                        }

/* 436 */               public Start_frame_AVFilterLink_AVFilterBufferRef(Pointer p) {
/* 437 */                   super(p);
                        }

/* 437 */               protected Start_frame_AVFilterLink_AVFilterBufferRef() {
                            allocate();
                        }
                    }

/* 449 */           public static class Get_video_buffer_AVFilterLink_int_int extends FunctionPointer {
                        private native void allocate();

                        public native avutil.AVFrame call(AVFilterLink aVFilterLink, int i, int i2);

                        static {
/* 451 */                   Loader.load();
                        }

/* 451 */               public Get_video_buffer_AVFilterLink_int_int(Pointer p) {
/* 452 */                   super(p);
                        }

/* 452 */               protected Get_video_buffer_AVFilterLink_int_int() {
                            allocate();
                        }
                    }

/* 464 */           public static class Get_audio_buffer_AVFilterLink_int extends FunctionPointer {
                        private native void allocate();

                        public native avutil.AVFrame call(AVFilterLink aVFilterLink, int i);

                        static {
/* 466 */                   Loader.load();
                        }

/* 466 */               public Get_audio_buffer_AVFilterLink_int(Pointer p) {
/* 467 */                   super(p);
                        }

/* 467 */               protected Get_audio_buffer_AVFilterLink_int() {
                            allocate();
                        }
                    }

/* 476 */           public static class End_frame_AVFilterLink extends FunctionPointer {
                        private native void allocate();

                        public native int call(AVFilterLink aVFilterLink);

                        static {
/* 478 */                   Loader.load();
                        }

/* 478 */               public End_frame_AVFilterLink(Pointer p) {
/* 479 */                   super(p);
                        }

/* 479 */               protected End_frame_AVFilterLink() {
                            allocate();
                        }
                    }

/* 488 */           public static class Draw_slice_AVFilterLink_int_int_int extends FunctionPointer {
                        private native void allocate();

                        public native int call(AVFilterLink aVFilterLink, int i, int i2, int i3);

                        static {
/* 490 */                   Loader.load();
                        }

/* 490 */               public Draw_slice_AVFilterLink_int_int_int(Pointer p) {
/* 491 */                   super(p);
                        }

/* 491 */               protected Draw_slice_AVFilterLink_int_int_int() {
                            allocate();
                        }
                    }

/* 507 */           public static class Filter_frame_AVFilterLink_AVFrame extends FunctionPointer {
                        private native void allocate();

                        public native int call(AVFilterLink aVFilterLink, avutil.AVFrame aVFrame);

                        static {
/* 509 */                   Loader.load();
                        }

/* 509 */               public Filter_frame_AVFilterLink_AVFrame(Pointer p) {
/* 510 */                   super(p);
                        }

/* 510 */               protected Filter_frame_AVFilterLink_AVFrame() {
                            allocate();
                        }
                    }

/* 525 */           public static class Poll_frame_AVFilterLink extends FunctionPointer {
                        private native void allocate();

                        public native int call(AVFilterLink aVFilterLink);

                        static {
/* 527 */                   Loader.load();
                        }

/* 527 */               public Poll_frame_AVFilterLink(Pointer p) {
/* 528 */                   super(p);
                        }

/* 528 */               protected Poll_frame_AVFilterLink() {
                            allocate();
                        }
                    }

/* 543 */           public static class Request_frame_AVFilterLink extends FunctionPointer {
                        private native void allocate();

                        public native int call(AVFilterLink aVFilterLink);

                        static {
/* 545 */                   Loader.load();
                        }

/* 545 */               public Request_frame_AVFilterLink(Pointer p) {
/* 546 */                   super(p);
                        }

/* 546 */               protected Request_frame_AVFilterLink() {
                            allocate();
                        }
                    }

/* 569 */           public static class Config_props_AVFilterLink extends FunctionPointer {
                        private native void allocate();

                        public native int call(AVFilterLink aVFilterLink);

                        static {
/* 571 */                   Loader.load();
                        }

/* 571 */               public Config_props_AVFilterLink(Pointer p) {
/* 572 */                   super(p);
                        }

/* 572 */               protected Config_props_AVFilterLink() {
                            allocate();
                        }
                    }
                }

/* 661 */       public static class AVFilter extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    @MemberGetter
                    @Cast({"const char*"})
                    public native BytePointer description();

                    public native int flags();

                    public native AVFilter flags(int i);

                    public native Init_AVFilterContext init();

                    public native AVFilter init(Init_AVFilterContext init_AVFilterContext);

                    public native Init_dict_AVFilterContext_PointerPointer init_dict();

                    public native AVFilter init_dict(Init_dict_AVFilterContext_PointerPointer init_dict_AVFilterContext_PointerPointer);

                    public native Init_opaque_AVFilterContext_Pointer init_opaque();

                    public native AVFilter init_opaque(Init_opaque_AVFilterContext_Pointer init_opaque_AVFilterContext_Pointer);

                    @MemberGetter
                    @Const
                    public native AVFilterPad inputs();

                    @MemberGetter
                    @Cast({"const char*"})
                    public native BytePointer name();

                    public native AVFilter next();

                    public native AVFilter next(AVFilter aVFilter);

                    @MemberGetter
                    @Const
                    public native AVFilterPad outputs();

                    @MemberGetter
                    @Const
                    public native avutil.AVClass priv_class();

                    public native int priv_size();

                    public native AVFilter priv_size(int i);

                    public native C1231x8f446100 process_command();

                    public native AVFilter process_command(C1231x8f446100 c1231x8f446100);

                    public native Query_formats_AVFilterContext query_formats();

                    public native AVFilter query_formats(Query_formats_AVFilterContext query_formats_AVFilterContext);

                    public native Uninit_AVFilterContext uninit();

                    public native AVFilter uninit(Uninit_AVFilterContext uninit_AVFilterContext);


                    static {
/* 664 */               Loader.load();
                    }

/* 664 */           public AVFilter() {
                        allocate();
                    }

/* 665 */           public AVFilter(int size) {
                        allocateArray(size);
                    }

/* 666 */           public AVFilter(Pointer p) {
/* 667 */               super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 670 */           public AVFilter position(int position) {
/* 671 */               return (AVFilter) super.position(position);
                    }

/* 746 */           public static class Init_AVFilterContext extends FunctionPointer {
                        private native void allocate();

                        public native int call(AVFilterContext aVFilterContext);

                        static {
/* 748 */                   Loader.load();
                        }

/* 748 */               public Init_AVFilterContext(Pointer p) {
/* 749 */                   super(p);
                        }

/* 749 */               protected Init_AVFilterContext() {
                            allocate();
                        }
                    }

/* 766 */           public static class Init_dict_AVFilterContext_PointerPointer extends FunctionPointer {
                        private native void allocate();

                        public native int call(AVFilterContext aVFilterContext, @Cast({"AVDictionary**"}) PointerPointer pointerPointer);

                        static {
/* 768 */                   Loader.load();
                        }

/* 768 */               public Init_dict_AVFilterContext_PointerPointer(Pointer p) {
/* 769 */                   super(p);
                        }

/* 769 */               protected Init_dict_AVFilterContext_PointerPointer() {
                            allocate();
                        }
                    }

/* 785 */           public static class Uninit_AVFilterContext extends FunctionPointer {
                        private native void allocate();

                        public native void call(AVFilterContext aVFilterContext);

                        static {
/* 787 */                   Loader.load();
                        }

/* 787 */               public Uninit_AVFilterContext(Pointer p) {
/* 788 */                   super(p);
                        }

/* 788 */               protected Uninit_AVFilterContext() {
                            allocate();
                        }
                    }

/* 816 */           public static class Query_formats_AVFilterContext extends FunctionPointer {
                        private native void allocate();

                        public native int call(AVFilterContext aVFilterContext);

                        static {
/* 818 */                   Loader.load();
                        }

/* 818 */               public Query_formats_AVFilterContext(Pointer p) {
/* 819 */                   super(p);
                        }

/* 819 */               protected Query_formats_AVFilterContext() {
                            allocate();
                        }
                    }

                    /* JADX INFO: renamed from: com.googlecode.javacv.cpp.avfilter$AVFilter$Process_command_AVFilterContext_BytePointer_BytePointer_BytePointer_int_int */
/* 846 */           public static class C1231x8f446100 extends FunctionPointer {
                        private native void allocate();

                        public native int call(AVFilterContext aVFilterContext, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, @Cast({"char*"}) BytePointer bytePointer3, int i, int i2);

                        static {
/* 848 */                   Loader.load();
                        }

/* 848 */               public C1231x8f446100(Pointer p) {
/* 849 */                   super(p);
                        }

/* 849 */               protected C1231x8f446100() {
                            allocate();
                        }
                    }

/* 860 */           public static class Init_opaque_AVFilterContext_Pointer extends FunctionPointer {
                        private native void allocate();

                        public native int call(AVFilterContext aVFilterContext, Pointer pointer);

                        static {
/* 862 */                   Loader.load();
                        }

/* 862 */               public Init_opaque_AVFilterContext_Pointer(Pointer p) {
/* 863 */                   super(p);
                        }

/* 863 */               protected Init_opaque_AVFilterContext_Pointer() {
                            allocate();
                        }
                    }
                }

                @Opaque
/* 875 */       public static class AVFilterInternal extends Pointer {
/* 876 */           public AVFilterInternal() {
                    }

/* 877 */           public AVFilterInternal(Pointer p) {
/* 878 */               super(p);
                    }
                }

/* 880 */       public static class AVFilterContext extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    @MemberGetter
                    @Const
                    public native avutil.AVClass av_class();

                    @Cast({"AVFilterCommand*"})
                    public native Pointer command_queue();

                    public native AVFilterContext command_queue(Pointer pointer);

                    public native Pointer enable();

                    public native AVFilterContext enable(Pointer pointer);

                    @Cast({"char*"})
                    public native BytePointer enable_str();

                    public native AVFilterContext enable_str(BytePointer bytePointer);

                    @MemberGetter
                    @Const
                    public native AVFilter filter();

                    public native AVFilterContext graph(AVFilterGraph aVFilterGraph);

                    public native AVFilterGraph graph();

                    public native AVFilterContext input_pads(AVFilterPad aVFilterPad);

                    public native AVFilterPad input_pads();

                    @MemberGetter
                    @Cast({"AVFilterLink**"})
                    public native PointerPointer inputs();

                    public native AVFilterContext inputs(int i, AVFilterLink aVFilterLink);

                    public native AVFilterLink inputs(int i);

                    public native AVFilterContext internal(AVFilterInternal aVFilterInternal);

                    public native AVFilterInternal internal();

                    public native int is_disabled();

                    public native AVFilterContext is_disabled(int i);

                    @Cast({"char*"})
                    public native BytePointer name();

                    public native AVFilterContext name(BytePointer bytePointer);

                    @Cast({"unsigned"})
                    public native int nb_inputs();

                    public native AVFilterContext nb_inputs(int i);

                    @Cast({"unsigned"})
                    public native int nb_outputs();

                    public native AVFilterContext nb_outputs(int i);

                    public native AVFilterContext output_pads(AVFilterPad aVFilterPad);

                    public native AVFilterPad output_pads();

                    @MemberGetter
                    @Cast({"AVFilterLink**"})
                    public native PointerPointer outputs();

                    public native AVFilterContext outputs(int i, AVFilterLink aVFilterLink);

                    public native AVFilterLink outputs(int i);

                    public native Pointer priv();

                    public native AVFilterContext priv(Pointer pointer);

                    public native int thread_type();

                    public native AVFilterContext thread_type(int i);

                    public native DoublePointer var_values();

                    public native AVFilterContext var_values(DoublePointer doublePointer);


                    static {
/* 883 */               Loader.load();
                    }

/* 883 */           public AVFilterContext() {
                        allocate();
                    }

/* 884 */           public AVFilterContext(int size) {
                        allocateArray(size);
                    }

/* 885 */           public AVFilterContext(Pointer p) {
/* 886 */               super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 889 */           public AVFilterContext position(int position) {
/* 890 */               return (AVFilterContext) super.position(position);
                    }
                }

/* 968 */       public static class AVFilterLink extends Pointer {
                    public static final int AVLINK_INIT = 2;
                    public static final int AVLINK_STARTINIT = 1;
                    public static final int AVLINK_UNINIT = 0;

                    private native void allocate();

                    private native void allocateArray(int i);

                    public native int age_index();

                    public native AVFilterLink age_index(int i);

                    @Cast({"uint64_t"})
                    public native long channel_layout();

                    public native AVFilterLink channel_layout(long j);

                    public native int channels();

                    public native AVFilterLink channels(int i);

                    public native int closed();

                    public native AVFilterLink closed(int i);

                    public native AVFilterBufferRef cur_buf_copy();

                    public native AVFilterLink cur_buf_copy(AVFilterBufferRef aVFilterBufferRef);

                    public native long current_pts();

                    public native AVFilterLink current_pts(long j);

                    public native AVFilterContext dst();

                    public native AVFilterLink dst(AVFilterContext aVFilterContext);

                    public native AVFilterLink dstpad(AVFilterPad aVFilterPad);

                    public native AVFilterPad dstpad();

                    @Cast({"unsigned"})
                    public native int flags();

                    public native AVFilterLink flags(int i);

                    public native int format();

                    public native AVFilterLink format(int i);

                    public native long frame_count();

                    public native AVFilterLink frame_count(long j);

                    public native AVFilterLink frame_rate(avutil.AVRational aVRational);

                    @ByVal
                    public native avutil.AVRational frame_rate();

                    @Cast({"unsigned"})
                    public native int frame_requested();

                    public native AVFilterLink frame_requested(int i);

                    public native AVFilterGraph graph();

                    public native AVFilterLink graph(AVFilterGraph aVFilterGraph);

                    /* JADX INFO: renamed from: h */
                    public native int m2403h();

                    /* JADX INFO: renamed from: h */
                    public native AVFilterLink m2404h(int i);

                    @Cast({"AVFilterChannelLayouts*"})
                    public native Pointer in_channel_layouts();

                    public native AVFilterLink in_channel_layouts(Pointer pointer);

                    public native AVFilterFormats in_formats();

                    public native AVFilterLink in_formats(AVFilterFormats aVFilterFormats);

                    public native AVFilterFormats in_samplerates();

                    public native AVFilterLink in_samplerates(AVFilterFormats aVFilterFormats);

                    public native int max_samples();

                    public native AVFilterLink max_samples(int i);

                    public native int min_samples();

                    public native AVFilterLink min_samples(int i);

                    @Cast({"AVFilterChannelLayouts*"})
                    public native Pointer out_channel_layouts();

                    public native AVFilterLink out_channel_layouts(Pointer pointer);

                    public native AVFilterFormats out_formats();

                    public native AVFilterLink out_formats(AVFilterFormats aVFilterFormats);

                    public native AVFilterFormats out_samplerates();

                    public native AVFilterLink out_samplerates(AVFilterFormats aVFilterFormats);

                    public native AVFilterLink partial_buf(avutil.AVFrame aVFrame);

                    public native avutil.AVFrame partial_buf();

                    public native int partial_buf_size();

                    public native AVFilterLink partial_buf_size(int i);

                    @Cast({"AVFilterPool*"})
                    public native Pointer pool();

                    public native AVFilterLink pool(Pointer pointer);

                    public native int request_samples();

                    public native AVFilterLink request_samples(int i);

                    public native AVFilterLink sample_aspect_ratio(avutil.AVRational aVRational);

                    @ByVal
                    public native avutil.AVRational sample_aspect_ratio();

                    public native int sample_rate();

                    public native AVFilterLink sample_rate(int i);

                    public native AVFilterContext src();

                    public native AVFilterLink src(AVFilterContext aVFilterContext);

                    public native AVFilterLink srcpad(AVFilterPad aVFilterPad);

                    public native AVFilterPad srcpad();

                    public native AVFilterLink time_base(avutil.AVRational aVRational);

                    @ByVal
                    public native avutil.AVRational time_base();

                    @Cast({"AVMediaType"})
                    public native int type();

                    public native AVFilterLink type(int i);

                    /* JADX INFO: renamed from: w */
                    public native int m2405w();

                    /* JADX INFO: renamed from: w */
                    public native AVFilterLink m2406w(int i);


                    static {
/* 971 */               Loader.load();
                    }

/* 971 */           public AVFilterLink() {
                        allocate();
                    }

/* 972 */           public AVFilterLink(int size) {
                        allocateArray(size);
                    }

/* 973 */           public AVFilterLink(Pointer p) {
/* 974 */               super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 977 */           public AVFilterLink position(int position) {
/* 978 */               return (AVFilterLink) super.position(position);
                    }
                }

                @Opaque
/* 1480 */      public static class AVFilterGraphInternal extends Pointer {
/* 1481 */          public AVFilterGraphInternal() {
                    }

/* 1482 */          public AVFilterGraphInternal(Pointer p) {
/* 1483 */              super(p);
                    }
                }

/* 1484 */      public static class AVFilterGraph extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    @Cast({"char*"})
                    public native BytePointer aresample_swr_opts();

                    public native AVFilterGraph aresample_swr_opts(BytePointer bytePointer);

                    @MemberGetter
                    @Const
                    public native avutil.AVClass av_class();

                    @Cast({"unsigned"})
                    public native int disable_auto_convert();

                    public native AVFilterGraph disable_auto_convert(int i);

                    @MemberGetter
                    @Cast({"AVFilterContext**"})
                    public native PointerPointer filters();

                    public native AVFilterContext filters(int i);

                    public native AVFilterGraph filters(int i, AVFilterContext aVFilterContext);

                    public native AVFilterGraph internal(AVFilterGraphInternal aVFilterGraphInternal);

                    public native AVFilterGraphInternal internal();

                    public native int nb_threads();

                    public native AVFilterGraph nb_threads(int i);

                    @Cast({"char*"})
                    public native BytePointer resample_lavr_opts();

                    public native AVFilterGraph resample_lavr_opts(BytePointer bytePointer);

                    @Cast({"char*"})
                    public native BytePointer scale_sws_opts();

                    public native AVFilterGraph scale_sws_opts(BytePointer bytePointer);

                    @MemberGetter
                    @Cast({"AVFilterLink**"})
                    public native PointerPointer sink_links();

                    public native AVFilterGraph sink_links(int i, AVFilterLink aVFilterLink);

                    public native AVFilterLink sink_links(int i);

                    public native int sink_links_count();

                    public native AVFilterGraph sink_links_count(int i);

                    public native int thread_type();

                    public native AVFilterGraph thread_type(int i);


                    static {
/* 1487 */              Loader.load();
                    }

/* 1487 */          public AVFilterGraph() {
                        allocate();
                    }

/* 1488 */          public AVFilterGraph(int size) {
                        allocateArray(size);
                    }

/* 1489 */          public AVFilterGraph(Pointer p) {
/* 1490 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 1493 */          public AVFilterGraph position(int position) {
/* 1494 */              return (AVFilterGraph) super.position(position);
                    }
                }

/* 1667 */      public static class AVFilterInOut extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native AVFilterContext filter_ctx();

                    public native AVFilterInOut filter_ctx(AVFilterContext aVFilterContext);

                    @Cast({"char*"})
                    public native BytePointer name();

                    public native AVFilterInOut name(BytePointer bytePointer);

                    public native AVFilterInOut next();

                    public native AVFilterInOut next(AVFilterInOut aVFilterInOut);

                    public native int pad_idx();

                    public native AVFilterInOut pad_idx(int i);


                    static {
/* 1670 */              Loader.load();
                    }

/* 1670 */          public AVFilterInOut() {
                        allocate();
                    }

/* 1671 */          public AVFilterInOut(int size) {
                        allocateArray(size);
                    }

/* 1672 */          public AVFilterInOut(Pointer p) {
/* 1673 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 1676 */          public AVFilterInOut position(int position) {
/* 1677 */              return (AVFilterInOut) super.position(position);
                    }
                }

/* 1983 */      public static class AVBufferSinkParams extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    @MemberGetter
                    @Cast({"const AVPixelFormat*"})
                    public native IntPointer pixel_fmts();


                    static {
/* 1986 */              Loader.load();
                    }

/* 1986 */          public AVBufferSinkParams() {
                        allocate();
                    }

/* 1987 */          public AVBufferSinkParams(int size) {
                        allocateArray(size);
                    }

/* 1988 */          public AVBufferSinkParams(Pointer p) {
/* 1989 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 1992 */          public AVBufferSinkParams position(int position) {
/* 1993 */              return (AVBufferSinkParams) super.position(position);
                    }
                }

/* 2008 */      public static class AVABufferSinkParams extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native int all_channel_counts();

                    public native AVABufferSinkParams all_channel_counts(int i);

                    @MemberGetter
                    @Const
                    public native IntPointer channel_counts();

                    @MemberGetter
                    @Const
                    public native LongPointer channel_layouts();

                    @MemberGetter
                    @Cast({"const AVSampleFormat*"})
                    public native IntPointer sample_fmts();

                    public native IntPointer sample_rates();

                    public native AVABufferSinkParams sample_rates(IntPointer intPointer);


                    static {
/* 2011 */              Loader.load();
                    }

/* 2011 */          public AVABufferSinkParams() {
                        allocate();
                    }

/* 2012 */          public AVABufferSinkParams(int size) {
                        allocateArray(size);
                    }

/* 2013 */          public AVABufferSinkParams(Pointer p) {
/* 2014 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 2017 */          public AVABufferSinkParams position(int position) {
/* 2018 */              return (AVABufferSinkParams) super.position(position);
                    }
                }
            }
