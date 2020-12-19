package org.egc.semantic;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.FileDocumentTarget;
import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi.model.*;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * read & write ontology/rdf files
 * https://jena.apache.org/documentation/io/rdf-output.html
 *
 * @author houzhiwei
 * @date 2017/4/16 16:40
 */
public class OntFileUtils {

    public static OWLOntology load(@NotNull File owlFile) throws OWLOntologyCreationException {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        return manager.loadOntologyFromOntologyDocument(owlFile);
    }

    /**
     * <pre>
     * Save Ontology.
     *
     *
     * @param ont     the Ontology
     * @param ontFile the target Ontology file
     * @param format  the format, e.g., {@code
     *      TurtleDocumentFormatFactory formatFactory = new TurtleDocumentFormatFactory();
     *      formatFactory.createFormat();
     * }
     * @throws OWLOntologyStorageException  the owl ontology storage exception
     */
    public static void save(OWLOntology ont, @NotNull File ontFile, OWLDocumentFormat format) throws OWLOntologyStorageException {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntologyDocumentTarget target = new FileDocumentTarget(ontFile);
        manager.saveOntology(ont, format, target);
    }

    public static Set<String> getDataPropertyValues(OWLOntology ont, String namespace, String localName, String dataPropertyName) {
        OWLDataFactory df = ont.getOWLOntologyManager().getOWLDataFactory();
        OWLNamedIndividual i = df.getOWLNamedIndividual(namespace, localName);
        return ont.dataPropertyAssertionAxioms(i)
                .filter(axiom -> axiom.getProperty().asOWLDataProperty().getIRI().getShortForm().equalsIgnoreCase(dataPropertyName))
                .map(OWLDataPropertyAssertionAxiom::getObject)
                .map(OWLLiteral::getLiteral).collect(Collectors.toSet());
    }

}
