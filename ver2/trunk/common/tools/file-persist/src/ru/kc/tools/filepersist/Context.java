package ru.kc.tools.filepersist;

import java.io.File;

import ru.kc.tools.filepersist.command.Command;
import ru.kc.tools.filepersist.impl.ConvertorServiceImpl;
import ru.kc.tools.filepersist.model.DataFactoryImpl;
import ru.kc.tools.filepersist.persist.FileSystemImpl;

public class Context {
	
	public final FileSystemImpl fs;
	public final DataFactoryImpl dataFactory;
	public final PersistServiceImpl persistService;
	public final ConvertorServiceImpl convertorService;
	

	public Context(FileSystemImpl fs, DataFactoryImpl dataFactory,
			PersistServiceImpl persistService,
			ConvertorServiceImpl convertorService) {
		super();
		this.fs = fs;
		this.dataFactory = dataFactory;
		this.persistService = persistService;
		this.convertorService = convertorService;
	}


	public <O> O invoke(Command<O> command) throws Exception{
		command.setContext(this);
		O out = command.invoke();
		return out;
	}

}
