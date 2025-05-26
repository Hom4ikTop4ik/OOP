tasks {
    task id: "Task_2_2_1",
            title: "Пиццерия",
            maxScore: 10,
            softDeadline: "2025-05-28",
            hardDeadline: "2025-06-01"

    task id: "Task_2_3_1",
            title: "Змейка",
            maxScore: 15,
            softDeadline: "2025-06-05",
            hardDeadline: "2025-06-10"
}

groups {
    group name: "23217", {
        student github: "Belov",
                name: "Белов Данил",
                repo: "https://github.com/bd986650/OOP"

        student github: "Martynov",
                name: "БДД",
                repo: "https://github.com/Hom4ikTop4ik/OOP"
    }

    group name: "23215", {
        student github: "Julia",
                name: "Юлия Вязникова",
                repo: "https://github.com/YuliaVyaznikova/OOP"
    }
}

assignments {
    assign github: "Martynov",  task: "Task_1_4_1"
    assign github: "Martynov",  task: "Task_2_1_1"
    assign github: "Belov",  task: "Task_2_2_1"
    assign github: "Belov",  task: "Task_2_3_1"
    assign github: "Julia",  task: "Task_2_1_1"
    assign github: "Julia",  task: "Task_2_3_1"
}

checkpoints {
    checkpoint id: "cp1",
            name: "Промежуточный контроль",
            date: "2025-06-10",
            tasks: ["Task_2_2_1", "Task_2_3_1"]

    checkpoint id: "cp2",
            name: "Финальный контроль",
            date: "2025-06-20",
            tasks: ["Task_2_2_1", "Task_2_3_1"]
}

settings {
    courseName          "Объектно-ориентированное программирование"
    latePenaltyPerDay   0.1
    autoCheckEnabled    true
    reviewTimeoutDays   3
}
