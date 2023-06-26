package com.example.wordoccurence;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;





    public class FinalGUI extends JFrame {
        @Test
        public void testWordOccurrence() throws Exception {

            URL TESTING = new URL("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm");


            String TestWords = "once";


            InputStream TestInput = new ByteArrayInputStream(TestWords.getBytes());


            BufferedReader TestReader = new BufferedReader(new InputStreamReader(TestInput));


            FinalGUI TestGUI = new FinalGUI() {

                URL newURL () throws Exception {
                    return TESTING;
                }

                BufferedReader createBufferedReader(URL url)  {
                    return TestReader;
                }


                Scanner newScanner (InputStream inputStream) {
                    return new Scanner(inputStream);
                }

            };


            TestGUI.WordOccurence();


            String outputText = TestGUI.getOutputText().getText();


            StringBuilder OUTPUT = new StringBuilder();

          OUTPUT.append("once occurred 1 times");



            Assertions.assertEquals(OUTPUT.toString(), outputText);
        }





        public JTextArea getOutputText() {
            return outputtext;
        }




        private JTextArea outputtext;



        public FinalGUI() {
            setTitle("Word Occurrence");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());

            outputtext = new JTextArea();
            outputtext.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(outputtext);
            add(scrollPane, BorderLayout.CENTER);

            JButton showOutputButton = new JButton("Show Output");
            showOutputButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        WordOccurence();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });


            JPanel buttonPanel = new JPanel();
            buttonPanel.add(showOutputButton);
            add(buttonPanel, BorderLayout.SOUTH);

            pack();
            setLocationRelativeTo(null);

        }

        public void WordOccurence() throws Exception {
            URL url = new URL("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine).append("\n");
            }
            in.close();

            String modifiedContent = content.toString().replace("<[^]+>", "")
                    .replaceAll("&[^;]+;", "")
                    .replaceAll("<br>", "")
                    .replaceAll("<b>", "")
                    .replaceAll("</b>", "")
                    .replaceAll("<i>", "")
                    .replaceAll("</i>", "")
                    .replaceAll("<u>", "")
                    .replaceAll("</u>", "")
                    .replaceAll("\"", "")
                    .replaceAll("<.*?>", "")
                    .replaceAll("=", "")
                    .replaceAll(",", "")
                    .replaceAll("\\[|\\]", "")
                    .replaceAll("\"", "")
                    .replaceAll(",", "")
                    .replace("</style>", "")
                    .replaceAll("}", "")
                    .replaceAll("<.*?>", "")
                    .replaceAll("\\.", "");

            InputStream inputStream = url.openStream();
            Scanner urlInput = new Scanner(inputStream);

            ArrayList<String> words = new ArrayList<>();
            ArrayList<Integer> count = new ArrayList<>();



            int start1 = 235;
            int output1 = 0;

            while (urlInput.hasNext() && output1 < start1 + count.size()) {
                String nextWord = urlInput.next().toLowerCase();
                if (words.contains(nextWord)) {
                    int index = words.indexOf(nextWord);
                    count.set(index, count.get(index) + 1);
                } else {

                    words.add(nextWord);
                    count.add(1);
                }
            }

            urlInput.close();
            inputStream.close();

            StringBuilder output = new StringBuilder();
            for (int i = start1; i < words.size() && i < start1 + 20; i++) {
                output.append(words.get(i)).append(" occurred ").append(count.get(i)).append(" times\n");
            }

            outputtext.setText(output.toString());

        }
        public ArrayList<String> getWords() {
            return extractWords(outputtext.getText());
        }

        public ArrayList<Integer> getCounts() {
            return extractCounts(outputtext.getText());
        }
        private ArrayList<String> extractWords(String output) {
            ArrayList<String> words = new ArrayList<>();
            String[] lines = output.split("\n");
            for (String line : lines) {
                String[] parts = line.split(" ");
                if (parts.length >= 3) {
                    String word = parts[0];
                    words.add(word);
                }
            }
            return words;
        }

        private ArrayList<Integer> extractCounts(String output) {
            ArrayList<Integer> counts = new ArrayList<>();
            String[] lines = output.split("\n");
            for (String line : lines) {
                String[] parts = line.split(" ");
                if (parts.length >= 3) {
                    int count = Integer.parseInt(parts[2]);
                    counts.add(count);
                }
            }
            return counts;
        }


        public static void main(String[] args) {
            SwingUtilities.invokeLater(new Runnable() {


                public void run() {
                    FinalGUI wordOccurrenceGUI = new FinalGUI();
                    wordOccurrenceGUI.setVisible(true);
                }
            });



        }
    }




