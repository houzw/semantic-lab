package org.semlab.ont.query;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.core.Var;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * TODO
 *
 * @author houzhiwei
 * @date 2017/4/17 16:31
 */
public interface Select
{
    /**
     * 获得实例名称
     *
     * @param sparqlStr
     * @param model
     * @return
     * @Houzw at 2016年4月18日下午5:19:35
     */
    List<String> getIndividualLocalNames(String sparqlStr, Model model);

    /**
     * 获得查询结果中的实例
     *
     * @param sparqlStr
     * @param model
     * @return
     * @Houzw at 2016年4月18日下午5:19:32
     */
    List<Individual> getIndividuals(String sparqlStr, Model model);

    /**
     * 返回结果中实例的uri
     *
     * @param sparqlStr
     * @param model
     * @return
     * @Houzw at 2016年1月15日下午2:31:33
     */
    List<String> getIndividualURIs(String sparqlStr, Model model);

    /**
     * 根据名称查询对应的资源。可能有同名对象
     *
     * @param model
     * @param localname
     * @return
     * @Houzw at 2016年3月30日下午3:22:30
     */
    List<Resource> getResourcesByLocalName(Model model, String localname);

    /**
     * 获得实例的 SKOS label（prefLabel、altLabel和hiddenLabel，一般用来表达多语言术语）
     *
     * @param model
     * @param localname
     * @return
     * @Houzw at 2016年4月27日下午8:02:16
     */
    List<String> getSKOSLabelsByLocalName(Model model, String localname);

    /**
     * 根据名称查询对应的实例。可能有同名对象
     *
     * @param model
     * @param localname
     * @return
     * @Houzw at 2016年3月30日下午3:22:30
     */
    List<Individual> getIndividualsByLocalName(OntModel model, String localname);

    /**
     * 执行select查询 同一个resultset只能被使用一次
     *
     * @param sparqlStr
     * @param model
     * @return Json格式结果
     */
    String execSelectJSON(String sparqlStr, Model model);

    /**
     * 执行select查询
     *
     * @param sparqlStr
     * @param model
     * @return
     * @Houzw at 2016年4月28日下午9:42:48
     */
    ResultSet execSelect(String sparqlStr, Model model);

    /**
     * 获取查询语句中查询变量（subject等）
     *
     * @param sparqlStr
     * @param query
     * @return
     * @Houzw at 2016年1月15日上午11:43:51
     */
    String[] getQueryVars(Query query);

    /**
     * 嵌套类。为本接口的实现类提供公共代码
     *
     * @author houzhiwei
     */
    class BaseSelect
    {
        public String execSelectJSON(String sparqlStr, Model model)
        {
            try {
                ResultSet results = execSelect(sparqlStr, model);
                ByteArrayOutputStream bout = new ByteArrayOutputStream();
                ResultSetFormatter.outputAsJSON(bout, results);
                return new String(bout.toByteArray(), "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
                return e.getLocalizedMessage() + "\n 查询失败! 请检查SPARQL!";
            }
        }

        public ResultSet execSelect(String sparqlStr, Model model)
        {
            Query query = QueryFactory.create(sparqlStr);
            QueryExecution queryExec = QueryExecutionFactory.create(query, model);
            ResultSet results = queryExec.execSelect();
            return results;
        }

        public String[] getQueryVars(Query query)
        {
            List<Var> vars = query.getProjectVars();
            String[] varArr = new String[vars.size()];
            for (int i = 0, len = vars.size(); i < len; i++) {
                varArr[i] = vars.get(i).getVarName();
            }
            return varArr;
        }

    }
}
