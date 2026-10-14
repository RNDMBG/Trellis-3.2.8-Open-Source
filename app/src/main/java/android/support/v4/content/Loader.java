            package android.support.v4.content;

            import android.content.Context;
            import android.database.ContentObserver;
            import android.os.Handler;
            import android.support.v4.util.DebugUtils;
            import java.io.FileDescriptor;
            import java.io.PrintWriter;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 49 */    public class Loader<D> {
                Context mContext;
                int mId;
                OnLoadCompleteListener<D> mListener;
                boolean mStarted = false;
                boolean mAbandoned = false;
                boolean mReset = true;
                boolean mContentChanged = false;
                boolean mProcessingChange = false;

                public interface OnLoadCompleteListener<D> {
                    void onLoadComplete(Loader<D> loader, D d);
                }

/* 50 */        public final class ForceLoadContentObserver extends ContentObserver {
/* 51 */            public ForceLoadContentObserver() {
/* 53 */                super(new Handler());
                    }

                    @Override // android.database.ContentObserver
/* 57 */            public boolean deliverSelfNotifications() {
/* 58 */                return true;
                    }

                    @Override // android.database.ContentObserver
/* 62 */            public void onChange(boolean selfChange) {
/* 63 */                Loader.this.onContentChanged();
                    }
                }

/* 90 */        public Loader(Context context) {
/* 92 */            this.mContext = context.getApplicationContext();
                }

/* 102 */       public void deliverResult(D data) {
/* 103 */           if (this.mListener != null) {
/* 104 */               this.mListener.onLoadComplete(this, data);
                    }
                }

/* 111 */       public Context getContext() {
/* 112 */           return this.mContext;
                }

/* 118 */       public int getId() {
/* 119 */           return this.mId;
                }

/* 129 */       public void registerListener(int id, OnLoadCompleteListener<D> listener) {
/* 130 */           if (this.mListener != null) {
/* 131 */               throw new IllegalStateException("There is already a listener registered");
                    }
/* 133 */           this.mListener = listener;
/* 134 */           this.mId = id;
                }

/* 142 */       public void unregisterListener(OnLoadCompleteListener<D> listener) {
/* 143 */           if (this.mListener == null) {
/* 144 */               throw new IllegalStateException("No listener register");
                    }
/* 146 */           if (this.mListener != listener) {
/* 147 */               throw new IllegalArgumentException("Attempting to unregister the wrong listener");
                    }
/* 149 */           this.mListener = null;
                }

/* 157 */       public boolean isStarted() {
/* 158 */           return this.mStarted;
                }

/* 166 */       public boolean isAbandoned() {
/* 167 */           return this.mAbandoned;
                }

/* 175 */       public boolean isReset() {
/* 176 */           return this.mReset;
                }

/* 194 */       public final void startLoading() {
/* 195 */           this.mStarted = true;
/* 196 */           this.mReset = false;
/* 197 */           this.mAbandoned = false;
/* 198 */           onStartLoading();
                }

/* 206 */       protected void onStartLoading() {
                }

/* 217 */       public void forceLoad() {
/* 218 */           onForceLoad();
                }

/* 225 */       protected void onForceLoad() {
                }

/* 242 */       public void stopLoading() {
/* 243 */           this.mStarted = false;
/* 244 */           onStopLoading();
                }

/* 253 */       protected void onStopLoading() {
                }

/* 261 */       public void abandon() {
/* 262 */           this.mAbandoned = true;
/* 263 */           onAbandon();
                }

/* 275 */       protected void onAbandon() {
                }

/* 290 */       public void reset() {
/* 291 */           onReset();
/* 292 */           this.mReset = true;
/* 293 */           this.mStarted = false;
/* 294 */           this.mAbandoned = false;
/* 295 */           this.mContentChanged = false;
/* 296 */           this.mProcessingChange = false;
                }

/* 305 */       protected void onReset() {
                }

/* 313 */       public boolean takeContentChanged() {
/* 314 */           boolean res = this.mContentChanged;
/* 315 */           this.mContentChanged = false;
/* 316 */           this.mProcessingChange |= res;
/* 317 */           return res;
                }

/* 327 */       public void commitContentChanged() {
/* 328 */           this.mProcessingChange = false;
                }

/* 338 */       public void rollbackContentChanged() {
/* 339 */           if (this.mProcessingChange) {
/* 340 */               this.mContentChanged = true;
                    }
                }

/* 352 */       public void onContentChanged() {
/* 353 */           if (this.mStarted) {
/* 354 */               forceLoad();
                    } else {
/* 359 */               this.mContentChanged = true;
                    }
                }

/* 367 */       public String dataToString(D data) {
/* 368 */           StringBuilder sb = new StringBuilder(64);
/* 369 */           DebugUtils.buildShortClassTag(data, sb);
/* 370 */           sb.append("}");
/* 371 */           return sb.toString();
                }

/* 375 */       public String toString() {
/* 376 */           StringBuilder sb = new StringBuilder(64);
/* 377 */           DebugUtils.buildShortClassTag(this, sb);
/* 378 */           sb.append(" id=");
/* 379 */           sb.append(this.mId);
/* 380 */           sb.append("}");
/* 381 */           return sb.toString();
                }

/* 392 */       public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
/* 393 */           writer.print(prefix);
                    writer.print("mId=");
                    writer.print(this.mId);
/* 394 */           writer.print(" mListener=");
                    writer.println(this.mListener);
/* 395 */           if (this.mStarted || this.mContentChanged || this.mProcessingChange) {
/* 396 */               writer.print(prefix);
                        writer.print("mStarted=");
                        writer.print(this.mStarted);
/* 397 */               writer.print(" mContentChanged=");
                        writer.print(this.mContentChanged);
/* 398 */               writer.print(" mProcessingChange=");
                        writer.println(this.mProcessingChange);
                    }
/* 400 */           if (this.mAbandoned || this.mReset) {
/* 401 */               writer.print(prefix);
                        writer.print("mAbandoned=");
                        writer.print(this.mAbandoned);
/* 402 */               writer.print(" mReset=");
                        writer.println(this.mReset);
                    }
                }
            }
