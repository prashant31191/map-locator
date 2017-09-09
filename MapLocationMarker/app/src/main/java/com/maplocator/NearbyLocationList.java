package com.maplocator;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.fmsirvent.ParallaxEverywhere.PEWImageView;
import com.google.android.gms.maps.model.LatLng;
import com.map.api.ApiService;
import com.map.model.NearbyCustomModel;
import com.map.model.NearbysearchModel;
import com.map.response.NearbysearchListResponse;
import com.squareup.picasso.Picasso;
import com.utils.App;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Query;

/**
 * Created by Admin on 10/21/2016.
 */

public class NearbyLocationList extends AppCompatActivity {
    Toolbar mToolbar;
    SearchView svSearchLocation;
    RecyclerView rvLocationList;


    Bundle intBundelData = null;


    String strFrom = "", strTitle = "Nearby locations", strType = "";
    String strLat = "10.0";
    String strLong = "20.0";

    int intRadius = 1500;

    ApiService apiService;
    ArrayList<NearbyCustomModel> arrListNearbyCustomModel = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_nearby_location_list);
        arrListNearbyCustomModel = new ArrayList<>();
        initData();
    }

    private void initData() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        svSearchLocation = (SearchView) findViewById(R.id.svSearchLocation);
        rvLocationList = (RecyclerView) findViewById(R.id.rvLocationList);

        rvLocationList.setLayoutManager(new GridLayoutManager(NearbyLocationList.this, 1));

        mToolbar.setTitle(strTitle);
        setSupportActionBar(mToolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        getBundelData();
        setApicallData();

        if (AppUtils.checkInternet(NearbyLocationList.this)) {
            asyncGetNearbyPlaces (strLat + "," + strLong,strType,""+intRadius,"false",AppUtils.ApiCallAdress.GOOGLE_API_KEY1);

        } else {
            AppUtils.showSnackBar(rvLocationList, "Please check internet your connection.");
        }


    }

    //set api calling data
    private void setApicallData() {
        Retrofit client = new Retrofit.Builder()
                .baseUrl(AppUtils.ApiCallAdress.WEB_GOOGLE_PLACES_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = client.create(ApiService.class);
    }

    //getting intent data
    private void getBundelData() {
        intBundelData = getIntent().getExtras();

        if (intBundelData != null) {
            if (intBundelData.getString(AppUtils.IntentTags.FROM) != null) {
                strFrom = intBundelData.getString(AppUtils.IntentTags.FROM);
            }

            if (intBundelData.getString(AppUtils.IntentTags.TITLE) != null) {
                strTitle = intBundelData.getString(AppUtils.IntentTags.TITLE);
            }

            if (intBundelData.getString(AppUtils.IntentTags.TYPE) != null) {
                strType = intBundelData.getString(AppUtils.IntentTags.TYPE);
            }

            if (intBundelData.getString(AppUtils.IntentTags.LAT) != null) {
                strLat = intBundelData.getString(AppUtils.IntentTags.LAT);
            }

            if (intBundelData.getString(AppUtils.IntentTags.LONG) != null) {
                strLong = intBundelData.getString(AppUtils.IntentTags.LONG);
            }
        }

    }

    private void asyncGetNearbyPlaces(final String location, String types, String radius, String sensor, String key) {

       /* @Query("location") String location,
        @Query("types") String types,
        @Query("radius") String radius,
        @Query("sensor") String sensor,
        @Query("key") String key*/
        AppUtils.showLog("=location==="+location);
        AppUtils.showLog("=types==="+types);
        AppUtils.showLog("=radius==="+radius);
        AppUtils.showLog("=key==="+key);
        //AppUtils.showLog("=Response==="+location);

        Call<NearbysearchListResponse> getcitylist = apiService.getNearbyLocation(location,types,radius,sensor,key);
        getcitylist.enqueue(new Callback<NearbysearchListResponse>() {
            @Override
            public void onResponse(Call<NearbysearchListResponse> call, Response<NearbysearchListResponse> response) {

                if (response.body() != null) {
                    AppUtils.showLog("=Response==="+response.body().toString());

                    NearbysearchListResponse nearbysearchListResponse = response.body();

                    if(nearbysearchListResponse.next_page_token !=null)
                    {
                        AppUtils.showLog("= Response 1== next_page_token =="+nearbysearchListResponse.next_page_token );
                    }

                    if(nearbysearchListResponse.arrListNearbysearchModel !=null &&  nearbysearchListResponse.arrListNearbysearchModel.size() >= 10)
                    {
                        AppUtils.showLog("= Response 1==nearbysearchListResponse.arrListNearbysearchModel==="+nearbysearchListResponse.arrListNearbysearchModel.size());


                        for (int i=0;i<nearbysearchListResponse.arrListNearbysearchModel.size();i++)
                        {
                            String name="",vicinity="",icon="",width="",height="",photo_reference="";

                            name = nearbysearchListResponse.arrListNearbysearchModel.get(i).name;
                            vicinity = nearbysearchListResponse.arrListNearbysearchModel.get(i).vicinity;
                            icon = nearbysearchListResponse.arrListNearbysearchModel.get(i).icon;

                            AppUtils.showLog("=Name="+name);
                            AppUtils.showLog("=vicinity="+vicinity);

                            if(nearbysearchListResponse.arrListNearbysearchModel.get(i).arrListPhotosModel !=null && nearbysearchListResponse.arrListNearbysearchModel.get(i).arrListPhotosModel.size() > 0)
                            {
                                for (int j=0;j<nearbysearchListResponse.arrListNearbysearchModel.get(i).arrListPhotosModel.size();j++) {
                                    width =  nearbysearchListResponse.arrListNearbysearchModel.get(i).arrListPhotosModel.get(j).width;
                                    height =  nearbysearchListResponse.arrListNearbysearchModel.get(i).arrListPhotosModel.get(j).height;
                                    photo_reference =  nearbysearchListResponse.arrListNearbysearchModel.get(i).arrListPhotosModel.get(j).photo_reference;

                                    AppUtils.showLog("=width=" + width);
                                    AppUtils.showLog("=height=" + height);
                                    AppUtils.showLog("=photo_reference=" + photo_reference);
                                }
                            }
                            else
                            {
                                width="";height="";photo_reference="";
                            }

                            double lat=0,lng=0 ;
                            LatLng latLng = new LatLng(lat,lng);
                            if(nearbysearchListResponse.arrListNearbysearchModel.get(i).geometry !=null && nearbysearchListResponse.arrListNearbysearchModel.get(i).geometry.location !=null)
                            {
                                NearbysearchModel.Geometry.Location location1 = nearbysearchListResponse.arrListNearbysearchModel.get(i).geometry.location;

                                if(location1.lat !=null && location1.lat.length() > 1) {
                                    lat = Double.parseDouble(location1.lat);
                                    lng = Double.parseDouble(location1.lng);
                                }
                                latLng = new LatLng(lat,lng);
                            }


                            NearbyCustomModel nearbyCustomModel = new NearbyCustomModel(name,vicinity,icon,width,height,photo_reference,latLng);
                            arrListNearbyCustomModel.add(nearbyCustomModel);

                        }


                        if(arrListNearbyCustomModel !=null && arrListNearbyCustomModel.size() > 0)
                        {
                            LocationListAdapter  locationListAdapter = new LocationListAdapter(arrListNearbyCustomModel,NearbyLocationList.this);
                            rvLocationList.setAdapter(locationListAdapter);
                        }

                    }
                    else
                    {
                        intRadius = intRadius + 300;
                        if(intRadius <= 2900) {
                            if (intRadius > 1850) {
                                asyncGetNearbyPlaces(strLat + "," + strLong, strType, "" + intRadius, "false", AppUtils.ApiCallAdress.GOOGLE_API_KEY2);
                            } else {
                                asyncGetNearbyPlaces(strLat + "," + strLong, strType, "" + intRadius, "false", AppUtils.ApiCallAdress.GOOGLE_API_KEY1);
                            }
                        }

                    }


                   /* String status = response.body().status;
                    Log.e("status in selectcity", status);
                    if (status.equals("1")) {

                         } else {

                    }*/
                }
                else
                {
                    intRadius = intRadius + 300;
                    if(intRadius <= 2900) {
                        if (intRadius > 1850) {
                            asyncGetNearbyPlaces(strLat + "," + strLong, strType, "" + intRadius, "false", AppUtils.ApiCallAdress.GOOGLE_API_KEY2);
                        } else {
                            asyncGetNearbyPlaces(strLat + "," + strLong, strType, "" + intRadius, "false", AppUtils.ApiCallAdress.GOOGLE_API_KEY1);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<NearbysearchListResponse> call, Throwable t) {
                AppUtils.showSnackBar(rvLocationList,"Please try again..!");
            }


        });

}





    public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.HomeWishViewHolder> {
        private ArrayList<NearbyCustomModel> mArrayListHomeWishListModel;
        Context mContext;

        public LocationListAdapter(ArrayList<NearbyCustomModel> arrayListHomeWishListModel, Context context) {
            mArrayListHomeWishListModel = arrayListHomeWishListModel;
            mContext = context;
        }

        @Override
        public LocationListAdapter.HomeWishViewHolder onCreateViewHolder(ViewGroup container, int position) {
            LayoutInflater inflater = LayoutInflater.from(container.getContext());

            View cell = inflater.inflate(R.layout.raw_nearby_location_list, container, false);
            AppUtils.showLog("--create view--", "-position-22--" + position);

            return new LocationListAdapter.HomeWishViewHolder(cell, container.getContext(), position);
        }

        @Override
        public void onBindViewHolder(final LocationListAdapter.HomeWishViewHolder viewHolder, final int position) {

            String urlLarge ="https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+ mArrayListHomeWishListModel.get(position).photo_reference +"&key="+AppUtils.ApiCallAdress.GOOGLE_API_KEY1;
            // https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=CoQBdwAAAFMJVYi-fj0V8WxGyEd8E2kOWx7h6Zt6oWK0UQiJTqPnoMzR4bhZwj5US6wCU3iUz82qE13Y1nyf1Uz-k2FcqcrU2SUr-2XgzSs2yjcSixG9a3MtoP_ZXYr0eiVjHZdnSAfFGUZ_s_mucM9_a19Fav8hdwmpT4DUhOEZSTfr9W1JEhBGhV3VWQmPQt_M9yFbxqhEGhSzGb7e3HubBwzrCECm5JLra7M76g&key=AIzaSyAgrsctqXjg_Qk7bo6wmMUUfV2QKWSd_wE
            if(mArrayListHomeWishListModel.get(position).photo_reference !=null && mArrayListHomeWishListModel.get(position).photo_reference.length() > 1)
            {
                AppUtils.showLog("==Yes_Loading_data_key===");
                Picasso.with(mContext).load(urlLarge).placeholder(R.color.tr4clrWhite1).error(R.color.tr0clrBlack0).into(viewHolder.ivImage);
            }
            else
            {
                AppUtils.showLog("==No_Loading_data_key===");
                 urlLarge ="https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=";
                 Picasso.with(mContext).load(urlLarge).placeholder(R.color.tr4clrBlack1).error(R.color.tr0clrBlack0).into(viewHolder.ivImage);
            }


            String url = mArrayListHomeWishListModel.get(position).icon;
            Picasso.with(mContext).load(url).placeholder(R.drawable.p_landscap).into(viewHolder.ivIcon);

      /* Glide
                .with(mContext)
                .load(url)
               // .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
               // .crossFade()
                .into(viewHolder.image);*/


            viewHolder.tvTtile.setText(position+". "+mArrayListHomeWishListModel.get(position).name);
            viewHolder.tvDesc.setText(mArrayListHomeWishListModel.get(position).vicinity);

            viewHolder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    App.showLog("===click card===","====position=="+position);

                    if( mArrayListHomeWishListModel.get(position).latLng !=null)
                    {
                        LatLng latLng = mArrayListHomeWishListModel.get(position).latLng;
                        App.showLog("===click card===","====latitude=="+latLng.latitude);
                        App.showLog("===click card===","====longitude=="+latLng.longitude);


                     //   String uri = String.format(Locale.ENGLISH, "geo:%f,%f", mArrayListHomeWishListModel.get(position).latLng.latitude, mArrayListHomeWishListModel.get(position).latLng.longitude);
                        String uri = String.format(Locale.ENGLISH, "geo:%f,%f?q=%f,%f(%s)",
                                latLng.latitude,
                                latLng.longitude,
                                latLng.latitude,
                                latLng.longitude,
                                ""+mArrayListHomeWishListModel.get(position).name+
                                "\n \n"+mArrayListHomeWishListModel.get(position).vicinity

                                );

                        App.showLog("====uri===="+uri);
/*
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<lat>,<long>?q=<lat>,<long>(Label+Name)"));
                        startActivity(intent);
                        */
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        mContext.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mArrayListHomeWishListModel.size();
        }


        public class HomeWishViewHolder extends RecyclerView.ViewHolder {
            PEWImageView ivImage;
            ImageView ivIcon;
            CardView cv;
            TextView tvTtile, tvDesc;

            public HomeWishViewHolder(View itemView, Context context, int position) {
                super(itemView);
                //image = (ImageView) itemView.findViewById(R.id.image);
             //   Log.i("--create view--", "-position---" + position);

                ivImage = (PEWImageView) itemView.findViewById(R.id.ivImage);
                ivIcon = (ImageView) itemView.findViewById(R.id.ivIcon);

                cv = (CardView) itemView.findViewById(R.id.cardlist_item);
                tvTtile = (TextView) itemView.findViewById(R.id.tvTtile);
                tvDesc = (TextView) itemView.findViewById(R.id.tvDesc);
            }
        }

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            // finish(); // close this activity and return to preview activity (if there is any)
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
