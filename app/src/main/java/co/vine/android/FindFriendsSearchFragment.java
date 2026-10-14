            package co.vine.android;

            import android.content.Intent;
            import android.net.Uri;
            import android.os.Bundle;
            import android.text.style.StyleSpan;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.Button;
            import co.vine.android.util.CommonUtil;
            import co.vine.android.util.ShareUtils;
            import co.vine.android.util.Util;
            import co.vine.android.util.analytics.FlurryUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 30 */    public class FindFriendsSearchFragment extends BaseControllerFragment implements View.OnClickListener {
                public static final String ARG_LAYOUT = "layout";
                private StyleSpan[] mBold;
                private int mLayoutRes;
                private Intent mSmsIntent;
                private View mView;

                @Override // co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 31 */        public void onCreate(Bundle savedInstanceState) {
/* 32 */            super.onCreate(savedInstanceState);
/* 33 */            this.mBold = new StyleSpan[]{new StyleSpan(1)};
/* 35 */            Bundle args = getArguments();
/* 36 */            this.mLayoutRes = args.getInt("layout", 0);
                }

                @Override // android.support.v4.app.Fragment
/* 40 */        public void onActivityCreated(Bundle savedInstanceState) {
/* 41 */            super.onActivityCreated(savedInstanceState);
/* 42 */            this.mSmsIntent = new Intent("android.intent.action.VIEW");
/* 43 */            this.mSmsIntent.setType("vnd.android-dir/mms-sms");
/* 44 */            this.mSmsIntent.putExtra("sms_body", ShareUtils.getSmsMessage(getActivity()));
/* 45 */            if (this.mSmsIntent.resolveActivity(getActivity().getPackageManager()) == null) {
/* 46 */                this.mView.findViewById(C0257R.id.btn_friends_txt).setVisibility(8);
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 52 */        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/* 53 */            View v = inflater.inflate(this.mLayoutRes, container, false);
/* 55 */            Button search = (Button) v.findViewById(C0257R.id.btn_friends_search);
/* 56 */            search.setText(Util.getSpannedText(this.mBold, getString(C0257R.string.find_friends_search_search), CommonUtil.MARKER_QUOTES));
/* 58 */            search.setOnClickListener(this);
/* 60 */            Button inviteText = (Button) v.findViewById(C0257R.id.btn_friends_txt);
/* 61 */            inviteText.setText(Util.getSpannedText(this.mBold, getString(C0257R.string.find_friends_search_txt), CommonUtil.MARKER_QUOTES));
/* 63 */            inviteText.setOnClickListener(this);
/* 65 */            Button inviteEmail = (Button) v.findViewById(C0257R.id.btn_friends_email);
/* 66 */            inviteEmail.setText(Util.getSpannedText(this.mBold, getString(C0257R.string.find_friends_search_email), CommonUtil.MARKER_QUOTES));
/* 68 */            inviteEmail.setOnClickListener(this);
/* 69 */            this.mView = v;
/* 70 */            return v;
                }

                @Override // android.view.View.OnClickListener
/* 74 */        public void onClick(View view) {
/* 75 */            int id = view.getId();
/* 76 */            if (id == 2131427579) {
/* 77 */                FlurryUtils.trackInvite("sms", "FindFriends");
/* 78 */                startActivity(Intent.createChooser(this.mSmsIntent, getString(C0257R.string.send)));
/* 92 */                return;
                    }
/* 79 */            if (id == 2131427580) {
/* 80 */                FlurryUtils.trackInvite("email", "FindFriends");
/* 81 */                Intent intent = new Intent("android.intent.action.SENDTO", Uri.fromParts("mailto", "", null));
/* 83 */                intent.putExtra("android.intent.extra.SUBJECT", getString(C0257R.string.find_friends_invite_email_subject));
/* 85 */                intent.putExtra("android.intent.extra.TEXT", ShareUtils.getEmailMessage(getActivity()));
/* 86 */                startActivity(Intent.createChooser(intent, getString(C0257R.string.find_friends_email_choose_title)));
/* 92 */                return;
                    }
/* 88 */            if (id == 2131427578) {
/* 89 */                FlurryUtils.trackVisitSearch("FindFriends");
/* 90 */                startActivity(new Intent(getActivity(), (Class<?>) SearchActivity.class));
                    }
                }
            }
