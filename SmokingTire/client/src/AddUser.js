import { useState } from "react";
import { useNavigate } from "react-router-dom";

function AddUser() {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");


    const navigate = useNavigate();

    function addUsernameHandler(e) {
        setUsername(e.target.value);
    }

    function addPasswordHandler(e) {
        setPassword(e.target.value);
    }

    function handleSubmit(e) {
        e.preventDefault();

        fetch("http://localhost:8080/api/security/register" + "/" + username + "/" + password, {
            method: "POST"
        })
            .then(response => {
                alert(response.status)
                navigate("/login")
            })
            .catch(
                rejection => console.log("failure ", rejection)
            );
    }


    return (
        <div className="register">

            <form className="addUser" onSubmit={handleSubmit}>
                <h3 className="addUserTitle">Register an account:</h3><br />
                <div className="stringInput">
                    <label htmlFor="username"><b>Enter Username</b></label><br />
                    <input name="username" placeholder="username" onChange={addUsernameHandler}></input><br /><br />
                </div>
                <div className="stringInput">
                    <label htmlFor="password"><b>Enter Password</b></label><br />
                    <input name="password" placeholder="password" onChange={addPasswordHandler}></input><br /><br />
                </div>
                <button type="submit">Create Account</button>
            </form>
        </div>
    )
}

export default AddUser;