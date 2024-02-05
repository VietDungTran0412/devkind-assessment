import {Box, BoxProps, Typography} from "@mui/material";
import AssessmentIcon from '@mui/icons-material/Assessment';
import { useNavigate } from "react-router-dom";
import React from "react";

interface LogoProps extends BoxProps {
    title: string
}

// Logo for the website
const Logo:React.FunctionComponent<LogoProps> = ({ title, sx, ...props }: LogoProps) => {
    // Get the navigate from react router
    const navigate = useNavigate();
    return (
        <Box sx={{cursor: 'pointer', ...sx}} {...props} display={'flex'} justifyContent={'flex-start'} alignItems={'flex-start'} onClick={(e) => navigate('/')}>
            <AssessmentIcon sx={{ fontSize: 24 }} />
            <Typography
                variant="h5"
                noWrap
                marginLeft={1}
                sx={{
                    fontWeight: 500,
                    letterSpacing: '.05rem',
                    color: 'white',
                    textDecoration: 'none',
                }}
            >
                { title }
            </Typography>
        </Box>
    )
}

export default Logo;