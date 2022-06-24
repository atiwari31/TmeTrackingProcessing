package com.dcardprocessing;

import java.time.LocalDate;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.dcardprocessing.bean.License;
import com.dcardprocessing.config.StageManager;
import com.dcardprocessing.util.JDBCConnection;
import com.dcardprocessing.view.FxmlView;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

@SpringBootApplication
@EnableScheduling
public class DCardProcessingApp extends Application {

	protected ConfigurableApplicationContext springContext;
	protected StageManager stageManager;

	public static void main(final String[] args) {
		Application.launch(args);
	}

	@Override
	public void init() throws Exception {
		springContext = springBootApplicationContext();
	}

	@Override
	public void start(Stage stage) throws Exception {

		stageManager = springContext.getBean(StageManager.class, stage);
		displayInitialScene();
	}

	@Override
	public void stop() throws Exception {
		springContext.close();
	}

	/**
	 * Useful to override this method by sub-classes wishing to change the first
	 * Scene to be displayed on startup. Example: Functional tests on main window.
	 */
	protected void displayInitialScene() {
		License license = JDBCConnection.getLicense();
		LocalDate d = LocalDate.now();

		if (license.getTotalIP() > 0) {
			if (JDBCConnection.updateLicense()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("License has been Expired!");
				alert.setHeaderText(null);
				alert.setContentText("Number of host machine crossed limit : " + license.getTotalIP());
				alert.showAndWait();
				System.exit(0);

			}
		} else if (null != license.getEndDate() && license.getEndDate().toLocalDate().isBefore(d)) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("License has been Expired!");
			alert.setHeaderText(null);
			alert.setContentText("Your license expired. You must renew license :" + license.getEndDate());
			alert.showAndWait();
			System.exit(0);

		} else if (JDBCConnection.updateSingleLicense()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("License has been Expired!");
			alert.setHeaderText(null);
			alert.setContentText("Software Already Installed on System  :" + license.getIpAddress());
			alert.showAndWait();
			System.exit(0);
		}
		stageManager.switchScene(FxmlView.LOGIN);

	}

	private ConfigurableApplicationContext springBootApplicationContext() {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(DCardProcessingApp.class);
		String[] args = getParameters().getRaw().stream().toArray(String[]::new);
		return builder.run(args);
	}

}
