package WillHero;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ReGenerateHero {
    private Hero generatedHero;

    public Hero getHero(String fileName) throws IOException, ClassNotFoundException {

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            try {
                generatedHero = (Hero) in.readObject();
            } catch (ClassCastException e) {
                System.out.println("Invalid Cast Exception");
            }
        }
        return generatedHero;
    }
}
