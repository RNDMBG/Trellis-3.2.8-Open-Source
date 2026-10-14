            package android.support.p001v8.renderscript;

            import android.renderscript.BaseObj;
            import android.renderscript.Type;
            import java.util.HashMap;
            
            
    class TypeThunker extends Type {
                static HashMap<Type, Type> mMap = new HashMap<>();

                
                Type f79mN;

                @Override 
          BaseObj getNObj() {
            return getNObj();
                }

                @Override 
        Type getNObj() {
            return this.f79mN;
                }

        void internalCalc() {
            this.mDimX = this.f79mN.getX();
            this.mDimY = this.f79mN.getY();
            this.mDimZ = this.f79mN.getZ();
            this.mDimFaces = this.f79mN.hasFaces();
            this.mDimMipmaps = this.f79mN.hasMipmaps();
            this.mDimYuv = this.f79mN.getYuv();
            calcElementCount();
                }

        TypeThunker(RenderScript rs, Type t) {
            super(0, rs);
            this.f79mN = t;
            internalCalc();
            this.mElement = new ElementThunker(rs, t.getElement());
                    synchronized (mMap) {
                        try {
                    mMap.put(this.f79mN, this);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

        static Type find(Type nt) {
            return mMap.get(nt);
                }

        static Type create(RenderScript rs, Element e, int dx, int dy, int dz, boolean dmip, boolean dfaces, int yuv) {
            ElementThunker et = (ElementThunker) e;
            RenderScriptThunker rst = (RenderScriptThunker) rs;
            Type.Builder tb = new Type.Builder(rst.f54mN, et.f24mN);
            if (dx > 0) {
                        tb.setX(dx);
                    }
            if (dy > 0) {
                        tb.setY(dy);
                    }
            if (dz > 0) {
                        tb.setZ(dz);
                    }
            if (dmip) {
                        tb.setMipmaps(dmip);
                    }
            if (dfaces) {
                        tb.setFaces(dfaces);
                    }
            if (yuv > 0) {
                        tb.setYuvFormat(yuv);
                    }
            Type nt = tb.create();
            TypeThunker tt = new TypeThunker(rs, nt);
            tt.internalCalc();
            return tt;
                }
            }
