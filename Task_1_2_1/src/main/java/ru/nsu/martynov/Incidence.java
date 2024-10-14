package ru.nsu.martynov;

import java.util.Arrays;

public class Incidence implements Graph {
    private int[][] matrix;

    public Incidence() {
        this.matrix = new int[0][0];
    }

    public int getVertCount() {
        return this.matrix.length;
    }

    public int getEdgeCount() {
        if (this.matrix.length == 0)
            return 0;
        return this.matrix[0].length;
    }

    public int[][] matrix() {
        return this.matrix;
    }

    private void printMatrix() {
        for (int[] integers : matrix) {
            for (Integer integer : integers) {
                // negative numbers have '-' symbol, but positive don't.
                if (integer >= 0) {
                    System.out.print(" " + integer + " ");
                } else {
                    System.out.print(integer + " ");
                }
            }
            System.out.println();
        }
    }

    // Добавление новой вершины
    public void addVert() {
        // На одну строчку больше.
        if (this.matrix.length <= 0) {
            this.matrix = new int[1][0];
            return;
        }
        int[][] newMatrix = new int[this.matrix.length + 1][this.matrix[0].length];

        // Копируем старую матрицу в новую
        for (int i = 0; i < this.matrix.length; i++) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, this.matrix[0].length);
        }

        // Заменяем старую матрицу на новую
        matrix = newMatrix;
    }

    // Удаление вершины по индексу.
    // То есть стереть строчку и связанные ребра. O(M) — кол-во рёбер.
    public void remVert(int index) {
        if (index < 0 || index >= this.matrix.length) {
            throw new IllegalArgumentException("Invalid index");
        }
        int cnt = 0;
        for (int j = 0; j < this.matrix[index].length; j++) {
            if (this.matrix[index][j] == 0) {
                cnt++;
            }
        }

        int[][] newMatrix = new int[this.matrix.length - 1][cnt];

        for (int j = 0, jj = 0; j < this.matrix[index].length; j++) {
            // Если ребра в/из удаляемой вершины нет, копируем столбец.
            // Иначе ребро есть и столбец нужно занулить, значит столбец станет бесполезным.
            if (this.matrix[index][j] == 0) {
                for (int i = 0; i < this.matrix.length; i++) {
                    newMatrix[i][jj] = this.matrix[index][j];
                }
                jj++;
            }
        }

        // Заменяем старую матрицу на новую.
        matrix = newMatrix;
    }

    @Override
    public void addEdge(int from, int to) {
        if (from < 0 || from >= this.matrix.length || to < 0 || to >= this.matrix.length) {
            throw new IllegalArgumentException("Invalid vertex index");
        }

        int edgeIndex = -1;

        if (from == to) {
            for (int j = 0; j < this.matrix[0].length; j++) {
                boolean flag = true;
                if (this.matrix[from][j] == 0) {
                    for (int i = 0; i < this.matrix.length; i++) {
                        if (i == from) {
                            continue;
                        }
                        if (this.matrix[i][j] != 0) {
                            flag = false;
                            break;
                        }
                    }
                }
                // Если весь столбец занулён, кроме ячейки from == to.
                // То есть нашли ребро в себя.
                if (flag) {
                    edgeIndex = j;
                    break;
                }
            }
        } else {
            for (int j = 0; j < this.matrix[0].length; j++) {
                if (this.matrix[from][j] != 0
                        && this.matrix[from][j] == (-1 * this.matrix[to][j])) {
                    edgeIndex = j;
                    break;
                }
            }
        }

        // Ребра не было — создаём.
        // Иначе добавим 2k или (+k и -k) к рёбрам.
        if (edgeIndex == -1) {
            int[][] newMatrix = new int[this.matrix.length][this.matrix[0].length + 1];

            for (int i = 0; i < this.matrix.length; i++) {
                System.arraycopy(this.matrix[i], 0, newMatrix[i], 0, this.matrix[0].length);
            }

            // Ребро в себя — 2.
            // Иначе +1 и -1.
            if (from == to) {
                newMatrix[from][this.matrix[0].length] = 2;
            } else {
                newMatrix[from][this.matrix[0].length] = 1;
                newMatrix[to][this.matrix[0].length] = -1;
            }

            this.matrix = newMatrix;
        } else {
            if (from == to) {
                this.matrix[from][edgeIndex] += 2;
            } else {
                matrix[from][this.matrix[0].length]++;
                matrix[to][this.matrix[0].length]--;
            }
        }
    }

    @Override
    public int remEdge(int from, int to) {
        if (from < 0 || from >= this.matrix.length || to < 0 || to >= this.matrix.length) {
            throw new IllegalArgumentException("Invalid vertex index");
        }

        // Если ребро в себя же, ищу его и пытаюсь -2k сделать.
        // Иначе ищу ребро from-to, пытаюсь -(+k) и -(-k) сделать.
        if (from == to) {
            int edgeIndex = -1;
            for (int j = 0; j < this.matrix[0].length; j++) {
                boolean flag = true;
                if ((this.matrix[from][j]) != 0 && (this.matrix[from][j] % 2 == 0)) {
                    for (int i = 0; i < this.matrix.length; i++) {
                        if (i == from) {
                            continue;
                        }
                        if (this.matrix[i][j] != 0) {
                            flag = false;
                            break;
                        }
                    }
                }
                // Если весь столбец занулён, кроме ячейки from == to.
                // То есть нашли ребро в себя.
                if (flag) {
                    edgeIndex = j;
                    break;
                }
            }

            if (edgeIndex == -1) {
                return 0;
            } else {
                if (this.matrix[from][edgeIndex] < 2) {
                    int cnt = this.matrix[from][edgeIndex] / 2;
                    this.matrix[from][edgeIndex] = 0;
                    return cnt;
                } else {
                    this.matrix[from][edgeIndex] -= 2;
                    return 1;
                }
            }
        } else { // Если не в себя.
            int edgeIndex = -1;
            for (int j = 0; j < this.matrix[0].length; j++) {
                if (this.matrix[from][j] == (-1 * this.matrix[to][j])) {
                    edgeIndex = j;
                    break;
                }
            }
            if (edgeIndex == -1) {
                return 0;
            } else {
                if (this.matrix[from][edgeIndex] <= 0) {
                    this.matrix[from][edgeIndex] = 0;
                    this.matrix[to][edgeIndex] = 0;
                    return 0;
                } else {
                    matrix[from][edgeIndex]--;
                    matrix[to][edgeIndex]++;
                    return 1;
                }
            }
        }
    }

    @Override
    public int[] getNeighbours(int index) {
        int cnt = 0;
        int[] neighbours = new int[this.matrix.length];
        for (int j = 0; j < this.matrix[index].length; j++) {
            if (this.matrix[index][j] != 0) {
                int oldCnt = cnt;

                for (int i = 0; i < this.matrix.length; i++) {
                    if (i == index) {
                        continue;
                    }
                    if (this.matrix[i][j] != 0) {
                        neighbours[cnt++] = i;
                        break;
                    }
                }
                if (oldCnt == cnt) {
                    neighbours[cnt] = index;
                    cnt++;
                }
            }
        }
        neighbours = Arrays.copyOf(neighbours, cnt);
        return neighbours;
    }

    @Override
    public void printGraph() {
        printMatrix();
    }

    @Override
    public void readFile(String fileName) {
        // TO DO
    }
}
