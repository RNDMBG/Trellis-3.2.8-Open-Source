            package android.support.p001v8.renderscript;

            import android.content.res.Resources;
            import android.graphics.Bitmap;
            import android.graphics.BitmapFactory;
            import android.renderscript.Allocation;
            import android.renderscript.BaseObj;
            import android.renderscript.Element;
            import android.renderscript.FieldPacker;
            
            
    class AllocationThunker extends Allocation {
                static BitmapFactory.Options mBitmapOptions = new BitmapFactory.Options();

                
                Allocation f3mN;

                @Override 
          BaseObj getNObj() {
            return getNObj();
                }

                @Override 
        Allocation getNObj() {
            return this.f3mN;
                }

        static Allocation.MipmapControl convertMipmapControl(Allocation.MipmapControl mc) {
                    switch (mc) {
                        case MIPMAP_NONE:
                    return Allocation.MipmapControl.MIPMAP_NONE;
                        case MIPMAP_FULL:
                    return Allocation.MipmapControl.MIPMAP_FULL;
                        case MIPMAP_ON_SYNC_TO_TEXTURE:
                    return Allocation.MipmapControl.MIPMAP_ON_SYNC_TO_TEXTURE;
                        default:
                    return null;
                    }
                }

                @Override 
        public Type getType() {
            return TypeThunker.find(this.f3mN.getType());
                }

                @Override 
        public Element getElement() {
            return getType().getElement();
                }

                @Override 
        public int getUsage() {
            return this.f3mN.getUsage();
                }

                @Override 
        public int getBytesSize() {
            return this.f3mN.getBytesSize();
                }

        AllocationThunker(RenderScript rs, Type t, int usage, Allocation na) {
            super(0, rs, t, usage);
            this.mType = t;
            this.mUsage = usage;
            this.f3mN = na;
                }

                @Override 
        public void syncAll(int srcLocation) {
            this.f3mN.syncAll(srcLocation);
                }

                @Override 
        public void ioSend() {
            this.f3mN.ioSend();
                }

                @Override 
        public void ioReceive() {
            this.f3mN.ioReceive();
                }

                @Override 
        public void copyFrom(BaseObj[] d) {
            if (d != null) {
                BaseObj[] dN = new BaseObj[d.length];
                        for (int i = 0; i < d.length; i++) {
                    dN[i] = d[i].getNObj();
                        }
                this.f3mN.copyFrom(dN);
                    }
                }

                @Override 
        public void copyFromUnchecked(int[] d) {
            this.f3mN.copyFromUnchecked(d);
                }

                @Override 
       public void copyFromUnchecked(short[] d) {
           this.f3mN.copyFromUnchecked(d);
                }

                @Override 
       public void copyFromUnchecked(byte[] d) {
           this.f3mN.copyFromUnchecked(d);
                }

                @Override 
       public void copyFromUnchecked(float[] d) {
           this.f3mN.copyFromUnchecked(d);
                }

                @Override 
       public void copyFrom(int[] d) {
           this.f3mN.copyFrom(d);
                }

                @Override 
       public void copyFrom(short[] d) {
           this.f3mN.copyFrom(d);
                }

                @Override 
       public void copyFrom(byte[] d) {
           this.f3mN.copyFrom(d);
                }

                @Override 
       public void copyFrom(float[] d) {
           this.f3mN.copyFrom(d);
                }

                @Override 
       public void copyFrom(Bitmap b) {
           this.f3mN.copyFrom(b);
                }

                @Override 
       public void copyFrom(Allocation a) {
           AllocationThunker at = (AllocationThunker) a;
           this.f3mN.copyFrom(at.f3mN);
                }

                @Override 
       public void setFromFieldPacker(int xoff, FieldPacker fp) {
           FieldPacker nfp = new FieldPacker(fp.getData());
           this.f3mN.setFromFieldPacker(xoff, nfp);
                }

                @Override 
       public void setFromFieldPacker(int xoff, int component_number, FieldPacker fp) {
           FieldPacker nfp = new FieldPacker(fp.getData());
           this.f3mN.setFromFieldPacker(xoff, component_number, nfp);
                }

                @Override 
       public void generateMipmaps() {
           this.f3mN.generateMipmaps();
                }

                @Override 
       public void copy1DRangeFromUnchecked(int off, int count, int[] d) {
           this.f3mN.copy1DRangeFromUnchecked(off, count, d);
                }

                @Override 
       public void copy1DRangeFromUnchecked(int off, int count, short[] d) {
           this.f3mN.copy1DRangeFromUnchecked(off, count, d);
                }

                @Override 
       public void copy1DRangeFromUnchecked(int off, int count, byte[] d) {
           this.f3mN.copy1DRangeFromUnchecked(off, count, d);
                }

                @Override 
       public void copy1DRangeFromUnchecked(int off, int count, float[] d) {
           this.f3mN.copy1DRangeFromUnchecked(off, count, d);
                }

                @Override 
       public void copy1DRangeFrom(int off, int count, int[] d) {
           this.f3mN.copy1DRangeFrom(off, count, d);
                }

                @Override 
       public void copy1DRangeFrom(int off, int count, short[] d) {
           this.f3mN.copy1DRangeFrom(off, count, d);
                }

                @Override 
       public void copy1DRangeFrom(int off, int count, byte[] d) {
           this.f3mN.copy1DRangeFrom(off, count, d);
                }

                @Override 
       public void copy1DRangeFrom(int off, int count, float[] d) {
           this.f3mN.copy1DRangeFrom(off, count, d);
                }

                @Override 
       public void copy1DRangeFrom(int off, int count, Allocation data, int dataOff) {
           AllocationThunker at = (AllocationThunker) data;
           this.f3mN.copy1DRangeFrom(off, count, at.f3mN, dataOff);
                }

                @Override 
       public void copy2DRangeFrom(int xoff, int yoff, int w, int h, byte[] data) {
           this.f3mN.copy2DRangeFrom(xoff, yoff, w, h, data);
                }

                @Override 
       public void copy2DRangeFrom(int xoff, int yoff, int w, int h, short[] data) {
           this.f3mN.copy2DRangeFrom(xoff, yoff, w, h, data);
                }

                @Override 
       public void copy2DRangeFrom(int xoff, int yoff, int w, int h, int[] data) {
           this.f3mN.copy2DRangeFrom(xoff, yoff, w, h, data);
                }

                @Override 
       public void copy2DRangeFrom(int xoff, int yoff, int w, int h, float[] data) {
           this.f3mN.copy2DRangeFrom(xoff, yoff, w, h, data);
                }

                @Override 
       public void copy2DRangeFrom(int xoff, int yoff, int w, int h, Allocation data, int dataXoff, int dataYoff) {
           AllocationThunker at = (AllocationThunker) data;
           this.f3mN.copy2DRangeFrom(xoff, yoff, w, h, at.f3mN, dataXoff, dataYoff);
                }

                @Override 
       public void copy2DRangeFrom(int xoff, int yoff, Bitmap data) {
           this.f3mN.copy2DRangeFrom(xoff, yoff, data);
                }

                @Override 
       public void copyTo(Bitmap b) {
           this.f3mN.copyTo(b);
                }

                @Override 
       public void copyTo(byte[] d) {
           this.f3mN.copyTo(d);
                }

                @Override 
       public void copyTo(short[] d) {
           this.f3mN.copyTo(d);
                }

                @Override 
       public void copyTo(int[] d) {
           this.f3mN.copyTo(d);
                }

                @Override 
       public void copyTo(float[] d) {
           this.f3mN.copyTo(d);
                }

                static {
           mBitmapOptions.inScaled = false;
                }

       public static Allocation createTyped(RenderScript rs, Type type, Allocation.MipmapControl mips, int usage) {
           RenderScriptThunker rst = (RenderScriptThunker) rs;
           TypeThunker tt = (TypeThunker) type;
           Allocation a = Allocation.createTyped(rst.f54mN, tt.f79mN, convertMipmapControl(mips), usage);
           return new AllocationThunker(rs, type, usage, a);
                }

       public static Allocation createFromBitmap(RenderScript rs, Bitmap b, Allocation.MipmapControl mips, int usage) {
           RenderScriptThunker rst = (RenderScriptThunker) rs;
           Allocation a = Allocation.createFromBitmap(rst.f54mN, b, convertMipmapControl(mips), usage);
           TypeThunker tt = new TypeThunker(rs, a.getType());
           return new AllocationThunker(rs, tt, usage, a);
                }

       public static Allocation createCubemapFromBitmap(RenderScript rs, Bitmap b, Allocation.MipmapControl mips, int usage) {
           RenderScriptThunker rst = (RenderScriptThunker) rs;
           Allocation a = Allocation.createCubemapFromBitmap(rst.f54mN, b, convertMipmapControl(mips), usage);
           TypeThunker tt = new TypeThunker(rs, a.getType());
           return new AllocationThunker(rs, tt, usage, a);
                }

       public static Allocation createCubemapFromCubeFaces(RenderScript rs, Bitmap xpos, Bitmap xneg, Bitmap ypos, Bitmap yneg, Bitmap zpos, Bitmap zneg, Allocation.MipmapControl mips, int usage) {
           RenderScriptThunker rst = (RenderScriptThunker) rs;
           Allocation a = Allocation.createCubemapFromCubeFaces(rst.f54mN, xpos, xneg, ypos, yneg, zpos, zneg, convertMipmapControl(mips), usage);
           TypeThunker tt = new TypeThunker(rs, a.getType());
           return new AllocationThunker(rs, tt, usage, a);
                }

       public static Allocation createFromBitmapResource(RenderScript rs, Resources res, int id, Allocation.MipmapControl mips, int usage) {
           RenderScriptThunker rst = (RenderScriptThunker) rs;
           Allocation a = Allocation.createFromBitmapResource(rst.f54mN, res, id, convertMipmapControl(mips), usage);
           TypeThunker tt = new TypeThunker(rs, a.getType());
           return new AllocationThunker(rs, tt, usage, a);
                }

       public static Allocation createFromString(RenderScript rs, String str, int usage) {
           RenderScriptThunker rst = (RenderScriptThunker) rs;
           Allocation a = Allocation.createFromString(rst.f54mN, str, usage);
           TypeThunker tt = new TypeThunker(rs, a.getType());
           return new AllocationThunker(rs, tt, usage, a);
                }

       public static Allocation createSized(RenderScript rs, Element e, int count, int usage) {
           RenderScriptThunker rst = (RenderScriptThunker) rs;
           Allocation a = Allocation.createSized(rst.f54mN, (Element) e.getNObj(), count, usage);
           TypeThunker tt = new TypeThunker(rs, a.getType());
           return new AllocationThunker(rs, tt, usage, a);
                }
            }
