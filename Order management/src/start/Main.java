package start;

import connection.ConnectionFactory;
import presentation.Controller;
import presentation.View;

public class Main {
	public static void main(String[] args) {
		ConnectionFactory.getConnection();
		View view = new View();
		Controller controller = new Controller(view);
		view.setVisible(true);
	}
}
