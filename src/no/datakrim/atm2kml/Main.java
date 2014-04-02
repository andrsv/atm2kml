package no.datakrim.atm2kml;


import java.io.File;

public class Main {
    public static void main(String [ ] args)
    {
        File dir = new File("routes");
        for (File child : dir.listFiles()) {
            String filename = child.getName();
            System.out.println();
            System.out.println("processsing " + dir.getName() + "/" + filename + "...");
            Kml kml = new Kml();
            kml.readAtmFile(dir.getName(),child.getName());
            kml.saveKmlFile("kml");
        }
    }

}
