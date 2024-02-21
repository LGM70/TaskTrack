import { useNavigate, useParams } from "react-router-dom"
import { useEffect, useRef, useState } from "react"
import TaskTable from "../taskTable/TaskTable"
import { Container, Row, Col, Button } from "react-bootstrap"
import TextChange from "../textChange/TextChange"
import api from "../../api/axiosConfig"
import Header from "../header/Header"
import SharingModal from "../sharingModal/SharingModal"

const ListPage = () => {
    const params = useParams()
    const user = params.name
    const listId = params.listId

    const navigate = useNavigate()

    const [list, setList] = useState()

    const getList = async () => {
        try {
            const response = await api.get(`/api/lists/${listId}?username=${user}`)
            setList(response.data)
        } catch (error) {
            console.log(error)
            navigate("/not-found")
        }
    }

    useEffect(() => {
        getList()
    }, [])

    const [descInput, setDescInput] = useState(0)
    const descText = useRef()

    const updateDesc = async () => {
        const desc = descText.current
        if (desc.value == "") {
            return
        }

        try {
            const response = await api.put(`/api/lists/${listId}/description?username=${user}`, { description: desc.value })
            setList(response.data)
        } catch (error) {
            console.log(error)
        }
    }

    const [titleInput, setTitleInput] = useState(0)
    const titleText = useRef()

    const updateTitle = async () => {
        const title = titleText.current
        if (title.value == "") {
            return
        }

        try {
            const response = await api.put(`/api/lists/${listId}/title?username=${user}`, { title: title.value })
            setList(response.data)
        } catch (error) {
            console.log(error)
        }
    }

    const deleteList = async () => {
        try {
            const response = await api.delete(`/api/lists/${listId}?username=${user}`)
            navigate(`/user/${user}`)
        } catch (error) {
            console.log(error)
            alert("You are not the owner of this list.")
        }
    }

    const [show, setShow] = useState(false)
    const refUser = useRef()

    const shareList = async () => {
        try {
            const response = await api.post(`/api/lists/${listId}/share?username=${user}&sharedUsername=${refUser.current.value}`)
        } catch (error) {
            console.log(error)
            alert("Sharing failed.")
        }
    }

    const revokeSharing = async () => {
        try {
            const response = await api.delete(`/api/lists/${listId}/share?username=${user}&sharedUsername=${refUser.current.value}`)
        } catch (error) {
            console.log(error)
            alert("Revoking failed.")
        }
    }

    return (
        <>
            <Header login={true} user={user} />
            <Container>
                <Row className="pt-2">
                    <Col>
                        <h1>
                            <TextChange
                                label=""
                                text={list?.title}
                                state={titleInput}
                                setState={setTitleInput}
                                refText={titleText}
                                handleSubmit={updateTitle}
                                size='md'
                            />
                        </h1>
                    </Col>
                    <Col xs={6} className="d-flex align-items-center justify-content-around">
                        <Button variant="info" size="md" onClick={() => setShow(true)}>
                            Share / Revoke Sharing
                        </Button>
                        <Button variant="danger" size="md" onClick={deleteList}>
                            Delete
                        </Button>
                    </Col>
                </Row>
                <SharingModal show={show} setShow={setShow} shareList={shareList} revokeSharing={revokeSharing} refUser={refUser}/>
                <Row className="pb-2">
                    <TextChange
                        label="Description: "
                        text={list?.description}
                        state={descInput}
                        setState={setDescInput}
                        refText={descText}
                        handleSubmit={updateDesc}
                        size='sm'
                    />
                </Row>
                <Row className="px-5 py-3">
                    <TaskTable user={user} list={list} />
                </Row>
            </Container>
        </>
    )
}

export default ListPage