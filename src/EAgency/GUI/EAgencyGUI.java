package EAgency.GUI;

import EAgency.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

/**
 * The type E agency gui.
 */
public class EAgencyGUI {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        File data = new File("objectStorage.dat");
        if (data.exists()) {
            ObjectPlus.loadExtent();
        } else
        {
            Address address1 = new Address("Wolska", "Warszawa", "00-000", 20);
            Address address2 = new Address("Grochowska", "Warszawa", "00-000", 20);
            Address address3 = new Address("Kosciuszki", "Warszawa", "00-000", 20);
            Address address4 = new Address("Majowa", "Warszawa", "00-000", 20);
            Address address5 = new Address("Zeromskiego", "Warszawa", "00-000", 20);
            Address address6 = new Address("Reja", "Warszawa", "00-000", 20);
            Neighbourhood neighbourhood1 = new Neighbourhood("Brave new Neighbourhood", 33000);
            Property house1 = House.createHouse(neighbourhood1, address1, 2000, 5, 30000, 1999, 200);
            Property house2 = House.createHouse(neighbourhood1, address2, 3000, 3, 70000, 2022, 400);
            Property apartment1 = Apartment.createApartment(neighbourhood1, address3, 3000, 3, 15000, 2021, 11, 500);
            Property apartment2 = Apartment.createApartment(neighbourhood1, address4, 3000, 3, 7000, 2020, 21, 400);
            Company company1 = new Company("Company1", address5, 1999);
            Person estateAgent1 = new Person("Andrzej", "Domowski", "99999999999", LocalDate.now().minusYears(30), 0.01f);
            Person estateAgent2 = new Person("Paulina", "Nowak", "99999999998", LocalDate.now().minusYears(30), 0.01f, 5000d);
            Trainee trainee = new Trainee("Jan", "Kowalski", "99999999997", LocalDate.now().minusYears(44), LocalDate.now().plusYears(1));
            trainee.setCompany(company1);
            Person convertedTrainee = trainee.becomeAgent();
            company1.addEmployment(new Employment(LocalDate.now().minusYears(10), null, "asdf", company1, estateAgent1));
            apartment2.setCompany(company1);

            house1.setEstateAgent(estateAgent1);
            house2.setEstateAgent(estateAgent2);
            apartment1.setEstateAgent(convertedTrainee);
            apartment2.setEstateAgent(estateAgent1);
        }
//        Property house666 = House.createHouse(new Neighbourhood("aaa", 89999), null, 2000, 5, 30000, 1999, 200);
        JFrame frame = new JFrame("EAgency App");
        frame.setMinimumSize(new Dimension(800, 600));
        Person client = ObjectPlus.getExtentForClass(Person.class)
                .stream().filter(i -> i.getSurname().equals("Soszynski"))
                .findFirst()
                .orElse(new Person("Artur", "Soszynski", "99999999999", LocalDate.now().minusYears(20), 60000d));
        Person andrzej = ObjectPlus.getExtentForClass(Person.class)
                .stream().filter(i -> i.getSurname().equals("Domowski"))
                .findFirst()
                .orElse(new Person("Artur", "Soszynski", "99999999999", LocalDate.now().minusYears(20), 60000d));
        frame.setContentPane(new CardGUILayout(client).getMainAppPanel());
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
                try {
                    ObjectPlus.saveExtent();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        System.out.println(client.getClient().getSaleSet());
        System.out.println(andrzej.getEstateAgent().getProperties());
        System.out.println(client.getClient().getBudget());
    }
}
