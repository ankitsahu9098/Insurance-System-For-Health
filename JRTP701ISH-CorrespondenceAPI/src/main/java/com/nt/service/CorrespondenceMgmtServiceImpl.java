package com.nt.service;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.nt.binding.CoSummary;
import com.nt.entity.CitizenAppRegistrationEntity;
import com.nt.entity.CoTriggerEntity;
import com.nt.entity.DcCaseEntity;
import com.nt.entity.EligibilityDetailsEntity;
import com.nt.repository.IApplicationRegistrationRepositary;
import com.nt.repository.ICoTriggerRepositary;
import com.nt.repository.IDcCaseRepository;
import com.nt.repository.IEligibilityDetermineRepository;
import com.nt.utils.EmailUtils;

@Service
public class CorrespondenceMgmtServiceImpl implements ICorrespondenceMgmtService {

	@Autowired
	private ICoTriggerRepositary triggerRepo;
	@Autowired
	private IEligibilityDetermineRepository eligiRepo;
	@Autowired
	private IDcCaseRepository caseRepo;
	@Autowired
	private IApplicationRegistrationRepositary citizenRepo;
	@Autowired
	private EmailUtils mailUtils;
	
	int pendingTriggers = 0;
	int successTriggers =0;
	
	@Override
	public CoSummary processPendingTriggers() {
		
		//get all pending triggers
		List<CoTriggerEntity> triggerList = triggerRepo.findByTriggerStatus("pendding");
		//prepare CoSummary Report
		CoSummary summary = new CoSummary();
		summary.setTotalTriggers(triggerList.size());
		
		//process the triggers in multithreaded env. using Executor Framwork
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		ExecutorCompletionService<Object> pool = new ExecutorCompletionService<Object>(executorService);
		
		//process each pending trigger
		for(CoTriggerEntity triggerEntity : triggerList) {
			pool.submit(()->{ //call method implementation
				try {
					processTrigger(summary, triggerEntity);
					successTriggers++;
				}
				catch(Exception e) {
					e.printStackTrace();
					pendingTriggers++;
				}
				return null;
		 } );
		}//for 
		
		summary.setPendingTrigger(pendingTriggers);
		summary.setSuccessTriggers(successTriggers);
		return summary;		
	}
	//improve code for loop code put in private method
	private CitizenAppRegistrationEntity processTrigger(CoSummary summary,CoTriggerEntity  triggerEntity) throws Exception{
		CitizenAppRegistrationEntity citizenEntity = null;
		//get eligibility details based on caseNumber
		EligibilityDetailsEntity eligiEntity = eligiRepo.findByCaseNumber(triggerEntity.getCaseNumber());
		//get appId based on caseNumber
		Optional<DcCaseEntity>optCaseEntity = caseRepo.findById(triggerEntity.getCaseNumber());
		if(optCaseEntity.isPresent()) {
			DcCaseEntity caseEntity = optCaseEntity.get();
			Integer appId = caseEntity.getAppId();
			//get the citizen details based on the appId
			Optional<CitizenAppRegistrationEntity> optCitizenEntity = citizenRepo.findById(appId);
			if(optCitizenEntity.isPresent()) {
				 citizenEntity = optCitizenEntity.get();
			}
		}
		generatePdfAndSendMail(eligiEntity, citizenEntity);
		return citizenEntity;
	}
	
	
	//helper method to generate the pdf  doc
	private void generatePdfAndSendMail(EligibilityDetailsEntity eligiEntity, CitizenAppRegistrationEntity citizenEntity) throws Exception{
		//create Document obj(openPdf)
		//create Document obj(open pdf)
				Document document = new Document(PageSize.A4);
				//create pdf file to write the content to it
				File file = new File(eligiEntity.getCaseNumber()+".pdf");
				FileOutputStream fos = new FileOutputStream(file);
				//get pdfWrite to write to the document and response obj
				PdfWriter.getInstance(document, fos);
				//open the document 
				document.open();
				//Define Font for the paragraph
				Font font  = FontFactory.getFont(FontFactory.TIMES_BOLD);
				font.setSize(20);
				font.setColor(Color.CYAN);
				
				//create the paragraph having content and above font style
				Paragraph para = new Paragraph("Plan Approval/Denial Communication", font);
				para.setAlignment(Paragraph.ALIGN_CENTER);
				// add paragraph to document
				document.add(para);
				
				//Display search Result as the pdf table 
				PdfPTable table = new PdfPTable(10);
				table.setWidthPercentage(70);
				table.setWidths(new float[] {3.0f,3.0f,3.0f,3.0f,3.0f,3.0f,3.0f,3.0f,3.0f,3.0f});
				table.setSpacingBefore(2.0f);
				
				//prepare heading row cells in the pdf table
				PdfPCell cell = new PdfPCell();
				cell.setBackgroundColor(Color.WHITE);
				cell.setPadding(5);
				Font cellFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
				cellFont.setColor(Color.BLACK);
			 	
				cell.setPhrase(new Phrase("TraceID",cellFont));
				table.addCell(cell);
				cell.setPhrase(new Phrase("CaseNumber",cellFont));
				table.addCell(cell);
				cell.setPhrase(new Phrase("HolderName",cellFont));
				table.addCell(cell);
				cell.setPhrase(new Phrase("HolderSSN",cellFont));
				table.addCell(cell);
				cell.setPhrase(new Phrase("PlanName",cellFont));
				table.addCell(cell);
				cell.setPhrase(new Phrase("PlanStatus",cellFont));
				table.addCell(cell);
				cell.setPhrase(new Phrase("PlanStartDate",cellFont));
				table.addCell(cell);
				cell.setPhrase(new Phrase("PlanEndDate",cellFont));
				table.addCell(cell);
				cell.setPhrase(new Phrase("BenifitAmt",cellFont));
				table.addCell(cell);
				cell.setPhrase(new Phrase("DenialReason",cellFont));
				table.addCell(cell);
				
				//add data cells to pdftable
				table.addCell((String.valueOf(eligiEntity.getEdTraceId())));
				table.addCell((String.valueOf(eligiEntity.getCaseNumber())));
				table.addCell(eligiEntity.getHolderName());
				table.addCell((String.valueOf(eligiEntity.getHolderSSN())));
				table.addCell(eligiEntity.getPlanName());
				table.addCell(eligiEntity.getPlanStatus());
				table.addCell((String.valueOf(eligiEntity.getPlanStartDate())));
				table.addCell((String.valueOf(eligiEntity.getPlanEndDate())));
				table.addCell((String.valueOf(eligiEntity.getBenifitAmt())));
				table.addCell((String.valueOf(eligiEntity.getDenialReason())));
				
				//add table to document
				document.add(table);
				//close the document
				document.close();
				//send the generated pdf doc as the email message
				String subject = "Plan approval/deniel mail";
				String body = "Hello Mr/Miss/Mrs. "+citizenEntity.getFullName()+", this mail cotains complete deatails plan approval or deniel";
				mailUtils.sendEmailMessage(citizenEntity.getEmail(), subject, body, file);
				//update Co_Trigger table 
				updateCoTrigger(eligiEntity.getCaseNumber(),file);
	}
	private void updateCoTrigger(Integer caseNumber, File file) throws Exception{
		//check Triggger availabity based on the  caseNumber
		CoTriggerEntity triggerEntity = triggerRepo.findByCaseNumber(caseNumber);
		//get byte[] representing the pdf doc content
		byte[] pdfContent = new byte[(int)file.length()];
		FileInputStream fis = new FileInputStream(file);
		fis.read(pdfContent);
		if(triggerEntity!=null) {
			triggerEntity.setCoNoticePdf(pdfContent);
			triggerEntity.setTriggerStatus("Completed");
			triggerRepo.save(triggerEntity);
		}
		fis.close();
	}
				
}
