import {Box, BoxProps, Typography} from "@mui/material";

interface ListItemsProps extends BoxProps {
    title: string,
    contentList: Array<any>
}

// Item inside Footer
const ListItems:React.FunctionComponent<ListItemsProps> = ({ title, contentList , ...props}) => {
    return (
        <Box {...props}>
            <Typography color={'neutral.light'} mb={2} variant="h3" fontWeight={'bold'}>{title}</Typography>
            {contentList.map((item, i) => <Typography mb={0.5} key={i}>{item}</Typography>)}
        </Box>
    )
}

export default ListItems;