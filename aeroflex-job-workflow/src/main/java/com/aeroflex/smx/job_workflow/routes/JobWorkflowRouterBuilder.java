package com.aeroflex.smx.job_workflow.routes;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import com.aeroflex.smx.job_workflow.JobWorkflow;
import com.aeroflex.smx.job_workflow.parser.SampleFile;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class JobWorkflowRouterBuilder  extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:loadfile").multicast().to("seda:aff", "seda:afx");
        from("seda:aff").process(new FileProcessor()).to("bean:jobService?method=loadFile").process(new JSONProcessor()).to("seda:aggregator");
        from("seda:aggregator").aggregator().header(JobWorkflow.REQUEST_HEADER).process(new AeroflexPojoProcessor()).to("bean:mainframeHelperBean?method=execute");
    }

    class FileProcessor implements Processor {
        public void process(Exchange exchange) throws Exception {
            // This is to extract the payload and change the input type
            SampleFile cs = (SampleFile)exchange.getIn().getBody();
            String fileName = cs.getFileName();
            exchange.getIn().setBody(fileName);
        }
    }

    
    class JSONProcessor implements Processor {
        public void process(Exchange exchange) throws Exception {
            // This is to extract the payload and change the input type
            SampleFile cs = (SampleFile) exchange.getIn().getBody();
            
            XStream xstream = new XStream(new JettisonMappedXmlDriver());
            xstream.setMode(XStream.NO_REFERENCES);
            xstream.alias("sample-file", SampleFile.class);

            exchange.getIn().setBody(xstream.toXML(cs));
        }
    }
    
    class AeroflexPojoProcessor implements Processor {
        public void process(Exchange exchange) throws Exception {
            String requestId = (String)exchange.getIn().getHeader(JobWorkflow.REQUEST_HEADER);
            
            List<String> params = new ArrayList<String>();
            params.add(requestId);
            params.add((String)exchange.getIn().getBody());
            
            exchange.getIn().setBody(params);
        }
    }

}