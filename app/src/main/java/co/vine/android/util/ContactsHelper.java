            package co.vine.android.util;

            import android.accounts.Account;
            import android.accounts.AccountManager;
            import android.content.Context;
            import android.database.Cursor;
            import android.net.Uri;
            import android.os.Bundle;
            import android.provider.ContactsContract;
            import android.support.v4.app.Fragment;
            import android.support.v4.app.LoaderManager;
            import android.support.v4.content.CursorLoader;
            import android.support.v4.content.Loader;
            import android.telephony.TelephonyManager;
            import android.text.TextUtils;
            import co.vine.android.service.VineService;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 21 */    public class ContactsHelper implements LoaderManager.LoaderCallbacks<Cursor> {
                private static final String GOOGLE_ACCOUNT = "com.google";
                private static final int LOADER_ID_CONTACTS_DATA = 2;
                private static final int LOADER_ID_CONTACTS_PROFILE = 1;
                private Fragment mFragment;
                private ContactHelperListener mListener;

                public interface ContactHelperListener {
                    void onEmailLoaded(String str);

                    void onNameLoaded(String str);

                    void onPhoneNumberLoaded(String str);
                }

                private interface ProfileQuery {
                    public static final int INDEX_MIME_TYPE = 2;
                    public static final int INDEX_PHONE_NUMBER = 1;
                    public static final String[] PROJECTION = {"_id", "data1", "mimetype"};
                }


/* 71 */        public static void loadContacts(Fragment fragment, ContactHelperListener listener) {
/* 72 */            ContactsHelper helper = new ContactsHelper(fragment, listener);
/* 73 */            helper.init();
                }

/* 81 */        public ContactsHelper(Fragment fragment, ContactHelperListener listener) {
/* 83 */            this.mFragment = fragment;
/* 84 */            this.mListener = listener;
                }

/* 87 */        public void init() {
/* 88 */            if (!BuildUtil.isExplore()) {
/* 89 */                LoaderManager lm = this.mFragment.getLoaderManager();
/* 90 */                lm.initLoader(1, null, this);
/* 91 */                lm.initLoader(2, null, this);
/* 93 */                AccountManager manager = AccountManager.get(this.mFragment.getActivity());
/* 94 */                Account[] accounts = manager.getAccountsByType("com.google");
/* 95 */                if (accounts != null && accounts.length > 0) {
/* 96 */                    this.mListener.onEmailLoaded(accounts[0].name);
                        }
                    }
                }

                @Override // android.support.v4.app.LoaderManager.LoaderCallbacks
/* 102 */       public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    switch (id) {
                        case 1:
/* 105 */                   return new ContactCursorLoader(this.mFragment.getActivity(), ContactsContract.Profile.CONTENT_URI, new String[]{"display_name"}, null, null, null);
                        case 2:
/* 109 */                   return new ContactCursorLoader(this.mFragment.getActivity(), Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI, "data"), ProfileQuery.PROJECTION, "mimetype=?", new String[]{"vnd.android.cursor.item/phone_v2"}, null);
                        default:
                            return null;
                    }
                }

/* 120 */       private static class ContactCursorLoader extends CursorLoader {

/* 124 */           public ContactCursorLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
/* 125 */               super(context, uri, projection, selection, selectionArgs, sortOrder);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.support.v4.content.CursorLoader, android.support.v4.content.AsyncTaskLoader
/* 130 */           public Cursor loadInBackground() {
                        try {
/* 131 */                   return super.loadInBackground();
                        } catch (Throwable e) {
/* 133 */                   CrashUtil.logException(e);
/* 134 */                   return null;
                        }
                    }
                }

/* 140 */       public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                    switch (loader.getId()) {
                        case 1:
/* 143 */                   if (cursor != null && cursor.moveToFirst()) {
/* 144 */                       String name = cursor.getString(cursor.getColumnIndex("display_name"));
/* 146 */                       this.mListener.onNameLoaded(name);
                                break;
                            }
                            break;
                        case 2:
/* 151 */                   String phone = null;
/* 152 */                   if (cursor != null && cursor.moveToFirst()) {
/* 153 */                       String mimeType = cursor.getString(2);
/* 154 */                       if ("vnd.android.cursor.item/phone_v2".equals(mimeType)) {
/* 155 */                           phone = cursor.getString(1);
                                }
                            }
/* 158 */                   if (TextUtils.isEmpty(phone)) {
/* 160 */                       TelephonyManager tm = (TelephonyManager) this.mFragment.getActivity().getSystemService(VineService.EXTRA_PHONE);
/* 162 */                       phone = tm.getLine1Number();
                            }
/* 164 */                   this.mListener.onPhoneNumberLoaded(phone);
                            break;
                    }
                }

                @Override // android.support.v4.app.LoaderManager.LoaderCallbacks
/* 175 */       public void onLoaderReset(Loader<Cursor> loader) {
                }
            }
