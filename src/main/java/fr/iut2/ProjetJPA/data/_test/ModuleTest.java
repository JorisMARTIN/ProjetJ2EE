package fr.iut2.ProjetJPA.data._test;

import fr.iut2.ProjetJPA.data.GestionFactory;
import fr.iut2.ProjetJPA.data.module.Module;
import fr.iut2.ProjetJPA.data.module.ModuleDAO;

import java.util.List;

public class ModuleTest {

    public static void main(String[] args) {
        GestionFactory.open();

        final String MODULE_NAME = "MI4";
        Module MI4 = ModuleDAO.create(MODULE_NAME);

        int id = MI4.getId();

        Module module = ModuleDAO.findById(id);
        System.out.print("ModuleDAO.findById : " );
        if (module != null && module.getId() == id) {
            System.out.println("OK");
        } else {
            System.out.println("ERREUR");
            return;
        }

        final String newName = "MI2";

        System.out.print("ModuleDAO.update : " );
        module.setName(newName);
        ModuleDAO.update(module);
        module = ModuleDAO.findById(id);
        if (module.getName().equalsIgnoreCase(newName)) {
            System.out.println("OK");
        } else {
            System.out.println("ERREUR");
            return;
        }

        System.out.print("ModuleDAO.remove : " );
        ModuleDAO.remove(module);
        module = ModuleDAO.findById(id);
        if (module == null) {
            System.out.println("OK");
        } else {
            System.out.println("ERREUR");
            return;
        }

        module = ModuleDAO.create("MI4");
        id = module.getId();

        System.out.print("ModuleDAO.removeById : " );
        ModuleDAO.removeById(id);
        module = ModuleDAO.findById(id);
        if (module == null) {
            System.out.println("OK");
        } else {
            System.out.println("ERREUR");
            return;
        }

        ModuleDAO.create("MI4");
        ModuleDAO.create("INFO7");
        ModuleDAO.create("ENV1");

        System.out.print("ModuleDAO.getAll : " );
        List<Module> modules_TMP = ModuleDAO.getAll();
        if (modules_TMP.size() == 3) {
            System.out.println("OK");
        } else {
            System.out.println("ERREUR");
            return;
        }

        System.out.print("ModuleDAO.removeAll : ");
        ModuleDAO.removeAll();
        modules_TMP = ModuleDAO.getAll();
        if (modules_TMP.size() == 0) {
            System.out.println("OK");
        } else {
            System.out.println("ERREUR");
            return;
        }


        GestionFactory.close();
    }
}
