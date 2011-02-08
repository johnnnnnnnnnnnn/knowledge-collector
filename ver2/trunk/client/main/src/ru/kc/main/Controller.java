package ru.kc.main;

import ru.kc.main.event.ChildAdded;
import ru.kc.model.Node;
import ru.kc.platform.annotations.Inject;
import ru.kc.platform.controller.AbstractController;
import ru.kc.platform.event.annotation.EventListener;
import ru.kc.platform.runtimestorage.RuntimeStorageService;
import ru.kc.tools.filepersist.Factory;
import ru.kc.tools.filepersist.PersistService;
import ru.kc.tools.filepersist.Tree;

public abstract class Controller<T> extends AbstractController<T>{
	
	@Inject
	protected Context context;
	protected PersistService persist;
	protected Tree persistTree;
	protected Factory persistFactory;
	
	@Override
	protected void beforeInit() {
		persist = context.persistService;
		persistTree = persist.tree();
		persistFactory = persist.factory();
	}
	
	protected void onChildAdded(Node parent, Node child){ /* override if need */ }
	
	
	@EventListener(ChildAdded.class)
	public void _onChildAdded(ChildAdded event){
		onChildAdded(event.parent, event.child);
	}
	
	

}
