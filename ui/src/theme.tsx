import { createTheme } from "@mui/material";

// Theme utilities
export const shades = {
    primary: {
        100: '#cccccc',
        200: '#999999',
        300: '#666666',
        400: '#333333',
        500: '#111111',
        600: '#000000',
        700: '#000000',
        800: '#000000',
        900: '#000000',
    },
    secondary: {
        500: '#C95C0A'
    },
    neutral: {
        100: '#f5f5f5',
        200: '#ecebeb',
        300: '#e2e1e1',
        400: '#d9d7d7',
        500: '#cfcdcd',
        600: '#a6a4a4',
        700: '#7c7b7b',
        800: '#535252',
        900: '#292929',
    }
}


export const theme = createTheme({
    palette: {
        mode: "light",
        primary: {
            main: shades.primary[500]
        },
        secondary: {
            main: shades.secondary[500]
        },
    },
    typography: {
        fontSize: 12,
        h1: {
            fontSize: 48
        },
        h2: {
            fontSize: 36
        },
        h3: {
            fontSize: 20
        },
        h4: {
            fontSize: 14,
            fontWeight: 'bold'
        },
    }
})