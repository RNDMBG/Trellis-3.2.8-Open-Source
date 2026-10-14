            package com.facebook.model;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface GraphLocation extends GraphObject {
                String getCity();

                String getCountry();

                double getLatitude();

                double getLongitude();

                String getState();

                String getStreet();

                String getZip();

                void setCity(String str);

                void setCountry(String str);

                void setLatitude(double d);

                void setLongitude(double d);

                void setState(String str);

                void setStreet(String str);

                void setZip(String str);
            }
