package me.moeux.passwordgenerator;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.Random;

public class Controller {

    private final Random random = new Random();
    @FXML
    private TextField textField;
    @FXML
    private Slider lengthSlider;
    @FXML
    private Slider strengthSlider;

    @FXML
    public void initialize() {
        strengthSlider.setLabelFormatter(new StringConverter<>() {
            @Override
            public String toString(Double object) {
                return switch (object.intValue()) {
                    case 1 -> "Weak: Only lower and uppercase letters";
                    case 2 -> "Medium: Weak + Numbers";
                    case 3 -> "Strong: Medium + Special Characters";
                    default -> "";
                };
            }

            @Override
            public Double fromString(String string) {
                return switch (string) {
                    case "Weak: Only lower and uppercase letters" -> 1d;
                    case "Medium: Weak + Numbers" -> 2d;
                    default -> 3d;
                };
            }
        });
    }

    @FXML
    public void generatePassword() {
        int strength = (int) strengthSlider.getValue();
        int length = (int) lengthSlider.getValue();
        char[] pw = new char[length];

        for (int i = 0; i < length; i++) {
            switch (strength) {
                case 1 -> {
                    do
                        pw[i] = (char) (random.nextInt('z' - 'A') + 'A');
                    while (pw[i] < 'a' && pw[i] > 'Z');
                }
                case 2 -> {
                    do
                        pw[i] = (char) (random.nextInt('z' - '0') + '0');
                    while ((pw[i] < 'a' && pw[i] > 'Z') || (pw[i] < 'A' && pw[i] > '9'));
                }
                default -> pw[i] = (char) (random.nextInt(127 - '!') + '!');
            }
        }

        textField.setText(String.valueOf(pw));
    }

    @FXML
    public void copyToClipboard() {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(textField.getText()), null);
    }

    @FXML
    public void exit() {
        Platform.exit();
    }
}
