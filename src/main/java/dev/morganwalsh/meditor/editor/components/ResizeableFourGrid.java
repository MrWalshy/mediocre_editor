package dev.morganwalsh.meditor.editor.components;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class ResizeableFourGrid<T extends JComponent> extends JPanel {

	JSplitPane topBottom;
	JSplitPane leftRight1;
	JSplitPane leftRight2;
	
	T comp1;
	T comp2;
	T comp3;
	T comp4;

	public ResizeableFourGrid(T comp1) {
		this(comp1, null, null, null);
	}

	public ResizeableFourGrid(T comp1, T comp2) {
		this(comp1, comp2, null, null);
	}

	public ResizeableFourGrid(T comp1, T comp2, T comp3) {
		this(comp1, comp2, comp3, null);
	}

	public ResizeableFourGrid(T comp1, T comp2, T comp3, T comp4) {
		this.comp1 = comp1;
		this.comp2 = comp2;
		this.comp3 = comp3;
		this.comp4 = comp4;
		
		topBottom = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		leftRight1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		leftRight2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		
		topBottom.setResizeWeight(1);
		topBottom.setDividerSize(4);
		topBottom.setOneTouchExpandable(true);
		
		leftRight1.setResizeWeight(0.5);
		leftRight1.setDividerSize(4);
		leftRight1.setOneTouchExpandable(true);
		
		leftRight2.setResizeWeight(0.5);
		leftRight2.setDividerSize(4);
		leftRight2.setOneTouchExpandable(true);
		
		setLayout(new BorderLayout());
		add(topBottom);
		
		topBottom.setTopComponent(leftRight1);
		topBottom.setBottomComponent(leftRight2);
		
		leftRight1.setLeftComponent(comp1);
		leftRight1.setRightComponent(comp2);
		leftRight2.setLeftComponent(comp3);
		leftRight2.setRightComponent(comp4);
	}
	
	public T getComponentByGridPosition(Position position) {
		switch (position) {
		case TOP_LEFT:
			return comp1;
		case TOP_RIGHT:
			return comp2;
		case BOTTOM_LEFT:
			return comp3;
		case BOTTOM_RIGHT:
			return comp4;
		default:
			throw new RuntimeException("Invalid position");
		}
	}
	
	public void setComponent(Position position, T component) {
		switch (position) {
		case TOP_LEFT:
			comp1 = component;
			leftRight1.setLeftComponent(component);
			break;
		case TOP_RIGHT:
			comp2 = component;
			leftRight1.setRightComponent(component);
			break;
		case BOTTOM_LEFT:
			comp3 = component;
			leftRight2.setLeftComponent(component);
			break;
		case BOTTOM_RIGHT:
			comp4 = component;
			leftRight2.setRightComponent(component);
			break;
		}
	}
	
	public void removeComponent(Position position) {
		switch (position) {
		case TOP_LEFT:
			leftRight1.setLeftComponent(null);
			break;
		case TOP_RIGHT:
			leftRight1.setRightComponent(null);
			break;
		case BOTTOM_LEFT:
			leftRight2.setLeftComponent(null);
			break;
		case BOTTOM_RIGHT:
			leftRight2.setRightComponent(null);
			break;
		}
	}
	
	public enum Position {
		TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT;
	}
}
