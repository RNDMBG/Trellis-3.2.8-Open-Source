            package co.vine.android.widget;

            import android.database.DataSetObserver;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.BaseAdapter;
            import android.widget.CursorAdapter;
            import android.widget.TextView;
            import co.vine.android.C0257R;
            import co.vine.android.util.CrashUtil;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 32 */    public class SectionAdapter extends BaseAdapter implements PinnedHeaderAdapter {
                public static final int TYPE_CARDROW = 0;
                public static final int TYPE_NORMAL = 1;
                private static final int VIEW_TYPE_SECTION = 0;
                private final BaseAdapter[] mAdapters;
                private final boolean[] mCardHeaders;
                private final AdapterDataSetObserver mDataSetObserver;
                private final int mDefaultType;
                boolean mNotifyOnChange;
                private PinnedHeaderAdapter mPinnedHeaderAdapter;
                private final int mSectionLayout;
                private final int[] mStringResIds;
                private final int[] mTypes;

/* 33 */        public SectionAdapter(BaseAdapter[] adapters) {
/* 34 */            this(null, null, null, adapters, C0257R.layout.section_divider, 0);
                }

/* 37 */        public SectionAdapter(BaseAdapter[] adapters, int defaultType) {
/* 38 */            this(null, null, null, adapters, C0257R.layout.section_divider, defaultType);
                }

/* 41 */        public SectionAdapter(int[] types, int[] stringIds, BaseAdapter[] adapters) {
/* 42 */            this(null, types, stringIds, adapters, C0257R.layout.section_divider, 0);
                }

                /* JADX WARN: Multi-variable type inference failed */
/* 45 */        public SectionAdapter(boolean[] headers, int[] types, int[] stringIds, BaseAdapter[] baseAdapterArr, int sectionLayout, int defaultType) {
/* 31 */            this.mNotifyOnChange = true;
/* 47 */            this.mCardHeaders = headers;
/* 48 */            this.mStringResIds = stringIds;
/* 49 */            this.mAdapters = baseAdapterArr;
/* 50 */            this.mSectionLayout = sectionLayout;
/* 51 */            this.mTypes = types;
/* 52 */            this.mDefaultType = defaultType;
/* 54 */            this.mDataSetObserver = new AdapterDataSetObserver();
                    for (BaseAdapter baseAdapter : baseAdapterArr) {
                        if (baseAdapter instanceof CursorAdapter) {
                            CursorAdapter cursorAdapter = (CursorAdapter) baseAdapter;
/* 56 */                cursorAdapter.registerDataSetObserver(this.mDataSetObserver);
/* 57 */                if (cursorAdapter instanceof PinnedHeaderAdapter) {
/* 58 */                    this.mPinnedHeaderAdapter = (PinnedHeaderAdapter) cursorAdapter;
                        }
                        }
                    }
                }

/* 63 */        public AdapterDataSetObserver getDataSetObserver() {
/* 64 */            return this.mDataSetObserver;
                }

/* 67 */        public void release() {
/* 68 */            BaseAdapter[] arr$ = this.mAdapters;
                    for (BaseAdapter adapter : arr$) {
/* 69 */                adapter.unregisterDataSetObserver(this.mDataSetObserver);
                    }
                }

/* 73 */        private boolean hasDivider(int index) {
                    return this.mStringResIds != null && this.mStringResIds[index] > 0;
                }

/* 78 */        private int getAdapterCount(BaseAdapter adapter) {
                    try {
/* 79 */                return adapter.getCount();
                    } catch (Exception e) {
/* 81 */                CrashUtil.logException(e);
/* 82 */                return 0;
                    }
                }

                @Override // android.widget.Adapter
/* 86 */        public int getCount() {
/* 87 */            int total = 0;
/* 88 */            int index = 0;
/* 90 */            BaseAdapter[] arr$ = this.mAdapters;
                    for (BaseAdapter adapter : arr$) {
/* 91 */                int count = getAdapterCount(adapter);
/* 92 */                if (count > 0) {
/* 93 */                    total += count;
/* 94 */                    if (hasDivider(index)) {
/* 95 */                        total++;
                            }
                        }
/* 98 */                index++;
                    }
/* 100 */           return total;
                }

/* 103 */       public int getNumberOfAdapters() {
/* 104 */           if (this.mAdapters != null) {
/* 105 */               return this.mAdapters.length;
                    }
                    return 0;
                }

                @Override // android.widget.Adapter
/* 111 */       public Object getItem(int pos) {
/* 112 */           int index = 0;
/* 113 */           BaseAdapter[] arr$ = this.mAdapters;
                    for (BaseAdapter adapter : arr$) {
/* 114 */               int count = getAdapterCount(adapter);
/* 115 */               if (count > 0) {
/* 116 */                   if (hasDivider(index)) {
/* 117 */                       count++;
/* 118 */                       if (pos == 0) {
                                    return null;
                                }
/* 120 */                       if (pos < count) {
/* 121 */                           return adapter.getItem(pos - 1);
                                }
                            } else if (pos < count) {
/* 125 */                       return adapter.getItem(pos);
                            }
/* 128 */                   pos -= count;
                        }
/* 130 */               index++;
                    }
                    return null;
                }

                @Override // android.widget.Adapter
/* 136 */       public long getItemId(int pos) {
/* 137 */           int index = 0;
/* 138 */           BaseAdapter[] arr$ = this.mAdapters;
                    for (BaseAdapter adapter : arr$) {
/* 139 */               int count = getAdapterCount(adapter);
/* 140 */               if (count > 0) {
/* 141 */                   if (hasDivider(index)) {
/* 142 */                       count++;
/* 143 */                       if (pos == 0) {
                                    return 0L;
                                }
/* 145 */                       if (pos < count) {
/* 146 */                           return adapter.getItemId(pos - 1);
                                }
                            } else if (pos < count) {
/* 150 */                       return adapter.getItemId(pos);
                            }
/* 153 */                   pos -= count;
                        }
/* 155 */               index++;
                    }
                    return 0L;
                }

                @Override // android.widget.Adapter
/* 160 */       public View getView(int pos, View view, ViewGroup parent) {
/* 161 */           LayoutInflater inflater = LayoutInflater.from(parent.getContext());
/* 164 */           int index = 0;
/* 165 */           int visibleIndex = 0;
/* 167 */           BaseAdapter[] arr$ = this.mAdapters;
                    for (BaseAdapter adapter : arr$) {
/* 168 */               int count = getAdapterCount(adapter);
/* 169 */               if (count > 0) {
/* 170 */                   if (hasDivider(index)) {
/* 171 */                       count++;
/* 172 */                       if (pos == 0) {
/* 174 */                           if (view == null) {
/* 175 */                               view = inflater.inflate(this.mSectionLayout, parent, false);
/* 176 */                               view.setTag(view.findViewById(C0257R.id.text));
                                    }
/* 178 */                           int[] strings = this.mStringResIds;
/* 179 */                           int stringResId = strings[index % strings.length];
/* 180 */                           if (stringResId == 0) {
/* 181 */                               ((TextView) view.getTag()).setText((CharSequence) null);
                                    } else {
/* 183 */                               ((TextView) view.getTag()).setText(stringResId);
                                    }
                                    return view;
                                }
/* 186 */                       if (pos < count) {
/* 187 */                           return getAdapterView(adapter, visibleIndex, index, pos - 1, view, parent);
                                }
                            } else if (pos < count) {
/* 191 */                       return getAdapterView(adapter, visibleIndex, index, pos, view, parent);
                            }
/* 194 */                   pos -= count;
/* 195 */                   visibleIndex++;
                        }
/* 197 */               index++;
                    }
                    return view;
                }

/* 205 */       protected View getAdapterView(BaseAdapter adapter, int visibleIndex, int adapterIndex, int pos, View convertView, ViewGroup parent) {
/* 206 */           if (this.mTypes != null) {
/* 207 */               int i = this.mTypes[adapterIndex];
                    } else {
/* 209 */               int type = this.mDefaultType;
                    }
/* 221 */           return adapter.getView(pos, convertView, parent);
                }

                @Override // android.widget.BaseAdapter, android.widget.ListAdapter
/* 226 */       public boolean areAllItemsEnabled() {
/* 227 */           boolean showDividers = false;
/* 228 */           int len = this.mAdapters.length;
                    for (int index = 0; index < len; index++) {
/* 230 */               if (hasDivider(index)) {
/* 231 */                   showDividers = true;
                            break;
                        }
                    }
                    return !showDividers && super.areAllItemsEnabled();
                }

                @Override // android.widget.BaseAdapter, android.widget.ListAdapter
/* 240 */       public boolean isEnabled(int pos) {
/* 241 */           int index = 0;
/* 242 */           BaseAdapter[] arr$ = this.mAdapters;
                    for (BaseAdapter adapter : arr$) {
/* 243 */               int count = getAdapterCount(adapter);
/* 244 */               if (count > 0) {
/* 245 */                   if (hasDivider(index)) {
/* 246 */                       count++;
/* 247 */                       if (pos == 0) {
/* 248 */                           return false;
                                }
/* 249 */                       if (pos < count) {
/* 250 */                           return adapter.isEnabled(pos - 1);
                                }
                            } else if (pos < count) {
/* 254 */                       return adapter.isEnabled(pos);
                            }
/* 257 */                   pos -= count;
                        }
/* 259 */               index++;
                    }
/* 261 */           return super.isEnabled(pos);
                }

                @Override // android.widget.BaseAdapter, android.widget.Adapter
/* 267 */       public int getItemViewType(int pos) {
/* 268 */           int typeCount = 1;
/* 269 */           int index = 0;
/* 270 */           BaseAdapter[] arr$ = this.mAdapters;
                    for (BaseAdapter adapter : arr$) {
/* 271 */               int count = getAdapterCount(adapter);
/* 272 */               if (count > 0) {
/* 273 */                   if (hasDivider(index)) {
/* 274 */                       count++;
/* 275 */                       if (pos == 0) {
/* 276 */                           return 0;
                                }
/* 277 */                       if (pos < count) {
/* 278 */                           return adapter.getItemViewType(pos - 1) + typeCount;
                                }
                            } else if (pos < count) {
/* 282 */                       return adapter.getItemViewType(pos) + typeCount;
                            }
/* 285 */                   pos -= count;
                        }
/* 287 */               typeCount += adapter.getViewTypeCount();
/* 288 */               index++;
                    }
/* 290 */           return super.getItemViewType(pos);
                }

                @Override // android.widget.BaseAdapter, android.widget.Adapter
/* 294 */       public int getViewTypeCount() {
/* 295 */           int count = 0;
/* 296 */           BaseAdapter[] arr$ = this.mAdapters;
                    for (BaseAdapter adapter : arr$) {
/* 297 */               count += adapter.getViewTypeCount();
                    }
/* 300 */           return count + 1;
                }

/* 309 */       public int getAdapterIndex(int pos) {
/* 310 */           int index = 0;
/* 312 */           BaseAdapter[] arr$ = this.mAdapters;
                    for (BaseAdapter adapter : arr$) {
/* 313 */               int count = getAdapterCount(adapter);
/* 314 */               if (hasDivider(index)) {
/* 315 */                   count++;
                        }
/* 317 */               if (pos < count) {
                            break;
                        }
/* 320 */               index++;
                    }
/* 322 */           return index;
                }

/* 325 */       public int getMyAdapterIndex(BaseAdapter adapter) {
/* 326 */           int index = 0;
/* 327 */           BaseAdapter[] arr$ = this.mAdapters;
                    for (BaseAdapter ba : arr$) {
/* 328 */               if (ba == adapter) {
                            break;
                        }
/* 331 */               index++;
                    }
/* 333 */           return index;
                }

/* 336 */       public int getPositionOffset(int index) {
/* 337 */           int offset = 0;
                    for (int i = 0; i < index; i++) {
/* 339 */               offset += this.mAdapters[i].getCount();
                    }
/* 341 */           return offset;
                }

                @Override // android.widget.BaseAdapter
/* 345 */       public void notifyDataSetChanged() {
/* 346 */           super.notifyDataSetChanged();
/* 347 */           this.mNotifyOnChange = true;
                }

                @Override // co.vine.android.widget.PinnedHeaderAdapter
/* 351 */       public PinnedHeaderAdapter.PinnedHeaderStatus getPinnedHeaderStatus(int navBottom) {
/* 352 */           if (this.mPinnedHeaderAdapter != null) {
/* 353 */               return this.mPinnedHeaderAdapter.getPinnedHeaderStatus(navBottom);
                    }
                    return null;
                }

                @Override // co.vine.android.widget.PinnedHeaderAdapter
/* 359 */       public View getPinnedHeaderView(View convertView) {
/* 360 */           if (this.mPinnedHeaderAdapter != null) {
/* 361 */               return this.mPinnedHeaderAdapter.getPinnedHeaderView(convertView);
                    }
                    return null;
                }

                @Override // co.vine.android.widget.PinnedHeaderAdapter
/* 367 */       public int getPinnedHeaderHeight() {
/* 368 */           if (this.mPinnedHeaderAdapter != null) {
/* 369 */               return this.mPinnedHeaderAdapter.getPinnedHeaderHeight();
                    }
                    return 0;
                }

                @Override // co.vine.android.widget.PinnedHeaderAdapter
/* 376 */       public void layoutPinnedHeader(View header, int l, int t, int r, int b) {
/* 377 */           this.mPinnedHeaderAdapter.layoutPinnedHeader(header, l, t, r, b);
                }

/* 378 */       class AdapterDataSetObserver extends DataSetObserver {
/* 379 */           AdapterDataSetObserver() {
                    }

                    @Override // android.database.DataSetObserver
/* 382 */           public void onInvalidated() {
/* 383 */               SectionAdapter.this.notifyDataSetInvalidated();
                    }

                    @Override // android.database.DataSetObserver
/* 387 */           public void onChanged() {
/* 388 */               if (SectionAdapter.this.mNotifyOnChange) {
/* 389 */                   SectionAdapter.this.notifyDataSetChanged();
                        }
                    }
                }
            }
