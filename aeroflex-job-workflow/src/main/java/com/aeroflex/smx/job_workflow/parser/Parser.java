package com.aeroflex.smx.job_workflow.parser;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class Parser {
    public SampleFile parse(String payload){
        XStream xstream = new XStream(new DomDriver());
        xstream.alias("sample-file", SampleFile.class);
        
        SampleFile cs = (SampleFile)xstream.fromXML(payload);

        return cs;
    }
}
