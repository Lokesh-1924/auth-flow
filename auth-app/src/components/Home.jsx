import { useContext } from "react";
import AuthContext from "../context/AuthContextProvider";

const Home = ()=>{

    const {auth} = useContext(AuthContext);    
    return (
        <div className="min-h-screen flex flex-col justify-center items-center">
            <h1>Hello {auth.username}</h1>
            <h1>Your email : {auth.email}</h1>
            <h1>Your roles : {auth.roles}</h1>
            <h1>Your id : {auth.id}</h1>
        </div>
    )
}

export default Home;