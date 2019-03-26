package com.example.krishanroy.bookswappers.ui.model;

public class Persons {
    private int id;
    private String name;
    private String title;
    private Address address;
    private String image;

    public Persons(int id, String name, String title, Address address, String image) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.address = address;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public Address getAddress() {
        return address;
    }


    public class Address {
        private String street;
        private String suite;
        private String city;
        private String zipcode;
        private Geo geo;

        public String getStreet() {
            return street;
        }

        public String getSuite() {
            return suite;
        }

        public String getCity() {
            return city;
        }

        public String getZipcode() {
            return zipcode;
        }

        public Geo getGeo() {
            return geo;
        }

        public Address(String street, String suite, String city, String zipcode, Geo geo) {
            this.street = street;
            this.suite = suite;
            this.city = city;
            this.zipcode = zipcode;
            this.geo = geo;
        }

        private class Geo {
            private String lat;
            private String lng;

            public String getLat() {
                return lat;
            }

            public String getLng() {
                return lng;
            }

            public Geo(String lat, String lng) {
                this.lat = lat;
                this.lng = lng;
            }
        }
    }
}
