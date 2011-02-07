package ru.kc.main.tree.tools;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import ru.kc.main.model.NodeIcon;
import ru.kc.model.Node;


public class CellRender extends DefaultTreeCellRenderer {
	
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
				row, hasFocus);
		Object ob = ((DefaultMutableTreeNode)value).getUserObject();
		if(ob instanceof Node){
			Node node = (Node) ob;
			Icon icon = NodeIcon.getIcon(node);
			String text = node.getName();
			
			setIcon(icon);
			setText(text);
		}
		return this;
		
	}

}
