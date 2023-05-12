package dev.morganwalsh.meditor.editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class GridBagResizePane extends JPanel {

	private GridBagLayout layout;
	private GridBagConstraints constraints;
	private JComponent selectedComponent;
	private Cursor oldCursor;
	private Point dragStart;
	private Border resizeBorder = new LineBorder(Color.BLACK, 1);
	private Cursor resizeCursor = Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);
	
	public GridBagResizePane() {
		layout = new GridBagLayout();
		setLayout(layout);
		constraints = new GridBagConstraints();
	}
	
	public void add(JComponent component, int x, int y, int width, int height) {
		component.setBorder(resizeBorder);
		initMouseListener(component);
		initMouseMotionListener(component);
		
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = width;
		constraints.gridheight = height;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.fill = GridBagConstraints.BOTH;
		
		layout.setConstraints(component, constraints);
		super.add(component);
	}
	
	public void remove(JComponent component) {
		layout.removeLayoutComponent(component);
		super.remove(component);
	}

	private void initMouseMotionListener(JComponent component) {
		component.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				super.mouseMoved(e);
				if (selectedComponent == null || dragStart == null) return;
				// check if mouse is over boarder of component
				if (isPointWithinBorders(e.getPoint(), component)) {
					System.out.println("CURSOR WITHIN BORDER");
				} else {
					System.out.println("CURSOR NOT IN BORDER");
//					component.setCursor(oldCursor);
				}
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				super.mouseDragged(e);
				if (selectedComponent == null || dragStart == null) return;
				
				int dx = e.getX() - dragStart.x;
				int dy = e.getY() - dragStart.y;
				
				GridBagConstraints constraints = new GridBagConstraints();
				int componentWidth = component.getWidth();
				int componentHeight = component.getHeight();
				double percentChange = 0.0;
				
				if (dx >= 0) {
					percentChange = dx / componentWidth * 100;
					System.out.println("dx % change: " + percentChange);
				}
				
				revalidate();
				repaint();
			}

			private boolean isPointWithinBorders(Point point, JComponent component) {
				int topBorder = component.getInsets().top, bottomBorder = component.getInsets().bottom;
				int leftBorder = component.getInsets().left, rightBorder = component.getInsets().right;
				int mouseX = point.x, mouseY = point.y;
				
				if (mouseY >= 0
						&& mouseY <= topBorder
						&& mouseX >= 0
						&& mouseX <= component.getWidth()
				) 
				{
					// top border hover
					component.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
					return true;
				} 
				else if (mouseY >= component.getHeight() - bottomBorder
						&& mouseY <= component.getHeight()
						&& mouseX >= 0
						&& mouseX <= component.getWidth()
				) 
				{
					// bottom border
					component.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
					return true;
				} 
				else if (mouseX >= component.getWidth() - rightBorder
						&& mouseX <= component.getWidth()
						&& mouseY >= 0
						&& mouseY <= component.getHeight()
				)
				{
					// right border
					component.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
					return true;
				} 
				else if (mouseX >= 0
						&& mouseX <= leftBorder
						&& mouseY >= 0
						&& mouseY <= component.getHeight()
				)
				{
					component.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
					return true;
				}
				return false;
			}
		});
	}

	private void initMouseListener(JComponent component) {
		component.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				selectedComponent = component;
				oldCursor = component.getCursor();
				dragStart = e.getPoint();
				System.out.println("COMPONENT DRAG START");
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				selectedComponent.setCursor(oldCursor);
				selectedComponent = null;
				dragStart = null;
				oldCursor = null;
			}
		});
	}
	
	
}
