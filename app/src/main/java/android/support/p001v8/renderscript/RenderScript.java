            package android.support.p001v8.renderscript;

            import android.content.Context;
            import android.graphics.Bitmap;
            import android.os.Build;
            import android.os.SystemProperties;
            import android.util.Log;
            import java.io.File;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 77 */    public class RenderScript {
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

/* 78 */        static boolean shouldThunk() {
/* 79 */            if (thunk == 0) {
/* 80 */                if (Build.VERSION.SDK_INT >= 18 && SystemProperties.getInt("debug.rs.forcecompat", 0) == 0) {
/* 82 */                    thunk = 1;
                        } else {
/* 84 */                    thunk = -1;
                        }
                    }
                    return thunk == 1;
                }

/* 106 */       public static void setupDiskCache(File cacheDir) {
/* 107 */           File f = new File(cacheDir, CACHE_PATH);
/* 108 */           mCachePath = f.getAbsolutePath();
/* 109 */           f.mkdirs();
                }

/* 136 */       public enum ContextType {
                    NORMAL(0),
                    DEBUG(1),
                    PROFILE(2);

                    int mID;

/* 137 */           ContextType(int id) {
/* 139 */               this.mID = id;
                    }
                }

/* 146 */       synchronized int nContextCreate(int dev, int ver, int sdkVer, int contextType) {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return rsnContextCreate(dev, ver, sdkVer, contextType);
                }

/* 150 */       synchronized void nContextDestroy() {
                    try {
                        validate();
/* 152 */               rsnContextDestroy(this.mContext);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 155 */       synchronized void nContextSetPriority(int p) {
                    try {
                        validate();
/* 157 */               rsnContextSetPriority(this.mContext, p);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 160 */       synchronized void nContextDump(int bits) {
                    try {
                        validate();
/* 162 */               rsnContextDump(this.mContext, bits);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 165 */       synchronized void nContextFinish() {
                    try {
                        validate();
/* 167 */               rsnContextFinish(this.mContext);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 171 */       synchronized void nContextSendMessage(int id, int[] data) {
                    try {
                        validate();
/* 173 */               rsnContextSendMessage(this.mContext, id, data);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 180 */       synchronized void nObjDestroy(int id) {
                    try {
                        if (this.mContext != 0) {
/* 182 */                   rsnObjDestroy(this.mContext, id);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 187 */       synchronized int nElementCreate(int type, int kind, boolean norm, int vecSize) {
                    try {
                        validate();
                    } catch (Throwable th) {
                        throw th;
                    }
/* 189 */           return rsnElementCreate(this.mContext, type, kind, norm, vecSize);
                }

/* 192 */       synchronized int nElementCreate2(int[] elements, String[] names, int[] arraySizes) {
                    try {
                        validate();
                    } catch (Throwable th) {
                        throw th;
                    }
/* 194 */           return rsnElementCreate2(this.mContext, elements, names, arraySizes);
                }

/* 197 */       synchronized void nElementGetNativeData(int id, int[] elementData) {
                    try {
                        validate();
/* 199 */               rsnElementGetNativeData(this.mContext, id, elementData);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 203 */       synchronized void nElementGetSubElements(int id, int[] IDs, String[] names, int[] arraySizes) {
                    try {
                        validate();
/* 205 */               rsnElementGetSubElements(this.mContext, id, IDs, names, arraySizes);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 209 */       synchronized int nTypeCreate(int eid, int x, int y, int z, boolean mips, boolean faces, int yuv) {
                    try {
                        validate();
                    } catch (Throwable th) {
                        throw th;
                    }
/* 211 */           return rsnTypeCreate(this.mContext, eid, x, y, z, mips, faces, yuv);
                }

/* 214 */       synchronized void nTypeGetNativeData(int id, int[] typeData) {
                    try {
                        validate();
/* 216 */               rsnTypeGetNativeData(this.mContext, id, typeData);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 220 */       synchronized int nAllocationCreateTyped(int type, int mip, int usage, int pointer) {
                    try {
                        validate();
                    } catch (Throwable th) {
                        throw th;
                    }
/* 222 */           return rsnAllocationCreateTyped(this.mContext, type, mip, usage, pointer);
                }

/* 225 */       synchronized int nAllocationCreateFromBitmap(int type, int mip, Bitmap bmp, int usage) {
                    try {
                        validate();
                    } catch (Throwable th) {
                        throw th;
                    }
/* 227 */           return rsnAllocationCreateFromBitmap(this.mContext, type, mip, bmp, usage);
                }

/* 231 */       synchronized int nAllocationCreateBitmapBackedAllocation(int type, int mip, Bitmap bmp, int usage) {
                    try {
                        validate();
                    } catch (Throwable th) {
                        throw th;
                    }
/* 233 */           return rsnAllocationCreateBitmapBackedAllocation(this.mContext, type, mip, bmp, usage);
                }

/* 238 */       synchronized int nAllocationCubeCreateFromBitmap(int type, int mip, Bitmap bmp, int usage) {
                    try {
                        validate();
                    } catch (Throwable th) {
                        throw th;
                    }
/* 240 */           return rsnAllocationCubeCreateFromBitmap(this.mContext, type, mip, bmp, usage);
                }

/* 243 */       synchronized int nAllocationCreateBitmapRef(int type, Bitmap bmp) {
                    try {
                        validate();
                    } catch (Throwable th) {
                        throw th;
                    }
/* 245 */           return rsnAllocationCreateBitmapRef(this.mContext, type, bmp);
                }

/* 248 */       synchronized int nAllocationCreateFromAssetStream(int mips, int assetStream, int usage) {
                    try {
                        validate();
                    } catch (Throwable th) {
                        throw th;
                    }
/* 250 */           return rsnAllocationCreateFromAssetStream(this.mContext, mips, assetStream, usage);
                }

/* 254 */       synchronized void nAllocationCopyToBitmap(int alloc, Bitmap bmp) {
                    try {
                        validate();
/* 256 */               rsnAllocationCopyToBitmap(this.mContext, alloc, bmp);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 261 */       synchronized void nAllocationSyncAll(int alloc, int src) {
                    try {
                        validate();
/* 263 */               rsnAllocationSyncAll(this.mContext, alloc, src);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 266 */       synchronized void nAllocationIoSend(int alloc) {
                    try {
                        validate();
/* 268 */               rsnAllocationIoSend(this.mContext, alloc);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 271 */       synchronized void nAllocationIoReceive(int alloc) {
                    try {
                        validate();
/* 273 */               rsnAllocationIoReceive(this.mContext, alloc);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 278 */       synchronized void nAllocationGenerateMipmaps(int alloc) {
                    try {
                        validate();
/* 280 */               rsnAllocationGenerateMipmaps(this.mContext, alloc);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 283 */       synchronized void nAllocationCopyFromBitmap(int alloc, Bitmap bmp) {
                    try {
                        validate();
/* 285 */               rsnAllocationCopyFromBitmap(this.mContext, alloc, bmp);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 290 */       synchronized void nAllocationData1D(int id, int off, int mip, int count, int[] d, int sizeBytes) {
                    try {
                        validate();
/* 292 */               rsnAllocationData1D(this.mContext, id, off, mip, count, d, sizeBytes);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 295 */       synchronized void nAllocationData1D(int id, int off, int mip, int count, short[] d, int sizeBytes) {
                    try {
                        validate();
/* 297 */               rsnAllocationData1D(this.mContext, id, off, mip, count, d, sizeBytes);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 300 */       synchronized void nAllocationData1D(int id, int off, int mip, int count, byte[] d, int sizeBytes) {
                    try {
                        validate();
/* 302 */               rsnAllocationData1D(this.mContext, id, off, mip, count, d, sizeBytes);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 305 */       synchronized void nAllocationData1D(int id, int off, int mip, int count, float[] d, int sizeBytes) {
                    try {
                        validate();
/* 307 */               rsnAllocationData1D(this.mContext, id, off, mip, count, d, sizeBytes);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 311 */       synchronized void nAllocationElementData1D(int id, int xoff, int mip, int compIdx, byte[] d, int sizeBytes) {
                    try {
                        validate();
/* 313 */               rsnAllocationElementData1D(this.mContext, id, xoff, mip, compIdx, d, sizeBytes);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 326 */       synchronized void nAllocationData2D(int dstAlloc, int dstXoff, int dstYoff, int dstMip, int dstFace, int width, int height, int srcAlloc, int srcXoff, int srcYoff, int srcMip, int srcFace) {
                    try {
                        validate();
/* 328 */               rsnAllocationData2D(this.mContext, dstAlloc, dstXoff, dstYoff, dstMip, dstFace, width, height, srcAlloc, srcXoff, srcYoff, srcMip, srcFace);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 337 */       synchronized void nAllocationData2D(int id, int xoff, int yoff, int mip, int face, int w, int h, byte[] d, int sizeBytes) {
                    try {
                        validate();
/* 339 */               rsnAllocationData2D(this.mContext, id, xoff, yoff, mip, face, w, h, d, sizeBytes);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 342 */       synchronized void nAllocationData2D(int id, int xoff, int yoff, int mip, int face, int w, int h, short[] d, int sizeBytes) {
                    try {
                        validate();
/* 344 */               rsnAllocationData2D(this.mContext, id, xoff, yoff, mip, face, w, h, d, sizeBytes);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 347 */       synchronized void nAllocationData2D(int id, int xoff, int yoff, int mip, int face, int w, int h, int[] d, int sizeBytes) {
                    try {
                        validate();
/* 349 */               rsnAllocationData2D(this.mContext, id, xoff, yoff, mip, face, w, h, d, sizeBytes);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 352 */       synchronized void nAllocationData2D(int id, int xoff, int yoff, int mip, int face, int w, int h, float[] d, int sizeBytes) {
                    try {
                        validate();
/* 354 */               rsnAllocationData2D(this.mContext, id, xoff, yoff, mip, face, w, h, d, sizeBytes);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 357 */       synchronized void nAllocationData2D(int id, int xoff, int yoff, int mip, int face, Bitmap b) {
                    try {
                        validate();
/* 359 */               rsnAllocationData2D(this.mContext, id, xoff, yoff, mip, face, b);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 372 */       synchronized void nAllocationData3D(int dstAlloc, int dstXoff, int dstYoff, int dstZoff, int dstMip, int width, int height, int depth, int srcAlloc, int srcXoff, int srcYoff, int srcZoff, int srcMip) {
                    try {
                        validate();
/* 374 */               rsnAllocationData3D(this.mContext, dstAlloc, dstXoff, dstYoff, dstZoff, dstMip, width, height, depth, srcAlloc, srcXoff, srcYoff, srcZoff, srcMip);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 381 */       synchronized void nAllocationData3D(int id, int xoff, int yoff, int zoff, int mip, int w, int h, int depth, byte[] d, int sizeBytes) {
                    try {
                        validate();
/* 383 */               rsnAllocationData3D(this.mContext, id, xoff, yoff, zoff, mip, w, h, depth, d, sizeBytes);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 386 */       synchronized void nAllocationData3D(int id, int xoff, int yoff, int zoff, int mip, int w, int h, int depth, short[] d, int sizeBytes) {
                    try {
                        validate();
/* 388 */               rsnAllocationData3D(this.mContext, id, xoff, yoff, zoff, mip, w, h, depth, d, sizeBytes);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 391 */       synchronized void nAllocationData3D(int id, int xoff, int yoff, int zoff, int mip, int w, int h, int depth, int[] d, int sizeBytes) {
                    try {
                        validate();
/* 393 */               rsnAllocationData3D(this.mContext, id, xoff, yoff, zoff, mip, w, h, depth, d, sizeBytes);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 396 */       synchronized void nAllocationData3D(int id, int xoff, int yoff, int zoff, int mip, int w, int h, int depth, float[] d, int sizeBytes) {
                    try {
                        validate();
/* 398 */               rsnAllocationData3D(this.mContext, id, xoff, yoff, zoff, mip, w, h, depth, d, sizeBytes);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 403 */       synchronized void nAllocationRead(int id, byte[] d) {
                    try {
                        validate();
/* 405 */               rsnAllocationRead(this.mContext, id, d);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 408 */       synchronized void nAllocationRead(int id, short[] d) {
                    try {
                        validate();
/* 410 */               rsnAllocationRead(this.mContext, id, d);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 413 */       synchronized void nAllocationRead(int id, int[] d) {
                    try {
                        validate();
/* 415 */               rsnAllocationRead(this.mContext, id, d);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 418 */       synchronized void nAllocationRead(int id, float[] d) {
                    try {
                        validate();
/* 420 */               rsnAllocationRead(this.mContext, id, d);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 423 */       synchronized int nAllocationGetType(int id) {
                    try {
                        validate();
                    } catch (Throwable th) {
                        throw th;
                    }
/* 425 */           return rsnAllocationGetType(this.mContext, id);
                }

/* 429 */       synchronized void nAllocationResize1D(int id, int dimX) {
                    try {
                        validate();
/* 431 */               rsnAllocationResize1D(this.mContext, id, dimX);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 434 */       synchronized void nAllocationResize2D(int id, int dimX, int dimY) {
                    try {
                        validate();
/* 436 */               rsnAllocationResize2D(this.mContext, id, dimX, dimY);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 440 */       synchronized void nScriptBindAllocation(int script, int alloc, int slot) {
                    try {
                        validate();
/* 442 */               rsnScriptBindAllocation(this.mContext, script, alloc, slot);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 445 */       synchronized void nScriptSetTimeZone(int script, byte[] timeZone) {
                    try {
                        validate();
/* 447 */               rsnScriptSetTimeZone(this.mContext, script, timeZone);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 450 */       synchronized void nScriptInvoke(int id, int slot) {
                    try {
                        validate();
/* 452 */               rsnScriptInvoke(this.mContext, id, slot);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 460 */       synchronized void nScriptForEach(int id, int slot, int ain, int aout, byte[] params) {
                    try {
                        validate();
/* 462 */               if (params == null) {
/* 463 */                   rsnScriptForEach(this.mContext, id, slot, ain, aout);
                        } else {
/* 465 */                   rsnScriptForEach(this.mContext, id, slot, ain, aout, params);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 470 */       synchronized void nScriptForEachClipped(int id, int slot, int ain, int aout, byte[] params, int xstart, int xend, int ystart, int yend, int zstart, int zend) {
                    try {
                        validate();
/* 472 */               if (params == null) {
/* 473 */                   rsnScriptForEachClipped(this.mContext, id, slot, ain, aout, xstart, xend, ystart, yend, zstart, zend);
                        } else {
/* 475 */                   rsnScriptForEachClipped(this.mContext, id, slot, ain, aout, params, xstart, xend, ystart, yend, zstart, zend);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 480 */       synchronized void nScriptInvokeV(int id, int slot, byte[] params) {
                    try {
                        validate();
/* 482 */               rsnScriptInvokeV(this.mContext, id, slot, params);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 485 */       synchronized void nScriptSetVarI(int id, int slot, int val) {
                    try {
                        validate();
/* 487 */               rsnScriptSetVarI(this.mContext, id, slot, val);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 490 */       synchronized void nScriptSetVarJ(int id, int slot, long val) {
                    try {
                        validate();
/* 492 */               rsnScriptSetVarJ(this.mContext, id, slot, val);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 495 */       synchronized void nScriptSetVarF(int id, int slot, float val) {
                    try {
                        validate();
/* 497 */               rsnScriptSetVarF(this.mContext, id, slot, val);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 500 */       synchronized void nScriptSetVarD(int id, int slot, double val) {
                    try {
                        validate();
/* 502 */               rsnScriptSetVarD(this.mContext, id, slot, val);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 505 */       synchronized void nScriptSetVarV(int id, int slot, byte[] val) {
                    try {
                        validate();
/* 507 */               rsnScriptSetVarV(this.mContext, id, slot, val);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 512 */       synchronized void nScriptSetVarVE(int id, int slot, byte[] val, int e, int[] dims) {
                    try {
                        validate();
/* 514 */               rsnScriptSetVarVE(this.mContext, id, slot, val, e, dims);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 517 */       synchronized void nScriptSetVarObj(int id, int slot, int val) {
                    try {
                        validate();
/* 519 */               rsnScriptSetVarObj(this.mContext, id, slot, val);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 524 */       synchronized int nScriptCCreate(String resName, String cacheDir, byte[] script, int length) {
                    try {
                        validate();
                    } catch (Throwable th) {
                        throw th;
                    }
/* 526 */           return rsnScriptCCreate(this.mContext, resName, cacheDir, script, length);
                }

/* 530 */       synchronized int nScriptIntrinsicCreate(int id, int eid) {
                    try {
                        validate();
                    } catch (Throwable th) {
                        throw th;
                    }
/* 532 */           return rsnScriptIntrinsicCreate(this.mContext, id, eid);
                }

/* 536 */       synchronized int nScriptKernelIDCreate(int sid, int slot, int sig) {
                    try {
                        validate();
                    } catch (Throwable th) {
                        throw th;
                    }
/* 538 */           return rsnScriptKernelIDCreate(this.mContext, sid, slot, sig);
                }

/* 542 */       synchronized int nScriptFieldIDCreate(int sid, int slot) {
                    try {
                        validate();
                    } catch (Throwable th) {
                        throw th;
                    }
/* 544 */           return rsnScriptFieldIDCreate(this.mContext, sid, slot);
                }

/* 548 */       synchronized int nScriptGroupCreate(int[] kernels, int[] src, int[] dstk, int[] dstf, int[] types) {
                    try {
                        validate();
                    } catch (Throwable th) {
                        throw th;
                    }
/* 550 */           return rsnScriptGroupCreate(this.mContext, kernels, src, dstk, dstf, types);
                }

/* 554 */       synchronized void nScriptGroupSetInput(int group, int kernel, int alloc) {
                    try {
                        validate();
/* 556 */               rsnScriptGroupSetInput(this.mContext, group, kernel, alloc);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 560 */       synchronized void nScriptGroupSetOutput(int group, int kernel, int alloc) {
                    try {
                        validate();
/* 562 */               rsnScriptGroupSetOutput(this.mContext, group, kernel, alloc);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 566 */       synchronized void nScriptGroupExecute(int group) {
                    try {
                        validate();
/* 568 */               rsnScriptGroupExecute(this.mContext, group);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 574 */       synchronized int nSamplerCreate(int magFilter, int minFilter, int wrapS, int wrapT, int wrapR, float aniso) {
                    try {
                        validate();
                    } catch (Throwable th) {
                        throw th;
                    }
/* 576 */           return rsnSamplerCreate(this.mContext, magFilter, minFilter, wrapS, wrapT, wrapR, aniso);
                }

/* 683 */       public static class RSMessageHandler implements Runnable {
                    protected int[] mData;
                    protected int mID;
                    protected int mLength;

                    @Override // java.lang.Runnable
/* 684 */           public void run() {
                    }
                }

/* 696 */       public void setMessageHandler(RSMessageHandler msg) {
/* 697 */           this.mMessageCallback = msg;
/* 698 */           if (isNative) {
/* 699 */               RenderScriptThunker rst = (RenderScriptThunker) this;
/* 700 */               android.renderscript.RenderScript.RSMessageHandler newmsg = new C01261();
/* 709 */               rst.f54mN.setMessageHandler(newmsg);
                    }
                }

                /* JADX INFO: renamed from: android.support.v8.renderscript.RenderScript$1 */
/* 699 */       class C01261 extends android.renderscript.RenderScript.RSMessageHandler {
/* 700 */           C01261() {
                    }

                    @Override // android.renderscript.RenderScript.RSMessageHandler, java.lang.Runnable
/* 702 */           public void run() {
/* 703 */               RenderScript.this.mMessageCallback.mData = this.mData;
/* 704 */               RenderScript.this.mMessageCallback.mID = this.mID;
/* 705 */               RenderScript.this.mMessageCallback.mLength = this.mLength;
/* 706 */               RenderScript.this.mMessageCallback.run();
                    }
                }

/* 712 */       public RSMessageHandler getMessageHandler() {
/* 713 */           return this.mMessageCallback;
                }

/* 725 */       public void sendMessage(int id, int[] data) {
/* 726 */           nContextSendMessage(id, data);
                }

/* 737 */       public static class RSErrorHandler implements Runnable {
                    protected String mErrorMessage;
                    protected int mErrorNum;

                    @Override // java.lang.Runnable
/* 738 */           public void run() {
                    }
                }

/* 751 */       public void setErrorHandler(RSErrorHandler msg) {
/* 752 */           this.mErrorCallback = msg;
/* 753 */           if (isNative) {
/* 754 */               RenderScriptThunker rst = (RenderScriptThunker) this;
/* 755 */               android.renderscript.RenderScript.RSErrorHandler newmsg = new C01272();
/* 763 */               rst.f54mN.setErrorHandler(newmsg);
                    }
                }

                /* JADX INFO: renamed from: android.support.v8.renderscript.RenderScript$2 */
/* 754 */       class C01272 extends android.renderscript.RenderScript.RSErrorHandler {
/* 755 */           C01272() {
                    }

                    @Override // android.renderscript.RenderScript.RSErrorHandler, java.lang.Runnable
/* 757 */           public void run() {
/* 758 */               RenderScript.this.mErrorCallback.mErrorMessage = this.mErrorMessage;
/* 759 */               RenderScript.this.mErrorCallback.mErrorNum = this.mErrorNum;
/* 760 */               RenderScript.this.mErrorCallback.run();
                    }
                }

/* 766 */       public RSErrorHandler getErrorHandler() {
/* 767 */           return this.mErrorCallback;
                }

/* 778 */       public enum Priority {
                    LOW(15),
                    NORMAL(-4);

                    int mID;

/* 779 */           Priority(int id) {
/* 781 */               this.mID = id;
                    }
                }

/* 785 */       void validate() {
/* 786 */           if (this.mContext == 0) {
/* 787 */               throw new RSInvalidStateException("Calling RS with no Context active.");
                    }
                }

/* 797 */       public void setPriority(Priority p) {
/* 798 */           validate();
/* 799 */           nContextSetPriority(p.mID);
                }

/* 814 */       static class MessageThread extends Thread {
                    static final int RS_ERROR_FATAL_UNKNOWN = 4096;
                    static final int RS_MESSAGE_TO_CLIENT_ERROR = 3;
                    static final int RS_MESSAGE_TO_CLIENT_EXCEPTION = 1;
                    static final int RS_MESSAGE_TO_CLIENT_NONE = 0;
                    static final int RS_MESSAGE_TO_CLIENT_RESIZE = 2;
                    static final int RS_MESSAGE_TO_CLIENT_USER = 4;
                    int[] mAuxData;
                    RenderScript mRS;
                    boolean mRun;

/* 815 */           MessageThread(RenderScript rs) {
/* 816 */               super("RSMessageThread");
/* 804 */               this.mRun = true;
/* 805 */               this.mAuxData = new int[2];
/* 817 */               this.mRS = rs;
                    }

                    @Override // java.lang.Thread, java.lang.Runnable
/* 823 */           public void run() {
/* 824 */               int[] rbuf = new int[16];
/* 825 */               this.mRS.nContextInitToClient(this.mRS.mContext);
/* 826 */               while (this.mRun) {
/* 827 */                   rbuf[0] = 0;
/* 828 */                   int msg = this.mRS.nContextPeekMessage(this.mRS.mContext, this.mAuxData);
/* 829 */                   int size = this.mAuxData[1];
/* 830 */                   int subID = this.mAuxData[0];
/* 832 */                   if (msg == 4) {
/* 833 */                       if ((size >> 2) >= rbuf.length) {
/* 834 */                           rbuf = new int[(size + 3) >> 2];
                                }
/* 836 */                       if (this.mRS.nContextGetUserMessage(this.mRS.mContext, rbuf) != 4) {
/* 838 */                           throw new RSDriverException("Error processing message from RenderScript.");
                                }
/* 841 */                       if (this.mRS.mMessageCallback != null) {
/* 842 */                           this.mRS.mMessageCallback.mData = rbuf;
/* 843 */                           this.mRS.mMessageCallback.mID = subID;
/* 844 */                           this.mRS.mMessageCallback.mLength = size;
/* 845 */                           this.mRS.mMessageCallback.run();
                                } else {
/* 847 */                           throw new RSInvalidStateException("Received a message from the script with no message handler installed.");
                                }
                            } else if (msg == 3) {
/* 853 */                       String e = this.mRS.nContextGetErrorMessage(this.mRS.mContext);
/* 855 */                       if (subID >= 4096) {
/* 856 */                           throw new RSRuntimeException("Fatal error " + subID + ", details: " + e);
                                }
/* 859 */                       if (this.mRS.mErrorCallback != null) {
/* 860 */                           this.mRS.mErrorCallback.mErrorMessage = e;
/* 861 */                           this.mRS.mErrorCallback.mErrorNum = subID;
/* 862 */                           this.mRS.mErrorCallback.run();
                                } else {
/* 864 */                           Log.e(RenderScript.LOG_TAG, "non fatal RS error, " + e);
                                }
                            } else {
                                try {
/* 876 */                           sleep(1L, 0);
                                } catch (InterruptedException e2) {
                                }
                            }
                        }
                    }
                }

/* 883 */       RenderScript(Context ctx) {
/* 885 */           if (ctx != null) {
/* 886 */               this.mApplicationContext = ctx.getApplicationContext();
                    }
                }

/* 895 */       public final Context getApplicationContext() {
/* 896 */           return this.mApplicationContext;
                }

/* 902 */       public static RenderScript create(Context ctx, int sdkVersion) {
/* 903 */           return create(ctx, sdkVersion, ContextType.NORMAL);
                }

/* 913 */       public static RenderScript create(Context ctx, int sdkVersion, ContextType ct) {
/* 914 */           RenderScript rs = new RenderScript(ctx);
/* 916 */           if (shouldThunk()) {
/* 917 */               Log.v(LOG_TAG, "RS native mode");
/* 918 */               return RenderScriptThunker.create(ctx, sdkVersion);
                    }
                    synchronized (lock) {
                        try {
/* 921 */                   if (!sInitialized) {
                                try {
/* 923 */                           System.loadLibrary("RSSupport");
/* 924 */                           System.loadLibrary("rsjni");
/* 925 */                           sInitialized = true;
                                } catch (UnsatisfiedLinkError e) {
/* 927 */                           Log.e(LOG_TAG, "Error loading RS jni library: " + e);
/* 928 */                           throw new RSRuntimeException("Error loading RS jni library: " + e);
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 933 */           Log.v(LOG_TAG, "RS compat mode");
/* 934 */           rs.mDev = rs.nDeviceCreate();
/* 935 */           rs.mContext = rs.nContextCreate(rs.mDev, 0, sdkVersion, ct.mID);
/* 936 */           if (rs.mContext == 0) {
/* 937 */               throw new RSDriverException("Failed to create RS context.");
                    }
/* 939 */           rs.mMessageThread = new MessageThread(rs);
/* 940 */           rs.mMessageThread.start();
/* 941 */           return rs;
                }

/* 950 */       public static RenderScript create(Context ctx) {
/* 951 */           return create(ctx, ContextType.NORMAL);
                }

/* 963 */       public static RenderScript create(Context ctx, ContextType ct) {
/* 964 */           int v = ctx.getApplicationInfo().targetSdkVersion;
/* 965 */           return create(ctx, v, ct);
                }

/* 973 */       public void contextDump() {
/* 974 */           validate();
/* 975 */           nContextDump(0);
                }

/* 983 */       public void finish() {
/* 984 */           nContextFinish();
                }

/* 993 */       public void destroy() {
/* 994 */           validate();
/* 995 */           nContextDeinitToClient(this.mContext);
/* 996 */           this.mMessageThread.mRun = false;
                    try {
/* 998 */               this.mMessageThread.join();
                    } catch (InterruptedException e) {
                    }
/* 1002 */          nContextDestroy();
/* 1003 */          this.mContext = 0;
/* 1005 */          nDeviceDestroy(this.mDev);
/* 1006 */          this.mDev = 0;
                }

/* 1009 */      boolean isAlive() {
                    return this.mContext != 0;
                }

/* 1013 */      int safeID(BaseObj o) {
/* 1014 */          if (o != null) {
/* 1015 */              return o.getID(this);
                    }
                    return 0;
                }
            }
