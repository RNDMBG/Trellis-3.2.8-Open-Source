            package android.support.p001v8.renderscript;

            import android.content.Context;
            import android.graphics.Bitmap;
            import android.os.Build;
            import android.os.SystemProperties;
            import android.util.Log;
            import java.io.File;
            
            
    public class RenderScript {
                private static final String CACHE_PATH = "com.android.renderscript.cache";
                static final boolean DEBUG = false;
                static final boolean LOG_ENABLED = false;
                static final String LOG_TAG = "RenderScript_jni";
                static String mCachePath;
                static boolean sInitialized;
                private Context mApplicationContext;
                int mContext;
                int mDev;
                Element mElement_ALLOCATION;
                Element mElement_A_8;
                Element mElement_BOOLEAN;
                Element mElement_CHAR_2;
                Element mElement_CHAR_3;
                Element mElement_CHAR_4;
                Element mElement_DOUBLE_2;
                Element mElement_DOUBLE_3;
                Element mElement_DOUBLE_4;
                Element mElement_ELEMENT;
                Element mElement_F32;
                Element mElement_F64;
                Element mElement_FLOAT_2;
                Element mElement_FLOAT_3;
                Element mElement_FLOAT_4;
                Element mElement_I16;
                Element mElement_I32;
                Element mElement_I64;
                Element mElement_I8;
                Element mElement_INT_2;
                Element mElement_INT_3;
                Element mElement_INT_4;
                Element mElement_LONG_2;
                Element mElement_LONG_3;
                Element mElement_LONG_4;
                Element mElement_MATRIX_2X2;
                Element mElement_MATRIX_3X3;
                Element mElement_MATRIX_4X4;
                Element mElement_RGBA_4444;
                Element mElement_RGBA_5551;
                Element mElement_RGBA_8888;
                Element mElement_RGB_565;
                Element mElement_RGB_888;
                Element mElement_SAMPLER;
                Element mElement_SCRIPT;
                Element mElement_SHORT_2;
                Element mElement_SHORT_3;
                Element mElement_SHORT_4;
                Element mElement_TYPE;
                Element mElement_U16;
                Element mElement_U32;
                Element mElement_U64;
                Element mElement_U8;
                Element mElement_UCHAR_2;
                Element mElement_UCHAR_3;
                Element mElement_UCHAR_4;
                Element mElement_UINT_2;
                Element mElement_UINT_3;
                Element mElement_UINT_4;
                Element mElement_ULONG_2;
                Element mElement_ULONG_3;
                Element mElement_ULONG_4;
                Element mElement_USHORT_2;
                Element mElement_USHORT_3;
                Element mElement_USHORT_4;
                MessageThread mMessageThread;
                Sampler mSampler_CLAMP_LINEAR;
                Sampler mSampler_CLAMP_LINEAR_MIP_LINEAR;
                Sampler mSampler_CLAMP_NEAREST;
                Sampler mSampler_MIRRORED_REPEAT_LINEAR;
                Sampler mSampler_MIRRORED_REPEAT_LINEAR_MIP_LINEAR;
                Sampler mSampler_MIRRORED_REPEAT_NEAREST;
                Sampler mSampler_WRAP_LINEAR;
                Sampler mSampler_WRAP_LINEAR_MIP_LINEAR;
                Sampler mSampler_WRAP_NEAREST;
                static Object lock = new Object();
                static boolean isNative = false;
                private static int thunk = 0;
                RSMessageHandler mMessageCallback = null;
                RSErrorHandler mErrorCallback = null;

                native void nContextDeinitToClient(int i);

                native String nContextGetErrorMessage(int i);

                native int nContextGetUserMessage(int i, int[] iArr);

                native void nContextInitToClient(int i);

                native int nContextPeekMessage(int i, int[] iArr);

                native int nDeviceCreate();

                native void nDeviceDestroy(int i);

                native void nDeviceSetConfig(int i, int i2, int i3);

                native void rsnAllocationCopyFromBitmap(int i, int i2, Bitmap bitmap);

                native void rsnAllocationCopyToBitmap(int i, int i2, Bitmap bitmap);

                native int rsnAllocationCreateBitmapBackedAllocation(int i, int i2, int i3, Bitmap bitmap, int i4);

                native int rsnAllocationCreateBitmapRef(int i, int i2, Bitmap bitmap);

                native int rsnAllocationCreateFromAssetStream(int i, int i2, int i3, int i4);

                native int rsnAllocationCreateFromBitmap(int i, int i2, int i3, Bitmap bitmap, int i4);

                native int rsnAllocationCreateTyped(int i, int i2, int i3, int i4, int i5);

                native int rsnAllocationCubeCreateFromBitmap(int i, int i2, int i3, Bitmap bitmap, int i4);

                native void rsnAllocationData1D(int i, int i2, int i3, int i4, int i5, byte[] bArr, int i6);

                native void rsnAllocationData1D(int i, int i2, int i3, int i4, int i5, float[] fArr, int i6);

                native void rsnAllocationData1D(int i, int i2, int i3, int i4, int i5, int[] iArr, int i6);

                native void rsnAllocationData1D(int i, int i2, int i3, int i4, int i5, short[] sArr, int i6);

                native void rsnAllocationData2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13);

                native void rsnAllocationData2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, byte[] bArr, int i9);

                native void rsnAllocationData2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, float[] fArr, int i9);

                native void rsnAllocationData2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int[] iArr, int i9);

                native void rsnAllocationData2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, short[] sArr, int i9);

                native void rsnAllocationData2D(int i, int i2, int i3, int i4, int i5, int i6, Bitmap bitmap);

                native void rsnAllocationData3D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14);

                native void rsnAllocationData3D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, byte[] bArr, int i10);

                native void rsnAllocationData3D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, float[] fArr, int i10);

                native void rsnAllocationData3D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int[] iArr, int i10);

                native void rsnAllocationData3D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, short[] sArr, int i10);

                native void rsnAllocationElementData1D(int i, int i2, int i3, int i4, int i5, byte[] bArr, int i6);

                native void rsnAllocationGenerateMipmaps(int i, int i2);

                native int rsnAllocationGetType(int i, int i2);

                native void rsnAllocationIoReceive(int i, int i2);

                native void rsnAllocationIoSend(int i, int i2);

                native void rsnAllocationRead(int i, int i2, byte[] bArr);

                native void rsnAllocationRead(int i, int i2, float[] fArr);

                native void rsnAllocationRead(int i, int i2, int[] iArr);

                native void rsnAllocationRead(int i, int i2, short[] sArr);

                native void rsnAllocationResize1D(int i, int i2, int i3);

                native void rsnAllocationResize2D(int i, int i2, int i3, int i4);

                native void rsnAllocationSyncAll(int i, int i2, int i3);

                native int rsnContextCreate(int i, int i2, int i3, int i4);

                native void rsnContextDestroy(int i);

                native void rsnContextDump(int i, int i2);

                native void rsnContextFinish(int i);

                native void rsnContextSendMessage(int i, int i2, int[] iArr);

                native void rsnContextSetPriority(int i, int i2);

                native int rsnElementCreate(int i, int i2, int i3, boolean z, int i4);

                native int rsnElementCreate2(int i, int[] iArr, String[] strArr, int[] iArr2);

                native void rsnElementGetNativeData(int i, int i2, int[] iArr);

                native void rsnElementGetSubElements(int i, int i2, int[] iArr, String[] strArr, int[] iArr2);

                native void rsnObjDestroy(int i, int i2);

                native int rsnSamplerCreate(int i, int i2, int i3, int i4, int i5, int i6, float f);

                native void rsnScriptBindAllocation(int i, int i2, int i3, int i4);

                native int rsnScriptCCreate(int i, String str, String str2, byte[] bArr, int i2);

                native int rsnScriptFieldIDCreate(int i, int i2, int i3);

                native void rsnScriptForEach(int i, int i2, int i3, int i4, int i5);

                native void rsnScriptForEach(int i, int i2, int i3, int i4, int i5, byte[] bArr);

                native void rsnScriptForEachClipped(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11);

                native void rsnScriptForEachClipped(int i, int i2, int i3, int i4, int i5, byte[] bArr, int i6, int i7, int i8, int i9, int i10, int i11);

                native int rsnScriptGroupCreate(int i, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, int[] iArr5);

                native void rsnScriptGroupExecute(int i, int i2);

                native void rsnScriptGroupSetInput(int i, int i2, int i3, int i4);

                native void rsnScriptGroupSetOutput(int i, int i2, int i3, int i4);

                native int rsnScriptIntrinsicCreate(int i, int i2, int i3);

                native void rsnScriptInvoke(int i, int i2, int i3);

                native void rsnScriptInvokeV(int i, int i2, int i3, byte[] bArr);

                native int rsnScriptKernelIDCreate(int i, int i2, int i3, int i4);

                native void rsnScriptSetTimeZone(int i, int i2, byte[] bArr);

                native void rsnScriptSetVarD(int i, int i2, int i3, double d);

                native void rsnScriptSetVarF(int i, int i2, int i3, float f);

                native void rsnScriptSetVarI(int i, int i2, int i3, int i4);

                native void rsnScriptSetVarJ(int i, int i2, int i3, long j);

                native void rsnScriptSetVarObj(int i, int i2, int i3, int i4);

                native void rsnScriptSetVarV(int i, int i2, int i3, byte[] bArr);

                native void rsnScriptSetVarVE(int i, int i2, int i3, byte[] bArr, int i4, int[] iArr);

                native int rsnTypeCreate(int i, int i2, int i3, int i4, int i5, boolean z, boolean z2, int i6);

                native void rsnTypeGetNativeData(int i, int i2, int[] iArr);

        static boolean shouldThunk() {
            if (thunk == 0) {
                if (Build.VERSION.SDK_INT >= 18 && SystemProperties.getInt("debug.rs.forcecompat", 0) == 0) {
                    thunk = 1;
                        } else {
                    thunk = -1;
                        }
                    }
                    return thunk == 1;
                }

       public static void setupDiskCache(File cacheDir) {
           File f = new File(cacheDir, CACHE_PATH);
           mCachePath = f.getAbsolutePath();
           f.mkdirs();
                }

       public enum ContextType {
                    NORMAL(0),
                    DEBUG(1),
                    PROFILE(2);

                    int mID;

           ContextType(int id) {
               this.mID = id;
                    }
                }

       synchronized int nContextCreate(int dev, int ver, int sdkVer, int contextType) {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return rsnContextCreate(dev, ver, sdkVer, contextType);
                }

       synchronized void nContextDestroy() {
                    try {
                        validate();
               rsnContextDestroy(this.mContext);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nContextSetPriority(int p) {
                    try {
                        validate();
               rsnContextSetPriority(this.mContext, p);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nContextDump(int bits) {
                    try {
                        validate();
               rsnContextDump(this.mContext, bits);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nContextFinish() {
                    try {
                        validate();
               rsnContextFinish(this.mContext);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nContextSendMessage(int id, int[] data) {
                    try {
                        validate();
               rsnContextSendMessage(this.mContext, id, data);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nObjDestroy(int id) {
                    try {
                        if (this.mContext != 0) {
                   rsnObjDestroy(this.mContext, id);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized int nElementCreate(int type, int kind, boolean norm, int vecSize) {
                    try {
                        validate();
                    } catch (Throwable th) {
                        throw th;
                    }
           return rsnElementCreate(this.mContext, type, kind, norm, vecSize);
                }

       synchronized int nElementCreate2(int[] elements, String[] names, int[] arraySizes) {
                    try {
                        validate();
                    } catch (Throwable th) {
                        throw th;
                    }
           return rsnElementCreate2(this.mContext, elements, names, arraySizes);
                }

       synchronized void nElementGetNativeData(int id, int[] elementData) {
                    try {
                        validate();
               rsnElementGetNativeData(this.mContext, id, elementData);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nElementGetSubElements(int id, int[] IDs, String[] names, int[] arraySizes) {
                    try {
                        validate();
               rsnElementGetSubElements(this.mContext, id, IDs, names, arraySizes);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized int nTypeCreate(int eid, int x, int y, int z, boolean mips, boolean faces, int yuv) {
                    try {
                        validate();
                    } catch (Throwable th) {
                        throw th;
                    }
           return rsnTypeCreate(this.mContext, eid, x, y, z, mips, faces, yuv);
                }

       synchronized void nTypeGetNativeData(int id, int[] typeData) {
                    try {
                        validate();
               rsnTypeGetNativeData(this.mContext, id, typeData);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized int nAllocationCreateTyped(int type, int mip, int usage, int pointer) {
                    try {
                        validate();
                    } catch (Throwable th) {
                        throw th;
                    }
           return rsnAllocationCreateTyped(this.mContext, type, mip, usage, pointer);
                }

       synchronized int nAllocationCreateFromBitmap(int type, int mip, Bitmap bmp, int usage) {
                    try {
                        validate();
                    } catch (Throwable th) {
                        throw th;
                    }
           return rsnAllocationCreateFromBitmap(this.mContext, type, mip, bmp, usage);
                }

       synchronized int nAllocationCreateBitmapBackedAllocation(int type, int mip, Bitmap bmp, int usage) {
                    try {
                        validate();
                    } catch (Throwable th) {
                        throw th;
                    }
           return rsnAllocationCreateBitmapBackedAllocation(this.mContext, type, mip, bmp, usage);
                }

       synchronized int nAllocationCubeCreateFromBitmap(int type, int mip, Bitmap bmp, int usage) {
                    try {
                        validate();
                    } catch (Throwable th) {
                        throw th;
                    }
           return rsnAllocationCubeCreateFromBitmap(this.mContext, type, mip, bmp, usage);
                }

       synchronized int nAllocationCreateBitmapRef(int type, Bitmap bmp) {
                    try {
                        validate();
                    } catch (Throwable th) {
                        throw th;
                    }
           return rsnAllocationCreateBitmapRef(this.mContext, type, bmp);
                }

       synchronized int nAllocationCreateFromAssetStream(int mips, int assetStream, int usage) {
                    try {
                        validate();
                    } catch (Throwable th) {
                        throw th;
                    }
           return rsnAllocationCreateFromAssetStream(this.mContext, mips, assetStream, usage);
                }

       synchronized void nAllocationCopyToBitmap(int alloc, Bitmap bmp) {
                    try {
                        validate();
               rsnAllocationCopyToBitmap(this.mContext, alloc, bmp);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nAllocationSyncAll(int alloc, int src) {
                    try {
                        validate();
               rsnAllocationSyncAll(this.mContext, alloc, src);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nAllocationIoSend(int alloc) {
                    try {
                        validate();
               rsnAllocationIoSend(this.mContext, alloc);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nAllocationIoReceive(int alloc) {
                    try {
                        validate();
               rsnAllocationIoReceive(this.mContext, alloc);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nAllocationGenerateMipmaps(int alloc) {
                    try {
                        validate();
               rsnAllocationGenerateMipmaps(this.mContext, alloc);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nAllocationCopyFromBitmap(int alloc, Bitmap bmp) {
                    try {
                        validate();
               rsnAllocationCopyFromBitmap(this.mContext, alloc, bmp);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nAllocationData1D(int id, int off, int mip, int count, int[] d, int sizeBytes) {
                    try {
                        validate();
               rsnAllocationData1D(this.mContext, id, off, mip, count, d, sizeBytes);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nAllocationData1D(int id, int off, int mip, int count, short[] d, int sizeBytes) {
                    try {
                        validate();
               rsnAllocationData1D(this.mContext, id, off, mip, count, d, sizeBytes);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nAllocationData1D(int id, int off, int mip, int count, byte[] d, int sizeBytes) {
                    try {
                        validate();
               rsnAllocationData1D(this.mContext, id, off, mip, count, d, sizeBytes);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nAllocationData1D(int id, int off, int mip, int count, float[] d, int sizeBytes) {
                    try {
                        validate();
               rsnAllocationData1D(this.mContext, id, off, mip, count, d, sizeBytes);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nAllocationElementData1D(int id, int xoff, int mip, int compIdx, byte[] d, int sizeBytes) {
                    try {
                        validate();
               rsnAllocationElementData1D(this.mContext, id, xoff, mip, compIdx, d, sizeBytes);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nAllocationData2D(int dstAlloc, int dstXoff, int dstYoff, int dstMip, int dstFace, int width, int height, int srcAlloc, int srcXoff, int srcYoff, int srcMip, int srcFace) {
                    try {
                        validate();
               rsnAllocationData2D(this.mContext, dstAlloc, dstXoff, dstYoff, dstMip, dstFace, width, height, srcAlloc, srcXoff, srcYoff, srcMip, srcFace);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nAllocationData2D(int id, int xoff, int yoff, int mip, int face, int w, int h, byte[] d, int sizeBytes) {
                    try {
                        validate();
               rsnAllocationData2D(this.mContext, id, xoff, yoff, mip, face, w, h, d, sizeBytes);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nAllocationData2D(int id, int xoff, int yoff, int mip, int face, int w, int h, short[] d, int sizeBytes) {
                    try {
                        validate();
               rsnAllocationData2D(this.mContext, id, xoff, yoff, mip, face, w, h, d, sizeBytes);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nAllocationData2D(int id, int xoff, int yoff, int mip, int face, int w, int h, int[] d, int sizeBytes) {
                    try {
                        validate();
               rsnAllocationData2D(this.mContext, id, xoff, yoff, mip, face, w, h, d, sizeBytes);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nAllocationData2D(int id, int xoff, int yoff, int mip, int face, int w, int h, float[] d, int sizeBytes) {
                    try {
                        validate();
               rsnAllocationData2D(this.mContext, id, xoff, yoff, mip, face, w, h, d, sizeBytes);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nAllocationData2D(int id, int xoff, int yoff, int mip, int face, Bitmap b) {
                    try {
                        validate();
               rsnAllocationData2D(this.mContext, id, xoff, yoff, mip, face, b);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nAllocationData3D(int dstAlloc, int dstXoff, int dstYoff, int dstZoff, int dstMip, int width, int height, int depth, int srcAlloc, int srcXoff, int srcYoff, int srcZoff, int srcMip) {
                    try {
                        validate();
               rsnAllocationData3D(this.mContext, dstAlloc, dstXoff, dstYoff, dstZoff, dstMip, width, height, depth, srcAlloc, srcXoff, srcYoff, srcZoff, srcMip);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nAllocationData3D(int id, int xoff, int yoff, int zoff, int mip, int w, int h, int depth, byte[] d, int sizeBytes) {
                    try {
                        validate();
               rsnAllocationData3D(this.mContext, id, xoff, yoff, zoff, mip, w, h, depth, d, sizeBytes);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nAllocationData3D(int id, int xoff, int yoff, int zoff, int mip, int w, int h, int depth, short[] d, int sizeBytes) {
                    try {
                        validate();
               rsnAllocationData3D(this.mContext, id, xoff, yoff, zoff, mip, w, h, depth, d, sizeBytes);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nAllocationData3D(int id, int xoff, int yoff, int zoff, int mip, int w, int h, int depth, int[] d, int sizeBytes) {
                    try {
                        validate();
               rsnAllocationData3D(this.mContext, id, xoff, yoff, zoff, mip, w, h, depth, d, sizeBytes);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nAllocationData3D(int id, int xoff, int yoff, int zoff, int mip, int w, int h, int depth, float[] d, int sizeBytes) {
                    try {
                        validate();
               rsnAllocationData3D(this.mContext, id, xoff, yoff, zoff, mip, w, h, depth, d, sizeBytes);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nAllocationRead(int id, byte[] d) {
                    try {
                        validate();
               rsnAllocationRead(this.mContext, id, d);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nAllocationRead(int id, short[] d) {
                    try {
                        validate();
               rsnAllocationRead(this.mContext, id, d);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nAllocationRead(int id, int[] d) {
                    try {
                        validate();
               rsnAllocationRead(this.mContext, id, d);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nAllocationRead(int id, float[] d) {
                    try {
                        validate();
               rsnAllocationRead(this.mContext, id, d);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized int nAllocationGetType(int id) {
                    try {
                        validate();
                    } catch (Throwable th) {
                        throw th;
                    }
           return rsnAllocationGetType(this.mContext, id);
                }

       synchronized void nAllocationResize1D(int id, int dimX) {
                    try {
                        validate();
               rsnAllocationResize1D(this.mContext, id, dimX);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nAllocationResize2D(int id, int dimX, int dimY) {
                    try {
                        validate();
               rsnAllocationResize2D(this.mContext, id, dimX, dimY);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nScriptBindAllocation(int script, int alloc, int slot) {
                    try {
                        validate();
               rsnScriptBindAllocation(this.mContext, script, alloc, slot);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nScriptSetTimeZone(int script, byte[] timeZone) {
                    try {
                        validate();
               rsnScriptSetTimeZone(this.mContext, script, timeZone);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nScriptInvoke(int id, int slot) {
                    try {
                        validate();
               rsnScriptInvoke(this.mContext, id, slot);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nScriptForEach(int id, int slot, int ain, int aout, byte[] params) {
                    try {
                        validate();
               if (params == null) {
                   rsnScriptForEach(this.mContext, id, slot, ain, aout);
                        } else {
                   rsnScriptForEach(this.mContext, id, slot, ain, aout, params);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nScriptForEachClipped(int id, int slot, int ain, int aout, byte[] params, int xstart, int xend, int ystart, int yend, int zstart, int zend) {
                    try {
                        validate();
               if (params == null) {
                   rsnScriptForEachClipped(this.mContext, id, slot, ain, aout, xstart, xend, ystart, yend, zstart, zend);
                        } else {
                   rsnScriptForEachClipped(this.mContext, id, slot, ain, aout, params, xstart, xend, ystart, yend, zstart, zend);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nScriptInvokeV(int id, int slot, byte[] params) {
                    try {
                        validate();
               rsnScriptInvokeV(this.mContext, id, slot, params);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nScriptSetVarI(int id, int slot, int val) {
                    try {
                        validate();
               rsnScriptSetVarI(this.mContext, id, slot, val);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nScriptSetVarJ(int id, int slot, long val) {
                    try {
                        validate();
               rsnScriptSetVarJ(this.mContext, id, slot, val);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nScriptSetVarF(int id, int slot, float val) {
                    try {
                        validate();
               rsnScriptSetVarF(this.mContext, id, slot, val);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nScriptSetVarD(int id, int slot, double val) {
                    try {
                        validate();
               rsnScriptSetVarD(this.mContext, id, slot, val);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nScriptSetVarV(int id, int slot, byte[] val) {
                    try {
                        validate();
               rsnScriptSetVarV(this.mContext, id, slot, val);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nScriptSetVarVE(int id, int slot, byte[] val, int e, int[] dims) {
                    try {
                        validate();
               rsnScriptSetVarVE(this.mContext, id, slot, val, e, dims);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nScriptSetVarObj(int id, int slot, int val) {
                    try {
                        validate();
               rsnScriptSetVarObj(this.mContext, id, slot, val);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized int nScriptCCreate(String resName, String cacheDir, byte[] script, int length) {
                    try {
                        validate();
                    } catch (Throwable th) {
                        throw th;
                    }
           return rsnScriptCCreate(this.mContext, resName, cacheDir, script, length);
                }

       synchronized int nScriptIntrinsicCreate(int id, int eid) {
                    try {
                        validate();
                    } catch (Throwable th) {
                        throw th;
                    }
           return rsnScriptIntrinsicCreate(this.mContext, id, eid);
                }

       synchronized int nScriptKernelIDCreate(int sid, int slot, int sig) {
                    try {
                        validate();
                    } catch (Throwable th) {
                        throw th;
                    }
           return rsnScriptKernelIDCreate(this.mContext, sid, slot, sig);
                }

       synchronized int nScriptFieldIDCreate(int sid, int slot) {
                    try {
                        validate();
                    } catch (Throwable th) {
                        throw th;
                    }
           return rsnScriptFieldIDCreate(this.mContext, sid, slot);
                }

       synchronized int nScriptGroupCreate(int[] kernels, int[] src, int[] dstk, int[] dstf, int[] types) {
                    try {
                        validate();
                    } catch (Throwable th) {
                        throw th;
                    }
           return rsnScriptGroupCreate(this.mContext, kernels, src, dstk, dstf, types);
                }

       synchronized void nScriptGroupSetInput(int group, int kernel, int alloc) {
                    try {
                        validate();
               rsnScriptGroupSetInput(this.mContext, group, kernel, alloc);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nScriptGroupSetOutput(int group, int kernel, int alloc) {
                    try {
                        validate();
               rsnScriptGroupSetOutput(this.mContext, group, kernel, alloc);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized void nScriptGroupExecute(int group) {
                    try {
                        validate();
               rsnScriptGroupExecute(this.mContext, group);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

       synchronized int nSamplerCreate(int magFilter, int minFilter, int wrapS, int wrapT, int wrapR, float aniso) {
                    try {
                        validate();
                    } catch (Throwable th) {
                        throw th;
                    }
           return rsnSamplerCreate(this.mContext, magFilter, minFilter, wrapS, wrapT, wrapR, aniso);
                }

       public static class RSMessageHandler implements Runnable {
                    protected int[] mData;
                    protected int mID;
                    protected int mLength;

                    @Override 
           public void run() {
                    }
                }

       public void setMessageHandler(RSMessageHandler msg) {
           this.mMessageCallback = msg;
           if (isNative) {
               RenderScriptThunker rst = (RenderScriptThunker) this;
               android.renderscript.RenderScript.RSMessageHandler newmsg = new C01261();
               rst.f54mN.setMessageHandler(newmsg);
                    }
                }

                
       class C01261 extends android.renderscript.RenderScript.RSMessageHandler {
           C01261() {
                    }

                    @Override 
           public void run() {
               RenderScript.this.mMessageCallback.mData = this.mData;
               RenderScript.this.mMessageCallback.mID = this.mID;
               RenderScript.this.mMessageCallback.mLength = this.mLength;
               RenderScript.this.mMessageCallback.run();
                    }
                }

       public RSMessageHandler getMessageHandler() {
           return this.mMessageCallback;
                }

       public void sendMessage(int id, int[] data) {
           nContextSendMessage(id, data);
                }

       public static class RSErrorHandler implements Runnable {
                    protected String mErrorMessage;
                    protected int mErrorNum;

                    @Override 
           public void run() {
                    }
                }

       public void setErrorHandler(RSErrorHandler msg) {
           this.mErrorCallback = msg;
           if (isNative) {
               RenderScriptThunker rst = (RenderScriptThunker) this;
               android.renderscript.RenderScript.RSErrorHandler newmsg = new C01272();
               rst.f54mN.setErrorHandler(newmsg);
                    }
                }

                
       class C01272 extends android.renderscript.RenderScript.RSErrorHandler {
           C01272() {
                    }

                    @Override 
           public void run() {
               RenderScript.this.mErrorCallback.mErrorMessage = this.mErrorMessage;
               RenderScript.this.mErrorCallback.mErrorNum = this.mErrorNum;
               RenderScript.this.mErrorCallback.run();
                    }
                }

       public RSErrorHandler getErrorHandler() {
           return this.mErrorCallback;
                }

       public enum Priority {
                    LOW(15),
                    NORMAL(-4);

                    int mID;

           Priority(int id) {
               this.mID = id;
                    }
                }

       void validate() {
           if (this.mContext == 0) {
               throw new RSInvalidStateException("Calling RS with no Context active.");
                    }
                }

       public void setPriority(Priority p) {
           validate();
           nContextSetPriority(p.mID);
                }

       static class MessageThread extends Thread {
                    static final int RS_ERROR_FATAL_UNKNOWN = 4096;
                    static final int RS_MESSAGE_TO_CLIENT_ERROR = 3;
                    static final int RS_MESSAGE_TO_CLIENT_EXCEPTION = 1;
                    static final int RS_MESSAGE_TO_CLIENT_NONE = 0;
                    static final int RS_MESSAGE_TO_CLIENT_RESIZE = 2;
                    static final int RS_MESSAGE_TO_CLIENT_USER = 4;
                    int[] mAuxData;
                    RenderScript mRS;
                    boolean mRun;

           MessageThread(RenderScript rs) {
               super("RSMessageThread");
               this.mRun = true;
               this.mAuxData = new int[2];
               this.mRS = rs;
                    }

                    @Override 
           public void run() {
               int[] rbuf = new int[16];
               this.mRS.nContextInitToClient(this.mRS.mContext);
               while (this.mRun) {
                   rbuf[0] = 0;
                   int msg = this.mRS.nContextPeekMessage(this.mRS.mContext, this.mAuxData);
                   int size = this.mAuxData[1];
                   int subID = this.mAuxData[0];
                   if (msg == 4) {
                       if ((size >> 2) >= rbuf.length) {
                           rbuf = new int[(size + 3) >> 2];
                                }
                       if (this.mRS.nContextGetUserMessage(this.mRS.mContext, rbuf) != 4) {
                           throw new RSDriverException("Error processing message from RenderScript.");
                                }
                       if (this.mRS.mMessageCallback != null) {
                           this.mRS.mMessageCallback.mData = rbuf;
                           this.mRS.mMessageCallback.mID = subID;
                           this.mRS.mMessageCallback.mLength = size;
                           this.mRS.mMessageCallback.run();
                                } else {
                           throw new RSInvalidStateException("Received a message from the script with no message handler installed.");
                                }
                            } else if (msg == 3) {
                       String e = this.mRS.nContextGetErrorMessage(this.mRS.mContext);
                       if (subID >= 4096) {
                           throw new RSRuntimeException("Fatal error " + subID + ", details: " + e);
                                }
                       if (this.mRS.mErrorCallback != null) {
                           this.mRS.mErrorCallback.mErrorMessage = e;
                           this.mRS.mErrorCallback.mErrorNum = subID;
                           this.mRS.mErrorCallback.run();
                                } else {
                           Log.e(RenderScript.LOG_TAG, "non fatal RS error, " + e);
                                }
                            } else {
                                try {
                           sleep(1L, 0);
                                } catch (InterruptedException e2) {
                                }
                            }
                        }
                    }
                }

       RenderScript(Context ctx) {
           if (ctx != null) {
               this.mApplicationContext = ctx.getApplicationContext();
                    }
                }

       public final Context getApplicationContext() {
           return this.mApplicationContext;
                }

       public static RenderScript create(Context ctx, int sdkVersion) {
           return create(ctx, sdkVersion, ContextType.NORMAL);
                }

       public static RenderScript create(Context ctx, int sdkVersion, ContextType ct) {
           RenderScript rs = new RenderScript(ctx);
           if (shouldThunk()) {
               Log.v(LOG_TAG, "RS native mode");
               return RenderScriptThunker.create(ctx, sdkVersion);
                    }
                    synchronized (lock) {
                        try {
                   if (!sInitialized) {
                                try {
                           System.loadLibrary("RSSupport");
                           System.loadLibrary("rsjni");
                           sInitialized = true;
                                } catch (UnsatisfiedLinkError e) {
                           Log.e(LOG_TAG, "Error loading RS jni library: " + e);
                           throw new RSRuntimeException("Error loading RS jni library: " + e);
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
           Log.v(LOG_TAG, "RS compat mode");
           rs.mDev = rs.nDeviceCreate();
           rs.mContext = rs.nContextCreate(rs.mDev, 0, sdkVersion, ct.mID);
           if (rs.mContext == 0) {
               throw new RSDriverException("Failed to create RS context.");
                    }
           rs.mMessageThread = new MessageThread(rs);
           rs.mMessageThread.start();
           return rs;
                }

       public static RenderScript create(Context ctx) {
           return create(ctx, ContextType.NORMAL);
                }

       public static RenderScript create(Context ctx, ContextType ct) {
           int v = ctx.getApplicationInfo().targetSdkVersion;
           return create(ctx, v, ct);
                }

       public void contextDump() {
           validate();
           nContextDump(0);
                }

       public void finish() {
           nContextFinish();
                }

       public void destroy() {
           validate();
           nContextDeinitToClient(this.mContext);
           this.mMessageThread.mRun = false;
                    try {
               this.mMessageThread.join();
                    } catch (InterruptedException e) {
                    }
          nContextDestroy();
          this.mContext = 0;
          nDeviceDestroy(this.mDev);
          this.mDev = 0;
                }

      boolean isAlive() {
                    return this.mContext != 0;
                }

      int safeID(BaseObj o) {
          if (o != null) {
              return o.getID(this);
                    }
                    return 0;
                }
            }
