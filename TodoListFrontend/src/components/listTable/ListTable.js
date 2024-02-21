import { ListGroup, Badge } from "react-bootstrap"
import { useNavigate } from "react-router-dom"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { faPlusCircle } from "@fortawesome/free-solid-svg-icons"
import TextChange from "../textChange/TextChange"
import { useRef, useState } from "react"
import api from "../../api/axiosConfig"

const ListTable = ({ user, lists, setLists, sharing }) => {
    const navigate = useNavigate()
    const [createState, SetCreateState] = useState(0)
    const titleText = useRef()

    const createList = async () => {
        const title = titleText.current
        if (title.value == "") {
            return
        }

        try {
            const response = await api.post(`/api/lists?username=${user}`, { title: title.value, description: "" })
            const updatedLists = [...lists, response.data]
            setLists(updatedLists)
        } catch (error) {
            console.log(error)
        }
    }

    const rows = []
    lists?.forEach((list) => {
        rows.push(
            <ListGroup.Item
                action variant="primary"
                onClick={() => { navigate(`/user/${user}/${list.id}`) }}
                className="d-flex justify-content-between align-items-center"
                key={list.title}
            >
                <strong>{list.title}</strong>
                <Badge pill bg="primary">{list.taskIds ? list.taskIds.length : 0}</Badge>
            </ListGroup.Item>
        )
    })

    // add a new list
    if (!sharing) {
        rows.push(
            <ListGroup.Item
                action variant="primary"
                className="d-flex align-items-center"
                key=""
            >
                <FontAwesomeIcon icon={faPlusCircle} className="py-1 px-1" />
                <TextChange
                    label=""
                    text="Create a new list"
                    state={createState}
                    setState={SetCreateState}
                    refText={titleText}
                    handleSubmit={createList}
                    size='sm'
                />
            </ListGroup.Item>
        )
    }


    return (
        <ListGroup variant="flush" className="rounded px-5">
            {rows}
        </ListGroup>
    )
}

export default ListTable