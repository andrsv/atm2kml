package no.datakrim.atm2kml;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Kml {
    private Date date;

    private List<LatLongPos> path;

    public Kml() {
        path = new ArrayList();
    }

    public void readAtmFile(String filepath, String filename) {
        try {
            date = new SimpleDateFormat("MM_dd_yyyy HH_mm_ss").parse(filename);
            Scanner scanner =  new Scanner(new File(filepath+"/"+filename));
            while (scanner.hasNextLine()) {
                Scanner lineScanner = new Scanner(scanner.nextLine());
                lineScanner.useDelimiter("=");
                if (scanner.hasNext()){
                    //assumes the line has a certain structure
                    String name = lineScanner.next();
                    String value = "";
                    try {
                        value = lineScanner.next();
                    } catch (Exception e) {
                        System.out.println("Fant ingen verdi for variabel: " + name + " i filen " + filename);
                    }
                    System.out.println("Name is : " + name.trim() + ", and Value is : " + value.trim());
                    if (name.equals("Start::ptGeo") || name.equals("End::ptGeo")) {
                        Scanner coordinateScanner = new Scanner(value);
                        coordinateScanner.useDelimiter("&");
                        path.add(new LatLongPos(coordinateScanner.next(),coordinateScanner.next()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveKmlFile(String filePath) {
        PrintWriter writer = null;
        try {
            DateFormat df = new SimpleDateFormat("yyyy_MM_dd HH_mm_ss");
            writer = new PrintWriter(filePath+"/"+df.format(date)+".kml", "UTF-8");
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.println("<kml xmlns=\"http://www.opengis.net/kml/2.2\">");
            writer.println("<Document>");
            writer.println("<name>Paths</name>");
            writer.println("<description>Tid " + date + "</description>");
            writer.println("<Style id=\"yellowLineGreenPoly\">");
            writer.println("<LineStyle>");
            writer.println("<color>7f00ffff</color>");
            writer.println("<width>4</width>");
            writer.println("</LineStyle>");
            writer.println("<PolyStyle>");
            writer.println("<color>7f00ff00</color>");
            writer.println("</PolyStyle>");
            writer.println("</Style>");
            writer.println("<Placemark>");
            writer.println("<name>Absolute Extruded</name>");
            writer.println("<description>Transparent green wall with yellow outlines</description>");
            writer.println("<styleUrl>#yellowLineGreenPoly</styleUrl>");
            writer.println("<LineString>");
            writer.println("<extrude>1</extrude>");
            writer.println("<tessellate>1</tessellate>");
            writer.println("<altitudeMode>absolute</altitudeMode>");
            writer.println("<coordinates>");
            for (LatLongPos latLongPos:path) {
                writer.println(latLongPos.getLongitude() + "," +latLongPos.getLatitude()+",10000");
                System.out.println(latLongPos.getLatitude() + "," +latLongPos.getLongitude());
            }
            writer.println("</coordinates>");
            writer.println("</LineString>");
            writer.println("</Placemark>");
            writer.println("</Document>");
            writer.println("</kml>");
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
