package ru.nsu.martynov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.nsu.martynov.studentrecord.Constants.DIFF_NAME;
import static ru.nsu.martynov.studentrecord.Constants.EXAM_NAME;
import static ru.nsu.martynov.studentrecord.Constants.EXCELLENT_MARK;
import static ru.nsu.martynov.studentrecord.Constants.GOOD_MARK;
import static ru.nsu.martynov.studentrecord.Constants.SATISFACTORY_MARK;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.martynov.studentrecord.StudentRecordBook;

class StudentRecordBookTest {
    private StudentRecordBook recordBook;

    @BeforeEach
    void setUp() {
        // Инициализируем зачетную книжку перед каждым тестом
        recordBook = new StudentRecordBook(false); // Студент на платном обучении
    }

    @Test
    void addGradeTest() {
        recordBook.addGrade("Math", 1, EXAM_NAME, EXCELLENT_MARK);
        recordBook.addGrade("Physics", 1, DIFF_NAME, GOOD_MARK);

        // Проверка, что оценки успешно добавлены и средняя оценка корректна
        assertEquals(4.5, recordBook.calculateAverageGrade());
    }

    @Test
    void addZachTest() {
        recordBook.addGrade("Math", 1, DIFF_NAME, EXCELLENT_MARK);
        recordBook.addGrade("Language", 1, DIFF_NAME, GOOD_MARK);
        recordBook.addZach("Physical Education", 1, true);  // Зачет

        // Проверка, что зачёт добавлен
        assertEquals(4.5, recordBook.calculateAverageGrade());
    }

    @Test
    void setQualificationWorkGradeTest() {

        recordBook.setQualificationWorkGrade(EXCELLENT_MARK); // Оценка за квалификационную работу
        assertFalse(recordBook.canGetRedDiplom(), "Нет оценок — НЕЛЬЗЯ");

        recordBook.addGrade("Math", 1, DIFF_NAME, EXCELLENT_MARK);
        assertTrue(recordBook.canGetRedDiplom(), "100% excellent and QWork is excellent — Red");

        recordBook.setQualificationWorkGrade(4);
        assertFalse(recordBook.canGetRedDiplom(), "QWork's grade is less than excellent.");
    }

    @Test
    void calculateAverageGradeTest() {
        recordBook.addGrade("Physics", 1, DIFF_NAME, GOOD_MARK);
        recordBook.addGrade("History", 2, EXAM_NAME, SATISFACTORY_MARK);
        recordBook.addGrade("Math", 1, EXAM_NAME, EXCELLENT_MARK);

        // Проверка среднего балла
        assertEquals(4.0, recordBook.calculateAverageGrade());
    }

    @Test
    void canGetFreeEducationTest() {
        recordBook.addGrade("Math", 3, EXAM_NAME, GOOD_MARK);
        recordBook.addGrade("Physics", 3, DIFF_NAME, EXCELLENT_MARK);
        recordBook.addGrade("Chemistry", 2, DIFF_NAME, GOOD_MARK);
        recordBook.addGrade("Biology", 2, DIFF_NAME, EXCELLENT_MARK);

        assertTrue(recordBook.canGetFreeEducation(), "Нет оценок 2 и 3 — го на бюджет.");

        // Добавляем оценку "удовлетворительно" в последних двух семестрах
        recordBook.addGrade("History", 3, EXAM_NAME, SATISFACTORY_MARK);
        assertFalse(recordBook.canGetFreeEducation(), "Есть тройка за последние 2 семестра.");

        recordBook.setFreeEducation(true);
        assertFalse(recordBook.canGetFreeEducation());
    }

    @Test
    void canGetRedDiplomTest() {
        recordBook.setQualificationWorkGrade(5); // Отлично за квалификационную работу

        recordBook.addGrade("Math", 1, DIFF_NAME, EXCELLENT_MARK);
        recordBook.addGrade("Physics", 1, DIFF_NAME, EXCELLENT_MARK);
        recordBook.addGrade("History", 2, EXAM_NAME, EXCELLENT_MARK);
        recordBook.addGrade("Chemistry", 2, DIFF_NAME, GOOD_MARK);

        assertTrue(recordBook.canGetRedDiplom(), "25% 'хорошо' — можно красный диплом.");

        recordBook.addGrade("Language", 2, DIFF_NAME, GOOD_MARK);
        assertFalse(recordBook.canGetRedDiplom(), "40% 'хорошо' — нельзя красный диплом.");

        recordBook.addGrade("Subject1", 2, DIFF_NAME, EXCELLENT_MARK);
        recordBook.addGrade("Subject2", 2, DIFF_NAME, EXCELLENT_MARK);
        recordBook.addGrade("Subject3", 2, DIFF_NAME, EXCELLENT_MARK);

        // Сейчас снова 25% 'хорошо'
        // Добавляем оценку "удовлетворительно"
        recordBook.addGrade("Literature", 3, EXAM_NAME, SATISFACTORY_MARK);
        assertFalse(recordBook.canGetRedDiplom(), "Satisfactory grade — no Red Diplom");

        recordBook.addGrade("Subject4", 3, DIFF_NAME, GOOD_MARK);
        // >25% is good grades — cannot get Red Diplom
        assertFalse(recordBook.canGetRedDiplom());
    }

    @Test
    void canGetBigStipTest() {
        assertFalse(recordBook.canGetBigStip());

        recordBook.addGrade("Math", 3, EXAM_NAME, EXCELLENT_MARK);
        recordBook.addGrade("Physics", 3, EXAM_NAME, GOOD_MARK);
        recordBook.addGrade("Language", 4, EXAM_NAME, EXCELLENT_MARK);
        recordBook.addGrade("Language2", 4, EXAM_NAME, GOOD_MARK);
        recordBook.addGrade("History", 4, DIFF_NAME, GOOD_MARK);

        // Если EXCELLENT_PERCENTAGE_FOR_BIG_STIP <= 0.25
        assertTrue(recordBook.canGetBigStip(), "Only good grades — can get BIG stip.");

        recordBook.addZach("English", 3, true);

        // Добавляем оценку "удовлетворительно"
        recordBook.addGrade("Chemistry", 4, EXAM_NAME, SATISFACTORY_MARK);
        assertFalse(recordBook.canGetBigStip(), "Satisfactory grade — not BIG stip");

        recordBook.addZach("English", 4, false);
        assertFalse(recordBook.canGetBigStip());
    }
}
