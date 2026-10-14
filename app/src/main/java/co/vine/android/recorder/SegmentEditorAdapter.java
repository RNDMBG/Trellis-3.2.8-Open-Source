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
            
            
    public class SegmentEditorAdapter extends BaseAdapter implements ListAdapter, DragSortWidget.RemoveListener, DragSortWidget.DragListener, DragSortWidget.ShouldAnimateInProvider {
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

        public SegmentEditorAdapter(ArrayList<RecordSegment> data, Context context, DragSortWidget widget, float density) {
            this.mLastDeletedIndex = -1;
            this.mWidget = widget;
            this.mDeleted = new ArrayList<>();
            this.mData = new ArrayList<>();
            this.mOriginals = new HashMap<>();
                    for (RecordSegment segment : data) {
                if (!segment.removed) {
                    RecordSegment copy = new RecordSegment(segment);
                    this.mData.add(copy);
                    this.mOriginals.put(copy, segment);
                        }
                    }
            this.hasInitialized = true;
            this.mContext = context;
            this.mMargin = (int) (10.0f * density);
            this.mSize = (int) (72.0f * density);
                }

        public SegmentEditorAdapter(SegmentEditorAdapter editorAdapter) {
            this.mLastDeletedIndex = -1;
            this.mWidget = editorAdapter.mWidget;
            this.mDeleted = editorAdapter.mDeleted;
            this.mData = editorAdapter.mData;
            this.mContext = editorAdapter.mContext;
            this.mMargin = editorAdapter.mMargin;
            this.mSize = editorAdapter.mSize;
            this.mLastDeletedIndex = editorAdapter.mLastDeletedIndex;
            this.mOriginals = editorAdapter.mOriginals;
                }

        public ArrayList<RecordSegment> getData() {
            return this.mData;
                }

        public ArrayList<RecordSegment> getDeleted() {
            return this.mDeleted;
                }

                @Override 
        public boolean areAllItemsEnabled() {
            return true;
                }

                @Override 
        public boolean isEnabled(int position) {
            return true;
                }

                @Override 
        public void registerDataSetObserver(DataSetObserver observer) {
            if (observer != null) {
                super.registerDataSetObserver(observer);
                    }
                }

                @Override 
        public void unregisterDataSetObserver(DataSetObserver observer) {
           if (observer != null) {
               super.unregisterDataSetObserver(observer);
                    }
                }

                @Override 
       public int getCount() {
           return this.mData.size();
                }

                @Override 
       public Object getItem(int position) {
           return this.mData.get(position);
                }

                @Override 
       public long getItemId(int position) {
           return 0L;
                }

                @Override 
       public boolean hasStableIds() {
           return false;
                }

                @Override 
       public View getView(int position, View convertView, ViewGroup parent) {
                    ViewGroup.MarginLayoutParams params;
           if (convertView == null) {
               if (this.mContext == null) {
                   this.mContext = parent.getContext();
                        }
               convertView = new View(this.mContext);
               params = new ViewGroup.MarginLayoutParams(this.mSize, this.mSize);
                    } else {
               params = (ViewGroup.MarginLayoutParams) convertView.getLayoutParams();
                    }
           params.bottomMargin = position == getCount() + (-1) ? 0 : this.mMargin;
           convertView.setLayoutParams(params);
           RecordSegment item = this.mData.get(position);
           if (item.drawable == null && !TextUtils.isEmpty(item.getThumbnailPath())) {
               item.drawable = BitmapDrawable.createFromPath(item.getThumbnailPath());
                    }
           ViewUtil.setBackground(convertView, item.drawable);
           return convertView;
                }

                @Override 
       public int getItemViewType(int position) {
           return 0;
                }

                @Override 
       public int getViewTypeCount() {
           return 0;
                }

                @Override 
       public boolean isEmpty() {
                    return this.mData.size() == 0;
                }

                @Override 
       public void remove(int which) {
           this.mLastDeletedIndex = which;
           this.mDeleted.add(this.mData.remove(which));
           notifyDataSetChanged();
                }

       public HashSet<RecordSegment> cleanUpForDeletion() {
                    for (RecordSegment segment : this.mData) {
               RecordSegment original = this.mOriginals.get(segment);
               if (original != null && segment.isDataFileBacked()) {
                   original.setFullDataId(segment.getFullDataId());
                        }
                    }
           HashSet<RecordSegment> filesToDelete = new HashSet<>();
                    for (RecordSegment segment2 : this.mDeleted) {
               if (this.mOriginals.get(segment2) == null && segment2.isDataFileBacked()) {
                   filesToDelete.add(segment2);
                        }
                    }
           return filesToDelete;
                }

                @Override 
       public void drag(int from, int to) {
           if (from != to) {
               RecordSegment temp = this.mData.get(from);
               this.mData.set(from, this.mData.get(to));
               this.mData.set(to, temp);
               notifyDataSetChanged();
                    }
                }

       public void reverse() {
           ArrayList<RecordSegment> newData = new ArrayList<>(this.mData.size());
                    for (int i = this.mData.size() - 1; i >= 0; i--) {
               newData.add(this.mData.get(i));
                    }
           this.mData.clear();
           this.mData.addAll(newData);
           notifyDataSetChanged();
                }

       public void undoDelete() {
           if (this.mLastDeletedIndex >= 0) {
               this.mData.add(this.mLastDeletedIndex, this.mDeleted.remove(this.mDeleted.size() - 1));
               this.mLastDeletedIndex = -1;
               notifyDataSetChanged();
                    }
                }

       public void commitDelete() {
           this.mLastDeletedIndex = -1;
                }

       public boolean canUndoDelete() {
                    return this.mLastDeletedIndex != -1;
                }

       public boolean compareTo(ArrayList<RecordSegment> editedSegments) {
           if (editedSegments == null) {
                        return this.mData == null;
                    }
           if (this.mDeleted.size() > 0 || editedSegments.size() != this.mData.size()) {
                        return false;
                    }
           int size = this.mData.size();
                    for (int i = 0; i < size; i++) {
               if (this.mData.get(i) != editedSegments.get(i)) {
                            return false;
                        }
                    }
                    return true;
                }

       public void addSegment(RecordSegment parent, RecordSegment segment, int pos, boolean duplicateAnimation) {
           if (parent != null) {
               this.mOriginals.put(segment, parent);
                    }
           this.mData.add(pos, segment);
           notifyDataSetChanged();
           segment.shouldAnimateIn = duplicateAnimation && this.mWidget.isVisible(pos);
                }

                @Override 
       public boolean shouldAnimateIn(int position) {
           RecordSegment segment = this.mData.get(position);
           boolean shouldAnimate = segment.shouldAnimateIn;
           segment.shouldAnimateIn = false;
           return shouldAnimate;
                }

       public long getDuration() {
           int duration = 0;
                    for (RecordSegment s : getData()) {
               duration += s.getDurationMs();
                    }
           return duration;
                }
            }
