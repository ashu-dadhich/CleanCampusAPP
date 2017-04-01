package Backend;

/**
 * Created by ashu on 13/1/17.
 */

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.ac.nith.swach_bharat.Frag_issue_details;
import in.ac.nith.swach_bharat.Frag_issue_list;
import in.ac.nith.swach_bharat.MainActivity;
import in.ac.nith.swach_bharat.R;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {
    //static SharedPreferenceStore mSharedPreferenceStore = new SharedPreferenceStore();
    Album album;
    private Context mContext;
    private List<Album> albumList;
    int position;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView status, time, desc;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            status = (TextView) view.findViewById(R.id.status);
            time = (TextView) view.findViewById(R.id.time);
            desc = (TextView) view.findViewById(R.id.desc);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }


        @Override
        public void onClick(View v) {
            position = getPosition();
            Log.d("ashu", "onClick " + getPosition() );
            showPopupMenu(v);
        }

    }



    public AlbumsAdapter(Context mContext, List<Album> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        album = albumList.get(position);
//        holder.title.setText(album.getName());
//        holder.count.setText(album.getNumOfSongs() + " songs");
//
//        // loading album cover using Glide library
//        Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);
        Glide.with(mContext).load(album.getUrl()).into(holder.thumbnail);
            if(album.getStatus().equals("0")){
                holder.status.setText("Pending");
                holder.time.setText(album.getU_time());
            }
            else{
                holder.status.setText("Resolved");
                holder.time.setText(album.getR_time());
            }
            holder.desc.setText(album.getDescription());

//        holder.overflow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showPopupMenu(holder.overflow);
//            }
//        });
    }


    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        //Toast.makeText(mContext, ""+position, Toast.LENGTH_SHORT).show();
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }



    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
            
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            switch (menuItem.getItemId()) {
                case R.id.action_details:
                    //Toast.makeText(mContext, ""+albumList.get(position).getEmail(), Toast.LENGTH_SHORT).show();
                    Bundle bundle=new Bundle();
                    bundle.putString("url",albumList.get(position).getUrl());
                    bundle.putString("status",albumList.get(position).getStatus());
                    bundle.putString("description",albumList.get(position).getDescription());
                    bundle.putString("location",albumList.get(position).getLocation());
                    bundle.putString("latitude",albumList.get(position).getLatitude());
                    bundle.putString("longitude",albumList.get(position).getLongitude());
                    bundle.putString("i_date",albumList.get(position).getU_time());
                    bundle.putString("r_date",albumList.get(position).getR_time());
                    bundle.putString("name",albumList.get(position).getName());
                    bundle.putString("email",albumList.get(position).getEmail().toString());
                    bundle.putString("phone",albumList.get(position).getMobile());
                    //Toast.makeText(mContext, ""+albumList.get(position).getUrl(), Toast.LENGTH_SHORT).show();
                    AppCompatActivity activity = (AppCompatActivity) mContext;
                    android.support.v4.app.Fragment fragment = new Frag_issue_details();
                    fragment.setArguments(bundle);
                    android.support.v4.app.FragmentTransaction fragmentTransaction =activity.getSupportFragmentManager().beginTransaction().
                            replace(R.id.myframe, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    return true;
                case R.id.action_admin:
//                    Boolean b=mSharedPreferenceStore.mPrefsStore.getBoolean("isAdminLoggedIn",false);
//                    String n = mSharedPreferenceStore.mPrefsStore.getString("userName", "User");
//                    Toast.makeText(mContext, n, Toast.LENGTH_SHORT).show();
                    if(MainActivity.isAdminLoggedIn){
                        final ProgressDialog progressDialog = new ProgressDialog(mContext);
                        progressDialog.setMessage("Please Wait");
                        progressDialog.show();

                        //Creating a string request
                        StringRequest req = new StringRequest(Request.Method.POST, Config.ISSUE_STATUS_CHANGE_URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        //dismissing the progress dialog
                                        progressDialog.dismiss();

                                        //if the server returned the string success
                                        //if (response.trim().equalsIgnoreCase("success")) {
                                        //Displaying a success toast
                                        Toast.makeText(mContext, "Action Successful", Toast.LENGTH_SHORT).show();

                                        //} else {
                                        Toast.makeText(mContext, "Action failed", Toast.LENGTH_SHORT).show();
                                        //}
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(mContext, "No Internet Connection Detected", Toast.LENGTH_SHORT).show();
                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                //adding parameters to post request as we need to send firebase id and email
                                params.put("id",albumList.get(position).getId());
                                params.put("status",albumList.get(position).getStatus());
                                return params;
                            }
                        };

                        //Adding the request to the queue
                        AppCompatActivity activity1 = (AppCompatActivity) mContext;
                        RequestQueue requestQueue = Volley.newRequestQueue(activity1);
                        requestQueue.add(req);
                        //menuItem.setVisible(false);
                        
                    }else {
                        Toast.makeText(mContext, "Please first login as admin", Toast.LENGTH_SHORT).show();
                    }
                    return true;

                case R.id.action_map:
                    double latitude = Double.parseDouble(albumList.get(position).getLatitude());
                    double longitude = Double.parseDouble(albumList.get(position).getLongitude());
                    String label = "Issue";
                    String uriBegin = "geo:" + latitude + "," + longitude;
                    String query = latitude + "," + longitude + "(" + label + ")";
                    String encodedQuery = Uri.encode(query);
                    String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                    Uri uri = Uri.parse(uriString);
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
                    mContext.startActivity(intent);
                    return true;
                default:

            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}
