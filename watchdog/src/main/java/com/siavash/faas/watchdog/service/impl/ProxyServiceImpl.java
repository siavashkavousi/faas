package com.siavash.faas.watchdog.service.impl;

import com.siavash.faas.watchdog.config.Configs;
import com.siavash.faas.watchdog.service.ProxyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Slf4j
@Service
public class ProxyServiceImpl implements ProxyService {

	private final Configs configs;

	public ProxyServiceImpl(final Configs configs) {
		this.configs = configs;
	}

	@Override
	public String exec(String request) throws Exception {
		ProcessBuilder builder = new ProcessBuilder();
		builder.command(getCommands(request));
		Process process = builder.start();

		StringBuilder result = new StringBuilder();
		StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), content -> result.append(content).append("\n"));
		Executors.newSingleThreadExecutor().submit(streamGobbler);

		int exitCode = process.waitFor();
		if (exitCode == 0) {
			return result.toString();
		} else {
			logger.error("process finished with status code: {}", exitCode);
			return null;
		}
	}

	private List<String> getCommands(String request) {
		ArrayList<String> commands = new ArrayList<>(Arrays.asList(configs.getFunctionCommand().split(" ")));
		commands.add("-");
		if (!StringUtils.isEmpty(request)) {
			commands.add(request);
		}
		return commands;
	}

	private static class StreamGobbler implements Runnable {
		private InputStream inputStream;
		private Consumer<String> consumer;

		StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
			this.inputStream = inputStream;
			this.consumer = consumer;
		}

		@Override
		public void run() {
			new BufferedReader(new InputStreamReader(inputStream)).lines().forEach(consumer);
		}
	}

}
