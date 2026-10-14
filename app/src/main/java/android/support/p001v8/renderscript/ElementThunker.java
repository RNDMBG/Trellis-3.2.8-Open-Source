            package android.support.p001v8.renderscript;

            import android.renderscript.BaseObj;
            import android.renderscript.Element;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    class ElementThunker extends Element {

                /* JADX INFO: renamed from: mN */
                Element f24mN;

                @Override // android.support.p001v8.renderscript.BaseObj
/* 22 */        /* bridge */ /* synthetic */ BaseObj getNObj() {
/* 23 */            return getNObj();
                }

                @Override // android.support.p001v8.renderscript.BaseObj
/* 26 */        Element getNObj() {
/* 27 */            return this.f24mN;
                }

                @Override // android.support.p001v8.renderscript.Element
/* 30 */        public int getBytesSize() {
/* 31 */            return this.f24mN.getBytesSize();
                }

                @Override // android.support.p001v8.renderscript.Element
/* 34 */        public int getVectorSize() {
/* 35 */            return this.f24mN.getVectorSize();
                }

/* 38 */        static Element.DataKind convertKind(Element.DataKind cdk) {
                    switch (cdk) {
                        case USER:
/* 41 */                    return Element.DataKind.USER;
                        case PIXEL_L:
/* 43 */                    return Element.DataKind.PIXEL_L;
                        case PIXEL_A:
/* 45 */                    return Element.DataKind.PIXEL_A;
                        case PIXEL_LA:
/* 47 */                    return Element.DataKind.PIXEL_LA;
                        case PIXEL_RGB:
/* 49 */                    return Element.DataKind.PIXEL_RGB;
                        case PIXEL_RGBA:
/* 51 */                    return Element.DataKind.PIXEL_RGBA;
                        default:
/* 53 */                    return null;
                    }
                }

/* 56 */        static Element.DataType convertType(Element.DataType cdt) {
                    switch (cdt) {
                        case NONE:
/* 59 */                    return Element.DataType.NONE;
                        case FLOAT_32:
/* 62 */                    return Element.DataType.FLOAT_32;
                        case FLOAT_64:
/* 64 */                    return Element.DataType.FLOAT_64;
                        case SIGNED_8:
/* 66 */                    return Element.DataType.SIGNED_8;
                        case SIGNED_16:
/* 68 */                    return Element.DataType.SIGNED_16;
                        case SIGNED_32:
/* 70 */                    return Element.DataType.SIGNED_32;
                        case SIGNED_64:
/* 72 */                    return Element.DataType.SIGNED_64;
                        case UNSIGNED_8:
/* 74 */                    return Element.DataType.UNSIGNED_8;
                        case UNSIGNED_16:
/* 76 */                    return Element.DataType.UNSIGNED_16;
                        case UNSIGNED_32:
/* 78 */                    return Element.DataType.UNSIGNED_32;
                        case UNSIGNED_64:
/* 80 */                    return Element.DataType.UNSIGNED_64;
                        case BOOLEAN:
/* 83 */                    return Element.DataType.BOOLEAN;
                        case MATRIX_4X4:
/* 86 */                    return Element.DataType.MATRIX_4X4;
                        case MATRIX_3X3:
/* 88 */                    return Element.DataType.MATRIX_3X3;
                        case MATRIX_2X2:
/* 90 */                    return Element.DataType.MATRIX_2X2;
                        case RS_ELEMENT:
/* 93 */                    return Element.DataType.RS_ELEMENT;
                        case RS_TYPE:
/* 95 */                    return Element.DataType.RS_TYPE;
                        case RS_ALLOCATION:
/* 97 */                    return Element.DataType.RS_ALLOCATION;
                        case RS_SAMPLER:
/* 99 */                    return Element.DataType.RS_SAMPLER;
                        case RS_SCRIPT:
/* 101 */                   return Element.DataType.RS_SCRIPT;
                        default:
/* 103 */                   return null;
                    }
                }

                @Override // android.support.p001v8.renderscript.Element
/* 106 */       public boolean isComplex() {
/* 107 */           return this.f24mN.isComplex();
                }

                @Override // android.support.p001v8.renderscript.Element
/* 110 */       public int getSubElementCount() {
/* 111 */           return this.f24mN.getSubElementCount();
                }

                @Override // android.support.p001v8.renderscript.Element
/* 114 */       public Element getSubElement(int index) {
/* 115 */           return new ElementThunker(this.mRS, this.f24mN.getSubElement(index));
                }

                @Override // android.support.p001v8.renderscript.Element
/* 118 */       public String getSubElementName(int index) {
/* 119 */           return this.f24mN.getSubElementName(index);
                }

                @Override // android.support.p001v8.renderscript.Element
/* 122 */       public int getSubElementArraySize(int index) {
/* 123 */           return this.f24mN.getSubElementArraySize(index);
                }

                @Override // android.support.p001v8.renderscript.Element
/* 126 */       public int getSubElementOffsetBytes(int index) {
/* 127 */           return this.f24mN.getSubElementOffsetBytes(index);
                }

                @Override // android.support.p001v8.renderscript.Element
/* 130 */       public Element.DataType getDataType() {
/* 131 */           return this.mType;
                }

                @Override // android.support.p001v8.renderscript.Element
/* 134 */       public Element.DataKind getDataKind() {
/* 135 */           return this.mKind;
                }

/* 139 */       ElementThunker(RenderScript rs, Element e) {
/* 140 */           super(0, rs);
/* 141 */           this.f24mN = e;
                }

/* 145 */       static Element create(RenderScript rs, Element.DataType dt) {
/* 146 */           RenderScriptThunker rst = (RenderScriptThunker) rs;
/* 147 */           Element e = null;
                    switch (dt) {
                        case FLOAT_32:
/* 150 */                   e = Element.F32(rst.f54mN);
                            break;
                        case FLOAT_64:
/* 153 */                   e = Element.F64(rst.f54mN);
                            break;
                        case SIGNED_8:
/* 156 */                   e = Element.I8(rst.f54mN);
                            break;
                        case SIGNED_16:
/* 159 */                   e = Element.I16(rst.f54mN);
                            break;
                        case SIGNED_32:
/* 162 */                   e = Element.I32(rst.f54mN);
                            break;
                        case SIGNED_64:
/* 165 */                   e = Element.I64(rst.f54mN);
                            break;
                        case UNSIGNED_8:
/* 168 */                   e = Element.U8(rst.f54mN);
                            break;
                        case UNSIGNED_16:
/* 171 */                   e = Element.U16(rst.f54mN);
                            break;
                        case UNSIGNED_32:
/* 174 */                   e = Element.U32(rst.f54mN);
                            break;
                        case UNSIGNED_64:
/* 177 */                   e = Element.U64(rst.f54mN);
                            break;
                        case BOOLEAN:
/* 181 */                   e = Element.BOOLEAN(rst.f54mN);
                            break;
                        case MATRIX_4X4:
/* 185 */                   e = Element.MATRIX_4X4(rst.f54mN);
                            break;
                        case MATRIX_3X3:
/* 188 */                   e = Element.MATRIX_3X3(rst.f54mN);
                            break;
                        case MATRIX_2X2:
/* 191 */                   e = Element.MATRIX_2X2(rst.f54mN);
                            break;
                        case RS_ELEMENT:
/* 195 */                   e = Element.ELEMENT(rst.f54mN);
                            break;
                        case RS_TYPE:
/* 198 */                   e = Element.TYPE(rst.f54mN);
                            break;
                        case RS_ALLOCATION:
/* 201 */                   e = Element.ALLOCATION(rst.f54mN);
                            break;
                        case RS_SAMPLER:
/* 204 */                   e = Element.SAMPLER(rst.f54mN);
                            break;
                        case RS_SCRIPT:
/* 207 */                   e = Element.SCRIPT(rst.f54mN);
                            break;
                    }
/* 211 */           return new ElementThunker(rs, e);
                }

/* 214 */       public static Element createVector(RenderScript rs, Element.DataType dt, int size) {
/* 215 */           RenderScriptThunker rst = (RenderScriptThunker) rs;
/* 217 */           Element e = Element.createVector(rst.f54mN, convertType(dt), size);
/* 218 */           return new ElementThunker(rs, e);
                }

/* 221 */       public static Element createPixel(RenderScript rs, Element.DataType dt, Element.DataKind dk) {
/* 222 */           RenderScriptThunker rst = (RenderScriptThunker) rs;
/* 224 */           Element e = Element.createPixel(rst.f54mN, convertType(dt), convertKind(dk));
/* 225 */           return new ElementThunker(rs, e);
                }

                @Override // android.support.p001v8.renderscript.Element
/* 228 */       public boolean isCompatible(Element e) {
/* 229 */           ElementThunker et = (ElementThunker) e;
/* 230 */           return et.f24mN.isCompatible(this.f24mN);
                }

/* 234 */       static class BuilderThunker {

                    /* JADX INFO: renamed from: mN */
                    Element.Builder f25mN;

/* 235 */           public BuilderThunker(RenderScript rs) {
/* 237 */               RenderScriptThunker rst = (RenderScriptThunker) rs;
/* 238 */               this.f25mN = new Element.Builder(rst.f54mN);
                    }

/* 241 */           public void add(Element e, String name, int arraySize) {
/* 242 */               ElementThunker et = (ElementThunker) e;
/* 243 */               this.f25mN.add(et.f24mN, name, arraySize);
                    }

/* 246 */           public Element create(RenderScript rs) {
/* 247 */               Element e = this.f25mN.create();
/* 248 */               return new ElementThunker(rs, e);
                    }
                }
            }
