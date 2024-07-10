import { Box, Button, Stack, TextField, Typography, styled } from '@mui/material'
import React, {useRef} from 'react'
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
      borderColor: theme.palette.commerceOrange.main, // Hover border color
    },
    '&.Mui-focused fieldset': {
      borderColor: theme.palette.commerceOrange.main, // Focused border color
    },
  },
  '& .MuiInputLabel-root': {
    color: 'gray', // Default label color
  },
  '& .MuiInputLabel-root.Mui-focused': {
    color: theme.palette.commerceOrange.main, // Focused label color
  },
}));

const Login = () => {
const {login} = useAuthService()
const theme = useTheme();
// const navigate = useNavigate();
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
        border:`2px solid ${theme.palette.commerceOrange.main}`,
        textAlign:'center',
        
        }}
        component='form'
        onSubmit={submitHandler}
        >
        <LockOpenIcon color='commerceOrange' fontSize='large' />
        <Typography variant="h5" gutterBottom sx={{fontWeight:'bold'}}>Login</Typography>
       
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
          backgroundColor: theme.palette.commerceOrange.main,
          '&:hover': {
            backgroundColor: theme.palette.commerceOrange.dark,
          }
        }}>Sing In</Button>
      </Box>
       <ToastContainer />
       </>
  )
}

export default Login
