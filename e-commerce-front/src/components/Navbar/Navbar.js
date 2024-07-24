import { useTheme } from '@emotion/react'
import { Badge, Box, Divider, Drawer, Fade, IconButton, Menu, MenuItem, Stack, Typography } from '@mui/material'
import React, { useState } from 'react'
import { Link } from 'react-router-dom'
import ShoppingCartOutlinedIcon from '@mui/icons-material/ShoppingCartOutlined';
import PersonOutlineOutlinedIcon from '@mui/icons-material/PersonOutlineOutlined';
import MenuIcon from '@mui/icons-material/Menu';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import InboxIcon from '@mui/icons-material/MoveToInbox';
import MailIcon from '@mui/icons-material/Mail';
import HomeIcon from '@mui/icons-material/Home';
import CategoryIcon from '@mui/icons-material/Category';
import useAuthService from '../../service/auth-service';
import { useAuthContext } from '../../context/AuthContext';


const Navbar = () => {
  const theme = useTheme();
  const [mode] = useState(theme.palette.mode);
  console.log('navbar');




   const myMode= (mode) =>{
    return (mode === 'light' ? theme.palette.myBlack.light : theme.palette.myBlack.dark)
  }

  //Drawer
  const [open,setOpen] = useState(false);

  const toggleDrawer = (newOpen) => (event) => {
    if (event.type === 'keydown' && (event.key === 'Tab' || event.key === 'Shift')) {
      return;
    }
    setOpen(newOpen);
  };

  const DrawerList = (
    
    <Box sx={{ width: 250 }} role="presentation" onClick={toggleDrawer(false)}>
      
      <List>
      <Link to='/home' style={{textDecoration:'none',color:`${myMode(mode)}`}}>
        <ListItem disablePadding>
            <ListItemButton>
              <ListItemIcon>
                <HomeIcon />
              </ListItemIcon>
              Home
            </ListItemButton>
          </ListItem>
          </Link>
          <Link to='/products' style={{textDecoration:'none',color:`${myMode(mode)}`}}>
        <ListItem disablePadding>
            <ListItemButton>
              <ListItemIcon>
                <CategoryIcon />
              </ListItemIcon>
              Porducts
            </ListItemButton>
          </ListItem>
          </Link>
      </List>
      <Divider />
      <List>
        {['All mail', 'Trash', 'Spam'].map((text, index) => (
          <ListItem key={text} disablePadding>
            <ListItemButton>
              <ListItemIcon>
                {index % 2 === 0 ? <InboxIcon /> : <MailIcon />}
              </ListItemIcon>
              <ListItemText primary={text} />
            </ListItemButton>
          </ListItem>
        ))}
      </List>
    </Box>
  );

  //Fade Menu (account menu)
  const [anchorEl, setAnchorEl] = useState(null);
  const openAccountMenu = Boolean(anchorEl);
  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  //////////////////////////logout////////////////////////////////////////
  const {refresh_token}= useAuthContext();
    const {logout} = useAuthService()
    const logoutHandler = () =>{
       
        handleClose()
        const payload = {
              refresh_token
        }

        logout(payload);
        
    }
/////////////////////////////////////////////////////////////////////////////////
  return (
    <>
    <Box sx={{
      width:'100%',
      padding:'15px',
      boxShadow:'0px 0px 3px black',
      display:'flex',
      justifyContent:'space-between',
      alignItems:'center'
    }}>
      <IconButton onClick={toggleDrawer(true)} sx={{color:`${myMode(mode)}`}}>
        <MenuIcon />
      </IconButton>
      <Drawer open={open} onClose={toggleDrawer(false)}>
        {DrawerList}
      </Drawer>
     
      <Typography variant='p'  sx={{fontWeight:'bolder',fontSize:{xs:'1em',sm:'1.2em',md:'1.4em'},}}>
       
        <Link to='/home' 
          style={{
            textDecoration:'none',
            color:`${myMode(mode)}`,
          }}
          > E-commerce site</Link>
        </Typography>
      <Stack 
        direction="row"
        divider={<Divider orientation="vertical" flexItem />}
        spacing={1.5}
        alignItems='center'
        >
         <Link to='/cart'>
          <IconButton>
            <Badge badgeContent={2} color='error'>
              <ShoppingCartOutlinedIcon style={{color:`${myMode(mode)}`}}/>
            </Badge>
            </IconButton>
          </Link>
          <IconButton 
          style={{color:`${myMode(mode)}`}}
          id="fade-button"
          aria-controls={openAccountMenu ? 'fade-menu' : undefined}
          aria-haspopup="true"
          aria-expanded={openAccountMenu ? 'true' : undefined}
          onClick={handleClick}
          >
              <PersonOutlineOutlinedIcon/>
          </IconButton>
          
      </Stack>
      <Menu
        id="fade-menu"
        MenuListProps={{
          'aria-labelledby': 'fade-button',
        }}
        anchorEl={anchorEl}
        open={openAccountMenu}
        onClose={handleClose}
        TransitionComponent={Fade}
      >
        <MenuItem onClick={handleClose}>Profile</MenuItem>
        <MenuItem onClick={handleClose}>Orders</MenuItem>
        <MenuItem onClick={logoutHandler}>Logout</MenuItem>
      </Menu>
    </Box>
    </>
  )
}

export default Navbar
