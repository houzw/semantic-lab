package org.egc.semantic.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.FileUtils;
import org.apache.jena.util.LocationMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;


/**
 * read & write ontology/rdf files
 * https://jena.apache.org/documentation/io/rdf-output.html
 *
 * @author houzhiwei
 * @date 2017/4/16 16:40
 */
public class OntFileUtils {
    private static String LOCATION_MAPPING_PATH = null;

    static {
        ClassPathResource resource = new ClassPathResource("config/semantic.properties");
        try {
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            LOCATION_MAPPING_PATH = properties.getProperty("jena.tdb.location-mapping");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final String LOCATION_MAPPING_LOCAL_PATH = "./src/main/resources/ont-config/location-mapping.ttl";


    /**
     * 读取本体，使用location-mapper确定引用文件地址
     *
     * @param locMapperPath the loc mapper path
     * @param baseOntPath   读取的本体文件路径
     * @return ont model
     */
    public static OntModel loadOntModelWithLocMapper(String locMapperPath, String baseOntPath) {
        OntModel model = ModelFactory.createOntologyModel();

        LocationMapper locMapper = readLocationMapper(locMapperPath);

        model.getDocumentManager().setProcessImports(true);
        FileManager fileManager = model.getDocumentManager().getFileManager();
        fileManager.setLocationMapper(locMapper);
        // 添加file:，替换\
        baseOntPath = StringUtil.getUriFilePath(baseOntPath);
        fileManager.readModel(model, baseOntPath);
        model.loadImports();

        return model;
    }

    /**
     * 读取并载入引用本体，使用默认位置的location-mapping.ttl文件
     *
     * @param baseOntPath the base ont path
     * @return ont model
     * @author Houzw at 2015年12月20日下午5:56:40
     */
    public static OntModel loadOntModelWithLocMapper(String baseOntPath) {
        OntModel model = ModelFactory.createOntologyModel();

        LocationMapper locMapper = readLocationMapper(null);
        model.getDocumentManager().setProcessImports(true);
        FileManager fileManager = model.getDocumentManager().getFileManager();
        fileManager.setLocationMapper(locMapper);
        // 添加file:，替换\
        baseOntPath = StringUtil.getUriFilePath(baseOntPath);
        fileManager.readModel(model, baseOntPath);
        model.loadImports();

        return model;
    }

    /**
     * 使用RDFDataMgr读取RDF文件
     *
     * @param fileName URI或 file:filename形式路径
     * @return ont model
     */
    public static OntModel openRDFFile(String fileName) {
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        fileName = StringUtil.getUriFilePath(fileName);
        RDFDataMgr.read(model, fileName);
        return model;
    }

    /**
     * Read model.
     *
     * @param fileName the file name
     * @param lang     the lang, e.g., {@link FileUtils#langTurtle}
     *                 null represents the default language, "RDF/XML".
     * @return the model
     * @throws IOException the io exception
     */
    public static Model read(String fileName, Lang lang) throws IOException {
        Model model = ModelFactory.createDefaultModel();
        FileInputStream is = org.apache.commons.io.FileUtils.openInputStream(new File(fileName));
        model.read(is, "urn:dummy", lang.getName());
        return model;
    }

    /**
     * Save model to file.
     *
     * @param model    the model
     * @param filepath the filepath
     * @param lang     the lang
     * @throws IOException the io exception
     */
    public static void save(Model model, String filepath, Lang lang) throws IOException {
        OutputStream os = org.apache.commons.io.FileUtils.openOutputStream(new File(filepath));
        RDFDataMgr.write(os, model, lang);
    }

    /**
     * 读取LocationMapper文件
     *
     * @param path LocationMapper path
     * @return LocationMapper
     * @author Houzw at 2015年12月20日下午5:09:18
     */
    private static LocationMapper readLocationMapper(String path) {
        if (StringUtils.isBlank(path))
            path = LOCATION_MAPPING_PATH;
        StringBuilder locSB = new StringBuilder();
        // 还需要在web环境下测试
        path = OntFileUtils.class.getResource(path).getFile();
        // 桌面环境下不需要上一行也能找到文件
        locSB.append(new File(path).getName());
        locSB.append(";");
        locSB.append(path);
        LocationMapper locMapper = new LocationMapper(locSB.toString());
        // path路径为web运行时的路径，不能用于本地运行测试
        if (!locMapper.listAltEntries().hasNext())
            locMapper = readLocalLocationMapper();
        return locMapper;
    }

    // 本地测试环境下
    private static LocationMapper readLocalLocationMapper() {
        StringBuilder locSB = new StringBuilder();
        // 还需要在web环境下测试
        String path = LOCATION_MAPPING_LOCAL_PATH;
        // 桌面环境下不需要上一行也能找到文件
        locSB.append(new File(path).getName());
        locSB.append(";");
        locSB.append(path);
        return new LocationMapper(locSB.toString());
    }


}
