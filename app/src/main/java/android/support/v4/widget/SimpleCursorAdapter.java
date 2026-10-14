            package android.support.v4.widget;

            import android.content.Context;
            import android.database.Cursor;
            import android.net.Uri;
            import android.view.View;
            import android.widget.ImageView;
            import android.widget.TextView;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 61 */    public class SimpleCursorAdapter extends ResourceCursorAdapter {
                private CursorToStringConverter mCursorToStringConverter;
                protected int[] mFrom;
                String[] mOriginalFrom;
                private int mStringConversionColumn;
                protected int[] mTo;
                private ViewBinder mViewBinder;

                public interface CursorToStringConverter {
                    CharSequence convertToString(Cursor cursor);
                }

                public interface ViewBinder {
                    boolean setViewValue(View view, Cursor cursor, int i);
                }

                @Deprecated
/* 62 */        public SimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
/* 63 */            super(context, layout, c);
/* 47 */            this.mStringConversionColumn = -1;
/* 64 */            this.mTo = to;
/* 65 */            this.mOriginalFrom = from;
/* 66 */            findColumns(from);
                }

/* 88 */        public SimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
/* 89 */            super(context, layout, c, flags);
/* 47 */            this.mStringConversionColumn = -1;
/* 90 */            this.mTo = to;
/* 91 */            this.mOriginalFrom = from;
/* 92 */            findColumns(from);
                }

                @Override // android.support.v4.widget.CursorAdapter
/* 120 */       public void bindView(View view, Context context, Cursor cursor) {
/* 121 */           ViewBinder binder = this.mViewBinder;
/* 122 */           int count = this.mTo.length;
/* 123 */           int[] from = this.mFrom;
/* 124 */           int[] to = this.mTo;
                    for (int i = 0; i < count; i++) {
/* 127 */               View v = view.findViewById(to[i]);
/* 128 */               if (v != null) {
/* 129 */                   boolean bound = false;
/* 130 */                   if (binder != null) {
/* 131 */                       bound = binder.setViewValue(v, cursor, from[i]);
                            }
/* 134 */                   if (bound) {
                                continue;
                            } else {
/* 135 */                       String text = cursor.getString(from[i]);
/* 136 */                       if (text == null) {
/* 137 */                           text = "";
                                }
/* 140 */                       if (v instanceof TextView) {
/* 141 */                           setViewText((TextView) v, text);
                                } else if (v instanceof ImageView) {
/* 143 */                           setViewImage((ImageView) v, text);
                                } else {
/* 145 */                           throw new IllegalStateException(v.getClass().getName() + " is not a  view that can be bounds by this SimpleCursorAdapter");
                                }
                            }
                        }
                    }
                }

/* 161 */       public ViewBinder getViewBinder() {
/* 162 */           return this.mViewBinder;
                }

/* 174 */       public void setViewBinder(ViewBinder viewBinder) {
/* 175 */           this.mViewBinder = viewBinder;
                }

/* 194 */       public void setViewImage(ImageView v, String value) {
                    try {
/* 195 */               v.setImageResource(Integer.parseInt(value));
                    } catch (NumberFormatException e) {
/* 197 */               v.setImageURI(Uri.parse(value));
                    }
                }

/* 212 */       public void setViewText(TextView v, String text) {
/* 213 */           v.setText(text);
                }

/* 227 */       public int getStringConversionColumn() {
/* 228 */           return this.mStringConversionColumn;
                }

/* 245 */       public void setStringConversionColumn(int stringConversionColumn) {
/* 246 */           this.mStringConversionColumn = stringConversionColumn;
                }

/* 261 */       public CursorToStringConverter getCursorToStringConverter() {
/* 262 */           return this.mCursorToStringConverter;
                }

/* 277 */       public void setCursorToStringConverter(CursorToStringConverter cursorToStringConverter) {
/* 278 */           this.mCursorToStringConverter = cursorToStringConverter;
                }

                @Override // android.support.v4.widget.CursorAdapter, android.support.v4.widget.CursorFilter.CursorFilterClient
/* 293 */       public CharSequence convertToString(Cursor cursor) {
/* 294 */           if (this.mCursorToStringConverter != null) {
/* 295 */               return this.mCursorToStringConverter.convertToString(cursor);
                    }
/* 296 */           if (this.mStringConversionColumn > -1) {
/* 297 */               return cursor.getString(this.mStringConversionColumn);
                    }
/* 300 */           return super.convertToString(cursor);
                }

/* 309 */       private void findColumns(String[] from) {
/* 310 */           if (this.mCursor != null) {
/* 312 */               int count = from.length;
/* 313 */               if (this.mFrom == null || this.mFrom.length != count) {
/* 314 */                   this.mFrom = new int[count];
                        }
                        for (int i = 0; i < count; i++) {
/* 317 */                   this.mFrom[i] = this.mCursor.getColumnIndexOrThrow(from[i]);
                        }
/* 322 */               return;
                    }
/* 320 */           this.mFrom = null;
                }

                @Override // android.support.v4.widget.CursorAdapter
/* 325 */       public Cursor swapCursor(Cursor c) {
/* 326 */           Cursor res = super.swapCursor(c);
/* 328 */           findColumns(this.mOriginalFrom);
/* 329 */           return res;
                }

/* 343 */       public void changeCursorAndColumns(Cursor c, String[] from, int[] to) {
/* 344 */           this.mOriginalFrom = from;
/* 345 */           this.mTo = to;
/* 346 */           super.changeCursor(c);
/* 347 */           findColumns(this.mOriginalFrom);
                }
            }
