package pres;

import dao.IDao;
import metier.IMetier;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Scanner;

public class Presentation2 {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("src/main/config.txt"));

            // Lecture du nom de la classe DAO
            String daoClassName = scanner.nextLine();
            Class cDao = Class.forName(daoClassName);
            IDao dao = (IDao) cDao.getConstructor().newInstance(); // Utilisation de getConstructor pour instancier correctement

            // Lecture du nom de la classe Metier
            String metierClassName = scanner.nextLine();
            Class cMetier = Class.forName(metierClassName);
            IMetier metier = (IMetier) cMetier.getConstructor(IDao.class).newInstance(dao);

            // Injection de l'IDao dans l'objet Metier
            Method setDaoMethod = cMetier.getMethod("setDao", IDao.class);
            setDaoMethod.invoke(metier, dao); // Invocation de setDao sur l'objet metier

            // Appel de la méthode calcul() et affichage du résultat
            System.out.println("RES=" + metier.calcul());

            scanner.close(); // Fermeture du scanner après utilisation

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
