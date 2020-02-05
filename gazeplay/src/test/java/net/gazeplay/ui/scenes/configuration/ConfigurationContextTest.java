package net.gazeplay.ui.scenes.configuration;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Window;
import lombok.extern.slf4j.Slf4j;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import net.gazeplay.GazePlay;
import net.gazeplay.TestingUtils;
import net.gazeplay.commons.configuration.Configuration;
import net.gazeplay.commons.themes.BuiltInUiTheme;
import net.gazeplay.commons.ui.I18NText;
import net.gazeplay.commons.ui.Translator;
import net.gazeplay.commons.utils.HomeButton;
import net.gazeplay.commons.utils.games.BackgroundMusicManager;
import net.gazeplay.commons.utils.games.GazePlayDirectories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.framework.junit5.ApplicationExtension;

import java.io.File;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
@ExtendWith(ApplicationExtension.class)
class ConfigurationContextTest {

    @Mock
    private GazePlay mockGazePlay;

    @Mock
    private Translator mockTranslator;

    @Mock
    private Configuration mockConfig;

    @Mock
    private ConfigurationContext mockContext;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        when(mockGazePlay.getTranslator()).thenReturn(mockTranslator);
    }

    @Test
    void shouldReturnToMenuOnHomeButtonPress() {
        ConfigurationContext context = new ConfigurationContext(mockGazePlay);

        HomeButton button = context.createHomeButtonInConfigurationManagementScreen(mockGazePlay);
        button.fireEvent(TestingUtils.clickOnTarget(button));

        verify(mockGazePlay).onReturnToMenu();
    }

    @Test
    void shouldBuildConfigGridPane() {
        ConfigurationContext context = new ConfigurationContext(mockGazePlay);

        GridPane pane = context.buildConfigGridPane(context, mockTranslator);

        ObservableList<Node> children = pane.getChildren();

        assertEquals(64, children.size());
        assertTrue(children.get(3) instanceof MenuButton);
        assertTrue(children.get(7) instanceof ChoiceBox);
        assertTrue(children.get(9) instanceof Spinner);
        assertTrue(children.get(11) instanceof CheckBox);
        assertTrue(children.get(15) instanceof ChoiceBox);
        assertTrue(children.get(17) instanceof Spinner);
        assertTrue(children.get(21) instanceof ChoiceBox);
        assertTrue(children.get(23) instanceof CheckBox);
        assertTrue(children.get(25) instanceof ChoiceBox);
        assertTrue(children.get(41) instanceof CheckBox);
        assertTrue(children.get(43) instanceof ChoiceBox);
        assertTrue(children.get(49) instanceof CheckBox);
        assertTrue(children.get(51) instanceof CheckBox);
        assertTrue(children.get(55) instanceof CheckBox);
        assertTrue(children.get(57) instanceof CheckBox);
        assertTrue(children.get(61) instanceof CheckBox);
        assertTrue(children.get(63) instanceof CheckBox);
    }

    @Test
    void shouldAddCategoryTitleLeftAligned() {
        when(mockTranslator.currentLocale()).thenReturn(Locale.FRANCE);
        ConfigurationContext context = new ConfigurationContext(mockGazePlay);

        when(mockTranslator.translate(anyString())).thenReturn("category");
        GridPane grid = new GridPane();
        AtomicInteger currentFormRow = new AtomicInteger(1);
        I18NText label = new I18NText(mockTranslator, "category");

        context.addCategoryTitle(grid, currentFormRow, label);

        assertTrue(grid.getChildren().get(0) instanceof Separator);
        assertEquals(HPos.CENTER, grid.getChildren().get(0).getProperties().get("gridpane-halignment"));
        assertTrue(grid.getChildren().contains(label));
        assertEquals(HPos.LEFT, grid.getChildren().get(1).getProperties().get("gridpane-halignment"));
    }

    @Test
    void shouldAddCategoryTitleRightAligned() {
        when(mockTranslator.currentLocale()).thenReturn(new Locale("ara"));
        ConfigurationContext context = new ConfigurationContext(mockGazePlay);

        when(mockTranslator.translate(anyString())).thenReturn("category");
        GridPane grid = new GridPane();
        AtomicInteger currentFormRow = new AtomicInteger(1);
        I18NText label = new I18NText(mockTranslator, "category");

        context.addCategoryTitle(grid, currentFormRow, label);

        assertTrue(grid.getChildren().get(0) instanceof Separator);
        assertEquals(HPos.CENTER, grid.getChildren().get(0).getProperties().get("gridpane-halignment"));
        assertTrue(grid.getChildren().contains(label));
        assertEquals(HPos.RIGHT, grid.getChildren().get(1).getProperties().get("gridpane-halignment"));
    }

    @Test
    void shouldAddSubcategoryTitleLeftAligned() {
        when(mockTranslator.currentLocale()).thenReturn(Locale.FRANCE);
        ConfigurationContext context = new ConfigurationContext(mockGazePlay);

        when(mockTranslator.translate(anyString())).thenReturn("category");
        GridPane grid = new GridPane();
        AtomicInteger currentFormRow = new AtomicInteger(1);
        I18NText label = new I18NText(mockTranslator, "category");

        context.addSubCategoryTitle(grid, currentFormRow, label);

        assertTrue(grid.getChildren().contains(label));
        assertEquals(HPos.LEFT, grid.getChildren().get(0).getProperties().get("gridpane-halignment"));
        assertTrue(grid.getChildren().get(1) instanceof Separator);
        assertEquals(HPos.LEFT, grid.getChildren().get(1).getProperties().get("gridpane-halignment"));
    }

    @Test
    void shouldAddSubcategoryTitleRightAligned() {
        when(mockTranslator.currentLocale()).thenReturn(new Locale("ara"));
        ConfigurationContext context = new ConfigurationContext(mockGazePlay);

        when(mockTranslator.translate(anyString())).thenReturn("category");
        GridPane grid = new GridPane();
        AtomicInteger currentFormRow = new AtomicInteger(1);
        I18NText label = new I18NText(mockTranslator, "category");

        context.addSubCategoryTitle(grid, currentFormRow, label);

        assertTrue(grid.getChildren().contains(label));
        assertEquals(HPos.RIGHT, grid.getChildren().get(0).getProperties().get("gridpane-halignment"));
        assertTrue(grid.getChildren().get(1) instanceof Separator);
        assertEquals(HPos.RIGHT, grid.getChildren().get(1).getProperties().get("gridpane-halignment"));
    }

    @Test
    void shouldAddNodeToGridTitleLeftAligned() {
        when(mockTranslator.currentLocale()).thenReturn(Locale.FRANCE);
        ConfigurationContext context = new ConfigurationContext(mockGazePlay);

        when(mockTranslator.translate(anyString())).thenReturn("category");
        GridPane grid = new GridPane();
        AtomicInteger currentFormRow = new AtomicInteger(1);
        I18NText label = new I18NText(mockTranslator, "category");
        CheckBox input = new CheckBox();

        context.addToGrid(grid, currentFormRow, label, input);

        assertTrue(grid.getChildren().contains(label));
        assertEquals(HPos.LEFT, grid.getChildren().get(0).getProperties().get("gridpane-halignment"));
        assertTrue(grid.getChildren().contains(input));
        assertEquals(HPos.LEFT, grid.getChildren().get(1).getProperties().get("gridpane-halignment"));
    }

    @Test
    void shouldAddNodeToGridRightAligned() {
        when(mockTranslator.currentLocale()).thenReturn(new Locale("ara"));
        ConfigurationContext context = new ConfigurationContext(mockGazePlay);

        when(mockTranslator.translate(anyString())).thenReturn("category");
        GridPane grid = new GridPane();
        AtomicInteger currentFormRow = new AtomicInteger(1);
        I18NText label = new I18NText(mockTranslator, "category");
        CheckBox input = new CheckBox();

        context.addToGrid(grid, currentFormRow, label, input);

        assertTrue(grid.getChildren().contains(label));
        assertEquals(HPos.RIGHT, grid.getChildren().get(0).getProperties().get("gridpane-halignment"));
        assertTrue(grid.getChildren().contains(input));
        assertEquals(HPos.RIGHT, grid.getChildren().get(1).getProperties().get("gridpane-halignment"));
    }

    @Test
    void shouldBuildSpinner() {
        SimpleIntegerProperty length = new SimpleIntegerProperty();

        Spinner<Double> result = ConfigurationContext.buildSpinner(0.3, 10, 0.5, 0.1, length);
        assertEquals(0.5, result.getValue());

        result.increment(5);
        assertEquals(1, result.getValue());
        assertEquals(1000, length.get());
    }

    @Test
    void shouldSetSpinnerValueToMaxIfHigher() {
        SimpleIntegerProperty length = new SimpleIntegerProperty();

        Spinner<Double> result = ConfigurationContext.buildSpinner(0.3, 10, 0.5, 0.1, length);
        assertEquals(0.5, result.getValue());

        result.getEditor().setText("11");
        result.commitValue();

        assertEquals(10, result.getValue());
        assertEquals(10000, length.get());
    }

    @Test
    void shouldSetSpinnerValueToMinIfLower() {
        SimpleIntegerProperty length = new SimpleIntegerProperty();

        Spinner<Double> result = ConfigurationContext.buildSpinner(0.3, 10, 0.5, 0.1, length);
        assertEquals(0.5, result.getValue());

        result.getEditor().setText("0.2");
        result.commitValue();

        assertEquals(0.3, result.getValue());
        assertEquals(300, length.get());
    }

    @Test
    void shouldCreateThemeChooserNonDefault() {
        StringProperty cssFileProperty = new SimpleStringProperty("builtin:BLUE");
        ObservableList<String> stylesheets = FXCollections.observableArrayList();
        Scene mockScene = mock(Scene.class);

        when(mockConfig.getCssFile()).thenReturn("builtin:BLUE");
        when(mockConfig.getCssfileProperty()).thenReturn(cssFileProperty);
        when(mockContext.getGazePlay()).thenReturn(mockGazePlay);
        when(mockGazePlay.getPrimaryScene()).thenReturn(mockScene);
        when(mockScene.getStylesheets()).thenReturn(stylesheets);

        ChoiceBox<BuiltInUiTheme> result = ConfigurationContext.buildStyleThemeChooser(mockConfig, mockContext);

        assertEquals(BuiltInUiTheme.values().length, result.getItems().size());
        assertEquals(BuiltInUiTheme.BLUE, result.getValue());
        assertEquals(cssFileProperty.getValue(), "builtin:BLUE");

        result.setValue(BuiltInUiTheme.GREEN);

        assertEquals(BuiltInUiTheme.GREEN, result.getValue());
        assertEquals(cssFileProperty.getValue(), "builtin:GREEN");
    }

    @Test
    void shouldCreateThemeChooserDefault() {
        StringProperty cssFileProperty = new SimpleStringProperty("builtin:WRONG");
        ObservableList<String> stylesheets = FXCollections.observableArrayList();
        Scene mockScene = mock(Scene.class);

        when(mockConfig.getCssFile()).thenReturn("builtin:WRONG");
        when(mockConfig.getCssfileProperty()).thenReturn(cssFileProperty);
        when(mockContext.getGazePlay()).thenReturn(mockGazePlay);
        when(mockGazePlay.getPrimaryScene()).thenReturn(mockScene);
        when(mockScene.getStylesheets()).thenReturn(stylesheets);

        ChoiceBox<BuiltInUiTheme> result = ConfigurationContext.buildStyleThemeChooser(mockConfig, mockContext);

        assertEquals(BuiltInUiTheme.values().length, result.getItems().size());
        assertEquals(BuiltInUiTheme.SILVER_AND_GOLD, result.getValue());

        result.setValue(BuiltInUiTheme.GREEN);

        assertEquals(BuiltInUiTheme.GREEN, result.getValue());
        assertEquals(cssFileProperty.getValue(), "builtin:GREEN");
    }

    @Test
    void shouldCreateFileDirectoryChooser() {
        StringProperty fileDirProperty = new SimpleStringProperty(System.getProperty("user.home") + "/GazePlay");
        Scene mockScene = mock(Scene.class);
        Window mockWindow = mock(Window.class);

        when(mockConfig.getFileDir()).thenReturn(fileDirProperty.getValue());
        when(mockConfig.getFiledirProperty()).thenReturn(fileDirProperty);
        when(mockContext.getGazePlay()).thenReturn(mockGazePlay);
        when(mockGazePlay.getPrimaryScene()).thenReturn(mockScene);
        when(mockScene.getWindow()).thenReturn(mockWindow);

        HBox result = (HBox) ConfigurationContext.buildDirectoryChooser(mockConfig, mockContext, mockTranslator, ConfigurationContext.DirectoryType.FILE);
        Button loadButton = (Button) result.getChildren().get(0);
        Button resetButton = (Button) result.getChildren().get(1);

        assertEquals(fileDirProperty.getValue(), loadButton.textProperty().getValue());

        resetButton.fire();
        assertEquals(GazePlayDirectories.getDefaultFileDirectoryDefaultValue().getAbsolutePath(), fileDirProperty.getValue());
    }

    @Test
    void shouldCreateWhereIsItDirectoryChooser() {
        StringProperty fileDirProperty = new SimpleStringProperty(System.getProperty("user.home") + "/GazePlay/whereisit");
        Scene mockScene = mock(Scene.class);
        Window mockWindow = mock(Window.class);

        when(mockConfig.getWhereIsItDir()).thenReturn(fileDirProperty.getValue());
        when(mockConfig.getWhereIsItDirProperty()).thenReturn(fileDirProperty);
        when(mockContext.getGazePlay()).thenReturn(mockGazePlay);
        when(mockGazePlay.getPrimaryScene()).thenReturn(mockScene);
        when(mockScene.getWindow()).thenReturn(mockWindow);

        HBox result = (HBox) ConfigurationContext.buildDirectoryChooser(mockConfig, mockContext, mockTranslator, ConfigurationContext.DirectoryType.WHERE_IS_IT);
        Button loadButton = (Button) result.getChildren().get(0);
        Button resetButton = (Button) result.getChildren().get(1);

        assertEquals(fileDirProperty.getValue(), loadButton.textProperty().getValue());

        resetButton.fire();
        assertEquals(Configuration.DEFAULT_VALUE_WHEREISIT_DIR, fileDirProperty.getValue());
    }

    @Test
    void shouldBuildLanguageChooser() throws InterruptedException {
        ConfigurationContext context = new ConfigurationContext(mockGazePlay);
        StringProperty languageProperty = new SimpleStringProperty("eng");
        StringProperty countryProperty = new SimpleStringProperty("GB");

        when(mockConfig.getLanguage()).thenReturn(languageProperty.getValue());
        when(mockConfig.getCountry()).thenReturn(countryProperty.getValue());
        when(mockConfig.getLanguageProperty()).thenReturn(languageProperty);
        when(mockConfig.getCountryProperty()).thenReturn(countryProperty);
        when(mockContext.getGazePlay()).thenReturn(mockGazePlay);

        MenuButton result = context.buildLanguageChooser(mockConfig, context);

        assertEquals(23, result.getItems().size());

        Platform.runLater(() -> {
            result.getItems().get(1).fire();
        });
        TestingUtils.waitForRunLater();

        ImageView image = (ImageView) result.getGraphic();
        assertTrue(image.getImage().getUrl().contains("Arab"));
        assertEquals("ara", languageProperty.getValue());
    }

    @Test
    void shouldSetupANewMusicFolder() {
        String songName = "songidea(copycat)_0.mp3";
        File testFolder = new File("music_test");
        File expectedFile = new File(testFolder, songName);

        ConfigurationContext.setupNewMusicFolder(testFolder, songName);

        assertTrue(testFolder.isDirectory());
        assertTrue(expectedFile.exists());

        assertTrue(expectedFile.delete());
        assertTrue(testFolder.delete());
    }

    @Test
    void shouldSetupANewMusicFolderIfTheFolderExists() {
        String songName = "songidea(copycat)_0.mp3";
        File testFolder = new File("music_test");
        assertTrue(testFolder.mkdir());
        File expectedFile = new File(testFolder, songName);

        ConfigurationContext.setupNewMusicFolder(testFolder, songName);

        assertTrue(testFolder.isDirectory());
        assertTrue(expectedFile.exists());

        assertTrue(expectedFile.delete());
        assertTrue(testFolder.delete());
    }

    @Test
    void shouldSetupANewMusicFolderIfTheSongDoesntExist() {
        String songName = "fakesong.mp3";
        File testFolder = new File("music_test");
        assertTrue(testFolder.mkdir());
        File expectedFile = new File(testFolder, songName);

        ConfigurationContext.setupNewMusicFolder(testFolder, songName);

        assertTrue(testFolder.isDirectory());
        assertFalse(expectedFile.exists());

        assertTrue(testFolder.delete());
    }

    @Test
    void shouldChangeTheMusicFolderAndPlayIfWasPlaying(@Mocked BackgroundMusicManager mockMusicManager,
                                                       @Mocked Configuration mockConfiguration) {
        StringProperty mockMusicFolderProperty = new SimpleStringProperty();

        new Expectations() {{
            mockMusicManager.isPlaying();
            result = true;

            BackgroundMusicManager.getInstance();
            result = mockMusicManager;

            mockConfiguration.getMusicFolderProperty();
            result = mockMusicFolderProperty;
        }};

        ConfigurationContext.changeMusicFolder("mockFolder", mockConfiguration);

        new Verifications() {{
            mockMusicManager.play();
            times = 1;
        }};
    }

    @Test
    void shouldChangeTheMusicFolderAndNotPlayIfWasNotPlaying(@Mocked BackgroundMusicManager mockMusicManager,
                                                             @Mocked Configuration mockConfiguration) {
        StringProperty mockMusicFolderProperty = new SimpleStringProperty();

        new Expectations() {{
            mockMusicManager.isPlaying();
            result = false;

            BackgroundMusicManager.getInstance();
            result = mockMusicManager;

            mockConfiguration.getMusicFolderProperty();
            result = mockMusicFolderProperty;
        }};

        ConfigurationContext.changeMusicFolder("mockFolder", mockConfiguration);

        new Verifications() {{
            mockMusicManager.play();
            times = 0;
        }};
    }

    @Test
    void shouldChangeTheMusicFolderWithBlankFolder(@Mocked BackgroundMusicManager mockMusicManager,
                                                   @Mocked Configuration mockConfiguration) {
        StringProperty mockMusicFolderProperty = new SimpleStringProperty();
        String expectedFolder = System.getProperty("user.home") + File.separator + "GazePlay" + File.separator + "music";

        new Expectations() {{
            mockMusicManager.isPlaying();
            result = false;

            BackgroundMusicManager.getInstance();
            result = mockMusicManager;

            mockConfiguration.getMusicFolderProperty();
            result = mockMusicFolderProperty;
        }};

        ConfigurationContext.changeMusicFolder("", mockConfiguration);

        new Verifications() {{
            mockMusicManager.getAudioFromFolder(expectedFolder);
        }};
    }
}
