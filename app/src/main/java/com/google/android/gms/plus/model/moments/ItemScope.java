            package com.google.android.gms.plus.model.moments;

            import com.google.android.gms.common.data.Freezable;
            import com.google.android.gms.internal.C1086fq;
            import java.util.HashSet;
            import java.util.List;
            import java.util.Set;
            
            
            public interface ItemScope extends Freezable<ItemScope> {

                public static class Builder {

                    
                    private String f2391hN;
                    private String mName;

                    
                    private String f2392mo;

                    
                    private double f2393oE;

                    
                    private double f2394oF;

                    
                    private final Set<Integer> f2395rI = new HashSet();

                    
                    private C1086fq f2396rJ;

                    
                    private List<String> f2397rK;

                    
                    private C1086fq f2398rL;

                    
                    private String f2399rM;

                    
                    private String f2400rN;

                    
                    private String f2401rO;

                    
                    private List<C1086fq> f2402rP;

                    
                    private int f2403rQ;

                    
                    private List<C1086fq> f2404rR;

                    
                    private C1086fq f2405rS;

                    
                    private List<C1086fq> f2406rT;

                    
                    private String f2407rU;

                    
                    private String f2408rV;

                    
                    private C1086fq f2409rW;

                    
                    private String f2410rX;

                    
                    private String f2411rY;

                    
                    private String f2412rZ;

                    
                    private C1086fq f2413sA;

                    
                    private String f2414sB;

                    
                    private String f2415sC;

                    
                    private String f2416sD;

                    
                    private String f2417sE;

                    
                    private String f2418sF;

                    
                    private List<C1086fq> f2419sa;

                    
                    private String f2420sb;

                    
                    private String f2421sc;

                    
                    private String f2422sd;

                    
                    private String f2423se;

                    
                    private String f2424sf;

                    
                    private String f2425sg;

                    
                    private String f2426sh;

                    
                    private String f2427si;

                    
                    private C1086fq f2428sj;

                    
                    private String f2429sk;

                    
                    private String f2430sl;

                    
                    private String f2431sm;

                    
                    private String f2432sn;

                    
                    private C1086fq f2433so;

                    
                    private C1086fq f2434sp;

                    
                    private C1086fq f2435sq;

                    
                    private List<C1086fq> f2436sr;

                    
                    private String f2437ss;

                    
                    private String f2438st;

                    
                    private String f2439su;

                    
                    private String f2440sv;

                    
                    private C1086fq f2441sw;

                    
                    private String f2442sx;

                    
                    private String f2443sy;

                    
                    private String f2444sz;

                    public ItemScope build() {
                        return new C1086fq(this.f2395rI, this.f2396rJ, this.f2397rK, this.f2398rL, this.f2399rM, this.f2400rN, this.f2401rO, this.f2402rP, this.f2403rQ, this.f2404rR, this.f2405rS, this.f2406rT, this.f2407rU, this.f2408rV, this.f2409rW, this.f2410rX, this.f2411rY, this.f2412rZ, this.f2419sa, this.f2420sb, this.f2421sc, this.f2422sd, this.f2392mo, this.f2423se, this.f2424sf, this.f2425sg, this.f2426sh, this.f2427si, this.f2428sj, this.f2429sk, this.f2430sl, this.f2431sm, this.f2432sn, this.f2433so, this.f2393oE, this.f2434sp, this.f2394oF, this.mName, this.f2435sq, this.f2436sr, this.f2437ss, this.f2438st, this.f2439su, this.f2440sv, this.f2441sw, this.f2442sx, this.f2443sy, this.f2444sz, this.f2413sA, this.f2414sB, this.f2415sC, this.f2416sD, this.f2391hN, this.f2417sE, this.f2418sF);
                    }

                    public Builder setAbout(ItemScope about) {
                        this.f2396rJ = (C1086fq) about;
                        this.f2395rI.add(2);
                        return this;
                    }

                    public Builder setAdditionalName(List<String> additionalName) {
                        this.f2397rK = additionalName;
                        this.f2395rI.add(3);
                        return this;
                    }

                    public Builder setAddress(ItemScope address) {
                        this.f2398rL = (C1086fq) address;
                        this.f2395rI.add(4);
                        return this;
                    }

                    public Builder setAddressCountry(String addressCountry) {
                        this.f2399rM = addressCountry;
                        this.f2395rI.add(5);
                        return this;
                    }

                    public Builder setAddressLocality(String addressLocality) {
                        this.f2400rN = addressLocality;
                        this.f2395rI.add(6);
                        return this;
                    }

                    public Builder setAddressRegion(String addressRegion) {
                        this.f2401rO = addressRegion;
                        this.f2395rI.add(7);
                        return this;
                    }

                    
                    public Builder setAssociated_media(List<ItemScope> list) {
                        this.f2402rP = list;
                        this.f2395rI.add(8);
                        return this;
                    }

                    public Builder setAttendeeCount(int attendeeCount) {
                        this.f2403rQ = attendeeCount;
                        this.f2395rI.add(9);
                        return this;
                    }

                    
                    public Builder setAttendees(List<ItemScope> list) {
                        this.f2404rR = list;
                        this.f2395rI.add(10);
                        return this;
                    }

                    public Builder setAudio(ItemScope audio) {
                        this.f2405rS = (C1086fq) audio;
                        this.f2395rI.add(11);
                        return this;
                    }

                    
                    public Builder setAuthor(List<ItemScope> list) {
                        this.f2406rT = list;
                        this.f2395rI.add(12);
                        return this;
                    }

                    public Builder setBestRating(String bestRating) {
                        this.f2407rU = bestRating;
                        this.f2395rI.add(13);
                        return this;
                    }

                    public Builder setBirthDate(String birthDate) {
                        this.f2408rV = birthDate;
                        this.f2395rI.add(14);
                        return this;
                    }

                    public Builder setByArtist(ItemScope byArtist) {
                        this.f2409rW = (C1086fq) byArtist;
                        this.f2395rI.add(15);
                        return this;
                    }

                    public Builder setCaption(String caption) {
                        this.f2410rX = caption;
                        this.f2395rI.add(16);
                        return this;
                    }

                    public Builder setContentSize(String contentSize) {
                        this.f2411rY = contentSize;
                        this.f2395rI.add(17);
                        return this;
                    }

                    public Builder setContentUrl(String contentUrl) {
                        this.f2412rZ = contentUrl;
                        this.f2395rI.add(18);
                        return this;
                    }

                    
                    public Builder setContributor(List<ItemScope> list) {
                        this.f2419sa = list;
                        this.f2395rI.add(19);
                        return this;
                    }

                    public Builder setDateCreated(String dateCreated) {
                        this.f2420sb = dateCreated;
                        this.f2395rI.add(20);
                        return this;
                    }

                    public Builder setDateModified(String dateModified) {
                        this.f2421sc = dateModified;
                        this.f2395rI.add(21);
                        return this;
                    }

                    public Builder setDatePublished(String datePublished) {
                        this.f2422sd = datePublished;
                        this.f2395rI.add(22);
                        return this;
                    }

                    public Builder setDescription(String description) {
                        this.f2392mo = description;
                        this.f2395rI.add(23);
                        return this;
                    }

                    public Builder setDuration(String duration) {
                        this.f2423se = duration;
                        this.f2395rI.add(24);
                        return this;
                    }

                    public Builder setEmbedUrl(String embedUrl) {
                        this.f2424sf = embedUrl;
                        this.f2395rI.add(25);
                        return this;
                    }

                    public Builder setEndDate(String endDate) {
                        this.f2425sg = endDate;
                        this.f2395rI.add(26);
                        return this;
                    }

                    public Builder setFamilyName(String familyName) {
                        this.f2426sh = familyName;
                        this.f2395rI.add(27);
                        return this;
                    }

                    public Builder setGender(String gender) {
                        this.f2427si = gender;
                        this.f2395rI.add(28);
                        return this;
                    }

                    public Builder setGeo(ItemScope geo) {
                        this.f2428sj = (C1086fq) geo;
                        this.f2395rI.add(29);
                        return this;
                    }

                    public Builder setGivenName(String givenName) {
                        this.f2429sk = givenName;
                        this.f2395rI.add(30);
                        return this;
                    }

                    public Builder setHeight(String height) {
                        this.f2430sl = height;
                        this.f2395rI.add(31);
                        return this;
                    }

                    public Builder setId(String id) {
                        this.f2431sm = id;
                        this.f2395rI.add(32);
                        return this;
                    }

                    public Builder setImage(String image) {
                        this.f2432sn = image;
                        this.f2395rI.add(33);
                        return this;
                    }

                    public Builder setInAlbum(ItemScope inAlbum) {
                        this.f2433so = (C1086fq) inAlbum;
                        this.f2395rI.add(34);
                        return this;
                    }

                    public Builder setLatitude(double latitude) {
                        this.f2393oE = latitude;
                        this.f2395rI.add(36);
                        return this;
                    }

                    public Builder setLocation(ItemScope location) {
                        this.f2434sp = (C1086fq) location;
                        this.f2395rI.add(37);
                        return this;
                    }

                    public Builder setLongitude(double longitude) {
                        this.f2394oF = longitude;
                        this.f2395rI.add(38);
                        return this;
                    }

                    public Builder setName(String name) {
                        this.mName = name;
                        this.f2395rI.add(39);
                        return this;
                    }

                    public Builder setPartOfTVSeries(ItemScope partOfTVSeries) {
                        this.f2435sq = (C1086fq) partOfTVSeries;
                        this.f2395rI.add(40);
                        return this;
                    }

                    
                    public Builder setPerformers(List<ItemScope> list) {
                        this.f2436sr = list;
                        this.f2395rI.add(41);
                        return this;
                    }

                    public Builder setPlayerType(String playerType) {
                        this.f2437ss = playerType;
                        this.f2395rI.add(42);
                        return this;
                    }

                    public Builder setPostOfficeBoxNumber(String postOfficeBoxNumber) {
                        this.f2438st = postOfficeBoxNumber;
                        this.f2395rI.add(43);
                        return this;
                    }

                    public Builder setPostalCode(String postalCode) {
                        this.f2439su = postalCode;
                        this.f2395rI.add(44);
                        return this;
                    }

                    public Builder setRatingValue(String ratingValue) {
                        this.f2440sv = ratingValue;
                        this.f2395rI.add(45);
                        return this;
                    }

                    public Builder setReviewRating(ItemScope reviewRating) {
                        this.f2441sw = (C1086fq) reviewRating;
                        this.f2395rI.add(46);
                        return this;
                    }

                    public Builder setStartDate(String startDate) {
                        this.f2442sx = startDate;
                        this.f2395rI.add(47);
                        return this;
                    }

                    public Builder setStreetAddress(String streetAddress) {
                        this.f2443sy = streetAddress;
                        this.f2395rI.add(48);
                        return this;
                    }

                    public Builder setText(String text) {
                        this.f2444sz = text;
                        this.f2395rI.add(49);
                        return this;
                    }

                    public Builder setThumbnail(ItemScope thumbnail) {
                        this.f2413sA = (C1086fq) thumbnail;
                        this.f2395rI.add(50);
                        return this;
                    }

                    public Builder setThumbnailUrl(String thumbnailUrl) {
                        this.f2414sB = thumbnailUrl;
                        this.f2395rI.add(51);
                        return this;
                    }

                    public Builder setTickerSymbol(String tickerSymbol) {
                        this.f2415sC = tickerSymbol;
                        this.f2395rI.add(52);
                        return this;
                    }

                    public Builder setType(String type) {
                        this.f2416sD = type;
                        this.f2395rI.add(53);
                        return this;
                    }

                    public Builder setUrl(String url) {
                        this.f2391hN = url;
                        this.f2395rI.add(54);
                        return this;
                    }

                    public Builder setWidth(String width) {
                        this.f2417sE = width;
                        this.f2395rI.add(55);
                        return this;
                    }

                    public Builder setWorstRating(String worstRating) {
                        this.f2418sF = worstRating;
                        this.f2395rI.add(56);
                        return this;
                    }
                }

                ItemScope getAbout();

                List<String> getAdditionalName();

                ItemScope getAddress();

                String getAddressCountry();

                String getAddressLocality();

                String getAddressRegion();

                List<ItemScope> getAssociated_media();

                int getAttendeeCount();

                List<ItemScope> getAttendees();

                ItemScope getAudio();

                List<ItemScope> getAuthor();

                String getBestRating();

                String getBirthDate();

                ItemScope getByArtist();

                String getCaption();

                String getContentSize();

                String getContentUrl();

                List<ItemScope> getContributor();

                String getDateCreated();

                String getDateModified();

                String getDatePublished();

                String getDescription();

                String getDuration();

                String getEmbedUrl();

                String getEndDate();

                String getFamilyName();

                String getGender();

                ItemScope getGeo();

                String getGivenName();

                String getHeight();

                String getId();

                String getImage();

                ItemScope getInAlbum();

                double getLatitude();

                ItemScope getLocation();

                double getLongitude();

                String getName();

                ItemScope getPartOfTVSeries();

                List<ItemScope> getPerformers();

                String getPlayerType();

                String getPostOfficeBoxNumber();

                String getPostalCode();

                String getRatingValue();

                ItemScope getReviewRating();

                String getStartDate();

                String getStreetAddress();

                String getText();

                ItemScope getThumbnail();

                String getThumbnailUrl();

                String getTickerSymbol();

                String getType();

                String getUrl();

                String getWidth();

                String getWorstRating();

                boolean hasAbout();

                boolean hasAdditionalName();

                boolean hasAddress();

                boolean hasAddressCountry();

                boolean hasAddressLocality();

                boolean hasAddressRegion();

                boolean hasAssociated_media();

                boolean hasAttendeeCount();

                boolean hasAttendees();

                boolean hasAudio();

                boolean hasAuthor();

                boolean hasBestRating();

                boolean hasBirthDate();

                boolean hasByArtist();

                boolean hasCaption();

                boolean hasContentSize();

                boolean hasContentUrl();

                boolean hasContributor();

                boolean hasDateCreated();

                boolean hasDateModified();

                boolean hasDatePublished();

                boolean hasDescription();

                boolean hasDuration();

                boolean hasEmbedUrl();

                boolean hasEndDate();

                boolean hasFamilyName();

                boolean hasGender();

                boolean hasGeo();

                boolean hasGivenName();

                boolean hasHeight();

                boolean hasId();

                boolean hasImage();

                boolean hasInAlbum();

                boolean hasLatitude();

                boolean hasLocation();

                boolean hasLongitude();

                boolean hasName();

                boolean hasPartOfTVSeries();

                boolean hasPerformers();

                boolean hasPlayerType();

                boolean hasPostOfficeBoxNumber();

                boolean hasPostalCode();

                boolean hasRatingValue();

                boolean hasReviewRating();

                boolean hasStartDate();

                boolean hasStreetAddress();

                boolean hasText();

                boolean hasThumbnail();

                boolean hasThumbnailUrl();

                boolean hasTickerSymbol();

                boolean hasType();

                boolean hasUrl();

                boolean hasWidth();

                boolean hasWorstRating();
            }
