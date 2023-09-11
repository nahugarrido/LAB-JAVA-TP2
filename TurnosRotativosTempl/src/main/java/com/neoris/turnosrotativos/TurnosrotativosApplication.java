package com.neoris.turnosrotativos;

import com.neoris.turnosrotativos.converters.EmpleadoConverter;
import com.neoris.turnosrotativos.converters.JornadaConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TurnosrotativosApplication {

	static Logger logger = LogManager.getLogger(TurnosrotativosApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TurnosrotativosApplication.class, args);
		logger.info("===========================================");
		logger.info("============    APP RUNNING    ============");
		logger.info("===========================================");
	}

	@Bean
	public ModelMapper modelMapper(EmpleadoConverter empleadoConverter, JornadaConverter jornadaConverter) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addConverter(jornadaConverter);
		modelMapper.addConverter(empleadoConverter);

		return modelMapper;
	}
}
