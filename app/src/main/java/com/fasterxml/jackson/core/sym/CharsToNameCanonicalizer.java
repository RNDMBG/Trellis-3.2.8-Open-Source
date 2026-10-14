            package com.fasterxml.jackson.core.sym;

            import com.fasterxml.jackson.core.util.ArraysCompat;
            import com.fasterxml.jackson.core.util.InternCache;
            import java.util.Arrays;
            
            
   public final class CharsToNameCanonicalizer {
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

       public static CharsToNameCanonicalizer createRoot() {
           long jCurrentTimeMillis = System.currentTimeMillis();
           return createRoot((((int) (jCurrentTimeMillis >>> 32)) + ((int) jCurrentTimeMillis)) | 1);
                }

       protected static CharsToNameCanonicalizer createRoot(int i) {
           return sBootstrapSymbolTable.makeOrphan(i);
                }

       private CharsToNameCanonicalizer() {
           this._canonicalize = true;
           this._intern = true;
           this._dirty = true;
           this._hashSeed = 0;
           this._longestCollisionList = 0;
           initTables(64);
                }

       private void initTables(int i) {
           this._symbols = new String[i];
           this._buckets = new Bucket[i >> 1];
           this._indexMask = i - 1;
           this._size = 0;
           this._longestCollisionList = 0;
           this._sizeThreshold = _thresholdSize(i);
                }

       private static int _thresholdSize(int i) {
           return i - (i >> 2);
                }

       private CharsToNameCanonicalizer(CharsToNameCanonicalizer charsToNameCanonicalizer, boolean z, boolean z2, String[] strArr, Bucket[] bucketArr, int i, int i2, int i3) {
           this._parent = charsToNameCanonicalizer;
           this._canonicalize = z;
           this._intern = z2;
           this._symbols = strArr;
           this._buckets = bucketArr;
           this._size = i;
           this._hashSeed = i2;
           int length = strArr.length;
           this._sizeThreshold = _thresholdSize(length);
           this._indexMask = length - 1;
           this._longestCollisionList = i3;
           this._dirty = false;
                }

       public CharsToNameCanonicalizer makeChild(boolean z, boolean z2) {
                    String[] strArr;
                    Bucket[] bucketArr;
                    int i;
                    int i2;
                    int i3;
                    synchronized (this) {
                        try {
                   strArr = this._symbols;
                   bucketArr = this._buckets;
                   i = this._size;
                   i2 = this._hashSeed;
                   i3 = this._longestCollisionList;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
           return new CharsToNameCanonicalizer(this, z, z2, strArr, bucketArr, i, i2, i3);
                }

       private CharsToNameCanonicalizer makeOrphan(int i) {
           return new CharsToNameCanonicalizer(null, true, true, this._symbols, this._buckets, this._size, i, this._longestCollisionList);
                }

       private void mergeChild(CharsToNameCanonicalizer charsToNameCanonicalizer) {
           if (charsToNameCanonicalizer.size() > 12000 || charsToNameCanonicalizer._longestCollisionList > 63) {
                        synchronized (this) {
                            try {
                       initTables(64);
                       this._dirty = false;
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
               return;
                    }
           if (charsToNameCanonicalizer.size() > size()) {
                        synchronized (this) {
                            try {
                       this._symbols = charsToNameCanonicalizer._symbols;
                       this._buckets = charsToNameCanonicalizer._buckets;
                       this._size = charsToNameCanonicalizer._size;
                       this._sizeThreshold = charsToNameCanonicalizer._sizeThreshold;
                       this._indexMask = charsToNameCanonicalizer._indexMask;
                       this._longestCollisionList = charsToNameCanonicalizer._longestCollisionList;
                       this._dirty = false;
                            } catch (Throwable th2) {
                                throw th2;
                            }
                        }
                    }
                }

       public void release() {
           if (maybeDirty() && this._parent != null) {
               this._parent.mergeChild(this);
               this._dirty = false;
                    }
                }

       public int size() {
           return this._size;
                }

       public int bucketCount() {
           return this._symbols.length;
                }

       public boolean maybeDirty() {
           return this._dirty;
                }

       public int hashSeed() {
           return this._hashSeed;
                }

       public int collisionCount() {
                    int length = 0;
                    for (Bucket bucket : this._buckets) {
               if (bucket != null) {
                   length += bucket.length();
                        }
                    }
           return length;
                }

       public int maxCollisionLength() {
           return this._longestCollisionList;
                }

       public String findSymbol(char[] cArr, int i, int i2, int i3) {
                    int i_hashToIndex;
                    String strFind;
           if (i2 < 1) {
               return "";
                    }
           if (!this._canonicalize) {
               return new String(cArr, i, i2);
                    }
           int i_hashToIndex2 = _hashToIndex(i3);
           String str = this._symbols[i_hashToIndex2];
           if (str != null) {
               if (str.length() == i2) {
                   int i4 = 0;
                   while (str.charAt(i4) == cArr[i + i4] && (i4 = i4 + 1) < i2) {
                            }
                   if (i4 == i2) {
                                return str;
                            }
                        }
               Bucket bucket = this._buckets[i_hashToIndex2 >> 1];
               if (bucket != null && (strFind = bucket.find(cArr, i, i2)) != null) {
                            return strFind;
                        }
                    }
           if (!this._dirty) {
               copyArrays();
               this._dirty = true;
                        i_hashToIndex = i_hashToIndex2;
                    } else if (this._size >= this._sizeThreshold) {
               rehash();
               i_hashToIndex = _hashToIndex(calcHash(cArr, i, i2));
                    } else {
                        i_hashToIndex = i_hashToIndex2;
                    }
           String str2 = new String(cArr, i, i2);
           if (this._intern) {
               str2 = InternCache.instance.intern(str2);
                    }
           this._size++;
           if (this._symbols[i_hashToIndex] == null) {
               this._symbols[i_hashToIndex] = str2;
               return str2;
                    }
           int i5 = i_hashToIndex >> 1;
           Bucket bucket2 = new Bucket(str2, this._buckets[i5]);
           this._buckets[i5] = bucket2;
           this._longestCollisionList = Math.max(bucket2.length(), this._longestCollisionList);
           if (this._longestCollisionList > 255) {
               reportTooManyCollisions(255);
               return str2;
                    }
           return str2;
                }

       public int _hashToIndex(int i) {
           return ((i >>> 15) + i) & this._indexMask;
                }

       public int calcHash(char[] cArr, int i, int i2) {
           int i3 = this._hashSeed;
           int i4 = 0;
                    while (i4 < i2) {
               int i5 = cArr[i4] + (i3 * 33);
               i4++;
                        i3 = i5;
                    }
           if (i3 == 0) {
                        return 1;
                    }
                    return i3;
                }

       public int calcHash(String str) {
           int length = str.length();
           int i = this._hashSeed;
           int i2 = 0;
                    while (i2 < length) {
               int iCharAt = str.charAt(i2) + (i * 33);
               i2++;
                        i = iCharAt;
                    }
           if (i == 0) {
                        return 1;
                    }
                    return i;
                }

       private void copyArrays() {
           String[] strArr = this._symbols;
           this._symbols = (String[]) ArraysCompat.copyOf(strArr, strArr.length);
           Bucket[] bucketArr = this._buckets;
           this._buckets = (Bucket[]) ArraysCompat.copyOf(bucketArr, bucketArr.length);
                }

       private void rehash() {
           int length = this._symbols.length;
           int i = length + length;
           if (i > 65536) {
               this._size = 0;
               Arrays.fill(this._symbols, (Object) null);
               Arrays.fill(this._buckets, (Object) null);
               this._dirty = true;
               return;
                    }
           String[] strArr = this._symbols;
           Bucket[] bucketArr = this._buckets;
           this._symbols = new String[i];
           this._buckets = new Bucket[i >> 1];
           this._indexMask = i - 1;
           this._sizeThreshold = _thresholdSize(i);
                    int iMax = 0;
                    int i2 = 0;
           for (int i3 = 0; i3 < length; i3++) {
               String str = strArr[i3];
               if (str != null) {
                   i2++;
                   int i_hashToIndex = _hashToIndex(calcHash(str));
                   if (this._symbols[i_hashToIndex] == null) {
                       this._symbols[i_hashToIndex] = str;
                            } else {
                       int i4 = i_hashToIndex >> 1;
                       Bucket bucket = new Bucket(str, this._buckets[i4]);
                       this._buckets[i4] = bucket;
                       iMax = Math.max(iMax, bucket.length());
                            }
                        }
                    }
           int i5 = length >> 1;
                    int i6 = 0;
                    int i7 = i2;
                    int i8 = iMax;
           while (i6 < i5) {
                        int iMax2 = i8;
               for (Bucket next = bucketArr[i6]; next != null; next = next.getNext()) {
                   i7++;
                   String symbol = next.getSymbol();
                   int i_hashToIndex2 = _hashToIndex(calcHash(symbol));
                   if (this._symbols[i_hashToIndex2] == null) {
                       this._symbols[i_hashToIndex2] = symbol;
                            } else {
                       int i9 = i_hashToIndex2 >> 1;
                       Bucket bucket2 = new Bucket(symbol, this._buckets[i9]);
                       this._buckets[i9] = bucket2;
                       iMax2 = Math.max(iMax2, bucket2.length());
                            }
                        }
               i6++;
                        i8 = iMax2;
                    }
           this._longestCollisionList = i8;
           if (i7 != this._size) {
               throw new Error("Internal error on SymbolTable.rehash(): had " + this._size + " entries; now have " + i7 + ".");
                    }
                }

       protected void reportTooManyCollisions(int i) {
           throw new IllegalStateException("Longest collision chain in symbol table (of size " + this._size + ") now exceeds maximum, " + i + " -- suspect a DoS attack based on hash collisions");
                }

       static final class Bucket {
                    private final int _length;
                    private final Bucket _next;
                    private final String _symbol;

           public Bucket(String str, Bucket bucket) {
               this._symbol = str;
               this._next = bucket;
               this._length = bucket == null ? 1 : bucket._length + 1;
                    }

           public String getSymbol() {
               return this._symbol;
                    }

           public Bucket getNext() {
               return this._next;
                    }

           public int length() {
               return this._length;
                    }

           public String find(char[] cArr, int i, int i2) {
               String symbol = this._symbol;
               Bucket next = this._next;
                        while (true) {
                   if (symbol.length() == i2) {
                       int i3 = 0;
                       while (symbol.charAt(i3) == cArr[i + i3] && (i3 = i3 + 1) < i2) {
                                }
                       if (i3 == i2) {
                           return symbol;
                                }
                            }
                   if (next != null) {
                       symbol = next.getSymbol();
                       next = next.getNext();
                            } else {
                       return null;
                            }
                        }
                    }
                }
            }
