import { Nav, Navbar, Container, Button } from "react-bootstrap"
import { NavLink, useNavigate } from "react-router-dom"
import api from "../../api/axiosConfig"

const Header = ({ login, user, setShow }) => {
    const navigate = useNavigate()

    return (
        <Navbar bg="primary" data-bs-theme="dark" expand="sm" sticky="top">
            <Container fluid>
                <Navbar.Brand className="ms-2">
                    TaskTrack
                </Navbar.Brand>
                <Navbar.Toggle aria-controls="collapse-navbar" />
                <Navbar.Collapse id="collapse-navbar">
                    <Nav className="me-auto">
                        <NavLink className="nav-link" to={login ? `/user/${user}` : "/"}>Lists</NavLink>
                    </Nav>
                    {login ?
                        <Nav>
                            <Button variant="primary" className="mx-1" onClick={() => {
                                localStorage.clear()
                                api.defaults.headers.common["token"] = ""
                                navigate("/")
                            }}>
                                Logout
                            </Button>
                            <Button disabled variant="primary" className="mx-1">
                                {user}
                            </Button>
                        </Nav>
                        : <Nav>
                            <Button variant="primary" className="mx-1" onClick={() => setShow(true)}>
                                Sign in
                            </Button>
                            <Button variant="primary" className="mx-1" onClick={() => setShow(true)}>
                                Sign up
                            </Button>
                        </Nav>}
                </Navbar.Collapse>
            </Container>
        </Navbar>

    )
}

export default Header