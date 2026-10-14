            package co.vine.android;

            import android.content.Context;
            import android.content.Intent;
            import android.os.Bundle;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 12 */    public class FavoritePeopleActivity extends BaseControllerActionBarActivity {
                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 13 */        protected void onCreate(Bundle savedInstanceState) {
/* 14 */            super.onCreate(savedInstanceState, C0257R.layout.user_list, true);
/* 16 */            Intent intent = getIntent();
/* 17 */            setupActionBar((Boolean) true, (Boolean) true, C0257R.string.favorite_people, (Boolean) true, (Boolean) true);
/* 19 */            Bundle extras = getIntent().getExtras();
/* 20 */            if (extras != null && extras.getInt("color", 0) != 0) {
/* 21 */                setActionBarColor(extras.getInt("color"));
                    }
/* 24 */            if (savedInstanceState == null) {
/* 25 */                FavoritePeopleFragment fragment = new FavoritePeopleFragment();
/* 26 */                Bundle b = BaseArrayListFragment.prepareArguments(intent, false);
/* 27 */                b.putBoolean("refresh", true);
/* 28 */                fragment.setArguments(b);
/* 29 */                getSupportFragmentManager().beginTransaction().add(C0257R.id.fragment_container, fragment).commit();
                    }
                }

/* 36 */        public static void start(Context context, int actionBarColor) {
/* 37 */            Intent intent = new Intent(context, (Class<?>) FavoritePeopleActivity.class);
/* 38 */            intent.putExtra("color", actionBarColor);
/* 39 */            context.startActivity(intent);
                }
            }
