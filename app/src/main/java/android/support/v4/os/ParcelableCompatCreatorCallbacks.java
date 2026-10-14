            package android.support.v4.os;

            import android.os.Parcel;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface ParcelableCompatCreatorCallbacks<T> {
                T createFromParcel(Parcel parcel, ClassLoader classLoader);

                T[] newArray(int i);
            }
