import React, { createContext, useContext, useState } from 'react';
import Snackbar from '@mui/material/Snackbar';
import MuiAlert, { AlertProps } from '@mui/material/Alert';
import CheckIcon from '@mui/icons-material/Check';

function Alert(props: AlertProps) {
    return <MuiAlert elevation={6} variant="filled" {...props} />;
}

interface NotificationContextType {
    showNotification: (message: string, severity: "success" | "error" | "info") => void;
}

const NotificationContext = createContext<NotificationContextType | undefined>(undefined);

export const useNotification = () => {
    const context = useContext(NotificationContext);
    if (!context) {
        throw new Error('useNotification must be used within a NotificationProvider');
    }
    return context;
};

interface NotificationProviderProps {
    children: React.ReactNode;
}

export const NotificationProvider: React.FC<NotificationProviderProps> = ({ children }) => {
    const [notificationOpen, setNotificationOpen] = useState(false);
    const [notificationMessage, setNotificationMessage] = useState('');
    const [alertType, setAlertType] = useState<"info" | "success" | "warning" | "error">("info")

    const showNotification = (message: string, severity: "info" | "success" | "warning" | "error") => {
        setNotificationMessage(message);
        setNotificationOpen(true);
        setAlertType(severity)
    };

    const handleNotificationClose = (event?: React.SyntheticEvent | Event, reason?: string) => {
        if (reason === 'clickaway') {
            return;
        }
        setNotificationOpen(false);
    };

    return (
        <NotificationContext.Provider value={{ showNotification }}>
                <Snackbar open={notificationOpen} autoHideDuration={3000} onClose={handleNotificationClose} anchorOrigin={{vertical: 'top', horizontal: 'right'}}>
                    <div>
                        <Alert
                            icon={<CheckIcon fontSize="inherit" />}
                            severity={alertType}
                            variant={'standard'}
                            sx={{ width: '100%' }}
                        >
                            {notificationMessage}
                        </Alert>
                    </div>

            </Snackbar>

            {children}
        </NotificationContext.Provider>
    );
};
