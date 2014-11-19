package at.hollanderpoecher.chat.util;

import java.util.concurrent.Semaphore;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

public class FXUtils {

	static FXApplication app;
	static Semaphore sem = new Semaphore(0);

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

	public static class FXApplication extends Application {

		WritableImage image;
		static Runnable run;

		public FXApplication() {
		}

		@Override
		public void start(Stage stage) throws Exception {
			app = this;
			run.run();
			sem.release();
		}

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
