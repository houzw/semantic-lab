package org.egc.semantic.query.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.jena.rdf.model.Model;
import org.egc.semantic.query.Construct;
import org.springframework.stereotype.Service;

/**
 * Description:
 * <pre>
 *
 * </pre>
 *
 * @author houzhiwei
 * @date 2019/10/6 9:43
 */
@Slf4j
@Service
public class ConstructImpl implements Construct {

    @Override
    public void printResult(Model constructResult) {
        constructResult.write(System.out, "TURTLE");
    }
}
