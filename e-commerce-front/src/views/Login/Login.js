import { Box, Button, Stack, TextField, Typography, styled } from '@mui/material'
import React, {useRef, useState} from 'react'
import LockOpenIcon from '@mui/icons-material/LockOpen';
import { useTheme } from '@emotion/react';
import { ToastContainer} from 'react-toastify';
import useAuthService from '../../service/auth-service';



const LoginTextField = styled(TextField)(({ theme }) => ({
  '& .MuiOutlinedInput-root': {
      '& fieldset': {
        borderColor: 'gray', // Default border color
      },
      '&:hover fieldset': {
        borderColor: theme.palette.mode === 'light' ? theme.palette.myBlack.light : theme.palette.myBlack.dark, // Hover border color
      },
      '&.Mui-focused fieldset': {
        borderColor: theme.palette.mode === 'light' ? theme.palette.myBlack.light : theme.palette.myBlack.dark, // Focused border color
      },
    },
    '& .MuiInputLabel-root': {
      color: 'gray', // Default label color
    },
    '& .MuiInputLabel-root.Mui-focused': {
      color: theme.palette.mode === 'light' ? theme.palette.myBlack.light : theme.palette.myBlack.dark, // Focused label color
    },
}));

const Login = () => {
const {login} = useAuthService()
const myTheme = useTheme()
  const [mode] = useState(myTheme.palette.mode);
  const theColor= (mode) =>{
    return (mode === 'light' ? myTheme.palette.myBlack.light : myTheme.palette.myBlack.dark)
  }
const emailRef = useRef();
const passwordRef = useRef();
// const {setUser,setToken,setRefreshToken} = useAuthContext()

const submitHandler = (e) =>{
  e.preventDefault();

  const payload = {
    email: emailRef.current.value,
    password: passwordRef.current.value,
  }

  
  
  login(payload);

 

}

  return (
    <>
    <Box sx={{
        width:{xs:'70%',sm:'60%',md:'50%',lg:'30%'},
        marginX:{xs:'16%',sm:'27%',md:'30%',lg:'32%'},
        marginY:{xs:'8%',md:'6%'},
        borderRadius:'3px',
        padding:'3%',
        border:`0.3px solid ${theColor(mode)}`,
        textAlign:'center',
        
        }}
        component='form'
        onSubmit={submitHandler}
        >
        <LockOpenIcon sx={{color:`${theColor(mode)}`}} fontSize='large' />
        <Typography variant="h5" gutterBottom sx={{fontWeight:'bold',color:`${theColor(mode)}`}}>Login</Typography>
       
        <Stack spacing={3} p={2}>
           
            <LoginTextField
                inputRef={emailRef}
                label="E-mail"
              />
            <LoginTextField
                inputRef={passwordRef}
                label="Password"
            />
           
        </Stack>
        <Button type="submit" variant="contained"  sx={{
          width: '40%',
          marginTop: '8%',
          backgroundColor: `${theColor(mode)}`,
          '&:hover': {
            backgroundColor: `${theColor(mode)}`,
          }
        }}>Sing In</Button>
      </Box>
       <ToastContainer />
       </>
  )
}

export default Login
