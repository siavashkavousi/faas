package com.siavash.faas.watchdog.service.impl;

import com.siavash.faas.watchdog.config.Configs;
import com.siavash.faas.watchdog.service.ProxyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;
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
		builder.command(configs.getFunctionCommand().split(" "));
		builder.redirectErrorStream(true);
		Process process = builder.start();

		if (!StringUtils.isEmpty(request)) {
			OutputStream stdin = process.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));
			writer.write(request);
			writer.flush();
			writer.close();
		}

		StringBuilder output = new StringBuilder();
		StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), content -> output.append(content).append("\n"));
		Executors.newSingleThreadExecutor().submit(streamGobbler);

		int exitCode = process.waitFor();
		if (exitCode != 0) {
			logger.error("process finished with status code: {}", exitCode);
		}

		return output.toString();
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
