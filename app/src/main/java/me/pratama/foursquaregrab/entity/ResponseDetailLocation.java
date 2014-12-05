package me.pratama.foursquaregrab.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by pratama on 12/5/14.
 */
public class ResponseDetailLocation {

    @SerializedName("response")
    private Response response;

    public Response getResponse() {
        return response;
    }

    public class Response {
        @SerializedName("venue")
        private Venue venue;

        public Venue getVenue() {
            return venue;
        }
    }

    public class Venue {
        @SerializedName("name")
        private String name;
        @SerializedName("contact")
        private Contact contact;
        @SerializedName("url")
        private String url;
        @SerializedName("rating")
        private double rating;
        @SerializedName("ratingColor")
        private String ratingColor;
        @SerializedName("location")
        private Location location;
        @SerializedName("photos")
        private Photos photos;

        @SerializedName("categories")
        private List<Categories> categoriesList;

        public List<Categories> getCategoriesList() {
            return categoriesList;
        }

        public Photos getPhotos() {
            return photos;
        }

        public Contact getContact() {
            return contact;
        }

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }

        public double getRating() {
            return rating;
        }

        public String getRatingColor() {
            return ratingColor;
        }

        public Location getLocation() {
            return location;
        }
    }

    public class Categories {
        @SerializedName("name")
        private String name;

        public String getName() {
            return name;
        }
    }

    public class Contact {
        @SerializedName("phone")
        private String phone;

        public String getPhone() {
            return phone;
        }
    }

    public class Location {
        @SerializedName("address")
        private String address;
        @SerializedName("lat")
        private double lat;
        @SerializedName("lng")
        private double lng;

        public String getAddress() {
            return address;
        }

        public double getLat() {
            return lat;
        }

        public double getLng() {
            return lng;
        }
    }

    public class Photos {
        @SerializedName("groups")
        private List<Groups> groupsList;

        public List<Groups> getGroupsList() {
            return groupsList;
        }
    }

    public class Groups {
        @SerializedName("items")
        private List<Items> itemsList;

        public List<Items> getItemsList() {
            return itemsList;
        }
    }

    public class Items {
        @SerializedName("id")
        private String id;
        @SerializedName("prefix")
        private String prefix;
        @SerializedName("suffix")
        private String suffix;
        @SerializedName("width")
        private int width;
        @SerializedName("height")
        private int height;
        @SerializedName("user")
        private User user;

        public String getId() {
            return id;
        }

        public String getPrefix() {
            return prefix;
        }

        public String getSuffix() {
            return suffix;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public User getUser() {
            return user;
        }
    }

    public class User {
        @SerializedName("firstName")
        private String firstName;
        @SerializedName("lastName")
        private String lastName;
        @SerializedName("photo")
        private UserPhoto userPhoto;

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public UserPhoto getUserPhoto() {
            return userPhoto;
        }
    }

    public class UserPhoto {
        @SerializedName("prefix")
        private String prefix;
        @SerializedName("suffix")
        private String suffix;

        public String getPrefix() {
            return prefix;
        }

        public String getSuffix() {
            return suffix;
        }
    }
}
