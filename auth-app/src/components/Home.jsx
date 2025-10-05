import { useLocation } from "react-router-dom";
const Home = ()=>{

    const location = useLocation()
    const {username} = location.state || ""
    return (
        <div className="min-h-screen flex justify-around items-center">
            <h1>Hello {username}</h1>
        </div>
    )
}

export default Home;