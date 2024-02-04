import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import PaginationItem from '@mui/material/PaginationItem';
import {Box, Divider, Pagination, Stack, Typography} from "@mui/material";
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import ArrowForwardIcon from '@mui/icons-material/ArrowForward';

function createData(
    timestamp: string,
    description: string,
    type: string
) {
    return { timestamp, description, type };
}

const rows = [
    createData("13-03-2003", "Test Activity", "AUTH"),
    createData("13-03-2003", "Test Activity", "AUTH"),
    createData("13-03-2003", "Test Activity", "AUTH"),
    createData("13-03-2003", "Test Activity", "AUTH"),
    createData("13-03-2003", "Test Activity", "AUTH"),
];

const Activity:React.FC = () => {
    return (
        <Box sx={{mt: 24, display: 'flex', justitfyContent: 'center', flexDirection: 'column', alignItems: 'center', width: '60%', mx: 'auto'}}>
            <Typography sx={{mb: 4}} variant={'h2'}>Your Activity</Typography>
            <Box sx={{ width: '100%' }}>
                <Divider/>
            </Box>
            <Paper sx={{width: '100%', mt: 4, mb: 4}}>
                <TableContainer component={Paper}>
                    <Table sx={{ minWidth: 650 }} aria-label="simple table">
                        <TableHead>
                            <TableRow>
                                <TableCell align="left">Timestamp</TableCell>
                                <TableCell align="left">Description</TableCell>
                                <TableCell align="left">Type</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {rows.map((row, i) => (
                                <TableRow
                                    key={i}
                                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                >
                                    <TableCell align="left">{row.timestamp}</TableCell>
                                    <TableCell align="left" sx={{minWidth: 450}}>{row.description}</TableCell>
                                    <TableCell align="left">{row.type}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </Paper>
            <Stack spacing={2}>
                <Pagination
                    count={10}

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