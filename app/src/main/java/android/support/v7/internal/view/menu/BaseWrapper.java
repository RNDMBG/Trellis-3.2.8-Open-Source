            package android.support.v7.internal.view.menu;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    class BaseWrapper<T> {
                final T mWrappedObject;

/* 22 */        BaseWrapper(T object) {
/* 24 */            if (object == null) {
/* 25 */                throw new IllegalArgumentException("Wrapped Object can not be null.");
                    }
/* 27 */            this.mWrappedObject = object;
                }

/* 30 */        public T getWrappedObject() {
/* 31 */            return this.mWrappedObject;
                }
            }
