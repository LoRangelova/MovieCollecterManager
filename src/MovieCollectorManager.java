import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

class Movie {
    String title, genre;

    Movie(String title, String genre) {
        this.title = title;
        this.genre = genre;
    }
}

public class MovieCollectorManager {
    private Movie[] movies = new Movie[100];
    private int movieCount = 0;

    private JFrame frame;
    private JTextField titleField, searchField;
    private JComboBox<String> genreBox;
    private JTable movieTable;
    private DefaultTableModel tableModel;
    private JLabel countLabel;

    public MovieCollectorManager() {
        frame = new JFrame("Movie Collector Manager");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        titleField = new JTextField();
        genreBox = new JComboBox<>(new String[]{"Action", "Comedy", "Horror", "Drama", "Sci-Fi", "Romance"});
        JButton addButton = new JButton("Add Movie");
        searchField = new JTextField();
        JButton searchButton = new JButton("Search");
        JButton sortTitleButton = new JButton("Sort by Title");
        JButton sortGenreButton = new JButton("Sort by Genre");
        countLabel = new JLabel("Total Movies: 0");

        tableModel = new DefaultTableModel(new String[]{"Title", "Genre"}, 0);
        movieTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(movieTable);

        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Genre:"));
        panel.add(genreBox);
        panel.add(addButton);
        panel.add(new JLabel("Search:"));
        panel.add(searchField);
        panel.add(searchButton);
        panel.add(sortTitleButton);
        panel.add(sortGenreButton);
        panel.add(countLabel);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addMovie();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchMovie();
            }
        });

        sortTitleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sortMoviesByTitle();
            }
        });

        sortGenreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sortMoviesByGenre();
            }
        });

        frame.setVisible(true);
    }

    private void addMovie() {
        String title = titleField.getText().trim();
        String genre = (String) genreBox.getSelectedItem();

        if (!title.isEmpty() && movieCount < movies.length) {
            movies[movieCount] = new Movie(title, genre);
            movieCount++;
            updateMovieTable();
            titleField.setText("");
        }
    }

    private void updateMovieTable() {
        countLabel.setText("Total Movies: " + movieCount);
        tableModel.setRowCount(0);
        for (int i = 0; i < movieCount; i++) {
            tableModel.addRow(new Object[]{movies[i].title, movies[i].genre});
        }
    }

    private void searchMovie() {
        String searchTitle = searchField.getText().trim().toLowerCase();

        if (searchTitle.isEmpty()) return;

        for (int i = 0; i < movieCount; i++) {
            if (movies[i].title.toLowerCase().contains(searchTitle)) {
                movieTable.setRowSelectionInterval(i, i);
                return;
            }
        }
        movieTable.clearSelection();
    }

    private void sortMoviesByTitle() {
        for (int i = 0; i < movieCount - 1; i++) {
            for (int j = 0; j < movieCount - i - 1; j++) {
                if (movies[j].title.compareToIgnoreCase(movies[j + 1].title) > 0) {
                    Movie temp = movies[j];
                    movies[j] = movies[j + 1];
                    movies[j + 1] = temp;
                }
            }
        }
        updateMovieTable();
    }

    private void sortMoviesByGenre() {
        for (int i = 0; i < movieCount - 1; i++) {
            for (int j = 0; j < movieCount - i - 1; j++) {
                if (movies[j].genre.compareToIgnoreCase(movies[j + 1].genre) > 0) {
                    Movie temp = movies[j];
                    movies[j] = movies[j + 1];
                    movies[j + 1] = temp;
                }
            }
        }
        updateMovieTable();
    }

    public static void main(String[] args) {
        new MovieCollectorManager();
    }
}
