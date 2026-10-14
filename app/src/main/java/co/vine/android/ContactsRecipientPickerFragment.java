            package co.vine.android;

            import android.database.Cursor;
            import android.net.Uri;
            import android.os.AsyncTask;
            import android.os.Bundle;
            import android.provider.ContactsContract;
            import android.support.v4.content.CursorLoader;
            import android.support.v4.content.Loader;
            import android.text.TextUtils;
            import android.view.View;
            import android.widget.AdapterView;
            import android.widget.ListAdapter;
            import co.vine.android.api.VineRecipient;
            import co.vine.android.api.VineUser;
            import co.vine.android.provider.Vine;
            import java.util.ArrayList;
            import java.util.Collections;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    public class ContactsRecipientPickerFragment extends BaseRecipientPickerFragment {
                public static final int FLAG_EMAIL = 1;
                public static final int FLAG_PHONE = 2;
                public static final int LOADER_ID_FILTER_CONTACTS = 1;
                private ContactsPickerAdapter mActiveAdapter;
                private ContactsPickerAdapter mArrayAdapter;
                private HashMap<Long, ContactEntry> mContactEntryMap;
                private ContactsPickerAdapter mFilterAdapter;
                private AsyncTask mFilterTask;
                private final OnContactEntryClickedListener mListener = new C01721();

/* 24 */        static /* synthetic */ HashMap access$000(ContactsRecipientPickerFragment x0) {
/* 25 */            return x0.mContactEntryMap;
                }

/* 24 */        static /* synthetic */ ContactsPickerAdapter access$100(ContactsRecipientPickerFragment x0) {
/* 25 */            return x0.mActiveAdapter;
                }

/* 24 */        static /* synthetic */ void access$200(ContactsRecipientPickerFragment x0, ContactEntry x1, VineRecipient x2, int x3, String x4) {
/* 25 */            x0.onContactEntryClicked(x1, x2, x3, x4);
                }


                @Override // co.vine.android.BaseRecipientPickerFragment, android.support.v4.app.Fragment
/* 40 */        public void onActivityCreated(Bundle savedInstanceState) {
/* 41 */            super.onActivityCreated(savedInstanceState);
/* 43 */            this.mProjection = ContactEntry.sProjection;
/* 45 */            this.mSelection = ContactEntry.sSelection;
/* 47 */            this.mSelArgs = ContactEntry.sSelectionArgs;
/* 49 */            this.mSortOrder = ContactEntry.sSortOrder;
/* 51 */            if (this.mArrayAdapter == null) {
/* 52 */                this.mArrayAdapter = new ContactsPickerAdapter(this);
                    }
/* 54 */            if (this.mFilterAdapter == null) {
/* 55 */                this.mFilterAdapter = new ContactsPickerAdapter(this);
                    }
/* 58 */            this.mContactEntryMap = new HashMap<>();
/* 59 */            this.mListView.setChoiceMode(0);
/* 60 */            initLoader();
                }

                @Override // co.vine.android.BaseRecipientPickerFragment, co.vine.android.BaseCursorAdapterFragment, android.support.v4.app.LoaderManager.LoaderCallbacks
/* 64 */        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    switch (id) {
                        case 1:
/* 67 */                    String query = args.getString(Vine.UserGroupColumns.QUERY_FRIEND_FILTER, "");
/* 69 */                    Uri contentUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_FILTER_URI, Uri.encode(query));
/* 71 */                    String[] projection = {"_id"};
/* 72 */                    return new CursorLoader(getActivity(), contentUri, projection, null, null, null);
                        default:
/* 76 */                    Uri contentUri2 = ContactEntry.sUri;
/* 77 */                    return new CursorLoader(getActivity(), contentUri2, this.mProjection, this.mSelection, this.mSelArgs, this.mSortOrder);
                    }
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // co.vine.android.BaseCursorAdapterFragment
/* 84 */        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
/* 85 */            int id = loader.getId();
                    switch (id) {
                        case 1:
/* 88 */                    if (this.mFilterTask != null) {
/* 89 */                        this.mFilterTask.cancel(true);
                            }
/* 91 */                    this.mActiveAdapter = this.mFilterAdapter;
                            this.mFilterTask = new SortRecpientContactsTask(id).execute(new Cursor[]{cursor});
                            break;
                        default:
/* 97 */                    this.mActiveAdapter = this.mArrayAdapter;
/* 98 */                    new SortRecpientContactsTask(id).execute(new Cursor[]{cursor});
                            break;
                    }
                }

/* 102 */       private class SortRecpientContactsTask extends SortContactsTask {
                    private int mId;


/* 107 */           public SortRecpientContactsTask(int id) {
/* 109 */               this.mId = id;
                    }

                    @Override // co.vine.android.SortContactsTask
/* 114 */           protected ArrayList<ContactEntry> prepareEntries(Cursor cursor) {
                        ArrayList<ContactEntry> entries;
/* 115 */               if (this.mId == 0) {
/* 116 */                   entries = super.prepareEntries(cursor);
                            for (ContactEntry entry : entries) {
/* 118 */                       ContactsRecipientPickerFragment.access$000(ContactsRecipientPickerFragment.this).put(Long.valueOf(entry.contactId), entry);
                            }
                        } else {
/* 121 */                   entries = new ArrayList<>();
/* 122 */                   while (cursor.moveToNext()) {
/* 123 */                       ContactEntry entry2 = (ContactEntry) ContactsRecipientPickerFragment.access$000(ContactsRecipientPickerFragment.this).get(Long.valueOf(cursor.getLong(0)));
/* 124 */                       if (entry2 != null) {
/* 125 */                           entries.add(entry2);
                                }
                            }
/* 128 */                   Collections.sort(entries);
                        }
/* 130 */               return entries;
                    }

                    /* JADX INFO: renamed from: onPostExecute, reason: avoid collision after fix types in other method */
/* 134 */           protected void onPostExecute(ArrayList<ContactEntry> entries) {
/* 135 */               ContactsRecipientPickerFragment.access$100(ContactsRecipientPickerFragment.this).clear();
/* 136 */               ContactsRecipientPickerFragment.access$100(ContactsRecipientPickerFragment.this).addAll(entries);
/* 137 */               ContactsRecipientPickerFragment.this.mListView.setAdapter((ListAdapter) ContactsRecipientPickerFragment.access$100(ContactsRecipientPickerFragment.this));
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.ContactsRecipientPickerFragment$1 */
/* 139 */       class C01721 extends OnContactEntryClickedListener {
/* 140 */           C01721() {
                    }

                    @Override // co.vine.android.OnContactEntryClickedListener
/* 143 */           protected void onContactEntryClicked(ContactEntry entry, VineRecipient recipient, int typeFlag, String value) {
/* 144 */               ContactsRecipientPickerFragment.access$200(ContactsRecipientPickerFragment.this, entry, recipient, typeFlag, value);
                    }
                }

                @Override // android.widget.AdapterView.OnItemClickListener
/* 149 */       public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
/* 150 */           if (view.isActivated()) {
/* 151 */               view.setActivated(false);
                    } else if (id > 0) {
/* 153 */               this.mListener.onClick(this, this.mContactEntryMap.get(Long.valueOf(id)));
                    }
                }

/* 157 */       private void onContactEntryClicked(ContactEntry entry, VineRecipient recipient, int typeFlag, String value) {
/* 158 */           if (!isRecipientSelected(recipient)) {
/* 159 */               addRecipient(recipient);
/* 160 */               if (this.mActiveAdapter != null) {
/* 161 */                   this.mActiveAdapter.onRecipientItemAdded(entry, typeFlag, value);
/* 167 */                   return;
                        }
/* 167 */               return;
                    }
/* 164 */           removeRecipient(recipient);
/* 165 */           onRecipientItemRemoved(recipient);
                }

                @Override // co.vine.android.BaseRecipientPickerFragment
/* 170 */       public void onRecipientItemRemoved(VineRecipient recipient) {
                    ContactEntry entry;
/* 171 */           if (this.mActiveAdapter != null && (entry = this.mContactEntryMap.get(Long.valueOf(recipient.contactId))) != null) {
/* 174 */               int typeFlag = 2;
/* 176 */               if ("email".equals(recipient.key)) {
/* 177 */                   typeFlag = 1;
                        }
/* 180 */               this.mActiveAdapter.onRecipientItemRemoved(entry, typeFlag, recipient.value);
                    }
                }

                @Override // co.vine.android.BaseRecipientPickerFragment
/* 186 */       protected void onTextChanged(CharSequence s, int start, int before, int count) {
/* 187 */           super.onTextChanged(s, start, before, count);
/* 188 */           String query = s.toString();
/* 189 */           if (!TextUtils.isEmpty(query)) {
/* 190 */               this.mHandler.postDelayed(new FilterRunnable(query), 150L);
                    } else if (this.mArrayAdapter != null) {
/* 193 */               this.mActiveAdapter = this.mArrayAdapter;
/* 194 */               this.mListView.setAdapter((ListAdapter) this.mActiveAdapter);
                    } else {
/* 196 */               getLoaderManager().restartLoader(0, null, this);
                    }
                }

                @Override // co.vine.android.BaseRecipientPickerFragment
/* 201 */       public void onGetFriendsTypeAheadComplete(String query, ArrayList<VineUser> users) {
                }

/* 205 */       private class FilterRunnable implements Runnable {
                    private String mQuery;

/* 206 */           public FilterRunnable(String query) {
/* 208 */               this.mQuery = query;
                    }

                    @Override // java.lang.Runnable
/* 212 */           public void run() {
/* 213 */               Bundle args = new Bundle();
/* 214 */               args.putString(Vine.UserGroupColumns.QUERY_FRIEND_FILTER, this.mQuery);
/* 215 */               ContactsRecipientPickerFragment.this.getLoaderManager().restartLoader(1, args, ContactsRecipientPickerFragment.this);
                    }
                }

/* 218 */       protected static final class RecipientContactsQuery {
                    public static final int INDEX_CONTACT_ID = 0;
                    public static final int INDEX_DISPLAY_NAME_ALTERNATE = 2;
                    public static final int INDEX_DISPLAY_NAME_PRIMARY = 1;
                    public static final int INDEX_MIMETYPE = 3;
                    public static final int INDEX_VALUE = 4;

/* 219 */           protected RecipientContactsQuery() {
                    }
                }

                @Override // co.vine.android.BaseRecipientPickerFragment
/* 229 */       protected void handleFocus() {
                }
            }
