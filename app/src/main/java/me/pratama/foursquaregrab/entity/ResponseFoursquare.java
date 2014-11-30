package me.pratama.foursquaregrab.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by pratama on 11/15/14.
 */
public class ResponseFoursquare {
    @SerializedName("groups")
    private List<Group> listGroup;

    public List<Group> getListGroup() {
        return listGroup;
    }

    public class Group {
        @SerializedName("items")
        private List<Item> listItem;

        public List<Item> getListItem() {
            return listItem;
        }

    }

    public class Item {
        @SerializedName("venue")
        private Venue venue;

        public Venue getVenue() {
            return venue;
        }
    }

    public class Venue {
        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;
        @SerializedName("location")
        private Location location;
        @SerializedName("categories")
        private List<Categories> categories;
        @SerializedName("rating")
        private double rating;
        @SerializedName("photos")
        private Photos photos;
        @SerializedName("ratingColor")
        private String ratingColor;

        public String getRatingColor() {
            return ratingColor;
        }

        public Photos getPhotos() {
            return photos;
        }

        public List<Categories> getCategories() {
            return categories;
        }

        public double getRating() {
            return rating;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Location getLocation() {
            return location;
        }

        public class Location {
            @SerializedName("address")
            private String address;
            @SerializedName("lat")
            private double lat;
            @SerializedName("lng")
            private double lng;
            @SerializedName("distance")
            private double distance;

            public double getDistance() {
                return distance;
            }

            public String getAddress() {
                return address;
            }
        }

        public class Categories {
            @SerializedName("id")
            private String id;
            @SerializedName("name")
            private String name;

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
            }

        }

        /**
         * list photo on venues
         */
        public class Photos {
            @SerializedName("groups")
            private List<Groups> listGroups;

            public List<Groups> getListGroups() {
                return listGroups;
            }

            public class Groups {
                @SerializedName("items")
                private List<Items> listPhoto;

                public List<Items> getListPhoto() {
                    return listPhoto;
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

            }
        }
    }
}
