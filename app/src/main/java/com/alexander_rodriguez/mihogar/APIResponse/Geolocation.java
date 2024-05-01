package com.alexander_rodriguez.mihogar.APIResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class Geolocation {
    public static  String STATUS = "status";
    public static  String COUNTRY = "country";
    public static  String COUNTRY_CODE = "countryCode";
    public static  String REGION = "region";
    public static  String REGION_NAME = "regionName";
    public static  String CITY = "city";
    public static  String ZIP = "zip";
    public static  String LAT = "lat";
    public static  String LON  = "lon";
    public static  String TIME_ZONE = "timezone";
    public static  String ISP = "isp";
    public static  String ORG = "org";
    public static  String AS = "as";
    public static  String QUERY = "query";

    public String status;
    public String country;
    public String countryCode;
    public String region;
    public String regionName;
    public String city;
    public String zip;
    public String lat;
    public String lon;
    public String timezone;
    public String isp;
    public String org;
    public String as;
    public String query;

    public Geolocation(JSONObject json) throws JSONException {
        status = json.getString(STATUS);
        country = json.getString(COUNTRY);
        countryCode = json.getString(COUNTRY_CODE);
        region = json.getString(REGION);
        regionName = json.getString(REGION_NAME);
        city = json.getString(CITY);
        zip = json.getString(ZIP);
        lat = json.getString(LAT);
        lon = json.getString(LON);
        timezone = json.getString(TIME_ZONE);
        isp = json.getString(ISP);
        org = json.getString(ORG);
        as = json.getString(AS);
        query = json.getString(QUERY);
    }

    public static String getSTATUS() {
        return STATUS;
    }

    public static String getCOUNTRY() {
        return COUNTRY;
    }

    public static String getCountryCode() {
        return COUNTRY_CODE;
    }

    public String getRegion() {
        return region;
    }

    public static String getREGION() {
        return REGION;
    }

    public static String getRegionName() {
        return REGION_NAME;
    }

    public String getCity() {
        return city;
    }

    public String getZip() {
        return zip;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getIsp() {
        return isp;
    }

    public String getOrg() {
        return org;
    }

    public String getAs() {
        return as;
    }

    public String getQuery() {
        return query;
    }

    public static String getCITY() {
        return CITY;
    }

    public static String getZIP() {
        return ZIP;
    }

    public static String getLAT() {
        return LAT;
    }

    public static String getLON() {
        return LON;
    }

    public static String getTimeZone() {
        return TIME_ZONE;
    }

    public static String getISP() {
        return ISP;
    }

    public static String getORG() {
        return ORG;
    }

    public static String getAS() {
        return AS;
    }

    public static String getQUERY() {
        return QUERY;
    }

    public String getStatus() {
        return status;
    }

    public String getCountry() {
        return country;
    }

    public boolean isSuccess(){
        return status.equals("success");
    }
}
