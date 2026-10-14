            package co.vine.android;

            import android.util.SparseArray;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.ArrayAdapter;
            import android.widget.ImageView;
            import android.widget.TextView;
            import co.vine.android.api.VineRecipient;
            import java.lang.ref.WeakReference;
            import java.util.ArrayList;
            import java.util.Collection;
            import java.util.Set;
            
            
    public class ContactsPickerAdapter extends ArrayAdapter<ContactEntry> {
                private static final int STATE_REGULAR = 2;
                private static final int STATE_SECTIONED = 1;
                private static final int STATE_UNKNOWN = 0;
                private final ContactsRecipientPickerFragment mFragment;
                private int[] mRowHeaderStates;
                private SparseArray<WeakReference<ContactsPickerViewHolder>> mViewHolders;

        public ContactsPickerAdapter(ContactsRecipientPickerFragment fragment) {
            super(fragment.getActivity(), 0);
            this.mFragment = fragment;
            this.mViewHolders = new SparseArray<>();
                }

        public boolean showSeparator(int position, ContactEntry entry) {
            boolean showSeparator = false;
                    switch (this.mRowHeaderStates[position]) {
                        case 1:
                    return true;
                        case 2:
                    return false;
                        default:
                    if (position == 0) {
                        showSeparator = true;
                            } else {
                        ContactEntry lastEntry = getItem(position - 1);
                        if (lastEntry.alternateName != null && entry.alternateName != null) {
                            if (lastEntry.alternateName.toLowerCase().charAt(0) != entry.alternateName.toLowerCase().charAt(0)) {
                                showSeparator = true;
                                    }
                                } else if (lastEntry.displayName.toLowerCase().charAt(0) != entry.displayName.toLowerCase().charAt(0)) {
                            showSeparator = true;
                                }
                            }
                    this.mRowHeaderStates[position] = showSeparator ? 1 : 2;
                    return showSeparator;
                    }
                }

                @Override 
        public View getView(int position, View convertView, ViewGroup parent) {
                    ContactsPickerViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(C0257R.layout.recipient_picker_contacts_row, parent, false);
                holder = new ContactsPickerViewHolder(convertView);
                convertView.setTag(holder);
                    } else {
                holder = (ContactsPickerViewHolder) convertView.getTag();
                    }
            this.mViewHolders.put(position, new WeakReference<>(holder));
            ContactEntry entry = getItem(position);
            boolean showSeparator = showSeparator(position, entry);
            boolean showEmail = (entry.typeFlag & 1) != 0;
            boolean showPhone = (entry.typeFlag & 2) != 0;
            holder.username.setText(entry.displayName);
            holder.emailIndicator.setVisibility(showEmail ? 0 : 8);
            holder.phoneIndicator.setVisibility(showPhone ? 0 : 8);
            holder.contactId = entry.contactId;
            invalidateHolderSelectors(entry, holder);
           if (showSeparator) {
               holder.separator.setVisibility(0);
               if (entry.alternateName != null) {
                   holder.separator.setText(new char[]{entry.alternateName.toUpperCase().charAt(0)}, 0, 1);
                        } else {
                   holder.separator.setText(new char[]{entry.displayName.toUpperCase().charAt(0)}, 0, 1);
                        }
                    } else {
               holder.separator.setVisibility(8);
                    }
           if (position + 1 < getCount()) {
               holder.divider.setVisibility(showSeparator(position + 1, getItem(position + 1)) ? 8 : 0);
                    }
           return convertView;
                }

       private Integer getSelectionState(ContactEntry entry) {
           Set<String> keySet = entry.valueMimeMap.keySet();
           boolean isEmailPicked = false;
           boolean isPhonePicked = false;
                    for (String value : keySet) {
               int typeFlag = entry.valueMimeMap.get(value).intValue();
               if (!isEmailPicked && typeFlag == 1) {
                   isEmailPicked = this.mFragment.isRecipientSelected(VineRecipient.fromEmail(entry.displayName, -1L, value, 0L));
                        }
               if (!isPhonePicked && typeFlag == 2) {
                   isPhonePicked = this.mFragment.isRecipientSelected(VineRecipient.fromPhone(entry.displayName, -1L, value, 0L));
                        }
               if (isEmailPicked && isPhonePicked) {
                            break;
                        }
                    }
           int state = isEmailPicked ? 1 : 0;
           if (isPhonePicked) {
                        state |= 2;
                    }
                    return Integer.valueOf(state);
                }

                @Override 
       public void addAll(Collection<? extends ContactEntry> collection) {
           super.addAll(collection);
           this.mRowHeaderStates = new int[collection.size()];
                }

                @Override 
       public long getItemId(int position) {
           ContactEntry entry = getItem(position);
           if (entry != null) {
               return entry.contactId;
                    }
                    return 0L;
                }

       public void onRecipientItemRemoved(ContactEntry entry, int typeFlag, String value) {
           ArrayList<Integer> toRemove = new ArrayList<>();
           int size = this.mViewHolders.size();
                    for (int i = 0; i < size; i++) {
               WeakReference<ContactsPickerViewHolder> ref = this.mViewHolders.valueAt(i);
               ContactsPickerViewHolder holder = ref.get();
               if (holder == null) {
                   toRemove.add(Integer.valueOf(this.mViewHolders.keyAt(i)));
                        } else if (holder.contactId == entry.contactId) {
                   invalidateHolderSelectors(entry, holder);
                        }
                    }
           int size2 = toRemove.size();
                    for (int i2 = 0; i2 < size2; i2++) {
               this.mViewHolders.remove(toRemove.get(i2).intValue());
                    }
                }

       private void invalidateHolderSelectors(ContactEntry entry, ContactsPickerViewHolder holder) {
           Integer state = getSelectionState(entry);
           int selectionState = state != null ? state.intValue() : 0;
           if ((selectionState & 1) != 0) {
               holder.emailIndicator.setSelected(true);
                    } else {
               holder.emailIndicator.setSelected(false);
                    }
           if ((selectionState & 2) != 0) {
               holder.phoneIndicator.setSelected(true);
                    } else {
               holder.phoneIndicator.setSelected(false);
                    }
                }

       public void onRecipientItemAdded(ContactEntry entry, int typeFlag, String value) {
           ArrayList<Integer> toRemove = new ArrayList<>();
           int size = this.mViewHolders.size();
                    for (int i = 0; i < size; i++) {
               WeakReference<ContactsPickerViewHolder> ref = this.mViewHolders.valueAt(i);
               ContactsPickerViewHolder holder = ref.get();
               if (holder == null) {
                   toRemove.add(Integer.valueOf(this.mViewHolders.keyAt(i)));
                        } else if (holder.contactId == entry.contactId) {
                   if ((typeFlag & 1) != 0) {
                       holder.emailIndicator.setSelected(true);
                            } else if ((typeFlag & 2) != 0) {
                       holder.phoneIndicator.setSelected(true);
                            }
                        }
                    }
           int size2 = toRemove.size();
                    for (int i2 = 0; i2 < size2; i2++) {
               this.mViewHolders.remove(toRemove.get(i2).intValue());
                    }
                }

       public static class ContactsPickerViewHolder {
                    public long contactId;
                    public View divider;
                    public ImageView emailIndicator;
                    public ImageView phoneIndicator;
                    public int position;
                    public TextView separator;
                    public TextView username;

           public ContactsPickerViewHolder(View v) {
               this.emailIndicator = (ImageView) v.findViewById(C0257R.id.email_indicator);
               this.phoneIndicator = (ImageView) v.findViewById(C0257R.id.phone_indicator);
               this.separator = (TextView) v.findViewById(C0257R.id.separator);
               this.username = (TextView) v.findViewById(C0257R.id.contact_name);
               this.divider = v.findViewById(C0257R.id.divider);
                    }
                }
            }
