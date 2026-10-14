            package android.support.v4.util;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    public class SparseArrayCompat<E> implements Cloneable {
                private static final Object DELETED = new Object();
                private boolean mGarbage;
                private int[] mKeys;
                private int mSize;
                private Object[] mValues;

                /* JADX INFO: renamed from: clone, reason: collision with other method in class */
/* 22 */        public /* bridge */ /* synthetic */ Object m2472clone() throws CloneNotSupportedException {
/* 23 */            return clone();
                }

/* 34 */        public SparseArrayCompat() {
/* 35 */            this(10);
                }

/* 44 */        public SparseArrayCompat(int initialCapacity) {
/* 25 */            this.mGarbage = false;
/* 46 */            if (initialCapacity == 0) {
/* 47 */                this.mKeys = ContainerHelpers.EMPTY_INTS;
/* 48 */                this.mValues = ContainerHelpers.EMPTY_OBJECTS;
                    } else {
/* 50 */                int initialCapacity2 = ContainerHelpers.idealIntArraySize(initialCapacity);
/* 51 */                this.mKeys = new int[initialCapacity2];
/* 52 */                this.mValues = new Object[initialCapacity2];
                    }
/* 54 */            this.mSize = 0;
                }

/* 59 */        public SparseArrayCompat<E> clone() {
/* 60 */            SparseArrayCompat<E> clone = null;
                    try {
/* 62 */                clone = (SparseArrayCompat) super.clone();
/* 63 */                clone.mKeys = (int[]) this.mKeys.clone();
/* 64 */                clone.mValues = (Object[]) this.mValues.clone();
/* 68 */                return clone;
                    } catch (CloneNotSupportedException e) {
/* 68 */                return clone;
                    }
                }

/* 75 */        public E get(int key) {
/* 76 */            return get(key, null);
                }

/* 84 */        public E get(int i, E e) {
/* 85 */            int iBinarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
                    return (iBinarySearch < 0 || this.mValues[iBinarySearch] == DELETED) ? e : (E) this.mValues[iBinarySearch];
                }

/* 97 */        public void delete(int key) {
/* 98 */            int i = ContainerHelpers.binarySearch(this.mKeys, this.mSize, key);
/* 100 */           if (i >= 0 && this.mValues[i] != DELETED) {
/* 102 */               this.mValues[i] = DELETED;
/* 103 */               this.mGarbage = true;
                    }
                }

/* 111 */       public void remove(int key) {
/* 112 */           delete(key);
                }

/* 118 */       public void removeAt(int index) {
/* 119 */           if (this.mValues[index] != DELETED) {
/* 120 */               this.mValues[index] = DELETED;
/* 121 */               this.mGarbage = true;
                    }
                }

/* 131 */       public void removeAtRange(int index, int size) {
/* 132 */           int end = Math.min(this.mSize, index + size);
                    for (int i = index; i < end; i++) {
/* 134 */               removeAt(i);
                    }
                }

                /* JADX INFO: renamed from: gc */
/* 140 */       private void m1gc() {
/* 141 */           int n = this.mSize;
/* 142 */           int o = 0;
/* 143 */           int[] keys = this.mKeys;
/* 144 */           Object[] values = this.mValues;
                    for (int i = 0; i < n; i++) {
/* 147 */               Object val = values[i];
/* 149 */               if (val != DELETED) {
/* 150 */                   if (i != o) {
/* 151 */                       keys[o] = keys[i];
/* 152 */                       values[o] = val;
/* 153 */                       values[i] = null;
                            }
/* 156 */                   o++;
                        }
                    }
/* 160 */           this.mGarbage = false;
/* 161 */           this.mSize = o;
                }

/* 171 */       public void put(int key, E value) {
/* 172 */           int i = ContainerHelpers.binarySearch(this.mKeys, this.mSize, key);
/* 174 */           if (i >= 0) {
/* 175 */               this.mValues[i] = value;
/* 216 */               return;
                    }
/* 177 */           int i2 = i ^ (-1);
/* 179 */           if (i2 < this.mSize && this.mValues[i2] == DELETED) {
/* 180 */               this.mKeys[i2] = key;
/* 181 */               this.mValues[i2] = value;
/* 216 */               return;
                    }
/* 185 */           if (this.mGarbage && this.mSize >= this.mKeys.length) {
/* 186 */               m1gc();
/* 189 */               i2 = ContainerHelpers.binarySearch(this.mKeys, this.mSize, key) ^ (-1);
                    }
/* 192 */           if (this.mSize >= this.mKeys.length) {
/* 193 */               int n = ContainerHelpers.idealIntArraySize(this.mSize + 1);
/* 195 */               int[] nkeys = new int[n];
/* 196 */               Object[] nvalues = new Object[n];
/* 199 */               System.arraycopy(this.mKeys, 0, nkeys, 0, this.mKeys.length);
/* 200 */               System.arraycopy(this.mValues, 0, nvalues, 0, this.mValues.length);
/* 202 */               this.mKeys = nkeys;
/* 203 */               this.mValues = nvalues;
                    }
/* 206 */           if (this.mSize - i2 != 0) {
/* 208 */               System.arraycopy(this.mKeys, i2, this.mKeys, i2 + 1, this.mSize - i2);
/* 209 */               System.arraycopy(this.mValues, i2, this.mValues, i2 + 1, this.mSize - i2);
                    }
/* 212 */           this.mKeys[i2] = key;
/* 213 */           this.mValues[i2] = value;
/* 214 */           this.mSize++;
                }

/* 222 */       public int size() {
/* 223 */           if (this.mGarbage) {
/* 224 */               m1gc();
                    }
/* 227 */           return this.mSize;
                }

/* 235 */       public int keyAt(int index) {
/* 236 */           if (this.mGarbage) {
/* 237 */               m1gc();
                    }
/* 240 */           return this.mKeys[index];
                }

/* 249 */       public E valueAt(int i) {
/* 250 */           if (this.mGarbage) {
/* 251 */               m1gc();
                    }
/* 254 */           return (E) this.mValues[i];
                }

/* 262 */       public void setValueAt(int index, E value) {
/* 263 */           if (this.mGarbage) {
/* 264 */               m1gc();
                    }
/* 267 */           this.mValues[index] = value;
                }

/* 275 */       public int indexOfKey(int key) {
/* 276 */           if (this.mGarbage) {
/* 277 */               m1gc();
                    }
/* 280 */           return ContainerHelpers.binarySearch(this.mKeys, this.mSize, key);
                }

/* 293 */       public int indexOfValue(E value) {
/* 294 */           if (this.mGarbage) {
/* 295 */               m1gc();
                    }
                    for (int i = 0; i < this.mSize; i++) {
/* 299 */               if (this.mValues[i] == value) {
/* 302 */                   return i;
                        }
                    }
/* 302 */           return -1;
                }

/* 308 */       public void clear() {
/* 309 */           int n = this.mSize;
/* 310 */           Object[] values = this.mValues;
                    for (int i = 0; i < n; i++) {
/* 313 */               values[i] = null;
                    }
/* 316 */           this.mSize = 0;
/* 317 */           this.mGarbage = false;
                }

/* 324 */       public void append(int key, E value) {
/* 325 */           if (this.mSize != 0 && key <= this.mKeys[this.mSize - 1]) {
/* 326 */               put(key, value);
/* 352 */               return;
                    }
/* 330 */           if (this.mGarbage && this.mSize >= this.mKeys.length) {
/* 331 */               m1gc();
                    }
/* 334 */           int pos = this.mSize;
/* 335 */           if (pos >= this.mKeys.length) {
/* 336 */               int n = ContainerHelpers.idealIntArraySize(pos + 1);
/* 338 */               int[] nkeys = new int[n];
/* 339 */               Object[] nvalues = new Object[n];
/* 342 */               System.arraycopy(this.mKeys, 0, nkeys, 0, this.mKeys.length);
/* 343 */               System.arraycopy(this.mValues, 0, nvalues, 0, this.mValues.length);
/* 345 */               this.mKeys = nkeys;
/* 346 */               this.mValues = nvalues;
                    }
/* 349 */           this.mKeys[pos] = key;
/* 350 */           this.mValues[pos] = value;
/* 351 */           this.mSize = pos + 1;
                }

/* 362 */       public String toString() {
/* 363 */           if (size() <= 0) {
/* 364 */               return "{}";
                    }
/* 367 */           StringBuilder buffer = new StringBuilder(this.mSize * 28);
/* 368 */           buffer.append('{');
                    for (int i = 0; i < this.mSize; i++) {
/* 370 */               if (i > 0) {
/* 371 */                   buffer.append(", ");
                        }
/* 373 */               int key = keyAt(i);
/* 374 */               buffer.append(key);
/* 375 */               buffer.append('=');
/* 376 */               Object value = valueAt(i);
/* 377 */               if (value != this) {
/* 378 */                   buffer.append(value);
                        } else {
/* 380 */                   buffer.append("(this Map)");
                        }
                    }
/* 383 */           buffer.append('}');
/* 384 */           return buffer.toString();
                }
            }
