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
    public static final String TPDC = "National Tibetan Plateau Data Center";

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


    public void CMADS() {
        String des = "The China Meteorological Assimilation Driving Datasets for the SWAT model (CMADS) is a public datasets developed by " +
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
                "and that data accuracy was vastly improved.";
        DCATDataset cmads = new DCATDataset(model, "cmads", "China meteorological assimilation driving datasets for the SWAT model Version 1.1 (2008-2016) ", des);
        cmads.setKeywords("SWAT", "Meteorology", "China", "CMADS");
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
        cmads.setDistribution(CMADSDistribution());
        cmads.setDistribution(CMADSBDDistribution());
        cmads.setDistribution(CMADSTpdcDistribution());
    }

    public DCATDistribution CMADSBDDistribution() {
        DCATDistribution dist = new DCATDistribution(model, "https://pan.baidu.com/s/1PwYub7uPlqvv4TxTj1FJWg",
                DataOnt.APP_DBF, "CMADS_BD_Cloud");
        dist.setDownloadUrl("https://pan.baidu.com/s/1PwYub7uPlqvv4TxTj1FJWg");
        dist.setTitle("CMADS V1.1 百度云数据下载", "zh");
        dist.setDescription("SWAT模型中国大气同化驱动数据集（CMADS V1.0）(2008-2016) - 百度云盘文件下载", "zh");
        dist.setDistributionType(InspireVocabulary.DOWNLOADABLE_FILE);
        dist.setCompressFormat(DataOnt.APP_7Z);
        dist.addFormat(DataOnt.TEXT_PLAIN);
        dist.setLanguage(InspireVocabulary.langCHN);
        dist.setIdentifier("CMADS_BD_Cloud");
        dist.setAccessRights(DataOnt.no_limitation);
        return dist;
    }

    public DCATDistribution CMADSDistribution() {
        DCATDistribution dist = new DCATDistribution(model,
                "http://data.tpdc.ac.cn/en/data/6aa7fe47-a8a1-42b6-ba49-62fb33050492/",
                DataOnt.APP_DBF, "CMADS_TPDC_Download_EN");
        dist.setTitle("CMADS V1.1 Data Download");
        dist.setDescription("CMADS V1.1(The China Meteorological Assimilation Driving Datasets for the SWAT model Version 1.1) - Download", "en");
        dist.setDistributionType(InspireVocabulary.DOWNLOADABLE_FILE);
        dist.setCompressFormat(DataOnt.APP_7Z);
        dist.addFormat(DataOnt.APP_DBF);
        dist.setAccessRights(DataOnt.authorization_required);
        dist.addFormat(DataOnt.TEXT_PLAIN);
        dist.setIdentifier("CMADS_TPDC_ENG");
        dist.setLanguage(InspireVocabulary.langENG);
        return dist;
    }

    public DCATDistribution CMADSTpdcDistribution() {
        DCATDistribution dist = new DCATDistribution(model,
                "https://data.tpdc.ac.cn/en/data/647e6569-bd21-4bea-8acc-5d38bc4cd3c0/",
                DataOnt.APP_DBF, "CMADS_download");
        dist.setTitle("CMADS V1.1 数据下载", "zh");
        dist.setDescription("SWAT模型中国大气同化驱动数据集（CMADS V1.0）(2008-2016) - 文件下载", "zh");
        dist.setDistributionType(InspireVocabulary.DOWNLOADABLE_FILE);
        dist.setCompressFormat(DataOnt.APP_7Z);
        dist.addFormat(DataOnt.APP_DBF);
        dist.addFormat(DataOnt.TEXT_PLAIN);
        dist.setAccessRights(DataOnt.authorization_required);
        dist.setIdentifier("CMADS_TPDC_CHN");
        dist.setLanguage(InspireVocabulary.langCHN);
        return dist;
    }


    //hwsd_China

}
