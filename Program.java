import java.util.*;
import java.io.*;

public class Program {
    static Area validAreas;
    static Scanner scanner;
    static List<Distributor> distributors;

    static void queryPermissions() {
        System.out.println();
        System.out.println("Distributor permission query");
        System.out.print("Distributor Name: ");
        String name = scanner.next();
        System.out.print("Area: ");
        String areaStr = scanner.next();

        Distributor curDist = null;

        for(Distributor dist: distributors) {
            if(dist.name().equals(name)) {
                curDist = dist;
                break;
            }
        }

        Area queriedArea = validAreas.findArea(areaStr);
        boolean permission = curDist.hasPermission(queriedArea);

        if(permission)
            System.out.println("YES");
        else
            System.out.println("NO");
    }

    static void addNewDistributor() {
        System.out.println();
        System.out.println("Distributor entry");
        System.out.print("Name: ");
        String name = scanner.next();
        System.out.print("Is sub-distributor of: ");
        String subOf = scanner.next();

        Distributor newDist = null;

        if(subOf.equals("none")) {
            newDist = new Distributor(name);
            distributors.add(newDist);
        } else {
            for(Distributor parentDist: distributors) {
                if(parentDist.name().equals(subOf)) {
                    newDist = new Distributor(name, parentDist);
                    distributors.add(newDist);
                    break;
                }
            }
        }

        String input = "";
        do {
            System.out.print("Include: ");
            input = scanner.next();
            if(!input.equals("none")) {
                Area includedArea = validAreas.findArea(input);
                newDist.include(includedArea);
            }
        } while(!input.equals("none"));

        do {
            System.out.print("Exclude: ");
            input = scanner.next();
            if(!input.equals("none")) {
                Area excludedArea = validAreas.findArea(input);
                newDist.exclude(excludedArea);
            }
        } while(!input.equals("none"));
    }

    public static void main(String[] args) {
        validAreas = new Area();
        scanner = new Scanner(System.in);
        distributors = new ArrayList<>();

        String line = null;
        String fileName = "cities.csv";

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                // System.out.println(line);
                String[] csvValues = line.split(",");
                CodeName city, province, country;
                city = new CodeName(csvValues[0], csvValues[3]);
                province = new CodeName(csvValues[1], csvValues[4]);
                country = new CodeName(csvValues[2], csvValues[5]);

                Area countryArea = validAreas.addSubAreaIfNotExists(country);
                Area provinceArea = countryArea.addSubAreaIfNotExists(province);
                provinceArea.addSubAreaIfNotExists(city);
            }

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
            // ex.printStackTrace();
        }

        scanner.useDelimiter("[\\r\\n]+");

        while(true) {
            System.out.println();
            System.out.println("Options:");
            System.out.println("1. Add new distributor");
            System.out.println("2. Query distributor permissions");
            System.out.println("0. Quit");
            String input = scanner.next();

            if(input.equals("1")) {
                addNewDistributor();
            }
            else if (input.equals("2")) {
                queryPermissions();
            }
            else if (input.equals("0")) {
                System.out.println("Quitting...");
                break;
            }
        }
    }
}