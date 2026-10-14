            package com.fasterxml.jackson.core.sym;

            import com.fasterxml.jackson.core.util.ArraysCompat;
            import com.fasterxml.jackson.core.util.InternCache;
            import com.flurry.android.Constants;
            import java.util.Arrays;
            import java.util.concurrent.atomic.AtomicReference;
            
            
   public final class BytesToNameCanonicalizer {
                protected static final int DEFAULT_TABLE_SIZE = 64;
                static final int INITIAL_COLLISION_LEN = 32;
                static final int LAST_VALID_BUCKET = 254;
                static final int MAX_COLL_CHAIN_FOR_REUSE = 63;
                static final int MAX_COLL_CHAIN_LENGTH = 255;
                static final int MAX_ENTRIES_FOR_REUSE = 6000;
                protected static final int MAX_TABLE_SIZE = 65536;
                static final int MIN_HASH_SIZE = 16;
                private static final int MULT = 33;
                private static final int MULT2 = 65599;
                private static final int MULT3 = 31;
                protected int _collCount;
                protected int _collEnd;
                protected Bucket[] _collList;
                private boolean _collListShared;
                protected int _count;
                private final int _hashSeed;
                protected final boolean _intern;
                protected int _longestCollisionList;
                protected int[] _mainHash;
                protected int _mainHashMask;
                private boolean _mainHashShared;
                protected Name[] _mainNames;
                private boolean _mainNamesShared;
                private transient boolean _needRehash;
                protected final BytesToNameCanonicalizer _parent;
                protected final AtomicReference<TableInfo> _tableInfo;

       private BytesToNameCanonicalizer(int i, boolean z, int i2) {
                    int i3 = 16;
           this._parent = null;
           this._hashSeed = i2;
           this._intern = z;
           if (i < 16) {
                        i = 16;
                    } else if (((i - 1) & i) != 0) {
               while (i3 < i) {
                   i3 += i3;
                        }
                        i = i3;
                    }
           this._tableInfo = new AtomicReference<>(initTableInfo(i));
                }

       private BytesToNameCanonicalizer(BytesToNameCanonicalizer bytesToNameCanonicalizer, boolean z, int i, TableInfo tableInfo) {
           this._parent = bytesToNameCanonicalizer;
           this._hashSeed = i;
           this._intern = z;
           this._tableInfo = null;
           this._count = tableInfo.count;
           this._mainHashMask = tableInfo.mainHashMask;
           this._mainHash = tableInfo.mainHash;
           this._mainNames = tableInfo.mainNames;
           this._collList = tableInfo.collList;
           this._collCount = tableInfo.collCount;
           this._collEnd = tableInfo.collEnd;
           this._longestCollisionList = tableInfo.longestCollisionList;
           this._needRehash = false;
           this._mainHashShared = true;
           this._mainNamesShared = true;
           this._collListShared = true;
                }

       private TableInfo initTableInfo(int i) {
           return new TableInfo(0, i - 1, new int[i], new Name[i], null, 0, 0, 0);
                }

       public static BytesToNameCanonicalizer createRoot() {
           long jCurrentTimeMillis = System.currentTimeMillis();
           return createRoot((((int) (jCurrentTimeMillis >>> 32)) + ((int) jCurrentTimeMillis)) | 1);
                }

       protected static BytesToNameCanonicalizer createRoot(int i) {
           return new BytesToNameCanonicalizer(64, true, i);
                }

       public BytesToNameCanonicalizer makeChild(boolean z, boolean z2) {
           return new BytesToNameCanonicalizer(this, z2, this._hashSeed, this._tableInfo.get());
                }

       public void release() {
           if (this._parent != null && maybeDirty()) {
               this._parent.mergeChild(new TableInfo(this));
               this._mainHashShared = true;
               this._mainNamesShared = true;
               this._collListShared = true;
                    }
                }

       private void mergeChild(TableInfo tableInfo) {
           int i = tableInfo.count;
           TableInfo tableInfo2 = this._tableInfo.get();
           if (i > tableInfo2.count) {
               if (i > 6000 || tableInfo.longestCollisionList > 63) {
                   tableInfo = initTableInfo(64);
                        }
               this._tableInfo.compareAndSet(tableInfo2, tableInfo);
                    }
                }

       public int size() {
                    return this._tableInfo != null ? this._tableInfo.get().count : this._count;
                }

       public int bucketCount() {
           return this._mainHash.length;
                }

       public boolean maybeDirty() {
                    return !this._mainHashShared;
                }

       public int hashSeed() {
           return this._hashSeed;
                }

       public int collisionCount() {
           return this._collCount;
                }

       public int maxCollisionLength() {
           return this._longestCollisionList;
                }

       public static Name getEmptyName() {
           return Name1.getEmptyName();
                }

       public Name findName(int i) {
           int iCalcHash = calcHash(i);
           int i2 = this._mainHashMask & iCalcHash;
           int i3 = this._mainHash[i2];
           if ((((i3 >> 8) ^ iCalcHash) << 8) == 0) {
               Name name = this._mainNames[i2];
               if (name == null) {
                            return null;
                        }
               if (name.equals(i)) {
                            return name;
                        }
                    } else if (i3 == 0) {
                        return null;
                    }
           int i4 = i3 & 255;
           if (i4 <= 0) {
                        return null;
                    }
           Bucket bucket = this._collList[i4 - 1];
           if (bucket != null) {
               return bucket.find(iCalcHash, i, 0);
                    }
                    return null;
                }

       public Name findName(int i, int i2) {
           int iCalcHash = i2 == 0 ? calcHash(i) : calcHash(i, i2);
           int i3 = this._mainHashMask & iCalcHash;
           int i4 = this._mainHash[i3];
           if ((((i4 >> 8) ^ iCalcHash) << 8) == 0) {
               Name name = this._mainNames[i3];
               if (name == null) {
                            return null;
                        }
               if (name.equals(i, i2)) {
                            return name;
                        }
                    } else if (i4 == 0) {
                        return null;
                    }
           int i5 = i4 & 255;
           if (i5 > 0) {
               Bucket bucket = this._collList[i5 - 1];
               if (bucket != null) {
                   return bucket.find(iCalcHash, i, i2);
                        }
                    }
                    return null;
                }

       public Name findName(int[] iArr, int i) {
           if (i < 3) {
                        return findName(iArr[0], i >= 2 ? iArr[1] : 0);
                    }
           int iCalcHash = calcHash(iArr, i);
           int i2 = this._mainHashMask & iCalcHash;
           int i3 = this._mainHash[i2];
           if ((((i3 >> 8) ^ iCalcHash) << 8) == 0) {
               Name name = this._mainNames[i2];
               if (name == null || name.equals(iArr, i)) {
                   return name;
                        }
                    } else if (i3 == 0) {
                        return null;
                    }
           int i4 = i3 & 255;
           if (i4 > 0) {
               Bucket bucket = this._collList[i4 - 1];
               if (bucket != null) {
                   return bucket.find(iCalcHash, iArr, i);
                        }
                    }
                    return null;
                }

       public Name addName(String str, int i, int i2) {
           if (this._intern) {
               str = InternCache.instance.intern(str);
                    }
           int iCalcHash = i2 == 0 ? calcHash(i) : calcHash(i, i2);
           Name nameConstructName = constructName(iCalcHash, str, i, i2);
           _addSymbol(iCalcHash, nameConstructName);
           return nameConstructName;
                }

       public Name addName(String str, int[] iArr, int i) {
                    int iCalcHash;
           if (this._intern) {
               str = InternCache.instance.intern(str);
                    }
           if (i < 3) {
               iCalcHash = i == 1 ? calcHash(iArr[0]) : calcHash(iArr[0], iArr[1]);
                    } else {
               iCalcHash = calcHash(iArr, i);
                    }
           Name nameConstructName = constructName(iCalcHash, str, iArr, i);
           _addSymbol(iCalcHash, nameConstructName);
           return nameConstructName;
                }

       public int calcHash(int i) {
           int i2 = this._hashSeed ^ i;
           int i3 = i2 + (i2 >>> 15);
           return i3 ^ (i3 >>> 9);
                }

       public int calcHash(int i, int i2) {
           int i3 = (((i >>> 15) ^ i) + (i2 * 33)) ^ this._hashSeed;
           return i3 + (i3 >>> 7);
                }

       public int calcHash(int[] iArr, int i) {
           if (i < 3) {
               throw new IllegalArgumentException();
                    }
           int i2 = iArr[0] ^ this._hashSeed;
           int i3 = (((i2 + (i2 >>> 9)) * 33) + iArr[1]) * MULT2;
           int i4 = (i3 + (i3 >>> 15)) ^ iArr[2];
           int i5 = i4 + (i4 >>> 17);
           for (int i6 = 3; i6 < i; i6++) {
               int i7 = (i5 * 31) ^ iArr[i6];
               int i8 = i7 + (i7 >>> 3);
               i5 = i8 ^ (i8 << 7);
                    }
           int i9 = (i5 >>> 15) + i5;
           return i9 ^ (i9 << 9);
                }

       protected static int[] calcQuads(byte[] bArr) {
           int length = bArr.length;
           int[] iArr = new int[(length + 3) / 4];
           int i = 0;
                    while (i < length) {
               int i2 = bArr[i] & Constants.UNKNOWN;
               int i3 = i + 1;
                        if (i3 < length) {
                   i2 = (i2 << 8) | (bArr[i3] & Constants.UNKNOWN);
                   i3++;
                            if (i3 < length) {
                       i2 = (i2 << 8) | (bArr[i3] & Constants.UNKNOWN);
                       i3++;
                                if (i3 < length) {
                           i2 = (i2 << 8) | (bArr[i3] & Constants.UNKNOWN);
                                }
                            }
                        }
               iArr[i3 >> 2] = i2;
               i = i3 + 1;
                    }
           return iArr;
                }

       private void _addSymbol(int i, Name name) {
                    int iFindBestBucket;
           if (this._mainHashShared) {
               unshareMain();
                    }
           if (this._needRehash) {
               rehash();
                    }
           this._count++;
           int i2 = i & this._mainHashMask;
           if (this._mainNames[i2] == null) {
               this._mainHash[i2] = i << 8;
               if (this._mainNamesShared) {
                   unshareNames();
                        }
               this._mainNames[i2] = name;
                    } else {
               if (this._collListShared) {
                   unshareCollision();
                        }
               this._collCount++;
               int i3 = this._mainHash[i2];
               int i4 = i3 & 255;
               if (i4 == 0) {
                   if (this._collEnd <= LAST_VALID_BUCKET) {
                       iFindBestBucket = this._collEnd;
                       this._collEnd++;
                       if (iFindBestBucket >= this._collList.length) {
                           expandCollision();
                                }
                            } else {
                       iFindBestBucket = findBestBucket();
                            }
                   this._mainHash[i2] = (i3 & (-256)) | (iFindBestBucket + 1);
                        } else {
                   iFindBestBucket = i4 - 1;
                        }
               Bucket bucket = new Bucket(name, this._collList[iFindBestBucket]);
               this._collList[iFindBestBucket] = bucket;
               this._longestCollisionList = Math.max(bucket.length(), this._longestCollisionList);
               if (this._longestCollisionList > 255) {
                   reportTooManyCollisions(255);
                        }
                    }
           int length = this._mainHash.length;
           if (this._count > (length >> 1)) {
               int i5 = length >> 2;
               if (this._count > length - i5) {
                   this._needRehash = true;
                        } else if (this._collCount >= i5) {
                   this._needRehash = true;
                        }
                    }
                }

       private void rehash() {
                    int iFindBestBucket;
                    int iMax;
                    int i = 0;
           this._needRehash = false;
           this._mainNamesShared = false;
           int length = this._mainHash.length;
           int i2 = length + length;
           if (i2 > 65536) {
               nukeSymbols();
               return;
                    }
           this._mainHash = new int[i2];
           this._mainHashMask = i2 - 1;
           Name[] nameArr = this._mainNames;
           this._mainNames = new Name[i2];
                    int i3 = 0;
           for (int i4 = 0; i4 < length; i4++) {
               Name name = nameArr[i4];
               if (name != null) {
                   i3++;
                   int iHashCode = name.hashCode();
                   int i5 = this._mainHashMask & iHashCode;
                   this._mainNames[i5] = name;
                   this._mainHash[i5] = iHashCode << 8;
                        }
                    }
           int i6 = this._collEnd;
           if (i6 == 0) {
               this._longestCollisionList = 0;
               return;
                    }
           this._collCount = 0;
           this._collEnd = 0;
           this._collListShared = false;
           Bucket[] bucketArr = this._collList;
           this._collList = new Bucket[bucketArr.length];
                    int i7 = 0;
                    int i8 = i3;
           while (i7 < i6) {
                        int i9 = i8;
               Bucket bucket = bucketArr[i7];
                        while (bucket != null) {
                   int i10 = i9 + 1;
                   Name name2 = bucket._name;
                   int iHashCode2 = name2.hashCode();
                   int i11 = this._mainHashMask & iHashCode2;
                   int i12 = this._mainHash[i11];
                   if (this._mainNames[i11] == null) {
                       this._mainHash[i11] = iHashCode2 << 8;
                       this._mainNames[i11] = name2;
                                iMax = i;
                            } else {
                       this._collCount++;
                       int i13 = i12 & 255;
                       if (i13 == 0) {
                           if (this._collEnd <= LAST_VALID_BUCKET) {
                               iFindBestBucket = this._collEnd;
                               this._collEnd++;
                               if (iFindBestBucket >= this._collList.length) {
                                   expandCollision();
                                        }
                                    } else {
                               iFindBestBucket = findBestBucket();
                                    }
                           this._mainHash[i11] = (i12 & (-256)) | (iFindBestBucket + 1);
                                } else {
                           iFindBestBucket = i13 - 1;
                                }
                       Bucket bucket2 = new Bucket(name2, this._collList[iFindBestBucket]);
                       this._collList[iFindBestBucket] = bucket2;
                       iMax = Math.max(i, bucket2.length());
                            }
                   bucket = bucket._next;
                            i = iMax;
                            i9 = i10;
                        }
               i7++;
                        i8 = i9;
                    }
           this._longestCollisionList = i;
           if (i8 != this._count) {
               throw new RuntimeException("Internal error: count after rehash " + i8 + "; should be " + this._count);
                    }
                }

       private void nukeSymbols() {
           this._count = 0;
           this._longestCollisionList = 0;
           Arrays.fill(this._mainHash, 0);
           Arrays.fill(this._mainNames, (Object) null);
           Arrays.fill(this._collList, (Object) null);
           this._collCount = 0;
           this._collEnd = 0;
                }

       private int findBestBucket() {
           Bucket[] bucketArr = this._collList;
           int i = Integer.MAX_VALUE;
           int i2 = -1;
           int i3 = 0;
                    int i4 = this._collEnd;
                    while (i3 < i4) {
               int length = bucketArr[i3].length();
               if (length >= i) {
                            length = i;
                        } else {
                   if (length == 1) {
                      return i3;
                            }
                            i2 = i3;
                        }
               i3++;
                        i = length;
                    }
                    return i2;
                }

      private void unshareMain() {
          int[] iArr = this._mainHash;
          this._mainHash = ArraysCompat.copyOf(iArr, iArr.length);
          this._mainHashShared = false;
                }

      private void unshareCollision() {
          Bucket[] bucketArr = this._collList;
          if (bucketArr == null) {
              this._collList = new Bucket[32];
                    } else {
              this._collList = (Bucket[]) ArraysCompat.copyOf(bucketArr, bucketArr.length);
                    }
          this._collListShared = false;
                }

      private void unshareNames() {
          Name[] nameArr = this._mainNames;
          this._mainNames = (Name[]) ArraysCompat.copyOf(nameArr, nameArr.length);
          this._mainNamesShared = false;
                }

      private void expandCollision() {
          Bucket[] bucketArr = this._collList;
          this._collList = (Bucket[]) ArraysCompat.copyOf(bucketArr, bucketArr.length * 2);
                }

      private static Name constructName(int i, String str, int i2, int i3) {
                    return i3 == 0 ? new Name1(str, i, i2) : new Name2(str, i, i2, i3);
                }

      private static Name constructName(int i, String str, int[] iArr, int i2) {
          if (i2 < 4) {
                        switch (i2) {
                            case 1:
                      return new Name1(str, i, iArr[0]);
                            case 2:
                      return new Name2(str, i, iArr[0], iArr[1]);
                            case 3:
                      return new Name3(str, i, iArr[0], iArr[1], iArr[2]);
                        }
                    }
          int[] iArr2 = new int[i2];
          for (int i3 = 0; i3 < i2; i3++) {
              iArr2[i3] = iArr[i3];
                    }
          return new NameN(str, i, iArr2, i2);
                }

      protected void reportTooManyCollisions(int i) {
          throw new IllegalStateException("Longest collision chain in symbol table (of size " + this._count + ") now exceeds maximum, " + i + " -- suspect a DoS attack based on hash collisions");
                }

      private static final class TableInfo {
                    public final int collCount;
                    public final int collEnd;
                    public final Bucket[] collList;
                    public final int count;
                    public final int longestCollisionList;
                    public final int[] mainHash;
                    public final int mainHashMask;
                    public final Name[] mainNames;

          public TableInfo(int i, int i2, int[] iArr, Name[] nameArr, Bucket[] bucketArr, int i3, int i4, int i5) {
              this.count = i;
              this.mainHashMask = i2;
              this.mainHash = iArr;
              this.mainNames = nameArr;
              this.collList = bucketArr;
              this.collCount = i3;
              this.collEnd = i4;
              this.longestCollisionList = i5;
                    }

          public TableInfo(BytesToNameCanonicalizer bytesToNameCanonicalizer) {
              this.count = bytesToNameCanonicalizer._count;
              this.mainHashMask = bytesToNameCanonicalizer._mainHashMask;
              this.mainHash = bytesToNameCanonicalizer._mainHash;
              this.mainNames = bytesToNameCanonicalizer._mainNames;
              this.collList = bytesToNameCanonicalizer._collList;
              this.collCount = bytesToNameCanonicalizer._collCount;
              this.collEnd = bytesToNameCanonicalizer._collEnd;
              this.longestCollisionList = bytesToNameCanonicalizer._longestCollisionList;
                    }
                }

      static final class Bucket {
                    private final int _length;
                    protected final Name _name;
                    protected final Bucket _next;

          Bucket(Name name, Bucket bucket) {
              this._name = name;
              this._next = bucket;
              this._length = bucket == null ? 1 : bucket._length + 1;
                    }

          public int length() {
              return this._length;
                    }

          public Name find(int i, int i2, int i3) {
              if (this._name.hashCode() == i && this._name.equals(i2, i3)) {
                  return this._name;
                        }
                        for (Bucket bucket = this._next; bucket != null; bucket = bucket._next) {
                  Name name = bucket._name;
                  if (name.hashCode() == i && name.equals(i2, i3)) {
                      return name;
                            }
                        }
              return null;
                    }

          public Name find(int i, int[] iArr, int i2) {
              if (this._name.hashCode() == i && this._name.equals(iArr, i2)) {
                  return this._name;
                        }
                        for (Bucket bucket = this._next; bucket != null; bucket = bucket._next) {
                  Name name = bucket._name;
                  if (name.hashCode() == i && name.equals(iArr, i2)) {
                      return name;
                            }
                        }
              return null;
                    }
                }
            }
