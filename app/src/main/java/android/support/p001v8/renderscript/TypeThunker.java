            package android.support.p001v8.renderscript;

            import android.renderscript.BaseObj;
            import android.renderscript.Type;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    class TypeThunker extends Type {
                static HashMap<Type, Type> mMap = new HashMap<>();

                /* JADX INFO: renamed from: mN */
                Type f79mN;

                @Override // android.support.p001v8.renderscript.BaseObj
/* 22 */        /* bridge */ /* synthetic */ BaseObj getNObj() {
/* 23 */            return getNObj();
                }

                @Override // android.support.p001v8.renderscript.BaseObj
/* 26 */        Type getNObj() {
/* 27 */            return this.f79mN;
                }

/* 32 */        void internalCalc() {
/* 33 */            this.mDimX = this.f79mN.getX();
/* 34 */            this.mDimY = this.f79mN.getY();
/* 35 */            this.mDimZ = this.f79mN.getZ();
/* 36 */            this.mDimFaces = this.f79mN.hasFaces();
/* 37 */            this.mDimMipmaps = this.f79mN.hasMipmaps();
/* 38 */            this.mDimYuv = this.f79mN.getYuv();
/* 39 */            calcElementCount();
                }

/* 42 */        TypeThunker(RenderScript rs, Type t) {
/* 43 */            super(0, rs);
/* 44 */            this.f79mN = t;
/* 45 */            internalCalc();
/* 46 */            this.mElement = new ElementThunker(rs, t.getElement());
                    synchronized (mMap) {
                        try {
/* 49 */                    mMap.put(this.f79mN, this);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 53 */        static Type find(Type nt) {
/* 54 */            return mMap.get(nt);
                }

/* 58 */        static Type create(RenderScript rs, Element e, int dx, int dy, int dz, boolean dmip, boolean dfaces, int yuv) {
/* 59 */            ElementThunker et = (ElementThunker) e;
/* 60 */            RenderScriptThunker rst = (RenderScriptThunker) rs;
/* 61 */            Type.Builder tb = new Type.Builder(rst.f54mN, et.f24mN);
/* 63 */            if (dx > 0) {
                        tb.setX(dx);
                    }
/* 64 */            if (dy > 0) {
                        tb.setY(dy);
                    }
/* 65 */            if (dz > 0) {
                        tb.setZ(dz);
                    }
/* 66 */            if (dmip) {
                        tb.setMipmaps(dmip);
                    }
/* 67 */            if (dfaces) {
                        tb.setFaces(dfaces);
                    }
/* 68 */            if (yuv > 0) {
                        tb.setYuvFormat(yuv);
                    }
/* 69 */            Type nt = tb.create();
/* 70 */            TypeThunker tt = new TypeThunker(rs, nt);
/* 71 */            tt.internalCalc();
/* 73 */            return tt;
                }
            }
