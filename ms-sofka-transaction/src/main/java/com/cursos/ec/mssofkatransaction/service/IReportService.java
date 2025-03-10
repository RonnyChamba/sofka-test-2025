package com.cursos.ec.mssofkatransaction.service;

import com.cursos.ec.mssofkatransaction.message.response.GenericRespDTO;
import com.cursos.ec.mssofkatransaction.message.response.ReportRespDTO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IReportService {

    CompletableFuture<GenericRespDTO<List<ReportRespDTO>>> generateReport(Integer customerIde,
                                                                          String dateStart,
                                                                          String dateEnd);
}
