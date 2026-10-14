            package co.vine.android;
            
            
     public class SonyRecordingFragment extends RecordingFragment {
                @Override 
         public void onResume() {
             super.onResume();
            ((SonyRecordingActivity) getActivity()).setupSonyOverlay();
                }
            }
