            package android.support.p001v8.renderscript;

            import android.content.res.Resources;
            import android.graphics.Bitmap;
            import android.graphics.BitmapFactory;
            import android.graphics.Canvas;
            import android.graphics.Paint;
            import com.vandalsoftware.p004io.IoUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 152 */   public class Allocation extends BaseObj {
                public static final int USAGE_GRAPHICS_TEXTURE = 2;
                public static final int USAGE_IO_INPUT = 32;
                public static final int USAGE_IO_OUTPUT = 64;
                public static final int USAGE_SCRIPT = 1;
                public static final int USAGE_SHARED = 128;
                static BitmapFactory.Options mBitmapOptions = new BitmapFactory.Options();
                Allocation mAdaptedAllocation;
                Bitmap mBitmap;
                boolean mConstrainedFace;
                boolean mConstrainedLOD;
                boolean mConstrainedY;
                boolean mConstrainedZ;
                int mCurrentCount;
                int mCurrentDimX;
                int mCurrentDimY;
                int mCurrentDimZ;
                boolean mReadAllowed;
                Type.CubemapFace mSelectedFace;
                int mSelectedLOD;
                int mSelectedY;
                int mSelectedZ;
                Type mType;
                int mUsage;
                boolean mWriteAllowed;

/* 153 */       public enum MipmapControl {
                    MIPMAP_NONE(0),
                    MIPMAP_FULL(1),
                    MIPMAP_ON_SYNC_TO_TEXTURE(2);

                    int mID;

/* 154 */           MipmapControl(int id) {
/* 156 */               this.mID = id;
                    }
                }

/* 161 */       private int getIDSafe() {
                    return this.mAdaptedAllocation != null ? this.mAdaptedAllocation.getID(this.mRS) : getID(this.mRS);
                }

/* 176 */       public Element getElement() {
/* 177 */           return this.mType.getElement();
                }

/* 186 */       public int getUsage() {
/* 187 */           return this.mUsage;
                }

/* 196 */       public int getBytesSize() {
/* 197 */           return this.mType.getCount() * this.mType.getElement().getBytesSize();
                }

/* 200 */       private void updateCacheInfo(Type t) {
/* 201 */           this.mCurrentDimX = t.getX();
/* 202 */           this.mCurrentDimY = t.getY();
/* 203 */           this.mCurrentDimZ = t.getZ();
/* 204 */           this.mCurrentCount = this.mCurrentDimX;
/* 205 */           if (this.mCurrentDimY > 1) {
/* 206 */               this.mCurrentCount *= this.mCurrentDimY;
                    }
/* 208 */           if (this.mCurrentDimZ > 1) {
/* 209 */               this.mCurrentCount *= this.mCurrentDimZ;
                    }
                }

/* 213 */       private void setBitmap(Bitmap b) {
/* 214 */           this.mBitmap = b;
                }

/* 217 */       Allocation(int id, RenderScript rs, Type t, int usage) {
/* 218 */           super(id, rs);
/* 69 */            this.mReadAllowed = true;
/* 70 */            this.mWriteAllowed = true;
/* 74 */            this.mSelectedFace = Type.CubemapFace.POSITIVE_X;
/* 219 */           if ((usage & (-228)) != 0) {
/* 224 */               throw new RSIllegalArgumentException("Unknown usage specified.");
                    }
/* 227 */           if ((usage & 32) != 0) {
/* 228 */               this.mWriteAllowed = false;
/* 230 */               if ((usage & (-36)) != 0) {
/* 233 */                   throw new RSIllegalArgumentException("Invalid usage combination.");
                        }
                    }
/* 237 */           this.mType = t;
/* 238 */           this.mUsage = usage;
/* 240 */           if (t != null) {
/* 241 */               updateCacheInfo(t);
                    }
                }

/* 245 */       private void validateIsInt32() {
/* 246 */           if (this.mType.mElement.mType == Element.DataType.SIGNED_32 || this.mType.mElement.mType == Element.DataType.UNSIGNED_32) {
                    } else {
/* 250 */               throw new RSIllegalArgumentException("32 bit integer source does not match allocation type " + this.mType.mElement.mType);
                    }
                }

/* 254 */       private void validateIsInt16() {
/* 255 */           if (this.mType.mElement.mType == Element.DataType.SIGNED_16 || this.mType.mElement.mType == Element.DataType.UNSIGNED_16) {
                    } else {
/* 259 */               throw new RSIllegalArgumentException("16 bit integer source does not match allocation type " + this.mType.mElement.mType);
                    }
                }

/* 263 */       private void validateIsInt8() {
/* 264 */           if (this.mType.mElement.mType == Element.DataType.SIGNED_8 || this.mType.mElement.mType == Element.DataType.UNSIGNED_8) {
                    } else {
/* 268 */               throw new RSIllegalArgumentException("8 bit integer source does not match allocation type " + this.mType.mElement.mType);
                    }
                }

/* 272 */       private void validateIsFloat32() {
/* 273 */           if (this.mType.mElement.mType == Element.DataType.FLOAT_32) {
                    } else {
/* 276 */               throw new RSIllegalArgumentException("32 bit float source does not match allocation type " + this.mType.mElement.mType);
                    }
                }

/* 280 */       private void validateIsObject() {
/* 281 */           if (this.mType.mElement.mType == Element.DataType.RS_ELEMENT || this.mType.mElement.mType == Element.DataType.RS_TYPE || this.mType.mElement.mType == Element.DataType.RS_ALLOCATION || this.mType.mElement.mType == Element.DataType.RS_SAMPLER || this.mType.mElement.mType == Element.DataType.RS_SCRIPT) {
                    } else {
/* 288 */               throw new RSIllegalArgumentException("Object source does not match allocation type " + this.mType.mElement.mType);
                    }
                }

/* 298 */       public Type getType() {
/* 299 */           return this.mType;
                }

/* 307 */       public void syncAll(int srcLocation) {
                    switch (srcLocation) {
                        case 1:
                        case 2:
/* 315 */                   this.mRS.validate();
/* 316 */                   this.mRS.nAllocationSyncAll(getIDSafe(), srcLocation);
/* 317 */                   return;
                        default:
/* 313 */                   throw new RSIllegalArgumentException("Source must be exactly one usage type.");
                    }
                }

/* 326 */       public void ioSend() {
/* 327 */           if ((this.mUsage & 64) == 0) {
/* 328 */               throw new RSIllegalArgumentException("Can only send buffer if IO_OUTPUT usage specified.");
                    }
/* 331 */           this.mRS.validate();
/* 332 */           this.mRS.nAllocationIoSend(getID(this.mRS));
                }

/* 339 */       public void ioSendOutput() {
/* 340 */           ioSend();
                }

/* 348 */       public void ioReceive() {
/* 349 */           if ((this.mUsage & 32) == 0) {
/* 350 */               throw new RSIllegalArgumentException("Can only receive if IO_INPUT usage specified.");
                    }
/* 353 */           this.mRS.validate();
/* 354 */           this.mRS.nAllocationIoReceive(getID(this.mRS));
                }

/* 362 */       public void copyFrom(BaseObj[] d) {
/* 363 */           this.mRS.validate();
/* 364 */           validateIsObject();
/* 365 */           if (d.length != this.mCurrentCount) {
/* 366 */               throw new RSIllegalArgumentException("Array size mismatch, allocation sizeX = " + this.mCurrentCount + ", array length = " + d.length);
                    }
/* 369 */           int[] i = new int[d.length];
                    for (int ct = 0; ct < d.length; ct++) {
/* 371 */               i[ct] = d[ct].getID(this.mRS);
                    }
/* 373 */           copy1DRangeFromUnchecked(0, this.mCurrentCount, i);
                }

/* 376 */       private void validateBitmapFormat(Bitmap b) {
/* 377 */           Bitmap.Config bc = b.getConfig();
/* 378 */           if (bc == null) {
/* 379 */               throw new RSIllegalArgumentException("Bitmap has an unsupported format for this operation");
                    }
                    switch (C01221.$SwitchMap$android$graphics$Bitmap$Config[bc.ordinal()]) {
                        case 1:
/* 383 */                   if (this.mType.getElement().mKind != Element.DataKind.PIXEL_A) {
/* 384 */                       throw new RSIllegalArgumentException("Allocation kind is " + this.mType.getElement().mKind + ", type " + this.mType.getElement().mType + " of " + this.mType.getElement().getBytesSize() + " bytes, passed bitmap was " + bc);
                            }
/* 423 */                   return;
                        case 2:
/* 392 */                   if (this.mType.getElement().mKind != Element.DataKind.PIXEL_RGBA || this.mType.getElement().getBytesSize() != 4) {
/* 394 */                       throw new RSIllegalArgumentException("Allocation kind is " + this.mType.getElement().mKind + ", type " + this.mType.getElement().mType + " of " + this.mType.getElement().getBytesSize() + " bytes, passed bitmap was " + bc);
                            }
/* 423 */                   return;
                        case 3:
/* 402 */                   if (this.mType.getElement().mKind != Element.DataKind.PIXEL_RGB || this.mType.getElement().getBytesSize() != 2) {
/* 404 */                       throw new RSIllegalArgumentException("Allocation kind is " + this.mType.getElement().mKind + ", type " + this.mType.getElement().mType + " of " + this.mType.getElement().getBytesSize() + " bytes, passed bitmap was " + bc);
                            }
/* 423 */                   return;
                        case 4:
/* 412 */                   if (this.mType.getElement().mKind != Element.DataKind.PIXEL_RGBA || this.mType.getElement().getBytesSize() != 2) {
/* 414 */                       throw new RSIllegalArgumentException("Allocation kind is " + this.mType.getElement().mKind + ", type " + this.mType.getElement().mType + " of " + this.mType.getElement().getBytesSize() + " bytes, passed bitmap was " + bc);
                            }
/* 423 */                   return;
                        default:
/* 423 */                   return;
                    }
                }

                /* JADX INFO: renamed from: android.support.v8.renderscript.Allocation$1 */
/* 379 */       static /* synthetic */ class C01221 {
                    static final /* synthetic */ int[] $SwitchMap$android$graphics$Bitmap$Config = new int[Bitmap.Config.values().length];

                    static {
                        try {
                            $SwitchMap$android$graphics$Bitmap$Config[Bitmap.Config.ALPHA_8.ordinal()] = 1;
                        } catch (NoSuchFieldError e) {
                        }
                        try {
                            $SwitchMap$android$graphics$Bitmap$Config[Bitmap.Config.ARGB_8888.ordinal()] = 2;
                        } catch (NoSuchFieldError e2) {
                        }
                        try {
                            $SwitchMap$android$graphics$Bitmap$Config[Bitmap.Config.RGB_565.ordinal()] = 3;
                        } catch (NoSuchFieldError e3) {
                        }
                        try {
                            $SwitchMap$android$graphics$Bitmap$Config[Bitmap.Config.ARGB_4444.ordinal()] = 4;
                        } catch (NoSuchFieldError e4) {
                        }
                    }
                }

/* 425 */       private void validateBitmapSize(Bitmap b) {
/* 426 */           if (this.mCurrentDimX != b.getWidth() || this.mCurrentDimY != b.getHeight()) {
/* 427 */               throw new RSIllegalArgumentException("Cannot update allocation from bitmap, sizes mismatch");
                    }
                }

/* 438 */       public void copyFromUnchecked(int[] d) {
/* 439 */           this.mRS.validate();
/* 440 */           if (this.mCurrentDimZ > 0) {
/* 441 */               copy3DRangeFromUnchecked(0, 0, 0, this.mCurrentDimX, this.mCurrentDimY, this.mCurrentDimZ, d);
                    } else if (this.mCurrentDimY > 0) {
/* 443 */               copy2DRangeFromUnchecked(0, 0, this.mCurrentDimX, this.mCurrentDimY, d);
                    } else {
/* 445 */               copy1DRangeFromUnchecked(0, this.mCurrentCount, d);
                    }
                }

/* 455 */       public void copyFromUnchecked(short[] d) {
/* 456 */           this.mRS.validate();
/* 457 */           if (this.mCurrentDimZ > 0) {
/* 458 */               copy3DRangeFromUnchecked(0, 0, 0, this.mCurrentDimX, this.mCurrentDimY, this.mCurrentDimZ, d);
                    } else if (this.mCurrentDimY > 0) {
/* 460 */               copy2DRangeFromUnchecked(0, 0, this.mCurrentDimX, this.mCurrentDimY, d);
                    } else {
/* 462 */               copy1DRangeFromUnchecked(0, this.mCurrentCount, d);
                    }
                }

/* 472 */       public void copyFromUnchecked(byte[] d) {
/* 473 */           this.mRS.validate();
/* 474 */           if (this.mCurrentDimZ > 0) {
/* 475 */               copy3DRangeFromUnchecked(0, 0, 0, this.mCurrentDimX, this.mCurrentDimY, this.mCurrentDimZ, d);
                    } else if (this.mCurrentDimY > 0) {
/* 477 */               copy2DRangeFromUnchecked(0, 0, this.mCurrentDimX, this.mCurrentDimY, d);
                    } else {
/* 479 */               copy1DRangeFromUnchecked(0, this.mCurrentCount, d);
                    }
                }

/* 489 */       public void copyFromUnchecked(float[] d) {
/* 490 */           this.mRS.validate();
/* 491 */           if (this.mCurrentDimZ > 0) {
/* 492 */               copy3DRangeFromUnchecked(0, 0, 0, this.mCurrentDimX, this.mCurrentDimY, this.mCurrentDimZ, d);
                    } else if (this.mCurrentDimY > 0) {
/* 494 */               copy2DRangeFromUnchecked(0, 0, this.mCurrentDimX, this.mCurrentDimY, d);
                    } else {
/* 496 */               copy1DRangeFromUnchecked(0, this.mCurrentCount, d);
                    }
                }

/* 507 */       public void copyFrom(int[] d) {
/* 508 */           this.mRS.validate();
/* 509 */           if (this.mCurrentDimZ > 0) {
/* 510 */               copy3DRangeFrom(0, 0, 0, this.mCurrentDimX, this.mCurrentDimY, this.mCurrentDimZ, d);
                    } else if (this.mCurrentDimY > 0) {
/* 512 */               copy2DRangeFrom(0, 0, this.mCurrentDimX, this.mCurrentDimY, d);
                    } else {
/* 514 */               copy1DRangeFrom(0, this.mCurrentCount, d);
                    }
                }

/* 525 */       public void copyFrom(short[] d) {
/* 526 */           this.mRS.validate();
/* 527 */           if (this.mCurrentDimZ > 0) {
/* 528 */               copy3DRangeFrom(0, 0, 0, this.mCurrentDimX, this.mCurrentDimY, this.mCurrentDimZ, d);
                    } else if (this.mCurrentDimY > 0) {
/* 530 */               copy2DRangeFrom(0, 0, this.mCurrentDimX, this.mCurrentDimY, d);
                    } else {
/* 532 */               copy1DRangeFrom(0, this.mCurrentCount, d);
                    }
                }

/* 543 */       public void copyFrom(byte[] d) {
/* 544 */           this.mRS.validate();
/* 545 */           if (this.mCurrentDimZ > 0) {
/* 546 */               copy3DRangeFrom(0, 0, 0, this.mCurrentDimX, this.mCurrentDimY, this.mCurrentDimZ, d);
                    } else if (this.mCurrentDimY > 0) {
/* 548 */               copy2DRangeFrom(0, 0, this.mCurrentDimX, this.mCurrentDimY, d);
                    } else {
/* 550 */               copy1DRangeFrom(0, this.mCurrentCount, d);
                    }
                }

/* 561 */       public void copyFrom(float[] d) {
/* 562 */           this.mRS.validate();
/* 563 */           if (this.mCurrentDimZ > 0) {
/* 564 */               copy3DRangeFrom(0, 0, 0, this.mCurrentDimX, this.mCurrentDimY, this.mCurrentDimZ, d);
                    } else if (this.mCurrentDimY > 0) {
/* 566 */               copy2DRangeFrom(0, 0, this.mCurrentDimX, this.mCurrentDimY, d);
                    } else {
/* 568 */               copy1DRangeFrom(0, this.mCurrentCount, d);
                    }
                }

/* 585 */       public void copyFrom(Bitmap b) {
/* 586 */           this.mRS.validate();
/* 587 */           if (b.getConfig() == null) {
/* 588 */               Bitmap newBitmap = Bitmap.createBitmap(b.getWidth(), b.getHeight(), Bitmap.Config.ARGB_8888);
/* 589 */               Canvas c = new Canvas(newBitmap);
/* 590 */               c.drawBitmap(b, 0.0f, 0.0f, (Paint) null);
/* 591 */               copyFrom(newBitmap);
/* 597 */               return;
                    }
/* 594 */           validateBitmapSize(b);
/* 595 */           validateBitmapFormat(b);
/* 596 */           this.mRS.nAllocationCopyFromBitmap(getID(this.mRS), b);
                }

/* 605 */       public void copyFrom(Allocation a) {
/* 606 */           this.mRS.validate();
/* 607 */           if (!this.mType.equals(a.getType())) {
/* 608 */               throw new RSIllegalArgumentException("Types of allocations must match.");
                    }
/* 610 */           copy2DRangeFrom(0, 0, this.mCurrentDimX, this.mCurrentDimY, a, 0, 0);
                }

/* 621 */       public void setFromFieldPacker(int xoff, FieldPacker fp) {
/* 622 */           this.mRS.validate();
/* 623 */           int eSize = this.mType.mElement.getBytesSize();
/* 624 */           byte[] data = fp.getData();
/* 626 */           int count = data.length / eSize;
/* 627 */           if (eSize * count != data.length) {
/* 628 */               throw new RSIllegalArgumentException("Field packer length " + data.length + " not divisible by element size " + eSize + ".");
                    }
/* 631 */           copy1DRangeFromUnchecked(xoff, count, data);
                }

/* 642 */       public void setFromFieldPacker(int xoff, int component_number, FieldPacker fp) {
/* 643 */           this.mRS.validate();
/* 644 */           if (component_number >= this.mType.mElement.mElements.length) {
/* 645 */               throw new RSIllegalArgumentException("Component_number " + component_number + " out of range.");
                    }
/* 647 */           if (xoff < 0) {
/* 648 */               throw new RSIllegalArgumentException("Offset must be >= 0.");
                    }
/* 651 */           byte[] data = fp.getData();
/* 653 */           int eSize = this.mType.mElement.mElements[component_number].getBytesSize() * this.mType.mElement.mArraySizes[component_number];
/* 655 */           if (data.length != eSize) {
/* 656 */               throw new RSIllegalArgumentException("Field packer sizelength " + data.length + " does not match component size " + eSize + ".");
                    }
/* 660 */           this.mRS.nAllocationElementData1D(getIDSafe(), xoff, this.mSelectedLOD, component_number, data, data.length);
                }

/* 664 */       private void data1DChecks(int off, int count, int len, int dataSize) {
/* 665 */           this.mRS.validate();
/* 666 */           if (off < 0) {
/* 667 */               throw new RSIllegalArgumentException("Offset must be >= 0.");
                    }
/* 669 */           if (count < 1) {
/* 670 */               throw new RSIllegalArgumentException("Count must be >= 1.");
                    }
/* 672 */           if (off + count > this.mCurrentCount) {
/* 673 */               throw new RSIllegalArgumentException("Overflow, Available count " + this.mCurrentCount + ", got " + count + " at offset " + off + ".");
                    }
/* 676 */           if (len < dataSize) {
/* 677 */               throw new RSIllegalArgumentException("Array too small for allocation type.");
                    }
                }

/* 691 */       public void generateMipmaps() {
/* 692 */           this.mRS.nAllocationGenerateMipmaps(getID(this.mRS));
                }

/* 703 */       public void copy1DRangeFromUnchecked(int off, int count, int[] d) {
/* 704 */           int dataSize = this.mType.mElement.getBytesSize() * count;
/* 705 */           data1DChecks(off, count, d.length * 4, dataSize);
/* 706 */           this.mRS.nAllocationData1D(getIDSafe(), off, this.mSelectedLOD, count, d, dataSize);
                }

/* 716 */       public void copy1DRangeFromUnchecked(int off, int count, short[] d) {
/* 717 */           int dataSize = this.mType.mElement.getBytesSize() * count;
/* 718 */           data1DChecks(off, count, d.length * 2, dataSize);
/* 719 */           this.mRS.nAllocationData1D(getIDSafe(), off, this.mSelectedLOD, count, d, dataSize);
                }

/* 729 */       public void copy1DRangeFromUnchecked(int off, int count, byte[] d) {
/* 730 */           int dataSize = this.mType.mElement.getBytesSize() * count;
/* 731 */           data1DChecks(off, count, d.length, dataSize);
/* 732 */           this.mRS.nAllocationData1D(getIDSafe(), off, this.mSelectedLOD, count, d, dataSize);
                }

/* 742 */       public void copy1DRangeFromUnchecked(int off, int count, float[] d) {
/* 743 */           int dataSize = this.mType.mElement.getBytesSize() * count;
/* 744 */           data1DChecks(off, count, d.length * 4, dataSize);
/* 745 */           this.mRS.nAllocationData1D(getIDSafe(), off, this.mSelectedLOD, count, d, dataSize);
                }

/* 757 */       public void copy1DRangeFrom(int off, int count, int[] d) {
/* 758 */           validateIsInt32();
/* 759 */           copy1DRangeFromUnchecked(off, count, d);
                }

/* 771 */       public void copy1DRangeFrom(int off, int count, short[] d) {
/* 772 */           validateIsInt16();
/* 773 */           copy1DRangeFromUnchecked(off, count, d);
                }

/* 785 */       public void copy1DRangeFrom(int off, int count, byte[] d) {
/* 786 */           validateIsInt8();
/* 787 */           copy1DRangeFromUnchecked(off, count, d);
                }

/* 799 */       public void copy1DRangeFrom(int off, int count, float[] d) {
/* 800 */           validateIsFloat32();
/* 801 */           copy1DRangeFromUnchecked(off, count, d);
                }

/* 813 */       public void copy1DRangeFrom(int off, int count, Allocation data, int dataOff) {
/* 814 */           this.mRS.nAllocationData2D(getIDSafe(), off, 0, this.mSelectedLOD, this.mSelectedFace.mID, count, 1, data.getID(this.mRS), dataOff, 0, data.mSelectedLOD, data.mSelectedFace.mID);
                }

/* 820 */       private void validate2DRange(int xoff, int yoff, int w, int h) {
/* 821 */           if (this.mAdaptedAllocation == null) {
/* 825 */               if (xoff < 0 || yoff < 0) {
/* 826 */                   throw new RSIllegalArgumentException("Offset cannot be negative.");
                        }
/* 828 */               if (h < 0 || w < 0) {
/* 829 */                   throw new RSIllegalArgumentException("Height or width cannot be negative.");
                        }
/* 831 */               if (xoff + w > this.mCurrentDimX || yoff + h > this.mCurrentDimY) {
/* 832 */                   throw new RSIllegalArgumentException("Updated region larger than allocation.");
                        }
                    }
                }

/* 837 */       void copy2DRangeFromUnchecked(int xoff, int yoff, int w, int h, byte[] data) {
/* 838 */           this.mRS.validate();
/* 839 */           validate2DRange(xoff, yoff, w, h);
/* 840 */           this.mRS.nAllocationData2D(getIDSafe(), xoff, yoff, this.mSelectedLOD, this.mSelectedFace.mID, w, h, data, data.length);
                }

/* 844 */       void copy2DRangeFromUnchecked(int xoff, int yoff, int w, int h, short[] data) {
/* 845 */           this.mRS.validate();
/* 846 */           validate2DRange(xoff, yoff, w, h);
/* 847 */           this.mRS.nAllocationData2D(getIDSafe(), xoff, yoff, this.mSelectedLOD, this.mSelectedFace.mID, w, h, data, data.length * 2);
                }

/* 851 */       void copy2DRangeFromUnchecked(int xoff, int yoff, int w, int h, int[] data) {
/* 852 */           this.mRS.validate();
/* 853 */           validate2DRange(xoff, yoff, w, h);
/* 854 */           this.mRS.nAllocationData2D(getIDSafe(), xoff, yoff, this.mSelectedLOD, this.mSelectedFace.mID, w, h, data, data.length * 4);
                }

/* 858 */       void copy2DRangeFromUnchecked(int xoff, int yoff, int w, int h, float[] data) {
/* 859 */           this.mRS.validate();
/* 860 */           validate2DRange(xoff, yoff, w, h);
/* 861 */           this.mRS.nAllocationData2D(getIDSafe(), xoff, yoff, this.mSelectedLOD, this.mSelectedFace.mID, w, h, data, data.length * 4);
                }

/* 876 */       public void copy2DRangeFrom(int xoff, int yoff, int w, int h, byte[] data) {
/* 877 */           validateIsInt8();
/* 878 */           copy2DRangeFromUnchecked(xoff, yoff, w, h, data);
                }

/* 891 */       public void copy2DRangeFrom(int xoff, int yoff, int w, int h, short[] data) {
/* 892 */           validateIsInt16();
/* 893 */           copy2DRangeFromUnchecked(xoff, yoff, w, h, data);
                }

/* 906 */       public void copy2DRangeFrom(int xoff, int yoff, int w, int h, int[] data) {
/* 907 */           validateIsInt32();
/* 908 */           copy2DRangeFromUnchecked(xoff, yoff, w, h, data);
                }

/* 921 */       public void copy2DRangeFrom(int xoff, int yoff, int w, int h, float[] data) {
/* 922 */           validateIsFloat32();
/* 923 */           copy2DRangeFromUnchecked(xoff, yoff, w, h, data);
                }

/* 939 */       public void copy2DRangeFrom(int xoff, int yoff, int w, int h, Allocation data, int dataXoff, int dataYoff) {
/* 940 */           this.mRS.validate();
/* 941 */           validate2DRange(xoff, yoff, w, h);
/* 942 */           this.mRS.nAllocationData2D(getIDSafe(), xoff, yoff, this.mSelectedLOD, this.mSelectedFace.mID, w, h, data.getID(this.mRS), dataXoff, dataYoff, data.mSelectedLOD, data.mSelectedFace.mID);
                }

/* 957 */       public void copy2DRangeFrom(int xoff, int yoff, Bitmap data) {
/* 958 */           this.mRS.validate();
/* 959 */           if (data.getConfig() == null) {
/* 960 */               Bitmap newBitmap = Bitmap.createBitmap(data.getWidth(), data.getHeight(), Bitmap.Config.ARGB_8888);
/* 961 */               Canvas c = new Canvas(newBitmap);
/* 962 */               c.drawBitmap(data, 0.0f, 0.0f, (Paint) null);
/* 963 */               copy2DRangeFrom(xoff, yoff, newBitmap);
/* 969 */               return;
                    }
/* 966 */           validateBitmapFormat(data);
/* 967 */           validate2DRange(xoff, yoff, data.getWidth(), data.getHeight());
/* 968 */           this.mRS.nAllocationData2D(getIDSafe(), xoff, yoff, this.mSelectedLOD, this.mSelectedFace.mID, data);
                }

/* 971 */       private void validate3DRange(int xoff, int yoff, int zoff, int w, int h, int d) {
/* 972 */           if (this.mAdaptedAllocation == null) {
/* 976 */               if (xoff < 0 || yoff < 0 || zoff < 0) {
/* 977 */                   throw new RSIllegalArgumentException("Offset cannot be negative.");
                        }
/* 979 */               if (h < 0 || w < 0 || d < 0) {
/* 980 */                   throw new RSIllegalArgumentException("Height or width cannot be negative.");
                        }
/* 982 */               if (xoff + w > this.mCurrentDimX || yoff + h > this.mCurrentDimY || zoff + d > this.mCurrentDimZ) {
/* 983 */                   throw new RSIllegalArgumentException("Updated region larger than allocation.");
                        }
                    }
                }

/* 992 */       void copy3DRangeFromUnchecked(int xoff, int yoff, int zoff, int w, int h, int d, byte[] data) {
/* 993 */           this.mRS.validate();
/* 994 */           validate3DRange(xoff, yoff, zoff, w, h, d);
/* 995 */           this.mRS.nAllocationData3D(getIDSafe(), xoff, yoff, zoff, this.mSelectedLOD, w, h, d, data, data.length);
                }

/* 1003 */      void copy3DRangeFromUnchecked(int xoff, int yoff, int zoff, int w, int h, int d, short[] data) {
/* 1004 */          this.mRS.validate();
/* 1005 */          validate3DRange(xoff, yoff, zoff, w, h, d);
/* 1006 */          this.mRS.nAllocationData3D(getIDSafe(), xoff, yoff, zoff, this.mSelectedLOD, w, h, d, data, data.length * 2);
                }

/* 1014 */      void copy3DRangeFromUnchecked(int xoff, int yoff, int zoff, int w, int h, int d, int[] data) {
/* 1015 */          this.mRS.validate();
/* 1016 */          validate3DRange(xoff, yoff, zoff, w, h, d);
/* 1017 */          this.mRS.nAllocationData3D(getIDSafe(), xoff, yoff, zoff, this.mSelectedLOD, w, h, d, data, data.length * 4);
                }

/* 1025 */      void copy3DRangeFromUnchecked(int xoff, int yoff, int zoff, int w, int h, int d, float[] data) {
/* 1026 */          this.mRS.validate();
/* 1027 */          validate3DRange(xoff, yoff, zoff, w, h, d);
/* 1028 */          this.mRS.nAllocationData3D(getIDSafe(), xoff, yoff, zoff, this.mSelectedLOD, w, h, d, data, data.length * 4);
                }

/* 1046 */      public void copy3DRangeFrom(int xoff, int yoff, int zoff, int w, int h, int d, byte[] data) {
/* 1047 */          validateIsInt8();
/* 1048 */          copy3DRangeFromUnchecked(xoff, yoff, zoff, w, h, d, data);
                }

/* 1055 */      public void copy3DRangeFrom(int xoff, int yoff, int zoff, int w, int h, int d, short[] data) {
/* 1056 */          validateIsInt16();
/* 1057 */          copy3DRangeFromUnchecked(xoff, yoff, zoff, w, h, d, data);
                }

/* 1064 */      public void copy3DRangeFrom(int xoff, int yoff, int zoff, int w, int h, int d, int[] data) {
/* 1065 */          validateIsInt32();
/* 1066 */          copy3DRangeFromUnchecked(xoff, yoff, zoff, w, h, d, data);
                }

/* 1073 */      public void copy3DRangeFrom(int xoff, int yoff, int zoff, int w, int h, int d, float[] data) {
/* 1074 */          validateIsFloat32();
/* 1075 */          copy3DRangeFromUnchecked(xoff, yoff, zoff, w, h, d, data);
                }

/* 1095 */      public void copy3DRangeFrom(int xoff, int yoff, int zoff, int w, int h, int d, Allocation data, int dataXoff, int dataYoff, int dataZoff) {
/* 1096 */          this.mRS.validate();
/* 1097 */          validate3DRange(xoff, yoff, zoff, w, h, d);
/* 1098 */          this.mRS.nAllocationData3D(getIDSafe(), xoff, yoff, zoff, this.mSelectedLOD, w, h, d, data.getID(this.mRS), dataXoff, dataYoff, dataZoff, data.mSelectedLOD);
                }

/* 1110 */      public void copyTo(Bitmap b) {
/* 1111 */          this.mRS.validate();
/* 1112 */          validateBitmapFormat(b);
/* 1113 */          validateBitmapSize(b);
/* 1114 */          this.mRS.nAllocationCopyToBitmap(getID(this.mRS), b);
                }

/* 1124 */      public void copyTo(byte[] d) {
/* 1125 */          validateIsInt8();
/* 1126 */          this.mRS.validate();
/* 1127 */          this.mRS.nAllocationRead(getID(this.mRS), d);
                }

/* 1137 */      public void copyTo(short[] d) {
/* 1138 */          validateIsInt16();
/* 1139 */          this.mRS.validate();
/* 1140 */          this.mRS.nAllocationRead(getID(this.mRS), d);
                }

/* 1150 */      public void copyTo(int[] d) {
/* 1151 */          validateIsInt32();
/* 1152 */          this.mRS.validate();
/* 1153 */          this.mRS.nAllocationRead(getID(this.mRS), d);
                }

/* 1163 */      public void copyTo(float[] d) {
/* 1164 */          validateIsFloat32();
/* 1165 */          this.mRS.validate();
/* 1166 */          this.mRS.nAllocationRead(getID(this.mRS), d);
                }

                static {
/* 1173 */          mBitmapOptions.inScaled = false;
                }

/* 1186 */      public static Allocation createTyped(RenderScript rs, Type type, MipmapControl mips, int usage) {
/* 1187 */          if (RenderScript.isNative) {
/* 1188 */              RenderScriptThunker rst = (RenderScriptThunker) rs;
/* 1189 */              return AllocationThunker.createTyped(rst, type, mips, usage);
                    }
/* 1191 */          rs.validate();
/* 1192 */          if (type.getID(rs) == 0) {
/* 1193 */              throw new RSInvalidStateException("Bad Type");
                    }
/* 1195 */          int id = rs.nAllocationCreateTyped(type.getID(rs), mips.mID, usage, 0);
/* 1196 */          if (id == 0) {
/* 1197 */              throw new RSRuntimeException("Allocation creation failed.");
                    }
/* 1199 */          return new Allocation(id, rs, type, usage);
                }

/* 1213 */      public static Allocation createTyped(RenderScript rs, Type type, int usage) {
/* 1214 */          return createTyped(rs, type, MipmapControl.MIPMAP_NONE, usage);
                }

/* 1226 */      public static Allocation createTyped(RenderScript rs, Type type) {
/* 1227 */          return createTyped(rs, type, MipmapControl.MIPMAP_NONE, 1);
                }

/* 1242 */      public static Allocation createSized(RenderScript rs, Element e, int count, int usage) {
/* 1243 */          if (RenderScript.isNative) {
/* 1245 */              return AllocationThunker.createSized(rs, e, count, usage);
                    }
/* 1247 */          rs.validate();
/* 1248 */          Type.Builder b = new Type.Builder(rs, e);
/* 1249 */          b.setX(count);
/* 1250 */          Type t = b.create();
/* 1252 */          int id = rs.nAllocationCreateTyped(t.getID(rs), MipmapControl.MIPMAP_NONE.mID, usage, 0);
/* 1253 */          if (id == 0) {
/* 1254 */              throw new RSRuntimeException("Allocation creation failed.");
                    }
/* 1256 */          return new Allocation(id, rs, t, usage);
                }

/* 1268 */      public static Allocation createSized(RenderScript rs, Element e, int count) {
/* 1269 */          return createSized(rs, e, count, 1);
                }

/* 1272 */      static Element elementFromBitmap(RenderScript rs, Bitmap b) {
/* 1273 */          Bitmap.Config bc = b.getConfig();
/* 1274 */          if (bc == Bitmap.Config.ALPHA_8) {
/* 1275 */              return Element.A_8(rs);
                    }
/* 1277 */          if (bc == Bitmap.Config.ARGB_4444) {
/* 1278 */              return Element.RGBA_4444(rs);
                    }
/* 1280 */          if (bc == Bitmap.Config.ARGB_8888) {
/* 1281 */              return Element.RGBA_8888(rs);
                    }
/* 1283 */          if (bc == Bitmap.Config.RGB_565) {
/* 1284 */              return Element.RGB_565(rs);
                    }
/* 1286 */          throw new RSInvalidStateException("Bad bitmap type: " + bc);
                }

/* 1290 */      static Type typeFromBitmap(RenderScript rs, Bitmap b, MipmapControl mip) {
/* 1291 */          Element e = elementFromBitmap(rs, b);
/* 1292 */          Type.Builder tb = new Type.Builder(rs, e);
/* 1293 */          tb.setX(b.getWidth());
/* 1294 */          tb.setY(b.getHeight());
/* 1295 */          tb.setMipmaps(mip == MipmapControl.MIPMAP_FULL);
/* 1296 */          return tb.create();
                }

/* 1314 */      public static Allocation createFromBitmap(RenderScript rs, Bitmap b, MipmapControl mips, int usage) {
/* 1315 */          if (RenderScript.isNative) {
/* 1316 */              RenderScriptThunker rst = (RenderScriptThunker) rs;
/* 1317 */              return AllocationThunker.createFromBitmap(rst, b, mips, usage);
                    }
/* 1319 */          rs.validate();
/* 1322 */          if (b.getConfig() == null) {
/* 1323 */              if ((usage & 128) != 0) {
/* 1324 */                  throw new RSIllegalArgumentException("USAGE_SHARED cannot be used with a Bitmap that has a null config.");
                        }
/* 1326 */              Bitmap newBitmap = Bitmap.createBitmap(b.getWidth(), b.getHeight(), Bitmap.Config.ARGB_8888);
/* 1327 */              Canvas c = new Canvas(newBitmap);
/* 1328 */              c.drawBitmap(b, 0.0f, 0.0f, (Paint) null);
/* 1329 */              return createFromBitmap(rs, newBitmap, mips, usage);
                    }
/* 1332 */          Type t = typeFromBitmap(rs, b, mips);
/* 1335 */          if (mips == MipmapControl.MIPMAP_NONE && t.getElement().isCompatible(Element.RGBA_8888(rs)) && usage == 131) {
/* 1338 */              int id = rs.nAllocationCreateBitmapBackedAllocation(t.getID(rs), mips.mID, b, usage);
/* 1339 */              if (id == 0) {
/* 1340 */                  throw new RSRuntimeException("Load failed.");
                        }
/* 1344 */              Allocation alloc = new Allocation(id, rs, t, usage);
/* 1345 */              alloc.setBitmap(b);
/* 1354 */              return alloc;
                    }
/* 1350 */          int id2 = rs.nAllocationCreateFromBitmap(t.getID(rs), mips.mID, b, usage);
/* 1351 */          if (id2 == 0) {
/* 1352 */              throw new RSRuntimeException("Load failed.");
                    }
/* 1354 */          return new Allocation(id2, rs, t, usage);
                }

/* 1369 */      public static Allocation createFromBitmap(RenderScript rs, Bitmap b) {
/* 1370 */          return createFromBitmap(rs, b, MipmapControl.MIPMAP_NONE, 131);
                }

/* 1391 */      public static Allocation createCubemapFromBitmap(RenderScript rs, Bitmap b, MipmapControl mips, int usage) {
/* 1392 */          rs.validate();
/* 1394 */          int height = b.getHeight();
/* 1395 */          int width = b.getWidth();
/* 1397 */          if (width % 6 != 0) {
/* 1398 */              throw new RSIllegalArgumentException("Cubemap height must be multiple of 6");
                    }
/* 1400 */          if (width / 6 != height) {
/* 1401 */              throw new RSIllegalArgumentException("Only square cube map faces supported");
                    }
/* 1403 */          boolean isPow2 = ((height + (-1)) & height) == 0;
/* 1404 */          if (!isPow2) {
/* 1405 */              throw new RSIllegalArgumentException("Only power of 2 cube faces supported");
                    }
/* 1408 */          Element e = elementFromBitmap(rs, b);
/* 1409 */          Type.Builder tb = new Type.Builder(rs, e);
/* 1410 */          tb.setX(height);
/* 1411 */          tb.setY(height);
/* 1412 */          tb.setFaces(true);
/* 1413 */          tb.setMipmaps(mips == MipmapControl.MIPMAP_FULL);
/* 1414 */          Type t = tb.create();
/* 1416 */          int id = rs.nAllocationCubeCreateFromBitmap(t.getID(rs), mips.mID, b, usage);
/* 1417 */          if (id == 0) {
/* 1418 */              throw new RSRuntimeException("Load failed for bitmap " + b + " element " + e);
                    }
/* 1420 */          return new Allocation(id, rs, t, usage);
                }

/* 1437 */      public static Allocation createCubemapFromBitmap(RenderScript rs, Bitmap b) {
/* 1438 */          return createCubemapFromBitmap(rs, b, MipmapControl.MIPMAP_NONE, 2);
                }

/* 1508 */      public static Allocation createCubemapFromCubeFaces(RenderScript rs, Bitmap xpos, Bitmap xneg, Bitmap ypos, Bitmap yneg, Bitmap zpos, Bitmap zneg, MipmapControl mips, int usage) {
/* 1509 */          return null;
                }

/* 1535 */      public static Allocation createCubemapFromCubeFaces(RenderScript rs, Bitmap xpos, Bitmap xneg, Bitmap ypos, Bitmap yneg, Bitmap zpos, Bitmap zneg) {
/* 1536 */          return createCubemapFromCubeFaces(rs, xpos, xneg, ypos, yneg, zpos, zneg, MipmapControl.MIPMAP_NONE, 2);
                }

/* 1561 */      public static Allocation createFromBitmapResource(RenderScript rs, Resources res, int id, MipmapControl mips, int usage) {
/* 1562 */          rs.validate();
/* 1563 */          if ((usage & 224) != 0) {
/* 1564 */              throw new RSIllegalArgumentException("Unsupported usage specified.");
                    }
/* 1566 */          Bitmap b = BitmapFactory.decodeResource(res, id);
/* 1567 */          Allocation alloc = createFromBitmap(rs, b, mips, usage);
/* 1568 */          b.recycle();
/* 1569 */          return alloc;
                }

/* 1588 */      public static Allocation createFromBitmapResource(RenderScript rs, Resources res, int id) {
/* 1589 */          return createFromBitmapResource(rs, res, id, MipmapControl.MIPMAP_NONE, 3);
                }

/* 1605 */      public static Allocation createFromString(RenderScript rs, String str, int usage) {
/* 1606 */          rs.validate();
                    try {
/* 1609 */              byte[] allocArray = str.getBytes(IoUtils.UTF_8);
/* 1610 */              Allocation alloc = createSized(rs, Element.m3U8(rs), allocArray.length, usage);
/* 1611 */              alloc.copyFrom(allocArray);
/* 1612 */              return alloc;
                    } catch (Exception e) {
/* 1615 */              throw new RSRuntimeException("Could not convert string to utf-8.");
                    }
                }
            }
