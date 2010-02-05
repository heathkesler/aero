package com.aeroflex.smx.job_workflow.util;

import java.util.List;
import java.util.logging.Logger;

import com.aeroflex.smx.aeroflex_pojo_service.AeroflexPojoService;
import com.aeroflex.smx.job_workflow.parser.Parser;
import com.aeroflex.smx.job_workflow.parser.SampleFile;

public class AeroflexHelperBean {
	private static final Logger LOG = Logger.getLogger(AeroflexHelperBean.class.getPackage().getName());
	
	private AeroflexPojoService aeroflexPojoService;
    
    public void setAeroflexPojoService(AeroflexPojoService aeroflexPojoService) {
        this.aeroflexPojoService = aeroflexPojoService;
    }

    public Object execute(Object inMessage){
       LOG.info(">>>>  [RECEIVING] message :: "+ inMessage.toString());
       Parser parser = new Parser();
       SampleFile file = parser.parse(inMessage.toString());
       
       System.out.println(">>>> [PAYLOAD] filename:" + file.getFileName());       
       return aeroflexPojoService.sendMessage(file.getFileName(), inMessage.toString());
    }
}
