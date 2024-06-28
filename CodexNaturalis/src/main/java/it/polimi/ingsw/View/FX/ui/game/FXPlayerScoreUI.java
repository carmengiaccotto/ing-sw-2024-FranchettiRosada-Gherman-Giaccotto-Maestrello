package it.polimi.ingsw.View.FX.ui.game;

import it.polimi.ingsw.Model.PlayGround.Player;
import it.polimi.ingsw.View.FX.ui.FXMainUI;
import it.polimi.ingsw.View.FX.utils.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * This class represents the UI for a player's score in the game.
 * It extends the AnchorPane class and contains a reference to the player, a boolean indicating whether the player is the current user, and various UI elements.
 */
public class FXPlayerScoreUI extends AnchorPane {

    // Represents the player for this score UI.
    private Player player;

    // A boolean indicating whether the player is the current user.
    private boolean me;

    // The label for the player's name.
    @FXML
    private Label namePlayer;

    // The label for the player's score.
    @FXML
    private Label score;

    // The label for the player's round.
    @FXML
    private Label round;

    // The image view for the player's color.
    @FXML
    private ImageView colorImage;

    // The button for viewing the player's area.
    @FXML
    private Button viewAreaButton;

    // The main UI for the game.
    private FXMainUI mainUI;

    /**
     * This is the constructor for the FXPlayerScoreUI class.
     * It initializes the new FXPlayerScoreUI object with the given main UI, player, and boolean indicating whether the player is the current user.
     * It also sets up the view area button to show the area of the player when clicked.
     * @param mainUI The main UI for the game.
     * @param player The player for this score UI.
     * @param me A boolean indicating whether the player is the current user.
     */
    public FXPlayerScoreUI(FXMainUI mainUI,Player player, boolean me)
    {
        FXMLUtils.load(FXPlayerScoreUI.class,"FXPlayerScoreUI.fxml",this);
        this.player = player;
        this.me = me;

        this.mainUI = mainUI;

        if (me)
            viewAreaButton.setVisible(false);

        viewAreaButton.setOnAction(e->{

                mainUI.showAreaOf(player);
        });
    }

    /**
     * Returns the player for this score UI.
     * @return The player for this score UI.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns whether the player is the current user.
     * @return A boolean indicating whether the player is the current user.
     */
    public boolean isMe() {
        return me;
    }

    /**
     * Updates the score UI with the current information of the player.
     * It sets the text of the name, score, and round labels, and the image of the color image view.
     */
    public void update()
    {
        namePlayer.setText(player.getNickname());
        score.setText(""+player.getScore());
        round.setText(player.getRound()+"");
        colorImage.setImage(FXMainUI.colorMap.get(player.getPawnColor()));
    }
}