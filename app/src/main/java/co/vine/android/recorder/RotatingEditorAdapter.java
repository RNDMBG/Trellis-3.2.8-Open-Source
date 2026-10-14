            package co.vine.android.recorder;

            import android.database.DataSetObserver;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.BaseAdapter;
            import android.widget.ListAdapter;
            import co.vine.android.dragsort.DragSortWidget;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 15 */    public class RotatingEditorAdapter extends BaseAdapter implements ListAdapter, DragSortWidget.RemoveListener, DragSortWidget.DragListener {
                SegmentEditorAdapter mAdapter;
                private int mFirstPosition = 0;

/* 16 */        public RotatingEditorAdapter(SegmentEditorAdapter s) {
/* 18 */            this.mAdapter = s;
                }

                @Override // android.widget.BaseAdapter, android.widget.ListAdapter
/* 22 */        public boolean areAllItemsEnabled() {
/* 23 */            return this.mAdapter.areAllItemsEnabled();
                }

                @Override // android.widget.BaseAdapter, android.widget.ListAdapter
/* 27 */        public boolean isEnabled(int position) {
/* 28 */            return this.mAdapter.isEnabled(getPositionInAdapter(position));
                }

                @Override // android.widget.BaseAdapter, android.widget.Adapter
/* 32 */        public void registerDataSetObserver(DataSetObserver observer) {
/* 33 */            if (observer != null) {
/* 34 */                super.registerDataSetObserver(observer);
                    }
/* 36 */            this.mAdapter.registerDataSetObserver(observer);
                }

                @Override // android.widget.BaseAdapter, android.widget.Adapter
/* 40 */        public void unregisterDataSetObserver(DataSetObserver observer) {
/* 41 */            if (observer != null) {
/* 42 */                super.unregisterDataSetObserver(observer);
                    }
/* 44 */            this.mAdapter.unregisterDataSetObserver(observer);
                }

                @Override // android.widget.Adapter
/* 48 */        public int getCount() {
/* 49 */            return this.mAdapter.getCount();
                }

                @Override // android.widget.Adapter
/* 53 */        public Object getItem(int position) {
/* 54 */            return this.mAdapter.getItem(getPositionInAdapter(position));
                }

                @Override // android.widget.Adapter
/* 58 */        public long getItemId(int position) {
/* 59 */            return this.mAdapter.getItemId(getPositionInAdapter(position));
                }

                @Override // android.widget.BaseAdapter, android.widget.Adapter
/* 63 */        public boolean hasStableIds() {
/* 64 */            return this.mAdapter.hasStableIds();
                }

                @Override // android.widget.Adapter
/* 68 */        public View getView(int position, View convertView, ViewGroup parent) {
/* 69 */            return this.mAdapter.getView(getPositionInAdapter(position), convertView, parent);
                }

                @Override // android.widget.BaseAdapter, android.widget.Adapter
/* 73 */        public int getItemViewType(int position) {
/* 74 */            return this.mAdapter.getItemViewType(getPositionInAdapter(position));
                }

                @Override // android.widget.BaseAdapter, android.widget.Adapter
/* 78 */        public int getViewTypeCount() {
/* 79 */            return this.mAdapter.getViewTypeCount();
                }

                @Override // android.widget.BaseAdapter, android.widget.Adapter
/* 83 */        public boolean isEmpty() {
/* 84 */            return this.mAdapter.isEmpty();
                }

                @Override // co.vine.android.dragsort.DragSortWidget.RemoveListener
/* 88 */        public void remove(int which) {
/* 89 */            this.mAdapter.remove(getPositionInAdapter(which));
                }

                @Override // co.vine.android.dragsort.DragSortWidget.DragListener
/* 93 */        public void drag(int from, int to) {
/* 94 */            this.mAdapter.drag(getPositionInAdapter(from), getPositionInAdapter(to));
                }

/* 97 */        public void setFirstPosition(int position) {
/* 98 */            this.mFirstPosition = (this.mAdapter.getCount() + position) % this.mAdapter.getCount();
/* 99 */            notifyDataSetChanged();
                }

/* 102 */       public void reset() {
/* 103 */           this.mFirstPosition = 0;
/* 104 */           notifyDataSetChanged();
                }

/* 107 */       public int getPositionInAdapter(int position) {
/* 108 */           return (this.mFirstPosition + position) % this.mAdapter.getCount();
                }
            }
