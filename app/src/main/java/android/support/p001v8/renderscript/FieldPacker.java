            package android.support.p001v8.renderscript;

            import android.renderscript.Byte2;
            import android.renderscript.Byte3;
            import android.renderscript.Byte4;
            import android.renderscript.Double2;
            import android.renderscript.Double3;
            import android.renderscript.Double4;
            import android.renderscript.Float2;
            import android.renderscript.Float3;
            import android.renderscript.Float4;
            import android.renderscript.Int2;
            import android.renderscript.Int3;
            import android.renderscript.Int4;
            import android.renderscript.Long2;
            import android.renderscript.Long3;
            import android.renderscript.Long4;
            import android.renderscript.Matrix2f;
            import android.renderscript.Matrix3f;
            import android.renderscript.Matrix4f;
            import android.renderscript.Short2;
            import android.renderscript.Short3;
            import android.renderscript.Short4;
            import android.util.Log;
            
            
    public class FieldPacker {
                private final byte[] mData;
                private int mLen;

                
                private android.renderscript.FieldPacker f26mN;
                private int mPos = 0;

        public FieldPacker(int len) {
            this.mLen = len;
            this.mData = new byte[len];
            if (RenderScript.shouldThunk()) {
                this.f26mN = new android.renderscript.FieldPacker(len);
                    }
                }

        public void align(int v) {
            if (RenderScript.shouldThunk()) {
                this.f26mN.align(v);
                return;
                    }
            if (v <= 0 || ((v - 1) & v) != 0) {
                throw new RSIllegalArgumentException("argument must be a non-negative non-zero power of 2: " + v);
                    }
            while ((this.mPos & (v - 1)) != 0) {
                byte[] bArr = this.mData;
                        int i = this.mPos;
                        this.mPos = i + 1;
                        bArr[i] = 0;
                    }
                }

        public void reset() {
            if (RenderScript.shouldThunk()) {
                this.f26mN.reset();
                    } else {
                this.mPos = 0;
                    }
                }

        public void reset(int i) {
            if (RenderScript.shouldThunk()) {
                this.f26mN.reset(i);
                    } else {
                if (i < 0 || i >= this.mLen) {
                    throw new RSIllegalArgumentException("out of range argument: " + i);
                        }
                this.mPos = i;
                    }
                }

        public void skip(int i) {
            if (RenderScript.shouldThunk()) {
                this.f26mN.skip(i);
                return;
                    }
            int res = this.mPos + i;
            if (res < 0 || res > this.mLen) {
                throw new RSIllegalArgumentException("out of range argument: " + i);
                    }
            this.mPos = res;
                }

        public void addI8(byte v) {
            if (RenderScript.shouldThunk()) {
                this.f26mN.addI8(v);
                return;
                    }
            byte[] bArr = this.mData;
                    int i = this.mPos;
                    this.mPos = i + 1;
                    bArr[i] = v;
                }

        public void addI16(short v) {
            if (RenderScript.shouldThunk()) {
                this.f26mN.addI16(v);
               return;
                    }
           align(2);
           byte[] bArr = this.mData;
                    int i = this.mPos;
                    this.mPos = i + 1;
                    bArr[i] = (byte) (v & 255);
           byte[] bArr2 = this.mData;
                    int i2 = this.mPos;
                    this.mPos = i2 + 1;
                    bArr2[i2] = (byte) (v >> 8);
                }

       public void addI32(int v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addI32(v);
               return;
                    }
           align(4);
           byte[] bArr = this.mData;
                    int i = this.mPos;
                    this.mPos = i + 1;
                    bArr[i] = (byte) (v & 255);
           byte[] bArr2 = this.mData;
                    int i2 = this.mPos;
                    this.mPos = i2 + 1;
                    bArr2[i2] = (byte) ((v >> 8) & 255);
           byte[] bArr3 = this.mData;
                    int i3 = this.mPos;
                    this.mPos = i3 + 1;
                    bArr3[i3] = (byte) ((v >> 16) & 255);
           byte[] bArr4 = this.mData;
                    int i4 = this.mPos;
                    this.mPos = i4 + 1;
                    bArr4[i4] = (byte) ((v >> 24) & 255);
                }

       public void addI64(long v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addI64(v);
               return;
                    }
           align(8);
           byte[] bArr = this.mData;
                    int i = this.mPos;
                    this.mPos = i + 1;
                    bArr[i] = (byte) (v & 255);
           byte[] bArr2 = this.mData;
                    int i2 = this.mPos;
                    this.mPos = i2 + 1;
                    bArr2[i2] = (byte) ((v >> 8) & 255);
           byte[] bArr3 = this.mData;
                    int i3 = this.mPos;
                    this.mPos = i3 + 1;
                    bArr3[i3] = (byte) ((v >> 16) & 255);
           byte[] bArr4 = this.mData;
                    int i4 = this.mPos;
                    this.mPos = i4 + 1;
                    bArr4[i4] = (byte) ((v >> 24) & 255);
           byte[] bArr5 = this.mData;
                    int i5 = this.mPos;
                    this.mPos = i5 + 1;
                    bArr5[i5] = (byte) ((v >> 32) & 255);
           byte[] bArr6 = this.mData;
                    int i6 = this.mPos;
                    this.mPos = i6 + 1;
                    bArr6[i6] = (byte) ((v >> 40) & 255);
           byte[] bArr7 = this.mData;
                    int i7 = this.mPos;
                    this.mPos = i7 + 1;
                    bArr7[i7] = (byte) ((v >> 48) & 255);
           byte[] bArr8 = this.mData;
                    int i8 = this.mPos;
                    this.mPos = i8 + 1;
                    bArr8[i8] = (byte) ((v >> 56) & 255);
                }

       public void addU8(short v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addU8(v);
               return;
                    }
           if (v < 0 || v > 255) {
               throw new IllegalArgumentException("Saving value out of range for type");
                    }
           byte[] bArr = this.mData;
                    int i = this.mPos;
                    this.mPos = i + 1;
                    bArr[i] = (byte) v;
                }

       public void addU16(int v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addU16(v);
               return;
                    }
           if (v < 0 || v > 65535) {
               Log.e("rs", "FieldPacker.addU16( " + v + " )");
               throw new IllegalArgumentException("Saving value out of range for type");
                    }
           align(2);
           byte[] bArr = this.mData;
                    int i = this.mPos;
                    this.mPos = i + 1;
                    bArr[i] = (byte) (v & 255);
           byte[] bArr2 = this.mData;
                    int i2 = this.mPos;
                    this.mPos = i2 + 1;
                    bArr2[i2] = (byte) (v >> 8);
                }

       public void addU32(long v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addU32(v);
               return;
                    }
                    if (v < 0 || v > 4294967295L) {
               Log.e("rs", "FieldPacker.addU32( " + v + " )");
               throw new IllegalArgumentException("Saving value out of range for type");
                    }
           align(4);
           byte[] bArr = this.mData;
                    int i = this.mPos;
                    this.mPos = i + 1;
                    bArr[i] = (byte) (v & 255);
           byte[] bArr2 = this.mData;
                    int i2 = this.mPos;
                    this.mPos = i2 + 1;
                    bArr2[i2] = (byte) ((v >> 8) & 255);
           byte[] bArr3 = this.mData;
                    int i3 = this.mPos;
                    this.mPos = i3 + 1;
                    bArr3[i3] = (byte) ((v >> 16) & 255);
           byte[] bArr4 = this.mData;
                    int i4 = this.mPos;
                    this.mPos = i4 + 1;
                    bArr4[i4] = (byte) ((v >> 24) & 255);
                }

       public void addU64(long v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addU64(v);
               return;
                    }
                    if (v < 0) {
               Log.e("rs", "FieldPacker.addU64( " + v + " )");
               throw new IllegalArgumentException("Saving value out of range for type");
                    }
           align(8);
           byte[] bArr = this.mData;
                    int i = this.mPos;
                    this.mPos = i + 1;
                    bArr[i] = (byte) (v & 255);
           byte[] bArr2 = this.mData;
                    int i2 = this.mPos;
                    this.mPos = i2 + 1;
                    bArr2[i2] = (byte) ((v >> 8) & 255);
           byte[] bArr3 = this.mData;
                    int i3 = this.mPos;
                    this.mPos = i3 + 1;
                    bArr3[i3] = (byte) ((v >> 16) & 255);
           byte[] bArr4 = this.mData;
                    int i4 = this.mPos;
                    this.mPos = i4 + 1;
                    bArr4[i4] = (byte) ((v >> 24) & 255);
           byte[] bArr5 = this.mData;
                    int i5 = this.mPos;
                    this.mPos = i5 + 1;
                    bArr5[i5] = (byte) ((v >> 32) & 255);
           byte[] bArr6 = this.mData;
                    int i6 = this.mPos;
                    this.mPos = i6 + 1;
                    bArr6[i6] = (byte) ((v >> 40) & 255);
           byte[] bArr7 = this.mData;
                    int i7 = this.mPos;
                    this.mPos = i7 + 1;
                    bArr7[i7] = (byte) ((v >> 48) & 255);
           byte[] bArr8 = this.mData;
                    int i8 = this.mPos;
                    this.mPos = i8 + 1;
                    bArr8[i8] = (byte) ((v >> 56) & 255);
                }

       public void addF32(float v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addF32(v);
                    } else {
               addI32(Float.floatToRawIntBits(v));
                    }
                }

       public void addF64(double v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addF64(v);
                    } else {
               addI64(Double.doubleToRawLongBits(v));
                    }
                }

       public void addObj(BaseObj obj) {
           if (RenderScript.shouldThunk()) {
               if (obj != null) {
                   this.f26mN.addObj(obj.getNObj());
                   return;
                        } else {
                   this.f26mN.addObj(null);
                   return;
                        }
                    }
           if (obj != null) {
               addI32(obj.getID(null));
                    } else {
               addI32(0);
                    }
                }

       public void addF32(Float2 v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addF32(new Float2(v.f27x, v.f28y));
                    } else {
               addF32(v.f27x);
               addF32(v.f28y);
                    }
                }

       public void addF32(Float3 v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addF32(new Float3(v.f29x, v.f30y, v.f31z));
               return;
                    }
           addF32(v.f29x);
           addF32(v.f30y);
           addF32(v.f31z);
                }

       public void addF32(Float4 v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addF32(new Float4(v.f33x, v.f34y, v.f35z, v.f32w));
               return;
                    }
           addF32(v.f33x);
           addF32(v.f34y);
           addF32(v.f35z);
           addF32(v.f32w);
                }

       public void addF64(Double2 v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addF64(new Double2(v.f14x, v.f15y));
                    } else {
               addF64(v.f14x);
               addF64(v.f15y);
                    }
                }

       public void addF64(Double3 v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addF64(new Double3(v.f16x, v.f17y, v.f18z));
               return;
                    }
           addF64(v.f16x);
           addF64(v.f17y);
           addF64(v.f18z);
                }

       public void addF64(Double4 v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addF64(new Double4(v.f20x, v.f21y, v.f22z, v.f19w));
               return;
                    }
           addF64(v.f20x);
           addF64(v.f21y);
           addF64(v.f22z);
           addF64(v.f19w);
                }

       public void addI8(Byte2 v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addI8(new Byte2(v.f5x, v.f6y));
                    } else {
               addI8(v.f5x);
               addI8(v.f6y);
                    }
                }

       public void addI8(Byte3 v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addI8(new Byte3(v.f7x, v.f8y, v.f9z));
               return;
                    }
           addI8(v.f7x);
           addI8(v.f8y);
           addI8(v.f9z);
                }

       public void addI8(Byte4 v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addI8(new Byte4(v.f11x, v.f12y, v.f13z, v.f10w));
               return;
                    }
           addI8(v.f11x);
           addI8(v.f12y);
           addI8(v.f13z);
           addI8(v.f10w);
                }

       public void addU8(Short2 v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addU8(new Short2(v.f70x, v.f71y));
                    } else {
               addU8(v.f70x);
               addU8(v.f71y);
                    }
                }

       public void addU8(Short3 v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addU8(new Short3(v.f72x, v.f73y, v.f74z));
               return;
                    }
           addU8(v.f72x);
           addU8(v.f73y);
           addU8(v.f74z);
                }

       public void addU8(Short4 v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addU8(new Short4(v.f76x, v.f77y, v.f78z, v.f75w));
               return;
                    }
           addU8(v.f76x);
           addU8(v.f77y);
           addU8(v.f78z);
           addU8(v.f75w);
                }

       public void addI16(Short2 v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addI16(new Short2(v.f70x, v.f71y));
                    } else {
               addI16(v.f70x);
               addI16(v.f71y);
                    }
                }

       public void addI16(Short3 v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addI16(new Short3(v.f72x, v.f73y, v.f74z));
               return;
                    }
           addI16(v.f72x);
           addI16(v.f73y);
           addI16(v.f74z);
                }

       public void addI16(Short4 v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addI16(new Short4(v.f76x, v.f77y, v.f78z, v.f75w));
               return;
                    }
           addI16(v.f76x);
           addI16(v.f77y);
           addI16(v.f78z);
           addI16(v.f75w);
                }

       public void addU16(Int2 v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addU16(new Int2(v.f36x, v.f37y));
                    } else {
               addU16(v.f36x);
               addU16(v.f37y);
                    }
                }

       public void addU16(Int3 v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addU16(new Int3(v.f38x, v.f39y, v.f40z));
               return;
                    }
           addU16(v.f38x);
           addU16(v.f39y);
           addU16(v.f40z);
                }

       public void addU16(Int4 v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addU16(new Int4(v.f42x, v.f43y, v.f44z, v.f41w));
               return;
                    }
           addU16(v.f42x);
           addU16(v.f43y);
           addU16(v.f44z);
           addU16(v.f41w);
                }

       public void addI32(Int2 v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addI32(new Int2(v.f36x, v.f37y));
                    } else {
               addI32(v.f36x);
               addI32(v.f37y);
                    }
                }

       public void addI32(Int3 v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addI32(new Int3(v.f38x, v.f39y, v.f40z));
               return;
                    }
           addI32(v.f38x);
           addI32(v.f39y);
           addI32(v.f40z);
                }

       public void addI32(Int4 v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addI32(new Int4(v.f42x, v.f43y, v.f44z, v.f41w));
               return;
                    }
           addI32(v.f42x);
           addI32(v.f43y);
           addI32(v.f44z);
           addI32(v.f41w);
                }

       public void addU32(Long2 v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addU32(new Long2(v.f45x, v.f46y));
                    } else {
               addU32(v.f45x);
               addU32(v.f46y);
                    }
                }

       public void addU32(Long3 v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addU32(new Long3(v.f47x, v.f48y, v.f49z));
               return;
                    }
           addU32(v.f47x);
           addU32(v.f48y);
           addU32(v.f49z);
                }

       public void addU32(Long4 v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addU32(new Long4(v.f51x, v.f52y, v.f53z, v.f50w));
               return;
                    }
           addU32(v.f51x);
           addU32(v.f52y);
           addU32(v.f53z);
           addU32(v.f50w);
                }

       public void addI64(Long2 v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addI64(new Long2(v.f45x, v.f46y));
                    } else {
               addI64(v.f45x);
               addI64(v.f46y);
                    }
                }

       public void addI64(Long3 v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addI64(new Long3(v.f47x, v.f48y, v.f49z));
               return;
                    }
           addI64(v.f47x);
           addI64(v.f48y);
           addI64(v.f49z);
                }

       public void addI64(Long4 v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addI64(new Long4(v.f51x, v.f52y, v.f53z, v.f50w));
               return;
                    }
           addI64(v.f51x);
           addI64(v.f52y);
           addI64(v.f53z);
           addI64(v.f50w);
                }

       public void addU64(Long2 v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addU64(new Long2(v.f45x, v.f46y));
                    } else {
               addU64(v.f45x);
               addU64(v.f46y);
                    }
                }

       public void addU64(Long3 v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addU64(new Long3(v.f47x, v.f48y, v.f49z));
               return;
                    }
           addU64(v.f47x);
           addU64(v.f48y);
           addU64(v.f49z);
                }

       public void addU64(Long4 v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addU64(new Long4(v.f51x, v.f52y, v.f53z, v.f50w));
               return;
                    }
           addU64(v.f51x);
           addU64(v.f52y);
           addU64(v.f53z);
           addU64(v.f50w);
                }

       public void addMatrix(Matrix4f v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addMatrix(new Matrix4f(v.getArray()));
               return;
                    }
                    for (int i = 0; i < v.mMat.length; i++) {
               addF32(v.mMat[i]);
                    }
                }

       public void addMatrix(Matrix3f v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addMatrix(new Matrix3f(v.getArray()));
               return;
                    }
                    for (int i = 0; i < v.mMat.length; i++) {
               addF32(v.mMat[i]);
                    }
                }

       public void addMatrix(Matrix2f v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addMatrix(new Matrix2f(v.getArray()));
               return;
                    }
                    for (int i = 0; i < v.mMat.length; i++) {
               addF32(v.mMat[i]);
                    }
                }

       public void addBoolean(boolean v) {
           if (RenderScript.shouldThunk()) {
               this.f26mN.addBoolean(v);
                    } else {
                        addI8((byte) (v ? 1 : 0));
                    }
                }

       public final byte[] getData() {
                    return RenderScript.shouldThunk() ? this.f26mN.getData() : this.mData;
                }
            }
