package org.egc.semantic.query;

import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.impl.SimpleLiteral;
import org.eclipse.rdf4j.model.impl.TreeModel;
import org.eclipse.rdf4j.model.util.Models;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.query.*;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.config.RepositoryConfig;
import org.eclipse.rdf4j.repository.config.RepositoryConfigSchema;
import org.eclipse.rdf4j.repository.http.HTTPRepository;
import org.eclipse.rdf4j.repository.manager.LocalRepositoryManager;
import org.eclipse.rdf4j.repository.manager.RepositoryManager;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParser;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.helpers.StatementCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * https://henrietteharmse.com/2018/06/29/getting-started-with-ontotext-graphdb-and-rdf4j/
 * https://henrietteharmse.com/2018/06/29/getting-started-with-ontotext-and-jena/
 * https://graphdb.ontotext.com/documentation/free/geosparql-support.html
 *
 * @author houzhiwei
 * @date 2020/12/16 14:50
 */
public class GraphDbRdf4j {
    private static final Logger log = LoggerFactory.getLogger(GraphDbRdf4j.class);

    private static final String REPO_SERVER =
            "http://localhost:7200/repositories";
    private static final String REPO_ID = "egc_model";

    private static final String REPO_QUERY =
            "http://localhost:7200/repositories/egc_model";
    private static final String REPO_UPDATE =
            "http://localhost:7200/repositories/egc_model/statements";

    private String repoServer;
    private String repoId;

    public GraphDbRdf4j(String repoServer, String repoId) {
        this.repoId = repoId;
        this.repoServer = repoServer;
    }

    private RepositoryConnection localRepositoryConnection;
    private Repository localRepository;
    private RepositoryManager localRepositoryManager;


    public RepositoryConnection getRepositoryConnection() {
        Repository repository = new HTTPRepository(this.repoServer, this.repoId);
        repository.init();
        return repository.getConnection();
    }

    public void insert(RepositoryConnection repositoryConnection, String strInsert) {
        repositoryConnection.begin();
        Update updateOperation = repositoryConnection.prepareUpdate(QueryLanguage.SPARQL, strInsert);
        updateOperation.execute();
        try {
            repositoryConnection.commit();
        } catch (Exception e) {
            if (repositoryConnection.isActive()) {
                repositoryConnection.rollback();
            }
            log.error(e.getLocalizedMessage());
        }
    }

    public void enableGeoSPARQL(RepositoryConnection repositoryConnection) {
        String enable = "PREFIX geo: <http://www.ontotext.com/plugins/geosparql#>\n" +
                "INSERT DATA { _:s geo:enabled \"true\" . }";
        insert(repositoryConnection, enable);
    }

    public void query(RepositoryConnection repositoryConnection, String strQuery) {
        TupleQuery tupleQuery = repositoryConnection.prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        TupleQueryResult result = null;
        try {
            result = tupleQuery.evaluate();
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral name = (SimpleLiteral) bindingSet.getValue("name");
                log.trace("name = " + name.stringValue());
            }
        } catch (QueryEvaluationException qee) {
            log.error(Arrays.toString(qee.getStackTrace()), qee);
        } finally {
            assert result != null;
            result.close();
        }
    }

    /* ************************************************************ */

    /**
     * Gets local repository connection.
     * https://henrietteharmse.com/2018/04/09/creating-a-local-repository-for-graphdb-with-rdf4j-programmatically/
     *
     * @param strRepositoryConfig the str repository config
     * @param repositoryId        the repository id
     * @throws IOException the io exception
     */
    public void getLocalRepositoryConnection(String strRepositoryConfig, String repositoryId) throws IOException {
        // Instantiate a local repository manager and initialize it
        RepositoryManager repositoryManager = new LocalRepositoryManager(new File("."));
        repositoryManager.init();
        this.localRepositoryManager = repositoryManager;
        // Instantiate a repository graph model
        TreeModel graph = new TreeModel();
        // Read repository configuration file
        InputStream config = new FileInputStream(strRepositoryConfig);
        RDFParser rdfParser = Rio.createParser(RDFFormat.TURTLE);
        rdfParser.setRDFHandler(new StatementCollector(graph));
        rdfParser.parse(config, RepositoryConfigSchema.NAMESPACE);
        config.close();
        // Retrieve the repository node as a resource
        Resource repositoryNode = Models.subject(graph.filter(null, RDF.TYPE, RepositoryConfigSchema.REPOSITORY))
                .orElseThrow(() -> new RuntimeException("Oops, no <http://www.openrdf.org/config/repository#> subject found!"));

        // Create a repository configuration object and add it to the repositoryManager
        RepositoryConfig repositoryConfig = RepositoryConfig.create(graph, repositoryNode);
        repositoryManager.addRepositoryConfig(repositoryConfig);

        // Get the repository from repository manager, note the repository id
        // set in configuration .ttl file
        Repository repository = repositoryManager.getRepository(repositoryId);
        this.localRepository = repository;
        // Open a connection to this repository
        this.localRepositoryConnection = repository.getConnection();
        // ... use the repository
    }

    /**
     * Shutdown connection, repository and manager
     */
    public void closeLocalRepositoryConnection() {
        if (this.localRepositoryConnection != null && this.localRepositoryConnection.isActive()) {
            this.localRepositoryConnection.close();
        }
        if (this.localRepository != null) {
            this.localRepository.shutDown();
        }
        if (this.localRepositoryManager != null) {
            this.localRepositoryManager.shutDown();
        }
    }

}
