package dev.morganwalsh.meditor;

import java.io.IOException;
import java.util.List;

import javax.swing.*;

import dev.morganwalsh.meditor.editor.components.grid.AbstractGrid;
import dev.morganwalsh.meditor.editor.components.grid.GridRow;

public class App {

	public static void main(String[] args) throws IOException {
//		SwingUtilities.invokeLater(() -> new UI());
//		Fox fox = new Fox();
//		fox.runPrompt();
		FooDisplay display = new FooDisplay();
		display.getRow().getColumnData().forEach(column -> {
			column.accept(display);
		});
		// Looks like having generic windows whose content could
		// be of diff. types will work... but the design means
		// a runtime error will occur if the visitor doesn't
		// have a supporting method
		// - the reflective visitor might be a better option
		//   this code is messy
	}

}

abstract class FooWindow<T extends JComponent> extends JPanel implements FooVisitable {

}

final class FooTextWindow extends  FooWindow<JTextPane> {

	@Override
	public void accept(FooVisitor visitor) {
		visitor.visit(this);
	}
}

final class FooButtonWindow extends FooWindow<JButton> {

	@Override
	public void accept(FooVisitor visitor) {
		visitor.visit(this);
	}
}

class FooDisplay extends AbstractGrid<FooWindow<?>> implements FooVisitor {

	public FooDisplay() {
		setGridComponent(0, 0, new FooButtonWindow());
		setGridComponent(0, 1, new FooTextWindow());
	}

	public GridRow<FooWindow<?>> getRow() {
		return rowData.get(0);
	}

	@Override
	public void visit(FooTextWindow pane) {
		System.out.println("FOOTEXT");
	}

	@Override
	public void visit(FooButtonWindow pane) {
		System.out.println("FOOBUTTON");
	}
}
interface FooVisitor {
	void visit(FooTextWindow pane);
	void visit(FooButtonWindow pane);
}

interface FooVisitable {
	void accept(FooVisitor visitor);
}