            package co.vine.android;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 7 */     public class SonyRecordingFragment extends RecordingFragment {
                @Override // co.vine.android.RecordingFragment, co.vine.android.BaseFragment, android.support.v4.app.Fragment
/* 8 */         public void onResume() {
/* 9 */             super.onResume();
/* 10 */            ((SonyRecordingActivity) getActivity()).setupSonyOverlay();
                }
            }
