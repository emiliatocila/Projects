package main.java.start;

import main.java.presentation.View;
import main.java.presentation.Controller;

public class Main {
    public static void main(String[] args){
        View view = new View();
        Controller controller = new Controller(view);
        view.setVisible(true);
    }
}
