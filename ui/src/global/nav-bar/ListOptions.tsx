import {
    Box,
    List,
    ListItem,
    ListItemButton,
    ListItemIcon,
    ListItemText,
    Divider,
    IconButton,
    BoxProps
} from "@mui/material"
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import {  HowToReg, Login } from "@mui/icons-material";
import ListIcon from '@mui/icons-material/List';
import Logout from "@mui/icons-material/Logout";
import React from "react";
import {useAuth} from "../../context/AuthContext";
import {useNotification} from "../../context/NotificationProvider";
import AccountCircleIcon from '@mui/icons-material/AccountCircle';

interface ListOptionsProps extends BoxProps {
    setOpen: React.Dispatch<React.SetStateAction<boolean>>
}

// List of options in the navigation bar
const ListOptions: React.FunctionComponent<ListOptionsProps> = ({setOpen, ...rest} : ListOptionsProps) => {

    const {isAuthenticated, logout} = useAuth();
    const { showNotification } = useNotification();

    return (
        <Box
            width={250}
            role="presentation"
            {...rest}
        >
            <Box>
                <IconButton size="large" onClick={() => setOpen(false)}>
                    <ChevronRightIcon/>
                </IconButton>
            </Box>
            <Divider />
            <List>
                <ListItem disablePadding>
                    <ListItemButton disabled={!isAuthenticated} href="/profile">
                        <ListItemIcon>
                            <AccountCircleIcon />
                        </ListItemIcon>
                        <ListItemText primary={'Account'} />
                    </ListItemButton>
                </ListItem>
                <ListItem disablePadding>
                    <ListItemButton disabled={!isAuthenticated} href="/activity">
                        <ListItemIcon>
                            <ListIcon />
                        </ListItemIcon>
                        <ListItemText primary={'Activity'} />
                    </ListItemButton>
                </ListItem>
                <ListItem disablePadding>
                    <ListItemButton disabled={isAuthenticated} href="log-in">
                        <ListItemIcon>
                            <Login />
                        </ListItemIcon>
                        <ListItemText primary={'Sign In'} />
                    </ListItemButton>
                </ListItem>
                <ListItem disablePadding>
                    <ListItemButton disabled={isAuthenticated} href="register">
                        <ListItemIcon>
                            <HowToReg />
                        </ListItemIcon>
                        <ListItemText primary={'Register'} />
                    </ListItemButton>
                </ListItem>
                <ListItem disablePadding>
                    <ListItemButton onClick={(e) => {
                        // e.preventDefault();
                        logout();
                        //  Show message
                        showNotification("Successfully loged out!", "success");
                    }} disabled={!isAuthenticated}>
                        <ListItemIcon>
                            <Logout />
                        </ListItemIcon>
                        <ListItemText primary={'Log Out'} />
                    </ListItemButton>
                </ListItem>
            </List>
        </Box>
    )
}

export default ListOptions;