package com.v2com.iws10.axon.template.service.soap.server;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebService(name = "EmployeeServiceTopDown", targetNamespace = "http://topdown.server.jaxws.v2com.com/")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class EmployeeServiceTopDownImp implements EmployeeServiceTopDown {
  private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceTopDownImp.class);

  @WebMethod(action = "http://topdown.server.jaxws.v2com.com/               EmployeeServiceTopDown/countEmployees")
  @WebResult(name = "countEmployeesResponse", targetNamespace = "http://topdown.server.jaxws.v2com.com/", partName = "parameters")
  @Override
  public int countEmployees() {
    logger.info("Receiving a request to count employees");
    return (int) (Math.random() * 100000);
  }
}