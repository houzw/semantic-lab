package org.egc.semantic.test;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Statement;
import org.egc.semantic.store.impl.TdbStatementImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.egc.semantic.store.alternative.TdbDataset;
import org.egc.semantic.store.TdbStatement;
import org.egc.semantic.store.impl.TdbDatasetImpl;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * TODO
 *
 * @author houzhiwei
 * @date 2017/4/16 22:00
 */
public class TDBTests
{
    protected TdbDataset tdbConnection = null;
    protected String modelUri = "http://www.egc.org/ont/dta/test#";

    protected String namedModel = "test";

    protected String subject = modelUri + "RF";
    protected String property = modelUri + "hasEquation";
    protected String object = modelUri + "RF-Equation";

    @Before
    public void setup()
    {
//        String path = "src/test/resources/ont-config2/assembler2.ttl";
//        File file = new File(path);
//        InputStream in = getClass().getResourceAsStream(path);

        tdbConnection = new TdbDatasetImpl(new File("src/test/resources/ont-config/assembler.ttl"));
    }
    @Test
    public void tdbtest(){
        Model tdbmodel =tdbConnection.getDefaultModel();
        System.out.println(tdbmodel);
//        Model graph = tdbConnection.getModel("name");
    }

    @Test
    public void stmttest()
    {
//        System.out.println(tdbConnection.listModels());
        TdbStatement tdbStatement = new TdbStatementImpl(tdbConnection.getDataset());
        tdbStatement.addStatement(namedModel,subject,property,object);
        List<Statement> result = tdbStatement.getStatements(namedModel, null, null, object);
        System.out.println( namedModel + " size: " + result.size() + "\n\t" + result );
        assertTrue( result.size() > 0);
    }

    @After
    public void teardown(){
        tdbConnection.close();
    }
}

