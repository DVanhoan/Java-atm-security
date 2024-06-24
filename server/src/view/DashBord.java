package view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DashBord extends JPanel {

    private final HashMap<String, Integer> userData;

    public DashBord() throws SQLException {
        this.userData = getUserData();
        setupUI();
    }

    private HashMap<String, Integer> getUserData() {
        HashMap<String, Integer> data = new HashMap<>();
        data.put("User1", 150);
        data.put("User2", 200);
        return data;
    }

    private void setupUI() {
        this.setLayout(new BorderLayout());
        this.add(createChartPanel(), BorderLayout.CENTER);
    }

    private JPanel createChartPanel() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Integer> entry : userData.entrySet()) {
            dataset.addValue(entry.getValue(), "Scores", entry.getKey());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "User Scores", // Chart title
                "User", // Domain axis label
                "Score", // Range axis label
                dataset, // Data
                PlotOrientation.VERTICAL, // Orientation
                true, // Include legend
                true, // Tooltips
                false // URLs?
        );

        return new ChartPanel(chart);
    }
}
