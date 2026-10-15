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
            
            
    public class FindFriendsSearchFragment extends BaseControllerFragment implements View.OnClickListener {
                public static final String ARG_LAYOUT = "layout";
                private StyleSpan[] mBold;
                private int mLayoutRes;
                private Intent mSmsIntent;
                private View mView;

                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.mBold = new StyleSpan[]{new StyleSpan(1)};
            Bundle args = getArguments();
            this.mLayoutRes = args.getInt("layout", 0);
                }

                @Override 
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            this.mSmsIntent = new Intent("android.intent.action.VIEW");
            this.mSmsIntent.setType("vnd.android-dir/mms-sms");
            this.mSmsIntent.putExtra("sms_body", ShareUtils.getSmsMessage(getActivity()));
            if (this.mSmsIntent.resolveActivity(getActivity().getPackageManager()) == null) {
                this.mView.findViewById(R.id.btn_friends_txt).setVisibility(8);
                    }
                }

                @Override 
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(this.mLayoutRes, container, false);
            Button search = (Button) v.findViewById(R.id.btn_friends_search);
            search.setText(Util.getSpannedText(this.mBold, getString(R.string.find_friends_search_search), CommonUtil.MARKER_QUOTES));
            search.setOnClickListener(this);
            Button inviteText = (Button) v.findViewById(R.id.btn_friends_txt);
            inviteText.setText(Util.getSpannedText(this.mBold, getString(R.string.find_friends_search_txt), CommonUtil.MARKER_QUOTES));
            inviteText.setOnClickListener(this);
            Button inviteEmail = (Button) v.findViewById(R.id.btn_friends_email);
            inviteEmail.setText(Util.getSpannedText(this.mBold, getString(R.string.find_friends_search_email), CommonUtil.MARKER_QUOTES));
            inviteEmail.setOnClickListener(this);
            this.mView = v;
            return v;
                }

                @Override 
        public void onClick(View view) {
            int id = view.getId();
            if (id == 2131427579) {
                FlurryUtils.trackInvite("sms", "FindFriends");
                startActivity(Intent.createChooser(this.mSmsIntent, getString(R.string.send)));
                return;
                    }
            if (id == 2131427580) {
                FlurryUtils.trackInvite("email", "FindFriends");
                Intent intent = new Intent("android.intent.action.SENDTO", Uri.fromParts("mailto", "", null));
                intent.putExtra("android.intent.extra.SUBJECT", getString(R.string.find_friends_invite_email_subject));
                intent.putExtra("android.intent.extra.TEXT", ShareUtils.getEmailMessage(getActivity()));
                startActivity(Intent.createChooser(intent, getString(R.string.find_friends_email_choose_title)));
                return;
                    }
            if (id == 2131427578) {
                FlurryUtils.trackVisitSearch("FindFriends");
                startActivity(new Intent(getActivity(), (Class<?>) SearchActivity.class));
                    }
                }
            }
