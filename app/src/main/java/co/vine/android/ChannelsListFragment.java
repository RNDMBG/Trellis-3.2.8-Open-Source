            package co.vine.android;

            import android.content.Context;
            import android.content.Intent;
            import android.database.Cursor;
            import android.graphics.Bitmap;
            import android.graphics.Color;
            import android.graphics.PorterDuff;
            import android.graphics.drawable.ColorDrawable;
            import android.graphics.drawable.Drawable;
            import android.os.Bundle;
            import android.support.v4.content.CursorLoader;
            import android.support.v4.content.Loader;
            import android.support.v4.widget.CursorAdapter;
            import android.text.TextUtils;
            import android.view.LayoutInflater;
            import android.view.Menu;
            import android.view.MenuInflater;
            import android.view.MenuItem;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.AbsListView;
            import android.widget.CompoundButton;
            import android.widget.ImageView;
            import android.widget.ListAdapter;
            import android.widget.ListView;
            import android.widget.TextView;
            import co.vine.android.api.VineChannel;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.client.AppController;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.drawable.RecyclableBitmapDrawable;
            import co.vine.android.provider.Vine;
            import co.vine.android.provider.VineDatabaseSQL;
            import co.vine.android.widget.VineToggleButton;
            import java.lang.ref.WeakReference;
            import java.util.ArrayList;
            import java.util.HashMap;
            import java.util.HashSet;
            import java.util.Set;
            
            
    public class ChannelsListFragment extends BaseCursorListFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
                public static final String ARG_SELECTED_CHANNEL = "selected_channel";
                public static final String ARG_SHOW_CHANNEL_FOLLOW = "show_channel_follow";
                private boolean mFetched;
                private VineToggleButton mSelectAllButton;
                private long mSelectedChannel;
                private boolean mShowChannelFollow;
                private HashSet<String> mAllChannelIds = new HashSet<>();
                private HashSet<String> mSelectedChannelIds = new HashSet<>();

        static  HashSet access$000(ChannelsListFragment x0) {
            return x0.mAllChannelIds;
                }

        static  HashSet access$100(ChannelsListFragment x0) {
            return x0.mSelectedChannelIds;
                }

        static  VineToggleButton access$200(ChannelsListFragment x0) {
            return x0.mSelectAllButton;
                }

        static  long access$300(ChannelsListFragment x0) {
            return x0.mSelectedChannel;
                }

        static  boolean access$400(ChannelsListFragment x0) {
            return x0.mShowChannelFollow;
                }


                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.mAppSessionListener = new ChannelsListSessionListener();
            Bundle args = getArguments();
            this.mSelectedChannel = args.getLong("selected_channel");
            this.mShowChannelFollow = args.getBoolean(ARG_SHOW_CHANNEL_FOLLOW);
            setHasOptionsMenu(true);
                }

                @Override 
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            if (this.mCursorAdapter == null) {
                this.mCursorAdapter = new ChannelsAdapter(getActivity(), this.mAppController, 0);
                    }
            ListView listview = this.mListView;
            listview.setBackgroundColor(getResources().getColor(C0257R.color.solid_white));
            listview.setDivider(getResources().getDrawable(C0257R.drawable.rule_horizontal_5));
            if (savedInstanceState == null && this.mShowChannelFollow) {
                View header = LayoutInflater.from(getActivity()).inflate(C0257R.layout.channel_row_view, (ViewGroup) listview, false);
                header.setLayoutParams(new AbsListView.LayoutParams(-1, getResources().getDimensionPixelSize(C0257R.dimen.channel_row_height)));
                ChannelViewHolder tmp = new ChannelViewHolder(header);
                tmp.channelImage.setVisibility(8);
               tmp.followButton.setVisibility(0);
               tmp.channelTitle.setText(C0257R.string.all_featured_posts);
               this.mSelectAllButton = tmp.followButton;
               listview.addHeaderView(header);
                    }
           listview.setAdapter((ListAdapter) this.mCursorAdapter);
                }

                @Override 
       public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
           inflater.inflate(C0257R.menu.next, menu);
                }

                @Override 
       public void onPrepareOptionsMenu(Menu menu) {
           MenuItem next = menu.findItem(C0257R.id.next);
           next.setEnabled(true);
                }

                @Override 
       public boolean onOptionsItemSelected(MenuItem item) {
           return getActivity().onOptionsItemSelected(item);
                }

                @Override 
       public void onResume() {
           super.onResume();
           if (this.mCursorAdapter.getCursor() == null) {
               initLoader();
                    }
           if (!this.mFetched) {
               fetchContent(3);
                    }
                }

                @Override 
       public Loader<Cursor> onCreateLoader(int id, Bundle args) {
           return new CursorLoader(getActivity(), Vine.Channels.CONTENT_URI, VineDatabaseSQL.ChannelsQuery.PROJECTION, null, null, null);
                }

                
                @Override 
       public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
           super.onLoadFinished(loader, cursor);
           if (this.mFetched) {
               hideProgress(3);
               if (this.mCursorAdapter.isEmpty()) {
                   showSadface(true);
                        } else {
                   showSadface(false);
                        }
                    }
                }

                @Override 
       protected void onScrollLastItem(Cursor cursor) {
           if (this.mRefreshable && cursor.getInt(7) == 0) {
               fetchContent(1);
                    }
                }

       public Set<String> getSelectedChannelIds() {
           return this.mSelectedChannelIds;
                }

       private void fetchContent(int fetchType) {
                    int pageType;
           if (!hasPendingRequest(fetchType)) {
                        switch (fetchType) {
                            case 1:
                       pageType = 3;
                                break;
                            case 2:
                       pageType = 2;
                                break;
                            default:
                       pageType = 1;
                                break;
                        }
               addRequest(this.mAppController.fetchChannels(pageType), fetchType);
               this.mFetched = true;
                    }
                }

                @Override 
       public void onListItemClick(ListView l, View v, int position, long id) {
                    Cursor cursor;
           if (!this.mShowChannelFollow && (cursor = this.mCursorAdapter.getCursor()) != null && cursor.moveToPosition(position)) {
               Intent intent = new Intent();
               long channelId = cursor.getLong(1);
               String channel = cursor.getString(2);
               this.mAppController.markChannelLastUsed(channelId);
               intent.putExtra("channel", channel);
               intent.putExtra("channel_id", channelId);
               getActivity().setResult(-1, intent);
               getActivity().finish();
                    }
                }

                @Override 
       public void onClick(View view) {
                }

       public class ChannelsListSessionListener extends AppSessionListener {
           public ChannelsListSessionListener() {
                    }

                    @Override 
           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
               ((ChannelsAdapter) ChannelsListFragment.this.mCursorAdapter).setChannelImages(images);
                    }

                    @Override 
           public void onGetChannelsComplete(String reqId, int statusCode, String reasonPhrase, ArrayList<VineChannel> channels) {
               if (statusCode == 200) {
                            for (VineChannel channel : channels) {
                       ChannelsListFragment.access$000(ChannelsListFragment.this).add(String.valueOf(channel.channelId));
                       ChannelsListFragment.access$100(ChannelsListFragment.this).add(String.valueOf(channel.channelId));
                            }
                   if (ChannelsListFragment.access$200(ChannelsListFragment.this) != null) {
                       ChannelsListFragment.access$200(ChannelsListFragment.this).setChecked(true);
                            }
                        }
                    }
                }

       private class ChannelsAdapter extends CursorAdapter {
                    private final ArrayList<WeakReference<ChannelViewHolder>> mViewHolders;

           public ChannelsAdapter(Context context, AppController appController, int flags) {
               super(context, (Cursor) null, flags);
               ChannelsListFragment.this.mAppController = appController;
               this.mViewHolders = new ArrayList<>();
                    }

                    @Override 
           public View newView(Context context, Cursor cursor, ViewGroup parent) {
               LayoutInflater layoutInflater = LayoutInflater.from(context);
               View v = layoutInflater.inflate(C0257R.layout.channel_row_view, parent, false);
               ChannelViewHolder holder = ChannelsListFragment.this.new ChannelViewHolder(v);
               this.mViewHolders.add(new WeakReference<>(holder));
               v.setTag(holder);
               return v;
                    }

                    @Override 
           public void bindView(View view, Context context, Cursor cursor) {
                        String iconFullUrl;
               ChannelViewHolder holder = (ChannelViewHolder) view.getTag();
               holder.channelTitle.setText(cursor.getString(2));
               long channelId = cursor.getLong(1);
               holder.channelId = String.valueOf(channelId);
                        try {
                   String colorStr = cursor.getString(4);
                   holder.channelColor = Color.parseColor(colorStr);
                        } catch (Exception e) {
                        }
                        if (channelId == ChannelsListFragment.access$300(ChannelsListFragment.this)) {
                   view.setBackground(ChannelsListFragment.this.getResources().getDrawable(C0257R.color.generic_selected));
                        } else {
                   view.setBackground(ChannelsListFragment.this.getResources().getDrawable(C0257R.drawable.bg_generic));
                        }
               if (ChannelsListFragment.this.getResources().getDisplayMetrics().densityDpi < 240) {
                   iconFullUrl = cursor.getString(8);
                        } else {
                   iconFullUrl = cursor.getString(9);
                        }
               if (!TextUtils.isEmpty(iconFullUrl)) {
                   ImageKey key = new ImageKey(iconFullUrl);
                   holder.channelImageKey = key;
                   setChannelImage(holder, ChannelsListFragment.this.mAppController.getPhotoBitmap(key));
                        } else {
                   setChannelImage(holder, null);
                        }
               if (ChannelsListFragment.access$400(ChannelsListFragment.this)) {
                   holder.followButton.setVisibility(0);
                   holder.followButton.setChecked(ChannelsListFragment.access$100(ChannelsListFragment.this).contains(holder.channelId));
                        } else {
                   holder.followButton.setVisibility(8);
                        }
                    }

           public synchronized void setChannelImages(HashMap<ImageKey, UrlImage> images) {
                        try {
                            ArrayList<WeakReference<ChannelViewHolder>> toRemove = new ArrayList<>();
                            for (WeakReference<ChannelViewHolder> ref : this.mViewHolders) {
                       ChannelViewHolder holder = ref.get();
                       if (holder == null) {
                           toRemove.add(ref);
                                } else {
                           UrlImage image = images.get(holder.channelImageKey);
                           if (image != null && image.isValid()) {
                               setChannelImage(holder, image.bitmap);
                                    }
                                }
                            }
                            for (WeakReference<ChannelViewHolder> r : toRemove) {
                       this.mViewHolders.remove(r);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }

           private void setChannelImage(ChannelViewHolder holder, Bitmap bmp) {
               if (bmp != null) {
                   holder.channelImage.setImageDrawable(new RecyclableBitmapDrawable(this.mContext.getResources(), bmp));
                   if (holder.channelColor != 0) {
                       holder.channelImage.setBackground(new ColorDrawable(holder.channelColor));
                       return;
                            }
                   return;
                        }
               holder.channelImage.setImageBitmap(null);
               holder.channelImage.setBackgroundColor(this.mContext.getResources().getColor(C0257R.color.solid_light_gray));
                    }
                }

       private class ChannelViewHolder {
                    public int channelColor;
                    public String channelId;
                    public ImageView channelImage;
                    public ImageKey channelImageKey;
                    public TextView channelTitle;
                    public VineToggleButton followButton;
                    public ViewGroup rootLayout;

           public ChannelViewHolder(View view) {
               this.channelImage = (ImageView) view.findViewById(C0257R.id.channel_image);
               this.channelTitle = (TextView) view.findViewById(C0257R.id.channel);
               this.followButton = (VineToggleButton) view.findViewById(C0257R.id.follow_button);
               this.followButton.setOnCheckedChangeListener(ChannelsListFragment.this);
               this.rootLayout = (ViewGroup) view.findViewById(C0257R.id.root_layout);
               if (ChannelsListFragment.access$400(ChannelsListFragment.this)) {
                   this.rootLayout.setOnClickListener(new ViewOnClickListenerC01611(ChannelsListFragment.this));
                        }
                    }

                    
           class ViewOnClickListenerC01611 implements View.OnClickListener {
                        final  ChannelsListFragment val$this$0;

               ViewOnClickListenerC01611(ChannelsListFragment channelsListFragment) {
                            this.val$this$0 = channelsListFragment;
                        }

                        @Override 
               public void onClick(View view) {
                   ChannelViewHolder.this.followButton.performClick();
                        }
                    }
                }

                @Override 
       public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int color;
           Object holder = ((View) buttonView.getParent()).getTag();
           if (this.mSelectAllButton == buttonView) {
               if (isChecked) {
                   this.mSelectedChannelIds.addAll(this.mAllChannelIds);
                        } else {
                   this.mSelectedChannelIds.removeAll(this.mAllChannelIds);
                        }
               this.mCursorAdapter.notifyDataSetChanged();
                    } else if (holder instanceof ChannelViewHolder) {
               String channelId = ((ChannelViewHolder) holder).channelId;
               if (isChecked) {
                   this.mSelectedChannelIds.add(channelId);
                        } else {
                   this.mSelectedChannelIds.remove(channelId);
                   if (this.mSelectAllButton != null) {
                       this.mSelectAllButton.setCheckedWithoutEvent(false);
                            }
                        }
                    }
           Drawable bg = buttonView.getBackground();
           if (bg != null && isChecked) {
               if (holder instanceof ChannelViewHolder) {
                   color = ((ChannelViewHolder) holder).channelColor;
                        } else {
                   color = getResources().getColor(C0257R.color.vine_green);
                        }
               bg.setColorFilter(color, PorterDuff.Mode.SRC_IN);
                    }
                }
            }
