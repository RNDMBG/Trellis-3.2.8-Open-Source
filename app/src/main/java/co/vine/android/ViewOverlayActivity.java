            package co.vine.android;

            import android.content.Context;
            import android.content.Intent;
            import android.os.Build;
            import android.os.Bundle;
            import android.support.v7.app.ActionBar;
            import android.view.View;
            import co.vine.android.util.Util;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    public class ViewOverlayActivity extends BaseControllerActionBarActivity implements View.OnClickListener {
                private static final String EXTRA_CALL_TO_ACTION_ID = "call_to_action_id";
                private static final String EXTRA_LAYOUT = "layout";
                private static final String EXTRA_SKIP_ID = "skip_id";

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 24 */        public void onCreate(Bundle savedInstanceState) {
                    ActionBar ab;
/* 25 */            Intent intent = getIntent();
/* 26 */            if (intent.getExtras() == null) {
/* 27 */                throw new IllegalStateException("Extras bundle must not be null; use this class's getIntent() helper.");
                    }
/* 30 */            int layoutId = intent.getIntExtra("layout", 0);
/* 31 */            if (layoutId < 1) {
/* 32 */                throw new IllegalStateException("You must provide a layout id when starting this Activity");
                    }
/* 34 */            super.onCreate(savedInstanceState, layoutId, true);
/* 36 */            if (Build.VERSION.SDK_INT <= 14 && (ab = getSupportActionBar()) != null) {
/* 39 */                ab.hide();
                    }
/* 43 */            View cta = findViewById(intent.getIntExtra(EXTRA_CALL_TO_ACTION_ID, 0));
/* 44 */            if (cta != null) {
/* 45 */                cta.setOnClickListener(this);
                    }
/* 48 */            View skip = findViewById(intent.getIntExtra(EXTRA_SKIP_ID, 0));
/* 49 */            if (skip != null) {
/* 50 */                skip.setOnClickListener(this);
                    }
                }

                @Override // android.view.View.OnClickListener
/* 55 */        public void onClick(View v) {
/* 56 */            int id = v.getId();
/* 57 */            if (id == 2131427627) {
/* 58 */                Util.getDefaultSharedPrefs(this).edit().putBoolean(Settings.PREF_GINGERBREAD_NOTIF_DISMISSED, true).apply();
/* 60 */                setResult(-1);
/* 61 */                finish();
                    }
/* 63 */            if (id == 2131427456) {
/* 64 */                setResult(0);
/* 65 */                finish();
                    }
                }

/* 70 */        public static Intent getIntent(Context context, int layout, int callToActionId, int skipButtonId) {
/* 71 */            Intent intent = new Intent(context, (Class<?>) ViewOverlayActivity.class);
/* 72 */            intent.putExtra("layout", layout);
/* 73 */            intent.putExtra(EXTRA_CALL_TO_ACTION_ID, callToActionId);
/* 74 */            intent.putExtra(EXTRA_SKIP_ID, skipButtonId);
/* 75 */            return intent;
                }
            }
