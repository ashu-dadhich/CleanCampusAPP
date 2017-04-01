package Backend;

/**
 * Created by ashu on 13/1/17.
 */

public class Album {
    private String id,url,name,longitude,latitude,description,location,email,mobile,status,i_time,r_time,u_time;

    public Album(String id, String url, String name, String longitude, String latitude, String description
            , String location, String email, String mobile, String status, String i_time, String r_time, String u_time) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.description = description;
        this.location = location;
        this.email = email;
        this.mobile = mobile;
        this.status = status;
        this.i_time = i_time;
        this.r_time = r_time;
        this.u_time = u_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getI_time() {
        return i_time;
    }

    public void setI_time(String i_time) {
        this.i_time = i_time;
    }

    public String getR_time() {
        return r_time;
    }

    public void setR_time(String r_time) {
        this.r_time = r_time;
    }

    public String getU_time() {
        return u_time;
    }

    public void setU_time(String u_time) {
        this.u_time = u_time;
    }
}
