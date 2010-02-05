package com.aeroflex.smx.job_workflow;


import org.apache.log4j.Logger;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.aeroflex.smx.job_workflow.parser.Parser;
import com.aeroflex.smx.job_workflow.parser.SampleFile;

public class JobWorkflowImpl implements InitializingBean, DisposableBean, JobWorkflow {

	private static Logger LOG = Logger.getLogger(JobWorkflowImpl.class);
    
    public static String OK ="OK";
    public static String INVALID = "PARSING ERROR - XML INVALID";
    
    private CamelContext camelContext;

    private ProducerTemplate<Exchange> template;
    
    private String fileUri;

    public void setCamelContext(CamelContext camelContext) {
        this.camelContext = camelContext;
    }

    public void setTemplate(ProducerTemplate<Exchange> template) {
        this.template = template;
    }

	public void setFileUri(String fileUri) {
		this.fileUri = fileUri;
	}

	public void afterPropertiesSet() throws Exception {
		LOG.info(">>>> [INITIATED] Job Workflow");
	}

	public void destroy() throws Exception {
		
	}

	public String processFile(String payload) {
		Parser parser = new Parser();
        
        try {
            
            //Parse the payload
            SampleFile cs = parser.parse(payload);
            
            //Send it on to the workflow
            template.sendBodyAndHeader(fileUri, cs, JobWorkflow.REQUEST_HEADER, cs.getRequestId());
            
        } catch (Exception e){
            LOG.error("Parsing Error", e);
            return INVALID;
        }
        
        return "OK";
	}

}
