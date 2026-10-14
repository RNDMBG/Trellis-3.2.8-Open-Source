            package android.support.v4.util;

            import java.util.Collection;
            import java.util.Map;
            import java.util.Set;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 52 */    public class ArrayMap<K, V> extends SimpleArrayMap<K, V> implements Map<K, V> {
                MapCollections<K, V> mCollections;

/* 53 */        public ArrayMap() {
                }

/* 60 */        public ArrayMap(int capacity) {
/* 61 */            super(capacity);
                }

/* 67 */        public ArrayMap(SimpleArrayMap map) {
/* 68 */            super(map);
                }

                /* JADX INFO: renamed from: android.support.v4.util.ArrayMap$1 */
/* 71 */        class C00381 extends MapCollections<K, V> {
/* 72 */            C00381() {
                    }

                    @Override // android.support.v4.util.MapCollections
/* 75 */            protected int colGetSize() {
/* 76 */                return ArrayMap.this.mSize;
                    }

                    @Override // android.support.v4.util.MapCollections
/* 80 */            protected Object colGetEntry(int index, int offset) {
/* 81 */                return ArrayMap.this.mArray[(index << 1) + offset];
                    }

                    @Override // android.support.v4.util.MapCollections
/* 85 */            protected int colIndexOfKey(Object key) {
                        return key == null ? ArrayMap.this.indexOfNull() : ArrayMap.this.indexOf(key, key.hashCode());
                    }

                    @Override // android.support.v4.util.MapCollections
/* 90 */            protected int colIndexOfValue(Object value) {
/* 91 */                return ArrayMap.this.indexOfValue(value);
                    }

                    @Override // android.support.v4.util.MapCollections
/* 95 */            protected Map<K, V> colGetMap() {
/* 96 */                return ArrayMap.this;
                    }

                    @Override // android.support.v4.util.MapCollections
/* 100 */           protected void colPut(K key, V value) {
/* 101 */               ArrayMap.this.put(key, value);
                    }

                    @Override // android.support.v4.util.MapCollections
/* 105 */           protected V colSetValue(int index, V value) {
/* 106 */               return ArrayMap.this.setValueAt(index, value);
                    }

                    @Override // android.support.v4.util.MapCollections
/* 110 */           protected void colRemoveAt(int index) {
/* 111 */               ArrayMap.this.removeAt(index);
                    }

                    @Override // android.support.v4.util.MapCollections
/* 115 */           protected void colClear() {
/* 116 */               ArrayMap.this.clear();
                    }
                }

/* 71 */        private MapCollections<K, V> getCollection() {
/* 72 */            if (this.mCollections == null) {
/* 73 */                this.mCollections = new C00381();
                    }
/* 120 */           return this.mCollections;
                }

/* 129 */       public boolean containsAll(Collection<?> collection) {
/* 130 */           return MapCollections.containsAllHelper(this, collection);
                }

                @Override // java.util.Map
/* 138 */       public void putAll(Map<? extends K, ? extends V> map) {
/* 139 */           ensureCapacity(this.mSize + map.size());
                    for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
/* 141 */               put(entry.getKey(), entry.getValue());
                    }
                }

/* 150 */       public boolean removeAll(Collection<?> collection) {
/* 151 */           return MapCollections.removeAllHelper(this, collection);
                }

/* 160 */       public boolean retainAll(Collection<?> collection) {
/* 161 */           return MapCollections.retainAllHelper(this, collection);
                }

                @Override // java.util.Map
/* 178 */       public Set<Map.Entry<K, V>> entrySet() {
/* 179 */           return getCollection().getEntrySet();
                }

                @Override // java.util.Map
/* 190 */       public Set<K> keySet() {
/* 191 */           return getCollection().getKeySet();
                }

                @Override // java.util.Map
/* 202 */       public Collection<V> values() {
/* 203 */           return getCollection().getValues();
                }
            }
