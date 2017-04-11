package view;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public interface GameScreen {
	public Pane update();
	public Pane create();
	public Image getPlayerImage();
	public Image getOponentImage();
	public void playTurnSound();
}
