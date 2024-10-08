import { useNavigate } from 'react-router';
import { toast } from 'react-toastify';
import { useAuthContext } from '../context/AuthContext';
//import useApi from './api';
//import axios from 'axios';
import axiosConfig from './api/newApi';

const useAuthService = () => {
    const navigate = useNavigate();
    const { setUser, setToken, setRefreshToken} = useAuthContext();
   // const api = useApi();

    const register = (register_payload) => {
        return axiosConfig.post("/auth/register", register_payload)
            .then((response) => {
                let message = response.data;
                toast.success(message + " You are directed to login page...", {
                    autoClose: 2000,
                    position: 'top-right',
                });
                setTimeout(() => {
                    navigate('/login');
                }, 3000);
            })
            .catch((err) => {
                if(err.response.data.errors){
                    toast.error(...err.response.data.errors, {
                        autoClose: 2000,
                        position: 'top-right',
                    });
                }
                else{
                    toast.error(err.response.data.message, {
                        autoClose: 2000,
                        position: 'top-right',
                    });
                }
                
            });
    };

    const login = (loginPayload) => {
        return axiosConfig.post("/auth/login", loginPayload)
            .then((response) => {
                console.log('login-data:', response.data);
                let data = response.data;
                let user = data.user_response;
                let token = data.access_token;
                let refreshToken = data.refresh_token;
                
                toast.success('Login is successful',{
                        autoClose: 2000,
                        position: 'top-right',
                })
                setTimeout(() =>{
                    setUser(user);
                    setRefreshToken(refreshToken);
                    setToken(token);
                },2200)
            })
            .catch((err) => {
                console.log('myerrr:',err);
                let statusCode = err.response.status;
                if (statusCode === 401) {
                    toast.error("E-mail or Password is incorrect", {
                        autoClose: 3000,
                        position: 'top-right',
                    });
                }
                if(statusCode === 404){
                    toast.error("Invalid email", {
                        autoClose: 3000,
                        position: 'top-right',
                    });
                }
            });
    };

    const logout = (logout_payload) => {
        return axiosConfig.post('/auth/logout', logout_payload)
            .then((response) => {
                console.log('qwer')
                let message = response.data;
                toast.success(message + " You are directed to login page...", {
                    autoClose: 2000,
                    position: 'top-right',
                });
                localStorage.removeItem('ACCESS_TOKEN');
                localStorage.removeItem('REFRESH_TOKEN');
                localStorage.removeItem('USER');
                setTimeout(() => {
                    window.location.reload();
                }, 2200);
                
            }).catch((err) => {
                //window.localStorage.href = '/login';
                console.log('ct:', err);
            });
    };

    return {
        register,
        login,
        logout
    };
};

export default useAuthService;
