package uz.sngroup.service.report;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import uz.sngroup.Application;
import uz.sngroup.model.event.SaleEvent;
import uz.sngroup.service.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ReportGenerator {
    @Autowired Util util;

    public ResponseEntity<byte[]> getReport(List<?> collectionDataSource, Map<String, Object> parameters, String frxmlFileName, String reportName){
        frxmlFileName = frxmlFileName + ".jrxml";
        reportName = reportName + ".PDF";
        byte[] pdf = generateReport(collectionDataSource,parameters,frxmlFileName);
        HttpHeaders headers = new HttpHeaders();
        headers.set(reportName, reportName);
        headers.setContentDispositionFormData(reportName, reportName);
        headers.setContentType(MediaType.APPLICATION_PDF);
        return ResponseEntity.ok().headers(headers).body(pdf);
    }

    @SneakyThrows
    private byte[] generateReport(List<?> collectionDataSource, Map<String, Object> parameters, String frxmlFileName) {
        byte[] arrayOfBytes = null;
        try {
            InputStream inputStream = new FileInputStream(util.getFileFromRecFolder(frxmlFileName,"data"));
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(collectionDataSource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            arrayOfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException e){
            log.error("Reportni ichida xato", e);
        }
        return arrayOfBytes;
    }
}
