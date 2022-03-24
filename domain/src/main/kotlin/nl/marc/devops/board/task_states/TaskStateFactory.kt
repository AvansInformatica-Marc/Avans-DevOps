package nl.marc.devops.board.task_states

import nl.marc.devops.board.Task

class TaskStateFactory(private val task: Task) {
    val plannedTask = PlannedTaskState(task)

    val taskInDevelopment = InDevelopmentTaskState(task)

    val taskReadyForTesting = ReadyForTestingTaskState(task)

    val testingInProgressTask = TestingInProgressTaskState(task)

    val testedTask = TestingCompleteTaskState(task)

    val completedTask = CompletedTaskState(task)
}
