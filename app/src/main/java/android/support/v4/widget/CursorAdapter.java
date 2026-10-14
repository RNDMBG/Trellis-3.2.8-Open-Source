            package android.support.v4.widget;

            import android.content.Context;
            import android.database.ContentObserver;
            import android.database.Cursor;
            import android.database.DataSetObserver;
            import android.os.Handler;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.BaseAdapter;
            import android.widget.Filter;
            import android.widget.FilterQueryProvider;
            import android.widget.Filterable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 119 */   public abstract class CursorAdapter extends BaseAdapter implements Filterable, CursorFilter.CursorFilterClient {

                @Deprecated
                public static final int FLAG_AUTO_REQUERY = 1;
                public static final int FLAG_REGISTER_CONTENT_OBSERVER = 2;
                protected boolean mAutoRequery;
                protected ChangeObserver mChangeObserver;
                protected Context mContext;
                protected Cursor mCursor;
                protected CursorFilter mCursorFilter;
                protected DataSetObserver mDataSetObserver;
                protected boolean mDataValid;
                protected FilterQueryProvider mFilterQueryProvider;
                protected int mRowIDColumn;

                public abstract void bindView(View view, Context context, Cursor cursor);

                public abstract View newView(Context context, Cursor cursor, ViewGroup viewGroup);

                @Deprecated
/* 120 */       public CursorAdapter(Context context, Cursor c) {
/* 122 */           init(context, c, 1);
                }

/* 136 */       public CursorAdapter(Context context, Cursor c, boolean autoRequery) {
/* 138 */           init(context, c, autoRequery ? 1 : 2);
                }

/* 149 */       public CursorAdapter(Context context, Cursor c, int flags) {
/* 151 */           init(context, c, flags);
                }

                @Deprecated
/* 159 */       protected void init(Context context, Cursor c, boolean autoRequery) {
/* 160 */           init(context, c, autoRequery ? 1 : 2);
                }

/* 163 */       void init(Context context, Cursor c, int flags) {
/* 164 */           if ((flags & 1) == 1) {
/* 165 */               flags |= 2;
/* 166 */               this.mAutoRequery = true;
                    } else {
/* 168 */               this.mAutoRequery = false;
                    }
/* 170 */           boolean cursorPresent = c != null;
/* 171 */           this.mCursor = c;
/* 172 */           this.mDataValid = cursorPresent;
/* 173 */           this.mContext = context;
/* 174 */           this.mRowIDColumn = cursorPresent ? c.getColumnIndexOrThrow("_id") : -1;
/* 175 */           if ((flags & 2) == 2) {
/* 176 */               this.mChangeObserver = new ChangeObserver();
/* 177 */               this.mDataSetObserver = new MyDataSetObserver(this, null);
                    } else {
/* 179 */               this.mChangeObserver = null;
/* 180 */               this.mDataSetObserver = null;
                    }
/* 183 */           if (cursorPresent) {
/* 184 */               if (this.mChangeObserver != null) {
                            c.registerContentObserver(this.mChangeObserver);
                        }
/* 185 */               if (this.mDataSetObserver != null) {
                            c.registerDataSetObserver(this.mDataSetObserver);
                        }
                    }
                }

                @Override // android.support.v4.widget.CursorFilter.CursorFilterClient
/* 193 */       public Cursor getCursor() {
/* 194 */           return this.mCursor;
                }

                @Override // android.widget.Adapter
/* 200 */       public int getCount() {
/* 201 */           if (!this.mDataValid || this.mCursor == null) {
                        return 0;
                    }
/* 202 */           return this.mCursor.getCount();
                }

                @Override // android.widget.Adapter
/* 211 */       public Object getItem(int position) {
/* 212 */           if (!this.mDataValid || this.mCursor == null) {
                        return null;
                    }
/* 213 */           this.mCursor.moveToPosition(position);
/* 214 */           return this.mCursor;
                }

                @Override // android.widget.Adapter
/* 223 */       public long getItemId(int position) {
/* 224 */           if (this.mDataValid && this.mCursor != null && this.mCursor.moveToPosition(position)) {
/* 226 */               return this.mCursor.getLong(this.mRowIDColumn);
                    }
                    return 0L;
                }

                @Override // android.widget.BaseAdapter, android.widget.Adapter
/* 236 */       public boolean hasStableIds() {
/* 237 */           return true;
                }

                @Override // android.widget.Adapter
/* 243 */       public View getView(int position, View convertView, ViewGroup parent) {
                    View v;
/* 244 */           if (!this.mDataValid) {
/* 245 */               throw new IllegalStateException("this should only be called when the cursor is valid");
                    }
/* 247 */           if (!this.mCursor.moveToPosition(position)) {
/* 248 */               throw new IllegalStateException("couldn't move cursor to position " + position);
                    }
/* 251 */           if (convertView == null) {
/* 252 */               v = newView(this.mContext, this.mCursor, parent);
                    } else {
/* 254 */               v = convertView;
                    }
/* 256 */           bindView(v, this.mContext, this.mCursor);
/* 257 */           return v;
                }

                @Override // android.widget.BaseAdapter, android.widget.SpinnerAdapter
/* 261 */       public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View v;
/* 262 */           if (this.mDataValid) {
/* 263 */               this.mCursor.moveToPosition(position);
/* 265 */               if (convertView == null) {
/* 266 */                   v = newDropDownView(this.mContext, this.mCursor, parent);
                        } else {
/* 268 */                   v = convertView;
                        }
/* 270 */               bindView(v, this.mContext, this.mCursor);
/* 273 */               return v;
                    }
/* 273 */           return null;
                }

/* 295 */       public View newDropDownView(Context context, Cursor cursor, ViewGroup parent) {
/* 296 */           return newView(context, cursor, parent);
                }

                @Override // android.support.v4.widget.CursorFilter.CursorFilterClient
/* 314 */       public void changeCursor(Cursor cursor) {
/* 315 */           Cursor old = swapCursor(cursor);
/* 316 */           if (old != null) {
/* 317 */               old.close();
                    }
                }

/* 331 */       public Cursor swapCursor(Cursor newCursor) {
/* 332 */           if (newCursor == this.mCursor) {
/* 333 */               return null;
                    }
/* 335 */           Cursor oldCursor = this.mCursor;
/* 336 */           if (oldCursor != null) {
/* 337 */               if (this.mChangeObserver != null) {
                            oldCursor.unregisterContentObserver(this.mChangeObserver);
                        }
/* 338 */               if (this.mDataSetObserver != null) {
                            oldCursor.unregisterDataSetObserver(this.mDataSetObserver);
                        }
                    }
/* 340 */           this.mCursor = newCursor;
/* 341 */           if (newCursor != null) {
/* 342 */               if (this.mChangeObserver != null) {
                            newCursor.registerContentObserver(this.mChangeObserver);
                        }
/* 343 */               if (this.mDataSetObserver != null) {
                            newCursor.registerDataSetObserver(this.mDataSetObserver);
                        }
/* 344 */               this.mRowIDColumn = newCursor.getColumnIndexOrThrow("_id");
/* 345 */               this.mDataValid = true;
/* 347 */               notifyDataSetChanged();
/* 354 */               return oldCursor;
                    }
/* 349 */           this.mRowIDColumn = -1;
/* 350 */           this.mDataValid = false;
/* 352 */           notifyDataSetInvalidated();
/* 354 */           return oldCursor;
                }

                @Override // android.support.v4.widget.CursorFilter.CursorFilterClient
/* 366 */       public CharSequence convertToString(Cursor cursor) {
                    return cursor == null ? "" : cursor.toString();
                }

                @Override // android.support.v4.widget.CursorFilter.CursorFilterClient
/* 395 */       public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
                    return this.mFilterQueryProvider != null ? this.mFilterQueryProvider.runQuery(constraint) : this.mCursor;
                }

                @Override // android.widget.Filterable
/* 403 */       public Filter getFilter() {
/* 404 */           if (this.mCursorFilter == null) {
/* 405 */               this.mCursorFilter = new CursorFilter(this);
                    }
/* 407 */           return this.mCursorFilter;
                }

/* 419 */       public FilterQueryProvider getFilterQueryProvider() {
/* 420 */           return this.mFilterQueryProvider;
                }

/* 435 */       public void setFilterQueryProvider(FilterQueryProvider filterQueryProvider) {
/* 436 */           this.mFilterQueryProvider = filterQueryProvider;
                }

/* 446 */       protected void onContentChanged() {
/* 447 */           if (this.mAutoRequery && this.mCursor != null && !this.mCursor.isClosed()) {
/* 449 */               this.mDataValid = this.mCursor.requery();
                    }
                }

/* 452 */       private class ChangeObserver extends ContentObserver {
/* 453 */           public ChangeObserver() {
/* 455 */               super(new Handler());
                    }

                    @Override // android.database.ContentObserver
/* 459 */           public boolean deliverSelfNotifications() {
/* 460 */               return true;
                    }

                    @Override // android.database.ContentObserver
/* 464 */           public void onChange(boolean selfChange) {
/* 465 */               CursorAdapter.this.onContentChanged();
                    }
                }

/* 467 */       private class MyDataSetObserver extends DataSetObserver {
/* 468 */           private MyDataSetObserver() {
                    }

/* 468 */           /* synthetic */ MyDataSetObserver(CursorAdapter x0, C00641 x1) {
/* 469 */               this();
                    }

                    @Override // android.database.DataSetObserver
/* 471 */           public void onChanged() {
/* 472 */               CursorAdapter.this.mDataValid = true;
/* 473 */               CursorAdapter.this.notifyDataSetChanged();
                    }

                    @Override // android.database.DataSetObserver
/* 477 */           public void onInvalidated() {
/* 478 */               CursorAdapter.this.mDataValid = false;
/* 479 */               CursorAdapter.this.notifyDataSetInvalidated();
                    }
                }
            }
