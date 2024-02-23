import { Button, Form, Modal } from 'react-bootstrap'

const LoginModal = ({ show, setShow, login, register, refName, refPass }) => {
    return (
        <Modal show={show} onHide={() => setShow(false)}>
            <Modal.Header closeButton>
                <Modal.Title> Sign in / Sign up</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <Form.Group>
                        <Form.Label>User name</Form.Label>
                        <Form.Control ref={refName}/>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Password</Form.Label>
                        <Form.Control ref={refPass}/>
                    </Form.Group>
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <Button variant='outline-primary' onClick={() => {login(); setShow(false)}}>
                    Sign in
                </Button>
                <Button variant='outline-primary' onClick={() => {register(); setShow(false)}}>
                    Sign up
                </Button>
            </Modal.Footer>
        </Modal>
    )
}

export default LoginModal