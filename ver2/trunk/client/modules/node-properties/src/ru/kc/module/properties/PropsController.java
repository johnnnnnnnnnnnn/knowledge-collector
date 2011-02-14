package ru.kc.module.properties;

import java.awt.BorderLayout;
import java.awt.Component;

import ru.kc.common.controller.Controller;
import ru.kc.common.tree.event.NodeSelected;
import ru.kc.model.Dir;
import ru.kc.model.FileLink;
import ru.kc.model.Link;
import ru.kc.model.Node;
import ru.kc.model.Text;
import ru.kc.module.properties.node.NodePropsModule;
import ru.kc.module.properties.ui.PropsPanel;
import ru.kc.platform.annotations.Mapping;
import ru.kc.platform.event.annotation.EventListener;
import ru.kc.platform.module.Module;

@Mapping(PropsPanel.class)
public class PropsController extends Controller<PropsPanel> {

	@Override
	protected void init() {
		ui.setLayout(new BorderLayout());
	}
	
	
	@EventListener(NodeSelected.class)
	public void onNodeSelected(NodeSelected event){
		Node node = event.node;
		showProps(node);
	}

	private void showProps(Node node) {
		if(node instanceof Dir){
			showProps((Dir)node);
		} else if(node instanceof Link){
			showProps((Link)node);
		} else if(node instanceof FileLink){
			showProps((FileLink)node);
		} else if(node instanceof Text){
			showProps((Text)node);
		} else {
			showPropsForUnknowType(node);
		}
	}


	private void showProps(Dir node) {
		NodePropsModule module = new NodePropsModule();
		module.setAppContext(appContext);
		module.setNode(node);
		show(module);
	}
	
	private void showProps(Link node) {
		// TODO Auto-generated method stub
		
	}
	
	private void showProps(FileLink node) {
		// TODO Auto-generated method stub
		
	}
	
	private void showProps(Text node) {
		// TODO Auto-generated method stub
		
	}
	
	private void showPropsForUnknowType(Node node) {
		// TODO Auto-generated method stub
		
	}
	
	
	private void show(Component component){
		clearOld();
		ui.add(component);
		ui.revalidate();
	}


	private void clearOld() {
		Component[] old = ui.getComponents();
		for(Component c : old){
			Module.removeAllListneres(c);
		}
		ui.removeAll();
	}

}
