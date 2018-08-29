package ru.alxndrzuev.iso20022.documents.payments.builder;

import iso20022.payments.AccountIdentification4Choice;
import iso20022.payments.AccountSchemeName1Choice;
import iso20022.payments.ActiveOrHistoricCurrencyAndAmount;
import iso20022.payments.AmountType4Choice;
import iso20022.payments.BranchAndFinancialInstitutionIdentification5;
import iso20022.payments.CashAccount24;
import iso20022.payments.ChargeBearerType1Code;
import iso20022.payments.ClearingSystemIdentification2Choice;
import iso20022.payments.ClearingSystemMemberIdentification2;
import iso20022.payments.CreditTransferTransaction20;
import iso20022.payments.CreditorReferenceInformation2;
import iso20022.payments.CustomerCreditTransferInitiationV06;
import iso20022.payments.Document;
import iso20022.payments.FinancialInstitutionIdentification8;
import iso20022.payments.GenericAccountIdentification1;
import iso20022.payments.GenericOrganisationIdentification1;
import iso20022.payments.GroupHeader48;
import iso20022.payments.ObjectFactory;
import iso20022.payments.OrganisationIdentification8;
import iso20022.payments.OrganisationIdentificationSchemeName1Choice;
import iso20022.payments.Party11Choice;
import iso20022.payments.PartyIdentification43;
import iso20022.payments.PaymentIdentification1;
import iso20022.payments.PaymentInstruction16;
import iso20022.payments.PaymentMethod3Code;
import iso20022.payments.PaymentTypeInformation19;
import iso20022.payments.PostalAddress6;
import iso20022.payments.Priority2Code;
import iso20022.payments.Purpose2Choice;
import iso20022.payments.ReferredDocumentInformation6;
import iso20022.payments.ReferredDocumentType3Choice;
import iso20022.payments.ReferredDocumentType4;
import iso20022.payments.RemittanceInformation10;
import iso20022.payments.ServiceLevel8Choice;
import iso20022.payments.StructuredRemittanceInformation12;
import iso20022.payments.SupplementaryData1;
import iso20022.payments.SupplementaryDataEnvelope1;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.alxndrzuev.iso20022.documents.payments.model.PaymentInstruction;
import ru.alxndrzuev.iso20022.documents.payments.model.PaymentMessage;
import ru.alxndrzuev.iso20022.documents.payments.model.PaymentPacket;
import ru.alxndrzuev.iso20022.utils.DateUtils;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.Date;

@Service
@Slf4j
public class PaymentRequestMessageBuilder {
    private JAXBContext jaxbContext;

    @PostConstruct
    @SneakyThrows
    public void init() {
        jaxbContext = JAXBContext.newInstance("iso20022.payments");

//        PaymentMessage message = new PaymentMessage();
//        message.setMessageId("7728142469_pain_MSG_20180829_00002");
//        message.setAgentInn("7728142469");
//        message.setAgentName("Общество с ограниченной ответственностью \"Управляющая компания \"Альфа-Капитал\"Д.У.");
//
//        PaymentPacket paymentRequest=new PaymentPacket();
//        message.getRequests().add(paymentRequest);
//        paymentRequest.setRequestId("7728142469_pain_PKG_20180829_00002");
//        paymentRequest.setExecutionDate(new Date());
//        paymentRequest.setDebitorCountry("RU");
//        paymentRequest.setDebtorName("Общество с ограниченной ответственностью \"Управляющая компания \"Альфа-Капитал\"Д.У.");
//        paymentRequest.setDebtorInn("7728142469");
//        paymentRequest.setDebtorAccount("40701810101600000059");
//        paymentRequest.setDebtorAccountCurrency("RUB");
//        paymentRequest.setDebtorBankBic("044525593");
//        paymentRequest.setDebtorBankName("АО \"АЛЬФА-БАНК\" Г МОСКВА");
//        paymentRequest.setDebtorBankCorrAccount("30101810200000000593");
//
//        PaymentPacket.PaymentInstruction payment=new PaymentPacket.PaymentInstruction();
//        paymentRequest.getPayments().add(payment);
//        payment.setInstructionId("7728142469_pain_PMT_20180829_00010");
//        payment.setDocumentId("20");
//        payment.setUrgency(PaymentPacket.PaymentUrgency.NURG);
//        payment.setPriority("5");
//        payment.setUin("0");
//        payment.setDescription("Обычный платеж физ лицу");
//        payment.setDocumentDate(new Date());
//        payment.setAmount(new BigDecimal("5000").setScale(2));
//        payment.setCreditorBankBic("044525593");
//        payment.setCreditorBankName("АО \"АЛЬФА-БАНК\" Г МОСКВА");
//        payment.setCreditorBankCorrAccount("30101810200000000593");
//        payment.setCreditorName("ФЕДОТОВ ВИТАЛИЙ ВЯЧЕСЛАВОВИЧ");
//        payment.setCreditorCountry("RU");
//        payment.setCreditorInn("773568135979");
//        payment.setCreditorAccount("40817810004000000190");
//        payment.setCreditorAccountCurrency("RUB");
//
//        log.info(buildRequest(message));
    }


    @SneakyThrows
    public String buildRequest(PaymentMessage request) {
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter sw = new StringWriter();
        marshaller.marshal(convertRequest(request), sw);
        return sw.toString();
    }

    private JAXBElement<Document> convertRequest(PaymentMessage request) {
        Document document = new Document();
        document.setCstmrCdtTrfInitn(new CustomerCreditTransferInitiationV06());

        GroupHeader48 groupHeader = new GroupHeader48();
        groupHeader.setCreDtTm(DateUtils.toXmlDate(new Date()));
        groupHeader.setMsgId(request.getMessageId());
        groupHeader.setNbOfTxs(String.valueOf(request.getRequests().size()));
        document.getCstmrCdtTrfInitn().setGrpHdr(groupHeader);

        groupHeader.setInitgPty(buildPartyIdentification(request.getAgentName(), request.getAgentInn(), null));

        for (PaymentPacket req : request.getRequests()) {
            PaymentInstruction16 paymentInstruction = new PaymentInstruction16();
            paymentInstruction.setPmtInfId(req.getRequestId());
            paymentInstruction.setPmtMtd(PaymentMethod3Code.TRF);
            paymentInstruction.setPmtTpInf(new PaymentTypeInformation19());
            paymentInstruction.getPmtTpInf().setInstrPrty(Priority2Code.NORM);
            paymentInstruction.getPmtTpInf().setSvcLvl(new ServiceLevel8Choice());
            paymentInstruction.getPmtTpInf().getSvcLvl().setCd("NURG");

            paymentInstruction.setReqdExctnDt(DateUtils.toXmlDate(req.getExecutionDate()));

            paymentInstruction.setDbtr(buildPartyIdentification(req.getDebtorName(), req.getDebtorInn(), "RU"));

            paymentInstruction.setDbtrAcct(buildCashAccount(req.getDebtorAccount(), req.getDebtorAccountCurrency(), "BBAN"));

            paymentInstruction.setDbtrAgt(buildBranchAndFinancialInstitutionIdentification(req.getDebtorBankBic(), req.getDebtorBankName()));

            paymentInstruction.setDbtrAgtAcct(buildCashAccount(req.getDebtorBankCorrAccount(), null, null));

            for (PaymentInstruction payment : req.getPayments()) {
                CreditTransferTransaction20 creditTransferTransaction = new CreditTransferTransaction20();
                paymentInstruction.getCdtTrfTxInf().add(creditTransferTransaction);

                creditTransferTransaction.setPmtId(new PaymentIdentification1());
                creditTransferTransaction.getPmtId().setInstrId(payment.getInstructionId());
                creditTransferTransaction.getPmtId().setEndToEndId(payment.getDocumentId());

                creditTransferTransaction.setPmtTpInf(new PaymentTypeInformation19());
                creditTransferTransaction.getPmtTpInf().setSvcLvl(new ServiceLevel8Choice());
                creditTransferTransaction.getPmtTpInf().getSvcLvl().setCd(payment.getUrgency().name());

                creditTransferTransaction.setAmt(new AmountType4Choice());
                creditTransferTransaction.getAmt().setInstdAmt(new ActiveOrHistoricCurrencyAndAmount());
                creditTransferTransaction.getAmt().getInstdAmt().setCcy(payment.getCreditorAccountCurrency());
                creditTransferTransaction.getAmt().getInstdAmt().setValue(payment.getAmount());

                creditTransferTransaction.setChrgBr(ChargeBearerType1Code.DEBT);

                creditTransferTransaction.setCdtrAgt(buildBranchAndFinancialInstitutionIdentification(payment.getCreditorBankBic(), payment.getCreditorBankName()));

                creditTransferTransaction.setCdtrAgtAcct(buildCashAccount(payment.getCreditorBankCorrAccount(), null, null));

                creditTransferTransaction.setCdtr(buildPartyIdentification(payment.getCreditorName(), payment.getCreditorInn(), "RU"));

                creditTransferTransaction.setCdtrAcct(buildCashAccount(payment.getCreditorAccount(), null, "BBAN"));

                creditTransferTransaction.setPurp(new Purpose2Choice());
                creditTransferTransaction.getPurp().setPrtry(payment.getPriority());

                creditTransferTransaction.setRmtInf(new RemittanceInformation10());
                creditTransferTransaction.getRmtInf().getUstrd().add(payment.getDescription());
                StructuredRemittanceInformation12 structuredRemittanceInformation = new StructuredRemittanceInformation12();
                creditTransferTransaction.getRmtInf().getStrd().add(structuredRemittanceInformation);
                ReferredDocumentInformation6 referredDocumentInformation = new ReferredDocumentInformation6();
                structuredRemittanceInformation.getRfrdDocInf().add(referredDocumentInformation);
                referredDocumentInformation.setTp(new ReferredDocumentType4());
                referredDocumentInformation.getTp().setCdOrPrtry(new ReferredDocumentType3Choice());
                referredDocumentInformation.getTp().getCdOrPrtry().setPrtry("POD");
                referredDocumentInformation.setRltdDt(DateUtils.toXmlDate(payment.getDocumentDate()));
                structuredRemittanceInformation.setCdtrRefInf(new CreditorReferenceInformation2());
                structuredRemittanceInformation.getCdtrRefInf().setRef(payment.getUin());
            }
            document.getCstmrCdtTrfInitn().getPmtInf().add(paymentInstruction);
        }
        SupplementaryData1 supplementaryData = new SupplementaryData1();
        supplementaryData.setEnvlp(new SupplementaryDataEnvelope1());

        document.getCstmrCdtTrfInitn().getSplmtryData().add(supplementaryData);

        return new ObjectFactory().createDocument(document);
    }

    private GenericOrganisationIdentification1 buildGenericOrganisationIdentification(String inn) {
        GenericOrganisationIdentification1 genericOrganisationIdentification = new GenericOrganisationIdentification1();
        genericOrganisationIdentification.setId(inn);
        genericOrganisationIdentification.setSchmeNm(new OrganisationIdentificationSchemeName1Choice());
        genericOrganisationIdentification.getSchmeNm().setCd("TXID");
        return genericOrganisationIdentification;
    }

    private BranchAndFinancialInstitutionIdentification5 buildBranchAndFinancialInstitutionIdentification(String bankBic, String bankName) {
        BranchAndFinancialInstitutionIdentification5 branchAndFinancialInstitutionIdentification = new BranchAndFinancialInstitutionIdentification5();
        FinancialInstitutionIdentification8 financialInstitutionIdentification = new FinancialInstitutionIdentification8();
        branchAndFinancialInstitutionIdentification.setFinInstnId(financialInstitutionIdentification);
        financialInstitutionIdentification.setClrSysMmbId(new ClearingSystemMemberIdentification2());
        financialInstitutionIdentification.getClrSysMmbId().setClrSysId(new ClearingSystemIdentification2Choice());
        financialInstitutionIdentification.getClrSysMmbId().getClrSysId().setCd("RUCBC");
        financialInstitutionIdentification.getClrSysMmbId().setMmbId(bankBic);
        financialInstitutionIdentification.setNm(bankName);
        financialInstitutionIdentification.setPstlAdr(new PostalAddress6());
        financialInstitutionIdentification.getPstlAdr().setCtry("RU");
        return branchAndFinancialInstitutionIdentification;
    }

    private CashAccount24 buildCashAccount(String account, String currency, String schemaName) {
        CashAccount24 cashAccount = new CashAccount24();
        cashAccount.setId(new AccountIdentification4Choice());
        cashAccount.getId().setOthr(new GenericAccountIdentification1());
        cashAccount.getId().getOthr().setId(account);
        if (StringUtils.isNotBlank(schemaName)) {
            cashAccount.getId().getOthr().setSchmeNm(new AccountSchemeName1Choice());
            cashAccount.getId().getOthr().getSchmeNm().setCd(schemaName);
        }
        cashAccount.setCcy(currency);
        return cashAccount;
    }

    private PartyIdentification43 buildPartyIdentification(String name, String inn, String country) {
        PartyIdentification43 partyIdentification = new PartyIdentification43();
        partyIdentification.setNm(name.length() > 140 ? name.substring(0, 140) : name);
        partyIdentification.setId(new Party11Choice());
        partyIdentification.getId().setOrgId(new OrganisationIdentification8());
        partyIdentification.getId().getOrgId().getOthr().add(buildGenericOrganisationIdentification(inn));
        if (StringUtils.isNotBlank(country)) {
            partyIdentification.setPstlAdr(new PostalAddress6());
            partyIdentification.getPstlAdr().setCtry(country);
        }
        return partyIdentification;
    }
}
