package org.semlab.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * TODO
 *
 * @author houzhiwei
 * @date 2017/4/7 15:21
 */
@WebService
public interface UserWebService
{
    @WebMethod
    String getName(@WebParam (name = "userid") long id);
}
