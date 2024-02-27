/***
 * * Author: Hank Rugg
 * Date: Feb. 26, 2024
 *
 * This program creates a GUI to display the results of the predictions
 *
 * Resources:
 * Kotlin Gui : https://zetcode.com/kotlin/swing/
 *
 */

import java.awt.Dimension
import java.awt.EventQueue
import java.awt.GridLayout
import javax.swing.*


class Gui() : JFrame() {

    var first: Boolean = true;

    init {
        makeGui()
    }

    private fun makeGui() {
        setSize(550, 600)
        layout = GridLayout(8, 1) // Two rows, one column
        isResizable = false

        val instructionsPanel = JPanel()

        val welcomeLabel1 = JLabel("Welcome to Iris predictions! This is a program that uses the nearest neighbors")
        val welcomeLabel2 = JLabel("algorithm to predict species of Iris flower.")
        val welcomeLabel3 = JLabel("Enter the specified information and get a prediction!")

        welcomeLabel1.setSize(100, 20)
        welcomeLabel2.setSize(100, 20)
        welcomeLabel3.setSize(100, 20)

        instructionsPanel.add(welcomeLabel1)
        instructionsPanel.add(welcomeLabel2)
        instructionsPanel.add(welcomeLabel3)

        //sepal width panel
        val panel1 = JPanel() // Create a panel to hold buttons


        val sepalWidthLabel = JLabel("Sepal Width:")
        sepalWidthLabel.setSize(20, 20)
        panel1.add(sepalWidthLabel)

        val sepalWidth = JTextField(10)
        sepalWidth.setSize(50, 10)
        panel1.add(sepalWidth)

        //sepal length panel
        val panel2 = JPanel() // Create a panel to hold buttons

        val sepalLengthLabel = JLabel("Sepal Length:")
        sepalLengthLabel.setSize(20, 20)
        panel2.add(sepalLengthLabel)

        val sepalLength = JTextField(10)
        sepalLength.setSize(50, 10)
        panel2.add(sepalLength)

        //Petal width panel
        val panel3 = JPanel() // Create a panel to hold buttons

        val petalWidthLabel = JLabel("Petal Width:")
        petalWidthLabel.setSize(20, 20)
        panel3.add(petalWidthLabel)

        val petalWidth = JTextField(10)
        petalWidth.setSize(50, 10)
        panel3.add(petalWidth)

        //petal length panel
        val panel4 = JPanel() // Create a panel to hold buttons

        val petalLengthLabel = JLabel("Petal Length:")
        petalLengthLabel.setSize(20, 20)
        panel4.add(petalLengthLabel)

        val petalLength = JTextField(10)
        petalLength.setSize(50, 10)
        panel4.add(petalLength)

        //number of neighbors panel
        val neighborsPanel = JPanel() // Create a panel to hold buttons

        val neighborsLabel = JLabel("Neighbors:")
        neighborsLabel.setSize(20, 20)
        neighborsPanel.add(neighborsLabel)

        val neighbors = JTextField(10)
        neighbors.setSize(50, 10)
        neighborsPanel.add(neighbors)

        val panel5 = JPanel() // Create a panel to hold buttons

        val predict = JButton("Make Prediction")
        predict.preferredSize = Dimension(200, 50)
        predict.addActionListener {
            makePrediction(
                neighbors.text,
                sepalLength.text,
                sepalWidth.text,
                petalLength.text,
                petalWidth.text
            )
        }
        panel5.add(predict)

        // add all the panels
        add(instructionsPanel)
        add(panel1)
        add(panel2)
        add(panel3)
        add(panel4)
        add(neighborsPanel)
        add(panel5)

    }

    private fun makePrediction(neighbors: String, slength: String, swidth: String, plength: String, pwidth: String) {

        removeAllPredictionLabels()

        var validInput = true;

        val panel6 = JPanel()
        panel6.name = "panel6" // Set the name for identification

        // load the data
        val n = NearestNeighbors()
        n.loadData("src/main/kotlin/Iris.csv")

        var results = "Invalid Input"

        // check for valid input
        if (!n.validateFloatInput(slength)) {
            results = "Invalid Sepal Length"
            validInput = false
        } else if (!n.validateFloatInput(swidth)) {
            results = "Invalid Sepal Width"
            validInput = false
        } else if (!n.validateFloatInput(plength)) {
            results = "Invalid Petal Length"
            validInput = false
        } else if (!n.validateFloatInput(pwidth)) {
            results = "Invalid Petal Width"
            validInput = false
        } else if (!n.validateIntInput(neighbors)) {
            results = "Invalid Neighbors input"
            validInput = false
        } else if (n.data.size < neighbors.toInt()) {
            results = "Invalid amount of neighbors"
            validInput = false
        }


        if (validInput) {
            results =
                n.test(neighbors.toInt(), slength.toFloat(), swidth.toFloat(), plength.toFloat(), pwidth.toFloat())
        }
        val resultsLabel = JLabel(results)
        panel6.add(resultsLabel)

        add(panel6) // Add the panel to the frame

        revalidate()
        repaint()
    }

    // only show one prediciton at a time
    private fun removeAllPredictionLabels() {
        val panel6 = contentPane.components.find { it.name == "panel6" } as? JPanel
        panel6?.let {
            remove(it) // Remove the panel from the frame
            revalidate() // Update the layout
            repaint()
        }
    }
}


private fun createAndShowGUI() {
    val frame = Gui()
    frame.isVisible = true

}

fun main() {
    EventQueue.invokeLater(::createAndShowGUI)
}