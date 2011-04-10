package ru.kc.module.imports.oldclient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import ru.kc.common.controller.Controller;
import ru.kc.model.Node;
import ru.kc.module.imports.oldclient.chain.SearchData;
import ru.kc.module.imports.oldclient.model.ImportOldDataTextModel;
import ru.kc.module.imports.ui.ImportOldDataDialog;
import ru.kc.platform.annotations.Mapping;
import ru.kc.util.workflow.Chain;
import ru.kc.util.workflow.ChainListener;
import ru.kc.util.workflow.ChainObject;

@Mapping(ImportOldDataDialog.class)
public class ImportOldDataController extends Controller<ImportOldDataDialog> {
	
	JTextArea textArea;
	Node importRoot;
	File dataDir;
	boolean abort;

	@Override
	protected void init() {
		textArea = ui.importPanel1.text;
		textArea.setEditable(false);
		
		ui.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				startImport();
			}
			
		});
		
		ui.okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ui.dispose();
			}
		});
		ui.okButton.setEnabled(false);
		
		ui.cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	public void setImportData(Node importRoot, File dataDir){
		this.importRoot = importRoot;
		this.dataDir = dataDir;
	}
	
	@SuppressWarnings("rawtypes")
	protected void startImport() {
		
		final ImportOldDataTextModel textModel = new ImportOldDataTextModel();
		textModel.title = "Importing...";
		
		final ArrayList<Object> chainContext = new ArrayList<Object>();
		chainContext.add(textModel);
		chainContext.add(dataDir);
		chainContext.add(importRoot);
		chainContext.add(new HashMap<String, Object>());
		chainContext.add(new DataLoader());
		
		SwingWorker worker = new SwingWorker() {

			@Override
			protected Object doInBackground() throws Exception {
				
				SearchData first = new SearchData();
				Chain chain = new Chain(first, chainContext, new ChainListener() {
					
					@Override
					public void onFinish() {
						textModel.addText("IMPORT PASSED");
					}
					
					@Override
					public void onException(ChainObject ob, Exception e) {
						textModel.addText("Error! Message: "+ e.getMessage());
						textModel.addText("IMPORT FAILED");
					}
					
					@Override
					public boolean continueAfter(ChainObject ob) {
						publishChainData();
						return true;
					}

					@Override
					public void onCanceled(ChainObject ob) {
						
					}
				});
				chain.execute();
				return null;
			}
			
			@SuppressWarnings("unchecked")
			public void publishChainData(){
				publish();
			}
			
			@Override
			protected void process(List chunks) {
				textArea.setText(textModel.getText());
			}
			
			@Override
			protected void done() {
				textModel.addText("You can close dialog window");
				textArea.setText(textModel.getText());
				ui.okButton.setEnabled(true);
				ui.cancelButton.setEnabled(false);
			}
		}; 
		worker.execute();
	}

}
