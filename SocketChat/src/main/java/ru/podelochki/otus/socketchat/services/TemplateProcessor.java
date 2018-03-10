package ru.podelochki.otus.socketchat.services;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

@Service
public class TemplateProcessor {
	private Configuration cfg;
	
	public TemplateProcessor() throws Exception {
		cfg = new Configuration(Configuration.VERSION_2_3_27);
		cfg.setClassForTemplateLoading(this.getClass(), "/");
		System.out.println(this.getClass().getResource("/"));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setLogTemplateExceptions(false);
		cfg.setWrapUncheckedExceptions(true);
	}

	public Configuration getCfg() {
		return cfg;
	}
}
