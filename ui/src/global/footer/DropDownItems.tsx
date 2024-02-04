import {Accordion, AccordionDetails, AccordionSummary, Box, BoxProps, Typography} from "@mui/material"
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';

interface DropDownItemsProps extends BoxProps {
    title: string,
    contentList: Array<any>
}

// Dropdown Items for responsive Footer
const DropDownItems: React.FunctionComponent<DropDownItemsProps> = ({ title, contentList, ...props }) => {
    return (
        <Box {...props}>
            <Accordion sx={{ bgcolor: 'primary.main', color: 'white'}}>
                <AccordionSummary
                    expandIcon={<ExpandMoreIcon sx={{color: 'white'}}/>}
                >
                    <Typography color={'neutral.light'} variant="h3" fontWeight={'bold'}>{title}</Typography>
                </AccordionSummary>
                <AccordionDetails>
                    {contentList.map((item, i) => (
                        <Typography mb={0.5} key={i}>{item}</Typography>
                    ))}
                </AccordionDetails>
            </Accordion>
        </Box>
    )
}


export default DropDownItems;