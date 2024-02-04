import {useState} from "react";
import {useAuth} from "../context/AuthContext";

const API_URL = process.env.REACT_APP_API_URL;

interface UpdatePasswordProps {
    username: string,
    password: string,
    newPassword: string
}

const useUpdatePassword = () => {
    const [data, setData] = useState<any>(null);
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);
    const { jwt } = useAuth();
    const update = async (values: UpdatePasswordProps) => {
        setData(null)
        setError(null)
        setLoading(true)
        await fetch(`${API_URL}/auth/update-password`, {
            method: "PUT",
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${jwt}`
            },
            body: JSON.stringify(values)
        }).then(res => {

            // Handle API request error
            if(res.status === 401 || res.status === 400) {
                setError("Username or your old password is inaccurate")
            }
            return res.json();
        })
            .then(res => {
                console.log(res)
                setData(res.data)
            })
            .catch(er => setError("Unexpected internal error!"))
            .finally(() => setLoading(false));
    }
    return { data, loading, error, update };
}

export default useUpdatePassword;