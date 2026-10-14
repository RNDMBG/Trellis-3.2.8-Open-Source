            package android.support.p001v8.renderscript;

            import android.content.res.Resources;
            import android.graphics.Bitmap;
            import android.graphics.BitmapFactory;
            import android.renderscript.Allocation;
            import android.renderscript.BaseObj;
            import android.renderscript.Element;
            import android.renderscript.FieldPacker;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 27 */    class AllocationThunker extends Allocation {
                static BitmapFactory.Options mBitmapOptions = new BitmapFactory.Options();

                /* JADX INFO: renamed from: mN */
                Allocation f3mN;

                @Override // android.support.p001v8.renderscript.BaseObj
/* 28 */        /* bridge */ /* synthetic */ BaseObj getNObj() {
/* 29 */            return getNObj();
                }

                @Override // android.support.p001v8.renderscript.BaseObj
/* 33 */        Allocation getNObj() {
/* 34 */            return this.f3mN;
                }

/* 39 */        static Allocation.MipmapControl convertMipmapControl(Allocation.MipmapControl mc) {
                    switch (mc) {
                        case MIPMAP_NONE:
/* 42 */                    return Allocation.MipmapControl.MIPMAP_NONE;
                        case MIPMAP_FULL:
/* 44 */                    return Allocation.MipmapControl.MIPMAP_FULL;
                        case MIPMAP_ON_SYNC_TO_TEXTURE:
/* 46 */                    return Allocation.MipmapControl.MIPMAP_ON_SYNC_TO_TEXTURE;
                        default:
/* 48 */                    return null;
                    }
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 51 */        public Type getType() {
/* 52 */            return TypeThunker.find(this.f3mN.getType());
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 55 */        public Element getElement() {
/* 56 */            return getType().getElement();
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 59 */        public int getUsage() {
/* 60 */            return this.f3mN.getUsage();
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 63 */        public int getBytesSize() {
/* 64 */            return this.f3mN.getBytesSize();
                }

/* 67 */        AllocationThunker(RenderScript rs, Type t, int usage, Allocation na) {
/* 68 */            super(0, rs, t, usage);
/* 70 */            this.mType = t;
/* 71 */            this.mUsage = usage;
/* 72 */            this.f3mN = na;
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 75 */        public void syncAll(int srcLocation) {
/* 76 */            this.f3mN.syncAll(srcLocation);
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 79 */        public void ioSend() {
/* 80 */            this.f3mN.ioSend();
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 83 */        public void ioReceive() {
/* 84 */            this.f3mN.ioReceive();
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 87 */        public void copyFrom(BaseObj[] d) {
/* 88 */            if (d != null) {
/* 91 */                BaseObj[] dN = new BaseObj[d.length];
                        for (int i = 0; i < d.length; i++) {
/* 93 */                    dN[i] = d[i].getNObj();
                        }
/* 95 */                this.f3mN.copyFrom(dN);
                    }
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 98 */        public void copyFromUnchecked(int[] d) {
/* 99 */            this.f3mN.copyFromUnchecked(d);
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 101 */       public void copyFromUnchecked(short[] d) {
/* 102 */           this.f3mN.copyFromUnchecked(d);
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 104 */       public void copyFromUnchecked(byte[] d) {
/* 105 */           this.f3mN.copyFromUnchecked(d);
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 107 */       public void copyFromUnchecked(float[] d) {
/* 108 */           this.f3mN.copyFromUnchecked(d);
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 111 */       public void copyFrom(int[] d) {
/* 112 */           this.f3mN.copyFrom(d);
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 114 */       public void copyFrom(short[] d) {
/* 115 */           this.f3mN.copyFrom(d);
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 117 */       public void copyFrom(byte[] d) {
/* 118 */           this.f3mN.copyFrom(d);
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 120 */       public void copyFrom(float[] d) {
/* 121 */           this.f3mN.copyFrom(d);
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 123 */       public void copyFrom(Bitmap b) {
/* 124 */           this.f3mN.copyFrom(b);
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 126 */       public void copyFrom(Allocation a) {
/* 127 */           AllocationThunker at = (AllocationThunker) a;
/* 128 */           this.f3mN.copyFrom(at.f3mN);
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 132 */       public void setFromFieldPacker(int xoff, FieldPacker fp) {
/* 133 */           FieldPacker nfp = new FieldPacker(fp.getData());
/* 135 */           this.f3mN.setFromFieldPacker(xoff, nfp);
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 137 */       public void setFromFieldPacker(int xoff, int component_number, FieldPacker fp) {
/* 138 */           FieldPacker nfp = new FieldPacker(fp.getData());
/* 140 */           this.f3mN.setFromFieldPacker(xoff, component_number, nfp);
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 143 */       public void generateMipmaps() {
/* 144 */           this.f3mN.generateMipmaps();
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 147 */       public void copy1DRangeFromUnchecked(int off, int count, int[] d) {
/* 148 */           this.f3mN.copy1DRangeFromUnchecked(off, count, d);
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 150 */       public void copy1DRangeFromUnchecked(int off, int count, short[] d) {
/* 151 */           this.f3mN.copy1DRangeFromUnchecked(off, count, d);
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 153 */       public void copy1DRangeFromUnchecked(int off, int count, byte[] d) {
/* 154 */           this.f3mN.copy1DRangeFromUnchecked(off, count, d);
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 156 */       public void copy1DRangeFromUnchecked(int off, int count, float[] d) {
/* 157 */           this.f3mN.copy1DRangeFromUnchecked(off, count, d);
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 160 */       public void copy1DRangeFrom(int off, int count, int[] d) {
/* 161 */           this.f3mN.copy1DRangeFrom(off, count, d);
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 163 */       public void copy1DRangeFrom(int off, int count, short[] d) {
/* 164 */           this.f3mN.copy1DRangeFrom(off, count, d);
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 166 */       public void copy1DRangeFrom(int off, int count, byte[] d) {
/* 167 */           this.f3mN.copy1DRangeFrom(off, count, d);
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 169 */       public void copy1DRangeFrom(int off, int count, float[] d) {
/* 170 */           this.f3mN.copy1DRangeFrom(off, count, d);
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 173 */       public void copy1DRangeFrom(int off, int count, Allocation data, int dataOff) {
/* 174 */           AllocationThunker at = (AllocationThunker) data;
/* 175 */           this.f3mN.copy1DRangeFrom(off, count, at.f3mN, dataOff);
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 178 */       public void copy2DRangeFrom(int xoff, int yoff, int w, int h, byte[] data) {
/* 179 */           this.f3mN.copy2DRangeFrom(xoff, yoff, w, h, data);
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 181 */       public void copy2DRangeFrom(int xoff, int yoff, int w, int h, short[] data) {
/* 182 */           this.f3mN.copy2DRangeFrom(xoff, yoff, w, h, data);
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 184 */       public void copy2DRangeFrom(int xoff, int yoff, int w, int h, int[] data) {
/* 185 */           this.f3mN.copy2DRangeFrom(xoff, yoff, w, h, data);
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 187 */       public void copy2DRangeFrom(int xoff, int yoff, int w, int h, float[] data) {
/* 188 */           this.f3mN.copy2DRangeFrom(xoff, yoff, w, h, data);
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 192 */       public void copy2DRangeFrom(int xoff, int yoff, int w, int h, Allocation data, int dataXoff, int dataYoff) {
/* 193 */           AllocationThunker at = (AllocationThunker) data;
/* 194 */           this.f3mN.copy2DRangeFrom(xoff, yoff, w, h, at.f3mN, dataXoff, dataYoff);
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 196 */       public void copy2DRangeFrom(int xoff, int yoff, Bitmap data) {
/* 197 */           this.f3mN.copy2DRangeFrom(xoff, yoff, data);
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 201 */       public void copyTo(Bitmap b) {
/* 202 */           this.f3mN.copyTo(b);
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 204 */       public void copyTo(byte[] d) {
/* 205 */           this.f3mN.copyTo(d);
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 207 */       public void copyTo(short[] d) {
/* 208 */           this.f3mN.copyTo(d);
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 210 */       public void copyTo(int[] d) {
/* 211 */           this.f3mN.copyTo(d);
                }

                @Override // android.support.p001v8.renderscript.Allocation
/* 213 */       public void copyTo(float[] d) {
/* 214 */           this.f3mN.copyTo(d);
                }

                static {
/* 221 */           mBitmapOptions.inScaled = false;
                }

/* 224 */       public static Allocation createTyped(RenderScript rs, Type type, Allocation.MipmapControl mips, int usage) {
/* 225 */           RenderScriptThunker rst = (RenderScriptThunker) rs;
/* 226 */           TypeThunker tt = (TypeThunker) type;
/* 228 */           Allocation a = Allocation.createTyped(rst.f54mN, tt.f79mN, convertMipmapControl(mips), usage);
/* 232 */           return new AllocationThunker(rs, type, usage, a);
                }

/* 238 */       public static Allocation createFromBitmap(RenderScript rs, Bitmap b, Allocation.MipmapControl mips, int usage) {
/* 239 */           RenderScriptThunker rst = (RenderScriptThunker) rs;
/* 240 */           Allocation a = Allocation.createFromBitmap(rst.f54mN, b, convertMipmapControl(mips), usage);
/* 243 */           TypeThunker tt = new TypeThunker(rs, a.getType());
/* 245 */           return new AllocationThunker(rs, tt, usage, a);
                }

/* 250 */       public static Allocation createCubemapFromBitmap(RenderScript rs, Bitmap b, Allocation.MipmapControl mips, int usage) {
/* 251 */           RenderScriptThunker rst = (RenderScriptThunker) rs;
/* 252 */           Allocation a = Allocation.createCubemapFromBitmap(rst.f54mN, b, convertMipmapControl(mips), usage);
/* 255 */           TypeThunker tt = new TypeThunker(rs, a.getType());
/* 256 */           return new AllocationThunker(rs, tt, usage, a);
                }

/* 267 */       public static Allocation createCubemapFromCubeFaces(RenderScript rs, Bitmap xpos, Bitmap xneg, Bitmap ypos, Bitmap yneg, Bitmap zpos, Bitmap zneg, Allocation.MipmapControl mips, int usage) {
/* 268 */           RenderScriptThunker rst = (RenderScriptThunker) rs;
/* 269 */           Allocation a = Allocation.createCubemapFromCubeFaces(rst.f54mN, xpos, xneg, ypos, yneg, zpos, zneg, convertMipmapControl(mips), usage);
/* 273 */           TypeThunker tt = new TypeThunker(rs, a.getType());
/* 274 */           return new AllocationThunker(rs, tt, usage, a);
                }

/* 282 */       public static Allocation createFromBitmapResource(RenderScript rs, Resources res, int id, Allocation.MipmapControl mips, int usage) {
/* 283 */           RenderScriptThunker rst = (RenderScriptThunker) rs;
/* 284 */           Allocation a = Allocation.createFromBitmapResource(rst.f54mN, res, id, convertMipmapControl(mips), usage);
/* 287 */           TypeThunker tt = new TypeThunker(rs, a.getType());
/* 288 */           return new AllocationThunker(rs, tt, usage, a);
                }

/* 293 */       public static Allocation createFromString(RenderScript rs, String str, int usage) {
/* 294 */           RenderScriptThunker rst = (RenderScriptThunker) rs;
/* 295 */           Allocation a = Allocation.createFromString(rst.f54mN, str, usage);
/* 298 */           TypeThunker tt = new TypeThunker(rs, a.getType());
/* 299 */           return new AllocationThunker(rs, tt, usage, a);
                }

/* 303 */       public static Allocation createSized(RenderScript rs, Element e, int count, int usage) {
/* 304 */           RenderScriptThunker rst = (RenderScriptThunker) rs;
/* 306 */           Allocation a = Allocation.createSized(rst.f54mN, (Element) e.getNObj(), count, usage);
/* 308 */           TypeThunker tt = new TypeThunker(rs, a.getType());
/* 309 */           return new AllocationThunker(rs, tt, usage, a);
                }
            }
