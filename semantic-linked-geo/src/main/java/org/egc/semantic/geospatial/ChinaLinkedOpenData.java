package org.egc.semantic.geospatial;

import org.apache.jena.rdf.model.Model;
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
public class ChinaLinkedOpenData {

    public static final String FAO = "Food And Agriculture Organization";
    public static final String NASA = "The National Aeronautics and Space Administration";

    public Model getModel() {
        CMADS();
        return model;
    }

    Model model;

    private ChinaLinkedOpenData() {
        this.model = RdfUtils.createDefaultModel();
    }

    public static ChinaLinkedOpenData getInstance() {
        return new ChinaLinkedOpenData();
    }

    public DCATDistribution CMADSDistribution(DCATDataset CMADS) {
        DCATDistribution dist = new DCATDistribution(model,
                "https://data.tpdc.ac.cn/en/data/647e6569-bd21-4bea-8acc-5d38bc4cd3c0/",
                "CMADS_download", "CMADS V1.1 Download");
        dist.setDescription("CMADS V1.1(The China Meteorological Assimilation Driving Datasets for the SWAT model Version 1.1) - Download", "en");
        dist.setDistributionType(InspireVocabulary.DOWNLOADABLE_FILE);
        dist.setCompressFormat(DataOnt.APP_7Z);
        dist.setFormat(DataOnt.APP_DBF);
        dist.setFormat(DataOnt.TEXT_PLAIN);
        dist.setIdentifier("CMADS");
        return dist;
    }

    public void CMADS() {
        DCATDataset cmads = new DCATDataset(model, "cmads", "China meteorological assimilation driving datasets for the SWAT model Version 1.1 (2008-2016) ",
                "The China Meteorological Assimilation Driving Datasets for the SWAT model (CMADS) is a public datasets developed by " +
                        "Prof. Dr. Xianyong Meng from China Agricultural University (CAU). " +
                        "CMADS incorporated technologies of LAPS/STMAS and was constructed using" +
                        " multiple technologies and scientific methods, including loop nesting of data," +
                        " projection of resampling models, and bilinear interpolation. " +
                        "The CMADS series of datasets can be used to drive various hydrological models, " +
                        "such as SWAT, the Variable Infiltration Capacity (VIC) model, " +
                        "and the Storm Water Management model (SWMM). " +
                        "It also allows users to conveniently extract a wide range of meteorological " +
                        "elements for detailed climatic analyses. Data sources for the CMADS series " +
                        "include nearly 40,000 regional automatic stations under China’s 2,421 national " +
                        "automatic and business assessment centres (Meng et al.,2017a). " +
                        "This ensures that the CMADS datasets have wide applicability within the country, " +
                        "and that data accuracy was vastly improved.");
        cmads.setKeywords("SWAT", "Meteorology", "China");
        cmads.setDescriptionUrl("http://www.cmads.org/");
        cmads.setDescriptionUrl("https://data.tpdc.ac.cn/zh-hans/data/647e6569-bd21-4bea-8acc-5d38bc4cd3c0/");
        cmads.setPrefLabel("CMADS", "en");
        cmads.setPrefLabel("SWAT模型中国大气同化驱动数据集", "zh");
        cmads.setThemes(GeoVocabulary.GCMD_ATMOSPHERE,
                InspireVocabulary.gemet_meteorology,
                InspireVocabulary.Meteorological_geographical_features);
        cmads.setVersionInfo("V1.1");
        cmads.setTemporalResolution("P1D");
        cmads.setStartDate(2008, 0, 0);
        cmads.setEndDate(2016, 0, 0);
        cmads.setSpatialResolutionInDegrees(BigDecimal.valueOf(0.25));
        cmads.setSpatialBbox(model, 60, 0, 160, 65);
        cmads.setCitation("Meng, X.; Wang, H.; Shi, C.; Wu, Y.; Ji, X.Establishment and Evaluation of the China Meteorological Assimilation Driving Datasets for the SWAT Model (CMADS).Water.10,1555. (2018).");
        cmads.setOwner("CMADS Group", "en");
        cmads.setCreator("CMADS Group", "en");
        cmads.setSpatialIdentifier(GeoVocabulary.GCMD_EASTERN_ASIA);
        cmads.setDistribution(CMADSDistribution(cmads));
    }


/*
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
    }*/


}
