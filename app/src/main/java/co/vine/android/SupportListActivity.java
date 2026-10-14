            package co.vine.android;

            import android.os.Bundle;
            import android.os.Handler;
            import android.support.annotation.NonNull;
            import android.support.v4.app.FragmentActivity;
            import android.view.View;
            import android.widget.AdapterView;
            import android.widget.ListAdapter;
            import android.widget.ListView;
            
            
   public class SupportListActivity extends FragmentActivity {
                protected ListAdapter mAdapter;
                protected ListView mList;
                private Handler mHandler = new Handler();
                private boolean mFinishedStart = false;
                private Runnable mRequestFocus = new RunnableC03001();
                private AdapterView.OnItemClickListener mOnClickListener = new C03012();

                
       class RunnableC03001 implements Runnable {
           RunnableC03001() {
                    }

                    @Override 
           public void run() {
               SupportListActivity.this.mList.focusableViewAvailable(SupportListActivity.this.mList);
                    }
                }

       protected void onListItemClick(ListView l, View v, int position, long id) {
                }

                @Override 
       protected void onRestoreInstanceState(@NonNull Bundle state) {
           ensureList();
           super.onRestoreInstanceState(state);
                }

                @Override 
       protected void onDestroy() {
           this.mHandler.removeCallbacks(this.mRequestFocus);
           super.onDestroy();
                }

                @Override 
       public void onContentChanged() {
           super.onContentChanged();
           View emptyView = findViewById(android.R.id.empty);
           this.mList = (ListView) findViewById(android.R.id.list);
           if (this.mList == null) {
               throw new RuntimeException("Your content must have a ListView whose id attribute is 'android.R.id.list'");
                    }
           if (emptyView != null) {
               this.mList.setEmptyView(emptyView);
                    }
           this.mList.setOnItemClickListener(this.mOnClickListener);
           if (this.mFinishedStart) {
               setListAdapter(this.mAdapter);
                    }
           this.mHandler.post(this.mRequestFocus);
           this.mFinishedStart = true;
                }

       public void setListAdapter(ListAdapter adapter) {
                    synchronized (this) {
                        try {
                   ensureList();
                   this.mAdapter = adapter;
                   this.mList.setAdapter(adapter);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       public void setSelection(int position) {
           this.mList.setSelection(position);
                }

       public int getSelectedItemPosition() {
           return this.mList.getSelectedItemPosition();
                }

       public long getSelectedItemId() {
           return this.mList.getSelectedItemId();
                }

       public ListView getListView() {
           ensureList();
           return this.mList;
                }

       public ListAdapter getListAdapter() {
           return this.mAdapter;
                }

       private void ensureList() {
           if (this.mList == null) {
               setContentView(android.R.layout.list_content);
                    }
                }

                
       class C03012 implements AdapterView.OnItemClickListener {
           C03012() {
                    }

                    @Override 
           public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
               SupportListActivity.this.onListItemClick((ListView) parent, v, position, id);
                    }
                }
            }
