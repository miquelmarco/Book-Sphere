package com.bookSphere.bookSphere.Controllers;
import com.bookSphere.bookSphere.models.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.bookSphere.bookSphere.dto.BuyDTO;
import com.bookSphere.bookSphere.repositories.BookRepository;
import com.bookSphere.bookSphere.repositories.BuyOrderRepository;
import com.bookSphere.bookSphere.repositories.ClientRepository;
import com.bookSphere.bookSphere.repositories.OrderBuyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/api")
public class BuyController {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    BuyOrderRepository buyOrderRepository;
    @Autowired
    OrderBuyRepository orderBuyRepository; // TABLA INTERMEDIA
    @Transactional
    @PostMapping("/orderGenerator")
    public ResponseEntity<?> createBuyOrder(Authentication authentication, @RequestBody BuyDTO buyDTO) {
        Client client = clientRepository.findByEmail(authentication.getName());
        BuyOrder buyOrder = new BuyOrder("", 0.0, LocalDate.now(), client, OrderStatus.PENDING,false);
        buyOrderRepository.save(buyOrder);
        if(client.getShippingAdress()==null){
            return new ResponseEntity<>("Please add a shipping address",HttpStatus.FORBIDDEN);
        }
        double totalAmount = 0.0;
        Map<Long, OrderBuy> bookIdToOrderBuyMap = new HashMap<>();
        for (Long bookId : buyDTO.getBookIds()) {
            Book book = bookRepository.findById(bookId).orElse(null);
            if (book != null) {
                OrderBuy orderBuy = bookIdToOrderBuyMap.get(bookId);
                if (orderBuy == null) {
                    orderBuy = new OrderBuy(1, book, book.getPrice());
                    orderBuy.setBuyOrder(buyOrder);
                    orderBuyRepository.save(orderBuy);
                    buyOrder.getOrderBuys().add(orderBuy);
                    bookIdToOrderBuyMap.put(bookId, orderBuy);
                } else {
                    int newQuantity = orderBuy.getQuantity() + 1;
                    orderBuy.setQuantity(newQuantity);
                }
                totalAmount += book.getPrice();
            }
        }
        buyOrder.setOrderNumber(orderNumberGenerator());
        buyOrder.setAmount(totalAmount);
        client.addBuyOrder(buyOrder);
        clientRepository.save(client);
//        return new ResponseEntity<>("Buy Order successfully created", HttpStatus.CREATED);
     // Generar el PDF de la factura
    byte[] pdfContent = generarPdfFactura(buyOrder);
    // Proporcionar el PDF como adjunto en la respuesta
    HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.add("Content-Disposition", "attachment; filename=ticket.pdf");
    ResponseEntity<byte[]> response = new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
        return response;
}
    private byte[] generarPdfFactura(BuyOrder buyOrder) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();
            Image image = Image.getInstance("C:/Users/mique/Desktop/Repos/bookSphereU3/src/main/resources/static/assets/images/logoFooter.png");
            image.scaleToFit(200, 200);
            document.add(image);
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD);
            Font subtitleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            LocalTime currentTime=LocalTime.now();
            String formattedTime=currentTime.getHour()+":"+currentTime.getMinute();
            document.add(new Paragraph("Purchase ticket",titleFont));
            document.add(new Paragraph("Order number: " + buyOrder.getOrderNumber()));
            document.add(new Paragraph("Date: " + buyOrder.getDate()));
            document.add(new Paragraph("Time: " + formattedTime));
            document.add(new Paragraph("Name: " + buyOrder.getClient().getFirstName()));
            document.add(new Paragraph("Last Name: " + buyOrder.getClient().getLastName()));
            document.add(new Paragraph("E-mail: " + buyOrder.getClient().getEmail()));
            document.add(new Paragraph("Shipping adress: " + buyOrder.getClient().getShippingAdress()));
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
            String formattedAmount = "Total: $" + decimalFormat.format(buyOrder.getAmount());
            document.add(new Paragraph("Products details:",subtitleFont));
            document.add(new Paragraph());
            document.add(new Paragraph());
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100f);
            float[] columnWidths = {3f, 1f, 2f};
            table.setWidths(columnWidths);
            for (OrderBuy orderBuy : buyOrder.getOrderBuys()) {
                Book book = orderBuy.getBook();
                PdfPCell cell1 = new PdfPCell(new Phrase(book.getTitle()));
                PdfPCell cell2 = new PdfPCell(new Phrase("Quantity: " + orderBuy.getQuantity()));
                PdfPCell cell3 = new PdfPCell(new Phrase("Unit price: $" + book.getPrice()));
                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell3);
            }
            document.add(table);
//            for (OrderBuy orderBuy : buyOrder.getOrderBuys()) {
//                Book book = orderBuy.getBook();
//                document.add(new Paragraph(book.getTitle() + " - Quantity: " + orderBuy.getQuantity() + " - Unit price: " + book.getPrice()));
//            }
            document.add(new Paragraph( formattedAmount,subtitleFont));
            document.close();
            return baos.toByteArray();
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String orderNumberGenerator() {
        String orderPrefix = "ORDER N-";
        int randomNumber = (int) (Math.random() * 1000000);
        String randomDigits = String.format("%06d", randomNumber);
        return orderPrefix + randomDigits;
    }
}