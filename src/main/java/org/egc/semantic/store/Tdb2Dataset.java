package org.egc.semantic.store;

import lombok.extern.slf4j.Slf4j;
import org.apache.jena.dboe.base.file.Location;
import org.apache.jena.query.Dataset;
import org.apache.jena.tdb2.TDB2Factory;

/**
 * <pre/>
 * Tdb2 dataset.
 * https://jena.apache.org/documentation/tdb2/
 * https://jena.apache.org/documentation/tdb2/tdb2_cmds.html
 * @author houzhiwei
 * @date 2020 /5/8 0:39
 */
@Slf4j
public class Tdb2Dataset {

    /**
     * Gets dataset.
     *
     * @param tdb2Dir the tdb2 dir
     * @return the dataset
     */
    public static Dataset getDataset(String tdb2Dir) {
        return TDB2Factory.connectDataset(Location.create(tdb2Dir));
    }

    /**
     * Gets dataset with assembler file (assembler.ttl).
     *
     * @param assemblerFile the assembler file
     * @return the dataset with assembler
     */
    public static Dataset getDatasetWithAssembler(String assemblerFile) {
        return TDB2Factory.assembleDataset(assemblerFile);
    }
}
