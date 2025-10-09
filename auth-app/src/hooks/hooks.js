import axios from "axios";
import { useState } from "react"
const baseUrl = import.meta.env.VITE_APP_API_URL

export const usePost = () => {

    const [response, setResponse] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const postData = async ({ route, body }) => {
        setLoading(true);
        try {
            const res = await axios.post(`${baseUrl}${route}`, body);
            setResponse(res.data);
        }
        catch (error) {
            setError(error)
        }
        finally {
            setLoading(false);
        }
    }


    return { response, loading, error, postData };

}
