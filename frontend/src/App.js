import './App.css'
import Layout from './components/Layout'
import { Route, Routes, useNavigate } from 'react-router-dom'
import Home from './components/home/Home'
import ListPage from './components/listPage/ListPage'
import NotFound from './components/notFound/NotFound'
import AllListsPage from './components/allListsPage/AllListsPage'

function App() {
    return (
        <div className="App">
            <Routes>
                <Route path='/' element={<Layout />}>
                    <Route path='/' element={<Home />} />
                    <Route path='/user/:name' element={<AllListsPage />} />
                    <Route path='/user/:name/:listId' element={<ListPage />} />
                    <Route path='/*' element={<NotFound />} />
                </Route>
            </Routes>
        </div>
    )
}

export default App
