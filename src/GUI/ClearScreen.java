package GUI;

public class ClearScreen {
    public void clear(){
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }
        catch(Exception e){
            System.out.println("An exception occurred while clearing the screen.");
            e.printStackTrace();
        }
    }
}
