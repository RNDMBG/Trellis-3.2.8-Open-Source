            package android.support.p001v8.renderscript;

            import com.google.android.gms.location.LocationStatusCodes;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 73 */    public class Element extends BaseObj {
                int[] mArraySizes;
                String[] mElementNames;
                Element[] mElements;
                DataKind mKind;
                boolean mNormalized;
                int[] mOffsetInBytes;
                int mSize;
                DataType mType;
                int mVectorSize;
                int[] mVisibleElementMap;

/* 74 */        private void updateVisibleSubElements() {
                    int ctNoPadding;
/* 75 */            if (this.mElements != null) {
/* 79 */                int noPaddingFieldCount = 0;
/* 80 */                int fieldCount = this.mElementNames.length;
                        for (int ct = 0; ct < fieldCount; ct++) {
/* 83 */                    if (this.mElementNames[ct].charAt(0) != '#') {
/* 84 */                        noPaddingFieldCount++;
                            }
                        }
/* 87 */                this.mVisibleElementMap = new int[noPaddingFieldCount];
/* 90 */                int ct2 = 0;
                        int ctNoPadding2 = 0;
                        while (ct2 < fieldCount) {
/* 91 */                    if (this.mElementNames[ct2].charAt(0) != '#') {
                                ctNoPadding = ctNoPadding2 + 1;
/* 92 */                        this.mVisibleElementMap[ctNoPadding2] = ct2;
                            } else {
                                ctNoPadding = ctNoPadding2;
                            }
/* 90 */                    ct2++;
                            ctNoPadding2 = ctNoPadding;
                        }
                    }
                }

/* 100 */       public int getBytesSize() {
/* 101 */           return this.mSize;
                }

/* 109 */       public int getVectorSize() {
/* 110 */           return this.mVectorSize;
                }

/* 160 */       public enum DataType {
                    NONE(0, 0),
                    FLOAT_32(2, 4),
                    FLOAT_64(3, 8),
                    SIGNED_8(4, 1),
                    SIGNED_16(5, 2),
                    SIGNED_32(6, 4),
                    SIGNED_64(7, 8),
                    UNSIGNED_8(8, 1),
                    UNSIGNED_16(9, 2),
                    UNSIGNED_32(10, 4),
                    UNSIGNED_64(11, 8),
                    BOOLEAN(12, 1),
                    UNSIGNED_5_6_5(13, 2),
                    UNSIGNED_5_5_5_1(14, 2),
                    UNSIGNED_4_4_4_4(15, 2),
                    MATRIX_4X4(16, 64),
                    MATRIX_3X3(17, 36),
                    MATRIX_2X2(18, 16),
                    RS_ELEMENT(1000, 4),
                    RS_TYPE(LocationStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES, 4),
                    RS_ALLOCATION(LocationStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS, 4),
                    RS_SAMPLER(1003, 4),
                    RS_SCRIPT(1004, 4);

                    int mID;
                    int mSize;

/* 161 */           DataType(int id, int size) {
/* 163 */               this.mID = id;
/* 164 */               this.mSize = size;
                    }
                }

/* 184 */       public enum DataKind {
                    USER(0),
                    PIXEL_L(7),
                    PIXEL_A(8),
                    PIXEL_LA(9),
                    PIXEL_RGB(10),
                    PIXEL_RGBA(11),
                    PIXEL_DEPTH(12),
                    PIXEL_YUV(13);

                    int mID;

/* 185 */           DataKind(int id) {
/* 187 */               this.mID = id;
                    }
                }

/* 197 */       public boolean isComplex() {
/* 198 */           if (this.mElements == null) {
                        return false;
                    }
                    for (int ct = 0; ct < this.mElements.length; ct++) {
/* 202 */               if (this.mElements[ct].mElements != null) {
/* 203 */                   return true;
                        }
                    }
                    return false;
                }

/* 216 */       public int getSubElementCount() {
/* 217 */           if (this.mVisibleElementMap == null) {
/* 218 */               return 0;
                    }
                    return this.mVisibleElementMap.length;
                }

/* 229 */       public Element getSubElement(int index) {
/* 230 */           if (this.mVisibleElementMap == null) {
/* 231 */               throw new RSIllegalArgumentException("Element contains no sub-elements");
                    }
/* 233 */           if (index < 0 || index >= this.mVisibleElementMap.length) {
/* 234 */               throw new RSIllegalArgumentException("Illegal sub-element index");
                    }
/* 236 */           return this.mElements[this.mVisibleElementMap[index]];
                }

/* 245 */       public String getSubElementName(int index) {
/* 246 */           if (this.mVisibleElementMap == null) {
/* 247 */               throw new RSIllegalArgumentException("Element contains no sub-elements");
                    }
/* 249 */           if (index < 0 || index >= this.mVisibleElementMap.length) {
/* 250 */               throw new RSIllegalArgumentException("Illegal sub-element index");
                    }
/* 252 */           return this.mElementNames[this.mVisibleElementMap[index]];
                }

/* 262 */       public int getSubElementArraySize(int index) {
/* 263 */           if (this.mVisibleElementMap == null) {
/* 264 */               throw new RSIllegalArgumentException("Element contains no sub-elements");
                    }
/* 266 */           if (index < 0 || index >= this.mVisibleElementMap.length) {
/* 267 */               throw new RSIllegalArgumentException("Illegal sub-element index");
                    }
/* 269 */           return this.mArraySizes[this.mVisibleElementMap[index]];
                }

/* 278 */       public int getSubElementOffsetBytes(int index) {
/* 279 */           if (this.mVisibleElementMap == null) {
/* 280 */               throw new RSIllegalArgumentException("Element contains no sub-elements");
                    }
/* 282 */           if (index < 0 || index >= this.mVisibleElementMap.length) {
/* 283 */               throw new RSIllegalArgumentException("Illegal sub-element index");
                    }
/* 285 */           return this.mOffsetInBytes[this.mVisibleElementMap[index]];
                }

/* 291 */       public DataType getDataType() {
/* 292 */           return this.mType;
                }

/* 298 */       public DataKind getDataKind() {
/* 299 */           return this.mKind;
                }

/* 309 */       public static Element BOOLEAN(RenderScript rs) {
/* 310 */           if (rs.mElement_BOOLEAN == null) {
/* 311 */               rs.mElement_BOOLEAN = createUser(rs, DataType.BOOLEAN);
                    }
/* 313 */           return rs.mElement_BOOLEAN;
                }

                /* JADX INFO: renamed from: U8 */
/* 323 */       public static Element m3U8(RenderScript rs) {
/* 324 */           if (rs.mElement_U8 == null) {
/* 325 */               rs.mElement_U8 = createUser(rs, DataType.UNSIGNED_8);
                    }
/* 327 */           return rs.mElement_U8;
                }

                /* JADX INFO: renamed from: I8 */
/* 337 */       public static Element m2I8(RenderScript rs) {
/* 338 */           if (rs.mElement_I8 == null) {
/* 339 */               rs.mElement_I8 = createUser(rs, DataType.SIGNED_8);
                    }
/* 341 */           return rs.mElement_I8;
                }

/* 344 */       public static Element U16(RenderScript rs) {
/* 345 */           if (rs.mElement_U16 == null) {
/* 346 */               rs.mElement_U16 = createUser(rs, DataType.UNSIGNED_16);
                    }
/* 348 */           return rs.mElement_U16;
                }

/* 351 */       public static Element I16(RenderScript rs) {
/* 352 */           if (rs.mElement_I16 == null) {
/* 353 */               rs.mElement_I16 = createUser(rs, DataType.SIGNED_16);
                    }
/* 355 */           return rs.mElement_I16;
                }

/* 358 */       public static Element U32(RenderScript rs) {
/* 359 */           if (rs.mElement_U32 == null) {
/* 360 */               rs.mElement_U32 = createUser(rs, DataType.UNSIGNED_32);
                    }
/* 362 */           return rs.mElement_U32;
                }

/* 365 */       public static Element I32(RenderScript rs) {
/* 366 */           if (rs.mElement_I32 == null) {
/* 367 */               rs.mElement_I32 = createUser(rs, DataType.SIGNED_32);
                    }
/* 369 */           return rs.mElement_I32;
                }

/* 372 */       public static Element U64(RenderScript rs) {
/* 373 */           if (rs.mElement_U64 == null) {
/* 374 */               rs.mElement_U64 = createUser(rs, DataType.UNSIGNED_64);
                    }
/* 376 */           return rs.mElement_U64;
                }

/* 379 */       public static Element I64(RenderScript rs) {
/* 380 */           if (rs.mElement_I64 == null) {
/* 381 */               rs.mElement_I64 = createUser(rs, DataType.SIGNED_64);
                    }
/* 383 */           return rs.mElement_I64;
                }

/* 386 */       public static Element F32(RenderScript rs) {
/* 387 */           if (rs.mElement_F32 == null) {
/* 388 */               rs.mElement_F32 = createUser(rs, DataType.FLOAT_32);
                    }
/* 390 */           return rs.mElement_F32;
                }

/* 393 */       public static Element F64(RenderScript rs) {
/* 394 */           if (rs.mElement_F64 == null) {
/* 395 */               rs.mElement_F64 = createUser(rs, DataType.FLOAT_64);
                    }
/* 397 */           return rs.mElement_F64;
                }

/* 400 */       public static Element ELEMENT(RenderScript rs) {
/* 401 */           if (rs.mElement_ELEMENT == null) {
/* 402 */               rs.mElement_ELEMENT = createUser(rs, DataType.RS_ELEMENT);
                    }
/* 404 */           return rs.mElement_ELEMENT;
                }

/* 407 */       public static Element TYPE(RenderScript rs) {
/* 408 */           if (rs.mElement_TYPE == null) {
/* 409 */               rs.mElement_TYPE = createUser(rs, DataType.RS_TYPE);
                    }
/* 411 */           return rs.mElement_TYPE;
                }

/* 414 */       public static Element ALLOCATION(RenderScript rs) {
/* 415 */           if (rs.mElement_ALLOCATION == null) {
/* 416 */               rs.mElement_ALLOCATION = createUser(rs, DataType.RS_ALLOCATION);
                    }
/* 418 */           return rs.mElement_ALLOCATION;
                }

/* 421 */       public static Element SAMPLER(RenderScript rs) {
/* 422 */           if (rs.mElement_SAMPLER == null) {
/* 423 */               rs.mElement_SAMPLER = createUser(rs, DataType.RS_SAMPLER);
                    }
/* 425 */           return rs.mElement_SAMPLER;
                }

/* 428 */       public static Element SCRIPT(RenderScript rs) {
/* 429 */           if (rs.mElement_SCRIPT == null) {
/* 430 */               rs.mElement_SCRIPT = createUser(rs, DataType.RS_SCRIPT);
                    }
/* 432 */           return rs.mElement_SCRIPT;
                }

/* 436 */       public static Element A_8(RenderScript rs) {
/* 437 */           if (rs.mElement_A_8 == null) {
/* 438 */               rs.mElement_A_8 = createPixel(rs, DataType.UNSIGNED_8, DataKind.PIXEL_A);
                    }
/* 440 */           return rs.mElement_A_8;
                }

/* 443 */       public static Element RGB_565(RenderScript rs) {
/* 444 */           if (rs.mElement_RGB_565 == null) {
/* 445 */               rs.mElement_RGB_565 = createPixel(rs, DataType.UNSIGNED_5_6_5, DataKind.PIXEL_RGB);
                    }
/* 447 */           return rs.mElement_RGB_565;
                }

/* 450 */       public static Element RGB_888(RenderScript rs) {
/* 451 */           if (rs.mElement_RGB_888 == null) {
/* 452 */               rs.mElement_RGB_888 = createPixel(rs, DataType.UNSIGNED_8, DataKind.PIXEL_RGB);
                    }
/* 454 */           return rs.mElement_RGB_888;
                }

/* 457 */       public static Element RGBA_5551(RenderScript rs) {
/* 458 */           if (rs.mElement_RGBA_5551 == null) {
/* 459 */               rs.mElement_RGBA_5551 = createPixel(rs, DataType.UNSIGNED_5_5_5_1, DataKind.PIXEL_RGBA);
                    }
/* 461 */           return rs.mElement_RGBA_5551;
                }

/* 464 */       public static Element RGBA_4444(RenderScript rs) {
/* 465 */           if (rs.mElement_RGBA_4444 == null) {
/* 466 */               rs.mElement_RGBA_4444 = createPixel(rs, DataType.UNSIGNED_4_4_4_4, DataKind.PIXEL_RGBA);
                    }
/* 468 */           return rs.mElement_RGBA_4444;
                }

/* 471 */       public static Element RGBA_8888(RenderScript rs) {
/* 472 */           if (rs.mElement_RGBA_8888 == null) {
/* 473 */               rs.mElement_RGBA_8888 = createPixel(rs, DataType.UNSIGNED_8, DataKind.PIXEL_RGBA);
                    }
/* 475 */           return rs.mElement_RGBA_8888;
                }

/* 478 */       public static Element F32_2(RenderScript rs) {
/* 479 */           if (rs.mElement_FLOAT_2 == null) {
/* 480 */               rs.mElement_FLOAT_2 = createVector(rs, DataType.FLOAT_32, 2);
                    }
/* 482 */           return rs.mElement_FLOAT_2;
                }

/* 485 */       public static Element F32_3(RenderScript rs) {
/* 486 */           if (rs.mElement_FLOAT_3 == null) {
/* 487 */               rs.mElement_FLOAT_3 = createVector(rs, DataType.FLOAT_32, 3);
                    }
/* 489 */           return rs.mElement_FLOAT_3;
                }

/* 492 */       public static Element F32_4(RenderScript rs) {
/* 493 */           if (rs.mElement_FLOAT_4 == null) {
/* 494 */               rs.mElement_FLOAT_4 = createVector(rs, DataType.FLOAT_32, 4);
                    }
/* 496 */           return rs.mElement_FLOAT_4;
                }

/* 499 */       public static Element F64_2(RenderScript rs) {
/* 500 */           if (rs.mElement_DOUBLE_2 == null) {
/* 501 */               rs.mElement_DOUBLE_2 = createVector(rs, DataType.FLOAT_64, 2);
                    }
/* 503 */           return rs.mElement_DOUBLE_2;
                }

/* 506 */       public static Element F64_3(RenderScript rs) {
/* 507 */           if (rs.mElement_DOUBLE_3 == null) {
/* 508 */               rs.mElement_DOUBLE_3 = createVector(rs, DataType.FLOAT_64, 3);
                    }
/* 510 */           return rs.mElement_DOUBLE_3;
                }

/* 513 */       public static Element F64_4(RenderScript rs) {
/* 514 */           if (rs.mElement_DOUBLE_4 == null) {
/* 515 */               rs.mElement_DOUBLE_4 = createVector(rs, DataType.FLOAT_64, 4);
                    }
/* 517 */           return rs.mElement_DOUBLE_4;
                }

/* 520 */       public static Element U8_2(RenderScript rs) {
/* 521 */           if (rs.mElement_UCHAR_2 == null) {
/* 522 */               rs.mElement_UCHAR_2 = createVector(rs, DataType.UNSIGNED_8, 2);
                    }
/* 524 */           return rs.mElement_UCHAR_2;
                }

/* 527 */       public static Element U8_3(RenderScript rs) {
/* 528 */           if (rs.mElement_UCHAR_3 == null) {
/* 529 */               rs.mElement_UCHAR_3 = createVector(rs, DataType.UNSIGNED_8, 3);
                    }
/* 531 */           return rs.mElement_UCHAR_3;
                }

/* 534 */       public static Element U8_4(RenderScript rs) {
/* 535 */           if (rs.mElement_UCHAR_4 == null) {
/* 536 */               rs.mElement_UCHAR_4 = createVector(rs, DataType.UNSIGNED_8, 4);
                    }
/* 538 */           return rs.mElement_UCHAR_4;
                }

/* 541 */       public static Element I8_2(RenderScript rs) {
/* 542 */           if (rs.mElement_CHAR_2 == null) {
/* 543 */               rs.mElement_CHAR_2 = createVector(rs, DataType.SIGNED_8, 2);
                    }
/* 545 */           return rs.mElement_CHAR_2;
                }

/* 548 */       public static Element I8_3(RenderScript rs) {
/* 549 */           if (rs.mElement_CHAR_3 == null) {
/* 550 */               rs.mElement_CHAR_3 = createVector(rs, DataType.SIGNED_8, 3);
                    }
/* 552 */           return rs.mElement_CHAR_3;
                }

/* 555 */       public static Element I8_4(RenderScript rs) {
/* 556 */           if (rs.mElement_CHAR_4 == null) {
/* 557 */               rs.mElement_CHAR_4 = createVector(rs, DataType.SIGNED_8, 4);
                    }
/* 559 */           return rs.mElement_CHAR_4;
                }

/* 562 */       public static Element U16_2(RenderScript rs) {
/* 563 */           if (rs.mElement_USHORT_2 == null) {
/* 564 */               rs.mElement_USHORT_2 = createVector(rs, DataType.UNSIGNED_16, 2);
                    }
/* 566 */           return rs.mElement_USHORT_2;
                }

/* 569 */       public static Element U16_3(RenderScript rs) {
/* 570 */           if (rs.mElement_USHORT_3 == null) {
/* 571 */               rs.mElement_USHORT_3 = createVector(rs, DataType.UNSIGNED_16, 3);
                    }
/* 573 */           return rs.mElement_USHORT_3;
                }

/* 576 */       public static Element U16_4(RenderScript rs) {
/* 577 */           if (rs.mElement_USHORT_4 == null) {
/* 578 */               rs.mElement_USHORT_4 = createVector(rs, DataType.UNSIGNED_16, 4);
                    }
/* 580 */           return rs.mElement_USHORT_4;
                }

/* 583 */       public static Element I16_2(RenderScript rs) {
/* 584 */           if (rs.mElement_SHORT_2 == null) {
/* 585 */               rs.mElement_SHORT_2 = createVector(rs, DataType.SIGNED_16, 2);
                    }
/* 587 */           return rs.mElement_SHORT_2;
                }

/* 590 */       public static Element I16_3(RenderScript rs) {
/* 591 */           if (rs.mElement_SHORT_3 == null) {
/* 592 */               rs.mElement_SHORT_3 = createVector(rs, DataType.SIGNED_16, 3);
                    }
/* 594 */           return rs.mElement_SHORT_3;
                }

/* 597 */       public static Element I16_4(RenderScript rs) {
/* 598 */           if (rs.mElement_SHORT_4 == null) {
/* 599 */               rs.mElement_SHORT_4 = createVector(rs, DataType.SIGNED_16, 4);
                    }
/* 601 */           return rs.mElement_SHORT_4;
                }

/* 604 */       public static Element U32_2(RenderScript rs) {
/* 605 */           if (rs.mElement_UINT_2 == null) {
/* 606 */               rs.mElement_UINT_2 = createVector(rs, DataType.UNSIGNED_32, 2);
                    }
/* 608 */           return rs.mElement_UINT_2;
                }

/* 611 */       public static Element U32_3(RenderScript rs) {
/* 612 */           if (rs.mElement_UINT_3 == null) {
/* 613 */               rs.mElement_UINT_3 = createVector(rs, DataType.UNSIGNED_32, 3);
                    }
/* 615 */           return rs.mElement_UINT_3;
                }

/* 618 */       public static Element U32_4(RenderScript rs) {
/* 619 */           if (rs.mElement_UINT_4 == null) {
/* 620 */               rs.mElement_UINT_4 = createVector(rs, DataType.UNSIGNED_32, 4);
                    }
/* 622 */           return rs.mElement_UINT_4;
                }

/* 625 */       public static Element I32_2(RenderScript rs) {
/* 626 */           if (rs.mElement_INT_2 == null) {
/* 627 */               rs.mElement_INT_2 = createVector(rs, DataType.SIGNED_32, 2);
                    }
/* 629 */           return rs.mElement_INT_2;
                }

/* 632 */       public static Element I32_3(RenderScript rs) {
/* 633 */           if (rs.mElement_INT_3 == null) {
/* 634 */               rs.mElement_INT_3 = createVector(rs, DataType.SIGNED_32, 3);
                    }
/* 636 */           return rs.mElement_INT_3;
                }

/* 639 */       public static Element I32_4(RenderScript rs) {
/* 640 */           if (rs.mElement_INT_4 == null) {
/* 641 */               rs.mElement_INT_4 = createVector(rs, DataType.SIGNED_32, 4);
                    }
/* 643 */           return rs.mElement_INT_4;
                }

/* 646 */       public static Element U64_2(RenderScript rs) {
/* 647 */           if (rs.mElement_ULONG_2 == null) {
/* 648 */               rs.mElement_ULONG_2 = createVector(rs, DataType.UNSIGNED_64, 2);
                    }
/* 650 */           return rs.mElement_ULONG_2;
                }

/* 653 */       public static Element U64_3(RenderScript rs) {
/* 654 */           if (rs.mElement_ULONG_3 == null) {
/* 655 */               rs.mElement_ULONG_3 = createVector(rs, DataType.UNSIGNED_64, 3);
                    }
/* 657 */           return rs.mElement_ULONG_3;
                }

/* 660 */       public static Element U64_4(RenderScript rs) {
/* 661 */           if (rs.mElement_ULONG_4 == null) {
/* 662 */               rs.mElement_ULONG_4 = createVector(rs, DataType.UNSIGNED_64, 4);
                    }
/* 664 */           return rs.mElement_ULONG_4;
                }

/* 667 */       public static Element I64_2(RenderScript rs) {
/* 668 */           if (rs.mElement_LONG_2 == null) {
/* 669 */               rs.mElement_LONG_2 = createVector(rs, DataType.SIGNED_64, 2);
                    }
/* 671 */           return rs.mElement_LONG_2;
                }

/* 674 */       public static Element I64_3(RenderScript rs) {
/* 675 */           if (rs.mElement_LONG_3 == null) {
/* 676 */               rs.mElement_LONG_3 = createVector(rs, DataType.SIGNED_64, 3);
                    }
/* 678 */           return rs.mElement_LONG_3;
                }

/* 681 */       public static Element I64_4(RenderScript rs) {
/* 682 */           if (rs.mElement_LONG_4 == null) {
/* 683 */               rs.mElement_LONG_4 = createVector(rs, DataType.SIGNED_64, 4);
                    }
/* 685 */           return rs.mElement_LONG_4;
                }

/* 688 */       public static Element MATRIX_4X4(RenderScript rs) {
/* 689 */           if (rs.mElement_MATRIX_4X4 == null) {
/* 690 */               rs.mElement_MATRIX_4X4 = createUser(rs, DataType.MATRIX_4X4);
                    }
/* 692 */           return rs.mElement_MATRIX_4X4;
                }

/* 695 */       public static Element MATRIX_3X3(RenderScript rs) {
/* 696 */           if (rs.mElement_MATRIX_3X3 == null) {
/* 697 */               rs.mElement_MATRIX_3X3 = createUser(rs, DataType.MATRIX_3X3);
                    }
/* 699 */           return rs.mElement_MATRIX_3X3;
                }

/* 702 */       public static Element MATRIX_2X2(RenderScript rs) {
/* 703 */           if (rs.mElement_MATRIX_2X2 == null) {
/* 704 */               rs.mElement_MATRIX_2X2 = createUser(rs, DataType.MATRIX_2X2);
                    }
/* 706 */           return rs.mElement_MATRIX_2X2;
                }

/* 709 */       Element(int id, RenderScript rs, Element[] e, String[] n, int[] as) {
/* 710 */           super(id, rs);
/* 711 */           this.mSize = 0;
/* 712 */           this.mVectorSize = 1;
/* 713 */           this.mElements = e;
/* 714 */           this.mElementNames = n;
/* 715 */           this.mArraySizes = as;
/* 716 */           this.mType = DataType.NONE;
/* 717 */           this.mKind = DataKind.USER;
/* 718 */           this.mOffsetInBytes = new int[this.mElements.length];
                    for (int ct = 0; ct < this.mElements.length; ct++) {
/* 720 */               this.mOffsetInBytes[ct] = this.mSize;
/* 721 */               this.mSize += this.mElements[ct].mSize * this.mArraySizes[ct];
                    }
/* 723 */           updateVisibleSubElements();
                }

/* 726 */       Element(int id, RenderScript rs, DataType dt, DataKind dk, boolean norm, int size) {
/* 727 */           super(id, rs);
/* 728 */           if (dt != DataType.UNSIGNED_5_6_5 && dt != DataType.UNSIGNED_4_4_4_4 && dt != DataType.UNSIGNED_5_5_5_1) {
/* 731 */               if (size == 3) {
/* 732 */                   this.mSize = dt.mSize * 4;
                        } else {
/* 734 */                   this.mSize = dt.mSize * size;
                        }
                    } else {
/* 737 */               this.mSize = dt.mSize;
                    }
/* 739 */           this.mType = dt;
/* 740 */           this.mKind = dk;
/* 741 */           this.mNormalized = norm;
/* 742 */           this.mVectorSize = size;
                }

/* 745 */       Element(int id, RenderScript rs) {
/* 746 */           super(id, rs);
                }

/* 757 */       static Element createUser(RenderScript rs, DataType dt) {
/* 758 */           if (RenderScript.isNative) {
/* 759 */               RenderScriptThunker rst = (RenderScriptThunker) rs;
/* 760 */               return ElementThunker.create(rst, dt);
                    }
/* 762 */           DataKind dk = DataKind.USER;
/* 763 */           int id = rs.nElementCreate(dt.mID, dk.mID, false, 1);
/* 766 */           return new Element(id, rs, dt, dk, false, 1);
                }

/* 782 */       public static Element createVector(RenderScript rs, DataType dt, int size) {
/* 783 */           if (RenderScript.isNative) {
/* 784 */               RenderScriptThunker rst = (RenderScriptThunker) rs;
/* 785 */               return ElementThunker.createVector(rst, dt, size);
                    }
/* 787 */           if (size < 2 || size > 4) {
/* 788 */               throw new RSIllegalArgumentException("Vector size out of range 2-4.");
                    }
                    switch (dt) {
                        case FLOAT_32:
                        case FLOAT_64:
                        case SIGNED_8:
                        case SIGNED_16:
                        case SIGNED_32:
                        case SIGNED_64:
                        case UNSIGNED_8:
                        case UNSIGNED_16:
                        case UNSIGNED_32:
                        case UNSIGNED_64:
                        case BOOLEAN:
/* 804 */                   DataKind dk = DataKind.USER;
/* 805 */                   int id = rs.nElementCreate(dt.mID, dk.mID, false, size);
/* 807 */                   return new Element(id, rs, dt, dk, false, size);
                        default:
/* 811 */                   throw new RSIllegalArgumentException("Cannot create vector of non-primitive type.");
                    }
                }

/* 829 */       public static Element createPixel(RenderScript rs, DataType dt, DataKind dk) {
/* 830 */           if (RenderScript.isNative) {
/* 831 */               RenderScriptThunker rst = (RenderScriptThunker) rs;
/* 832 */               return ElementThunker.createPixel(rst, dt, dk);
                    }
/* 835 */           if (dk != DataKind.PIXEL_L && dk != DataKind.PIXEL_A && dk != DataKind.PIXEL_LA && dk != DataKind.PIXEL_RGB && dk != DataKind.PIXEL_RGBA && dk != DataKind.PIXEL_DEPTH && dk != DataKind.PIXEL_YUV) {
/* 842 */               throw new RSIllegalArgumentException("Unsupported DataKind");
                    }
/* 844 */           if (dt != DataType.UNSIGNED_8 && dt != DataType.UNSIGNED_16 && dt != DataType.UNSIGNED_5_6_5 && dt != DataType.UNSIGNED_4_4_4_4 && dt != DataType.UNSIGNED_5_5_5_1) {
/* 849 */               throw new RSIllegalArgumentException("Unsupported DataType");
                    }
/* 851 */           if (dt == DataType.UNSIGNED_5_6_5 && dk != DataKind.PIXEL_RGB) {
/* 852 */               throw new RSIllegalArgumentException("Bad kind and type combo");
                    }
/* 854 */           if (dt == DataType.UNSIGNED_5_5_5_1 && dk != DataKind.PIXEL_RGBA) {
/* 855 */               throw new RSIllegalArgumentException("Bad kind and type combo");
                    }
/* 857 */           if (dt == DataType.UNSIGNED_4_4_4_4 && dk != DataKind.PIXEL_RGBA) {
/* 858 */               throw new RSIllegalArgumentException("Bad kind and type combo");
                    }
/* 860 */           if (dt == DataType.UNSIGNED_16 && dk != DataKind.PIXEL_DEPTH) {
/* 862 */               throw new RSIllegalArgumentException("Bad kind and type combo");
                    }
/* 865 */           int size = 1;
                    switch (dk) {
                        case PIXEL_LA:
/* 868 */                   size = 2;
                            break;
                        case PIXEL_RGB:
/* 871 */                   size = 3;
                            break;
                        case PIXEL_RGBA:
/* 874 */                   size = 4;
                            break;
                    }
/* 878 */           int id = rs.nElementCreate(dt.mID, dk.mID, true, size);
/* 880 */           return new Element(id, rs, dt, dk, true, size);
                }

/* 895 */       public boolean isCompatible(Element e) {
/* 896 */           if (equals(e)) {
                        return true;
                    }
                    return this.mSize == e.mSize && this.mType != DataType.NONE && this.mType == e.mType && this.mVectorSize == e.mVectorSize;
                }

/* 929 */       public static class Builder {
                    int[] mArraySizes;
                    int mCount;
                    String[] mElementNames;
                    Element[] mElements;
                    RenderScript mRS;
                    int mSkipPadding;

                    /* JADX INFO: renamed from: mT */
                    ElementThunker.BuilderThunker f23mT;

/* 930 */           public Builder(RenderScript rs) {
/* 932 */               if (RenderScript.isNative) {
/* 934 */                   this.f23mT = new ElementThunker.BuilderThunker(rs);
                        }
/* 936 */               this.mRS = rs;
/* 937 */               this.mCount = 0;
/* 938 */               this.mElements = new Element[8];
/* 939 */               this.mElementNames = new String[8];
/* 940 */               this.mArraySizes = new int[8];
                    }

/* 950 */           public Builder add(Element element, String name, int arraySize) {
/* 951 */               if (this.f23mT != null) {
/* 952 */                   this.f23mT.add(element, name, arraySize);
                        } else {
/* 956 */                   if (arraySize < 1) {
/* 957 */                       throw new RSIllegalArgumentException("Array size cannot be less than 1.");
                            }
/* 961 */                   if (this.mSkipPadding != 0 && name.startsWith("#padding_")) {
/* 963 */                       this.mSkipPadding = 0;
                            } else {
/* 968 */                       if (element.mVectorSize == 3) {
/* 969 */                           this.mSkipPadding = 1;
                                } else {
/* 971 */                           this.mSkipPadding = 0;
                                }
/* 974 */                       if (this.mCount == this.mElements.length) {
/* 975 */                           Element[] e = new Element[this.mCount + 8];
/* 976 */                           String[] s = new String[this.mCount + 8];
/* 977 */                           int[] as = new int[this.mCount + 8];
/* 978 */                           System.arraycopy(this.mElements, 0, e, 0, this.mCount);
/* 979 */                           System.arraycopy(this.mElementNames, 0, s, 0, this.mCount);
/* 980 */                           System.arraycopy(this.mArraySizes, 0, as, 0, this.mCount);
/* 981 */                           this.mElements = e;
/* 982 */                           this.mElementNames = s;
/* 983 */                           this.mArraySizes = as;
                                }
/* 985 */                       this.mElements[this.mCount] = element;
/* 986 */                       this.mElementNames[this.mCount] = name;
/* 987 */                       this.mArraySizes[this.mCount] = arraySize;
/* 988 */                       this.mCount++;
                            }
                        }
/* 990 */               return this;
                    }

/* 999 */           public Builder add(Element element, String name) {
/* 1000 */              return add(element, name, 1);
                    }

/* 1009 */          public Element create() {
/* 1010 */              if (this.f23mT != null) {
/* 1011 */                  return this.f23mT.create(this.mRS);
                        }
/* 1014 */              this.mRS.validate();
/* 1015 */              Element[] ein = new Element[this.mCount];
/* 1016 */              String[] sin = new String[this.mCount];
/* 1017 */              int[] asin = new int[this.mCount];
/* 1018 */              System.arraycopy(this.mElements, 0, ein, 0, this.mCount);
/* 1019 */              System.arraycopy(this.mElementNames, 0, sin, 0, this.mCount);
/* 1020 */              System.arraycopy(this.mArraySizes, 0, asin, 0, this.mCount);
/* 1022 */              int[] ids = new int[ein.length];
                        for (int ct = 0; ct < ein.length; ct++) {
/* 1024 */                  ids[ct] = ein[ct].getID(this.mRS);
                        }
/* 1027 */              int id = this.mRS.nElementCreate2(ids, sin, asin);
/* 1028 */              return new Element(id, this.mRS, ein, sin, asin);
                    }
                }
            }
