            package co.vine.android.recorder;

            import android.content.Context;
            import android.database.DataSetObserver;
            import android.graphics.drawable.BitmapDrawable;
            import android.text.TextUtils;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.BaseAdapter;
            import android.widget.ListAdapter;
            import co.vine.android.dragsort.DragSortWidget;
            import co.vine.android.util.ViewUtil;
            import java.util.ArrayList;
            import java.util.HashMap;
            import java.util.HashSet;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 42 */    public class SegmentEditorAdapter extends BaseAdapter implements ListAdapter, DragSortWidget.RemoveListener, DragSortWidget.DragListener, DragSortWidget.ShouldAnimateInProvider {
                private static final int NO_LAST_DELETION = -1;
                public boolean hasInitialized;
                private Context mContext;
                private final ArrayList<RecordSegment> mData;
                private final ArrayList<RecordSegment> mDeleted;
                private int mLastDeletedIndex;
                private final int mMargin;
                private final HashMap<RecordSegment, RecordSegment> mOriginals;
                private final int mSize;
                private final DragSortWidget mWidget;

/* 43 */        public SegmentEditorAdapter(ArrayList<RecordSegment> data, Context context, DragSortWidget widget, float density) {
/* 41 */            this.mLastDeletedIndex = -1;
/* 45 */            this.mWidget = widget;
/* 46 */            this.mDeleted = new ArrayList<>();
/* 47 */            this.mData = new ArrayList<>();
/* 48 */            this.mOriginals = new HashMap<>();
                    for (RecordSegment segment : data) {
/* 50 */                if (!segment.removed) {
/* 51 */                    RecordSegment copy = new RecordSegment(segment);
/* 52 */                    this.mData.add(copy);
/* 53 */                    this.mOriginals.put(copy, segment);
                        }
                    }
/* 56 */            this.hasInitialized = true;
/* 57 */            this.mContext = context;
/* 58 */            this.mMargin = (int) (10.0f * density);
/* 59 */            this.mSize = (int) (72.0f * density);
                }

/* 61 */        public SegmentEditorAdapter(SegmentEditorAdapter editorAdapter) {
/* 41 */            this.mLastDeletedIndex = -1;
/* 63 */            this.mWidget = editorAdapter.mWidget;
/* 64 */            this.mDeleted = editorAdapter.mDeleted;
/* 65 */            this.mData = editorAdapter.mData;
/* 66 */            this.mContext = editorAdapter.mContext;
/* 67 */            this.mMargin = editorAdapter.mMargin;
/* 68 */            this.mSize = editorAdapter.mSize;
/* 69 */            this.mLastDeletedIndex = editorAdapter.mLastDeletedIndex;
/* 70 */            this.mOriginals = editorAdapter.mOriginals;
                }

/* 73 */        public ArrayList<RecordSegment> getData() {
/* 74 */            return this.mData;
                }

/* 77 */        public ArrayList<RecordSegment> getDeleted() {
/* 78 */            return this.mDeleted;
                }

                @Override // android.widget.BaseAdapter, android.widget.ListAdapter
/* 82 */        public boolean areAllItemsEnabled() {
/* 83 */            return true;
                }

                @Override // android.widget.BaseAdapter, android.widget.ListAdapter
/* 87 */        public boolean isEnabled(int position) {
/* 88 */            return true;
                }

                @Override // android.widget.BaseAdapter, android.widget.Adapter
/* 92 */        public void registerDataSetObserver(DataSetObserver observer) {
/* 93 */            if (observer != null) {
/* 94 */                super.registerDataSetObserver(observer);
                    }
                }

                @Override // android.widget.BaseAdapter, android.widget.Adapter
/* 99 */        public void unregisterDataSetObserver(DataSetObserver observer) {
/* 100 */           if (observer != null) {
/* 101 */               super.unregisterDataSetObserver(observer);
                    }
                }

                @Override // android.widget.Adapter
/* 106 */       public int getCount() {
/* 107 */           return this.mData.size();
                }

                @Override // android.widget.Adapter
/* 111 */       public Object getItem(int position) {
/* 112 */           return this.mData.get(position);
                }

                @Override // android.widget.Adapter
/* 116 */       public long getItemId(int position) {
/* 117 */           return 0L;
                }

                @Override // android.widget.BaseAdapter, android.widget.Adapter
/* 121 */       public boolean hasStableIds() {
/* 122 */           return false;
                }

                @Override // android.widget.Adapter
/* 127 */       public View getView(int position, View convertView, ViewGroup parent) {
                    ViewGroup.MarginLayoutParams params;
/* 128 */           if (convertView == null) {
/* 129 */               if (this.mContext == null) {
/* 130 */                   this.mContext = parent.getContext();
                        }
/* 132 */               convertView = new View(this.mContext);
/* 133 */               params = new ViewGroup.MarginLayoutParams(this.mSize, this.mSize);
                    } else {
/* 135 */               params = (ViewGroup.MarginLayoutParams) convertView.getLayoutParams();
                    }
/* 137 */           params.bottomMargin = position == getCount() + (-1) ? 0 : this.mMargin;
/* 138 */           convertView.setLayoutParams(params);
/* 140 */           RecordSegment item = this.mData.get(position);
/* 141 */           if (item.drawable == null && !TextUtils.isEmpty(item.getThumbnailPath())) {
/* 142 */               item.drawable = BitmapDrawable.createFromPath(item.getThumbnailPath());
                    }
/* 144 */           ViewUtil.setBackground(convertView, item.drawable);
/* 146 */           return convertView;
                }

                @Override // android.widget.BaseAdapter, android.widget.Adapter
/* 150 */       public int getItemViewType(int position) {
/* 151 */           return 0;
                }

                @Override // android.widget.BaseAdapter, android.widget.Adapter
/* 155 */       public int getViewTypeCount() {
/* 156 */           return 0;
                }

                @Override // android.widget.BaseAdapter, android.widget.Adapter
/* 160 */       public boolean isEmpty() {
                    return this.mData.size() == 0;
                }

                @Override // co.vine.android.dragsort.DragSortWidget.RemoveListener
/* 165 */       public void remove(int which) {
/* 166 */           this.mLastDeletedIndex = which;
/* 167 */           this.mDeleted.add(this.mData.remove(which));
/* 168 */           notifyDataSetChanged();
                }

/* 174 */       public HashSet<RecordSegment> cleanUpForDeletion() {
                    for (RecordSegment segment : this.mData) {
/* 176 */               RecordSegment original = this.mOriginals.get(segment);
/* 177 */               if (original != null && segment.isDataFileBacked()) {
/* 178 */                   original.setFullDataId(segment.getFullDataId());
                        }
                    }
/* 181 */           HashSet<RecordSegment> filesToDelete = new HashSet<>();
                    for (RecordSegment segment2 : this.mDeleted) {
/* 185 */               if (this.mOriginals.get(segment2) == null && segment2.isDataFileBacked()) {
/* 186 */                   filesToDelete.add(segment2);
                        }
                    }
/* 189 */           return filesToDelete;
                }

                @Override // co.vine.android.dragsort.DragSortWidget.DragListener
/* 193 */       public void drag(int from, int to) {
/* 194 */           if (from != to) {
/* 195 */               RecordSegment temp = this.mData.get(from);
/* 196 */               this.mData.set(from, this.mData.get(to));
/* 197 */               this.mData.set(to, temp);
/* 198 */               notifyDataSetChanged();
                    }
                }

/* 202 */       public void reverse() {
/* 203 */           ArrayList<RecordSegment> newData = new ArrayList<>(this.mData.size());
                    for (int i = this.mData.size() - 1; i >= 0; i--) {
/* 205 */               newData.add(this.mData.get(i));
                    }
/* 207 */           this.mData.clear();
/* 208 */           this.mData.addAll(newData);
/* 209 */           notifyDataSetChanged();
                }

/* 212 */       public void undoDelete() {
/* 213 */           if (this.mLastDeletedIndex >= 0) {
/* 214 */               this.mData.add(this.mLastDeletedIndex, this.mDeleted.remove(this.mDeleted.size() - 1));
/* 215 */               this.mLastDeletedIndex = -1;
/* 216 */               notifyDataSetChanged();
                    }
                }

/* 220 */       public void commitDelete() {
/* 221 */           this.mLastDeletedIndex = -1;
                }

/* 224 */       public boolean canUndoDelete() {
                    return this.mLastDeletedIndex != -1;
                }

/* 233 */       public boolean compareTo(ArrayList<RecordSegment> editedSegments) {
/* 234 */           if (editedSegments == null) {
                        return this.mData == null;
                    }
/* 237 */           if (this.mDeleted.size() > 0 || editedSegments.size() != this.mData.size()) {
                        return false;
                    }
/* 240 */           int size = this.mData.size();
                    for (int i = 0; i < size; i++) {
/* 242 */               if (this.mData.get(i) != editedSegments.get(i)) {
                            return false;
                        }
                    }
                    return true;
                }

/* 250 */       public void addSegment(RecordSegment parent, RecordSegment segment, int pos, boolean duplicateAnimation) {
/* 251 */           if (parent != null) {
/* 252 */               this.mOriginals.put(segment, parent);
                    }
/* 254 */           this.mData.add(pos, segment);
/* 255 */           notifyDataSetChanged();
/* 256 */           segment.shouldAnimateIn = duplicateAnimation && this.mWidget.isVisible(pos);
                }

                @Override // co.vine.android.dragsort.DragSortWidget.ShouldAnimateInProvider
/* 260 */       public boolean shouldAnimateIn(int position) {
/* 261 */           RecordSegment segment = this.mData.get(position);
/* 262 */           boolean shouldAnimate = segment.shouldAnimateIn;
/* 263 */           segment.shouldAnimateIn = false;
/* 264 */           return shouldAnimate;
                }

/* 267 */       public long getDuration() {
/* 268 */           int duration = 0;
                    for (RecordSegment s : getData()) {
/* 270 */               duration += s.getDurationMs();
                    }
/* 272 */           return duration;
                }
            }
