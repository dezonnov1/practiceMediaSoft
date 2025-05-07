package org.coolCompany.writer;

import org.coolCompany.service.WorkTimeReport;

import java.io.File;
import java.util.Map;


public interface DataWriter{
    void write(Map<Integer, Map<String, WorkTimeReport>> report, File outputFile) throws Exception;
}
