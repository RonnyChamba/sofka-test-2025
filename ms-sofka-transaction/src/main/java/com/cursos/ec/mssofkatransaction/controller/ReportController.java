package com.cursos.ec.mssofkatransaction.controller;

import com.cursos.ec.mssofkatransaction.exception.GenericException;
import com.cursos.ec.mssofkatransaction.message.response.GenericRespDTO;
import com.cursos.ec.mssofkatransaction.message.response.ReportRespDTO;
import com.cursos.ec.mssofkatransaction.service.IReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/reportes")
@RequiredArgsConstructor
@Tag(name = "Report", description = "Report management system")
public class ReportController {

    private final IReportService reportService;

    @GetMapping
    public CompletableFuture<GenericRespDTO<List<ReportRespDTO>>> findAccountByCustomers(
            @RequestParam(name = "cliente") Integer customerIde,
            @RequestParam(name = "dateFrom") String dateFrom,
            @RequestParam(name = "dateTo") String dateTo) {

        return reportService.generateReport(customerIde, dateFrom, dateTo);
    }

}
