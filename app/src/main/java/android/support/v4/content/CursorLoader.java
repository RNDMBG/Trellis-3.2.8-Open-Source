            package android.support.v4.content;

            import android.content.Context;
            import android.database.Cursor;
            import android.net.Uri;
            import java.io.FileDescriptor;
            import java.io.PrintWriter;
            import java.util.Arrays;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 33 */    public class CursorLoader extends AsyncTaskLoader<Cursor> {
                Cursor mCursor;
                final Loader<Cursor>.ForceLoadContentObserver mObserver;
                String[] mProjection;
                String mSelection;
                String[] mSelectionArgs;
                String mSortOrder;
                Uri mUri;

                @Override // android.support.v4.content.Loader
/* 34 */        public /* bridge */ /* synthetic */ void deliverResult(Object x0) {
/* 35 */            deliverResult((Cursor) x0);
                }

                @Override // android.support.v4.content.AsyncTaskLoader
/* 34 */        public /* bridge */ /* synthetic */ Cursor loadInBackground() {
/* 35 */            return loadInBackground();
                }

                @Override // android.support.v4.content.AsyncTaskLoader
/* 34 */        public /* bridge */ /* synthetic */ void onCanceled(Cursor cursor) {
/* 35 */            onCanceled2(cursor);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.support.v4.content.AsyncTaskLoader
/* 48 */        public Cursor loadInBackground() {
/* 49 */            Cursor cursor = getContext().getContentResolver().query(this.mUri, this.mProjection, this.mSelection, this.mSelectionArgs, this.mSortOrder);
/* 51 */            if (cursor != null) {
/* 53 */                cursor.getCount();
/* 54 */                cursor.registerContentObserver(this.mObserver);
                    }
/* 56 */            return cursor;
                }

/* 61 */        public void deliverResult(Cursor cursor) {
/* 62 */            if (isReset()) {
/* 64 */                if (cursor != null) {
/* 65 */                    cursor.close();
/* 79 */                    return;
                        }
/* 79 */                return;
                    }
/* 69 */            Cursor oldCursor = this.mCursor;
/* 70 */            this.mCursor = cursor;
/* 72 */            if (isStarted()) {
/* 73 */                super.deliverResult(cursor);
                    }
/* 76 */            if (oldCursor != null && oldCursor != cursor && !oldCursor.isClosed()) {
/* 77 */                oldCursor.close();
                    }
                }

/* 86 */        public CursorLoader(Context context) {
/* 87 */            super(context);
/* 88 */            this.mObserver = new Loader.ForceLoadContentObserver();
                }

/* 98 */        public CursorLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
/* 99 */            super(context);
/* 100 */           this.mObserver = new Loader.ForceLoadContentObserver();
/* 101 */           this.mUri = uri;
/* 102 */           this.mProjection = projection;
/* 103 */           this.mSelection = selection;
/* 104 */           this.mSelectionArgs = selectionArgs;
/* 105 */           this.mSortOrder = sortOrder;
                }

                @Override // android.support.v4.content.Loader
/* 116 */       protected void onStartLoading() {
/* 117 */           if (this.mCursor != null) {
/* 118 */               deliverResult(this.mCursor);
                    }
/* 120 */           if (takeContentChanged() || this.mCursor == null) {
/* 121 */               forceLoad();
                    }
                }

                @Override // android.support.v4.content.Loader
/* 130 */       protected void onStopLoading() {
/* 131 */           cancelLoad();
                }

                /* JADX INFO: renamed from: onCanceled, reason: avoid collision after fix types in other method */
/* 135 */       public void onCanceled2(Cursor cursor) {
/* 136 */           if (cursor != null && !cursor.isClosed()) {
/* 137 */               cursor.close();
                    }
                }

                @Override // android.support.v4.content.Loader
/* 142 */       protected void onReset() {
/* 143 */           super.onReset();
/* 146 */           onStopLoading();
/* 148 */           if (this.mCursor != null && !this.mCursor.isClosed()) {
/* 149 */               this.mCursor.close();
                    }
/* 151 */           this.mCursor = null;
                }

/* 154 */       public Uri getUri() {
/* 155 */           return this.mUri;
                }

/* 158 */       public void setUri(Uri uri) {
/* 159 */           this.mUri = uri;
                }

/* 162 */       public String[] getProjection() {
/* 163 */           return this.mProjection;
                }

/* 166 */       public void setProjection(String[] projection) {
/* 167 */           this.mProjection = projection;
                }

/* 170 */       public String getSelection() {
/* 171 */           return this.mSelection;
                }

/* 174 */       public void setSelection(String selection) {
/* 175 */           this.mSelection = selection;
                }

/* 178 */       public String[] getSelectionArgs() {
/* 179 */           return this.mSelectionArgs;
                }

/* 182 */       public void setSelectionArgs(String[] selectionArgs) {
/* 183 */           this.mSelectionArgs = selectionArgs;
                }

/* 186 */       public String getSortOrder() {
/* 187 */           return this.mSortOrder;
                }

/* 190 */       public void setSortOrder(String sortOrder) {
/* 191 */           this.mSortOrder = sortOrder;
                }

                @Override // android.support.v4.content.AsyncTaskLoader, android.support.v4.content.Loader
/* 195 */       public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
/* 196 */           super.dump(prefix, fd, writer, args);
/* 197 */           writer.print(prefix);
                    writer.print("mUri=");
                    writer.println(this.mUri);
/* 198 */           writer.print(prefix);
                    writer.print("mProjection=");
/* 199 */           writer.println(Arrays.toString(this.mProjection));
/* 200 */           writer.print(prefix);
                    writer.print("mSelection=");
                    writer.println(this.mSelection);
/* 201 */           writer.print(prefix);
                    writer.print("mSelectionArgs=");
/* 202 */           writer.println(Arrays.toString(this.mSelectionArgs));
/* 203 */           writer.print(prefix);
                    writer.print("mSortOrder=");
                    writer.println(this.mSortOrder);
/* 204 */           writer.print(prefix);
                    writer.print("mCursor=");
                    writer.println(this.mCursor);
/* 205 */           writer.print(prefix);
                    writer.print("mContentChanged=");
                    writer.println(this.mContentChanged);
                }
            }
