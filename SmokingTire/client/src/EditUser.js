import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";

function EditUser(){


    const [toEdit, setToEdit] = useState(null);

    const {username} = useParams();

    const nav = useNavigate();


    useEffect(
        () => {
            const jwt = localStorage.getItem("token");
            if(jwt){
                fetch("http://localhost:8080/api/security/findUser/" + username,
                {
                    headers: {
                        Authorization: "Bearer " + jwt
                    }
                })
                .then(response => {
                    if(response.status == 200){
                        return response.json();
                    }else{
                        console.log(response);
                        alert("retrieving toEdit failed");
                    }
                })
                .then(retrievedUser => {
                    console.log(retrievedUser);
                    setToEdit(retrievedUser);
                })
                .catch(rejection => {
                    console.log(rejection);
                    alert("Something very bad happened...");
                });
            }else{
                nav("/login");
            }
        },
        []
    );



    function handleUsernameChange(event){
        let copy = {...toEdit};
        copy.username = event.target.value;
        setToEdit( copy );
    }


    function handlePasswordChange(event){
        let copy = {...toEdit};
        copy.password = event.target.value;
        setToEdit( copy );
    }


    function handleEditSubmit(event){
        event.preventDefault();

        const jwt = localStorage.getItem("token");
        const updatedUser = {userId:toEdit.userId, username:toEdit.username, password:toEdit.password, roles:toEdit.roles};

        fetch("http://localhost:8080/api/security/update/" + toEdit?.userId, {
            method: "PUT",
            headers: {
                Authorization: "Bearer " + jwt,
                "Content-Type": "application/json"
            },
            body: JSON.stringify(updatedUser)
        })
        .then(response => {
            if(response.status == 204){
                nav("/userpage");
            }else{
                console.log(response);
                alert("update failed");
            }
        })
        .catch(rejection => {
            console.log(rejection);
            alert("Something bad has happened.");
        });
    }

    function cancel() {
        nav("/userpage");
    }

    return toEdit ? <form onSubmit={handleEditSubmit}>
            <label htmlFor="username">New Username: </label><br/>
            <input className="username-edit" id="username" value={toEdit?.username} onChange={handleUsernameChange}></input><br/>

            <label htmlFor="password">New Password: </label><br/>
            <input type="password" id="password" onChange={handlePasswordChange}></input><br/>

            <button>Submit</button>
            <button onClick={cancel}>Cancel</button>

    </form> :
    <></>

}

export default EditUser;