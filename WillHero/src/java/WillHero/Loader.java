package WillHero;


import java.io.*;

public class Loader implements Serializable {
    int score;
    int color;
    int obstacle;
    double corX,corY;
    Loader() {
        Loader temp = bringIn();
        this.score = temp.score;
        this.color = temp.color;
        this.obstacle = temp.obstacle;
        this.corX = temp.corX;
        this.corY = temp.corY;
    }
    Loader(int s,int c, int ob ,double x, double y){
        score = s;
        color = c;
        obstacle = ob;
        corX = x;
        corY = y;
    }
    public Loader bringIn(){
        try {
            FileInputStream BringIn = new FileInputStream("Load.txt");
            ObjectInputStream in = new ObjectInputStream(BringIn);
            Loader temp = (Loader) in.readObject();

            in.close();
            BringIn.close();
            return temp;

        }
        catch (IOException i){

        }
        catch (ClassNotFoundException i){

        }
        return null;
    }
    public void writeOut()throws IOException {
        FileOutputStream WriteOut = new FileOutputStream("Load.txt");
        ObjectOutputStream out = new ObjectOutputStream(WriteOut);
        out.writeObject(this);
        out.close();
        WriteOut.close();

    }
}
