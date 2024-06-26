package net.gazeplay.games.colorblend;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import net.gazeplay.GameLifeCycle;
import net.gazeplay.IGameContext;
import net.gazeplay.commons.gaze.devicemanager.GazeEvent;
import net.gazeplay.commons.utils.stats.Stats;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;

/**
 * Choose between multiple colors and blend them together
 *
 * This class implements a color blending game where users can select and mix colors.
 *
 * @version 1.0
 *
 * author Yanis HARKATI
 */
@Slf4j
public class ColorBlend implements GameLifeCycle {
    private final IGameContext gameContext;
    private final Stats stats;

    private Paint color1;
    private Paint color2;
    private Circle circle;

    private static final int CIRCLE_RADIUS = 400;
    private ProgressIndicator progressIndicator;
    private Timeline timelineProgressBar;

    public ColorBlend(final IGameContext gameContext, Stats stats) {
        this.gameContext = gameContext;
        this.stats = stats;
    }

    @Override
    public void launch() {
        createBackground();
        createPalette();

        stats.notifyNewRoundReady();
        gameContext.getGazeDeviceManager().addStats(stats);
    }

    @Override
    public void dispose() {
        // Dispose resources if needed (empty implementation in this example)
    }

    /**
     * Apply background image to the game scene.
     */
    private void createBackground() {
        Background background = new Background(new BackgroundImage(
            new Image("data/colorblend/images/park.png"),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true)
        ));
        gameContext.getRoot().setBackground(background);
    }

    /**
     * Create the palette of colors and layout the UI elements.
     */
    private void createPalette() {
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

        // Define the size of the palette as a percentage of the screen size
        double paletteWidthPercentage = 0.12; // 12% of the screen width
        double paletteHeightPercentage = 0.65; // 65% of the screen height

        double paletteWidth = screenWidth * paletteWidthPercentage;
        double paletteHeight = screenHeight * paletteHeightPercentage;

        // Create color rectangles
        Rectangle[] colors = {
            createColorRectangle(Color.RED),
            createColorRectangle(Color.ORANGE),
            createColorRectangle(Color.GREEN),
            createColorRectangle(Color.LIGHTBLUE),
            createColorRectangle(Color.DARKBLUE),
            createColorRectangle(Color.MAGENTA),
            createColorRectangle(Color.PINK),
            createColorRectangle(Color.WHITE),
            createColorRectangle(Color.BLACK),
            createColorRectangle(Color.YELLOW)
        };

        // Add click event handlers to color rectangles
        for (Rectangle color : colors) {
            color.setOnMouseClicked(mouseEvent -> handleColorClick(color));
        }

        // Create a grid to arrange the color rectangles in pairs
        GridPane colorGrid = new GridPane();
        colorGrid.setPadding(new Insets(20));
        colorGrid.setHgap(25);
        colorGrid.setVgap(25);

        // Add color rectangles to the grid in pairs
        for (int i = 0; i < colors.length; i += 2) {
            colorGrid.addRow(i / 2, colors[i], colors[i + 1]);
        }

        Rectangle paletteRectangle = createPaletteRectangle(paletteWidth, paletteHeight);

        // Create the reset button with an image
        Image resetImage = new Image("data/colorblend/images/glass.png");
        ImageView imageView = new ImageView(resetImage);

        double glassWidthPercentage = 0.05; // 5% of the screen width
        double glassHeightPercentage = 0.10; // 10% of the screen height

        imageView.setFitWidth(screenWidth * glassWidthPercentage); // Set image size
        imageView.setFitHeight(screenHeight * glassHeightPercentage);

        // Reset button with glass image on it
        Button resetButton = new Button();
        HBox buttonBox = new HBox(resetButton);

        resetButton.setGraphic(imageView);
        resetButton.setOnAction(event -> handleReset());
        resetButton.addEventFilter(MouseEvent.ANY, resetBuildEvent(buttonBox));
        resetButton.addEventFilter(GazeEvent.ANY, resetBuildEvent(buttonBox));

        buttonBox.setAlignment(Pos.CENTER);

        // Combine palette, color grid, and reset button in a single layout
        StackPane root = new StackPane(paletteRectangle, colorGrid);
        HBox container = new HBox(root, buttonBox, this.progressIndicator);
        container.setAlignment(Pos.CENTER); // Center align the container

        // Add the container to the game scene
        gameContext.getChildren().add(container);
    }

    /**
     * Create a rectangle for a color.
     *
     * @param color the color to fill the rectangle with
     * @return a Rectangle object used for choosing color
     */
    private Rectangle createColorRectangle(Color color) {
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

        // Define the size of the rectangle as a percentage of the screen size
        double rectangleWidthPercentage = 0.05; // 5% of the screen width
        double rectangleHeightPercentage = 0.10; // 10% of the screen height

        Rectangle rectangle = new Rectangle(screenWidth * rectangleWidthPercentage, screenHeight * rectangleHeightPercentage);
        rectangle.setFill(color);
        rectangle.setStroke(color.darker());
        rectangle.setStrokeWidth(1);

        this.progressIndicator = createProgressIndicator(rectangle);

        // Add event filters for gaze and mouse interactions
        rectangle.addEventFilter(MouseEvent.ANY, rectangleBuildEvent(rectangle));
        rectangle.addEventFilter(GazeEvent.ANY, rectangleBuildEvent(rectangle));

        return rectangle;
    }

    /**
     * Build event handler for color rectangle interactions.
     */
    private EventHandler<Event> rectangleBuildEvent(final Rectangle rectangle) {
        return e -> {
            if (e.getEventType() == MouseEvent.MOUSE_ENTERED || e.getEventType() == GazeEvent.GAZE_ENTERED) {
                // Handle gaze or mouse entered events
                timelineProgressBar = new Timeline();
                timelineProgressBar.getKeyFrames().add(new KeyFrame(new Duration(gameContext.getConfiguration().getFixationLength()),
                    new KeyValue(progressIndicator.progressProperty(), 1)));

                timelineProgressBar.setOnFinished(actionEvent -> handleColorClick(rectangle));

                progressIndicator.setStyle(" -fx-progress-color: " + gameContext.getConfiguration().getProgressBarColor());
                progressIndicator.setOpacity(1);
                progressIndicator.setProgress(0);

                progressIndicator.layoutXProperty().bind(rectangle.layoutXProperty().add(rectangle.getWidth() / 2).subtract(progressIndicator.getMinWidth() / 2));
                progressIndicator.layoutYProperty().bind(rectangle.layoutYProperty().add(rectangle.getHeight() / 2).subtract(progressIndicator.getMinHeight() / 2));

                timelineProgressBar.play();
            } else if (e.getEventType() == MouseEvent.MOUSE_EXITED || e.getEventType() == GazeEvent.GAZE_EXITED) {
                // Handle gaze or mouse exited events
                timelineProgressBar.stop();
                progressIndicator.setOpacity(0);
                progressIndicator.setProgress(0);
                // Avoid event calling on it
                progressIndicator.setMouseTransparent(true);
            }
        };
    }

    /**
     * Build event handler for reset button interactions.
     */
    private EventHandler<Event> resetBuildEvent(HBox buttonBox) {
        return e -> {
            if (e.getEventType() == MouseEvent.MOUSE_ENTERED || e.getEventType() == GazeEvent.GAZE_ENTERED) {
                // Handle gaze or mouse entered events for reset button
                timelineProgressBar = new Timeline();
                timelineProgressBar.getKeyFrames().add(new KeyFrame(new Duration(gameContext.getConfiguration().getFixationLength()),
                    new KeyValue(progressIndicator.progressProperty(), 1)));

                timelineProgressBar.setOnFinished(actionEvent -> handleReset());

                progressIndicator.setStyle(" -fx-progress-color: " + gameContext.getConfiguration().getProgressBarColor());
                progressIndicator.setOpacity(1);
                progressIndicator.setProgress(0);

                // Bind layoutX and layoutY properties of the ProgressIndicator to the button box
                progressIndicator.layoutXProperty().bind(buttonBox.layoutXProperty().add(buttonBox.getWidth() / 2).subtract(progressIndicator.getMinWidth() / 2));
                progressIndicator.layoutYProperty().bind(buttonBox.layoutYProperty().add(buttonBox.getHeight() / 2).subtract(progressIndicator.getMinHeight() / 2));

                timelineProgressBar.play();
            } else if (e.getEventType() == MouseEvent.MOUSE_EXITED || e.getEventType() == GazeEvent.GAZE_EXITED) {
                // Handle gaze or mouse exited events for reset button
                timelineProgressBar.stop();
                progressIndicator.setOpacity(0);
                progressIndicator.setProgress(0);
                // Prevent mouse events from being captured by the ProgressIndicator
                progressIndicator.setMouseTransparent(true);
            }
        };
    }

    /**
     * Create a progress indicator for a given rectangle.
     *
     * @param rectangle the rectangle to attach the progress indicator to
     * @return a new ProgressIndicator object
     */
    private ProgressIndicator createProgressIndicator(final Rectangle rectangle) {
        final ProgressIndicator indicator = new ProgressIndicator(0);
        indicator.setMinWidth(rectangle.getWidth() / 2);
        indicator.setMinHeight(rectangle.getHeight() / 2);
        indicator.setOpacity(0);
        return indicator;
    }

    /**
     * Create a rectangle to represent the color palette.
     *
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     * @return a new Rectangle object
     */
    private Rectangle createPaletteRectangle(double width, double height) {
        Rectangle rectangle = new Rectangle(width, height);
        rectangle.setFill(Color.BEIGE);
        rectangle.setStroke(Color.BEIGE.darker());
        rectangle.setStrokeWidth(2);
        return rectangle;
    }

    /**
     * Handle click event on a color rectangle.
     *
     * @param color the color rectangle that was clicked
     */
    private void handleColorClick(Rectangle color) {
        if (this.color1 == null) {
            this.color1 = color.getFill();
            this.circle = createCircle();
            this.circle.setFill(this.color1);
        } else {
            this.color2 = color.getFill();
            color.setStroke(Color.WHITE);
            Color newColor = blendColors();
            this.circle.setFill(newColor);
            this.color1 = newColor;
        }
    }

    /**
     * Handle reset button click event.
     */
    private void handleReset() {
        // Reset colors and circle
        color1 = null;
        color2 = null;
        if (circle != null) circle.setFill(null);
    }

    /**
     * Get a blended color from the two selected colors.
     *
     * @return a blended Color object
     */
    private Color blendColors() {
        // Get the initial RGB components of the colors
        Color color1 = (Color) this.color1;
        Color color2 = (Color) this.color2;

        double r1 = color1.getRed();
        double g1 = color1.getGreen();
        double b1 = color1.getBlue();

        double r2 = color2.getRed();
        double g2 = color2.getGreen();
        double b2 = color2.getBlue();

        // Merge colors
        double r = (r1 + r2) / 2;
        double g = (g1 + g2) / 2;
        double b = (b1 + b2) / 2;

        return new Color(r, g, b, 1);
    }

    /**
     * Create a circle to display the blended color.
     *
     * @return a new Circle object
     */
    private Circle createCircle() {
        Circle circle = new Circle();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

        // Define the size of the circle as a percentage of the screen height
        double circleHeightPercentage = 0.25; // 25% of the screen height
        circle.setRadius(screenHeight * circleHeightPercentage);

        this.gameContext.getRoot().getChildren().add(circle);

        circle.centerXProperty().bind(this.gameContext.getRoot().widthProperty().divide(2));
        circle.centerYProperty().bind(this.gameContext.getRoot().heightProperty().divide(2));

        return circle;
    }
}
