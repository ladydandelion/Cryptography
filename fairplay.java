public class fairplay {
    private String key = "";
    private char[][] cipherMatrix = new char[5][5];
    private char[] alphabet = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public fairplay() {
    }

    public void setKey(String key) {
        this.key = key;
    }

    private boolean CheckRepeated(char[][] matrix, char word) {
        boolean repeated = false;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == word) {
                    repeated = true;
                }
            }
        }
        return repeated;
    }

    private void fillCipherMatrix() {
        int counter = 0;
        int length = this.key.length();
        for (int i = 0; i < this.cipherMatrix.length; i++) {        //vaciado de matrix cada vez que llenamos
            for (int j = 0; j < this.cipherMatrix[i].length; j++) {
                this.cipherMatrix[i][j] = ' ';
            }
        }
        for (int i = 0; i < this.cipherMatrix.length; i++) {
            for (int j = 0; j < this.cipherMatrix[i].length; j++) {
                if (counter < this.key.length()) {
                    counter = 0;
                    for (int a = 0; a < this.key.length(); a++) {
                        if (this.CheckRepeated(this.cipherMatrix, this.key.charAt(counter))) {
                            counter++;
                        }
                    }
                    this.cipherMatrix[i][j] = this.key.charAt(counter);
                    while (counter < this.key.length()) {
                        if (this.CheckRepeated(this.cipherMatrix, this.key.charAt(counter))) {
                            counter++;
                        } else {
                            break;
                        }
                    }
                } else {
                    int countLetters = 0;
                    while (this.CheckRepeated(cipherMatrix, alphabet[countLetters])) {
                        countLetters++;
                    }

                    cipherMatrix[i][j] = alphabet[countLetters];
                }
            }
        }
        for (int i = 0; i < this.cipherMatrix.length; i++) {
            for (int j = 0; j < this.cipherMatrix[i].length; j++) {
                System.out.print(this.cipherMatrix[i][j]);
            }
            System.out.println("");
        }
    }

    private String addChar(String str, char ch, int position) {
        int len = str.length();
        char[] updatedArr = new char[len + 1];
        str.getChars(0, position, updatedArr, 0);
        updatedArr[position] = ch;
        str.getChars(position, len, updatedArr, position + 1);
        return new String(updatedArr);
    }

    private String replace(String str, int index, char replace) {
        if (str == null) {
            return str;
        } else if (index < 0 || index >= str.length()) {
            return str;
        }

        char[] chars = str.toCharArray();
        chars[index] = replace;
        String result = String.valueOf(chars);

        return String.valueOf(chars);
    }


    private String proccessWord(String word) {
        for (int i = 0; i < word.length() - 1; i++) {
            if (word.charAt(i) == word.charAt(i + 1)) {
                word = this.addChar(word, 'x', i + 1);
            }
        }
        if (word.length() % 2 != 0) {
            word = this.addChar(word, 'x', word.length());
        }
        return word;
    }

    public char[] encrypt(char[][] matrix, char a, char b, boolean decrypt) {
        char[] encrypted = new char[2];
        int[] positions = this.findPos(matrix, a, b);
        if (decrypt) {
            encrypted = this.matrixDecrypt(matrix, positions);
        } else {
            encrypted = this.matrixEncrypt(matrix, positions);
        }
        return encrypted;
    }

    private char[] matrixDecrypt(char[][] matrix, int[] positions) {
        char[] result = new char[2];
        if (positions[1] == positions[3]) { //same column case
            if (positions[0] > 0) {
                result[0] = matrix[positions[0] - 1][positions[1]];
            } else {
                result[0] = matrix[matrix.length - 1][positions[1]];
            }
            if (positions[2] > 0) {
                result[1] = matrix[positions[2] - 1][positions[3]];
            } else {
                result[1] = matrix[matrix.length - 1][positions[3]];
            }
        } else if (positions[0] == positions[2]) { //same row case
            if (positions[1] > 0) {
                result[0] = matrix[positions[0]][positions[1] - 1];
            } else {
                result[0] = matrix[positions[0]][matrix.length - 1];
            }
            if (positions[3] > 0) {
                result[1] = matrix[positions[2]][positions[3] - 1];
            } else {
                result[1] = matrix[positions[2]][matrix.length - 1];
            }
        } else {
            result[0] = matrix[positions[0]][positions[3]];
            result[1] = matrix[positions[2]][positions[1]];
        }
        return result;
    }

    private char[] matrixEncrypt(char[][] matrix, int[] positions) {
        char[] result = new char[2];
        if (positions[1] == positions[3]) { //same column case
            if (positions[0] < matrix.length - 1) {
                result[0] = matrix[positions[0] + 1][positions[1]];
            } else {
                result[0] = matrix[0][positions[1]];
            }
            if (positions[2] < matrix.length - 1) {
                result[1] = matrix[positions[2] + 1][positions[3]];
            } else {
                result[1] = matrix[0][positions[3]];
            }
        } else if (positions[0] == positions[2]) { //same row case
            if (positions[1] < matrix[0].length - 1) {
                result[0] = matrix[positions[0]][positions[1] + 1];
            } else {
                result[0] = matrix[positions[0]][0];
            }
            if (positions[3] < matrix[0].length - 1) {
                result[1] = matrix[positions[2]][positions[3] + 1];
            } else {
                result[1] = matrix[positions[2]][0];
            }
        } else {
            result[0] = matrix[positions[0]][positions[3]];
            result[1] = matrix[positions[2]][positions[1]];
        }
        return result;
    }

    private int[] findPos(char[][] matrix, char a, char b) {
        int[] pos = new int[4];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == a) {
                    pos[0] = i;
                    pos[1] = j;
                }
                if (matrix[i][j] == b) {
                    pos[2] = i;
                    pos[3] = j;
                }
                if (a == ' ') {                 //los espacios encriptados como ultima pos de la matriz
                    int[] posz = this.findPosUnic(matrix, 'z');
                    pos[0] = posz[0];
                    pos[1] = posz[1];
                }
                if (b == ' ') {
                    int[] posz = this.findPosUnic(matrix, 'z');
                    pos[2] = posz[0];
                    pos[3] = posz[1];
                }
            }
        }
        return pos;
    }

    private int[] findPosUnic(char[][] matrix, char a) {
        int[] pos = new int[4];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == a) {
                    pos[0] = i;
                    pos[1] = j;
                }
            }
        }
        return pos;
    }


    public String encrypt(String word, String key) {
        String wordProcessed = this.proccessWord(word);
        int first = 0;
        int second = 1;
        this.setKey(key);
        this.fillCipherMatrix();
        while (second < wordProcessed.length()) {
            char[] encrypted = this.encrypt(this.cipherMatrix, wordProcessed.charAt(first), wordProcessed.charAt(second), false);
            wordProcessed = this.replace(wordProcessed, first, encrypted[0]);
            wordProcessed = this.replace(wordProcessed, second, encrypted[1]);
            first += 2;
            second += 2;
        }

        return wordProcessed;
    }


    public String decrypt(String word, String key) {
        int first = 0;
        int second = 1;
        this.setKey(key);
        this.fillCipherMatrix();
        while (second < word.length()) {
            char[] encrypted = this.encrypt(this.cipherMatrix, word.charAt(first), word.charAt(second), true);
            word = this.replace(word, first, encrypted[0]);
            word = this.replace(word, second, encrypted[1]);
            first += 2;
            second += 2;
        }
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == 'z') {
                word = this.replace(word, i, ' ');         //substitute z by spaces because i decided to encrypt spaces as z
            } else if (word.charAt(i) == 'x') {
                word = word.substring(0, i) + word.substring(i + 1, word.length());   //deletes x because they were used in preccing of the word
            }
        }
        return word;
    }


}
