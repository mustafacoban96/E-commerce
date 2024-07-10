import React from 'react'
import { useAuthContext } from '../../context/AuthContext';
import useAuthService from '../../service/auth-service';

const Logout = () => {
    const {refresh_token}= useAuthContext();
    const {logout} = useAuthService()
    const logoutHandler = () =>{
       

        const payload = {
            token:refresh_token
        }

        logout(payload);
        
    }
  return (
    <div>
        <p>{refresh_token}</p>
      <button type='submit' onClick={() => logoutHandler()}>Logout</button>
    </div>
  )
}

export default Logout
