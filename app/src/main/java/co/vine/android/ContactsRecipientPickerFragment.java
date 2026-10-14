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
            
            
    public class ContactsRecipientPickerFragment extends BaseRecipientPickerFragment {
                public static final int FLAG_EMAIL = 1;
                public static final int FLAG_PHONE = 2;
                public static final int LOADER_ID_FILTER_CONTACTS = 1;
                private ContactsPickerAdapter mActiveAdapter;
                private ContactsPickerAdapter mArrayAdapter;
                private HashMap<Long, ContactEntry> mContactEntryMap;
                private ContactsPickerAdapter mFilterAdapter;
                private AsyncTask mFilterTask;
                private final OnContactEntryClickedListener mListener = new C01721();

        static  HashMap access$000(ContactsRecipientPickerFragment x0) {
            return x0.mContactEntryMap;
                }

        static  ContactsPickerAdapter access$100(ContactsRecipientPickerFragment x0) {
            return x0.mActiveAdapter;
                }

        static  void access$200(ContactsRecipientPickerFragment x0, ContactEntry x1, VineRecipient x2, int x3, String x4) {
            x0.onContactEntryClicked(x1, x2, x3, x4);
                }


                @Override 
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            this.mProjection = ContactEntry.sProjection;
            this.mSelection = ContactEntry.sSelection;
            this.mSelArgs = ContactEntry.sSelectionArgs;
            this.mSortOrder = ContactEntry.sSortOrder;
            if (this.mArrayAdapter == null) {
                this.mArrayAdapter = new ContactsPickerAdapter(this);
                    }
            if (this.mFilterAdapter == null) {
                this.mFilterAdapter = new ContactsPickerAdapter(this);
                    }
            this.mContactEntryMap = new HashMap<>();
            this.mListView.setChoiceMode(0);
            initLoader();
                }

                @Override 
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    switch (id) {
                        case 1:
                    String query = args.getString(Vine.UserGroupColumns.QUERY_FRIEND_FILTER, "");
                    Uri contentUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_FILTER_URI, Uri.encode(query));
                    String[] projection = {"_id"};
                    return new CursorLoader(getActivity(), contentUri, projection, null, null, null);
                        default:
                    Uri contentUri2 = ContactEntry.sUri;
                    return new CursorLoader(getActivity(), contentUri2, this.mProjection, this.mSelection, this.mSelArgs, this.mSortOrder);
                    }
                }

                
                @Override 
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            int id = loader.getId();
                    switch (id) {
                        case 1:
                    if (this.mFilterTask != null) {
                        this.mFilterTask.cancel(true);
                            }
                    this.mActiveAdapter = this.mFilterAdapter;
                            this.mFilterTask = new SortRecpientContactsTask(id).execute(new Cursor[]{cursor});
                            break;
                        default:
                    this.mActiveAdapter = this.mArrayAdapter;
                    new SortRecpientContactsTask(id).execute(new Cursor[]{cursor});
                            break;
                    }
                }

       private class SortRecpientContactsTask extends SortContactsTask {
                    private int mId;


           public SortRecpientContactsTask(int id) {
               this.mId = id;
                    }

                    @Override 
           protected ArrayList<ContactEntry> prepareEntries(Cursor cursor) {
                        ArrayList<ContactEntry> entries;
               if (this.mId == 0) {
                   entries = super.prepareEntries(cursor);
                            for (ContactEntry entry : entries) {
                       ContactsRecipientPickerFragment.access$000(ContactsRecipientPickerFragment.this).put(Long.valueOf(entry.contactId), entry);
                            }
                        } else {
                   entries = new ArrayList<>();
                   while (cursor.moveToNext()) {
                       ContactEntry entry2 = (ContactEntry) ContactsRecipientPickerFragment.access$000(ContactsRecipientPickerFragment.this).get(Long.valueOf(cursor.getLong(0)));
                       if (entry2 != null) {
                           entries.add(entry2);
                                }
                            }
                   Collections.sort(entries);
                        }
               return entries;
                    }

                    
           protected void onPostExecute(ArrayList<ContactEntry> entries) {
               ContactsRecipientPickerFragment.access$100(ContactsRecipientPickerFragment.this).clear();
               ContactsRecipientPickerFragment.access$100(ContactsRecipientPickerFragment.this).addAll(entries);
               ContactsRecipientPickerFragment.this.mListView.setAdapter((ListAdapter) ContactsRecipientPickerFragment.access$100(ContactsRecipientPickerFragment.this));
                    }
                }

                
       class C01721 extends OnContactEntryClickedListener {
           C01721() {
                    }

                    @Override 
           protected void onContactEntryClicked(ContactEntry entry, VineRecipient recipient, int typeFlag, String value) {
               ContactsRecipientPickerFragment.access$200(ContactsRecipientPickerFragment.this, entry, recipient, typeFlag, value);
                    }
                }

                @Override 
       public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           if (view.isActivated()) {
               view.setActivated(false);
                    } else if (id > 0) {
               this.mListener.onClick(this, this.mContactEntryMap.get(Long.valueOf(id)));
                    }
                }

       private void onContactEntryClicked(ContactEntry entry, VineRecipient recipient, int typeFlag, String value) {
           if (!isRecipientSelected(recipient)) {
               addRecipient(recipient);
               if (this.mActiveAdapter != null) {
                   this.mActiveAdapter.onRecipientItemAdded(entry, typeFlag, value);
                   return;
                        }
               return;
                    }
           removeRecipient(recipient);
           onRecipientItemRemoved(recipient);
                }

                @Override 
       public void onRecipientItemRemoved(VineRecipient recipient) {
                    ContactEntry entry;
           if (this.mActiveAdapter != null && (entry = this.mContactEntryMap.get(Long.valueOf(recipient.contactId))) != null) {
               int typeFlag = 2;
               if ("email".equals(recipient.key)) {
                   typeFlag = 1;
                        }
               this.mActiveAdapter.onRecipientItemRemoved(entry, typeFlag, recipient.value);
                    }
                }

                @Override 
       protected void onTextChanged(CharSequence s, int start, int before, int count) {
           super.onTextChanged(s, start, before, count);
           String query = s.toString();
           if (!TextUtils.isEmpty(query)) {
               this.mHandler.postDelayed(new FilterRunnable(query), 150L);
                    } else if (this.mArrayAdapter != null) {
               this.mActiveAdapter = this.mArrayAdapter;
               this.mListView.setAdapter((ListAdapter) this.mActiveAdapter);
                    } else {
               getLoaderManager().restartLoader(0, null, this);
                    }
                }

                @Override 
       public void onGetFriendsTypeAheadComplete(String query, ArrayList<VineUser> users) {
                }

       private class FilterRunnable implements Runnable {
                    private String mQuery;

           public FilterRunnable(String query) {
               this.mQuery = query;
                    }

                    @Override 
           public void run() {
               Bundle args = new Bundle();
               args.putString(Vine.UserGroupColumns.QUERY_FRIEND_FILTER, this.mQuery);
               ContactsRecipientPickerFragment.this.getLoaderManager().restartLoader(1, args, ContactsRecipientPickerFragment.this);
                    }
                }

       protected static final class RecipientContactsQuery {
                    public static final int INDEX_CONTACT_ID = 0;
                    public static final int INDEX_DISPLAY_NAME_ALTERNATE = 2;
                    public static final int INDEX_DISPLAY_NAME_PRIMARY = 1;
                    public static final int INDEX_MIMETYPE = 3;
                    public static final int INDEX_VALUE = 4;

           protected RecipientContactsQuery() {
                    }
                }

                @Override 
       protected void handleFocus() {
                }
            }
