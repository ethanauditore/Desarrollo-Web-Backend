package ciencias.unam.mx;

import java.io.Console;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;

public class Menu {

    private Scanner scanner;
    private SQL sql;
    private static final String MAIN_MENU = "\n" +
            "+-----------------------------+\n" +
            "| Elige una opcion:           |\n" +
            "+-----------------------------+\n" +
            "| 1. Crear una base de datos  |\n" +
            "| 2. Cargar una base de datos |\n" +
            "| 0. Salir                    |\n" +
            "+-----------------------------+\n";

    private static final String OPTIONS_MENU =
            "+---------------------------+\n" +
            "| Elige una opcion:         |\n" +
            "+---------------------------+\n" +
            "| 1. Crear un cliente       |\n" +
            "| 2. Mostrar a los clientes |\n" +
            "| 3. Eliminar un cliente    |\n" +
            "| 0. Salir                  |\n" +
            "+---------------------------+\n";

    public Menu() {
        scanner = new Scanner(System.in);
        getSQLInfo();
    }

    @FunctionalInterface
    private interface MenuOptions {
        public boolean exec(String opcion);
    }

    private void display(String menu, String mensaje, MenuOptions options) {
        while (true) {
            System.out.println(menu);
            if (!mensaje.isEmpty()) {
                System.out.print(mensaje);
            }
            if (!options.exec(scanner.next())) {
                break;
            }
        }
    }

    public void mainMenu() {
        display(MAIN_MENU, "Su eleccion: ", (opcion) -> {
            switch(opcion) {
                case "0":
                    System.out.println("\nSaliendo...");
                    return false;
                case "1":
                    createDatabase();
                    optionsMenu();
                    break;
                case "2":
                    loadDatabase();
                    optionsMenu();
                    break;
                default:
                    System.out.println("\nERROR: Seleccione una opcion correcta.\n");
                    break;
            }
            return true;
        });
    }

    public void optionsMenu() {
        display(OPTIONS_MENU, "Su eleccion: ", (opcion) -> {
            switch(opcion) {
                case "0":
                    System.out.println("\nSaliendo...");
                    return false;
                case "1":
                    Region region = getRegionInfo();
                    createRegion(region);
                    createCustomers(getCustomerInfo(region));
                    break;
                case "2":
                    getCustomers();
                    break;
                case "3":
                    deleteCustomers(0);
                    break;
                default:
                    System.out.println("\nERROR: Seleccione una opcion correcta.\n");
                    break;
            }
            return true;
        });
    }

    private void getCustomers() {
        System.out.println("\nLos clientes se muestran a continuacion:");
        sql.printTable("Cliente");
    }

    private void createCustomers(Customer customer) {
        System.out.println("\nInserte los datos del cliente:");
        String[] fields = customer.toString().split("\n");
        for (int i = 0; i < fields.length; i++) {
            fields[i] = fields[i].substring(fields[i].indexOf(" ") + 1);
            if (!fields[i].matches("[0-9]+")) {
                fields[i] = String.format("'%s'", fields[i]);
            }
        }
        sql.exec(String.format("INSERT INTO Cliente VALUES (%s)",
                                        String.join(", ", fields)));
    }

    private void deleteCustomers(int customerId) {
        System.out.print("\nInserte el id del cliente a eliminar: ");
        String instruction = "DELETE FROM Cliente where clienteId = %s";
        sql.exec(String.format(instruction, scanner.next()));
    }

    private void createRegion(Region region) {
        System.out.println("\nInserte los datos de la region:");
        String[] fields = region.toString().split("\n");
        for (int i = 0; i < fields.length; i++) {
            fields[i] = fields[i].substring(fields[i].indexOf(" ") + 1);
            if (!fields[i].matches("[0-9]+")) {
                fields[i] = String.format("'%s'", fields[i]);
            }
        }
        sql.exec(String.format("INSERT INTO Region VALUES (%s)",
                                        String.join(", ", fields)));
    }

    private Region getRegionInfo() {
        while (true) {
            System.out.println("\nInserte los datos de la region:");
            System.out.print("Region: ");
            String regionName = scanner.next();
            return new Region(regionName);
        }
    }

    private Customer getCustomerInfo(Region region) {
        while (true) {
            System.out.println("\nInserte los datos del cliente:");
            System.out.print("Nombre: ");
            String name = scanner.next();
            System.out.print("Apellido: ");
            String surname = scanner.next();
            System.out.print("RFC: ");
            String rfc = scanner.next();
            System.out.print("Mail: ");
            String mail = scanner.next();
            return new Customer(name, surname, rfc, mail, region);
        }
    }

    private void createDatabase() {
        System.out.print("\nNombre de la Base de Datos: ");
        String databaseName = scanner.next();
        System.out.println("Creando base de datos...");
        sql.createDatabase(databaseName);
        System.out.println("Base de datos creada.\n");
        makeTableInfo(new Region().toString(), "Region", "");
        makeTableInfo(new Customer().toString(), "Cliente", "regionId");
    }

    private void makeTableInfo(String object, String objectName, String foreignKey) {
        List<String> fields = new ArrayList<String>(Arrays.asList(object.split("\n")));
        String primaryKey = objectName.toLowerCase() + "Id";
        String format = "";
        for (int i = 0; i < fields.size(); i++) {
            if (fields.get(i).contains("ID:")) {
                String key = getKey(fields.get(i)).replaceAll("id", "Id");
                fields.set(i, String.format("%s INT not NULL, ", key));
                continue;
            }
            format = "%s VARCHAR(255), ";
            fields.set(i, String.format(format, getKey(fields.get(i))));
        }
        if (foreignKey.isEmpty()) {
            format = "PRIMARY KEY (%s)";
            fields.add(String.format(format, primaryKey));
        } else {
            format = "PRIMARY KEY (%s), ";
            fields.add(String.format(format, primaryKey));
            format = "FOREIGN KEY (%s) REFERENCES %s(%s)";
            String otherTable = foreignKey.replaceAll("Id", "");
            otherTable = otherTable.substring(0, 1).toUpperCase() + otherTable.substring(1);
            fields.add(String.format(format, foreignKey, otherTable, foreignKey));
        }
        sql.createTable(objectName, fields);
    }

    private String getKey(String value) {
        return value.toLowerCase().substring(0, value.indexOf(":"));
    }

    private void loadDatabase() {
        while (true) {
            System.out.println(makeTable("Bases de Datos", sql.getDatabases()));
            System.out.print("Selecciona una Base de Datos: ");
            if (sql.selectDatabase(scanner.next())) {
                System.out.println("\nCargando base de datos...");
                System.out.println("Base de datos cargada.\n");
                break;
            }
        }
    }

    private void getSQLInfo() {
        while (true) {
            System.out.println("Ingresa tu usario y contraseña de SQL: ");
            System.out.print("Usuario: ");
            String username = scanner.next();
            Console console = System.console() ;
            char [] passwordChar = console.readPassword("Contraseña: ");
            String password = String.valueOf(passwordChar);
            Arrays.fill(passwordChar,' ');
            SQL sql = new SQL(username, password);
            if (sql.connect()) {
                this.sql = sql;
                break;
            } else {
                System.out.println("\nUsuario o contraseña incorrectos.\n");
            }
        }
    }

    private String makeTable(String title, String contents) {
        String[] words = contents.split("\n");
        int longestWordLength = Stream.of(words).map(String::length)
            .max(Integer::compareTo).get();
        String format = "| %-" + longestWordLength + "s | \n";
        String hline = makeLine(longestWordLength + 2) + "\n";
        StringBuilder s = new StringBuilder(hline);
        s.append(String.format(format, title));
        s.append(hline);
        for (String word: words) {
            s.append(String.format(format, word));
        }
        s.append(hline);
        return s.toString();
    }

    private String makeLine(int length) {
        return String.format("+%s+", "-".repeat(length));
    }
}
