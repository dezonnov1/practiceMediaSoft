package org.coolCompany.writer;

import org.coolCompany.model.CrewMember;
import org.coolCompany.service.WorkTimeReport;

import java.io.File;
import java.util.List;
import java.util.Map;


public interface DataWriter{
    public void write(Map<Integer, Map<String, WorkTimeReport>> report, File outputFile) throws Exception;
    static void checkWriteInput(Map<Integer, Map<String, WorkTimeReport>> report, List<CrewMember> crews, File outputFile){
    }
}
