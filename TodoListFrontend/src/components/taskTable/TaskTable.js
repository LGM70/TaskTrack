import { Form, ListGroup, Accordion, Row, Col } from "react-bootstrap"
import { useState, useRef, useEffect } from "react"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { faPlus, faMinus } from "@fortawesome/free-solid-svg-icons"
import TextChange from "../textChange/TextChange"
import api from "../../api/axiosConfig"

const TaskTable = ({ user, list }) => {
    const [tasks, setTasks] = useState()

    useEffect(() => {
        setTasks(list?.taskIds)
    }, [list])

    const [createState, SetCreateState] = useState(0)
    const bodyText = useRef()

    const createTask = async () => {
        const body = bodyText.current
        if (body.value == "") {
            return
        }

        try {
            const response = await api.post(`/api/tasks/${list.id}?username=${user}`, { taskBody: body.value })
            let newTasks = structuredClone(tasks)
            newTasks = [...newTasks, response.data]
            setTasks(newTasks)
        } catch (error) {
            console.log(error)
        }
    }

    const updateStatus = async (task) => {
        try {
            const response = await api.put(`/api/tasks/${list.id}?username=${user}&taskId=${task.id}&status=${!task.done}`)
            const newTasks = structuredClone(tasks)
            newTasks.forEach((t) => {
                if (t.id == task.id) {
                    t.done = !task.done
                }
            })
            setTasks(newTasks)
        } catch (error) {
            console.log(error)
        }
    }

    const deleteTask = async (task) => {
        try {
            const response = await api.delete(`/api/tasks/${list.id}?username=${user}&taskId=${task.id}`)
            const newTasks = []
            tasks.forEach((t) => {
                if (t.id != task.id) {
                    newTasks.push(t)
                }
            })
            setTasks(newTasks)
        } catch (error) {
            console.log(error)
        }
    }

    const incompleteTasks = []
    const completedTasks = []
    tasks?.forEach((task) => {
        (task.done ? completedTasks : incompleteTasks).push(
            <ListGroup.Item
                key={task.id}
                variant="light"
                className="d-flex align-items-center"
            >   
                <Col xs={2}>
                    <Form.Check className="px-1" onChange={() => updateStatus(task)} />
                </Col>
                <Col xs={8} className="d-flex flex-row">
                    {task.body}
                </Col>
                <Col xs={2}>
                    <FontAwesomeIcon icon={faMinus} className="text-danger" onClick={() => deleteTask(task)} />
                </Col>
            </ListGroup.Item>
        )
    })

    // add a new list
    incompleteTasks.push(
        <ListGroup.Item
            key={0}
            variant="light"
            className="d-flex align-items-center"
        >
            <FontAwesomeIcon icon={faPlus} className="py-1 px-1" />
            <TextChange
                label=""
                text="Add a new task"
                state={createState}
                setState={SetCreateState}
                refText={bodyText}
                handleSubmit={createTask}
                size='sm'
            />
        </ListGroup.Item>
    )

    return (
        <Accordion defaultActiveKey="0" alwaysOpen>
            <Accordion.Item eventKey="0">
                <Accordion.Header>Tasks</Accordion.Header>
                <Accordion.Body className="p-0">
                    <ListGroup className="rounded-0">
                        {incompleteTasks}
                    </ListGroup>
                </Accordion.Body>
            </Accordion.Item>
            <Accordion.Item eventKey="1">
                <Accordion.Header>Completed</Accordion.Header>
                <Accordion.Body className="p-0">
                    <ListGroup className="rounded-0">
                        {completedTasks}
                    </ListGroup>
                </Accordion.Body>
            </Accordion.Item>
        </Accordion>

    )
}

export default TaskTable