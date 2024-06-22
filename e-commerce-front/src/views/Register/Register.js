import { Box, Button, Stack, TextField, Typography, styled } from '@mui/material'
import React, {useRef} from 'react'
import LockOpenIcon from '@mui/icons-material/LockOpen';
import axios from 'axios';
import { useNavigate } from 'react-router';
import { useTheme } from '@emotion/react';
import { ToastContainer, toast } from 'react-toastify';

const RegisterTextField = styled(TextField)(({ theme }) => ({
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

const Register = () => {
   
  //navigate login after register
  const navigate = useNavigate()

  const usernameRef = useRef();
  const emailRef = useRef();
  const passwordRef = useRef();
  const confirmPasswordRef = useRef();

  const submitHandler = (e) =>{
    e.preventDefault();

    const payload = {
      username: usernameRef.current.value,
      email: emailRef.current.value,
      password: passwordRef.current.value,
      confirm_password: confirmPasswordRef.current.value,
      authorities:["ROLE_USER"]
    }

    axios
    .post(`${process.env.REACT_APP_AUTH_BASE_URL}/register`, payload)
    .then((response) => {
        //console.log(response)
        let message = response.data;
        toast.success(message+" You are directed to login page...", {
            autoClose: 2000,
            position:'top-right',
            });
      setTimeout(() =>{
        navigate('/login')
      },3000)
    })
    .catch((err) =>{
        let res = err.response.data
        let message = res.message
        // console.log('bb:',response)
        // error i handled via except class in spring boot.
        if(message){
            toast.error(message, {
                autoClose: 3000,
                position:'top-right',
                
                });
        }
        else{
            toast.error(res.errors[0], {
                autoClose: 3000,
                position:'top-right',
                
                });
        }
       
    
    });

  }

  const theme = useTheme();

  return (
    <>
    <Box sx={{
        width:{xs:'70%',sm:'60%',md:'50%',lg:'35%'},
        marginX:{xs:'16%',sm:'27%',md:'30%',lg:'32%'},
        marginY:{xs:'8%',md:'6%'},
        borderRadius:'3px',
        padding:'12px',
        border:`2px solid ${theme.palette.commerceOrange.main}`,
        textAlign:'center',
        
        }}
        component='form'
        onSubmit={submitHandler}
        >
        <LockOpenIcon color='commerceOrange' fontSize='large' />
        <Typography variant="h5" gutterBottom sx={{fontWeight:'bold'}}>Register</Typography>
       
        <Stack spacing={3} p={2}>
            <RegisterTextField
                inputRef={usernameRef}
                label="Username"
              />
            <RegisterTextField
                inputRef={emailRef}
                label="E-mail"
              />
            <RegisterTextField
                inputRef={passwordRef}
                label="Password"
            />
            <RegisterTextField
                inputRef={confirmPasswordRef}
                label="Confirm Password"
            />
        </Stack>
        <Button type="submit" variant="contained"  sx={{
          width: '40%',
          marginTop: '8%',
          backgroundColor: theme.palette.commerceOrange.main,
          '&:hover': {
            backgroundColor: theme.palette.commerceOrange.dark,
          }
        }}>Sing Up</Button>
      </Box>
       <ToastContainer />
       </>
  )
}

export default Register
