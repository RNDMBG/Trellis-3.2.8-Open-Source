            package com.googlecode.javacv.cpp;

            import com.googlecode.javacpp.BytePointer;
            import com.googlecode.javacpp.DoublePointer;
            import com.googlecode.javacpp.FloatPointer;
            import com.googlecode.javacpp.FunctionPointer;
            import com.googlecode.javacpp.IntPointer;
            import com.googlecode.javacpp.Loader;
            import com.googlecode.javacpp.LongPointer;
            import com.googlecode.javacpp.Pointer;
            import com.googlecode.javacpp.PointerPointer;
            import com.googlecode.javacpp.ShortPointer;
            import com.googlecode.javacpp.SizeTPointer;
            import com.googlecode.javacpp.annotation.ByPtrPtr;
            import com.googlecode.javacpp.annotation.ByVal;
            import com.googlecode.javacpp.annotation.Cast;
            import com.googlecode.javacpp.annotation.Const;
            import com.googlecode.javacpp.annotation.MemberGetter;
            import com.googlecode.javacpp.annotation.NoOffset;
            import com.googlecode.javacpp.annotation.Opaque;
            import com.googlecode.javacpp.annotation.Platform;
            import com.googlecode.javacpp.annotation.Properties;
            import java.nio.ByteBuffer;
            import java.nio.DoubleBuffer;
            import java.nio.FloatBuffer;
            import java.nio.IntBuffer;
            import java.nio.LongBuffer;
            import java.nio.ShortBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @Properties({@Platform(cinclude = {"<libavutil/avutil.h>", "<libavutil/error.h>", "<libavutil/mem.h>", "<libavutil/mathematics.h>", "<libavutil/rational.h>", "<libavutil/log.h>", "<libavutil/buffer.h>", "<libavutil/frame.h>", "<libavutil/pixfmt.h>", "<libavutil/samplefmt.h>", "<libavutil/channel_layout.h>", "<libavutil/cpu.h>", "<libavutil/dict.h>", "<libavutil/opt.h>", "<libavutil/audioconvert.h>", "<libavutil/pixdesc.h>", "<libavutil/imgutils.h>"}, define = {"__STDC_CONSTANT_MACROS"}, includepath = {"/usr/local/include/ffmpeg/", "/opt/local/include/ffmpeg/", "/usr/include/ffmpeg/"}, link = {"avutil@.52"}, options = {"default", "nodeprecated"}), @Platform(includepath = {"C:/MinGW/local/include/ffmpeg/", "C:/MinGW/include/ffmpeg/", "src/main/resources/com/googlecode/javacv/cpp/"}, preload = {"avutil-52"}, value = {"windows"})})
/* 41 */    public class avutil {
                public static final int AVERROR_BSF_NOT_FOUND;
                public static final int AVERROR_BUFFER_TOO_SMALL;
                public static final int AVERROR_BUG;
                public static final int AVERROR_BUG2;
                public static final int AVERROR_DECODER_NOT_FOUND;
                public static final int AVERROR_DEMUXER_NOT_FOUND;
                public static final int AVERROR_ENCODER_NOT_FOUND;
                public static final int AVERROR_EOF;
                public static final int AVERROR_EXIT;
                public static final int AVERROR_EXPERIMENTAL = -733130664;
                public static final int AVERROR_EXTERNAL;
                public static final int AVERROR_FILTER_NOT_FOUND;
                public static final int AVERROR_INVALIDDATA;
                public static final int AVERROR_MUXER_NOT_FOUND;
                public static final int AVERROR_OPTION_NOT_FOUND;
                public static final int AVERROR_PATCHWELCOME;
                public static final int AVERROR_PROTOCOL_NOT_FOUND;
                public static final int AVERROR_STREAM_NOT_FOUND;
                public static final int AVERROR_UNKNOWN;
                public static final int AVMEDIA_TYPE_ATTACHMENT = 4;
                public static final int AVMEDIA_TYPE_AUDIO = 1;
                public static final int AVMEDIA_TYPE_DATA = 2;
                public static final int AVMEDIA_TYPE_NB = 5;
                public static final int AVMEDIA_TYPE_SUBTITLE = 3;
                public static final int AVMEDIA_TYPE_UNKNOWN = -1;
                public static final int AVMEDIA_TYPE_VIDEO = 0;
                public static final int AVPALETTE_COUNT = 256;
                public static final int AVPALETTE_SIZE = 1024;
                public static final int AV_BUFFER_FLAG_READONLY = 1;
                public static final int AV_CH_BACK_CENTER = 256;
                public static final int AV_CH_BACK_LEFT = 16;
                public static final int AV_CH_BACK_RIGHT = 32;
                public static final int AV_CH_FRONT_CENTER = 4;
                public static final int AV_CH_FRONT_LEFT = 1;
                public static final int AV_CH_FRONT_LEFT_OF_CENTER = 64;
                public static final int AV_CH_FRONT_RIGHT = 2;
                public static final int AV_CH_FRONT_RIGHT_OF_CENTER = 128;
                public static final int AV_CH_LAYOUT_2POINT1 = 11;
                public static final int AV_CH_LAYOUT_2_1 = 259;
                public static final int AV_CH_LAYOUT_2_2 = 1539;
                public static final int AV_CH_LAYOUT_3POINT1 = 15;
                public static final int AV_CH_LAYOUT_4POINT0 = 263;
                public static final int AV_CH_LAYOUT_4POINT1 = 271;
                public static final int AV_CH_LAYOUT_5POINT0 = 1543;
                public static final int AV_CH_LAYOUT_5POINT0_BACK = 55;
                public static final int AV_CH_LAYOUT_5POINT1 = 1551;
                public static final int AV_CH_LAYOUT_5POINT1_BACK = 63;
                public static final int AV_CH_LAYOUT_6POINT0 = 1799;
                public static final int AV_CH_LAYOUT_6POINT0_FRONT = 1731;
                public static final int AV_CH_LAYOUT_6POINT1 = 1807;
                public static final int AV_CH_LAYOUT_6POINT1_BACK = 319;
                public static final int AV_CH_LAYOUT_6POINT1_FRONT = 1739;
                public static final int AV_CH_LAYOUT_7POINT0 = 1591;
                public static final int AV_CH_LAYOUT_7POINT0_FRONT = 1735;
                public static final int AV_CH_LAYOUT_7POINT1 = 1599;
                public static final int AV_CH_LAYOUT_7POINT1_WIDE = 1743;
                public static final int AV_CH_LAYOUT_7POINT1_WIDE_BACK = 255;
                public static final int AV_CH_LAYOUT_HEXAGONAL = 311;
                public static final int AV_CH_LAYOUT_MONO = 4;
                public static final long AV_CH_LAYOUT_NATIVE = Long.MIN_VALUE;
                public static final int AV_CH_LAYOUT_OCTAGONAL = 1847;
                public static final int AV_CH_LAYOUT_QUAD = 51;
                public static final int AV_CH_LAYOUT_STEREO = 3;
                public static final int AV_CH_LAYOUT_STEREO_DOWNMIX = 1610612736;
                public static final int AV_CH_LAYOUT_SURROUND = 7;
                public static final int AV_CH_LOW_FREQUENCY = 8;
                public static final long AV_CH_LOW_FREQUENCY_2 = 34359738368L;
                public static final int AV_CH_SIDE_LEFT = 512;
                public static final int AV_CH_SIDE_RIGHT = 1024;
                public static final int AV_CH_STEREO_LEFT = 536870912;
                public static final int AV_CH_STEREO_RIGHT = 1073741824;
                public static final long AV_CH_SURROUND_DIRECT_LEFT = 8589934592L;
                public static final long AV_CH_SURROUND_DIRECT_RIGHT = 17179869184L;
                public static final int AV_CH_TOP_BACK_CENTER = 65536;
                public static final int AV_CH_TOP_BACK_LEFT = 32768;
                public static final int AV_CH_TOP_BACK_RIGHT = 131072;
                public static final int AV_CH_TOP_CENTER = 2048;
                public static final int AV_CH_TOP_FRONT_CENTER = 8192;
                public static final int AV_CH_TOP_FRONT_LEFT = 4096;
                public static final int AV_CH_TOP_FRONT_RIGHT = 16384;
                public static final long AV_CH_WIDE_LEFT = 2147483648L;
                public static final long AV_CH_WIDE_RIGHT = 4294967296L;
                public static final int AV_CLASS_CATEGORY_BITSTREAM_FILTER = 8;
                public static final int AV_CLASS_CATEGORY_DECODER = 6;
                public static final int AV_CLASS_CATEGORY_DEMUXER = 4;
                public static final int AV_CLASS_CATEGORY_ENCODER = 5;
                public static final int AV_CLASS_CATEGORY_FILTER = 7;
                public static final int AV_CLASS_CATEGORY_INPUT = 1;
                public static final int AV_CLASS_CATEGORY_MUXER = 3;
                public static final int AV_CLASS_CATEGORY_NA = 0;
                public static final int AV_CLASS_CATEGORY_NB = 11;
                public static final int AV_CLASS_CATEGORY_OUTPUT = 2;
                public static final int AV_CLASS_CATEGORY_SWRESAMPLER = 10;
                public static final int AV_CLASS_CATEGORY_SWSCALER = 9;
                public static final int AV_CPU_FLAG_3DNOW = 4;
                public static final int AV_CPU_FLAG_3DNOWEXT = 32;
                public static final int AV_CPU_FLAG_ALTIVEC = 1;
                public static final int AV_CPU_FLAG_ARMV5TE = 1;
                public static final int AV_CPU_FLAG_ARMV6 = 2;
                public static final int AV_CPU_FLAG_ARMV6T2 = 4;
                public static final int AV_CPU_FLAG_ATOM = 268435456;
                public static final int AV_CPU_FLAG_AVX = 16384;
                public static final int AV_CPU_FLAG_CMOV = 16781312;
                public static final int AV_CPU_FLAG_FMA4 = 2048;
                public static final int AV_CPU_FLAG_FORCE = Integer.MIN_VALUE;
                public static final int AV_CPU_FLAG_MMX = 1;
                public static final int AV_CPU_FLAG_MMX2 = 2;
                public static final int AV_CPU_FLAG_MMXEXT = 2;
                public static final int AV_CPU_FLAG_NEON = 32;
                public static final int AV_CPU_FLAG_SSE = 8;
                public static final int AV_CPU_FLAG_SSE2 = 16;
                public static final int AV_CPU_FLAG_SSE2SLOW = 1073741824;
                public static final int AV_CPU_FLAG_SSE3 = 64;
                public static final int AV_CPU_FLAG_SSE3SLOW = 536870912;
                public static final int AV_CPU_FLAG_SSE4 = 256;
                public static final int AV_CPU_FLAG_SSE42 = 512;
                public static final int AV_CPU_FLAG_SSSE3 = 128;
                public static final int AV_CPU_FLAG_VFP = 8;
                public static final int AV_CPU_FLAG_VFPV3 = 16;
                public static final int AV_CPU_FLAG_XOP = 1024;
                public static final int AV_DICT_APPEND = 32;
                public static final int AV_DICT_DONT_OVERWRITE = 16;
                public static final int AV_DICT_DONT_STRDUP_KEY = 4;
                public static final int AV_DICT_DONT_STRDUP_VAL = 8;
                public static final int AV_DICT_IGNORE_SUFFIX = 2;
                public static final int AV_DICT_MATCH_CASE = 1;
                public static final int AV_ERROR_MAX_STRING_SIZE = 64;
                public static final int AV_FRAME_DATA_PANSCAN = 0;
                public static final int AV_LOG_DEBUG = 48;
                public static final int AV_LOG_ERROR = 16;
                public static final int AV_LOG_FATAL = 8;
                public static final int AV_LOG_INFO = 32;
                public static final int AV_LOG_MAX_OFFSET = 56;
                public static final int AV_LOG_PANIC = 0;
                public static final int AV_LOG_QUIET = -8;
                public static final int AV_LOG_SKIP_REPEATED = 1;
                public static final int AV_LOG_VERBOSE = 40;
                public static final int AV_LOG_WARNING = 24;
                public static final int AV_MATRIX_ENCODING_DOLBY = 1;
                public static final int AV_MATRIX_ENCODING_DPLII = 2;
                public static final int AV_MATRIX_ENCODING_NB = 3;
                public static final int AV_MATRIX_ENCODING_NONE = 0;
                public static final long AV_NOPTS_VALUE;
                public static final int AV_OPT_FLAG_IMPLICIT_KEY = 1;
                public static final int AV_OPT_SEARCH_CHILDREN = 1;
                public static final int AV_OPT_SEARCH_FAKE_OBJ = 2;
                public static final int AV_OPT_TYPE_BINARY = 7;
                public static final int AV_OPT_TYPE_COLOR;
                public static final int AV_OPT_TYPE_CONST = 128;
                public static final int AV_OPT_TYPE_DOUBLE = 3;
                public static final int AV_OPT_TYPE_DURATION;
                public static final int AV_OPT_TYPE_FLAGS = 0;
                public static final int AV_OPT_TYPE_FLOAT = 4;
                public static final int AV_OPT_TYPE_IMAGE_SIZE;
                public static final int AV_OPT_TYPE_INT = 1;
                public static final int AV_OPT_TYPE_INT64 = 2;
                public static final int AV_OPT_TYPE_PIXEL_FMT;
                public static final int AV_OPT_TYPE_RATIONAL = 6;
                public static final int AV_OPT_TYPE_SAMPLE_FMT;
                public static final int AV_OPT_TYPE_STRING = 5;
                public static final int AV_OPT_TYPE_VIDEO_RATE;
                public static final int AV_PICTURE_TYPE_B = 3;
                public static final int AV_PICTURE_TYPE_BI = 7;
                public static final int AV_PICTURE_TYPE_I = 1;
                public static final int AV_PICTURE_TYPE_NONE = 0;
                public static final int AV_PICTURE_TYPE_P = 2;
                public static final int AV_PICTURE_TYPE_S = 4;
                public static final int AV_PICTURE_TYPE_SI = 5;
                public static final int AV_PICTURE_TYPE_SP = 6;
                public static final int AV_PIX_FMT_0BGR = 297;
                public static final int AV_PIX_FMT_0BGR32;
                public static final int AV_PIX_FMT_0RGB = 295;
                public static final int AV_PIX_FMT_0RGB32;
                public static final int AV_PIX_FMT_ABGR = 29;
                public static final int AV_PIX_FMT_ARGB = 27;
                public static final int AV_PIX_FMT_BGR0 = 298;
                public static final int AV_PIX_FMT_BGR24 = 3;
                public static final int AV_PIX_FMT_BGR32;
                public static final int AV_PIX_FMT_BGR32_1;
                public static final int AV_PIX_FMT_BGR4 = 20;
                public static final int AV_PIX_FMT_BGR444;
                public static final int AV_PIX_FMT_BGR444BE = 65;
                public static final int AV_PIX_FMT_BGR444LE = 64;
                public static final int AV_PIX_FMT_BGR48;
                public static final int AV_PIX_FMT_BGR48BE = 67;
                public static final int AV_PIX_FMT_BGR48LE = 68;
                public static final int AV_PIX_FMT_BGR4_BYTE = 21;
                public static final int AV_PIX_FMT_BGR555;
                public static final int AV_PIX_FMT_BGR555BE = 49;
                public static final int AV_PIX_FMT_BGR555LE = 50;
                public static final int AV_PIX_FMT_BGR565;
                public static final int AV_PIX_FMT_BGR565BE = 47;
                public static final int AV_PIX_FMT_BGR565LE = 48;
                public static final int AV_PIX_FMT_BGR8 = 19;
                public static final int AV_PIX_FMT_BGRA = 30;
                public static final int AV_PIX_FMT_BGRA64;
                public static final int AV_PIX_FMT_BGRA64BE = 293;
                public static final int AV_PIX_FMT_BGRA64LE = 294;
                public static final int AV_PIX_FMT_DXVA2_VLD = 61;
                public static final int AV_PIX_FMT_FLAG_ALPHA = 128;
                public static final int AV_PIX_FMT_FLAG_BE = 1;
                public static final int AV_PIX_FMT_FLAG_BITSTREAM = 4;
                public static final int AV_PIX_FMT_FLAG_HWACCEL = 8;
                public static final int AV_PIX_FMT_FLAG_PAL = 2;
                public static final int AV_PIX_FMT_FLAG_PLANAR = 16;
                public static final int AV_PIX_FMT_FLAG_PSEUDOPAL = 64;
                public static final int AV_PIX_FMT_FLAG_RGB = 32;
                public static final int AV_PIX_FMT_GBR24P = 82;
                public static final int AV_PIX_FMT_GBRAP = 317;
                public static final int AV_PIX_FMT_GBRAP16BE = 318;
                public static final int AV_PIX_FMT_GBRAP16LE = 319;
                public static final int AV_PIX_FMT_GBRP = 82;
                public static final int AV_PIX_FMT_GBRP10;
                public static final int AV_PIX_FMT_GBRP10BE = 85;
                public static final int AV_PIX_FMT_GBRP10LE = 86;
                public static final int AV_PIX_FMT_GBRP12;
                public static final int AV_PIX_FMT_GBRP12BE = 313;
                public static final int AV_PIX_FMT_GBRP12LE = 314;
                public static final int AV_PIX_FMT_GBRP14;
                public static final int AV_PIX_FMT_GBRP14BE = 315;
                public static final int AV_PIX_FMT_GBRP14LE = 316;
                public static final int AV_PIX_FMT_GBRP16;
                public static final int AV_PIX_FMT_GBRP16BE = 87;
                public static final int AV_PIX_FMT_GBRP16LE = 88;
                public static final int AV_PIX_FMT_GBRP9;
                public static final int AV_PIX_FMT_GBRP9BE = 83;
                public static final int AV_PIX_FMT_GBRP9LE = 84;
                public static final int AV_PIX_FMT_GRAY16;
                public static final int AV_PIX_FMT_GRAY16BE = 31;
                public static final int AV_PIX_FMT_GRAY16LE = 32;
                public static final int AV_PIX_FMT_GRAY8 = 8;
                public static final int AV_PIX_FMT_GRAY8A = 66;
                public static final int AV_PIX_FMT_MONOBLACK = 10;
                public static final int AV_PIX_FMT_MONOWHITE = 9;
                public static final int AV_PIX_FMT_NB = 321;
                public static final int AV_PIX_FMT_NONE = -1;
                public static final int AV_PIX_FMT_NV12 = 25;
                public static final int AV_PIX_FMT_NV21 = 26;
                public static final int AV_PIX_FMT_PAL8 = 11;
                public static final int AV_PIX_FMT_RGB0 = 296;
                public static final int AV_PIX_FMT_RGB24 = 2;
                public static final int AV_PIX_FMT_RGB32;
                public static final int AV_PIX_FMT_RGB32_1;
                public static final int AV_PIX_FMT_RGB4 = 23;
                public static final int AV_PIX_FMT_RGB444;
                public static final int AV_PIX_FMT_RGB444BE = 63;
                public static final int AV_PIX_FMT_RGB444LE = 62;
                public static final int AV_PIX_FMT_RGB48;
                public static final int AV_PIX_FMT_RGB48BE = 41;
                public static final int AV_PIX_FMT_RGB48LE = 42;
                public static final int AV_PIX_FMT_RGB4_BYTE = 24;
                public static final int AV_PIX_FMT_RGB555;
                public static final int AV_PIX_FMT_RGB555BE = 45;
                public static final int AV_PIX_FMT_RGB555LE = 46;
                public static final int AV_PIX_FMT_RGB565;
                public static final int AV_PIX_FMT_RGB565BE = 43;
                public static final int AV_PIX_FMT_RGB565LE = 44;
                public static final int AV_PIX_FMT_RGB8 = 22;
                public static final int AV_PIX_FMT_RGBA = 28;
                public static final int AV_PIX_FMT_RGBA64;
                public static final int AV_PIX_FMT_RGBA64BE = 291;
                public static final int AV_PIX_FMT_RGBA64LE = 292;
                public static final int AV_PIX_FMT_UYVY422 = 17;
                public static final int AV_PIX_FMT_UYYVYY411 = 18;
                public static final int AV_PIX_FMT_VAAPI_IDCT = 52;
                public static final int AV_PIX_FMT_VAAPI_MOCO = 51;
                public static final int AV_PIX_FMT_VAAPI_VLD = 53;
                public static final int AV_PIX_FMT_VDA_VLD = 81;
                public static final int AV_PIX_FMT_VDPAU = 109;
                public static final int AV_PIX_FMT_VDPAU_H264 = 36;
                public static final int AV_PIX_FMT_VDPAU_MPEG1 = 37;
                public static final int AV_PIX_FMT_VDPAU_MPEG2 = 38;
                public static final int AV_PIX_FMT_VDPAU_MPEG4 = 60;
                public static final int AV_PIX_FMT_VDPAU_VC1 = 40;
                public static final int AV_PIX_FMT_VDPAU_WMV3 = 39;
                public static final int AV_PIX_FMT_XVMC_MPEG2_IDCT = 16;
                public static final int AV_PIX_FMT_XVMC_MPEG2_MC = 15;
                public static final int AV_PIX_FMT_XYZ12;
                public static final int AV_PIX_FMT_XYZ12BE = 111;
                public static final int AV_PIX_FMT_XYZ12LE = 110;
                public static final int AV_PIX_FMT_Y400A = 66;
                public static final int AV_PIX_FMT_YUV410P = 6;
                public static final int AV_PIX_FMT_YUV411P = 7;
                public static final int AV_PIX_FMT_YUV420P = 0;
                public static final int AV_PIX_FMT_YUV420P10;
                public static final int AV_PIX_FMT_YUV420P10BE = 71;
                public static final int AV_PIX_FMT_YUV420P10LE = 72;
                public static final int AV_PIX_FMT_YUV420P12;
                public static final int AV_PIX_FMT_YUV420P12BE = 301;
                public static final int AV_PIX_FMT_YUV420P12LE = 302;
                public static final int AV_PIX_FMT_YUV420P14;
                public static final int AV_PIX_FMT_YUV420P14BE = 303;
                public static final int AV_PIX_FMT_YUV420P14LE = 304;
                public static final int AV_PIX_FMT_YUV420P16;
                public static final int AV_PIX_FMT_YUV420P16BE = 55;
                public static final int AV_PIX_FMT_YUV420P16LE = 54;
                public static final int AV_PIX_FMT_YUV420P9;
                public static final int AV_PIX_FMT_YUV420P9BE = 69;
                public static final int AV_PIX_FMT_YUV420P9LE = 70;
                public static final int AV_PIX_FMT_YUV422P = 4;
                public static final int AV_PIX_FMT_YUV422P10;
                public static final int AV_PIX_FMT_YUV422P10BE = 73;
                public static final int AV_PIX_FMT_YUV422P10LE = 74;
                public static final int AV_PIX_FMT_YUV422P12;
                public static final int AV_PIX_FMT_YUV422P12BE = 305;
                public static final int AV_PIX_FMT_YUV422P12LE = 306;
                public static final int AV_PIX_FMT_YUV422P14;
                public static final int AV_PIX_FMT_YUV422P14BE = 307;
                public static final int AV_PIX_FMT_YUV422P14LE = 308;
                public static final int AV_PIX_FMT_YUV422P16;
                public static final int AV_PIX_FMT_YUV422P16BE = 57;
                public static final int AV_PIX_FMT_YUV422P16LE = 56;
                public static final int AV_PIX_FMT_YUV422P9;
                public static final int AV_PIX_FMT_YUV422P9BE = 79;
                public static final int AV_PIX_FMT_YUV422P9LE = 80;
                public static final int AV_PIX_FMT_YUV440P = 33;
                public static final int AV_PIX_FMT_YUV444P = 5;
                public static final int AV_PIX_FMT_YUV444P10;
                public static final int AV_PIX_FMT_YUV444P10BE = 77;
                public static final int AV_PIX_FMT_YUV444P10LE = 78;
                public static final int AV_PIX_FMT_YUV444P12;
                public static final int AV_PIX_FMT_YUV444P12BE = 309;
                public static final int AV_PIX_FMT_YUV444P12LE = 310;
                public static final int AV_PIX_FMT_YUV444P14;
                public static final int AV_PIX_FMT_YUV444P14BE = 311;
                public static final int AV_PIX_FMT_YUV444P14LE = 312;
                public static final int AV_PIX_FMT_YUV444P16;
                public static final int AV_PIX_FMT_YUV444P16BE = 59;
                public static final int AV_PIX_FMT_YUV444P16LE = 58;
                public static final int AV_PIX_FMT_YUV444P9;
                public static final int AV_PIX_FMT_YUV444P9BE = 75;
                public static final int AV_PIX_FMT_YUV444P9LE = 76;
                public static final int AV_PIX_FMT_YUVA420P = 35;
                public static final int AV_PIX_FMT_YUVA420P10;
                public static final int AV_PIX_FMT_YUVA420P10BE = 97;
                public static final int AV_PIX_FMT_YUVA420P10LE = 98;
                public static final int AV_PIX_FMT_YUVA420P16;
                public static final int AV_PIX_FMT_YUVA420P16BE = 103;
                public static final int AV_PIX_FMT_YUVA420P16LE = 104;
                public static final int AV_PIX_FMT_YUVA420P9;
                public static final int AV_PIX_FMT_YUVA420P9BE = 91;
                public static final int AV_PIX_FMT_YUVA420P9LE = 92;
                public static final int AV_PIX_FMT_YUVA422P = 300;
                public static final int AV_PIX_FMT_YUVA422P10;
                public static final int AV_PIX_FMT_YUVA422P10BE = 99;
                public static final int AV_PIX_FMT_YUVA422P10LE = 100;
                public static final int AV_PIX_FMT_YUVA422P16;
                public static final int AV_PIX_FMT_YUVA422P16BE = 105;
                public static final int AV_PIX_FMT_YUVA422P16LE = 106;
                public static final int AV_PIX_FMT_YUVA422P9;
                public static final int AV_PIX_FMT_YUVA422P9BE = 93;
                public static final int AV_PIX_FMT_YUVA422P9LE = 94;
                public static final int AV_PIX_FMT_YUVA422P_LIBAV = 89;
                public static final int AV_PIX_FMT_YUVA444P = 299;
                public static final int AV_PIX_FMT_YUVA444P10;
                public static final int AV_PIX_FMT_YUVA444P10BE = 101;
                public static final int AV_PIX_FMT_YUVA444P10LE = 102;
                public static final int AV_PIX_FMT_YUVA444P16;
                public static final int AV_PIX_FMT_YUVA444P16BE = 107;
                public static final int AV_PIX_FMT_YUVA444P16LE = 108;
                public static final int AV_PIX_FMT_YUVA444P9;
                public static final int AV_PIX_FMT_YUVA444P9BE = 95;
                public static final int AV_PIX_FMT_YUVA444P9LE = 96;
                public static final int AV_PIX_FMT_YUVA444P_LIBAV = 90;
                public static final int AV_PIX_FMT_YUVJ411P = 320;
                public static final int AV_PIX_FMT_YUVJ420P = 12;
                public static final int AV_PIX_FMT_YUVJ422P = 13;
                public static final int AV_PIX_FMT_YUVJ440P = 34;
                public static final int AV_PIX_FMT_YUVJ444P = 14;
                public static final int AV_PIX_FMT_YUYV422 = 1;
                public static final int AV_ROUND_DOWN = 2;
                public static final int AV_ROUND_INF = 1;
                public static final int AV_ROUND_NEAR_INF = 5;
                public static final int AV_ROUND_PASS_MINMAX = 8192;
                public static final int AV_ROUND_UP = 3;
                public static final int AV_ROUND_ZERO = 0;
                public static final int AV_SAMPLE_FMT_DBL = 4;
                public static final int AV_SAMPLE_FMT_DBLP = 9;
                public static final int AV_SAMPLE_FMT_FLT = 3;
                public static final int AV_SAMPLE_FMT_FLTP = 8;
                public static final int AV_SAMPLE_FMT_NB = 10;
                public static final int AV_SAMPLE_FMT_NONE = -1;
                public static final int AV_SAMPLE_FMT_S16 = 1;
                public static final int AV_SAMPLE_FMT_S16P = 6;
                public static final int AV_SAMPLE_FMT_S32 = 2;
                public static final int AV_SAMPLE_FMT_S32P = 7;
                public static final int AV_SAMPLE_FMT_U8 = 0;
                public static final int AV_SAMPLE_FMT_U8P = 5;
                public static final int AV_TIME_BASE = 1000000;
                public static final int FF_LAMBDA_MAX = 32767;
                public static final int FF_LAMBDA_SCALE = 128;
                public static final int FF_LAMBDA_SHIFT = 7;
                public static final int FF_OPT_TYPE_BINARY = 7;
                public static final int FF_OPT_TYPE_CONST = 128;
                public static final int FF_OPT_TYPE_DOUBLE = 3;
                public static final int FF_OPT_TYPE_FLAGS = 0;
                public static final int FF_OPT_TYPE_FLOAT = 4;
                public static final int FF_OPT_TYPE_INT = 1;
                public static final int FF_OPT_TYPE_INT64 = 2;
                public static final int FF_OPT_TYPE_RATIONAL = 6;
                public static final int FF_OPT_TYPE_STRING = 5;
                public static final int FF_QP2LAMBDA = 118;
                public static final int FF_QUALITY_SCALE = 128;
                public static final int INFINITY;
                public static final double M_E = 2.718281828459045d;
                public static final double M_LN10 = 2.302585092994046d;
                public static final double M_LN2 = 0.6931471805599453d;
                public static final double M_LOG2_10 = 3.321928094887362d;
                public static final double M_PHI = 1.618033988749895d;
                public static final double M_PI = 3.141592653589793d;
                public static final double M_SQRT1_2 = 0.7071067811865476d;
                public static final double M_SQRT2 = 1.4142135623730951d;
                public static final int NAN;
                public static final int PIX_FMT_0BGR32;
                public static final int PIX_FMT_0RGB32;
                public static final int PIX_FMT_ALPHA = 128;
                public static final int PIX_FMT_BE = 1;
                public static final int PIX_FMT_BGR32;
                public static final int PIX_FMT_BGR32_1;
                public static final int PIX_FMT_BGR444;
                public static final int PIX_FMT_BGR48;
                public static final int PIX_FMT_BGR555;
                public static final int PIX_FMT_BGR565;
                public static final int PIX_FMT_BGRA64;
                public static final int PIX_FMT_BITSTREAM = 4;
                public static final int PIX_FMT_GBR24P = 82;
                public static final int PIX_FMT_GBRP10;
                public static final int PIX_FMT_GBRP12;
                public static final int PIX_FMT_GBRP14;
                public static final int PIX_FMT_GBRP16;
                public static final int PIX_FMT_GBRP9;
                public static final int PIX_FMT_GRAY16;
                public static final int PIX_FMT_HWACCEL = 8;
                public static final int PIX_FMT_PAL = 2;
                public static final int PIX_FMT_PLANAR = 16;
                public static final int PIX_FMT_PSEUDOPAL = 64;
                public static final int PIX_FMT_RGB = 32;
                public static final int PIX_FMT_RGB32;
                public static final int PIX_FMT_RGB32_1;
                public static final int PIX_FMT_RGB444;
                public static final int PIX_FMT_RGB48;
                public static final int PIX_FMT_RGB555;
                public static final int PIX_FMT_RGB565;
                public static final int PIX_FMT_RGBA64;
                public static final int PIX_FMT_Y400A = 66;
                public static final int PIX_FMT_YUV420P10;
                public static final int PIX_FMT_YUV420P12;
                public static final int PIX_FMT_YUV420P14;
                public static final int PIX_FMT_YUV420P16;
                public static final int PIX_FMT_YUV420P9;
                public static final int PIX_FMT_YUV422P10;
                public static final int PIX_FMT_YUV422P12;
                public static final int PIX_FMT_YUV422P14;
                public static final int PIX_FMT_YUV422P16;
                public static final int PIX_FMT_YUV422P9;
                public static final int PIX_FMT_YUV444P10;
                public static final int PIX_FMT_YUV444P12;
                public static final int PIX_FMT_YUV444P14;
                public static final int PIX_FMT_YUV444P16;
                public static final int PIX_FMT_YUV444P9;

                @MemberGetter
                public static native int AVERROR_BSF_NOT_FOUND();

                @MemberGetter
                public static native int AVERROR_BUFFER_TOO_SMALL();

                @MemberGetter
                public static native int AVERROR_BUG();

                @MemberGetter
                public static native int AVERROR_BUG2();

                @MemberGetter
                public static native int AVERROR_DECODER_NOT_FOUND();

                @MemberGetter
                public static native int AVERROR_DEMUXER_NOT_FOUND();

                @MemberGetter
                public static native int AVERROR_ENCODER_NOT_FOUND();

                @MemberGetter
                public static native int AVERROR_EOF();

                @MemberGetter
                public static native int AVERROR_EXIT();

                @MemberGetter
                public static native int AVERROR_EXTERNAL();

                @MemberGetter
                public static native int AVERROR_FILTER_NOT_FOUND();

                @MemberGetter
                public static native int AVERROR_INVALIDDATA();

                @MemberGetter
                public static native int AVERROR_MUXER_NOT_FOUND();

                @MemberGetter
                public static native int AVERROR_OPTION_NOT_FOUND();

                @MemberGetter
                public static native int AVERROR_PATCHWELCOME();

                @MemberGetter
                public static native int AVERROR_PROTOCOL_NOT_FOUND();

                @MemberGetter
                public static native int AVERROR_STREAM_NOT_FOUND();

                @MemberGetter
                public static native int AVERROR_UNKNOWN();

                @MemberGetter
                public static native long AV_NOPTS_VALUE();

                @MemberGetter
                public static native int AV_OPT_TYPE_COLOR();

                @MemberGetter
                public static native int AV_OPT_TYPE_DURATION();

                @MemberGetter
                public static native int AV_OPT_TYPE_IMAGE_SIZE();

                @MemberGetter
                public static native int AV_OPT_TYPE_PIXEL_FMT();

                @MemberGetter
                public static native int AV_OPT_TYPE_SAMPLE_FMT();

                @MemberGetter
                public static native int AV_OPT_TYPE_VIDEO_RATE();

                @MemberGetter
                public static native int AV_PIX_FMT_0BGR32();

                @MemberGetter
                public static native int AV_PIX_FMT_0RGB32();

                @MemberGetter
                public static native int AV_PIX_FMT_BGR32();

                @MemberGetter
                public static native int AV_PIX_FMT_BGR32_1();

                @MemberGetter
                public static native int AV_PIX_FMT_BGR444();

                @MemberGetter
                public static native int AV_PIX_FMT_BGR48();

                @MemberGetter
                public static native int AV_PIX_FMT_BGR555();

                @MemberGetter
                public static native int AV_PIX_FMT_BGR565();

                @MemberGetter
                public static native int AV_PIX_FMT_BGRA64();

                @MemberGetter
                public static native int AV_PIX_FMT_GBRP10();

                @MemberGetter
                public static native int AV_PIX_FMT_GBRP12();

                @MemberGetter
                public static native int AV_PIX_FMT_GBRP14();

                @MemberGetter
                public static native int AV_PIX_FMT_GBRP16();

                @MemberGetter
                public static native int AV_PIX_FMT_GBRP9();

                @MemberGetter
                public static native int AV_PIX_FMT_GRAY16();

                @MemberGetter
                public static native int AV_PIX_FMT_RGB32();

                @MemberGetter
                public static native int AV_PIX_FMT_RGB32_1();

                @MemberGetter
                public static native int AV_PIX_FMT_RGB444();

                @MemberGetter
                public static native int AV_PIX_FMT_RGB48();

                @MemberGetter
                public static native int AV_PIX_FMT_RGB555();

                @MemberGetter
                public static native int AV_PIX_FMT_RGB565();

                @MemberGetter
                public static native int AV_PIX_FMT_RGBA64();

                @MemberGetter
                public static native int AV_PIX_FMT_XYZ12();

                @MemberGetter
                public static native int AV_PIX_FMT_YUV420P10();

                @MemberGetter
                public static native int AV_PIX_FMT_YUV420P12();

                @MemberGetter
                public static native int AV_PIX_FMT_YUV420P14();

                @MemberGetter
                public static native int AV_PIX_FMT_YUV420P16();

                @MemberGetter
                public static native int AV_PIX_FMT_YUV420P9();

                @MemberGetter
                public static native int AV_PIX_FMT_YUV422P10();

                @MemberGetter
                public static native int AV_PIX_FMT_YUV422P12();

                @MemberGetter
                public static native int AV_PIX_FMT_YUV422P14();

                @MemberGetter
                public static native int AV_PIX_FMT_YUV422P16();

                @MemberGetter
                public static native int AV_PIX_FMT_YUV422P9();

                @MemberGetter
                public static native int AV_PIX_FMT_YUV444P10();

                @MemberGetter
                public static native int AV_PIX_FMT_YUV444P12();

                @MemberGetter
                public static native int AV_PIX_FMT_YUV444P14();

                @MemberGetter
                public static native int AV_PIX_FMT_YUV444P16();

                @MemberGetter
                public static native int AV_PIX_FMT_YUV444P9();

                @MemberGetter
                public static native int AV_PIX_FMT_YUVA420P10();

                @MemberGetter
                public static native int AV_PIX_FMT_YUVA420P16();

                @MemberGetter
                public static native int AV_PIX_FMT_YUVA420P9();

                @MemberGetter
                public static native int AV_PIX_FMT_YUVA422P10();

                @MemberGetter
                public static native int AV_PIX_FMT_YUVA422P16();

                @MemberGetter
                public static native int AV_PIX_FMT_YUVA422P9();

                @MemberGetter
                public static native int AV_PIX_FMT_YUVA444P10();

                @MemberGetter
                public static native int AV_PIX_FMT_YUVA444P16();

                @MemberGetter
                public static native int AV_PIX_FMT_YUVA444P9();

                @MemberGetter
                public static native int INFINITY();

                @MemberGetter
                public static native int NAN();

                @ByVal
                public static native AVRational av_add_q(@ByVal AVRational aVRational, @ByVal AVRational aVRational2);

                public static native void av_bprint_channel_layout(AVBPrint aVBPrint, int i, @Cast({"uint64_t"}) long j);

                public static native AVBufferRef av_buffer_alloc(int i);

                public static native AVBufferRef av_buffer_allocz(int i);

                public static native AVBufferRef av_buffer_create(@Cast({"uint8_t*"}) BytePointer bytePointer, int i, Free_Pointer_BytePointer free_Pointer_BytePointer, Pointer pointer, int i2);

                public static native AVBufferRef av_buffer_create(@Cast({"uint8_t*"}) ByteBuffer byteBuffer, int i, Free_Pointer_ByteBuffer free_Pointer_ByteBuffer, Pointer pointer, int i2);

                public static native AVBufferRef av_buffer_create(@Cast({"uint8_t*"}) byte[] bArr, int i, Free_Pointer_byte free_Pointer_byte, Pointer pointer, int i2);

                public static native void av_buffer_default_free(Pointer pointer, @Cast({"uint8_t*"}) BytePointer bytePointer);

                public static native void av_buffer_default_free(Pointer pointer, @Cast({"uint8_t*"}) ByteBuffer byteBuffer);

                public static native void av_buffer_default_free(Pointer pointer, @Cast({"uint8_t*"}) byte[] bArr);

                public static native Pointer av_buffer_get_opaque(@Const AVBufferRef aVBufferRef);

                public static native int av_buffer_get_ref_count(@Const AVBufferRef aVBufferRef);

                public static native int av_buffer_is_writable(@Const AVBufferRef aVBufferRef);

                public static native int av_buffer_make_writable(@Cast({"AVBufferRef**"}) PointerPointer pointerPointer);

                public static native int av_buffer_make_writable(@ByPtrPtr AVBufferRef aVBufferRef);

                public static native AVBufferRef av_buffer_pool_get(AVBufferPool aVBufferPool);

                public static native AVBufferPool av_buffer_pool_init(int i, Alloc_int alloc_int);

                public static native void av_buffer_pool_uninit(@Cast({"AVBufferPool**"}) PointerPointer pointerPointer);

                public static native void av_buffer_pool_uninit(@ByPtrPtr AVBufferPool aVBufferPool);

                public static native int av_buffer_realloc(@Cast({"AVBufferRef**"}) PointerPointer pointerPointer, int i);

                public static native int av_buffer_realloc(@ByPtrPtr AVBufferRef aVBufferRef, int i);

                public static native AVBufferRef av_buffer_ref(AVBufferRef aVBufferRef);

                public static native void av_buffer_unref(@Cast({"AVBufferRef**"}) PointerPointer pointerPointer);

                public static native void av_buffer_unref(@ByPtrPtr AVBufferRef aVBufferRef);

                public static native Pointer av_calloc(long j, long j2);

                @Cast({"uint64_t"})
                public static native long av_channel_layout_extract_channel(@Cast({"uint64_t"}) long j, int i);

                public static native int av_cmp_q(@ByVal AVRational aVRational, @ByVal AVRational aVRational2);

                public static native long av_compare_mod(@Cast({"uint64_t"}) long j, @Cast({"uint64_t"}) long j2, @Cast({"uint64_t"}) long j3);

                public static native int av_compare_ts(long j, @ByVal AVRational aVRational, long j2, @ByVal AVRational aVRational2);

                public static native int av_cpu_count();

                @ByVal
                public static native AVRational av_d2q(double d, int i);

                @Cast({"AVClassCategory"})
                public static native int av_default_get_category(Pointer pointer);

                @Cast({"const char*"})
                public static native BytePointer av_default_item_name(Pointer pointer);

                public static native void av_dict_copy(@Cast({"AVDictionary**"}) PointerPointer pointerPointer, AVDictionary aVDictionary, int i);

                public static native void av_dict_copy(@ByPtrPtr AVDictionary aVDictionary, AVDictionary aVDictionary2, int i);

                public static native int av_dict_count(@Const AVDictionary aVDictionary);

                public static native void av_dict_free(@Cast({"AVDictionary**"}) PointerPointer pointerPointer);

                public static native void av_dict_free(@ByPtrPtr AVDictionary aVDictionary);

                public static native AVDictionaryEntry av_dict_get(AVDictionary aVDictionary, @Cast({"const char*"}) BytePointer bytePointer, @Const AVDictionaryEntry aVDictionaryEntry, int i);

                public static native AVDictionaryEntry av_dict_get(AVDictionary aVDictionary, String str, @Const AVDictionaryEntry aVDictionaryEntry, int i);

                public static native int av_dict_parse_string(@Cast({"AVDictionary**"}) PointerPointer pointerPointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, @Cast({"const char*"}) BytePointer bytePointer3, int i);

                public static native int av_dict_parse_string(@ByPtrPtr AVDictionary aVDictionary, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, @Cast({"const char*"}) BytePointer bytePointer3, int i);

                public static native int av_dict_parse_string(@ByPtrPtr AVDictionary aVDictionary, String str, String str2, String str3, int i);

                public static native int av_dict_set(@Cast({"AVDictionary**"}) PointerPointer pointerPointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, int i);

                public static native int av_dict_set(@ByPtrPtr AVDictionary aVDictionary, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, int i);

                public static native int av_dict_set(@ByPtrPtr AVDictionary aVDictionary, String str, String str2, int i);

                @ByVal
                public static native AVRational av_div_q(@ByVal AVRational aVRational, @ByVal AVRational aVRational2);

                public static native Pointer av_dynarray2_add(@ByPtrPtr @Cast({"void**"}) Pointer pointer, IntPointer intPointer, long j, @Cast({"const uint8_t*"}) BytePointer bytePointer);

                public static native Pointer av_dynarray2_add(@ByPtrPtr @Cast({"void**"}) Pointer pointer, IntBuffer intBuffer, long j, @Cast({"const uint8_t*"}) ByteBuffer byteBuffer);

                public static native Pointer av_dynarray2_add(@ByPtrPtr @Cast({"void**"}) Pointer pointer, int[] iArr, long j, @Cast({"const uint8_t*"}) byte[] bArr);

                public static native Pointer av_dynarray2_add(@Cast({"void**"}) PointerPointer pointerPointer, IntPointer intPointer, long j, @Cast({"const uint8_t*"}) BytePointer bytePointer);

                public static native void av_dynarray_add(Pointer pointer, IntPointer intPointer, Pointer pointer2);

                public static native void av_dynarray_add(Pointer pointer, IntBuffer intBuffer, Pointer pointer2);

                public static native void av_dynarray_add(Pointer pointer, int[] iArr, Pointer pointer2);

                public static native int av_find_nearest_q_idx(@ByVal AVRational aVRational, @Const AVRational aVRational2);

                @Const
                @Deprecated
                public static native AVOption av_find_opt(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, int i, int i2);

                @Const
                @Deprecated
                public static native AVOption av_find_opt(Pointer pointer, String str, String str2, int i, int i2);

                public static native void av_force_cpu_flags(int i);

                public static native AVFrame av_frame_alloc();

                public static native AVFrame av_frame_clone(AVFrame aVFrame);

                public static native int av_frame_copy_props(AVFrame aVFrame, @Const AVFrame aVFrame2);

                public static native void av_frame_free(@Cast({"AVFrame**"}) PointerPointer pointerPointer);

                public static native void av_frame_free(@ByPtrPtr AVFrame aVFrame);

                public static native long av_frame_get_best_effort_timestamp(@Const AVFrame aVFrame);

                public static native int av_frame_get_buffer(AVFrame aVFrame, int i);

                public static native long av_frame_get_channel_layout(@Const AVFrame aVFrame);

                public static native int av_frame_get_channels(@Const AVFrame aVFrame);

                public static native int av_frame_get_decode_error_flags(@Const AVFrame aVFrame);

                public static native AVDictionary av_frame_get_metadata(@Const AVFrame aVFrame);

                public static native long av_frame_get_pkt_duration(@Const AVFrame aVFrame);

                public static native long av_frame_get_pkt_pos(@Const AVFrame aVFrame);

                public static native int av_frame_get_pkt_size(@Const AVFrame aVFrame);

                public static native AVBufferRef av_frame_get_plane_buffer(AVFrame aVFrame, int i);

                public static native BytePointer av_frame_get_qp_table(AVFrame aVFrame, IntPointer intPointer, IntPointer intPointer2);

                public static native ByteBuffer av_frame_get_qp_table(AVFrame aVFrame, IntBuffer intBuffer, IntBuffer intBuffer2);

                public static native byte[] av_frame_get_qp_table(AVFrame aVFrame, int[] iArr, int[] iArr2);

                public static native int av_frame_get_sample_rate(@Const AVFrame aVFrame);

                public static native AVFrameSideData av_frame_get_side_data(AVFrame aVFrame, @Cast({"AVFrameSideDataType"}) int i);

                public static native int av_frame_is_writable(AVFrame aVFrame);

                public static native int av_frame_make_writable(AVFrame aVFrame);

                public static native void av_frame_move_ref(AVFrame aVFrame, AVFrame aVFrame2);

                public static native AVFrameSideData av_frame_new_side_data(AVFrame aVFrame, @Cast({"AVFrameSideDataType"}) int i, int i2);

                public static native int av_frame_ref(AVFrame aVFrame, AVFrame aVFrame2);

                public static native void av_frame_set_best_effort_timestamp(AVFrame aVFrame, long j);

                public static native void av_frame_set_channel_layout(AVFrame aVFrame, long j);

                public static native void av_frame_set_channels(AVFrame aVFrame, int i);

                public static native void av_frame_set_decode_error_flags(AVFrame aVFrame, int i);

                public static native void av_frame_set_metadata(AVFrame aVFrame, AVDictionary aVDictionary);

                public static native void av_frame_set_pkt_duration(AVFrame aVFrame, long j);

                public static native void av_frame_set_pkt_pos(AVFrame aVFrame, long j);

                public static native void av_frame_set_pkt_size(AVFrame aVFrame, int i);

                public static native int av_frame_set_qp_table(AVFrame aVFrame, AVBufferRef aVBufferRef, int i, int i2);

                public static native void av_frame_set_sample_rate(AVFrame aVFrame, int i);

                public static native void av_frame_unref(AVFrame aVFrame);

                public static native void av_free(Pointer pointer);

                public static native void av_freep(Pointer pointer);

                @Const
                public static native long av_gcd(long j, long j2);

                @Cast({"AVSampleFormat"})
                public static native int av_get_alt_sample_fmt(@Cast({"AVSampleFormat"}) int i, int i2);

                public static native int av_get_bits_per_pixel(@Const AVPixFmtDescriptor aVPixFmtDescriptor);

                @Deprecated
                public static native int av_get_bits_per_sample_fmt(@Cast({"AVSampleFormat"}) int i);

                public static native int av_get_bytes_per_sample(@Cast({"AVSampleFormat"}) int i);

                @Cast({"const char*"})
                public static native BytePointer av_get_channel_description(@Cast({"uint64_t"}) long j);

                @Cast({"uint64_t"})
                public static native long av_get_channel_layout(@Cast({"const char*"}) BytePointer bytePointer);

                @Cast({"uint64_t"})
                public static native long av_get_channel_layout(String str);

                public static native int av_get_channel_layout_channel_index(@Cast({"uint64_t"}) long j, @Cast({"uint64_t"}) long j2);

                public static native int av_get_channel_layout_nb_channels(@Cast({"uint64_t"}) long j);

                public static native void av_get_channel_layout_string(@Cast({"char*"}) BytePointer bytePointer, int i, int i2, @Cast({"uint64_t"}) long j);

                public static native void av_get_channel_layout_string(@Cast({"char*"}) ByteBuffer byteBuffer, int i, int i2, @Cast({"uint64_t"}) long j);

                public static native void av_get_channel_layout_string(@Cast({"char*"}) byte[] bArr, int i, int i2, @Cast({"uint64_t"}) long j);

                @Cast({"const char*"})
                public static native BytePointer av_get_channel_name(@Cast({"uint64_t"}) long j);

                public static native int av_get_cpu_flags();

                public static native long av_get_default_channel_layout(int i);

                public static native double av_get_double(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const AVOption**"}) PointerPointer pointerPointer);

                public static native double av_get_double(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Const @ByPtrPtr AVOption aVOption);

                public static native double av_get_double(Pointer pointer, String str, @Const @ByPtrPtr AVOption aVOption);

                public static native long av_get_int(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const AVOption**"}) PointerPointer pointerPointer);

                public static native long av_get_int(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Const @ByPtrPtr AVOption aVOption);

                public static native long av_get_int(Pointer pointer, String str, @Const @ByPtrPtr AVOption aVOption);

                @Cast({"const char*"})
                public static native BytePointer av_get_media_type_string(@Cast({"AVMediaType"}) int i);

                @Cast({"AVSampleFormat"})
                public static native int av_get_packed_sample_fmt(@Cast({"AVSampleFormat"}) int i);

                public static native int av_get_padded_bits_per_pixel(@Const AVPixFmtDescriptor aVPixFmtDescriptor);

                @Cast({"char"})
                public static native byte av_get_picture_type_char(@Cast({"AVPictureType"}) int i);

                @Cast({"AVPixelFormat"})
                public static native int av_get_pix_fmt(@Cast({"const char*"}) BytePointer bytePointer);

                @Cast({"AVPixelFormat"})
                public static native int av_get_pix_fmt(String str);

                @Cast({"const char*"})
                public static native BytePointer av_get_pix_fmt_name(@Cast({"AVPixelFormat"}) int i);

                @Cast({"char*"})
                public static native BytePointer av_get_pix_fmt_string(@Cast({"char*"}) BytePointer bytePointer, int i, @Cast({"AVPixelFormat"}) int i2);

                @Cast({"char*"})
                public static native ByteBuffer av_get_pix_fmt_string(@Cast({"char*"}) ByteBuffer byteBuffer, int i, @Cast({"AVPixelFormat"}) int i2);

                @Cast({"char*"})
                public static native byte[] av_get_pix_fmt_string(@Cast({"char*"}) byte[] bArr, int i, @Cast({"AVPixelFormat"}) int i2);

                @Cast({"AVSampleFormat"})
                public static native int av_get_planar_sample_fmt(@Cast({"AVSampleFormat"}) int i);

                @ByVal
                public static native AVRational av_get_q(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const AVOption**"}) PointerPointer pointerPointer);

                @ByVal
                public static native AVRational av_get_q(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Const @ByPtrPtr AVOption aVOption);

                @ByVal
                public static native AVRational av_get_q(Pointer pointer, String str, @Const @ByPtrPtr AVOption aVOption);

                @Cast({"AVSampleFormat"})
                public static native int av_get_sample_fmt(@Cast({"const char*"}) BytePointer bytePointer);

                @Cast({"AVSampleFormat"})
                public static native int av_get_sample_fmt(String str);

                @Cast({"const char*"})
                public static native BytePointer av_get_sample_fmt_name(@Cast({"AVSampleFormat"}) int i);

                @Cast({"char*"})
                public static native BytePointer av_get_sample_fmt_string(@Cast({"char*"}) BytePointer bytePointer, int i, @Cast({"AVSampleFormat"}) int i2);

                @Cast({"char*"})
                public static native ByteBuffer av_get_sample_fmt_string(@Cast({"char*"}) ByteBuffer byteBuffer, int i, @Cast({"AVSampleFormat"}) int i2);

                @Cast({"char*"})
                public static native byte[] av_get_sample_fmt_string(@Cast({"char*"}) byte[] bArr, int i, @Cast({"AVSampleFormat"}) int i2);

                public static native int av_get_standard_channel_layout(@Cast({"unsigned"}) int i, @Cast({"uint64_t*"}) LongPointer longPointer, @ByPtrPtr @Cast({"const char**"}) BytePointer bytePointer);

                public static native int av_get_standard_channel_layout(@Cast({"unsigned"}) int i, @Cast({"uint64_t*"}) LongPointer longPointer, @Cast({"const char**"}) PointerPointer pointerPointer);

                public static native int av_get_standard_channel_layout(@Cast({"unsigned"}) int i, @Cast({"uint64_t*"}) LongBuffer longBuffer, @ByPtrPtr @Cast({"const char**"}) ByteBuffer byteBuffer);

                public static native int av_get_standard_channel_layout(@Cast({"unsigned"}) int i, @Cast({"uint64_t*"}) long[] jArr, @ByPtrPtr @Cast({"const char**"}) byte[] bArr);

                @Cast({"const char*"})
                @Deprecated
                public static native BytePointer av_get_string(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const AVOption**"}) PointerPointer pointerPointer, @Cast({"char*"}) BytePointer bytePointer2, int i);

                @Cast({"const char*"})
                @Deprecated
                public static native BytePointer av_get_string(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Const @ByPtrPtr AVOption aVOption, @Cast({"char*"}) BytePointer bytePointer2, int i);

                @Cast({"const char*"})
                @Deprecated
                public static native BytePointer av_get_string(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Const @ByPtrPtr AVOption aVOption, @Cast({"char*"}) ByteBuffer byteBuffer, int i);

                @Cast({"const char*"})
                @Deprecated
                public static native BytePointer av_get_string(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Const @ByPtrPtr AVOption aVOption, @Cast({"char*"}) byte[] bArr, int i);

                @Deprecated
                public static native String av_get_string(Pointer pointer, String str, @Const @ByPtrPtr AVOption aVOption, @Cast({"char*"}) BytePointer bytePointer, int i);

                @Deprecated
                public static native String av_get_string(Pointer pointer, String str, @Const @ByPtrPtr AVOption aVOption, @Cast({"char*"}) ByteBuffer byteBuffer, int i);

                @Deprecated
                public static native String av_get_string(Pointer pointer, String str, @Const @ByPtrPtr AVOption aVOption, @Cast({"char*"}) byte[] bArr, int i);

                public static native int av_image_alloc(@ByPtrPtr @Cast({"uint8_t**"}) BytePointer bytePointer, IntPointer intPointer, int i, int i2, @Cast({"AVPixelFormat"}) int i3, int i4);

                public static native int av_image_alloc(@Cast({"uint8_t**"}) PointerPointer pointerPointer, IntPointer intPointer, int i, int i2, @Cast({"AVPixelFormat"}) int i3, int i4);

                public static native int av_image_alloc(@ByPtrPtr @Cast({"uint8_t**"}) ByteBuffer byteBuffer, IntBuffer intBuffer, int i, int i2, @Cast({"AVPixelFormat"}) int i3, int i4);

                public static native int av_image_alloc(@ByPtrPtr @Cast({"uint8_t**"}) byte[] bArr, int[] iArr, int i, int i2, @Cast({"AVPixelFormat"}) int i3, int i4);

                public static native int av_image_check_size(@Cast({"unsigned int"}) int i, @Cast({"unsigned int"}) int i2, int i3, Pointer pointer);

                public static native void av_image_copy(@ByPtrPtr @Cast({"uint8_t**"}) BytePointer bytePointer, IntPointer intPointer, @ByPtrPtr @Cast({"const uint8_t**"}) BytePointer bytePointer2, @Const IntPointer intPointer2, @Cast({"AVPixelFormat"}) int i, int i2, int i3);

                public static native void av_image_copy(@Cast({"uint8_t**"}) PointerPointer pointerPointer, IntPointer intPointer, @Cast({"const uint8_t**"}) PointerPointer pointerPointer2, @Const IntPointer intPointer2, @Cast({"AVPixelFormat"}) int i, int i2, int i3);

                public static native void av_image_copy(@ByPtrPtr @Cast({"uint8_t**"}) ByteBuffer byteBuffer, IntBuffer intBuffer, @ByPtrPtr @Cast({"const uint8_t**"}) ByteBuffer byteBuffer2, @Const IntBuffer intBuffer2, @Cast({"AVPixelFormat"}) int i, int i2, int i3);

                public static native void av_image_copy(@ByPtrPtr @Cast({"uint8_t**"}) byte[] bArr, int[] iArr, @ByPtrPtr @Cast({"const uint8_t**"}) byte[] bArr2, @Const int[] iArr2, @Cast({"AVPixelFormat"}) int i, int i2, int i3);

                public static native void av_image_copy_plane(@Cast({"uint8_t*"}) BytePointer bytePointer, int i, @Cast({"const uint8_t*"}) BytePointer bytePointer2, int i2, int i3, int i4);

                public static native void av_image_copy_plane(@Cast({"uint8_t*"}) ByteBuffer byteBuffer, int i, @Cast({"const uint8_t*"}) ByteBuffer byteBuffer2, int i2, int i3, int i4);

                public static native void av_image_copy_plane(@Cast({"uint8_t*"}) byte[] bArr, int i, @Cast({"const uint8_t*"}) byte[] bArr2, int i2, int i3, int i4);

                public static native int av_image_copy_to_buffer(@Cast({"uint8_t*"}) BytePointer bytePointer, int i, @ByPtrPtr @Cast({"const uint8_t*const*"}) BytePointer bytePointer2, @Const IntPointer intPointer, @Cast({"AVPixelFormat"}) int i2, int i3, int i4, int i5);

                public static native int av_image_copy_to_buffer(@Cast({"uint8_t*"}) BytePointer bytePointer, int i, @Cast({"const uint8_t*const*"}) PointerPointer pointerPointer, @Const IntPointer intPointer, @Cast({"AVPixelFormat"}) int i2, int i3, int i4, int i5);

                public static native int av_image_copy_to_buffer(@Cast({"uint8_t*"}) ByteBuffer byteBuffer, int i, @ByPtrPtr @Cast({"const uint8_t*const*"}) ByteBuffer byteBuffer2, @Const IntBuffer intBuffer, @Cast({"AVPixelFormat"}) int i2, int i3, int i4, int i5);

                public static native int av_image_copy_to_buffer(@Cast({"uint8_t*"}) byte[] bArr, int i, @ByPtrPtr @Cast({"const uint8_t*const*"}) byte[] bArr2, @Const int[] iArr, @Cast({"AVPixelFormat"}) int i2, int i3, int i4, int i5);

                public static native int av_image_fill_arrays(@ByPtrPtr @Cast({"uint8_t**"}) BytePointer bytePointer, IntPointer intPointer, @Cast({"const uint8_t*"}) BytePointer bytePointer2, @Cast({"AVPixelFormat"}) int i, int i2, int i3, int i4);

                public static native int av_image_fill_arrays(@Cast({"uint8_t**"}) PointerPointer pointerPointer, IntPointer intPointer, @Cast({"const uint8_t*"}) BytePointer bytePointer, @Cast({"AVPixelFormat"}) int i, int i2, int i3, int i4);

                public static native int av_image_fill_arrays(@ByPtrPtr @Cast({"uint8_t**"}) ByteBuffer byteBuffer, IntBuffer intBuffer, @Cast({"const uint8_t*"}) ByteBuffer byteBuffer2, @Cast({"AVPixelFormat"}) int i, int i2, int i3, int i4);

                public static native int av_image_fill_arrays(@ByPtrPtr @Cast({"uint8_t**"}) byte[] bArr, int[] iArr, @Cast({"const uint8_t*"}) byte[] bArr2, @Cast({"AVPixelFormat"}) int i, int i2, int i3, int i4);

                public static native int av_image_fill_linesizes(IntPointer intPointer, @Cast({"AVPixelFormat"}) int i, int i2);

                public static native int av_image_fill_linesizes(IntBuffer intBuffer, @Cast({"AVPixelFormat"}) int i, int i2);

                public static native int av_image_fill_linesizes(int[] iArr, @Cast({"AVPixelFormat"}) int i, int i2);

                public static native void av_image_fill_max_pixsteps(IntPointer intPointer, IntPointer intPointer2, @Const AVPixFmtDescriptor aVPixFmtDescriptor);

                public static native void av_image_fill_max_pixsteps(IntBuffer intBuffer, IntBuffer intBuffer2, @Const AVPixFmtDescriptor aVPixFmtDescriptor);

                public static native void av_image_fill_max_pixsteps(int[] iArr, int[] iArr2, @Const AVPixFmtDescriptor aVPixFmtDescriptor);

                public static native int av_image_fill_pointers(@ByPtrPtr @Cast({"uint8_t**"}) BytePointer bytePointer, @Cast({"AVPixelFormat"}) int i, int i2, @Cast({"uint8_t*"}) BytePointer bytePointer2, @Const IntPointer intPointer);

                public static native int av_image_fill_pointers(@Cast({"uint8_t**"}) PointerPointer pointerPointer, @Cast({"AVPixelFormat"}) int i, int i2, @Cast({"uint8_t*"}) BytePointer bytePointer, @Const IntPointer intPointer);

                public static native int av_image_fill_pointers(@ByPtrPtr @Cast({"uint8_t**"}) ByteBuffer byteBuffer, @Cast({"AVPixelFormat"}) int i, int i2, @Cast({"uint8_t*"}) ByteBuffer byteBuffer2, @Const IntBuffer intBuffer);

                public static native int av_image_fill_pointers(@ByPtrPtr @Cast({"uint8_t**"}) byte[] bArr, @Cast({"AVPixelFormat"}) int i, int i2, @Cast({"uint8_t*"}) byte[] bArr2, @Const int[] iArr);

                public static native int av_image_get_buffer_size(@Cast({"AVPixelFormat"}) int i, int i2, int i3, int i4);

                public static native int av_image_get_linesize(@Cast({"AVPixelFormat"}) int i, int i2, int i3);

                @Cast({"unsigned"})
                public static native int av_int_list_length_for_size(@Cast({"unsigned"}) int i, @Const Pointer pointer, @Cast({"uint64_t"}) long j);

                @ByVal
                public static native AVRational av_inv_q(@ByVal AVRational aVRational);

                public static native void av_log(Pointer pointer, int i, @Cast({"const char*"}) BytePointer bytePointer);

                public static native void av_log(Pointer pointer, int i, String str);

                public static native void av_log_default_callback(Pointer pointer, int i, @Cast({"const char*"}) BytePointer bytePointer, @ByVal @Cast({"va_list*"}) Pointer pointer2);

                public static native void av_log_default_callback(Pointer pointer, int i, String str, @ByVal @Cast({"va_list*"}) Pointer pointer2);

                public static native void av_log_format_line(Pointer pointer, int i, @Cast({"const char*"}) BytePointer bytePointer, @ByVal @Cast({"va_list*"}) Pointer pointer2, @Cast({"char*"}) BytePointer bytePointer2, int i2, IntPointer intPointer);

                public static native void av_log_format_line(Pointer pointer, int i, @Cast({"const char*"}) BytePointer bytePointer, @ByVal @Cast({"va_list*"}) Pointer pointer2, @Cast({"char*"}) ByteBuffer byteBuffer, int i2, IntBuffer intBuffer);

                public static native void av_log_format_line(Pointer pointer, int i, @Cast({"const char*"}) BytePointer bytePointer, @ByVal @Cast({"va_list*"}) Pointer pointer2, @Cast({"char*"}) byte[] bArr, int i2, int[] iArr);

                public static native void av_log_format_line(Pointer pointer, int i, String str, @ByVal @Cast({"va_list*"}) Pointer pointer2, @Cast({"char*"}) BytePointer bytePointer, int i2, IntPointer intPointer);

                public static native void av_log_format_line(Pointer pointer, int i, String str, @ByVal @Cast({"va_list*"}) Pointer pointer2, @Cast({"char*"}) ByteBuffer byteBuffer, int i2, IntBuffer intBuffer);

                public static native void av_log_format_line(Pointer pointer, int i, String str, @ByVal @Cast({"va_list*"}) Pointer pointer2, @Cast({"char*"}) byte[] bArr, int i2, int[] iArr);

                public static native int av_log_get_level();

                public static native void av_log_set_callback(Arg0_Pointer_int_BytePointer_Pointer arg0_Pointer_int_BytePointer_Pointer);

                public static native void av_log_set_callback(Arg0_Pointer_int_String_Pointer arg0_Pointer_int_String_Pointer);

                public static native void av_log_set_flags(int i);

                public static native void av_log_set_level(int i);

                @Cast({"char*"})
                public static native BytePointer av_make_error_string(@Cast({"char*"}) BytePointer bytePointer, long j, int i);

                @Cast({"char*"})
                public static native ByteBuffer av_make_error_string(@Cast({"char*"}) ByteBuffer byteBuffer, long j, int i);

                @Cast({"char*"})
                public static native byte[] av_make_error_string(@Cast({"char*"}) byte[] bArr, long j, int i);

                public static native Pointer av_malloc(long j);

                public static native Pointer av_malloc_array(long j, long j2);

                public static native Pointer av_mallocz(long j);

                public static native Pointer av_mallocz_array(long j, long j2);

                public static native void av_max_alloc(long j);

                public static native void av_memcpy_backptr(@Cast({"uint8_t*"}) BytePointer bytePointer, int i, int i2);

                public static native void av_memcpy_backptr(@Cast({"uint8_t*"}) ByteBuffer byteBuffer, int i, int i2);

                public static native void av_memcpy_backptr(@Cast({"uint8_t*"}) byte[] bArr, int i, int i2);

                public static native Pointer av_memdup(@Const Pointer pointer, long j);

                @ByVal
                public static native AVRational av_mul_q(@ByVal AVRational aVRational, @ByVal AVRational aVRational2);

                public static native int av_nearer_q(@ByVal AVRational aVRational, @ByVal AVRational aVRational2, @ByVal AVRational aVRational3);

                @Const
                @Deprecated
                public static native AVOption av_next_option(Pointer pointer, @Const AVOption aVOption);

                @Const
                public static native AVClass av_opt_child_class_next(@Const AVClass aVClass, @Const AVClass aVClass2);

                public static native Pointer av_opt_child_next(Pointer pointer, Pointer pointer2);

                public static native int av_opt_eval_double(Pointer pointer, @Const AVOption aVOption, @Cast({"const char*"}) BytePointer bytePointer, DoublePointer doublePointer);

                public static native int av_opt_eval_double(Pointer pointer, @Const AVOption aVOption, @Cast({"const char*"}) BytePointer bytePointer, DoubleBuffer doubleBuffer);

                public static native int av_opt_eval_double(Pointer pointer, @Const AVOption aVOption, @Cast({"const char*"}) BytePointer bytePointer, double[] dArr);

                public static native int av_opt_eval_double(Pointer pointer, @Const AVOption aVOption, String str, DoublePointer doublePointer);

                public static native int av_opt_eval_double(Pointer pointer, @Const AVOption aVOption, String str, DoubleBuffer doubleBuffer);

                public static native int av_opt_eval_double(Pointer pointer, @Const AVOption aVOption, String str, double[] dArr);

                public static native int av_opt_eval_flags(Pointer pointer, @Const AVOption aVOption, @Cast({"const char*"}) BytePointer bytePointer, IntPointer intPointer);

                public static native int av_opt_eval_flags(Pointer pointer, @Const AVOption aVOption, @Cast({"const char*"}) BytePointer bytePointer, IntBuffer intBuffer);

                public static native int av_opt_eval_flags(Pointer pointer, @Const AVOption aVOption, @Cast({"const char*"}) BytePointer bytePointer, int[] iArr);

                public static native int av_opt_eval_flags(Pointer pointer, @Const AVOption aVOption, String str, IntPointer intPointer);

                public static native int av_opt_eval_flags(Pointer pointer, @Const AVOption aVOption, String str, IntBuffer intBuffer);

                public static native int av_opt_eval_flags(Pointer pointer, @Const AVOption aVOption, String str, int[] iArr);

                public static native int av_opt_eval_float(Pointer pointer, @Const AVOption aVOption, @Cast({"const char*"}) BytePointer bytePointer, FloatPointer floatPointer);

                public static native int av_opt_eval_float(Pointer pointer, @Const AVOption aVOption, @Cast({"const char*"}) BytePointer bytePointer, FloatBuffer floatBuffer);

                public static native int av_opt_eval_float(Pointer pointer, @Const AVOption aVOption, @Cast({"const char*"}) BytePointer bytePointer, float[] fArr);

                public static native int av_opt_eval_float(Pointer pointer, @Const AVOption aVOption, String str, FloatPointer floatPointer);

                public static native int av_opt_eval_float(Pointer pointer, @Const AVOption aVOption, String str, FloatBuffer floatBuffer);

                public static native int av_opt_eval_float(Pointer pointer, @Const AVOption aVOption, String str, float[] fArr);

                public static native int av_opt_eval_int(Pointer pointer, @Const AVOption aVOption, @Cast({"const char*"}) BytePointer bytePointer, IntPointer intPointer);

                public static native int av_opt_eval_int(Pointer pointer, @Const AVOption aVOption, @Cast({"const char*"}) BytePointer bytePointer, IntBuffer intBuffer);

                public static native int av_opt_eval_int(Pointer pointer, @Const AVOption aVOption, @Cast({"const char*"}) BytePointer bytePointer, int[] iArr);

                public static native int av_opt_eval_int(Pointer pointer, @Const AVOption aVOption, String str, IntPointer intPointer);

                public static native int av_opt_eval_int(Pointer pointer, @Const AVOption aVOption, String str, IntBuffer intBuffer);

                public static native int av_opt_eval_int(Pointer pointer, @Const AVOption aVOption, String str, int[] iArr);

                public static native int av_opt_eval_int64(Pointer pointer, @Const AVOption aVOption, @Cast({"const char*"}) BytePointer bytePointer, LongPointer longPointer);

                public static native int av_opt_eval_int64(Pointer pointer, @Const AVOption aVOption, @Cast({"const char*"}) BytePointer bytePointer, LongBuffer longBuffer);

                public static native int av_opt_eval_int64(Pointer pointer, @Const AVOption aVOption, @Cast({"const char*"}) BytePointer bytePointer, long[] jArr);

                public static native int av_opt_eval_int64(Pointer pointer, @Const AVOption aVOption, String str, LongPointer longPointer);

                public static native int av_opt_eval_int64(Pointer pointer, @Const AVOption aVOption, String str, LongBuffer longBuffer);

                public static native int av_opt_eval_int64(Pointer pointer, @Const AVOption aVOption, String str, long[] jArr);

                public static native int av_opt_eval_q(Pointer pointer, @Const AVOption aVOption, @Cast({"const char*"}) BytePointer bytePointer, AVRational aVRational);

                public static native int av_opt_eval_q(Pointer pointer, @Const AVOption aVOption, String str, AVRational aVRational);

                @Const
                public static native AVOption av_opt_find(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, int i, int i2);

                @Const
                public static native AVOption av_opt_find(Pointer pointer, String str, String str2, int i, int i2);

                @Const
                public static native AVOption av_opt_find2(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, int i, int i2, @ByPtrPtr @Cast({"void**"}) Pointer pointer2);

                @Const
                public static native AVOption av_opt_find2(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, int i, int i2, @Cast({"void**"}) PointerPointer pointerPointer);

                @Const
                public static native AVOption av_opt_find2(Pointer pointer, String str, String str2, int i, int i2, @ByPtrPtr @Cast({"void**"}) Pointer pointer2);

                public static native int av_opt_flag_is_set(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2);

                public static native int av_opt_flag_is_set(Pointer pointer, String str, String str2);

                public static native void av_opt_free(Pointer pointer);

                public static native void av_opt_freep_ranges(@Cast({"AVOptionRanges**"}) PointerPointer pointerPointer);

                public static native void av_opt_freep_ranges(@ByPtrPtr AVOptionRanges aVOptionRanges);

                public static native int av_opt_get(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, @ByPtrPtr @Cast({"uint8_t**"}) BytePointer bytePointer2);

                public static native int av_opt_get(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, @Cast({"uint8_t**"}) PointerPointer pointerPointer);

                public static native int av_opt_get(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, @ByPtrPtr @Cast({"uint8_t**"}) ByteBuffer byteBuffer);

                public static native int av_opt_get(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, @ByPtrPtr @Cast({"uint8_t**"}) byte[] bArr);

                public static native int av_opt_get(Pointer pointer, String str, int i, @ByPtrPtr @Cast({"uint8_t**"}) BytePointer bytePointer);

                public static native int av_opt_get(Pointer pointer, String str, int i, @ByPtrPtr @Cast({"uint8_t**"}) ByteBuffer byteBuffer);

                public static native int av_opt_get(Pointer pointer, String str, int i, @ByPtrPtr @Cast({"uint8_t**"}) byte[] bArr);

                public static native int av_opt_get_double(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, DoublePointer doublePointer);

                public static native int av_opt_get_double(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, DoubleBuffer doubleBuffer);

                public static native int av_opt_get_double(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, double[] dArr);

                public static native int av_opt_get_double(Pointer pointer, String str, int i, DoublePointer doublePointer);

                public static native int av_opt_get_double(Pointer pointer, String str, int i, DoubleBuffer doubleBuffer);

                public static native int av_opt_get_double(Pointer pointer, String str, int i, double[] dArr);

                public static native int av_opt_get_image_size(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, IntPointer intPointer, IntPointer intPointer2);

                public static native int av_opt_get_image_size(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, IntBuffer intBuffer, IntBuffer intBuffer2);

                public static native int av_opt_get_image_size(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, int[] iArr, int[] iArr2);

                public static native int av_opt_get_image_size(Pointer pointer, String str, int i, IntPointer intPointer, IntPointer intPointer2);

                public static native int av_opt_get_image_size(Pointer pointer, String str, int i, IntBuffer intBuffer, IntBuffer intBuffer2);

                public static native int av_opt_get_image_size(Pointer pointer, String str, int i, int[] iArr, int[] iArr2);

                public static native int av_opt_get_int(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, LongPointer longPointer);

                public static native int av_opt_get_int(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, LongBuffer longBuffer);

                public static native int av_opt_get_int(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, long[] jArr);

                public static native int av_opt_get_int(Pointer pointer, String str, int i, LongPointer longPointer);

                public static native int av_opt_get_int(Pointer pointer, String str, int i, LongBuffer longBuffer);

                public static native int av_opt_get_int(Pointer pointer, String str, int i, long[] jArr);

                public static native int av_opt_get_key_value(@ByPtrPtr @Cast({"const char**"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, @Cast({"const char*"}) BytePointer bytePointer3, @Cast({"unsigned"}) int i, @ByPtrPtr @Cast({"char**"}) BytePointer bytePointer4, @ByPtrPtr @Cast({"char**"}) BytePointer bytePointer5);

                public static native int av_opt_get_key_value(@ByPtrPtr @Cast({"const char**"}) BytePointer bytePointer, String str, String str2, @Cast({"unsigned"}) int i, @ByPtrPtr @Cast({"char**"}) BytePointer bytePointer2, @ByPtrPtr @Cast({"char**"}) BytePointer bytePointer3);

                public static native int av_opt_get_key_value(@Cast({"const char**"}) PointerPointer pointerPointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, @Cast({"unsigned"}) int i, @Cast({"char**"}) PointerPointer pointerPointer2, @Cast({"char**"}) PointerPointer pointerPointer3);

                public static native int av_opt_get_key_value(@ByPtrPtr @Cast({"const char**"}) ByteBuffer byteBuffer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, @Cast({"unsigned"}) int i, @ByPtrPtr @Cast({"char**"}) ByteBuffer byteBuffer2, @ByPtrPtr @Cast({"char**"}) ByteBuffer byteBuffer3);

                public static native int av_opt_get_key_value(@ByPtrPtr @Cast({"const char**"}) ByteBuffer byteBuffer, String str, String str2, @Cast({"unsigned"}) int i, @ByPtrPtr @Cast({"char**"}) ByteBuffer byteBuffer2, @ByPtrPtr @Cast({"char**"}) ByteBuffer byteBuffer3);

                public static native int av_opt_get_key_value(@ByPtrPtr @Cast({"const char**"}) byte[] bArr, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, @Cast({"unsigned"}) int i, @ByPtrPtr @Cast({"char**"}) byte[] bArr2, @ByPtrPtr @Cast({"char**"}) byte[] bArr3);

                public static native int av_opt_get_key_value(@ByPtrPtr @Cast({"const char**"}) byte[] bArr, String str, String str2, @Cast({"unsigned"}) int i, @ByPtrPtr @Cast({"char**"}) byte[] bArr2, @ByPtrPtr @Cast({"char**"}) byte[] bArr3);

                public static native int av_opt_get_pixel_fmt(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, @Cast({"AVPixelFormat*"}) IntPointer intPointer);

                public static native int av_opt_get_pixel_fmt(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, @Cast({"AVPixelFormat*"}) IntBuffer intBuffer);

                public static native int av_opt_get_pixel_fmt(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, @Cast({"AVPixelFormat*"}) int[] iArr);

                public static native int av_opt_get_pixel_fmt(Pointer pointer, String str, int i, @Cast({"AVPixelFormat*"}) IntPointer intPointer);

                public static native int av_opt_get_pixel_fmt(Pointer pointer, String str, int i, @Cast({"AVPixelFormat*"}) IntBuffer intBuffer);

                public static native int av_opt_get_pixel_fmt(Pointer pointer, String str, int i, @Cast({"AVPixelFormat*"}) int[] iArr);

                public static native int av_opt_get_q(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, AVRational aVRational);

                public static native int av_opt_get_q(Pointer pointer, String str, int i, AVRational aVRational);

                public static native int av_opt_get_sample_fmt(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, @Cast({"AVSampleFormat*"}) IntPointer intPointer);

                public static native int av_opt_get_sample_fmt(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, @Cast({"AVSampleFormat*"}) IntBuffer intBuffer);

                public static native int av_opt_get_sample_fmt(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, @Cast({"AVSampleFormat*"}) int[] iArr);

                public static native int av_opt_get_sample_fmt(Pointer pointer, String str, int i, @Cast({"AVSampleFormat*"}) IntPointer intPointer);

                public static native int av_opt_get_sample_fmt(Pointer pointer, String str, int i, @Cast({"AVSampleFormat*"}) IntBuffer intBuffer);

                public static native int av_opt_get_sample_fmt(Pointer pointer, String str, int i, @Cast({"AVSampleFormat*"}) int[] iArr);

                public static native int av_opt_get_video_rate(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, AVRational aVRational);

                public static native int av_opt_get_video_rate(Pointer pointer, String str, int i, AVRational aVRational);

                @Const
                public static native AVOption av_opt_next(Pointer pointer, @Const AVOption aVOption);

                public static native Pointer av_opt_ptr(@Const AVClass aVClass, Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer);

                public static native Pointer av_opt_ptr(@Const AVClass aVClass, Pointer pointer, String str);

                public static native int av_opt_query_ranges(@Cast({"AVOptionRanges**"}) PointerPointer pointerPointer, Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i);

                public static native int av_opt_query_ranges(@ByPtrPtr AVOptionRanges aVOptionRanges, Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i);

                public static native int av_opt_query_ranges(@ByPtrPtr AVOptionRanges aVOptionRanges, Pointer pointer, String str, int i);

                public static native int av_opt_query_ranges_default(@Cast({"AVOptionRanges**"}) PointerPointer pointerPointer, Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i);

                public static native int av_opt_query_ranges_default(@ByPtrPtr AVOptionRanges aVOptionRanges, Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i);

                public static native int av_opt_query_ranges_default(@ByPtrPtr AVOptionRanges aVOptionRanges, Pointer pointer, String str, int i);

                public static native int av_opt_set(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, int i);

                public static native int av_opt_set(Pointer pointer, String str, String str2, int i);

                public static native int av_opt_set_bin(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const uint8_t*"}) BytePointer bytePointer2, int i, int i2);

                public static native int av_opt_set_bin(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const uint8_t*"}) ByteBuffer byteBuffer, int i, int i2);

                public static native int av_opt_set_bin(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const uint8_t*"}) byte[] bArr, int i, int i2);

                public static native int av_opt_set_bin(Pointer pointer, String str, @Cast({"const uint8_t*"}) BytePointer bytePointer, int i, int i2);

                public static native int av_opt_set_bin(Pointer pointer, String str, @Cast({"const uint8_t*"}) ByteBuffer byteBuffer, int i, int i2);

                public static native int av_opt_set_bin(Pointer pointer, String str, @Cast({"const uint8_t*"}) byte[] bArr, int i, int i2);

                public static native void av_opt_set_defaults(Pointer pointer);

                @Deprecated
                public static native void av_opt_set_defaults2(Pointer pointer, int i, int i2);

                public static native int av_opt_set_dict(Pointer pointer, @Cast({"AVDictionary**"}) PointerPointer pointerPointer);

                public static native int av_opt_set_dict(Pointer pointer, @ByPtrPtr AVDictionary aVDictionary);

                public static native int av_opt_set_double(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, double d, int i);

                public static native int av_opt_set_double(Pointer pointer, String str, double d, int i);

                public static native int av_opt_set_from_string(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @ByPtrPtr @Cast({"const char*const*"}) BytePointer bytePointer2, @Cast({"const char*"}) BytePointer bytePointer3, @Cast({"const char*"}) BytePointer bytePointer4);

                public static native int av_opt_set_from_string(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*const*"}) PointerPointer pointerPointer, @Cast({"const char*"}) BytePointer bytePointer2, @Cast({"const char*"}) BytePointer bytePointer3);

                public static native int av_opt_set_from_string(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @ByPtrPtr @Cast({"const char*const*"}) ByteBuffer byteBuffer, @Cast({"const char*"}) BytePointer bytePointer2, @Cast({"const char*"}) BytePointer bytePointer3);

                public static native int av_opt_set_from_string(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @ByPtrPtr @Cast({"const char*const*"}) byte[] bArr, @Cast({"const char*"}) BytePointer bytePointer2, @Cast({"const char*"}) BytePointer bytePointer3);

                public static native int av_opt_set_from_string(Pointer pointer, String str, @ByPtrPtr @Cast({"const char*const*"}) BytePointer bytePointer, String str2, String str3);

                public static native int av_opt_set_from_string(Pointer pointer, String str, @ByPtrPtr @Cast({"const char*const*"}) ByteBuffer byteBuffer, String str2, String str3);

                public static native int av_opt_set_from_string(Pointer pointer, String str, @ByPtrPtr @Cast({"const char*const*"}) byte[] bArr, String str2, String str3);

                public static native int av_opt_set_image_size(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i, int i2, int i3);

                public static native int av_opt_set_image_size(Pointer pointer, String str, int i, int i2, int i3);

                public static native int av_opt_set_int(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, long j, int i);

                public static native int av_opt_set_int(Pointer pointer, String str, long j, int i);

                public static native int av_opt_set_pixel_fmt(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"AVPixelFormat"}) int i, int i2);

                public static native int av_opt_set_pixel_fmt(Pointer pointer, String str, @Cast({"AVPixelFormat"}) int i, int i2);

                public static native int av_opt_set_q(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @ByVal AVRational aVRational, int i);

                public static native int av_opt_set_q(Pointer pointer, String str, @ByVal AVRational aVRational, int i);

                public static native int av_opt_set_sample_fmt(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"AVSampleFormat"}) int i, int i2);

                public static native int av_opt_set_sample_fmt(Pointer pointer, String str, @Cast({"AVSampleFormat"}) int i, int i2);

                public static native int av_opt_set_video_rate(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @ByVal AVRational aVRational, int i);

                public static native int av_opt_set_video_rate(Pointer pointer, String str, @ByVal AVRational aVRational, int i);

                public static native int av_opt_show2(Pointer pointer, Pointer pointer2, int i, int i2);

                public static native int av_parse_cpu_caps(@Cast({"unsigned*"}) IntPointer intPointer, @Cast({"const char*"}) BytePointer bytePointer);

                public static native int av_parse_cpu_caps(@Cast({"unsigned*"}) IntPointer intPointer, String str);

                public static native int av_parse_cpu_caps(@Cast({"unsigned*"}) IntBuffer intBuffer, @Cast({"const char*"}) BytePointer bytePointer);

                public static native int av_parse_cpu_caps(@Cast({"unsigned*"}) IntBuffer intBuffer, String str);

                public static native int av_parse_cpu_caps(@Cast({"unsigned*"}) int[] iArr, @Cast({"const char*"}) BytePointer bytePointer);

                public static native int av_parse_cpu_caps(@Cast({"unsigned*"}) int[] iArr, String str);

                @Deprecated
                public static native int av_parse_cpu_flags(@Cast({"const char*"}) BytePointer bytePointer);

                @Deprecated
                public static native int av_parse_cpu_flags(String str);

                public static native int av_pix_fmt_count_planes(@Cast({"AVPixelFormat"}) int i);

                @Const
                public static native AVPixFmtDescriptor av_pix_fmt_desc_get(@Cast({"AVPixelFormat"}) int i);

                @Cast({"AVPixelFormat"})
                public static native int av_pix_fmt_desc_get_id(@Const AVPixFmtDescriptor aVPixFmtDescriptor);

                @Const
                public static native AVPixFmtDescriptor av_pix_fmt_desc_next(@Const AVPixFmtDescriptor aVPixFmtDescriptor);

                @MemberGetter
                @Const
                public static native AVPixFmtDescriptor av_pix_fmt_descriptors();

                @MemberGetter
                @ByVal
                @Const
                public static native AVPixFmtDescriptor av_pix_fmt_descriptors(int i);

                public static native int av_pix_fmt_get_chroma_sub_sample(@Cast({"AVPixelFormat"}) int i, IntPointer intPointer, IntPointer intPointer2);

                public static native int av_pix_fmt_get_chroma_sub_sample(@Cast({"AVPixelFormat"}) int i, IntBuffer intBuffer, IntBuffer intBuffer2);

                public static native int av_pix_fmt_get_chroma_sub_sample(@Cast({"AVPixelFormat"}) int i, int[] iArr, int[] iArr2);

                @Cast({"AVPixelFormat"})
                public static native int av_pix_fmt_swap_endianness(@Cast({"AVPixelFormat"}) int i);

                public static native double av_q2d(@ByVal AVRational aVRational);

                public static native void av_read_image_line(@Cast({"uint16_t*"}) ShortPointer shortPointer, @ByPtrPtr @Cast({"const uint8_t**"}) BytePointer bytePointer, @Const IntPointer intPointer, @Const AVPixFmtDescriptor aVPixFmtDescriptor, int i, int i2, int i3, int i4, int i5);

                public static native void av_read_image_line(@Cast({"uint16_t*"}) ShortPointer shortPointer, @Cast({"const uint8_t**"}) PointerPointer pointerPointer, @Const IntPointer intPointer, @Const AVPixFmtDescriptor aVPixFmtDescriptor, int i, int i2, int i3, int i4, int i5);

                public static native void av_read_image_line(@Cast({"uint16_t*"}) ShortBuffer shortBuffer, @ByPtrPtr @Cast({"const uint8_t**"}) ByteBuffer byteBuffer, @Const IntBuffer intBuffer, @Const AVPixFmtDescriptor aVPixFmtDescriptor, int i, int i2, int i3, int i4, int i5);

                public static native void av_read_image_line(@Cast({"uint16_t*"}) short[] sArr, @ByPtrPtr @Cast({"const uint8_t**"}) byte[] bArr, @Const int[] iArr, @Const AVPixFmtDescriptor aVPixFmtDescriptor, int i, int i2, int i3, int i4, int i5);

                public static native Pointer av_realloc(Pointer pointer, long j);

                public static native Pointer av_realloc_array(Pointer pointer, long j, long j2);

                public static native Pointer av_realloc_f(Pointer pointer, long j, long j2);

                public static native int av_reallocp_array(Pointer pointer, long j, long j2);

                public static native int av_reduce(IntPointer intPointer, IntPointer intPointer2, long j, long j2, long j3);

                public static native int av_reduce(IntBuffer intBuffer, IntBuffer intBuffer2, long j, long j2, long j3);

                public static native int av_reduce(int[] iArr, int[] iArr2, long j, long j2, long j3);

                public static native long av_rescale(long j, long j2, long j3);

                public static native long av_rescale_delta(@ByVal AVRational aVRational, long j, @ByVal AVRational aVRational2, int i, LongPointer longPointer, @ByVal AVRational aVRational3);

                public static native long av_rescale_delta(@ByVal AVRational aVRational, long j, @ByVal AVRational aVRational2, int i, LongBuffer longBuffer, @ByVal AVRational aVRational3);

                public static native long av_rescale_delta(@ByVal AVRational aVRational, long j, @ByVal AVRational aVRational2, int i, long[] jArr, @ByVal AVRational aVRational3);

                public static native long av_rescale_q(long j, @ByVal AVRational aVRational, @ByVal AVRational aVRational2);

                public static native long av_rescale_q_rnd(long j, @ByVal AVRational aVRational, @ByVal AVRational aVRational2, @Cast({"AVRounding"}) int i);

                public static native long av_rescale_rnd(long j, long j2, long j3, @Cast({"AVRounding"}) int i);

                public static native int av_sample_fmt_is_planar(@Cast({"AVSampleFormat"}) int i);

                public static native int av_samples_alloc(@ByPtrPtr @Cast({"uint8_t**"}) BytePointer bytePointer, IntPointer intPointer, int i, int i2, @Cast({"AVSampleFormat"}) int i3, int i4);

                public static native int av_samples_alloc(@Cast({"uint8_t**"}) PointerPointer pointerPointer, IntPointer intPointer, int i, int i2, @Cast({"AVSampleFormat"}) int i3, int i4);

                public static native int av_samples_alloc(@ByPtrPtr @Cast({"uint8_t**"}) ByteBuffer byteBuffer, IntBuffer intBuffer, int i, int i2, @Cast({"AVSampleFormat"}) int i3, int i4);

                public static native int av_samples_alloc(@ByPtrPtr @Cast({"uint8_t**"}) byte[] bArr, int[] iArr, int i, int i2, @Cast({"AVSampleFormat"}) int i3, int i4);

                public static native int av_samples_alloc_array_and_samples(@Cast({"uint8_t***"}) PointerPointer pointerPointer, IntPointer intPointer, int i, int i2, @Cast({"AVSampleFormat"}) int i3, int i4);

                public static native int av_samples_alloc_array_and_samples(@Cast({"uint8_t***"}) PointerPointer pointerPointer, IntBuffer intBuffer, int i, int i2, @Cast({"AVSampleFormat"}) int i3, int i4);

                public static native int av_samples_alloc_array_and_samples(@Cast({"uint8_t***"}) PointerPointer pointerPointer, int[] iArr, int i, int i2, @Cast({"AVSampleFormat"}) int i3, int i4);

                public static native int av_samples_copy(@ByPtrPtr @Cast({"uint8_t**"}) BytePointer bytePointer, @ByPtrPtr @Cast({"uint8_t*const*"}) BytePointer bytePointer2, int i, int i2, int i3, int i4, @Cast({"AVSampleFormat"}) int i5);

                public static native int av_samples_copy(@Cast({"uint8_t**"}) PointerPointer pointerPointer, @Cast({"uint8_t*const*"}) PointerPointer pointerPointer2, int i, int i2, int i3, int i4, @Cast({"AVSampleFormat"}) int i5);

                public static native int av_samples_copy(@ByPtrPtr @Cast({"uint8_t**"}) ByteBuffer byteBuffer, @ByPtrPtr @Cast({"uint8_t*const*"}) ByteBuffer byteBuffer2, int i, int i2, int i3, int i4, @Cast({"AVSampleFormat"}) int i5);

                public static native int av_samples_copy(@ByPtrPtr @Cast({"uint8_t**"}) byte[] bArr, @ByPtrPtr @Cast({"uint8_t*const*"}) byte[] bArr2, int i, int i2, int i3, int i4, @Cast({"AVSampleFormat"}) int i5);

                public static native int av_samples_fill_arrays(@ByPtrPtr @Cast({"uint8_t**"}) BytePointer bytePointer, IntPointer intPointer, @Cast({"const uint8_t*"}) BytePointer bytePointer2, int i, int i2, @Cast({"AVSampleFormat"}) int i3, int i4);

                public static native int av_samples_fill_arrays(@Cast({"uint8_t**"}) PointerPointer pointerPointer, IntPointer intPointer, @Cast({"const uint8_t*"}) BytePointer bytePointer, int i, int i2, @Cast({"AVSampleFormat"}) int i3, int i4);

                public static native int av_samples_fill_arrays(@ByPtrPtr @Cast({"uint8_t**"}) ByteBuffer byteBuffer, IntBuffer intBuffer, @Cast({"const uint8_t*"}) ByteBuffer byteBuffer2, int i, int i2, @Cast({"AVSampleFormat"}) int i3, int i4);

                public static native int av_samples_fill_arrays(@ByPtrPtr @Cast({"uint8_t**"}) byte[] bArr, int[] iArr, @Cast({"const uint8_t*"}) byte[] bArr2, int i, int i2, @Cast({"AVSampleFormat"}) int i3, int i4);

                public static native int av_samples_get_buffer_size(IntPointer intPointer, int i, int i2, @Cast({"AVSampleFormat"}) int i3, int i4);

                public static native int av_samples_get_buffer_size(IntBuffer intBuffer, int i, int i2, @Cast({"AVSampleFormat"}) int i3, int i4);

                public static native int av_samples_get_buffer_size(int[] iArr, int i, int i2, @Cast({"AVSampleFormat"}) int i3, int i4);

                public static native int av_samples_set_silence(@ByPtrPtr @Cast({"uint8_t**"}) BytePointer bytePointer, int i, int i2, int i3, @Cast({"AVSampleFormat"}) int i4);

                public static native int av_samples_set_silence(@Cast({"uint8_t**"}) PointerPointer pointerPointer, int i, int i2, int i3, @Cast({"AVSampleFormat"}) int i4);

                public static native int av_samples_set_silence(@ByPtrPtr @Cast({"uint8_t**"}) ByteBuffer byteBuffer, int i, int i2, int i3, @Cast({"AVSampleFormat"}) int i4);

                public static native int av_samples_set_silence(@ByPtrPtr @Cast({"uint8_t**"}) byte[] bArr, int i, int i2, int i3, @Cast({"AVSampleFormat"}) int i4);

                @Deprecated
                public static native void av_set_cpu_flags_mask(int i);

                @Const
                @Deprecated
                public static native AVOption av_set_double(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, double d);

                @Const
                @Deprecated
                public static native AVOption av_set_double(Pointer pointer, String str, double d);

                @Const
                @Deprecated
                public static native AVOption av_set_int(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, long j);

                @Const
                @Deprecated
                public static native AVOption av_set_int(Pointer pointer, String str, long j);

                public static native int av_set_options_string(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, @Cast({"const char*"}) BytePointer bytePointer3);

                public static native int av_set_options_string(Pointer pointer, String str, String str2, String str3);

                @Const
                @Deprecated
                public static native AVOption av_set_q(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @ByVal AVRational aVRational);

                @Const
                @Deprecated
                public static native AVOption av_set_q(Pointer pointer, String str, @ByVal AVRational aVRational);

                @Deprecated
                public static native int av_set_string3(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, int i, @Cast({"const AVOption**"}) PointerPointer pointerPointer);

                @Deprecated
                public static native int av_set_string3(Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, @Cast({"const char*"}) BytePointer bytePointer2, int i, @Const @ByPtrPtr AVOption aVOption);

                @Deprecated
                public static native int av_set_string3(Pointer pointer, String str, String str2, int i, @Const @ByPtrPtr AVOption aVOption);

                public static native int av_size_mult(long j, long j2, SizeTPointer sizeTPointer);

                @Cast({"char*"})
                public static native BytePointer av_strdup(@Cast({"const char*"}) BytePointer bytePointer);

                @Cast({"char*"})
                public static native ByteBuffer av_strdup(String str);

                public static native int av_strerror(int i, @Cast({"char*"}) BytePointer bytePointer, long j);

                public static native int av_strerror(int i, @Cast({"char*"}) ByteBuffer byteBuffer, long j);

                public static native int av_strerror(int i, @Cast({"char*"}) byte[] bArr, long j);

                @ByVal
                public static native AVRational av_sub_q(@ByVal AVRational aVRational, @ByVal AVRational aVRational2);

                public static native void av_vlog(Pointer pointer, int i, @Cast({"const char*"}) BytePointer bytePointer, @ByVal @Cast({"va_list*"}) Pointer pointer2);

                public static native void av_vlog(Pointer pointer, int i, String str, @ByVal @Cast({"va_list*"}) Pointer pointer2);

                public static native void av_write_image_line(@Cast({"const uint16_t*"}) ShortPointer shortPointer, @ByPtrPtr @Cast({"uint8_t**"}) BytePointer bytePointer, @Const IntPointer intPointer, @Const AVPixFmtDescriptor aVPixFmtDescriptor, int i, int i2, int i3, int i4);

                public static native void av_write_image_line(@Cast({"const uint16_t*"}) ShortPointer shortPointer, @Cast({"uint8_t**"}) PointerPointer pointerPointer, @Const IntPointer intPointer, @Const AVPixFmtDescriptor aVPixFmtDescriptor, int i, int i2, int i3, int i4);

                public static native void av_write_image_line(@Cast({"const uint16_t*"}) ShortBuffer shortBuffer, @ByPtrPtr @Cast({"uint8_t**"}) ByteBuffer byteBuffer, @Const IntBuffer intBuffer, @Const AVPixFmtDescriptor aVPixFmtDescriptor, int i, int i2, int i3, int i4);

                public static native void av_write_image_line(@Cast({"const uint16_t*"}) short[] sArr, @ByPtrPtr @Cast({"uint8_t**"}) byte[] bArr, @Const int[] iArr, @Const AVPixFmtDescriptor aVPixFmtDescriptor, int i, int i2, int i3, int i4);

                public static native Pointer av_x_if_null(@Const Pointer pointer, @Const Pointer pointer2);

                @Cast({"AVDictionary**"})
                public static native PointerPointer avpriv_frame_get_metadatap(AVFrame aVFrame);

                public static native int avpriv_set_systematic_pal2(@Cast({"uint32_t*"}) IntPointer intPointer, @Cast({"AVPixelFormat"}) int i);

                public static native int avpriv_set_systematic_pal2(@Cast({"uint32_t*"}) IntBuffer intBuffer, @Cast({"AVPixelFormat"}) int i);

                public static native int avpriv_set_systematic_pal2(@Cast({"uint32_t*"}) int[] iArr, @Cast({"AVPixelFormat"}) int i);

                @Cast({"const char*"})
                public static native BytePointer avutil_configuration();

                @Cast({"const char*"})
                public static native BytePointer avutil_license();

                @Cast({"unsigned"})
                public static native int avutil_version();

                public static native void ff_check_pixfmt_descriptors();

                static {
/* 43 */            Loader.load();
/* 274 */           AV_NOPTS_VALUE = AV_NOPTS_VALUE();
/* 417 */           AVERROR_BSF_NOT_FOUND = AVERROR_BSF_NOT_FOUND();
/* 420 */           AVERROR_BUG = AVERROR_BUG();
/* 423 */           AVERROR_BUFFER_TOO_SMALL = AVERROR_BUFFER_TOO_SMALL();
/* 426 */           AVERROR_DECODER_NOT_FOUND = AVERROR_DECODER_NOT_FOUND();
/* 429 */           AVERROR_DEMUXER_NOT_FOUND = AVERROR_DEMUXER_NOT_FOUND();
/* 432 */           AVERROR_ENCODER_NOT_FOUND = AVERROR_ENCODER_NOT_FOUND();
/* 435 */           AVERROR_EOF = AVERROR_EOF();
/* 438 */           AVERROR_EXIT = AVERROR_EXIT();
/* 441 */           AVERROR_EXTERNAL = AVERROR_EXTERNAL();
/* 444 */           AVERROR_FILTER_NOT_FOUND = AVERROR_FILTER_NOT_FOUND();
/* 447 */           AVERROR_INVALIDDATA = AVERROR_INVALIDDATA();
/* 450 */           AVERROR_MUXER_NOT_FOUND = AVERROR_MUXER_NOT_FOUND();
/* 453 */           AVERROR_OPTION_NOT_FOUND = AVERROR_OPTION_NOT_FOUND();
/* 456 */           AVERROR_PATCHWELCOME = AVERROR_PATCHWELCOME();
/* 459 */           AVERROR_PROTOCOL_NOT_FOUND = AVERROR_PROTOCOL_NOT_FOUND();
/* 463 */           AVERROR_STREAM_NOT_FOUND = AVERROR_STREAM_NOT_FOUND();
/* 469 */           AVERROR_BUG2 = AVERROR_BUG2();
/* 472 */           AVERROR_UNKNOWN = AVERROR_UNKNOWN();
/* 863 */           NAN = NAN();
/* 867 */           INFINITY = INFINITY();
/* 2791 */          AV_PIX_FMT_RGB32 = AV_PIX_FMT_RGB32();
/* 2793 */          AV_PIX_FMT_RGB32_1 = AV_PIX_FMT_RGB32_1();
/* 2795 */          AV_PIX_FMT_BGR32 = AV_PIX_FMT_BGR32();
/* 2797 */          AV_PIX_FMT_BGR32_1 = AV_PIX_FMT_BGR32_1();
/* 2799 */          AV_PIX_FMT_0RGB32 = AV_PIX_FMT_0RGB32();
/* 2801 */          AV_PIX_FMT_0BGR32 = AV_PIX_FMT_0BGR32();
/* 2804 */          AV_PIX_FMT_GRAY16 = AV_PIX_FMT_GRAY16();
/* 2806 */          AV_PIX_FMT_RGB48 = AV_PIX_FMT_RGB48();
/* 2808 */          AV_PIX_FMT_RGB565 = AV_PIX_FMT_RGB565();
/* 2810 */          AV_PIX_FMT_RGB555 = AV_PIX_FMT_RGB555();
/* 2812 */          AV_PIX_FMT_RGB444 = AV_PIX_FMT_RGB444();
/* 2814 */          AV_PIX_FMT_BGR48 = AV_PIX_FMT_BGR48();
/* 2816 */          AV_PIX_FMT_BGR565 = AV_PIX_FMT_BGR565();
/* 2818 */          AV_PIX_FMT_BGR555 = AV_PIX_FMT_BGR555();
/* 2820 */          AV_PIX_FMT_BGR444 = AV_PIX_FMT_BGR444();
/* 2823 */          AV_PIX_FMT_YUV420P9 = AV_PIX_FMT_YUV420P9();
/* 2825 */          AV_PIX_FMT_YUV422P9 = AV_PIX_FMT_YUV422P9();
/* 2827 */          AV_PIX_FMT_YUV444P9 = AV_PIX_FMT_YUV444P9();
/* 2829 */          AV_PIX_FMT_YUV420P10 = AV_PIX_FMT_YUV420P10();
/* 2831 */          AV_PIX_FMT_YUV422P10 = AV_PIX_FMT_YUV422P10();
/* 2833 */          AV_PIX_FMT_YUV444P10 = AV_PIX_FMT_YUV444P10();
/* 2835 */          AV_PIX_FMT_YUV420P12 = AV_PIX_FMT_YUV420P12();
/* 2837 */          AV_PIX_FMT_YUV422P12 = AV_PIX_FMT_YUV422P12();
/* 2839 */          AV_PIX_FMT_YUV444P12 = AV_PIX_FMT_YUV444P12();
/* 2841 */          AV_PIX_FMT_YUV420P14 = AV_PIX_FMT_YUV420P14();
/* 2843 */          AV_PIX_FMT_YUV422P14 = AV_PIX_FMT_YUV422P14();
/* 2845 */          AV_PIX_FMT_YUV444P14 = AV_PIX_FMT_YUV444P14();
/* 2847 */          AV_PIX_FMT_YUV420P16 = AV_PIX_FMT_YUV420P16();
/* 2849 */          AV_PIX_FMT_YUV422P16 = AV_PIX_FMT_YUV422P16();
/* 2851 */          AV_PIX_FMT_YUV444P16 = AV_PIX_FMT_YUV444P16();
/* 2854 */          AV_PIX_FMT_RGBA64 = AV_PIX_FMT_RGBA64();
/* 2856 */          AV_PIX_FMT_BGRA64 = AV_PIX_FMT_BGRA64();
/* 2858 */          AV_PIX_FMT_GBRP9 = AV_PIX_FMT_GBRP9();
/* 2860 */          AV_PIX_FMT_GBRP10 = AV_PIX_FMT_GBRP10();
/* 2862 */          AV_PIX_FMT_GBRP12 = AV_PIX_FMT_GBRP12();
/* 2864 */          AV_PIX_FMT_GBRP14 = AV_PIX_FMT_GBRP14();
/* 2866 */          AV_PIX_FMT_GBRP16 = AV_PIX_FMT_GBRP16();
/* 2869 */          AV_PIX_FMT_YUVA420P9 = AV_PIX_FMT_YUVA420P9();
/* 2871 */          AV_PIX_FMT_YUVA422P9 = AV_PIX_FMT_YUVA422P9();
/* 2873 */          AV_PIX_FMT_YUVA444P9 = AV_PIX_FMT_YUVA444P9();
/* 2875 */          AV_PIX_FMT_YUVA420P10 = AV_PIX_FMT_YUVA420P10();
/* 2877 */          AV_PIX_FMT_YUVA422P10 = AV_PIX_FMT_YUVA422P10();
/* 2879 */          AV_PIX_FMT_YUVA444P10 = AV_PIX_FMT_YUVA444P10();
/* 2881 */          AV_PIX_FMT_YUVA420P16 = AV_PIX_FMT_YUVA420P16();
/* 2883 */          AV_PIX_FMT_YUVA422P16 = AV_PIX_FMT_YUVA422P16();
/* 2885 */          AV_PIX_FMT_YUVA444P16 = AV_PIX_FMT_YUVA444P16();
/* 2888 */          AV_PIX_FMT_XYZ12 = AV_PIX_FMT_XYZ12();
/* 2895 */          PIX_FMT_RGB32 = AV_PIX_FMT_RGB32;
/* 2896 */          PIX_FMT_RGB32_1 = AV_PIX_FMT_RGB32_1;
/* 2897 */          PIX_FMT_BGR32 = AV_PIX_FMT_BGR32;
/* 2898 */          PIX_FMT_BGR32_1 = AV_PIX_FMT_BGR32_1;
/* 2899 */          PIX_FMT_0RGB32 = AV_PIX_FMT_0RGB32;
/* 2900 */          PIX_FMT_0BGR32 = AV_PIX_FMT_0BGR32;
/* 2902 */          PIX_FMT_GRAY16 = AV_PIX_FMT_GRAY16;
/* 2903 */          PIX_FMT_RGB48 = AV_PIX_FMT_RGB48;
/* 2904 */          PIX_FMT_RGB565 = AV_PIX_FMT_RGB565;
/* 2905 */          PIX_FMT_RGB555 = AV_PIX_FMT_RGB555;
/* 2906 */          PIX_FMT_RGB444 = AV_PIX_FMT_RGB444;
/* 2907 */          PIX_FMT_BGR48 = AV_PIX_FMT_BGR48;
/* 2908 */          PIX_FMT_BGR565 = AV_PIX_FMT_BGR565;
/* 2909 */          PIX_FMT_BGR555 = AV_PIX_FMT_BGR555;
/* 2910 */          PIX_FMT_BGR444 = AV_PIX_FMT_BGR444;
/* 2912 */          PIX_FMT_YUV420P9 = AV_PIX_FMT_YUV420P9;
/* 2913 */          PIX_FMT_YUV422P9 = AV_PIX_FMT_YUV422P9;
/* 2914 */          PIX_FMT_YUV444P9 = AV_PIX_FMT_YUV444P9;
/* 2915 */          PIX_FMT_YUV420P10 = AV_PIX_FMT_YUV420P10;
/* 2916 */          PIX_FMT_YUV422P10 = AV_PIX_FMT_YUV422P10;
/* 2917 */          PIX_FMT_YUV444P10 = AV_PIX_FMT_YUV444P10;
/* 2918 */          PIX_FMT_YUV420P12 = AV_PIX_FMT_YUV420P12;
/* 2919 */          PIX_FMT_YUV422P12 = AV_PIX_FMT_YUV422P12;
/* 2920 */          PIX_FMT_YUV444P12 = AV_PIX_FMT_YUV444P12;
/* 2921 */          PIX_FMT_YUV420P14 = AV_PIX_FMT_YUV420P14;
/* 2922 */          PIX_FMT_YUV422P14 = AV_PIX_FMT_YUV422P14;
/* 2923 */          PIX_FMT_YUV444P14 = AV_PIX_FMT_YUV444P14;
/* 2924 */          PIX_FMT_YUV420P16 = AV_PIX_FMT_YUV420P16;
/* 2925 */          PIX_FMT_YUV422P16 = AV_PIX_FMT_YUV422P16;
/* 2926 */          PIX_FMT_YUV444P16 = AV_PIX_FMT_YUV444P16;
/* 2928 */          PIX_FMT_RGBA64 = AV_PIX_FMT_RGBA64;
/* 2929 */          PIX_FMT_BGRA64 = AV_PIX_FMT_BGRA64;
/* 2930 */          PIX_FMT_GBRP9 = AV_PIX_FMT_GBRP9;
/* 2931 */          PIX_FMT_GBRP10 = AV_PIX_FMT_GBRP10;
/* 2932 */          PIX_FMT_GBRP12 = AV_PIX_FMT_GBRP12;
/* 2933 */          PIX_FMT_GBRP14 = AV_PIX_FMT_GBRP14;
/* 2934 */          PIX_FMT_GBRP16 = AV_PIX_FMT_GBRP16;
/* 4045 */          AV_OPT_TYPE_IMAGE_SIZE = AV_OPT_TYPE_IMAGE_SIZE();
/* 4048 */          AV_OPT_TYPE_PIXEL_FMT = AV_OPT_TYPE_PIXEL_FMT();
/* 4051 */          AV_OPT_TYPE_SAMPLE_FMT = AV_OPT_TYPE_SAMPLE_FMT();
/* 4055 */          AV_OPT_TYPE_VIDEO_RATE = AV_OPT_TYPE_VIDEO_RATE();
/* 4058 */          AV_OPT_TYPE_DURATION = AV_OPT_TYPE_DURATION();
/* 4061 */          AV_OPT_TYPE_COLOR = AV_OPT_TYPE_COLOR();
                }

/* 1009 */      public static class AVRational extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native int den();

                    public native AVRational den(int i);

                    public native int num();

                    public native AVRational num(int i);


                    static {
/* 1012 */              Loader.load();
                    }

/* 1012 */          public AVRational() {
                        allocate();
                    }

/* 1013 */          public AVRational(int size) {
                        allocateArray(size);
                    }

/* 1014 */          public AVRational(Pointer p) {
/* 1015 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 1018 */          public AVRational position(int position) {
/* 1019 */              return (AVRational) super.position(position);
                    }
                }

/* 1175 */      public static class AVClass extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    @Cast({"AVClassCategory"})
                    public native int category();

                    public native AVClass category(int i);

                    @MemberGetter
                    public native Child_class_next_AVClass child_class_next();

                    public native Child_next_Pointer_Pointer child_next();

                    public native AVClass child_next(Child_next_Pointer_Pointer child_next_Pointer_Pointer);

                    @MemberGetter
                    @Cast({"const char*"})
                    public native BytePointer class_name();

                    public native Get_category_Pointer get_category();

                    public native AVClass get_category(Get_category_Pointer get_category_Pointer);

                    @MemberGetter
                    public native Item_name_Pointer item_name();

                    public native int log_level_offset_offset();

                    public native AVClass log_level_offset_offset(int i);

                    @MemberGetter
                    @Const
                    public native AVOption option();

                    public native int parent_log_context_offset();

                    public native AVClass parent_log_context_offset(int i);

                    public native Query_ranges_PointerPointer_Pointer_BytePointer_int query_ranges();

                    public native AVClass query_ranges(Query_ranges_PointerPointer_Pointer_BytePointer_int query_ranges_PointerPointer_Pointer_BytePointer_int);

                    public native int version();

                    public native AVClass version(int i);


                    static {
/* 1178 */              Loader.load();
                    }

/* 1178 */          public AVClass() {
                        allocate();
                    }

/* 1179 */          public AVClass(int size) {
                        allocateArray(size);
                    }

/* 1180 */          public AVClass(Pointer p) {
/* 1181 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 1184 */          public AVClass position(int position) {
/* 1185 */              return (AVClass) super.position(position);
                    }

/* 1197 */          public static class Item_name_Pointer extends FunctionPointer {
                        private native void allocate();

                        @Cast({"const char*"})
                        public native BytePointer call(Pointer pointer);

                        static {
/* 1199 */                  Loader.load();
                        }

/* 1199 */              public Item_name_Pointer(Pointer p) {
/* 1200 */                  super(p);
                        }

/* 1200 */              protected Item_name_Pointer() {
                            allocate();
                        }
                    }

/* 1239 */          public static class Child_next_Pointer_Pointer extends FunctionPointer {
                        private native void allocate();

                        public native Pointer call(Pointer pointer, Pointer pointer2);

                        static {
/* 1241 */                  Loader.load();
                        }

/* 1241 */              public Child_next_Pointer_Pointer(Pointer p) {
/* 1242 */                  super(p);
                        }

/* 1242 */              protected Child_next_Pointer_Pointer() {
                            allocate();
                        }
                    }

/* 1256 */          public static class Child_class_next_AVClass extends FunctionPointer {
                        private native void allocate();

                        @Const
                        public native AVClass call(@Const AVClass aVClass);

                        static {
/* 1258 */                  Loader.load();
                        }

/* 1258 */              public Child_class_next_AVClass(Pointer p) {
/* 1259 */                  super(p);
                        }

/* 1259 */              protected Child_class_next_AVClass() {
                            allocate();
                        }
                    }

/* 1276 */          public static class Get_category_Pointer extends FunctionPointer {
                        private native void allocate();

                        @Cast({"AVClassCategory"})
                        public native int call(Pointer pointer);

                        static {
/* 1278 */                  Loader.load();
                        }

/* 1278 */              public Get_category_Pointer(Pointer p) {
/* 1279 */                  super(p);
                        }

/* 1279 */              protected Get_category_Pointer() {
                            allocate();
                        }
                    }

/* 1289 */          public static class Query_ranges_PointerPointer_Pointer_BytePointer_int extends FunctionPointer {
                        private native void allocate();

                        public native int call(@Cast({"AVOptionRanges**"}) PointerPointer pointerPointer, Pointer pointer, @Cast({"const char*"}) BytePointer bytePointer, int i);

                        static {
/* 1291 */                  Loader.load();
                        }

/* 1291 */              public Query_ranges_PointerPointer_Pointer_BytePointer_int(Pointer p) {
/* 1292 */                  super(p);
                        }

/* 1292 */              protected Query_ranges_PointerPointer_Pointer_BytePointer_int() {
                            allocate();
                        }
                    }
                }

/* 1358 */      public static class Arg0_Pointer_int_BytePointer_Pointer extends FunctionPointer {
                    private native void allocate();

                    public native void call(Pointer pointer, int i, @Cast({"const char*"}) BytePointer bytePointer, @ByVal @Cast({"va_list*"}) Pointer pointer2);

                    static {
/* 1360 */              Loader.load();
                    }

/* 1360 */          public Arg0_Pointer_int_BytePointer_Pointer(Pointer p) {
/* 1361 */              super(p);
                    }

/* 1361 */          protected Arg0_Pointer_int_BytePointer_Pointer() {
                        allocate();
                    }
                }

/* 1366 */      public static class Arg0_Pointer_int_String_Pointer extends FunctionPointer {
                    private native void allocate();

                    public native void call(Pointer pointer, int i, String str, @ByVal @Cast({"va_list*"}) Pointer pointer2);

                    static {
/* 1368 */              Loader.load();
                    }

/* 1368 */          public Arg0_Pointer_int_String_Pointer(Pointer p) {
/* 1369 */              super(p);
                    }

/* 1369 */          protected Arg0_Pointer_int_String_Pointer() {
                        allocate();
                    }
                }

                @Opaque
/* 1498 */      public static class AVBuffer extends Pointer {
/* 1499 */          public AVBuffer() {
                    }

/* 1500 */          public AVBuffer(Pointer p) {
/* 1501 */              super(p);
                    }
                }

/* 1508 */      public static class AVBufferRef extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native AVBuffer buffer();

                    public native AVBufferRef buffer(AVBuffer aVBuffer);

                    @Cast({"uint8_t*"})
                    public native BytePointer data();

                    public native AVBufferRef data(BytePointer bytePointer);

                    public native int size();

                    public native AVBufferRef size(int i);


                    static {
/* 1511 */              Loader.load();
                    }

/* 1511 */          public AVBufferRef() {
                        allocate();
                    }

/* 1512 */          public AVBufferRef(int size) {
                        allocateArray(size);
                    }

/* 1513 */          public AVBufferRef(Pointer p) {
/* 1514 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 1517 */          public AVBufferRef position(int position) {
/* 1518 */              return (AVBufferRef) super.position(position);
                    }
                }

/* 1568 */      public static class Free_Pointer_BytePointer extends FunctionPointer {
                    private native void allocate();

                    public native void call(Pointer pointer, @Cast({"uint8_t*"}) BytePointer bytePointer);

                    static {
/* 1570 */              Loader.load();
                    }

/* 1570 */          public Free_Pointer_BytePointer(Pointer p) {
/* 1571 */              super(p);
                    }

/* 1571 */          protected Free_Pointer_BytePointer() {
                        allocate();
                    }
                }

/* 1576 */      public static class Free_Pointer_ByteBuffer extends FunctionPointer {
                    private native void allocate();

                    public native void call(Pointer pointer, @Cast({"uint8_t*"}) ByteBuffer byteBuffer);

                    static {
/* 1578 */              Loader.load();
                    }

/* 1578 */          public Free_Pointer_ByteBuffer(Pointer p) {
/* 1579 */              super(p);
                    }

/* 1579 */          protected Free_Pointer_ByteBuffer() {
                        allocate();
                    }
                }

/* 1584 */      public static class Free_Pointer_byte extends FunctionPointer {
                    private native void allocate();

                    public native void call(Pointer pointer, @Cast({"uint8_t*"}) byte[] bArr);

                    static {
/* 1586 */              Loader.load();
                    }

/* 1586 */          public Free_Pointer_byte(Pointer p) {
/* 1587 */              super(p);
                    }

/* 1587 */          protected Free_Pointer_byte() {
                        allocate();
                    }
                }

                @Opaque
/* 1711 */      public static class AVBufferPool extends Pointer {
/* 1712 */          public AVBufferPool() {
                    }

/* 1713 */          public AVBufferPool(Pointer p) {
/* 1714 */              super(p);
                    }
                }

/* 1725 */      public static class Alloc_int extends FunctionPointer {
                    private native void allocate();

                    public native AVBufferRef call(int i);

                    static {
/* 1727 */              Loader.load();
                    }

/* 1727 */          public Alloc_int(Pointer p) {
/* 1728 */              super(p);
                    }

/* 1728 */          protected Alloc_int() {
                        allocate();
                    }
                }

/* 1802 */      public static class AVFrameSideData extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    @Cast({"uint8_t*"})
                    public native BytePointer data();

                    public native AVFrameSideData data(BytePointer bytePointer);

                    public native AVDictionary metadata();

                    public native AVFrameSideData metadata(AVDictionary aVDictionary);

                    public native int size();

                    public native AVFrameSideData size(int i);

                    @Cast({"AVFrameSideDataType"})
                    public native int type();

                    public native AVFrameSideData type(int i);


                    static {
/* 1805 */              Loader.load();
                    }

/* 1805 */          public AVFrameSideData() {
                        allocate();
                    }

/* 1806 */          public AVFrameSideData(int size) {
                        allocateArray(size);
                    }

/* 1807 */          public AVFrameSideData(Pointer p) {
/* 1808 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 1811 */          public AVFrameSideData position(int position) {
/* 1812 */              return (AVFrameSideData) super.position(position);
                    }
                }

/* 1848 */      public static class AVFrame extends Pointer {
                    public static final int AV_NUM_DATA_POINTERS = 8;
                    public static final int FF_DECODE_ERROR_INVALID_BITSTREAM = 1;
                    public static final int FF_DECODE_ERROR_MISSING_REFERENCE = 2;

                    private native void allocate();

                    private native void allocateArray(int i);

                    @Cast({"uint8_t*"})
                    @Deprecated
                    public native BytePointer base(int i);

                    @MemberGetter
                    @Cast({"uint8_t**"})
                    @Deprecated
                    public native PointerPointer base();

                    public native AVFrame base(int i, BytePointer bytePointer);

                    public native long best_effort_timestamp();

                    public native AVFrame best_effort_timestamp(long j);

                    @MemberGetter
                    @Cast({"AVBufferRef**"})
                    public native PointerPointer buf();

                    public native AVBufferRef buf(int i);

                    public native AVFrame buf(int i, AVBufferRef aVBufferRef);

                    @Deprecated
                    public native int buffer_hints();

                    public native AVFrame buffer_hints(int i);

                    @Cast({"uint64_t"})
                    public native long channel_layout();

                    public native AVFrame channel_layout(long j);

                    public native int channels();

                    public native AVFrame channels(int i);

                    public native int coded_picture_number();

                    public native AVFrame coded_picture_number(int i);

                    @Cast({"uint8_t*"})
                    public native BytePointer data(int i);

                    @MemberGetter
                    @Cast({"uint8_t**"})
                    public native PointerPointer data();

                    public native AVFrame data(int i, BytePointer bytePointer);

                    @Deprecated
                    public native ShortPointer dct_coeff();

                    public native AVFrame dct_coeff(ShortPointer shortPointer);

                    public native int decode_error_flags();

                    public native AVFrame decode_error_flags(int i);

                    public native int display_picture_number();

                    public native AVFrame display_picture_number(int i);

                    @Cast({"uint64_t"})
                    public native long error(int i);

                    @MemberGetter
                    @Cast({"uint64_t*"})
                    public native LongPointer error();

                    public native AVFrame error(int i, long j);

                    @MemberGetter
                    @Cast({"AVBufferRef**"})
                    public native PointerPointer extended_buf();

                    public native AVBufferRef extended_buf(int i);

                    public native AVFrame extended_buf(int i, AVBufferRef aVBufferRef);

                    @Cast({"uint8_t*"})
                    public native BytePointer extended_data(int i);

                    @MemberGetter
                    @Cast({"uint8_t**"})
                    public native PointerPointer extended_data();

                    public native AVFrame extended_data(int i, BytePointer bytePointer);

                    public native int format();

                    public native AVFrame format(int i);

                    public native int height();

                    public native AVFrame height(int i);

                    @Deprecated
                    public native Pointer hwaccel_picture_private();

                    public native AVFrame hwaccel_picture_private(Pointer pointer);

                    public native int interlaced_frame();

                    public native AVFrame interlaced_frame(int i);

                    public native int key_frame();

                    public native AVFrame key_frame(int i);

                    public native int linesize(int i);

                    @MemberGetter
                    public native IntPointer linesize();

                    public native AVFrame linesize(int i, int i2);

                    @Cast({"uint32_t*"})
                    @Deprecated
                    public native IntPointer mb_type();

                    public native AVFrame mb_type(IntPointer intPointer);

                    @Cast({"uint8_t*"})
                    @Deprecated
                    public native BytePointer mbskip_table();

                    public native AVFrame mbskip_table(BytePointer bytePointer);

                    public native AVDictionary metadata();

                    public native AVFrame metadata(AVDictionary aVDictionary);

                    @Cast({"uint8_t"})
                    @Deprecated
                    public native byte motion_subsample_log2();

                    public native AVFrame motion_subsample_log2(byte b);

                    @MemberGetter
                    @Cast({"int16_t*"})
                    @Deprecated
                    public native ShortPointer motion_val();

                    public native AVFrame motion_val(int i, int i2, int i3, short s);

                    @Deprecated
                    public native short motion_val(int i, int i2, int i3);

                    public native int nb_extended_buf();

                    public native AVFrame nb_extended_buf(int i);

                    public native int nb_samples();

                    public native AVFrame nb_samples(int i);

                    public native int nb_side_data();

                    public native AVFrame nb_side_data(int i);

                    public native Pointer opaque();

                    public native AVFrame opaque(Pointer pointer);

                    @Cast({"AVCodecContext*"})
                    @Deprecated
                    public native Pointer owner();

                    public native AVFrame owner(Pointer pointer);

                    public native int palette_has_changed();

                    public native AVFrame palette_has_changed(int i);

                    @Cast({"AVPanScan*"})
                    @Deprecated
                    public native Pointer pan_scan();

                    public native AVFrame pan_scan(Pointer pointer);

                    @Cast({"AVPictureType"})
                    public native int pict_type();

                    public native AVFrame pict_type(int i);

                    public native long pkt_dts();

                    public native AVFrame pkt_dts(long j);

                    public native long pkt_duration();

                    public native AVFrame pkt_duration(long j);

                    public native long pkt_pos();

                    public native AVFrame pkt_pos(long j);

                    public native long pkt_pts();

                    public native AVFrame pkt_pts(long j);

                    public native int pkt_size();

                    public native AVFrame pkt_size(int i);

                    public native long pts();

                    public native AVFrame pts(long j);

                    public native AVBufferRef qp_table_buf();

                    public native AVFrame qp_table_buf(AVBufferRef aVBufferRef);

                    @Deprecated
                    public native BytePointer qscale_table();

                    public native AVFrame qscale_table(BytePointer bytePointer);

                    @Deprecated
                    public native int qscale_type();

                    public native AVFrame qscale_type(int i);

                    @Deprecated
                    public native int qstride();

                    public native AVFrame qstride(int i);

                    public native int quality();

                    public native AVFrame quality(int i);

                    @Deprecated
                    public native BytePointer ref_index(int i);

                    @MemberGetter
                    @Cast({"int8_t**"})
                    @Deprecated
                    public native PointerPointer ref_index();

                    public native AVFrame ref_index(int i, BytePointer bytePointer);

                    @Deprecated
                    public native int reference();

                    public native AVFrame reference(int i);

                    public native long reordered_opaque();

                    public native AVFrame reordered_opaque(long j);

                    public native int repeat_pict();

                    public native AVFrame repeat_pict(int i);

                    public native AVFrame sample_aspect_ratio(AVRational aVRational);

                    @ByVal
                    public native AVRational sample_aspect_ratio();

                    public native int sample_rate();

                    public native AVFrame sample_rate(int i);

                    @MemberGetter
                    @Cast({"AVFrameSideData**"})
                    public native PointerPointer side_data();

                    public native AVFrame side_data(int i, AVFrameSideData aVFrameSideData);

                    public native AVFrameSideData side_data(int i);

                    @Deprecated
                    public native Pointer thread_opaque();

                    public native AVFrame thread_opaque(Pointer pointer);

                    public native int top_field_first();

                    public native AVFrame top_field_first(int i);

                    @Deprecated
                    public native int type();

                    public native AVFrame type(int i);

                    public native int width();

                    public native AVFrame width(int i);


                    static {
/* 1851 */              Loader.load();
                    }

/* 1851 */          public AVFrame() {
                        allocate();
                    }

/* 1852 */          public AVFrame(int size) {
                        allocateArray(size);
                    }

/* 1853 */          public AVFrame(Pointer p) {
/* 1854 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 1857 */          public AVFrame position(int position) {
/* 1858 */              return (AVFrame) super.position(position);
                    }
                }

                @Opaque
/* 3411 */      public static class AVBPrint extends Pointer {
/* 3412 */          public AVBPrint() {
                    }

/* 3413 */          public AVBPrint(Pointer p) {
/* 3414 */              super(p);
                    }
                }

/* 3706 */      public static class AVDictionaryEntry extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    @Cast({"char*"})
                    public native BytePointer key();

                    public native AVDictionaryEntry key(BytePointer bytePointer);

                    @Cast({"char*"})
                    public native BytePointer value();

                    public native AVDictionaryEntry value(BytePointer bytePointer);


                    static {
/* 3709 */              Loader.load();
                    }

/* 3709 */          public AVDictionaryEntry() {
                        allocate();
                    }

/* 3710 */          public AVDictionaryEntry(int size) {
                        allocateArray(size);
                    }

/* 3711 */          public AVDictionaryEntry(Pointer p) {
/* 3712 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 3715 */          public AVDictionaryEntry position(int position) {
/* 3716 */              return (AVDictionaryEntry) super.position(position);
                    }
                }

                @Opaque
/* 3722 */      public static class AVDictionary extends Pointer {
/* 3723 */          public AVDictionary() {
                    }

/* 3724 */          public AVDictionary(Pointer p) {
/* 3725 */              super(p);
                    }
                }

/* 4076 */      public static class AVOption extends Pointer {
                    public static final int AV_OPT_FLAG_AUDIO_PARAM = 8;
                    public static final int AV_OPT_FLAG_DECODING_PARAM = 2;
                    public static final int AV_OPT_FLAG_ENCODING_PARAM = 1;
                    public static final int AV_OPT_FLAG_FILTERING_PARAM = 65536;
                    public static final int AV_OPT_FLAG_METADATA = 4;
                    public static final int AV_OPT_FLAG_SUBTITLE_PARAM = 32;
                    public static final int AV_OPT_FLAG_VIDEO_PARAM = 16;

                    private native void allocate();

                    private native void allocateArray(int i);

                    public native int flags();

                    public native AVOption flags(int i);

                    @MemberGetter
                    @Cast({"const char*"})
                    public native BytePointer help();

                    public native double max();

                    public native AVOption max(double d);

                    public native double min();

                    public native AVOption min(double d);

                    @MemberGetter
                    @Cast({"const char*"})
                    public native BytePointer name();

                    public native int offset();

                    public native AVOption offset(int i);

                    @Cast({"AVOptionType"})
                    public native int type();

                    public native AVOption type(int i);

                    @MemberGetter
                    @Cast({"const char*"})
                    public native BytePointer unit();


                    static {
/* 4079 */              Loader.load();
                    }

/* 4079 */          public AVOption() {
                        allocate();
                    }

/* 4080 */          public AVOption(int size) {
                        allocateArray(size);
                    }

/* 4081 */          public AVOption(Pointer p) {
/* 4082 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 4085 */          public AVOption position(int position) {
/* 4086 */              return (AVOption) super.position(position);
                    }
                }

/* 4135 */      public static class AVOptionRange extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native double component_max();

                    public native AVOptionRange component_max(double d);

                    public native double component_min();

                    public native AVOptionRange component_min(double d);

                    public native int is_range();

                    public native AVOptionRange is_range(int i);

                    @MemberGetter
                    @Cast({"const char*"})
                    public native BytePointer str();

                    public native double value_max();

                    public native AVOptionRange value_max(double d);

                    public native double value_min();

                    public native AVOptionRange value_min(double d);


                    static {
/* 4138 */              Loader.load();
                    }

/* 4138 */          public AVOptionRange() {
                        allocate();
                    }

/* 4139 */          public AVOptionRange(int size) {
                        allocateArray(size);
                    }

/* 4140 */          public AVOptionRange(Pointer p) {
/* 4141 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 4144 */          public AVOptionRange position(int position) {
/* 4145 */              return (AVOptionRange) super.position(position);
                    }
                }

/* 4160 */      public static class AVOptionRanges extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native int nb_ranges();

                    public native AVOptionRanges nb_ranges(int i);

                    @MemberGetter
                    @Cast({"AVOptionRange**"})
                    public native PointerPointer range();

                    public native AVOptionRange range(int i);

                    public native AVOptionRanges range(int i, AVOptionRange aVOptionRange);


                    static {
/* 4163 */              Loader.load();
                    }

/* 4163 */          public AVOptionRanges() {
                        allocate();
                    }

/* 4164 */          public AVOptionRanges(int size) {
                        allocateArray(size);
                    }

/* 4165 */          public AVOptionRanges(Pointer p) {
/* 4166 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 4169 */          public AVOptionRanges position(int position) {
/* 4170 */              return (AVOptionRanges) super.position(position);
                    }
                }

/* 4796 */      public static class AVComponentDescriptor extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native AVComponentDescriptor depth_minus1(short s);

                    @NoOffset
                    @Cast({"uint16_t"})
                    public native short depth_minus1();

                    public native AVComponentDescriptor offset_plus1(short s);

                    @NoOffset
                    @Cast({"uint16_t"})
                    public native short offset_plus1();

                    public native AVComponentDescriptor plane(short s);

                    @NoOffset
                    @Cast({"uint16_t"})
                    public native short plane();

                    public native AVComponentDescriptor shift(short s);

                    @NoOffset
                    @Cast({"uint16_t"})
                    public native short shift();

                    public native AVComponentDescriptor step_minus1(short s);

                    @NoOffset
                    @Cast({"uint16_t"})
                    public native short step_minus1();


                    static {
/* 4799 */              Loader.load();
                    }

/* 4799 */          public AVComponentDescriptor() {
                        allocate();
                    }

/* 4800 */          public AVComponentDescriptor(int size) {
                        allocateArray(size);
                    }

/* 4801 */          public AVComponentDescriptor(Pointer p) {
/* 4802 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 4805 */          public AVComponentDescriptor position(int position) {
/* 4806 */              return (AVComponentDescriptor) super.position(position);
                    }
                }

/* 4836 */      public static class AVPixFmtDescriptor extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    @MemberGetter
                    public native AVComponentDescriptor comp();

                    @ByVal
                    public native AVComponentDescriptor comp(int i);

                    public native AVPixFmtDescriptor comp(int i, AVComponentDescriptor aVComponentDescriptor);

                    @Cast({"uint8_t"})
                    public native byte flags();

                    public native AVPixFmtDescriptor flags(byte b);

                    @Cast({"uint8_t"})
                    public native byte log2_chroma_h();

                    public native AVPixFmtDescriptor log2_chroma_h(byte b);

                    @Cast({"uint8_t"})
                    public native byte log2_chroma_w();

                    public native AVPixFmtDescriptor log2_chroma_w(byte b);

                    @MemberGetter
                    @Cast({"const char*"})
                    public native BytePointer name();

                    @Cast({"uint8_t"})
                    public native byte nb_components();

                    public native AVPixFmtDescriptor nb_components(byte b);


                    static {
/* 4839 */              Loader.load();
                    }

/* 4839 */          public AVPixFmtDescriptor() {
                        allocate();
                    }

/* 4840 */          public AVPixFmtDescriptor(int size) {
                        allocateArray(size);
                    }

/* 4841 */          public AVPixFmtDescriptor(Pointer p) {
/* 4842 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 4845 */          public AVPixFmtDescriptor position(int position) {
/* 4846 */              return (AVPixFmtDescriptor) super.position(position);
                    }
                }
            }
