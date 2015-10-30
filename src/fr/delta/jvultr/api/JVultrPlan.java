package fr.delta.jvultr.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.delta.jvultr.JVultrCache;

import java.util.Arrays;

/**
 * Created by david on 29/10/15.
 */
public class JVultrPlan {
    public enum PlanType{
        SSD,
        DEDICATED,
        SATA;
    }
    private int id;
    private String name;
    private int cpus;
    private int ram;
    private int disk;
    private float bandwidth;
    private float pricePerMonth;
    private boolean windows;
    private PlanType type;
    private JVultrRegion[] availableRegions;

    public JVultrPlan(JsonObject value) {
        this.id = value.get("VPSPLANID").getAsInt();
        this.name = value.get("name").getAsString();
        this.cpus = value.get("vcpu_count").getAsInt();
        this.ram = value.get("ram").getAsInt();
        this.disk = value.get("disk").getAsInt();
        this.bandwidth = value.get("bandwidth").getAsFloat();
        this.pricePerMonth = value.get("price_per_month").getAsFloat();
        this.windows = value.get("windows").getAsBoolean();
        this.type = PlanType.valueOf(value.get("plan_type").getAsString());
        if(value.has("available_locations")){
            JsonArray array = value.get("available_locations").getAsJsonArray();
            availableRegions = new JVultrRegion[array.size()];
            int i = 0;
            for(JsonElement element : array){
                int regionId = element.getAsInt();
                if(!JVultrCache.getCachedRegions().containsKey(regionId)) JVultrCache.reloadCachedRegions();
                availableRegions[i] = JVultrCache.getCachedRegions().get(regionId);
                i++;
            }
        }else{
             availableRegions = new JVultrRegion[0];
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCpus() {
        return cpus;
    }

    public int getRam() {
        return ram;
    }

    public int getDisk() {
        return disk;
    }

    public float getBandwidth() {
        return bandwidth;
    }

    public float getPricePerMonth() {
        return pricePerMonth;
    }

    public boolean isWindows() {
        return windows;
    }

    public PlanType getType() {
        return type;
    }

    public JVultrRegion[] getAvailableRegions() {
        return availableRegions;
    }

    @Override
    public String toString() {
        return "id:" + id + ",name:" + name + ",cpus:"
                + cpus + ",ram:" + ram + ",disk:" + disk + ",bandwidth:"
                + bandwidth + ",pricePerMonth:" + pricePerMonth + ",windows:" + windows
                + ",type:" + type + ",availableLocations:" + Arrays.toString(availableRegions);
    }
}
