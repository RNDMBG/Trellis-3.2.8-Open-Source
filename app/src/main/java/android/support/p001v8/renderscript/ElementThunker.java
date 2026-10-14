            package android.support.p001v8.renderscript;

            import android.renderscript.BaseObj;
            import android.renderscript.Element;
            
            
    class ElementThunker extends Element {

                
                Element f24mN;

                @Override 
          BaseObj getNObj() {
            return getNObj();
                }

                @Override 
        Element getNObj() {
            return this.f24mN;
                }

                @Override 
        public int getBytesSize() {
            return this.f24mN.getBytesSize();
                }

                @Override 
        public int getVectorSize() {
            return this.f24mN.getVectorSize();
                }

        static Element.DataKind convertKind(Element.DataKind cdk) {
                    switch (cdk) {
                        case USER:
                    return Element.DataKind.USER;
                        case PIXEL_L:
                    return Element.DataKind.PIXEL_L;
                        case PIXEL_A:
                    return Element.DataKind.PIXEL_A;
                        case PIXEL_LA:
                    return Element.DataKind.PIXEL_LA;
                        case PIXEL_RGB:
                    return Element.DataKind.PIXEL_RGB;
                        case PIXEL_RGBA:
                    return Element.DataKind.PIXEL_RGBA;
                        default:
                    return null;
                    }
                }

        static Element.DataType convertType(Element.DataType cdt) {
                    switch (cdt) {
                        case NONE:
                    return Element.DataType.NONE;
                        case FLOAT_32:
                    return Element.DataType.FLOAT_32;
                        case FLOAT_64:
                    return Element.DataType.FLOAT_64;
                        case SIGNED_8:
                    return Element.DataType.SIGNED_8;
                        case SIGNED_16:
                    return Element.DataType.SIGNED_16;
                        case SIGNED_32:
                    return Element.DataType.SIGNED_32;
                        case SIGNED_64:
                    return Element.DataType.SIGNED_64;
                        case UNSIGNED_8:
                    return Element.DataType.UNSIGNED_8;
                        case UNSIGNED_16:
                    return Element.DataType.UNSIGNED_16;
                        case UNSIGNED_32:
                    return Element.DataType.UNSIGNED_32;
                        case UNSIGNED_64:
                    return Element.DataType.UNSIGNED_64;
                        case BOOLEAN:
                    return Element.DataType.BOOLEAN;
                        case MATRIX_4X4:
                    return Element.DataType.MATRIX_4X4;
                        case MATRIX_3X3:
                    return Element.DataType.MATRIX_3X3;
                        case MATRIX_2X2:
                    return Element.DataType.MATRIX_2X2;
                        case RS_ELEMENT:
                    return Element.DataType.RS_ELEMENT;
                        case RS_TYPE:
                    return Element.DataType.RS_TYPE;
                        case RS_ALLOCATION:
                    return Element.DataType.RS_ALLOCATION;
                        case RS_SAMPLER:
                    return Element.DataType.RS_SAMPLER;
                        case RS_SCRIPT:
                   return Element.DataType.RS_SCRIPT;
                        default:
                   return null;
                    }
                }

                @Override 
       public boolean isComplex() {
           return this.f24mN.isComplex();
                }

                @Override 
       public int getSubElementCount() {
           return this.f24mN.getSubElementCount();
                }

                @Override 
       public Element getSubElement(int index) {
           return new ElementThunker(this.mRS, this.f24mN.getSubElement(index));
                }

                @Override 
       public String getSubElementName(int index) {
           return this.f24mN.getSubElementName(index);
                }

                @Override 
       public int getSubElementArraySize(int index) {
           return this.f24mN.getSubElementArraySize(index);
                }

                @Override 
       public int getSubElementOffsetBytes(int index) {
           return this.f24mN.getSubElementOffsetBytes(index);
                }

                @Override 
       public Element.DataType getDataType() {
           return this.mType;
                }

                @Override 
       public Element.DataKind getDataKind() {
           return this.mKind;
                }

       ElementThunker(RenderScript rs, Element e) {
           super(0, rs);
           this.f24mN = e;
                }

       static Element create(RenderScript rs, Element.DataType dt) {
           RenderScriptThunker rst = (RenderScriptThunker) rs;
           Element e = null;
                    switch (dt) {
                        case FLOAT_32:
                   e = Element.F32(rst.f54mN);
                            break;
                        case FLOAT_64:
                   e = Element.F64(rst.f54mN);
                            break;
                        case SIGNED_8:
                   e = Element.I8(rst.f54mN);
                            break;
                        case SIGNED_16:
                   e = Element.I16(rst.f54mN);
                            break;
                        case SIGNED_32:
                   e = Element.I32(rst.f54mN);
                            break;
                        case SIGNED_64:
                   e = Element.I64(rst.f54mN);
                            break;
                        case UNSIGNED_8:
                   e = Element.U8(rst.f54mN);
                            break;
                        case UNSIGNED_16:
                   e = Element.U16(rst.f54mN);
                            break;
                        case UNSIGNED_32:
                   e = Element.U32(rst.f54mN);
                            break;
                        case UNSIGNED_64:
                   e = Element.U64(rst.f54mN);
                            break;
                        case BOOLEAN:
                   e = Element.BOOLEAN(rst.f54mN);
                            break;
                        case MATRIX_4X4:
                   e = Element.MATRIX_4X4(rst.f54mN);
                            break;
                        case MATRIX_3X3:
                   e = Element.MATRIX_3X3(rst.f54mN);
                            break;
                        case MATRIX_2X2:
                   e = Element.MATRIX_2X2(rst.f54mN);
                            break;
                        case RS_ELEMENT:
                   e = Element.ELEMENT(rst.f54mN);
                            break;
                        case RS_TYPE:
                   e = Element.TYPE(rst.f54mN);
                            break;
                        case RS_ALLOCATION:
                   e = Element.ALLOCATION(rst.f54mN);
                            break;
                        case RS_SAMPLER:
                   e = Element.SAMPLER(rst.f54mN);
                            break;
                        case RS_SCRIPT:
                   e = Element.SCRIPT(rst.f54mN);
                            break;
                    }
           return new ElementThunker(rs, e);
                }

       public static Element createVector(RenderScript rs, Element.DataType dt, int size) {
           RenderScriptThunker rst = (RenderScriptThunker) rs;
           Element e = Element.createVector(rst.f54mN, convertType(dt), size);
           return new ElementThunker(rs, e);
                }

       public static Element createPixel(RenderScript rs, Element.DataType dt, Element.DataKind dk) {
           RenderScriptThunker rst = (RenderScriptThunker) rs;
           Element e = Element.createPixel(rst.f54mN, convertType(dt), convertKind(dk));
           return new ElementThunker(rs, e);
                }

                @Override 
       public boolean isCompatible(Element e) {
           ElementThunker et = (ElementThunker) e;
           return et.f24mN.isCompatible(this.f24mN);
                }

       static class BuilderThunker {

                    
                    Element.Builder f25mN;

           public BuilderThunker(RenderScript rs) {
               RenderScriptThunker rst = (RenderScriptThunker) rs;
               this.f25mN = new Element.Builder(rst.f54mN);
                    }

           public void add(Element e, String name, int arraySize) {
               ElementThunker et = (ElementThunker) e;
               this.f25mN.add(et.f24mN, name, arraySize);
                    }

           public Element create(RenderScript rs) {
               Element e = this.f25mN.create();
               return new ElementThunker(rs, e);
                    }
                }
            }
