import { Container, Row } from 'react-bootstrap'
import Header from '../header/Header'
import { useNavigate } from 'react-router-dom'
import { useEffect } from 'react'

const NotFound = () => {
    const navigate = useNavigate()

    useEffect(() => {
        setTimeout(() => navigate("/"), 3000)
    }, [])

    return (
        <>
            <Header login={false} setShow={() => {}}/>
            <Container>
                <Row className='my-2'>
                    <h1>Oops... this page could not be found</h1>
                </Row>

                <Row>
                    <h5>Will redirect to home page soon...</h5>
                </Row>
            </Container>
        </>
    )
}

export default NotFound