            package co.vine.android;

            import android.content.ComponentName;
            import android.content.Context;
            import android.content.DialogInterface;
            import android.content.Intent;
            import android.content.ServiceConnection;
            import android.database.Cursor;
            import android.os.Bundle;
            import android.os.Handler;
            import android.os.IBinder;
            import android.os.Message;
            import android.os.Messenger;
            import android.os.RemoteException;
            import android.support.v4.app.FragmentActivity;
            import android.support.v4.content.CursorLoader;
            import android.support.v4.content.Loader;
            import android.text.TextUtils;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.AdapterView;
            import android.widget.ImageView;
            import android.widget.ListAdapter;
            import android.widget.ListView;
            import android.widget.ProgressBar;
            import android.widget.TextView;
            import co.vine.android.api.VineUpload;
            import co.vine.android.provider.VineUploads;
            import co.vine.android.provider.VineUploadsDatabaseSQL;
            import co.vine.android.recorder.ProgressView;
            import co.vine.android.recorder.RecordSessionVersion;
            import co.vine.android.service.VineUploadService;
            import co.vine.android.util.Util;
            import co.vine.android.widgets.PromptDialogSupportFragment;
            import com.edisonwang.android.slog.SLog;
            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 52 */    public class UploadsListFragment extends BaseCursorListFragment implements View.OnClickListener, AdapterView.OnItemLongClickListener {
                private static final int DIALOG_CONFIRM_DELETE = 1;
                private static final String TAG = "UploadsListFragment";
                private boolean mBound;
                private ServiceConnection mConnection;
                private Messenger mIncomingMessenger;
                private String mPath;
                private ProgressView mProgressView;
                private int mProgressViewWidth = 0;
                private String mReference;
                private ImageView mRefreshIcon;
                private Messenger mServiceMessenger;
                private TextView mStatusMessage;
                private RecordSessionVersion[] mVersions;

/* 53 */        static /* synthetic */ String access$1000(UploadsListFragment x0) {
/* 54 */            return x0.mReference;
                }

/* 53 */        static /* synthetic */ Messenger access$102(UploadsListFragment x0, Messenger x1) {
/* 54 */            x0.mServiceMessenger = x1;
                    return x1;
                }

/* 53 */        static /* synthetic */ String access$1100(UploadsListFragment x0) {
/* 54 */            return x0.mPath;
                }

/* 53 */        static /* synthetic */ boolean access$202(UploadsListFragment x0, boolean x1) {
/* 54 */            x0.mBound = x1;
                    return x1;
                }

/* 53 */        static /* synthetic */ Messenger access$300(UploadsListFragment x0) {
/* 54 */            return x0.mIncomingMessenger;
                }

/* 53 */        static /* synthetic */ void access$400(UploadsListFragment x0, Message x1) {
/* 54 */            x0.sendMessage(x1);
                }

/* 53 */        static /* synthetic */ TextView access$500(UploadsListFragment x0) {
/* 54 */            return x0.mStatusMessage;
                }

/* 53 */        static /* synthetic */ ProgressView access$600(UploadsListFragment x0) {
/* 54 */            return x0.mProgressView;
                }

/* 53 */        static /* synthetic */ int access$700(UploadsListFragment x0) {
/* 54 */            return x0.mProgressViewWidth;
                }

/* 53 */        static /* synthetic */ int access$702(UploadsListFragment x0, int x1) {
/* 54 */            x0.mProgressViewWidth = x1;
                    return x1;
                }

/* 53 */        static /* synthetic */ void access$800(UploadsListFragment x0) {
/* 54 */            x0.resetFailedUpload();
                }

/* 53 */        static /* synthetic */ void access$900(UploadsListFragment x0, Cursor x1, boolean x2) {
/* 54 */            x0.handleRetryPost(x1, x2);
                }


                @Override // co.vine.android.BaseCursorListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 76 */        public void onCreate(Bundle savedInstanceState) {
/* 77 */            super.onCreate(savedInstanceState);
/* 79 */            this.mIncomingMessenger = new Messenger(new UploadProgressHandler(this, null));
/* 80 */            this.mBound = false;
/* 81 */            this.mConnection = new ServiceConnectionC03101();
                }

                /* JADX INFO: renamed from: co.vine.android.UploadsListFragment$1 */
/* 79 */        class ServiceConnectionC03101 implements ServiceConnection {
/* 80 */            ServiceConnectionC03101() {
                    }

                    @Override // android.content.ServiceConnection
/* 83 */            public void onServiceConnected(ComponentName name, IBinder service) {
/* 84 */                SLog.dWithTag(UploadsListFragment.TAG, "Bind VineUploadService successful!");
/* 85 */                UploadsListFragment.access$102(UploadsListFragment.this, new Messenger(service));
/* 86 */                UploadsListFragment.access$202(UploadsListFragment.this, true);
/* 89 */                Message subscribe = Message.obtain((Handler) null, 1);
/* 90 */                subscribe.replyTo = UploadsListFragment.access$300(UploadsListFragment.this);
/* 91 */                UploadsListFragment.access$400(UploadsListFragment.this, subscribe);
                    }

                    @Override // android.content.ServiceConnection
/* 95 */            public void onServiceDisconnected(ComponentName name) {
/* 96 */                SLog.dWithTag(UploadsListFragment.TAG, "Connection to VineUploadService lost unexpectedly!");
/* 97 */                UploadsListFragment.access$102(UploadsListFragment.this, null);
/* 98 */                UploadsListFragment.access$202(UploadsListFragment.this, false);
                    }
                }

/* 101 */       private class UploadProgressHandler extends Handler {
/* 102 */           private UploadProgressHandler() {
                    }

/* 102 */           /* synthetic */ UploadProgressHandler(UploadsListFragment x0, ServiceConnectionC03101 x1) {
/* 103 */               this();
                    }

                    @Override // android.os.Handler
/* 105 */           public void handleMessage(Message msg) {
/* 106 */               int what = msg.what;
/* 107 */               Bundle data = (Bundle) msg.obj;
/* 108 */               SLog.dWithTag(UploadsListFragment.TAG, "Message received, what=" + what);
                        switch (what) {
                            case 6:
/* 112 */                       if (UploadsListFragment.access$500(UploadsListFragment.this) != null && UploadsListFragment.access$600(UploadsListFragment.this) != null) {
/* 113 */                           double p = data.getDouble(VineUploadService.EXTRA_UPLOAD_PROGRESS);
/* 114 */                           SLog.dWithTag(UploadsListFragment.TAG, "Upload progress changed to p=" + p);
/* 115 */                           if (UploadsListFragment.access$700(UploadsListFragment.this) == 0) {
/* 116 */                               UploadsListFragment.access$702(UploadsListFragment.this, UploadsListFragment.access$600(UploadsListFragment.this).getMeasuredWidth());
                                    }
/* 119 */                           UploadsListFragment.access$600(UploadsListFragment.this).setProgressRatio((float) ((0.98d * p) / 100.0d));
/* 120 */                           UploadsListFragment.access$500(UploadsListFragment.this).setText(UploadsListFragment.this.getString(C0257R.string.upload_status_uploading));
                                    break;
                                }
                                break;
                            case 7:
/* 125 */                       boolean success = data.getBoolean(VineUploadService.EXTRA_SUCCESS);
/* 126 */                       SLog.dWithTag(UploadsListFragment.TAG, "Post completed, success=" + success);
/* 127 */                       sendMessage(Message.obtain((Handler) null, 2));
/* 128 */                       if (success) {
/* 129 */                           UploadsListFragment.access$500(UploadsListFragment.this).setText(UploadsListFragment.this.getString(C0257R.string.upload_status_succeeded));
                                } else {
/* 131 */                           UploadsListFragment.access$800(UploadsListFragment.this);
                                }
                                break;
                        }
                    }
                }

/* 139 */       private void resetFailedUpload() {
/* 140 */           if (this.mStatusMessage != null && this.mRefreshIcon != null && this.mProgressView != null) {
/* 141 */               this.mStatusMessage.setText(getString(C0257R.string.upload_status_failed));
/* 142 */               this.mRefreshIcon.setVisibility(0);
/* 143 */               this.mProgressView.setProgressRatio(0.0f);
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 148 */       public void onActivityCreated(Bundle savedInstanceState) {
/* 149 */           super.onActivityCreated(savedInstanceState);
/* 151 */           FragmentActivity activity = getActivity();
/* 153 */           if (this.mCursorAdapter == null) {
/* 154 */               this.mCursorAdapter = new UploadsAdapter(activity, this.mAppController, 0);
                    }
                    try {
/* 158 */               this.mVersions = RecordSessionVersion.getValuesWithManagers(activity);
                    } catch (IOException e) {
/* 160 */               Util.showCenteredToast(activity, C0257R.string.storage_not_ready);
/* 161 */               activity.finish();
                    }
/* 164 */           View header = LayoutInflater.from(activity).inflate(C0257R.layout.upload_header_view, (ViewGroup) this.mListView, false);
/* 165 */           TextView headerTitle = (TextView) header.findViewById(2131427387);
/* 166 */           headerTitle.setText(C0257R.string.upload_header_title);
/* 168 */           ListView listview = this.mListView;
/* 169 */           listview.addHeaderView(header, null, false);
/* 170 */           listview.setBackgroundColor(getResources().getColor(C0257R.color.uploads_list_bg));
/* 171 */           listview.setDividerHeight(0);
/* 172 */           listview.setOnItemLongClickListener(this);
/* 173 */           listview.setAdapter((ListAdapter) this.mCursorAdapter);
                }

                @Override // co.vine.android.BaseCursorListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, co.vine.android.BaseFragment, android.support.v4.app.Fragment
/* 177 */       public void onResume() {
/* 178 */           super.onResume();
/* 179 */           if (this.mCursorAdapter.getCursor() == null) {
/* 180 */               initLoader();
                    }
/* 183 */           if (!this.mBound) {
/* 184 */               SLog.dWithTag(TAG, "Will bind to VineUploadService now");
/* 185 */               Intent intent = new Intent(getActivity(), (Class<?>) VineUploadService.class);
/* 186 */               getActivity().bindService(intent, this.mConnection, 1);
                    }
                }

/* 188 */       public class UploadListPendingRequestHelper extends BaseAdapterFragment.BasePendingRequestHelper {
/* 189 */           public UploadListPendingRequestHelper() {
                        super();
                    }



                    @Override // co.vine.android.PendingRequestHelper
/* 193 */           public void handleFailedCaptchaRequest(PendingCaptchaRequest pendingCaptchaRequest) {
                        View currentRow;
/* 194 */               if (UploadsListFragment.this.mListView != null && UploadsListFragment.this.mListView.getChildCount() > UploadsListFragment.this.mListView.getHeaderViewsCount() && (currentRow = UploadsListFragment.this.mListView.getChildAt(UploadsListFragment.this.mListView.getHeaderViewsCount())) != null) {
/* 198 */                   ProgressBar loading = (ProgressBar) currentRow.findViewById(2131427334);
/* 200 */                   if (loading != null) {
/* 201 */                       loading.setVisibility(8);
                            }
/* 205 */                   ImageView retry = (ImageView) currentRow.findViewById(C0257R.id.retry);
/* 206 */                   if (retry != null) {
/* 207 */                       retry.setVisibility(0);
                            }
                        }
                    }

                    @Override // co.vine.android.PendingRequestHelper
/* 216 */           public void handleRetryCaptchaRequest(PendingCaptchaRequest pendingCaptchaRequest) {
/* 217 */               Cursor c = UploadsListFragment.this.mCursorAdapter.getCursor();
/* 218 */               if (c.moveToFirst()) {
/* 219 */                   UploadsListFragment.access$900(UploadsListFragment.this, c, true);
                        }
                    }
                }

                @Override // co.vine.android.BaseAdapterFragment
/* 225 */       protected PendingRequestHelper createPendingRequestHelper() {
/* 226 */           return new UploadListPendingRequestHelper();
                }

                @Override // co.vine.android.BaseCursorListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 230 */       public void onPause() {
/* 231 */           super.onPause();
/* 233 */           if (this.mBound) {
/* 234 */               SLog.dWithTag(TAG, "Will unbind from VineUploadService now");
/* 236 */               sendMessage(Message.obtain((Handler) null, 2));
/* 237 */               getActivity().unbindService(this.mConnection);
/* 238 */               this.mBound = false;
                    }
                }

/* 243 */       private void sendMessage(Message msg) {
                    try {
/* 244 */               this.mServiceMessenger.send(msg);
                    } catch (RemoteException e) {
                    }
                }

                @Override // co.vine.android.BaseCursorAdapterFragment, android.support.v4.app.LoaderManager.LoaderCallbacks
/* 250 */       public Loader<Cursor> onCreateLoader(int id, Bundle args) {
/* 252 */           String[] selArgs = {String.valueOf(2), "0"};
/* 253 */           return new CursorLoader(getActivity(), VineUploads.Uploads.CONTENT_URI, VineUploadsDatabaseSQL.UploadsQuery.PROJECTION, "status=? AND is_private=?", selArgs, "_id ASC");
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // co.vine.android.BaseCursorAdapterFragment
/* 258 */       public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
/* 259 */           super.onLoadFinished(loader, cursor);
/* 260 */           hideProgress(3);
/* 261 */           if (isEmpty()) {
/* 262 */               showSadface(true, false);
/* 263 */               getActivity().startService(VineUploadService.getClearNotificationsIntent(getActivity()));
                    } else {
/* 265 */               showSadface(false);
                    }
                }

                @Override // co.vine.android.BaseCursorListFragment
/* 270 */       public void onListItemClick(ListView l, View v, int position, long id) {
/* 271 */           Cursor cursor = this.mCursorAdapter.getCursor();
/* 272 */           int uploadPosition = position - l.getHeaderViewsCount();
/* 274 */           if (uploadPosition == 0 && cursor.moveToFirst()) {
/* 275 */               this.mPath = cursor.getString(1);
/* 276 */               this.mProgressView = (ProgressView) v.findViewById(C0257R.id.progress_view);
/* 278 */               ImageView retry = (ImageView) v.findViewById(C0257R.id.retry);
/* 279 */               retry.setVisibility(8);
/* 280 */               this.mRefreshIcon = retry;
/* 282 */               TextView statusMessage = (TextView) v.findViewById(C0257R.id.status_message);
/* 283 */               statusMessage.setText(getString(C0257R.string.uploading).toUpperCase());
/* 284 */               this.mStatusMessage = statusMessage;
/* 286 */               handleRetryPost(cursor, false);
                    }
                }

/* 290 */       private void handleRetryPost(Cursor cursor, boolean ignoreCaptcha) {
/* 291 */           this.mPath = cursor.getString(1);
/* 294 */           int status = cursor.getInt(3);
/* 295 */           VineUpload upload = VineUpload.fromCursor(this.mPath, cursor);
/* 296 */           if (status == 0) {
/* 297 */               Intent intent = VineUploadService.getUploadIntent(getActivity(), upload.path, upload.hash, upload.thumbnailPath, upload.reference, false, -1L);
/* 299 */               getActivity().startService(intent);
                    } else if (TextUtils.isEmpty(upload.postInfo)) {
/* 302 */               Intent intent2 = PostActivity.getIntent(getActivity(), upload.path, upload.thumbnailPath, null, true, false);
/* 304 */               getActivity().startActivityForResult(intent2, 0);
                    } else if (TextUtils.isEmpty(upload.captchaUrl) || ignoreCaptcha) {
/* 306 */               Intent intent3 = VineUploadService.getPostIntent(getActivity(), upload, true);
/* 307 */               getActivity().startService(intent3);
                    } else {
/* 309 */               this.mPendingRequestHelper.onCaptchaRequired(getActivity(), this.mPath, 0, null, upload.captchaUrl);
                    }
                }

                @Override // android.widget.AdapterView.OnItemLongClickListener
/* 315 */       public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
/* 316 */           Cursor cursor = this.mCursorAdapter.getCursor();
/* 317 */           if (!cursor.moveToPosition(position - ((ListView) parent).getHeaderViewsCount())) {
                        return false;
                    }
/* 318 */           this.mPath = cursor.getString(1);
/* 319 */           this.mReference = cursor.getString(11);
/* 320 */           PromptDialogSupportFragment promptDialogFragment = PromptDialogSupportFragment.newInstance(1);
/* 322 */           promptDialogFragment.setListener(new C03112());
/* 344 */           promptDialogFragment.setMessage(C0257R.string.delete_confirm_pending);
/* 345 */           promptDialogFragment.setPositiveButton(C0257R.string.yes);
/* 346 */           promptDialogFragment.setNegativeButton(C0257R.string.cancel);
/* 347 */           promptDialogFragment.show(getChildFragmentManager());
                    return true;
                }

                /* JADX INFO: renamed from: co.vine.android.UploadsListFragment$2 */
/* 320 */       class C03112 implements PromptDialogSupportFragment.OnDialogDoneListener {
/* 321 */           C03112() {
                    }

                    @Override // co.vine.android.widgets.PromptDialogFragment.OnDialogDoneListener
/* 324 */           public void onDialogDone(DialogInterface dialog, int id, int which) {
                        switch (which) {
                            case -1:
/* 330 */                       Context context = UploadsListFragment.this.getActivity().getApplicationContext();
                                try {
/* 332 */                           if (!TextUtils.isEmpty(UploadsListFragment.access$1000(UploadsListFragment.this))) {
/* 333 */                               RecordSessionVersion.deleteSessionWithName(context, UploadsListFragment.access$1000(UploadsListFragment.this));
                                    }
                                } catch (IOException e) {
/* 336 */                           SLog.m455e("Failed to delete session.");
                                }
/* 338 */                       UploadsListFragment.this.getActivity().startService(VineUploadService.getDiscardIntent(context, UploadsListFragment.access$1100(UploadsListFragment.this)));
                                break;
                        }
                    }
                }

                @Override // android.view.View.OnClickListener
/* 354 */       public void onClick(View view) {
                }

                @Override // co.vine.android.BaseCursorListFragment, android.support.v4.app.Fragment
/* 358 */       public void onActivityResult(int requestCode, int resultCode, Intent data) {
/* 359 */           if (!this.mPendingRequestHelper.onActivityResult(requestCode, resultCode, data) && resultCode == 0) {
/* 361 */               resetFailedUpload();
                    }
                }
            }
