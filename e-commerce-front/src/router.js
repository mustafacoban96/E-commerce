import { Navigate, createBrowserRouter } from "react-router-dom";
import DefaultLayout from "./components/DefaultLayout/DefaultLayout"
import GuestLayout from './components/GuestLayout/GuestLayout'
import Dashboard from './views/DashBoard/Dashboard'
import Home from "./views/Home/Home";
import Login from "./views/Login/Login";
import NotFound from './views/NotFound/NotFound'
import Register from "./views/Register/Register";



const router = createBrowserRouter([
    {
        path:'/',
        element: <DefaultLayout/>,
        children:[
            {
                path:'/',
                element:<Navigate to='/home'/>
            },
            {
                path:'/dashboard',
                element:<Dashboard/>
            },
            {
                path:'home',
                element:<Home/>
            }
        ]
    },
    {
        path:'/',
        element:<GuestLayout/>,
        children:[
            {
                path:'login',
                element:<Login/>
            },
            {
                path:'register',
                element:<Register/>
            }
        ]
    },
    {
        path:'*',
        element:<NotFound/>
    }
])

export default router