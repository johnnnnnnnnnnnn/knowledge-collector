package ru.kc.tools.filepersist.persist.transaction.actions;

import java.util.ArrayList;
import java.util.List;

import ru.kc.tools.filepersist.model.impl.Container;
import ru.kc.tools.filepersist.model.impl.NodeBean;
import ru.kc.tools.filepersist.persist.transaction.AtomicAction;
import ru.kc.util.collection.Pair;

public class GetChildren extends AtomicAction<List<NodeBean>>{
	
	NodeBean parent;
	ArrayList<Container> loadContainers;
	
	

	public GetChildren(NodeBean parent) {
		super();
		this.parent = parent;
	}

	@Override
	protected List<NodeBean> invoke() throws Throwable {
		ArrayList<NodeBean> out = new ArrayList<NodeBean>();
		loadContainers = new ArrayList<Container>();
		
		List<String> childrenIds = parent.getChildrenIds();
		for (String path : childrenIds) {
			Pair<String,String> data = parent.parse(path);
			String simpleFilePath = data.getFirst();
			Container container = c.containerModel.getContainer(simpleFilePath);
			if(container == null) throw new IllegalStateException("container not found by path "+simpleFilePath);
				
			String childId = data.getSecond();
			NodeBean node = container.find(childId);
			if(node != null){
				out.add(node);
			} else {
				log.error("can'f find child by id "+childId+" in "+container);
			}
		}
		
		return out;
	}

	@Override
	protected void rollback() throws Throwable {}

}
