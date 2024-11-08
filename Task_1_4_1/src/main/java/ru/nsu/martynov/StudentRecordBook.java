package ru.nsu.martynov;

import static ru.nsu.martynov.Constants.DIFF_NAME;
import static ru.nsu.martynov.Constants.EXAM_NAME;
import static ru.nsu.martynov.Constants.EXCELLENT_MARK;
import static ru.nsu.martynov.Constants.EXCELLENT_PERCENTAGE_FOR_BIG_STIP;
import static ru.nsu.martynov.Constants.EXCELLENT_PERCENTAGE_FOR_RED_DIPLOM;
import static ru.nsu.martynov.Constants.SATISFACTORY_COUNT_FOR_RED_DIPLOM;
import static ru.nsu.martynov.Constants.SATISFACTORY_COUNT_FOR_BIG_STIP;
import static ru.nsu.martynov.Constants.SATISFACTORY_MARK;
import static ru.nsu.martynov.Constants.ZACH_NAME;

import java.util.ArrayList;
import java.util.List;

/**
 * Class about student record book.
 */
public class StudentRecordBook {
    // Список с предметами, их оценками, номером семестра и похожим.
    private List<Grade> grades;
    // Квалификационная работа (EXCELLENT_MARK, GOOD_MARK, SATISFACTORY_MARK или ниже)
    private int qualificationWorkGrade;
    // true - бюджет, false - платное обучение
    private boolean isFreeEducation;


    /**
     * Constructor.
     *
     * @param isFreeEducation — boolean flag.
     */
    public StudentRecordBook(boolean isFreeEducation) {
        this.grades = new ArrayList<>();
        this.isFreeEducation = isFreeEducation;
    }

    /**
     * Method to add grade to recordBook.
     *
     * @param subject — subject name.
     * @param semester — number of semester.
     * @param gradeType — exam, diff_zachet or zachet.
     * @param gradeValue — grade value, for example, 5 or 2.
     */
    public void addGrade(String subject, int semester, String gradeType, int gradeValue) {
        grades.add(new Grade(subject, semester, gradeType, gradeValue));
    }

    /**
     * Method for add zachet to record block.
     *
     * @param subject — subject name.
     * @param semester — number of semester.
     * @param pass — zachet or otchislen :).
     */
    public void addZach(String subject, int semester, boolean pass) {
        grades.add(new Grade(subject, semester, ZACH_NAME, pass ? 5 : 2));
    }

    /**
     * Set Qualification work grade.
     *
     * @param gradeValue — grade value.
     */
    public void setQualificationWorkGrade(int gradeValue) {
        this.qualificationWorkGrade = gradeValue;
    }

    /**
     * Method to calculate average grade.
     *
     * @return average grade for every semesters.
     */
    public double calculateAverageGrade() {
        int totalPoints = 0;
        int count = 0;
        for (Grade grade : grades) {
            if (grade.isExam() || grade.isDiff()) {
                totalPoints += grade.getNumericValue();
                count++;
            }
        }

        return count == 0 ? 0 : (double) totalPoints / count;
    }

    /**
     * This method return boolean value — can or not student get FREE education.
     *
     * @return can student get FREE education or not.
     */
    public boolean canGetFreeEducation() {
        if (isFreeEducation) {
            return false; // Уже на бюджете
        }

        // Проверка оценок за последние два семестра
        int lastSemester = getLastSemester();

        for (Grade grade : grades) {
            boolean satisfactory = grade.getNumericValue() <= SATISFACTORY_MARK;
            boolean lastPreLastSem = grade.getSemester() == lastSemester
                                    || grade.getSemester() == lastSemester - 1;
            // если в течение последних двух семестров плохая оценка за экзамен
            if (grade.isExam() && lastPreLastSem && satisfactory) {
                return false;
            }
        }

        return true; // Нет плохих оценок за последние два семестра
    }

    /**
     * Method for changing FreeEducation status.
     *
     * @param free — boolean flag.
     */
    public void setFreeEducation(boolean free) {
        isFreeEducation = free;
    }

    // 3. Проверка возможности получения диплома с отличием

    /**
     * This method return boolean value — can or not student get Red Diplom.
     *
     * @return can student get Red Diplom having current grades.
     */
    public boolean canGetRedDiplom() {
        if (qualificationWorkGrade != EXCELLENT_MARK) { // Квалификационная работа не на "отлично"
            return false;
        }

        int excellentCount = 0;
        int satisfactoryCount = 0;

        for (Grade grade : grades) {
            if (grade.isExam() || grade.isDiff()) {
                if (grade.getNumericValue() == EXCELLENT_MARK) {
                    excellentCount++;
                } else if (grade.getNumericValue() <= SATISFACTORY_MARK) {
                    satisfactoryCount++;
                }
            }
        }

        // Нет оценок — нет красного (любого) диплома
        double excPercent = grades.isEmpty() ? 0 : (double) excellentCount / grades.size();
        return excPercent >= EXCELLENT_PERCENTAGE_FOR_RED_DIPLOM
                && satisfactoryCount == SATISFACTORY_COUNT_FOR_RED_DIPLOM;
    }

    /**
     * This method return boolean value — can or not student get BIG stip.
     *
     * @return can student get BIG stip or not.
     */
    public boolean canGetBigStip() {
        if (grades.isEmpty()) {
            // нельзя получать стипендию без оценок
            return false;
        }

        int lastSemester = getLastSemester();
        int excellentCount = 0;
        int satisfactoryCount = 0;
        int count = 0;

        for (Grade grade : grades) {
            if (grade.getSemester() == lastSemester) {
                if (grade.isZach() && grade.getNumericValue() <= SATISFACTORY_MARK) {
                    return false; // отчислен, если НЕзачёт
                }
                if (grade.isExam() || grade.isDiff()) {
                    if (grade.getNumericValue() == EXCELLENT_MARK) {
                        excellentCount++;
                    } else if (grade.getNumericValue() <= SATISFACTORY_MARK) {
                        satisfactoryCount++;
                    }
                    count++; // количество всех оценок
                }
            }
        }

        double excellentPercentage = count == 0 ? 1 : (double) excellentCount / count;
        return excellentPercentage >= EXCELLENT_PERCENTAGE_FOR_BIG_STIP
                && satisfactoryCount == SATISFACTORY_COUNT_FOR_BIG_STIP;
    }

    private int getLastSemester() {
        int lastSemester = 0;
        for (Grade grade : grades) {
            if (grade.getSemester() > lastSemester) {
                lastSemester = grade.getSemester();
            }
        }

        return lastSemester;
    }

    /**
     * Class for storing information about grades.
     */
    private static class Grade {
        private String subject;
        private int semester;
        private String gradeType; // EXAM_NAME, DIFF_NAME, или ZACH_NAME
        private int gradeValue;

        /**
         * Constructor.
         *
         * @param subject — subject name.
         * @param semester — number of semester.
         * @param gradeType — exam, diff or zachet.
         * @param gradeValue — grade, for example, 5 or 2.
         */
        public Grade(String subject, int semester, String gradeType, int gradeValue) {
            this.subject = subject;
            this.semester = semester;
            this.gradeType = gradeType;
            this.gradeValue = gradeValue;
        }

        /**
         * Method for getting gradeValue.
         *
         * @return grade value.
         */
        public int getNumericValue() {
            return gradeValue;
        }

        /**
         * Method return true if grade from exam.
         *
         * @return if gradeType is exam.
         */
        public boolean isExam() {
            return gradeType.equals(EXAM_NAME);
        }

        /**
         * Method return true if grade from diff zachet.
         *
         * @return if gradeType is diff zachet.
         */
        public boolean isDiff() {
            return gradeType.equals(DIFF_NAME);
        }

        /**
         * Method return true if grade from zachet.
         *
         * @return if gradeType is zachet.
         */
        public boolean isZach() {
            return gradeType.equals(ZACH_NAME);
        }

        /**
         * Method return number current semester.
         *
         * @return number of current semester.
         */
        public int getSemester() {
            return semester;
        }
    }
}
