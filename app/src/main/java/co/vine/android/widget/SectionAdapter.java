            package co.vine.android.widget;

            import android.database.DataSetObserver;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.BaseAdapter;
            import android.widget.CursorAdapter;
            import android.widget.TextView;
            import co.vine.android.R;
            import co.vine.android.util.CrashUtil;
            
            
    public class SectionAdapter extends BaseAdapter implements PinnedHeaderAdapter {
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

        public SectionAdapter(BaseAdapter[] adapters) {
            this(null, null, null, adapters, R.layout.section_divider, 0);
                }

        public SectionAdapter(BaseAdapter[] adapters, int defaultType) {
            this(null, null, null, adapters, R.layout.section_divider, defaultType);
                }

        public SectionAdapter(int[] types, int[] stringIds, BaseAdapter[] adapters) {
            this(null, types, stringIds, adapters, R.layout.section_divider, 0);
                }

                
        public SectionAdapter(boolean[] headers, int[] types, int[] stringIds, BaseAdapter[] baseAdapterArr, int sectionLayout, int defaultType) {
            this.mNotifyOnChange = true;
            this.mCardHeaders = headers;
            this.mStringResIds = stringIds;
            this.mAdapters = baseAdapterArr;
            this.mSectionLayout = sectionLayout;
            this.mTypes = types;
            this.mDefaultType = defaultType;
            this.mDataSetObserver = new AdapterDataSetObserver();
                    for (BaseAdapter baseAdapter : baseAdapterArr) {
                        if (baseAdapter instanceof CursorAdapter) {
                            CursorAdapter cursorAdapter = (CursorAdapter) baseAdapter;
                cursorAdapter.registerDataSetObserver(this.mDataSetObserver);
                if (cursorAdapter instanceof PinnedHeaderAdapter) {
                    this.mPinnedHeaderAdapter = (PinnedHeaderAdapter) cursorAdapter;
                        }
                        }
                    }
                }

        public AdapterDataSetObserver getDataSetObserver() {
            return this.mDataSetObserver;
                }

        public void release() {
            BaseAdapter[] arr$ = this.mAdapters;
                    for (BaseAdapter adapter : arr$) {
                adapter.unregisterDataSetObserver(this.mDataSetObserver);
                    }
                }

        private boolean hasDivider(int index) {
                    return this.mStringResIds != null && this.mStringResIds[index] > 0;
                }

        private int getAdapterCount(BaseAdapter adapter) {
                    try {
                return adapter.getCount();
                    } catch (Exception e) {
                CrashUtil.logException(e);
                return 0;
                    }
                }

                @Override 
        public int getCount() {
            int total = 0;
            int index = 0;
            BaseAdapter[] arr$ = this.mAdapters;
                    for (BaseAdapter adapter : arr$) {
                int count = getAdapterCount(adapter);
                if (count > 0) {
                    total += count;
                    if (hasDivider(index)) {
                        total++;
                            }
                        }
                index++;
                    }
           return total;
                }

       public int getNumberOfAdapters() {
           if (this.mAdapters != null) {
               return this.mAdapters.length;
                    }
                    return 0;
                }

                @Override 
       public Object getItem(int pos) {
           int index = 0;
           BaseAdapter[] arr$ = this.mAdapters;
                    for (BaseAdapter adapter : arr$) {
               int count = getAdapterCount(adapter);
               if (count > 0) {
                   if (hasDivider(index)) {
                       count++;
                       if (pos == 0) {
                                    return null;
                                }
                       if (pos < count) {
                           return adapter.getItem(pos - 1);
                                }
                            } else if (pos < count) {
                       return adapter.getItem(pos);
                            }
                   pos -= count;
                        }
               index++;
                    }
                    return null;
                }

                @Override 
       public long getItemId(int pos) {
           int index = 0;
           BaseAdapter[] arr$ = this.mAdapters;
                    for (BaseAdapter adapter : arr$) {
               int count = getAdapterCount(adapter);
               if (count > 0) {
                   if (hasDivider(index)) {
                       count++;
                       if (pos == 0) {
                                    return 0L;
                                }
                       if (pos < count) {
                           return adapter.getItemId(pos - 1);
                                }
                            } else if (pos < count) {
                       return adapter.getItemId(pos);
                            }
                   pos -= count;
                        }
               index++;
                    }
                    return 0L;
                }

                @Override 
       public View getView(int pos, View view, ViewGroup parent) {
           LayoutInflater inflater = LayoutInflater.from(parent.getContext());
           int index = 0;
           int visibleIndex = 0;
           BaseAdapter[] arr$ = this.mAdapters;
                    for (BaseAdapter adapter : arr$) {
               int count = getAdapterCount(adapter);
               if (count > 0) {
                   if (hasDivider(index)) {
                       count++;
                       if (pos == 0) {
                           if (view == null) {
                               view = inflater.inflate(this.mSectionLayout, parent, false);
                               view.setTag(view.findViewById(R.id.text));
                                    }
                           int[] strings = this.mStringResIds;
                           int stringResId = strings[index % strings.length];
                           if (stringResId == 0) {
                               ((TextView) view.getTag()).setText((CharSequence) null);
                                    } else {
                               ((TextView) view.getTag()).setText(stringResId);
                                    }
                                    return view;
                                }
                       if (pos < count) {
                           return getAdapterView(adapter, visibleIndex, index, pos - 1, view, parent);
                                }
                            } else if (pos < count) {
                       return getAdapterView(adapter, visibleIndex, index, pos, view, parent);
                            }
                   pos -= count;
                   visibleIndex++;
                        }
               index++;
                    }
                    return view;
                }

       protected View getAdapterView(BaseAdapter adapter, int visibleIndex, int adapterIndex, int pos, View convertView, ViewGroup parent) {
           if (this.mTypes != null) {
               int i = this.mTypes[adapterIndex];
                    } else {
               int type = this.mDefaultType;
                    }
           return adapter.getView(pos, convertView, parent);
                }

                @Override 
       public boolean areAllItemsEnabled() {
           boolean showDividers = false;
           int len = this.mAdapters.length;
                    for (int index = 0; index < len; index++) {
               if (hasDivider(index)) {
                   showDividers = true;
                            break;
                        }
                    }
                    return !showDividers && super.areAllItemsEnabled();
                }

                @Override 
       public boolean isEnabled(int pos) {
           int index = 0;
           BaseAdapter[] arr$ = this.mAdapters;
                    for (BaseAdapter adapter : arr$) {
               int count = getAdapterCount(adapter);
               if (count > 0) {
                   if (hasDivider(index)) {
                       count++;
                       if (pos == 0) {
                           return false;
                                }
                       if (pos < count) {
                           return adapter.isEnabled(pos - 1);
                                }
                            } else if (pos < count) {
                       return adapter.isEnabled(pos);
                            }
                   pos -= count;
                        }
               index++;
                    }
           return super.isEnabled(pos);
                }

                @Override 
       public int getItemViewType(int pos) {
           int typeCount = 1;
           int index = 0;
           BaseAdapter[] arr$ = this.mAdapters;
                    for (BaseAdapter adapter : arr$) {
               int count = getAdapterCount(adapter);
               if (count > 0) {
                   if (hasDivider(index)) {
                       count++;
                       if (pos == 0) {
                           return 0;
                                }
                       if (pos < count) {
                           return adapter.getItemViewType(pos - 1) + typeCount;
                                }
                            } else if (pos < count) {
                       return adapter.getItemViewType(pos) + typeCount;
                            }
                   pos -= count;
                        }
               typeCount += adapter.getViewTypeCount();
               index++;
                    }
           return super.getItemViewType(pos);
                }

                @Override 
       public int getViewTypeCount() {
           int count = 0;
           BaseAdapter[] arr$ = this.mAdapters;
                    for (BaseAdapter adapter : arr$) {
               count += adapter.getViewTypeCount();
                    }
           return count + 1;
                }

       public int getAdapterIndex(int pos) {
           int index = 0;
           BaseAdapter[] arr$ = this.mAdapters;
                    for (BaseAdapter adapter : arr$) {
               int count = getAdapterCount(adapter);
               if (hasDivider(index)) {
                   count++;
                        }
               if (pos < count) {
                            break;
                        }
               index++;
                    }
           return index;
                }

       public int getMyAdapterIndex(BaseAdapter adapter) {
           int index = 0;
           BaseAdapter[] arr$ = this.mAdapters;
                    for (BaseAdapter ba : arr$) {
               if (ba == adapter) {
                            break;
                        }
               index++;
                    }
           return index;
                }

       public int getPositionOffset(int index) {
           int offset = 0;
                    for (int i = 0; i < index; i++) {
               offset += this.mAdapters[i].getCount();
                    }
           return offset;
                }

                @Override 
       public void notifyDataSetChanged() {
           super.notifyDataSetChanged();
           this.mNotifyOnChange = true;
                }

                @Override 
       public PinnedHeaderAdapter.PinnedHeaderStatus getPinnedHeaderStatus(int navBottom) {
           if (this.mPinnedHeaderAdapter != null) {
               return this.mPinnedHeaderAdapter.getPinnedHeaderStatus(navBottom);
                    }
                    return null;
                }

                @Override 
       public View getPinnedHeaderView(View convertView) {
           if (this.mPinnedHeaderAdapter != null) {
               return this.mPinnedHeaderAdapter.getPinnedHeaderView(convertView);
                    }
                    return null;
                }

                @Override 
       public int getPinnedHeaderHeight() {
           if (this.mPinnedHeaderAdapter != null) {
               return this.mPinnedHeaderAdapter.getPinnedHeaderHeight();
                    }
                    return 0;
                }

                @Override 
       public void layoutPinnedHeader(View header, int l, int t, int r, int b) {
           this.mPinnedHeaderAdapter.layoutPinnedHeader(header, l, t, r, b);
                }

       class AdapterDataSetObserver extends DataSetObserver {
           AdapterDataSetObserver() {
                    }

                    @Override 
           public void onInvalidated() {
               SectionAdapter.this.notifyDataSetInvalidated();
                    }

                    @Override 
           public void onChanged() {
               if (SectionAdapter.this.mNotifyOnChange) {
                   SectionAdapter.this.notifyDataSetChanged();
                        }
                    }
                }
            }
