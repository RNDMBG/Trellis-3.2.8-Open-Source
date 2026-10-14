            package android.support.v4.util;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 41 */    public class LongSparseArray<E> implements Cloneable {
                private static final Object DELETED = new Object();
                private boolean mGarbage;
                private long[] mKeys;
                private int mSize;
                private Object[] mValues;

                /* JADX INFO: renamed from: clone, reason: collision with other method in class */
/* 42 */        public /* bridge */ /* synthetic */ Object m2471clone() throws CloneNotSupportedException {
/* 43 */            return clone();
                }

/* 54 */        public LongSparseArray() {
/* 55 */            this(10);
                }

/* 64 */        public LongSparseArray(int initialCapacity) {
/* 45 */            this.mGarbage = false;
/* 66 */            if (initialCapacity == 0) {
/* 67 */                this.mKeys = ContainerHelpers.EMPTY_LONGS;
/* 68 */                this.mValues = ContainerHelpers.EMPTY_OBJECTS;
                    } else {
/* 70 */                int initialCapacity2 = ContainerHelpers.idealLongArraySize(initialCapacity);
/* 71 */                this.mKeys = new long[initialCapacity2];
/* 72 */                this.mValues = new Object[initialCapacity2];
                    }
/* 74 */            this.mSize = 0;
                }

/* 79 */        public LongSparseArray<E> clone() {
/* 80 */            LongSparseArray<E> clone = null;
                    try {
/* 82 */                clone = (LongSparseArray) super.clone();
/* 83 */                clone.mKeys = (long[]) this.mKeys.clone();
/* 84 */                clone.mValues = (Object[]) this.mValues.clone();
/* 88 */                return clone;
                    } catch (CloneNotSupportedException e) {
/* 88 */                return clone;
                    }
                }

/* 95 */        public E get(long key) {
/* 96 */            return get(key, null);
                }

/* 104 */       public E get(long j, E e) {
/* 105 */           int iBinarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, j);
                    return (iBinarySearch < 0 || this.mValues[iBinarySearch] == DELETED) ? e : (E) this.mValues[iBinarySearch];
                }

/* 117 */       public void delete(long key) {
/* 118 */           int i = ContainerHelpers.binarySearch(this.mKeys, this.mSize, key);
/* 120 */           if (i >= 0 && this.mValues[i] != DELETED) {
/* 122 */               this.mValues[i] = DELETED;
/* 123 */               this.mGarbage = true;
                    }
                }

/* 131 */       public void remove(long key) {
/* 132 */           delete(key);
                }

/* 138 */       public void removeAt(int index) {
/* 139 */           if (this.mValues[index] != DELETED) {
/* 140 */               this.mValues[index] = DELETED;
/* 141 */               this.mGarbage = true;
                    }
                }

                /* JADX INFO: renamed from: gc */
/* 147 */       private void m0gc() {
/* 148 */           int n = this.mSize;
/* 149 */           int o = 0;
/* 150 */           long[] keys = this.mKeys;
/* 151 */           Object[] values = this.mValues;
                    for (int i = 0; i < n; i++) {
/* 154 */               Object val = values[i];
/* 156 */               if (val != DELETED) {
/* 157 */                   if (i != o) {
/* 158 */                       keys[o] = keys[i];
/* 159 */                       values[o] = val;
/* 160 */                       values[i] = null;
                            }
/* 163 */                   o++;
                        }
                    }
/* 167 */           this.mGarbage = false;
/* 168 */           this.mSize = o;
                }

/* 178 */       public void put(long key, E value) {
/* 179 */           int i = ContainerHelpers.binarySearch(this.mKeys, this.mSize, key);
/* 181 */           if (i >= 0) {
/* 182 */               this.mValues[i] = value;
/* 223 */               return;
                    }
/* 184 */           int i2 = i ^ (-1);
/* 186 */           if (i2 < this.mSize && this.mValues[i2] == DELETED) {
/* 187 */               this.mKeys[i2] = key;
/* 188 */               this.mValues[i2] = value;
/* 223 */               return;
                    }
/* 192 */           if (this.mGarbage && this.mSize >= this.mKeys.length) {
/* 193 */               m0gc();
/* 196 */               i2 = ContainerHelpers.binarySearch(this.mKeys, this.mSize, key) ^ (-1);
                    }
/* 199 */           if (this.mSize >= this.mKeys.length) {
/* 200 */               int n = ContainerHelpers.idealLongArraySize(this.mSize + 1);
/* 202 */               long[] nkeys = new long[n];
/* 203 */               Object[] nvalues = new Object[n];
/* 206 */               System.arraycopy(this.mKeys, 0, nkeys, 0, this.mKeys.length);
/* 207 */               System.arraycopy(this.mValues, 0, nvalues, 0, this.mValues.length);
/* 209 */               this.mKeys = nkeys;
/* 210 */               this.mValues = nvalues;
                    }
/* 213 */           if (this.mSize - i2 != 0) {
/* 215 */               System.arraycopy(this.mKeys, i2, this.mKeys, i2 + 1, this.mSize - i2);
/* 216 */               System.arraycopy(this.mValues, i2, this.mValues, i2 + 1, this.mSize - i2);
                    }
/* 219 */           this.mKeys[i2] = key;
/* 220 */           this.mValues[i2] = value;
/* 221 */           this.mSize++;
                }

/* 229 */       public int size() {
/* 230 */           if (this.mGarbage) {
/* 231 */               m0gc();
                    }
/* 234 */           return this.mSize;
                }

/* 242 */       public long keyAt(int index) {
/* 243 */           if (this.mGarbage) {
/* 244 */               m0gc();
                    }
/* 247 */           return this.mKeys[index];
                }

/* 256 */       public E valueAt(int i) {
/* 257 */           if (this.mGarbage) {
/* 258 */               m0gc();
                    }
/* 261 */           return (E) this.mValues[i];
                }

/* 269 */       public void setValueAt(int index, E value) {
/* 270 */           if (this.mGarbage) {
/* 271 */               m0gc();
                    }
/* 274 */           this.mValues[index] = value;
                }

/* 282 */       public int indexOfKey(long key) {
/* 283 */           if (this.mGarbage) {
/* 284 */               m0gc();
                    }
/* 287 */           return ContainerHelpers.binarySearch(this.mKeys, this.mSize, key);
                }

/* 298 */       public int indexOfValue(E value) {
/* 299 */           if (this.mGarbage) {
/* 300 */               m0gc();
                    }
                    for (int i = 0; i < this.mSize; i++) {
/* 304 */               if (this.mValues[i] == value) {
/* 307 */                   return i;
                        }
                    }
/* 307 */           return -1;
                }

/* 313 */       public void clear() {
/* 314 */           int n = this.mSize;
/* 315 */           Object[] values = this.mValues;
                    for (int i = 0; i < n; i++) {
/* 318 */               values[i] = null;
                    }
/* 321 */           this.mSize = 0;
/* 322 */           this.mGarbage = false;
                }

/* 329 */       public void append(long key, E value) {
/* 330 */           if (this.mSize != 0 && key <= this.mKeys[this.mSize - 1]) {
/* 331 */               put(key, value);
/* 357 */               return;
                    }
/* 335 */           if (this.mGarbage && this.mSize >= this.mKeys.length) {
/* 336 */               m0gc();
                    }
/* 339 */           int pos = this.mSize;
/* 340 */           if (pos >= this.mKeys.length) {
/* 341 */               int n = ContainerHelpers.idealLongArraySize(pos + 1);
/* 343 */               long[] nkeys = new long[n];
/* 344 */               Object[] nvalues = new Object[n];
/* 347 */               System.arraycopy(this.mKeys, 0, nkeys, 0, this.mKeys.length);
/* 348 */               System.arraycopy(this.mValues, 0, nvalues, 0, this.mValues.length);
/* 350 */               this.mKeys = nkeys;
/* 351 */               this.mValues = nvalues;
                    }
/* 354 */           this.mKeys[pos] = key;
/* 355 */           this.mValues[pos] = value;
/* 356 */           this.mSize = pos + 1;
                }

/* 367 */       public String toString() {
/* 368 */           if (size() <= 0) {
/* 369 */               return "{}";
                    }
/* 372 */           StringBuilder buffer = new StringBuilder(this.mSize * 28);
/* 373 */           buffer.append('{');
                    for (int i = 0; i < this.mSize; i++) {
/* 375 */               if (i > 0) {
/* 376 */                   buffer.append(", ");
                        }
/* 378 */               long key = keyAt(i);
/* 379 */               buffer.append(key);
/* 380 */               buffer.append('=');
/* 381 */               Object value = valueAt(i);
/* 382 */               if (value != this) {
/* 383 */                   buffer.append(value);
                        } else {
/* 385 */                   buffer.append("(this Map)");
                        }
                    }
/* 388 */           buffer.append('}');
/* 389 */           return buffer.toString();
                }
            }
