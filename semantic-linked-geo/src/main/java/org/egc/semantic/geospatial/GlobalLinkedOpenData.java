package org.egc.semantic.geospatial;

import org.apache.jena.rdf.model.Model;
import org.egc.semantic.dto.DCATDataService;
import org.egc.semantic.dto.DCATDataset;
import org.egc.semantic.dto.DCATDistribution;
import org.egc.semantic.rdf.RdfUtils;
import org.egc.semantic.vocab.DataOnt;
import org.egc.semantic.vocab.GeoVocabulary;
import org.egc.semantic.vocab.InspireVocabulary;

import java.math.BigDecimal;

/**
 * @author houzhiwei
 * @date 2020/12/13 11:39
 */
public class GlobalLinkedOpenData {

    public static final String FAO = "Food And Agriculture Organization";
    public static final String NASA = "The National Aeronautics and Space Administration";
    public static final String USGS = "U.S. Geological Survey";

    public Model getModel() {
        digitalSoilMapOfTheWorld();
        glccLanuse();
        srtm();
        AW3D30();
        hwsd();
        return model;
    }

    Model model;

    private GlobalLinkedOpenData() {
        this.model = RdfUtils.createDefaultModel();
    }

    public static GlobalLinkedOpenData getInstance() {
        return new GlobalLinkedOpenData();
    }

    /************************************************
     *          Digital Soil Map of the World
     * ***********************************************/

    public void digitalSoilMapOfTheWorld() {
        DCATDataset dsm = new DCATDataset(model, "dsmw", "The Digital Soil Map of the World",
                "FAO Digital Soil Map of the World (DSMW)");
        dsm.setKeywords("soil", "FAO", "digital soil map", "world");
        dsm.setDescriptionUrl("https://swat.tamu.edu/media/116412/readme.pdf");
        dsm.setDescriptionUrl("http://www.fao.org/land-water/land/land-governance/land-resources-planning-toolbox/category/details/en/c/1026564/");
        dsm.setIssued(200, 1, 1);
        dsm.setPrefLabel("FAO Digital Soil Map of the World", "en");
        dsm.setThemes(GeoVocabulary.GCMD_SOIL, InspireVocabulary.Soil, InspireVocabulary.gemet_soil);
        dsm.setVersionInfo("3.6");
        dsm.setSpatialBbox(model, -180, -56.57045621787, 180, 84.5989698307553, 4326);
        dsm.setOwner(FAO, "en");
        dsm.setSpatialIdentifier(GeoVocabulary.GCMD_GLOBAL_LAND);
        dsm.setDistribution(dsmwDistribution(dsm));
    }

    public DCATDistribution dsmwDistribution(DCATDataset dsm) {
        DCATDistribution dist = new DCATDistribution(model,
                "http://localhost:8088/geoserver/ows?service=WCS&version=2.0.1&request=GetCapabilities",
                DataOnt.FORMAT_API, "dsmw_wcs");
        dist.setDescription("Global Soil Map EGC Web Coverage Service (WCS) - GetCapabilities", "en");
        dist.setDistributionType(InspireVocabulary.WEB_SERVICE);
        dist.setTitle("Digital Soil Map of the World WCS");
        //coverageId
        dist.setIdentifier("global__soil");
        dist.setSpatialResolutionInDegrees(BigDecimal.valueOf(0.06751287711555500892));
        dist.setAccessService(globalService(dsm));
        return dist;
    }

    /***********************************************
     *        Harmonized World Soil Database
     * *********************************************/

    public void hwsd() {
        DCATDataset hwsd = new DCATDataset(model, "hwsd", "Harmonized World Soil Database",
                "The HWSD is a 30 arc-second raster database with over" +
                        " 16000 different soil mapping units that combines existing" +
                        " regional and national updates of soil information worldwide (SOTER, ESD, Soil Map of China, WISE)" +
                        " with the information contained within the " +
                        "1:5 000 000 scale FAO-UNESCO Soil Map of the World (FAO, 19711981).");
        hwsd.setKeywords("soil", "FAO", "digital soil map", "world");
        hwsd.setDescriptionUrl("http://www.fao.org/soils-portal/soil-survey/soil-maps-and-databases/harmonized-world-soil-database-v12/en/");
        //hwsd.setDescriptionUrl("http://webarchive.iiasa.ac.at/Research/LUC/External-World-soil-database/HTML/index.html?sb=1");
        hwsd.setIssued(2012, 3, 7);
        hwsd.setPrefLabel("Harmonized World Soil Database v 1.2", "en");
        hwsd.setThemes(GeoVocabulary.GCMD_SOIL, InspireVocabulary.Soil, InspireVocabulary.gemet_soil);
        hwsd.setVersionInfo("1.21");
        hwsd.setSpatialBbox(model, -180, -56.57045621787, 180, 84.5989698307553, 4326);
        hwsd.setCitation("FAO/IIASA/ISRIC/ISSCAS/JRC, 2012. Harmonized World Soil Database (version 1.2). FAO, Rome, Italy and IIASA, Laxenburg, Austria.");
        hwsd.setOwner(FAO, "en");
        hwsd.setSpatialIdentifier(GeoVocabulary.GCMD_GLOBAL_LAND);
        hwsd.setDistribution(hwsdFaoDistribution(hwsd));
    }


    public DCATDistribution hwsdFaoDistribution(DCATDataset ds) {
        //FORMAT_EHdr bil
        DCATDistribution dist = new DCATDistribution(model,
                "http://www.fao.org/soils-portal/soil-survey/soil-maps-and-databases/harmonized-world-soil-database-v12/en/",
                DataOnt.FORMAT_EHdr, "hwsd_fao");
        dist.setDescription("Download viewer & data (only soil types), database (.mdb), HWSD Raster, and Technical Report and Instructions", "en");
        dist.setDistributionType(InspireVocabulary.DOWNLOADABLE_FILE);
        dist.setAccessRights(DataOnt.no_limitation);
        dist.setDownloadUrl("http://www.fao.org/fileadmin/user_upload/soils/HWSD%20Viewer/HWSD_viewer_setup.exe");
        dist.setDownloadUrl("http://www.fao.org/fileadmin/user_upload/soils/HWSD%20Viewer/HWSD.zip");
        dist.setDownloadUrl("http://www.fao.org/fileadmin/user_upload/soils/HWSD%20Viewer/HWSD_RASTER.zip");
        dist.setTitle("Harmonized World Soil Database - FAO SOILS PORTAL");
        dist.setCompressFormat(DataOnt.APP_ZIP);
        //30 arc second
        dist.setSpatialResolutionInDegrees(BigDecimal.valueOf(0.477464829275686));
//        dist.setAccessService(globalService(ds));
        return dist;
    }

    /***********************************************
     *       Global Land Cover Characteristics
     * *********************************************/

    public void glccLanuse() {
        DCATDataset landuse = new DCATDataset(model, "glcc", "Global Land Cover Characteristics",
                "A global land cover database primarily derived from 1992 to 1993 1-km AVHRR data.");
        landuse.setKeywords("landuse", "USGS", "landuse map", "world");
        landuse.setDescriptionUrl("https://www.usgs.gov/centers/eros/science/usgs-eros-archive-land-cover-products-global-land-cover-characterization-glcc");
//        landuse.setIssued("2003-01-01");
        landuse.setPrefLabel("USGS Global Land Cover Characterization (GLCC)", "en");
        landuse.setThemes(GeoVocabulary.GCMD_Landuse, InspireVocabulary.Land_use, InspireVocabulary.Land_cover);
        landuse.setVersionInfo("Version 2");
        //TODO
        landuse.setSpatialBbox(model, -180, -56.57045621787, 180, 84.5989698307553, 4326);
        landuse.setOwner(USGS, "en");
        landuse.setSpatialIdentifier(GeoVocabulary.GCMD_GLOBAL_LAND);
        landuse.setDistribution(landuseDistribution(landuse));
    }

    public DCATDistribution landuseDistribution(DCATDataset landuse) {
        DCATDistribution land_dist = new DCATDistribution(model,
                "http://localhost:8088/geoserver/ows?service=WCS&version=2.0.1&request=GetCapabilities",
                DataOnt.FORMAT_API, "glcc_wcs");
        land_dist.setTitle("Global Land Cover Characterization");
        land_dist.setDescription("Global Land Cover Map EGC Web Coverage Service (WCS) - GetCapabilities", "en");
        land_dist.setDistributionType(InspireVocabulary.WEB_SERVICE);
        land_dist.addFormat(DataOnt.FORMAT_API);
        land_dist.setPrefLabel("USGS Global Land Cover Characterization (GLCC) of the World WCS", "en");
        land_dist.setSpatialResolutionInMeters(BigDecimal.valueOf(400));
        land_dist.setAccessService(globalService(landuse));
        land_dist.setAccessRights(DataOnt.no_limitation);
        //coverageId
        land_dist.setIdentifier("global__landuse");
        return land_dist;
    }

    /***********************************************
     *       global data web coverage service
     * *********************************************/

    public DCATDataService globalService(DCATDataset... dataset) {
        DCATDataService service = new DCATDataService(model, "http://localhost:8088/geoserver/ows",
                "egc global wcs", "EGC Global Data WCS");
        service.setOgcServiceType(GeoVocabulary.wcs);
        service.setServiceStandard(GeoVocabulary.wcs_standard);
        service.setSpatialDataServiceType(InspireVocabulary.serviceType_Download);
        service.setServedDatasets(dataset);
        service.setServiceType(InspireVocabulary.serviceCategory_CoverageAccessService);
        service.setEndpointDescriptionUrl("http://localhost:8088/geoserver/ows?service=WCS&version=2.0.1&request=GetCapabilities");
        service.setKeywords("Web Coverage Service", "Global");
        service.setPublisher("EasyGC", "en");
        return service;
    }


    /***********************************************
     *       global data web coverage service
     * *********************************************/

    String openTopo = "https://www.opentopography.org/developers#API";

    public void AW3D30() {
        DCATDataset aw3d30 = new DCATDataset(model, "aw3d30", "Shuttle Radar Topography Mission",
                "The ALOS Global Digital Surface Model (AW3D30) is a global dataset generated " +
                        "from images collected using the Panchromatic Remote-sensing Instrument for Stereo Mapping (PRISM)" +
                        " aboard the Advanced Land Observing Satellite (ALOS) from 2006 to 2011. As described by the Japan " +
                        "Aerospace Exploration Agency: The Japan Aerospace Exploration Agency (JAXA) releases the global " +
                        "digital surface model (DSM) dataset with a horizontal resolution of approx. 30-meter mesh (1 arcsec) " +
                        "free of charge. The dataset has been compiled with images acquired by the " +
                        "Advanced Land Observing Satellite \"DAICHI\" (ALOS). The dataset is published based on the " +
                        "DSM dataset (5-meter mesh version) of the \"World 3D Topographic Data\", which is the most " +
                        "precise global-scale elevation data at this time, and its elevation precision is also at a world-leading level " +
                        "as a 30-meter mesh version. This dataset is expected to be useful for scientific research, " +
                        "education, as well as the private service sector that uses geospatial information.");
        aw3d30.setKeywords("AW3D30", "DEM", "digital elevation model", "world", "ALOS");
        aw3d30.setDescriptionUrl("https://www.eorc.jaxa.jp/ALOS/en/aw3d30/");
        aw3d30.setIssued(2006, 1, 1);
        aw3d30.setPrefLabel("The Shuttle Radar Topography Mission (SRTM)", "en");
        aw3d30.setThemes(GeoVocabulary.GCMD_DEM, InspireVocabulary.DEM, InspireVocabulary.gemet_altitude);
        aw3d30.setVersionInfo("3.0");
        //SIS 有问题
        aw3d30.setSpatialBbox(model, -180, -60, 180, 60, 4326);
        //
        aw3d30.setOwner("The National Aeronautics and Space Administration", "en");
        aw3d30.setSpatialIdentifier(GeoVocabulary.GCMD_GLOBAL_LAND);
        aw3d30.setDistribution(aw3d30Distribution(aw3d30));
    }

    public DCATDistribution aw3d30Distribution(DCATDataset aw3d30) {
        DCATDistribution dist = new DCATDistribution(model, openTopo, DataOnt.FORMAT_OpenAPI, "aw3d30");
        dist.setTitle("ALOS World 3D - 30m");
        dist.setDescription("ALOS World 3D - 30m", "en");
        dist.setDistributionType(InspireVocabulary.WEB_SERVICE);
        dist.setDescribedBy("https://portal.opentopography.org/raster?opentopoID=OTALOS.112016.4326.2");
        dist.setDescribedByType(DataOnt.APP_OPENAPI_JSON);
        dist.addFormat(DataOnt.FORMAT_OpenAPI);
        dist.setSpatialResolutionInMeters(BigDecimal.valueOf(30));
        dist.setAccessService(openTopographyAPI(aw3d30));
        dist.setIdentifier("AW3D30");
        dist.setAccessRights(DataOnt.no_limitation);
        return dist;
    }

    public void srtm() {
        DCATDataset srtm = new DCATDataset(model, "srtm", "Shuttle Radar Topography Mission",
                "The Shuttle Radar Topography Mission (SRTM) obtained elevation data on a near-global scale to generate the most complete high-resolution digital topographic database of Earth. " +
                        "SRTM consisted of a specially modified radar system that flew onboard the Space Shuttle Endeavour during an 11-day mission in February of 2000. " +
                        "SRTM is an international project spearheaded by the National Geospatial-Intelligence Agency (NGA) and the National Aeronautics and Space Administration (NASA). ");
        //coverageId
        srtm.setIdentifier("SRTM");
        srtm.setKeywords("SRTM", "DEM", "digital elevation model", "world");
        srtm.setDescriptionUrl("https://www2.jpl.nasa.gov/srtm/");
        srtm.setIssued(2000, 2, 11);
        srtm.setPrefLabel("The Shuttle Radar Topography Mission (SRTM)", "en");
        srtm.setThemes(GeoVocabulary.GCMD_DEM, InspireVocabulary.DEM, InspireVocabulary.gemet_altitude);
        srtm.setVersionInfo("Version 3");
        srtm.setSpatialBbox(model, -180, -56, 180, 60, 4326);
        System.out.println(srtm.getSpatialString());
        srtm.setOwner(NASA, "en");
        srtm.setSpatialIdentifier(GeoVocabulary.GCMD_GLOBAL_LAND);
        srtm.setDistribution(srtmGL1Distribution(srtm));
        srtm.setDistribution(srtmGL3Distribution(srtm));
    }


    public DCATDistribution srtmGL1Distribution(DCATDataset srtmgl1) {
        DCATDistribution dist = new DCATDistribution(model, openTopo, DataOnt.FORMAT_OpenAPI, "srtmgl1");
        dist.setTitle("SRTM GL1");
        dist.setDescription("Shuttle Radar Topography Mission (SRTM GL1) Global 30m", "en");
        dist.setDistributionType(InspireVocabulary.WEB_SERVICE);
        dist.setDescribedBy("https://portal.opentopography.org/raster?opentopoID=OTSRTM.082015.4326.1");
        dist.setDescribedByType(DataOnt.APP_OPENAPI_JSON);
        dist.addFormat(DataOnt.FORMAT_OpenAPI);
        dist.setSpatialResolutionInMeters(BigDecimal.valueOf(30));
        dist.setAccessService(openTopographyAPI(srtmgl1));
        dist.setIdentifier("SRTMGL1");
        dist.setAccessRights(DataOnt.no_limitation);
        return dist;
    }

    public DCATDistribution srtmGL3Distribution(DCATDataset srtmgl3) {
        DCATDistribution dist = new DCATDistribution(model, openTopo, DataOnt.FORMAT_OpenAPI, "srtmgl3");
        dist.setDescription("Shuttle Radar Topography Mission (SRTM GL3) Global 90m", "en");
        dist.setTitle("SRTM GL3");
        dist.setDistributionType(InspireVocabulary.WEB_SERVICE);
        dist.setDescribedBy("https://portal.opentopography.org/raster?opentopoID=OTSRTM.042013.4326.1");
        dist.setDescribedByType(DataOnt.APP_OPENAPI_JSON);
        dist.setSpatialResolutionInMeters(BigDecimal.valueOf(90));
        dist.setAccessService(openTopographyAPI(srtmgl3));
        dist.setIdentifier("SRTMGL3");
        dist.setAccessRights(DataOnt.no_limitation);
        return dist;
    }

    public DCATDataService openTopographyAPI(DCATDataset... dataset) {
        DCATDataService service = new DCATDataService(model, "https://portal.opentopography.org/apidocs/",
                "opentopo", "OpenTopography API");
//        service.setServiceStandard(GeoVocabulary.wcs_standard);
        service.setDescription("RESTful Web service for accessing Shuttle Radar Topography Mission GL3 (Global 90m), GL1 (Global 30m), GL1 Ellipsoidal, " +
                " ALOS World 3D (Global 30m), and ALOS World 3D Ellipsoidal data.", "en");
        service.setSpatialDataServiceType(InspireVocabulary.serviceType_Download);
        service.setServedDatasets(dataset);
        service.setEndpointDescriptionUrl("https://portal.opentopography.org/apidocs/openapi.json");
        service.setKeywords("RESTFul Web Service", "Global", "DEM");
        service.setServiceType(InspireVocabulary.serviceCategory_CoverageAccessService);
        service.setPublisher("OpenTopography", "en");
        return service;
    }
}
