package org.semlab.ont.test;

import org.apache.jena.query.Dataset;
import org.apache.jena.tdb.TDBFactory;
import org.junit.Test;

/**
 * TODO
 *
 * @author houzhiwei
 * @date 2017/4/24 14:55
 */
public class TDBTests2
{
    @Test
    public void testtdb(){
        String path ="F:/workspace/semantic-lab/semantic-lab-ont/src/test/resources/ont-config/assembler.ttl";
        String path2 = "src/test/resources/ont-config/assembler.ttl";
        String assemblerFile =  path;
        Dataset ds = TDBFactory.assembleDataset(assemblerFile) ;
        // ... do work ...
        System.out.println(ds.getDefaultModel());
        ds.close() ;
    }
}
