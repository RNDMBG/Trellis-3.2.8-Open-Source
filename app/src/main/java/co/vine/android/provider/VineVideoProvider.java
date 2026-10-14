            package co.vine.android.provider;

            import android.content.ContentProvider;
            import android.content.ContentValues;
            import android.database.Cursor;
            import android.net.Uri;
            import android.os.ParcelFileDescriptor;
            import co.vine.android.util.PlayerUtil;
            import java.io.File;
            import java.io.FileNotFoundException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    public class VineVideoProvider extends ContentProvider {
                public static final String AUTHORITY = PlayerUtil.getAuthority(".provider.VineVideoProvider");
                public static final String CONTENT_AUTHORITY = "content://" + AUTHORITY + "/";
                private static final String VIDEO_MIME_TYPE = "video/mp4";

                @Override // android.content.ContentProvider
/* 24 */        public boolean onCreate() {
/* 25 */            return true;
                }

                @Override // android.content.ContentProvider
/* 29 */        public String getType(Uri uri) {
/* 30 */            return "video/mp4";
                }

                @Override // android.content.ContentProvider
/* 35 */        public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
/* 36 */            File f = new File(uri.getPath());
/* 38 */            if (f.exists()) {
/* 39 */                return ParcelFileDescriptor.open(f, 268435456);
                    }
/* 42 */            throw new FileNotFoundException(uri.getPath());
                }

                @Override // android.content.ContentProvider
/* 47 */        public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
/* 48 */            throw new UnsupportedOperationException();
                }

                @Override // android.content.ContentProvider
/* 52 */        public Uri insert(Uri uri, ContentValues values) {
/* 53 */            throw new UnsupportedOperationException();
                }

                @Override // android.content.ContentProvider
/* 57 */        public int delete(Uri uri, String selection, String[] selectionArgs) {
/* 58 */            throw new UnsupportedOperationException();
                }

                @Override // android.content.ContentProvider
/* 62 */        public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
/* 63 */            throw new UnsupportedOperationException();
                }
            }
