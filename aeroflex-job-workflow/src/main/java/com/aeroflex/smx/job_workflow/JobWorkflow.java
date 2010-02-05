
package com.aeroflex.smx.job_workflow;


public interface JobWorkflow {
    
    public static String REQUEST_HEADER = "requestId";
    
    public String processFile(String payload);
}