import {useState} from "react";

const API_URL = process.env.REACT_APP_API_URL;

interface RegisterFormData {
    firstName: string;
    lastName: string;
    email: string;
    password: string;
    dateOfBirth: string;
}

// Fetch register
const useRegister = () => {
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const [data, setData] = useState<any>(null);

    const register = async (formData: RegisterFormData) => {
        setData(null)
        setError(null)
        setLoading(true)
        await fetch(  `${API_URL}/auth/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        }).then(res => {
            if(res.status === 409) {
                setError("Username has been already existed!")
            }
            return res.json();
        })
            .then(res => {
                setData(res.data)
            })
            .catch(er => setError("Unexpected internal error!"))
            .finally(() => setLoading(false));
    }

    return {loading, error, data, register}
}

export default useRegister;