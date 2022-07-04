package nl.marc.devops.board.task_states

import nl.marc.devops.board.BacklogItem

class TaskStateFactory(private val backlogItem: BacklogItem) {
    val plannedTask: BacklogItemState = PlannedBacklogItemState(backlogItem)

    val taskInDevelopment: BacklogItemState = InDevelopmentBacklogItemState(backlogItem)

    val taskReadyForTesting: BacklogItemState = ReadyForTestingBacklogItemState(backlogItem)

    val testingInProgressTask: BacklogItemState = TestingInProgressBacklogItemState(backlogItem)

    val testedTask: BacklogItemState = TestingCompleteBacklogItemState(backlogItem)

    val completedTask: BacklogItemState = CompletedBacklogItemState(backlogItem)
}
