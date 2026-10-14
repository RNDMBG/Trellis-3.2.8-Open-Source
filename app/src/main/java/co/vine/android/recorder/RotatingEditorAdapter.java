            package co.vine.android.recorder;

            import android.database.DataSetObserver;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.BaseAdapter;
            import android.widget.ListAdapter;
            import co.vine.android.dragsort.DragSortWidget;
            
            
    public class RotatingEditorAdapter extends BaseAdapter implements ListAdapter, DragSortWidget.RemoveListener, DragSortWidget.DragListener {
                SegmentEditorAdapter mAdapter;
                private int mFirstPosition = 0;

        public RotatingEditorAdapter(SegmentEditorAdapter s) {
            this.mAdapter = s;
                }

                @Override 
        public boolean areAllItemsEnabled() {
            return this.mAdapter.areAllItemsEnabled();
                }

                @Override 
        public boolean isEnabled(int position) {
            return this.mAdapter.isEnabled(getPositionInAdapter(position));
                }

                @Override 
        public void registerDataSetObserver(DataSetObserver observer) {
            if (observer != null) {
                super.registerDataSetObserver(observer);
                    }
            this.mAdapter.registerDataSetObserver(observer);
                }

                @Override 
        public void unregisterDataSetObserver(DataSetObserver observer) {
            if (observer != null) {
                super.unregisterDataSetObserver(observer);
                    }
            this.mAdapter.unregisterDataSetObserver(observer);
                }

                @Override 
        public int getCount() {
            return this.mAdapter.getCount();
                }

                @Override 
        public Object getItem(int position) {
            return this.mAdapter.getItem(getPositionInAdapter(position));
                }

                @Override 
        public long getItemId(int position) {
            return this.mAdapter.getItemId(getPositionInAdapter(position));
                }

                @Override 
        public boolean hasStableIds() {
            return this.mAdapter.hasStableIds();
                }

                @Override 
        public View getView(int position, View convertView, ViewGroup parent) {
            return this.mAdapter.getView(getPositionInAdapter(position), convertView, parent);
                }

                @Override 
        public int getItemViewType(int position) {
            return this.mAdapter.getItemViewType(getPositionInAdapter(position));
                }

                @Override 
        public int getViewTypeCount() {
            return this.mAdapter.getViewTypeCount();
                }

                @Override 
        public boolean isEmpty() {
            return this.mAdapter.isEmpty();
                }

                @Override 
        public void remove(int which) {
            this.mAdapter.remove(getPositionInAdapter(which));
                }

                @Override 
        public void drag(int from, int to) {
            this.mAdapter.drag(getPositionInAdapter(from), getPositionInAdapter(to));
                }

        public void setFirstPosition(int position) {
            this.mFirstPosition = (this.mAdapter.getCount() + position) % this.mAdapter.getCount();
            notifyDataSetChanged();
                }

       public void reset() {
           this.mFirstPosition = 0;
           notifyDataSetChanged();
                }

       public int getPositionInAdapter(int position) {
           return (this.mFirstPosition + position) % this.mAdapter.getCount();
                }
            }
