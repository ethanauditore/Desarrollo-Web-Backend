package ciencias.unam.mx;

public class Region {
    private static int regionId;
    private String region;

    public Region() {}

    public Region(String region) {
        this.region = region;
        regionId++;
    }

    public static int getRegionId() {
        return regionId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegionId(String region) {
        this.region = region;
    }

    @Override public String toString() {
        return String.format("RegionID: %d\n" +
                             "Region: %s",
                             regionId, region);
    }
}
