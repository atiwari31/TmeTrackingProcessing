package com.timetracking;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import javafx.application.Application;

@SpringBootApplication
@EnableScheduling
public class TimeTrackingApp {

	protected ConfigurableApplicationContext springContext;

	public static void main(final String[] args) {
		Application.launch(args);
	}

	}


