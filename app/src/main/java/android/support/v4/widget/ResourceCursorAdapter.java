            package android.support.v4.widget;

            import android.content.Context;
            import android.database.Cursor;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 52 */    public abstract class ResourceCursorAdapter extends CursorAdapter {
                private int mDropDownLayout;
                private LayoutInflater mInflater;
                private int mLayout;

                @Deprecated
/* 53 */        public ResourceCursorAdapter(Context context, int layout, Cursor c) {
/* 54 */            super(context, c);
/* 55 */            this.mDropDownLayout = layout;
                    this.mLayout = layout;
/* 56 */            this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
                }

/* 75 */        public ResourceCursorAdapter(Context context, int layout, Cursor c, boolean autoRequery) {
/* 76 */            super(context, c, autoRequery);
/* 77 */            this.mDropDownLayout = layout;
                    this.mLayout = layout;
/* 78 */            this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
                }

/* 92 */        public ResourceCursorAdapter(Context context, int layout, Cursor c, int flags) {
/* 93 */            super(context, c, flags);
/* 94 */            this.mDropDownLayout = layout;
                    this.mLayout = layout;
/* 95 */            this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
                }

                @Override // android.support.v4.widget.CursorAdapter
/* 105 */       public View newView(Context context, Cursor cursor, ViewGroup parent) {
/* 106 */           return this.mInflater.inflate(this.mLayout, parent, false);
                }

                @Override // android.support.v4.widget.CursorAdapter
/* 110 */       public View newDropDownView(Context context, Cursor cursor, ViewGroup parent) {
/* 111 */           return this.mInflater.inflate(this.mDropDownLayout, parent, false);
                }

/* 119 */       public void setViewResource(int layout) {
/* 120 */           this.mLayout = layout;
                }

/* 128 */       public void setDropDownViewResource(int dropDownLayout) {
/* 129 */           this.mDropDownLayout = dropDownLayout;
                }
            }
