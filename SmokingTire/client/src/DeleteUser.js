import { useContext, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import AuthContext from './AuthContext';

function DeleteUser() { 
    const {userId} = useParams();
    const [user, setUser] = useContext(AuthContext);

    const navigate = useNavigate();

    function handleDelete(event) {
        event.preventDefault();

        localStorage.removeItem("token");
        setUser(null);

        fetch("http://localhost:8080/api/security/delete/" + userId, {
            method: "DELETE"
        })
        .then(response => {

            if (response.status === 204) {
                navigate("/");
            } else if (response.status === 404) {
                alert("User not found.");
            } else {
                console.log(response)
                alert("Failed with status: \n\n" + response.status);
            }

        })
        .catch(rejection => alert("oops! \n\n" + rejection));

    }

    function cancel() {
        navigate("/userpage");
    }

    return (
        <div className="delete-user">
            <h2>Delete Account</h2>
            
            <div className="delete-user-card">
                <p>Are you sure you want to delete this account? This action can not be undone and will delete all history of your activity.</p>

                <button id="delete-user" onClick={handleDelete}>Delete</button>&nbsp;
                <button onClick={cancel}>Cancel</button>
            </div>
        </div>
    )

}

export default DeleteUser;