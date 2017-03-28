package view;

import javafx.scene.layout.Pane;

public interface WindowScreen {
	public void create();
	public void update();
	public Pane getPane();
}
