package org.egc.semantic.test;

import com.alibaba.fastjson.JSONObject;
import org.apache.jena.ontology.OntModel;
import org.egc.semantic.rdf.OntModelUtils;
import org.egc.semantic.util.OntFile;
import org.junit.Test;

import java.io.File;

/**
 * @author houzhiwei
 * @date 2020/9/5 14:36
 */
public class OntTest {

    @Test
    public void readOwl(){
        String owl = new File("src/test/resources/spatialrelations.owl").getAbsolutePath();
        OntModel ontModel = OntFile.openRDFFile(owl);
//        System.out.println(OntModelUtils.getAllNs(ontModel));
        System.out.println(JSONObject.toJSONString(OntModelUtils.getAllNs(ontModel), true));
    }

}
