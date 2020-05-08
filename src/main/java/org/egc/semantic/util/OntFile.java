package org.egc.semantic.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.LocationMapper;
import org.egc.commons.util.PropertiesUtil;

import java.io.File;

/**
 * TODO
 *
 * @author houzhiwei
 * @date 2017/4/16 16:40
 */
public class OntFile
{
    private static final String LOCATION_MAPPING_PATH = PropertiesUtil.getPropertyFromConfig("jena.tdb.location-mapping","semantic");
    private static final String LOCATION_MAPPING_LOCALPATH = "./src/main/resources/ont-config/location-mapping.ttl";

    /**
     * 读取本体，使用location-mapper确定引用文件地址
     *
     * @param locMapperPath the loc mapper path
     * @param baseOntPath   读取的本体文件路径
     * @return ont model
     */
    public static OntModel loadOntModelWithLocMapper(String locMapperPath, String baseOntPath)
    {
        OntModel model = ModelFactory.createOntologyModel();

        LocationMapper locMapper = readLocationMapper(locMapperPath);

        model.getDocumentManager().setProcessImports(true);
        FileManager fileManager = model.getDocumentManager().getFileManager();
        fileManager.setLocationMapper(locMapper);
        baseOntPath = StringUtil.getUriFilePath(baseOntPath);// 添加file:，替换\
        fileManager.readModel(model, baseOntPath);
        model.loadImports();

        return model;
    }

    /**
     * 读取并载入引用本体，使用默认位置的location-mapping.ttl文件
     *
     * @param baseOntPath the base ont path
     * @return ont model
     * @Houzw at 2015年12月20日下午5:56:40
     */
    public static OntModel loadOntModelWithLocMapper(String baseOntPath)
    {
        OntModel model = ModelFactory.createOntologyModel();

        LocationMapper locMapper = readLocationMapper(null);

        model.getDocumentManager().setProcessImports(true);
        FileManager fileManager = model.getDocumentManager().getFileManager();
        fileManager.setLocationMapper(locMapper);
        baseOntPath = StringUtil.getUriFilePath(baseOntPath);// 添加file:，替换\
        fileManager.readModel(model, baseOntPath);
        model.loadImports();

        return model;
    }

    /**
     * 读取LocationMapper文件
     *
     * @param path
     * @return
     * @Houzw at 2015年12月20日下午5:09:18
     */
    private static LocationMapper readLocationMapper(String path)
    {
        if (StringUtils.isBlank(path))
            path = LOCATION_MAPPING_PATH;
        StringBuilder locSB = new StringBuilder();
        path = OntFile.class.getResource(path).getFile();// 还需要在web环境下测试
        locSB.append(new File(path).getName());// 桌面环境下不需要上一行也能找到文件
        locSB.append(";");
        locSB.append(path);
        LocationMapper locMapper = new LocationMapper(locSB.toString());
        if (!locMapper.listAltEntries().hasNext())// path路径为web运行时的路径，不能用于本地运行测试
            locMapper = readLocalLocationMapper();
        return locMapper;
    }

    // 本地测试环境下
    private static LocationMapper readLocalLocationMapper()
    {
        StringBuilder locSB = new StringBuilder();
        String path = LOCATION_MAPPING_LOCALPATH;// 还需要在web环境下测试
        locSB.append(new File(path).getName());// 桌面环境下不需要上一行也能找到文件
        locSB.append(";");
        locSB.append(path);
        LocationMapper locMapper = new LocationMapper(locSB.toString());
        return locMapper;
    }
}
