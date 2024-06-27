package it.polimi.ingsw.View.FX.ui.game;

import it.polimi.ingsw.Model.PlayGround.Player;
import it.polimi.ingsw.View.FX.ui.FXMainUI;
import it.polimi.ingsw.View.FX.utils.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;



public class FXPlayerScoreUI extends AnchorPane {
    private Player player;
    private boolean me;

    @FXML
    private Label namePlayer;

    @FXML
    private Label score;

    @FXML
    private Label round;

    @FXML
    private ImageView colorImage;

    @FXML
    private Button viewAreaButton;

    private FXMainUI mainUI;


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

    public Player getPlayer() {
        return player;
    }

    public boolean isMe() {
        return me;
    }

    public void update()
    {
        namePlayer.setText(player.getNickname());
        score.setText(""+player.getScore());
        round.setText(player.getRound()+"");
        colorImage.setImage(FXMainUI.colorMap.get(player.getPawnColor()));
    }
}
