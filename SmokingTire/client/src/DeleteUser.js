import { useContext, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import AuthContext from './AuthContext';

function DeleteUser() { 
    const [toDelete, setToDelete] = useContext(AuthContext);
    const {id} = useParams(toDelete);
    const navigate = useNavigate();

    function handleDelete(event) {
        event.preventDefault();

        const jwt = localStorage.getItem("token");

        if (jwt) {
            fetch("http://localhost:8080/api/security/delete/" + id, {
                method: "DELETE",
                headers: {
                    Authorization: "Bearer " + jwt
                }
            })
            .then(response => {

                if (response.status === 204) {
                    navigate("/home");
                    localStorage.removeItem("token");
                    setToDelete((prev) => ({...prev, toDelete : null}));
                } else if (response.status === 404) {
                    alert("User not found.");
                } else {
                    alert("Failed with status: \n\n" + response.status);
                }

            })
            .catch(rejection => alert("oops! \n\n" + rejection));
        } else {
            navigate("/login")
        }

    }

    function cancel() {
        navigate("/userpage");
    }

    return (
        <div className="delete-user">
            <h2>Delete {toDelete?.username}</h2>
            
            <div className="delete-user-card">
                <p>Are you sure you want to delete this account? This action can not be undone.</p>

                <button id="delete-user" onClick={handleDelete}>Delete</button>&nbsp;
                <button onClick={cancel}>Cancel</button>
            </div>
        </div>
    )

}

export default DeleteUser;