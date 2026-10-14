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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 53 */    public class ChannelsListFragment extends BaseCursorListFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
                public static final String ARG_SELECTED_CHANNEL = "selected_channel";
                public static final String ARG_SHOW_CHANNEL_FOLLOW = "show_channel_follow";
                private boolean mFetched;
                private VineToggleButton mSelectAllButton;
                private long mSelectedChannel;
                private boolean mShowChannelFollow;
                private HashSet<String> mAllChannelIds = new HashSet<>();
                private HashSet<String> mSelectedChannelIds = new HashSet<>();

/* 54 */        static /* synthetic */ HashSet access$000(ChannelsListFragment x0) {
/* 55 */            return x0.mAllChannelIds;
                }

/* 54 */        static /* synthetic */ HashSet access$100(ChannelsListFragment x0) {
/* 55 */            return x0.mSelectedChannelIds;
                }

/* 54 */        static /* synthetic */ VineToggleButton access$200(ChannelsListFragment x0) {
/* 55 */            return x0.mSelectAllButton;
                }

/* 54 */        static /* synthetic */ long access$300(ChannelsListFragment x0) {
/* 55 */            return x0.mSelectedChannel;
                }

/* 54 */        static /* synthetic */ boolean access$400(ChannelsListFragment x0) {
/* 55 */            return x0.mShowChannelFollow;
                }


                @Override // co.vine.android.BaseCursorListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 69 */        public void onCreate(Bundle savedInstanceState) {
/* 70 */            super.onCreate(savedInstanceState);
/* 71 */            this.mAppSessionListener = new ChannelsListSessionListener();
/* 73 */            Bundle args = getArguments();
/* 74 */            this.mSelectedChannel = args.getLong("selected_channel");
/* 75 */            this.mShowChannelFollow = args.getBoolean(ARG_SHOW_CHANNEL_FOLLOW);
/* 77 */            setHasOptionsMenu(true);
                }

                @Override // android.support.v4.app.Fragment
/* 81 */        public void onActivityCreated(Bundle savedInstanceState) {
/* 82 */            super.onActivityCreated(savedInstanceState);
/* 84 */            if (this.mCursorAdapter == null) {
/* 85 */                this.mCursorAdapter = new ChannelsAdapter(getActivity(), this.mAppController, 0);
                    }
/* 88 */            ListView listview = this.mListView;
/* 89 */            listview.setBackgroundColor(getResources().getColor(C0257R.color.solid_white));
/* 90 */            listview.setDivider(getResources().getDrawable(C0257R.drawable.rule_horizontal_5));
/* 92 */            if (savedInstanceState == null && this.mShowChannelFollow) {
/* 93 */                View header = LayoutInflater.from(getActivity()).inflate(C0257R.layout.channel_row_view, (ViewGroup) listview, false);
/* 95 */                header.setLayoutParams(new AbsListView.LayoutParams(-1, getResources().getDimensionPixelSize(C0257R.dimen.channel_row_height)));
/* 98 */                ChannelViewHolder tmp = new ChannelViewHolder(header);
/* 99 */                tmp.channelImage.setVisibility(8);
/* 100 */               tmp.followButton.setVisibility(0);
/* 101 */               tmp.channelTitle.setText(C0257R.string.all_featured_posts);
/* 102 */               this.mSelectAllButton = tmp.followButton;
/* 103 */               listview.addHeaderView(header);
                    }
/* 105 */           listview.setAdapter((ListAdapter) this.mCursorAdapter);
                }

                @Override // android.support.v4.app.Fragment
/* 109 */       public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
/* 110 */           inflater.inflate(C0257R.menu.next, menu);
                }

                @Override // android.support.v4.app.Fragment
/* 114 */       public void onPrepareOptionsMenu(Menu menu) {
/* 115 */           MenuItem next = menu.findItem(C0257R.id.next);
/* 116 */           next.setEnabled(true);
                }

                @Override // android.support.v4.app.Fragment
/* 120 */       public boolean onOptionsItemSelected(MenuItem item) {
/* 121 */           return getActivity().onOptionsItemSelected(item);
                }

                @Override // co.vine.android.BaseCursorListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, co.vine.android.BaseFragment, android.support.v4.app.Fragment
/* 125 */       public void onResume() {
/* 126 */           super.onResume();
/* 127 */           if (this.mCursorAdapter.getCursor() == null) {
/* 128 */               initLoader();
                    }
/* 130 */           if (!this.mFetched) {
/* 131 */               fetchContent(3);
                    }
                }

                @Override // co.vine.android.BaseCursorAdapterFragment, android.support.v4.app.LoaderManager.LoaderCallbacks
/* 136 */       public Loader<Cursor> onCreateLoader(int id, Bundle args) {
/* 137 */           return new CursorLoader(getActivity(), Vine.Channels.CONTENT_URI, VineDatabaseSQL.ChannelsQuery.PROJECTION, null, null, null);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // co.vine.android.BaseCursorAdapterFragment
/* 142 */       public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
/* 143 */           super.onLoadFinished(loader, cursor);
/* 144 */           if (this.mFetched) {
/* 145 */               hideProgress(3);
/* 146 */               if (this.mCursorAdapter.isEmpty()) {
/* 147 */                   showSadface(true);
                        } else {
/* 149 */                   showSadface(false);
                        }
                    }
                }

                @Override // co.vine.android.BaseCursorListFragment
/* 155 */       protected void onScrollLastItem(Cursor cursor) {
/* 156 */           if (this.mRefreshable && cursor.getInt(7) == 0) {
/* 157 */               fetchContent(1);
                    }
                }

/* 161 */       public Set<String> getSelectedChannelIds() {
/* 162 */           return this.mSelectedChannelIds;
                }

/* 165 */       private void fetchContent(int fetchType) {
                    int pageType;
/* 166 */           if (!hasPendingRequest(fetchType)) {
                        switch (fetchType) {
                            case 1:
/* 177 */                       pageType = 3;
                                break;
                            case 2:
/* 173 */                       pageType = 2;
                                break;
                            default:
/* 182 */                       pageType = 1;
                                break;
                        }
/* 186 */               addRequest(this.mAppController.fetchChannels(pageType), fetchType);
/* 187 */               this.mFetched = true;
                    }
                }

                @Override // co.vine.android.BaseCursorListFragment
/* 191 */       public void onListItemClick(ListView l, View v, int position, long id) {
                    Cursor cursor;
/* 192 */           if (!this.mShowChannelFollow && (cursor = this.mCursorAdapter.getCursor()) != null && cursor.moveToPosition(position)) {
/* 198 */               Intent intent = new Intent();
/* 199 */               long channelId = cursor.getLong(1);
/* 200 */               String channel = cursor.getString(2);
/* 202 */               this.mAppController.markChannelLastUsed(channelId);
/* 203 */               intent.putExtra("channel", channel);
/* 204 */               intent.putExtra("channel_id", channelId);
/* 205 */               getActivity().setResult(-1, intent);
/* 206 */               getActivity().finish();
                    }
                }

                @Override // android.view.View.OnClickListener
/* 211 */       public void onClick(View view) {
                }

/* 212 */       public class ChannelsListSessionListener extends AppSessionListener {
/* 213 */           public ChannelsListSessionListener() {
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 216 */           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
/* 217 */               ((ChannelsAdapter) ChannelsListFragment.this.mCursorAdapter).setChannelImages(images);
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 221 */           public void onGetChannelsComplete(String reqId, int statusCode, String reasonPhrase, ArrayList<VineChannel> channels) {
/* 222 */               if (statusCode == 200) {
                            for (VineChannel channel : channels) {
/* 224 */                       ChannelsListFragment.access$000(ChannelsListFragment.this).add(String.valueOf(channel.channelId));
/* 225 */                       ChannelsListFragment.access$100(ChannelsListFragment.this).add(String.valueOf(channel.channelId));
                            }
/* 228 */                   if (ChannelsListFragment.access$200(ChannelsListFragment.this) != null) {
/* 229 */                       ChannelsListFragment.access$200(ChannelsListFragment.this).setChecked(true);
                            }
                        }
                    }
                }

/* 236 */       private class ChannelsAdapter extends CursorAdapter {
                    private final ArrayList<WeakReference<ChannelViewHolder>> mViewHolders;

/* 237 */           public ChannelsAdapter(Context context, AppController appController, int flags) {
/* 239 */               super(context, (Cursor) null, flags);
/* 240 */               ChannelsListFragment.this.mAppController = appController;
/* 241 */               this.mViewHolders = new ArrayList<>();
                    }

                    @Override // android.support.v4.widget.CursorAdapter
/* 245 */           public View newView(Context context, Cursor cursor, ViewGroup parent) {
/* 246 */               LayoutInflater layoutInflater = LayoutInflater.from(context);
/* 247 */               View v = layoutInflater.inflate(C0257R.layout.channel_row_view, parent, false);
/* 249 */               ChannelViewHolder holder = ChannelsListFragment.this.new ChannelViewHolder(v);
/* 251 */               this.mViewHolders.add(new WeakReference<>(holder));
/* 252 */               v.setTag(holder);
/* 253 */               return v;
                    }

                    @Override // android.support.v4.widget.CursorAdapter
/* 257 */           public void bindView(View view, Context context, Cursor cursor) {
                        String iconFullUrl;
/* 258 */               ChannelViewHolder holder = (ChannelViewHolder) view.getTag();
/* 259 */               holder.channelTitle.setText(cursor.getString(2));
/* 260 */               long channelId = cursor.getLong(1);
/* 261 */               holder.channelId = String.valueOf(channelId);
                        try {
/* 264 */                   String colorStr = cursor.getString(4);
/* 265 */                   holder.channelColor = Color.parseColor(colorStr);
                        } catch (Exception e) {
                        }
                        if (channelId == ChannelsListFragment.access$300(ChannelsListFragment.this)) {
/* 271 */                   view.setBackground(ChannelsListFragment.this.getResources().getDrawable(C0257R.color.generic_selected));
                        } else {
/* 273 */                   view.setBackground(ChannelsListFragment.this.getResources().getDrawable(C0257R.drawable.bg_generic));
                        }
/* 277 */               if (ChannelsListFragment.this.getResources().getDisplayMetrics().densityDpi < 240) {
/* 278 */                   iconFullUrl = cursor.getString(8);
                        } else {
/* 280 */                   iconFullUrl = cursor.getString(9);
                        }
/* 283 */               if (!TextUtils.isEmpty(iconFullUrl)) {
/* 284 */                   ImageKey key = new ImageKey(iconFullUrl);
/* 285 */                   holder.channelImageKey = key;
/* 286 */                   setChannelImage(holder, ChannelsListFragment.this.mAppController.getPhotoBitmap(key));
                        } else {
/* 288 */                   setChannelImage(holder, null);
                        }
/* 291 */               if (ChannelsListFragment.access$400(ChannelsListFragment.this)) {
/* 292 */                   holder.followButton.setVisibility(0);
/* 293 */                   holder.followButton.setChecked(ChannelsListFragment.access$100(ChannelsListFragment.this).contains(holder.channelId));
                        } else {
/* 295 */                   holder.followButton.setVisibility(8);
                        }
                    }

/* 299 */           public synchronized void setChannelImages(HashMap<ImageKey, UrlImage> images) {
                        try {
                            ArrayList<WeakReference<ChannelViewHolder>> toRemove = new ArrayList<>();
                            for (WeakReference<ChannelViewHolder> ref : this.mViewHolders) {
/* 302 */                       ChannelViewHolder holder = ref.get();
/* 303 */                       if (holder == null) {
/* 304 */                           toRemove.add(ref);
                                } else {
/* 306 */                           UrlImage image = images.get(holder.channelImageKey);
/* 307 */                           if (image != null && image.isValid()) {
/* 308 */                               setChannelImage(holder, image.bitmap);
                                    }
                                }
                            }
                            for (WeakReference<ChannelViewHolder> r : toRemove) {
/* 313 */                       this.mViewHolders.remove(r);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }

/* 317 */           private void setChannelImage(ChannelViewHolder holder, Bitmap bmp) {
/* 318 */               if (bmp != null) {
/* 319 */                   holder.channelImage.setImageDrawable(new RecyclableBitmapDrawable(this.mContext.getResources(), bmp));
/* 320 */                   if (holder.channelColor != 0) {
/* 321 */                       holder.channelImage.setBackground(new ColorDrawable(holder.channelColor));
/* 328 */                       return;
                            }
/* 328 */                   return;
                        }
/* 324 */               holder.channelImage.setImageBitmap(null);
/* 325 */               holder.channelImage.setBackgroundColor(this.mContext.getResources().getColor(C0257R.color.solid_light_gray));
                    }
                }

/* 338 */       private class ChannelViewHolder {
                    public int channelColor;
                    public String channelId;
                    public ImageView channelImage;
                    public ImageKey channelImageKey;
                    public TextView channelTitle;
                    public VineToggleButton followButton;
                    public ViewGroup rootLayout;

/* 339 */           public ChannelViewHolder(View view) {
/* 341 */               this.channelImage = (ImageView) view.findViewById(C0257R.id.channel_image);
/* 342 */               this.channelTitle = (TextView) view.findViewById(C0257R.id.channel);
/* 343 */               this.followButton = (VineToggleButton) view.findViewById(C0257R.id.follow_button);
/* 344 */               this.followButton.setOnCheckedChangeListener(ChannelsListFragment.this);
/* 345 */               this.rootLayout = (ViewGroup) view.findViewById(C0257R.id.root_layout);
/* 347 */               if (ChannelsListFragment.access$400(ChannelsListFragment.this)) {
/* 348 */                   this.rootLayout.setOnClickListener(new ViewOnClickListenerC01611(ChannelsListFragment.this));
                        }
                    }

                    /* JADX INFO: renamed from: co.vine.android.ChannelsListFragment$ChannelViewHolder$1 */
/* 346 */           class ViewOnClickListenerC01611 implements View.OnClickListener {
                        final /* synthetic */ ChannelsListFragment val$this$0;

/* 347 */               ViewOnClickListenerC01611(ChannelsListFragment channelsListFragment) {
                            this.val$this$0 = channelsListFragment;
                        }

                        @Override // android.view.View.OnClickListener
/* 350 */               public void onClick(View view) {
/* 351 */                   ChannelViewHolder.this.followButton.performClick();
                        }
                    }
                }

                @Override // android.widget.CompoundButton.OnCheckedChangeListener
/* 359 */       public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int color;
/* 360 */           Object holder = ((View) buttonView.getParent()).getTag();
/* 361 */           if (this.mSelectAllButton == buttonView) {
/* 362 */               if (isChecked) {
/* 363 */                   this.mSelectedChannelIds.addAll(this.mAllChannelIds);
                        } else {
/* 365 */                   this.mSelectedChannelIds.removeAll(this.mAllChannelIds);
                        }
/* 367 */               this.mCursorAdapter.notifyDataSetChanged();
                    } else if (holder instanceof ChannelViewHolder) {
/* 369 */               String channelId = ((ChannelViewHolder) holder).channelId;
/* 370 */               if (isChecked) {
/* 371 */                   this.mSelectedChannelIds.add(channelId);
                        } else {
/* 373 */                   this.mSelectedChannelIds.remove(channelId);
/* 374 */                   if (this.mSelectAllButton != null) {
/* 375 */                       this.mSelectAllButton.setCheckedWithoutEvent(false);
                            }
                        }
                    }
/* 380 */           Drawable bg = buttonView.getBackground();
/* 381 */           if (bg != null && isChecked) {
/* 383 */               if (holder instanceof ChannelViewHolder) {
/* 384 */                   color = ((ChannelViewHolder) holder).channelColor;
                        } else {
/* 386 */                   color = getResources().getColor(C0257R.color.vine_green);
                        }
/* 388 */               bg.setColorFilter(color, PorterDuff.Mode.SRC_IN);
                    }
                }
            }
