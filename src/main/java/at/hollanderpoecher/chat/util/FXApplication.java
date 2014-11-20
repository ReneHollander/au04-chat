package at.hollanderpoecher.chat.util;

import java.util.concurrent.Semaphore;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * A Helper Class that holds an FX Application at when launched. If you launch a
 * stage the method will block until the run() method ends.
 * 
 * @author Rene Hollander
 */
public class FXApplication extends Application {

	private static Semaphore sem;
	private static Application app;
	private static Stage primaryStage;

	/**
	 * Launches a FX Application. If one instance was launched before, it won't
	 * launch it again.
	 */
	public static void launch() {
		if (app == null && sem == null && primaryStage == null) {
			sem = new Semaphore(0);
			Thread t = new Thread(() -> {
				Application.launch(FXApplication.class);
				System.exit(0);
			});
			t.start();
			try {
				sem.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Runs the Runnable on thr FX Thread and waits till run() finished.
	 * 
	 * @param runnable
	 *            Runnable
	 */
	public static void launchStage(Runnable runnable) {
		Platform.runLater(() -> {
			runnable.run();
			sem.release();
		});
		try {
			sem.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		app = this;
		FXApplication.primaryStage = primaryStage;
		sem.release();
	}

	/**
	 * Get the Primary Stage from the FX Application
	 * 
	 * @return PrimaryStage
	 */
	public static Stage getPrimaryStage() {
		return primaryStage;
	}
}
