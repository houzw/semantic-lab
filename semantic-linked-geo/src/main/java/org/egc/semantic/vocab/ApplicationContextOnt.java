package org.egc.semantic.vocab;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.egc.semantic.rdf.RdfUtils;

/**
 * @author houzhiwei
 * @date 2020/12/7 9:55
 */
public class ApplicationContextOnt {
    public static final String NS = "http://www.egc.org/ont/context#";
    private static final Model Context = RdfUtils.createDefaultModel();

    //class
    public static final Resource ApplicationContext = createResource("ApplicationContext");
    public static final Resource ApplicationPurpose = createResource("ApplicationPurpose");
    public static final Resource Watershed = createResource("Watershed");
    public static final Resource StudyArea = createResource("StudyArea");

    public static final Resource purpose_BOD = createResource("BOD_simulation");
    public static final Resource purpose_DO = createResource("DO_simulation");
    public static final Resource purpose_ammonia_nitrogen = createResource("ammonia_nitrogen_simulation");
    public static final Resource purpose_evapotranspiration = createResource("evapotranspiration_simulation");
    public static final Resource purpose_groundwater_level = createResource("groundwater_level_simulation");
    public static final Resource purpose_groundwater_runoff = createResource("groundwater_runoff_simulation");
    public static final Resource purpose_nitrogen = createResource("nitrogen_simulation");
    public static final Resource purpose_pesticide = createResource("pesticide_simulation");
    public static final Resource purpose_phosphorus = createResource("phosphorus_simulation");
    public static final Resource purpose_snow = createResource("snow_sublimation");
    public static final Resource purpose_soil_erosion = createResource("soil_erosion_simulation");
    public static final Resource purpose_subsurface_runoff = createResource("subsurface_runoff_simulation");
    public static final Resource purpose_surface_runoff = createResource("surface_runoff_simulation");
    public static final Resource purpose_total_runoff = createResource("total_runoff_simulation");

    public static Resource createPurpose(String applicationPurpose) {
        return Context.createResource(NS + applicationPurpose);
    }

    //total runoff simulation
    public static final Property applicationPurpose = createProperty("applicationPurpose");


    public static final Resource climate_arid = createResource("arid");
    public static final Resource climate_humid = createResource("humid");
    public static final Resource climate_semi_arid = createResource("semi_arid");
    public static final Resource climate_semi_humid = createResource("semi_humid");

    //arid
    public static final Property climateType = createProperty("climateType");


    public static final Resource surfaceInput_watershed_boundary = createResource("watershed_boundary");
    public static final Resource surfaceInput_DEM = createResource("DEM");
    public static final Resource surfaceInput_land_use = createResource("land_use");
    public static final Resource surfaceInput_soil_type = createResource("soil_type");
    public static final Resource surfaceInput_road = createResource("road");
    public static final Resource surfaceInput_lake_wetland = createResource("lake/wetland");
    public static final Resource surfaceInput_river_geometric = createResource("river_geometric");
    public static final Resource surfaceInput_reservoir_dam = createResource("reservoir/dam");
    //DEM
    public static final Property underlyingSurfaceInput = createProperty("underlyingSurfaceInput");

    public static Resource createUnderlyingSurfaceInput(String localName) {
        return Context.createResource(NS + localName);
    }

    public static final Resource timestep_yearly = createResource("yearly");
    public static final Resource timestep_monthly = createResource("monthly");
    public static final Resource timestep_daily = createResource("daily");

    public static final Resource climateInput_P = createResource("P");
    public static final Resource climateInput_PET = createResource("PET");
    public static final Resource climateInput_RH = createResource("RH");
    public static final Resource climateInput_SD = createResource("SD");
    public static final Resource climateInput_TMAX = createResource("TMAX");
    public static final Resource climateInput_TMIN = createResource("TMIN");

    //TMAX,TMIN,P
    public static final Property climateInput = createProperty("climateInput");

    public static Resource createResource(String localName) {
        return Context.createResource(NS + localName);
    }

    public static final Property adoptedModel = createProperty("adoptedModel");
    public static final Property applicableContext = createProperty("applicableContext");


    public static final Property particularProcess = createProperty("particularProcess");
    public static final Property spatialScale = createProperty("spatialScale");
    //continous
    public static final Property timeScale = createProperty("timeScale");
    public static final Property timeStep = createProperty("timeStep");

    //rural
    public static final Property underlyingSurfaceType = createProperty("underlyingSurfaceType");


    public static final Property geoLocation = createProperty("geoLocation");
    public static final Property geomorphologicalType = createProperty("geomorphologicalType");
    public static final Property hydrologicRegimes = createProperty("hydrologicRegimes");
    public static final Property hydrologicalStation = createProperty("hydrologicalStation");
    public static final Property landuseType = createProperty("landuseType");
    public static final Property soilType = createProperty("soilType");
    public static final Property spatialDiscretization = createProperty("spatialDiscretization");
    public static final Property studyArea = createProperty("studyArea");
    public static final Property task = createProperty("task");
    public static final Property timestep = createProperty("timestep");
    public static final Property timestepType = createProperty("timestepType");
    public static final Property usedByTask = createProperty("usedByTask");
    public static final Property temporalResolution = createProperty("temporalResolution");

    public static final Property areaInSquareKilometres = createProperty("areaInSquareKilometre");
    public static final Property elevation = createProperty("elevation");

    public static final Property suitableResolutionInMeters = createProperty("suitableResolutionInMeters");
    public static final Property maxOptimalDEMResolution = createProperty("maxOptimalDEMResolution");
    public static final Property minOptimalDEMResolution = createProperty("minOptimalDEMResolution");
    public static final Property mostUsedDataSource = createProperty("mostUsedDataSource");
    public static final Property recommendedDataset = createProperty("recommendedDataset");
    public static final Property recommendedDatasetDistribution = createProperty("recommendedDatasetDistribution");

    public static Property createProperty(String localName) {
        return Context.createProperty(NS, localName);
    }

}
