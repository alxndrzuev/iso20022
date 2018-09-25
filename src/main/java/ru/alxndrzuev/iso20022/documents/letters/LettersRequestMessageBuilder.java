package ru.alxndrzuev.iso20022.documents.letters;

import iso20022.letters.CurrencyControlHeader3;
import iso20022.letters.CurrencyControlRequestOrLetterV01;
import iso20022.letters.Document;
import iso20022.letters.GenericOrganisationIdentification1;
import iso20022.letters.ObjectFactory;
import iso20022.letters.OrganisationIdentification8;
import iso20022.letters.OrganisationIdentificationSchemeName1Choice;
import iso20022.letters.Party11Choice;
import iso20022.letters.Party28Choice;
import iso20022.letters.PartyIdentification77;
import iso20022.letters.SupplementaryData1;
import iso20022.letters.SupplementaryDataEnvelope1;
import iso20022.letters.SupportingDocumentRequestOrLetter1;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.alxndrzuev.iso20022.documents.letters.model.Letter;
import ru.alxndrzuev.iso20022.documents.letters.model.LetterMessage;
import ru.alxndrzuev.iso20022.utils.DateUtils;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.Date;

@Service
@Slf4j
public class LettersRequestMessageBuilder {
    private JAXBContext jaxbContext;

    @PostConstruct
    @SneakyThrows
    public void init() {
        jaxbContext = JAXBContext.newInstance("iso20022.letters");
    }

//    <?xml version="1.0" encoding="UTF-8"?><Document xmlns="urn:iso:std:iso:20022:tech:xsd:auth.026.001.01" xmlns:ds="http://www.w3.org/2000/09/xmldsig#">
//	<CcyCtrlReqOrLttr>
//		<GrpHdr>
//			<MsgId>7730189312-MSG-20180831-133417</MsgId>
//			<CreDtTm>2018-08-31T13:34:14+03:00</CreDtTm>
//			<NbOfItms>1</NbOfItms>
//			<InitgPty>
//				<Pty>
//    <Nm>Общество с ограниченной ответственностью "Мир технологий"</Nm>
//					<Id>
//						<OrgId>
//							<Othr>
//								<Id>7730189312</Id>
//								<SchmeNm>
//									<Cd>BANK</Cd>
//								</SchmeNm>
//							</Othr>
//							<Othr>
//								<Id>40702810001300013144</Id>
//								<SchmeNm>
//									<Cd>ACC</Cd>
//								</SchmeNm>
//							</Othr>
//						</OrgId>
//					</Id>
//				</Pty>
//			</InitgPty>
//		</GrpHdr>
//		<ReqOrLttr>
//			<ReqOrLttrId>7730189312-RQLT-20180831-133417</ReqOrLttrId>
//			<Dt>2018-08-31</Dt>
//			<Sndr>
//				<Pty>
//    <Nm>Общество с ограниченной ответственностью "Мир технологий"</Nm>
//					<Id>
//						<OrgId>
//							<Othr>
//								<Id>7730189312</Id>
//								<SchmeNm>
//									<Cd>BANK</Cd>
//								</SchmeNm>
//							</Othr>
//							<Othr>
//								<Id>40702810001300013144</Id>
//								<SchmeNm>
//									<Cd>ACC</Cd>
//								</SchmeNm>
//							</Othr>
//						</OrgId>
//					</Id>
//				</Pty>
//			</Sndr>
//			<Rcvr>
//				<Pty>
//					<Nm>АО "Альфа-Банк"</Nm>
//					<Id>
//						<OrgId>
//							<AnyBIC>ALFARUMMXXX</AnyBIC>
//						</OrgId>
//					</Id>
//				</Pty>
//			</Rcvr>
//    <Sbjt>Финансовые документы. Документы застройщика</Sbjt>
//			<Tp>FDOC</Tp>
//    <Desc>Финансовые документы. Документы застройщика</Desc>
//			<RspnReqrd>false</RspnReqrd>
//		</ReqOrLttr>
//		<SplmtryData>
//			<Envlp>
//				<SgntrSt>
//				<ds:Signature Id="sigID1" xmlns:ds="http://www.w3.org/2000/09/xmldsig#"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#"/><ds:SignatureMethod Algorithm="http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411"/><ds:Reference URI=""><ds:Transforms><ds:Transform Algorithm="http://www.w3.org/2000/09/xmldsig#enveloped-signature"/><ds:Transform Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#"/></ds:Transforms><ds:DigestMethod Algorithm="http://www.w3.org/2001/04/xmldsig-more#gostr3411"/><ds:DigestValue>6UIC62jnY8Ios2+p0lxmfaKDUogzyxjZgcOSVL6hUv4=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>Jt74zp/k7xed0BhrmPGKYpPZa+XdR/l5a76jZX/L7DARMmfSietPT6MMncdH1nUIFpFu3MzDEMm9RYtTPa1tdQ==</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>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</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature></SgntrSt>
//			</Envlp>
//		</SplmtryData>
//	</CcyCtrlReqOrLttr>
//</Document>

    @SneakyThrows
    public String buildRequest(LetterMessage request) {
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter sw = new StringWriter();
        marshaller.marshal(convertRequest(request), sw);
        return sw.toString();
    }

    private JAXBElement<Document> convertRequest(LetterMessage request) {
        Document document = new Document();

        document.setCcyCtrlReqOrLttr(new CurrencyControlRequestOrLetterV01());

        CurrencyControlHeader3 currencyControlHeader = new CurrencyControlHeader3();
        currencyControlHeader.setMsgId(request.getMessageId());
        currencyControlHeader.setCreDtTm(DateUtils.toXmlDate(new Date()));
        currencyControlHeader.setNbOfItms(String.valueOf(request.getLetters().size()));
        currencyControlHeader.setInitgPty(buildParty28Choice(request.getAgentName(), request.getAgentInn(), null, null));
        document.getCcyCtrlReqOrLttr().setGrpHdr(currencyControlHeader);

        for (Letter letter : request.getLetters()) {
            SupportingDocumentRequestOrLetter1 supportingDocumentRequestOrLetter1 = new SupportingDocumentRequestOrLetter1();
            supportingDocumentRequestOrLetter1.setReqOrLttrId(letter.getId());
            supportingDocumentRequestOrLetter1.setDt(DateUtils.toXmlDate(new Date()));
            supportingDocumentRequestOrLetter1.setSndr(buildParty28Choice(letter.getSenderName(), letter.getSenderInn(), letter.getSenderAccount(), null));
            supportingDocumentRequestOrLetter1.setRcvr(buildParty28Choice(letter.getReceiverName(), null, null, letter.getReceiverBic()));
            supportingDocumentRequestOrLetter1.setSbjt(letter.getSubject());
            supportingDocumentRequestOrLetter1.setTp(Letter.LetterCategory.OTHR.name());
            supportingDocumentRequestOrLetter1.setDesc(letter.getBody());
            document.getCcyCtrlReqOrLttr().getReqOrLttr().add(supportingDocumentRequestOrLetter1);
        }

        SupplementaryData1 supplementaryData = new SupplementaryData1();
        supplementaryData.setEnvlp(new SupplementaryDataEnvelope1());
        document.getCcyCtrlReqOrLttr().getSplmtryData().add(supplementaryData);

        return new ObjectFactory().createDocument(document);
    }

    private Party28Choice buildParty28Choice(String name, String inn, String account, String bic) {
        Party28Choice party28Choice = new Party28Choice();
        party28Choice.setPty(new PartyIdentification77());
        party28Choice.getPty().setNm(name);
        party28Choice.getPty().setId(new Party11Choice());
        party28Choice.getPty().getId().setOrgId(new OrganisationIdentification8());
        if (StringUtils.isNotBlank(inn)) {
            GenericOrganisationIdentification1 genericOrganisationIdentification1 = new GenericOrganisationIdentification1();
            genericOrganisationIdentification1.setId(inn);
            genericOrganisationIdentification1.setSchmeNm(new OrganisationIdentificationSchemeName1Choice());
            genericOrganisationIdentification1.getSchmeNm().setCd("BANK");
            party28Choice.getPty().getId().getOrgId().getOthr().add(genericOrganisationIdentification1);
        }
        if (StringUtils.isNotBlank(account)) {
            GenericOrganisationIdentification1 genericOrganisationIdentification1 = new GenericOrganisationIdentification1();
            genericOrganisationIdentification1.setId(account);
            genericOrganisationIdentification1.setSchmeNm(new OrganisationIdentificationSchemeName1Choice());
            genericOrganisationIdentification1.getSchmeNm().setCd("ACC");
            party28Choice.getPty().getId().getOrgId().getOthr().add(genericOrganisationIdentification1);
        }
        party28Choice.getPty().getId().getOrgId().setAnyBIC(bic);
        return party28Choice;
    }
}
