import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.TextFieldSkin;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.math.*;
import javax.lang.model.util.ElementScanner14;
import java.text.DecimalFormat;

public class CalC extends Application {

    private TextField textField = new TextField();
    private TextField textField2 = new TextField();
    private double num1 = 0;
    private double num2 = 0;
    private String op = "";
    private String so = "";
    private boolean start = true;
    private double result = 0;
    private double temp = 0;
    private boolean pointClicked = false;
    private boolean resultDisplayed = false;
    private Stage primaryStage;
    private LineChart<Number, Number> lineChart;
    private XYChart.Series<Number, Number> sinSeries;
    private XYChart.Series<Number, Number> cosSeries;
    private XYChart.Series<Number, Number> tanSeries;
    // private XYChart.Series<Number, Number> series;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        ChoiceBox<String> choiceBox = new ChoiceBox<>();

        // Get items return the dropdown menu
        choiceBox.getItems().add("Calculator");
        choiceBox.getItems().add("Graph");

        // set default value
        choiceBox.setValue("Calculator");

        choiceBox.setOnAction(event -> handleChoiceBoxSelection(choiceBox.getValue()));

        textField2.setFont(Font.font(10));
        textField2.setPrefHeight(40);
        textField2.setAlignment(Pos.CENTER_RIGHT);
        textField2.setEditable(false);
        textField2.previousWord();
        textField2.paddingProperty();

        textField.setFont(Font.font(20));
        textField.setPrefWidth(500);
        textField.setPrefHeight(50);
        textField.setAlignment(Pos.CENTER_RIGHT);
        textField.setEditable(false);
        textField.previousWord();

        // HBox textFieldsBox = new HBox(10);
        // textFieldsBox.setAlignment(Pos.CENTER);
        // textFieldsBox.getChildren().addAll(textField, textField2);

        // GridPane gridPane = new GridPane();
        // gridPane.setAlignment(Pos.CENTER);
        // gridPane.setHgap(10);

        // Add textField to the first column, first row
        // gridPane.add(textField, 0, 0);

        // Create a new TextField instance
        // TextField textField2 = new TextField();
        // Add textField2 to the second column, first row
        // gridPane.add(textField2, 0, 1);

        StackPane stackpane = new StackPane();
        stackpane.setPadding(new Insets(5, 0, 5, 0));
        stackpane.getChildren().add(textField);

        StackPane stackPane2 = new StackPane();
        stackPane2.setPadding(new Insets(10));
        stackPane2.getChildren().add(textField2);

        VBox vbox = new VBox(8);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(choiceBox, stackpane);

        // DecimalFormat decimalFormat = new DecimalFormat("#.##");

        TilePane tile = new TilePane();
        tile.setHgap(10);
        tile.setVgap(9);
        tile.setAlignment(Pos.TOP_CENTER);
        tile.getChildren().addAll(

                createButtonForSin("sin"),
                createButtonForCos("cos"),
                createButtonForTan("tan"),
                createButtonForInverse("1/x"),
                createButtonForDeleteLastCh("<--"),

                createButtonForSqrtButton("√"),
                createButtonForSquare("X^2"),
                createButtonForOperators("%"),
                createButtonForlnx("lnx"),
                createButtonForEx("e^x"),

                createButtonForNumber("7"),
                createButtonForNumber("8"),
                createButtonForNumber("9"),
                createButtonForlogx("logx"),
                createButtonFor10x("10^x"),

                createButtonForNumber("4"),
                createButtonForNumber("5"),
                createButtonForNumber("6"),
                createButtonForOperators("X"),
                createButtonForOperators("/"),

                createButtonForNumber("1"),
                createButtonForNumber("2"),
                createButtonForNumber("3"),
                createButtonForOperators("-"),
                createButtonForxPowy("x^y"),

                createButtonForNumber("0"),
                createButtonForDot("."),
                createButtonForOperators("="),
                createButtonForOperators("+"),
                createButtonForClear("C"));

        BorderPane root = new BorderPane();
        root.setTop(vbox);
        root.setCenter(tile);

        Scene scene = new Scene(root, 447, 474);
        primaryStage.setScene(scene);
        primaryStage.setTitle("My Calculator");
        primaryStage.setResizable(false);
        primaryStage.show();
        root.setStyle("-fx-background-color: #136fa4;");
    }

    private void handleChoiceBoxSelection(String selectedOption) {
        if (selectedOption.equals("Calculator")) {
            // Stage stage = new Stage();
            try {
                start(primaryStage);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else if (selectedOption.equals("Graph")) {
            navigateToGraph();
        }
    }

    private void navigateToGraph() {
        ChoiceBox<String> choiceBox = new ChoiceBox<>();

        // Get items return the dropdown menu
        choiceBox.getItems().add("Calculator");
        choiceBox.getItems().add("Graph");

        // set default value
        choiceBox.setValue("Graph");
        choiceBox.setOnAction(event -> handleChoiceBoxSelection(choiceBox.getValue()));

        // Create the x-axis
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("X");

        // Create the y-axis
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Y");

        // Create the line chart and set its title
        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Trigonometric Graphs");

        // Create series for sin, cos, and tan
        sinSeries = new XYChart.Series<>();
        sinSeries.setName("sin");
        cosSeries = new XYChart.Series<>();
        cosSeries.setName("cos");
        tanSeries = new XYChart.Series<>();
        tanSeries.setName("tan");

        // Create buttons for sin, cos, and tan
        Button sinButton = new Button("sin");
        Button cosButton = new Button("cos");
        Button tanButton = new Button("tan");

        // Set button event handlers
        // Series<Number, Number> series = new Series<>();
        // Initialize and populate the series with data

        // TrigButtonHandler trigButtonHandler = new TrigButtonHandler(series);

        sinButton.setOnAction(new TrigButtonHandler(sinSeries));
        cosButton.setOnAction(new TrigButtonHandler(cosSeries));
        tanButton.setOnAction(new TrigButtonHandler(tanSeries));

        // Create the layout
        BorderPane root = new BorderPane();
        root.setTop(sinButton);
        root.setCenter(lineChart);
        root.setBottom(tanButton);
        root.setRight(cosButton);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(choiceBox, root);
        layout.setStyle("-fx-background-color: #136fa4;");

        Scene scene1 = new Scene(layout, 800, 520);
        primaryStage.setScene(scene1);
    }

    class TrigButtonHandler implements EventHandler<ActionEvent> {
        private Series<Number, Number> series;

        public TrigButtonHandler(XYChart.Series<Number, Number> series) {
            this.series = series;
        }

        @Override
        public void handle(ActionEvent event) {
            // Clear all series
            sinSeries.getData().clear();
            cosSeries.getData().clear();
            tanSeries.getData().clear();

            // Add data points to the selected series
            for (double x = -360; x <= 360; x += 20) {
                double radians = Math.toRadians(x);
                series.getData().add(new XYChart.Data<>(x, calculateValue(x)));
            }

            // Highlight the selected series on the graph
            lineChart.getData().setAll(series);
        }

        private double calculateValue(double x) {
            switch (series.getName()) {
                case "sin":
                    return Math.sin(Math.toRadians(x));
                case "cos":
                    return Math.cos(Math.toRadians(x));
                case "tan":
                    return Math.tan(Math.toRadians(x));
                default:
                    return 0.0;
            }
        }

        // TrigButtonHandler trigButtonHandler = new TrigButtonHandler();

    }

    private Button createButtonForNumber(String ch) {
        Button button = new Button(ch);
        button.setFont(Font.font(18));
        button.setFont(new Font("Verdana Bold", 18));
        button.setPrefSize(77, 50);
        button.setOnAction(this::processNumber);
        return button;
    }

    private Button createButtonForOperators(String ch) {
        Button button = new Button(ch);
        button.setFont(new Font("Verdana Bold", 18));
        button.setPrefSize(77, 50);
        button.setOnAction(this::processOperator);
        return button;
    }

    private Button createButtonForDeleteLastCh(String ch) {
        Button button = new Button(ch);
        button.setFont(new Font("Verdana Bold", 18));
        button.setPrefSize(77, 50);
        button.setOnAction(this::processDeleteLastCh);
        return button;
    }

    private Button createButtonForClear(String ch) {
        Button button = new Button(ch);
        button.setFont(new Font("Verdana Bold", 18));
        button.setPrefSize(77, 50);
        button.setOnAction(e -> {
            textField.setText("");
            op = "";
            start = true;
            result = 0.0;
            num1 = 0;
            num2 = 0;
            pointClicked = false;
        });
        return button;
    }

    private Button createButtonForSqrtButton(String ch) {
        Button button = new Button(ch);
        button.setFont(new Font("Verdana Bold", 18));
        button.setPrefSize(77, 50);
        button.setOnAction(this::processOperatorSqrt);
        return button;
    }

    private Button createButtonForSquare(String ch) {
        Button button = new Button(ch);
        button.setFont(new Font("Verdana Bold", 18));
        button.setPrefSize(77, 50);
        button.setOnAction(this::processOperatorSquare);
        return button;
    }

    private Button createButtonForSin(String ch) {
        Button button = new Button(ch);
        button.setFont(new Font("Verdana Bold", 18));
        button.setPrefSize(77, 50);
        button.setOnAction(this::processSin);
        return button;
    }

    private Button createButtonForCos(String ch) {
        Button button = new Button(ch);
        button.setFont(new Font("Verdana Bold", 18));
        button.setPrefSize(77, 50);
        button.setOnAction(this::processCos);
        return button;
    }

    private Button createButtonForTan(String ch) {
        Button button = new Button(ch);
        button.setFont(new Font("Verdana Bold", 18));
        button.setPrefSize(77, 50);
        button.setOnAction(this::processTan);
        return button;
    }

    private Button createButtonForInverse(String ch) {
        Button button = new Button(ch);
        button.setFont(new Font("Verdana Bold", 18));
        button.setPrefSize(77, 50);
        button.setOnAction(this::processInverse);
        return button;
    }

    private Button createButtonForlnx(String ch) {
        Button button = new Button(ch);
        button.setFont(new Font("Verdana Bold", 18));
        button.setPrefSize(77, 50);
        button.setOnAction(this::processlnx);
        return button;
    }

    private Button createButtonForEx(String ch) {
        Button button = new Button(ch);
        button.setFont(new Font("Verdana Bold", 18));
        button.setPrefSize(77, 50);
        button.setOnAction(this::processEx);
        return button;
    }

    private Button createButtonForlogx(String ch) {
        Button button = new Button(ch);
        button.setFont(new Font("Verdana Bold", 18));
        button.setPrefSize(77, 50);
        button.setOnAction(this::processlogx);
        return button;
    }

    private Button createButtonFor10x(String ch) {
        Button button = new Button(ch);
        button.setFont(new Font("Verdana Bold", 18));
        button.setPrefSize(77, 50);
        button.setOnAction(this::process10x);
        return button;
    }

    private Button createButtonForxPowy(String ch) {
        Button button = new Button(ch);
        button.setFont(new Font("Verdana Bold", 18));
        button.setPrefSize(77, 50);
        button.setOnAction(this::processxPowy);
        return button;
    }

    private Button createButtonForDot(String ch) {
        Button button = new Button(ch);
        button.setFont(new Font("Verdana Bold", 18));
        button.setPrefSize(77, 50);
        button.setOnAction(this::processDot);
        return button;
    }

    private void processNumber(ActionEvent e) {
        if (textField.getText().contains(".")) {
            textField.setText(textField.getText());
        } else if (start) {
            textField.setText("");
            start = false;
        }
        String value = ((Button) e.getSource()).getText();
        if (resultDisplayed) {
            textField.setText("");
            op = "";
            result = 0.0;
            resultDisplayed = false;
            textField.appendText(value);
        }
        // textField.appendText(value);
        else if (textField.getText().contains(".")) {
            textField.setText(textField.getText() + value);
            // int pos = textField.getText().length() - 1;
            // if ((textField.getText()).charAt(pos)).equals("."))
            // textField.setText(textField.getText());
            // else
            // textField.setText(textField.getText() + value);
        } else {
            // textField.appendText(value);
            textField.setText(textField.getText() + value);
        }

        // result = Double.parseDouble(textField.getText());

    }

    private void processDot(ActionEvent e) {
        // if (start) {
        // textField.setText("0");
        // start = false;
        // }
        // pointClicked = false;
        String value = ((Button) e.getSource()).getText();

        if (value.equals(".") && textField.getText().isEmpty()) {
            textField.appendText("0.");
            pointClicked = true;
        } else if (value.equals(".")) {
            if (!textField.getText().contains(".")) {
                textField.appendText(".");
                pointClicked = true;
            }

        }

        System.out.println(textField.getText().getClass().getSimpleName());

        // result = Double.parseDouble(textField.getText());
        System.out.println(result);
        // if (!textField.getText().contains(".")) {
        // textField.setText(textField.getText() + ".");
        // }
    }

    private void processOperator(ActionEvent e) {
        if (start) {
            textField.setText("");
            start = false;
        }
        String value = ((Button) e.getSource()).getText();
        System.out.println(value);
        if (!value.equals("="))
            so = value;
        // System.out.println(so);

        if (!value.equals("=")) {
            if (resultDisplayed) {
                resultDisplayed = false;
                // textField.appendText(value);
            }

            if (!op.isEmpty())
                return;
            num1 = Double.parseDouble(textField.getText());
            // result=num1;
            so = ((Button) e.getSource()).getText();
            // System.out.println(so);
            op = so;
            textField.setText("");
            pointClicked = false;
            resultDisplayed = false;

        } else {
            // value = ((Button) e.getSource()).getText();
            // if (!value.equals("="))
            // so = value;
            if (op.isEmpty())
                return;
            if (result != 0)
                num1 = result;
            op = so;
            // System.out.println(so);
            // System.out.println(op);
            num2 = Double.parseDouble(textField.getText());
            System.out.println(num1 + " " + op + " " + num2);

            if (num2 == 0 && op.equals("/")) {
                textField.setText("Undefined");
            } else {
                result = calculate(num1, num2, op);
                System.out.println(result);
                textField.setText(String.valueOf(result));
            }
            // System.out.println(op);
            start = true;
            pointClicked = false;
            resultDisplayed = true;
        }
    }

    private void processDeleteLastCh(ActionEvent e) {
        String backspace = null;
        if (textField.getText().length() > 0) {
            StringBuilder str = new StringBuilder(textField.getText());
            str.deleteCharAt(textField.getText().length() - 1);
            backspace = str.toString();
            textField.setText(backspace);
        }
    }

    private void processOperatorSqrt(ActionEvent e) {
        String value = ((Button) e.getSource()).getText();
        if (value.equals("√")) {
            double num2 = Double.parseDouble(textField.getText());
            result = Math.sqrt(num2);
            textField.setText(String.valueOf(result));
        }
    }

    private void processOperatorSquare(ActionEvent e) {
        String value = ((Button) e.getSource()).getText();
        if (value.equals("X^2")) {
            double num2 = Double.parseDouble(textField.getText());
            result = Math.pow(num2, 2);
            textField.setText(String.valueOf(result));
        }
    }

    private void processSin(ActionEvent e) {
        double degrees;
        if (start) {
            textField.setText("");
            start = false;
        }
        if (result != 0.0)
            degrees = result;
        else
            degrees = Double.parseDouble(textField.getText());
        double radians = Math.toRadians(degrees);
        result = Math.sin(radians);
        textField.setText(String.valueOf(result));
        start = true;
    }

    private void processCos(ActionEvent e) {
        double degrees;
        if (start) {
            textField.setText("");
            start = false;
        }
        if (result != 0.0)
            degrees = result;
        else
            degrees = Double.parseDouble(textField.getText());
        double radians = Math.toRadians(degrees);
        result = Math.cos(radians);
        textField.setText(String.valueOf(result));
        start = true;
    }

    private void processTan(ActionEvent e) {
        double degrees;
        if (start) {
            textField.setText("");
            start = false;
        }
        if (result != 0.0)
            degrees = result;
        else
            degrees = Double.parseDouble(textField.getText());

        if (degrees == 90)
            textField.setText("Undefined");
        else {
            double radians = Math.toRadians(degrees);
            result = Math.tan(radians);
            textField.setText(String.valueOf(result));
        }
        start = true;
    }

    private void processInverse(ActionEvent e) {
        if (start) {
            textField.setText("");
            start = false;
        }
        if (result != 0.0)
            temp = result;
        else
            temp = Double.parseDouble(textField.getText());
        result = Math.pow(temp, -1);
        textField.setText(String.valueOf(result));
        start = true;
    }

    private void processlnx(ActionEvent e) {
        if (start) {
            textField.setText("");
            start = false;
        }
        if (result != 0.0)
            temp = result;
        else
            temp = Double.parseDouble(textField.getText());
        result = Math.log(temp);
        textField.setText(String.valueOf(result));
        start = true;
    }

    private void processEx(ActionEvent e) {
        if (start) {
            textField.setText("");
            start = false;
        }
        if (result != 0.0)
            temp = result;
        else
            temp = Double.parseDouble(textField.getText());
        result = Math.exp(temp);
        textField.setText(String.valueOf(result));
        start = true;
    }

    private void processlogx(ActionEvent e) {
        if (start) {
            textField.setText("");
            start = false;
        }
        if (result != 0.0)
            temp = result;
        else
            temp = Double.parseDouble(textField.getText());
        result = Math.log10(temp);
        textField.setText(String.valueOf(result));
        start = true;
    }

    private void process10x(ActionEvent e) {
        if (start) {
            textField.setText("");
            start = false;
        }
        if (result != 0.0)
            temp = result;
        else
            temp = Double.parseDouble(textField.getText());
        result = Math.pow(10, temp);
        textField.setText(String.valueOf(result));
        start = true;
    }

    private void processxPowy(ActionEvent e) {
        if (start) {
            textField.setText("");
            start = false;
        }
        String value = ((Button) e.getSource()).getText();

        if (!value.equals("="))
            so = value;
        // System.out.println(so);

        if (!value.equals("=")) {
            if (resultDisplayed) {
                resultDisplayed = false;
                // textField.appendText(value);
            }
            if (!op.isEmpty())
                return;
            if (result != 0)
                num1 = result;
            num1 = Long.parseLong(textField.getText());
            so = ((Button) e.getSource()).getText();
            // System.out.println(so);
            op = so;
            textField.setText("");
            resultDisplayed = false;
        } else {
            if (op.isEmpty())
                return;
            if (result != 0)
                num1 = result;
            num2 = Long.parseLong(textField.getText());
            result = Math.pow(num1, num2);
            System.out.println(result);
            textField.setText(String.valueOf(result));
            start = true;
            op = "";
            resultDisplayed = true;
        }
    }

    private double calculate(double num1, double num2, String operator) {
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "X":
                return num1 * num2;
            case "/":
                if (num2 == 0)
                    textField.setText("Undefined");
                else
                    return num1 / num2;
            case "%":
                return num1 % num2;
            case "x^y":
                return Math.pow(num1, num2);
            default:
                return 0;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}