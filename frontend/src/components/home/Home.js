import { Container, Row } from "react-bootstrap"
import Header from "../header/Header"
import { useRef, useState } from "react"
import LoginModal from "../loginModal/LoginModal"
import { useNavigate } from "react-router-dom"
import api from "../../api/axiosConfig"

const Home = () => {
    const [show, setShow] = useState(false)
    const refName = useRef()
    const refPass = useRef()
    const navigate = useNavigate()

    const login = async () => {
        try {
            let username = refName.current.value
            const response = await api.get(`/api/users?username=${username}&password=${refPass.current.value}`)
            localStorage.setItem("token", response.data)
            api.defaults.headers.common["token"] = response.data
            navigate(`/user/${username}`)
        } catch (error) {
            console.log(error)
            alert("Wrong username or password.")
        }
    }

    const register = async () => {
        try {
            const response = await api.post(`/api/users?username=${refName.current.value}&password=${refPass.current.value}`)
            alert("Now please sign in.")
        } catch (error) {
            console.log(error)
            alert("Please use another user name.")
        }
    }

    return (
        <>
            <Header login={false} setShow={setShow}/>
            <Container>
                <Row className="my-3">
                    <h1>Welcome to To-Do!</h1>
                </Row>
                <Row>
                    <center>Sign in or sign up to enjoy the service.</center>
                </Row>
            </Container>
            <LoginModal show={show} setShow={setShow} login={login} register={register} refName={refName} refPass={refPass}/>
        </>
    )
}

export default Home