            package co.vine.android;

            import android.app.Activity;
            import android.content.Intent;
            import android.os.Bundle;
            import android.support.annotation.Nullable;
            import android.support.v4.app.Fragment;
            import co.vine.android.util.CrashUtil;
            import com.flurry.android.FlurryAgent;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 25 */    public class BaseFragment extends Fragment {
                public static final String ARG_ACTION = "action";

/* 26 */        public static Bundle prepareArguments(@Nullable Intent intent) {
/* 27 */            if (intent != null) {
/* 29 */                Bundle args = intent.getExtras() != null ? intent.getExtras() : new Bundle();
/* 30 */                args.putString(ARG_ACTION, intent.getAction());
/* 33 */                return args;
                    }
/* 33 */            return null;
                }

/* 37 */        public void setActionBarColor() {
/* 38 */            Activity activity = getActivity();
/* 39 */            if (activity instanceof BaseActionBarActivity) {
/* 40 */                ((BaseActionBarActivity) activity).setActionBarColor();
                    }
                }

/* 44 */        public void setActionBarColor(int color) {
/* 45 */            Activity activity = getActivity();
/* 46 */            if (activity instanceof BaseActionBarActivity) {
/* 47 */                ((BaseActionBarActivity) activity).setActionBarColor(color);
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 52 */        public void onResume() {
/* 53 */            super.onResume();
/* 54 */            CrashUtil.set("Fragment", getClass().getName() + " - " + System.currentTimeMillis());
/* 55 */            FlurryAgent.onPageView();
                }
            }
