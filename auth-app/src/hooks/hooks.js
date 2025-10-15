import axios from "axios";
import { useCallback, useState } from "react"
const baseUrl = import.meta.env.VITE_APP_API_URL

export const usePost = () => {

    const [response, setResponse] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const postData = useCallback(async ({ route, body }) => {
        setLoading(true);
        setError(null)
        setResponse(null)

        try {
            const res = await axios.post(
                `${baseUrl}${route}`,
                body,
                {
                    headers: { 'Content-Type': 'application/json' },
                    withCredentials: true
                }
            );
            setResponse(res.data)
        }
        catch (err) {
            const message = err.response?.data?.message || "Something went wrong. Please try again.";
            setError(message)
        }
        finally {
            setLoading(false);
        }
    }, [])


    return { response, loading, error, postData };

}
