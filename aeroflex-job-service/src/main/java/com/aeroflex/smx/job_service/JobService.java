
package com.aeroflex.smx.job_service;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.aeroflex.smx.process_invoke.ProcessInvoke;

/**
 * @version $Revision: 640450 $
 */
public class JobService implements InitializingBean, DisposableBean  {
    private static final transient Log LOG = LogFactory.getLog(JobService.class);
    private boolean verbose = true;
    
    private ProcessInvoke processInvoke;

    public void setProcessInvoke(ProcessInvoke processInvoke) {
        this.processInvoke = processInvoke;
    }
    
    public void loadFile(Object payload) {
    	
    	LOG.info(">>>> [METHOD] JobService.loadFile()");
        List<String> list = (List<String>) payload;
        String fileName = list.get(0);
        String data = list.get(1);
        if (verbose) {
            System.out.println(">>>> [File]: " + fileName + " || [Data]: " + data);
            System.out.println(">>>> [SEND]: file to execute");
            processInvoke.execute(fileName);
            
        }
        LOG.info(">>>> [File]: " + fileName + " || [Data]: " + data);
        
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

	public void afterPropertiesSet() throws Exception {
		LOG.info(">>>>  [INITIALIZED] Job Service");
	}

	public void destroy() throws Exception {
		
	}

}
