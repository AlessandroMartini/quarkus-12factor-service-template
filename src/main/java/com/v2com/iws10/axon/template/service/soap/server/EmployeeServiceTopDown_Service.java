
package com.v2com.iws10.axon.template.service.soap.server;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "EmployeeServiceTopDown", targetNamespace = "http://topdown.server.jaxws.v2com.com/", wsdlLocation = "http://localhost:8085/employeeservicetopdown?wsdl")
public class EmployeeServiceTopDown_Service
    extends Service
{

    private final static URL EMPLOYEESERVICETOPDOWN_WSDL_LOCATION;
    private final static WebServiceException EMPLOYEESERVICETOPDOWN_EXCEPTION;
    private final static QName EMPLOYEESERVICETOPDOWN_QNAME = new QName("http://topdown.server.jaxws.v2com.com/", "EmployeeServiceTopDown");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8085/employeeservicetopdown?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        EMPLOYEESERVICETOPDOWN_WSDL_LOCATION = url;
        EMPLOYEESERVICETOPDOWN_EXCEPTION = e;
    }

    public EmployeeServiceTopDown_Service() {
        super(__getWsdlLocation(), EMPLOYEESERVICETOPDOWN_QNAME);
    }

    public EmployeeServiceTopDown_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), EMPLOYEESERVICETOPDOWN_QNAME, features);
    }

    public EmployeeServiceTopDown_Service(URL wsdlLocation) {
        super(wsdlLocation, EMPLOYEESERVICETOPDOWN_QNAME);
    }

    public EmployeeServiceTopDown_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, EMPLOYEESERVICETOPDOWN_QNAME, features);
    }

    public EmployeeServiceTopDown_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public EmployeeServiceTopDown_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns EmployeeServiceTopDown
     */
    @WebEndpoint(name = "EmployeeServiceTopDownSOAP")
    public EmployeeServiceTopDown getEmployeeServiceTopDownSOAP() {
        return super.getPort(new QName("http://topdown.server.jaxws.v2com.com/", "EmployeeServiceTopDownSOAP"), EmployeeServiceTopDown.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns EmployeeServiceTopDown
     */
    @WebEndpoint(name = "EmployeeServiceTopDownSOAP")
    public EmployeeServiceTopDown getEmployeeServiceTopDownSOAP(WebServiceFeature... features) {
        return super.getPort(new QName("http://topdown.server.jaxws.v2com.com/", "EmployeeServiceTopDownSOAP"), EmployeeServiceTopDown.class, features);
    }

    private static URL __getWsdlLocation() {
        if (EMPLOYEESERVICETOPDOWN_EXCEPTION!= null) {
            throw EMPLOYEESERVICETOPDOWN_EXCEPTION;
        }
        return EMPLOYEESERVICETOPDOWN_WSDL_LOCATION;
    }

}
