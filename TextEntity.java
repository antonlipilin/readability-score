package readability;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
    class TextEntity {
    private final String content;

    TextEntity(String content) {
        this.content = content;
    }

    private int getCountOfWords() {
        return this.content.split("[\\t\\s\\n]").length;
    };

     private int getCountOfSentences() {
        return this.content.trim().split("[.!?]").length;
    }

     private int getCountOfCharacters() {
        int countOfCharacters = 0;
        String[] words = this.content.split("[\\t\\s\\n]");

        for (String word: words) {
            countOfCharacters += word.split("").length;
        }
        return countOfCharacters;
    }

     private int getCountSyllablesInText() {
        String[] words = this.content.split("[\\t\\s\\n]");
        int allSyllables = 0;

        for (String word: words) {
            allSyllables += getCountSyllablesInWord(word);
        }

        return allSyllables;
    }

    private int getCountSyllablesInWord(String word) {
        String regExp = "e[\\w]|[aouieyAOUIEY]{2,}|[aouiyAOUIY]\\b|\\b[aouiyeEAOUIY][^aouiyeEAOUIY]|[aouiyAOUIY]";
        int syllables = 0;
        Matcher matcher = Pattern.compile(regExp).matcher(word);

        while (matcher.find()) {
            syllables += 1;
        }

        return syllables == 0 ? 1 : syllables;
    }

     private int getCountOfPolysyllables() {
        String[] words = this.content.split("[\\t\\s\\n]");
        int count = 0;

        for (String word: words) {
            if (getCountSyllablesInWord(word) > 2) {
                count++;
            }
        }

        return count;
    }

    private double getAutomatedReadabilityIndex() {
         double characters = getCountOfCharacters();
         double words = getCountOfWords();
         double sentences = getCountOfSentences();
         return 4.71 * (characters / words) + 0.5 * (words / sentences) - 21.43;
     }

    private double getFleschKincaidIndex() {
         double words = getCountOfWords();
         double sentences = getCountOfSentences();
         double syllables = getCountSyllablesInText();
         return 0.39 * (words / sentences) + 11.8 * (syllables / words ) - 15.59;
     }

    private double getSMOGIndex() {
         double polysyllables = getCountOfPolysyllables();
         double sentences = getCountOfSentences();
         return 1.043 * Math.sqrt(polysyllables * (30 / sentences)) + 3.1291;
     }

    private double getColemanLiauIndex() {
         double characters = getCountOfCharacters();
         double words = getCountOfWords();
         double sentences = getCountOfSentences();
         double s = sentences / words * 100;
         double l = characters / words * 100;
         return 0.0588 * l - 0.296 * s - 15.8;
     }

     void printAutomatedReadabilityIndex() {
         int score = getAgeByScore(getAutomatedReadabilityIndex());
         String ari = getFormattedScore(getAutomatedReadabilityIndex());
         System.out.printf("Automated Readability Index: %s (about %d-year-olds).\n", ari, score);
     }

     void printFleschKincaidIndex() {
         int score = getAgeByScore(getFleschKincaidIndex());
         String fki = getFormattedScore(getFleschKincaidIndex());
         System.out.printf("Flesch–Kincaid readability tests: %s (about %d-year-olds).\n", fki, score);
     }

     void printSMOGIndex() {
         int score = getAgeByScore(getSMOGIndex());
         String smog = getFormattedScore(getSMOGIndex());
         System.out.printf("Simple Measure of Gobbledygook: %s (about %d-year-olds).\n", smog, score);
     }

     void printColemanLiauIndex() {
         int score = getAgeByScore(getColemanLiauIndex());
         String cli = getFormattedScore(getColemanLiauIndex());
         System.out.printf("Coleman–Liau index: %s (about %d-year-olds).\n", cli, score);
     }

     void printAllScores() {
         printAutomatedReadabilityIndex();
         printFleschKincaidIndex();
         printSMOGIndex();
         printColemanLiauIndex();
         System.out.println();
         System.out.printf("This text should be understood in average by %s-year-olds.", getFormattedScore(getAverageAge()));
     }

     private double getAverageAge() {
         return (double) (getAgeByScore(getAutomatedReadabilityIndex()) +
                 getAgeByScore(getFleschKincaidIndex()) +
                 getAgeByScore(getSMOGIndex()) +
                 getAgeByScore(getColemanLiauIndex())) / 4;
     }
     void printInfoAboutText() {
        int countOfWords = getCountOfWords();
        int countOfSentences = getCountOfSentences();
        int countOfCharacters = getCountOfCharacters();
        int countOfSyllables = getCountSyllablesInText();
        int countOfPolysyllables = getCountOfPolysyllables();

        System.out.println("The text is:");
        System.out.println(this.content.trim());
        System.out.println();
        System.out.println("Words: " + countOfWords);
        System.out.println("Sentences: " + countOfSentences);
        System.out.println("Characters: " + countOfCharacters);
        System.out.println("Syllables: " + countOfSyllables);
        System.out.println("Polysyllables: " + countOfPolysyllables);
    }
    private int getAgeByScore(double score) {
            int intValue = (int) Math.ceil(score);
            return switch (intValue) {
                case 1 -> 6;
                case 2 -> 7;
                case 3 -> 8;
                case 4 -> 9;
                case 5 -> 10;
                case 6 -> 11;
                case 7 -> 12;
                case 8 -> 13;
                case 9 -> 14;
                case 10 -> 15;
                case 11 -> 16;
                case 12 -> 17;
                case 13 -> 18;
                default -> 22;
            };
        }

    private String getFormattedScore(double score) {
        String stringValue = String.valueOf(score);

        if (stringValue.length() <= 4) {
            return stringValue;
        }

        int indexOfDot = stringValue.indexOf(".");
        return stringValue.substring(0, indexOfDot + 3);
    }
}
