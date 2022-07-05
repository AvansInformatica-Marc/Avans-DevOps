package nl.marc.devops.board.task_states

class BacklogItemStateFactory {
    val plannedTask: BacklogItemState
        get() = PlannedBacklogItemState(this)

    val taskInDevelopment: BacklogItemState
        get() = InDevelopmentBacklogItemState(this)

    val taskReadyForTesting: BacklogItemState
        get() = ReadyForTestingBacklogItemState(this)

    val testingInProgressTask: BacklogItemState
        get() = TestingInProgressBacklogItemState(this)

    val testedTask: BacklogItemState
        get() = TestingCompleteBacklogItemState(this)

    val completedTask: BacklogItemState
        get() = CompletedBacklogItemState()
}
