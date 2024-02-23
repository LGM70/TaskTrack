import { Button, Form, Modal } from 'react-bootstrap'

const SharingModal = ({ show, setShow, shareList, revokeSharing, refUser }) => {
    return (
        <Modal show={show} onHide={() => setShow(false)}>
            <Modal.Header closeButton>
                <Modal.Title>Share this list</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <Form.Group>
                        <Form.Label>User name</Form.Label>
                        <Form.Control ref={refUser}/>
                    </Form.Group>
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <Button variant='outline-primary' onClick={() => {shareList(); setShow(false)}}>
                    Share
                </Button>
                <Button variant='outline-primary' onClick={() => {revokeSharing(); setShow(false)}}>
                    Revoke
                </Button>
            </Modal.Footer>
        </Modal>
    )
}

export default SharingModal