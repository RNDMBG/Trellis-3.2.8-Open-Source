            package co.vine.android;

            import android.app.Dialog;
            import android.app.ProgressDialog;
            import android.content.Context;
            import android.content.DialogInterface;
            import android.content.Intent;
            import android.graphics.Bitmap;
            import android.graphics.Rect;
            import android.graphics.RectF;
            import android.graphics.drawable.BitmapDrawable;
            import android.net.Uri;
            import android.os.AsyncTask;
            import android.os.Bundle;
            import android.util.DisplayMetrics;
            import android.util.Log;
            import android.view.View;
            import android.widget.ImageView;
            import android.widget.Toast;
            import co.vine.android.client.AppController;
            import co.vine.android.util.ImageUtils;
            import co.vine.android.widget.CroppableImageView;
            import java.io.ByteArrayOutputStream;
            import java.lang.ref.WeakReference;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 48 */    public class CropActivity extends BaseControllerActionBarActivity implements DialogInterface.OnCancelListener {
                private static final int DIALOG_CROPPING_PROGRESS_ID = 0;
                private static final int DIALOG_LOADING_PROGRESS_ID = 1;
                public static final String EXTRA_CROP_RECT = "cropped_rect";
                public static final String EXTRA_CROP_TYPE = "crop_type";
                public static final String EXTRA_URI = "uri";
                private static final String STATE_DEGREES = "degrees";
                int mBitmapHeight;
                int mBitmapHeightOffset;
                protected boolean mBitmapLoaded;
                int mBitmapWidth;
                int mBitmapWidthOffset;
                protected CropImageTask mCropTask;
                private int mCurrDegrees;
                int mFileToViewScale;
                protected Uri mUri;
                protected CroppableImageView mView;

/* 49 */        protected void onCreate(Bundle savedInstanceState, int layout) {
/* 50 */            super.onCreate(savedInstanceState, layout, false);
/* 51 */            this.mUri = (Uri) getIntent().getParcelableExtra("uri");
/* 52 */            this.mView = (CroppableImageView) findViewById(2131427383);
/* 53 */            this.mView.setVisibility(4);
/* 54 */            if (savedInstanceState != null) {
/* 55 */                this.mCurrDegrees = savedInstanceState.getInt(STATE_DEGREES, 0);
                    } else {
/* 57 */                this.mCurrDegrees = 0;
                    }
/* 59 */            this.mCropTask = (CropImageTask) getLastCustomNonConfigurationInstance();
/* 60 */            if (this.mCropTask == null) {
/* 61 */                new LoadImageTask(this).execute(new Void[0]);
                    } else {
/* 63 */                this.mCropTask.attachActivity(this);
                    }
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 68 */        protected void onSaveInstanceState(Bundle outState) {
/* 69 */            outState.putInt(STATE_DEGREES, this.mCurrDegrees);
                }

                @Override // android.support.v4.app.FragmentActivity
/* 73 */        public Object onRetainCustomNonConfigurationInstance() {
/* 74 */            if (this.mCropTask != null) {
/* 75 */                this.mCropTask.detachActivity();
                    }
/* 77 */            return this.mCropTask;
                }

                @Override // android.app.Activity, android.view.Window.Callback
/* 81 */        public boolean onSearchRequested() {
/* 82 */            return false;
                }

/* 85 */        protected void doCrop() {
/* 86 */            if (this.mBitmapLoaded) {
/* 89 */                if (this.mCropTask != null) {
/* 90 */                    this.mCropTask.detachActivity();
                        }
/* 92 */                this.mCropTask = new CropImageTask(this);
/* 93 */                this.mCropTask.execute(new Void[0]);
                    }
                }

/* 96 */        public void onClickHandler(View view) {
/* 97 */            int id = view.getId();
/* 98 */            if (id == 2131427518) {
/* 99 */                doCrop();
                    } else if (id == 2131427517) {
/* 101 */               setResult(0);
/* 102 */               finish();
                    }
                }

                @Override // android.app.Activity
/* 107 */       protected Dialog onCreateDialog(int id) {
/* 108 */           ProgressDialog dialog = new ProgressDialog(this, C0257R.style.ProgressDialogTheme);
/* 109 */           dialog.setProgressStyle(0);
/* 110 */           dialog.setCanceledOnTouchOutside(false);
/* 111 */           dialog.setOnCancelListener(this);
                    switch (id) {
                        case 0:
/* 114 */                   dialog.setMessage(getString(C0257R.string.cropping_image));
                            return dialog;
                        case 1:
/* 118 */                   dialog.setMessage(getString(C0257R.string.loading_image));
                            return dialog;
                        default:
/* 122 */                   return super.onCreateDialog(id);
                    }
                }

/* 127 */       protected void onBitmapProcessingDone(Bitmap bmp) {
/* 128 */           this.mView.setVisibility(0);
/* 129 */           this.mView.setImageBitmap(bmp);
                }

                @Override // co.vine.android.BaseControllerActionBarActivity, co.vine.android.BaseActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 133 */       protected void onPause() {
/* 134 */           super.onPause();
/* 135 */           removeDialog(0);
/* 136 */           removeDialog(1);
                }

/* 137 */       class LoadImageTask extends AsyncTask<Void, Void, Bitmap> {
                    private final WeakReference<CropActivity> mActivity;



/* 141 */           LoadImageTask(CropActivity activity) {
/* 143 */               this.mActivity = new WeakReference<>(activity);
/* 144 */               CropActivity.this.mBitmapLoaded = false;
                    }

                    @Override // android.os.AsyncTask
/* 148 */           protected void onPreExecute() {
/* 149 */               CropActivity.this.showDialog(1);
                    }

                    /* JADX INFO: renamed from: doInBackground, reason: avoid collision after fix types in other method */
/* 153 */           protected Bitmap doInBackground(Void... voids) {
/* 154 */               DisplayMetrics metrics = CropActivity.this.getResources().getDisplayMetrics();
/* 155 */               ImageUtils.BitmapInfo bmpInfo = ImageUtils.safeDecode(CropActivity.this, CropActivity.this.mUri, metrics.widthPixels, metrics.heightPixels, true);
/* 157 */               if (bmpInfo == null) {
                            return null;
                        }
/* 158 */               int scale = bmpInfo.scale;
/* 159 */               CropActivity.this.mBitmapWidth = bmpInfo.origWidth;
/* 160 */               CropActivity.this.mBitmapHeight = bmpInfo.origHeight;
/* 161 */               CropActivity.this.mBitmapWidthOffset = bmpInfo.origWidth % scale;
/* 162 */               CropActivity.this.mBitmapHeightOffset = bmpInfo.origHeight % scale;
/* 163 */               CropActivity.this.mFileToViewScale = scale;
/* 164 */               return bmpInfo.bitmap;
                    }

                    /* JADX INFO: renamed from: onPostExecute, reason: avoid collision after fix types in other method */
/* 170 */           protected void onPostExecute(Bitmap bmp) {
/* 171 */               CropActivity activity = this.mActivity.get();
/* 172 */               if (activity != null) {
/* 175 */                   if (bmp != null) {
/* 176 */                       activity.mBitmapLoaded = true;
/* 177 */                       activity.onBitmapProcessingDone(bmp);
                            } else {
/* 179 */                       Toast.makeText(CropActivity.this, C0257R.string.load_image_failure, 1).show();
/* 181 */                       CropActivity.this.removeDialog(1);
/* 182 */                       CropActivity.this.setResult(0);
/* 183 */                       CropActivity.this.finish();
                            }
/* 185 */                   CropActivity.this.removeDialog(1);
                        }
                    }
                }

/* 189 */       public void onCropDone(boolean success, Intent intent) {
/* 190 */           if (success) {
/* 191 */               setResult(-1, intent);
                    } else {
/* 193 */               Toast.makeText(this, C0257R.string.cropping_image_failure, 1).show();
/* 194 */               setResult(0);
                    }
/* 196 */           finish();
                }

/* 197 */       private static class CropImageTask extends AsyncTask<Void, Void, Boolean> {
                    CropActivity mActivity;
                    final int mBitmapHeight;
                    final int mBitmapHeightOffset;
                    final int mBitmapWidth;
                    final int mBitmapWidthOffset;
                    final Context mContext;
                    final Rect mCropRect;
                    final int mFileToViewScale;
                    final Intent mIntent = new Intent();
                    final Uri mUri;
                    ImageView mView;
                    Bitmap mViewBitmap;



/* 212 */           CropImageTask(CropActivity activity) {
/* 214 */               this.mActivity = activity;
/* 215 */               this.mContext = activity.getApplicationContext();
/* 216 */               this.mFileToViewScale = activity.mFileToViewScale;
/* 217 */               this.mBitmapWidthOffset = activity.mBitmapWidthOffset;
/* 218 */               this.mBitmapHeightOffset = activity.mBitmapHeightOffset;
/* 219 */               this.mBitmapHeight = activity.mBitmapHeight;
/* 220 */               this.mBitmapWidth = activity.mBitmapWidth;
/* 221 */               this.mUri = activity.mUri;
/* 223 */               this.mView = activity.mView;
/* 225 */               RectF rectF = ((CroppableImageView) this.mView).getCropRect();
/* 226 */               this.mCropRect = new Rect((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
                    }

/* 230 */           public void attachActivity(CropActivity activity) {
/* 231 */               this.mActivity = activity;
                    }

/* 234 */           public void detachActivity() {
/* 235 */               this.mActivity = null;
/* 236 */               this.mView = null;
                    }

                    @Override // android.os.AsyncTask
/* 240 */           protected void onPreExecute() {
/* 241 */               ImageView view = this.mView;
/* 242 */               if (this.mActivity != null && view != null && view.getDrawable() != null) {
/* 243 */                   this.mViewBitmap = ((BitmapDrawable) view.getDrawable()).getBitmap();
/* 244 */                   this.mActivity.showDialog(0);
                        }
                    }

/* 248 */           private Boolean basicCrop() throws Throwable {
                        Bitmap cropped;
/* 249 */               Context context = this.mContext;
/* 250 */               Rect rect = this.mCropRect;
/* 251 */               Bitmap bmp = this.mViewBitmap;
/* 252 */               if (bmp == null) {
/* 253 */                   return Boolean.FALSE;
                        }
/* 255 */               Rect bitmapRect = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
/* 257 */               int height = rect.height();
/* 258 */               int width = rect.width();
/* 259 */               if (width <= 1 || height <= 1) {
/* 260 */                   return Boolean.FALSE;
                        }
/* 264 */               if (bitmapRect.contains(rect) && (cropped = ImageUtils.createBitmap(context, bmp, rect.left, rect.top, rect.width(), rect.height())) != null) {
/* 268 */                   ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 269 */                   cropped.compress(Bitmap.CompressFormat.JPEG, 100, baos);
/* 270 */                   ImageUtils.BitmapInfo bi = ImageUtils.resizeBitmap(context, baos.toByteArray(), 200, 200);
/* 272 */                   if (bi != null && bi.bitmap != null) {
/* 273 */                       long ownerId = AppController.getInstance(context).getActiveId();
/* 274 */                       Uri uri = ImageUtils.writePicToFile(context, bi.bitmap, ownerId);
/* 275 */                       if (uri != null) {
/* 276 */                           int scale = this.mFileToViewScale;
/* 277 */                           rect.set(rect.left * scale, rect.top * scale, (rect.right - this.mBitmapWidthOffset) * scale, (rect.bottom - this.mBitmapHeightOffset) * scale);
/* 281 */                           this.mIntent.putExtra(CropActivity.EXTRA_CROP_RECT, rect);
/* 282 */                           this.mIntent.putExtra("uri", uri);
/* 283 */                           return Boolean.TRUE;
                                }
                            }
                        }
/* 288 */               return Boolean.FALSE;
                    }

                    /* JADX INFO: renamed from: doInBackground, reason: avoid collision after fix types in other method */
/* 292 */           protected Boolean doInBackground(Void... voids) {
/* 293 */               Context context = this.mContext;
                    try {
/* 295 */                  return basicCrop();
                    } catch (Throwable e) {
                        Log.e("CropActivity", "Unable to crop image", e);
                        return Boolean.FALSE;
                    }
            }

                    /* JADX INFO: renamed from: onPostExecute, reason: avoid collision after fix types in other method */
/* 301 */           protected void onPostExecute(Boolean success) {
/* 302 */               if (this.mActivity != null) {
/* 303 */                   this.mActivity.removeDialog(0);
/* 304 */                   this.mActivity.onCropDone(success.booleanValue(), this.mIntent);
                        }
                    }
                }

                @Override // android.content.DialogInterface.OnCancelListener
/* 309 */       public void onCancel(DialogInterface dialog) {
/* 310 */           setResult(0);
/* 311 */           finish();
                }
            }
