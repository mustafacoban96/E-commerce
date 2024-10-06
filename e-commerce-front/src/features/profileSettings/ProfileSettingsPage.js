import { Alert, Avatar, Box, Button, CircularProgress, Paper, Stack, TextField, Typography } from '@mui/material';
import React, { useState } from 'react';
import { useAuthContext } from '../../context/AuthContext';
import SendIcon from '@mui/icons-material/Send';
import { useDispatch, useSelector } from 'react-redux';
import { getError, getIsLoading, updateProfileInfo } from './profileSlice';

const ProfileSettingsPage = () => {
  const { user } = useAuthContext();
  const dispatch = useDispatch();
  const error = useSelector(getError);
  const isLoading = useSelector(getIsLoading);

  // Form state initialization
  const [formData, setFormData] = useState({
    username: user.username,
    email: user.email,
    new_password: '',
    confirm_new_password: ''
  });

  // Update form data
  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.id]: e.target.value });
  };

  const handleUpdateFields = () => {
    const user_info = {
      ...(formData.username && { username: formData.username }),
      ...(formData.email && { email: formData.email }),
      ...(formData.new_password && { new_password: formData.new_password }),
      ...(formData.confirm_new_password && { confirm_new_password: formData.confirm_new_password })
    };

    const user_id = user.user_id;
    dispatch(updateProfileInfo({ user_id, user_info}));
    console.log(dispatch(updateProfileInfo({ user_id, user_info})))
    console.log("Updated User:", user_info);
  };

  return (
    <>
      <Box sx={{ p: '5px' }}>
        <Typography sx={{ fontSize: { xs: '1.4em', md: '1.6em' }, fontWeight: 'bold', marginLeft: '10px' }}>
          User Information
        </Typography>
      </Box>

      {error && (
        <Box key={'error'} sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
          <Alert severity="error">{error}</Alert>
        </Box>
      )}

      <Box sx={{ display: 'flex', alignItems: { xs: 'center', md: 'initial' }, flexDirection: { xs: 'column', md: 'row' }, gap: '1em', p: 2 }}>
        <Paper sx={{ width: { xs: '100%', sm: '85%', md: '20%' }, display: 'flex', flexDirection: 'column', p: 3, gap: '20px', alignItems: 'center', height: '25%' }} elevation={4}>
          <Avatar sx={{ width: 56, height: 56 }} src="https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png" />
          <Typography>{user.username}</Typography>
          <Typography>{user.email}</Typography>
          <Typography>Gaziantep</Typography>
        </Paper>

        {isLoading ? (
          <Box key={'loading'} sx={{ display: 'flex', justifyContent: 'center', width: { xs: '100%', sm: '85%', md: '60%' }, height: '100vh', alignItems: 'center' }}>
            <CircularProgress color="success" />
          </Box>
        ) : (
          <Paper elevation={6} sx={{ backgroundColor: 'white', width: { xs: '100%', sm: '85%', md: '60%' }, p: 1 }} component={'form'}>
            <Stack direction={'column'} sx={{ p: 2 }} spacing={1}>
              <Typography sx={{ fontWeight: 'bold' }}>Username</Typography>
              <TextField id="username" value={formData.username} onChange={handleChange} />
            </Stack>

            <Stack direction={'column'} sx={{ p: 2 }} spacing={1}>
              <Typography sx={{ fontWeight: 'bold' }}>Email</Typography>
              <TextField id="email" value={formData.email} onChange={handleChange} />
            </Stack>

            <Stack direction={'column'} sx={{ p: 2 }} spacing={1}>
              <Typography sx={{ fontWeight: 'bold' }}>New Password</Typography>
              <TextField id="new_password" value={formData.new_password} onChange={handleChange} type="password" />
            </Stack>

            <Stack direction={'column'} sx={{ p: 2 }} spacing={1}>
              <Typography sx={{ fontWeight: 'bold' }}>Confirm New Password</Typography>
              <TextField id="confirm_new_password" value={formData.confirm_new_password} onChange={handleChange} type="password" />
            </Stack>

            <Box sx={{ display: 'flex', p: 1, justifyContent: 'end' }}>
              <Button onClick={handleUpdateFields} variant="contained" color="success" endIcon={<SendIcon />}>
                Update
              </Button>
            </Box>
          </Paper>
        )}
      </Box>
    </>
  );
};

export default ProfileSettingsPage;
