            package com.fasterxml.jackson.core.sym;

            import com.fasterxml.jackson.core.util.ArraysCompat;
            import com.fasterxml.jackson.core.util.InternCache;
            import java.util.Arrays;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 216 */   public final class CharsToNameCanonicalizer {
                protected static final int DEFAULT_TABLE_SIZE = 64;
                public static final int HASH_MULT = 33;
                static final int MAX_COLL_CHAIN_FOR_REUSE = 63;
                static final int MAX_COLL_CHAIN_LENGTH = 255;
                static final int MAX_ENTRIES_FOR_REUSE = 12000;
                protected static final int MAX_TABLE_SIZE = 65536;
                static final CharsToNameCanonicalizer sBootstrapSymbolTable = new CharsToNameCanonicalizer();
                protected Bucket[] _buckets;
                protected final boolean _canonicalize;
                protected boolean _dirty;
                private final int _hashSeed;
                protected int _indexMask;
                protected final boolean _intern;
                protected int _longestCollisionList;
                protected CharsToNameCanonicalizer _parent;
                protected int _size;
                protected int _sizeThreshold;
                protected String[] _symbols;

/* 217 */       public static CharsToNameCanonicalizer createRoot() {
/* 218 */           long jCurrentTimeMillis = System.currentTimeMillis();
/* 221 */           return createRoot((((int) (jCurrentTimeMillis >>> 32)) + ((int) jCurrentTimeMillis)) | 1);
                }

/* 224 */       protected static CharsToNameCanonicalizer createRoot(int i) {
/* 225 */           return sBootstrapSymbolTable.makeOrphan(i);
                }

/* 234 */       private CharsToNameCanonicalizer() {
/* 237 */           this._canonicalize = true;
/* 238 */           this._intern = true;
/* 240 */           this._dirty = true;
/* 241 */           this._hashSeed = 0;
/* 242 */           this._longestCollisionList = 0;
/* 243 */           initTables(64);
                }

/* 247 */       private void initTables(int i) {
/* 248 */           this._symbols = new String[i];
/* 249 */           this._buckets = new Bucket[i >> 1];
/* 251 */           this._indexMask = i - 1;
/* 252 */           this._size = 0;
/* 253 */           this._longestCollisionList = 0;
/* 255 */           this._sizeThreshold = _thresholdSize(i);
                }

/* 258 */       private static int _thresholdSize(int i) {
/* 259 */           return i - (i >> 2);
                }

/* 268 */       private CharsToNameCanonicalizer(CharsToNameCanonicalizer charsToNameCanonicalizer, boolean z, boolean z2, String[] strArr, Bucket[] bucketArr, int i, int i2, int i3) {
/* 270 */           this._parent = charsToNameCanonicalizer;
/* 271 */           this._canonicalize = z;
/* 272 */           this._intern = z2;
/* 274 */           this._symbols = strArr;
/* 275 */           this._buckets = bucketArr;
/* 276 */           this._size = i;
/* 277 */           this._hashSeed = i2;
/* 279 */           int length = strArr.length;
/* 280 */           this._sizeThreshold = _thresholdSize(length);
/* 281 */           this._indexMask = length - 1;
/* 282 */           this._longestCollisionList = i3;
/* 285 */           this._dirty = false;
                }

/* 312 */       public CharsToNameCanonicalizer makeChild(boolean z, boolean z2) {
                    String[] strArr;
                    Bucket[] bucketArr;
                    int i;
                    int i2;
                    int i3;
                    synchronized (this) {
                        try {
/* 314 */                   strArr = this._symbols;
/* 315 */                   bucketArr = this._buckets;
/* 316 */                   i = this._size;
/* 317 */                   i2 = this._hashSeed;
/* 318 */                   i3 = this._longestCollisionList;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 321 */           return new CharsToNameCanonicalizer(this, z, z2, strArr, bucketArr, i, i2, i3);
                }

/* 326 */       private CharsToNameCanonicalizer makeOrphan(int i) {
/* 327 */           return new CharsToNameCanonicalizer(null, true, true, this._symbols, this._buckets, this._size, i, this._longestCollisionList);
                }

/* 345 */       private void mergeChild(CharsToNameCanonicalizer charsToNameCanonicalizer) {
/* 346 */           if (charsToNameCanonicalizer.size() > 12000 || charsToNameCanonicalizer._longestCollisionList > 63) {
                        synchronized (this) {
                            try {
/* 352 */                       initTables(64);
/* 355 */                       this._dirty = false;
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
/* 376 */               return;
                    }
/* 360 */           if (charsToNameCanonicalizer.size() > size()) {
                        synchronized (this) {
                            try {
/* 365 */                       this._symbols = charsToNameCanonicalizer._symbols;
/* 366 */                       this._buckets = charsToNameCanonicalizer._buckets;
/* 367 */                       this._size = charsToNameCanonicalizer._size;
/* 368 */                       this._sizeThreshold = charsToNameCanonicalizer._sizeThreshold;
/* 369 */                       this._indexMask = charsToNameCanonicalizer._indexMask;
/* 370 */                       this._longestCollisionList = charsToNameCanonicalizer._longestCollisionList;
/* 373 */                       this._dirty = false;
                            } catch (Throwable th2) {
                                throw th2;
                            }
                        }
                    }
                }

/* 380 */       public void release() {
/* 381 */           if (maybeDirty() && this._parent != null) {
/* 385 */               this._parent.mergeChild(this);
/* 390 */               this._dirty = false;
                    }
                }

/* 399 */       public int size() {
/* 400 */           return this._size;
                }

/* 408 */       public int bucketCount() {
/* 409 */           return this._symbols.length;
                }

/* 410 */       public boolean maybeDirty() {
/* 411 */           return this._dirty;
                }

/* 412 */       public int hashSeed() {
/* 413 */           return this._hashSeed;
                }

/* 425 */       public int collisionCount() {
                    int length = 0;
                    for (Bucket bucket : this._buckets) {
/* 427 */               if (bucket != null) {
/* 428 */                   length += bucket.length();
                        }
                    }
/* 431 */           return length;
                }

/* 442 */       public int maxCollisionLength() {
/* 443 */           return this._longestCollisionList;
                }

/* 453 */       public String findSymbol(char[] cArr, int i, int i2, int i3) {
                    int i_hashToIndex;
                    String strFind;
/* 454 */           if (i2 < 1) {
/* 455 */               return "";
                    }
/* 457 */           if (!this._canonicalize) {
/* 458 */               return new String(cArr, i, i2);
                    }
/* 466 */           int i_hashToIndex2 = _hashToIndex(i3);
/* 467 */           String str = this._symbols[i_hashToIndex2];
/* 470 */           if (str != null) {
/* 472 */               if (str.length() == i2) {
/* 473 */                   int i4 = 0;
/* 475 */                   while (str.charAt(i4) == cArr[i + i4] && (i4 = i4 + 1) < i2) {
                            }
/* 480 */                   if (i4 == i2) {
                                return str;
                            }
                        }
/* 485 */               Bucket bucket = this._buckets[i_hashToIndex2 >> 1];
/* 486 */               if (bucket != null && (strFind = bucket.find(cArr, i, i2)) != null) {
                            return strFind;
                        }
                    }
/* 494 */           if (!this._dirty) {
/* 495 */               copyArrays();
/* 496 */               this._dirty = true;
                        i_hashToIndex = i_hashToIndex2;
                    } else if (this._size >= this._sizeThreshold) {
/* 498 */               rehash();
/* 502 */               i_hashToIndex = _hashToIndex(calcHash(cArr, i, i2));
                    } else {
                        i_hashToIndex = i_hashToIndex2;
                    }
/* 505 */           String str2 = new String(cArr, i, i2);
/* 506 */           if (this._intern) {
/* 507 */               str2 = InternCache.instance.intern(str2);
                    }
/* 509 */           this._size++;
/* 511 */           if (this._symbols[i_hashToIndex] == null) {
/* 512 */               this._symbols[i_hashToIndex] = str2;
/* 523 */               return str2;
                    }
/* 514 */           int i5 = i_hashToIndex >> 1;
/* 515 */           Bucket bucket2 = new Bucket(str2, this._buckets[i5]);
/* 516 */           this._buckets[i5] = bucket2;
/* 517 */           this._longestCollisionList = Math.max(bucket2.length(), this._longestCollisionList);
/* 518 */           if (this._longestCollisionList > 255) {
/* 519 */               reportTooManyCollisions(255);
/* 523 */               return str2;
                    }
/* 523 */           return str2;
                }

/* 531 */       public int _hashToIndex(int i) {
/* 533 */           return ((i >>> 15) + i) & this._indexMask;
                }

/* 546 */       public int calcHash(char[] cArr, int i, int i2) {
/* 547 */           int i3 = this._hashSeed;
/* 548 */           int i4 = 0;
                    while (i4 < i2) {
/* 549 */               int i5 = cArr[i4] + (i3 * 33);
/* 548 */               i4++;
                        i3 = i5;
                    }
/* 552 */           if (i3 == 0) {
                        return 1;
                    }
                    return i3;
                }

/* 556 */       public int calcHash(String str) {
/* 557 */           int length = str.length();
/* 559 */           int i = this._hashSeed;
/* 560 */           int i2 = 0;
                    while (i2 < length) {
/* 561 */               int iCharAt = str.charAt(i2) + (i * 33);
/* 560 */               i2++;
                        i = iCharAt;
                    }
/* 564 */           if (i == 0) {
                        return 1;
                    }
                    return i;
                }

/* 578 */       private void copyArrays() {
/* 579 */           String[] strArr = this._symbols;
/* 580 */           this._symbols = (String[]) ArraysCompat.copyOf(strArr, strArr.length);
/* 581 */           Bucket[] bucketArr = this._buckets;
/* 582 */           this._buckets = (Bucket[]) ArraysCompat.copyOf(bucketArr, bucketArr.length);
                }

/* 593 */       private void rehash() {
/* 594 */           int length = this._symbols.length;
/* 595 */           int i = length + length;
/* 601 */           if (i > 65536) {
/* 606 */               this._size = 0;
/* 607 */               Arrays.fill(this._symbols, (Object) null);
/* 608 */               Arrays.fill(this._buckets, (Object) null);
/* 609 */               this._dirty = true;
/* 666 */               return;
                    }
/* 613 */           String[] strArr = this._symbols;
/* 614 */           Bucket[] bucketArr = this._buckets;
/* 615 */           this._symbols = new String[i];
/* 616 */           this._buckets = new Bucket[i >> 1];
/* 618 */           this._indexMask = i - 1;
/* 619 */           this._sizeThreshold = _thresholdSize(i);
                    int iMax = 0;
                    int i2 = 0;
/* 627 */           for (int i3 = 0; i3 < length; i3++) {
/* 628 */               String str = strArr[i3];
/* 629 */               if (str != null) {
/* 630 */                   i2++;
/* 631 */                   int i_hashToIndex = _hashToIndex(calcHash(str));
/* 632 */                   if (this._symbols[i_hashToIndex] == null) {
/* 633 */                       this._symbols[i_hashToIndex] = str;
                            } else {
/* 635 */                       int i4 = i_hashToIndex >> 1;
/* 636 */                       Bucket bucket = new Bucket(str, this._buckets[i4]);
/* 637 */                       this._buckets[i4] = bucket;
/* 638 */                       iMax = Math.max(iMax, bucket.length());
                            }
                        }
                    }
/* 643 */           int i5 = length >> 1;
                    int i6 = 0;
                    int i7 = i2;
                    int i8 = iMax;
/* 644 */           while (i6 < i5) {
                        int iMax2 = i8;
/* 646 */               for (Bucket next = bucketArr[i6]; next != null; next = next.getNext()) {
/* 647 */                   i7++;
/* 648 */                   String symbol = next.getSymbol();
/* 649 */                   int i_hashToIndex2 = _hashToIndex(calcHash(symbol));
/* 650 */                   if (this._symbols[i_hashToIndex2] == null) {
/* 651 */                       this._symbols[i_hashToIndex2] = symbol;
                            } else {
/* 653 */                       int i9 = i_hashToIndex2 >> 1;
/* 654 */                       Bucket bucket2 = new Bucket(symbol, this._buckets[i9]);
/* 655 */                       this._buckets[i9] = bucket2;
/* 656 */                       iMax2 = Math.max(iMax2, bucket2.length());
                            }
                        }
/* 644 */               i6++;
                        i8 = iMax2;
                    }
/* 661 */           this._longestCollisionList = i8;
/* 663 */           if (i7 != this._size) {
/* 664 */               throw new Error("Internal error on SymbolTable.rehash(): had " + this._size + " entries; now have " + i7 + ".");
                    }
                }

/* 672 */       protected void reportTooManyCollisions(int i) {
/* 673 */           throw new IllegalStateException("Longest collision chain in symbol table (of size " + this._size + ") now exceeds maximum, " + i + " -- suspect a DoS attack based on hash collisions");
                }

/* 691 */       static final class Bucket {
                    private final int _length;
                    private final Bucket _next;
                    private final String _symbol;

/* 692 */           public Bucket(String str, Bucket bucket) {
/* 694 */               this._symbol = str;
/* 695 */               this._next = bucket;
/* 696 */               this._length = bucket == null ? 1 : bucket._length + 1;
                    }

/* 698 */           public String getSymbol() {
/* 699 */               return this._symbol;
                    }

/* 699 */           public Bucket getNext() {
/* 700 */               return this._next;
                    }

/* 700 */           public int length() {
/* 701 */               return this._length;
                    }

/* 703 */           public String find(char[] cArr, int i, int i2) {
/* 704 */               String symbol = this._symbol;
/* 705 */               Bucket next = this._next;
                        while (true) {
/* 708 */                   if (symbol.length() == i2) {
/* 709 */                       int i3 = 0;
/* 711 */                       while (symbol.charAt(i3) == cArr[i + i3] && (i3 = i3 + 1) < i2) {
                                }
/* 715 */                       if (i3 == i2) {
/* 725 */                           return symbol;
                                }
                            }
/* 719 */                   if (next != null) {
/* 722 */                       symbol = next.getSymbol();
/* 723 */                       next = next.getNext();
                            } else {
/* 725 */                       return null;
                            }
                        }
                    }
                }
            }
