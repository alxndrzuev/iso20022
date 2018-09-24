package ru.alxndrzuev.iso20022.documents.statements;


import iso20022.statements.AccountIdentification4Choice;
import iso20022.statements.AccountReportingRequestV03;
import iso20022.statements.CashAccount24;
import iso20022.statements.DatePeriodDetails1;
import iso20022.statements.Document;
import iso20022.statements.GenericAccountIdentification1;
import iso20022.statements.GroupHeader59;
import iso20022.statements.ObjectFactory;
import iso20022.statements.Party12Choice;
import iso20022.statements.PartyIdentification43;
import iso20022.statements.QueryType3Code;
import iso20022.statements.ReportingPeriod1;
import iso20022.statements.ReportingRequest3;
import iso20022.statements.SupplementaryData1;
import iso20022.statements.SupplementaryDataEnvelope1;
import iso20022.statements.TimePeriodDetails1;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.alxndrzuev.iso20022.documents.statements.model.StatementRequest;
import ru.alxndrzuev.iso20022.documents.statements.model.StatementRequestMessage;
import ru.alxndrzuev.iso20022.utils.DateUtils;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.Date;

@Service
public class StatementRequestMessageBuilder {
    private JAXBContext jaxbContext;

    @PostConstruct
    @SneakyThrows
    public void init() {
        jaxbContext = JAXBContext.newInstance("iso20022.statements");
    }


    @SneakyThrows
    public String buildRequest(StatementRequestMessage request) {
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter sw = new StringWriter();
        marshaller.marshal(convertRequest(request), sw);
        return sw.toString();
    }

    private JAXBElement<Document> convertRequest(StatementRequestMessage request) {
        Document document = new Document();
        document.setAcctRptgReq(new AccountReportingRequestV03());

        GroupHeader59 groupHeader = new GroupHeader59();
        groupHeader.setCreDtTm(DateUtils.toXmlDate(new Date()));
        groupHeader.setMsgId(request.getMessageId());
        document.getAcctRptgReq().setGrpHdr(groupHeader);

        for (StatementRequest req : request.getRequests()) {
            ReportingRequest3 reportingRequest = new ReportingRequest3();
            reportingRequest.setId(req.getRequestId());
            reportingRequest.setReqdMsgNmId("HMQSTASCF");
            reportingRequest.setAcct(new CashAccount24());
            reportingRequest.getAcct().setId(new AccountIdentification4Choice());
            reportingRequest.getAcct().getId().setOthr(new GenericAccountIdentification1());
            reportingRequest.getAcct().getId().getOthr().setId(req.getAccount());
            reportingRequest.setAcctOwnr(new Party12Choice());
            reportingRequest.getAcctOwnr().setPty(new PartyIdentification43());
            reportingRequest.getAcctOwnr().getPty().setNm(req.getOrganizationName());
            reportingRequest.setRptgPrd(new ReportingPeriod1());
            reportingRequest.getRptgPrd().setFrToDt(new DatePeriodDetails1());
            reportingRequest.getRptgPrd().getFrToDt().setFrDt(DateUtils.toXmlDate(req.getDateFrom()));
            reportingRequest.getRptgPrd().getFrToDt().setToDt(DateUtils.toXmlDate(req.getDateTo()));
            reportingRequest.getRptgPrd().setFrToTm(new TimePeriodDetails1());
            reportingRequest.getRptgPrd().getFrToTm().setFrTm(DateUtils.toXmlDate(req.getDateFrom()));
            reportingRequest.getRptgPrd().getFrToTm().setToTm(DateUtils.toXmlDate(req.getDateTo()));
            reportingRequest.getRptgPrd().setTp(QueryType3Code.ALLL);
            document.getAcctRptgReq().getRptgReq().add(reportingRequest);
        }
        SupplementaryData1 supplementaryData = new SupplementaryData1();
        supplementaryData.setEnvlp(new SupplementaryDataEnvelope1());

        document.getAcctRptgReq().getSplmtryData().add(supplementaryData);

        return new ObjectFactory().createDocument(document);
    }
}
