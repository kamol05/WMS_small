package uz.sngroup.service.event;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.sngroup.model.event.Event;
import javax.print.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PrinterService {

    @Value( "${send.toprinter}" )
    private boolean print;

    public void print(Event event){
        try {
            if (print){ sendCommand(event); }
        } catch (PrintException e) {
            e.printStackTrace();
        }
    }

    private void sendCommand(Event event) throws PrintException {
        PrintService pservice = PrintServiceLookup.lookupDefaultPrintService(); // acquire print service of your printer
        DocPrintJob job = pservice.createPrintJob();
        String commands = getWord(event);
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        Doc doc = new SimpleDoc(commands.getBytes(), flavor, null);
        job.print(doc, null);
    }

    private String getWord(Event event){
        String quality = event.getProduct().getName();
        String gramm = event.getProduct().getGramm().getWeight();
        String color = event.getProduct().getColor().getName();
        String pieces = String.valueOf(event.getPieces());
        String weight = String.valueOf(event.getWeight());
        String size = event.getWidth() + " X " + event.getHeight();
        String serial = String.valueOf(event.getSerial());
        String ean = String.valueOf(event.getEan());
        String date = formattedDate(event.getDate());
        String str =
                "^XA\n" +
                "^LL0400\n" +
                "^PW800\n" +
                "^FT140,55^A0N,27,27^FH\\^FDQUALITY^FS\n" +
                "^FT140,85^A0N,27,27^FH\\^FDCOLOR^FS\n" +
                "^FT140,115^A0N,27,27^FH\\^FDPIECES^FS\n" +
                "^FT140,145^A0N,27,27^FH\\^FDWEIGHT^FS\n" +
                "^FT140,185^A0N,32,32^FH\\^FDSIZE^FS\n" +
                "^FT270,55^A0N,27,27^FH\\^FD:" + quality + "^FS\n" +
                "^FT270,85^A0N,27,27^FH\\^FD:" + color + "^FS\n" +
                "^FT270,115^A0N,27,27^FH\\^FD:" + "1 X " + pieces + "^FS\n" +
                "^FT270,145^A0N,27,27^FH\\^FD:" + weight + " KG" + "^FS\n" +
                "^FT270,185^A0N,32,32^FH\\^FD:" + size + "^FS\n" +
                "^FT590,55^A0N,27,27^FH\\^FD:" + gramm + " gr" + "^FS\n" +
                "^FT500,295^A0N,28,28^FH\\^FD" + date + "^FS\n" +
                "^BY2^FO170,200^BCN,70,Y,N,N^SN" + serial + ",1,Y^FS\n" +
                "^BY2^FO590,70^BEB,80,Y,N^FD" + ean + "^FS\n" +
                "^FT580,150^A0B,25,25^FH\\^SN" + serial + "^FS\n" +
                "^PQ1\n" +
                "^XZ\n" +
                "^XA";
        return str;
    }

    public String formattedDate(Date date){
        String pattern = "dd-MM HH:mm";
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

}
