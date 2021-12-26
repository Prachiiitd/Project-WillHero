package WillHero;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ReGenerateHero {
    private Hero generatedHero;

    public Hero getHero(String fileName) throws IOException, ClassNotFoundException {

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            try {
                generatedHero = (Hero) in.readObject();
                generatedHero.setHero();
                generatedHero.getHelmet().getWeapon(0).setWeapon();
                generatedHero.getHelmet().getWeapon(1).setWeapon();
            } catch (ClassCastException e) {
                System.out.println("Hero: Invalid Cast Exception");
            }
        }
        return generatedHero;
    }
}
