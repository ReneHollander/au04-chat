package at.hollanderpoecher.chat.util;

import java.util.concurrent.Semaphore;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * 
 * Helper Class to launch an JavaFX stage from everywhere in an application.
 * After the Window ist shown, we return to the Thread that executed the call.
 * 
 * Source:
 * http://a-hackers-craic.blogspot.co.at/2014/08/starting-javafx-from-random
 * -java-code.html
 * 
 * @author NOTZED
 */
public class FXUtils {

	static FXApplication app;
	static Semaphore sem = new Semaphore(0);

	/**
	 * Launch a stage
	 * 
	 * @param r
	 *            Runnable that launches a stage
	 */
	public static void startFX(Runnable r) {
		if (app == null) {
			try {
				Thread t = new Thread(() -> {
					FXApplication.start(r);
				});
				t.start();

				sem.acquire();
			} catch (InterruptedException ex) {
			}
		} else {
			Platform.runLater(r);
		}
	}

	/**
	 * Application Wrapper to run a Runnable as an FX App
	 * 
	 * @author NOTZED
	 */
	public static class FXApplication extends Application {

		static Runnable run;

		@Override
		public void start(Stage stage) throws Exception {
			app = this;
			run.run();
			sem.release();
		}

		/**
		 * @param r
		 */
		public static void start(Runnable r) {
			run = r;
			// Application.launch() can only be called from a static
			// method from a class that extends Application
			Application.launch();
			// no windows - no app!
			System.exit(0);
		}
	}
}
