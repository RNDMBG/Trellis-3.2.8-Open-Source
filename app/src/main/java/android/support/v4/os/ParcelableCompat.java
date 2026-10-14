            package android.support.v4.os;

            import android.os.Build;
            import android.os.Parcel;
            import android.os.Parcelable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 34 */    public class ParcelableCompat {
/* 35 */        public static <T> Parcelable.Creator<T> newCreator(ParcelableCompatCreatorCallbacks<T> callbacks) {
/* 36 */            if (Build.VERSION.SDK_INT >= 13) {
/* 37 */                ParcelableCompatCreatorHoneycombMR2Stub.instantiate(callbacks);
                    }
/* 39 */            return new CompatCreator(callbacks);
                }

/* 43 */        static class CompatCreator<T> implements Parcelable.Creator<T> {
                    final ParcelableCompatCreatorCallbacks<T> mCallbacks;

/* 44 */            public CompatCreator(ParcelableCompatCreatorCallbacks<T> callbacks) {
/* 46 */                this.mCallbacks = callbacks;
                    }

                    @Override // android.os.Parcelable.Creator
/* 50 */            public T createFromParcel(Parcel source) {
/* 51 */                return this.mCallbacks.createFromParcel(source, null);
                    }

                    @Override // android.os.Parcelable.Creator
/* 55 */            public T[] newArray(int size) {
/* 56 */                return this.mCallbacks.newArray(size);
                    }
                }
            }
