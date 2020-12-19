package org.egc.semantic.test;

import com.alibaba.fastjson.JSONObject;
import org.apache.jena.ontology.OntModel;
import org.egc.semantic.owl.OntModelUtils;
import org.egc.semantic.utils.OntFileUtils;
import org.junit.Test;

import java.io.File;

/**
 * @author houzhiwei
 * @date 2020/9/5 14:36
 */
public class OntTest {

    @Test
    public void readOwl(){
        String owl = new File("src/test/resources/OntoEGC.owl").getAbsolutePath();
        OntModel ontModel = OntFileUtils.openRDFFile(owl);
//        System.out.println(OntModelUtils.getAllNs(ontModel));
        System.out.println(JSONObject.toJSONString(OntModelUtils.getAllClasses(ontModel), true));
    }

}
