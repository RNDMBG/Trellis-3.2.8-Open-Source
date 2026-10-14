            package co.vine.android;

            import android.app.Activity;
            import android.content.Intent;
            import android.os.Bundle;
            import android.support.annotation.Nullable;
            import android.support.v4.app.Fragment;
            import co.vine.android.util.CrashUtil;
            import com.flurry.android.FlurryAgent;
            
            
    public class BaseFragment extends Fragment {
                public static final String ARG_ACTION = "action";

        public static Bundle prepareArguments(@Nullable Intent intent) {
            if (intent != null) {
                Bundle args = intent.getExtras() != null ? intent.getExtras() : new Bundle();
                args.putString(ARG_ACTION, intent.getAction());
                return args;
                    }
            return null;
                }

        public void setActionBarColor() {
            Activity activity = getActivity();
            if (activity instanceof BaseActionBarActivity) {
                ((BaseActionBarActivity) activity).setActionBarColor();
                    }
                }

        public void setActionBarColor(int color) {
            Activity activity = getActivity();
            if (activity instanceof BaseActionBarActivity) {
                ((BaseActionBarActivity) activity).setActionBarColor(color);
                    }
                }

                @Override 
        public void onResume() {
            super.onResume();
            CrashUtil.set("Fragment", getClass().getName() + " - " + System.currentTimeMillis());
            FlurryAgent.onPageView();
                }
            }
