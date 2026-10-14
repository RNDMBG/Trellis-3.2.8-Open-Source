            package android.support.v4.view;

            import android.database.DataSetObservable;
            import android.database.DataSetObserver;
            import android.os.Parcelable;
            import android.view.View;
            import android.view.ViewGroup;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 93 */    public abstract class PagerAdapter {
                public static final int POSITION_NONE = -2;
                public static final int POSITION_UNCHANGED = -1;
                private DataSetObservable mObservable = new DataSetObservable();

                public abstract int getCount();

                public abstract boolean isViewFromObject(View view, Object obj);

/* 94 */        public void startUpdate(ViewGroup container) {
/* 95 */            startUpdate((View) container);
                }

/* 109 */       public Object instantiateItem(ViewGroup container, int position) {
/* 110 */           return instantiateItem((View) container, position);
                }

/* 123 */       public void destroyItem(ViewGroup container, int position, Object object) {
/* 124 */           destroyItem((View) container, position, object);
                }

/* 136 */       public void setPrimaryItem(ViewGroup container, int position, Object object) {
/* 137 */           setPrimaryItem((View) container, position, object);
                }

/* 147 */       public void finishUpdate(ViewGroup container) {
/* 148 */           finishUpdate((View) container);
                }

/* 158 */       public void startUpdate(View container) {
                }

/* 174 */       public Object instantiateItem(View container, int position) {
/* 175 */           throw new UnsupportedOperationException("Required method instantiateItem was not overridden");
                }

/* 191 */       public void destroyItem(View container, int position, Object object) {
/* 192 */           throw new UnsupportedOperationException("Required method destroyItem was not overridden");
                }

/* 206 */       public void setPrimaryItem(View container, int position, Object object) {
                }

/* 218 */       public void finishUpdate(View container) {
                }

/* 238 */       public Parcelable saveState() {
/* 239 */           return null;
                }

/* 249 */       public void restoreState(Parcelable state, ClassLoader loader) {
                }

/* 267 */       public int getItemPosition(Object object) {
/* 268 */           return -1;
                }

/* 275 */       public void notifyDataSetChanged() {
/* 276 */           this.mObservable.notifyChanged();
                }

/* 284 */       public void registerDataSetObserver(DataSetObserver observer) {
/* 285 */           this.mObservable.registerObserver(observer);
                }

/* 293 */       public void unregisterDataSetObserver(DataSetObserver observer) {
/* 294 */           this.mObservable.unregisterObserver(observer);
                }

/* 306 */       public CharSequence getPageTitle(int position) {
/* 307 */           return null;
                }

/* 317 */       public float getPageWidth(int position) {
/* 318 */           return 1.0f;
                }
            }
