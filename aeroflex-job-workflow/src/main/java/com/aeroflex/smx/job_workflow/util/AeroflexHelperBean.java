package com.aeroflex.smx.job_workflow.util;

import java.util.List;

import com.aeroflex.smx.aeroflex_pojo_service.AeroflexPojoService;

public class AeroflexHelperBean {
	private AeroflexPojoService aeroflexPojoService;
    
    
    public void setMainframePojoService(AeroflexPojoService aeroflexPojoService) {
        this.aeroflexPojoService = aeroflexPojoService;
    }

    public Object execute(Object inMessage){
       List<String> params = (List<String>)inMessage; 
       
       String filename = params.get(0);
       String payload = params.get(1);
       System.out.println("****PAYLOAD = " + payload);       
       return aeroflexPojoService.sendMessage(filename, payload);
    }
}
