package nl.marc.devops.board.task_states

import nl.marc.devops.board.Task

class TaskStateFactory(private val task: Task) {
    val plannedTask: TaskState = PlannedTaskState(task)

    val taskInDevelopment: TaskState = InDevelopmentTaskState(task)

    val taskReadyForTesting: TaskState = ReadyForTestingTaskState(task)

    val testingInProgressTask: TaskState = TestingInProgressTaskState(task)

    val testedTask: TaskState = TestingCompleteTaskState(task)

    val completedTask: TaskState = CompletedTaskState(task)
}
