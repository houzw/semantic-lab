package org.semlab.ont.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.NsIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.*;

/**
 * 注册命名空间前缀
 * @author houzhiwei
 * @date 2015年11月8日
 */
public class RegisterPrefix
{
	static String[][] preNS = new String[][] {
			{ "rdfs", RDFS.getURI() },
			{ "rdf", RDF.getURI() },
			{ "owl", OWL2.getURI() },
			{ "xsd", XSD.getURI() },
			{ "rss", RSS.getURI() },
			{ "vcard", VCARD.getURI() },
			{ "foaf", Consts.NS_FOAF },
			{ "swrl", Consts.NS_SWRL },
			{ "fn", Consts.NS_FN },
			{ "sfn", Consts.NS_SPARQL } };

	/**
	 * 给模型注册命名空间前缀
	 * 
	 * @param model
	 * @return model
	 */
	public static OntModel registerPrefixes(OntModel model)
	{
		model = registerOntPrefixes(model);
		NsIterator nsIter = model.listNameSpaces();
		String ns = null, pre = null;
		while (nsIter.hasNext())
		{
			ns = nsIter.next();
			if (StringUtils.isBlank(model.getNsURIPrefix(ns)))
			{
				pre = getPrefixStr(ns);
				if (!StringUtils.isBlank(pre))
					model.setNsPrefix(pre, ns);// 设置命名空间前缀
			}
		}
		return model;
	}

	/**
	 * 注册公用本体前缀<br/>
	 * RDF/RDFS/OWL/SWRL。。 因为部分前缀无法自动取出，<br/>
	 * 如rdf: http://www.w3.org/1999/02/22-rdf-syntax-ns#
	 * 
	 * @return
	 */
	protected static OntModel registerOntPrefixes(OntModel model)
	{
		for (int i = 0, leni = preNS.length; i < leni; i++)
		{
			model.setNsPrefix(preNS[i][0], preNS[i][1]);
		}
		return model;
	}

	/**
	 * 设置命名空间前缀
	 * 
	 * @param resource
	 * @return
	 */
	public static String setResPrefix(Resource resource)
	{
		String ns = resource.getNameSpace();
		String prefix = null;
		Model resModel = resource.getModel();
		if (resModel != null && ns != null)
		{
			// ns = ns.substring(0, ns.lastIndexOf("#") + 1);//
			// 将#后面的名称去掉，这样即使后面有/也不用担心下一行报错
			String pre = getPrefixStr(ns);
			if (StringUtils.isBlank(pre))
				return null;
			try
			{
				resModel.setNsPrefix(pre, ns);
				prefix = resModel.getNsURIPrefix(ns);
			}
			catch (Exception e)
			{
				// e.printStackTrace();
				return pre;
			}
		}
		return prefix;
	}

	public static String getPrefixStr(String ns)
	{
		String pre = null;
		for (int i = 0, leni = preNS.length; i < leni; i++)
		{
			if (ns.equalsIgnoreCase(preNS[i][1]))
				return preNS[i][0];
		}
		if (ns.indexOf('#') > -1)
		{
			ns = ns.substring(0, ns.lastIndexOf('#') + 1);
			pre = ns.substring(ns.lastIndexOf('/') + 1, ns.indexOf('#'));
			int owl = pre.indexOf(".owl");
			if (owl > -1)// 去掉.owl
				pre = pre.substring(0, owl);
		}
		return pre;
	}
}
