            package co.vine.android;

            import android.content.res.Resources;
            import android.graphics.Point;
            import android.os.Bundle;
            import android.view.LayoutInflater;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.ImageView;
            import android.widget.RelativeLayout;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    public class DraftCameraPreviewFragment extends BaseFragment implements View.OnClickListener {
                private int mDimen;
                private ImageView mIcon;
                private boolean mMasksAdjusted;
                private View mPreviewView;
                private Resources mRes;
                private final View.OnTouchListener onMaskTouchListener = new ViewOnTouchListenerC02021();

                /* JADX INFO: renamed from: co.vine.android.DraftCameraPreviewFragment$1 */
/* 25 */        class ViewOnTouchListenerC02021 implements View.OnTouchListener {
/* 26 */            ViewOnTouchListenerC02021() {
                    }

                    @Override // android.view.View.OnTouchListener
/* 28 */            public boolean onTouch(View v, MotionEvent event) {
/* 29 */                return true;
                    }
                }

/* 41 */        public void setDimen(int dimen) {
/* 42 */            this.mDimen = dimen;
                }

                @Override // android.support.v4.app.Fragment
/* 46 */        public void onCreate(Bundle savedInstanceState) {
/* 47 */            super.onCreate(savedInstanceState);
/* 48 */            this.mRes = getResources();
                }

                @Override // android.support.v4.app.Fragment
/* 53 */        public View onCreateView(LayoutInflater inflater, ViewGroup root, Bundle savedInstanceState) {
/* 54 */            View rootView = inflater.inflate(C0257R.layout.draft_camera, root, false);
/* 56 */            if (rootView == null) {
/* 57 */                return null;
                    }
/* 60 */            int draftProgressDimen = this.mRes.getDimensionPixelOffset(C0257R.dimen.draft_progress_view_height);
/* 62 */            int progressDimen = this.mRes.getDimensionPixelOffset(C0257R.dimen.progress_view_height);
/* 64 */            RelativeLayout container = (RelativeLayout) rootView.findViewById(C0257R.id.preview_container);
/* 66 */            container.setOnClickListener(this);
/* 68 */            RelativeLayout icon = (RelativeLayout) container.findViewById(C0257R.id.icon_container);
/* 70 */            RelativeLayout.LayoutParams iconParams = (RelativeLayout.LayoutParams) icon.getLayoutParams();
/* 73 */            iconParams.width = this.mDimen;
/* 74 */            iconParams.height = this.mDimen;
/* 75 */            iconParams.topMargin = (draftProgressDimen * 2) + progressDimen;
/* 77 */            icon.setLayoutParams(iconParams);
/* 78 */            this.mPreviewView = icon;
/* 80 */            this.mIcon = (ImageView) icon.findViewById(C0257R.id.preview_icon);
/* 82 */            Point screenSize = ((AbstractRecordingActivity) getActivity()).getScreenSize();
/* 84 */            int dotsHeight = this.mRes.getDimensionPixelOffset(C0257R.dimen.draft_dots_height);
/* 86 */            int actualHeight = screenSize.x + progressDimen + dotsHeight;
/* 88 */            initMasks(rootView, screenSize.x, actualHeight);
/* 90 */            return rootView;
                }

/* 94 */        public synchronized void initMasks(View rootView, int actualWidth, int actualHeight) {
                    try {
                        if (!this.mMasksAdjusted) {
/* 96 */                    this.mMasksAdjusted = true;
/* 97 */                    View topMask = rootView.findViewById(C0257R.id.draft_camera_top_mask);
/* 98 */                    View bottomMask = rootView.findViewById(C0257R.id.draft_camera_bottom_mask);
/* 100 */                   topMask.setOnTouchListener(this.onMaskTouchListener);
/* 101 */                   bottomMask.setOnTouchListener(this.onMaskTouchListener);
/* 102 */                   int desiredHeight = this.mDimen;
/* 103 */                   if (actualHeight > desiredHeight) {
/* 105 */                       int draftProgress = this.mRes.getDimensionPixelOffset(C0257R.dimen.draft_progress_view_height);
/* 107 */                       int progress = this.mRes.getDimensionPixelOffset(C0257R.dimen.progress_view_height);
/* 110 */                       int topHeight = (draftProgress * 3) + progress;
/* 111 */                       int bottomHeight = (actualHeight - topHeight) - this.mDimen;
/* 113 */                       if (bottomHeight > 0) {
/* 114 */                           ViewGroup.LayoutParams topMaskParams = topMask.getLayoutParams();
/* 115 */                           ViewGroup.LayoutParams bottomMaskParams = bottomMask.getLayoutParams();
/* 116 */                           topMaskParams.height = topHeight;
/* 117 */                           bottomMaskParams.height = bottomHeight;
/* 118 */                           topMask.setLayoutParams(topMaskParams);
/* 119 */                           bottomMask.setLayoutParams(bottomMaskParams);
                                }
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // android.view.View.OnClickListener
/* 127 */       public void onClick(View v) {
/* 128 */           if (v.getId() == 2131427524) {
/* 129 */               AbstractRecordingActivity activity = (AbstractRecordingActivity) getActivity();
/* 130 */               activity.cameraPreviewToRecorder(this.mPreviewView, this.mIcon);
                    }
                }

/* 134 */       public View getIconView() {
/* 135 */           return this.mPreviewView;
                }
            }
