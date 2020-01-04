package net.gazeplay.games.moles;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import net.gazeplay.IGameContext;
import net.gazeplay.commons.gaze.devicemanager.GazeEvent;

import java.util.Random;

@Slf4j
public class MolesChar extends Parent {

    private Rectangle mole;

    private Rectangle moleMoved;

    private final IGameContext gameContext;

    private final Moles gameInstance;

    private final double distTranslation;

    private boolean touched;
    private boolean canTouched;
    boolean canGoOut;
    public boolean out;

    private ProgressIndicator progressIndicator;

    private Timeline timeMinToWhackTheMole;

    private Timeline timeMoleOut;

    private final int timeMoleStayOut = 2500;

    public final EventHandler<Event> enterEvent;

    MolesChar(
        double positionX, double positionY,
        double width, double height,
        double distTrans,
        IGameContext gameContext,
        Moles gameInstance
    ) {
        this.gameContext = gameContext;

        this.out = false;

        this.distTranslation = distTrans;

        touched = false;
        canTouched = false;
        canGoOut = true;

        this.gameInstance = gameInstance;

        this.progressIndicator = createProgressIndicator(width, height);

        this.enterEvent = buildEvent();

        this.moleMoved = new Rectangle(positionX, positionY - distTrans, width, height);
        this.moleMoved.setFill(new ImagePattern(new Image("data/whackmole/images/bibouleMole.png"), 5, 5, 1, 1, true));
        this.moleMoved.opacityProperty().set(1);
        this.moleMoved.addEventHandler(MouseEvent.ANY, enterEvent);
        this.moleMoved.addEventHandler(GazeEvent.ANY, enterEvent);
        gameContext.getGazeDeviceManager().addEventFilter(this.moleMoved);

        this.mole = new Rectangle(positionX, positionY, width, height);
        this.mole.setFill(new ImagePattern(new Image("data/whackmole/images/bibouleMole.png"), 5, 5, 1, 1, true));
        this.getChildren().add(mole);
        this.mole.opacityProperty().set(0);

    }

    private ProgressIndicator createProgressIndicator(double width, double height) {
        ProgressIndicator indicator = new ProgressIndicator(0);
        indicator.setMinWidth(width * 0.9);
        indicator.setMinHeight(width * 0.9);
        indicator.setOpacity(0);
        return indicator;
    }

    public EventHandler<Event> buildEvent() {
        return e -> {

            if (!canTouched || !out || touched) // If the mole is not out or already touched: nothing append
                return;

            /* If the mole is out and touched */
            if (
                e.getEventType() == MouseEvent.MOUSE_MOVED
                    || e.getEventType() == GazeEvent.GAZE_MOVED
                    || e.getEventType() == MouseEvent.MOUSE_ENTERED
                    || e.getEventType() == GazeEvent.GAZE_ENTERED
            ) {
                canTouched = false;
                if (!touched && out) {
                    gameInstance.oneMoleWhacked();
                    touched = true;
                    goIn();
                }
            } else if (e.getEventType() == MouseEvent.MOUSE_EXITED || e.getEventType() == GazeEvent.GAZE_EXITED) {

                Timeline timeline = new Timeline();

                timeline.play();
                if (timeMinToWhackTheMole != null) {
                    timeMinToWhackTheMole.stop();
                }
                progressIndicator.setOpacity(0);
                progressIndicator.setProgress(0);
            }
        };
    }

    /* This mole must go out */
    void getOut() {

        this.canGoOut = false;

        gameInstance.getNbMolesOut().incrementAndGet();

        TranslateTransition translation = new TranslateTransition(new Duration(1500), this);
        translation.setByX(0);
        translation.setByY(-this.distTranslation);
        translation.play();
        this.mole.opacityProperty().set(1);

        translation.setOnFinished(actionEvent -> {

            canTouched = true;

            gameInstance.getGameContext().getChildren().add(moleMoved);
            gameContext.getGazeDeviceManager().addEventFilter(moleMoved);

            mole.opacityProperty().set(0);

            out = true;

            timeMoleOut = new Timeline(); // New time this mole go out
            Random r = new Random();
            int time = r.nextInt(timeMoleStayOut) + 2000;

            timeMoleOut.getKeyFrames()
                .add(new KeyFrame(new Duration(time),
                    new KeyValue(progressIndicator.progressProperty(), 1)));
            timeMoleOut.rateProperty().bind(gameContext.getAnimationSpeedRatioSource().getSpeedRatioProperty());
            timeMoleOut.play();
            /* If the Mole is stay out without being touching */
            timeMoleOut.setOnFinished(actionEvent1 -> {
                if (!touched && out) {

                    goIn(); // Go back in the hole
                }
            });

        });
    }

    private void goIn() {
        canTouched = false;
        out = false;

        this.mole.opacityProperty().set(1);
        gameInstance.getGameContext().getChildren().remove(moleMoved);
        gameContext.getGazeDeviceManager().removeEventFilter(this.moleMoved);

        double timeGoIn = 1500;
        if (touched) {
            timeGoIn = 500;
        }
        touched = false;
        TranslateTransition translation = new TranslateTransition(new Duration(timeGoIn), this);
        translation.setByX(0);
        translation.setByY(this.distTranslation);
        translation.play();
        translation.setOnFinished(actionEvent -> {
            gameInstance.getNbMolesOut().decrementAndGet();
            mole.opacityProperty().set(0);
            canGoOut = true;
        });
    }

}