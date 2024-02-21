import React from 'react'
import Header from '../header/Header'
import { useNavigate, useParams } from 'react-router-dom'
import { useState, useEffect } from 'react'
import api from '../../api/axiosConfig'
import ListTable from '../listTable/ListTable'
import { Container, Row } from 'react-bootstrap'

const AllListsPage = () => {
    const params = useParams()
    const user = params.name

    const navigate = useNavigate()

    const [ownedLists, setOwnedLists] = useState()
    const [sharedLists, setSharedLists] = useState();

    const getLists = async () => {
        try {
            const response = await api.get(`/api/lists?username=${user}`)
            setOwnedLists(response.data[0])
            setSharedLists(response.data[1])
        } catch (error) {
            console.log(error)
            navigate("/not-found")
        }
    }

    useEffect(() => {
        getLists()
    }, [])

    return (
        <>
            <Header login={true} user={user}/>
            <Container>
                <Row className='py-3'>
                    <h3>Owned Lists</h3>
                </Row>
                <Row>
                    <ListTable user={user} lists={ownedLists} setLists={setOwnedLists} sharing={false}/>
                </Row>
                <Row className='py-3'>
                    <h3>Lists shared by others</h3>
                </Row>
                <Row>
                    <ListTable user={user} lists={sharedLists} setLists={setSharedLists} sharing={true}/>
                </Row>
            </Container>
            
        </>
    )
}

export default AllListsPage