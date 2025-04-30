package org.coolCompany.writer;

import org.coolCompany.service.WorkTimeReport;

import java.util.Map;

public interface DataWriter {
    void write(Map<String, Map<String, WorkTimeReport>> report, String outputPath) throws Exception;
}
