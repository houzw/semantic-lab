package org.egc.semantic.infer;

/**
 * Description:
 * <pre>
 *
 * </pre>
 *
 * @author houzhiwei
 * @date 2019/5/18 16:40
 */
public interface ShaclValidator {

    /**
     * validate ontology using SHACL defined shapes
     * @return
     */
    boolean validateShacl();
}
