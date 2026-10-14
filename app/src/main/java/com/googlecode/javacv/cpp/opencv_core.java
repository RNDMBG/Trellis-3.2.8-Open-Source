            package com.googlecode.javacv.cpp;

            import com.flurry.android.Constants;
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
            import com.googlecode.javacpp.annotation.Adapter;
            import com.googlecode.javacpp.annotation.ByPtrPtr;
            import com.googlecode.javacpp.annotation.ByRef;
            import com.googlecode.javacpp.annotation.ByVal;
            import com.googlecode.javacpp.annotation.Cast;
            import com.googlecode.javacpp.annotation.Const;
            import com.googlecode.javacpp.annotation.Convention;
            import com.googlecode.javacpp.annotation.Index;
            import com.googlecode.javacpp.annotation.MemberGetter;
            import com.googlecode.javacpp.annotation.MemberSetter;
            import com.googlecode.javacpp.annotation.Name;
            import com.googlecode.javacpp.annotation.Namespace;
            import com.googlecode.javacpp.annotation.NoOffset;
            import com.googlecode.javacpp.annotation.Opaque;
            import com.googlecode.javacpp.annotation.Platform;
            import com.googlecode.javacpp.annotation.Properties;
            import com.googlecode.javacpp.annotation.StdVector;
            import com.googlecode.javacpp.annotation.ValueGetter;
            import com.googlecode.javacpp.annotation.ValueSetter;
            import java.lang.annotation.ElementType;
            import java.lang.annotation.Retention;
            import java.lang.annotation.RetentionPolicy;
            import java.lang.annotation.Target;
            import java.nio.Buffer;
            import java.nio.ByteBuffer;
            import java.nio.DoubleBuffer;
            import java.nio.FloatBuffer;
            import java.nio.IntBuffer;
            import java.nio.ShortBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @Properties({@Platform(include = {"<opencv2/core/core.hpp>", "opencv_adapters.h"}, link = {"opencv_core@.2.4"}, preload = {"tbb"}), @Platform(define = {"_WIN32_WINNT 0x0502"}, includepath = {"C:/opencv/build/include/"}, link = {"opencv_core246"}, preload = {"msvcr100", "msvcp100"}, value = {"windows"}), @Platform(linkpath = {"C:/opencv/build/x86/vc10/lib/"}, preloadpath = {"C:/opencv/build/x86/vc10/bin/"}, value = {"windows-x86"}), @Platform(linkpath = {"C:/opencv/build/x64/vc10/lib/"}, preloadpath = {"C:/opencv/build/x64/vc10/bin/"}, value = {"windows-x86_64"})})
/* 103 */   public class opencv_core {
                static final /* synthetic */ boolean $assertionsDisabled;
                public static final int CV_16S = 3;
                public static final int CV_16SC1;
                public static final int CV_16SC2;
                public static final int CV_16SC3;
                public static final int CV_16SC4;
                public static final int CV_16U = 2;
                public static final int CV_16UC1;
                public static final int CV_16UC2;
                public static final int CV_16UC3;
                public static final int CV_16UC4;
                public static final int CV_32F = 5;
                public static final int CV_32FC1;
                public static final int CV_32FC2;
                public static final int CV_32FC3;
                public static final int CV_32FC4;
                public static final int CV_32S = 4;
                public static final int CV_32SC1;
                public static final int CV_32SC2;
                public static final int CV_32SC3;
                public static final int CV_32SC4;
                public static final int CV_64F = 6;
                public static final int CV_64FC1;
                public static final int CV_64FC2;
                public static final int CV_64FC3;
                public static final int CV_64FC4;
                public static final int CV_8S = 1;
                public static final int CV_8SC1;
                public static final int CV_8SC2;
                public static final int CV_8SC3;
                public static final int CV_8SC4;
                public static final int CV_8U = 0;
                public static final int CV_8UC1;
                public static final int CV_8UC2;
                public static final int CV_8UC3;
                public static final int CV_8UC4;
                public static final int CV_AA = 16;
                public static final CvAttrList CV_ATTR_LIST_EMPTY;
                public static final int CV_AUTOSTEP = Integer.MAX_VALUE;
                public static final int CV_AUTO_STEP = Integer.MAX_VALUE;
                public static final int CV_BACK = 0;
                public static final int CV_BadAlign = -21;
                public static final int CV_BadAlphaChannel = -18;
                public static final int CV_BadCOI = -24;
                public static final int CV_BadCallBack = -22;
                public static final int CV_BadDataPtr = -12;
                public static final int CV_BadDepth = -17;
                public static final int CV_BadImageSize = -10;
                public static final int CV_BadModelOrChSeq = -14;
                public static final int CV_BadNumChannel1U = -16;
                public static final int CV_BadNumChannels = -15;
                public static final int CV_BadOffset = -11;
                public static final int CV_BadOrder = -19;
                public static final int CV_BadOrigin = -20;
                public static final int CV_BadROISize = -25;
                public static final int CV_BadStep = -13;
                public static final int CV_BadTileSize = -23;
                public static final int CV_C = 1;
                public static final int CV_CHECK_QUIET = 2;
                public static final int CV_CHECK_RANGE = 1;
                public static final int CV_CHOLESKY = 3;
                public static final int CV_CMP_EQ = 0;
                public static final int CV_CMP_GE = 2;
                public static final int CV_CMP_GT = 1;
                public static final int CV_CMP_LE = 4;
                public static final int CV_CMP_LT = 3;
                public static final int CV_CMP_NE = 5;
                public static final int CV_CN_MAX = 512;
                public static final int CV_CN_SHIFT = 3;
                public static final int CV_COVAR_COLS = 16;
                public static final int CV_COVAR_NORMAL = 1;
                public static final int CV_COVAR_ROWS = 8;
                public static final int CV_COVAR_SCALE = 4;
                public static final int CV_COVAR_SCRAMBLED = 0;
                public static final int CV_COVAR_USE_AVG = 2;
                public static final int CV_CPU_AVX = 10;
                public static final int CV_CPU_MMX = 1;
                public static final int CV_CPU_NONE = 0;
                public static final int CV_CPU_POPCNT = 8;
                public static final int CV_CPU_SSE = 2;
                public static final int CV_CPU_SSE2 = 3;
                public static final int CV_CPU_SSE3 = 4;
                public static final int CV_CPU_SSE4_1 = 6;
                public static final int CV_CPU_SSE4_2 = 7;
                public static final int CV_CPU_SSSE3 = 5;
                public static final int CV_DEPTH_MAX = 8;
                public static final int CV_DIFF = 16;
                public static final int CV_DIFF_C = 17;
                public static final int CV_DIFF_L1 = 18;
                public static final int CV_DIFF_L2 = 20;
                public static final int CV_DXT_FORWARD = 0;
                public static final int CV_DXT_INVERSE = 1;
                public static final int CV_DXT_INVERSE_SCALE = 3;
                public static final int CV_DXT_INV_SCALE = 3;
                public static final int CV_DXT_MUL_CONJ = 8;
                public static final int CV_DXT_ROWS = 4;
                public static final int CV_DXT_SCALE = 2;
                public static final int CV_ErrModeLeaf = 0;
                public static final int CV_ErrModeParent = 1;
                public static final int CV_ErrModeSilent = 2;
                public static final int CV_FILLED = -1;
                public static final int CV_FONT_HERSHEY_COMPLEX = 3;
                public static final int CV_FONT_HERSHEY_COMPLEX_SMALL = 5;
                public static final int CV_FONT_HERSHEY_DUPLEX = 2;
                public static final int CV_FONT_HERSHEY_PLAIN = 1;
                public static final int CV_FONT_HERSHEY_SCRIPT_COMPLEX = 7;
                public static final int CV_FONT_HERSHEY_SCRIPT_SIMPLEX = 6;
                public static final int CV_FONT_HERSHEY_SIMPLEX = 0;
                public static final int CV_FONT_HERSHEY_TRIPLEX = 4;
                public static final int CV_FONT_ITALIC = 16;
                public static final int CV_FONT_VECTOR0 = 0;
                public static final int CV_FRONT = 1;
                public static final int CV_GEMM_A_T = 1;
                public static final int CV_GEMM_B_T = 2;
                public static final int CV_GEMM_C_T = 4;
                public static final int CV_GRAPH = 4096;
                public static final int CV_GRAPH_ALL_ITEMS = -1;
                public static final int CV_GRAPH_ANY_EDGE = 30;
                public static final int CV_GRAPH_BACKTRACKING = 64;
                public static final int CV_GRAPH_BACK_EDGE = 4;
                public static final int CV_GRAPH_CROSS_EDGE = 16;
                public static final int CV_GRAPH_FLAG_ORIENTED = 16384;
                public static final int CV_GRAPH_FORWARD_EDGE = 8;
                public static final int CV_GRAPH_FORWARD_EDGE_FLAG = 268435456;
                public static final int CV_GRAPH_ITEM_VISITED_FLAG = 1073741824;
                public static final int CV_GRAPH_NEW_TREE = 32;
                public static final int CV_GRAPH_OVER = -1;
                public static final int CV_GRAPH_SEARCH_TREE_NODE_FLAG = 536870912;
                public static final int CV_GRAPH_TREE_EDGE = 2;
                public static final int CV_GRAPH_VERTEX = 1;
                public static final int CV_GpuApiCallError = -217;
                public static final int CV_GpuNotSupported = -216;
                public static final int CV_HARDWARE_MAX_FEATURE = 255;
                public static final int CV_HeaderIsNull = -9;
                public static final int CV_KMEANS_USE_INITIAL_LABELS = 1;
                public static final int CV_L1 = 2;
                public static final int CV_L2 = 4;
                public static final int CV_LSQ = 8;
                public static final int CV_LU = 0;
                public static final int CV_MAGIC_MASK = -65536;
                public static final int CV_MAJOR_VERSION = 2;
                public static final int CV_MATND_MAGIC_VAL = 1111687168;
                public static final int CV_MAT_CN_MASK = 4088;
                public static final int CV_MAT_CONT_FLAG = 16384;
                public static final int CV_MAT_CONT_FLAG_SHIFT = 14;
                public static final int CV_MAT_DEPTH_MASK = 7;
                public static final int CV_MAT_MAGIC_VAL = 1111621632;
                public static final int CV_MAT_TEMP_FLAG = 32768;
                public static final int CV_MAT_TEMP_FLAG_SHIFT = 15;
                public static final int CV_MAT_TYPE_MASK = 4095;
                public static final int CV_MAX_ARR = 10;
                public static final int CV_MAX_DIM = 32;
                public static final int CV_MAX_DIM_HEAP = 1024;
                public static final int CV_MINMAX = 32;
                public static final int CV_MINOR_VERSION = 4;
                public static final int CV_MaskIsTiled = -26;
                public static final int CV_NODE_EMPTY = 32;
                public static final int CV_NODE_FLOAT = 2;
                public static final int CV_NODE_FLOW = 8;
                public static final int CV_NODE_INT = 1;
                public static final int CV_NODE_INTEGER = 1;
                public static final int CV_NODE_MAP = 6;
                public static final int CV_NODE_NAMED = 64;
                public static final int CV_NODE_NONE = 0;
                public static final int CV_NODE_REAL = 2;
                public static final int CV_NODE_REF = 4;
                public static final int CV_NODE_SEQ = 5;
                public static final int CV_NODE_SEQ_SIMPLE = 256;
                public static final int CV_NODE_STR = 3;
                public static final int CV_NODE_STRING = 3;
                public static final int CV_NODE_TYPE_MASK = 7;
                public static final int CV_NODE_USER = 16;
                public static final int CV_NORMAL = 16;
                public static final int CV_NORM_MASK = 7;
                public static final int CV_NO_CN_CHECK = 2;
                public static final int CV_NO_DEPTH_CHECK = 1;
                public static final int CV_NO_SIZE_CHECK = 4;
                public static final int CV_ORIENTED_GRAPH = 20480;
                public static final int CV_OpenGlApiCallError = -219;
                public static final int CV_OpenGlNotSupported = -218;
                public static final int CV_PCA_DATA_AS_COL = 1;
                public static final int CV_PCA_DATA_AS_ROW = 0;
                public static final int CV_PCA_USE_AVG = 2;
                public static final int CV_QR = 4;
                public static final int CV_RAND_NORMAL = 1;
                public static final int CV_RAND_UNI = 0;
                public static final int CV_REDUCE_AVG = 1;
                public static final int CV_REDUCE_MAX = 2;
                public static final int CV_REDUCE_MIN = 3;
                public static final int CV_REDUCE_SUM = 0;
                public static final int CV_RELATIVE = 8;
                public static final int CV_RELATIVE_C = 9;
                public static final int CV_RELATIVE_L1 = 10;
                public static final int CV_RELATIVE_L2 = 12;
                public static final long CV_RNG_COEFF = 4164903690L;
                public static final int CV_SEQ_CHAIN;
                public static final int CV_SEQ_CHAIN_CONTOUR;
                public static final int CV_SEQ_CONNECTED_COMP = 0;
                public static final int CV_SEQ_CONTOUR;
                public static final int CV_SEQ_ELTYPE_BITS = 12;
                public static final int CV_SEQ_ELTYPE_CODE;
                public static final int CV_SEQ_ELTYPE_CONNECTED_COMP = 0;
                public static final int CV_SEQ_ELTYPE_GENERIC = 0;
                public static final int CV_SEQ_ELTYPE_GRAPH_EDGE = 0;
                public static final int CV_SEQ_ELTYPE_GRAPH_VERTEX = 0;
                public static final int CV_SEQ_ELTYPE_INDEX;
                public static final int CV_SEQ_ELTYPE_MASK = 4095;
                public static final int CV_SEQ_ELTYPE_POINT;
                public static final int CV_SEQ_ELTYPE_POINT3D;
                public static final int CV_SEQ_ELTYPE_PPOINT = 7;
                public static final int CV_SEQ_ELTYPE_PTR = 7;
                public static final int CV_SEQ_ELTYPE_TRIAN_ATR = 0;
                public static final int CV_SEQ_FLAG_CLOSED = 16384;
                public static final int CV_SEQ_FLAG_CONVEX = 0;
                public static final int CV_SEQ_FLAG_HOLE = 32768;
                public static final int CV_SEQ_FLAG_SHIFT = 14;
                public static final int CV_SEQ_FLAG_SIMPLE = 0;
                public static final int CV_SEQ_INDEX;
                public static final int CV_SEQ_KIND_BIN_TREE = 8192;
                public static final int CV_SEQ_KIND_BITS = 2;
                public static final int CV_SEQ_KIND_CURVE = 4096;
                public static final int CV_SEQ_KIND_GENERIC = 0;
                public static final int CV_SEQ_KIND_GRAPH = 4096;
                public static final int CV_SEQ_KIND_MASK = 12288;
                public static final int CV_SEQ_KIND_SUBDIV2D = 8192;
                public static final int CV_SEQ_MAGIC_VAL = 1117323264;
                public static final int CV_SEQ_POINT3D_SET;
                public static final int CV_SEQ_POINT_SET;
                public static final int CV_SEQ_POLYGON;
                public static final int CV_SEQ_POLYGON_TREE = 8192;
                public static final int CV_SEQ_POLYLINE;
                public static final int CV_SEQ_SIMPLE_POLYGON;
                public static final int CV_SET_ELEM_FREE_FLAG = 128;
                public static final int CV_SET_ELEM_IDX_MASK = 67108863;
                public static final int CV_SET_MAGIC_VAL = 1117257728;
                public static final int CV_SORT_ASCENDING = 0;
                public static final int CV_SORT_DESCENDING = 16;
                public static final int CV_SORT_EVERY_COLUMN = 1;
                public static final int CV_SORT_EVERY_ROW = 0;
                public static final int CV_SPARSE_MAT_MAGIC_VAL = 1111752704;
                public static final int CV_STORAGE_APPEND = 2;
                public static final int CV_STORAGE_FORMAT_AUTO = 0;
                public static final int CV_STORAGE_FORMAT_MASK = 56;
                public static final int CV_STORAGE_FORMAT_XML = 8;
                public static final int CV_STORAGE_FORMAT_YAML = 16;
                public static final int CV_STORAGE_MAGIC_VAL = 1116274688;
                public static final int CV_STORAGE_MEMORY = 4;
                public static final int CV_STORAGE_READ = 0;
                public static final int CV_STORAGE_WRITE = 1;
                public static final int CV_STORAGE_WRITE_BINARY = 1;
                public static final int CV_STORAGE_WRITE_TEXT = 1;
                public static final int CV_SUBMINOR_VERSION = 6;
                public static final int CV_SVD = 1;
                public static final int CV_SVD_MODIFY_A = 1;
                public static final int CV_SVD_SYM = 2;
                public static final int CV_SVD_U_T = 2;
                public static final int CV_SVD_V_T = 4;
                public static final int CV_StsAssert = -215;
                public static final int CV_StsAutoTrace = -8;
                public static final int CV_StsBackTrace = -1;
                public static final int CV_StsBadArg = -5;
                public static final int CV_StsBadFlag = -206;
                public static final int CV_StsBadFunc = -6;
                public static final int CV_StsBadMask = -208;
                public static final int CV_StsBadMemBlock = -214;
                public static final int CV_StsBadPoint = -207;
                public static final int CV_StsBadSize = -201;
                public static final int CV_StsDivByZero = -202;
                public static final int CV_StsError = -2;
                public static final int CV_StsFilterOffsetErr = -31;
                public static final int CV_StsFilterStructContentErr = -29;
                public static final int CV_StsInplaceNotSupported = -203;
                public static final int CV_StsInternal = -3;
                public static final int CV_StsKernelStructContentErr = -30;
                public static final int CV_StsNoConv = -7;
                public static final int CV_StsNoMem = -4;
                public static final int CV_StsNotImplemented = -213;
                public static final int CV_StsNullPtr = -27;
                public static final int CV_StsObjectNotFound = -204;
                public static final int CV_StsOk = 0;
                public static final int CV_StsOutOfRange = -211;
                public static final int CV_StsParseError = -212;
                public static final int CV_StsUnmatchedFormats = -205;
                public static final int CV_StsUnmatchedSizes = -209;
                public static final int CV_StsUnsupportedFormat = -210;
                public static final int CV_StsVecLengthErr = -28;
                public static final int CV_TERMCRIT_EPS = 2;
                public static final int CV_TERMCRIT_ITER = 1;
                public static final int CV_TERMCRIT_NUMBER = 1;
                public static final String CV_TYPE_NAME_GRAPH = "opencv-graph";
                public static final String CV_TYPE_NAME_IMAGE = "opencv-image";
                public static final String CV_TYPE_NAME_MAT = "opencv-matrix";
                public static final String CV_TYPE_NAME_MATND = "opencv-nd-matrix";
                public static final String CV_TYPE_NAME_SEQ = "opencv-sequence";
                public static final String CV_TYPE_NAME_SEQ_TREE = "opencv-sequence-tree";
                public static final String CV_TYPE_NAME_SPARSE_MAT = "opencv-sparse-matrix";
                public static final int CV_USRTYPE1 = 7;
                public static final String CV_VERSION = "2.4.6.1";
                public static final int CV_VERSION_EPOCH = 2;
                public static final int CV_VERSION_MAJOR = 4;
                public static final int CV_VERSION_MINOR = 6;
                public static final int CV_VERSION_REVISION = 1;
                public static final CvSlice CV_WHOLE_ARR;
                public static final CvSlice CV_WHOLE_SEQ;
                public static final int CV_WHOLE_SEQ_END_INDEX = 1073741823;
                public static final int IPL_ALIGN_16BYTES = 16;
                public static final int IPL_ALIGN_32BYTES = 32;
                public static final int IPL_ALIGN_4BYTES = 4;
                public static final int IPL_ALIGN_8BYTES = 8;
                public static final int IPL_ALIGN_DWORD = 4;
                public static final int IPL_ALIGN_QWORD = 8;
                public static final int IPL_BORDER_CONSTANT = 0;
                public static final int IPL_BORDER_REFLECT = 2;
                public static final int IPL_BORDER_REFLECT_101 = 4;
                public static final int IPL_BORDER_REPLICATE = 1;
                public static final int IPL_BORDER_TRANSPARENT = 5;
                public static final int IPL_BORDER_WRAP = 3;
                public static final int IPL_DATA_ORDER_PIXEL = 0;
                public static final int IPL_DATA_ORDER_PLANE = 1;
                public static final int IPL_DEPTH_16S = -2147483632;
                public static final int IPL_DEPTH_16U = 16;
                public static final int IPL_DEPTH_1U = 1;
                public static final int IPL_DEPTH_32F = 32;
                public static final int IPL_DEPTH_32S = -2147483616;
                public static final int IPL_DEPTH_64F = 64;
                public static final int IPL_DEPTH_8S = -2147483640;
                public static final int IPL_DEPTH_8U = 8;
                public static final int IPL_DEPTH_SIGN = Integer.MIN_VALUE;
                public static final int IPL_IMAGE_DATA = 2;
                public static final int IPL_IMAGE_HEADER = 1;
                public static final int IPL_IMAGE_MAGIC_VAL;
                public static final int IPL_IMAGE_ROI = 4;
                public static final int IPL_ORIGIN_BL = 1;
                public static final int IPL_ORIGIN_TL = 0;
                public static final int NORM_HAMMING = 6;
                public static final int NORM_HAMMING2 = 7;
                public static final int NORM_INF = 1;
                public static final int NORM_L1 = 2;
                public static final int NORM_L2 = 4;
                public static final int NORM_L2SQR = 5;
                public static final int NORM_MINMAX = 32;
                public static final int NORM_RELATIVE = 8;
                public static final int NORM_TYPE_MASK = 7;

                @Target({ElementType.METHOD, ElementType.PARAMETER})
                @Const
                @Retention(RetentionPolicy.RUNTIME)
                @Adapter("ArrayAdapter")
                public @interface InputArray {
                }

                @Target({ElementType.METHOD, ElementType.PARAMETER})
                @Const
                @Retention(RetentionPolicy.RUNTIME)
                @Adapter("MatAdapter")
                public @interface InputMat {
                }

                @Target({ElementType.METHOD, ElementType.PARAMETER})
                @Retention(RetentionPolicy.RUNTIME)
                @Adapter("ArrayAdapter")
                public @interface OutputArray {
                }

                @Target({ElementType.METHOD, ElementType.PARAMETER})
                @Retention(RetentionPolicy.RUNTIME)
                @Adapter("MatAdapter")
                public @interface OutputMat {
                }

                @Target({ElementType.METHOD, ElementType.PARAMETER})
                @Retention(RetentionPolicy.RUNTIME)
                @Adapter("PtrAdapter")
                @Cast({"cv::Ptr", "&"})
                public @interface Ptr {
                    String value() default "";
                }

                public static native void CV_NEXT_LINE_POINT(@ByVal CvLineIterator cvLineIterator);

                public static native void CV_NEXT_SEQ_ELEM(int i, @ByVal CvSeqReader cvSeqReader);

                public static native void CV_PREV_SEQ_ELEM(int i, @ByVal CvSeqReader cvSeqReader);

                public static native void CV_READ_SEQ_ELEM(@ByVal CvPoint cvPoint, @ByVal CvSeqReader cvSeqReader);

                public static native void CV_REV_READ_SEQ_ELEM(@ByVal CvPoint cvPoint, @ByVal CvSeqReader cvSeqReader);

                public static native void CV_WRITE_SEQ_ELEM(@ByVal CvPoint cvPoint, @ByVal CvSeqWriter cvSeqWriter);

                public static native void CV_WRITE_SEQ_ELEM_VAR(Pointer pointer, @ByVal CvSeqWriter cvSeqWriter);

                public static native void SetLibraryPath(String str);

                @Namespace("cv")
                public static native void batchDistance(@InputArray CvArr cvArr, @InputArray CvArr cvArr2, @OutputArray CvMat cvMat, int i, @OutputArray CvMat cvMat2, int i2, int i3, @InputArray CvArr cvArr3, int i4, @Cast({"bool"}) boolean z);

                public static native void cvAbsDiff(CvArr cvArr, CvArr cvArr2, CvArr cvArr3);

                public static native void cvAbsDiffS(CvArr cvArr, CvArr cvArr2, @ByVal CvScalar cvScalar);

                public static native void cvAdd(CvArr cvArr, CvArr cvArr2, CvArr cvArr3, CvArr cvArr4);

                public static native void cvAddS(CvArr cvArr, @ByVal CvScalar cvScalar, CvArr cvArr2, CvArr cvArr3);

                public static native void cvAddWeighted(CvArr cvArr, double d, CvArr cvArr2, double d2, double d3, CvArr cvArr3);

                public static native Pointer cvAlloc(@Cast({"size_t"}) long j);

                public static native void cvAnd(CvArr cvArr, CvArr cvArr2, CvArr cvArr3, CvArr cvArr4);

                public static native void cvAndS(CvArr cvArr, @ByVal CvScalar cvScalar, CvArr cvArr2, CvArr cvArr3);

                public static native String cvAttrValue(CvAttrList cvAttrList, String str);

                @ByVal
                public static native CvScalar cvAvg(CvArr cvArr, CvArr cvArr2);

                public static native void cvAvgSdv(CvArr cvArr, CvScalar cvScalar, CvScalar cvScalar2, CvArr cvArr2);

                public static native void cvBackProjectPCA(CvArr cvArr, CvArr cvArr2, CvArr cvArr3, CvArr cvArr4);

                public static native void cvCalcCovarMatrix(@Const CvArrArray cvArrArray, int i, CvArr cvArr, CvArr cvArr2, int i2);

                public static native void cvCalcPCA(CvArr cvArr, CvArr cvArr2, CvArr cvArr3, CvArr cvArr4, int i);

                public static native void cvCartToPolar(CvArr cvArr, CvArr cvArr2, CvArr cvArr3, CvArr cvArr4, int i);

                public static native float cvCbrt(float f);

                public static native void cvChangeSeqBlock(CvSeqReader cvSeqReader, int i);

                public static native int cvCheckArr(CvArr cvArr, int i, double d, double d2);

                public static native int cvCheckHardwareSupport(int i);

                @ByVal
                public static native CvTermCriteria cvCheckTermCriteria(@ByVal CvTermCriteria cvTermCriteria, double d, int i);

                public static native void cvCircle(CvArr cvArr, @ByVal CvPoint cvPoint, int i, @ByVal CvScalar cvScalar, int i2, int i3, int i4);

                public static native void cvClearGraph(CvGraph cvGraph);

                public static native void cvClearMemStorage(CvMemStorage cvMemStorage);

                public static native void cvClearND(CvArr cvArr, int[] iArr);

                public static native void cvClearSeq(CvSeq cvSeq);

                public static native void cvClearSet(CvSet cvSet);

                public static native int cvClipLine(@ByVal CvSize cvSize, CvPoint cvPoint, CvPoint cvPoint2);

                public static native Pointer cvClone(Pointer pointer);

                public static native CvGraph cvCloneGraph(CvGraph cvGraph, CvMemStorage cvMemStorage);

                public static native IplImage cvCloneImage(IplImage iplImage);

                public static native CvMat cvCloneMat(CvMat cvMat);

                public static native CvMatND cvCloneMatND(CvMatND cvMatND);

                public static native CvSparseMat cvCloneSparseMat(CvSparseMat cvSparseMat);

                public static native void cvCmp(CvArr cvArr, CvArr cvArr2, CvArr cvArr3, int i);

                public static native void cvCmpS(CvArr cvArr, double d, CvArr cvArr2, int i);

                @ByVal
                public static native CvScalar cvColorToScalar(double d, int i);

                public static native void cvCompleteSymm(CvMat cvMat, int i);

                public static native void cvConvertScale(CvArr cvArr, CvArr cvArr2, double d, double d2);

                public static native void cvConvertScaleAbs(CvArr cvArr, CvArr cvArr2, double d, double d2);

                public static native void cvCopy(CvArr cvArr, CvArr cvArr2, CvArr cvArr3);

                public static native int cvCountNonZero(CvArr cvArr);

                public static native CvMemStorage cvCreateChildMemStorage(CvMemStorage cvMemStorage);

                public static native void cvCreateData(CvArr cvArr);

                public static native CvGraph cvCreateGraph(int i, int i2, int i3, int i4, CvMemStorage cvMemStorage);

                public static native CvGraphScanner cvCreateGraphScanner(CvGraph cvGraph, CvGraphVtx cvGraphVtx, int i);

                public static native IplImage cvCreateImage(@ByVal CvSize cvSize, int i, int i2);

                public static native IplImage cvCreateImageHeader(@ByVal CvSize cvSize, int i, int i2);

                public static native CvMat cvCreateMat(int i, int i2, int i3);

                public static native CvMat cvCreateMatHeader(int i, int i2, int i3);

                public static native CvMatND cvCreateMatND(int i, int[] iArr, int i2);

                public static native CvMatND cvCreateMatNDHeader(int i, int[] iArr, int i2);

                public static native CvMemStorage cvCreateMemStorage(int i);

                public static native CvSeq cvCreateSeq(int i, @Cast({"size_t"}) long j, int i2, CvMemStorage cvMemStorage);

                public static native void cvCreateSeqBlock(CvSeqWriter cvSeqWriter);

                public static native CvSet cvCreateSet(int i, int i2, int i3, CvMemStorage cvMemStorage);

                public static native CvSparseMat cvCreateSparseMat(int i, int[] iArr, int i2);

                public static native void cvCrossProduct(CvArr cvArr, CvArr cvArr2, CvArr cvArr3);

                public static native Pointer cvCvtSeqToArray(CvSeq cvSeq, Pointer pointer, @ByVal CvSlice cvSlice);

                public static native Pointer cvCvtSeqToArray(CvSeq cvSeq, Buffer buffer, @ByVal CvSlice cvSlice);

                public static native void cvDCT(CvArr cvArr, CvArr cvArr2, int i);

                public static native void cvDFT(CvArr cvArr, CvArr cvArr2, int i, int i2);

                public static native double cvDet(CvArr cvArr);

                public static native void cvDiv(CvArr cvArr, CvArr cvArr2, CvArr cvArr3, double d);

                public static native double cvDotProduct(CvArr cvArr, CvArr cvArr2);

                public static native void cvDrawContours(CvArr cvArr, CvSeq cvSeq, @ByVal CvScalar cvScalar, @ByVal CvScalar cvScalar2, int i, int i2, int i3, @ByVal CvPoint cvPoint);

                public static native void cvEigenVV(CvArr cvArr, CvArr cvArr2, CvArr cvArr3, double d, int i, int i2);

                public static native void cvEllipse(CvArr cvArr, @ByVal CvPoint cvPoint, @ByVal CvSize cvSize, double d, double d2, double d3, @ByVal CvScalar cvScalar, int i, int i2, int i3);

                public static native int cvEllipse2Poly(@ByVal CvPoint cvPoint, @ByVal CvSize cvSize, int i, int i2, int i3, CvPoint cvPoint2, int i4);

                public static native CvSeq cvEndWriteSeq(CvSeqWriter cvSeqWriter);

                public static native void cvEndWriteStruct(CvFileStorage cvFileStorage);

                public static native void cvError(int i, String str, String str2, String str3, int i2);

                public static native int cvErrorFromIppStatus(int i);

                public static native String cvErrorStr(int i);

                public static native void cvExp(CvArr cvArr, CvArr cvArr2);

                public static native float cvFastArctan(float f, float f2);

                public static native void cvFillConvexPoly(CvArr cvArr, CvPoint cvPoint, int i, @ByVal CvScalar cvScalar, int i2, int i3);

                public static native void cvFillConvexPoly(CvArr cvArr, @Cast({"CvPoint*"}) int[] iArr, int i, @ByVal CvScalar cvScalar, int i2, int i3);

                public static native void cvFillPoly(CvArr cvArr, @Cast({"CvPoint**"}) PointerPointer pointerPointer, int[] iArr, int i, @ByVal CvScalar cvScalar, int i2, int i3);

                public static native void cvFillPoly(CvArr cvArr, @ByPtrPtr CvPoint cvPoint, int[] iArr, int i, @ByVal CvScalar cvScalar, int i2, int i3);

                public static native CvGraphEdge cvFindGraphEdge(CvGraph cvGraph, int i, int i2);

                public static native CvGraphEdge cvFindGraphEdgeByPtr(CvGraph cvGraph, CvGraphVtx cvGraphVtx, CvGraphVtx cvGraphVtx2);

                public static native CvTypeInfo cvFindType(String str);

                public static native CvTypeInfo cvFirstType();

                public static native void cvFlip(CvArr cvArr, CvArr cvArr2, int i);

                public static native void cvFlushSeqWriter(CvSeqWriter cvSeqWriter);

                public static native void cvFree_(Pointer pointer);

                public static native void cvGEMM(CvArr cvArr, CvArr cvArr2, double d, CvArr cvArr3, double d2, CvArr cvArr4, int i);

                @ByVal
                public static native CvScalar cvGet1D(CvArr cvArr, int i);

                @ByVal
                public static native CvScalar cvGet2D(CvArr cvArr, int i, int i2);

                @ByVal
                public static native CvScalar cvGet3D(CvArr cvArr, int i, int i2, int i3);

                public static native CvMat cvGetCols(CvArr cvArr, CvMat cvMat, int i, int i2);

                public static native CvMat cvGetDiag(CvArr cvArr, CvMat cvMat, int i);

                public static native int cvGetDimSize(CvArr cvArr, int i);

                public static native int cvGetDims(CvArr cvArr, int[] iArr);

                public static native int cvGetElemType(CvArr cvArr);

                public static native int cvGetErrInfo(@ByPtrPtr @Cast({"const char**"}) BytePointer bytePointer, @ByPtrPtr @Cast({"const char**"}) BytePointer bytePointer2, @ByPtrPtr @Cast({"const char**"}) BytePointer bytePointer3, int[] iArr);

                public static native int cvGetErrMode();

                public static native int cvGetErrStatus();

                public static native CvFileNode cvGetFileNode(CvFileStorage cvFileStorage, CvFileNode cvFileNode, CvStringHashNode cvStringHashNode, int i);

                public static native CvFileNode cvGetFileNodeByName(CvFileStorage cvFileStorage, CvFileNode cvFileNode, String str);

                public static native String cvGetFileNodeName(CvFileNode cvFileNode);

                public static native CvStringHashNode cvGetHashedKey(CvFileStorage cvFileStorage, String str, int i, int i2);

                public static native IplImage cvGetImage(CvArr cvArr, IplImage iplImage);

                public static native int cvGetImageCOI(IplImage iplImage);

                @ByVal
                public static native CvRect cvGetImageROI(IplImage iplImage);

                public static native CvMat cvGetMat(CvArr cvArr, CvMat cvMat, int[] iArr, int i);

                public static native void cvGetModuleInfo(String str, @ByPtrPtr @Cast({"const char**"}) BytePointer bytePointer, @ByPtrPtr @Cast({"const char**"}) BytePointer bytePointer2);

                @ByVal
                public static native CvScalar cvGetND(CvArr cvArr, int[] iArr);

                public static native CvSparseNode cvGetNextSparseNode(CvSparseMatIterator cvSparseMatIterator);

                public static native int cvGetNumThreads();

                public static native int cvGetOptimalDFTSize(int i);

                public static native void cvGetRawData(CvArr cvArr, @ByPtrPtr @Cast({"uchar**"}) BytePointer bytePointer, int[] iArr, CvSize cvSize);

                public static native double cvGetReal1D(CvArr cvArr, int i);

                public static native double cvGetReal2D(CvArr cvArr, int i, int i2);

                public static native double cvGetReal3D(CvArr cvArr, int i, int i2, int i3);

                public static native double cvGetRealND(CvArr cvArr, int[] iArr);

                public static native CvFileNode cvGetRootFileNode(CvFileStorage cvFileStorage, int i);

                public static native CvMat cvGetRows(CvArr cvArr, CvMat cvMat, int i, int i2, int i3);

                public static native Pointer cvGetSeqElem(CvSeq cvSeq, int i);

                public static native int cvGetSeqReaderPos(CvSeqReader cvSeqReader);

                @ByVal
                public static native CvSize cvGetSize(CvArr cvArr);

                public static native CvMat cvGetSubRect(CvArr cvArr, CvMat cvMat, @ByVal CvRect cvRect);

                public static native void cvGetTextSize(String str, CvFont cvFont, CvSize cvSize, int[] iArr);

                public static native int cvGetThreadNum();

                public static native long cvGetTickCount();

                public static native double cvGetTickFrequency();

                public static native int cvGraphAddEdge(CvGraph cvGraph, int i, int i2, CvGraphEdge cvGraphEdge, @ByPtrPtr CvGraphEdge cvGraphEdge2);

                public static native int cvGraphAddEdgeByPtr(CvGraph cvGraph, CvGraphVtx cvGraphVtx, CvGraphVtx cvGraphVtx2, CvGraphEdge cvGraphEdge, @ByPtrPtr CvGraphEdge cvGraphEdge2);

                public static native int cvGraphAddVtx(CvGraph cvGraph, CvGraphVtx cvGraphVtx, @ByPtrPtr CvGraphVtx cvGraphVtx2);

                public static native void cvGraphRemoveEdge(CvGraph cvGraph, int i, int i2);

                public static native void cvGraphRemoveEdgeByPtr(CvGraph cvGraph, CvGraphVtx cvGraphVtx, CvGraphVtx cvGraphVtx2);

                public static native int cvGraphRemoveVtx(CvGraph cvGraph, int i);

                public static native int cvGraphRemoveVtxByPtr(CvGraph cvGraph, CvGraphVtx cvGraphVtx);

                public static native int cvGraphVtxDegree(CvGraph cvGraph, int i);

                public static native int cvGraphVtxDegreeByPtr(CvGraph cvGraph, CvGraphVtx cvGraphVtx);

                public static native int cvGuiBoxReport(int i, String str, String str2, String str3, int i2, Pointer pointer);

                public static native void cvInRange(CvArr cvArr, CvArr cvArr2, CvArr cvArr3, CvArr cvArr4);

                public static native void cvInRangeS(CvArr cvArr, @ByVal CvScalar cvScalar, @ByVal CvScalar cvScalar2, CvArr cvArr2);

                public static native void cvInitFont(CvFont cvFont, int i, double d, double d2, double d3, int i2, int i3);

                public static native IplImage cvInitImageHeader(IplImage iplImage, @ByVal CvSize cvSize, int i, int i2, int i3, int i4);

                public static native int cvInitLineIterator(CvArr cvArr, @ByVal CvPoint cvPoint, @ByVal CvPoint cvPoint2, CvLineIterator cvLineIterator, int i, int i2);

                public static native CvMat cvInitMatHeader(CvMat cvMat, int i, int i2, int i3, Pointer pointer, int i4);

                public static native CvMatND cvInitMatNDHeader(CvMatND cvMatND, int i, int[] iArr, int i2, Pointer pointer);

                public static native int cvInitNArrayIterator(int i, CvArrArray cvArrArray, CvArr cvArr, CvMatND cvMatND, CvNArrayIterator cvNArrayIterator, int i2);

                public static native CvSparseNode cvInitSparseMatIterator(CvSparseMat cvSparseMat, CvSparseMatIterator cvSparseMatIterator);

                public static native void cvInitTreeNodeIterator(CvTreeNodeIterator cvTreeNodeIterator, Pointer pointer, int i);

                public static native void cvInsertNodeIntoTree(Pointer pointer, Pointer pointer2, Pointer pointer3);

                public static native double cvInvert(CvArr cvArr, CvArr cvArr2, int i);

                public static native int cvKMeans2(CvArr cvArr, int i, CvArr cvArr2, @ByVal CvTermCriteria cvTermCriteria, int i2, CvRNG cvRNG, int i3, CvArr cvArr3, double[] dArr);

                public static native void cvLUT(CvArr cvArr, CvArr cvArr2, CvArr cvArr3);

                public static native void cvLine(CvArr cvArr, @ByVal CvPoint cvPoint, @ByVal CvPoint cvPoint2, @ByVal CvScalar cvScalar, int i, int i2, int i3);

                public static native Pointer cvLoad(String str, CvMemStorage cvMemStorage, String str2, @ByPtrPtr @Cast({"const char**"}) BytePointer bytePointer);

                public static native void cvLog(CvArr cvArr, CvArr cvArr2);

                public static native double cvMahalanobis(CvArr cvArr, CvArr cvArr2, CvArr cvArr3);

                public static native CvSeq cvMakeSeqHeaderForArray(int i, int i2, int i3, Pointer pointer, int i4, CvSeq cvSeq, CvSeqBlock cvSeqBlock);

                public static native void cvMax(CvArr cvArr, CvArr cvArr2, CvArr cvArr3);

                public static native void cvMaxS(CvArr cvArr, double d, CvArr cvArr2);

                public static native Pointer cvMemStorageAlloc(CvMemStorage cvMemStorage, @Cast({"size_t"}) long j);

                @ByVal
                public static native CvString cvMemStorageAllocString(CvMemStorage cvMemStorage, @Cast({"const char*"}) BytePointer bytePointer, int i);

                @ByVal
                public static native CvString cvMemStorageAllocString(CvMemStorage cvMemStorage, String str, int i);

                public static native void cvMerge(CvArr cvArr, CvArr cvArr2, CvArr cvArr3, CvArr cvArr4, CvArr cvArr5);

                public static native void cvMin(CvArr cvArr, CvArr cvArr2, CvArr cvArr3);

                public static native void cvMinMaxLoc(CvArr cvArr, double[] dArr, double[] dArr2, CvPoint cvPoint, CvPoint cvPoint2, CvArr cvArr2);

                public static native void cvMinMaxLoc(CvArr cvArr, double[] dArr, double[] dArr2, @Cast({"CvPoint*"}) int[] iArr, @Cast({"CvPoint*"}) int[] iArr2, CvArr cvArr2);

                public static native void cvMinS(CvArr cvArr, double d, CvArr cvArr2);

                public static native void cvMixChannels(@Const CvArrArray cvArrArray, int i, CvArrArray cvArrArray2, int i2, int[] iArr, int i3);

                public static native void cvMul(CvArr cvArr, CvArr cvArr2, CvArr cvArr3, double d);

                public static native void cvMulSpectrums(CvArr cvArr, CvArr cvArr2, CvArr cvArr3, int i);

                public static native void cvMulTransposed(CvArr cvArr, CvArr cvArr2, int i, CvArr cvArr3, double d);

                public static native int cvNextGraphItem(CvGraphScanner cvGraphScanner);

                public static native int cvNextNArraySlice(CvNArrayIterator cvNArrayIterator);

                public static native Pointer cvNextTreeNode(CvTreeNodeIterator cvTreeNodeIterator);

                public static native double cvNorm(CvArr cvArr, CvArr cvArr2, int i, CvArr cvArr3);

                public static native void cvNormalize(CvArr cvArr, CvArr cvArr2, double d, double d2, int i, CvArr cvArr3);

                public static native void cvNot(CvArr cvArr, CvArr cvArr2);

                public static native int cvNulDevReport(int i, String str, String str2, String str3, int i2, Pointer pointer);

                public static native CvFileStorage cvOpenFileStorage(String str, CvMemStorage cvMemStorage, int i, String str2);

                public static native void cvOr(CvArr cvArr, CvArr cvArr2, CvArr cvArr3, CvArr cvArr4);

                public static native void cvOrS(CvArr cvArr, @ByVal CvScalar cvScalar, CvArr cvArr2, CvArr cvArr3);

                public static native void cvPerspectiveTransform(CvArr cvArr, CvArr cvArr2, CvMat cvMat);

                public static native void cvPolarToCart(CvArr cvArr, CvArr cvArr2, CvArr cvArr3, CvArr cvArr4, int i);

                public static native void cvPolyLine(CvArr cvArr, @Cast({"CvPoint**"}) PointerPointer pointerPointer, int[] iArr, int i, int i2, @ByVal CvScalar cvScalar, int i3, int i4, int i5);

                public static native void cvPolyLine(CvArr cvArr, @ByPtrPtr CvPoint cvPoint, int[] iArr, int i, int i2, @ByVal CvScalar cvScalar, int i3, int i4, int i5);

                public static native void cvPow(CvArr cvArr, CvArr cvArr2, double d);

                public static native Pointer cvPrevTreeNode(CvTreeNodeIterator cvTreeNodeIterator);

                public static native void cvProjectPCA(CvArr cvArr, CvArr cvArr2, CvArr cvArr3, CvArr cvArr4);

                public static native Pointer cvPtr1D(CvArr cvArr, int i, int[] iArr);

                public static native Pointer cvPtr2D(CvArr cvArr, int i, int i2, int[] iArr);

                public static native Pointer cvPtr3D(CvArr cvArr, int i, int i2, int i3, int[] iArr);

                public static native Pointer cvPtrND(CvArr cvArr, int[] iArr, int[] iArr2, int i, @Cast({"unsigned*"}) int[] iArr3);

                public static native void cvPutText(CvArr cvArr, String str, @ByVal CvPoint cvPoint, CvFont cvFont, @ByVal CvScalar cvScalar);

                public static native void cvRandArr(CvRNG cvRNG, CvArr cvArr, int i, @ByVal CvScalar cvScalar, @ByVal CvScalar cvScalar2);

                public static native void cvRandShuffle(CvArr cvArr, CvRNG cvRNG, double d);

                public static native CvArr cvRange(CvArr cvArr, double d, double d2);

                public static native void cvRawDataToScalar(Pointer pointer, int i, CvScalar cvScalar);

                public static native Pointer cvRead(CvFileStorage cvFileStorage, CvFileNode cvFileNode, CvAttrList cvAttrList);

                public static native void cvReadRawData(CvFileStorage cvFileStorage, CvFileNode cvFileNode, Pointer pointer, String str);

                public static native void cvReadRawDataSlice(CvFileStorage cvFileStorage, CvSeqReader cvSeqReader, int i, Pointer pointer, String str);

                public static native void cvRectangle(CvArr cvArr, @ByVal CvPoint cvPoint, @ByVal CvPoint cvPoint2, @ByVal CvScalar cvScalar, int i, int i2, int i3);

                public static native void cvRectangleR(CvArr cvArr, @ByVal CvRect cvRect, @ByVal CvScalar cvScalar, int i, int i2, int i3);

                public static native CvErrorCallback cvRedirectError(CvErrorCallback cvErrorCallback, Pointer pointer, @ByPtrPtr @Cast({"void**"}) Pointer pointer2);

                public static native void cvReduce(CvArr cvArr, CvArr cvArr2, int i, int i2);

                public static native int cvRegisterModule(CvModuleInfo cvModuleInfo);

                public static native void cvRegisterType(CvTypeInfo cvTypeInfo);

                public static native void cvRelease(PointerPointer pointerPointer);

                public static native void cvReleaseData(CvArr cvArr);

                public static native void cvReleaseFileStorage(@ByPtrPtr CvFileStorage cvFileStorage);

                public static native void cvReleaseGraphScanner(@ByPtrPtr CvGraphScanner cvGraphScanner);

                public static native void cvReleaseImage(@ByPtrPtr IplImage iplImage);

                public static native void cvReleaseImageHeader(@ByPtrPtr IplImage iplImage);

                public static native void cvReleaseMat(@ByPtrPtr CvMat cvMat);

                public static native void cvReleaseMatND(@ByPtrPtr CvMatND cvMatND);

                public static native void cvReleaseMemStorage(@ByPtrPtr CvMemStorage cvMemStorage);

                public static native void cvReleaseSparseMat(@ByPtrPtr CvSparseMat cvSparseMat);

                public static native void cvRemoveNodeFromTree(Pointer pointer, Pointer pointer2);

                public static native void cvRepeat(CvArr cvArr, CvArr cvArr2);

                public static native void cvResetImageROI(IplImage iplImage);

                public static native CvMat cvReshape(CvArr cvArr, CvMat cvMat, int i, int i2);

                public static native CvArr cvReshapeMatND(CvArr cvArr, int i, CvArr cvArr2, int i2, int i3, int[] iArr);

                public static native void cvRestoreMemStoragePos(CvMemStorage cvMemStorage, CvMemStoragePos cvMemStoragePos);

                public static native void cvSVBkSb(CvArr cvArr, CvArr cvArr2, CvArr cvArr3, CvArr cvArr4, CvArr cvArr5, int i);

                public static native void cvSVD(CvArr cvArr, CvArr cvArr2, CvArr cvArr3, CvArr cvArr4, int i);

                public static native void cvSave(String str, Pointer pointer, String str2, String str3, @ByVal CvAttrList cvAttrList);

                public static native void cvSaveMemStoragePos(CvMemStorage cvMemStorage, CvMemStoragePos cvMemStoragePos);

                public static native void cvScalarToRawData(CvScalar cvScalar, Pointer pointer, int i, int i2);

                public static native void cvScaleAdd(CvArr cvArr, @ByVal CvScalar cvScalar, CvArr cvArr2, CvArr cvArr3);

                public static native int cvSeqElemIdx(CvSeq cvSeq, Pointer pointer, @ByPtrPtr CvSeqBlock cvSeqBlock);

                public static native Pointer cvSeqInsert(CvSeq cvSeq, int i, Pointer pointer);

                public static native void cvSeqInsertSlice(CvSeq cvSeq, int i, CvArr cvArr);

                public static native void cvSeqInvert(CvSeq cvSeq);

                public static native int cvSeqPartition(CvSeq cvSeq, CvMemStorage cvMemStorage, @ByPtrPtr CvSeq cvSeq2, CvCmpFunc cvCmpFunc, Pointer pointer);

                public static native void cvSeqPop(CvSeq cvSeq, Pointer pointer);

                public static native void cvSeqPopFront(CvSeq cvSeq, Pointer pointer);

                public static native void cvSeqPopMulti(CvSeq cvSeq, Pointer pointer, int i, int i2);

                @Cast({"schar*"})
                public static native BytePointer cvSeqPush(CvSeq cvSeq, Pointer pointer);

                @Cast({"schar*"})
                public static native BytePointer cvSeqPushFront(CvSeq cvSeq, Pointer pointer);

                public static native void cvSeqPushMulti(CvSeq cvSeq, Pointer pointer, int i, int i2);

                public static native void cvSeqRemove(CvSeq cvSeq, int i);

                public static native void cvSeqRemoveSlice(CvSeq cvSeq, @ByVal CvSlice cvSlice);

                public static native Pointer cvSeqSearch(CvSeq cvSeq, Pointer pointer, CvCmpFunc cvCmpFunc, int i, int[] iArr, Pointer pointer2);

                public static native CvSeq cvSeqSlice(CvSeq cvSeq, @ByVal CvSlice cvSlice, CvMemStorage cvMemStorage, int i);

                public static native void cvSeqSort(CvSeq cvSeq, CvCmpFunc cvCmpFunc, Pointer pointer);

                public static native void cvSet(CvArr cvArr, @ByVal CvScalar cvScalar, CvArr cvArr2);

                public static native void cvSet1D(CvArr cvArr, int i, @ByVal CvScalar cvScalar);

                public static native void cvSet2D(CvArr cvArr, int i, int i2, @ByVal CvScalar cvScalar);

                public static native void cvSet3D(CvArr cvArr, int i, int i2, int i3, @ByVal CvScalar cvScalar);

                public static native int cvSetAdd(CvSet cvSet, CvSetElem cvSetElem, @ByPtrPtr CvSetElem cvSetElem2);

                public static native void cvSetData(CvArr cvArr, Pointer pointer, int i);

                public static native int cvSetErrMode(int i);

                public static native void cvSetErrStatus(int i);

                public static native void cvSetIPLAllocators(Cv_iplCreateImageHeader cv_iplCreateImageHeader, Cv_iplAllocateImageData cv_iplAllocateImageData, Cv_iplDeallocate cv_iplDeallocate, Cv_iplCreateROI cv_iplCreateROI, Cv_iplCloneImage cv_iplCloneImage);

                public static native void cvSetIdentity(CvArr cvArr, @ByVal CvScalar cvScalar);

                public static native void cvSetImageCOI(IplImage iplImage, int i);

                public static native void cvSetImageROI(IplImage iplImage, @ByVal CvRect cvRect);

                public static native void cvSetMemoryManager(CvAllocFunc cvAllocFunc, CvFreeFunc cvFreeFunc, Pointer pointer);

                public static native void cvSetND(CvArr cvArr, int[] iArr, @ByVal CvScalar cvScalar);

                public static native void cvSetNumThreads(int i);

                public static native void cvSetReal1D(CvArr cvArr, int i, double d);

                public static native void cvSetReal2D(CvArr cvArr, int i, int i2, double d);

                public static native void cvSetReal3D(CvArr cvArr, int i, int i2, int i3, double d);

                public static native void cvSetRealND(CvArr cvArr, int[] iArr, double d);

                public static native void cvSetRemove(CvSet cvSet, int i);

                public static native void cvSetSeqBlockSize(CvSeq cvSeq, int i);

                public static native void cvSetSeqReaderPos(CvSeqReader cvSeqReader, int i, int i2);

                public static native void cvSetZero(CvArr cvArr);

                public static native int cvSliceLength(@ByVal CvSlice cvSlice, CvSeq cvSeq);

                public static native int cvSolve(CvArr cvArr, CvArr cvArr2, CvArr cvArr3, int i);

                public static native int cvSolveCubic(CvMat cvMat, CvMat cvMat2);

                public static native void cvSolvePoly(CvMat cvMat, CvMat cvMat2, int i, int i2);

                public static native void cvSort(CvArr cvArr, CvArr cvArr2, CvArr cvArr3, int i);

                public static native void cvSplit(CvArr cvArr, CvArr cvArr2, CvArr cvArr3, CvArr cvArr4, CvArr cvArr5);

                public static native void cvStartAppendToSeq(CvSeq cvSeq, CvSeqWriter cvSeqWriter);

                public static native void cvStartNextStream(CvFileStorage cvFileStorage);

                public static native void cvStartReadRawData(CvFileStorage cvFileStorage, CvFileNode cvFileNode, CvSeqReader cvSeqReader);

                public static native void cvStartReadSeq(CvSeq cvSeq, CvSeqReader cvSeqReader, int i);

                public static native void cvStartWriteSeq(int i, int i2, int i3, CvMemStorage cvMemStorage, CvSeqWriter cvSeqWriter);

                public static native void cvStartWriteStruct(CvFileStorage cvFileStorage, String str, int i, String str2, @ByVal CvAttrList cvAttrList);

                public static native int cvStdErrReport(int i, String str, String str2, String str3, int i2, Pointer pointer);

                public static native void cvSub(CvArr cvArr, CvArr cvArr2, CvArr cvArr3, CvArr cvArr4);

                public static native void cvSubRS(CvArr cvArr, @ByVal CvScalar cvScalar, CvArr cvArr2, CvArr cvArr3);

                @ByVal
                public static native CvScalar cvSum(CvArr cvArr);

                @ByVal
                public static native CvScalar cvTrace(CvArr cvArr);

                public static native void cvTransform(CvArr cvArr, CvArr cvArr2, CvMat cvMat, CvMat cvMat2);

                public static native void cvTranspose(CvArr cvArr, CvArr cvArr2);

                public static native CvSeq cvTreeToNodeSeq(Pointer pointer, int i, CvMemStorage cvMemStorage);

                public static native CvTypeInfo cvTypeOf(Pointer pointer);

                public static native void cvUnregisterType(String str);

                public static native int cvUseOptimized(int i);

                public static native void cvWrite(CvFileStorage cvFileStorage, String str, Pointer pointer, @ByVal CvAttrList cvAttrList);

                public static native void cvWriteComment(CvFileStorage cvFileStorage, String str, int i);

                public static native void cvWriteFileNode(CvFileStorage cvFileStorage, String str, CvFileNode cvFileNode, int i);

                public static native void cvWriteInt(CvFileStorage cvFileStorage, String str, int i);

                public static native void cvWriteRawData(CvFileStorage cvFileStorage, Pointer pointer, int i, String str);

                public static native void cvWriteReal(CvFileStorage cvFileStorage, String str, double d);

                public static native void cvWriteString(CvFileStorage cvFileStorage, String str, String str2, int i);

                public static native void cvXor(CvArr cvArr, CvArr cvArr2, CvArr cvArr3, CvArr cvArr4);

                public static native void cvXorS(CvArr cvArr, @ByVal CvScalar cvScalar, CvArr cvArr2, CvArr cvArr3);

                @ByRef
                @Namespace("cv")
                public static native String getBuildInformation();

                @Name({"cv::partition<void*>"})
                public static native int partition(@StdVector PointerPointer pointerPointer, @StdVector IntPointer intPointer, @ByRef Predicate predicate);

                static {
/* 105 */           $assertionsDisabled = !opencv_core.class.desiredAssertionStatus();
/* 114 */           if (Loader.load() != null) {
/* 115 */               String platformName = Loader.getPlatformName();
/* 116 */               if (platformName.equals("windows-x86")) {
/* 117 */                   SetLibraryPath("C:/opencv/build/x86/vc10/bin/");
                        } else if (platformName.equals("windows-x86_64")) {
/* 119 */                   SetLibraryPath("C:/opencv/build/x64/vc10/bin/");
                        }
                    }
/* 926 */           IPL_IMAGE_MAGIC_VAL = Loader.load() == null ? 0 : Loader.sizeof(IplImage.class);
/* 966 */           CV_8UC1 = CV_MAKETYPE(0, 1);
/* 967 */           CV_8UC2 = CV_MAKETYPE(0, 2);
/* 968 */           CV_8UC3 = CV_MAKETYPE(0, 3);
/* 969 */           CV_8UC4 = CV_MAKETYPE(0, 4);
/* 971 */           CV_8SC1 = CV_MAKETYPE(1, 1);
/* 972 */           CV_8SC2 = CV_MAKETYPE(1, 2);
/* 973 */           CV_8SC3 = CV_MAKETYPE(1, 3);
/* 974 */           CV_8SC4 = CV_MAKETYPE(1, 4);
/* 976 */           CV_16UC1 = CV_MAKETYPE(2, 1);
/* 977 */           CV_16UC2 = CV_MAKETYPE(2, 2);
/* 978 */           CV_16UC3 = CV_MAKETYPE(2, 3);
/* 979 */           CV_16UC4 = CV_MAKETYPE(2, 4);
/* 981 */           CV_16SC1 = CV_MAKETYPE(3, 1);
/* 982 */           CV_16SC2 = CV_MAKETYPE(3, 2);
/* 983 */           CV_16SC3 = CV_MAKETYPE(3, 3);
/* 984 */           CV_16SC4 = CV_MAKETYPE(3, 4);
/* 986 */           CV_32SC1 = CV_MAKETYPE(4, 1);
/* 987 */           CV_32SC2 = CV_MAKETYPE(4, 2);
/* 988 */           CV_32SC3 = CV_MAKETYPE(4, 3);
/* 989 */           CV_32SC4 = CV_MAKETYPE(4, 4);
/* 991 */           CV_32FC1 = CV_MAKETYPE(5, 1);
/* 992 */           CV_32FC2 = CV_MAKETYPE(5, 2);
/* 993 */           CV_32FC3 = CV_MAKETYPE(5, 3);
/* 994 */           CV_32FC4 = CV_MAKETYPE(5, 4);
/* 996 */           CV_64FC1 = CV_MAKETYPE(6, 1);
/* 997 */           CV_64FC2 = CV_MAKETYPE(6, 2);
/* 998 */           CV_64FC3 = CV_MAKETYPE(6, 3);
/* 999 */           CV_64FC4 = CV_MAKETYPE(6, 4);
/* 1002 */          CV_WHOLE_ARR = Loader.load() == null ? null : cvSlice(0, CV_WHOLE_SEQ_END_INDEX);
/* 2384 */          CV_WHOLE_SEQ = Loader.load() == null ? null : cvSlice(0, CV_WHOLE_SEQ_END_INDEX);
/* 2769 */          CV_SEQ_ELTYPE_POINT = CV_32SC2;
/* 2770 */          CV_SEQ_ELTYPE_CODE = CV_8UC1;
/* 2774 */          CV_SEQ_ELTYPE_INDEX = CV_32SC1;
/* 2779 */          CV_SEQ_ELTYPE_POINT3D = CV_32FC3;
/* 2808 */          CV_SEQ_POINT_SET = CV_SEQ_ELTYPE_POINT | 0;
/* 2809 */          CV_SEQ_POINT3D_SET = CV_SEQ_ELTYPE_POINT3D | 0;
/* 2810 */          CV_SEQ_POLYLINE = CV_SEQ_ELTYPE_POINT | 4096;
/* 2811 */          CV_SEQ_POLYGON = CV_SEQ_POLYLINE | 16384;
/* 2812 */          CV_SEQ_CONTOUR = CV_SEQ_POLYGON;
/* 2813 */          CV_SEQ_SIMPLE_POLYGON = CV_SEQ_POLYGON | 0;
/* 2816 */          CV_SEQ_CHAIN = CV_SEQ_ELTYPE_CODE | 4096;
/* 2817 */          CV_SEQ_CHAIN_CONTOUR = CV_SEQ_CHAIN | 16384;
/* 2826 */          CV_SEQ_INDEX = CV_SEQ_ELTYPE_INDEX | 0;
/* 3008 */          CV_ATTR_LIST_EMPTY = new CvAttrList();
                }

                @Opaque
/* 134 */       public static class CvArr extends Pointer implements Cloneable {
                    static {
/* 136 */               Loader.load();
                    }

/* 136 */           protected CvArr() {
                    }

/* 137 */           protected CvArr(Pointer p) {
/* 138 */               super(p);
                    }
                }

                @Name({"CvArr*"})
/* 139 */       public static class CvArrArray extends Pointer {
                    private native void allocateArray(int i);

                    public native CvArr get();

                    public native CvArrArray put(CvArr cvArr);


                    static {
/* 143 */               Loader.load();
                    }

/* 143 */           public CvArrArray(CvArr... array) {
/* 144 */               this(array.length);
                        put(array);
                        position(0);
                    }

/* 144 */           public CvArrArray(int size) {
                        allocateArray(size);
                    }

/* 145 */           public CvArrArray(Pointer p) {
/* 146 */               super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 149 */           public CvArrArray position(int position) {
/* 150 */               return (CvArrArray) super.position(position);
                    }

/* 153 */           public CvArrArray put(CvArr... array) {
                        for (int i = 0; i < array.length; i++) {
/* 155 */                   position(i).put(array[i]);
                        }
/* 157 */               return this;
                    }
                }

                @Name({"CvMat*"})
/* 162 */       public static class CvMatArray extends CvArrArray {
                    private native void allocateArray(int i);

                    @Override // com.googlecode.javacv.cpp.opencv_core.CvArrArray
                    @ValueGetter
                    public native CvMat get();






/* 165 */           public CvMatArray(CvMat... array) {
/* 166 */               this(array.length);
                        put((CvArr[]) array);
                        position(0);
                    }

/* 166 */           public CvMatArray(int size) {
/* 167 */               super(new CvArr[0]);
                        allocateArray(size);
                    }

/* 167 */           public CvMatArray(Pointer p) {
/* 168 */               super(p);
                    }

                    @Override // com.googlecode.javacv.cpp.opencv_core.CvArrArray, com.googlecode.javacpp.Pointer
/* 171 */           public CvMatArray position(int position) {
/* 172 */               return (CvMatArray) super.position(position);
                    }

                    @Override // com.googlecode.javacv.cpp.opencv_core.CvArrArray
/* 174 */           public CvMatArray put(CvArr... array) {
/* 175 */               return (CvMatArray) super.put(array);
                    }

                    @Override // com.googlecode.javacv.cpp.opencv_core.CvArrArray
/* 178 */           public CvMatArray put(CvArr p) {
/* 179 */               if (p instanceof CvMat) {
/* 180 */                   return (CvMatArray) super.put(p);
                        }
/* 182 */               throw new ArrayStoreException(p.getClass().getName());
                    }
                }

                @Name({"CvMatND*"})
/* 185 */       public static class CvMatNDArray extends CvArrArray {
                    private native void allocateArray(int i);

                    @Override // com.googlecode.javacv.cpp.opencv_core.CvArrArray
                    @ValueGetter
                    public native CvMatND get();






/* 188 */           public CvMatNDArray(CvMatND... array) {
/* 189 */               this(array.length);
                        put((CvArr[]) array);
                        position(0);
                    }

/* 189 */           public CvMatNDArray(int size) {
/* 190 */               super(new CvArr[0]);
                        allocateArray(size);
                    }

/* 190 */           public CvMatNDArray(Pointer p) {
/* 191 */               super(p);
                    }

                    @Override // com.googlecode.javacv.cpp.opencv_core.CvArrArray, com.googlecode.javacpp.Pointer
/* 194 */           public CvMatNDArray position(int position) {
/* 195 */               return (CvMatNDArray) super.position(position);
                    }

                    @Override // com.googlecode.javacv.cpp.opencv_core.CvArrArray
/* 197 */           public CvMatNDArray put(CvArr... array) {
/* 198 */               return (CvMatNDArray) super.put(array);
                    }

                    @Override // com.googlecode.javacv.cpp.opencv_core.CvArrArray
/* 201 */           public CvMatNDArray put(CvArr p) {
/* 202 */               if (p instanceof CvMatND) {
/* 203 */                   return (CvMatNDArray) super.put(p);
                        }
/* 205 */               throw new ArrayStoreException(p.getClass().getName());
                    }
                }

                @Name({"IplImage*"})
/* 208 */       public static class IplImageArray extends CvArrArray {
                    private native void allocateArray(int i);

                    @Override // com.googlecode.javacv.cpp.opencv_core.CvArrArray
                    @ValueGetter
                    public native IplImage get();






/* 211 */           public IplImageArray(IplImage... array) {
/* 212 */               this(array.length);
                        put((CvArr[]) array);
                        position(0);
                    }

/* 212 */           public IplImageArray(int size) {
/* 213 */               super(new CvArr[0]);
                        allocateArray(size);
                    }

/* 213 */           public IplImageArray(Pointer p) {
/* 214 */               super(p);
                    }

                    @Override // com.googlecode.javacv.cpp.opencv_core.CvArrArray, com.googlecode.javacpp.Pointer
/* 217 */           public IplImageArray position(int position) {
/* 218 */               return (IplImageArray) super.position(position);
                    }

                    @Override // com.googlecode.javacv.cpp.opencv_core.CvArrArray
/* 220 */           public IplImageArray put(CvArr... array) {
/* 221 */               return (IplImageArray) super.put(array);
                    }

                    @Override // com.googlecode.javacv.cpp.opencv_core.CvArrArray
/* 224 */           public IplImageArray put(CvArr p) {
/* 225 */               if (p instanceof IplImage) {
/* 226 */                   return (IplImageArray) super.put(p);
                        }
/* 228 */               throw new ArrayStoreException(p.getClass().getName());
                    }
                }

/* 231 */       public static class Cv32suf extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    /* JADX INFO: renamed from: f */
                    public native float m2415f();

                    /* JADX INFO: renamed from: f */
                    public native Cv32suf m2416f(float f);

                    /* JADX INFO: renamed from: i */
                    public native int m2417i();

                    /* JADX INFO: renamed from: i */
                    public native Cv32suf m2418i(int i);

                    /* JADX INFO: renamed from: u */
                    public native int m2419u();

                    /* JADX INFO: renamed from: u */
                    public native Cv32suf m2420u(int i);


                    static {
/* 234 */               Loader.load();
                    }

/* 234 */           public Cv32suf() {
                        allocate();
                    }

/* 235 */           public Cv32suf(int size) {
                        allocateArray(size);
                    }

/* 236 */           public Cv32suf(Pointer p) {
/* 237 */               super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 241 */           public Cv32suf position(int position) {
/* 242 */               return (Cv32suf) super.position(position);
                    }
                }

/* 248 */       public static class Cv64suf extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    /* JADX INFO: renamed from: f */
                    public native double m2421f();

                    /* JADX INFO: renamed from: f */
                    public native Cv64suf m2422f(double d);

                    /* JADX INFO: renamed from: i */
                    public native long m2423i();

                    /* JADX INFO: renamed from: i */
                    public native Cv64suf m2424i(long j);

                    /* JADX INFO: renamed from: u */
                    public native long m2425u();

                    /* JADX INFO: renamed from: u */
                    public native Cv64suf m2426u(long j);


                    static {
/* 251 */               Loader.load();
                    }

/* 251 */           public Cv64suf() {
                        allocate();
                    }

/* 252 */           public Cv64suf(int size) {
                        allocateArray(size);
                    }

/* 253 */           public Cv64suf(Pointer p) {
/* 254 */               super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 258 */           public Cv64suf position(int position) {
/* 259 */               return (Cv64suf) super.position(position);
                    }
                }

/* 323 */       public static class CvRNG extends LongPointer {
                    private native void allocate();

                    static {
/* 325 */               Loader.load();
                    }

/* 325 */           public CvRNG() {
/* 326 */               this(null);
                        allocate();
                    }

/* 326 */           public CvRNG(Pointer p) {
/* 327 */               super(p);
                    }
                }

/* 330 */       public static CvRNG cvRNG() {
/* 331 */           return cvRNG(-1L);
                }

/* 333 */       public static CvRNG cvRNG(long seed) {
/* 334 */           CvRNG cvRNG = new CvRNG();
                    if (seed == 0) {
                        seed = -1;
                    }
                    return (CvRNG) cvRNG.put(seed);
                }

/* 336 */       public static int cvRandInt(CvRNG rng) {
/* 337 */           long temp = rng.get();
/* 338 */           long temp2 = ((temp & 4294967295L) * CV_RNG_COEFF) + ((temp >> 32) & 4294967295L);
/* 339 */           rng.put(temp2);
/* 340 */           return (int) temp2;
                }

/* 342 */       public static double cvRandReal(CvRNG rng) {
/* 343 */           return (((long) cvRandInt(rng)) & 4294967295L) * 2.3283064365386963E-10d;
                }

/* 377 */       public static class IplImage extends CvArr {
                    static final /* synthetic */ boolean $assertionsDisabled;
                    public static final byte[] gamma22;
                    public static final byte[] gamma22inv;

                    private native void allocate();

                    private native void allocateArray(int i);

                    public native int BorderConst(int i);

                    public native IplImage BorderConst(int i, int i2);

                    public native int BorderMode(int i);

                    public native IplImage BorderMode(int i, int i2);

                    /* JADX INFO: renamed from: ID */
                    public native int m2457ID();

                    /* JADX INFO: renamed from: ID */
                    public native IplImage m2458ID(int i);

                    public native int align();

                    public native IplImage align(int i);

                    public native int alphaChannel();

                    public native IplImage alphaChannel(int i);

                    public native int channelSeq(int i);

                    public native IplImage channelSeq(int i, int i2);

                    public native int colorModel(int i);

                    public native IplImage colorModel(int i, int i2);

                    public native int dataOrder();

                    public native IplImage dataOrder(int i);

                    public native int depth();

                    public native IplImage depth(int i);

                    public native int height();

                    public native IplImage height(int i);

                    @Cast({"char*"})
                    public native BytePointer imageData();

                    public native IplImage imageData(BytePointer bytePointer);

                    @MemberSetter
                    public native IplImage imageData(@Cast({"char*"}) ByteBuffer byteBuffer);

                    @Cast({"char*"})
                    public native BytePointer imageDataOrigin();

                    public native IplImage imageDataOrigin(BytePointer bytePointer);

                    public native Pointer imageId();

                    public native IplImage imageId(Pointer pointer);

                    public native int imageSize();

                    public native IplImage imageSize(int i);

                    public native IplImage maskROI();

                    public native IplImage maskROI(IplImage iplImage);

                    public native int nChannels();

                    public native IplImage nChannels(int i);

                    public native int nSize();

                    public native IplImage nSize(int i);

                    public native int origin();

                    public native IplImage origin(int i);

                    public native IplImage roi(IplROI iplROI);

                    public native IplROI roi();

                    public native IplImage tileInfo(IplTileInfo iplTileInfo);

                    public native IplTileInfo tileInfo();

                    public native int width();

                    public native IplImage width(int i);

                    public native int widthStep();

                    public native IplImage widthStep(int i);

                    static {
/* 379 */               $assertionsDisabled = !opencv_core.class.desiredAssertionStatus();
/* 532 */               gamma22 = new byte[256];
/* 533 */               gamma22inv = new byte[256];
                        for (int i = 0; i < 256; i++) {
/* 536 */                   gamma22[i] = (byte) Math.round(Math.pow(((double) i) / 255.0d, 2.2d) * 255.0d);
/* 537 */                   gamma22inv[i] = (byte) Math.round(Math.pow(((double) i) / 255.0d, 0.45454545454545453d) * 255.0d);
                        }
                    }

                    /* JADX INFO: renamed from: clone, reason: collision with other method in class */


/* 379 */           public IplImage() {
                        allocate();
                        zero();
                    }

/* 380 */           public IplImage(int size) {
                        allocateArray(size);
                        zero();
                    }

/* 381 */           public IplImage(Pointer p) {
/* 382 */               super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 386 */           public IplImage position(int position) {
/* 387 */               return (IplImage) super.position(position);
                    }

/* 390 */           public static IplImage create(CvSize size, int depth, int channels) {
/* 391 */               IplImage i = opencv_core.cvCreateImage(size, depth, channels);
/* 392 */               if (i != null) {
/* 393 */                   i.deallocator(new ReleaseDeallocator(i));
                        }
/* 395 */               return i;
                    }

/* 397 */           public static IplImage create(int width, int height, int depth, int channels) {
/* 398 */               return create(opencv_core.cvSize(width, height), depth, channels);
                    }

/* 400 */           public static IplImage create(CvSize size, int depth, int channels, int origin) {
/* 401 */               IplImage i = create(size, depth, channels);
/* 402 */               if (i != null) {
/* 403 */                   i.origin(origin);
                        }
/* 405 */               return i;
                    }

/* 407 */           public static IplImage create(int width, int height, int depth, int channels, int origin) {
/* 408 */               IplImage i = create(width, height, depth, channels);
/* 409 */               if (i != null) {
/* 410 */                   i.origin(origin);
                        }
/* 412 */               return i;
                    }

/* 415 */           public static IplImage createHeader(CvSize size, int depth, int channels) {
/* 416 */               IplImage i = opencv_core.cvCreateImageHeader(size, depth, channels);
/* 417 */               if (i != null) {
/* 418 */                   i.deallocator(new HeaderReleaseDeallocator(i));
                        }
/* 420 */               return i;
                    }

/* 422 */           public static IplImage createHeader(int width, int height, int depth, int channels) {
/* 423 */               return createHeader(opencv_core.cvSize(width, height), depth, channels);
                    }

/* 425 */           public static IplImage createHeader(CvSize size, int depth, int channels, int origin) {
/* 426 */               IplImage i = createHeader(size, depth, channels);
/* 427 */               if (i != null) {
/* 428 */                   i.origin(origin);
                        }
/* 430 */               return i;
                    }

/* 432 */           public static IplImage createHeader(int width, int height, int depth, int channels, int origin) {
/* 433 */               IplImage i = createHeader(width, height, depth, channels);
/* 434 */               if (i != null) {
/* 435 */                   i.origin(origin);
                        }
/* 437 */               return i;
                    }

/* 440 */           public static IplImage createCompatible(IplImage template) {
/* 441 */               return createIfNotCompatible(null, template);
                    }

/* 443 */           public static IplImage createIfNotCompatible(IplImage image, IplImage template) {
/* 444 */               if (image == null || image.width() != template.width() || image.height() != template.height() || image.depth() != template.depth() || image.nChannels() != template.nChannels()) {
/* 446 */                   image = create(template.width(), template.height(), template.depth(), template.nChannels(), template.origin());
                        }
/* 449 */               image.origin(template.origin());
/* 450 */               return image;
                    }

/* 454 */           public IplImage clone() {
/* 455 */               IplImage i = opencv_core.cvCloneImage(this);
/* 456 */               if (i != null) {
/* 457 */                   i.deallocator(new ReleaseDeallocator(i));
                        }
/* 459 */               return i;
                    }

/* 462 */           public void release() {
/* 463 */               deallocate();
                    }

/* 463 */           protected static class ReleaseDeallocator extends IplImage implements Pointer.Deallocator {
/* 465 */               ReleaseDeallocator(IplImage p) {
/* 466 */                   super(p);
                        }

                        @Override // com.googlecode.javacpp.Pointer, com.googlecode.javacpp.Pointer.Deallocator
/* 466 */               public void deallocate() {
/* 467 */                   opencv_core.cvReleaseImage(this);
                        }
                    }

/* 467 */           protected static class HeaderReleaseDeallocator extends IplImage implements Pointer.Deallocator {
/* 469 */               HeaderReleaseDeallocator(IplImage p) {
/* 470 */                   super(p);
                        }

                        @Override // com.googlecode.javacpp.Pointer, com.googlecode.javacpp.Pointer.Deallocator
/* 470 */               public void deallocate() {
/* 471 */                   opencv_core.cvReleaseImageHeader(this);
                        }
                    }

/* 502 */           public double highValue() {
                        switch (depth()) {
                            case opencv_core.IPL_DEPTH_8S /* -2147483640 */:
/* 507 */                       return 127.0d;
                            case opencv_core.IPL_DEPTH_16S /* -2147483632 */:
/* 508 */                       return 32767.0d;
                            case opencv_core.IPL_DEPTH_32S /* -2147483616 */:
/* 509 */                       return 2.147483647E9d;
                            case 1:
                            case 32:
                            case 64:
/* 512 */                       return 1.0d;
                            case 8:
/* 505 */                       return 255.0d;
                            case 16:
/* 506 */                       return 65535.0d;
                            default:
/* 513 */                       if ($assertionsDisabled) {
/* 503 */                           return 0.0d;
                                }
                                throw new AssertionError();
                        }
                    }

/* 517 */           public CvSize cvSize() {
/* 518 */               return opencv_core.cvSize(width(), height());
                    }

/* 519 */           public ByteBuffer getByteBuffer(int index) {
/* 520 */               return imageData().position(index).capacity(imageSize()).asByteBuffer();
                    }

/* 520 */           public ShortBuffer getShortBuffer(int index) {
/* 521 */               return getByteBuffer(index * 2).asShortBuffer();
                    }

/* 521 */           public IntBuffer getIntBuffer(int index) {
/* 522 */               return getByteBuffer(index * 4).asIntBuffer();
                    }

/* 522 */           public FloatBuffer getFloatBuffer(int index) {
/* 523 */               return getByteBuffer(index * 4).asFloatBuffer();
                    }

/* 523 */           public DoubleBuffer getDoubleBuffer(int index) {
/* 524 */               return getByteBuffer(index * 8).asDoubleBuffer();
                    }

/* 524 */           public ByteBuffer getByteBuffer() {
/* 525 */               return getByteBuffer(0);
                    }

/* 525 */           public ShortBuffer getShortBuffer() {
/* 526 */               return getShortBuffer(0);
                    }

/* 526 */           public IntBuffer getIntBuffer() {
/* 527 */               return getIntBuffer(0);
                    }

/* 527 */           public FloatBuffer getFloatBuffer() {
/* 528 */               return getFloatBuffer(0);
                    }

/* 528 */           public DoubleBuffer getDoubleBuffer() {
/* 529 */               return getDoubleBuffer(0);
                    }

/* 540 */           public static int decodeGamma22(int value) {
/* 541 */               return gamma22[value & 255] & Constants.UNKNOWN;
                    }

/* 543 */           public static int encodeGamma22(int value) {
/* 544 */               return gamma22inv[value & 255] & Constants.UNKNOWN;
                    }

/* 547 */           public static void flipCopyWithGamma(ByteBuffer srcBuf, int srcStep, ByteBuffer dstBuf, int dstStep, boolean signed, double gamma, boolean flip, int channels) {
                        byte out;
                        byte out2;
                        byte out3;
                        byte out4;
/* 548 */               if (!$assertionsDisabled && srcBuf == dstBuf) {
                            throw new AssertionError();
                        }
/* 549 */               int w = Math.min(srcStep, dstStep);
/* 550 */               int srcLine = srcBuf.position();
                        int dstLine = dstBuf.position();
/* 551 */               byte[] buffer = new byte[channels];
/* 552 */               while (srcLine < srcBuf.capacity() && dstLine < dstBuf.capacity()) {
/* 553 */                   if (flip) {
/* 554 */                       srcBuf.position((srcBuf.capacity() - srcLine) - srcStep);
                            } else {
/* 556 */                       srcBuf.position(srcLine);
                            }
/* 558 */                   dstBuf.position(dstLine);
/* 559 */                   w = Math.min(Math.min(w, srcBuf.remaining()), dstBuf.remaining());
/* 560 */                   if (signed) {
/* 561 */                       if (channels > 1) {
/* 562 */                           int x = 0;
                                    while (x < w) {
                                        for (int z = 0; z < channels; z++) {
/* 564 */                                   int in = srcBuf.get();
                                            if (gamma == 1.0d) {
/* 567 */                                       out4 = (byte) in;
                                            } else {
/* 569 */                                       out4 = (byte) Math.round(Math.pow(((double) in) / 127.0d, gamma) * 127.0d);
                                            }
/* 571 */                                   buffer[z] = out4;
                                        }
                                        for (int z2 = channels - 1; z2 >= 0; z2--) {
/* 574 */                                   dstBuf.put(buffer[z2]);
                                        }
/* 562 */                               x += channels;
                                    }
                                } else {
                                    for (int x2 = 0; x2 < w; x2++) {
/* 579 */                               int in2 = srcBuf.get();
                                        if (gamma == 1.0d) {
/* 582 */                                   out3 = (byte) in2;
                                        } else {
/* 584 */                                   out3 = (byte) Math.round(Math.pow(((double) in2) / 127.0d, gamma) * 127.0d);
                                        }
/* 586 */                               dstBuf.put(out3);
                                    }
                                }
                            } else if (channels > 1) {
/* 591 */                       int x3 = 0;
                                while (x3 < w) {
                                    for (int z3 = 0; z3 < channels; z3++) {
/* 594 */                               int in3 = srcBuf.get() & Constants.UNKNOWN;
                                        if (gamma == 1.0d) {
/* 596 */                                   out2 = (byte) in3;
                                        } else if (gamma == 2.2d) {
/* 598 */                                   out2 = gamma22[in3];
                                        } else if (gamma == 0.45454545454545453d) {
/* 600 */                                   out2 = gamma22inv[in3];
                                        } else {
/* 602 */                                   out2 = (byte) Math.round(Math.pow(((double) in3) / 255.0d, gamma) * 255.0d);
                                        }
/* 604 */                               buffer[z3] = out2;
                                    }
                                    for (int z4 = channels - 1; z4 >= 0; z4--) {
/* 607 */                               dstBuf.put(buffer[z4]);
                                    }
/* 591 */                           x3 += channels;
                                }
                            } else {
                                for (int x4 = 0; x4 < w; x4++) {
/* 613 */                           int in4 = srcBuf.get() & Constants.UNKNOWN;
                                    if (gamma == 1.0d) {
/* 615 */                               out = (byte) in4;
                                    } else if (gamma == 2.2d) {
/* 617 */                               out = gamma22[in4];
                                    } else if (gamma == 0.45454545454545453d) {
/* 619 */                               out = gamma22inv[in4];
                                    } else {
/* 621 */                               out = (byte) Math.round(Math.pow(((double) in4) / 255.0d, gamma) * 255.0d);
                                    }
/* 623 */                           dstBuf.put(out);
                                }
                            }
/* 627 */                   srcLine += srcStep;
/* 628 */                   dstLine += dstStep;
                        }
                    }

/* 632 */           public static void flipCopyWithGamma(ShortBuffer srcBuf, int srcStep, ShortBuffer dstBuf, int dstStep, boolean signed, double gamma, boolean flip, int channels) {
                        short out;
                        short out2;
                        short out3;
                        short out4;
/* 633 */               if (!$assertionsDisabled && srcBuf == dstBuf) {
                            throw new AssertionError();
                        }
/* 634 */               int w = Math.min(srcStep, dstStep);
/* 635 */               int srcLine = srcBuf.position();
                        int dstLine = dstBuf.position();
/* 636 */               short[] buffer = new short[channels];
/* 637 */               while (srcLine < srcBuf.capacity() && dstLine < dstBuf.capacity()) {
/* 638 */                   if (flip) {
/* 639 */                       srcBuf.position((srcBuf.capacity() - srcLine) - srcStep);
                            } else {
/* 641 */                       srcBuf.position(srcLine);
                            }
/* 643 */                   dstBuf.position(dstLine);
/* 644 */                   w = Math.min(Math.min(w, srcBuf.remaining()), dstBuf.remaining());
/* 645 */                   if (signed) {
/* 646 */                       if (channels > 1) {
/* 647 */                           int x = 0;
                                    while (x < w) {
                                        for (int z = 0; z < channels; z++) {
/* 649 */                                   int in = srcBuf.get();
                                            if (gamma == 1.0d) {
/* 652 */                                       out4 = (short) in;
                                            } else {
/* 654 */                                       out4 = (short) Math.round(Math.pow(((double) in) / 32767.0d, gamma) * 32767.0d);
                                            }
/* 656 */                                   buffer[z] = out4;
                                        }
                                        for (int z2 = channels - 1; z2 >= 0; z2--) {
/* 659 */                                   dstBuf.put(buffer[z2]);
                                        }
/* 647 */                               x += channels;
                                    }
                                } else {
                                    for (int x2 = 0; x2 < w; x2++) {
/* 664 */                               int in2 = srcBuf.get();
                                        if (gamma == 1.0d) {
/* 667 */                                   out3 = (short) in2;
                                        } else {
/* 669 */                                   out3 = (short) Math.round(Math.pow(((double) in2) / 32767.0d, gamma) * 32767.0d);
                                        }
/* 671 */                               dstBuf.put(out3);
                                    }
                                }
                            } else if (channels > 1) {
/* 676 */                       int x3 = 0;
                                while (x3 < w) {
                                    for (int z3 = 0; z3 < channels; z3++) {
/* 678 */                               int in3 = srcBuf.get();
                                        if (gamma == 1.0d) {
/* 681 */                                   out2 = (short) in3;
                                        } else {
/* 683 */                                   out2 = (short) Math.round(Math.pow(((double) in3) / 65535.0d, gamma) * 65535.0d);
                                        }
/* 685 */                               buffer[z3] = out2;
                                    }
                                    for (int z4 = channels - 1; z4 >= 0; z4--) {
/* 688 */                               dstBuf.put(buffer[z4]);
                                    }
/* 676 */                           x3 += channels;
                                }
                            } else {
                                for (int x4 = 0; x4 < w; x4++) {
/* 693 */                           int in4 = srcBuf.get() & 65535;
                                    if (gamma == 1.0d) {
/* 696 */                               out = (short) in4;
                                    } else {
/* 698 */                               out = (short) Math.round(Math.pow(((double) in4) / 65535.0d, gamma) * 65535.0d);
                                    }
/* 700 */                           dstBuf.put(out);
                                }
                            }
/* 704 */                   srcLine += srcStep;
/* 705 */                   dstLine += dstStep;
                        }
                    }

/* 709 */           public static void flipCopyWithGamma(IntBuffer srcBuf, int srcStep, IntBuffer dstBuf, int dstStep, double gamma, boolean flip, int channels) {
                        int out;
                        int out2;
/* 710 */               if (!$assertionsDisabled && srcBuf == dstBuf) {
                            throw new AssertionError();
                        }
/* 711 */               int w = Math.min(srcStep, dstStep);
/* 712 */               int srcLine = srcBuf.position();
                        int dstLine = dstBuf.position();
/* 713 */               int[] buffer = new int[channels];
/* 714 */               while (srcLine < srcBuf.capacity() && dstLine < dstBuf.capacity()) {
/* 715 */                   if (flip) {
/* 716 */                       srcBuf.position((srcBuf.capacity() - srcLine) - srcStep);
                            } else {
/* 718 */                       srcBuf.position(srcLine);
                            }
/* 720 */                   dstBuf.position(dstLine);
/* 721 */                   w = Math.min(Math.min(w, srcBuf.remaining()), dstBuf.remaining());
/* 722 */                   if (channels > 1) {
/* 723 */                       int x = 0;
                                while (x < w) {
                                    for (int z = 0; z < channels; z++) {
/* 725 */                               int in = srcBuf.get();
                                        if (gamma == 1.0d) {
/* 728 */                                   out2 = in;
                                        } else {
/* 730 */                                   out2 = (int) Math.round(Math.pow(((double) in) / 2.147483647E9d, gamma) * 2.147483647E9d);
                                        }
/* 732 */                               buffer[z] = out2;
                                    }
                                    for (int z2 = channels - 1; z2 >= 0; z2--) {
/* 735 */                               dstBuf.put(buffer[z2]);
                                    }
/* 723 */                           x += channels;
                                }
                            } else {
                                for (int x2 = 0; x2 < w; x2++) {
/* 740 */                           int in2 = srcBuf.get();
                                    if (gamma == 1.0d) {
/* 743 */                               out = in2;
                                    } else {
/* 745 */                               out = (int) Math.round(Math.pow(((double) in2) / 2.147483647E9d, gamma) * 2.147483647E9d);
                                    }
/* 747 */                           dstBuf.put(out);
                                }
                            }
/* 750 */                   srcLine += srcStep;
/* 751 */                   dstLine += dstStep;
                        }
                    }

/* 755 */           public static void flipCopyWithGamma(FloatBuffer srcBuf, int srcStep, FloatBuffer dstBuf, int dstStep, double gamma, boolean flip, int channels) {
                        float out;
                        float out2;
/* 756 */               if (!$assertionsDisabled && srcBuf == dstBuf) {
                            throw new AssertionError();
                        }
/* 757 */               int w = Math.min(srcStep, dstStep);
/* 758 */               int srcLine = srcBuf.position();
                        int dstLine = dstBuf.position();
/* 759 */               float[] buffer = new float[channels];
/* 760 */               while (srcLine < srcBuf.capacity() && dstLine < dstBuf.capacity()) {
/* 761 */                   if (flip) {
/* 762 */                       srcBuf.position((srcBuf.capacity() - srcLine) - srcStep);
                            } else {
/* 764 */                       srcBuf.position(srcLine);
                            }
/* 766 */                   dstBuf.position(dstLine);
/* 767 */                   w = Math.min(Math.min(w, srcBuf.remaining()), dstBuf.remaining());
/* 768 */                   if (channels > 1) {
/* 769 */                       int x = 0;
                                while (x < w) {
                                    for (int z = 0; z < channels; z++) {
/* 771 */                               float in = srcBuf.get();
                                        if (gamma == 1.0d) {
/* 774 */                                   out2 = in;
                                        } else {
/* 776 */                                   out2 = (float) Math.pow(in, gamma);
                                        }
/* 778 */                               buffer[z] = out2;
                                    }
                                    for (int z2 = channels - 1; z2 >= 0; z2--) {
/* 781 */                               dstBuf.put(buffer[z2]);
                                    }
/* 769 */                           x += channels;
                                }
                            } else {
                                for (int x2 = 0; x2 < w; x2++) {
/* 786 */                           float in2 = srcBuf.get();
                                    if (gamma == 1.0d) {
/* 789 */                               out = in2;
                                    } else {
/* 791 */                               out = (float) Math.pow(in2, gamma);
                                    }
/* 793 */                           dstBuf.put(out);
                                }
                            }
/* 796 */                   srcLine += srcStep;
/* 797 */                   dstLine += dstStep;
                        }
                    }

/* 801 */           public static void flipCopyWithGamma(DoubleBuffer srcBuf, int srcStep, DoubleBuffer dstBuf, int dstStep, double gamma, boolean flip, int channels) {
                        double out;
                        double out2;
/* 802 */               if (!$assertionsDisabled && srcBuf == dstBuf) {
                            throw new AssertionError();
                        }
/* 803 */               int w = Math.min(srcStep, dstStep);
/* 804 */               int srcLine = srcBuf.position();
                        int dstLine = dstBuf.position();
/* 805 */               double[] buffer = new double[channels];
/* 806 */               while (srcLine < srcBuf.capacity() && dstLine < dstBuf.capacity()) {
/* 807 */                   if (flip) {
/* 808 */                       srcBuf.position((srcBuf.capacity() - srcLine) - srcStep);
                            } else {
/* 810 */                       srcBuf.position(srcLine);
                            }
/* 812 */                   dstBuf.position(dstLine);
/* 813 */                   w = Math.min(Math.min(w, srcBuf.remaining()), dstBuf.remaining());
/* 814 */                   if (channels > 1) {
/* 815 */                       int x = 0;
                                while (x < w) {
                                    for (int z = 0; z < channels; z++) {
/* 817 */                               double in = srcBuf.get();
                                        if (gamma == 1.0d) {
/* 820 */                                   out2 = in;
                                        } else {
/* 822 */                                   out2 = Math.pow(in, gamma);
                                        }
/* 824 */                               buffer[z] = out2;
                                    }
                                    for (int z2 = channels - 1; z2 >= 0; z2--) {
/* 827 */                               dstBuf.put(buffer[z2]);
                                    }
/* 815 */                           x += channels;
                                }
                            } else {
                                for (int x2 = 0; x2 < w; x2++) {
/* 832 */                           double in2 = srcBuf.get();
                                    if (gamma == 1.0d) {
/* 835 */                               out = in2;
                                    } else {
/* 837 */                               out = Math.pow(in2, gamma);
                                    }
/* 839 */                           dstBuf.put(out);
                                }
                            }
/* 842 */                   srcLine += srcStep;
/* 843 */                   dstLine += dstStep;
                        }
                    }

/* 846 */           public void applyGamma(double gamma) {
                        if (gamma != 1.0d) {
                            switch (depth()) {
                                case opencv_core.IPL_DEPTH_8S /* -2147483640 */:
/* 855 */                           flipCopyWithGamma(getByteBuffer(), widthStep(), getByteBuffer(), widthStep(), true, gamma, false, 0);
/* 875 */                           return;
                                case opencv_core.IPL_DEPTH_16S /* -2147483632 */:
/* 861 */                           flipCopyWithGamma(getShortBuffer(), widthStep() / 2, getShortBuffer(), widthStep() / 2, true, gamma, false, 0);
/* 875 */                           return;
                                case opencv_core.IPL_DEPTH_32S /* -2147483616 */:
/* 864 */                           flipCopyWithGamma(getFloatBuffer(), widthStep() / 4, getFloatBuffer(), widthStep() / 4, gamma, false, 0);
/* 875 */                           return;
                                case 8:
/* 852 */                           flipCopyWithGamma(getByteBuffer(), widthStep(), getByteBuffer(), widthStep(), false, gamma, false, 0);
/* 875 */                           return;
                                case 16:
/* 858 */                           flipCopyWithGamma(getShortBuffer(), widthStep() / 2, getShortBuffer(), widthStep() / 2, false, gamma, false, 0);
/* 875 */                           return;
                                case 32:
/* 867 */                           flipCopyWithGamma(getFloatBuffer(), widthStep() / 4, getFloatBuffer(), widthStep() / 4, gamma, false, 0);
/* 875 */                           return;
                                case 64:
/* 870 */                           flipCopyWithGamma(getDoubleBuffer(), widthStep() / 8, getDoubleBuffer(), widthStep() / 8, gamma, false, 0);
/* 875 */                           return;
                                default:
/* 873 */                           if (!$assertionsDisabled) {
                                        throw new AssertionError();
                                    }
/* 875 */                           return;
                            }
                        }
                    }

/* 877 */           public CvMat asCvMat() {
/* 878 */               CvMat mat = new CvMat();
/* 879 */               opencv_core.cvGetMat(this, mat, null, 0);
/* 880 */               return mat;
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 883 */           public String toString() {
                        return isNull() ? super.toString() : "IplImage[width=" + width() + ",height=" + height() + ",depth=" + depth() + ",nChannels=" + nChannels() + "]";
                    }
                }

                @Opaque
/* 892 */       public static class IplTileInfo extends Pointer {
                    static {
/* 894 */               Loader.load();
                    }

/* 894 */           public IplTileInfo() {
                    }

/* 895 */           public IplTileInfo(Pointer p) {
/* 896 */               super(p);
                    }
                }

/* 897 */       public static class IplROI extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native int coi();

                    public native IplROI coi(int i);

                    public native int height();

                    public native IplROI height(int i);

                    public native int width();

                    public native IplROI width(int i);

                    public native int xOffset();

                    public native IplROI xOffset(int i);

                    public native int yOffset();

                    public native IplROI yOffset(int i);


                    static {
/* 900 */               Loader.load();
                    }

/* 900 */           public IplROI() {
                        allocate();
                    }

/* 901 */           public IplROI(int size) {
                        allocateArray(size);
                    }

/* 902 */           public IplROI(Pointer p) {
/* 903 */               super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 907 */           public IplROI position(int position) {
/* 908 */               return (IplROI) super.position(position);
                    }
                }

/* 929 */       public static boolean CV_IS_IMAGE_HDR(CvArr img) {
                    return img != null && new IplImage(img).nSize() == Loader.sizeof(IplImage.class);
                }

/* 932 */       public static boolean CV_IS_IMAGE(CvArr img) {
                    return CV_IS_IMAGE_HDR(img) && new IplImage(img).imageData() != null;
                }

/* 951 */       public static int CV_MAT_DEPTH(int flags) {
/* 952 */           return flags & 7;
                }

/* 953 */       public static int CV_MAKETYPE(int depth, int cn) {
/* 954 */           return CV_MAT_DEPTH(depth) + ((cn - 1) << 3);
                }

/* 954 */       public static int CV_MAKE_TYPE(int depth, int cn) {
/* 955 */           return CV_MAKETYPE(depth, cn);
                }

/* 956 */       public static int CV_8UC(int n) {
/* 957 */           return CV_MAKETYPE(0, n);
                }

/* 957 */       public static int CV_8SC(int n) {
/* 958 */           return CV_MAKETYPE(1, n);
                }

/* 958 */       public static int CV_16UC(int n) {
/* 959 */           return CV_MAKETYPE(0, n);
                }

/* 959 */       public static int CV_16SC(int n) {
/* 960 */           return CV_MAKETYPE(3, n);
                }

/* 960 */       public static int CV_32SC(int n) {
/* 961 */           return CV_MAKETYPE(4, n);
                }

/* 961 */       public static int CV_32FC(int n) {
/* 962 */           return CV_MAKETYPE(5, n);
                }

/* 962 */       public static int CV_64FC(int n) {
/* 963 */           return CV_MAKETYPE(6, n);
                }

/* 1010 */      public static int CV_MAT_CN(int flags) {
/* 1011 */          return ((flags & CV_MAT_CN_MASK) >> 3) + 1;
                }

/* 1011 */      public static int CV_MAT_TYPE(int flags) {
/* 1012 */          return flags & 4095;
                }

/* 1012 */      public static boolean CV_IS_MAT_CONT(int flags) {
                    return (flags & 16384) != 0;
                }

/* 1013 */      public static boolean CV_IS_CONT_MAT(int flags) {
/* 1014 */          return CV_IS_MAT_CONT(flags);
                }

/* 1014 */      public static boolean CV_IS_TEMP_MAT(int flags) {
                    return (32768 & flags) != 0;
                }

/* 1020 */      public static class CvMat extends CvArr {
                    static final /* synthetic */ boolean $assertionsDisabled;
                    private ByteBuffer byteBuffer;
                    private DoubleBuffer doubleBuffer;
                    private FloatBuffer floatBuffer;
                    private int fullSize;
                    private IntBuffer intBuffer;
                    private ShortBuffer shortBuffer;

                    private native void allocate();

                    private native void allocateArray(int i);

                    public native int cols();

                    public native CvMat cols(int i);

                    @Name({"data.db"})
                    public native DoublePointer data_db();

                    public native CvMat data_db(DoublePointer doublePointer);

                    @Name({"data.fl"})
                    public native FloatPointer data_fl();

                    public native CvMat data_fl(FloatPointer floatPointer);

                    @Name({"data.i"})
                    public native IntPointer data_i();

                    public native CvMat data_i(IntPointer intPointer);

                    @Name({"data.ptr"})
                    @Cast({"uchar*"})
                    public native BytePointer data_ptr();

                    public native CvMat data_ptr(BytePointer bytePointer);

                    @Name({"data.s"})
                    public native ShortPointer data_s();

                    public native CvMat data_s(ShortPointer shortPointer);

                    public native int hdr_refcount();

                    public native CvMat hdr_refcount(int i);

                    @Name({"type"})
                    public native int raw_type();

                    public native CvMat raw_type(int i);

                    public native IntPointer refcount();

                    public native CvMat refcount(IntPointer intPointer);

                    public native int rows();

                    public native CvMat rows(int i);

                    public native int step();

                    public native CvMat step(int i);

                    static {
/* 1022 */              $assertionsDisabled = !opencv_core.class.desiredAssertionStatus();
                    }

                    /* JADX INFO: renamed from: clone, reason: collision with other method in class */


/* 1022 */          public CvMat() {
/* 1179 */              this.fullSize = 0;
/* 1181 */              this.byteBuffer = null;
/* 1182 */              this.shortBuffer = null;
/* 1183 */              this.intBuffer = null;
/* 1184 */              this.floatBuffer = null;
/* 1185 */              this.doubleBuffer = null;
/* 1023 */              allocate();
                        zero();
                    }

/* 1023 */          public CvMat(int size) {
/* 1179 */              this.fullSize = 0;
/* 1181 */              this.byteBuffer = null;
/* 1182 */              this.shortBuffer = null;
/* 1183 */              this.intBuffer = null;
/* 1184 */              this.floatBuffer = null;
/* 1185 */              this.doubleBuffer = null;
/* 1024 */              allocateArray(size);
                        zero();
                    }

/* 1024 */          public CvMat(Pointer p) {
/* 1025 */              super(p);
/* 1179 */              this.fullSize = 0;
/* 1181 */              this.byteBuffer = null;
/* 1182 */              this.shortBuffer = null;
/* 1183 */              this.intBuffer = null;
/* 1184 */              this.floatBuffer = null;
/* 1185 */              this.doubleBuffer = null;
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 1029 */          public CvMat position(int position) {
/* 1030 */              return (CvMat) super.position(position);
                    }

/* 1033 */          public static CvMat create(int rows, int cols, int type) {
/* 1034 */              CvMat m = opencv_core.cvCreateMat(rows, cols, type);
/* 1035 */              if (m != null) {
/* 1036 */                  m.fullSize = m.size();
/* 1037 */                  m.deallocator(new ReleaseDeallocator(m));
                        }
/* 1039 */              return m;
                    }

/* 1041 */          public static CvMat create(int rows, int cols, int depth, int channels) {
/* 1042 */              return create(rows, cols, opencv_core.CV_MAKETYPE(depth, channels));
                    }

/* 1044 */          public static CvMat create(int rows, int cols) {
/* 1045 */              return create(rows, cols, 6, 1);
                    }

/* 1048 */          public static CvMat createHeader(int rows, int cols, int type) {
/* 1049 */              CvMat m = opencv_core.cvCreateMatHeader(rows, cols, type);
/* 1050 */              if (m != null) {
/* 1051 */                  m.fullSize = m.size();
/* 1052 */                  m.deallocator(new ReleaseDeallocator(m));
                        }
/* 1054 */              return m;
                    }

/* 1056 */          public static CvMat createHeader(int rows, int cols, int depth, int channels) {
/* 1057 */              return createHeader(rows, cols, opencv_core.CV_MAKETYPE(depth, channels));
                    }

/* 1059 */          public static CvMat createHeader(int rows, int cols) {
/* 1060 */              return createHeader(rows, cols, 6, 1);
                    }

                    /* JADX INFO: renamed from: com.googlecode.javacv.cpp.opencv_core$CvMat$1 */
/* 1062 */          static class C12321 extends ThreadLocal<CvMat> {
                        final /* synthetic */ int val$cols;
                        final /* synthetic */ int val$rows;
                        final /* synthetic */ int val$type;

/* 1063 */              C12321(int i, int i2, int i3) {
/* 1064 */                  this.val$rows = i;
                            this.val$cols = i2;
                            this.val$type = i3;
                        }


                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // java.lang.ThreadLocal
/* 1064 */              protected CvMat initialValue() {
/* 1065 */                  return CvMat.create(this.val$rows, this.val$cols, this.val$type);
                        }
                    }

/* 1063 */          public static ThreadLocal<CvMat> createThreadLocal(int rows, int cols, int type) {
/* 1064 */              return new C12321(rows, cols, type);
                    }

/* 1068 */          public static ThreadLocal<CvMat> createThreadLocal(int rows, int cols, int depth, int channels) {
/* 1069 */              return createThreadLocal(rows, cols, opencv_core.CV_MAKETYPE(depth, channels));
                    }

/* 1071 */          public static ThreadLocal<CvMat> createThreadLocal(int rows, int cols) {
/* 1072 */              return createThreadLocal(rows, cols, 6, 1);
                    }

                    /* JADX INFO: renamed from: com.googlecode.javacv.cpp.opencv_core$CvMat$2 */
/* 1074 */          static class C12332 extends ThreadLocal<CvMat> {
                        final /* synthetic */ int val$cols;
                        final /* synthetic */ int val$rows;
                        final /* synthetic */ int val$type;

/* 1075 */              C12332(int i, int i2, int i3) {
/* 1076 */                  this.val$rows = i;
                            this.val$cols = i2;
                            this.val$type = i3;
                        }


                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // java.lang.ThreadLocal
/* 1076 */              protected CvMat initialValue() {
/* 1077 */                  return CvMat.createHeader(this.val$rows, this.val$cols, this.val$type);
                        }
                    }

/* 1075 */          public static ThreadLocal<CvMat> createHeaderThreadLocal(int rows, int cols, int type) {
/* 1076 */              return new C12332(rows, cols, type);
                    }

/* 1080 */          public static ThreadLocal<CvMat> createHeaderThreadLocal(int rows, int cols, int depth, int channels) {
/* 1081 */              return createHeaderThreadLocal(rows, cols, opencv_core.CV_MAKETYPE(depth, channels));
                    }

/* 1083 */          public static ThreadLocal<CvMat> createHeaderThreadLocal(int rows, int cols) {
/* 1084 */              return createHeaderThreadLocal(rows, cols, 6, 1);
                    }

/* 1087 */          public CvMat clone() {
/* 1088 */              CvMat m = opencv_core.cvCloneMat(this);
/* 1089 */              if (m != null) {
/* 1090 */                  m.deallocator(new ReleaseDeallocator(m));
                        }
/* 1092 */              return m;
                    }

/* 1095 */          public void release() {
/* 1096 */              deallocate();
                    }

/* 1096 */          protected static class ReleaseDeallocator extends CvMat implements Pointer.Deallocator {
/* 1098 */              ReleaseDeallocator(CvMat m) {
/* 1099 */                  super(m);
                        }

                        @Override // com.googlecode.javacpp.Pointer, com.googlecode.javacpp.Pointer.Deallocator
/* 1099 */              public void deallocate() {
/* 1100 */                  opencv_core.cvReleaseMat(this);
                        }
                    }

/* 1122 */          public int type() {
/* 1123 */              return opencv_core.CV_MAT_TYPE(raw_type());
                    }

/* 1125 */          public void type(int depth, int cn) {
/* 1126 */              raw_type(opencv_core.CV_MAKETYPE(depth, cn) | opencv_core.CV_MAT_MAGIC_VAL);
                    }

/* 1128 */          public int depth() {
/* 1129 */              return opencv_core.CV_MAT_DEPTH(type());
                    }

/* 1131 */          public int channels() {
/* 1132 */              return opencv_core.CV_MAT_CN(type());
                    }

/* 1134 */          public int nChannels() {
/* 1135 */              return opencv_core.CV_MAT_CN(type());
                    }

/* 1137 */          public boolean isContinuous() {
/* 1138 */              return opencv_core.CV_IS_MAT_CONT(type());
                    }

/* 1140 */          public int elemSize() {
                        switch (depth()) {
                            case 0:
                            case 1:
/* 1143 */                      return 1;
                            case 2:
                            case 3:
/* 1145 */                      return 2;
                            case 4:
                            case 5:
/* 1147 */                      return 4;
                            case 6:
/* 1148 */                      return 8;
                            default:
/* 1149 */                      if ($assertionsDisabled) {
/* 1151 */                          return 0;
                                }
                                throw new AssertionError();
                        }
                    }

/* 1153 */          public int length() {
/* 1154 */              return rows() * cols();
                    }

/* 1156 */          public int total() {
/* 1157 */              return rows() * cols();
                    }

/* 1159 */          public boolean empty() {
                        return length() == 0;
                    }

/* 1163 */          public int size() {
/* 1164 */              int rows = rows();
                        return (rows > 1 ? step() * (rows - 1) : 0) + (channels() * cols() * elemSize());
                    }

/* 1167 */          public CvSize cvSize() {
/* 1168 */              return opencv_core.cvSize(cols(), rows());
                    }

/* 1170 */          public void reset() {
/* 1171 */              this.fullSize = 0;
/* 1172 */              this.byteBuffer = null;
/* 1173 */              this.shortBuffer = null;
/* 1174 */              this.intBuffer = null;
/* 1175 */              this.floatBuffer = null;
/* 1176 */              this.doubleBuffer = null;
                    }

/* 1179 */          private int fullSize() {
/* 1180 */              if (this.fullSize > 0) {
                            return this.fullSize;
                        }
                        int size = size();
                        this.fullSize = size;
                        return size;
                    }

/* 1186 */          public ByteBuffer getByteBuffer() {
/* 1187 */              if (this.byteBuffer == null) {
/* 1188 */                  this.byteBuffer = data_ptr().capacity(fullSize()).asBuffer();
                        }
/* 1190 */              this.byteBuffer.position(0);
/* 1191 */              return this.byteBuffer;
                    }

/* 1193 */          public ShortBuffer getShortBuffer() {
/* 1194 */              if (this.shortBuffer == null) {
/* 1195 */                  this.shortBuffer = data_s().capacity(fullSize() / 2).asBuffer();
                        }
/* 1197 */              this.shortBuffer.position(0);
/* 1198 */              return this.shortBuffer;
                    }

/* 1200 */          public IntBuffer getIntBuffer() {
/* 1201 */              if (this.intBuffer == null) {
/* 1202 */                  this.intBuffer = data_i().capacity(fullSize() / 4).asBuffer();
                        }
/* 1204 */              this.intBuffer.position(0);
/* 1205 */              return this.intBuffer;
                    }

/* 1207 */          public FloatBuffer getFloatBuffer() {
/* 1208 */              if (this.floatBuffer == null) {
/* 1209 */                  this.floatBuffer = data_fl().capacity(fullSize() / 4).asBuffer();
                        }
/* 1211 */              this.floatBuffer.position(0);
/* 1212 */              return this.floatBuffer;
                    }

/* 1214 */          public DoubleBuffer getDoubleBuffer() {
/* 1215 */              if (this.doubleBuffer == null) {
/* 1216 */                  this.doubleBuffer = data_db().capacity(fullSize() / 8).asBuffer();
                        }
/* 1218 */              this.doubleBuffer.position(0);
/* 1219 */              return this.doubleBuffer;
                    }

/* 1222 */          public double get(int i) {
                        switch (depth()) {
                            case 0:
/* 1224 */                      return getByteBuffer().get(i) & Constants.UNKNOWN;
                            case 1:
/* 1225 */                      return getByteBuffer().get(i);
                            case 2:
/* 1226 */                      return getShortBuffer().get(i) & 65535;
                            case 3:
/* 1227 */                      return getShortBuffer().get(i);
                            case 4:
/* 1228 */                      return getIntBuffer().get(i);
                            case 5:
/* 1229 */                      return getFloatBuffer().get(i);
                            case 6:
/* 1230 */                      return getDoubleBuffer().get(i);
                            default:
/* 1231 */                      if ($assertionsDisabled) {
/* 1233 */                          return Double.NaN;
                                }
                                throw new AssertionError();
                        }
                    }

/* 1235 */          public double get(int i, int j) {
/* 1236 */              return get(((step() * i) / elemSize()) + (channels() * j));
                    }

/* 1239 */          public double get(int i, int j, int k) {
/* 1240 */              return get(((step() * i) / elemSize()) + (channels() * j) + k);
                    }

/* 1242 */          public synchronized CvMat get(int index, double[] vv, int offset, int length) {
                        try {
                            int d = depth();
                            switch (d) {
                                case 0:
                                case 1:
/* 1247 */                          ByteBuffer bb = getByteBuffer();
/* 1248 */                          bb.position(index);
                                    for (int i = 0; i < length; i++) {
/* 1250 */                              if (d == 0) {
/* 1251 */                                  vv[i + offset] = bb.get(i) & Constants.UNKNOWN;
                                        } else {
/* 1253 */                                  vv[i + offset] = bb.get(i);
                                        }
                                    }
                                    break;
                                case 2:
                                case 3:
/* 1259 */                          ShortBuffer sb = getShortBuffer();
/* 1260 */                          sb.position(index);
                                    for (int i2 = 0; i2 < length; i2++) {
/* 1262 */                              if (d == 2) {
/* 1263 */                                  vv[i2 + offset] = sb.get() & 65535;
                                        } else {
/* 1265 */                                  vv[i2 + offset] = sb.get();
                                        }
                                    }
                                    break;
                                case 4:
/* 1270 */                          IntBuffer ib = getIntBuffer();
/* 1271 */                          ib.position(index);
                                    for (int i3 = 0; i3 < length; i3++) {
/* 1273 */                              vv[i3 + offset] = ib.get();
                                    }
                                    break;
                                case 5:
/* 1277 */                          FloatBuffer fb = getFloatBuffer();
/* 1278 */                          fb.position(index);
                                    for (int i4 = 0; i4 < length; i4++) {
/* 1280 */                              vv[i4 + offset] = fb.get();
                                    }
                                    break;
                                case 6:
/* 1284 */                          getDoubleBuffer().position(index);
/* 1285 */                          getDoubleBuffer().get(vv, offset, length);
                                    break;
                                default:
/* 1287 */                          if (!$assertionsDisabled) {
                                        throw new AssertionError();
                                    }
                                    break;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                        return this;
                    }

/* 1291 */          public CvMat get(int index, double[] vv) {
/* 1292 */              return get(index, vv, 0, vv.length);
                    }

/* 1294 */          public CvMat get(double[] vv) {
/* 1295 */              return get(0, vv);
                    }

/* 1297 */          public double[] get() {
/* 1298 */              double[] vv = new double[fullSize() / elemSize()];
/* 1299 */              get(vv);
/* 1300 */              return vv;
                    }

/* 1303 */          public CvMat put(int i, double v) {
                        switch (depth()) {
                            case 0:
                            case 1:
/* 1306 */                      getByteBuffer().put(i, (byte) v);
/* 1314 */                      return this;
                            case 2:
                            case 3:
/* 1308 */                      getShortBuffer().put(i, (short) v);
/* 1314 */                      return this;
                            case 4:
/* 1309 */                      getIntBuffer().put(i, (int) v);
/* 1314 */                      return this;
                            case 5:
/* 1310 */                      getFloatBuffer().put(i, (float) v);
/* 1314 */                      return this;
                            case 6:
/* 1311 */                      getDoubleBuffer().put(i, v);
/* 1314 */                      return this;
                            default:
/* 1312 */                      if (!$assertionsDisabled) {
                                    throw new AssertionError();
                                }
/* 1314 */                      return this;
                        }
                    }

/* 1316 */          public CvMat put(int i, int j, double v) {
/* 1317 */              return put(((step() * i) / elemSize()) + (channels() * j), v);
                    }

/* 1319 */          public CvMat put(int i, int j, int k, double v) {
/* 1320 */              return put(((step() * i) / elemSize()) + (channels() * j) + k, v);
                    }

/* 1322 */          public synchronized CvMat put(int index, double[] vv, int offset, int length) {
                        try {
                            switch (depth()) {
                                case 0:
                                case 1:
/* 1326 */                          ByteBuffer bb = getByteBuffer();
/* 1327 */                          bb.position(index);
                                    for (int i = 0; i < length; i++) {
/* 1329 */                              bb.put((byte) vv[i + offset]);
                                    }
                                    break;
                                case 2:
                                case 3:
/* 1334 */                          ShortBuffer sb = getShortBuffer();
/* 1335 */                          sb.position(index);
                                    for (int i2 = 0; i2 < length; i2++) {
/* 1337 */                              sb.put((short) vv[i2 + offset]);
                                    }
                                    break;
                                case 4:
/* 1341 */                          IntBuffer ib = getIntBuffer();
/* 1342 */                          ib.position(index);
                                    for (int i3 = 0; i3 < length; i3++) {
/* 1344 */                              ib.put((int) vv[i3 + offset]);
                                    }
                                    break;
                                case 5:
/* 1348 */                          FloatBuffer fb = getFloatBuffer();
/* 1349 */                          fb.position(index);
                                    for (int i4 = 0; i4 < length; i4++) {
/* 1351 */                              fb.put((float) vv[i4 + offset]);
                                    }
                                    break;
                                case 6:
/* 1355 */                          DoubleBuffer db = getDoubleBuffer();
/* 1356 */                          db.position(index);
/* 1357 */                          db.put(vv, offset, length);
                                    break;
                                default:
/* 1359 */                          if (!$assertionsDisabled) {
                                        throw new AssertionError();
                                    }
                                    break;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                        return this;
                    }

/* 1363 */          public CvMat put(int index, double... vv) {
/* 1364 */              return put(index, vv, 0, vv.length);
                    }

/* 1366 */          public CvMat put(double... vv) {
/* 1367 */              return put(0, vv);
                    }

/* 1370 */          public CvMat put(CvMat mat) {
/* 1371 */              return put(0, 0, 0, mat, 0, 0, 0);
                    }

/* 1374 */          public synchronized CvMat put(int dsti, int dstj, int dstk, CvMat mat, int srci, int srcj, int srck) {
                        try {
                            if (rows() == mat.rows() && cols() == mat.cols() && step() == mat.step() && type() == mat.type() && dsti == 0 && dstj == 0 && dstk == 0 && srci == 0 && srcj == 0 && srck == 0) {
/* 1377 */                      getByteBuffer().clear();
/* 1378 */                      mat.getByteBuffer().clear();
/* 1379 */                      getByteBuffer().put(mat.getByteBuffer());
                            } else {
/* 1381 */                      int w = Math.min(rows() - dsti, mat.rows() - srci);
/* 1382 */                      int h = Math.min(cols() - dstj, mat.cols() - srcj);
/* 1383 */                      int d = Math.min(channels() - dstk, mat.channels() - srck);
                                for (int i = 0; i < w; i++) {
                                    for (int j = 0; j < h; j++) {
                                        for (int k = 0; k < d; k++) {
/* 1387 */                                  put(i + dsti, j + dstj, k + dstk, mat.get(i + srci, j + srcj, k + srck));
                                        }
                                    }
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                        return this;
                    }

/* 1395 */          public IplImage asIplImage() {
/* 1396 */              IplImage image = new IplImage();
/* 1397 */              opencv_core.cvGetImage(this, image);
/* 1398 */              return image;
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 1401 */          public String toString() {
/* 1402 */              return toString(0);
                    }

/* 1404 */          public String toString(int indent) {
/* 1405 */              StringBuilder s = new StringBuilder("[ ");
/* 1406 */              int channels = channels();
                        for (int i = 0; i < rows(); i++) {
                            for (int j = 0; j < cols(); j++) {
/* 1409 */                      CvScalar v = opencv_core.cvGet2D(this, i, j);
/* 1410 */                      if (channels > 1) {
/* 1411 */                          s.append("(");
                                }
                                for (int k = 0; k < channels; k++) {
/* 1414 */                          s.append((float) v.val(k));
/* 1415 */                          if (k < channels - 1) {
/* 1416 */                              s.append(", ");
                                    }
                                }
/* 1419 */                      if (channels > 1) {
/* 1420 */                          s.append(")");
                                }
/* 1422 */                      if (j < cols() - 1) {
/* 1423 */                          s.append(", ");
                                }
                            }
/* 1426 */                  if (i < rows() - 1) {
/* 1427 */                      s.append("\n  ");
                                for (int j2 = 0; j2 < indent; j2++) {
/* 1429 */                          s.append(' ');
                                }
                            }
                        }
/* 1433 */              s.append(" ]");
/* 1434 */              return s.toString();
                    }
                }

/* 1438 */      public static boolean CV_IS_MAT_HDR(CvArr mat) {
/* 1439 */          CvMat m = new CvMat(mat);
                    return mat != null && (m.raw_type() & (-65536)) == 1111621632 && m.cols() > 0 && m.rows() > 0;
                }

/* 1443 */      public static boolean CV_IS_MAT_HDR_Z(CvArr mat) {
/* 1444 */          CvMat m = new CvMat(mat);
                    return mat != null && (m.raw_type() & (-65536)) == 1111621632 && m.cols() >= 0 && m.rows() >= 0;
                }

/* 1448 */      public static boolean CV_IS_MAT(CvArr mat) {
                    return CV_IS_MAT_HDR(mat) && new CvMat(mat).data_ptr() != null;
                }

/* 1451 */      public static boolean CV_IS_MASK_ARR(CvMat mat) {
                    return (mat.raw_type() & ((CV_8SC1 ^ (-1)) & 4095)) == 0;
                }

/* 1454 */      public static boolean CV_ARE_TYPES_EQ(CvMat mat1, CvMat mat2) {
                    return ((mat1.raw_type() ^ mat2.raw_type()) & 4095) == 0;
                }

/* 1457 */      public static boolean CV_ARE_CNS_EQ(CvMat mat1, CvMat mat2) {
                    return ((mat1.raw_type() ^ mat2.raw_type()) & CV_MAT_CN_MASK) == 0;
                }

/* 1460 */      public static boolean CV_ARE_DEPTHS_EQ(CvMat mat1, CvMat mat2) {
                    return ((mat1.raw_type() ^ mat2.raw_type()) & 7) == 0;
                }

/* 1463 */      public static boolean CV_ARE_SIZES_EQ(CvMat mat1, CvMat mat2) {
                    return mat1.rows() == mat2.rows() && mat1.cols() == mat2.cols();
                }

/* 1466 */      public static boolean CV_IS_MAT_CONST(CvMat mat) {
                    return (mat.rows() | mat.cols()) == 1;
                }

/* 1469 */      public static int CV_ELEM_SIZE1(int type) {
/* 1470 */          return (((Loader.sizeof(SizeTPointer.class) << 28) | 138682897) >> (CV_MAT_DEPTH(type) * 4)) & 15;
                }

/* 1472 */      public static int CV_ELEM_SIZE(int type) {
/* 1473 */          return CV_MAT_CN(type) << ((((((Loader.sizeof(SizeTPointer.class) / 4) + 1) * 16384) | 14928) >> (CV_MAT_DEPTH(type) * 2)) & 3);
                }

/* 1475 */      public static int IPL2CV_DEPTH(int depth) {
/* 1476 */          return (1125516576 >> (((Integer.MIN_VALUE & depth) != 0 ? 20 : 0) + ((depth & 240) >> 2))) & 15;
                }

/* 1481 */      public static CvMat cvMat(int rows, int cols, int type, Pointer data) {
/* 1482 */          CvMat m = new CvMat();
/* 1484 */          if (!$assertionsDisabled && (CV_MAT_DEPTH(type) < 0 || CV_MAT_DEPTH(type) > 6)) {
                        throw new AssertionError();
                    }
/* 1485 */          int type2 = CV_MAT_TYPE(type);
/* 1486 */          m.raw_type(1111638016 | type2);
/* 1487 */          m.cols(cols);
/* 1488 */          m.rows(rows);
/* 1489 */          m.step(CV_ELEM_SIZE(type2) * cols);
/* 1490 */          m.data_ptr(new BytePointer(data));
/* 1491 */          m.refcount(null);
/* 1492 */          m.hdr_refcount(0);
/* 1494 */          return m;
                }

/* 1497 */      public static int cvIplDepth(int type) {
/* 1498 */          int depth = CV_MAT_DEPTH(type);
                    return ((depth == 1 || depth == 3 || depth == 4) ? Integer.MIN_VALUE : 0) | (CV_ELEM_SIZE1(depth) * 8);
                }

/* 1509 */      public static class CvMatND extends CvArr {
                    private native void allocate();

                    private native void allocateArray(int i);

                    @Name({"data.db"})
                    public native DoublePointer data_db();

                    public native CvMatND data_db(DoublePointer doublePointer);

                    @Name({"data.fl"})
                    public native FloatPointer data_fl();

                    public native CvMatND data_fl(FloatPointer floatPointer);

                    @Name({"data.i"})
                    public native IntPointer data_i();

                    public native CvMatND data_i(IntPointer intPointer);

                    @Name({"data.ptr"})
                    @Cast({"uchar*"})
                    public native BytePointer data_ptr();

                    public native CvMatND data_ptr(BytePointer bytePointer);

                    @Name({"data.s"})
                    public native ShortPointer data_s();

                    public native CvMatND data_s(ShortPointer shortPointer);

                    @Name({"dim", ".size"})
                    public native int dim_size(int i);

                    public native CvMatND dim_size(int i, int i2);

                    @Name({"dim", ".step"})
                    public native int dim_step(int i);

                    public native CvMatND dim_step(int i, int i2);

                    public native int dims();

                    public native CvMatND dims(int i);

                    public native int hdr_refcount();

                    public native CvMatND hdr_refcount(int i);

                    public native IntPointer refcount();

                    public native CvMatND refcount(IntPointer intPointer);

                    public native int type();

                    public native CvMatND type(int i);

                    /* JADX INFO: renamed from: clone, reason: collision with other method in class */


/* 1511 */          public CvMatND() {
                        allocate();
                        zero();
                    }

/* 1512 */          public CvMatND(int size) {
                        allocateArray(size);
                        zero();
                    }

/* 1513 */          public CvMatND(Pointer p) {
/* 1514 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 1518 */          public CvMatND position(int position) {
/* 1519 */              return (CvMatND) super.position(position);
                    }

/* 1522 */          public static CvMatND create(int dims, int[] sizes, int type) {
/* 1523 */              CvMatND m = opencv_core.cvCreateMatND(dims, sizes, type);
/* 1524 */              if (m != null) {
/* 1525 */                  m.deallocator(new ReleaseDeallocator(m));
                        }
/* 1527 */              return m;
                    }

/* 1530 */          public CvMatND clone() {
/* 1531 */              CvMatND m = opencv_core.cvCloneMatND(this);
/* 1532 */              if (m != null) {
/* 1533 */                  m.deallocator(new ReleaseDeallocator(m));
                        }
/* 1535 */              return m;
                    }

/* 1538 */          public void release() {
/* 1539 */              deallocate();
                    }

/* 1539 */          protected static class ReleaseDeallocator extends CvMatND implements Pointer.Deallocator {
/* 1541 */              ReleaseDeallocator(CvMatND p) {
/* 1542 */                  super(p);
                        }

                        @Override // com.googlecode.javacpp.Pointer, com.googlecode.javacpp.Pointer.Deallocator
/* 1542 */              public void deallocate() {
/* 1543 */                  opencv_core.cvReleaseMatND(this);
                        }
                    }
                }

/* 1568 */      public static boolean CV_IS_MATND_HDR(CvArr mat) {
                    return mat != null && (new CvMatND(mat).type() & (-65536)) == 1111687168;
                }

/* 1571 */      public static boolean CV_IS_MATND(CvArr mat) {
                    return CV_IS_MATND_HDR(mat) && new CvMatND(mat).data_ptr() != null;
                }

/* 1577 */      public static class CvSparseMat extends CvArr {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native int dims();

                    public native CvSparseMat dims(int i);

                    public native int hashsize();

                    public native CvSparseMat hashsize(int i);

                    public native PointerPointer hashtable();

                    public native CvSparseMat hashtable(PointerPointer pointerPointer);

                    public native int hdr_refcount();

                    public native CvSparseMat hdr_refcount(int i);

                    public native CvSet heap();

                    public native CvSparseMat heap(CvSet cvSet);

                    public native int idxoffset();

                    public native CvSparseMat idxoffset(int i);

                    public native IntPointer refcount();

                    public native CvSparseMat refcount(IntPointer intPointer);

                    public native int size(int i);

                    public native CvSparseMat size(int i, int i2);

                    public native int type();

                    public native CvSparseMat type(int i);

                    public native int valoffset();

                    public native CvSparseMat valoffset(int i);

                    /* JADX INFO: renamed from: clone, reason: collision with other method in class */


/* 1579 */          public CvSparseMat() {
                        allocate();
                        zero();
                    }

/* 1580 */          public CvSparseMat(int size) {
                        allocateArray(size);
                        zero();
                    }

/* 1581 */          public CvSparseMat(Pointer p) {
/* 1582 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 1586 */          public CvSparseMat position(int position) {
/* 1587 */              return (CvSparseMat) super.position(position);
                    }

/* 1590 */          public static CvSparseMat create(int dims, int[] sizes, int type) {
/* 1591 */              CvSparseMat m = opencv_core.cvCreateSparseMat(dims, sizes, type);
/* 1592 */              if (m != null) {
/* 1593 */                  m.deallocator(new ReleaseDeallocator(m));
                        }
/* 1595 */              return m;
                    }

/* 1598 */          public CvSparseMat clone() {
/* 1599 */              CvSparseMat m = opencv_core.cvCloneSparseMat(this);
/* 1600 */              if (m != null) {
/* 1601 */                  m.deallocator(new ReleaseDeallocator(m));
                        }
/* 1603 */              return m;
                    }

/* 1605 */          public void release() {
/* 1606 */              deallocate();
                    }

/* 1606 */          protected static class ReleaseDeallocator extends CvSparseMat implements Pointer.Deallocator {
/* 1608 */              ReleaseDeallocator(CvSparseMat p) {
/* 1609 */                  super(p);
                        }

                        @Override // com.googlecode.javacpp.Pointer, com.googlecode.javacpp.Pointer.Deallocator
/* 1609 */              public void deallocate() {
/* 1610 */                  opencv_core.cvReleaseSparseMat(this);
                        }
                    }
                }

/* 1626 */      public static boolean CV_IS_SPARSE_MAT_HDR(CvArr mat) {
                    return mat != null && (new CvSparseMat(mat).type() & (-65536)) == 1111752704;
                }

/* 1629 */      public static boolean CV_IS_SPARSE_MAT(CvArr mat) {
/* 1630 */          return CV_IS_SPARSE_MAT_HDR(mat);
                }

/* 1631 */      public static class CvSparseNode extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native int hashval();

                    public native CvSparseNode hashval(int i);

                    public native CvSparseNode next();

                    public native CvSparseNode next(CvSparseNode cvSparseNode);


                    static {
/* 1634 */              Loader.load();
                    }

/* 1634 */          public CvSparseNode() {
                        allocate();
                    }

/* 1635 */          public CvSparseNode(int size) {
                        allocateArray(size);
                    }

/* 1636 */          public CvSparseNode(Pointer p) {
/* 1637 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 1641 */          public CvSparseNode position(int position) {
/* 1642 */              return (CvSparseNode) super.position(position);
                    }
                }

/* 1647 */      public static class CvSparseMatIterator extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native int curidx();

                    public native CvSparseMatIterator curidx(int i);

                    public native CvSparseMat mat();

                    public native CvSparseMatIterator mat(CvSparseMat cvSparseMat);

                    public native CvSparseMatIterator node(CvSparseNode cvSparseNode);

                    public native CvSparseNode node();


                    static {
/* 1650 */              Loader.load();
                    }

/* 1650 */          public CvSparseMatIterator() {
                        allocate();
                    }

/* 1651 */          public CvSparseMatIterator(int size) {
                        allocateArray(size);
                    }

/* 1652 */          public CvSparseMatIterator(Pointer p) {
/* 1653 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 1657 */          public CvSparseMatIterator position(int position) {
/* 1658 */              return (CvSparseMatIterator) super.position(position);
                    }
                }

/* 1666 */      public static Pointer CV_NODE_VAL(CvSparseMat mat, CvSparseNode node) {
/* 1667 */          return new BytePointer(node).position(mat.valoffset());
                }

/* 1669 */      public static IntPointer CV_NODE_IDX(CvSparseMat mat, CvSparseNode node) {
/* 1670 */          return new IntPointer(new BytePointer(node).position(mat.idxoffset()));
                }

/* 1672 */      public static class CvRect extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native int height();

                    public native CvRect height(int i);

                    public native int width();

                    public native CvRect width(int i);

                    /* JADX INFO: renamed from: x */
                    public native int m2453x();

                    /* JADX INFO: renamed from: x */
                    public native CvRect m2454x(int i);

                    /* JADX INFO: renamed from: y */
                    public native int m2455y();

                    /* JADX INFO: renamed from: y */
                    public native CvRect m2456y(int i);


                    static {
/* 1675 */              Loader.load();
                    }

/* 1675 */          public CvRect() {
                        allocate();
                    }

/* 1676 */          public CvRect(int size) {
                        allocateArray(size);
                    }

/* 1677 */          public CvRect(Pointer p) {
/* 1678 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 1682 */          public CvRect position(int position) {
/* 1683 */              return (CvRect) super.position(position);
                    }

/* 1685 */          public CvRect(int x, int y, int width, int height) {
/* 1687 */              allocate();
                        m2454x(x).m2456y(y).width(width).height(height);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 1695 */          public String toString() {
/* 1696 */              if (isNull()) {
/* 1697 */                  return super.toString();
                        }
/* 1699 */              if (capacity() == 0) {
/* 1700 */                  return "(" + m2453x() + ", " + m2455y() + "; " + width() + ", " + height() + ")";
                        }
/* 1702 */              String s = "";
/* 1703 */              int p = position();
/* 1704 */              int i = 0;
                        while (i < capacity()) {
/* 1705 */                  position(i);
                            s = s + (i == 0 ? "(" : " (") + m2453x() + ", " + m2455y() + "; " + width() + ", " + height() + ")";
/* 1704 */                  i++;
                        }
/* 1708 */              position(p);
/* 1709 */              return s;
                    }
                }

/* 1713 */      public static CvRect cvRect(int x, int y, int width, int height) {
/* 1714 */          return new CvRect().m2454x(x).m2456y(y).width(width).height(height);
                }

/* 1716 */      public static IplROI cvRectToROI(CvRect rect, int coi) {
/* 1717 */          IplROI roi = new IplROI();
/* 1718 */          roi.xOffset(rect.m2453x());
/* 1719 */          roi.yOffset(rect.m2455y());
/* 1720 */          roi.width(rect.width());
/* 1721 */          roi.height(rect.height());
/* 1722 */          roi.coi(coi);
/* 1723 */          return roi;
                }

/* 1725 */      public static CvRect cvROIToRect(IplROI roi) {
/* 1726 */          return cvRect(roi.xOffset(), roi.yOffset(), roi.width(), roi.height());
                }

/* 1732 */      public static class CvTermCriteria extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native double epsilon();

                    public native CvTermCriteria epsilon(double d);

                    public native int max_iter();

                    public native CvTermCriteria max_iter(int i);

                    public native int type();

                    public native CvTermCriteria type(int i);


                    static {
/* 1735 */              Loader.load();
                    }

/* 1735 */          public CvTermCriteria() {
                        allocate();
                    }

/* 1736 */          public CvTermCriteria(int size) {
                        allocateArray(size);
                    }

/* 1737 */          public CvTermCriteria(Pointer p) {
/* 1738 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 1742 */          public CvTermCriteria position(int position) {
/* 1743 */              return (CvTermCriteria) super.position(position);
                    }

/* 1745 */          public CvTermCriteria(int type, int max_iter, double epsilon) {
/* 1747 */              allocate();
                        type(type).max_iter(max_iter).epsilon(epsilon);
                    }
                }

/* 1754 */      public static CvTermCriteria cvTermCriteria(int type, int max_iter, double epsilon) {
/* 1755 */          return new CvTermCriteria().type(type).max_iter(max_iter).epsilon(epsilon);
                }

/* 1757 */      public static class CvPoint extends Pointer {
                    public static final CvPoint ZERO;

                    private native void allocate();

                    private native void allocateArray(int i);

                    /* JADX INFO: renamed from: x */
                    public native int m2429x();

                    /* JADX INFO: renamed from: x */
                    public native CvPoint m2430x(int i);

                    /* JADX INFO: renamed from: y */
                    public native int m2431y();

                    /* JADX INFO: renamed from: y */
                    public native CvPoint m2432y(int i);


                    static {
/* 1760 */              Loader.load();
/* 1861 */              ZERO = new CvPoint().m2430x(0).m2432y(0);
                    }

/* 1760 */          public CvPoint() {
                        allocate();
                    }

/* 1761 */          public CvPoint(int size) {
                        allocateArray(size);
                    }

/* 1762 */          public CvPoint(Pointer p) {
/* 1763 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 1767 */          public CvPoint position(int position) {
/* 1768 */              return (CvPoint) super.position(position);
                    }

/* 1771 */          public CvPoint(int[] pts, int offset, int length) {
/* 1772 */              this(length / 2);
/* 1773 */              put(pts, offset, length);
                    }

/* 1775 */          public CvPoint(int... pts) {
/* 1776 */              this(pts, 0, pts.length);
                    }

/* 1778 */          public CvPoint(byte shift, double[] pts, int offset, int length) {
/* 1779 */              this(length / 2);
/* 1780 */              put(shift, pts, offset, length);
                    }

/* 1782 */          public CvPoint(byte shift, double... pts) {
/* 1783 */              this(shift, pts, 0, pts.length);
                    }

/* 1789 */          public int[] get() {
/* 1790 */              int[] pts = new int[this.capacity == 0 ? 2 : this.capacity * 2];
/* 1791 */              get(pts);
/* 1792 */              return pts;
                    }

/* 1794 */          public CvPoint get(int[] pts) {
/* 1795 */              return get(pts, 0, pts.length);
                    }

/* 1797 */          public CvPoint get(int[] pts, int offset, int length) {
                        for (int i = 0; i < length / 2; i++) {
/* 1799 */                  position(i);
/* 1800 */                  pts[(i * 2) + offset] = m2429x();
/* 1801 */                  pts[(i * 2) + offset + 1] = m2431y();
                        }
/* 1803 */              return position(0);
                    }

/* 1806 */          public final CvPoint put(int[] pts, int offset, int length) {
                        for (int i = 0; i < length / 2; i++) {
/* 1808 */                  position(i).put(pts[(i * 2) + offset], pts[(i * 2) + offset + 1]);
                        }
/* 1810 */              return position(0);
                    }

/* 1812 */          public final CvPoint put(int... pts) {
/* 1813 */              return put(pts, 0, pts.length);
                    }

/* 1815 */          public final CvPoint put(byte shift, double[] pts, int offset, int length) {
/* 1816 */              int[] a = new int[length];
                        for (int i = 0; i < length; i++) {
/* 1818 */                  a[i] = (int) Math.round(pts[offset + i] * ((double) (1 << shift)));
                        }
/* 1820 */              return put(a, 0, length);
                    }

/* 1822 */          public final CvPoint put(byte shift, double... pts) {
/* 1823 */              return put(shift, pts, 0, pts.length);
                    }

/* 1826 */          public CvPoint put(int x, int y) {
/* 1827 */              return m2430x(x).m2432y(y);
                    }

/* 1829 */          public CvPoint put(CvPoint o) {
/* 1830 */              return m2430x(o.m2429x()).m2432y(o.m2431y());
                    }

/* 1832 */          public CvPoint put(byte shift, CvPoint2D32f o) {
/* 1833 */              m2430x(Math.round(o.m2433x() * (1 << shift)));
/* 1834 */              m2432y(Math.round(o.m2435y() * (1 << shift)));
/* 1835 */              return this;
                    }

/* 1837 */          public CvPoint put(byte shift, CvPoint2D64f o) {
/* 1838 */              m2430x((int) Math.round(o.m2437x() * ((double) (1 << shift))));
/* 1839 */              m2432y((int) Math.round(o.m2439y() * ((double) (1 << shift))));
/* 1840 */              return this;
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 1843 */          public String toString() {
/* 1844 */              if (isNull()) {
/* 1845 */                  return super.toString();
                        }
/* 1847 */              if (capacity() == 0) {
/* 1848 */                  return "(" + m2429x() + ", " + m2431y() + ")";
                        }
/* 1850 */              String s = "";
/* 1851 */              int p = position();
/* 1852 */              int i = 0;
                        while (i < capacity()) {
/* 1853 */                  position(i);
                            s = s + (i == 0 ? "(" : " (") + m2429x() + ", " + m2431y() + ")";
/* 1852 */                  i++;
                        }
/* 1856 */              position(p);
/* 1857 */              return s;
                    }
                }

/* 1863 */      public static CvPoint cvPoint(int x, int y) {
/* 1864 */          return new CvPoint().m2430x(x).m2432y(y);
                }

/* 1865 */      public static class CvPoint2D32f extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    /* JADX INFO: renamed from: x */
                    public native float m2433x();

                    /* JADX INFO: renamed from: x */
                    public native CvPoint2D32f m2434x(float f);

                    /* JADX INFO: renamed from: y */
                    public native float m2435y();

                    /* JADX INFO: renamed from: y */
                    public native CvPoint2D32f m2436y(float f);


                    static {
/* 1868 */              Loader.load();
                    }

/* 1868 */          public CvPoint2D32f() {
                        allocate();
                    }

/* 1869 */          public CvPoint2D32f(int size) {
                        allocateArray(size);
                    }

/* 1870 */          public CvPoint2D32f(Pointer p) {
/* 1871 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 1875 */          public CvPoint2D32f position(int position) {
/* 1876 */              return (CvPoint2D32f) super.position(position);
                    }

/* 1879 */          public CvPoint2D32f(double[] pts, int offset, int length) {
/* 1880 */              this(length / 2);
/* 1881 */              put(pts, offset, length);
                    }

/* 1883 */          public CvPoint2D32f(double... pts) {
/* 1884 */              this(pts, 0, pts.length);
                    }

/* 1890 */          public double[] get() {
/* 1891 */              double[] pts = new double[this.capacity == 0 ? 2 : this.capacity * 2];
/* 1892 */              get(pts);
/* 1893 */              return pts;
                    }

/* 1895 */          public CvPoint2D32f get(double[] pts) {
/* 1896 */              return get(pts, 0, pts.length);
                    }

/* 1898 */          public CvPoint2D32f get(double[] pts, int offset, int length) {
                        for (int i = 0; i < length / 2; i++) {
/* 1900 */                  position(i);
/* 1901 */                  pts[(i * 2) + offset] = m2433x();
/* 1902 */                  pts[(i * 2) + offset + 1] = m2435y();
                        }
/* 1904 */              return position(0);
                    }

/* 1907 */          public final CvPoint2D32f put(double[] pts, int offset, int length) {
                        for (int i = 0; i < length / 2; i++) {
/* 1909 */                  position(i).put(pts[(i * 2) + offset], pts[(i * 2) + offset + 1]);
                        }
/* 1911 */              return position(0);
                    }

/* 1913 */          public final CvPoint2D32f put(double... pts) {
/* 1914 */              return put(pts, 0, pts.length);
                    }

/* 1917 */          public CvPoint2D32f put(double x, double y) {
/* 1918 */              return m2434x((float) x).m2436y((float) y);
                    }

/* 1920 */          public CvPoint2D32f put(CvPoint o) {
/* 1921 */              return m2434x(o.m2429x()).m2436y(o.m2431y());
                    }

/* 1923 */          public CvPoint2D32f put(CvPoint2D32f o) {
/* 1924 */              return m2434x(o.m2433x()).m2436y(o.m2435y());
                    }

/* 1926 */          public CvPoint2D32f put(CvPoint2D64f o) {
/* 1927 */              return m2434x((float) o.m2437x()).m2436y((float) o.m2439y());
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 1930 */          public String toString() {
/* 1931 */              if (isNull()) {
/* 1932 */                  return super.toString();
                        }
/* 1934 */              if (capacity() == 0) {
/* 1935 */                  return "(" + m2433x() + ", " + m2435y() + ")";
                        }
/* 1937 */              String s = "";
/* 1938 */              int p = position();
/* 1939 */              int i = 0;
                        while (i < capacity()) {
/* 1940 */                  position(i);
                            s = s + (i == 0 ? "(" : " (") + m2433x() + ", " + m2435y() + ")";
/* 1939 */                  i++;
                        }
/* 1943 */              position(p);
/* 1944 */              return s;
                    }
                }

/* 1948 */      public static CvPoint2D32f cvPoint2D32f(double x, double y) {
/* 1949 */          return new CvPoint2D32f().m2434x((float) x).m2436y((float) y);
                }

/* 1951 */      public static CvPoint2D32f cvPointTo32f(CvPoint point) {
/* 1952 */          return cvPoint2D32f(point.m2429x(), point.m2431y());
                }

/* 1954 */      public static CvPoint cvPointFrom32f(CvPoint2D32f point) {
/* 1955 */          CvPoint ipt = new CvPoint();
/* 1956 */          ipt.m2430x(Math.round(point.m2433x()));
/* 1957 */          ipt.m2432y(Math.round(point.m2435y()));
/* 1958 */          return ipt;
                }

/* 1959 */      public static class CvPoint3D32f extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    /* JADX INFO: renamed from: x */
                    public native float m2441x();

                    /* JADX INFO: renamed from: x */
                    public native CvPoint3D32f m2442x(float f);

                    /* JADX INFO: renamed from: y */
                    public native float m2443y();

                    /* JADX INFO: renamed from: y */
                    public native CvPoint3D32f m2444y(float f);

                    /* JADX INFO: renamed from: z */
                    public native float m2445z();

                    /* JADX INFO: renamed from: z */
                    public native CvPoint3D32f m2446z(float f);


                    static {
/* 1962 */              Loader.load();
                    }

/* 1962 */          public CvPoint3D32f() {
                        allocate();
                    }

/* 1963 */          public CvPoint3D32f(int size) {
                        allocateArray(size);
                    }

/* 1964 */          public CvPoint3D32f(Pointer p) {
/* 1965 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 1969 */          public CvPoint3D32f position(int position) {
/* 1970 */              return (CvPoint3D32f) super.position(position);
                    }

/* 1973 */          public CvPoint3D32f(double[] pts, int offset, int length) {
/* 1974 */              this(length / 3);
/* 1975 */              put(pts, offset, length);
                    }

/* 1977 */          public CvPoint3D32f(double... pts) {
/* 1978 */              this(pts, 0, pts.length);
                    }

/* 1985 */          public double[] get() {
/* 1986 */              double[] pts = new double[this.capacity == 0 ? 3 : this.capacity * 3];
/* 1987 */              get(pts);
/* 1988 */              return pts;
                    }

/* 1990 */          public CvPoint3D32f get(double[] pts) {
/* 1991 */              return get(pts, 0, pts.length);
                    }

/* 1993 */          public CvPoint3D32f get(double[] pts, int offset, int length) {
                        for (int i = 0; i < length / 3; i++) {
/* 1995 */                  position(i);
/* 1996 */                  pts[(i * 3) + offset] = m2441x();
/* 1997 */                  pts[(i * 3) + offset + 1] = m2443y();
/* 1998 */                  pts[(i * 3) + offset + 2] = m2445z();
                        }
/* 2000 */              return position(0);
                    }

/* 2003 */          public final CvPoint3D32f put(double[] pts, int offset, int length) {
                        for (int i = 0; i < length / 3; i++) {
/* 2005 */                  position(i).put(pts[(i * 3) + offset], pts[(i * 3) + offset + 1], pts[(i * 3) + offset + 2]);
                        }
/* 2007 */              return position(0);
                    }

/* 2009 */          public final CvPoint3D32f put(double... pts) {
/* 2010 */              return put(pts, 0, pts.length);
                    }

/* 2013 */          public CvPoint3D32f put(double x, double y, double z) {
/* 2014 */              return m2442x((float) x).m2444y((float) y).m2446z((float) z);
                    }

/* 2016 */          public CvPoint3D32f put(CvPoint o) {
/* 2017 */              return m2442x(o.m2429x()).m2444y(o.m2431y()).m2446z(0.0f);
                    }

/* 2019 */          public CvPoint3D32f put(CvPoint2D32f o) {
/* 2020 */              return m2442x(o.m2433x()).m2444y(o.m2435y()).m2446z(0.0f);
                    }

/* 2022 */          public CvPoint3D32f put(CvPoint2D64f o) {
/* 2023 */              return m2442x((float) o.m2437x()).m2444y((float) o.m2439y()).m2446z(0.0f);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 2026 */          public String toString() {
/* 2027 */              if (isNull()) {
/* 2028 */                  return super.toString();
                        }
/* 2030 */              if (capacity() == 0) {
/* 2031 */                  return "(" + m2441x() + ", " + m2443y() + ", " + m2445z() + ")";
                        }
/* 2033 */              String s = "";
/* 2034 */              int p = position();
/* 2035 */              int i = 0;
                        while (i < capacity()) {
/* 2036 */                  position(i);
                            s = s + (i == 0 ? "(" : " (") + m2441x() + ", " + m2443y() + ", " + m2445z() + ")";
/* 2035 */                  i++;
                        }
/* 2039 */              position(p);
/* 2040 */              return s;
                    }
                }

/* 2044 */      public static CvPoint3D32f cvPoint3D32f(double x, double y, double z) {
/* 2045 */          return new CvPoint3D32f().m2442x((float) x).m2444y((float) y).m2446z((float) z);
                }

/* 2046 */      public static class CvPoint2D64f extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    /* JADX INFO: renamed from: x */
                    public native double m2437x();

                    /* JADX INFO: renamed from: x */
                    public native CvPoint2D64f m2438x(double d);

                    /* JADX INFO: renamed from: y */
                    public native double m2439y();

                    /* JADX INFO: renamed from: y */
                    public native CvPoint2D64f m2440y(double d);


                    static {
/* 2049 */              Loader.load();
                    }

/* 2049 */          public CvPoint2D64f() {
                        allocate();
                    }

/* 2050 */          public CvPoint2D64f(int size) {
                        allocateArray(size);
                    }

/* 2051 */          public CvPoint2D64f(Pointer p) {
/* 2052 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 2056 */          public CvPoint2D64f position(int position) {
/* 2057 */              return (CvPoint2D64f) super.position(position);
                    }

/* 2060 */          public CvPoint2D64f(double[] pts, int offset, int length) {
/* 2061 */              this(length / 2);
/* 2062 */              put(pts, offset, length);
                    }

/* 2064 */          public CvPoint2D64f(double... pts) {
/* 2065 */              this(pts, 0, pts.length);
                    }

/* 2071 */          public double[] get() {
/* 2072 */              double[] pts = new double[this.capacity == 0 ? 2 : this.capacity * 2];
/* 2073 */              get(pts);
/* 2074 */              return pts;
                    }

/* 2076 */          public CvPoint2D64f get(double[] pts) {
/* 2077 */              return get(pts, 0, pts.length);
                    }

/* 2079 */          public CvPoint2D64f get(double[] pts, int offset, int length) {
                        for (int i = 0; i < length / 2; i++) {
/* 2081 */                  position(i);
/* 2082 */                  pts[(i * 2) + offset] = m2437x();
/* 2083 */                  pts[(i * 2) + offset + 1] = m2439y();
                        }
/* 2085 */              return position(0);
                    }

/* 2088 */          public final CvPoint2D64f put(double[] pts, int offset, int length) {
                        for (int i = 0; i < length / 2; i++) {
/* 2090 */                  position(i).put(pts[(i * 2) + offset], pts[(i * 2) + offset + 1]);
                        }
/* 2092 */              return position(0);
                    }

/* 2094 */          public final CvPoint2D64f put(double... pts) {
/* 2095 */              return put(pts, 0, pts.length);
                    }

/* 2098 */          public CvPoint2D64f put(double x, double y) {
/* 2099 */              return m2438x(x).m2440y(y);
                    }

/* 2101 */          public CvPoint2D64f put(CvPoint o) {
/* 2102 */              return m2438x(o.m2429x()).m2440y(o.m2431y());
                    }

/* 2104 */          public CvPoint2D64f put(CvPoint2D32f o) {
/* 2105 */              return m2438x(o.m2433x()).m2440y(o.m2435y());
                    }

/* 2107 */          public CvPoint2D64f put(CvPoint2D64f o) {
/* 2108 */              return m2438x(o.m2437x()).m2440y(o.m2439y());
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 2111 */          public String toString() {
/* 2112 */              if (isNull()) {
/* 2113 */                  return super.toString();
                        }
/* 2115 */              if (capacity() == 0) {
/* 2116 */                  return "(" + ((float) m2437x()) + ", " + ((float) m2439y()) + ")";
                        }
/* 2118 */              String s = "";
/* 2119 */              int p = position();
/* 2120 */              int i = 0;
                        while (i < capacity()) {
/* 2121 */                  position(i);
                            s = s + (i == 0 ? "(" : " (") + ((float) m2437x()) + ", " + ((float) m2439y()) + ")";
/* 2120 */                  i++;
                        }
/* 2124 */              position(p);
/* 2125 */              return s;
                    }
                }

/* 2129 */      public static CvPoint2D64f cvPoint2D64f(double x, double y) {
/* 2130 */          return new CvPoint2D64f().m2438x(x).m2440y(y);
                }

/* 2131 */      public static class CvPoint3D64f extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    /* JADX INFO: renamed from: x */
                    public native double m2447x();

                    /* JADX INFO: renamed from: x */
                    public native CvPoint3D64f m2448x(double d);

                    /* JADX INFO: renamed from: y */
                    public native double m2449y();

                    /* JADX INFO: renamed from: y */
                    public native CvPoint3D64f m2450y(double d);

                    /* JADX INFO: renamed from: z */
                    public native double m2451z();

                    /* JADX INFO: renamed from: z */
                    public native CvPoint3D64f m2452z(double d);


                    static {
/* 2134 */              Loader.load();
                    }

/* 2134 */          public CvPoint3D64f() {
                        allocate();
                    }

/* 2135 */          public CvPoint3D64f(int size) {
                        allocateArray(size);
                    }

/* 2136 */          public CvPoint3D64f(Pointer p) {
/* 2137 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 2141 */          public CvPoint3D64f position(int position) {
/* 2142 */              return (CvPoint3D64f) super.position(position);
                    }

/* 2145 */          public CvPoint3D64f(double[] pts, int offset, int length) {
/* 2146 */              this(length / 3);
/* 2147 */              put(pts, offset, length);
                    }

/* 2149 */          public CvPoint3D64f(double... pts) {
/* 2150 */              this(pts, 0, pts.length);
                    }

/* 2157 */          public double[] get() {
/* 2158 */              double[] pts = new double[this.capacity == 0 ? 3 : this.capacity * 3];
/* 2159 */              get(pts);
/* 2160 */              return pts;
                    }

/* 2162 */          public CvPoint3D64f get(double[] pts) {
/* 2163 */              return get(pts, 0, pts.length);
                    }

/* 2165 */          public CvPoint3D64f get(double[] pts, int offset, int length) {
                        for (int i = 0; i < length / 3; i++) {
/* 2167 */                  position(i);
/* 2168 */                  pts[(i * 3) + offset] = m2447x();
/* 2169 */                  pts[(i * 3) + offset + 1] = m2449y();
/* 2170 */                  pts[(i * 3) + offset + 2] = m2451z();
                        }
/* 2172 */              return position(0);
                    }

/* 2175 */          public final CvPoint3D64f put(double[] pts, int offset, int length) {
                        for (int i = 0; i < length / 3; i++) {
/* 2177 */                  position(i).put(pts[(i * 3) + offset], pts[(i * 3) + offset + 1], pts[(i * 3) + offset + 2]);
                        }
/* 2179 */              return position(0);
                    }

/* 2181 */          public final CvPoint3D64f put(double... pts) {
/* 2182 */              return put(pts, 0, pts.length);
                    }

/* 2185 */          public CvPoint3D64f put(double x, double y, double z) {
/* 2186 */              return m2448x(m2447x()).m2450y(m2449y()).m2452z(m2451z());
                    }

/* 2188 */          public CvPoint3D64f put(CvPoint o) {
/* 2189 */              return m2448x(o.m2429x()).m2450y(o.m2431y()).m2452z(0.0d);
                    }

/* 2191 */          public CvPoint3D64f put(CvPoint2D32f o) {
/* 2192 */              return m2448x(o.m2433x()).m2450y(o.m2435y()).m2452z(0.0d);
                    }

/* 2194 */          public CvPoint3D64f put(CvPoint2D64f o) {
/* 2195 */              return m2448x(o.m2437x()).m2450y(o.m2439y()).m2452z(0.0d);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 2198 */          public String toString() {
/* 2199 */              if (isNull()) {
/* 2200 */                  return super.toString();
                        }
/* 2202 */              if (capacity() == 0) {
/* 2203 */                  return "(" + ((float) m2447x()) + ", " + ((float) m2449y()) + ", " + ((float) m2451z()) + ")";
                        }
/* 2205 */              String s = "";
/* 2206 */              int p = position();
/* 2207 */              int i = 0;
                        while (i < capacity()) {
/* 2208 */                  position(i);
                            s = s + (i == 0 ? "(" : " (") + ((float) m2447x()) + ", " + ((float) m2449y()) + ", " + ((float) m2451z()) + ")";
/* 2207 */                  i++;
                        }
/* 2211 */              position(p);
/* 2212 */              return s;
                    }
                }

/* 2216 */      public static CvPoint3D64f cvPoint3D64f(double x, double y, double z) {
/* 2217 */          return new CvPoint3D64f().m2448x(x).m2450y(y).m2452z(z);
                }

/* 2218 */      public static class CvSize extends Pointer {
                    public static final CvSize ZERO;

                    private native void allocate();

                    private native void allocateArray(int i);

                    public native int height();

                    public native CvSize height(int i);

                    public native int width();

                    public native CvSize width(int i);


                    static {
/* 2221 */              Loader.load();
/* 2256 */              ZERO = new CvSize().width(0).height(0);
                    }

/* 2221 */          public CvSize() {
                        allocate();
                    }

/* 2222 */          public CvSize(int size) {
                        allocateArray(size);
                    }

/* 2223 */          public CvSize(Pointer p) {
/* 2224 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 2228 */          public CvSize position(int position) {
/* 2229 */              return (CvSize) super.position(position);
                    }

/* 2231 */          public CvSize(int width, int height) {
/* 2233 */              allocate();
                        width(width).height(height);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 2239 */          public String toString() {
/* 2240 */              if (isNull()) {
/* 2241 */                  return super.toString();
                        }
/* 2243 */              if (capacity() == 0) {
/* 2244 */                  return "(" + width() + ", " + height() + ")";
                        }
/* 2246 */              String s = "";
/* 2247 */              int p = position();
/* 2248 */              int i = 0;
                        while (i < capacity()) {
/* 2249 */                  position(i);
                            s = s + (i == 0 ? "(" : " (") + width() + ", " + height() + ")";
/* 2248 */                  i++;
                        }
/* 2252 */              position(p);
/* 2253 */              return s;
                    }
                }

/* 2258 */      public static CvSize cvSize(int width, int height) {
/* 2259 */          return new CvSize().width(width).height(height);
                }

/* 2260 */      public static class CvSize2D32f extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native float height();

                    public native CvSize2D32f height(float f);

                    public native float width();

                    public native CvSize2D32f width(float f);


                    static {
/* 2263 */              Loader.load();
                    }

/* 2263 */          public CvSize2D32f() {
                        allocate();
                    }

/* 2264 */          public CvSize2D32f(int size) {
                        allocateArray(size);
                    }

/* 2265 */          public CvSize2D32f(Pointer p) {
/* 2266 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 2270 */          public CvSize2D32f position(int position) {
/* 2271 */              return (CvSize2D32f) super.position(position);
                    }

/* 2273 */          public CvSize2D32f(float width, float height) {
/* 2275 */              allocate();
                        width(width).height(height);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 2281 */          public String toString() {
/* 2282 */              if (isNull()) {
/* 2283 */                  return super.toString();
                        }
/* 2285 */              if (capacity() == 0) {
/* 2286 */                  return "(" + width() + ", " + height() + ")";
                        }
/* 2288 */              String s = "";
/* 2289 */              int p = position();
/* 2290 */              int i = 0;
                        while (i < capacity()) {
/* 2291 */                  position(i);
                            s = s + (i == 0 ? "(" : " (") + width() + ", " + height() + ")";
/* 2290 */                  i++;
                        }
/* 2294 */              position(p);
/* 2295 */              return s;
                    }
                }

/* 2299 */      public static CvSize2D32f cvSize2D32f(double width, double height) {
/* 2300 */          return new CvSize2D32f().width((float) width).height((float) height);
                }

/* 2301 */      public static class CvBox2D extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native float angle();

                    public native CvBox2D angle(float f);

                    public native CvBox2D center(CvPoint2D32f cvPoint2D32f);

                    @ByRef
                    public native CvPoint2D32f center();

                    public native CvBox2D size(CvSize2D32f cvSize2D32f);

                    @ByRef
                    public native CvSize2D32f size();


                    static {
/* 2304 */              Loader.load();
                    }

/* 2304 */          public CvBox2D() {
                        allocate();
                    }

/* 2305 */          public CvBox2D(int size) {
                        allocateArray(size);
                    }

/* 2306 */          public CvBox2D(Pointer p) {
/* 2307 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 2311 */          public CvBox2D position(int position) {
/* 2312 */              return (CvBox2D) super.position(position);
                    }

/* 2314 */          public CvBox2D(CvPoint2D32f center, CvSize2D32f size, float angle) {
/* 2316 */              allocate();
                        center(center).size(size).angle(angle);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 2323 */          public String toString() {
/* 2324 */              if (isNull()) {
/* 2325 */                  return super.toString();
                        }
/* 2327 */              if (capacity() == 0) {
/* 2328 */                  return "(" + center() + ", " + size() + ", " + angle() + ")";
                        }
/* 2330 */              String s = "";
/* 2331 */              int p = position();
/* 2332 */              int i = 0;
                        while (i < capacity()) {
/* 2333 */                  position(i);
                            s = s + (i == 0 ? "(" : " (") + center() + ", " + size() + ", " + angle() + ")";
/* 2332 */                  i++;
                        }
/* 2336 */              position(p);
/* 2337 */              return s;
                    }
                }

/* 2340 */      public static class CvLineIterator extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native int err();

                    public native CvLineIterator err(int i);

                    public native int minus_delta();

                    public native CvLineIterator minus_delta(int i);

                    public native int minus_step();

                    public native CvLineIterator minus_step(int i);

                    public native int plus_delta();

                    public native CvLineIterator plus_delta(int i);

                    public native int plus_step();

                    public native CvLineIterator plus_step(int i);

                    @Cast({"uchar*"})
                    public native BytePointer ptr();

                    public native CvLineIterator ptr(BytePointer bytePointer);


                    static {
/* 2343 */              Loader.load();
                    }

/* 2343 */          public CvLineIterator() {
                        allocate();
                    }

/* 2344 */          public CvLineIterator(int size) {
                        allocateArray(size);
                    }

/* 2345 */          public CvLineIterator(Pointer p) {
/* 2346 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 2350 */          public CvLineIterator position(int position) {
/* 2351 */              return (CvLineIterator) super.position(position);
                    }
                }

/* 2363 */      public static class CvSlice extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native int end_index();

                    public native CvSlice end_index(int i);

                    public native int start_index();

                    public native CvSlice start_index(int i);


                    static {
/* 2366 */              Loader.load();
                    }

/* 2366 */          public CvSlice() {
                        allocate();
                    }

/* 2367 */          public CvSlice(int size) {
                        allocateArray(size);
                    }

/* 2368 */          public CvSlice(Pointer p) {
/* 2369 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 2373 */          public CvSlice position(int position) {
/* 2374 */              return (CvSlice) super.position(position);
                    }
                }

/* 2380 */      public static CvSlice cvSlice(int start, int end) {
/* 2381 */          return new CvSlice().start_index(start).end_index(end);
                }

/* 2385 */      public static class CvScalar extends Pointer {
                    public static final CvScalar ALPHA1;
                    public static final CvScalar ALPHA255;
                    public static final CvScalar BLACK;
                    public static final CvScalar BLUE;
                    public static final CvScalar CYAN;
                    public static final CvScalar GRAY;
                    public static final CvScalar GREEN;
                    public static final CvScalar MAGENTA;
                    public static final CvScalar ONE;
                    public static final CvScalar ONEHALF;
                    public static final CvScalar RED;
                    public static final CvScalar WHITE;
                    public static final CvScalar YELLOW;
                    public static final CvScalar ZERO;

                    private native void allocate();

                    private native void allocateArray(int i);

                    @MemberGetter
                    @Name({"val"})
                    public native DoublePointer getDoublePointerVal();

                    @MemberGetter
                    @Name({"val"})
                    @Cast({"double*"})
                    public native LongPointer getLongPointerVal();

                    public native double val(int i);

                    public native CvScalar val(int i, double d);


                    static {
/* 2388 */              Loader.load();
/* 2451 */              ZERO = new CvScalar().val(0, 0.0d).val(1, 0.0d).val(2, 0.0d).val(3, 0.0d);
/* 2452 */              ONE = new CvScalar().val(0, 1.0d).val(1, 1.0d).val(2, 1.0d).val(3, 1.0d);
/* 2453 */              ONEHALF = new CvScalar().val(0, 0.5d).val(1, 0.5d).val(2, 0.5d).val(3, 0.5d);
/* 2454 */              ALPHA1 = new CvScalar().val(0, 0.0d).val(1, 0.0d).val(2, 0.0d).val(3, 1.0d);
/* 2455 */              ALPHA255 = new CvScalar().val(0, 0.0d).val(1, 0.0d).val(2, 0.0d).val(3, 255.0d);
/* 2457 */              WHITE = opencv_core.CV_RGB(255.0d, 255.0d, 255.0d);
/* 2458 */              GRAY = opencv_core.CV_RGB(128.0d, 128.0d, 128.0d);
/* 2459 */              BLACK = opencv_core.CV_RGB(0.0d, 0.0d, 0.0d);
/* 2460 */              RED = opencv_core.CV_RGB(255.0d, 0.0d, 0.0d);
/* 2461 */              GREEN = opencv_core.CV_RGB(0.0d, 255.0d, 0.0d);
/* 2462 */              BLUE = opencv_core.CV_RGB(0.0d, 0.0d, 255.0d);
/* 2463 */              CYAN = opencv_core.CV_RGB(0.0d, 255.0d, 255.0d);
/* 2464 */              MAGENTA = opencv_core.CV_RGB(255.0d, 0.0d, 255.0d);
/* 2465 */              YELLOW = opencv_core.CV_RGB(255.0d, 255.0d, 0.0d);
                    }

/* 2388 */          public CvScalar() {
                        allocate();
                    }

/* 2389 */          public CvScalar(int size) {
                        allocateArray(size);
                    }

/* 2390 */          public CvScalar(Pointer p) {
/* 2391 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 2395 */          public CvScalar position(int position) {
/* 2396 */              return (CvScalar) super.position(position);
                    }

/* 2398 */          public CvScalar(double val0, double val1, double val2, double val3) {
/* 2400 */              allocate();
                        val(0, val0).val(1, val1).val(2, val2).val(3, val3);
                    }

/* 2403 */          public double getVal(int i) {
/* 2404 */              return val(i);
                    }

/* 2404 */          public CvScalar setVal(int i, double val) {
/* 2405 */              return val(i, val);
                    }

/* 2413 */          public void scale(double s) {
                        for (int i = 0; i < 4; i++) {
/* 2415 */                  val(i, val(i) * s);
                        }
                    }

/* 2418 */          public double red() {
/* 2419 */              return val(2);
                    }

/* 2419 */          public double green() {
/* 2420 */              return val(1);
                    }

/* 2420 */          public double blue() {
/* 2421 */              return val(0);
                    }

/* 2421 */          public CvScalar red(double r) {
/* 2422 */              val(2, r);
                        return this;
                    }

/* 2422 */          public CvScalar green(double g) {
/* 2423 */              val(1, g);
                        return this;
                    }

/* 2423 */          public CvScalar blue(double b) {
/* 2424 */              val(0, b);
                        return this;
                    }

/* 2426 */          public double magnitude() {
/* 2427 */              return Math.sqrt((val(0) * val(0)) + (val(1) * val(1)) + (val(2) * val(2)) + (val(3) * val(3)));
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 2430 */          public String toString() {
/* 2431 */              if (isNull()) {
/* 2432 */                  return super.toString();
                        }
/* 2434 */              if (capacity() == 0) {
/* 2435 */                  return "(" + ((float) val(0)) + ", " + ((float) val(1)) + ", " + ((float) val(2)) + ", " + ((float) val(3)) + ")";
                        }
/* 2438 */              String s = "";
/* 2439 */              int p = position();
/* 2440 */              int i = 0;
                        while (i < capacity()) {
/* 2441 */                  position(i);
                            s = s + (i == 0 ? "(" : " (") + ((float) val(0)) + ", " + ((float) val(1)) + ", " + ((float) val(2)) + ", " + ((float) val(3)) + ")";
/* 2440 */                  i++;
                        }
/* 2445 */              position(p);
/* 2446 */              return s;
                    }
                }

/* 2467 */      public static CvScalar cvScalar(double val0, double val1, double val2, double val3) {
/* 2468 */          return new CvScalar().val(0, val0).val(1, val1).val(2, val2).val(3, val3);
                }

/* 2470 */      public static CvScalar cvRealScalar(double val0) {
/* 2471 */          return new CvScalar().val(0, val0).val(1, 0.0d).val(2, 0.0d).val(3, 0.0d);
                }

/* 2473 */      public static CvScalar cvScalarAll(double val0123) {
/* 2474 */          return new CvScalar().val(0, val0123).val(1, val0123).val(2, val0123).val(3, val0123);
                }

/* 2476 */      public static class CvMemBlock extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native CvMemBlock next();

                    public native CvMemBlock next(CvMemBlock cvMemBlock);

                    public native CvMemBlock prev();

                    public native CvMemBlock prev(CvMemBlock cvMemBlock);


                    static {
/* 2479 */              Loader.load();
                    }

/* 2479 */          public CvMemBlock() {
                        allocate();
                    }

/* 2480 */          public CvMemBlock(int size) {
                        allocateArray(size);
                    }

/* 2481 */          public CvMemBlock(Pointer p) {
/* 2482 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 2486 */          public CvMemBlock position(int position) {
/* 2487 */              return (CvMemBlock) super.position(position);
                    }
                }

/* 2494 */      public static class CvMemStorage extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native int block_size();

                    public native CvMemStorage block_size(int i);

                    public native CvMemBlock bottom();

                    public native CvMemStorage bottom(CvMemBlock cvMemBlock);

                    public native int free_space();

                    public native CvMemStorage free_space(int i);

                    public native CvMemStorage parent();

                    public native CvMemStorage parent(CvMemStorage cvMemStorage);

                    public native int signature();

                    public native CvMemStorage signature(int i);

                    public native CvMemBlock top();

                    public native CvMemStorage top(CvMemBlock cvMemBlock);


                    static {
/* 2497 */              Loader.load();
                    }

/* 2497 */          public CvMemStorage() {
                        allocate();
                        zero();
                    }

/* 2498 */          public CvMemStorage(int size) {
                        allocateArray(size);
                        zero();
                    }

/* 2499 */          public CvMemStorage(Pointer p) {
/* 2500 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 2504 */          public CvMemStorage position(int position) {
/* 2505 */              return (CvMemStorage) super.position(position);
                    }

/* 2508 */          public static CvMemStorage create(int block_size) {
/* 2509 */              CvMemStorage m = opencv_core.cvCreateMemStorage(block_size);
/* 2510 */              if (m != null) {
/* 2511 */                  m.deallocator(new ReleaseDeallocator(m));
                        }
/* 2513 */              return m;
                    }

/* 2515 */          public static CvMemStorage create() {
/* 2516 */              return create(0);
                    }

/* 2519 */          public void release() {
/* 2520 */              deallocate();
                    }

/* 2520 */          protected static class ReleaseDeallocator extends CvMemStorage implements Pointer.Deallocator {

/* 2522 */              ReleaseDeallocator(CvMemStorage p) {
/* 2523 */                  super(p);
                        }

                        @Override // com.googlecode.javacpp.Pointer, com.googlecode.javacpp.Pointer.Deallocator
/* 2523 */              public void deallocate() {
/* 2524 */                  opencv_core.cvReleaseMemStorage(this);
                        }
                    }
                }

/* 2535 */      public static boolean CV_IS_STORAGE(CvArr storage) {
                    return storage != null && (new CvMemStorage(storage).signature() & (-65536)) == 1116274688;
                }

/* 2537 */      public static class CvMemStoragePos extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native int free_space();

                    public native CvMemStoragePos free_space(int i);

                    public native CvMemBlock top();

                    public native CvMemStoragePos top(CvMemBlock cvMemBlock);


                    static {
/* 2540 */              Loader.load();
                    }

/* 2540 */          public CvMemStoragePos() {
                        allocate();
                    }

/* 2541 */          public CvMemStoragePos(int size) {
                        allocateArray(size);
                    }

/* 2542 */          public CvMemStoragePos(Pointer p) {
/* 2543 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 2547 */          public CvMemStoragePos position(int position) {
/* 2548 */              return (CvMemStoragePos) super.position(position);
                    }
                }

/* 2554 */      public static class CvSeqBlock extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native int count();

                    public native CvSeqBlock count(int i);

                    @Cast({"schar*"})
                    public native BytePointer data();

                    public native CvSeqBlock data(BytePointer bytePointer);

                    public native CvSeqBlock next();

                    public native CvSeqBlock next(CvSeqBlock cvSeqBlock);

                    public native CvSeqBlock prev();

                    public native CvSeqBlock prev(CvSeqBlock cvSeqBlock);

                    public native int start_index();

                    public native CvSeqBlock start_index(int i);


                    static {
/* 2557 */              Loader.load();
                    }

/* 2557 */          public CvSeqBlock() {
                        allocate();
                    }

/* 2558 */          public CvSeqBlock(int size) {
                        allocateArray(size);
                    }

/* 2559 */          public CvSeqBlock(Pointer p) {
/* 2560 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 2564 */          public CvSeqBlock position(int position) {
/* 2565 */              return (CvSeqBlock) super.position(position);
                    }
                }

/* 2574 */      public static class CvSeq extends CvArr {
                    private native void allocate();

                    private native void allocateArray(int i);

                    @Cast({"schar*"})
                    public native BytePointer block_max();

                    public native CvSeq block_max(BytePointer bytePointer);

                    public native int delta_elems();

                    public native CvSeq delta_elems(int i);

                    public native int elem_size();

                    public native CvSeq elem_size(int i);

                    public native CvSeq first(CvSeqBlock cvSeqBlock);

                    public native CvSeqBlock first();

                    public native int flags();

                    public native CvSeq flags(int i);

                    public native CvSeq free_blocks(CvSeqBlock cvSeqBlock);

                    public native CvSeqBlock free_blocks();

                    public native CvSeq h_next();

                    public native CvSeq h_next(CvSeq cvSeq);

                    public native CvSeq h_prev();

                    public native CvSeq h_prev(CvSeq cvSeq);

                    public native int header_size();

                    public native CvSeq header_size(int i);

                    @Cast({"schar*"})
                    public native BytePointer ptr();

                    public native CvSeq ptr(BytePointer bytePointer);

                    public native CvMemStorage storage();

                    public native CvSeq storage(CvMemStorage cvMemStorage);

                    public native int total();

                    public native CvSeq total(int i);

                    public native CvSeq v_next();

                    public native CvSeq v_next(CvSeq cvSeq);

                    public native CvSeq v_prev();

                    public native CvSeq v_prev(CvSeq cvSeq);


/* 2576 */          public CvSeq() {
                        allocate();
                        zero();
                    }

/* 2577 */          public CvSeq(int size) {
                        allocateArray(size);
                        zero();
                    }

/* 2578 */          public CvSeq(Pointer p) {
/* 2579 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 2583 */          public CvSeq position(int position) {
/* 2584 */              return (CvSeq) super.position(position);
                    }

/* 2588 */          public static CvSeq create(int seq_flags, int header_size, int elem_size, CvMemStorage storage) {
/* 2589 */              return opencv_core.cvCreateSeq(seq_flags, header_size, elem_size, storage);
                    }
                }

/* 2613 */      public static class CvSetElem extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native int flags();

                    public native CvSetElem flags(int i);

                    public native CvSetElem next_free();

                    public native CvSetElem next_free(CvSetElem cvSetElem);


                    static {
/* 2616 */              Loader.load();
                    }

/* 2616 */          public CvSetElem() {
                        allocate();
                    }

/* 2617 */          public CvSetElem(int size) {
                        allocateArray(size);
                    }

/* 2618 */          public CvSetElem(Pointer p) {
/* 2619 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 2623 */          public CvSetElem position(int position) {
/* 2624 */              return (CvSetElem) super.position(position);
                    }
                }

/* 2629 */      public static class CvSet extends CvSeq {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native int active_count();

                    public native CvSet active_count(int i);

                    public native CvSet free_elems(CvSetElem cvSetElem);

                    public native CvSetElem free_elems();



/* 2631 */          public CvSet() {
                        allocate();
                        zero();
                    }

/* 2632 */          public CvSet(int size) {
                        allocateArray(size);
                        zero();
                    }

/* 2633 */          public CvSet(Pointer p) {
/* 2634 */              super(p);
                    }

                    @Override // com.googlecode.javacv.cpp.opencv_core.CvSeq, com.googlecode.javacpp.Pointer
/* 2638 */          public CvSet position(int position) {
/* 2639 */              return (CvSet) super.position(position);
                    }

/* 2643 */          public static CvSet create(int set_flags, int header_size, int elem_size, CvMemStorage storage) {
/* 2644 */              return opencv_core.cvCreateSet(set_flags, header_size, elem_size, storage);
                    }
                }

/* 2653 */      public static boolean CV_IS_SET_ELEM(Pointer ptr) {
                    return new CvSetElem(ptr).flags() >= 0;
                }

/* 2656 */      public static class CvGraphEdge extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native int flags();

                    public native CvGraphEdge flags(int i);

                    public native CvGraphEdge next(int i);

                    public native CvGraphEdge next(int i, CvGraphEdge cvGraphEdge);

                    public native CvGraphEdge vtx(int i, CvGraphVtx cvGraphVtx);

                    public native CvGraphVtx vtx(int i);

                    public native float weight();

                    public native CvGraphEdge weight(float f);


                    static {
/* 2659 */              Loader.load();
                    }

/* 2659 */          public CvGraphEdge() {
                        allocate();
                    }

/* 2660 */          public CvGraphEdge(int size) {
                        allocateArray(size);
                    }

/* 2661 */          public CvGraphEdge(Pointer p) {
/* 2662 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 2666 */          public CvGraphEdge position(int position) {
/* 2667 */              return (CvGraphEdge) super.position(position);
                    }
                }

/* 2674 */      public static class CvGraphVtx extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native CvGraphEdge first();

                    public native CvGraphVtx first(CvGraphEdge cvGraphEdge);

                    public native int flags();

                    public native CvGraphVtx flags(int i);


                    static {
/* 2677 */              Loader.load();
                    }

/* 2677 */          public CvGraphVtx() {
                        allocate();
                    }

/* 2678 */          public CvGraphVtx(int size) {
                        allocateArray(size);
                    }

/* 2679 */          public CvGraphVtx(Pointer p) {
/* 2680 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 2684 */          public CvGraphVtx position(int position) {
/* 2685 */              return (CvGraphVtx) super.position(position);
                    }
                }

/* 2690 */      public static class CvGraphVtx2D extends CvGraphVtx {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native CvGraphVtx2D ptr(CvPoint2D32f cvPoint2D32f);

                    public native CvPoint2D32f ptr();



/* 2692 */          public CvGraphVtx2D() {
                        allocate();
                    }

/* 2693 */          public CvGraphVtx2D(int size) {
                        allocateArray(size);
                    }

/* 2694 */          public CvGraphVtx2D(Pointer p) {
/* 2695 */              super(p);
                    }

                    @Override // com.googlecode.javacv.cpp.opencv_core.CvGraphVtx, com.googlecode.javacpp.Pointer
/* 2699 */          public CvGraphVtx2D position(int position) {
/* 2700 */              return (CvGraphVtx2D) super.position(position);
                    }
                }

/* 2704 */      public static class CvGraph extends CvSet {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native CvGraph edges(CvSet cvSet);

                    public native CvSet edges();




/* 2706 */          public CvGraph() {
                        allocate();
                        zero();
                    }

/* 2707 */          public CvGraph(int size) {
                        allocateArray(size);
                        zero();
                    }

/* 2708 */          public CvGraph(Pointer p) {
/* 2709 */              super(p);
                    }

                    @Override // com.googlecode.javacv.cpp.opencv_core.CvSet, com.googlecode.javacv.cpp.opencv_core.CvSeq, com.googlecode.javacpp.Pointer
/* 2713 */          public CvGraph position(int position) {
/* 2714 */              return (CvGraph) super.position(position);
                    }

/* 2718 */          public static CvGraph create(int graph_flags, int header_size, int vtx_size, int edge_size, CvMemStorage storage) {
/* 2719 */              return opencv_core.cvCreateGraph(graph_flags, header_size, vtx_size, edge_size, storage);
                    }
                }

/* 2726 */      public static class CvChain extends CvSeq {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native CvChain origin(CvPoint cvPoint);

                    @ByRef
                    public native CvPoint origin();



/* 2728 */          public CvChain() {
                        allocate();
                    }

/* 2729 */          public CvChain(int size) {
                        allocateArray(size);
                    }

/* 2730 */          public CvChain(Pointer p) {
/* 2731 */              super(p);
                    }

                    @Override // com.googlecode.javacv.cpp.opencv_core.CvSeq, com.googlecode.javacpp.Pointer
/* 2735 */          public CvChain position(int position) {
/* 2736 */              return (CvChain) super.position(position);
                    }
                }

/* 2740 */      public static class CvContour extends CvSeq {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native int color();

                    public native CvContour color(int i);

                    public native CvContour rect(CvRect cvRect);

                    @ByRef
                    public native CvRect rect();

                    public native int reserved(int i);

                    public native CvContour reserved(int i, int i2);



/* 2742 */          public CvContour() {
                        allocate();
                    }

/* 2743 */          public CvContour(int size) {
                        allocateArray(size);
                    }

/* 2744 */          public CvContour(Pointer p) {
/* 2745 */              super(p);
                    }

                    @Override // com.googlecode.javacv.cpp.opencv_core.CvSeq, com.googlecode.javacpp.Pointer
/* 2749 */          public CvContour position(int position) {
/* 2750 */              return (CvContour) super.position(position);
                    }
                }

/* 2828 */      public static boolean CV_IS_SEQ(CvArr seq) {
                    return seq != null && (new CvSeq(seq).flags() & (-65536)) == 1117323264;
                }

/* 2831 */      public static boolean CV_IS_SET(CvArr set) {
                    return set != null && (new CvSet(set).flags() & (-65536)) == 1117257728;
                }

/* 2834 */      public static int CV_SEQ_ELTYPE(CvSeq seq) {
/* 2835 */          return seq.flags() & 4095;
                }

/* 2835 */      public static int CV_SEQ_KIND(CvSeq seq) {
/* 2836 */          return seq.flags() & 12288;
                }

/* 2837 */      public static boolean CV_IS_SEQ_INDEX(CvSeq seq) {
                    return CV_SEQ_ELTYPE(seq) == CV_SEQ_ELTYPE_INDEX && CV_SEQ_KIND(seq) == 0;
                }

/* 2840 */      public static boolean CV_IS_SEQ_CURVE(CvSeq seq) {
                    return CV_SEQ_KIND(seq) == 4096;
                }

/* 2841 */      public static boolean CV_IS_SEQ_CLOSED(CvSeq seq) {
                    return (seq.flags() & 16384) != 0;
                }

/* 2842 */      public static boolean CV_IS_SEQ_CONVEX(CvSeq seq) {
/* 2843 */          return false;
                }

/* 2843 */      public static boolean CV_IS_SEQ_HOLE(CvSeq seq) {
                    return (seq.flags() & 32768) != 0;
                }

/* 2844 */      public static boolean CV_IS_SEQ_SIMPLE(CvSeq seq) {
/* 2845 */          return true;
                }

/* 2847 */      public static boolean CV_IS_SEQ_POINT_SET(CvSeq seq) {
                    return CV_SEQ_ELTYPE(seq) == CV_32SC2 || CV_SEQ_ELTYPE(seq) == CV_32FC2;
                }

/* 2850 */      public static boolean CV_IS_SEQ_POINT_SUBSET(CvSeq seq) {
                    return CV_IS_SEQ_INDEX(seq) || CV_SEQ_ELTYPE(seq) == 7;
                }

/* 2853 */      public static boolean CV_IS_SEQ_POLYLINE(CvSeq seq) {
                    return CV_SEQ_KIND(seq) == 4096 && CV_IS_SEQ_POINT_SET(seq);
                }

/* 2856 */      public static boolean CV_IS_SEQ_POLYGON(CvSeq seq) {
                    return CV_IS_SEQ_POLYLINE(seq) && CV_IS_SEQ_CLOSED(seq);
                }

/* 2859 */      public static boolean CV_IS_SEQ_CHAIN(CvSeq seq) {
                    return CV_SEQ_KIND(seq) == 4096 && seq.elem_size() == 1;
                }

/* 2862 */      public static boolean CV_IS_SEQ_CONTOUR(CvSeq seq) {
                    return CV_IS_SEQ_CLOSED(seq) && (CV_IS_SEQ_POLYLINE(seq) || CV_IS_SEQ_CHAIN(seq));
                }

/* 2865 */      public static boolean CV_IS_SEQ_CHAIN_CONTOUR(CvSeq seq) {
                    return CV_IS_SEQ_CHAIN(seq) && CV_IS_SEQ_CLOSED(seq);
                }

/* 2868 */      public static boolean CV_IS_SEQ_POLYGON_TREE(CvSeq seq) {
                    return CV_SEQ_ELTYPE(seq) == 0 && CV_SEQ_KIND(seq) == 8192;
                }

/* 2872 */      public static boolean CV_IS_GRAPH(CvSeq seq) {
                    return CV_IS_SET(seq) && CV_SEQ_KIND(seq) == 4096;
                }

/* 2875 */      public static boolean CV_IS_GRAPH_ORIENTED(CvSeq seq) {
                    return (seq.flags() & 16384) != 0;
                }

/* 2878 */      public static boolean CV_IS_SUBDIV2D(CvSeq seq) {
                    return CV_IS_SET(seq) && CV_SEQ_KIND(seq) == 8192;
                }

/* 2881 */      public static class CvSeqWriter extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native CvSeqBlock block();

                    public native CvSeqWriter block(CvSeqBlock cvSeqBlock);

                    @Cast({"schar*"})
                    public native BytePointer block_max();

                    public native CvSeqWriter block_max(BytePointer bytePointer);

                    @Cast({"schar*"})
                    public native BytePointer block_min();

                    public native CvSeqWriter block_min(BytePointer bytePointer);

                    public native int header_size();

                    public native CvSeqWriter header_size(int i);

                    @Cast({"schar*"})
                    public native BytePointer ptr();

                    public native CvSeqWriter ptr(BytePointer bytePointer);

                    public native CvSeq seq();

                    public native CvSeqWriter seq(CvSeq cvSeq);


                    static {
/* 2884 */              Loader.load();
                    }

/* 2884 */          public CvSeqWriter() {
                        allocate();
                    }

/* 2885 */          public CvSeqWriter(int size) {
                        allocateArray(size);
                    }

/* 2886 */          public CvSeqWriter(Pointer p) {
/* 2887 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 2891 */          public CvSeqWriter position(int position) {
/* 2892 */              return (CvSeqWriter) super.position(position);
                    }
                }

/* 2904 */      public static class CvSeqReader extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native CvSeqBlock block();

                    public native CvSeqReader block(CvSeqBlock cvSeqBlock);

                    @Cast({"schar*"})
                    public native BytePointer block_max();

                    public native CvSeqReader block_max(BytePointer bytePointer);

                    @Cast({"schar*"})
                    public native BytePointer block_min();

                    public native CvSeqReader block_min(BytePointer bytePointer);

                    public native int delta_index();

                    public native CvSeqReader delta_index(int i);

                    public native int header_size();

                    public native CvSeqReader header_size(int i);

                    @Cast({"schar*"})
                    public native BytePointer prev_elem();

                    public native CvSeqReader prev_elem(BytePointer bytePointer);

                    @Cast({"schar*"})
                    public native BytePointer ptr();

                    public native CvSeqReader ptr(BytePointer bytePointer);

                    public native CvSeq seq();

                    public native CvSeqReader seq(CvSeq cvSeq);


                    static {
/* 2907 */              Loader.load();
                    }

/* 2907 */          public CvSeqReader() {
                        allocate();
                    }

/* 2908 */          public CvSeqReader(int size) {
                        allocateArray(size);
                    }

/* 2909 */          public CvSeqReader(Pointer p) {
/* 2910 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 2914 */          public CvSeqReader position(int position) {
/* 2915 */              return (CvSeqReader) super.position(position);
                    }
                }

/* 2938 */      public static CvPoint CV_CURRENT_POINT(CvSeqReader reader) {
/* 2939 */          return new CvPoint(reader.ptr());
                }

/* 2939 */      public static CvPoint CV_PREV_POINT(CvSeqReader reader) {
/* 2940 */          return new CvPoint(reader.prev_elem());
                }

/* 2941 */      public static void CV_READ_EDGE(CvPoint pt1, CvPoint pt2, CvSeqReader reader) {
/* 2942 */          if (!$assertionsDisabled && reader.seq().elem_size() != Loader.sizeof(CvPoint.class)) {
                        throw new AssertionError();
                    }
/* 2943 */          CV_PREV_POINT(reader);
/* 2944 */          CV_CURRENT_POINT(reader);
/* 2945 */          reader.prev_elem(reader.ptr());
/* 2946 */          CV_NEXT_SEQ_ELEM(Loader.sizeof(CvPoint.class), reader);
                }

/* 2948 */      public static CvGraphEdge CV_NEXT_GRAPH_EDGE(CvGraphEdge edge, CvGraphVtx vertex) {
/* 2949 */          if ($assertionsDisabled || edge.vtx(0).equals(vertex) || edge.vtx(1).equals(vertex)) {
                        return edge.next(edge.vtx(1).equals(vertex) ? 1 : 0);
                    }
                    throw new AssertionError();
                }

                @Opaque
/* 2953 */      public static class CvFileStorage extends Pointer {
                    static {
/* 2955 */              Loader.load();
                    }

/* 2955 */          public CvFileStorage() {
                    }

/* 2956 */          public CvFileStorage(Pointer p) {
/* 2957 */              super(p);
                    }

/* 2959 */          public static CvFileStorage open(String filename, CvMemStorage memstorage, int flags) {
/* 2960 */              return open(filename, memstorage, flags, null);
                    }

/* 2962 */          public static CvFileStorage open(String filename, CvMemStorage memstorage, int flags, String encoding) {
/* 2963 */              CvFileStorage f = opencv_core.cvOpenFileStorage(filename, memstorage, flags, encoding);
/* 2964 */              if (f != null) {
/* 2965 */                  f.deallocator(new ReleaseDeallocator(f));
                        }
/* 2967 */              return f;
                    }

/* 2970 */          public void release() {
/* 2971 */              deallocate();
                    }

/* 2972 */          protected static class ReleaseDeallocator extends CvFileStorage implements Pointer.Deallocator {
/* 2973 */              ReleaseDeallocator(CvFileStorage p) {
/* 2974 */                  super(p);
                        }

                        @Override // com.googlecode.javacpp.Pointer, com.googlecode.javacpp.Pointer.Deallocator
/* 2974 */              public void deallocate() {
/* 2975 */                  opencv_core.cvReleaseFileStorage(this);
                        }
                    }
                }

/* 2989 */      public static class CvAttrList extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    @Cast({"const char**"})
                    public native PointerPointer attr();

                    public native CvAttrList attr(PointerPointer pointerPointer);

                    public native CvAttrList next();

                    public native CvAttrList next(CvAttrList cvAttrList);


                    static {
/* 2992 */              Loader.load();
                    }

/* 2992 */          public CvAttrList() {
                        allocate();
                    }

/* 2993 */          public CvAttrList(int size) {
                        allocateArray(size);
                    }

/* 2994 */          public CvAttrList(Pointer p) {
/* 2995 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 2999 */          public CvAttrList position(int position) {
/* 3000 */              return (CvAttrList) super.position(position);
                    }
                }

/* 3009 */      public static CvAttrList cvAttrList(PointerPointer attr, CvAttrList next) {
/* 3010 */          return new CvAttrList().attr(attr).next(next);
                }

/* 3012 */      public static CvAttrList cvAttrList() {
/* 3013 */          return new CvAttrList();
                }

/* 3034 */      public static int CV_NODE_TYPE(int flags) {
/* 3035 */          return flags & 7;
                }

/* 3035 */      public static boolean CV_NODE_IS_INT(int flags) {
                    return CV_NODE_TYPE(flags) == 1;
                }

/* 3036 */      public static boolean CV_NODE_IS_REAL(int flags) {
                    return CV_NODE_TYPE(flags) == 2;
                }

/* 3037 */      public static boolean CV_NODE_IS_STRING(int flags) {
                    return CV_NODE_TYPE(flags) == 3;
                }

/* 3038 */      public static boolean CV_NODE_IS_SEQ(int flags) {
                    return CV_NODE_TYPE(flags) == 5;
                }

/* 3039 */      public static boolean CV_NODE_IS_MAP(int flags) {
                    return CV_NODE_TYPE(flags) == 6;
                }

/* 3040 */      public static boolean CV_NODE_IS_COLLECTION(int flags) {
                    return CV_NODE_TYPE(flags) >= 5;
                }

/* 3041 */      public static boolean CV_NODE_IS_FLOW(int flags) {
                    return (flags & 8) != 0;
                }

/* 3042 */      public static boolean CV_NODE_IS_EMPTY(int flags) {
                    return (flags & 32) != 0;
                }

/* 3043 */      public static boolean CV_NODE_IS_USER(int flags) {
                    return (flags & 16) != 0;
                }

/* 3044 */      public static boolean CV_NODE_HAS_NAME(int flags) {
                    return (flags & 64) != 0;
                }

/* 3045 */      public static boolean CV_NODE_SEQ_IS_SIMPLE(CvSeq seq) {
                    return (seq.flags() & 256) != 0;
                }

/* 3046 */      public static class CvString extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native int len();

                    public native CvString len(int i);

                    @Cast({"char*"})
                    public native BytePointer ptr();

                    public native CvString ptr(BytePointer bytePointer);


                    static {
/* 3049 */              Loader.load();
                    }

/* 3049 */          public CvString() {
                        allocate();
                    }

/* 3050 */          public CvString(int size) {
                        allocateArray(size);
                    }

/* 3051 */          public CvString(Pointer p) {
/* 3052 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 3056 */          public CvString position(int position) {
/* 3057 */              return (CvString) super.position(position);
                    }
                }

/* 3063 */      public static class CvStringHashNode extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native int hashval();

                    public native CvStringHashNode hashval(int i);

                    public native CvStringHashNode next();

                    public native CvStringHashNode next(CvStringHashNode cvStringHashNode);

                    @ByRef
                    public native CvString str();

                    public native CvStringHashNode str(CvString cvString);


                    static {
/* 3066 */              Loader.load();
                    }

/* 3066 */          public CvStringHashNode() {
                        allocate();
                    }

/* 3067 */          public CvStringHashNode(int size) {
                        allocateArray(size);
                    }

/* 3068 */          public CvStringHashNode(Pointer p) {
/* 3069 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 3073 */          public CvStringHashNode position(int position) {
/* 3074 */              return (CvStringHashNode) super.position(position);
                    }
                }

                @Opaque
/* 3081 */      public static class CvFileNodeHash extends Pointer {
                    static {
/* 3083 */              Loader.load();
                    }

/* 3083 */          public CvFileNodeHash() {
                    }

/* 3084 */          public CvFileNodeHash(Pointer p) {
/* 3085 */              super(p);
                    }
                }

/* 3086 */      public static class CvFileNode extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    @Name({"data.f"})
                    public native double data_f();

                    public native CvFileNode data_f(double d);

                    @Name({"data.i"})
                    public native int data_i();

                    public native CvFileNode data_i(int i);

                    public native CvFileNode data_map(CvFileNodeHash cvFileNodeHash);

                    @Name({"data.map"})
                    public native CvFileNodeHash data_map();

                    public native CvFileNode data_seq(CvSeq cvSeq);

                    @Name({"data.seq"})
                    public native CvSeq data_seq();

                    public native CvFileNode data_str(CvString cvString);

                    @Name({"data.str"})
                    @ByRef
                    public native CvString data_str();

                    public native CvFileNode info(CvTypeInfo cvTypeInfo);

                    public native CvTypeInfo info();

                    public native int tag();

                    public native CvFileNode tag(int i);


                    static {
/* 3089 */              Loader.load();
                    }

/* 3089 */          public CvFileNode() {
                        allocate();
                    }

/* 3090 */          public CvFileNode(int size) {
                        allocateArray(size);
                    }

/* 3091 */          public CvFileNode(Pointer p) {
/* 3092 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 3096 */          public CvFileNode position(int position) {
/* 3097 */              return (CvFileNode) super.position(position);
                    }
                }

/* 3111 */      public static class CvIsInstanceFunc extends FunctionPointer {
                    private native void allocate();

                    public native int call(@Const Pointer pointer);

                    static {
/* 3113 */              Loader.load();
                    }

/* 3113 */          public CvIsInstanceFunc(Pointer p) {
/* 3114 */              super(p);
                    }

/* 3114 */          protected CvIsInstanceFunc() {
                        allocate();
                    }
                }

/* 3118 */      public static class CvReleaseFunc extends FunctionPointer {
                    private native void allocate();

                    public native void call(PointerPointer pointerPointer);

                    static {
/* 3120 */              Loader.load();
                    }

/* 3120 */          public CvReleaseFunc(Pointer p) {
/* 3121 */              super(p);
                    }

/* 3121 */          protected CvReleaseFunc() {
                        allocate();
                    }
                }

/* 3125 */      public static class CvReadFunc extends FunctionPointer {
                    private native void allocate();

                    public native Pointer call(CvFileStorage cvFileStorage, CvFileNode cvFileNode);

                    static {
/* 3127 */              Loader.load();
                    }

/* 3127 */          public CvReadFunc(Pointer p) {
/* 3128 */              super(p);
                    }

/* 3128 */          protected CvReadFunc() {
                        allocate();
                    }
                }

/* 3132 */      public static class CvWriteFunc extends FunctionPointer {
                    private native void allocate();

                    public native void call(CvFileStorage cvFileStorage, String str, @Const Pointer pointer, @ByVal CvAttrList cvAttrList);

                    static {
/* 3134 */              Loader.load();
                    }

/* 3134 */          public CvWriteFunc(Pointer p) {
/* 3135 */              super(p);
                    }

/* 3135 */          protected CvWriteFunc() {
                        allocate();
                    }
                }

/* 3140 */      public static class CvCloneFunc extends FunctionPointer {
                    private native void allocate();

                    public native Pointer call(@Const Pointer pointer);

                    static {
/* 3142 */              Loader.load();
                    }

/* 3142 */          public CvCloneFunc(Pointer p) {
/* 3143 */              super(p);
                    }

/* 3143 */          protected CvCloneFunc() {
                        allocate();
                    }
                }

/* 3146 */      public static class CvTypeInfo extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native CvCloneFunc clone();

                    public native CvTypeInfo clone(CvCloneFunc cvCloneFunc);

                    public native int flags();

                    public native CvTypeInfo flags(int i);

                    public native int header_size();

                    public native CvTypeInfo header_size(int i);

                    public native CvIsInstanceFunc is_instance();

                    public native CvTypeInfo is_instance(CvIsInstanceFunc cvIsInstanceFunc);

                    public native CvTypeInfo next();

                    public native CvTypeInfo next(CvTypeInfo cvTypeInfo);

                    public native CvTypeInfo prev();

                    public native CvTypeInfo prev(CvTypeInfo cvTypeInfo);

                    public native CvReadFunc read();

                    public native CvTypeInfo read(CvReadFunc cvReadFunc);

                    public native CvReleaseFunc release();

                    public native CvTypeInfo release(CvReleaseFunc cvReleaseFunc);

                    @Cast({"const char*"})
                    public native BytePointer type_name();

                    public native CvTypeInfo type_name(BytePointer bytePointer);

                    public native CvTypeInfo write(CvWriteFunc cvWriteFunc);

                    public native CvWriteFunc write();

                    /* JADX INFO: renamed from: clone, reason: collision with other method in class */


                    static {
/* 3149 */              Loader.load();
                    }

/* 3149 */          public CvTypeInfo() {
                        allocate();
                    }

/* 3150 */          public CvTypeInfo(int size) {
                        allocateArray(size);
                    }

/* 3151 */          public CvTypeInfo(Pointer p) {
/* 3152 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 3156 */          public CvTypeInfo position(int position) {
/* 3157 */              return (CvTypeInfo) super.position(position);
                    }
                }

/* 3174 */      public static class CvPluginFuncInfo extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native Pointer default_func_addr();

                    public native CvPluginFuncInfo default_func_addr(Pointer pointer);

                    public native PointerPointer func_addr();

                    public native CvPluginFuncInfo func_addr(PointerPointer pointerPointer);

                    @Cast({"const char*"})
                    public native BytePointer func_names();

                    public native CvPluginFuncInfo func_names(BytePointer bytePointer);

                    public native int loaded_from();

                    public native CvPluginFuncInfo loaded_from(int i);

                    public native int search_modules();

                    public native CvPluginFuncInfo search_modules(int i);


                    static {
/* 3177 */              Loader.load();
                    }

/* 3177 */          public CvPluginFuncInfo() {
                        allocate();
                    }

/* 3178 */          public CvPluginFuncInfo(int size) {
                        allocateArray(size);
                    }

/* 3179 */          public CvPluginFuncInfo(Pointer p) {
/* 3180 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 3184 */          public CvPluginFuncInfo position(int position) {
/* 3185 */              return (CvPluginFuncInfo) super.position(position);
                    }
                }

/* 3194 */      public static class CvModuleInfo extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native CvModuleInfo func_tab(CvPluginFuncInfo cvPluginFuncInfo);

                    public native CvPluginFuncInfo func_tab();

                    @Cast({"const char*"})
                    public native BytePointer name();

                    public native CvModuleInfo name(BytePointer bytePointer);

                    public native CvModuleInfo next();

                    public native CvModuleInfo next(CvModuleInfo cvModuleInfo);

                    @Cast({"const char*"})
                    public native BytePointer version();

                    public native CvModuleInfo version(BytePointer bytePointer);


                    static {
/* 3197 */              Loader.load();
                    }

/* 3197 */          public CvModuleInfo() {
                        allocate();
                    }

/* 3198 */          public CvModuleInfo(int size) {
                        allocateArray(size);
                    }

/* 3199 */          public CvModuleInfo(Pointer p) {
/* 3200 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 3204 */          public CvModuleInfo position(int position) {
/* 3205 */              return (CvModuleInfo) super.position(position);
                    }
                }

/* 3219 */      public static void cvFree(Pointer ptr) {
/* 3220 */          cvFree_(ptr);
/* 3221 */          ptr.setNull();
                }

/* 3247 */      public static CvMat cvGetSubArr(CvArr arr, CvMat submat, @ByVal CvRect rect) {
/* 3248 */          return cvGetSubRect(arr, submat, rect);
                }

/* 3252 */      public static CvMat cvGetRow(CvArr arr, CvMat submat, int row) {
/* 3253 */          return cvGetRows(arr, submat, row, row + 1, 1);
                }

/* 3257 */      public static CvMat cvGetCol(CvArr arr, CvMat submat, int col) {
/* 3258 */          return cvGetCols(arr, submat, col, col + 1);
                }

/* 3280 */      public static class CvNArrayIterator extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native int count();

                    public native CvNArrayIterator count(int i);

                    public native int dims();

                    public native CvNArrayIterator dims(int i);

                    public native CvMatND hdr(int i);

                    public native CvNArrayIterator hdr(int i, CvMatND cvMatND);

                    @Cast({"uchar*"})
                    public native BytePointer ptr(int i);

                    public native CvNArrayIterator ptr(int i, BytePointer bytePointer);

                    public native CvNArrayIterator size(CvSize cvSize);

                    @ByRef
                    public native CvSize size();

                    public native int stack(int i);

                    public native CvNArrayIterator stack(int i, int i2);


                    static {
/* 3283 */              Loader.load();
                    }

/* 3283 */          public CvNArrayIterator() {
                        allocate();
                    }

/* 3284 */          public CvNArrayIterator(int size) {
                        allocateArray(size);
                    }

/* 3285 */          public CvNArrayIterator(Pointer p) {
/* 3286 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 3290 */          public CvNArrayIterator position(int position) {
/* 3291 */              return (CvNArrayIterator) super.position(position);
                    }
                }

/* 3310 */      public static int cvInitNArrayIterator(int count, CvArr[] arrs, CvArr mask, CvMatND stubs, CvNArrayIterator array_iterator, int flags) {
/* 3311 */          return cvInitNArrayIterator(count, new CvArrArray(arrs), mask, stubs, array_iterator, flags);
                }

/* 3349 */      public static CvArr cvReshapeND(CvArr arr, CvArr header, int new_cn, int new_dims, int[] new_sizes) {
/* 3350 */          return cvReshapeMatND(arr, Loader.sizeof(header.getClass()), header, new_cn, new_dims, new_sizes);
                }

/* 3365 */      public static void cvCopy(CvArr src, CvArr dst) {
/* 3366 */          cvCopy(src, dst, null);
                }

/* 3369 */      public static void cvSet(CvArr arr, CvScalar value) {
/* 3370 */          cvSet(arr, value, null);
                }

/* 3373 */      public static void cvZero(CvArr arr) {
/* 3374 */          cvSetZero(arr);
                }

/* 3380 */      public static void cvMixChannels(CvArr[] src, int src_count, CvArr[] dst, int dst_count, int[] from_to, int pair_count) {
/* 3381 */          cvMixChannels(new CvArrArray(src), src_count, new CvArrArray(dst), dst_count, from_to, pair_count);
                }

/* 3387 */      public static void cvCvtScale(CvArr src, CvArr dst, double scale, double shift) {
/* 3388 */          cvConvertScale(src, dst, scale, shift);
                }

/* 3390 */      public static void cvScale(CvArr src, CvArr dst, double scale, double shift) {
/* 3391 */          cvConvertScale(src, dst, scale, shift);
                }

/* 3393 */      public static void cvConvert(CvArr src, CvArr dst) {
/* 3394 */          cvConvertScale(src, dst, 1.0d, 0.0d);
                }

/* 3397 */      public static void cvCvtScaleAbs(CvArr src, CvArr dst, double scale, double shift) {
/* 3398 */          cvConvertScaleAbs(src, dst, scale, shift);
                }

/* 3407 */      public static void cvSubS(CvArr src, CvScalar value, CvArr dst, CvArr mask) {
/* 3408 */          cvAddS(src, cvScalar(-value.val(0), -value.val(1), -value.val(2), -value.val(3)), dst, mask);
                }

/* 3414 */      public static void cvAXPY(CvArr A, double real_scalar, CvArr B, CvArr C) {
/* 3415 */          cvScaleAdd(A, cvRealScalar(real_scalar), B, C);
                }

/* 3445 */      public static void cvAbs(CvArr src, CvArr dst) {
/* 3446 */          cvAbsDiffS(src, dst, cvScalarAll(0.0d));
                }

/* 3464 */      public static int cvCheckArray(CvArr arr, int flags, double min_val, double max_val) {
/* 3465 */          return cvCheckArr(arr, flags, min_val, max_val);
                }

/* 3487 */      public static void cvMatMulAdd(CvArr src1, CvArr src2, CvArr src3, CvArr dst) {
/* 3488 */          cvGEMM(src1, src2, 1.0d, src3, 1.0d, dst, 0);
                }

/* 3490 */      public static void cvMatMul(CvArr src1, CvArr src2, CvArr dst) {
/* 3491 */          cvMatMulAdd(src1, src2, null, dst);
                }

/* 3500 */      public static void cvMatMulAddEx(CvArr srcA, CvArr srcB, double alpha, CvArr srcC, double beta, CvArr dst, int tABC) {
/* 3501 */          cvGEMM(srcA, srcB, alpha, srcC, beta, dst, tABC);
                }

/* 3504 */      public static void cvMatMulAddS(CvArr src, CvArr dst, CvMat transmat, CvMat shiftvec) {
/* 3505 */          cvTransform(src, dst, transmat, shiftvec);
                }

/* 3511 */      public static void cvT(CvArr src, CvArr dst) {
/* 3512 */          cvTranspose(src, dst);
                }

/* 3516 */      public static void cvMirror(CvArr src, CvArr dst, int flip_mode) {
/* 3517 */          cvFlip(src, dst, flip_mode);
                }

/* 3536 */      public static double cvInvert(CvArr src, CvArr dst) {
/* 3537 */          return cvInvert(src, dst, 0);
                }

/* 3539 */      public static double cvInv(CvArr src, CvArr dst, int method) {
/* 3540 */          return cvInvert(src, dst, method);
                }

/* 3542 */      public static double cvInv(CvArr src, CvArr dst) {
/* 3543 */          return cvInvert(src, dst, 0);
                }

/* 3546 */      public static int cvSolve(CvArr A, CvArr B, CvArr X) {
/* 3547 */          return cvSolve(A, B, X, 0);
                }

/* 3557 */      public static void cvSetIdentity(CvArr mat, double value) {
/* 3558 */          cvSetIdentity(mat, cvRealScalar(value));
                }

/* 3560 */      public static void cvSetIdentity(CvArr mat) {
/* 3561 */          cvSetIdentity(mat, 1.0d);
                }

/* 3572 */      public static void cvCalcCovarMatrix(CvArr[] vects, int count, CvArr cov_mat, CvArr avg, int flags) {
/* 3573 */          cvCalcCovarMatrix(new CvArrArray(vects), count, cov_mat, avg, flags);
                }

/* 3587 */      public static double cvMahalonobis(CvArr vec1, CvArr vec2, CvArr mat) {
/* 3588 */          return cvMahalanobis(vec1, vec2, mat);
                }

/* 3600 */      public static void cvMinMaxLoc(CvArr arr, double[] min_val, double[] max_val) {
/* 3601 */          cvMinMaxLoc(arr, min_val, max_val, (CvPoint) null, (CvPoint) null, (CvArr) null);
                }

/* 3622 */      public static double cvNorm(CvArr arr1, CvArr arr2, int norm_type) {
/* 3623 */          return cvNorm(arr1, arr2, norm_type, null);
                }

/* 3625 */      public static double cvNorm(CvArr arr1, CvArr arr2) {
/* 3626 */          return cvNorm(arr1, arr2, 4, null);
                }

/* 3628 */      public static double cvNorm(CvArr arr1) {
/* 3629 */          return cvNorm(arr1, null, 4, null);
                }

/* 3634 */      public static void cvNormalize(CvArr src, CvArr dst) {
/* 3635 */          cvNormalize(src, dst, 1.0d, 0.0d, 4, null);
                }

/* 3656 */      public static void cvFFT(CvArr src, CvArr dst, int flags, int nonzero_rows) {
/* 3657 */          cvDFT(src, dst, flags, nonzero_rows);
                }

/* 3706 */      public static Pointer cvCvtSeqToArray(CvSeq seq, Pointer elements) {
/* 3707 */          return cvCvtSeqToArray(seq, elements, CV_WHOLE_SEQ);
                }

/* 3710 */      public static Pointer cvCvtSeqToArray(CvSeq seq, Buffer elements) {
/* 3711 */          return cvCvtSeqToArray(seq, elements, CV_WHOLE_SEQ);
                }

/* 3718 */      public static CvSeq cvCloneSeq(CvSeq seq, CvMemStorage storage) {
/* 3719 */          return cvSeqSlice(seq, CV_WHOLE_SEQ, storage, 1);
                }

/* 3722 */      public static class CvCmpFunc extends FunctionPointer {
                    private native void allocate();

                    public native int call(@Const Pointer pointer, @Const Pointer pointer2, Pointer pointer3);

                    static {
/* 3724 */              Loader.load();
                    }

/* 3724 */          public CvCmpFunc(Pointer p) {
/* 3725 */              super(p);
                    }

/* 3725 */          protected CvCmpFunc() {
                        allocate();
                    }
                }

/* 3744 */      public CvSetElem cvSetNew(CvSet set_header) {
/* 3745 */          CvSetElem elem = set_header.free_elems();
/* 3746 */          if (elem != null) {
/* 3747 */              set_header.free_elems(elem.next_free());
/* 3748 */              elem.flags(elem.flags() & CV_SET_ELEM_IDX_MASK);
/* 3749 */              set_header.active_count(set_header.active_count() + 1);
                    } else {
/* 3751 */              cvSetAdd(set_header, null, elem);
                    }
/* 3753 */          return elem;
                }

/* 3755 */      public void cvSetRemoveByPtr(CvSet set_header, CvSetElem elem) {
/* 3756 */          if (!$assertionsDisabled && elem.flags() < 0) {
                        throw new AssertionError();
                    }
/* 3757 */          elem.next_free(set_header.free_elems());
/* 3758 */          elem.flags((elem.flags() & CV_SET_ELEM_IDX_MASK) | 128);
/* 3759 */          set_header.free_elems(elem);
/* 3760 */          set_header.active_count(set_header.active_count() - 1);
                }

/* 3763 */      public static CvSetElem cvGetSetElem(CvSet set_header, int index) {
/* 3764 */          CvSetElem elem = new CvSetElem(cvGetSeqElem(set_header, index));
/* 3765 */          if (elem == null || !CV_IS_SET_ELEM(elem)) {
                        return null;
                    }
                    return elem;
                }

/* 3784 */      public static CvGraphEdge cvGraphFindEdge(CvGraph graph, int start_idx, int end_idx) {
/* 3785 */          return cvFindGraphEdge(graph, start_idx, end_idx);
                }

/* 3787 */      public static CvGraphEdge cvGraphFindEdgeByPtr(CvGraph graph, CvGraphVtx start_vtx, CvGraphVtx end_vtx) {
/* 3788 */          return cvFindGraphEdgeByPtr(graph, start_vtx, end_vtx);
                }

/* 3793 */      public static CvGraphVtx cvGetGraphVtx(CvGraph graph, int idx) {
/* 3794 */          return new CvGraphVtx(cvGetSetElem(graph, idx));
                }

/* 3794 */      public static int cvGraphVtxIdx(CvGraph graph, CvGraphVtx vtx) {
/* 3795 */          return vtx.flags() & CV_SET_ELEM_IDX_MASK;
                }

/* 3795 */      public static int cvGraphEdgeIdx(CvGraph graph, CvGraphEdge edge) {
/* 3796 */          return edge.flags() & CV_SET_ELEM_IDX_MASK;
                }

/* 3796 */      public static int cvGraphGetVtxCount(CvGraph graph) {
/* 3797 */          return graph.active_count();
                }

/* 3797 */      public static int cvGraphGetEdgeCount(CvGraph graph) {
/* 3798 */          return graph.edges().active_count();
                }

/* 3817 */      public static boolean CV_IS_GRAPH_EDGE_VISITED(CvGraphVtx vtx) {
                    return (vtx.flags() & 1073741824) != 0;
                }

/* 3820 */      public static boolean CV_IS_GRAPH_VERTEX_VISITED(CvGraphEdge edge) {
                    return (edge.flags() & 1073741824) != 0;
                }

/* 3822 */      public static class CvGraphScanner extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native CvGraphScanner dst(CvGraphVtx cvGraphVtx);

                    public native CvGraphVtx dst();

                    public native CvGraphEdge edge();

                    public native CvGraphScanner edge(CvGraphEdge cvGraphEdge);

                    public native CvGraph graph();

                    public native CvGraphScanner graph(CvGraph cvGraph);

                    public native int index();

                    public native CvGraphScanner index(int i);

                    public native int mask();

                    public native CvGraphScanner mask(int i);

                    public native CvGraphScanner stack(CvSeq cvSeq);

                    public native CvSeq stack();

                    public native CvGraphScanner vtx(CvGraphVtx cvGraphVtx);

                    public native CvGraphVtx vtx();


                    static {
/* 3825 */              Loader.load();
                    }

/* 3825 */          public CvGraphScanner() {
                        allocate();
                        zero();
                    }

/* 3826 */          public CvGraphScanner(int size) {
                        allocateArray(size);
                        zero();
                    }

/* 3827 */          public CvGraphScanner(Pointer p) {
/* 3828 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 3832 */          public CvGraphScanner position(int position) {
/* 3833 */              return (CvGraphScanner) super.position(position);
                    }

/* 3837 */          public static CvGraphScanner create(CvGraph graph, CvGraphVtx vtx, int mask) {
/* 3838 */              CvGraphScanner g = opencv_core.cvCreateGraphScanner(graph, vtx, mask);
/* 3839 */              if (g != null) {
/* 3840 */                  g.deallocator(new ReleaseDeallocator(g));
                        }
/* 3842 */              return g;
                    }

/* 3844 */          public void release() {
/* 3845 */              deallocate();
                    }

/* 3845 */          protected static class ReleaseDeallocator extends CvGraphScanner implements Pointer.Deallocator {

/* 3847 */              ReleaseDeallocator(CvGraphScanner p) {
/* 3848 */                  super(p);
                        }

                        @Override // com.googlecode.javacpp.Pointer, com.googlecode.javacpp.Pointer.Deallocator
/* 3848 */              public void deallocate() {
/* 3849 */                  opencv_core.cvReleaseGraphScanner(this);
                        }
                    }
                }

/* 3869 */      public static CvScalar CV_RGB(double r, double g, double b) {
/* 3870 */          return cvScalar(b, g, r, 0.0d);
                }

/* 3887 */      public static void cvEllipseBox(CvArr img, @ByVal CvBox2D box, @ByVal CvScalar color, int thickness, int line_type, int shift) {
/* 3888 */          CvSize2D32f size = box.size();
/* 3889 */          CvSize axes = cvSize((int) Math.round(((double) size.width()) * 0.5d), (int) Math.round(((double) size.height()) * 0.5d));
/* 3890 */          cvEllipse(img, cvPointFrom32f(box.center()), axes, box.angle(), 0.0d, 360.0d, color, thickness, line_type, shift);
                }

/* 3898 */      public static void cvFillPoly(CvArr img, CvPoint[] pts, int[] npts, int contours, @ByVal CvScalar color, int line_type, int shift) {
/* 3899 */          cvFillPoly(img, new PointerPointer(pts), npts, contours, color, line_type, shift);
                }

/* 3907 */      public static void cvPolyLine(CvArr img, CvPoint[] pts, int[] npts, int contours, int is_closed, @ByVal CvScalar color, int thickness, int line_type, int shift) {
/* 3908 */          cvPolyLine(img, new PointerPointer(pts), npts, contours, is_closed, color, thickness, line_type, shift);
                }

/* 3918 */      public static void cvDrawRect(CvArr img, CvPoint pt1, CvPoint pt2, CvScalar color, int thickness, int line_type, int shift) {
/* 3919 */          cvRectangle(img, pt1, pt2, color, thickness, line_type, shift);
                }

/* 3922 */      public static void cvDrawLine(CvArr img, CvPoint pt1, CvPoint pt2, CvScalar color, int thickness, int line_type, int shift) {
/* 3923 */          cvLine(img, pt1, pt2, color, thickness, line_type, shift);
                }

/* 3926 */      public static void cvDrawCircle(CvArr img, CvPoint center, int radius, CvScalar color, int thickness, int line_type, int shift) {
/* 3927 */          cvCircle(img, center, radius, color, thickness, line_type, shift);
                }

/* 3931 */      public static void cvDrawEllipse(CvArr img, CvPoint center, CvSize axes, double angle, double start_angle, double end_angle, CvScalar color, int thickness, int line_type, int shift) {
/* 3932 */          cvEllipse(img, center, axes, angle, start_angle, end_angle, color, thickness, line_type, shift);
                }

/* 3937 */      public static void cvDrawPolyLine(CvArr img, CvPoint[] pts, int[] npts, int contours, int is_closed, CvScalar color, int thickness, int line_type, int shift) {
/* 3938 */          cvPolyLine(img, pts, npts, contours, is_closed, color, thickness, line_type, shift);
                }

/* 3942 */      public static void cvDrawPolyLine(CvArr img, @Cast({"CvPoint**"}) PointerPointer pts, int[] npts, int contours, int is_closed, CvScalar color, int thickness, int line_type, int shift) {
/* 3943 */          cvPolyLine(img, pts, npts, contours, is_closed, color, thickness, line_type, shift);
                }

/* 3947 */      public static void cvDrawPolyLine(CvArr img, @ByPtrPtr CvPoint pts, int[] npts, int contours, int is_closed, CvScalar color, int thickness, int line_type, int shift) {
/* 3948 */          cvPolyLine(img, pts, npts, contours, is_closed, color, thickness, line_type, shift);
                }

/* 3968 */      public static class CvFont extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    @Const
                    public native IntPointer ascii();

                    public native CvFont ascii(IntPointer intPointer);

                    public native CvFont color(CvScalar cvScalar);

                    @ByRef
                    public native CvScalar color();

                    @Const
                    public native IntPointer cyrillic();

                    public native CvFont cyrillic(IntPointer intPointer);

                    /* JADX INFO: renamed from: dx */
                    public native float m2427dx();

                    /* JADX INFO: renamed from: dx */
                    public native CvFont m2428dx(float f);

                    public native int font_face();

                    public native CvFont font_face(int i);

                    @Const
                    public native IntPointer greek();

                    public native CvFont greek(IntPointer intPointer);

                    public native float hscale();

                    public native CvFont hscale(float f);

                    public native int line_type();

                    public native CvFont line_type(int i);

                    @Cast({"const char*"})
                    public native BytePointer nameFont();

                    public native CvFont nameFont(BytePointer bytePointer);

                    public native float shear();

                    public native CvFont shear(float f);

                    public native int thickness();

                    public native CvFont thickness(int i);

                    public native float vscale();

                    public native CvFont vscale(float f);


                    static {
/* 3971 */              Loader.load();
                    }

/* 3971 */          public CvFont() {
                        allocate();
                    }

/* 3972 */          public CvFont(int size) {
                        allocateArray(size);
                    }

/* 3973 */          public CvFont(Pointer p) {
/* 3974 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 3978 */          public CvFont position(int position) {
/* 3979 */              return (CvFont) super.position(position);
                    }

/* 3982 */          public CvFont(int font_face, double hscale, double vscale, double shear, int thickness, int line_type) {
/* 3984 */              allocate();
/* 3985 */              opencv_core.cvInitFont(this, font_face, hscale, vscale, shear, thickness, line_type);
                    }

/* 3986 */          public CvFont(int font_face, double scale, int thickness) {
/* 3988 */              allocate();
/* 3989 */              opencv_core.cvInitFont(this, font_face, scale, scale, 0.0d, thickness, 16);
                    }
                }

/* 4008 */      public static CvFont cvFont(double scale, int thickness) {
/* 4009 */          CvFont font = new CvFont();
/* 4010 */          cvInitFont(font, 1, scale, scale, 0.0d, thickness, 16);
/* 4011 */          return font;
                }

/* 4027 */      public static void cvDrawContours(CvArr img, CvSeq contour, @ByVal CvScalar external_color, @ByVal CvScalar hole_color, int max_level, int thickness, int line_type) {
/* 4028 */          cvDrawContours(img, contour, external_color, hole_color, max_level, thickness, line_type, CvPoint.ZERO);
                }

/* 4031 */      public static class CvTreeNodeIterator extends Pointer {
                    private native void allocate();

                    private native void allocateArray(int i);

                    public native int level();

                    public native CvTreeNodeIterator level(int i);

                    public native int max_level();

                    public native CvTreeNodeIterator max_level(int i);

                    @Const
                    public native Pointer node();

                    public native CvTreeNodeIterator node(Pointer pointer);


                    static {
/* 4034 */              Loader.load();
                    }

/* 4034 */          public CvTreeNodeIterator() {
                        allocate();
                    }

/* 4035 */          public CvTreeNodeIterator(int size) {
                        allocateArray(size);
                    }

/* 4036 */          public CvTreeNodeIterator(Pointer p) {
/* 4037 */              super(p);
                    }

                    @Override // com.googlecode.javacpp.Pointer
/* 4041 */          public CvTreeNodeIterator position(int position) {
/* 4042 */              return (CvTreeNodeIterator) super.position(position);
                    }
                }

/* 4067 */      public static class CvAllocFunc extends FunctionPointer {
                    private native void allocate();

                    public native Pointer call(@Cast({"size_t"}) long j, Pointer pointer);

                    static {
/* 4069 */              Loader.load();
                    }

/* 4069 */          public CvAllocFunc(Pointer p) {
/* 4070 */              super(p);
                    }

/* 4070 */          protected CvAllocFunc() {
                        allocate();
                    }
                }

/* 4074 */      public static class CvFreeFunc extends FunctionPointer {
                    private native void allocate();

                    public native int call(Pointer pointer, Pointer pointer2);

                    static {
/* 4076 */              Loader.load();
                    }

/* 4076 */          public CvFreeFunc(Pointer p) {
/* 4077 */              super(p);
                    }

/* 4077 */          protected CvFreeFunc() {
                        allocate();
                    }
                }

                @Convention("CV_STDCALL")
/* 4086 */      public static class Cv_iplCreateImageHeader extends FunctionPointer {
                    private native void allocate();

                    public native IplImage call(int i, int i2, int i3, @Cast({"char*"}) BytePointer bytePointer, @Cast({"char*"}) BytePointer bytePointer2, int i4, int i5, int i6, int i7, int i8, IplROI iplROI, IplImage iplImage, Pointer pointer, IplTileInfo iplTileInfo);

                    static {
/* 4088 */              Loader.load();
                    }

/* 4088 */          public Cv_iplCreateImageHeader(Pointer p) {
/* 4089 */              super(p);
                    }

/* 4089 */          protected Cv_iplCreateImageHeader() {
                        allocate();
                    }
                }

                @Convention("CV_STDCALL")
/* 4096 */      public static class Cv_iplAllocateImageData extends FunctionPointer {
                    private native void allocate();

                    public native void call(IplImage iplImage, int i, int i2);

                    static {
/* 4098 */              Loader.load();
                    }

/* 4098 */          public Cv_iplAllocateImageData(Pointer p) {
/* 4099 */              super(p);
                    }

/* 4099 */          protected Cv_iplAllocateImageData() {
                        allocate();
                    }
                }

                @Convention("CV_STDCALL")
/* 4104 */      public static class Cv_iplDeallocate extends FunctionPointer {
                    private native void allocate();

                    public native void call(IplImage iplImage, int i);

                    static {
/* 4106 */              Loader.load();
                    }

/* 4106 */          public Cv_iplDeallocate(Pointer p) {
/* 4107 */              super(p);
                    }

/* 4107 */          protected Cv_iplDeallocate() {
                        allocate();
                    }
                }

                @Convention("CV_STDCALL")
/* 4112 */      public static class Cv_iplCreateROI extends FunctionPointer {
                    private native void allocate();

                    public native IplROI call(int i, int i2, int i3, int i4, int i5);

                    static {
/* 4114 */              Loader.load();
                    }

/* 4114 */          public Cv_iplCreateROI(Pointer p) {
/* 4115 */              super(p);
                    }

/* 4115 */          protected Cv_iplCreateROI() {
                        allocate();
                    }
                }

                @Convention("CV_STDCALL")
/* 4120 */      public static class Cv_iplCloneImage extends FunctionPointer {
                    private native void allocate();

                    public native IplImage call(@Const IplImage iplImage);

                    static {
/* 4122 */              Loader.load();
                    }

/* 4122 */          public Cv_iplCloneImage(Pointer p) {
/* 4123 */              super(p);
                    }

/* 4123 */          protected Cv_iplCloneImage() {
                        allocate();
                    }
                }

/* 4140 */      public static void cvStartWriteStruct(CvFileStorage fs, String name, int struct_flags, String type_name) {
/* 4141 */          cvStartWriteStruct(fs, name, struct_flags, type_name, CV_ATTR_LIST_EMPTY);
                }

/* 4150 */      public static void cvWrite(CvFileStorage fs, String name, Pointer ptr) {
/* 4151 */          cvWrite(fs, name, ptr, CV_ATTR_LIST_EMPTY);
                }

/* 4162 */      public static int cvReadInt(CvFileNode node) {
/* 4163 */          return cvReadInt(node, 0);
                }

/* 4165 */      public static int cvReadInt(CvFileNode node, int default_value) {
/* 4166 */          if (node == null) {
                        return default_value;
                    }
                    if (CV_NODE_IS_INT(node.tag())) {
                        int default_value2 = node.data_i();
                        return default_value2;
                    }
                    if (!CV_NODE_IS_REAL(node.tag())) {
                        return Integer.MAX_VALUE;
                    }
                    int default_value3 = (int) Math.round(node.data_f());
                    return default_value3;
                }

/* 4170 */      public static int cvReadIntByName(CvFileStorage fs, CvFileNode map, String name) {
/* 4171 */          return cvReadIntByName(fs, map, name, 0);
                }

/* 4173 */      public static int cvReadIntByName(CvFileStorage fs, CvFileNode map, String name, int default_value) {
/* 4174 */          return cvReadInt(cvGetFileNodeByName(fs, map, name), default_value);
                }

/* 4176 */      public static double cvReadReal(CvFileNode node) {
/* 4177 */          return cvReadReal(node, 0.0d);
                }

/* 4179 */      public static double cvReadReal(CvFileNode node, double default_value) {
/* 4180 */          if (node == null) {
                        return default_value;
                    }
                    if (CV_NODE_IS_INT(node.tag())) {
                        double default_value2 = node.data_i();
                        return default_value2;
                    }
                    if (!CV_NODE_IS_REAL(node.tag())) {
                        return 1.0E300d;
                    }
                    double default_value3 = node.data_f();
                    return default_value3;
                }

/* 4184 */      public static double cvReadRealByName(CvFileStorage fs, CvFileNode map, String name) {
/* 4185 */          return cvReadRealByName(fs, map, name, 0.0d);
                }

/* 4187 */      public static double cvReadRealByName(CvFileStorage fs, CvFileNode map, String name, double default_value) {
/* 4188 */          return cvReadReal(cvGetFileNodeByName(fs, map, name), default_value);
                }

/* 4190 */      public static String cvReadString(CvFileNode node) {
/* 4191 */          return cvReadString(node, null);
                }

/* 4193 */      public static String cvReadString(CvFileNode node, String default_value) {
/* 4194 */          if (node != null) {
/* 4196 */              if (CV_NODE_IS_STRING(node.tag())) {
/* 4197 */                  CvString str = node.data_str();
/* 4198 */                  BytePointer pointer = str.ptr();
/* 4199 */                  byte[] bytes = new byte[str.len()];
/* 4200 */                  pointer.get(bytes);
/* 4201 */                  String default_value2 = new String(bytes);
/* 4203 */                  return default_value2;
                        }
/* 4203 */              return null;
                    }
/* 4203 */          return default_value;
                }

/* 4206 */      public static String cvReadStringByName(CvFileStorage fs, CvFileNode map, String name) {
/* 4207 */          return cvReadStringByName(fs, map, name, null);
                }

/* 4209 */      public static String cvReadStringByName(CvFileStorage fs, CvFileNode map, String name, String default_value) {
/* 4210 */          return cvReadString(cvGetFileNodeByName(fs, map, name), default_value);
                }

/* 4212 */      public static Pointer cvRead(CvFileStorage fs, CvFileNode node) {
/* 4213 */          return cvRead(fs, node, CV_ATTR_LIST_EMPTY);
                }

/* 4216 */      public static Pointer cvReadByName(CvFileStorage fs, CvFileNode map, String name) {
/* 4217 */          return cvReadByName(fs, map, name, CV_ATTR_LIST_EMPTY);
                }

/* 4219 */      public static Pointer cvReadByName(CvFileStorage fs, CvFileNode map, String name, CvAttrList attributes) {
/* 4220 */          CvFileNode n = cvGetFileNodeByName(fs, map, name);
/* 4221 */          return cvRead(fs, n, attributes);
                }

/* 4240 */      public static void cvSave(String filename, Pointer struct_ptr) {
/* 4241 */          cvSave(filename, struct_ptr, null, null, CV_ATTR_LIST_EMPTY);
                }

/* 4245 */      public static Pointer cvLoad(String filename) {
/* 4246 */          return cvLoad(filename, null, null, null);
                }

/* 4290 */      public static class CvErrorCallback extends FunctionPointer {
                    private native void allocate();

                    public native int call(int i, String str, String str2, String str3, int i2, Pointer pointer);

                    static {
/* 4292 */              Loader.load();
                    }

/* 4292 */          public CvErrorCallback(Pointer p) {
/* 4293 */              super(p);
                    }

/* 4293 */          protected CvErrorCallback() {
                        allocate();
                    }
                }

                @Name({"std::vector<std::string>"})
/* 4327 */      public static class StringVector extends Pointer {
                    private native void allocate();

                    private native void allocate(@Cast({"size_t"}) long j);

                    @ByRef
                    @Index
                    public native String get(@Cast({"size_t"}) long j);

                    public native StringVector put(@Cast({"size_t"}) long j, String str);

                    public native void resize(@Cast({"size_t"}) long j);

                    public native long size();

                    static {
/* 4329 */              Loader.load();
                    }

/* 4329 */          public StringVector(String... array) {
/* 4330 */              this(array.length);
                        put(array);
                    }

/* 4330 */          public StringVector() {
                        allocate();
                    }

/* 4331 */          public StringVector(long n) {
                        allocate(n);
                    }

/* 4332 */          public StringVector(Pointer p) {
/* 4333 */              super(p);
                    }

/* 4343 */          public StringVector put(String... array) {
                        if (size() < array.length) {
                            resize(array.length);
                        }
                        for (int i = 0; i < array.length; i++) {
/* 4346 */                  put(i, array[i]);
                        }
/* 4348 */              return this;
                    }
                }

                @Name({"std::vector<cv::Mat>"})
/* 4352 */      public static class MatVector extends Pointer {
                    private native void allocate();

                    private native void allocate(@Cast({"size_t"}) long j);

                    @OutputMat
                    @ValueGetter
                    @Index
                    public native CvMat getCvMat(@Cast({"size_t"}) long j);

                    @OutputMat
                    @ValueGetter
                    @Index
                    public native CvMatND getCvMatND(@Cast({"size_t"}) long j);

                    @OutputMat
                    @ValueGetter
                    @Index
                    public native IplImage getIplImage(@Cast({"size_t"}) long j);

                    @ValueSetter
                    @Index
                    public native MatVector put(@Cast({"size_t"}) long j, @InputMat CvArr cvArr);

                    public native void resize(@Cast({"size_t"}) long j);

                    public native long size();

                    static {
/* 4354 */              Loader.load();
                    }

/* 4354 */          public MatVector(CvArr... array) {
/* 4355 */              this(array.length);
                        put(array);
                    }

/* 4355 */          public MatVector() {
                        allocate();
                    }

/* 4356 */          public MatVector(long n) {
                        allocate(n);
                    }

/* 4357 */          public MatVector(Pointer p) {
/* 4358 */              super(p);
                    }

/* 4370 */          public MatVector put(CvArr... array) {
                        if (size() < array.length) {
                            resize(array.length);
                        }
                        for (int i = 0; i < array.length; i++) {
/* 4373 */                  put(i, array[i]);
                        }
/* 4375 */              return this;
                    }
                }

                @Name({"std::vector<std::vector<char> >"})
/* 4379 */      public static class ByteVectorVector extends Pointer {
                    private native void allocate();

                    private native void allocate(@Cast({"size_t"}) long j);

                    @Index
                    public native byte get(@Cast({"size_t"}) long j, @Cast({"size_t"}) long j2);

                    public native ByteVectorVector put(@Cast({"size_t"}) long j, @Cast({"size_t"}) long j2, byte b);

                    public native void resize(@Cast({"size_t"}) long j);

                    @Index
                    public native void resize(@Cast({"size_t"}) long j, @Cast({"size_t"}) long j2);

                    public native long size();

                    @Index
                    public native long size(@Cast({"size_t"}) long j);

                    static {
/* 4381 */              Loader.load();
                    }

/* 4381 */          public ByteVectorVector() {
                        allocate();
                    }

/* 4382 */          public ByteVectorVector(long n) {
                        allocate(n);
                    }

/* 4383 */          public ByteVectorVector(Pointer p) {
/* 4384 */              super(p);
                    }
                }

                @Name({"std::vector<std::vector<int> >"})
/* 4397 */      public static class IntVectorVector extends Pointer {
                    private native void allocate();

                    private native void allocate(@Cast({"size_t"}) long j);

                    @Index
                    public native int get(@Cast({"size_t"}) long j, @Cast({"size_t"}) long j2);

                    public native IntVectorVector put(@Cast({"size_t"}) long j, @Cast({"size_t"}) long j2, int i);

                    public native void resize(@Cast({"size_t"}) long j);

                    @Index
                    public native void resize(@Cast({"size_t"}) long j, @Cast({"size_t"}) long j2);

                    public native long size();

                    @Index
                    public native long size(@Cast({"size_t"}) long j);

                    static {
/* 4399 */              Loader.load();
                    }

/* 4399 */          public IntVectorVector() {
                        allocate();
                    }

/* 4400 */          public IntVectorVector(long n) {
                        allocate(n);
                    }

/* 4401 */          public IntVectorVector(Pointer p) {
/* 4402 */              super(p);
                    }
                }

                @Name({"std::vector<std::vector<cv::Point> >"})
/* 4415 */      public static class PointVectorVector extends Pointer {
                    private native void allocate();

                    private native void allocate(@Cast({"size_t"}) long j);

                    @ByVal
                    @Index
                    public native CvPoint get(@Cast({"size_t"}) long j, @Cast({"size_t"}) long j2);

                    public native PointVectorVector put(@Cast({"size_t"}) long j, @Cast({"size_t"}) long j2, CvPoint cvPoint);

                    public native void resize(@Cast({"size_t"}) long j);

                    @Index
                    public native void resize(@Cast({"size_t"}) long j, @Cast({"size_t"}) long j2);

                    public native long size();

                    @Index
                    public native long size(@Cast({"size_t"}) long j);

                    static {
/* 4417 */              Loader.load();
                    }

/* 4417 */          public PointVectorVector() {
                        allocate();
                    }

/* 4418 */          public PointVectorVector(long n) {
                        allocate(n);
                    }

/* 4419 */          public PointVectorVector(Pointer p) {
/* 4420 */              super(p);
                    }
                }

                @Name({"std::vector<std::vector<cv::Point2f> >"})
/* 4433 */      public static class Point2fVectorVector extends Pointer {
                    private native void allocate();

                    private native void allocate(@Cast({"size_t"}) long j);

                    @ByVal
                    @Index
                    public native CvPoint2D32f get(@Cast({"size_t"}) long j, @Cast({"size_t"}) long j2);

                    public native Point2fVectorVector put(@Cast({"size_t"}) long j, @Cast({"size_t"}) long j2, CvPoint2D32f cvPoint2D32f);

                    public native void resize(@Cast({"size_t"}) long j);

                    @Index
                    public native void resize(@Cast({"size_t"}) long j, @Cast({"size_t"}) long j2);

                    public native long size();

                    @Index
                    public native long size(@Cast({"size_t"}) long j);

                    static {
/* 4435 */              Loader.load();
                    }

/* 4435 */          public Point2fVectorVector() {
                        allocate();
                    }

/* 4436 */          public Point2fVectorVector(long n) {
                        allocate(n);
                    }

/* 4437 */          public Point2fVectorVector(Pointer p) {
/* 4438 */              super(p);
                    }
                }

                @Name({"std::vector<std::vector<cv::Point2d> >"})
/* 4451 */      public static class Point2dVectorVector extends Pointer {
                    private native void allocate();

                    private native void allocate(@Cast({"size_t"}) long j);

                    @ByVal
                    @Index
                    public native CvPoint2D32f get(@Cast({"size_t"}) long j, @Cast({"size_t"}) long j2);

                    public native Point2dVectorVector put(@Cast({"size_t"}) long j, @Cast({"size_t"}) long j2, CvPoint2D32f cvPoint2D32f);

                    public native void resize(@Cast({"size_t"}) long j);

                    @Index
                    public native void resize(@Cast({"size_t"}) long j, @Cast({"size_t"}) long j2);

                    public native long size();

                    @Index
                    public native long size(@Cast({"size_t"}) long j);

                    static {
/* 4453 */              Loader.load();
                    }

/* 4453 */          public Point2dVectorVector() {
                        allocate();
                    }

/* 4454 */          public Point2dVectorVector(long n) {
                        allocate(n);
                    }

/* 4455 */          public Point2dVectorVector(Pointer p) {
/* 4456 */              super(p);
                    }
                }

                @Name({"std::vector<std::vector<cv::Rect> >"})
/* 4469 */      public static class RectVectorVector extends Pointer {
                    private native void allocate();

                    private native void allocate(@Cast({"size_t"}) long j);

                    @ByVal
                    @Index
                    public native CvRect get(@Cast({"size_t"}) long j, @Cast({"size_t"}) long j2);

                    public native RectVectorVector put(@Cast({"size_t"}) long j, @Cast({"size_t"}) long j2, CvRect cvRect);

                    public native void resize(@Cast({"size_t"}) long j);

                    @Index
                    public native void resize(@Cast({"size_t"}) long j, @Cast({"size_t"}) long j2);

                    public native long size();

                    @Index
                    public native long size(@Cast({"size_t"}) long j);

                    static {
/* 4471 */              Loader.load();
                    }

/* 4471 */          public RectVectorVector() {
                        allocate();
                    }

/* 4472 */          public RectVectorVector(long n) {
                        allocate(n);
                    }

/* 4473 */          public RectVectorVector(Pointer p) {
/* 4474 */              super(p);
                    }
                }

                @Namespace("cv")
                @NoOffset
/* 4495 */      public static class KDTree extends Pointer {
                    private native void allocate();

                    private native void allocate(@InputArray CvMat cvMat, @InputArray CvMat cvMat2, @Cast({"bool"}) boolean z);

                    private native void allocate(@InputArray CvMat cvMat, @Cast({"bool"}) boolean z);

                    public native void build(@InputArray CvMat cvMat, @InputArray CvMat cvMat2, @Cast({"bool"}) boolean z);

                    public native void build(@InputArray CvMat cvMat, @Cast({"bool"}) boolean z);

                    public native int dims();

                    public native int findNearest(@InputArray FloatPointer floatPointer, int i, int i2, @OutputArray IntPointer intPointer, @OutputArray CvMat cvMat, @OutputArray FloatPointer floatPointer2, @OutputArray IntPointer intPointer2);

                    public native int findNearest(@InputArray CvMat cvMat, int i, int i2, @OutputArray CvMat cvMat2, @OutputArray CvMat cvMat3, @OutputArray CvMat cvMat4, @OutputArray CvMat cvMat5);

                    public native void findOrthoRange(@InputArray FloatPointer floatPointer, @InputArray FloatPointer floatPointer2, @OutputArray IntPointer intPointer, @OutputArray CvMat cvMat, @OutputArray IntPointer intPointer2);

                    public native void findOrthoRange(@InputArray CvMat cvMat, @InputArray CvMat cvMat2, @OutputArray CvMat cvMat3, @OutputArray CvMat cvMat4, @OutputArray CvMat cvMat5);

                    @Const
                    public native FloatPointer getPoint(int i, int[] iArr);

                    public native void getPoints(@InputArray FloatPointer floatPointer, @OutputArray CvMat cvMat, @OutputArray IntPointer intPointer);

                    public native void getPoints(@InputArray CvMat cvMat, @OutputArray CvMat cvMat2, @OutputArray CvMat cvMat3);

                    @StdVector
                    @Const
                    public native IntPointer labels();

                    public native KDTree labels(IntPointer intPointer);

                    public native int maxDepth();

                    public native KDTree maxDepth(int i);

                    @StdVector
                    @Const
                    public native Node nodes();

                    public native KDTree nodes(Node node);

                    public native int normType();

                    public native KDTree normType(int i);

                    @InputMat
                    public native CvMat points();

                    public native KDTree points(CvMat cvMat);

                    @NoOffset
/* 4496 */          public static class Node extends Pointer {
                        private native void allocate();

                        private native void allocate(int i, int i2, int i3, float f);

                        private native void allocateArray(int i);

                        public native float boundary();

                        public native Node boundary(float f);

                        public native int idx();

                        public native Node idx(int i);

                        public native int left();

                        public native Node left(int i);

                        public native int right();

                        public native Node right(int i);


                        static {
/* 4499 */                  Loader.load();
                        }

/* 4499 */              public Node() {
                            allocate();
                        }

/* 4500 */              public Node(int size) {
                            allocateArray(size);
                        }

/* 4501 */              public Node(Pointer p) {
/* 4502 */                  super(p);
                        }

/* 4502 */              public Node(int _idx, int _left, int _right, float _boundary) {
/* 4504 */                  allocate(_idx, _left, _right, _boundary);
                        }

                        @Override // com.googlecode.javacpp.Pointer
/* 4510 */              public Node position(int position) {
/* 4511 */                  return (Node) super.position(position);
                        }
                    }

                    static {
/* 4497 */              Loader.load();
                    }

/* 4518 */          public KDTree() {
                        allocate();
                    }

/* 4519 */          public KDTree(Pointer p) {
/* 4520 */              super(p);
                    }

/* 4520 */          public KDTree(CvMat _points, boolean copyAndReorderPoints) {
/* 4522 */              allocate(_points, copyAndReorderPoints);
                    }

/* 4523 */          public KDTree(CvMat _points, CvMat _labels, boolean copyAndReorderPoints) {
/* 4525 */              allocate(_points, _labels, copyAndReorderPoints);
                    }
                }

                @Namespace("cv")
/* 4560 */      public static class Algorithm extends Pointer {
                    @Ptr
                    public static native Algorithm _create(String str);

                    private native void allocate();

                    public static native void getList(@ByRef StringVector stringVector);

                    @Ptr
                    @Const
                    public native Algorithm getAlgorithm(String str);

                    @Cast({"bool"})
                    public native boolean getBool(String str);

                    public native double getDouble(String str);

                    public native int getInt(String str);

                    @OutputMat
                    public native CvMat getMat(String str);

                    @ByVal
                    public native MatVector getMatVector(String str);

                    public native void getParams(@ByRef StringVector stringVector);

                    @ByRef
                    public native String getString(String str);

                    public native AlgorithmInfo info();

                    @ByRef
                    public native String name();

                    @ByRef
                    public native String paramHelp(String str);

                    public native int paramType(String str);

                    public native void read(@Const @Adapter(argc = 2, value = "FileNodeAdapter") CvFileStorage cvFileStorage, CvFileNode cvFileNode);

                    public native void set(String str, double d);

                    public native void set(String str, int i);

                    public native void set(String str, @Ptr Algorithm algorithm);

                    public native void set(String str, CvMat cvMat);

                    public native void set(String str, @ByRef MatVector matVector);

                    public native void set(String str, String str2);

                    public native void set(String str, @Cast({"bool"}) boolean z);

                    public native void write(@Const @Adapter("FileStorageAdapter") CvFileStorage cvFileStorage);

                    static {
/* 4562 */              Loader.load();
                    }

/* 4562 */          public Algorithm() {
                        allocate();
                    }

/* 4563 */          public Algorithm(Pointer p) {
/* 4564 */              super(p);
                    }

/* 4594 */          public static class Constructor extends FunctionPointer {
                        private native void allocate();

                        public native Algorithm call();

                        static {
/* 4596 */                  Loader.load();
                        }

/* 4596 */              public Constructor(Pointer p) {
/* 4597 */                  super(p);
                        }

/* 4597 */              protected Constructor() {
                            allocate();
                        }
                    }

                    @Const
                    @Namespace("cv::Algorithm")
/* 4601 */          public static class Getter extends FunctionPointer {
                        public native int call(Algorithm algorithm);

                        static {
/* 4603 */                  Loader.load();
                        }

/* 4603 */              public Getter(Pointer p) {
/* 4604 */                  super(p);
                        }
                    }

                    @Namespace("cv::Algorithm")
/* 4606 */          public static class Setter extends FunctionPointer {
                        public native void call(Algorithm algorithm, int i);

                        static {
/* 4608 */                  Loader.load();
                        }

/* 4608 */              public Setter(Pointer p) {
/* 4609 */                  super(p);
                        }
                    }
                }

                @Namespace("cv")
/* 4615 */      public static class AlgorithmInfo extends Pointer {
                    private native void allocate(String str, Algorithm.Constructor constructor);

                    public native void addParam_(@ByRef Algorithm algorithm, String str, int i, Pointer pointer, @Cast({"bool"}) boolean z, Algorithm.Getter getter, Algorithm.Setter setter, String str2);

                    public native void get(Algorithm algorithm, String str, int i, Pointer pointer);

                    public native void getParams(@ByRef StringVector stringVector);

                    @ByRef
                    public native String name();

                    @ByRef
                    public native String paramHelp(String str);

                    public native int paramType(String str);

                    public native void read(Algorithm algorithm, @Const @Adapter(argc = 2, value = "FileNodeAdapter") CvFileStorage cvFileStorage, CvFileNode cvFileNode);

                    public native void write(Algorithm algorithm, @Const @Adapter("FileStorageAdapter") CvFileStorage cvFileStorage);

                    static {
/* 4617 */              Loader.load();
                    }

/* 4617 */          public AlgorithmInfo(String name, Algorithm.Constructor create) {
                        allocate(name, create);
                    }

/* 4618 */          public AlgorithmInfo(Pointer p) {
/* 4619 */              super(p);
                    }
                }

                @NoOffset
                @Namespace("cv")
/* 4638 */      public static class Param extends Pointer {
                    public static final int ALGORITHM = 6;
                    public static final int BOOLEAN = 1;
                    public static final int FLOAT = 7;
                    public static final int INT = 0;
                    public static final int MAT = 4;
                    public static final int MAT_VECTOR = 5;
                    public static final int REAL = 2;
                    public static final int SHORT = 10;
                    public static final int STRING = 3;
                    public static final int UCHAR = 11;
                    public static final int UINT64 = 9;
                    public static final int UNSIGNED_INT = 8;

                    private native void allocate();

                    private native void allocate(int i, @Cast({"bool"}) boolean z, int i2, Algorithm.Getter getter, Algorithm.Setter setter, String str);

                    public native Algorithm.Getter getter();

                    public native Param getter(Algorithm.Getter getter);

                    public native Param help(String str);

                    @ByRef
                    public native String help();

                    public native int offset();

                    public native Param offset(int i);

                    public native Param readonly(boolean z);

                    @Cast({"bool"})
                    public native boolean readonly();

                    public native Algorithm.Setter setter();

                    public native Param setter(Algorithm.Setter setter);

                    public native int type();

                    public native Param type(int i);

                    static {
/* 4640 */              Loader.load();
                    }

/* 4640 */          public Param() {
                        allocate();
                    }

/* 4642 */          public Param(int _type, @Cast({"bool"}) boolean _readonly, int _offset, Algorithm.Getter _getter, Algorithm.Setter _setter, String _help) {
/* 4644 */              allocate(_type, _readonly, _offset, _getter, _setter, _help);
                    }

/* 4645 */          public Param(Pointer p) {
/* 4646 */              super(p);
                    }
                }

/* 4661 */      public static class Predicate extends FunctionPointer {
                    private native void allocate();

                    public native boolean call(Pointer pointer, Pointer pointer2);

                    static {
/* 4663 */              Loader.load();
                    }

/* 4663 */          public Predicate(Pointer p) {
/* 4664 */              super(p);
                    }

/* 4664 */          protected Predicate() {
                        allocate();
                    }
                }
            }
