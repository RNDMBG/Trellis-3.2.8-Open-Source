            package android.support.v4.os;

            import android.os.Parcel;
            import android.os.Parcelable;
            
            /* JADX INFO: compiled from: ParcelableCompatHoneycombMR2.java */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 29 */    class ParcelableCompatCreatorHoneycombMR2<T> implements Parcelable.ClassLoaderCreator<T> {
                private final ParcelableCompatCreatorCallbacks<T> mCallbacks;

/* 30 */        public ParcelableCompatCreatorHoneycombMR2(ParcelableCompatCreatorCallbacks<T> callbacks) {
/* 32 */            this.mCallbacks = callbacks;
                }

                @Override // android.os.Parcelable.Creator
/* 35 */        public T createFromParcel(Parcel in) {
/* 36 */            return this.mCallbacks.createFromParcel(in, null);
                }

                @Override // android.os.Parcelable.ClassLoaderCreator
/* 39 */        public T createFromParcel(Parcel in, ClassLoader loader) {
/* 40 */            return this.mCallbacks.createFromParcel(in, loader);
                }

                @Override // android.os.Parcelable.Creator
/* 43 */        public T[] newArray(int size) {
/* 44 */            return this.mCallbacks.newArray(size);
                }
            }
