/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.aeroflex.smx.process_invoke;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @version $Revision: 640450 $
 */
public class ProcessInvoke implements InitializingBean, DisposableBean {
    private static final transient Log LOG = LogFactory.getLog(ProcessInvoke.class);
    private boolean verbose = true;
    private String waitTime = "0";

	public void setWaitTime(String waitTime) {
		this.waitTime = waitTime;
	}

	public Object execute(Object body) {
        String answer = "Execute external command with a wait time:  " + waitTime;
        if (isVerbose()){
        	System.out.println(answer);
        }
        LOG.debug(">>>> " + answer);
        return answer;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public void afterPropertiesSet() throws Exception {
		LOG.info("Initialized Process Invoker with a wait time of " + waitTime);
	}

	public void destroy() throws Exception {
		
	}
}
