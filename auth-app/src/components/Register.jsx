import { useEffect, useRef, useState } from "react";
import { AlertCircle, CheckCircle2, Camera, Eye, EyeOff, TurkishLira } from "lucide-react";
import { NavLink, useNavigate } from "react-router-dom";
import { usePost } from "../hooks/hooks";

const USERNAME_REGEX = /^[A-z][A-z0-9-_]{3,23}$/;
const EMAIL_REGEX = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
const PASSWORD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%]).{8,24}$/;

const Register = () => {

    const { response, loading, error, postData } = usePost();

    const navigate = useNavigate();

    const usernameRef = useRef();
    const emailRef = useRef();
    const errorRef = useRef();

    const [username, setUsername] = useState('');
    const [validUsername, setValidUsername] = useState(false);
    const [usernameFocus, setUsernameFocus] = useState(false);

    const [email, setEmail] = useState('')
    const [validEmail, setValidEmail] = useState(false);
    const [emailFocus, setEmailFocus] = useState(false);

    const [password, setPassword] = useState('')
    const [validPassword, setValidPassword] = useState(false);
    const [passwordFocus, setPasswordFocus] = useState(false);
    const [showPassword, setShowPassword] = useState(false);

    const [confirmPassword, setConfirmPassword] = useState('');
    const [validConfirmPassword, setValidConfirmPassword] = useState(false);
    const [confirmPasswordFocus, setConfirmPasswordFocus] = useState(false);
    const [showConfirmPassword, setShowConfirmPassword] = useState(false);

    const [errorMsg, setErrorMsg] = useState('');
    const [success, setSuccess] = useState(false);

    useEffect(() => {
        usernameRef.current.focus();
    }, [])

    useEffect(() => {
        const result = USERNAME_REGEX.test(username)
        setValidUsername(result);
    }, [username])

    useEffect(() => {
        const result = EMAIL_REGEX.test(email)
        setValidEmail(result);
    }, [email])

    useEffect(() => {
        const result = PASSWORD_REGEX.test(password);
        setValidPassword(result);

        const match = password === confirmPassword;
        setValidConfirmPassword(match);

    }, [password, confirmPassword])

    useEffect(() => {
        setErrorMsg('')
    }, [username, password, confirmPassword, email])

    const handleSubmit = async (e) => {
        e.preventDefault();

        setErrorMsg("");
        setSuccess(false);
        // If we retry, then we wont have the error from previous request
        // Thats why we clear the error msg and success

        const v1 = USERNAME_REGEX.test(username);
        const v2 = EMAIL_REGEX.test(email);
        const v3 = PASSWORD_REGEX.test(password);
        if (!v1 || !v2 || !v3) {
            setErrorMsg("Invalid Entry !");
            return;
        }

        await postData({
            route: "/user/register",
            body: {
                "username": username,
                "email": email,
                "password": password
            }
        });
    }

    useEffect(() => {
        if (response?.success) {
            setSuccess(true)
            navigate("/login");
        }
    }, [response])

    useEffect(()=>{
        if(error){
            setErrorMsg(error);
            errorRef.current.focus();
        }
    },[error])

    return (
        <div className="flex flex-col min-h-screen justify-center items-center bg-black">

            <section className="w-full max-w-md p-8 rounded-md shadow-lg bg-white">
                <p
                    ref={errorRef}
                    className={`mb-4 text-sm flex items-center gap-2 ${errorMsg ? "text-red-600" : "hidden"
                        }`}
                    aria-live="assertive"
                >
                    <AlertCircle size={16} /> {errorMsg}
                </p>
                <h1 className="mb-6 text-center text-2xl font-bold ">
                    Register
                </h1>
                <form onSubmit={handleSubmit} className="space-y-6">
                    <div>
                        <div className="relative flex items-center">
                            <input
                                type="text"
                                id="username"
                                placeholder="username"
                                value={username}
                                ref={usernameRef}
                                autoComplete="off"
                                onChange={(e) => setUsername(e.target.value)}
                                required
                                aria-invalid={validUsername ? "false" : "true"}
                                aria-describedby="uidnote"
                                onFocus={() => setUsernameFocus(true)}
                                onBlur={() => setUsernameFocus(false)}
                                className={`mt-1 w-full rounded-lg border border-gray-300 px-3 py-2 text-gray-700 ${validUsername ? "focus:border-green-500 focus:ring-green-200" : !username ? "focus:border-blue-500 focus:ring-blue-200" : "focus:border-red-500 focus:ring-red-200"} focus:outline-none focus:ring `}
                            />
                            <p className="absolute inset-y-0 right-2 flex items-center pointer-events-none">
                                {validUsername ? (
                                    <CheckCircle2 size={18} className="text-green-500" />
                                ) : (
                                    username && <AlertCircle size={18} className="text-red-500" />
                                )}
                            </p>
                        </div>
                        {usernameFocus && username && !validUsername && (
                            <p id="uidnote" className="mt-1 text-sm text-gray-500 italic">
                                4–24 characters. Must start with a letter. Letters, numbers,
                                hyphens, and underscores allowed.
                            </p>
                        )}
                    </div>
                    <div>
                        <input
                            type="email"
                            id="email"
                            placeholder="email"
                            value={email}
                            ref={emailRef}
                            autoComplete="off"
                            onChange={(e) => setEmail(e.target.value)}
                            required
                            aria-invalid={validEmail ? "false" : "true"}
                            onFocus={() => setEmailFocus(true)}
                            onBlur={() => setEmailFocus(false)}
                            className={`mt-1 w-full rounded-lg border border-gray-300 px-3 py-2 text-gray-700 ${validEmail ? "focus:border-green-500 focus:ring-green-200" : !email ? "focus:border-blue-500 focus:ring-blue-200" : "focus:border-red-500 focus:ring-red-200"} focus:outline-none focus:ring `}
                        />
                        {emailFocus && email && !validEmail && (
                            <p className="mt-1 text-sm text-gray-500 italic">
                                Must be a valid email (example@domain.com).
                            </p>
                        )}
                    </div>
                    <div>
                        <div className="relative flex items-center">
                            <input
                                type={showPassword ? "text" : "password"}
                                id="password"
                                placeholder="password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                required
                                aria-invalid={validPassword ? "false" : "true"}
                                onFocus={() => setPasswordFocus(true)}
                                onBlur={() => setPasswordFocus(false)}
                                className={`mt-1 w-full rounded-lg border border-gray-300 px-3 py-2 text-gray-700 ${validPassword ? "focus:border-green-500 focus:ring-green-200" : !password ? "focus:border-blue-500 focus:ring-blue-200" : "focus:border-red-500 focus:ring-red-200"} focus:outline-none focus:ring `}
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
                        {passwordFocus && password && !validPassword && (
                            <p className="mt-1 text-sm text-gray-500 italic">
                                8–24 characters. Must include uppercase, lowercase, number, and
                                special character (!@#$%).
                            </p>
                        )}
                    </div>
                    <div>
                        <div className="relative flex items-center">
                            <input
                                type={showConfirmPassword ? "text" : "password"}
                                id="confirmpassword"
                                placeholder="confirm password"
                                value={confirmPassword}
                                onChange={(e) => setConfirmPassword(e.target.value)}
                                required
                                aria-invalid={validConfirmPassword ? "false" : "true"}
                                onFocus={() => setConfirmPasswordFocus(true)}
                                onBlur={() => setConfirmPasswordFocus(false)}
                                className={`mt-1 w-full rounded-lg border border-gray-300 px-3 py-2 text-gray-700 ${validConfirmPassword ? "focus:border-green-500 focus:ring-green-200" : !validConfirmPassword ? "focus:border-blue-500 focus:ring-blue-200" : "focus:border-red-500 focus:ring-red-200"} focus:outline-none focus:ring `}
                            />

                            <button
                                type="button"
                                tabIndex={-1}
                                className="absolute inset-y-0 right-2 flex items-center text-gray-500 hover:text-gray-700 cursor-pointer"
                                onClick={(e) => setShowConfirmPassword((prev) => !prev)}
                            >
                                {showConfirmPassword ? <EyeOff size={18} /> : <Eye size={18} />}
                            </button>
                        </div>
                        {confirmPasswordFocus && confirmPassword && !validConfirmPassword && (
                            <p className="mt-1 text-sm text-gray-500 italic">
                                Must match the password entered above.
                            </p>
                        )}

                    </div>
                    {/* <div className="p-4 space-y-4">
                        <div className="flex items-center gap-2">
                            <AlertCircle color="red" size={24} />
                            <span className="text-red-600">This is an alert icon</span>
                        </div>
                        <div className="flex items-center gap-2">
                            <CheckCircle2 color="green" size={24} />
                            <span className="text-green-600">This is a success icon</span>
                        </div>
                        <div className="flex items-center gap-2">
                            <Camera size={32} />
                            <span>Camera icon with default color</span>
                        </div>
                    </div> */}
                    <button type="submit" className="w-full px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition disabled:bg-gray-400 cursor-pointer"
                        disabled={!validUsername || !validEmail || !validPassword || !validConfirmPassword}
                    >
                        {loading ? "Singing up..." : "Sign Up"}
                    </button>
                </form>

                <div className="mt-4">
                    Have an account? 
                    <NavLink to="/login"> Login
                    </NavLink>
                </div>
            </section>
        </div>
    )
}

export default Register;