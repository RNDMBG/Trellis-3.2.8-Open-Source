            package android.support.v4.os;

            import android.os.Parcel;
            import android.os.Parcelable;
            
            
            
    class ParcelableCompatCreatorHoneycombMR2<T> implements Parcelable.ClassLoaderCreator<T> {
                private final ParcelableCompatCreatorCallbacks<T> mCallbacks;

        public ParcelableCompatCreatorHoneycombMR2(ParcelableCompatCreatorCallbacks<T> callbacks) {
            this.mCallbacks = callbacks;
                }

                @Override 
        public T createFromParcel(Parcel in) {
            return this.mCallbacks.createFromParcel(in, null);
                }

                @Override 
        public T createFromParcel(Parcel in, ClassLoader loader) {
            return this.mCallbacks.createFromParcel(in, loader);
                }

                @Override 
        public T[] newArray(int size) {
            return this.mCallbacks.newArray(size);
                }
            }
