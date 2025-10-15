import { useContext, useEffect, useRef, useState } from "react";
import { AlertCircle, Eye, EyeOff } from "lucide-react";
import { usePost } from "../hooks/hooks";
import { useNavigate } from "react-router-dom";
import AuthContext from '../context/AuthContextProvider'

const Login = () => {

    const { setAuth } = useContext(AuthContext);

    const navigate = useNavigate();

    const { response, loading, error, postData } = usePost();

    const usernameRef = useRef();
    const errorRef = useRef();

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [showPassword, setShowPassword] = useState(false)
    const [errorMsg, setErrorMsg] = useState('');
    const [success, setSuccess] = useState(false);

    useEffect(() => {
        usernameRef.current.focus();
    }, [])

    const handleSubmit = async (e) => {
        e.preventDefault();

        postData({
            route: "/user/login",
            body: {
                "username": username,
                "password": password
            }
        });
    }

    useEffect(() => {
        if (response?.success) {
            setAuth({
                "id": response?.data?.id,
                "email":response?.data?.email,
                "username": response?.data?.username,
                "roles": response?.data?.roles
            })
            navigate("/");
        }
    }, [response])

    useEffect(() => {
        if (error) {
            setErrorMsg(error);
            errorRef.current.focus();
        }
    }, [error])

    return (
        <div className="flex flex-col min-h-screen justify-center items-center bg-black">
            <section className="w-full max-w-md p-8 rounded-md shadow-lg bg-white">
                <p ref={errorRef}
                    className={`mb-4 text-sm flex items-center gap-2 ${errorMsg ? "text-red-600" : "hidden"
                        }`}
                    aria-live="assertive">

                    <AlertCircle size={16} />{errorMsg}
                </p>
                <h1 className="mb-6 text-center text-2xl font-bold">
                    Login
                </h1>

                <form onSubmit={handleSubmit} className="space-y-6">
                    <div className="relative flex items-center">
                        <input
                            type="text"
                            id="username"
                            placeholder="username"
                            value={username}
                            ref={usernameRef}
                            autoComplete="off"
                            required
                            onChange={(e) => setUsername(e.target.value)}
                            className="mt-1 w-full rounded-lg border border-gray-300 px-3 py-2 text-gray-700 focus:border-blue-500 focus:ring-blue-200 focus:outline-none focus:ring"
                        />

                    </div>


                    <div className="relative flex items-center">
                        <input
                            type={showPassword ? "text" : "password"}
                            id="password"
                            placeholder="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                            className="mt-1 w-full rounded-lg border border-gray-300 px-3 py-2 text-gray-700 focus:border-blue-500 focus:ring-blue-200 focus:outline-none focus:ring"
                        />
                        <button
                            type="button"
                            tabIndex={-1}
                            onClick={() => setShowPassword((prev) => !prev)}
                            className="absolute inset-y-0 right-2 flex items-center text-gray-500 hover:text-gray-700 cursor-pointer"
                        >
                            {showPassword ? <EyeOff size={18} /> : <Eye size={18} />}
                        </button>
                    </div>

                    <button type="submit" className="w-full px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition disabled:bg-gray-400 cursor-pointer"
                        disabled={!username || !password}
                    >
                        {loading ? "Logging in..." : "Login"}
                    </button>
                </form>
            </section>
        </div>
    )
}

export default Login;