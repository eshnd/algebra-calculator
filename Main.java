import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GUI();
    }

    // eshaan
    private static void algebraCalculator(String inputText, JLabel resultLabel) {
        Scanner scanner = new Scanner(inputText);
        String equation;
        equation = scanner.next(); // more flexible input

        if (equation.equalsIgnoreCase("exit")) { // EXIT would work too if ur rlly mad at my program :(
            scanner.close();
            return;
        }

        if (equation.contains("=")) {
            String[] sides = equation.split("=");
            String leftSide = sides[0].replaceAll(" ", ""); // NO SPACES
            String rightOG = sides[1].replaceAll(" ", "");

            double right = 0;
            boolean isRightX = false;

            if (rightOG.equals("x")) {
                isRightX = true;
            } else {
                right = Double.parseDouble(rightOG); // convert string to double which is just BIGger float
            }

            if (leftSide.matches(".*[*/].*")) {
                resultLabel.setText("error NO DIVISION OR * MULTPLICATION"); // if multiplication or division is present in algebraic expressions (too hard for the puny little baby 16 yr old i am)
            } else {
                double coefficientX = 0;
                double constant = 0;

                String[] parts = leftSide.split("(?=[+-])"); // splits into different parts like "3x+3" -> "3x","+3"

                for (String part : parts) {
                    if (part.contains("x")) { // if dealing with coeff eg 3x
                        part = part.replace("x", "");
                        if (part.equals("+") || part.equals("")) { // this bc +x is positive x but just x is also positive x
                            coefficientX += 1;
                        } else if (part.equals("-")) {
                            coefficientX -= 1;
                        } else {
                            coefficientX += Double.parseDouble(part); // coeffx = coeffx+part (handles coeffeciants like 7x or -13x)
                        }
                    } else { // if dealing with not coeff (constant) eg 3
                        constant += Double.parseDouble(part);
                    }
                }

                if (coefficientX == 0) {
                    if (isRightX) {
                        resultLabel.setText("x = " + String.valueOf(constant));
                    } else {
                        resultLabel.setText("error!!!!"); // grrr WHY NO X?????????
                    }
                } else {
                    double x;
                    if (isRightX) { // so if 3+3 or similar was input
                        x = constant / coefficientX; // 6/1
                    } else {
                        right = Double.parseDouble(rightOG);
                        x = (right - constant) / coefficientX; // the eq for basic algebra
                    }
                    resultLabel.setText("x = " + String.valueOf(x));
                }
            }
        } else {
            scanner.close();
            return; 
        }

        scanner.close();
    }

    // namith
    private static void arithmeticCalculator(String equation, JLabel resultLabel) {
    try {
        String[] addSubParts = equation.split("(?=[+-])"); 

        StringBuilder intermediateEquation = new StringBuilder();
        for (String part : addSubParts) {
            double result = 1;
            boolean isMultiplying = true;
            boolean hasOperators = false;

            String[] mulDivParts = part.split("(?=[*/])");

            if (mulDivParts.length > 0) {
                result = Double.parseDouble(mulDivParts[0].trim());

                for (int i = 1; i < mulDivParts.length; i++) {
                    String mulDivPart = mulDivParts[i].trim();

                    if (mulDivPart.startsWith("*")) {
                        mulDivPart = mulDivPart.substring(1).trim(); 
                        result *= Double.parseDouble(mulDivPart);
                        hasOperators = true;
                    } else if (mulDivPart.startsWith("/")) {
                        mulDivPart = mulDivPart.substring(1).trim(); 
                        result /= Double.parseDouble(mulDivPart);
                        hasOperators = true;
                    }
                }
            }

            if (hasOperators) {
                intermediateEquation.append(result);
            } else {
                intermediateEquation.append(part); 
            }
        }

        String finalEquation = intermediateEquation.toString();
        String[] addSubPartsFinal = finalEquation.split("(?=[+-])");

        double finalResult = 0;
        for (String part : addSubPartsFinal) {
            if (part.startsWith("-")) {
                finalResult -= Double.parseDouble(part.substring(1).trim());
            } else {
                finalResult += Double.parseDouble(part.trim());
            }
        }

        resultLabel.setText("Result = " + finalResult);
    } catch (Exception e) {
        resultLabel.setText("error!!!");
    }
}


    // 3rd Function: sai
    private static void GUI() {
        JFrame frame = new JFrame("");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        JTextField textField = new JTextField(20);
        textField.setMaximumSize(new Dimension(200, 25));
        textField.setHorizontalAlignment(JTextField.CENTER);
        JButton button = new JButton("solve");
        JLabel resultLabel = new JLabel("");
        JLabel Instr = new JLabel("Algebra Calculator");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = textField.getText();

                if (inputText.contains("=")) {
                    algebraCalculator(inputText, resultLabel);
                } else {
                    arithmeticCalculator(inputText, resultLabel);
                }
            }
        });

        JPanel verticalPanel = new JPanel();
        verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));
        verticalPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        verticalPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        verticalPanel.add(Instr);
        verticalPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        verticalPanel.add(textField);
        verticalPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        verticalPanel.add(button);
        verticalPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        verticalPanel.add(resultLabel);
        frame.setBackground(Color.decode("#7ba0b0"));
        verticalPanel.setBackground(Color.decode("#7ba0b0"));

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        Instr.setAlignmentX(Component.CENTER_ALIGNMENT);
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);

        frame.add(verticalPanel);
        frame.setVisible(true);
    }
}