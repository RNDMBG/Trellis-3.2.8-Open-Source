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
            
            
    public class UploadsListFragment extends BaseCursorListFragment implements View.OnClickListener, AdapterView.OnItemLongClickListener {
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

        static  String access$1000(UploadsListFragment x0) {
            return x0.mReference;
                }

        static  Messenger access$102(UploadsListFragment x0, Messenger x1) {
            x0.mServiceMessenger = x1;
                    return x1;
                }

        static  String access$1100(UploadsListFragment x0) {
            return x0.mPath;
                }

        static  boolean access$202(UploadsListFragment x0, boolean x1) {
            x0.mBound = x1;
                    return x1;
                }

        static  Messenger access$300(UploadsListFragment x0) {
            return x0.mIncomingMessenger;
                }

        static  void access$400(UploadsListFragment x0, Message x1) {
            x0.sendMessage(x1);
                }

        static  TextView access$500(UploadsListFragment x0) {
            return x0.mStatusMessage;
                }

        static  ProgressView access$600(UploadsListFragment x0) {
            return x0.mProgressView;
                }

        static  int access$700(UploadsListFragment x0) {
            return x0.mProgressViewWidth;
                }

        static  int access$702(UploadsListFragment x0, int x1) {
            x0.mProgressViewWidth = x1;
                    return x1;
                }

        static  void access$800(UploadsListFragment x0) {
            x0.resetFailedUpload();
                }

        static  void access$900(UploadsListFragment x0, Cursor x1, boolean x2) {
            x0.handleRetryPost(x1, x2);
                }


                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.mIncomingMessenger = new Messenger(new UploadProgressHandler(this, null));
            this.mBound = false;
            this.mConnection = new ServiceConnectionC03101();
                }

                
        class ServiceConnectionC03101 implements ServiceConnection {
            ServiceConnectionC03101() {
                    }

                    @Override 
            public void onServiceConnected(ComponentName name, IBinder service) {
                SLog.dWithTag(UploadsListFragment.TAG, "Bind VineUploadService successful!");
                UploadsListFragment.access$102(UploadsListFragment.this, new Messenger(service));
                UploadsListFragment.access$202(UploadsListFragment.this, true);
                Message subscribe = Message.obtain((Handler) null, 1);
                subscribe.replyTo = UploadsListFragment.access$300(UploadsListFragment.this);
                UploadsListFragment.access$400(UploadsListFragment.this, subscribe);
                    }

                    @Override 
            public void onServiceDisconnected(ComponentName name) {
                SLog.dWithTag(UploadsListFragment.TAG, "Connection to VineUploadService lost unexpectedly!");
                UploadsListFragment.access$102(UploadsListFragment.this, null);
                UploadsListFragment.access$202(UploadsListFragment.this, false);
                    }
                }

       private class UploadProgressHandler extends Handler {
           private UploadProgressHandler() {
                    }

            UploadProgressHandler(UploadsListFragment x0, ServiceConnectionC03101 x1) {
               this();
                    }

                    @Override 
           public void handleMessage(Message msg) {
               int what = msg.what;
               Bundle data = (Bundle) msg.obj;
               SLog.dWithTag(UploadsListFragment.TAG, "Message received, what=" + what);
                        switch (what) {
                            case 6:
                       if (UploadsListFragment.access$500(UploadsListFragment.this) != null && UploadsListFragment.access$600(UploadsListFragment.this) != null) {
                           double p = data.getDouble(VineUploadService.EXTRA_UPLOAD_PROGRESS);
                           SLog.dWithTag(UploadsListFragment.TAG, "Upload progress changed to p=" + p);
                           if (UploadsListFragment.access$700(UploadsListFragment.this) == 0) {
                               UploadsListFragment.access$702(UploadsListFragment.this, UploadsListFragment.access$600(UploadsListFragment.this).getMeasuredWidth());
                                    }
                           UploadsListFragment.access$600(UploadsListFragment.this).setProgressRatio((float) ((0.98d * p) / 100.0d));
                           UploadsListFragment.access$500(UploadsListFragment.this).setText(UploadsListFragment.this.getString(C0257R.string.upload_status_uploading));
                                    break;
                                }
                                break;
                            case 7:
                       boolean success = data.getBoolean(VineUploadService.EXTRA_SUCCESS);
                       SLog.dWithTag(UploadsListFragment.TAG, "Post completed, success=" + success);
                       sendMessage(Message.obtain((Handler) null, 2));
                       if (success) {
                           UploadsListFragment.access$500(UploadsListFragment.this).setText(UploadsListFragment.this.getString(C0257R.string.upload_status_succeeded));
                                } else {
                           UploadsListFragment.access$800(UploadsListFragment.this);
                                }
                                break;
                        }
                    }
                }

       private void resetFailedUpload() {
           if (this.mStatusMessage != null && this.mRefreshIcon != null && this.mProgressView != null) {
               this.mStatusMessage.setText(getString(C0257R.string.upload_status_failed));
               this.mRefreshIcon.setVisibility(0);
               this.mProgressView.setProgressRatio(0.0f);
                    }
                }

                @Override 
       public void onActivityCreated(Bundle savedInstanceState) {
           super.onActivityCreated(savedInstanceState);
           FragmentActivity activity = getActivity();
           if (this.mCursorAdapter == null) {
               this.mCursorAdapter = new UploadsAdapter(activity, this.mAppController, 0);
                    }
                    try {
               this.mVersions = RecordSessionVersion.getValuesWithManagers(activity);
                    } catch (IOException e) {
               Util.showCenteredToast(activity, C0257R.string.storage_not_ready);
               activity.finish();
                    }
           View header = LayoutInflater.from(activity).inflate(C0257R.layout.upload_header_view, (ViewGroup) this.mListView, false);
           TextView headerTitle = (TextView) header.findViewById(2131427387);
           headerTitle.setText(C0257R.string.upload_header_title);
           ListView listview = this.mListView;
           listview.addHeaderView(header, null, false);
           listview.setBackgroundColor(getResources().getColor(C0257R.color.uploads_list_bg));
           listview.setDividerHeight(0);
           listview.setOnItemLongClickListener(this);
           listview.setAdapter((ListAdapter) this.mCursorAdapter);
                }

                @Override 
       public void onResume() {
           super.onResume();
           if (this.mCursorAdapter.getCursor() == null) {
               initLoader();
                    }
           if (!this.mBound) {
               SLog.dWithTag(TAG, "Will bind to VineUploadService now");
               Intent intent = new Intent(getActivity(), (Class<?>) VineUploadService.class);
               getActivity().bindService(intent, this.mConnection, 1);
                    }
                }

       public class UploadListPendingRequestHelper extends BaseAdapterFragment.BasePendingRequestHelper {
           public UploadListPendingRequestHelper() {
                        super();
                    }



                    @Override 
           public void handleFailedCaptchaRequest(PendingCaptchaRequest pendingCaptchaRequest) {
                        View currentRow;
               if (UploadsListFragment.this.mListView != null && UploadsListFragment.this.mListView.getChildCount() > UploadsListFragment.this.mListView.getHeaderViewsCount() && (currentRow = UploadsListFragment.this.mListView.getChildAt(UploadsListFragment.this.mListView.getHeaderViewsCount())) != null) {
                   ProgressBar loading = (ProgressBar) currentRow.findViewById(2131427334);
                   if (loading != null) {
                       loading.setVisibility(8);
                            }
                   ImageView retry = (ImageView) currentRow.findViewById(C0257R.id.retry);
                   if (retry != null) {
                       retry.setVisibility(0);
                            }
                        }
                    }

                    @Override 
           public void handleRetryCaptchaRequest(PendingCaptchaRequest pendingCaptchaRequest) {
               Cursor c = UploadsListFragment.this.mCursorAdapter.getCursor();
               if (c.moveToFirst()) {
                   UploadsListFragment.access$900(UploadsListFragment.this, c, true);
                        }
                    }
                }

                @Override 
       protected PendingRequestHelper createPendingRequestHelper() {
           return new UploadListPendingRequestHelper();
                }

                @Override 
       public void onPause() {
           super.onPause();
           if (this.mBound) {
               SLog.dWithTag(TAG, "Will unbind from VineUploadService now");
               sendMessage(Message.obtain((Handler) null, 2));
               getActivity().unbindService(this.mConnection);
               this.mBound = false;
                    }
                }

       private void sendMessage(Message msg) {
                    try {
               this.mServiceMessenger.send(msg);
                    } catch (RemoteException e) {
                    }
                }

                @Override 
       public Loader<Cursor> onCreateLoader(int id, Bundle args) {
           String[] selArgs = {String.valueOf(2), "0"};
           return new CursorLoader(getActivity(), VineUploads.Uploads.CONTENT_URI, VineUploadsDatabaseSQL.UploadsQuery.PROJECTION, "status=? AND is_private=?", selArgs, "_id ASC");
                }

                
                @Override 
       public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
           super.onLoadFinished(loader, cursor);
           hideProgress(3);
           if (isEmpty()) {
               showSadface(true, false);
               getActivity().startService(VineUploadService.getClearNotificationsIntent(getActivity()));
                    } else {
               showSadface(false);
                    }
                }

                @Override 
       public void onListItemClick(ListView l, View v, int position, long id) {
           Cursor cursor = this.mCursorAdapter.getCursor();
           int uploadPosition = position - l.getHeaderViewsCount();
           if (uploadPosition == 0 && cursor.moveToFirst()) {
               this.mPath = cursor.getString(1);
               this.mProgressView = (ProgressView) v.findViewById(C0257R.id.progress_view);
               ImageView retry = (ImageView) v.findViewById(C0257R.id.retry);
               retry.setVisibility(8);
               this.mRefreshIcon = retry;
               TextView statusMessage = (TextView) v.findViewById(C0257R.id.status_message);
               statusMessage.setText(getString(C0257R.string.uploading).toUpperCase());
               this.mStatusMessage = statusMessage;
               handleRetryPost(cursor, false);
                    }
                }

       private void handleRetryPost(Cursor cursor, boolean ignoreCaptcha) {
           this.mPath = cursor.getString(1);
           int status = cursor.getInt(3);
           VineUpload upload = VineUpload.fromCursor(this.mPath, cursor);
           if (status == 0) {
               Intent intent = VineUploadService.getUploadIntent(getActivity(), upload.path, upload.hash, upload.thumbnailPath, upload.reference, false, -1L);
               getActivity().startService(intent);
                    } else if (TextUtils.isEmpty(upload.postInfo)) {
               Intent intent2 = PostActivity.getIntent(getActivity(), upload.path, upload.thumbnailPath, null, true, false);
               getActivity().startActivityForResult(intent2, 0);
                    } else if (TextUtils.isEmpty(upload.captchaUrl) || ignoreCaptcha) {
               Intent intent3 = VineUploadService.getPostIntent(getActivity(), upload, true);
               getActivity().startService(intent3);
                    } else {
               this.mPendingRequestHelper.onCaptchaRequired(getActivity(), this.mPath, 0, null, upload.captchaUrl);
                    }
                }

                @Override 
       public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
           Cursor cursor = this.mCursorAdapter.getCursor();
           if (!cursor.moveToPosition(position - ((ListView) parent).getHeaderViewsCount())) {
                        return false;
                    }
           this.mPath = cursor.getString(1);
           this.mReference = cursor.getString(11);
           PromptDialogSupportFragment promptDialogFragment = PromptDialogSupportFragment.newInstance(1);
           promptDialogFragment.setListener(new C03112());
           promptDialogFragment.setMessage(C0257R.string.delete_confirm_pending);
           promptDialogFragment.setPositiveButton(C0257R.string.yes);
           promptDialogFragment.setNegativeButton(C0257R.string.cancel);
           promptDialogFragment.show(getChildFragmentManager());
                    return true;
                }

                
       class C03112 implements PromptDialogSupportFragment.OnDialogDoneListener {
           C03112() {
                    }

                    @Override 
           public void onDialogDone(DialogInterface dialog, int id, int which) {
                        switch (which) {
                            case -1:
                       Context context = UploadsListFragment.this.getActivity().getApplicationContext();
                                try {
                           if (!TextUtils.isEmpty(UploadsListFragment.access$1000(UploadsListFragment.this))) {
                               RecordSessionVersion.deleteSessionWithName(context, UploadsListFragment.access$1000(UploadsListFragment.this));
                                    }
                                } catch (IOException e) {
                           SLog.m455e("Failed to delete session.");
                                }
                       UploadsListFragment.this.getActivity().startService(VineUploadService.getDiscardIntent(context, UploadsListFragment.access$1100(UploadsListFragment.this)));
                                break;
                        }
                    }
                }

                @Override 
       public void onClick(View view) {
                }

                @Override 
       public void onActivityResult(int requestCode, int resultCode, Intent data) {
           if (!this.mPendingRequestHelper.onActivityResult(requestCode, resultCode, data) && resultCode == 0) {
               resetFailedUpload();
                    }
                }
            }
