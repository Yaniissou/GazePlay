package net.gazeplay.games.colorblend;

import javafx.geometry.Dimension2D;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import lombok.extern.slf4j.Slf4j;
import net.gazeplay.GameLifeCycle;
import net.gazeplay.IGameContext;
import net.gazeplay.commons.utils.stats.Stats;

/**
 * Choose between multiple colors and blend them together
 * @author Yanis HARKATI
 */
@Slf4j
public class ColorBlend implements GameLifeCycle {

    private IGameContext gameContext;
    private Stats stats;

    public ColorBlend(final IGameContext gameContext, Stats stats){
        this.gameContext = gameContext;
        this.stats = stats;
        final Dimension2D dimensions = gameContext.getGamePanelDimensionProvider().getDimension2D();
        createBackground();
    }
    @Override
    public void launch() {

    }

    @Override
    public void dispose() {

    }

    private void createBackground() {
        Background background = new Background(new BackgroundImage(
            new Image("data/colorblend/images/park.png"),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true)
        ));
        gameContext.getRoot().setBackground(background);
    }
}