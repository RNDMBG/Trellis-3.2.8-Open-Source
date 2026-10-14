            package com.fasterxml.jackson.core.type;

            import java.lang.reflect.ParameterizedType;
            import java.lang.reflect.Type;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 25 */    public abstract class TypeReference<T> implements Comparable<TypeReference<T>> {
                protected final Type _type;


/* 32 */        protected TypeReference() {
/* 34 */            Type genericSuperclass = getClass().getGenericSuperclass();
/* 35 */            if (genericSuperclass instanceof Class) {
/* 36 */                throw new IllegalArgumentException("Internal error: TypeReference constructed without actual type information");
                    }
/* 45 */            this._type = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
                }

/* 47 */        public Type getType() {
/* 48 */            return this._type;
                }

/* 57 */        public int compareTo(TypeReference<T> typeReference) {
/* 58 */            return 0;
                }
            }
