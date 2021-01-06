package com.alexander_rodriguez.mihogar;

import android.os.Environment;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PDF {
    public static final String  DIRECTORI_NAME = "docs";
    private File pdfFile;
    private Document document;
    private String fileName;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;
    private Font fTitle = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.NORMAL);
    private Font fSuvtitle = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.NORMAL);
    private Font fText = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
    private Font fHint = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.NORMAL);

    public static File getFolder(){
        return new File(Environment.getExternalStorageDirectory().toString(), DIRECTORI_NAME);
    }
    public static String parseName(String id){
        return "VPDF_" + id + ".pdf";
    }
    public void createFile(String id){
        fileName = parseName(id);
        File folder = getFolder();
        if (!folder.exists())   folder.mkdir();
        pdfFile = new File(folder, fileName);
    }
    public  void openDocument(String id) throws DocumentException, FileNotFoundException {
        createFile(id);
        document = new Document(PageSize.A4);
        pdfWriter = PdfWriter.getInstance(document, new FileOutputStream((pdfFile)));
        document.open();

    }

    public void crearVoucher(String numCuarto, String dni, String numVoucher,String costo, String direccion, String fecha) throws FileNotFoundException, DocumentException {
        openDocument(numVoucher);
        addMetaData("Alquiler", "voucher", "AlexRodriguez");

        addParagraph("RECIBO DE ALQUILER");
        addParagraph( direccion + " - HABITACION N°" + numCuarto);
        addParagraph(fecha);
        addParagraph("PAGO REALIZADO N°:  #    "+ numVoucher);
        addParagraph("--------------------------------------------------------");
        addParagraph("DOCUMENTO          "+ dni);
        addParagraph("VALOR DE PAGO:     S/  "+ costo);
        addParagraph("ESTE ES UN COMPROVANTE DE PAGO\nRESPALDADO POR \"My House\"");
        addParagraph("--------------------------------------------------------");
        addParagraph("GRACIAS POR PAGAR A TIEMPO");
        closeDocument();
    }

    public void closeDocument(){
        document.close();
    }

    public  void addMetaData(String title, String subject, String autor){
        document.addTitle(title);
        document.addSubject(subject);
        document.addAuthor(autor);
    }

    public  void addTitles(String title, String subTitle, String date){
        try {
            paragraph = new Paragraph();
            addChild(new Paragraph(title, fTitle));
            addChild(new Paragraph(subTitle, fSuvtitle));
            addChild(new Paragraph(date, fHint));
            paragraph.setSpacingAfter(30);
            document.add(paragraph);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private void addChild(Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);

    }
    public void addParagraph(String text){
        try {
            paragraph = new Paragraph();
            addChild(new Paragraph(text, fText));
            paragraph.setSpacingAfter(5);
            paragraph.setSpacingBefore(5);
            document.add(paragraph);

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
    public void createTable(String []header, ArrayList<String[]>clients){
        try {
            paragraph = new Paragraph();
            paragraph.setFont(fText);
            PdfPTable pdfPTable = new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(100);
            PdfPCell pdfPCell;
            int indexC = 0;
            while (indexC<header.length){
                pdfPCell = new PdfPCell(new Phrase(header[indexC++ ], fSuvtitle));
                pdfPCell.setBackgroundColor(BaseColor.GREEN);
                pdfPCell.setBorder(10);
                pdfPTable.addCell(pdfPCell);
            }
            for (int indexR = 0 ; indexR<clients.size(); indexR++){
                String[] row = clients.get(indexR);
                for (indexC = 0 ; indexC<header.length; indexC++) {
                    pdfPCell = new PdfPCell(new Phrase(row[indexC]));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfPCell.setFixedHeight(40);
                    pdfPCell.setBorder(0);
                    pdfPTable.addCell(pdfPCell);
                }
            }
            paragraph.add(pdfPTable);
            document.add(pdfPTable);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public File getPdfFile() {
        return pdfFile;
    }

    public String getFileName() {
        return fileName;
    }
}
