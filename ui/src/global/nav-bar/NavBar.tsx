import { useState } from "react";
// import Logo from "../../components/Logo";
import ListOptions from "./ListOptions";
import {useTheme} from "@mui/material";
import MenuIcon from '@mui/icons-material/Menu';
import Logo from "../../component/Logo";

const { Toolbar, Box, IconButton, AppBar, Drawer, useMediaQuery } = require("@mui/material")

// Navigation Bar components
const NavBar = () => {
    // Check if drawer needs to be openned
    const [drawerOpen, setDrawerOpen] = useState(false);
    // Query media from MUI
    const theme = useTheme();
    const matches = useMediaQuery(theme.breakpoints.up('md'));
    return (
        <AppBar
            position="fixed"
        >
            <Toolbar>
                <Box display={{ xs: 'flex' }} width='100%' flexDirection={'row'} justifyContent={'space-between'} alignItems={'center'} marginX={'auto'}>
                    <Logo sx={{ml: matches ? 4 : 0}} title={"Devkind Assessment"}/>
                    <Box ml={'auto'} display={'block'}>
                        {/* Menu Icon for opening the drawer */}
                        <IconButton color="inherit" size="large" onClick={() => setDrawerOpen(true)} >
                            <MenuIcon />
                        </IconButton>
                    </Box>

                    <Drawer
                        anchor={'right'}
                        open={drawerOpen}
                    >
                        <ListOptions setOpen={setDrawerOpen} />
                    </Drawer>
                </Box>

            </Toolbar>
        </AppBar>
    )
}

export default NavBar;