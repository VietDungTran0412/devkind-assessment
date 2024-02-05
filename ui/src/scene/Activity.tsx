import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import PaginationItem from '@mui/material/PaginationItem';
import {Box, Divider, Pagination, Stack, Typography, useMediaQuery, useTheme} from "@mui/material";
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import ArrowForwardIcon from '@mui/icons-material/ArrowForward';
import useGetActivity from "../hooks/useGetActivity";
import {useEffect} from "react";
import {useNotification} from "../context/NotificationProvider";

const MIN_HEIGHT_TABLE_ROW = 50;

const initialPage = {
    page: 0,
    size: 10
}

/* Activity Scene for the application */
const Activity:React.FC = () => {
    const { data, loading, error, page, setPage } = useGetActivity(initialPage);
    const theme = useTheme();
    const matches = useMediaQuery(theme.breakpoints.down('lg'))
    const { showNotification } = useNotification();
    useEffect(() => {
        if(error) {
            showNotification("Internal server error!", "error");
        }
    }, [error]);
    return (
        <Box sx={{mt: 24, display: 'flex', justitfyContent: 'center', flexDirection: 'column', alignItems: 'center', width: matches ? '90%' : '60%', mx: 'auto'}}>
            <Typography sx={{mb: 4}} variant={'h2'}>Your Activity</Typography>
            <Box sx={{ width: '100%' }}>
                <Divider/>
            </Box>
            <Paper sx={{width: '100%', mt: 4, mb: 4, minHeight: MIN_HEIGHT_TABLE_ROW * (initialPage.size + 1) + 1.25}}>
                <TableContainer component={Paper} sx={{height: '100%'}}>
                    <Table sx={{ minWidth: 650 }} aria-label="simple table">
                        <TableHead >
                            <TableRow sx={{minHeight: MIN_HEIGHT_TABLE_ROW }}>
                                <TableCell align="left"><Typography variant={'h4'}>Timestamp</Typography></TableCell>
                                <TableCell align="left"><Typography variant={'h4'}>Description</Typography></TableCell>
                                <TableCell align="left"><Typography variant={'h4'}>Type</Typography></TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {data?.content?.map((row: any, i: number) => (
                                <TableRow
                                    hover
                                    key={i}
                                    sx={{ '&:last-child td, &:last-child th': { border: 0 }, minHeight: MIN_HEIGHT_TABLE_ROW }}
                                >
                                    <TableCell sx={{width: 250}} align="left">{row.createdDate}</TableCell>
                                    <TableCell align="left" sx={{width: 800}}>{row.description}</TableCell>
                                    <TableCell align="left">{row.type}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </Paper>
            <Stack spacing={2} position={'sticky'}>
                <Pagination
                    count={data?.totalPage}
                    page={page+1}
                    disabled={loading}
                    onChange={(e, currentPage) => setPage(currentPage - 1)}
                    renderItem={(item) => (
                        <PaginationItem
                            slots={{ previous: ArrowBackIcon, next: ArrowForwardIcon }}
                            {...item}
                        />
                    )}
                />
            </Stack>
        </Box>
    );
}


export default Activity;