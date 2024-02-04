import {Box, BoxProps, Typography} from "@mui/material";

interface ErrorTextProps extends BoxProps {
    errorMessage: string
}

const ErrorText:React.FunctionComponent<ErrorTextProps> = ({errorMessage, ...props}) => {
    return (
        <Box sx={{color: 'red'}}>
            <Typography component={'div'}>{errorMessage}</Typography>
        </Box>
    )
}

export default ErrorText;