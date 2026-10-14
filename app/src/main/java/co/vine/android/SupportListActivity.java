            package co.vine.android;

            import android.os.Bundle;
            import android.os.Handler;
            import android.support.annotation.NonNull;
            import android.support.v4.app.FragmentActivity;
            import android.view.View;
            import android.widget.AdapterView;
            import android.widget.ListAdapter;
            import android.widget.ListView;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 189 */   public class SupportListActivity extends FragmentActivity {
                protected ListAdapter mAdapter;
                protected ListView mList;
                private Handler mHandler = new Handler();
                private boolean mFinishedStart = false;
                private Runnable mRequestFocus = new RunnableC03001();
                private AdapterView.OnItemClickListener mOnClickListener = new C03012();

                /* JADX INFO: renamed from: co.vine.android.SupportListActivity$1 */
/* 190 */       class RunnableC03001 implements Runnable {
/* 191 */           RunnableC03001() {
                    }

                    @Override // java.lang.Runnable
/* 193 */           public void run() {
/* 194 */               SupportListActivity.this.mList.focusableViewAvailable(SupportListActivity.this.mList);
                    }
                }

/* 209 */       protected void onListItemClick(ListView l, View v, int position, long id) {
                }

                @Override // android.app.Activity
/* 219 */       protected void onRestoreInstanceState(@NonNull Bundle state) {
/* 220 */           ensureList();
/* 221 */           super.onRestoreInstanceState(state);
                }

                @Override // android.support.v4.app.FragmentActivity, android.app.Activity
/* 228 */       protected void onDestroy() {
/* 229 */           this.mHandler.removeCallbacks(this.mRequestFocus);
/* 230 */           super.onDestroy();
                }

                @Override // android.app.Activity, android.view.Window.Callback
/* 240 */       public void onContentChanged() {
/* 241 */           super.onContentChanged();
/* 242 */           View emptyView = findViewById(android.R.id.empty);
/* 243 */           this.mList = (ListView) findViewById(android.R.id.list);
/* 244 */           if (this.mList == null) {
/* 245 */               throw new RuntimeException("Your content must have a ListView whose id attribute is 'android.R.id.list'");
                    }
/* 249 */           if (emptyView != null) {
/* 250 */               this.mList.setEmptyView(emptyView);
                    }
/* 252 */           this.mList.setOnItemClickListener(this.mOnClickListener);
/* 253 */           if (this.mFinishedStart) {
/* 254 */               setListAdapter(this.mAdapter);
                    }
/* 256 */           this.mHandler.post(this.mRequestFocus);
/* 257 */           this.mFinishedStart = true;
                }

/* 263 */       public void setListAdapter(ListAdapter adapter) {
                    synchronized (this) {
                        try {
/* 265 */                   ensureList();
/* 266 */                   this.mAdapter = adapter;
/* 267 */                   this.mList.setAdapter(adapter);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 277 */       public void setSelection(int position) {
/* 278 */           this.mList.setSelection(position);
                }

/* 284 */       public int getSelectedItemPosition() {
/* 285 */           return this.mList.getSelectedItemPosition();
                }

/* 291 */       public long getSelectedItemId() {
/* 292 */           return this.mList.getSelectedItemId();
                }

/* 298 */       public ListView getListView() {
/* 299 */           ensureList();
/* 300 */           return this.mList;
                }

/* 306 */       public ListAdapter getListAdapter() {
/* 307 */           return this.mAdapter;
                }

/* 310 */       private void ensureList() {
/* 311 */           if (this.mList == null) {
/* 314 */               setContentView(android.R.layout.list_content);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.SupportListActivity$2 */
/* 316 */       class C03012 implements AdapterView.OnItemClickListener {
/* 317 */           C03012() {
                    }

                    @Override // android.widget.AdapterView.OnItemClickListener
/* 319 */           public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
/* 320 */               SupportListActivity.this.onListItemClick((ListView) parent, v, position, id);
                    }
                }
            }
